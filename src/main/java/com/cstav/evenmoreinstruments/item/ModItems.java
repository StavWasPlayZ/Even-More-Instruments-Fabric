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
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
    public static void load() {}

    public static final Item
        VIOLIN_BOW = register("violin_bow",
            () -> new InstrumentAccessoryItem(
                new Properties()
                    .stacksTo(1)
                    .durability(InstrumentAccessoryItem.MAX_DURABILITY)
                    .tab(GICreativeModeTabs.INSTRUMENTS_TAB)
            )
        ),
        VIOLIN = register("violin", ViolinItem::new),

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
                new Properties()
                    .stacksTo(1)
                    .durability(InstrumentAccessoryItem.MAX_DURABILITY)
                    .tab(GICreativeModeTabs.INSTRUMENTS_TAB)
            )
        ),
        SHAMISEN = register("shamisen",
            () -> new AccessoryInstrumentItem(
                (player) -> EMIPacketHandler.sendToClient(
                    new EMIOpenInstrumentPacket("shamisen"), player
                ),
                (InstrumentAccessoryItem) BACHI,
                "Roland SC-88"
            )
        ),

        KOTO = register("koto", () ->
            new CreditableBlockInstrumentItem(
                ModBlocks.KOTO, new Properties().stacksTo(1),
                "DSK Asian DreamZ"
            )
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
            )
        ),

        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND,
            GICreativeModeTabs.INSTRUMENTS_TAB
        ),

        LOOPER = registerBlockItem(ModBlocks.LOOPER, EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
        LOOPER_ADAPTER = register("looper_adapter", () -> new LooperAdapterItem(new Properties()
            .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
        )),

        RECORD_WRITABLE = register("record_writable", () ->
            new WritableRecordItem(new Properties()
                .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
            )
        ),
        RECORD_JOHNNY = register("record_johnny", () ->
            new BurnedRecordItem(
                new Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
                new ResourceLocation(EMIMain.MODID, "johnny"),
                "Hänschen klein - Franz Wiedemann",
                null
            )
        ),
        RECORD_SUPER_IDOL = register("record_super_idol", () ->
            new BurnedRecordItem(
                new Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
                new ResourceLocation(EMIMain.MODID, "super_idol"),
                "Super Idol - De Xian Rong",
                "Saxophy"
            )
        ),
        RECORD_OVEN_KID = register("record_oven_kid", () ->
            new BurnedRecordItem(
                new Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
                new ResourceLocation(EMIMain.MODID, "oven_kid"),
                "Timmy Trumpet & Savage - Freaks",
                "StavWasPlayZ"
            )
        ),
        RECORD_SAD_VIOLIN = register("record_sad_violin", () ->
            new BurnedRecordItem(
                new Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
                new ResourceLocation(EMIMain.MODID, "sad_violin"),
                "Sad Romance - Ji Pyeongkeyon",
                "StavWasPlayZ"
            )
        ),
        RECORD_RICKROLL = register("record_rickroll", () ->
            new BurnedRecordItem(
                new Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
                new ResourceLocation(EMIMain.MODID, "rickroll"),
                null,
                "StavWasPlayZ",
                Component.translatable("item.evenmoreinstruments.interesting_record")
            )
        )
    ;

    public static final Map<NoteBlockInstrument, Item> NOTEBLOCK_INSTRUMENTS = initNoteBlockInstruments();

    public static HashMap<NoteBlockInstrument, Item> initNoteBlockInstruments() {
        final NoteBlockInstrument[] instruments = NoteBlockInstrument.values();
        final HashMap<NoteBlockInstrument, Item> result = new HashMap<>(instruments.length);

        for (final NoteBlockInstrument instrument : instruments) {
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
    private static Item registerBlockItem(Block block, CreativeModeTab tab) {
        return register(Registry.BLOCK.getKey(block).getPath(),
            () -> new BlockItem(block, new Properties().tab(tab))
        );
    }

    private static Item register(String name, Supplier<Item> item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(EMIMain.MODID, name), item.get());
    }

}
