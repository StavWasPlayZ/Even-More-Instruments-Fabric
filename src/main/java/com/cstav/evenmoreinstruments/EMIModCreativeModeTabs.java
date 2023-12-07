package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class EMIModCreativeModeTabs {

    public static final CreativeModeTab
        INSTRUMENT_ACCESSORY_TAB = FabricItemGroup.builder(new ResourceLocation(Main.MODID, "instrument_accessories_tab"))
            .icon(() -> new ItemStack(ModItems.KEYBOARD_STAND))
            .title(Component.translatable("itemGroup.evenmoreinstruments.instrument_accessories"))
            .build()
    ;

}
