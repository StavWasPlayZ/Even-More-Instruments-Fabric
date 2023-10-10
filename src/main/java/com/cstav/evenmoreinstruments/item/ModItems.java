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

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
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
    // How can I declare my mod to load only after another mod?
    public static void load() {}

    private static void defaultInstrumentsTabs(final Item item) {
        addToTab(ModCreativeModeTabs.INSTRUMENTS_TAB, item);
        addToTab(CreativeModeTabs.BUILDING_BLOCKS, item);
    }
    private static void defaultInstrumentBlocksTab(final Item item) {
        defaultInstrumentsTabs(item);
        addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, item);
    }


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
            ModItems::defaultInstrumentBlocksTab
        ),


        //TODO re-implement
        // LOOPER = registerBlockItem(ModBlocks.LOOPER,
        //     getKey(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB), CreativeModeTabs.FUNCTIONAL_BLOCKS,
        //     CreativeModeTabs.REDSTONE_BLOCKS
        // ),
        // LOOPER_ADAPTER = register("looper_adapter", new LooperAdapterItem(new Properties()),
        //     CreativeModeTabs.REDSTONE_BLOCKS, getKey(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
        // ),
        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND, (item) ->
            addToTab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB, item)
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
    private static Item registerBlockItem(Block block, Consumer<Item> tabAdder) {
        return register(BuiltInRegistries.BLOCK.getKey(block).getPath(),
            new BlockItem(block, new Properties())
        , tabAdder);
    }
    
    private static Item register(String name, Item item, Consumer<Item> tabAdder) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Main.MODID, name), item);
        
        tabAdder.accept(item);

        return item;
    }
    private static Item register(String name, Item item) {
        return register(name, item, ModItems::defaultInstrumentsTabs);
    }


    private static void addToTab(CreativeModeTab tab, final Item item) {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((_tab, entries) -> {
            if (getKey(tab).equals(getKey(_tab)))
                entries.accept(item);
        });
    }
    private static void addToTab(final ResourceKey<CreativeModeTab> tabKey, final Item item) {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((tab, entries) -> {
            if (getKey(tab).equals(tabKey))
                entries.accept(item);
        });
    }


    private static ResourceKey<CreativeModeTab> getKey(final CreativeModeTab tab) {
        return BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).orElseThrow();
    }

}
