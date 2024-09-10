package com.cstav.evenmoreinstruments.networking.packet.s2c;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.genshinstrument.networking.IModPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class LooperPlayStatePacket extends IModPacket {
    public static final String MOD_ID = EMIMain.MODID;
    public static final StreamCodec<RegistryFriendlyByteBuf, LooperPlayStatePacket> CODEC = CustomPacketPayload.codec(
        LooperPlayStatePacket::write,
        LooperPlayStatePacket::new
    );

    public final boolean isPlaying;
    public final BlockPos blockPos;

    public LooperPlayStatePacket(final boolean isPlaying, final BlockPos blockPos) {
        this.isPlaying = isPlaying;
        this.blockPos = blockPos;
    }

    public LooperPlayStatePacket(final RegistryFriendlyByteBuf buf) {
        isPlaying = buf.readBoolean();
        blockPos = buf.readBlockPos();
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(isPlaying);
        buf.writeBlockPos(blockPos);
    }

}
