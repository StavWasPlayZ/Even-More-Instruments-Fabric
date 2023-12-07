package com.cstav.evenmoreinstruments.block.blockentity;

import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static void load() {}


     public static final BlockEntityType<LooperBlockEntity> LOOPER = register("looper",
         FabricBlockEntityTypeBuilder.create(
             LooperBlockEntity::new, ModBlocks.LOOPER
         ).build(null)
     );

    public static final BlockEntityType<ModInstrumentBlockEntity> INSTRUMENT_BE = register("instrument_be",
        FabricBlockEntityTypeBuilder.create(ModInstrumentBlockEntity::new,
            ModBlocks.KEYBOARD, ModBlocks.KEYBOARD_STAND
        )
        .build(null)
    );
    
    private static <T extends BlockEntity> BlockEntityType<T> register(final String name, final BlockEntityType<T> bet) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(Main.MODID, name), bet);
        return bet;
    }
    
}