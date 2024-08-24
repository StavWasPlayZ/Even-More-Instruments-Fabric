package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.sound.ModSounds;
import com.cstav.genshinstrument.event.InstrumentPlayedEvent.InstrumentPlayedEventArgs;
import com.cstav.genshinstrument.networking.packet.instrument.util.InstrumentPacketUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

class ViolinItem extends AccessoryInstrumentItem {
    public ViolinItem() {
        super((player) -> InstrumentPacketUtil.sendOpenPacket(
                player, new ResourceLocation(EMIMain.MODID, "violin")
            ),
            (InstrumentAccessoryItem) ModItems.VIOLIN_BOW,
            "Philharmonia"
        );
    }

    @Override
    public int hurtAccessoryBy(final InstrumentPlayedEventArgs<?> args, final ItemStack accessory) {
        // If we did a long press, deal damage by 2.
        final boolean playedLong = Arrays.stream(ModSounds.VIOLIN_FULL_NOTE)
            .anyMatch((sound) -> sound.equals(args.sound()));

        return super.hurtAccessoryBy(args, accessory) * (playedLong ? 2 : 1);
    }
}