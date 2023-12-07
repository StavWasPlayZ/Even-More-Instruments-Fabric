package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.EMIModCreativeModeTabs;
import com.cstav.evenmoreinstruments.Main;
import com.cstav.evenmoreinstruments.block.ModBlocks;
import com.cstav.evenmoreinstruments.item.partial.WindInstrumentItem;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.ModOpenInstrumentPacket;
import com.cstav.genshinstrument.ModCreativeModeTabs;
import com.cstav.genshinstrument.item.InstrumentItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    // How can I declare my mod to load only after another mod?
    public static void load() {}


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


        LOOPER = registerBlockItem(ModBlocks.LOOPER, EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB),
        LOOPER_ADAPTER = register("looper_adapter", new LooperAdapterItem(new Properties()
            .tab(EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
        )),

        KEYBOARD = register("keyboard",
            new KeyboardBlockItem(ModBlocks.KEYBOARD, new Properties().tab(ModCreativeModeTabs.INSTRUMENTS_TAB))
        ),
        KEYBOARD_STAND = registerBlockItem(ModBlocks.KEYBOARD_STAND, EMIModCreativeModeTabs.INSTRUMENT_ACCESSORY_TAB)
    ;

    public static final Map<NoteBlockInstrument, Item> NOTEBLOCK_INSTRUMENTS = initNoteBlockInstruments();

    public static HashMap<NoteBlockInstrument, Item> initNoteBlockInstruments() {
        final NoteBlockInstrument[] instruments = NoteBlockInstrument.values();
        final HashMap<NoteBlockInstrument, Item> result = new HashMap<>(instruments.length);

        for (final NoteBlockInstrument instrument : instruments) {
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
    private static Item registerBlockItem(Block block, CreativeModeTab tab) {
        return register(Registry.BLOCK.getKey(block).getPath(),
            new BlockItem(block, new Properties().tab(tab))
        );
    }

    private static Item register(String name, Item item) {
        Registry.register(Registry.ITEM, new ResourceLocation(Main.MODID, name), item);
        return item;
    }

}
