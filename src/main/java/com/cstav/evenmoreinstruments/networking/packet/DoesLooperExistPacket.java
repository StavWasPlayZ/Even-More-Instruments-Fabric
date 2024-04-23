package com.cstav.evenmoreinstruments.networking.packet;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.block.blockentity.LooperBlockEntity;
import com.cstav.evenmoreinstruments.networking.EMIPacketHandler;
import com.cstav.evenmoreinstruments.util.LooperUtil;
import com.cstav.genshinstrument.networking.IModPacket;
import com.cstav.genshinstrument.util.InstrumentEntityData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;


public class DoesLooperExistPacket implements IModPacket {
    public static final int MAX_RECORD_DIST = 8;

    final Optional<InteractionHand> hand;

    public DoesLooperExistPacket(final InteractionHand hand) {
        this.hand = Optional.of(hand);
    }

    /**
     * Counts this update request as a request for a block instrument
     */
    public DoesLooperExistPacket() {
        this.hand = Optional.empty();
    }

    public DoesLooperExistPacket(FriendlyByteBuf buf) {
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

        LooperBlockEntity looperBE;

        if (hand.isPresent()) {
            final ItemStack instrumentItem = player.getItemInHand(hand.get());
            looperBE = LooperUtil.getFromInstrument(level, instrumentItem);

            if (looperBE != null)
                // For items, also check if we are too far away
                if (!looperBE.getBlockPos().closerToCenterThan(player.position(), MAX_RECORD_DIST)) {
                    looperBE = null;
                    LooperUtil.remLooperTag(instrumentItem);
                }
        } else {
            final BlockPos instrumentBlockPos = InstrumentEntityData.getBlockPos(player);
            final BlockEntity instrumentBlockEntity = level.getBlockEntity(instrumentBlockPos);

            looperBE = LooperUtil.getFromInstrument(level, instrumentBlockEntity);

            // Manually update the tag removal for the client
            if (looperBE == null) {
                EMIPacketHandler.sendToClient(
                    new SyncModTagPacket(EMIMain.modTag(instrumentBlockEntity), instrumentBlockPos), player
                );
            }

        }

        if (looperBE == null)
            EMIPacketHandler.sendToClient(new LooperRemovedPacket(), player);
    }

}
