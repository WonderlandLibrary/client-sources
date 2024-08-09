/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RedstoneWireBlock
extends Block {
    public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.REDSTONE_NORTH;
    public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.REDSTONE_EAST;
    public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.REDSTONE_SOUTH;
    public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.REDSTONE_WEST;
    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
    public static final Map<Direction, EnumProperty<RedstoneSide>> FACING_PROPERTY_MAP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
    private static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    private static final Map<Direction, VoxelShape> SIDE_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(3.0, 0.0, 0.0, 13.0, 1.0, 13.0), Direction.SOUTH, Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 16.0), Direction.EAST, Block.makeCuboidShape(3.0, 0.0, 3.0, 16.0, 1.0, 13.0), Direction.WEST, Block.makeCuboidShape(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)));
    private static final Map<Direction, VoxelShape> SIDE_TO_ASCENDING_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.NORTH), Block.makeCuboidShape(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)), Direction.SOUTH, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.SOUTH), Block.makeCuboidShape(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)), Direction.EAST, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.EAST), Block.makeCuboidShape(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)), Direction.WEST, VoxelShapes.or(SIDE_TO_SHAPE.get(Direction.WEST), Block.makeCuboidShape(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))));
    private final Map<BlockState, VoxelShape> stateToShapeMap = Maps.newHashMap();
    private static final Vector3f[] powerRGB = new Vector3f[16];
    private final BlockState sideBaseState;
    private boolean canProvidePower = true;

    public RedstoneWireBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(NORTH, RedstoneSide.NONE)).with(EAST, RedstoneSide.NONE)).with(SOUTH, RedstoneSide.NONE)).with(WEST, RedstoneSide.NONE)).with(POWER, 0));
        this.sideBaseState = (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(NORTH, RedstoneSide.SIDE)).with(EAST, RedstoneSide.SIDE)).with(SOUTH, RedstoneSide.SIDE)).with(WEST, RedstoneSide.SIDE);
        for (BlockState blockState : this.getStateContainer().getValidStates()) {
            if (blockState.get(POWER) != 0) continue;
            this.stateToShapeMap.put(blockState, this.getShapeForState(blockState));
        }
    }

    private VoxelShape getShapeForState(BlockState blockState) {
        VoxelShape voxelShape = BASE_SHAPE;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneSide = (RedstoneSide)blockState.get(FACING_PROPERTY_MAP.get(direction));
            if (redstoneSide == RedstoneSide.SIDE) {
                voxelShape = VoxelShapes.or(voxelShape, SIDE_TO_SHAPE.get(direction));
                continue;
            }
            if (redstoneSide != RedstoneSide.UP) continue;
            voxelShape = VoxelShapes.or(voxelShape, SIDE_TO_ASCENDING_SHAPE.get(direction));
        }
        return voxelShape;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.stateToShapeMap.get(blockState.with(POWER, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.getUpdatedState(blockItemUseContext.getWorld(), this.sideBaseState, blockItemUseContext.getPos());
    }

    private BlockState getUpdatedState(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos) {
        boolean bl;
        boolean bl2 = RedstoneWireBlock.areAllSidesInvalid(blockState);
        blockState = this.recalculateFacingState(iBlockReader, (BlockState)this.getDefaultState().with(POWER, blockState.get(POWER)), blockPos);
        if (bl2 && RedstoneWireBlock.areAllSidesInvalid(blockState)) {
            return blockState;
        }
        boolean bl3 = blockState.get(NORTH).func_235921_b_();
        boolean bl4 = blockState.get(SOUTH).func_235921_b_();
        boolean bl5 = blockState.get(EAST).func_235921_b_();
        boolean bl6 = blockState.get(WEST).func_235921_b_();
        boolean bl7 = !bl3 && !bl4;
        boolean bl8 = bl = !bl5 && !bl6;
        if (!bl6 && bl7) {
            blockState = (BlockState)blockState.with(WEST, RedstoneSide.SIDE);
        }
        if (!bl5 && bl7) {
            blockState = (BlockState)blockState.with(EAST, RedstoneSide.SIDE);
        }
        if (!bl3 && bl) {
            blockState = (BlockState)blockState.with(NORTH, RedstoneSide.SIDE);
        }
        if (!bl4 && bl) {
            blockState = (BlockState)blockState.with(SOUTH, RedstoneSide.SIDE);
        }
        return blockState;
    }

    private BlockState recalculateFacingState(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos) {
        boolean bl = !iBlockReader.getBlockState(blockPos.up()).isNormalCube(iBlockReader, blockPos);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (((RedstoneSide)blockState.get(FACING_PROPERTY_MAP.get(direction))).func_235921_b_()) continue;
            RedstoneSide redstoneSide = this.recalculateSide(iBlockReader, blockPos, direction, bl);
            blockState = (BlockState)blockState.with(FACING_PROPERTY_MAP.get(direction), redstoneSide);
        }
        return blockState;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == Direction.DOWN) {
            return blockState;
        }
        if (direction == Direction.UP) {
            return this.getUpdatedState(iWorld, blockState, blockPos);
        }
        RedstoneSide redstoneSide = this.getSide(iWorld, blockPos, direction);
        return redstoneSide.func_235921_b_() == ((RedstoneSide)blockState.get(FACING_PROPERTY_MAP.get(direction))).func_235921_b_() && !RedstoneWireBlock.areAllSidesValid(blockState) ? (BlockState)blockState.with(FACING_PROPERTY_MAP.get(direction), redstoneSide) : this.getUpdatedState(iWorld, (BlockState)((BlockState)this.sideBaseState.with(POWER, blockState.get(POWER))).with(FACING_PROPERTY_MAP.get(direction), redstoneSide), blockPos);
    }

    private static boolean areAllSidesValid(BlockState blockState) {
        return blockState.get(NORTH).func_235921_b_() && blockState.get(SOUTH).func_235921_b_() && blockState.get(EAST).func_235921_b_() && blockState.get(WEST).func_235921_b_();
    }

    private static boolean areAllSidesInvalid(BlockState blockState) {
        return !blockState.get(NORTH).func_235921_b_() && !blockState.get(SOUTH).func_235921_b_() && !blockState.get(EAST).func_235921_b_() && !blockState.get(WEST).func_235921_b_();
    }

    @Override
    public void updateDiagonalNeighbors(BlockState blockState, IWorld iWorld, BlockPos blockPos, int n, int n2) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Object object;
            Object object2;
            RedstoneSide redstoneSide = (RedstoneSide)blockState.get(FACING_PROPERTY_MAP.get(direction));
            if (redstoneSide == RedstoneSide.NONE || iWorld.getBlockState(mutable.setAndMove(blockPos, direction)).isIn(this)) continue;
            mutable.move(Direction.DOWN);
            BlockState blockState2 = iWorld.getBlockState(mutable);
            if (!blockState2.isIn(Blocks.OBSERVER)) {
                object2 = mutable.offset(direction.getOpposite());
                object = blockState2.updatePostPlacement(direction.getOpposite(), iWorld.getBlockState((BlockPos)object2), iWorld, mutable, (BlockPos)object2);
                RedstoneWireBlock.replaceBlockState(blockState2, (BlockState)object, iWorld, mutable, n, n2);
            }
            mutable.setAndMove(blockPos, direction).move(Direction.UP);
            object2 = iWorld.getBlockState(mutable);
            if (((AbstractBlock.AbstractBlockState)object2).isIn(Blocks.OBSERVER)) continue;
            object = mutable.offset(direction.getOpposite());
            BlockState blockState3 = ((AbstractBlock.AbstractBlockState)object2).updatePostPlacement(direction.getOpposite(), iWorld.getBlockState((BlockPos)object), iWorld, mutable, (BlockPos)object);
            RedstoneWireBlock.replaceBlockState((BlockState)object2, blockState3, iWorld, mutable, n, n2);
        }
    }

    private RedstoneSide getSide(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return this.recalculateSide(iBlockReader, blockPos, direction, !iBlockReader.getBlockState(blockPos.up()).isNormalCube(iBlockReader, blockPos));
    }

    private RedstoneSide recalculateSide(IBlockReader iBlockReader, BlockPos blockPos, Direction direction, boolean bl) {
        boolean bl2;
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState blockState = iBlockReader.getBlockState(blockPos2);
        if (bl && (bl2 = this.canPlaceOnTopOf(iBlockReader, blockPos2, blockState)) && RedstoneWireBlock.canConnectUpwardsTo(iBlockReader.getBlockState(blockPos2.up()))) {
            if (blockState.isSolidSide(iBlockReader, blockPos2, direction.getOpposite())) {
                return RedstoneSide.UP;
            }
            return RedstoneSide.SIDE;
        }
        return !RedstoneWireBlock.canConnectTo(blockState, direction) && (blockState.isNormalCube(iBlockReader, blockPos2) || !RedstoneWireBlock.canConnectUpwardsTo(iBlockReader.getBlockState(blockPos2.down()))) ? RedstoneSide.NONE : RedstoneSide.SIDE;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState2 = iWorldReader.getBlockState(blockPos2);
        return this.canPlaceOnTopOf(iWorldReader, blockPos2, blockState2);
    }

    private boolean canPlaceOnTopOf(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return blockState.isSolidSide(iBlockReader, blockPos, Direction.UP) || blockState.isIn(Blocks.HOPPER);
    }

    private void updatePower(World world, BlockPos blockPos, BlockState blockState) {
        int n = this.getStrongestSignal(world, blockPos);
        if (blockState.get(POWER) != n) {
            if (world.getBlockState(blockPos) == blockState) {
                world.setBlockState(blockPos, (BlockState)blockState.with(POWER, n), 1);
            }
            HashSet<BlockPos> hashSet = Sets.newHashSet();
            hashSet.add(blockPos);
            for (Direction direction : Direction.values()) {
                hashSet.add(blockPos.offset(direction));
            }
            for (BlockPos blockPos2 : hashSet) {
                world.notifyNeighborsOfStateChange(blockPos2, this);
            }
        }
    }

    private int getStrongestSignal(World world, BlockPos blockPos) {
        this.canProvidePower = false;
        int n = world.getRedstonePowerFromNeighbors(blockPos);
        this.canProvidePower = true;
        int n2 = 0;
        if (n < 15) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                n2 = Math.max(n2, this.getPower(blockState));
                BlockPos blockPos3 = blockPos.up();
                if (blockState.isNormalCube(world, blockPos2) && !world.getBlockState(blockPos3).isNormalCube(world, blockPos3)) {
                    n2 = Math.max(n2, this.getPower(world.getBlockState(blockPos2.up())));
                    continue;
                }
                if (blockState.isNormalCube(world, blockPos2)) continue;
                n2 = Math.max(n2, this.getPower(world.getBlockState(blockPos2.down())));
            }
        }
        return Math.max(n, n2 - 1);
    }

    private int getPower(BlockState blockState) {
        return blockState.isIn(this) ? blockState.get(POWER) : 0;
    }

    private void notifyWireNeighborsOfStateChange(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos).isIn(this)) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            for (Direction direction : Direction.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(direction), this);
            }
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock()) && !world.isRemote) {
            this.updatePower(world, blockPos, blockState);
            for (Direction direction : Direction.Plane.VERTICAL) {
                world.notifyNeighborsOfStateChange(blockPos.offset(direction), this);
            }
            this.updateNeighboursStateChange(world, blockPos);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl && !blockState.isIn(blockState2.getBlock())) {
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
            if (!world.isRemote) {
                for (Direction direction : Direction.values()) {
                    world.notifyNeighborsOfStateChange(blockPos.offset(direction), this);
                }
                this.updatePower(world, blockPos, blockState);
                this.updateNeighboursStateChange(world, blockPos);
            }
        }
    }

    private void updateNeighboursStateChange(World world, BlockPos blockPos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            this.notifyWireNeighborsOfStateChange(world, blockPos.offset(direction));
        }
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            if (world.getBlockState(blockPos2).isNormalCube(world, blockPos2)) {
                this.notifyWireNeighborsOfStateChange(world, blockPos2.up());
                continue;
            }
            this.notifyWireNeighborsOfStateChange(world, blockPos2.down());
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!world.isRemote) {
            if (blockState.isValidPosition(world, blockPos)) {
                this.updatePower(world, blockPos, blockState);
            } else {
                RedstoneWireBlock.spawnDrops(blockState, world, blockPos);
                world.removeBlock(blockPos, true);
            }
        }
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return !this.canProvidePower ? 0 : blockState.getWeakPower(iBlockReader, blockPos, direction);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        if (this.canProvidePower && direction != Direction.DOWN) {
            int n = blockState.get(POWER);
            if (n == 0) {
                return 1;
            }
            return direction != Direction.UP && !((RedstoneSide)this.getUpdatedState(iBlockReader, blockState, blockPos).get(FACING_PROPERTY_MAP.get(direction.getOpposite()))).func_235921_b_() ? 0 : n;
        }
        return 1;
    }

    protected static boolean canConnectUpwardsTo(BlockState blockState) {
        return RedstoneWireBlock.canConnectTo(blockState, null);
    }

    protected static boolean canConnectTo(BlockState blockState, @Nullable Direction direction) {
        if (blockState.isIn(Blocks.REDSTONE_WIRE)) {
            return false;
        }
        if (blockState.isIn(Blocks.REPEATER)) {
            Direction direction2 = blockState.get(RepeaterBlock.HORIZONTAL_FACING);
            return direction2 == direction || direction2.getOpposite() == direction;
        }
        if (blockState.isIn(Blocks.OBSERVER)) {
            return direction == blockState.get(ObserverBlock.FACING);
        }
        return blockState.canProvidePower() && direction != null;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return this.canProvidePower;
    }

    public static int getRGBByPower(int n) {
        Vector3f vector3f = powerRGB[n];
        return MathHelper.rgb(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    private void spawnPoweredParticle(World world, Random random2, BlockPos blockPos, Vector3f vector3f, Direction direction, Direction direction2, float f, float f2) {
        float f3 = f2 - f;
        if (!(random2.nextFloat() >= 0.2f * f3)) {
            float f4 = 0.4375f;
            float f5 = f + f3 * random2.nextFloat();
            double d = 0.5 + (double)(0.4375f * (float)direction.getXOffset()) + (double)(f5 * (float)direction2.getXOffset());
            double d2 = 0.5 + (double)(0.4375f * (float)direction.getYOffset()) + (double)(f5 * (float)direction2.getYOffset());
            double d3 = 0.5 + (double)(0.4375f * (float)direction.getZOffset()) + (double)(f5 * (float)direction2.getZOffset());
            world.addParticle(new RedstoneParticleData(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0f), (double)blockPos.getX() + d, (double)blockPos.getY() + d2, (double)blockPos.getZ() + d3, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        int n = blockState.get(POWER);
        if (n != 0) {
            block4: for (Direction direction : Direction.Plane.HORIZONTAL) {
                RedstoneSide redstoneSide = (RedstoneSide)blockState.get(FACING_PROPERTY_MAP.get(direction));
                switch (redstoneSide) {
                    case UP: {
                        this.spawnPoweredParticle(world, random2, blockPos, powerRGB[n], direction, Direction.UP, -0.5f, 0.5f);
                    }
                    case SIDE: {
                        this.spawnPoweredParticle(world, random2, blockPos, powerRGB[n], Direction.DOWN, direction, 0.0f, 0.5f);
                        continue block4;
                    }
                }
                this.spawnPoweredParticle(world, random2, blockPos, powerRGB[n], Direction.DOWN, direction, 0.0f, 0.3f);
            }
        }
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, blockState.get(SOUTH))).with(EAST, blockState.get(WEST))).with(SOUTH, blockState.get(NORTH))).with(WEST, blockState.get(EAST));
            }
            case COUNTERCLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, blockState.get(EAST))).with(EAST, blockState.get(SOUTH))).with(SOUTH, blockState.get(WEST))).with(WEST, blockState.get(NORTH));
            }
            case CLOCKWISE_90: {
                return (BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, blockState.get(WEST))).with(EAST, blockState.get(NORTH))).with(SOUTH, blockState.get(EAST))).with(WEST, blockState.get(SOUTH));
            }
        }
        return blockState;
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT: {
                return (BlockState)((BlockState)blockState.with(NORTH, blockState.get(SOUTH))).with(SOUTH, blockState.get(NORTH));
            }
            case FRONT_BACK: {
                return (BlockState)((BlockState)blockState.with(EAST, blockState.get(WEST))).with(WEST, blockState.get(EAST));
            }
        }
        return super.mirror(blockState, mirror);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, POWER);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!playerEntity.abilities.allowEdit) {
            return ActionResultType.PASS;
        }
        if (RedstoneWireBlock.areAllSidesValid(blockState) || RedstoneWireBlock.areAllSidesInvalid(blockState)) {
            BlockState blockState2 = RedstoneWireBlock.areAllSidesValid(blockState) ? this.getDefaultState() : this.sideBaseState;
            blockState2 = (BlockState)blockState2.with(POWER, blockState.get(POWER));
            if ((blockState2 = this.getUpdatedState(world, blockState2, blockPos)) != blockState) {
                world.setBlockState(blockPos, blockState2, 0);
                this.updateChangedConnections(world, blockPos, blockState, blockState2);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    private void updateChangedConnections(World world, BlockPos blockPos, BlockState blockState, BlockState blockState2) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            if (((RedstoneSide)blockState.get(FACING_PROPERTY_MAP.get(direction))).func_235921_b_() == ((RedstoneSide)blockState2.get(FACING_PROPERTY_MAP.get(direction))).func_235921_b_() || !world.getBlockState(blockPos2).isNormalCube(world, blockPos2)) continue;
            world.notifyNeighborsOfStateExcept(blockPos2, blockState2.getBlock(), direction.getOpposite());
        }
    }

    static {
        for (int i = 0; i <= 15; ++i) {
            float f;
            float f2 = f * 0.6f + ((f = (float)i / 15.0f) > 0.0f ? 0.4f : 0.3f);
            float f3 = MathHelper.clamp(f * f * 0.7f - 0.5f, 0.0f, 1.0f);
            float f4 = MathHelper.clamp(f * f * 0.6f - 0.7f, 0.0f, 1.0f);
            RedstoneWireBlock.powerRGB[i] = new Vector3f(f2, f3, f4);
        }
    }
}

