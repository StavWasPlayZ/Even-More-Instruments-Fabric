package com.cstav.evenmoreinstruments.mixins.util;

import net.minecraft.nbt.CompoundTag;

public interface InjectedBlockEntity {
    CompoundTag getModTag();
    void setModTag(CompoundTag tag);
}
