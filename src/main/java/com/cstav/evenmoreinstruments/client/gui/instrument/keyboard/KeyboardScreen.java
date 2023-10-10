package com.cstav.evenmoreinstruments.client.gui.instrument.keyboard;

import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.sound.ModSounds;
import com.cstav.genshinstrument.client.gui.screen.instrument.partial.InstrumentThemeLoader;
import com.cstav.genshinstrument.client.gui.screen.instrument.partial.notegrid.AbstractGridInstrumentScreen;
import com.cstav.genshinstrument.sound.NoteSound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

@Environment(EnvType.CLIENT)
public class KeyboardScreen extends AbstractGridInstrumentScreen {
    public static final ResourceLocation INSTRUMENT_ID = new ResourceLocation(Main.MODID, "keyboard");

    public KeyboardScreen(InteractionHand hand) {
        super(hand);
    }
    @Override
    public boolean isGenshinInstrument() {
        return false;
    }


    @Override
    public NoteSound[] getInitSounds() {
        return ModSounds.KEYBOARD;
    }

    @Override
    public ResourceLocation getInstrumentId() {
        return INSTRUMENT_ID;
    }


    private static final InstrumentThemeLoader THEME_LOADER = new InstrumentThemeLoader(INSTRUMENT_ID);
    @Override
    public InstrumentThemeLoader getThemeLoader() {
        return THEME_LOADER;
    }
    
}
