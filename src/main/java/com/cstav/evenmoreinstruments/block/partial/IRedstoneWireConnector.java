package com.cstav.evenmoreinstruments.block.partial;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IRedstoneWireConnector {
    boolean canConnectRedstone(BlockState state, Direction direction);
}
