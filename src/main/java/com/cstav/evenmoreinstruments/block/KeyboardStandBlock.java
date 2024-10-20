package com.cstav.evenmoreinstruments.block;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.block.blockentity.ModInstrumentBlockEntity;
import com.cstav.evenmoreinstruments.item.ModItems;
import com.cstav.genshinstrument.block.partial.AbstractInstrumentBlock;
import com.cstav.genshinstrument.block.partial.InstrumentBlockEntity;
import com.cstav.genshinstrument.networking.packet.instrument.util.InstrumentPacketUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KeyboardStandBlock extends AbstractInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HAS_KEYBOARD = BooleanProperty.create("has_keyboard");
    

    public static final VoxelShape BASE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D),
        WITH_KEYBOARD_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(HAS_KEYBOARD) ? WITH_KEYBOARD_SHAPE : BASE_SHAPE;
    }

    public KeyboardStandBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState()
            .setValue(FACING, Direction.NORTH)
            .setValue(HAS_KEYBOARD, false)
        );
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        if (!pState.getValue(HAS_KEYBOARD))
            return InteractionResult.FAIL;

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState arg0, Level arg1, BlockPos arg2, BlockState arg3, boolean arg4) {
        //NOTE: Does not check for creative, because no context is given
        if (!arg1.isClientSide && arg0.getValue(HAS_KEYBOARD))
            arg1.addFreshEntity(
                new ItemEntity(arg1, arg2.getX(), arg2.getY(), arg2.getZ(), new ItemStack(ModItems.KEYBOARD))
            );

        super.onRemove(arg0, arg1, arg2, arg3, arg4);
    }


    @Override
    protected void onInstrumentOpen(ServerPlayer player) {
        InstrumentPacketUtil.sendOpenPacket(player, new ResourceLocation(EMIMain.MODID, "keyboard"));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public InstrumentBlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
        return new ModInstrumentBlockEntity(arg0, arg1);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HAS_KEYBOARD);
    }
    
}
