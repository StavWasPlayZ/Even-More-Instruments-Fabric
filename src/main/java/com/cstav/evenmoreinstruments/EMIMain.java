package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.block.blockentity.ModBlockEntities;
import com.cstav.evenmoreinstruments.criteria.ModCriteria;
import com.cstav.evenmoreinstruments.gamerule.ModGameRules;
import com.cstav.evenmoreinstruments.item.ModItems;
import com.cstav.evenmoreinstruments.item.crafting.ModRecipeSerializers;
import com.cstav.evenmoreinstruments.item.emirecord.RecordRepository;
import com.cstav.evenmoreinstruments.mixins.util.InjectedBlockEntity;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EMIMain implements ModInitializer {
    public static final String MODID = "evenmoreinstruments";

    public static CompoundTag modTag(final ItemStack item) {
        return item.getOrCreateTagElement(MODID);
    }
    public static CompoundTag modTag(final BlockEntity be) {
        return ((InjectedBlockEntity)be).getModTag();
    }
    
    @Override
    public void onInitialize() {
        ModPacketHandler.registerServerPackets();

        ModSounds.load();

        ModGameRules.load();
        ModCriteria.load();

        ModBlocks.load();
        ModBlockEntities.load();
        ModRecipeSerializers.load();

        EMIModCreativeModeTabs.load();
        ModItems.load();
        RecordRepository.load();
    }
}
