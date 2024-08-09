/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.TripWireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateHolder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TripWireHookBlock
extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    protected static final VoxelShape HOOK_NORTH_AABB = Block.makeCuboidShape(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
    protected static final VoxelShape HOOK_SOUTH_AABB = Block.makeCuboidShape(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
    protected static final VoxelShape HOOK_WEST_AABB = Block.makeCuboidShape(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
    protected static final VoxelShape HOOK_EAST_AABB = Block.makeCuboidShape(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

    public TripWireHookBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(POWERED, false)).with(ATTACHED, false));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[blockState.get(FACING).ordinal()]) {
            default: {
                return HOOK_EAST_AABB;
            }
            case 2: {
                return HOOK_WEST_AABB;
            }
            case 3: {
                return HOOK_SOUTH_AABB;
            }
            case 4: 
        }
        return HOOK_NORTH_AABB;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        Direction direction = blockState.get(FACING);
        BlockPos blockPos2 = blockPos.offset(direction.getOpposite());
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        return direction.getAxis().isHorizontal() && blockState2.isSolidSide(iWorldReader, blockPos2, direction);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction.getOpposite() == blockState.get(FACING) && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction[] directionArray;
        BlockState blockState = (BlockState)((BlockState)this.getDefaultState().with(POWERED, false)).with(ATTACHED, false);
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        for (Direction direction : directionArray = blockItemUseContext.getNearestLookingDirections()) {
            Direction direction2;
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(FACING, direction2 = direction.getOpposite())).isValidPosition(world, blockPos)) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        this.calculateState(world, blockPos, blockState, false, false, -1, null);
    }

    public void calculateState(World world, BlockPos blockPos, BlockState blockState, boolean bl, boolean bl2, int n, @Nullable BlockState blockState2) {
        Object object;
        BlockPos blockPos2;
        Direction direction = blockState.get(FACING);
        boolean bl3 = blockState.get(ATTACHED);
        boolean bl4 = blockState.get(POWERED);
        boolean bl5 = !bl;
        boolean bl6 = false;
        int n2 = 0;
        BlockState[] blockStateArray = new BlockState[42];
        for (int i = 1; i < 42; ++i) {
            blockPos2 = blockPos.offset(direction, i);
            object = world.getBlockState(blockPos2);
            if (((AbstractBlock.AbstractBlockState)object).isIn(Blocks.TRIPWIRE_HOOK)) {
                if (((StateHolder)object).get(FACING) != direction.getOpposite()) break;
                n2 = i;
                break;
            }
            if (!((AbstractBlock.AbstractBlockState)object).isIn(Blocks.TRIPWIRE) && i != n) {
                blockStateArray[i] = null;
                bl5 = false;
                continue;
            }
            if (i == n) {
                object = MoreObjects.firstNonNull(blockState2, object);
            }
            boolean bl7 = ((StateHolder)object).get(TripWireBlock.DISARMED) == false;
            boolean bl8 = ((StateHolder)object).get(TripWireBlock.POWERED);
            bl6 |= bl7 && bl8;
            blockStateArray[i] = object;
            if (i != n) continue;
            world.getPendingBlockTicks().scheduleTick(blockPos, this, 10);
            bl5 &= bl7;
        }
        BlockState blockState3 = (BlockState)((BlockState)this.getDefaultState().with(ATTACHED, bl5)).with(POWERED, bl6 &= (bl5 &= n2 > 1));
        if (n2 > 0) {
            blockPos2 = blockPos.offset(direction, n2);
            object = direction.getOpposite();
            world.setBlockState(blockPos2, (BlockState)blockState3.with(FACING, object), 0);
            this.notifyNeighbors(world, blockPos2, (Direction)object);
            this.playSound(world, blockPos2, bl5, bl6, bl3, bl4);
        }
        this.playSound(world, blockPos, bl5, bl6, bl3, bl4);
        if (!bl) {
            world.setBlockState(blockPos, (BlockState)blockState3.with(FACING, direction), 0);
            if (bl2) {
                this.notifyNeighbors(world, blockPos, direction);
            }
        }
        if (bl3 != bl5) {
            for (int i = 1; i < n2; ++i) {
                object = blockPos.offset(direction, i);
                BlockState blockState4 = blockStateArray[i];
                if (blockState4 == null) continue;
                world.setBlockState((BlockPos)object, (BlockState)blockState4.with(ATTACHED, bl5), 0);
                if (world.getBlockState((BlockPos)object).isAir()) continue;
            }
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.calculateState(serverWorld, blockPos, blockState, false, true, -1, null);
    }

    private void playSound(World world, BlockPos blockPos, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (bl2 && !bl4) {
            world.playSound(null, blockPos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4f, 0.6f);
        } else if (!bl2 && bl4) {
            world.playSound(null, blockPos, SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4f, 0.5f);
        } else if (bl && !bl3) {
            world.playSound(null, blockPos, SoundEvents.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4f, 0.7f);
        } else if (!bl && bl3) {
            world.playSound(null, blockPos, SoundEvents.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4f, 1.2f / (world.rand.nextFloat() * 0.2f + 0.9f));
        }
    }

    private void notifyNeighbors(World world, BlockPos blockPos, Direction direction) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(direction.getOpposite()), this);
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl && !blockState.isIn(blockState2.getBlock())) {
            boolean bl2 = blockState.get(ATTACHED);
            boolean bl3 = blockState.get(POWERED);
            if (bl2 || bl3) {
                this.calculateState(world, blockPos, blockState, true, false, -1, null);
            }
            if (bl3) {
                world.notifyNeighborsOfStateChange(blockPos, this);
                world.notifyNeighborsOfStateChange(blockPos.offset(blockState.get(FACING).getOpposite()), this);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWERED) != false ? 15 : 0;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        if (!blockState.get(POWERED).booleanValue()) {
            return 1;
        }
        return blockState.get(FACING) == direction ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(FACING, rotation.rotate(blockState.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, ATTACHED);
    }
}

