package com.cstav.evenmoreinstruments.block;

import com.cstav.evenmoreinstruments.Main;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks {
    
    public static void load() {}

    public static final Block
        KEYBOARD = register("keyboard", new KeyboardBlock(
            Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion().strength(.3f)
        )),
        KEYBOARD_STAND = register("keyboard_stand", new KeyboardStandBlock(
            Properties.copy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion().strength(.3f)
        )),

        LOOPER = register("looper", new LooperBlock(Properties.copy(Blocks.NOTE_BLOCK)))
    ;


    public static Block register(final String name, final Block block) {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Main.MODID, name), block);
        return block;
    }

}
