/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ComposterBlock
extends Block
implements ISidedInventoryProvider {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_8;
    public static final Object2FloatMap<IItemProvider> CHANCES = new Object2FloatOpenHashMap<IItemProvider>();
    private static final VoxelShape OUT_SHAPE = VoxelShapes.fullCube();
    private static final VoxelShape[] SHAPE = Util.make(new VoxelShape[9], ComposterBlock::lambda$static$0);

    public static void init() {
        CHANCES.defaultReturnValue(-1.0f);
        float f = 0.3f;
        float f2 = 0.5f;
        float f3 = 0.65f;
        float f4 = 0.85f;
        float f5 = 1.0f;
        ComposterBlock.registerCompostable(0.3f, Items.JUNGLE_LEAVES);
        ComposterBlock.registerCompostable(0.3f, Items.OAK_LEAVES);
        ComposterBlock.registerCompostable(0.3f, Items.SPRUCE_LEAVES);
        ComposterBlock.registerCompostable(0.3f, Items.DARK_OAK_LEAVES);
        ComposterBlock.registerCompostable(0.3f, Items.ACACIA_LEAVES);
        ComposterBlock.registerCompostable(0.3f, Items.BIRCH_LEAVES);
        ComposterBlock.registerCompostable(0.3f, Items.OAK_SAPLING);
        ComposterBlock.registerCompostable(0.3f, Items.SPRUCE_SAPLING);
        ComposterBlock.registerCompostable(0.3f, Items.BIRCH_SAPLING);
        ComposterBlock.registerCompostable(0.3f, Items.JUNGLE_SAPLING);
        ComposterBlock.registerCompostable(0.3f, Items.ACACIA_SAPLING);
        ComposterBlock.registerCompostable(0.3f, Items.DARK_OAK_SAPLING);
        ComposterBlock.registerCompostable(0.3f, Items.BEETROOT_SEEDS);
        ComposterBlock.registerCompostable(0.3f, Items.DRIED_KELP);
        ComposterBlock.registerCompostable(0.3f, Items.GRASS);
        ComposterBlock.registerCompostable(0.3f, Items.KELP);
        ComposterBlock.registerCompostable(0.3f, Items.MELON_SEEDS);
        ComposterBlock.registerCompostable(0.3f, Items.PUMPKIN_SEEDS);
        ComposterBlock.registerCompostable(0.3f, Items.SEAGRASS);
        ComposterBlock.registerCompostable(0.3f, Items.SWEET_BERRIES);
        ComposterBlock.registerCompostable(0.3f, Items.WHEAT_SEEDS);
        ComposterBlock.registerCompostable(0.5f, Items.DRIED_KELP_BLOCK);
        ComposterBlock.registerCompostable(0.5f, Items.TALL_GRASS);
        ComposterBlock.registerCompostable(0.5f, Items.CACTUS);
        ComposterBlock.registerCompostable(0.5f, Items.SUGAR_CANE);
        ComposterBlock.registerCompostable(0.5f, Items.VINE);
        ComposterBlock.registerCompostable(0.5f, Items.NETHER_SPROUTS);
        ComposterBlock.registerCompostable(0.5f, Items.WEEPING_VINES);
        ComposterBlock.registerCompostable(0.5f, Items.TWISTING_VINES);
        ComposterBlock.registerCompostable(0.5f, Items.MELON_SLICE);
        ComposterBlock.registerCompostable(0.65f, Items.SEA_PICKLE);
        ComposterBlock.registerCompostable(0.65f, Items.LILY_PAD);
        ComposterBlock.registerCompostable(0.65f, Items.PUMPKIN);
        ComposterBlock.registerCompostable(0.65f, Items.CARVED_PUMPKIN);
        ComposterBlock.registerCompostable(0.65f, Items.MELON);
        ComposterBlock.registerCompostable(0.65f, Items.APPLE);
        ComposterBlock.registerCompostable(0.65f, Items.BEETROOT);
        ComposterBlock.registerCompostable(0.65f, Items.CARROT);
        ComposterBlock.registerCompostable(0.65f, Items.COCOA_BEANS);
        ComposterBlock.registerCompostable(0.65f, Items.POTATO);
        ComposterBlock.registerCompostable(0.65f, Items.WHEAT);
        ComposterBlock.registerCompostable(0.65f, Items.BROWN_MUSHROOM);
        ComposterBlock.registerCompostable(0.65f, Items.RED_MUSHROOM);
        ComposterBlock.registerCompostable(0.65f, Items.MUSHROOM_STEM);
        ComposterBlock.registerCompostable(0.65f, Items.CRIMSON_FUNGUS);
        ComposterBlock.registerCompostable(0.65f, Items.WARPED_FUNGUS);
        ComposterBlock.registerCompostable(0.65f, Items.NETHER_WART);
        ComposterBlock.registerCompostable(0.65f, Items.CRIMSON_ROOTS);
        ComposterBlock.registerCompostable(0.65f, Items.WARPED_ROOTS);
        ComposterBlock.registerCompostable(0.65f, Items.SHROOMLIGHT);
        ComposterBlock.registerCompostable(0.65f, Items.DANDELION);
        ComposterBlock.registerCompostable(0.65f, Items.POPPY);
        ComposterBlock.registerCompostable(0.65f, Items.BLUE_ORCHID);
        ComposterBlock.registerCompostable(0.65f, Items.ALLIUM);
        ComposterBlock.registerCompostable(0.65f, Items.AZURE_BLUET);
        ComposterBlock.registerCompostable(0.65f, Items.RED_TULIP);
        ComposterBlock.registerCompostable(0.65f, Items.ORANGE_TULIP);
        ComposterBlock.registerCompostable(0.65f, Items.WHITE_TULIP);
        ComposterBlock.registerCompostable(0.65f, Items.PINK_TULIP);
        ComposterBlock.registerCompostable(0.65f, Items.OXEYE_DAISY);
        ComposterBlock.registerCompostable(0.65f, Items.CORNFLOWER);
        ComposterBlock.registerCompostable(0.65f, Items.LILY_OF_THE_VALLEY);
        ComposterBlock.registerCompostable(0.65f, Items.WITHER_ROSE);
        ComposterBlock.registerCompostable(0.65f, Items.FERN);
        ComposterBlock.registerCompostable(0.65f, Items.SUNFLOWER);
        ComposterBlock.registerCompostable(0.65f, Items.LILAC);
        ComposterBlock.registerCompostable(0.65f, Items.ROSE_BUSH);
        ComposterBlock.registerCompostable(0.65f, Items.PEONY);
        ComposterBlock.registerCompostable(0.65f, Items.LARGE_FERN);
        ComposterBlock.registerCompostable(0.85f, Items.HAY_BLOCK);
        ComposterBlock.registerCompostable(0.85f, Items.BROWN_MUSHROOM_BLOCK);
        ComposterBlock.registerCompostable(0.85f, Items.RED_MUSHROOM_BLOCK);
        ComposterBlock.registerCompostable(0.85f, Items.NETHER_WART_BLOCK);
        ComposterBlock.registerCompostable(0.85f, Items.WARPED_WART_BLOCK);
        ComposterBlock.registerCompostable(0.85f, Items.BREAD);
        ComposterBlock.registerCompostable(0.85f, Items.BAKED_POTATO);
        ComposterBlock.registerCompostable(0.85f, Items.COOKIE);
        ComposterBlock.registerCompostable(1.0f, Items.CAKE);
        ComposterBlock.registerCompostable(1.0f, Items.PUMPKIN_PIE);
    }

    private static void registerCompostable(float f, IItemProvider iItemProvider) {
        CHANCES.put((IItemProvider)iItemProvider.asItem(), f);
    }

    public ComposterBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(LEVEL, 0));
    }

    public static void playEvent(World world, BlockPos blockPos, boolean bl) {
        BlockState blockState = world.getBlockState(blockPos);
        world.playSound(blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), bl ? SoundEvents.BLOCK_COMPOSTER_FILL_SUCCESS : SoundEvents.BLOCK_COMPOSTER_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
        double d = blockState.getShape(world, blockPos).max(Direction.Axis.Y, 0.5, 0.5) + 0.03125;
        double d2 = 0.13125f;
        double d3 = 0.7375f;
        Random random2 = world.getRandom();
        for (int i = 0; i < 10; ++i) {
            double d4 = random2.nextGaussian() * 0.02;
            double d5 = random2.nextGaussian() * 0.02;
            double d6 = random2.nextGaussian() * 0.02;
            world.addParticle(ParticleTypes.COMPOSTER, (double)blockPos.getX() + (double)0.13125f + (double)0.7375f * (double)random2.nextFloat(), (double)blockPos.getY() + d + (double)random2.nextFloat() * (1.0 - d), (double)blockPos.getZ() + (double)0.13125f + (double)0.7375f * (double)random2.nextFloat(), d4, d5, d6);
        }
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE[blockState.get(LEVEL)];
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return OUT_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE[0];
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.get(LEVEL) == 7) {
            world.getPendingBlockTicks().scheduleTick(blockPos, blockState.getBlock(), 20);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        int n = blockState.get(LEVEL);
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (n < 8 && CHANCES.containsKey(itemStack.getItem())) {
            if (n < 7 && !world.isRemote) {
                BlockState blockState2 = ComposterBlock.attemptCompost(blockState, world, blockPos, itemStack);
                world.playEvent(1500, blockPos, blockState != blockState2 ? 1 : 0);
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        if (n == 8) {
            ComposterBlock.empty(blockState, world, blockPos);
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.PASS;
    }

    public static BlockState attemptFill(BlockState blockState, ServerWorld serverWorld, ItemStack itemStack, BlockPos blockPos) {
        int n = blockState.get(LEVEL);
        if (n < 7 && CHANCES.containsKey(itemStack.getItem())) {
            BlockState blockState2 = ComposterBlock.attemptCompost(blockState, serverWorld, blockPos, itemStack);
            itemStack.shrink(1);
            return blockState2;
        }
        return blockState;
    }

    public static BlockState empty(BlockState blockState, World world, BlockPos blockPos) {
        if (!world.isRemote) {
            float f = 0.7f;
            double d = (double)(world.rand.nextFloat() * 0.7f) + (double)0.15f;
            double d2 = (double)(world.rand.nextFloat() * 0.7f) + 0.06000000238418579 + 0.6;
            double d3 = (double)(world.rand.nextFloat() * 0.7f) + (double)0.15f;
            ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + d, (double)blockPos.getY() + d2, (double)blockPos.getZ() + d3, new ItemStack(Items.BONE_MEAL));
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }
        BlockState blockState2 = ComposterBlock.resetFillState(blockState, world, blockPos);
        world.playSound(null, blockPos, SoundEvents.BLOCK_COMPOSTER_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return blockState2;
    }

    private static BlockState resetFillState(BlockState blockState, IWorld iWorld, BlockPos blockPos) {
        BlockState blockState2 = (BlockState)blockState.with(LEVEL, 0);
        iWorld.setBlockState(blockPos, blockState2, 3);
        return blockState2;
    }

    private static BlockState attemptCompost(BlockState blockState, IWorld iWorld, BlockPos blockPos, ItemStack itemStack) {
        int n = blockState.get(LEVEL);
        float f = CHANCES.getFloat(itemStack.getItem());
        if (!(n == 0 && f > 0.0f || iWorld.getRandom().nextDouble() < (double)f)) {
            return blockState;
        }
        int n2 = n + 1;
        BlockState blockState2 = (BlockState)blockState.with(LEVEL, n2);
        iWorld.setBlockState(blockPos, blockState2, 3);
        if (n2 == 7) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, blockState.getBlock(), 20);
        }
        return blockState2;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(LEVEL) == 7) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(LEVEL), 0);
            serverWorld.playSound(null, blockPos, SoundEvents.BLOCK_COMPOSTER_READY, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return blockState.get(LEVEL);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    @Override
    public ISidedInventory createInventory(BlockState blockState, IWorld iWorld, BlockPos blockPos) {
        int n = blockState.get(LEVEL);
        if (n == 8) {
            return new FullInventory(blockState, iWorld, blockPos, new ItemStack(Items.BONE_MEAL));
        }
        return (ISidedInventory)((Object)(n < 7 ? new PartialInventory(blockState, iWorld, blockPos) : new EmptyInventory()));
    }

    private static void lambda$static$0(VoxelShape[] voxelShapeArray) {
        for (int i = 0; i < 8; ++i) {
            voxelShapeArray[i] = VoxelShapes.combineAndSimplify(OUT_SHAPE, Block.makeCuboidShape(2.0, Math.max(2, 1 + i * 2), 2.0, 14.0, 16.0, 14.0), IBooleanFunction.ONLY_FIRST);
        }
        voxelShapeArray[8] = voxelShapeArray[0];
    }

    static class FullInventory
    extends Inventory
    implements ISidedInventory {
        private final BlockState state;
        private final IWorld world;
        private final BlockPos pos;
        private boolean extracted;

        public FullInventory(BlockState blockState, IWorld iWorld, BlockPos blockPos, ItemStack itemStack) {
            super(itemStack);
            this.state = blockState;
            this.world = iWorld;
            this.pos = blockPos;
        }

        @Override
        public int getInventoryStackLimit() {
            return 0;
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            int[] nArray;
            if (direction == Direction.DOWN) {
                int[] nArray2 = new int[1];
                nArray = nArray2;
                nArray2[0] = 0;
            } else {
                nArray = new int[]{};
            }
            return nArray;
        }

        @Override
        public boolean canInsertItem(int n, ItemStack itemStack, @Nullable Direction direction) {
            return true;
        }

        @Override
        public boolean canExtractItem(int n, ItemStack itemStack, Direction direction) {
            return !this.extracted && direction == Direction.DOWN && itemStack.getItem() == Items.BONE_MEAL;
        }

        @Override
        public void markDirty() {
            ComposterBlock.resetFillState(this.state, this.world, this.pos);
            this.extracted = true;
        }
    }

    static class PartialInventory
    extends Inventory
    implements ISidedInventory {
        private final BlockState state;
        private final IWorld world;
        private final BlockPos pos;
        private boolean inserted;

        public PartialInventory(BlockState blockState, IWorld iWorld, BlockPos blockPos) {
            super(1);
            this.state = blockState;
            this.world = iWorld;
            this.pos = blockPos;
        }

        @Override
        public int getInventoryStackLimit() {
            return 0;
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            int[] nArray;
            if (direction == Direction.UP) {
                int[] nArray2 = new int[1];
                nArray = nArray2;
                nArray2[0] = 0;
            } else {
                nArray = new int[]{};
            }
            return nArray;
        }

        @Override
        public boolean canInsertItem(int n, ItemStack itemStack, @Nullable Direction direction) {
            return !this.inserted && direction == Direction.UP && CHANCES.containsKey(itemStack.getItem());
        }

        @Override
        public boolean canExtractItem(int n, ItemStack itemStack, Direction direction) {
            return true;
        }

        @Override
        public void markDirty() {
            ItemStack itemStack = this.getStackInSlot(0);
            if (!itemStack.isEmpty()) {
                this.inserted = true;
                BlockState blockState = ComposterBlock.attemptCompost(this.state, this.world, this.pos, itemStack);
                this.world.playEvent(1500, this.pos, blockState != this.state ? 1 : 0);
                this.removeStackFromSlot(0);
            }
        }
    }

    static class EmptyInventory
    extends Inventory
    implements ISidedInventory {
        public EmptyInventory() {
            super(0);
        }

        @Override
        public int[] getSlotsForFace(Direction direction) {
            return new int[0];
        }

        @Override
        public boolean canInsertItem(int n, ItemStack itemStack, @Nullable Direction direction) {
            return true;
        }

        @Override
        public boolean canExtractItem(int n, ItemStack itemStack, Direction direction) {
            return true;
        }
    }
}

