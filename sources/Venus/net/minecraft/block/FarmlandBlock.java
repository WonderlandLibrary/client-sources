/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AttachedStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.MovingPistonBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FarmlandBlock
extends Block {
    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE_0_7;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    protected FarmlandBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(MOISTURE, 0));
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == Direction.UP && !blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.up());
        return !blockState2.getMaterial().isSolid() || blockState2.getBlock() instanceof FenceGateBlock || blockState2.getBlock() instanceof MovingPistonBlock;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return !this.getDefaultState().isValidPosition(blockItemUseContext.getWorld(), blockItemUseContext.getPos()) ? Blocks.DIRT.getDefaultState() : super.getStateForPlacement(blockItemUseContext);
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            FarmlandBlock.turnToDirt(blockState, serverWorld, blockPos);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n = blockState.get(MOISTURE);
        if (!FarmlandBlock.hasWater(serverWorld, blockPos) && !serverWorld.isRainingAt(blockPos.up())) {
            if (n > 0) {
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(MOISTURE, n - 1), 1);
            } else if (!FarmlandBlock.hasCrops(serverWorld, blockPos)) {
                FarmlandBlock.turnToDirt(blockState, serverWorld, blockPos);
            }
        } else if (n < 7) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(MOISTURE, 7), 1);
        }
    }

    @Override
    public void onFallenUpon(World world, BlockPos blockPos, Entity entity2, float f) {
        if (!world.isRemote && world.rand.nextFloat() < f - 0.5f && entity2 instanceof LivingEntity && (entity2 instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && entity2.getWidth() * entity2.getWidth() * entity2.getHeight() > 0.512f) {
            FarmlandBlock.turnToDirt(world.getBlockState(blockPos), world, blockPos);
        }
        super.onFallenUpon(world, blockPos, entity2, f);
    }

    public static void turnToDirt(BlockState blockState, World world, BlockPos blockPos) {
        world.setBlockState(blockPos, FarmlandBlock.nudgeEntitiesWithNewState(blockState, Blocks.DIRT.getDefaultState(), world, blockPos));
    }

    private static boolean hasCrops(IBlockReader iBlockReader, BlockPos blockPos) {
        Block block = iBlockReader.getBlockState(blockPos.up()).getBlock();
        return block instanceof CropsBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
    }

    private static boolean hasWater(IWorldReader iWorldReader, BlockPos blockPos) {
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos.add(-4, 0, -4), blockPos.add(4, 1, 4))) {
            if (!iWorldReader.getFluidState(blockPos2).isTagged(FluidTags.WATER)) continue;
            return false;
        }
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

