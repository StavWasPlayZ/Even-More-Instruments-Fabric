package com.cstav.evenmoreinstruments.networking.packet.s2c;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.genshinstrument.networking.IModPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * A packet telling the client, who just attempted
 * to record into a looper - that they cannot.
 */
public class LooperUnplayablePacket extends IModPacket {
    public static final LooperUnplayablePacket INSTANCE = new LooperUnplayablePacket();
    public static final String MOD_ID = EMIMain.MODID;
    public static final StreamCodec<RegistryFriendlyByteBuf, LooperUnplayablePacket> CODEC = StreamCodec.unit(INSTANCE);
}
