/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class HopperBlock
extends ContainerBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING_EXCEPT_UP;
    public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
    private static final VoxelShape INPUT_SHAPE = Block.makeCuboidShape(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.makeCuboidShape(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
    private static final VoxelShape INPUT_MIDDLE_SHAPE = VoxelShapes.or(MIDDLE_SHAPE, INPUT_SHAPE);
    private static final VoxelShape BASE_SHAPE = VoxelShapes.combineAndSimplify(INPUT_MIDDLE_SHAPE, IHopper.INSIDE_BOWL_SHAPE, IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape DOWN_SHAPE = VoxelShapes.or(BASE_SHAPE, Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
    private static final VoxelShape EAST_SHAPE = VoxelShapes.or(BASE_SHAPE, Block.makeCuboidShape(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.or(BASE_SHAPE, Block.makeCuboidShape(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(BASE_SHAPE, Block.makeCuboidShape(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
    private static final VoxelShape WEST_SHAPE = VoxelShapes.or(BASE_SHAPE, Block.makeCuboidShape(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
    private static final VoxelShape DOWN_RAYTRACE_SHAPE = IHopper.INSIDE_BOWL_SHAPE;
    private static final VoxelShape EAST_RAYTRACE_SHAPE = VoxelShapes.or(IHopper.INSIDE_BOWL_SHAPE, Block.makeCuboidShape(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
    private static final VoxelShape NORTH_RAYTRACE_SHAPE = VoxelShapes.or(IHopper.INSIDE_BOWL_SHAPE, Block.makeCuboidShape(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
    private static final VoxelShape SOUTH_RAYTRACE_SHAPE = VoxelShapes.or(IHopper.INSIDE_BOWL_SHAPE, Block.makeCuboidShape(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
    private static final VoxelShape WEST_RAYTRACE_SHAPE = VoxelShapes.or(IHopper.INSIDE_BOWL_SHAPE, Block.makeCuboidShape(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

    public HopperBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.DOWN)).with(ENABLED, true));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[blockState.get(FACING).ordinal()]) {
            case 1: {
                return DOWN_SHAPE;
            }
            case 2: {
                return NORTH_SHAPE;
            }
            case 3: {
                return SOUTH_SHAPE;
            }
            case 4: {
                return WEST_SHAPE;
            }
            case 5: {
                return EAST_SHAPE;
            }
        }
        return BASE_SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[blockState.get(FACING).ordinal()]) {
            case 1: {
                return DOWN_RAYTRACE_SHAPE;
            }
            case 2: {
                return NORTH_RAYTRACE_SHAPE;
            }
            case 3: {
                return SOUTH_RAYTRACE_SHAPE;
            }
            case 4: {
                return WEST_RAYTRACE_SHAPE;
            }
            case 5: {
                return EAST_RAYTRACE_SHAPE;
            }
        }
        return IHopper.INSIDE_BOWL_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getFace().getOpposite();
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction)).with(ENABLED, true);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new HopperTileEntity();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof HopperTileEntity) {
            ((HopperTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            this.updateState(world, blockPos, blockState);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof HopperTileEntity) {
            playerEntity.openContainer((HopperTileEntity)tileEntity);
            playerEntity.addStat(Stats.INSPECT_HOPPER);
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        this.updateState(world, blockPos, blockState);
    }

    private void updateState(World world, BlockPos blockPos, BlockState blockState) {
        boolean bl;
        boolean bl2 = bl = !world.isBlockPowered(blockPos);
        if (bl != blockState.get(ENABLED)) {
            world.setBlockState(blockPos, (BlockState)blockState.with(ENABLED, bl), 1);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof HopperTileEntity) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)((HopperTileEntity)tileEntity));
                world.updateComparatorOutputLevel(blockPos, this);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
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
        builder.add(FACING, ENABLED);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof HopperTileEntity) {
            ((HopperTileEntity)tileEntity).onEntityCollision(entity2);
        }
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

