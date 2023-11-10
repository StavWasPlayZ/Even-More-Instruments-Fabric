package com.cstav.evenmoreinstruments.networking;

import com.cstav.evenmoreinstruments.client.gui.instrument.LooperOverlayInjector;

//tuwo06 aka ign leorio told me to name this as such
public class ClientBulgaria {

    public static void handleLooperRemoved() {
        LooperOverlayInjector.removeRecordButton();
    }

}
