package com.cstav.evenmoreinstruments.networking.packet;

import com.cstav.evenmoreinstruments.util.LooperRecordStateUtil;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class LooperRecordStatePacket implements IModPacket {
    private final Optional<InteractionHand> usedHand;
    private final boolean recording;

    public LooperRecordStatePacket(boolean recording, InteractionHand usedHand) {
        this.recording = recording;
        this.usedHand = Optional.ofNullable(usedHand);
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

    @Override
    public void handle(Player lPlayer, PacketSender responseSender) {
        LooperRecordStateUtil.handle((ServerPlayer) lPlayer, usedHand, recording);
    }

}
