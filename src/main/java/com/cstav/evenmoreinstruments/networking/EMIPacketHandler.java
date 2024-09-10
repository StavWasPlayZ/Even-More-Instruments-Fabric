package com.cstav.evenmoreinstruments.networking;

import com.cstav.evenmoreinstruments.networking.packet.c2s.DoesLooperExistPacket;
import com.cstav.evenmoreinstruments.networking.packet.c2s.LooperRecordStatePacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.LooperPlayStatePacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.LooperUnplayablePacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.OpenNoteBlockInstrumentPacket;
import com.cstav.evenmoreinstruments.networking.packet.s2c.SyncModTagPacket;
import com.cstav.genshinstrument.networking.IModPacket;
import com.cstav.genshinstrument.util.ServerUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;


// Copy pasta
public class EMIPacketHandler {

    @SuppressWarnings("unchecked")
    private static final List<Class<IModPacket>>
        S2C_PACKETS = List.of(new Class[] {
            OpenNoteBlockInstrumentPacket.class,

            LooperPlayStatePacket.class, LooperUnplayablePacket.class,
            SyncModTagPacket.class
        }),
        C2S_PACKETS = List.of(new Class[] {
            LooperRecordStatePacket.class, DoesLooperExistPacket.class
        })
    ;

    public static void registerCodecs() {
        ServerUtil.registerCodecs(C2S_PACKETS, S2C_PACKETS);
    }

    public static void registerClientPackets() {
        ServerUtil.registerClientPackets(S2C_PACKETS, ClientDistExec.PACKET_SWITCH);
    }
    public static void registerServerPackets() {
        ServerUtil.registerServerPackets(C2S_PACKETS);
    }


    public static void sendToServer(final CustomPacketPayload packet) {
        ClientPlayNetworking.send(packet);
    }
    public static void sendToClient(final CustomPacketPayload packet, final ServerPlayer player) {
        ServerPlayNetworking.send(player, packet);
    }
    
}
