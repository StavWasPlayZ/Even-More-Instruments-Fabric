package com.cstav.evenmoreinstruments.criteria;

import com.cstav.evenmoreinstruments.EMIMain;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModCriteria {
    public static void load() {}

    public static final RecordInjectedTrigger RECORD_INJECTED_TRIGGER = register("record_injected", new RecordInjectedTrigger());

    private static <T extends CriterionTrigger<?>> T register(String name, T criterionTrigger) {
        return Registry.register(
            BuiltInRegistries.TRIGGER_TYPES,
            EMIMain.loc(name),
            criterionTrigger
        );
    }
}