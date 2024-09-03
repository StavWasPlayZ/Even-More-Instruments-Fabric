package com.cstav.evenmoreinstruments.util;

import com.cstav.evenmoreinstruments.mixins.util.IEntityModData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;

public class LooperRecordEntityData {
    public static final String
        RECORDING_TAG = "Recording",
        REC_POS_TAG = "LooperPos"
    ;

    public static CompoundTag getModTag(final Entity entity) {
        return ((IEntityModData)entity).evenmoreinstruments$getPersistentData();
    }

    public static void setRecording(final Entity entity, final BlockPos looperPos) {
        getModTag(entity).putBoolean(RECORDING_TAG, true);
        getModTag(entity).put(REC_POS_TAG, NbtUtils.writeBlockPos(looperPos));
    }
    public static void setNotRecording(final Entity entity) {
        getModTag(entity).putBoolean(RECORDING_TAG, false);
        getModTag(entity).remove(REC_POS_TAG);
    }

    public static boolean isRecording(final Entity entity) {
        return getModTag(entity).getBoolean(RECORDING_TAG);
    }
    public static BlockPos getLooperPos(final Entity entity) {
        final CompoundTag posTag = getModTag(entity).getCompound(REC_POS_TAG);
        return posTag.isEmpty() ? null : NbtUtils.readBlockPos(posTag);
    }
}
