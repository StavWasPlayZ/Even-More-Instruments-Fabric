package com.cstav.evenmoreinstruments.block.blockentity;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static void load() {}


     public static final BlockEntityType<LooperBlockEntity> LOOPER = register("looper",
         BlockEntityType.Builder.of(
             LooperBlockEntity::new,
             ModBlocks.LOOPER
         ).build(null)
     );

    public static final BlockEntityType<ModInstrumentBlockEntity> INSTRUMENT_BE = register("instrument_be",
        BlockEntityType.Builder.of(
            ModInstrumentBlockEntity::new,
            ModBlocks.KEYBOARD,
            ModBlocks.KEYBOARD_STAND,
            ModBlocks.KOTO
        )
        .build(null)
    );
    
    private static <T extends BlockEntity> BlockEntityType<T> register(final String name, final BlockEntityType<T> bet) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, EMIMain.loc(name), bet);
        return bet;
    }
    
}