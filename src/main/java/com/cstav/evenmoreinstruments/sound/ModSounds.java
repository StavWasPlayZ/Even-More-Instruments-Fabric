package com.cstav.evenmoreinstruments.sound;

import com.cstav.evenmoreinstruments.Main;
import com.cstav.genshinstrument.sound.NoteSound;
import com.cstav.genshinstrument.sound.NoteSoundRegistrer;

import net.minecraft.resources.ResourceLocation;

public class ModSounds {
    public static void load() {}

    public static final NoteSound[]
        KEYBOARD = NoteSoundRegistrer.createInstrumentNotes(loc("keyboard"), true),

        VIOLIN_FULL_NOTE = NoteSoundRegistrer.createInstrumentNotes(loc("violin_full")),
        VIOLIN_HALF_NOTE = NoteSoundRegistrer.createInstrumentNotes(loc("violin_half")),

        TROMBONE = NoteSoundRegistrer.createInstrumentNotes(loc("trombone")),
        GUITAR = NoteSoundRegistrer.createInstrumentNotes(loc("guitar"))
    ;


    private static ResourceLocation loc(final String id) {
        return new ResourceLocation(Main.MODID, id);
    }

}
