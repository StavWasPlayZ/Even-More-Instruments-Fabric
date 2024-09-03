package com.cstav.evenmoreinstruments.networking.packet;

import com.cstav.evenmoreinstruments.networking.ClientBulgaria;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * A packet telling the client, who just attempted
 * to record into a looper - that they cannot.
 */
public class LooperUnplayablePacket implements IModPacket {

    public LooperUnplayablePacket() {}

    public LooperUnplayablePacket(final FriendlyByteBuf buf) {}

    @Override
    public void handle(Player player, PacketSender responseSender) {
        ClientBulgaria.handleLooperRemovedResponse();
    }

}
