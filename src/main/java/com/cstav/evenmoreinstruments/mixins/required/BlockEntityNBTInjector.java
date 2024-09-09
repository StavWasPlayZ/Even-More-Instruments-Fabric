package com.cstav.evenmoreinstruments.mixins.required;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.mixins.util.InjectedBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BlockEntityNBTInjector implements InjectedBlockEntity {

    @Unique
    private CompoundTag persistentData = new CompoundTag();

    @Unique
    @Override
    public CompoundTag evenmoreinstruments$getModTag() {
        return persistentData;
    }
    @Unique
    @Override
    public void evenmoreinstruments$setModTag(CompoundTag tag) {
        this.persistentData = tag;
    }


    @Inject(method = "saveAdditional", at = @At("TAIL"))
    protected void saveAdditionalInjector(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        if (!persistentData.isEmpty())
            tag.put(EMIMain.MODID, persistentData);
    }
    @Inject(method = "loadAdditional", at = @At("TAIL"))
    protected void loadInjector(CompoundTag tag, HolderLookup.Provider provider, CallbackInfo ci) {
        persistentData = tag.getCompound(EMIMain.MODID);
    }

}
