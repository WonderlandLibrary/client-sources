/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Arrays;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PistonHeadBlock
extends DirectionalBlock {
    public static final EnumProperty<PistonType> TYPE = BlockStateProperties.PISTON_TYPE;
    public static final BooleanProperty SHORT = BlockStateProperties.SHORT;
    protected static final VoxelShape PISTON_EXTENSION_EAST_AABB = Block.makeCuboidShape(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_EXTENSION_WEST_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_EXTENSION_SOUTH_AABB = Block.makeCuboidShape(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_EXTENSION_NORTH_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
    protected static final VoxelShape PISTON_EXTENSION_UP_AABB = Block.makeCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_EXTENSION_DOWN_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    protected static final VoxelShape UP_ARM_AABB = Block.makeCuboidShape(6.0, -4.0, 6.0, 10.0, 12.0, 10.0);
    protected static final VoxelShape DOWN_ARM_AABB = Block.makeCuboidShape(6.0, 4.0, 6.0, 10.0, 20.0, 10.0);
    protected static final VoxelShape SOUTH_ARM_AABB = Block.makeCuboidShape(6.0, 6.0, -4.0, 10.0, 10.0, 12.0);
    protected static final VoxelShape NORTH_ARM_AABB = Block.makeCuboidShape(6.0, 6.0, 4.0, 10.0, 10.0, 20.0);
    protected static final VoxelShape EAST_ARM_AABB = Block.makeCuboidShape(-4.0, 6.0, 6.0, 12.0, 10.0, 10.0);
    protected static final VoxelShape WEST_ARM_AABB = Block.makeCuboidShape(4.0, 6.0, 6.0, 20.0, 10.0, 10.0);
    protected static final VoxelShape SHORT_UP_ARM_AABB = Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
    protected static final VoxelShape SHORT_DOWN_ARM_AABB = Block.makeCuboidShape(6.0, 4.0, 6.0, 10.0, 16.0, 10.0);
    protected static final VoxelShape SHORT_SOUTH_ARM_AABB = Block.makeCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 12.0);
    protected static final VoxelShape SHORT_NORTH_ARM_AABB = Block.makeCuboidShape(6.0, 6.0, 4.0, 10.0, 10.0, 16.0);
    protected static final VoxelShape SHORT_EAST_ARM_AABB = Block.makeCuboidShape(0.0, 6.0, 6.0, 12.0, 10.0, 10.0);
    protected static final VoxelShape SHORT_WEST_ARM_AABB = Block.makeCuboidShape(4.0, 6.0, 6.0, 16.0, 10.0, 10.0);
    private static final VoxelShape[] EXTENDED_SHAPES = PistonHeadBlock.getShapesForExtension(true);
    private static final VoxelShape[] UNEXTENDED_SHAPES = PistonHeadBlock.getShapesForExtension(false);

    private static VoxelShape[] getShapesForExtension(boolean bl) {
        return (VoxelShape[])Arrays.stream(Direction.values()).map(arg_0 -> PistonHeadBlock.lambda$getShapesForExtension$0(bl, arg_0)).toArray(PistonHeadBlock::lambda$getShapesForExtension$1);
    }

    private static VoxelShape getShapeForDirection(Direction direction, boolean bl) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            default: {
                return VoxelShapes.or(PISTON_EXTENSION_DOWN_AABB, bl ? SHORT_DOWN_ARM_AABB : DOWN_ARM_AABB);
            }
            case 2: {
                return VoxelShapes.or(PISTON_EXTENSION_UP_AABB, bl ? SHORT_UP_ARM_AABB : UP_ARM_AABB);
            }
            case 3: {
                return VoxelShapes.or(PISTON_EXTENSION_NORTH_AABB, bl ? SHORT_NORTH_ARM_AABB : NORTH_ARM_AABB);
            }
            case 4: {
                return VoxelShapes.or(PISTON_EXTENSION_SOUTH_AABB, bl ? SHORT_SOUTH_ARM_AABB : SOUTH_ARM_AABB);
            }
            case 5: {
                return VoxelShapes.or(PISTON_EXTENSION_WEST_AABB, bl ? SHORT_WEST_ARM_AABB : WEST_ARM_AABB);
            }
            case 6: 
        }
        return VoxelShapes.or(PISTON_EXTENSION_EAST_AABB, bl ? SHORT_EAST_ARM_AABB : EAST_ARM_AABB);
    }

    public PistonHeadBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(TYPE, PistonType.DEFAULT)).with(SHORT, false));
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return (blockState.get(SHORT) != false ? EXTENDED_SHAPES : UNEXTENDED_SHAPES)[blockState.get(FACING).ordinal()];
    }

    private boolean isExtended(BlockState blockState, BlockState blockState2) {
        Block block = blockState.get(TYPE) == PistonType.DEFAULT ? Blocks.PISTON : Blocks.STICKY_PISTON;
        return blockState2.isIn(block) && blockState2.get(PistonBlock.EXTENDED) != false && blockState2.get(FACING) == blockState.get(FACING);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        BlockPos blockPos2;
        if (!world.isRemote && playerEntity.abilities.isCreativeMode && this.isExtended(blockState, world.getBlockState(blockPos2 = blockPos.offset(blockState.get(FACING).getOpposite())))) {
            world.destroyBlock(blockPos2, true);
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
            BlockPos blockPos2 = blockPos.offset(blockState.get(FACING).getOpposite());
            if (this.isExtended(blockState, world.getBlockState(blockPos2))) {
                world.destroyBlock(blockPos2, false);
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction.getOpposite() == blockState.get(FACING) && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.offset(blockState.get(FACING).getOpposite()));
        return this.isExtended(blockState, blockState2) || blockState2.isIn(Blocks.MOVING_PISTON) && blockState2.get(FACING) == blockState.get(FACING);
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (blockState.isValidPosition(world, blockPos)) {
            BlockPos blockPos3 = blockPos.offset(blockState.get(FACING).getOpposite());
            world.getBlockState(blockPos3).neighborChanged(world, blockPos3, block, blockPos2, true);
        }
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(blockState.get(TYPE) == PistonType.STICKY ? Blocks.STICKY_PISTON : Blocks.PISTON);
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
        builder.add(FACING, TYPE, SHORT);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    private static VoxelShape[] lambda$getShapesForExtension$1(int n) {
        return new VoxelShape[n];
    }

    private static VoxelShape lambda$getShapesForExtension$0(boolean bl, Direction direction) {
        return PistonHeadBlock.getShapeForDirection(direction, bl);
    }
}

