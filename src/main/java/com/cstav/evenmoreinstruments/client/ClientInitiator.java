package com.cstav.evenmoreinstruments.client;

import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.client.gui.instrument.LooperOverlayInjector;
import com.cstav.evenmoreinstruments.client.gui.instrument.guitar.GuitarScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.keyboard.KeyboardScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.noteblockinstrument.NoteBlockInstrumentScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.saxophone.SaxophoneScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.trombone.TromboneScreen;
import com.cstav.evenmoreinstruments.client.gui.instrument.violin.ViolinScreen;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.genshinstrument.networking.IModPacket;
import com.cstav.genshinstrument.util.CommonUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

@Environment(EnvType.CLIENT)
public class ClientInitiator implements ClientModInitializer {

    private static final Class<?>[] LOAD_ME = new Class[] {
        ViolinScreen.class, TromboneScreen.class,
        NoteBlockInstrumentScreen.class, KeyboardScreen.class,
        GuitarScreen.class, SaxophoneScreen.class
    };


    @Override
    public void onInitializeClient() {
        registerClientPackets();
        
        KeyMappings.load();

        ModLoadingContext.registerConfig(Main.MODID, ModConfig.Type.CLIENT, ModClientConfigs.CONFIGS);
        CommonUtil.loadClasses(LOAD_ME);

        ScreenEvents.AFTER_INIT.register(LooperOverlayInjector::onScreenInit);
    }


    public static void registerClientPackets() {
        for (final Class<IModPacket> packetClass : ModPacketHandler.S2C_PACKETS) {

            ClientPlayNetworking.registerGlobalReceiver(
                IModPacket.getChannelName(packetClass),
                (client, handler, buf, sender) ->
                    ModPacketHandler.handlePacket(client.player, sender, buf, packetClass, client::execute)
            );

        }
    }
    
}
