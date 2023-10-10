package com.cstav.evenmoreinstruments.item;

import com.cstav.evenmoreinstruments.client.ModArmPose;
import com.cstav.evenmoreinstruments.networking.ModPacketHandler;
import com.cstav.evenmoreinstruments.networking.packet.ModOpenInstrumentPacket;
import com.cstav.genshinstrument.event.PosePlayerArmEvent.PosePlayerArmEventArgs;
import com.cstav.genshinstrument.item.InstrumentItem;

public class TromboneItem extends InstrumentItem {

    public TromboneItem() {
        super((player, hand) -> ModPacketHandler.sendToClient(
            new ModOpenInstrumentPacket("trombone", hand), player
        ));
    }

    @Override
    public void onPosePlayerArm(PosePlayerArmEventArgs args) {
        ModArmPose.poseForTrombone(args);
    }
    
}