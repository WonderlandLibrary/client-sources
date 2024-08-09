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
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BambooBlock
extends Block
implements IGrowable {
    protected static final VoxelShape SHAPE_NORMAL = Block.makeCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    protected static final VoxelShape SHAPE_LARGE_LEAVES = Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape SHAPE_COLLISION = Block.makeCuboidShape(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
    public static final IntegerProperty PROPERTY_AGE = BlockStateProperties.AGE_0_1;
    public static final EnumProperty<BambooLeaves> PROPERTY_BAMBOO_LEAVES = BlockStateProperties.BAMBOO_LEAVES;
    public static final IntegerProperty PROPERTY_STAGE = BlockStateProperties.STAGE_0_1;

    public BambooBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(PROPERTY_AGE, 0)).with(PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE)).with(PROPERTY_STAGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_AGE, PROPERTY_BAMBOO_LEAVES, PROPERTY_STAGE);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        VoxelShape voxelShape = blockState.get(PROPERTY_BAMBOO_LEAVES) == BambooLeaves.LARGE ? SHAPE_LARGE_LEAVES : SHAPE_NORMAL;
        Vector3d vector3d = blockState.getOffset(iBlockReader, blockPos);
        return voxelShape.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Vector3d vector3d = blockState.getOffset(iBlockReader, blockPos);
        return SHAPE_COLLISION.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        if (!fluidState.isEmpty()) {
            return null;
        }
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos().down());
        if (blockState.isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
            if (blockState.isIn(Blocks.BAMBOO_SAPLING)) {
                return (BlockState)this.getDefaultState().with(PROPERTY_AGE, 0);
            }
            if (blockState.isIn(Blocks.BAMBOO)) {
                int n = blockState.get(PROPERTY_AGE) > 0 ? 1 : 0;
                return (BlockState)this.getDefaultState().with(PROPERTY_AGE, n);
            }
            BlockState blockState2 = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos().up());
            return !blockState2.isIn(Blocks.BAMBOO) && !blockState2.isIn(Blocks.BAMBOO_SAPLING) ? Blocks.BAMBOO_SAPLING.getDefaultState() : (BlockState)this.getDefaultState().with(PROPERTY_AGE, blockState2.get(PROPERTY_AGE));
        }
        return null;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, false);
        }
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(PROPERTY_STAGE) == 0;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n;
        if (blockState.get(PROPERTY_STAGE) == 0 && random2.nextInt(3) == 0 && serverWorld.isAirBlock(blockPos.up()) && serverWorld.getLightSubtracted(blockPos.up(), 0) >= 9 && (n = this.getNumBambooBlocksBelow(serverWorld, blockPos) + 1) < 16) {
            this.grow(blockState, serverWorld, blockPos, random2, n);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return iWorldReader.getBlockState(blockPos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        if (direction == Direction.UP && blockState2.isIn(Blocks.BAMBOO) && blockState2.get(PROPERTY_AGE) > blockState.get(PROPERTY_AGE)) {
            iWorld.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(PROPERTY_AGE), 2);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        int n;
        int n2 = this.getNumBambooBlocksAbove(iBlockReader, blockPos);
        return n2 + (n = this.getNumBambooBlocksBelow(iBlockReader, blockPos)) + 1 < 16 && iBlockReader.getBlockState(blockPos.up(n2)).get(PROPERTY_STAGE) != 1;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        int n = this.getNumBambooBlocksAbove(serverWorld, blockPos);
        int n2 = this.getNumBambooBlocksBelow(serverWorld, blockPos);
        int n3 = n + n2 + 1;
        int n4 = 1 + random2.nextInt(2);
        for (int i = 0; i < n4; ++i) {
            BlockPos blockPos2 = blockPos.up(n);
            BlockState blockState2 = serverWorld.getBlockState(blockPos2);
            if (n3 >= 16 || blockState2.get(PROPERTY_STAGE) == 1 || !serverWorld.isAirBlock(blockPos2.up())) {
                return;
            }
            this.grow(blockState2, serverWorld, blockPos2, random2, n3);
            ++n;
            ++n3;
        }
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState blockState, PlayerEntity playerEntity, IBlockReader iBlockReader, BlockPos blockPos) {
        return playerEntity.getHeldItemMainhand().getItem() instanceof SwordItem ? 1.0f : super.getPlayerRelativeBlockHardness(blockState, playerEntity, iBlockReader, blockPos);
    }

    protected void grow(BlockState blockState, World world, BlockPos blockPos, Random random2, int n) {
        BlockState blockState2 = world.getBlockState(blockPos.down());
        BlockPos blockPos2 = blockPos.down(2);
        BlockState blockState3 = world.getBlockState(blockPos2);
        BambooLeaves bambooLeaves = BambooLeaves.NONE;
        if (n >= 1) {
            if (blockState2.isIn(Blocks.BAMBOO) && blockState2.get(PROPERTY_BAMBOO_LEAVES) != BambooLeaves.NONE) {
                if (blockState2.isIn(Blocks.BAMBOO) && blockState2.get(PROPERTY_BAMBOO_LEAVES) != BambooLeaves.NONE) {
                    bambooLeaves = BambooLeaves.LARGE;
                    if (blockState3.isIn(Blocks.BAMBOO)) {
                        world.setBlockState(blockPos.down(), (BlockState)blockState2.with(PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL), 0);
                        world.setBlockState(blockPos2, (BlockState)blockState3.with(PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE), 0);
                    }
                }
            } else {
                bambooLeaves = BambooLeaves.SMALL;
            }
        }
        int n2 = blockState.get(PROPERTY_AGE) != 1 && !blockState3.isIn(Blocks.BAMBOO) ? 0 : 1;
        int n3 = !(n >= 11 && random2.nextFloat() < 0.25f || n == 15) ? 0 : 1;
        world.setBlockState(blockPos.up(), (BlockState)((BlockState)((BlockState)this.getDefaultState().with(PROPERTY_AGE, n2)).with(PROPERTY_BAMBOO_LEAVES, bambooLeaves)).with(PROPERTY_STAGE, n3), 0);
    }

    protected int getNumBambooBlocksAbove(IBlockReader iBlockReader, BlockPos blockPos) {
        int n;
        for (n = 0; n < 16 && iBlockReader.getBlockState(blockPos.up(n + 1)).isIn(Blocks.BAMBOO); ++n) {
        }
        return n;
    }

    protected int getNumBambooBlocksBelow(IBlockReader iBlockReader, BlockPos blockPos) {
        int n;
        for (n = 0; n < 16 && iBlockReader.getBlockState(blockPos.down(n + 1)).isIn(Blocks.BAMBOO); ++n) {
        }
        return n;
    }
}

