package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.item.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class EMIModCreativeModeTabs {

    public static final CreativeModeTab
        INSTRUMENT_ACCESSORY_TAB = FabricItemGroupBuilder.build(
            new ResourceLocation(Main.MODID, "instrument_accessories_tab"),
                () -> new ItemStack(ModItems.KEYBOARD_STAND)
        )
    ;

}
