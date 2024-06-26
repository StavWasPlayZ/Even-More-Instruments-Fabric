package com.cstav.evenmoreinstruments.client.gui.instrument.trombone;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.sound.ModSounds;
import com.cstav.genshinstrument.client.gui.screen.instrument.partial.InstrumentThemeLoader;
import com.cstav.genshinstrument.client.gui.screen.instrument.partial.notegrid.GridInstrumentScreen;
import com.cstav.genshinstrument.sound.NoteSound;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TromboneScreen extends GridInstrumentScreen {
    public static final ResourceLocation INSTRUMENT_ID = new ResourceLocation(EMIMain.MODID, "trombone");


    @Override
    public NoteSound[] getInitSounds() {
        return ModSounds.TROMBONE;
    }

    @Override
    public ResourceLocation getInstrumentId() {
        return INSTRUMENT_ID;
    }
    @Override
    public boolean isGenshinInstrument() {
        return false;
    }


    private static final InstrumentThemeLoader THEME_LOADER = new InstrumentThemeLoader(INSTRUMENT_ID);
    @Override
    public InstrumentThemeLoader getThemeLoader() {
        return THEME_LOADER;
    }
    
}
