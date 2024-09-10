package com.cstav.evenmoreinstruments;

import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.block.blockentity.LooperNoteListener;
import com.cstav.evenmoreinstruments.block.blockentity.ModBlockEntities;
import com.cstav.evenmoreinstruments.criteria.ModCriteria;
import com.cstav.evenmoreinstruments.gamerule.ModGameRules;
import com.cstav.evenmoreinstruments.item.ModItems;
import com.cstav.evenmoreinstruments.item.component.ModDataComponents;
import com.cstav.evenmoreinstruments.item.crafting.ModRecipeSerializers;
import com.cstav.evenmoreinstruments.item.emirecord.RecordRepository;
import com.cstav.evenmoreinstruments.mixins.util.InjectedBlockEntity;
import com.cstav.evenmoreinstruments.networking.EMIPacketHandler;
import com.cstav.evenmoreinstruments.server.MCServerInstance;
import com.cstav.evenmoreinstruments.server.ServerEvents;
import com.cstav.evenmoreinstruments.server.command.ModCommands;
import com.cstav.evenmoreinstruments.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EMIMain implements ModInitializer {
    public static final String MODID = "evenmoreinstruments";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

//    public static CompoundTag modTag(final ItemStack item) {
//        return item.getOrCreateTagElement(MODID);
//    }
    public static CompoundTag modTag(final BlockEntity be) {
        return ((InjectedBlockEntity)be).evenmoreinstruments$getModTag();
    }
    
    @Override
    public void onInitialize() {
        EMIPacketHandler.registerCodecs();
        EMIPacketHandler.registerServerPackets();

        MCServerInstance.attach();
        ServerEvents.register();

        ModSounds.load();

        ModGameRules.load();
        ModCriteria.load();


        ModBlocks.load();
        ModBlockEntities.load();
        ModRecipeSerializers.load();

        LooperNoteListener.register();


        ModDataComponents.load();
        EMIModCreativeModeTabs.load();
        ModItems.load();
        RecordRepository.load();

        ModCommands.register();
    }
}
