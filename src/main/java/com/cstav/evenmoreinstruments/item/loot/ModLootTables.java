package com.cstav.evenmoreinstruments.item.loot;

import com.cstav.evenmoreinstruments.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import java.util.Map;
import java.util.function.Supplier;

public class ModLootTables {
    private static final float RECORD_DROP_PROBABILITY = .056f;

    private static final Map<ResourceLocation, Supplier<LootPool>> TO_INJECT = Map.of(
        BuiltInLootTables.JUNGLE_TEMPLE, () -> createRecordPool(ModItems.RECORD_SUPER_IDOL),
        BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, () -> createRecordPool(ModItems.RECORD_SAD_VIOLIN),
        BuiltInLootTables.BASTION_OTHER, () -> createRecordPool(ModItems.RECORD_OVEN_KID),
        BuiltInLootTables.ABANDONED_MINESHAFT, () -> createRecordPool(ModItems.RECORD_RICKROLL),
        BuiltInLootTables.BURIED_TREASURE, () -> createRecordPool(ModItems.RECORD_JOHNNY)
    );

    private static LootPool createRecordPool(final Item item) {
        return LootPool.lootPool()
            .add(
                LootItem.lootTableItem(item)
                    .when(LootItemRandomChanceCondition.randomChance(RECORD_DROP_PROBABILITY))
            )
            .build()
        ;
    }


    static {
        LootTableEvents.MODIFY.register(ModLootTables::onLootTablesLoad);
    }

    private static void onLootTablesLoad(ResourceManager resourceManager, LootDataManager lootDataManager,
                                         ResourceLocation id, Builder tableBuilder, LootTableSource tableSource) {
        if (!TO_INJECT.containsKey(id))
            return;

        tableBuilder.pool(TO_INJECT.get(id).get());
    }

}