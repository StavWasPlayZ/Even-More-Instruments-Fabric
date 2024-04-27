package com.cstav.evenmoreinstruments.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class MCServerInstance {

    private static MinecraftServer instance;

    public static void initiate() {
        ServerLifecycleEvents.SERVER_STARTING.register((server) ->
            instance = server
        );
        ServerLifecycleEvents.SERVER_STOPPED.register((server) ->
            instance = null
        );
    }

    public static MinecraftServer get() {
        return instance;
    }
}
