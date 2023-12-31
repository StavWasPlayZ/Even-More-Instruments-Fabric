package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class EMIModCreativeModeTabs {

    public static final CreativeModeTab
        INSTRUMENT_ACCESSORY_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.KEYBOARD_STAND))
            .title(Component.translatable("itemGroup.evenmoreinstruments.instrument_accessories_tab"))
            .build()
    ;

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(Main.MODID, "instrument_accessories_tab"), INSTRUMENT_ACCESSORY_TAB
        );
    }

}
