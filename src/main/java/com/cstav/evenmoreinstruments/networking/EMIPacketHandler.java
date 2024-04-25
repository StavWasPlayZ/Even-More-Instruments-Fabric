package com.cstav.evenmoreinstruments.networking;

import com.cstav.evenmoreinstruments.networking.packet.*;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Consumer;

import java.util.List;


// Copy pasta
public class EMIPacketHandler {

    @SuppressWarnings("unchecked")
    public static final List<Class<IModPacket>>
        S2C_PACKETS = List.of(new Class[] {
            EMIOpenInstrumentPacket.class, OpenNoteBlockInstrumentPacket.class,

            LooperPlayStatePacket.class, LooperRemovedPacket.class,
            SyncModTagPacket.class
        }),
        C2S_PACKETS = List.of(new Class[] {
            LooperRecordStatePacket.class, DoesLooperExistPacket.class
        })
    ;


    public static void registerServerPackets() {
        for (final Class<IModPacket> packetClass : C2S_PACKETS) {

            ServerPlayNetworking.registerGlobalReceiver(
                IModPacket.getChannelName(packetClass),
                (server, player, handler, buf, sender) ->
                    handlePacket(player, sender, buf, packetClass, server::execute)
            );

        }
    }

    public static void handlePacket(Player player, PacketSender sender, FriendlyByteBuf buf,
            Class<? extends IModPacket> packetClass, Consumer<Runnable> executor) {
        IModPacket packet = IModPacket.createPacket(packetClass, buf);

        executor.accept(() -> packet.handle(player, sender));
    }


    public static void sendToServer(final IModPacket packet) {
        ClientPlayNetworking.send(packet.getChannelName(), getPacketBuf(packet));
    }
    public static void sendToClient(final IModPacket packet, final ServerPlayer player) {
        ServerPlayNetworking.send(player, packet.getChannelName(), getPacketBuf(packet));
    }

    private static FriendlyByteBuf getPacketBuf(final IModPacket packet) {
        final FriendlyByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        return buf;
    }
    
}
