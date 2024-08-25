package com.cstav.evenmoreinstruments.client;

import com.cstav.evenmoreinstruments.EMIMain;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyMappings {
    public static final String CATEGORY = EMIMain.MODID+".keymaps";

    public static void load() {}
    
    public static final KeyMapping
        INSTRUMENT_TYPE_MODIFIER = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                CATEGORY+".instrument_type_modifier",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT,
                CATEGORY
            )
        ),
        RECORD = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                CATEGORY+".record",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_GRAVE_ACCENT,
                CATEGORY
            )
        )
    ;

}
