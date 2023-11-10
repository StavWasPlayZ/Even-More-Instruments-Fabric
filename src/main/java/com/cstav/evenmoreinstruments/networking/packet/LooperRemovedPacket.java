 package com.cstav.evenmoreinstruments.networking.packet;

 import com.cstav.evenmoreinstruments.networking.ClientBulgaria;
 import com.cstav.genshinstrument.networking.IModPacket;
 import net.fabricmc.fabric.api.networking.v1.PacketSender;
 import net.minecraft.network.FriendlyByteBuf;
 import net.minecraft.world.entity.player.Player;


 public class LooperRemovedPacket implements IModPacket {
    
     public LooperRemovedPacket() {}
     public LooperRemovedPacket(final FriendlyByteBuf buf) {}

     @Override
     public void handle(Player player, PacketSender responseSender) {
         ClientBulgaria.handleLooperRemoved();
     }
    
 }
