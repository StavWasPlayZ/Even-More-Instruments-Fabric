package com.cstav.evenmoreinstruments.mixins.required;

import com.cstav.evenmoreinstruments.block.partial.IRedstoneWireConnector;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireConnectorInjector {

    @Inject(method = "shouldConnectTo(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;)Z", at = @At("TAIL"), cancellable = true)
    private static void shouldConnectToInjector(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof IRedstoneWireConnector redstoneConnector) {
            cir.setReturnValue(redstoneConnector.canConnectRedstone(state, direction));
        }
    }

}
