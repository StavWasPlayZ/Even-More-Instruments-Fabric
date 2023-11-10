 package com.cstav.evenmoreinstruments.networking.packet;

 import com.cstav.evenmoreinstruments.Main;
 import com.cstav.evenmoreinstruments.block.LooperBlock;
 import com.cstav.evenmoreinstruments.block.blockentity.LooperBlockEntity;
 import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
 import com.cstav.evenmoreinstruments.util.LooperUtil;
 import com.cstav.genshinstrument.networking.IModPacket;
 import com.cstav.genshinstrument.util.InstrumentEntityData;
 import net.fabricmc.fabric.api.networking.v1.PacketSender;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.FriendlyByteBuf;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.entity.BlockEntity;
 import net.minecraft.world.level.block.state.BlockState;

 import java.util.Optional;

 public class LooperRecordStatePacket implements IModPacket {

     private final Optional<InteractionHand> usedHand;
     private final boolean recording;

     public LooperRecordStatePacket(boolean recording, Optional<InteractionHand> usedHand) {
         this.recording = recording;
         this.usedHand = usedHand;
     }
     public LooperRecordStatePacket(final FriendlyByteBuf buf) {
         recording = buf.readBoolean();
         usedHand = buf.readOptional((fbb) -> fbb.readEnum(InteractionHand.class));
     }

     @Override
     public void write(final FriendlyByteBuf buf) {
         buf.writeBoolean(recording);
         buf.writeOptional(usedHand, FriendlyByteBuf::writeEnum);
     }


     //TODO extract all the below to its own method in LooperUtil

     @Override
     public void handle(Player lPlayer, PacketSender responseSender) {
         final ServerPlayer player = (ServerPlayer) lPlayer;

         if (usedHand.isPresent())
             handleItem(player);
         else
             handleBlock(player);
     }

     private void handleBlock(final ServerPlayer player) {
         final BlockPos instrumentBlockPos = InstrumentEntityData.getBlockPos(player);

         final BlockEntity instrumentBlock = player.level().getBlockEntity(instrumentBlockPos);
         final CompoundTag looperTag = LooperUtil.looperTag(instrumentBlock);

         if (isMaliciousPos(player, looperTag))
             return;

         final LooperBlockEntity lbe = LooperBlockEntity.getLBE(player.level(), instrumentBlock);
         changeRecordingState(player, looperTag, lbe, () -> LooperUtil.remLooperTag(instrumentBlock));

         ModPacketHandler.sendToClient(new SyncModTagPacket(Main.modTag(instrumentBlock), instrumentBlockPos), player);
     }
     private void handleItem(final ServerPlayer player) {
         final ItemStack instrumentItem = player.getItemInHand(usedHand.get());
         final CompoundTag looperTag = LooperUtil.looperTag(instrumentItem);

         if (isMaliciousPos(player, looperTag))
             return;


         final LooperBlockEntity lbe = LooperBlockEntity.getLBE(player.level(), instrumentItem);
         changeRecordingState(player, looperTag, lbe, () -> LooperUtil.remLooperTag(instrumentItem));
     }

     private void changeRecordingState(Player player, CompoundTag looperTag, LooperBlockEntity lbe, Runnable removeLooperTagRunnable) {
         if (lbe.isLocked() && !lbe.isLockedBy(player.getUUID()))
             return;

         if (!recording) {
             lbe.lock();

             final BlockState blockState = lbe.getBlockState();
             final Level level = player.level();

             if (!(blockState.getBlock() instanceof LooperBlock looperBlock))
                 return;

             level.setBlock(lbe.getBlockPos(),
                 looperBlock.setPlaying(true, level, blockState, lbe.getBlockPos())
             , 3);

             removeLooperTagRunnable.run();
         } else
             LooperUtil.setRecording(looperTag, true);
     }

     // According to Forge
     @SuppressWarnings("deprecation")
     private static boolean isMaliciousPos(final Player player, final CompoundTag looperTag) {
         final BlockPos looperPos = LooperUtil.getLooperPos(looperTag);
         return !player.level().hasChunkAt(looperPos);
     }

 }
