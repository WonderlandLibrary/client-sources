/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RedstoneWallTorchBlock
extends RedstoneTorchBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty REDSTONE_TORCH_LIT = RedstoneTorchBlock.LIT;

    protected RedstoneWallTorchBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(REDSTONE_TORCH_LIT, true));
    }

    @Override
    public String getTranslationKey() {
        return this.asItem().getTranslationKey();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return WallTorchBlock.getShapeForState(blockState);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return Blocks.WALL_TORCH.isValidPosition(blockState, iWorldReader, blockPos);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return Blocks.WALL_TORCH.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = Blocks.WALL_TORCH.getStateForPlacement(blockItemUseContext);
        return blockState == null ? null : (BlockState)this.getDefaultState().with(FACING, blockState.get(FACING));
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(REDSTONE_TORCH_LIT).booleanValue()) {
            Direction direction = blockState.get(FACING).getOpposite();
            double d = 0.27;
            double d2 = (double)blockPos.getX() + 0.5 + (random2.nextDouble() - 0.5) * 0.2 + 0.27 * (double)direction.getXOffset();
            double d3 = (double)blockPos.getY() + 0.7 + (random2.nextDouble() - 0.5) * 0.2 + 0.22;
            double d4 = (double)blockPos.getZ() + 0.5 + (random2.nextDouble() - 0.5) * 0.2 + 0.27 * (double)direction.getZOffset();
            world.addParticle(this.particleData, d2, d3, d4, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected boolean shouldBeOff(World world, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(FACING).getOpposite();
        return world.isSidePowered(blockPos.offset(direction), direction);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(REDSTONE_TORCH_LIT) != false && blockState.get(FACING) != direction ? 15 : 0;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return Blocks.WALL_TORCH.rotate(blockState, rotation);
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return Blocks.WALL_TORCH.mirror(blockState, mirror);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, REDSTONE_TORCH_LIT);
    }
}

