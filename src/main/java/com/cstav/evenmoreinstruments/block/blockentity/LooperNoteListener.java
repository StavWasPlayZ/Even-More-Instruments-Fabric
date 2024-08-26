package com.cstav.evenmoreinstruments.block.blockentity;

import com.cstav.evenmoreinstruments.util.LooperUtil;
import com.cstav.genshinstrument.event.HeldNoteSoundPlayedEvent;
import com.cstav.genshinstrument.event.HeldNoteSoundPlayedEvent.HeldNoteSoundPlayedEventArgs;
import com.cstav.genshinstrument.event.InstrumentPlayedEvent.InstrumentPlayedEventArgs;
import com.cstav.genshinstrument.event.NoteSoundPlayedEvent;
import com.cstav.genshinstrument.event.NoteSoundPlayedEvent.NoteSoundPlayedEventArgs;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Optional;

import static com.cstav.evenmoreinstruments.item.emirecord.EMIRecordItem.INSTRUMENT_ID_TAG;

/**
 * Listens to instrument played events
 * and writes it to a matching looper.
 */
public class LooperNoteListener {

    public static void register() {
        NoteSoundPlayedEvent.EVENT.register(LooperNoteListener::onNoteSoundPlayed);
        HeldNoteSoundPlayedEvent.EVENT.register(LooperNoteListener::onHeldNoteSoundPlayed);
    }

    public static void onNoteSoundPlayed(final NoteSoundPlayedEventArgs args) {
        getMatchingLooper(args).ifPresent((looperBE) ->
            looperBE.writeNote(args.sound(), args.soundMeta(), looperBE.getTicks())
        );
    }

    public static void onHeldNoteSoundPlayed(final HeldNoteSoundPlayedEventArgs args) {
        getMatchingLooper(args).ifPresent((looperBE) ->
            looperBE.writeHeldNote(args.sound(), args.phase, args.soundMeta(), looperBE.getTicks())
        );
    }


    /**
     * @return The looper matching the provided event
     */
    private static Optional<LooperBlockEntity> getMatchingLooper(final InstrumentPlayedEventArgs<?> event) {
        // Only get player events
        if (!event.isByPlayer())
            return Optional.empty();

        final Player player = (Player) event.entityInfo().get().entity;

        if (event.level().isClientSide || !LooperUtil.isRecording(player))
            return Optional.empty();


        final Level level = player.level();

        final LooperBlockEntity looperBE = LooperUtil.getFromEvent(event);
        if (looperBE == null || looperBE.isCapped(level))
            return Optional.empty();

        // Omit if record is not writable
        if (!looperBE.isWritable())
            return Optional.empty();


        if (looperBE.isLocked()) {
            if (!looperBE.isRecording() || !looperBE.isAllowedToRecord(player))
                return Optional.empty();
        } else {
            looperBE.setLockedBy(player);
            looperBE.setRecording(true);
            looperBE.getChannel().putString(INSTRUMENT_ID_TAG, event.soundMeta().instrumentId().toString());
        }

        return Optional.of(looperBE);
    }

}