package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.EMIModCreativeModeTabs;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.item.emirecord.BurnedRecordItem;
import com.cstav.evenmoreinstruments.item.emirecord.WritableRecordItem;
import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableBlockInstrumentItem;
import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableInstrumentItem;
import com.cstav.evenmoreinstruments.item.partial.instrument.CreditableWindInstrumentItem;
import com.cstav.genshinstrument.GICreativeModeTabs;
import com.cstav.genshinstrument.networking.packet.instrument.util.InstrumentPacketUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.cstav.genshinstrument.GICreativeModeTabs.addToTab;

public class ModItems {
    public static void load() {}

    private static void toDefaultInstrumentTabs(Item item, Item appearsBefore) {
        GICreativeModeTabs.addToInstrumentsTab(1, item, appearsBefore);
        addToTab(1, CreativeModeTabs.TOOLS_AND_UTILITIES, item, appearsBefore);
    }
    private static void toDefaultBlockInstrumentTabs(Item item, Item appearsBefore) {
        toDefaultInstrumentTabs(item, appearsBefore);
        addToTab(1, CreativeModeTabs.FUNCTIONAL_BLOCKS, item, appearsBefore);
    }
    private static void toRecordTabs(Item item, Item appearsBefore) {
        addToTab(1, CreativeModeTabs.TOOLS_AND_UTILITIES, item, appearsBefore);
        addToTab(1, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB, item, appearsBefore);
    }


    public static final Item
        VIOLIN_BOW = register("violin_bow",
            () -> new InstrumentAccessoryItem(
                new Properties().stacksTo(1).durability(InstrumentAccessoryItem.MAX_DURABILITY)
            )
        ),
        VIOLIN = register("violin", ViolinItem::new, ModItems::toDefaultInstrumentTabs, VIOLIN_BOW),

        GUITAR = register("guitar", () -> new CreditableInstrumentItem(
            (player) -> InstrumentPacketUtil.sendOpenPacket(
                player, loc("guitar")
            ),
            "Philharmonia"
        )),
        PIPA = register("pipa", () -> new CreditableInstrumentItem(
            (player) -> InstrumentPacketUtil.sendOpenPacket(
                player, loc("pipa")
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
                (player) -> InstrumentPacketUtil.sendOpenPacket(
                    player, loc("shamisen")
                ),
                (InstrumentAccessoryItem) BACHI,
                "Roland SC-88"
            ),
            ModItems::toDefaultInstrumentTabs,
            BACHI
        ),

        KOTO = register("koto", () ->
                new CreditableBlockInstrumentItem(
                    ModBlocks.KOTO, new Properties().stacksTo(1),
                    "DSK Asian DreamZ"
                ),
            ModItems::toDefaultBlockInstrumentTabs
        ),

        TROMBONE = register("trombone", () -> new CreditableWindInstrumentItem(
            (player) -> InstrumentPacketUtil.sendOpenPacket(
                player, loc("trombone")
            ),
            "Philharmonia"
        )),
        SAXOPHONE = register("saxophone", () -> new CreditableWindInstrumentItem(
            (player) -> InstrumentPacketUtil.sendOpenPacket(
                player, loc("saxophone")
            ),
            "Philharmonia"
        )),
        KEYBOARD = register("keyboard", () ->
                new KeyboardBlockItem(
                    ModBlocks.KEYBOARD, new Properties().stacksTo(1),
                    null
                ),
            ModItems::toDefaultBlockInstrumentTabs
        ),

        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND,
            ModItems::toDefaultInstrumentTabs
        ),

        LOOPER = registerBlockItem(ModBlocks.LOOPER, (item, appearsBefore) -> {
            addToTab(1, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB, item, appearsBefore);
            addToTab(1, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB, item, appearsBefore);
            addToTab(1, CreativeModeTabs.FUNCTIONAL_BLOCKS, item, appearsBefore);
            addToTab(1, CreativeModeTabs.REDSTONE_BLOCKS, item, appearsBefore);
        }),
        LOOPER_ADAPTER = register("looper_adapter",
            () -> new LooperAdapterItem(new Properties().stacksTo(1)),
            (item, appearsBefore) -> {
                 addToTab(1, CreativeModeTabs.REDSTONE_BLOCKS, item, appearsBefore);
                 addToTab(1, EMIModCreativeModeTabs.MUSIC_PRODUCTION_TAB, item, appearsBefore);
            }
        ),

        RECORD_WRITABLE = register("record_writable", () ->
                new WritableRecordItem(new Properties()),
            ModItems::toRecordTabs
        ),
        RECORD_JOHNNY = register("record_johnny", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    EMIMain.loc("johnny"),
                    "Hänschen klein - Franz Wiedemann",
                    null
                ),
            ModItems::toRecordTabs
        ),
        RECORD_SUPER_IDOL = register("record_super_idol", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    EMIMain.loc("super_idol"),
                    "Super Idol - De Xian Rong",
                    "Saxophy"
                ),
            ModItems::toRecordTabs
        ),
        RECORD_OVEN_KID = register("record_oven_kid", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    EMIMain.loc("oven_kid"),
                    "Timmy Trumpet & Savage - Freaks",
                    "StavWasPlayZ"
                ),
            ModItems::toRecordTabs
        ),
        RECORD_SAD_VIOLIN = register("record_sad_violin", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.RARE),
                    EMIMain.loc("sad_violin"),
                    "Sad Romance - Ji Pyeongkeyon",
                    "StavWasPlayZ"
                ),
            ModItems::toRecordTabs
        ),
        RECORD_RICKROLL = register("record_rickroll", () ->
                new BurnedRecordItem(
                    new Properties().stacksTo(1).rarity(Rarity.EPIC),
                    EMIMain.loc("rickroll"),
                    null,
                    "StavWasPlayZ",
                    Component.translatable("item.evenmoreinstruments.interesting_record")
                ),
            ModItems::toRecordTabs
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


    private static ResourceLocation loc(final String path) {
        return EMIMain.loc(path);
    }


    // private static Item registerBlockItem(final Block block) {
    //     return registerBlockItem(block, DEFAULT_INSTRUMENT_BLOCK_TABS);
    // }
    private static Item registerBlockItem(Block block, BiConsumer<Item, Item> tabConsumer,
                                          Item appearsBefore) {
        return register(
            BuiltInRegistries.BLOCK.getKey(block).getPath(),
            () -> new BlockItem(block, new Properties()),
            tabConsumer, appearsBefore
        );
    }
    private static Item registerBlockItem(Block block, BiConsumer<Item, Item> tabConsumer) {
        return registerBlockItem(block, tabConsumer, null);
    }

    // Suppliers will stay for compatibility reasons
    private static Item register(String name, Supplier<Item> supplier, BiConsumer<Item, Item> tabConsumer,
                                                 Item appearsBefore) {
        final Item item = Registry.register(BuiltInRegistries.ITEM, EMIMain.loc(name), supplier.get());
        tabConsumer.accept(item, appearsBefore);

        return item;
    }
    private static Item register(String name, Supplier<Item> supplier, BiConsumer<Item, Item> tabConsumer) {
        return register(name, supplier, tabConsumer, null);
    }
    private static Item register(String name, Supplier<Item> supplier) {
        return register(name, supplier, ModItems::toDefaultInstrumentTabs);
    }

}
