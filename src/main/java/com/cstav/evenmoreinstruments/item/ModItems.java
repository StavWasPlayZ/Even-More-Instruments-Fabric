package com.cstav.evenmoreinstruments.item;

import java.util.HashMap;
import java.util.Map;

import com.cstav.evenmoreinstruments.EMIModCreativeModeTabs;
import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.ModOpenInstrumentPacket;
import com.cstav.genshinstrument.ModCreativeModeTabs;
import com.cstav.genshinstrument.item.InstrumentItem;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
public class ModItems {

    public static void load() {}


    @SuppressWarnings("unchecked")
    private static final ResourceKey<CreativeModeTab>[] DEFAULT_INSTRUMENTS_TABS = new ResourceKey[] {
        getKey(ModCreativeModeTabs.INSTRUMENTS_TAB), CreativeModeTabs.BUILDING_BLOCKS
    };
    @SuppressWarnings("unchecked")
    private static final ResourceKey<CreativeModeTab>[] DEFAULT_INSTRUMENT_BLOCK_TABS = new ResourceKey[] {
        getKey(ModCreativeModeTabs.INSTRUMENTS_TAB), CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.FUNCTIONAL_BLOCKS
    };


    public static final Item
        VIOLIN = register("violin", new InstrumentItem(
            (player, hand) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("violin", hand), player
            )
        )),
        GUITAR = register("guitar", new InstrumentItem(
            (player, hand) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("guitar", hand), player
            )
        )),

        TROMBONE = register("trombone", new TromboneItem()),


        KEYBOARD = register("keyboard",
            new KeyboardBlockItem(ModBlocks.KEYBOARD, new Properties()),
            DEFAULT_INSTRUMENT_BLOCK_TABS
        ),


        //TODO re-implement
        // LOOPER = registerBlockItem(ModBlocks.LOOPER,
        //     getKey(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB), CreativeModeTabs.FUNCTIONAL_BLOCKS,
        //     CreativeModeTabs.REDSTONE_BLOCKS
        // ),
        // LOOPER_ADAPTER = register("looper_adapter", new LooperAdapterItem(new Properties()),
        //     CreativeModeTabs.REDSTONE_BLOCKS, getKey(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
        // ),
        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND,
            getKey(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
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
                    new NoteBlockInstrumentItem(instrument)
                )
            );
        }
        
        return result;
    }


    // private static RegistryObject<Item> registerBlockItem(final RegistryObject<Block> block) {
    //     return registerBlockItem(block, DEFAULT_INSTRUMENT_BLOCK_TABS);
    // }
    @SafeVarargs
    private static Item registerBlockItem(Block block, ResourceKey<CreativeModeTab>... tabs) {
        return register(BuiltInRegistries.BLOCK.getKey(block).getPath(),
            new BlockItem(block, new Properties())
        , tabs);
    }

    @SafeVarargs
    private static Item register(String name, Item item, ResourceKey<CreativeModeTab>... tabs) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Main.MODID, name), item);
        
        for (final ResourceKey<CreativeModeTab> tabKey : tabs)
            addToTab(tabKey, item);

        return item;
    }
    private static Item register(String name, Item item) {
        return register(name, item, DEFAULT_INSTRUMENTS_TABS);
    }


    private static void addToTab(final ResourceKey<CreativeModeTab> tab, final Item item) {
        ItemGroupEvents.modifyEntriesEvent(tab).register((content) -> content.accept(item));
    }


    private static ResourceKey<CreativeModeTab> getKey(final CreativeModeTab tab) {
        return BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElseThrow();
    }

}
