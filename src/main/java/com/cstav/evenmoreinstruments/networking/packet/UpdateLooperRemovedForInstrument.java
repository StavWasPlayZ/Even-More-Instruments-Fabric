 package com.cstav.evenmoreinstruments.networking.packet;

 import com.cstav.evenmoreinstruments.Main;
 import com.cstav.evenmoreinstruments.block.blockentity.LooperBlockEntity;
 import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
 import com.cstav.genshinstrument.networking.IModPacket;
 import com.cstav.genshinstrument.util.InstrumentEntityData;
 import net.fabricmc.fabric.api.networking.v1.PacketSender;
 import net.minecraft.core.BlockPos;
 import net.minecraft.network.FriendlyByteBuf;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.entity.BlockEntity;

 import java.util.Optional;


 public class UpdateLooperRemovedForInstrument implements IModPacket {

     final Optional<InteractionHand> hand;

     public UpdateLooperRemovedForInstrument(final InteractionHand hand) {
         this.hand = Optional.of(hand);
     }
     /**
      * Counts this update request as a request for a block instrument
      */
     public UpdateLooperRemovedForInstrument() {
         this.hand = Optional.empty();
     }
     public UpdateLooperRemovedForInstrument(FriendlyByteBuf buf) {
         hand = buf.readOptional((fbb) -> fbb.readEnum(InteractionHand.class));
     }

     @Override
     public void write(final FriendlyByteBuf buf) {
         buf.writeOptional(hand, FriendlyByteBuf::writeEnum);
     }

     @Override
     public void handle(Player lPlayer, PacketSender responseSender) {
         final ServerPlayer player = (ServerPlayer) lPlayer;
         final Level level = player.level();

         LooperBlockEntity result;

         if (hand.isPresent())
             result = LooperBlockEntity.getLBE(level, player.getItemInHand(hand.get()));
         else {
             final BlockPos instrumentBlockPos = InstrumentEntityData.getBlockPos(player);
             final BlockEntity instrumentBlockEntity = level.getBlockEntity(instrumentBlockPos);
            
             result = LooperBlockEntity.getLBE(level, instrumentBlockEntity);

             // Manually update the tag removal for the client
             if (result == null)
                 ModPacketHandler.sendToClient(
                     new SyncModTagPacket(Main.modTag(instrumentBlockEntity), instrumentBlockPos)
                 , player);

         }

         if (result == null)
             ModPacketHandler.sendToClient(new LooperRemovedPacket(), player);
     }
    
 }
