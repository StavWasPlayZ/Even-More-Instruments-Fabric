package com.cstav.evenmoreinstruments.server;

import com.cstav.evenmoreinstruments.util.LooperRecordStateUtil;
import com.cstav.genshinstrument.event.InstrumentOpenStateChangedEvent;
import com.cstav.genshinstrument.event.InstrumentOpenStateChangedEvent.InstrumentOpenStateChangedEventArgs;
import net.minecraft.server.level.ServerPlayer;

public class ServerEvents {

    public static void register() {
        InstrumentOpenStateChangedEvent.EVENT.register(ServerEvents::onInstrumentClosedStateClosed);
    }

    public static void onInstrumentClosedStateClosed(final InstrumentOpenStateChangedEventArgs args) {
        if (args.player.level().isClientSide)
            return;

        if (!args.isOpen) {
            LooperRecordStateUtil.handle((ServerPlayer) args.player, args.hand, false);
        }
    }

}
