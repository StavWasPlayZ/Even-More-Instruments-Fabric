package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.ModOpenInstrumentPacket;
import com.cstav.evenmoreinstruments.sound.ModSounds;
import com.cstav.genshinstrument.event.InstrumentPlayedEvent.ByPlayer.ByPlayerArgs;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

class ViolinItem extends AccessoryInstrumentItem {
    public ViolinItem() {
        super((player) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("violin"), player
            ),
            (InstrumentAccessoryItem) ModItems.VIOLIN_BOW,
            "Philharmonia"
        );
    }

    @Override
    public int hurtInstrumentBy(final ByPlayerArgs event, final ItemStack accessory) {
        // If we did a long press, deal damage by 2.
        final boolean playedLong = Arrays.stream(ModSounds.VIOLIN_FULL_NOTE)
            .anyMatch((sound) -> sound.equals(event.sound));

        return super.hurtInstrumentBy(event, accessory) * (playedLong ? 2 : 1);
    }
}