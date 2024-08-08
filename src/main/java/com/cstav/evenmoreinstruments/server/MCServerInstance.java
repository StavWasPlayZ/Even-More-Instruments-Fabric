package com.cstav.evenmoreinstruments.server;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

/**
 * A wrapper class for getting the active
 * {@link MinecraftServer} instance.
 */
public class MCServerInstance {

    private static @Nullable MinecraftServer instance;

    public static void attach() {
        ServerLifecycleEvents.SERVER_STARTING.register((server) ->
            instance = server
        );
        ServerLifecycleEvents.SERVER_STOPPED.register((server) ->
            instance = null
        );
    }

    public static @Nullable MinecraftServer get() {
        return instance;
    }
}
