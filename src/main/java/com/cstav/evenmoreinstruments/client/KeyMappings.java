package com.cstav.evenmoreinstruments.client;

import org.lwjgl.glfw.GLFW;

import com.cstav.evenmoreinstruments.Main;
import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

@Environment(EnvType.CLIENT)
public class KeyMappings {
    public static final String CATEGORY = Main.MODID+".keymaps";

    public static void load() {}
    
    public static final KeyMapping
        VIOLIN_TYPE_MODIFIER = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(CATEGORY+".violin_type_modifier",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT
            , CATEGORY)
        );

}
