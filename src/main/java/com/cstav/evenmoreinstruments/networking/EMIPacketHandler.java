package com.cstav.evenmoreinstruments.networking;

import com.cstav.evenmoreinstruments.networking.packet.*;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
