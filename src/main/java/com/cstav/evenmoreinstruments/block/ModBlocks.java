package com.cstav.evenmoreinstruments.block;

import com.cstav.evenmoreinstruments.Main;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MaterialColor;

public class ModBlocks {
    
    public static void load() {}

    public static final Block
        KEYBOARD = register("keyboard", new KeyboardBlock(
            Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_BLACK).noOcclusion().strength(.3f)
        )),
        KEYBOARD_STAND = register("keyboard_stand", new KeyboardStandBlock(
            Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_BLACK).noOcclusion().strength(.3f)
        )),
        KOTO = register("koto", new KotoBlock(
            Properties.of().noOcclusion().strength(.3f).sound(SoundType.WOOD)
        )),

        LOOPER = register("looper", new LooperBlock(Properties.copy(Blocks.NOTE_BLOCK)))
    ;


    public static Block register(final String name, final Block block) {
        Registry.register(Registry.BLOCK, new ResourceLocation(EMIMain.MODID, name), block);
        return block;
    }

}
