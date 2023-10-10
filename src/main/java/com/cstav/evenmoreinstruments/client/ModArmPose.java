package com.cstav.evenmoreinstruments.client;

import com.cstav.genshinstrument.event.PosePlayerArmEvent.HandType;
import com.cstav.genshinstrument.event.PosePlayerArmEvent.PosePlayerArmEventArgs;
import com.cstav.genshinstrument.util.InstrumentEntityData;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModArmPose {
    
    public static void poseForTrombone(PosePlayerArmEventArgs args) {
        if (!InstrumentEntityData.isOpen(args.player) || !InstrumentEntityData.isItem(args.player))
            return;

        if (args.hand == HandType.RIGHT) {
            args.arm.xRot = -1.5f;
            args.arm.zRot = -0.35f;
            args.arm.yRot = -0.5f;
        } else {
            args.arm.xRot = -1.5f;
            args.arm.zRot = 0.55f;
            args.arm.yRot = 0.5f;
        }
    }

}
