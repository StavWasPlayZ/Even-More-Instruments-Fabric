package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.EMIModCreativeModeTabs;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.item.emirecord.BurnedRecordItem;
import com.cstav.evenmoreinstruments.item.emirecord.WritableRecordItem;
import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableBlockInstrumentItem;
import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableInstrumentItem;
import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableWindInstrumentItem;
import com.cstav.evenmoreinstruments.networking.EMIPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.EMIOpenInstrumentPacket;
import com.cstav.genshinstrument.GICreativeModeTabs;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
    public static void load() {
        subscribeCreativeTabHandlers();
    }

    private static final LinkedHashMap<ResourceKey<CreativeModeTab>, ArrayList<Item>> CREATIVE_TABS_MAP = new LinkedHashMap<>();
    private static ArrayList<Item> getCreativeItems(final ResourceKey<CreativeModeTab> tabKey) {
        if (!CREATIVE_TABS_MAP.containsKey(tabKey))
            CREATIVE_TABS_MAP.put(tabKey, new ArrayList<>());
        return CREATIVE_TABS_MAP.get(tabKey);
    }

    @SuppressWarnings("unchecked")
    private static final ResourceKey<CreativeModeTab>[] DEFAULT_INSTRUMENTS_TABS = new ResourceKey[] {
        GICreativeModeTabs.INSTRUMENTS_TAB, CreativeModeTabs.TOOLS_AND_UTILITIES
    };
    @SuppressWarnings("unchecked")
    private static final ResourceKey<CreativeModeTab>[] DEFAULT_INSTRUMENT_BLOCK_TABS = new ResourceKey[] {
        GICreativeModeTabs.INSTRUMENTS_TAB, CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.FUNCTIONAL_BLOCKS
    };


    public static final Item
        VIOLIN_BOW = register("violin_bow",
            () -> new InstrumentAccessoryItem(
                new Properties().stacksTo(1).durability(InstrumentAccessoryItem.MAX_DURABILITY)
            )
        ),
        VIOLIN = register("violin", ViolinItem::new, DEFAULT_INSTRUMENTS_TABS, VIOLIN_BOW),

        GUITAR = register("guitar", () -> new CreditableInstrumentItem(
            (player) -> EMIPacketHandler.sendToClient(
                new EMIOpenInstrumentPacket("guitar"), player
            ),
            "Philharmonia"
        )),
        PIPA = register("pipa", () -> new CreditableInstrumentItem(
            (player) -> EMIPacketHandler.sendToClient(
                new EMIOpenInstrumentPacket("pipa"), player
            ),
            "DSK Asian DreamZ"
        )),

        BACHI = register("bachi",
            () -> new InstrumentAccessoryItem(
                new Properties().stacksTo(1).durability(InstrumentAccessoryItem.MAX_DURABILITY)
            )
        ),
        SHAMISEN = register("shamisen",
            () -> new AccessoryInstrumentItem(
                (player) -> EMIPacketHandler.sendToClient(
                    new EMIOpenInstrumentPacket("shamisen"), player
                ),
                (InstrumentAccessoryItem) BACHI,
                "Roland SC-88"
            ),
            DEFAULT_INSTRUMENTS_TABS,
            BACHI
        ),

        KOTO = register("koto", () ->
                new CreditableBlockInstrumentItem(
                    ModBlocks.KOTO, new Properties().stacksTo(1),
                    "DSK Asian DreamZ"
                ),
            DEFAULT_INSTRUMENT_BLOCK_TABS
        ),

        TROMBONE = register("trombone", () -> new CreditableWindInstrumentItem(
            (player) -> EMIPacketHandler.sendToClient(
                new EMIOpenInstrumentPacket("trombone"), player
            ),
            "Philharmonia"
        )),
        SAXOPHONE = register("saxophone", () -> new CreditableWindInstrumentItem(
            (player) -> EMIPacketHandler.sendToClient(
                new EMIOpenInstrumentPacket("saxophone"), player
            ),
            "Philharmonia"
        )),
        KEYBOARD = register("keyboard", () ->
                new KeyboardBlockItem(
                    ModBlocks.KEYBOARD, new Properties().stacksTo(1),
                    null
                ),
            DEFAULT_INSTRUMENT_BLOCK_TABS
        ),

        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND,
            GICreativeModeTabs.INSTRUMENTS_TAB
        ),

        LOOPER = registerBlockItem(ModBlocks.LOOPER,
            EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB, CreativeModeTabs.FUNCTIONAL_BLOCKS,
            CreativeModeTabs.REDSTONE_BLOCKS
        ),
        LOOPER_ADAPTER = register("looper_adapter",
            () -> new LooperAdapterItem(new Properties().stacksTo(1)),
            CreativeModeTabs.REDSTONE_BLOCKS, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        ),

        RECORD_WRITABLE = register("record_writable", () -> new WritableRecordItem(new Properties()),
            CreativeModeTabs.TOOLS_AND_UTILITIES, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        ),
        RECORD_JOHNNY = register("record_johnny", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    new ResourceLocation(EMIMain.MODID, "johnny"),
                    "Hänschen klein - Franz Wiedemann",
                    null
                ),
            CreativeModeTabs.TOOLS_AND_UTILITIES, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        ),
        RECORD_SUPER_IDOL = register("record_super_idol", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    new ResourceLocation(EMIMain.MODID, "super_idol"),
                    "Super Idol - De Xian Rong",
                    "Saxophy"
                ),
            CreativeModeTabs.TOOLS_AND_UTILITIES, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        ),
        RECORD_OVEN_KID = register("record_oven_kid", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    new ResourceLocation(EMIMain.MODID, "oven_kid"),
                    "Timmy Trumpet & Savage - Freaks",
                    "StavWasPlayZ"
                ),
            CreativeModeTabs.TOOLS_AND_UTILITIES, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        ),
        RECORD_SAD_VIOLIN = register("record_sad_violin", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    new ResourceLocation(EMIMain.MODID, "sad_violin"),
                    "Sad Romance - Ji Pyeongkeyon",
                    "StavWasPlayZ"
                ),
            CreativeModeTabs.TOOLS_AND_UTILITIES, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        ),
        RECORD_RICKROLL = register("record_rickroll", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.EPIC),
                    new ResourceLocation(EMIMain.MODID, "rickroll"),
                    null,
                    "StavWasPlayZ",
                    Component.translatable("item.evenmoreinstruments.interesting_record")
                ),
            CreativeModeTabs.TOOLS_AND_UTILITIES, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB
        )
    ;

    public static final Map<NoteBlockInstrument, Item> NOTEBLOCK_INSTRUMENTS = initNoteBlockInstruments();

    public static HashMap<NoteBlockInstrument, Item> initNoteBlockInstruments() {
        final NoteBlockInstrument[] instruments = NoteBlockInstrument.values();
        final HashMap<NoteBlockInstrument, Item> result = new HashMap<>(instruments.length);

        for (final NoteBlockInstrument instrument : instruments) {
            if (!instrument.isTunable())
                continue;

            result.put(instrument,
                register(NoteBlockInstrumentItem.getId(instrument),
                    () -> new NoteBlockInstrumentItem(instrument)
                )
            );
        }

        return result;
    }


    // private static Item registerBlockItem(final Block block) {
    //     return registerBlockItem(block, DEFAULT_INSTRUMENT_BLOCK_TABS);
    // }
    @SafeVarargs
    private static Item registerBlockItem(Block block, ResourceKey<CreativeModeTab>... tabs) {
        return register(
            BuiltInRegistries.BLOCK.getKey(block).getPath(),
            () -> new BlockItem(block, new Properties()),
            tabs
        );
    }

    // Suppliers will stay for compatibility reasons
    private static Item register(String name, Supplier<Item> supplier, ResourceKey<CreativeModeTab>[] tabs,
                                                 Item appearsBefore) {
        final Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EMIMain.MODID, name), supplier.get());

        for (final ResourceKey<CreativeModeTab> tabKey : tabs) {
            final ArrayList<Item> items = getCreativeItems(tabKey);
            if (items.contains(appearsBefore)) {
                items.add(items.indexOf(appearsBefore), item);
            } else {
                items.add(item);
            }
        }

        return item;
    }
    @SafeVarargs
    private static Item register(String name, Supplier<Item> supplier, ResourceKey<CreativeModeTab>... tabs) {
        final Item item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(EMIMain.MODID, name), supplier.get());

        for (final ResourceKey<CreativeModeTab> tabKey: tabs) {
            getCreativeItems(tabKey).add(item);
        }

        return item;
    }
    private static Item register(String name, Supplier<Item> supplier) {
        return register(name, supplier, DEFAULT_INSTRUMENTS_TABS);
    }


    public static void subscribeCreativeTabHandlers() {
        CREATIVE_TABS_MAP.keySet().forEach((tabKey) -> {

            ItemGroupEvents.modifyEntriesEvent(tabKey).register((entries) ->
                entries.acceptAll(
                    getCreativeItems(tabKey).stream()
                        .map(ItemStack::new)
                        .toList()
                )
            );

        });
    }

}
