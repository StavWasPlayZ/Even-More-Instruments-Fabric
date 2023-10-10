package com.cstav.evenmoreinstruments.block.blockentity;

import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.block.ModBlocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static void load() {}


    // public static final RegistryObject<BlockEntityType<LooperBlockEntity>> LOOPER = BLOCK_ENTITIES.register("looper", () ->
    //     BlockEntityType.Builder.of(
    //         LooperBlockEntity::new, ModBlocks.LOOPER.get()
    //     ).build(null)
    // );

    public static final BlockEntityType<ModInstrumentBlockEntity> INSTRUMENT_BE = regsiter("instrument_be",
        BlockEntityType.Builder.of(ModInstrumentBlockEntity::new,
            ModBlocks.KEYBOARD, ModBlocks.KEYBOARD_STAND
        )
        .build(null)
    );
    
    private static <T extends BlockEntity> BlockEntityType<T> regsiter(final String name, final BlockEntityType<T> bet) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Main.MODID, name), bet);
        return bet;
    }
    
}