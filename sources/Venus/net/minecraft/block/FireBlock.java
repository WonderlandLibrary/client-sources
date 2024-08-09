/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FireBlock
extends AbstractFireBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty UP = SixWayBlock.UP;
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter(FireBlock::lambda$static$0).collect(Util.toMapCollector());
    private static final VoxelShape FIRE_SHAPE_UP = Block.makeCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape FIRE_SHAPE_WEST = Block.makeCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape FIRE_SHAPE_EAST = Block.makeCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape FIRE_SHAPE_NORTH = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape FIRE_SHAPE_SOUTH = Block.makeCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Object2IntMap<Block> encouragements = new Object2IntOpenHashMap<Block>();
    private final Object2IntMap<Block> flammabilities = new Object2IntOpenHashMap<Block>();

    public FireBlock(AbstractBlock.Properties properties) {
        super(properties, 1.0f);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(UP, false));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateContainer.getValidStates().stream().filter(FireBlock::lambda$new$1).collect(Collectors.toMap(Function.identity(), FireBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState blockState) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (blockState.get(UP).booleanValue()) {
            voxelShape = FIRE_SHAPE_UP;
        }
        if (blockState.get(NORTH).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, FIRE_SHAPE_NORTH);
        }
        if (blockState.get(SOUTH).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, FIRE_SHAPE_SOUTH);
        }
        if (blockState.get(EAST).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, FIRE_SHAPE_EAST);
        }
        if (blockState.get(WEST).booleanValue()) {
            voxelShape = VoxelShapes.or(voxelShape, FIRE_SHAPE_WEST);
        }
        return voxelShape.isEmpty() ? shapeDown : voxelShape;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return this.isValidPosition(blockState, iWorld, blockPos) ? this.getFireWithAge(iWorld, blockPos, blockState.get(AGE)) : Blocks.AIR.getDefaultState();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.stateToShapeMap.get(blockState.with(AGE, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.getStateForPlacement(blockItemUseContext.getWorld(), blockItemUseContext.getPos());
    }

    protected BlockState getStateForPlacement(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = iBlockReader.getBlockState(blockPos2);
        if (!this.canBurn(blockState) && !blockState.isSolidSide(iBlockReader, blockPos2, Direction.UP)) {
            BlockState blockState2 = this.getDefaultState();
            for (Direction direction : Direction.values()) {
                BooleanProperty booleanProperty = FACING_TO_PROPERTY_MAP.get(direction);
                if (booleanProperty == null) continue;
                blockState2 = (BlockState)blockState2.with(booleanProperty, this.canBurn(iBlockReader.getBlockState(blockPos.offset(direction))));
            }
            return blockState2;
        }
        return this.getDefaultState();
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        return iWorldReader.getBlockState(blockPos2).isSolidSide(iWorldReader, blockPos2, Direction.UP) || this.areNeighborsFlammable(iWorldReader, blockPos);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        serverWorld.getPendingBlockTicks().scheduleTick(blockPos, this, FireBlock.getTickCooldown(serverWorld.rand));
        if (serverWorld.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            if (!blockState.isValidPosition(serverWorld, blockPos)) {
                serverWorld.removeBlock(blockPos, true);
            }
            BlockState blockState2 = serverWorld.getBlockState(blockPos.down());
            boolean bl = blockState2.isIn(serverWorld.getDimensionType().isInfiniBurn());
            int n = blockState.get(AGE);
            if (!bl && serverWorld.isRaining() && this.canDie(serverWorld, blockPos) && random2.nextFloat() < 0.2f + (float)n * 0.03f) {
                serverWorld.removeBlock(blockPos, true);
            } else {
                boolean bl2;
                int n2 = Math.min(15, n + random2.nextInt(3) / 2);
                if (n != n2) {
                    blockState = (BlockState)blockState.with(AGE, n2);
                    serverWorld.setBlockState(blockPos, blockState, 1);
                }
                if (!bl) {
                    if (!this.areNeighborsFlammable(serverWorld, blockPos)) {
                        BlockPos blockPos2 = blockPos.down();
                        if (!serverWorld.getBlockState(blockPos2).isSolidSide(serverWorld, blockPos2, Direction.UP) || n > 3) {
                            serverWorld.removeBlock(blockPos, true);
                        }
                        return;
                    }
                    if (n == 15 && random2.nextInt(4) == 0 && !this.canBurn(serverWorld.getBlockState(blockPos.down()))) {
                        serverWorld.removeBlock(blockPos, true);
                        return;
                    }
                }
                int n3 = (bl2 = serverWorld.isBlockinHighHumidity(blockPos)) ? -50 : 0;
                this.catchOnFire(serverWorld, blockPos.east(), 300 + n3, random2, n);
                this.catchOnFire(serverWorld, blockPos.west(), 300 + n3, random2, n);
                this.catchOnFire(serverWorld, blockPos.down(), 250 + n3, random2, n);
                this.catchOnFire(serverWorld, blockPos.up(), 250 + n3, random2, n);
                this.catchOnFire(serverWorld, blockPos.north(), 300 + n3, random2, n);
                this.catchOnFire(serverWorld, blockPos.south(), 300 + n3, random2, n);
                BlockPos.Mutable mutable = new BlockPos.Mutable();
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        for (int k = -1; k <= 4; ++k) {
                            if (i == 0 && k == 0 && j == 0) continue;
                            int n4 = 100;
                            if (k > 1) {
                                n4 += (k - 1) * 100;
                            }
                            mutable.setAndOffset(blockPos, i, k, j);
                            int n5 = this.getNeighborEncouragement(serverWorld, mutable);
                            if (n5 <= 0) continue;
                            int n6 = (n5 + 40 + serverWorld.getDifficulty().getId() * 7) / (n + 30);
                            if (bl2) {
                                n6 /= 2;
                            }
                            if (n6 <= 0 || random2.nextInt(n4) > n6 || serverWorld.isRaining() && this.canDie(serverWorld, mutable)) continue;
                            int n7 = Math.min(15, n + random2.nextInt(5) / 4);
                            serverWorld.setBlockState(mutable, this.getFireWithAge(serverWorld, mutable, n7), 0);
                        }
                    }
                }
            }
        }
    }

    protected boolean canDie(World world, BlockPos blockPos) {
        return world.isRainingAt(blockPos) || world.isRainingAt(blockPos.west()) || world.isRainingAt(blockPos.east()) || world.isRainingAt(blockPos.north()) || world.isRainingAt(blockPos.south());
    }

    private int getFlammability(BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.get(BlockStateProperties.WATERLOGGED) != false ? 0 : this.flammabilities.getInt(blockState.getBlock());
    }

    private int getFireSpreadSpeed(BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.get(BlockStateProperties.WATERLOGGED) != false ? 0 : this.encouragements.getInt(blockState.getBlock());
    }

    private void catchOnFire(World world, BlockPos blockPos, int n, Random random2, int n2) {
        int n3 = this.getFlammability(world.getBlockState(blockPos));
        if (random2.nextInt(n) < n3) {
            BlockState blockState = world.getBlockState(blockPos);
            if (random2.nextInt(n2 + 10) < 5 && !world.isRainingAt(blockPos)) {
                int n4 = Math.min(n2 + random2.nextInt(5) / 4, 15);
                world.setBlockState(blockPos, this.getFireWithAge(world, blockPos, n4), 0);
            } else {
                world.removeBlock(blockPos, true);
            }
            Block block = blockState.getBlock();
            if (block instanceof TNTBlock) {
                TNTBlock tNTBlock = (TNTBlock)block;
                TNTBlock.explode(world, blockPos);
            }
        }
    }

    private BlockState getFireWithAge(IWorld iWorld, BlockPos blockPos, int n) {
        BlockState blockState = FireBlock.getFireForPlacement(iWorld, blockPos);
        return blockState.isIn(Blocks.FIRE) ? (BlockState)blockState.with(AGE, n) : blockState;
    }

    private boolean areNeighborsFlammable(IBlockReader iBlockReader, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            if (!this.canBurn(iBlockReader.getBlockState(blockPos.offset(direction)))) continue;
            return false;
        }
        return true;
    }

    private int getNeighborEncouragement(IWorldReader iWorldReader, BlockPos blockPos) {
        if (!iWorldReader.isAirBlock(blockPos)) {
            return 1;
        }
        int n = 0;
        for (Direction direction : Direction.values()) {
            BlockState blockState = iWorldReader.getBlockState(blockPos.offset(direction));
            n = Math.max(this.getFireSpreadSpeed(blockState), n);
        }
        return n;
    }

    @Override
    protected boolean canBurn(BlockState blockState) {
        return this.getFireSpreadSpeed(blockState) > 0;
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onBlockAdded(blockState, world, blockPos, blockState2, bl);
        world.getPendingBlockTicks().scheduleTick(blockPos, this, FireBlock.getTickCooldown(world.rand));
    }

    private static int getTickCooldown(Random random2) {
        return 30 + random2.nextInt(10);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    private void setFireInfo(Block block, int n, int n2) {
        this.encouragements.put(block, n);
        this.flammabilities.put(block, n2);
    }

    public static void init() {
        FireBlock fireBlock = (FireBlock)Blocks.FIRE;
        fireBlock.setFireInfo(Blocks.OAK_PLANKS, 5, 20);
        fireBlock.setFireInfo(Blocks.SPRUCE_PLANKS, 5, 20);
        fireBlock.setFireInfo(Blocks.BIRCH_PLANKS, 5, 20);
        fireBlock.setFireInfo(Blocks.JUNGLE_PLANKS, 5, 20);
        fireBlock.setFireInfo(Blocks.ACACIA_PLANKS, 5, 20);
        fireBlock.setFireInfo(Blocks.DARK_OAK_PLANKS, 5, 20);
        fireBlock.setFireInfo(Blocks.OAK_SLAB, 5, 20);
        fireBlock.setFireInfo(Blocks.SPRUCE_SLAB, 5, 20);
        fireBlock.setFireInfo(Blocks.BIRCH_SLAB, 5, 20);
        fireBlock.setFireInfo(Blocks.JUNGLE_SLAB, 5, 20);
        fireBlock.setFireInfo(Blocks.ACACIA_SLAB, 5, 20);
        fireBlock.setFireInfo(Blocks.DARK_OAK_SLAB, 5, 20);
        fireBlock.setFireInfo(Blocks.OAK_FENCE_GATE, 5, 20);
        fireBlock.setFireInfo(Blocks.SPRUCE_FENCE_GATE, 5, 20);
        fireBlock.setFireInfo(Blocks.BIRCH_FENCE_GATE, 5, 20);
        fireBlock.setFireInfo(Blocks.JUNGLE_FENCE_GATE, 5, 20);
        fireBlock.setFireInfo(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
        fireBlock.setFireInfo(Blocks.ACACIA_FENCE_GATE, 5, 20);
        fireBlock.setFireInfo(Blocks.OAK_FENCE, 5, 20);
        fireBlock.setFireInfo(Blocks.SPRUCE_FENCE, 5, 20);
        fireBlock.setFireInfo(Blocks.BIRCH_FENCE, 5, 20);
        fireBlock.setFireInfo(Blocks.JUNGLE_FENCE, 5, 20);
        fireBlock.setFireInfo(Blocks.DARK_OAK_FENCE, 5, 20);
        fireBlock.setFireInfo(Blocks.ACACIA_FENCE, 5, 20);
        fireBlock.setFireInfo(Blocks.OAK_STAIRS, 5, 20);
        fireBlock.setFireInfo(Blocks.BIRCH_STAIRS, 5, 20);
        fireBlock.setFireInfo(Blocks.SPRUCE_STAIRS, 5, 20);
        fireBlock.setFireInfo(Blocks.JUNGLE_STAIRS, 5, 20);
        fireBlock.setFireInfo(Blocks.ACACIA_STAIRS, 5, 20);
        fireBlock.setFireInfo(Blocks.DARK_OAK_STAIRS, 5, 20);
        fireBlock.setFireInfo(Blocks.OAK_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.SPRUCE_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.BIRCH_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.JUNGLE_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.ACACIA_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.DARK_OAK_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_OAK_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_OAK_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.OAK_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.SPRUCE_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.BIRCH_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.JUNGLE_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.ACACIA_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.DARK_OAK_WOOD, 5, 5);
        fireBlock.setFireInfo(Blocks.OAK_LEAVES, 30, 60);
        fireBlock.setFireInfo(Blocks.SPRUCE_LEAVES, 30, 60);
        fireBlock.setFireInfo(Blocks.BIRCH_LEAVES, 30, 60);
        fireBlock.setFireInfo(Blocks.JUNGLE_LEAVES, 30, 60);
        fireBlock.setFireInfo(Blocks.ACACIA_LEAVES, 30, 60);
        fireBlock.setFireInfo(Blocks.DARK_OAK_LEAVES, 30, 60);
        fireBlock.setFireInfo(Blocks.BOOKSHELF, 30, 20);
        fireBlock.setFireInfo(Blocks.TNT, 15, 100);
        fireBlock.setFireInfo(Blocks.GRASS, 60, 100);
        fireBlock.setFireInfo(Blocks.FERN, 60, 100);
        fireBlock.setFireInfo(Blocks.DEAD_BUSH, 60, 100);
        fireBlock.setFireInfo(Blocks.SUNFLOWER, 60, 100);
        fireBlock.setFireInfo(Blocks.LILAC, 60, 100);
        fireBlock.setFireInfo(Blocks.ROSE_BUSH, 60, 100);
        fireBlock.setFireInfo(Blocks.PEONY, 60, 100);
        fireBlock.setFireInfo(Blocks.TALL_GRASS, 60, 100);
        fireBlock.setFireInfo(Blocks.LARGE_FERN, 60, 100);
        fireBlock.setFireInfo(Blocks.DANDELION, 60, 100);
        fireBlock.setFireInfo(Blocks.POPPY, 60, 100);
        fireBlock.setFireInfo(Blocks.BLUE_ORCHID, 60, 100);
        fireBlock.setFireInfo(Blocks.ALLIUM, 60, 100);
        fireBlock.setFireInfo(Blocks.AZURE_BLUET, 60, 100);
        fireBlock.setFireInfo(Blocks.RED_TULIP, 60, 100);
        fireBlock.setFireInfo(Blocks.ORANGE_TULIP, 60, 100);
        fireBlock.setFireInfo(Blocks.WHITE_TULIP, 60, 100);
        fireBlock.setFireInfo(Blocks.PINK_TULIP, 60, 100);
        fireBlock.setFireInfo(Blocks.OXEYE_DAISY, 60, 100);
        fireBlock.setFireInfo(Blocks.CORNFLOWER, 60, 100);
        fireBlock.setFireInfo(Blocks.LILY_OF_THE_VALLEY, 60, 100);
        fireBlock.setFireInfo(Blocks.WITHER_ROSE, 60, 100);
        fireBlock.setFireInfo(Blocks.WHITE_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.ORANGE_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.MAGENTA_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.LIGHT_BLUE_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.YELLOW_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.LIME_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.PINK_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.GRAY_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.LIGHT_GRAY_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.CYAN_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.PURPLE_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.BLUE_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.BROWN_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.GREEN_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.RED_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.BLACK_WOOL, 30, 60);
        fireBlock.setFireInfo(Blocks.VINE, 15, 100);
        fireBlock.setFireInfo(Blocks.COAL_BLOCK, 5, 5);
        fireBlock.setFireInfo(Blocks.HAY_BLOCK, 60, 20);
        fireBlock.setFireInfo(Blocks.TARGET, 15, 20);
        fireBlock.setFireInfo(Blocks.WHITE_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.ORANGE_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.MAGENTA_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.LIGHT_BLUE_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.YELLOW_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.LIME_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.PINK_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.GRAY_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.LIGHT_GRAY_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.CYAN_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.PURPLE_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.BLUE_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.BROWN_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.GREEN_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.RED_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.BLACK_CARPET, 60, 20);
        fireBlock.setFireInfo(Blocks.DRIED_KELP_BLOCK, 30, 60);
        fireBlock.setFireInfo(Blocks.BAMBOO, 60, 60);
        fireBlock.setFireInfo(Blocks.SCAFFOLDING, 60, 60);
        fireBlock.setFireInfo(Blocks.LECTERN, 30, 20);
        fireBlock.setFireInfo(Blocks.COMPOSTER, 5, 20);
        fireBlock.setFireInfo(Blocks.SWEET_BERRY_BUSH, 60, 100);
        fireBlock.setFireInfo(Blocks.BEEHIVE, 5, 20);
        fireBlock.setFireInfo(Blocks.BEE_NEST, 30, 20);
    }

    private static boolean lambda$new$1(BlockState blockState) {
        return blockState.get(AGE) == 0;
    }

    private static boolean lambda$static$0(Map.Entry entry) {
        return entry.getKey() != Direction.DOWN;
    }
}

