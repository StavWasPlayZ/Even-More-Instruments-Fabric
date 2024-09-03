package com.cstav.evenmoreinstruments.sound;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.genshinstrument.sound.NoteSound;
import com.cstav.genshinstrument.sound.registrar.NoteSoundRegistrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.HashMap;

public class ModSounds {
    public static void load() {}

    static {
        NOTEBLOCK_SOUNDS = new HashMap<>();
        registerNoteBlockSounds();
    }

    public static final NoteSound[]
        KEYBOARD = nsr(loc("keyboard")).stereo().registerGrid(),

        VIOLIN_FULL_NOTE = nsr(loc("violin_full")).registerGrid(),
        VIOLIN_HALF_NOTE = nsr(loc("violin_half")).registerGrid(),

        TROMBONE = nsr(loc("trombone")).registerGrid(),
        SAXOPHONE = nsr(loc("saxophone")).registerGrid(),

        GUITAR = nsr(loc("guitar")).registerGrid(),
        SHAMISEN = nsr(loc("shamisen")).stereo().registerGrid(),
        KOTO = nsr(loc("koto")).registerGrid(),

        PIPA_REGULAR = nsr(loc("pipa_regular")).registerGrid(),
        PIPA_TERMOLO = nsr(loc("pipa_tremolo")).registerGrid()
    ;


    private static final HashMap<NoteBlockInstrument, NoteSound> NOTEBLOCK_SOUNDS;
    public static NoteSound[] getNoteblockSounds(final NoteBlockInstrument instrumentType) {
        return new NoteSound[] {NOTEBLOCK_SOUNDS.get(instrumentType)};
    }

    private static void registerNoteBlockSounds() {
        final NoteSoundRegistrar registrar = nsr(loc("note_block_instrument"));

        for (NoteBlockInstrument noteSound : NoteBlockInstrument.values()) {
            registrar.chain(noteSound.getSoundEvent().value().getLocation())
                .alreadyRegistered()
                .add();
            NOTEBLOCK_SOUNDS.put(noteSound, registrar.peek());
        }

        registrar.registerAll();
    }


    private static ResourceLocation loc(final String id) {
        return new ResourceLocation(EMIMain.MODID, id);
    }
    private static NoteSoundRegistrar nsr(final ResourceLocation location) {
        return new NoteSoundRegistrar(location);
    }

}
