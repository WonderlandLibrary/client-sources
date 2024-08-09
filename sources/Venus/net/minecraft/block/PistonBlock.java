/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.MovingPistonBlock;
import net.minecraft.block.PistonBlockStructureHelper;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PistonBlock
extends DirectionalBlock {
    public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;
    protected static final VoxelShape PISTON_BASE_EAST_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_BASE_WEST_AABB = Block.makeCuboidShape(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_BASE_SOUTH_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
    protected static final VoxelShape PISTON_BASE_NORTH_AABB = Block.makeCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PISTON_BASE_UP_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    protected static final VoxelShape PISTON_BASE_DOWN_AABB = Block.makeCuboidShape(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    private final boolean isSticky;

    public PistonBlock(boolean bl, AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(FACING, Direction.NORTH)).with(EXTENDED, false));
        this.isSticky = bl;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (blockState.get(EXTENDED).booleanValue()) {
            switch (blockState.get(FACING)) {
                case DOWN: {
                    return PISTON_BASE_DOWN_AABB;
                }
                default: {
                    return PISTON_BASE_UP_AABB;
                }
                case NORTH: {
                    return PISTON_BASE_NORTH_AABB;
                }
                case SOUTH: {
                    return PISTON_BASE_SOUTH_AABB;
                }
                case WEST: {
                    return PISTON_BASE_WEST_AABB;
                }
                case EAST: 
            }
            return PISTON_BASE_EAST_AABB;
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock()) && !world.isRemote && world.getTileEntity(blockPos) == null) {
            this.checkForMove(world, blockPos, blockState);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, blockItemUseContext.getNearestLookingDirection().getOpposite())).with(EXTENDED, false);
    }

    private void checkForMove(World world, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.get(FACING);
        boolean bl = this.shouldBeExtended(world, blockPos, direction);
        if (bl && !blockState.get(EXTENDED).booleanValue()) {
            if (new PistonBlockStructureHelper(world, blockPos, direction, true).canMove()) {
                world.addBlockEvent(blockPos, this, 0, direction.getIndex());
            }
        } else if (!bl && blockState.get(EXTENDED).booleanValue()) {
            PistonTileEntity pistonTileEntity;
            TileEntity tileEntity;
            BlockPos blockPos2 = blockPos.offset(direction, 2);
            BlockState blockState2 = world.getBlockState(blockPos2);
            int n = 1;
            if (blockState2.isIn(Blocks.MOVING_PISTON) && blockState2.get(FACING) == direction && (tileEntity = world.getTileEntity(blockPos2)) instanceof PistonTileEntity && (pistonTileEntity = (PistonTileEntity)tileEntity).isExtending() && (pistonTileEntity.getProgress(0.0f) < 0.5f || world.getGameTime() == pistonTileEntity.getLastTicked() || ((ServerWorld)world).isInsideTick())) {
                n = 2;
            }
            world.addBlockEvent(blockPos, this, n, direction.getIndex());
        }
    }

    private boolean shouldBeExtended(World world, BlockPos blockPos, Direction direction) {
        for (Direction direction2 : Direction.values()) {
            if (direction2 == direction || !world.isSidePowered(blockPos.offset(direction2), direction2)) continue;
            return false;
        }
        if (world.isSidePowered(blockPos, Direction.DOWN)) {
            return false;
        }
        BlockPos blockPos2 = blockPos.up();
        for (Direction direction3 : Direction.values()) {
            if (direction3 == Direction.DOWN || !world.isSidePowered(blockPos2.offset(direction3), direction3)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean eventReceived(BlockState blockState, World world, BlockPos blockPos, int n, int n2) {
        Direction direction = blockState.get(FACING);
        if (!world.isRemote) {
            boolean bl = this.shouldBeExtended(world, blockPos, direction);
            if (bl && (n == 1 || n == 2)) {
                world.setBlockState(blockPos, (BlockState)blockState.with(EXTENDED, true), 1);
                return true;
            }
            if (!bl && n == 0) {
                return true;
            }
        }
        if (n == 0) {
            if (!this.doMove(world, blockPos, direction, false)) {
                return true;
            }
            world.setBlockState(blockPos, (BlockState)blockState.with(EXTENDED, true), 0);
            world.playSound(null, blockPos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5f, world.rand.nextFloat() * 0.25f + 0.6f);
        } else if (n == 1 || n == 2) {
            TileEntity tileEntity = world.getTileEntity(blockPos.offset(direction));
            if (tileEntity instanceof PistonTileEntity) {
                ((PistonTileEntity)tileEntity).clearPistonTileEntity();
            }
            BlockState blockState2 = (BlockState)((BlockState)Blocks.MOVING_PISTON.getDefaultState().with(MovingPistonBlock.FACING, direction)).with(MovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
            world.setBlockState(blockPos, blockState2, 1);
            world.setTileEntity(blockPos, MovingPistonBlock.createTilePiston((BlockState)this.getDefaultState().with(FACING, Direction.byIndex(n2 & 7)), direction, false, true));
            world.func_230547_a_(blockPos, blockState2.getBlock());
            blockState2.updateNeighbours(world, blockPos, 2);
            if (this.isSticky) {
                PistonTileEntity pistonTileEntity;
                TileEntity tileEntity2;
                BlockPos blockPos2 = blockPos.add(direction.getXOffset() * 2, direction.getYOffset() * 2, direction.getZOffset() * 2);
                BlockState blockState3 = world.getBlockState(blockPos2);
                boolean bl = false;
                if (blockState3.isIn(Blocks.MOVING_PISTON) && (tileEntity2 = world.getTileEntity(blockPos2)) instanceof PistonTileEntity && (pistonTileEntity = (PistonTileEntity)tileEntity2).getFacing() == direction && pistonTileEntity.isExtending()) {
                    pistonTileEntity.clearPistonTileEntity();
                    bl = true;
                }
                if (!bl) {
                    if (n != 1 || blockState3.isAir() || !PistonBlock.canPush(blockState3, world, blockPos2, direction.getOpposite(), false, direction) || blockState3.getPushReaction() != PushReaction.NORMAL && !blockState3.isIn(Blocks.PISTON) && !blockState3.isIn(Blocks.STICKY_PISTON)) {
                        world.removeBlock(blockPos.offset(direction), true);
                    } else {
                        this.doMove(world, blockPos, direction, true);
                    }
                }
            } else {
                world.removeBlock(blockPos.offset(direction), true);
            }
            world.playSound(null, blockPos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5f, world.rand.nextFloat() * 0.15f + 0.6f);
        }
        return false;
    }

    public static boolean canPush(BlockState blockState, World world, BlockPos blockPos, Direction direction, boolean bl, Direction direction2) {
        if (blockPos.getY() >= 0 && blockPos.getY() <= world.getHeight() - 1 && world.getWorldBorder().contains(blockPos)) {
            if (blockState.isAir()) {
                return false;
            }
            if (!(blockState.isIn(Blocks.OBSIDIAN) || blockState.isIn(Blocks.CRYING_OBSIDIAN) || blockState.isIn(Blocks.RESPAWN_ANCHOR))) {
                if (direction == Direction.DOWN && blockPos.getY() == 0) {
                    return true;
                }
                if (direction == Direction.UP && blockPos.getY() == world.getHeight() - 1) {
                    return true;
                }
                if (!blockState.isIn(Blocks.PISTON) && !blockState.isIn(Blocks.STICKY_PISTON)) {
                    if (blockState.getBlockHardness(world, blockPos) == -1.0f) {
                        return true;
                    }
                    switch (blockState.getPushReaction()) {
                        case BLOCK: {
                            return true;
                        }
                        case DESTROY: {
                            return bl;
                        }
                        case PUSH_ONLY: {
                            return direction == direction2;
                        }
                    }
                } else if (blockState.get(EXTENDED).booleanValue()) {
                    return true;
                }
                return !blockState.getBlock().isTileEntityProvider();
            }
            return true;
        }
        return true;
    }

    private boolean doMove(World world, BlockPos blockPos, Direction direction, boolean bl) {
        int n;
        Object object;
        Object object3;
        int n2;
        Object object4;
        BlockState[] blockStateArray;
        PistonBlockStructureHelper pistonBlockStructureHelper;
        BlockPos blockPos2 = blockPos.offset(direction);
        if (!bl && world.getBlockState(blockPos2).isIn(Blocks.PISTON_HEAD)) {
            world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 1);
        }
        if (!(pistonBlockStructureHelper = new PistonBlockStructureHelper(world, blockPos, direction, bl)).canMove()) {
            return true;
        }
        HashMap<BlockState[], Object> hashMap = Maps.newHashMap();
        List<BlockPos> list = pistonBlockStructureHelper.getBlocksToMove();
        ArrayList<BlockState> arrayList = Lists.newArrayList();
        for (int i = 0; i < list.size(); ++i) {
            blockStateArray = list.get(i);
            object4 = world.getBlockState((BlockPos)blockStateArray);
            arrayList.add((BlockState)object4);
            hashMap.put(blockStateArray, object4);
        }
        List<BlockPos> list2 = pistonBlockStructureHelper.getBlocksToDestroy();
        blockStateArray = new BlockState[list.size() + list2.size()];
        object4 = bl ? direction : direction.getOpposite();
        int n3 = 0;
        for (n2 = list2.size() - 1; n2 >= 0; --n2) {
            object3 = list2.get(n2);
            BlockState object22 = world.getBlockState((BlockPos)object3);
            object = object22.getBlock().isTileEntityProvider() ? world.getTileEntity((BlockPos)object3) : null;
            PistonBlock.spawnDrops(object22, world, (BlockPos)object3, (TileEntity)object);
            world.setBlockState((BlockPos)object3, Blocks.AIR.getDefaultState(), 1);
            blockStateArray[n3++] = object22;
        }
        for (n2 = list.size() - 1; n2 >= 0; --n2) {
            object3 = list.get(n2);
            BlockState blockState = world.getBlockState((BlockPos)object3);
            object3 = ((BlockPos)object3).offset((Direction)object4);
            hashMap.remove(object3);
            world.setBlockState((BlockPos)object3, (BlockState)Blocks.MOVING_PISTON.getDefaultState().with(FACING, direction), 1);
            world.setTileEntity((BlockPos)object3, MovingPistonBlock.createTilePiston((BlockState)arrayList.get(n2), direction, bl, false));
            blockStateArray[n3++] = blockState;
        }
        if (bl) {
            PistonType pistonType = this.isSticky ? PistonType.STICKY : PistonType.DEFAULT;
            object3 = (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, direction)).with(PistonHeadBlock.TYPE, pistonType);
            BlockState blockState = (BlockState)((BlockState)Blocks.MOVING_PISTON.getDefaultState().with(MovingPistonBlock.FACING, direction)).with(MovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
            hashMap.remove(blockPos2);
            world.setBlockState(blockPos2, blockState, 1);
            world.setTileEntity(blockPos2, MovingPistonBlock.createTilePiston((BlockState)object3, direction, true, true));
        }
        BlockState blockState = Blocks.AIR.getDefaultState();
        for (BlockPos blockPos3 : hashMap.keySet()) {
            world.setBlockState(blockPos3, blockState, 1);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            object = (BlockPos)entry.getKey();
            BlockState blockState2 = (BlockState)entry.getValue();
            blockState2.updateDiagonalNeighbors(world, (BlockPos)object, 2);
            blockState.updateNeighbours(world, (BlockPos)object, 2);
            blockState.updateDiagonalNeighbors(world, (BlockPos)object, 2);
        }
        n3 = 0;
        for (n = list2.size() - 1; n >= 0; --n) {
            BlockState blockState3 = blockStateArray[n3++];
            object = list2.get(n);
            blockState3.updateDiagonalNeighbors(world, (BlockPos)object, 2);
            world.notifyNeighborsOfStateChange((BlockPos)object, blockState3.getBlock());
        }
        for (n = list.size() - 1; n >= 0; --n) {
            world.notifyNeighborsOfStateChange(list.get(n), blockStateArray[n3++].getBlock());
        }
        if (bl) {
            world.notifyNeighborsOfStateChange(blockPos2, Blocks.PISTON_HEAD);
        }
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
        builder.add(FACING, EXTENDED);
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return blockState.get(EXTENDED);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

