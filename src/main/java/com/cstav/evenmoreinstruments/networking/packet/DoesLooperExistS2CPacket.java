package com.cstav.evenmoreinstruments.networking.packet;

import com.cstav.evenmoreinstruments.networking.ClientBulgaria;
import com.cstav.genshinstrument.networking.IModPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;


public class DoesLooperExistS2CPacket implements IModPacket {

    private final boolean looperExists;
    public DoesLooperExistS2CPacket(final boolean looperExists) {
        this.looperExists = looperExists;
    }

    public DoesLooperExistS2CPacket(final FriendlyByteBuf buf) {
        looperExists = buf.readBoolean();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(looperExists);
    }

    @Override
    public void handle(Player player, PacketSender responseSender) {
        ClientBulgaria.handleLooperRemovedResponse(looperExists);
    }

}
