package com.cstav.evenmoreinstruments.networking.packet.s2c;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.genshinstrument.networking.IModPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class OpenNoteBlockInstrumentPacket extends IModPacket {
    public static final String MOD_ID = EMIMain.MODID;
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenNoteBlockInstrumentPacket> CODEC = CustomPacketPayload.codec(
        OpenNoteBlockInstrumentPacket::write,
        OpenNoteBlockInstrumentPacket::new
    );
    
    public final NoteBlockInstrument instrument;

    public OpenNoteBlockInstrumentPacket(final NoteBlockInstrument instrument) {
        this.instrument = instrument;
    }

    public OpenNoteBlockInstrumentPacket(final RegistryFriendlyByteBuf buf) {
        instrument = buf.readEnum(NoteBlockInstrument.class);
    }
    @Override
    public void write(final RegistryFriendlyByteBuf buf) {
        buf.writeEnum(instrument);
    }
}
