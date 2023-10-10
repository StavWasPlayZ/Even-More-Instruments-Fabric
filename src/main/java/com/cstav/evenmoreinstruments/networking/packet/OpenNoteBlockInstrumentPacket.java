package com.cstav.evenmoreinstruments.networking.packet;

import com.cstav.evenmoreinstruments.client.gui.instrument.noteblockinstrument.NoteBlockInstrumentScreen;
import com.cstav.genshinstrument.networking.IModPacket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public class OpenNoteBlockInstrumentPacket implements IModPacket {
    
    private final NoteBlockInstrument instrument;
    private final InteractionHand hand;

    public OpenNoteBlockInstrumentPacket(final NoteBlockInstrument instrument, final InteractionHand hand) {
        this.instrument = instrument;
        this.hand = hand;
    }

    public OpenNoteBlockInstrumentPacket(final FriendlyByteBuf buf) {
        instrument = buf.readEnum(NoteBlockInstrument.class);
        hand = buf.readEnum(InteractionHand.class);
    }
    @Override
    public void write(final FriendlyByteBuf buf) {
        buf.writeEnum(instrument);
        buf.writeEnum(hand);
    }


    @Override
    public void handle(Player player, PacketSender sender) {
        openScreen();
    }
    
    @Environment(EnvType.CLIENT)
    private void openScreen() {
        Minecraft.getInstance().setScreen(new NoteBlockInstrumentScreen(hand, instrument));
    }

}
