package com.cstav.evenmoreinstruments.networking.packet.s2c;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.genshinstrument.networking.IModPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * Syncs the given mod tag on the client to the block position.
 * Primarily used to update block instruments for looper record states.
 */
public class SyncModTagPacket extends IModPacket {
    public static final String MOD_ID = EMIMain.MODID;
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenNoteBlockInstrumentPacket> CODEC = CustomPacketPayload.codec(
        OpenNoteBlockInstrumentPacket::write,
        OpenNoteBlockInstrumentPacket::new
    );

    public final CompoundTag modTag;
    public final BlockPos pos;

    public SyncModTagPacket(final CompoundTag modTag, final BlockPos pos) {
        this.modTag = modTag;
        this.pos = pos;
    }

    public SyncModTagPacket(final RegistryFriendlyByteBuf buf) {
        // We don't need to read over 0x20000 or however many zeroes.
        modTag = buf.readNbt();
        pos = buf.readBlockPos();
    }

    @Override
    public void write(final RegistryFriendlyByteBuf buf) {
        buf.writeNbt(modTag);
        buf.writeBlockPos(pos);
    }
}
