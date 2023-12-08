package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.EMIModCreativeModeTabs;
import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.item.partial.WindInstrumentItem;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.ModOpenInstrumentPacket;
import com.cstav.genshinstrument.ModCreativeModeTabs;
import com.cstav.genshinstrument.item.InstrumentItem;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    // How can I declare my mod to load only after another mod?
    public static void load() {}

    private static void defaultInstrumentsTabs(final Item item) {
        addToTab(item, ModCreativeModeTabs.INSTRUMENTS_TAB);
        addToTab(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
    }
    private static void defaultInstrumentBlocksTab(final Item item) {
        defaultInstrumentsTabs(item);
        addToTab(item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }


    public static final Item
        VIOLIN = register("violin", new InstrumentItem(
            (player) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("violin"), player
            )
        )),
        GUITAR = register("guitar", new InstrumentItem(
            (player) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("guitar"), player
            )
        )),

        TROMBONE = register("trombone", new WindInstrumentItem(
            (player) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("trombone"), player
            )
        )),
        SAXOPHONE = register("saxophone", new WindInstrumentItem(
            (player) -> ModPacketHandler.sendToClient(
                new ModOpenInstrumentPacket("saxophone"), player
            )
        )),


        LOOPER = registerBlockItem(ModBlocks.LOOPER, (item) -> {
            addToTab(item, EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB);
            addToTab(item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
            addToTab(item, CreativeModeTabs.REDSTONE_BLOCKS);
        }),
        LOOPER_ADAPTER = register("looper_adapter", new LooperAdapterItem(new Properties()), (item) -> {
            addToTab(item, CreativeModeTabs.REDSTONE_BLOCKS);
            addToTab(item, EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB);
        }),

        KEYBOARD = register("keyboard",
            new KeyboardBlockItem(ModBlocks.KEYBOARD, new Properties()),
            ModItems::defaultInstrumentBlocksTab
        ),
        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND, (item) ->
            addToTab(item, EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
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


    private static void addToTab(final Item item, CreativeModeTab tab) {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((_tab, entries) -> {
            if (tab.equals(_tab))
                entries.accept(item);
        });
    }

}
