package com.cstav.evenmoreinstruments.item.partial;

import net.minecraft.world.item.ItemStack;

public interface IDynamicStackSize {

    int getMaxStackSize(ItemStack stack);

}
