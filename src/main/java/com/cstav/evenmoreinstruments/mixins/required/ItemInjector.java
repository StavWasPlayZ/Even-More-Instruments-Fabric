package com.cstav.evenmoreinstruments.mixins.required;

import com.cstav.evenmoreinstruments.item.partial.IDynamicStackSize;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemInjector {

    @Inject(method = "getMaxStackSize", at = @At("TAIL"), cancellable = true)
    private void getMaxStackSizeInjector(CallbackInfoReturnable<Integer> cir) {
        final ItemStack self = (ItemStack)((Object)this);

        if (self.getItem() instanceof IDynamicStackSize dynItem) {
            cir.setReturnValue(dynItem.getMaxStackSize(self));
        }
    }

}
