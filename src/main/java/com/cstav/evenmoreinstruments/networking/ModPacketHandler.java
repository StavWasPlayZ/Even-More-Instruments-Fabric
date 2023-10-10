package com.cstav.evenmoreinstruments.networking;

import java.util.List;

import com.cstav.evenmoreinstruments.networking.packet.ModOpenInstrumentPacket;
import com.cstav.evenmoreinstruments.networking.packet.OpenNoteBlockInstrumentPacket;
import com.cstav.genshinstrument.networking.IModPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;


// Copy pasta
public class ModPacketHandler {

    @SuppressWarnings("unchecked")
    private static final List<Class<IModPacket>>
        S2C_PACKETS = List.of(new Class[] {
            ModOpenInstrumentPacket.class, OpenNoteBlockInstrumentPacket.class

            // LooperPlayState.class, LooperRemovedPacket.class
            // SyncModTagPacket.class
        }),
        C2S_PACKETS = List.of(new Class[] {
            // LooperRecordStatePacket.class, UpdateLooperRemovedForInstrument.class
        })
    ;


    public static void registerClientPackets() {
        for (final Class<IModPacket> packetClass : S2C_PACKETS) {

            ClientPlayNetworking.registerGlobalReceiver(
                IModPacket.type(packetClass),
                IModPacket::handle
            );

        }
    }
    public static void registerServerPackets() {
        for (final Class<IModPacket> packetClass : C2S_PACKETS) {

            ServerPlayNetworking.registerGlobalReceiver(
                IModPacket.type(packetClass),
                IModPacket::handle
            );

        }
    }


    public static void sendToServer(final FabricPacket packet) {
        ClientPlayNetworking.send(packet);
    }
    public static void sendToClient(final FabricPacket packet, final ServerPlayer player) {
        ServerPlayNetworking.send(player, packet);
    }
    
}
