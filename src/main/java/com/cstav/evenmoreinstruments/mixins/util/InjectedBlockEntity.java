package com.cstav.evenmoreinstruments.mixins.util;

import net.minecraft.nbt.CompoundTag;

public interface InjectedBlockEntity {
    CompoundTag evenmoreinstruments$getModTag();
    void evenmoreinstruments$setModTag(CompoundTag tag);
}
