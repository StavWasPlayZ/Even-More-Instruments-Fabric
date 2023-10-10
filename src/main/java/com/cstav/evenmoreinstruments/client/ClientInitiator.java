package com.cstav.evenmoreinstruments.client;

import java.util.List;

import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.client.gui.instrument.guitar.GuitarScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.keyboard.KeyboardScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.noteblockinstrument.NoteBlockInstrumentScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.trombone.TromboneScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.violin.ViolinScreen;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.genshinstrument.GInstrumentMod;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.fml.config.ModConfig;

@Environment(EnvType.CLIENT)
public class ClientInitiator implements ClientModInitializer {

    private static final List<Class<?>> LOAD_ME = List.of(
		ViolinScreen.class, TromboneScreen.class,
        NoteBlockInstrumentScreen.class, KeyboardScreen.class,
        GuitarScreen.class
	);


    @Override
    public void onInitializeClient() {
        ModPacketHandler.registerClientPackets();
        
        KeyMappings.load();

        ForgeConfigRegistry.INSTANCE.register(Main.MODID, ModConfig.Type.CLIENT, ModClientConfigs.CONFIGS);

        //TODO migrate to CommonUtil#loadClasses in newer GI versions
        for (final Class<?> loadMe : LOAD_ME) {
			
			try {
				Class.forName(loadMe.getName());
			} catch (ClassNotFoundException e) {
				GInstrumentMod.LOGGER.error("Failed to load class "+ loadMe.getSimpleName() +": class not found", e);
			}

		}
    }
    
}
