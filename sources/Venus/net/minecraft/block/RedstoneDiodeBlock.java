/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerTickList;
import net.minecraft.world.server.ServerWorld;

public abstract class RedstoneDiodeBlock
extends HorizontalBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected RedstoneDiodeBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return RedstoneDiodeBlock.hasSolidSideOnTop(iWorldReader, blockPos.down());
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!this.isLocked(serverWorld, blockPos, blockState)) {
            boolean bl = blockState.get(POWERED);
            boolean bl2 = this.shouldBePowered(serverWorld, blockPos, blockState);
            if (bl && !bl2) {
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(POWERED, false), 1);
            } else if (!bl) {
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(POWERED, true), 1);
                if (!bl2) {
                    ((ServerTickList)serverWorld.getPendingBlockTicks()).scheduleTick(blockPos, this, this.getDelay(blockState), TickPriority.VERY_HIGH);
                }
            }
        }
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.getWeakPower(iBlockReader, blockPos, direction);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        if (!blockState.get(POWERED).booleanValue()) {
            return 1;
        }
        return blockState.get(HORIZONTAL_FACING) == direction ? this.getActiveSignal(iBlockReader, blockPos, blockState) : 0;
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (blockState.isValidPosition(world, blockPos)) {
            this.updateState(world, blockPos, blockState);
        } else {
            TileEntity tileEntity = this.isTileEntityProvider() ? world.getTileEntity(blockPos) : null;
            RedstoneDiodeBlock.spawnDrops(blockState, world, blockPos, tileEntity);
            world.removeBlock(blockPos, true);
            for (Direction direction : Direction.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(direction), this);
            }
        }
    }

    protected void updateState(World world, BlockPos blockPos, BlockState blockState) {
        boolean bl;
        boolean bl2;
        if (!this.isLocked(world, blockPos, blockState) && (bl2 = blockState.get(POWERED).booleanValue()) != (bl = this.shouldBePowered(world, blockPos, blockState)) && !world.getPendingBlockTicks().isTickPending(blockPos, this)) {
            TickPriority tickPriority = TickPriority.HIGH;
            if (this.isFacingTowardsRepeater(world, blockPos, blockState)) {
                tickPriority = TickPriority.EXTREMELY_HIGH;
            } else if (bl2) {
                tickPriority = TickPriority.VERY_HIGH;
            }
            world.getPendingBlockTicks().scheduleTick(blockPos, this, this.getDelay(blockState), tickPriority);
        }
    }

    public boolean isLocked(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    protected boolean shouldBePowered(World world, BlockPos blockPos, BlockState blockState) {
        return this.calculateInputStrength(world, blockPos, blockState) > 0;
    }

    protected int calculateInputStrength(World world, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        BlockPos blockPos2 = blockPos.offset(direction);
        int n = world.getRedstonePower(blockPos2, direction);
        if (n >= 15) {
            return n;
        }
        BlockState blockState2 = world.getBlockState(blockPos2);
        return Math.max(n, blockState2.isIn(Blocks.REDSTONE_WIRE) ? blockState2.get(RedstoneWireBlock.POWER) : 0);
    }

    protected int getPowerOnSides(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        Direction direction2 = direction.rotateY();
        Direction direction3 = direction.rotateYCCW();
        return Math.max(this.getPowerOnSide(iWorldReader, blockPos.offset(direction2), direction2), this.getPowerOnSide(iWorldReader, blockPos.offset(direction3), direction3));
    }

    protected int getPowerOnSide(IWorldReader iWorldReader, BlockPos blockPos, Direction direction) {
        BlockState blockState = iWorldReader.getBlockState(blockPos);
        if (this.isAlternateInput(blockState)) {
            if (blockState.isIn(Blocks.REDSTONE_BLOCK)) {
                return 0;
            }
            return blockState.isIn(Blocks.REDSTONE_WIRE) ? blockState.get(RedstoneWireBlock.POWER).intValue() : iWorldReader.getStrongPower(blockPos, direction);
        }
        return 1;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(HORIZONTAL_FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        if (this.shouldBePowered(world, blockPos, blockState)) {
            world.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        this.notifyNeighbors(world, blockPos, blockState);
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl && !blockState.isIn(blockState2.getBlock())) {
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
            this.notifyNeighbors(world, blockPos, blockState);
        }
    }

    protected void notifyNeighbors(World world, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(HORIZONTAL_FACING);
        BlockPos blockPos2 = blockPos.offset(direction.getOpposite());
        world.neighborChanged(blockPos2, this, blockPos);
        world.notifyNeighborsOfStateExcept(blockPos2, this, direction);
    }

    protected boolean isAlternateInput(BlockState blockState) {
        return blockState.canProvidePower();
    }

    protected int getActiveSignal(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return 0;
    }

    public static boolean isDiode(BlockState blockState) {
        return blockState.getBlock() instanceof RedstoneDiodeBlock;
    }

    public boolean isFacingTowardsRepeater(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(HORIZONTAL_FACING).getOpposite();
        BlockState blockState2 = iBlockReader.getBlockState(blockPos.offset(direction));
        return RedstoneDiodeBlock.isDiode(blockState2) && blockState2.get(HORIZONTAL_FACING) != direction;
    }

    protected abstract int getDelay(BlockState var1);
}

