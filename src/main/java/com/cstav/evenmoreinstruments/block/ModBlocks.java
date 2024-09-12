package com.cstav.evenmoreinstruments.block;

import com.cstav.evenmoreinstruments.EMIMain;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks {
    
    public static void load() {}

    public static final Block
        KEYBOARD = register("keyboard", new KeyboardBlock(
            Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion().strength(.3f)
        )),
        KEYBOARD_STAND = register("keyboard_stand", new KeyboardStandBlock(
            Properties.ofFullCopy(Blocks.STONE).mapColor(DyeColor.BLACK).noOcclusion().strength(.3f)
        )),
        KOTO = register("koto", new KotoBlock(
            Properties.of().noOcclusion().strength(.3f).sound(SoundType.WOOD)
        )),

        LOOPER = register("looper", new LooperBlock(Properties.ofFullCopy(Blocks.NOTE_BLOCK)))
    ;


    public static Block register(final String name, final Block block) {
        Registry.register(BuiltInRegistries.BLOCK, EMIMain.loc(name), block);
        return block;
    }

}
