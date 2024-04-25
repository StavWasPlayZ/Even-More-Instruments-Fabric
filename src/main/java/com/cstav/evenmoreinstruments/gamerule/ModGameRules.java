package com.cstav.evenmoreinstruments.gamerule;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.mixins.required.GameRuleIntegerInvoker;
import net.fabricmc.fabric.mixin.gamerule.GameRulesAccessor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.Category;

public abstract class ModGameRules {

    public static void load() {}

    public static final GameRules.Key<GameRules.IntegerValue>
        RULE_LOOPER_MAX_NOTES = GameRulesAccessor.callRegister(EMIMain.MODID+"_looperMaxNotes", Category.MISC,
            GameRuleIntegerInvoker.callCreate(255)
        )
    ;
    //NOTE: GameRulesIntRuleAccessor exists
    
}
