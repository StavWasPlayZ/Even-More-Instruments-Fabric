package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.mixins.util.InjectedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.block.blockentity.ModBlockEntities;
import com.cstav.evenmoreinstruments.gamerule.ModGameRules;
import com.cstav.evenmoreinstruments.item.ModItems;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.sound.ModSounds;

import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class Main implements ModInitializer {
    public static final String MODID = "evenmoreinstruments";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static CompoundTag modTag(final ItemStack item) {
        return item.getOrCreateTagElement(MODID);
    }
    public static CompoundTag modTag(final BlockEntity be) {
        return ((InjectedBlockEntity)be).getModTag();
    }
    
    @Override
    public void onInitialize() {
        ModPacketHandler.registerServerPackets();
        EMIModCreativeModeTabs.regsiter();

        ModSounds.load();
        ModGameRules.load();


        ModBlocks.load();
        ModBlockEntities.load();

        ModItems.load();
    }
}
