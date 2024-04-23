package com.cstav.evenmoreinstruments.criteria;

import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteria {
    public static void load() {}

    public static final RecordInjectedTrigger RECORD_INJECTED_TRIGGER = CriteriaTriggers.register(new RecordInjectedTrigger());
}