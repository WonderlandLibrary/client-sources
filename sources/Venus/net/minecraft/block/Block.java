/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import mpp.venusfr.events.BreakEvent;
import mpp.venusfr.events.PlaceObsidianEvent;
import mpp.venusfr.venusfr;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockVoxelShape;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block
extends AbstractBlock
implements IItemProvider {
    protected static final Logger LOGGER = LogManager.getLogger();
    public static final ObjectIntIdentityMap<BlockState> BLOCK_STATE_IDS = new ObjectIntIdentityMap();
    private static final LoadingCache<VoxelShape, Boolean> OPAQUE_CACHE = CacheBuilder.newBuilder().maximumSize(512L).weakKeys().build(new CacheLoader<VoxelShape, Boolean>(){

        @Override
        public Boolean load(VoxelShape voxelShape) {
            return !VoxelShapes.compare(VoxelShapes.fullCube(), voxelShape, IBooleanFunction.NOT_SAME);
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((VoxelShape)object);
        }
    });
    protected final StateContainer<Block, BlockState> stateContainer;
    private BlockState defaultState;
    @Nullable
    private String translationKey;
    @Nullable
    private Item item;
    private static final ThreadLocal<Object2ByteLinkedOpenHashMap<RenderSideCacheKey>> SHOULD_SIDE_RENDER_CACHE = ThreadLocal.withInitial(Block::lambda$static$0);
    PlaceObsidianEvent placeObsidianEvent = new PlaceObsidianEvent(null, null);

    public static int getStateId(@Nullable BlockState blockState) {
        if (blockState == null) {
            return 1;
        }
        int n = BLOCK_STATE_IDS.getId(blockState);
        return n == -1 ? 0 : n;
    }

    public static BlockState getStateById(int n) {
        BlockState blockState = BLOCK_STATE_IDS.getByValue(n);
        return blockState == null ? Blocks.AIR.getDefaultState() : blockState;
    }

    public static Block getBlockFromItem(@Nullable Item item) {
        return item instanceof BlockItem ? ((BlockItem)item).getBlock() : Blocks.AIR;
    }

    public static BlockState nudgeEntitiesWithNewState(BlockState blockState, BlockState blockState2, World world, BlockPos blockPos) {
        VoxelShape voxelShape = VoxelShapes.combine(blockState.getCollisionShape(world, blockPos), blockState2.getCollisionShape(world, blockPos), IBooleanFunction.ONLY_SECOND).withOffset(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        for (Entity entity2 : world.getEntitiesWithinAABBExcludingEntity(null, voxelShape.getBoundingBox())) {
            double d = VoxelShapes.getAllowedOffset(Direction.Axis.Y, entity2.getBoundingBox().offset(0.0, 1.0, 0.0), Stream.of(voxelShape), -1.0);
            entity2.setPositionAndUpdate(entity2.getPosX(), entity2.getPosY() + 1.0 + d, entity2.getPosZ());
        }
        return blockState2;
    }

    public static VoxelShape makeCuboidShape(double d, double d2, double d3, double d4, double d5, double d6) {
        return VoxelShapes.create(d / 16.0, d2 / 16.0, d3 / 16.0, d4 / 16.0, d5 / 16.0, d6 / 16.0);
    }

    public boolean isIn(ITag<Block> iTag) {
        return iTag.contains(this);
    }

    public boolean matchesBlock(Block block) {
        return this == block;
    }

    public static BlockState getValidBlockForPosition(BlockState blockState, IWorld iWorld, BlockPos blockPos) {
        BlockState blockState2 = blockState;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : UPDATE_ORDER) {
            mutable.setAndMove(blockPos, direction);
            blockState2 = blockState2.updatePostPlacement(direction, iWorld.getBlockState(mutable), iWorld, blockPos, mutable);
        }
        return blockState2;
    }

    public static void replaceBlock(BlockState blockState, BlockState blockState2, IWorld iWorld, BlockPos blockPos, int n) {
        Block.replaceBlockState(blockState, blockState2, iWorld, blockPos, n, 512);
    }

    public static void replaceBlockState(BlockState blockState, BlockState blockState2, IWorld iWorld, BlockPos blockPos, int n, int n2) {
        if (blockState2 != blockState) {
            if (blockState2.isAir()) {
                if (!iWorld.isRemote()) {
                    iWorld.destroyBlock(blockPos, (n & 0x20) == 0, null, n2);
                }
            } else {
                iWorld.setBlockState(blockPos, blockState2, n & 0xFFFFFFDF, n2);
            }
        }
    }

    public Block(AbstractBlock.Properties properties) {
        super(properties);
        StateContainer.Builder<Block, BlockState> builder = new StateContainer.Builder<Block, BlockState>(this);
        this.fillStateContainer(builder);
        this.stateContainer = builder.func_235882_a_(Block::getDefaultState, BlockState::new);
        this.setDefaultState(this.stateContainer.getBaseState());
    }

    public static boolean cannotAttach(Block block) {
        return block instanceof LeavesBlock || block == Blocks.BARRIER || block == Blocks.CARVED_PUMPKIN || block == Blocks.JACK_O_LANTERN || block == Blocks.MELON || block == Blocks.PUMPKIN || block.isIn(BlockTags.SHULKER_BOXES);
    }

    public boolean ticksRandomly(BlockState blockState) {
        return this.ticksRandomly;
    }

    public static boolean shouldSideBeRendered(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState blockState2 = iBlockReader.getBlockState(blockPos2);
        if (blockState.isSideInvisible(blockState2, direction)) {
            return true;
        }
        if (blockState2.isSolid()) {
            RenderSideCacheKey renderSideCacheKey = new RenderSideCacheKey(blockState, blockState2, direction);
            Object2ByteLinkedOpenHashMap<RenderSideCacheKey> object2ByteLinkedOpenHashMap = SHOULD_SIDE_RENDER_CACHE.get();
            byte by = object2ByteLinkedOpenHashMap.getAndMoveToFirst(renderSideCacheKey);
            if (by != 127) {
                return by != 0;
            }
            VoxelShape voxelShape = blockState.getFaceOcclusionShape(iBlockReader, blockPos, direction);
            VoxelShape voxelShape2 = blockState2.getFaceOcclusionShape(iBlockReader, blockPos2, direction.getOpposite());
            boolean bl = VoxelShapes.compare(voxelShape, voxelShape2, IBooleanFunction.ONLY_FIRST);
            if (object2ByteLinkedOpenHashMap.size() == 2048) {
                object2ByteLinkedOpenHashMap.removeLastByte();
            }
            object2ByteLinkedOpenHashMap.putAndMoveToFirst(renderSideCacheKey, (byte)(bl ? 1 : 0));
            return bl;
        }
        return false;
    }

    public static boolean hasSolidSideOnTop(IBlockReader iBlockReader, BlockPos blockPos) {
        return iBlockReader.getBlockState(blockPos).func_242698_a(iBlockReader, blockPos, Direction.UP, BlockVoxelShape.RIGID);
    }

    public static boolean hasEnoughSolidSide(IWorldReader iWorldReader, BlockPos blockPos, Direction direction) {
        BlockState blockState = iWorldReader.getBlockState(blockPos);
        return direction == Direction.DOWN && blockState.isIn(BlockTags.UNSTABLE_BOTTOM_CENTER) ? false : blockState.func_242698_a(iWorldReader, blockPos, direction, BlockVoxelShape.CENTER);
    }

    public static boolean doesSideFillSquare(VoxelShape voxelShape, Direction direction) {
        VoxelShape voxelShape2 = voxelShape.project(direction);
        return Block.isOpaque(voxelShape2);
    }

    public static boolean isOpaque(VoxelShape voxelShape) {
        return OPAQUE_CACHE.getUnchecked(voxelShape);
    }

    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return !Block.isOpaque(blockState.getShape(iBlockReader, blockPos)) && blockState.getFluidState().isEmpty();
    }

    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
    }

    public void onPlayerDestroy(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        venusfr.getInstance().getEventBus().post(new BreakEvent());
    }

    public static List<ItemStack> getDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, @Nullable TileEntity tileEntity) {
        LootContext.Builder builder = new LootContext.Builder(serverWorld).withRandom(serverWorld.rand).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(blockPos)).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.BLOCK_ENTITY, tileEntity);
        return blockState.getDrops(builder);
    }

    public static List<ItemStack> getDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, @Nullable TileEntity tileEntity, @Nullable Entity entity2, ItemStack itemStack) {
        LootContext.Builder builder = new LootContext.Builder(serverWorld).withRandom(serverWorld.rand).withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(blockPos)).withParameter(LootParameters.TOOL, itemStack).withNullableParameter(LootParameters.THIS_ENTITY, entity2).withNullableParameter(LootParameters.BLOCK_ENTITY, tileEntity);
        return blockState.getDrops(builder);
    }

    public static void spawnDrops(BlockState blockState, World world, BlockPos blockPos) {
        if (world instanceof ServerWorld) {
            Block.getDrops(blockState, (ServerWorld)world, blockPos, null).forEach(arg_0 -> Block.lambda$spawnDrops$1(world, blockPos, arg_0));
            blockState.spawnAdditionalDrops((ServerWorld)world, blockPos, ItemStack.EMPTY);
        }
    }

    public static void spawnDrops(BlockState blockState, IWorld iWorld, BlockPos blockPos, @Nullable TileEntity tileEntity) {
        if (iWorld instanceof ServerWorld) {
            Block.getDrops(blockState, (ServerWorld)iWorld, blockPos, tileEntity).forEach(arg_0 -> Block.lambda$spawnDrops$2(iWorld, blockPos, arg_0));
            blockState.spawnAdditionalDrops((ServerWorld)iWorld, blockPos, ItemStack.EMPTY);
        }
    }

    public static void spawnDrops(BlockState blockState, World world, BlockPos blockPos, @Nullable TileEntity tileEntity, Entity entity2, ItemStack itemStack) {
        if (world instanceof ServerWorld) {
            Block.getDrops(blockState, (ServerWorld)world, blockPos, tileEntity, entity2, itemStack).forEach(arg_0 -> Block.lambda$spawnDrops$3(world, blockPos, arg_0));
            blockState.spawnAdditionalDrops((ServerWorld)world, blockPos, itemStack);
        }
    }

    public static void spawnAsEntity(World world, BlockPos blockPos, ItemStack itemStack) {
        if (!world.isRemote && !itemStack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            float f = 0.5f;
            double d = (double)(world.rand.nextFloat() * 0.5f) + 0.25;
            double d2 = (double)(world.rand.nextFloat() * 0.5f) + 0.25;
            double d3 = (double)(world.rand.nextFloat() * 0.5f) + 0.25;
            ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + d, (double)blockPos.getY() + d2, (double)blockPos.getZ() + d3, itemStack);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }
    }

    protected void dropXpOnBlockBreak(ServerWorld serverWorld, BlockPos blockPos, int n) {
        if (serverWorld.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
            while (n > 0) {
                int n2 = ExperienceOrbEntity.getXPSplit(n);
                n -= n2;
                serverWorld.addEntity(new ExperienceOrbEntity(serverWorld, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, n2));
            }
        }
    }

    public float getExplosionResistance() {
        return this.blastResistance;
    }

    public void onExplosionDestroy(World world, BlockPos blockPos, Explosion explosion) {
    }

    public void onEntityWalk(World world, BlockPos blockPos, Entity entity2) {
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.getDefaultState();
    }

    public void harvestBlock(World world, PlayerEntity playerEntity, BlockPos blockPos, BlockState blockState, @Nullable TileEntity tileEntity, ItemStack itemStack) {
        playerEntity.addStat(Stats.BLOCK_MINED.get(this));
        playerEntity.addExhaustion(0.005f);
        Block.spawnDrops(blockState, world, blockPos, tileEntity, playerEntity, itemStack);
    }

    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        if (blockState.getBlock() == Blocks.OBSIDIAN) {
            this.placeObsidianEvent.setBlock(this);
            this.placeObsidianEvent.setPos(blockPos);
            venusfr.getInstance().getEventBus().post(this.placeObsidianEvent);
        }
    }

    public boolean canSpawnInBlock() {
        return !this.material.isSolid() && !this.material.isLiquid();
    }

    public IFormattableTextComponent getTranslatedName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("block", Registry.BLOCK.getKey(this));
        }
        return this.translationKey;
    }

    public void onFallenUpon(World world, BlockPos blockPos, Entity entity2, float f) {
        entity2.onLivingFall(f, 1.0f);
    }

    public void onLanded(IBlockReader iBlockReader, Entity entity2) {
        entity2.setMotion(entity2.getMotion().mul(1.0, 0.0, 1.0));
    }

    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(this);
    }

    public void fillItemGroup(ItemGroup itemGroup, NonNullList<ItemStack> nonNullList) {
        nonNullList.add(new ItemStack(this));
    }

    public float getSlipperiness() {
        return this.slipperiness;
    }

    public float getSpeedFactor() {
        return this.speedFactor;
    }

    public float getJumpFactor() {
        return this.jumpFactor;
    }

    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        world.playEvent(playerEntity, 2001, blockPos, Block.getStateId(blockState));
        if (this.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinTasks.func_234478_a_(playerEntity, false);
        }
    }

    public void fillWithRain(World world, BlockPos blockPos) {
    }

    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    }

    public StateContainer<Block, BlockState> getStateContainer() {
        return this.stateContainer;
    }

    protected final void setDefaultState(BlockState blockState) {
        this.defaultState = blockState;
    }

    public final BlockState getDefaultState() {
        return this.defaultState;
    }

    public SoundType getSoundType(BlockState blockState) {
        return this.soundType;
    }

    @Override
    public Item asItem() {
        if (this.item == null) {
            this.item = Item.getItemFromBlock(this);
        }
        return this.item;
    }

    public boolean isVariableOpacity() {
        return this.variableOpacity;
    }

    public String toString() {
        return "Block{" + Registry.BLOCK.getKey(this) + "}";
    }

    public void addInformation(ItemStack itemStack, @Nullable IBlockReader iBlockReader, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
    }

    @Override
    protected Block getSelf() {
        return this;
    }

    private static void lambda$spawnDrops$3(World world, BlockPos blockPos, ItemStack itemStack) {
        Block.spawnAsEntity(world, blockPos, itemStack);
    }

    private static void lambda$spawnDrops$2(IWorld iWorld, BlockPos blockPos, ItemStack itemStack) {
        Block.spawnAsEntity((ServerWorld)iWorld, blockPos, itemStack);
    }

    private static void lambda$spawnDrops$1(World world, BlockPos blockPos, ItemStack itemStack) {
        Block.spawnAsEntity(world, blockPos, itemStack);
    }

    private static Object2ByteLinkedOpenHashMap lambda$static$0() {
        Object2ByteLinkedOpenHashMap<RenderSideCacheKey> object2ByteLinkedOpenHashMap = new Object2ByteLinkedOpenHashMap<RenderSideCacheKey>(2048, 0.25f){

            @Override
            protected void rehash(int n) {
            }
        };
        object2ByteLinkedOpenHashMap.defaultReturnValue((byte)127);
        return object2ByteLinkedOpenHashMap;
    }

    public static final class RenderSideCacheKey {
        private final BlockState state;
        private final BlockState adjacentState;
        private final Direction side;

        public RenderSideCacheKey(BlockState blockState, BlockState blockState2, Direction direction) {
            this.state = blockState;
            this.adjacentState = blockState2;
            this.side = direction;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof RenderSideCacheKey)) {
                return true;
            }
            RenderSideCacheKey renderSideCacheKey = (RenderSideCacheKey)object;
            return this.state == renderSideCacheKey.state && this.adjacentState == renderSideCacheKey.adjacentState && this.side == renderSideCacheKey.side;
        }

        public int hashCode() {
            int n = this.state.hashCode();
            n = 31 * n + this.adjacentState.hashCode();
            return 31 * n + this.side.hashCode();
        }
    }
}

