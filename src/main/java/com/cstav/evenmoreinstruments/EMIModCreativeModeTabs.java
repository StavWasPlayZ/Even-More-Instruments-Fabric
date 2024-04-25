package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public abstract class EMIModCreativeModeTabs {
    public static void load() {}

    public static final ResourceKey<CreativeModeTab> MUSIC_PRODUCTION_TAB = register("instrument_accessories_tab",
        FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.LOOPER))
            .title(Component.translatable("itemGroup.evenmoreinstruments.music_production_tab"))
            .build()
    );

    private static ResourceKey<CreativeModeTab> register(final String name, final CreativeModeTab tab) {
        final ResourceKey<CreativeModeTab> tabKey = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            new ResourceLocation(EMIMain.MODID, name)
        );

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, tabKey, tab);

        return tabKey;
    }

}
