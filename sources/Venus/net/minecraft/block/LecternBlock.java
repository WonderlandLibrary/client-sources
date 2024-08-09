/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LecternBlock
extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty HAS_BOOK = BlockStateProperties.HAS_BOOK;
    public static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    public static final VoxelShape POST_SHAPE = Block.makeCuboidShape(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
    public static final VoxelShape COMMON_SHAPE = VoxelShapes.or(BASE_SHAPE, POST_SHAPE);
    public static final VoxelShape TOP_PLATE_SHAPE = Block.makeCuboidShape(0.0, 15.0, 0.0, 16.0, 15.0, 16.0);
    public static final VoxelShape COLLISION_SHAPE = VoxelShapes.or(COMMON_SHAPE, TOP_PLATE_SHAPE);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.or(Block.makeCuboidShape(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0), Block.makeCuboidShape(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0), Block.makeCuboidShape(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0), COMMON_SHAPE);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333), Block.makeCuboidShape(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667), Block.makeCuboidShape(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0), COMMON_SHAPE);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.or(Block.makeCuboidShape(15.0, 10.0, 0.0, 10.666667, 14.0, 16.0), Block.makeCuboidShape(10.666667, 12.0, 0.0, 6.333333, 16.0, 16.0), Block.makeCuboidShape(6.333333, 14.0, 0.0, 2.0, 18.0, 16.0), COMMON_SHAPE);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0, 10.0, 15.0, 16.0, 14.0, 10.666667), Block.makeCuboidShape(0.0, 12.0, 10.666667, 16.0, 16.0, 6.333333), Block.makeCuboidShape(0.0, 14.0, 6.333333, 16.0, 18.0, 2.0), COMMON_SHAPE);

    protected LecternBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(POWERED, false)).with(HAS_BOOK, false));
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getRenderShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return COMMON_SHAPE;
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        CompoundNBT compoundNBT;
        World world = blockItemUseContext.getWorld();
        ItemStack itemStack = blockItemUseContext.getItem();
        CompoundNBT compoundNBT2 = itemStack.getTag();
        PlayerEntity playerEntity = blockItemUseContext.getPlayer();
        boolean bl = false;
        if (!world.isRemote && playerEntity != null && compoundNBT2 != null && playerEntity.canUseCommandBlock() && compoundNBT2.contains("BlockEntityTag") && (compoundNBT = compoundNBT2.getCompound("BlockEntityTag")).contains("Book")) {
            bl = true;
        }
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite())).with(HAS_BOOK, bl);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[blockState.get(FACING).ordinal()]) {
            case 1: {
                return NORTH_SHAPE;
            }
            case 2: {
                return SOUTH_SHAPE;
            }
            case 3: {
                return EAST_SHAPE;
            }
            case 4: {
                return WEST_SHAPE;
            }
        }
        return COMMON_SHAPE;
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
        builder.add(FACING, POWERED, HAS_BOOK);
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new LecternTileEntity();
    }

    public static boolean tryPlaceBook(World world, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        if (!blockState.get(HAS_BOOK).booleanValue()) {
            if (!world.isRemote) {
                LecternBlock.placeBook(world, blockPos, blockState, itemStack);
            }
            return false;
        }
        return true;
    }

    private static void placeBook(World world, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof LecternTileEntity) {
            LecternTileEntity lecternTileEntity = (LecternTileEntity)tileEntity;
            lecternTileEntity.setBook(itemStack.split(1));
            LecternBlock.setHasBook(world, blockPos, blockState, true);
            world.playSound(null, blockPos, SoundEvents.ITEM_BOOK_PUT, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    public static void setHasBook(World world, BlockPos blockPos, BlockState blockState, boolean bl) {
        world.setBlockState(blockPos, (BlockState)((BlockState)blockState.with(POWERED, false)).with(HAS_BOOK, bl), 0);
        LecternBlock.notifyNeighbors(world, blockPos, blockState);
    }

    public static void pulse(World world, BlockPos blockPos, BlockState blockState) {
        LecternBlock.setPowered(world, blockPos, blockState, true);
        world.getPendingBlockTicks().scheduleTick(blockPos, blockState.getBlock(), 2);
        world.playEvent(1043, blockPos, 0);
    }

    private static void setPowered(World world, BlockPos blockPos, BlockState blockState, boolean bl) {
        world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, bl), 0);
        LecternBlock.notifyNeighbors(world, blockPos, blockState);
    }

    private static void notifyNeighbors(World world, BlockPos blockPos, BlockState blockState) {
        world.notifyNeighborsOfStateChange(blockPos.down(), blockState.getBlock());
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        LecternBlock.setPowered(serverWorld, blockPos, blockState, false);
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            if (blockState.get(HAS_BOOK).booleanValue()) {
                this.dropBook(blockState, world, blockPos);
            }
            if (blockState.get(POWERED).booleanValue()) {
                world.notifyNeighborsOfStateChange(blockPos.down(), this);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    private void dropBook(BlockState blockState, World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof LecternTileEntity) {
            LecternTileEntity lecternTileEntity = (LecternTileEntity)tileEntity;
            Direction direction = blockState.get(FACING);
            ItemStack itemStack = lecternTileEntity.getBook().copy();
            float f = 0.25f * (float)direction.getXOffset();
            float f2 = 0.25f * (float)direction.getZOffset();
            ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + 0.5 + (double)f, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5 + (double)f2, itemStack);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
            lecternTileEntity.clear();
        }
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWERED) != false ? 15 : 0;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return direction == Direction.UP && blockState.get(POWERED) != false ? 15 : 0;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        TileEntity tileEntity;
        if (blockState.get(HAS_BOOK).booleanValue() && (tileEntity = world.getTileEntity(blockPos)) instanceof LecternTileEntity) {
            return ((LecternTileEntity)tileEntity).getComparatorSignalLevel();
        }
        return 1;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (blockState.get(HAS_BOOK).booleanValue()) {
            if (!world.isRemote) {
                this.openContainer(world, blockPos, playerEntity);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        return !itemStack.isEmpty() && !itemStack.getItem().isIn(ItemTags.LECTERN_BOOKS) ? ActionResultType.CONSUME : ActionResultType.PASS;
    }

    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        return blockState.get(HAS_BOOK) == false ? null : super.getContainer(blockState, world, blockPos);
    }

    private void openContainer(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof LecternTileEntity) {
            playerEntity.openContainer((LecternTileEntity)tileEntity);
            playerEntity.addStat(Stats.INTERACT_WITH_LECTERN);
        }
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

