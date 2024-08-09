/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.AntiPush;
import mpp.venusfr.functions.impl.player.AutoTool;
import mpp.venusfr.venusfr;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.BlockVoxelShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.EmptyBlockReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class AbstractBlock {
    protected static final Direction[] UPDATE_ORDER = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP};
    protected final Material material;
    protected final boolean canCollide;
    protected final float blastResistance;
    protected final boolean ticksRandomly;
    protected final SoundType soundType;
    public final float slipperiness;
    protected final float speedFactor;
    protected final float jumpFactor;
    protected final boolean variableOpacity;
    protected final Properties properties;
    @Nullable
    protected ResourceLocation lootTable;

    public AbstractBlock(Properties properties) {
        this.material = properties.material;
        this.canCollide = properties.blocksMovement;
        this.lootTable = properties.lootTable;
        this.blastResistance = properties.resistance;
        this.ticksRandomly = properties.ticksRandomly;
        this.soundType = properties.soundType;
        this.slipperiness = properties.slipperiness;
        this.speedFactor = properties.speedFactor;
        this.jumpFactor = properties.jumpFactor;
        this.variableOpacity = properties.variableOpacity;
        this.properties = properties;
    }

    @Deprecated
    public void updateDiagonalNeighbors(BlockState blockState, IWorld iWorld, BlockPos blockPos, int n, int n2) {
    }

    @Deprecated
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        switch (1.$SwitchMap$net$minecraft$pathfinding$PathType[pathType.ordinal()]) {
            case 1: {
                return !blockState.hasOpaqueCollisionShape(iBlockReader, blockPos);
            }
            case 2: {
                return iBlockReader.getFluidState(blockPos).isTagged(FluidTags.WATER);
            }
            case 3: {
                return !blockState.hasOpaqueCollisionShape(iBlockReader, blockPos);
            }
        }
        return true;
    }

    @Deprecated
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return blockState;
    }

    @Deprecated
    public boolean isSideInvisible(BlockState blockState, BlockState blockState2, Direction direction) {
        return true;
    }

    @Deprecated
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        DebugPacketSender.func_218806_a(world, blockPos);
    }

    @Deprecated
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
    }

    @Deprecated
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (this.isTileEntityProvider() && !blockState.isIn(blockState2.getBlock())) {
            world.removeTileEntity(blockPos);
        }
    }

    @Deprecated
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        return ActionResultType.PASS;
    }

    @Deprecated
    public boolean eventReceived(BlockState blockState, World world, BlockPos blockPos, int n, int n2) {
        return true;
    }

    @Deprecated
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Deprecated
    public boolean isTransparent(BlockState blockState) {
        return true;
    }

    @Deprecated
    public boolean canProvidePower(BlockState blockState) {
        return true;
    }

    @Deprecated
    public PushReaction getPushReaction(BlockState blockState) {
        return this.material.getPushReaction();
    }

    @Deprecated
    public FluidState getFluidState(BlockState blockState) {
        return Fluids.EMPTY.getDefaultState();
    }

    @Deprecated
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return true;
    }

    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    @Deprecated
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState;
    }

    @Deprecated
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState;
    }

    @Deprecated
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        return this.material.isReplaceable() && (blockItemUseContext.getItem().isEmpty() || blockItemUseContext.getItem().getItem() != this.asItem());
    }

    @Deprecated
    public boolean isReplaceable(BlockState blockState, Fluid fluid) {
        return this.material.isReplaceable() || !this.material.isSolid();
    }

    @Deprecated
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        ResourceLocation resourceLocation = this.getLootTable();
        if (resourceLocation == LootTables.EMPTY) {
            return Collections.emptyList();
        }
        LootContext lootContext = builder.withParameter(LootParameters.BLOCK_STATE, blockState).build(LootParameterSets.BLOCK);
        ServerWorld serverWorld = lootContext.getWorld();
        LootTable lootTable = serverWorld.getServer().getLootTableManager().getLootTableFromLocation(resourceLocation);
        return lootTable.generate(lootContext);
    }

    @Deprecated
    public long getPositionRandom(BlockState blockState, BlockPos blockPos) {
        return MathHelper.getPositionRandom(blockPos);
    }

    @Deprecated
    public VoxelShape getRenderShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.getShape(iBlockReader, blockPos);
    }

    @Deprecated
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return this.getCollisionShape(blockState, iBlockReader, blockPos, ISelectionContext.dummy());
    }

    @Deprecated
    public VoxelShape getRaytraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return VoxelShapes.empty();
    }

    @Deprecated
    public int getOpacity(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        if (blockState.isOpaqueCube(iBlockReader, blockPos)) {
            return iBlockReader.getMaxLightLevel();
        }
        return blockState.propagatesSkylightDown(iBlockReader, blockPos) ? 0 : 1;
    }

    @Nullable
    @Deprecated
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        return null;
    }

    @Deprecated
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return false;
    }

    @Deprecated
    public float getAmbientOcclusionLightValue(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.hasOpaqueCollisionShape(iBlockReader, blockPos) ? 0.2f : 1.0f;
    }

    @Deprecated
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return 1;
    }

    @Deprecated
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return VoxelShapes.fullCube();
    }

    @Deprecated
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.canCollide ? blockState.getShape(iBlockReader, blockPos) : VoxelShapes.empty();
    }

    @Deprecated
    public VoxelShape getRayTraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.getCollisionShape(blockState, iBlockReader, blockPos, iSelectionContext);
    }

    @Deprecated
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.tick(blockState, serverWorld, blockPos, random2);
    }

    @Deprecated
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
    }

    @Deprecated
    public float getPlayerRelativeBlockHardness(BlockState blockState, PlayerEntity playerEntity, IBlockReader iBlockReader, BlockPos blockPos) {
        float f = blockState.getBlockHardness(iBlockReader, blockPos);
        if (f == -1.0f) {
            return 0.0f;
        }
        AutoTool autoTool = venusfr.getInstance().getFunctionRegistry().getAutoTool();
        int n = playerEntity.func_234569_d_(blockState) ? 30 : 100;
        return (autoTool.isState() && (Boolean)autoTool.silent.get() != false && autoTool.itemIndex != -1 ? playerEntity.getDigSpeed(blockState, Minecraft.getInstance().player.inventory.getStackInSlot(autoTool.itemIndex), autoTool.itemIndex) : playerEntity.getDigSpeed(blockState)) / f / (float)n;
    }

    @Deprecated
    public void spawnAdditionalDrops(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
    }

    @Deprecated
    public void onBlockClicked(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
    }

    @Deprecated
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return 1;
    }

    @Deprecated
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
    }

    @Deprecated
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return 1;
    }

    public final boolean isTileEntityProvider() {
        return this instanceof ITileEntityProvider;
    }

    public final ResourceLocation getLootTable() {
        if (this.lootTable == null) {
            ResourceLocation resourceLocation = Registry.BLOCK.getKey(this.getSelf());
            this.lootTable = new ResourceLocation(resourceLocation.getNamespace(), "blocks/" + resourceLocation.getPath());
        }
        return this.lootTable;
    }

    @Deprecated
    public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
    }

    public abstract Item asItem();

    protected abstract Block getSelf();

    public MaterialColor getMaterialColor() {
        return this.properties.blockColors.apply(this.getSelf().getDefaultState());
    }

    public static class Properties {
        private Material material;
        private Function<BlockState, MaterialColor> blockColors;
        private boolean blocksMovement = true;
        private SoundType soundType = SoundType.STONE;
        private ToIntFunction<BlockState> lightLevel = Properties::lambda$new$0;
        private float resistance;
        private float hardness;
        private boolean requiresTool;
        private boolean ticksRandomly;
        private float slipperiness = 0.6f;
        private float speedFactor = 1.0f;
        private float jumpFactor = 1.0f;
        private ResourceLocation lootTable;
        private boolean isSolid = true;
        private boolean isAir;
        private IExtendedPositionPredicate<EntityType<?>> allowsSpawn = Properties::lambda$new$1;
        private IPositionPredicate isOpaque = Properties::lambda$new$2;
        private IPositionPredicate suffocates;
        private IPositionPredicate blocksVision = this.suffocates = this::lambda$new$3;
        private IPositionPredicate needsPostProcessing = Properties::lambda$new$4;
        private IPositionPredicate emmissiveRendering = Properties::lambda$new$5;
        private boolean variableOpacity;

        private Properties(Material material, MaterialColor materialColor) {
            this(material, arg_0 -> Properties.lambda$new$6(materialColor, arg_0));
        }

        private Properties(Material material, Function<BlockState, MaterialColor> function) {
            this.material = material;
            this.blockColors = function;
        }

        public static Properties create(Material material) {
            return Properties.create(material, material.getColor());
        }

        public static Properties create(Material material, DyeColor dyeColor) {
            return Properties.create(material, dyeColor.getMapColor());
        }

        public static Properties create(Material material, MaterialColor materialColor) {
            return new Properties(material, materialColor);
        }

        public static Properties create(Material material, Function<BlockState, MaterialColor> function) {
            return new Properties(material, function);
        }

        public static Properties from(AbstractBlock abstractBlock) {
            Properties properties = new Properties(abstractBlock.material, abstractBlock.properties.blockColors);
            properties.material = abstractBlock.properties.material;
            properties.hardness = abstractBlock.properties.hardness;
            properties.resistance = abstractBlock.properties.resistance;
            properties.blocksMovement = abstractBlock.properties.blocksMovement;
            properties.ticksRandomly = abstractBlock.properties.ticksRandomly;
            properties.lightLevel = abstractBlock.properties.lightLevel;
            properties.blockColors = abstractBlock.properties.blockColors;
            properties.soundType = abstractBlock.properties.soundType;
            properties.slipperiness = abstractBlock.properties.slipperiness;
            properties.speedFactor = abstractBlock.properties.speedFactor;
            properties.variableOpacity = abstractBlock.properties.variableOpacity;
            properties.isSolid = abstractBlock.properties.isSolid;
            properties.isAir = abstractBlock.properties.isAir;
            properties.requiresTool = abstractBlock.properties.requiresTool;
            return properties;
        }

        public Properties doesNotBlockMovement() {
            this.blocksMovement = false;
            this.isSolid = false;
            return this;
        }

        public Properties notSolid() {
            this.isSolid = false;
            return this;
        }

        public Properties slipperiness(float f) {
            this.slipperiness = f;
            return this;
        }

        public Properties speedFactor(float f) {
            this.speedFactor = f;
            return this;
        }

        public Properties jumpFactor(float f) {
            this.jumpFactor = f;
            return this;
        }

        public Properties sound(SoundType soundType) {
            this.soundType = soundType;
            return this;
        }

        public Properties setLightLevel(ToIntFunction<BlockState> toIntFunction) {
            this.lightLevel = toIntFunction;
            return this;
        }

        public Properties hardnessAndResistance(float f, float f2) {
            this.hardness = f;
            this.resistance = Math.max(0.0f, f2);
            return this;
        }

        public Properties zeroHardnessAndResistance() {
            return this.hardnessAndResistance(0.0f);
        }

        public Properties hardnessAndResistance(float f) {
            this.hardnessAndResistance(f, f);
            return this;
        }

        public Properties tickRandomly() {
            this.ticksRandomly = true;
            return this;
        }

        public Properties variableOpacity() {
            this.variableOpacity = true;
            return this;
        }

        public Properties noDrops() {
            this.lootTable = LootTables.EMPTY;
            return this;
        }

        public Properties lootFrom(Block block) {
            this.lootTable = block.getLootTable();
            return this;
        }

        public Properties setAir() {
            this.isAir = true;
            return this;
        }

        public Properties setAllowsSpawn(IExtendedPositionPredicate<EntityType<?>> iExtendedPositionPredicate) {
            this.allowsSpawn = iExtendedPositionPredicate;
            return this;
        }

        public Properties setOpaque(IPositionPredicate iPositionPredicate) {
            this.isOpaque = iPositionPredicate;
            return this;
        }

        public Properties setSuffocates(IPositionPredicate iPositionPredicate) {
            this.suffocates = iPositionPredicate;
            return this;
        }

        public Properties setBlocksVision(IPositionPredicate iPositionPredicate) {
            this.blocksVision = iPositionPredicate;
            return this;
        }

        public Properties setNeedsPostProcessing(IPositionPredicate iPositionPredicate) {
            this.needsPostProcessing = iPositionPredicate;
            return this;
        }

        public Properties setEmmisiveRendering(IPositionPredicate iPositionPredicate) {
            this.emmissiveRendering = iPositionPredicate;
            return this;
        }

        public Properties setRequiresTool() {
            this.requiresTool = true;
            return this;
        }

        private static MaterialColor lambda$new$6(MaterialColor materialColor, BlockState blockState) {
            return materialColor;
        }

        private static boolean lambda$new$5(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
            return true;
        }

        private static boolean lambda$new$4(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
            return true;
        }

        private boolean lambda$new$3(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
            return this.material.blocksMovement() && blockState.hasOpaqueCollisionShape(iBlockReader, blockPos);
        }

        private static boolean lambda$new$2(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
            return blockState.getMaterial().isOpaque() && blockState.hasOpaqueCollisionShape(iBlockReader, blockPos);
        }

        private static boolean lambda$new$1(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType entityType) {
            return blockState.isSolidSide(iBlockReader, blockPos, Direction.UP) && blockState.getLightValue() < 14;
        }

        private static int lambda$new$0(BlockState blockState) {
            return 1;
        }
    }

    public static enum OffsetType {
        NONE,
        XZ,
        XYZ;

    }

    public static interface IPositionPredicate {
        public boolean test(BlockState var1, IBlockReader var2, BlockPos var3);
    }

    public static interface IExtendedPositionPredicate<A> {
        public boolean test(BlockState var1, IBlockReader var2, BlockPos var3, A var4);
    }

    public static abstract class AbstractBlockState
    extends StateHolder<Block, BlockState> {
        private final int lightLevel;
        private final boolean transparent;
        private final boolean isAir;
        private final Material material;
        private final MaterialColor materialColor;
        private final float hardness;
        private final boolean requiresTool;
        private final boolean isSolid;
        private final IPositionPredicate isNormalCube;
        private final IPositionPredicate blocksVisionChecker;
        private final IPositionPredicate blocksVision;
        private final IPositionPredicate needsPostProcessing;
        private final IPositionPredicate emissiveRendering;
        @Nullable
        protected Cache cache;

        protected AbstractBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
            super(block, immutableMap, mapCodec);
            Properties properties = block.properties;
            this.lightLevel = properties.lightLevel.applyAsInt(this.getSelf());
            this.transparent = block.isTransparent(this.getSelf());
            this.isAir = properties.isAir;
            this.material = properties.material;
            this.materialColor = properties.blockColors.apply(this.getSelf());
            this.hardness = properties.hardness;
            this.requiresTool = properties.requiresTool;
            this.isSolid = properties.isSolid;
            this.isNormalCube = properties.isOpaque;
            this.blocksVisionChecker = properties.suffocates;
            this.blocksVision = properties.blocksVision;
            this.needsPostProcessing = properties.needsPostProcessing;
            this.emissiveRendering = properties.emmissiveRendering;
        }

        public void cacheState() {
            if (!this.getBlock().isVariableOpacity()) {
                this.cache = new Cache(this.getSelf());
            }
        }

        public Block getBlock() {
            return (Block)this.instance;
        }

        public Material getMaterial() {
            return this.material;
        }

        public boolean canEntitySpawn(IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType) {
            return this.getBlock().properties.allowsSpawn.test(this.getSelf(), iBlockReader, blockPos, entityType);
        }

        public boolean propagatesSkylightDown(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.cache != null ? this.cache.propagatesSkylightDown : this.getBlock().propagatesSkylightDown(this.getSelf(), iBlockReader, blockPos);
        }

        public int getOpacity(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.cache != null ? this.cache.opacity : this.getBlock().getOpacity(this.getSelf(), iBlockReader, blockPos);
        }

        public VoxelShape getFaceOcclusionShape(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return this.cache != null && this.cache.renderShapes != null ? this.cache.renderShapes[direction.ordinal()] : VoxelShapes.getFaceShape(this.getRenderShapeTrue(iBlockReader, blockPos), direction);
        }

        public VoxelShape getRenderShapeTrue(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.getBlock().getRenderShape(this.getSelf(), iBlockReader, blockPos);
        }

        public boolean isCollisionShapeLargerThanFullBlock() {
            return this.cache == null || this.cache.isCollisionShapeLargerThanFullBlock;
        }

        public boolean isTransparent() {
            return this.transparent;
        }

        public int getLightValue() {
            return this.lightLevel;
        }

        public boolean isAir() {
            return this.isAir;
        }

        public MaterialColor getMaterialColor(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.materialColor;
        }

        public BlockState rotate(Rotation rotation) {
            return this.getBlock().rotate(this.getSelf(), rotation);
        }

        public BlockState mirror(Mirror mirror) {
            return this.getBlock().mirror(this.getSelf(), mirror);
        }

        public BlockRenderType getRenderType() {
            return this.getBlock().getRenderType(this.getSelf());
        }

        public boolean isEmissiveRendering(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.emissiveRendering.test(this.getSelf(), iBlockReader, blockPos);
        }

        public float getAmbientOcclusionLightValue(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.getBlock().getAmbientOcclusionLightValue(this.getSelf(), iBlockReader, blockPos);
        }

        public boolean isNormalCube(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.isNormalCube.test(this.getSelf(), iBlockReader, blockPos);
        }

        public boolean canProvidePower() {
            return this.getBlock().canProvidePower(this.getSelf());
        }

        public int getWeakPower(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return this.getBlock().getWeakPower(this.getSelf(), iBlockReader, blockPos, direction);
        }

        public boolean hasComparatorInputOverride() {
            return this.getBlock().hasComparatorInputOverride(this.getSelf());
        }

        public int getComparatorInputOverride(World world, BlockPos blockPos) {
            return this.getBlock().getComparatorInputOverride(this.getSelf(), world, blockPos);
        }

        public float getBlockHardness(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.hardness;
        }

        public float getPlayerRelativeBlockHardness(PlayerEntity playerEntity, IBlockReader iBlockReader, BlockPos blockPos) {
            return this.getBlock().getPlayerRelativeBlockHardness(this.getSelf(), playerEntity, iBlockReader, blockPos);
        }

        public int getStrongPower(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return this.getBlock().getStrongPower(this.getSelf(), iBlockReader, blockPos, direction);
        }

        public PushReaction getPushReaction() {
            return this.getBlock().getPushReaction(this.getSelf());
        }

        public boolean isOpaqueCube(IBlockReader iBlockReader, BlockPos blockPos) {
            if (this.cache != null) {
                return this.cache.opaqueCube;
            }
            BlockState blockState = this.getSelf();
            return blockState.isSolid() ? Block.isOpaque(blockState.getRenderShapeTrue(iBlockReader, blockPos)) : false;
        }

        public boolean isSolid() {
            return this.isSolid;
        }

        public boolean isSideInvisible(BlockState blockState, Direction direction) {
            return this.getBlock().isSideInvisible(this.getSelf(), blockState, direction);
        }

        public VoxelShape getShape(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.getShape(iBlockReader, blockPos, ISelectionContext.dummy());
        }

        public VoxelShape getShape(IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
            return this.getBlock().getShape(this.getSelf(), iBlockReader, blockPos, iSelectionContext);
        }

        public VoxelShape getCollisionShape(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.cache != null ? this.cache.collisionShape : this.getCollisionShape(iBlockReader, blockPos, ISelectionContext.dummy());
        }

        public VoxelShape getCollisionShape(IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
            return this.getBlock().getCollisionShape(this.getSelf(), iBlockReader, blockPos, iSelectionContext);
        }

        public VoxelShape getRenderShape(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.getBlock().getCollisionShape(this.getSelf(), iBlockReader, blockPos);
        }

        public VoxelShape getRaytraceShape(IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
            return this.getBlock().getRayTraceShape(this.getSelf(), iBlockReader, blockPos, iSelectionContext);
        }

        public VoxelShape getRayTraceShape(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.getBlock().getRaytraceShape(this.getSelf(), iBlockReader, blockPos);
        }

        public final boolean canSpawnMobs(IBlockReader iBlockReader, BlockPos blockPos, Entity entity2) {
            return this.isTopSolid(iBlockReader, blockPos, entity2, Direction.UP);
        }

        public final boolean isTopSolid(IBlockReader iBlockReader, BlockPos blockPos, Entity entity2, Direction direction) {
            return Block.doesSideFillSquare(this.getCollisionShape(iBlockReader, blockPos, ISelectionContext.forEntity(entity2)), direction);
        }

        public Vector3d getOffset(IBlockReader iBlockReader, BlockPos blockPos) {
            OffsetType offsetType = this.getBlock().getOffsetType();
            if (offsetType == OffsetType.NONE) {
                return Vector3d.ZERO;
            }
            long l = MathHelper.getCoordinateRandom(blockPos.getX(), 0, blockPos.getZ());
            return new Vector3d(((double)((float)(l & 0xFL) / 15.0f) - 0.5) * 0.5, offsetType == OffsetType.XYZ ? ((double)((float)(l >> 4 & 0xFL) / 15.0f) - 1.0) * 0.2 : 0.0, ((double)((float)(l >> 8 & 0xFL) / 15.0f) - 0.5) * 0.5);
        }

        public boolean receiveBlockEvent(World world, BlockPos blockPos, int n, int n2) {
            return this.getBlock().eventReceived(this.getSelf(), world, blockPos, n, n2);
        }

        public void neighborChanged(World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
            this.getBlock().neighborChanged(this.getSelf(), world, blockPos, block, blockPos2, bl);
        }

        public final void updateNeighbours(IWorld iWorld, BlockPos blockPos, int n) {
            this.updateNeighbours(iWorld, blockPos, n, 512);
        }

        public final void updateNeighbours(IWorld iWorld, BlockPos blockPos, int n, int n2) {
            this.getBlock();
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (Direction direction : UPDATE_ORDER) {
                mutable.setAndMove(blockPos, direction);
                BlockState blockState = iWorld.getBlockState(mutable);
                BlockState blockState2 = blockState.updatePostPlacement(direction.getOpposite(), this.getSelf(), iWorld, mutable, blockPos);
                Block.replaceBlockState(blockState, blockState2, iWorld, mutable, n, n2);
            }
        }

        public final void updateDiagonalNeighbors(IWorld iWorld, BlockPos blockPos, int n) {
            this.updateDiagonalNeighbors(iWorld, blockPos, n, 512);
        }

        public void updateDiagonalNeighbors(IWorld iWorld, BlockPos blockPos, int n, int n2) {
            this.getBlock().updateDiagonalNeighbors(this.getSelf(), iWorld, blockPos, n, n2);
        }

        public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState, boolean bl) {
            this.getBlock().onBlockAdded(this.getSelf(), world, blockPos, blockState, bl);
        }

        public void onReplaced(World world, BlockPos blockPos, BlockState blockState, boolean bl) {
            this.getBlock().onReplaced(this.getSelf(), world, blockPos, blockState, bl);
        }

        public void tick(ServerWorld serverWorld, BlockPos blockPos, Random random2) {
            this.getBlock().tick(this.getSelf(), serverWorld, blockPos, random2);
        }

        public void randomTick(ServerWorld serverWorld, BlockPos blockPos, Random random2) {
            this.getBlock().randomTick(this.getSelf(), serverWorld, blockPos, random2);
        }

        public void onEntityCollision(World world, BlockPos blockPos, Entity entity2) {
            this.getBlock().onEntityCollision(this.getSelf(), world, blockPos, entity2);
        }

        public void spawnAdditionalDrops(ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
            this.getBlock().spawnAdditionalDrops(this.getSelf(), serverWorld, blockPos, itemStack);
        }

        public List<ItemStack> getDrops(LootContext.Builder builder) {
            return this.getBlock().getDrops(this.getSelf(), builder);
        }

        public ActionResultType onBlockActivated(World world, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
            return this.getBlock().onBlockActivated(this.getSelf(), world, blockRayTraceResult.getPos(), playerEntity, hand, blockRayTraceResult);
        }

        public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {
            this.getBlock().onBlockClicked(this.getSelf(), world, blockPos, playerEntity);
        }

        public boolean isSuffocating(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.blocksVisionChecker.test(this.getSelf(), iBlockReader, blockPos);
        }

        public boolean causesSuffocation(IBlockReader iBlockReader, BlockPos blockPos) {
            FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
            AntiPush antiPush = functionRegistry.getAntiPush();
            if (antiPush.isState() && ((Boolean)antiPush.getModes().getValueByName("\u0411\u043b\u043e\u043a\u0438").get()).booleanValue()) {
                return true;
            }
            return this.blocksVision.test(this.getSelf(), iBlockReader, blockPos);
        }

        public BlockState updatePostPlacement(Direction direction, BlockState blockState, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
            return this.getBlock().updatePostPlacement(this.getSelf(), direction, blockState, iWorld, blockPos, blockPos2);
        }

        public boolean allowsMovement(IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
            return this.getBlock().allowsMovement(this.getSelf(), iBlockReader, blockPos, pathType);
        }

        public boolean isReplaceable(BlockItemUseContext blockItemUseContext) {
            return this.getBlock().isReplaceable(this.getSelf(), blockItemUseContext);
        }

        public boolean isReplaceable(Fluid fluid) {
            return this.getBlock().isReplaceable(this.getSelf(), fluid);
        }

        public boolean isValidPosition(IWorldReader iWorldReader, BlockPos blockPos) {
            return this.getBlock().isValidPosition(this.getSelf(), iWorldReader, blockPos);
        }

        public boolean blockNeedsPostProcessing(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.needsPostProcessing.test(this.getSelf(), iBlockReader, blockPos);
        }

        @Nullable
        public INamedContainerProvider getContainer(World world, BlockPos blockPos) {
            return this.getBlock().getContainer(this.getSelf(), world, blockPos);
        }

        public boolean isIn(ITag<Block> iTag) {
            return this.getBlock().isIn(iTag);
        }

        public boolean isInAndMatches(ITag<Block> iTag, Predicate<AbstractBlockState> predicate) {
            return this.getBlock().isIn(iTag) && predicate.test(this);
        }

        public boolean isIn(Block block) {
            return this.getBlock().matchesBlock(block);
        }

        public FluidState getFluidState() {
            return this.getBlock().getFluidState(this.getSelf());
        }

        public boolean ticksRandomly() {
            return this.getBlock().ticksRandomly(this.getSelf());
        }

        public long getPositionRandom(BlockPos blockPos) {
            return this.getBlock().getPositionRandom(this.getSelf(), blockPos);
        }

        public SoundType getSoundType() {
            return this.getBlock().getSoundType(this.getSelf());
        }

        public void onProjectileCollision(World world, BlockState blockState, BlockRayTraceResult blockRayTraceResult, ProjectileEntity projectileEntity) {
            this.getBlock().onProjectileCollision(world, blockState, blockRayTraceResult, projectileEntity);
        }

        public boolean isSolidSide(IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
            return this.func_242698_a(iBlockReader, blockPos, direction, BlockVoxelShape.FULL);
        }

        public boolean func_242698_a(IBlockReader iBlockReader, BlockPos blockPos, Direction direction, BlockVoxelShape blockVoxelShape) {
            return this.cache != null ? this.cache.isSolidSide(direction, blockVoxelShape) : blockVoxelShape.func_241854_a(this.getSelf(), iBlockReader, blockPos, direction);
        }

        public boolean hasOpaqueCollisionShape(IBlockReader iBlockReader, BlockPos blockPos) {
            return this.cache != null ? this.cache.opaqueCollisionShape : Block.isOpaque(this.getCollisionShape(iBlockReader, blockPos));
        }

        protected abstract BlockState getSelf();

        public boolean getRequiresTool() {
            return this.requiresTool;
        }

        static final class Cache {
            private static final Direction[] DIRECTIONS = Direction.values();
            private static final int shapeValueLength = BlockVoxelShape.values().length;
            protected final boolean opaqueCube;
            private final boolean propagatesSkylightDown;
            private final int opacity;
            @Nullable
            private final VoxelShape[] renderShapes;
            protected final VoxelShape collisionShape;
            protected final boolean isCollisionShapeLargerThanFullBlock;
            private final boolean[] solidSides;
            protected final boolean opaqueCollisionShape;

            private Cache(BlockState blockState) {
                Block block = blockState.getBlock();
                this.opaqueCube = blockState.isOpaqueCube(EmptyBlockReader.INSTANCE, BlockPos.ZERO);
                this.propagatesSkylightDown = block.propagatesSkylightDown(blockState, EmptyBlockReader.INSTANCE, BlockPos.ZERO);
                this.opacity = block.getOpacity(blockState, EmptyBlockReader.INSTANCE, BlockPos.ZERO);
                if (!blockState.isSolid()) {
                    this.renderShapes = null;
                } else {
                    this.renderShapes = new VoxelShape[DIRECTIONS.length];
                    Direction[] directionArray = block.getRenderShape(blockState, EmptyBlockReader.INSTANCE, BlockPos.ZERO);
                    Direction[] directionArray2 = DIRECTIONS;
                    int n = directionArray2.length;
                    for (int i = 0; i < n; ++i) {
                        BlockVoxelShape[] blockVoxelShapeArray = directionArray2[i];
                        this.renderShapes[blockVoxelShapeArray.ordinal()] = VoxelShapes.getFaceShape((VoxelShape)directionArray, (Direction)blockVoxelShapeArray);
                    }
                }
                this.collisionShape = block.getCollisionShape(blockState, EmptyBlockReader.INSTANCE, BlockPos.ZERO, ISelectionContext.dummy());
                this.isCollisionShapeLargerThanFullBlock = Arrays.stream(Direction.Axis.values()).anyMatch(this::lambda$new$0);
                this.solidSides = new boolean[DIRECTIONS.length * shapeValueLength];
                for (Direction direction : DIRECTIONS) {
                    for (BlockVoxelShape blockVoxelShape : BlockVoxelShape.values()) {
                        this.solidSides[Cache.func_242701_b((Direction)direction, (BlockVoxelShape)blockVoxelShape)] = blockVoxelShape.func_241854_a(blockState, EmptyBlockReader.INSTANCE, BlockPos.ZERO, direction);
                    }
                }
                this.opaqueCollisionShape = Block.isOpaque(blockState.getCollisionShape(EmptyBlockReader.INSTANCE, BlockPos.ZERO));
            }

            public boolean isSolidSide(Direction direction, BlockVoxelShape blockVoxelShape) {
                return this.solidSides[Cache.func_242701_b(direction, blockVoxelShape)];
            }

            private static int func_242701_b(Direction direction, BlockVoxelShape blockVoxelShape) {
                return direction.ordinal() * shapeValueLength + blockVoxelShape.ordinal();
            }

            private boolean lambda$new$0(Direction.Axis axis) {
                return this.collisionShape.getStart(axis) < 0.0 || this.collisionShape.getEnd(axis) > 1.0;
            }
        }
    }
}

