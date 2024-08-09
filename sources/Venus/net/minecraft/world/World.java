/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.profiler.IProfiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.storage.ISpawnWorldInfo;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class World
implements IWorld,
AutoCloseable {
    protected static final Logger LOGGER = LogManager.getLogger();
    public static final Codec<RegistryKey<World>> CODEC = ResourceLocation.CODEC.xmap(RegistryKey.getKeyCreator(Registry.WORLD_KEY), RegistryKey::getLocation);
    public static final RegistryKey<World> OVERWORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("overworld"));
    public static final RegistryKey<World> THE_NETHER = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("the_nether"));
    public static final RegistryKey<World> THE_END = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation("the_end"));
    private static final Direction[] FACING_VALUES = Direction.values();
    public final List<TileEntity> loadedTileEntityList = Lists.newArrayList();
    public final List<TileEntity> tickableTileEntities = Lists.newArrayList();
    protected final List<TileEntity> addedTileEntityList = Lists.newArrayList();
    protected final List<TileEntity> tileEntitiesToBeRemoved = Lists.newArrayList();
    private final Thread mainThread;
    private final boolean isDebug;
    private int skylightSubtracted;
    protected int updateLCG = new Random().nextInt();
    protected final int DIST_HASH_MAGIC = 1013904223;
    protected float prevRainingStrength;
    protected float rainingStrength;
    protected float prevThunderingStrength;
    protected float thunderingStrength;
    public final Random rand = new Random();
    private final DimensionType dimensionType;
    protected final ISpawnWorldInfo worldInfo;
    private final Supplier<IProfiler> profiler;
    public final boolean isRemote;
    protected boolean processingLoadedTiles;
    private final WorldBorder worldBorder;
    private final BiomeManager biomeManager;
    private final RegistryKey<World> dimension;

    protected World(ISpawnWorldInfo iSpawnWorldInfo, RegistryKey<World> registryKey, DimensionType dimensionType, Supplier<IProfiler> supplier, boolean bl, boolean bl2, long l) {
        this.profiler = supplier;
        this.worldInfo = iSpawnWorldInfo;
        this.dimensionType = dimensionType;
        this.dimension = registryKey;
        this.isRemote = bl;
        this.worldBorder = dimensionType.getCoordinateScale() != 1.0 ? new WorldBorder(this, dimensionType){
            final DimensionType val$dimensionType;
            final World this$0;
            {
                this.this$0 = world;
                this.val$dimensionType = dimensionType;
            }

            @Override
            public double getCenterX() {
                return super.getCenterX() / this.val$dimensionType.getCoordinateScale();
            }

            @Override
            public double getCenterZ() {
                return super.getCenterZ() / this.val$dimensionType.getCoordinateScale();
            }
        } : new WorldBorder();
        this.mainThread = Thread.currentThread();
        this.biomeManager = new BiomeManager(this, l, dimensionType.getMagnifier());
        this.isDebug = bl2;
    }

    @Override
    public boolean isRemote() {
        return this.isRemote;
    }

    @Nullable
    public MinecraftServer getServer() {
        return null;
    }

    public static boolean isValid(BlockPos blockPos) {
        return !World.isOutsideBuildHeight(blockPos) && World.isValidXZPosition(blockPos);
    }

    public static boolean isInvalidPosition(BlockPos blockPos) {
        return !World.isInvalidYPosition(blockPos.getY()) && World.isValidXZPosition(blockPos);
    }

    private static boolean isValidXZPosition(BlockPos blockPos) {
        return blockPos.getX() >= -30000000 && blockPos.getZ() >= -30000000 && blockPos.getX() < 30000000 && blockPos.getZ() < 30000000;
    }

    private static boolean isInvalidYPosition(int n) {
        return n < -20000000 || n >= 20000000;
    }

    public static boolean isOutsideBuildHeight(BlockPos blockPos) {
        return World.isYOutOfBounds(blockPos.getY());
    }

    public static boolean isYOutOfBounds(int n) {
        return n < 0 || n >= 256;
    }

    public Chunk getChunkAt(BlockPos blockPos) {
        return this.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    @Override
    public Chunk getChunk(int n, int n2) {
        return (Chunk)this.getChunk(n, n2, ChunkStatus.FULL);
    }

    @Override
    public IChunk getChunk(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        IChunk iChunk = this.getChunkProvider().getChunk(n, n2, chunkStatus, bl);
        if (iChunk == null && bl) {
            throw new IllegalStateException("Should always be able to create a chunk!");
        }
        return iChunk;
    }

    @Override
    public boolean setBlockState(BlockPos blockPos, BlockState blockState, int n) {
        return this.setBlockState(blockPos, blockState, n, 1);
    }

    @Override
    public boolean setBlockState(BlockPos blockPos, BlockState blockState, int n, int n2) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return true;
        }
        if (!this.isRemote && this.isDebug()) {
            return true;
        }
        Chunk chunk = this.getChunkAt(blockPos);
        Block block = blockState.getBlock();
        BlockState blockState2 = chunk.setBlockState(blockPos, blockState, (n & 0x40) != 0);
        if (blockState2 == null) {
            return true;
        }
        BlockState blockState3 = this.getBlockState(blockPos);
        if ((n & 0x80) == 0 && blockState3 != blockState2 && (blockState3.getOpacity(this, blockPos) != blockState2.getOpacity(this, blockPos) || blockState3.getLightValue() != blockState2.getLightValue() || blockState3.isTransparent() || blockState2.isTransparent())) {
            this.getProfiler().startSection("queueCheckLight");
            this.getChunkProvider().getLightManager().checkBlock(blockPos);
            this.getProfiler().endSection();
        }
        if (blockState3 == blockState) {
            if (blockState2 != blockState3) {
                this.markBlockRangeForRenderUpdate(blockPos, blockState2, blockState3);
            }
            if ((n & 2) != 0 && (!this.isRemote || (n & 4) == 0) && (this.isRemote || chunk.getLocationType() != null && chunk.getLocationType().isAtLeast(ChunkHolder.LocationType.TICKING))) {
                this.notifyBlockUpdate(blockPos, blockState2, blockState, n);
            }
            if ((n & 1) != 0) {
                this.func_230547_a_(blockPos, blockState2.getBlock());
                if (!this.isRemote && blockState.hasComparatorInputOverride()) {
                    this.updateComparatorOutputLevel(blockPos, block);
                }
            }
            if ((n & 0x10) == 0 && n2 > 0) {
                int n3 = n & 0xFFFFFFDE;
                blockState2.updateDiagonalNeighbors(this, blockPos, n3, n2 - 1);
                blockState.updateNeighbours(this, blockPos, n3, n2 - 1);
                blockState.updateDiagonalNeighbors(this, blockPos, n3, n2 - 1);
            }
            this.onBlockStateChange(blockPos, blockState2, blockState3);
        }
        return false;
    }

    public void onBlockStateChange(BlockPos blockPos, BlockState blockState, BlockState blockState2) {
    }

    @Override
    public boolean removeBlock(BlockPos blockPos, boolean bl) {
        FluidState fluidState = this.getFluidState(blockPos);
        return this.setBlockState(blockPos, fluidState.getBlockState(), 3 | (bl ? 64 : 0));
    }

    @Override
    public boolean destroyBlock(BlockPos blockPos, boolean bl, @Nullable Entity entity2, int n) {
        BlockState blockState = this.getBlockState(blockPos);
        if (blockState.isAir()) {
            return true;
        }
        FluidState fluidState = this.getFluidState(blockPos);
        if (!(blockState.getBlock() instanceof AbstractFireBlock)) {
            this.playEvent(2001, blockPos, Block.getStateId(blockState));
        }
        if (bl) {
            TileEntity tileEntity = blockState.getBlock().isTileEntityProvider() ? this.getTileEntity(blockPos) : null;
            Block.spawnDrops(blockState, this, blockPos, tileEntity, entity2, ItemStack.EMPTY);
        }
        return this.setBlockState(blockPos, fluidState.getBlockState(), 3, n);
    }

    public boolean setBlockState(BlockPos blockPos, BlockState blockState) {
        return this.setBlockState(blockPos, blockState, 0);
    }

    public abstract void notifyBlockUpdate(BlockPos var1, BlockState var2, BlockState var3, int var4);

    public void markBlockRangeForRenderUpdate(BlockPos blockPos, BlockState blockState, BlockState blockState2) {
    }

    public void notifyNeighborsOfStateChange(BlockPos blockPos, Block block) {
        this.neighborChanged(blockPos.west(), block, blockPos);
        this.neighborChanged(blockPos.east(), block, blockPos);
        this.neighborChanged(blockPos.down(), block, blockPos);
        this.neighborChanged(blockPos.up(), block, blockPos);
        this.neighborChanged(blockPos.north(), block, blockPos);
        this.neighborChanged(blockPos.south(), block, blockPos);
    }

    public void notifyNeighborsOfStateExcept(BlockPos blockPos, Block block, Direction direction) {
        if (direction != Direction.WEST) {
            this.neighborChanged(blockPos.west(), block, blockPos);
        }
        if (direction != Direction.EAST) {
            this.neighborChanged(blockPos.east(), block, blockPos);
        }
        if (direction != Direction.DOWN) {
            this.neighborChanged(blockPos.down(), block, blockPos);
        }
        if (direction != Direction.UP) {
            this.neighborChanged(blockPos.up(), block, blockPos);
        }
        if (direction != Direction.NORTH) {
            this.neighborChanged(blockPos.north(), block, blockPos);
        }
        if (direction != Direction.SOUTH) {
            this.neighborChanged(blockPos.south(), block, blockPos);
        }
    }

    public void neighborChanged(BlockPos blockPos, Block block, BlockPos blockPos2) {
        if (!this.isRemote) {
            BlockState blockState = this.getBlockState(blockPos);
            try {
                blockState.neighborChanged(this, blockPos, block, blockPos2, true);
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Exception while updating neighbours");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being updated");
                crashReportCategory.addDetail("Source block type", () -> World.lambda$neighborChanged$0(block));
                CrashReportCategory.addBlockInfo(crashReportCategory, blockPos, blockState);
                throw new ReportedException(crashReport);
            }
        }
    }

    @Override
    public int getHeight(Heightmap.Type type, int n, int n2) {
        int n3 = n >= -30000000 && n2 >= -30000000 && n < 30000000 && n2 < 30000000 ? (this.chunkExists(n >> 4, n2 >> 4) ? this.getChunk(n >> 4, n2 >> 4).getTopBlockY(type, n & 0xF, n2 & 0xF) + 1 : 0) : this.getSeaLevel() + 1;
        return n3;
    }

    @Override
    public WorldLightManager getLightManager() {
        return this.getChunkProvider().getLightManager();
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return Blocks.VOID_AIR.getDefaultState();
        }
        Chunk chunk = this.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
        return chunk.getBlockState(blockPos);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return Fluids.EMPTY.getDefaultState();
        }
        Chunk chunk = this.getChunkAt(blockPos);
        return chunk.getFluidState(blockPos);
    }

    public boolean isDaytime() {
        return !this.getDimensionType().doesFixedTimeExist() && this.skylightSubtracted < 4;
    }

    public boolean isNightTime() {
        return !this.getDimensionType().doesFixedTimeExist() && !this.isDaytime();
    }

    @Override
    public void playSound(@Nullable PlayerEntity playerEntity, BlockPos blockPos, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
        this.playSound(playerEntity, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, soundEvent, soundCategory, f, f2);
    }

    public abstract void playSound(@Nullable PlayerEntity var1, double var2, double var4, double var6, SoundEvent var8, SoundCategory var9, float var10, float var11);

    public abstract void playMovingSound(@Nullable PlayerEntity var1, Entity var2, SoundEvent var3, SoundCategory var4, float var5, float var6);

    public void playSound(double d, double d2, double d3, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, boolean bl) {
    }

    @Override
    public void addParticle(IParticleData iParticleData, double d, double d2, double d3, double d4, double d5, double d6) {
    }

    public void addParticle(IParticleData iParticleData, boolean bl, double d, double d2, double d3, double d4, double d5, double d6) {
    }

    public void addOptionalParticle(IParticleData iParticleData, double d, double d2, double d3, double d4, double d5, double d6) {
    }

    public void addOptionalParticle(IParticleData iParticleData, boolean bl, double d, double d2, double d3, double d4, double d5, double d6) {
    }

    public float getCelestialAngleRadians(float f) {
        float f2 = this.func_242415_f(f);
        return f2 * ((float)Math.PI * 2);
    }

    public boolean addTileEntity(TileEntity tileEntity) {
        boolean bl;
        if (this.processingLoadedTiles) {
            org.apache.logging.log4j.util.Supplier[] supplierArray = new org.apache.logging.log4j.util.Supplier[2];
            supplierArray[0] = () -> World.lambda$addTileEntity$1(tileEntity);
            supplierArray[1] = tileEntity::getPos;
            LOGGER.error("Adding block entity while ticking: {} @ {}", supplierArray);
        }
        if ((bl = this.loadedTileEntityList.add(tileEntity)) && tileEntity instanceof ITickableTileEntity) {
            this.tickableTileEntities.add(tileEntity);
        }
        if (this.isRemote) {
            BlockPos blockPos = tileEntity.getPos();
            BlockState blockState = this.getBlockState(blockPos);
            this.notifyBlockUpdate(blockPos, blockState, blockState, 2);
        }
        return bl;
    }

    public void addTileEntities(Collection<TileEntity> collection) {
        if (this.processingLoadedTiles) {
            this.addedTileEntityList.addAll(collection);
        } else {
            for (TileEntity tileEntity : collection) {
                this.addTileEntity(tileEntity);
            }
        }
    }

    public void tickBlockEntities() {
        Object object;
        IProfiler iProfiler = this.getProfiler();
        iProfiler.startSection("blockEntities");
        if (!this.tileEntitiesToBeRemoved.isEmpty()) {
            this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
            this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
            this.tileEntitiesToBeRemoved.clear();
        }
        this.processingLoadedTiles = true;
        Iterator<TileEntity> iterator2 = this.tickableTileEntities.iterator();
        while (iterator2.hasNext()) {
            TileEntity tileEntity = iterator2.next();
            if (!tileEntity.isRemoved() && tileEntity.hasWorld()) {
                object = tileEntity.getPos();
                if (this.getChunkProvider().canTick((BlockPos)object) && this.getWorldBorder().contains((BlockPos)object)) {
                    try {
                        iProfiler.startSection(() -> World.lambda$tickBlockEntities$2(tileEntity));
                        if (tileEntity.getType().isValidBlock(this.getBlockState((BlockPos)object).getBlock())) {
                            ((ITickableTileEntity)((Object)tileEntity)).tick();
                        } else {
                            tileEntity.warnInvalidBlock();
                        }
                        iProfiler.endSection();
                    } catch (Throwable throwable) {
                        CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Ticking block entity");
                        CrashReportCategory crashReportCategory = crashReport.makeCategory("Block entity being ticked");
                        tileEntity.addInfoToCrashReport(crashReportCategory);
                        throw new ReportedException(crashReport);
                    }
                }
            }
            if (!tileEntity.isRemoved()) continue;
            iterator2.remove();
            this.loadedTileEntityList.remove(tileEntity);
            if (!this.isBlockLoaded(tileEntity.getPos())) continue;
            this.getChunkAt(tileEntity.getPos()).removeTileEntity(tileEntity.getPos());
        }
        this.processingLoadedTiles = false;
        iProfiler.endStartSection("pendingBlockEntities");
        if (!this.addedTileEntityList.isEmpty()) {
            for (int i = 0; i < this.addedTileEntityList.size(); ++i) {
                object = this.addedTileEntityList.get(i);
                if (((TileEntity)object).isRemoved()) continue;
                if (!this.loadedTileEntityList.contains(object)) {
                    this.addTileEntity((TileEntity)object);
                }
                if (!this.isBlockLoaded(((TileEntity)object).getPos())) continue;
                Chunk chunk = this.getChunkAt(((TileEntity)object).getPos());
                BlockState blockState = chunk.getBlockState(((TileEntity)object).getPos());
                chunk.addTileEntity(((TileEntity)object).getPos(), (TileEntity)object);
                this.notifyBlockUpdate(((TileEntity)object).getPos(), blockState, blockState, 3);
            }
            this.addedTileEntityList.clear();
        }
        iProfiler.endSection();
    }

    public void guardEntityTick(Consumer<Entity> consumer, Entity entity2) {
        try {
            consumer.accept(entity2);
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Ticking entity");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Entity being ticked");
            entity2.fillCrashReport(crashReportCategory);
            throw new ReportedException(crashReport);
        }
    }

    public Explosion createExplosion(@Nullable Entity entity2, double d, double d2, double d3, float f, Explosion.Mode mode) {
        return this.createExplosion(entity2, null, null, d, d2, d3, f, false, mode);
    }

    public Explosion createExplosion(@Nullable Entity entity2, double d, double d2, double d3, float f, boolean bl, Explosion.Mode mode) {
        return this.createExplosion(entity2, null, null, d, d2, d3, f, bl, mode);
    }

    public Explosion createExplosion(@Nullable Entity entity2, @Nullable DamageSource damageSource, @Nullable ExplosionContext explosionContext, double d, double d2, double d3, float f, boolean bl, Explosion.Mode mode) {
        Explosion explosion = new Explosion(this, entity2, damageSource, explosionContext, d, d2, d3, f, bl, mode);
        explosion.doExplosionA();
        explosion.doExplosionB(false);
        return explosion;
    }

    public String getProviderName() {
        return this.getChunkProvider().makeString();
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return null;
        }
        if (!this.isRemote && Thread.currentThread() != this.mainThread) {
            return null;
        }
        TileEntity tileEntity = null;
        if (this.processingLoadedTiles) {
            tileEntity = this.getPendingTileEntityAt(blockPos);
        }
        if (tileEntity == null) {
            tileEntity = this.getChunkAt(blockPos).getTileEntity(blockPos, Chunk.CreateEntityType.IMMEDIATE);
        }
        if (tileEntity == null) {
            tileEntity = this.getPendingTileEntityAt(blockPos);
        }
        return tileEntity;
    }

    @Nullable
    private TileEntity getPendingTileEntityAt(BlockPos blockPos) {
        for (int i = 0; i < this.addedTileEntityList.size(); ++i) {
            TileEntity tileEntity = this.addedTileEntityList.get(i);
            if (tileEntity.isRemoved() || !tileEntity.getPos().equals(blockPos)) continue;
            return tileEntity;
        }
        return null;
    }

    public void setTileEntity(BlockPos blockPos, @Nullable TileEntity tileEntity) {
        if (!World.isOutsideBuildHeight(blockPos) && tileEntity != null && !tileEntity.isRemoved()) {
            if (this.processingLoadedTiles) {
                tileEntity.setWorldAndPos(this, blockPos);
                Iterator<TileEntity> iterator2 = this.addedTileEntityList.iterator();
                while (iterator2.hasNext()) {
                    TileEntity tileEntity2 = iterator2.next();
                    if (!tileEntity2.getPos().equals(blockPos)) continue;
                    tileEntity2.remove();
                    iterator2.remove();
                }
                this.addedTileEntityList.add(tileEntity);
            } else {
                this.getChunkAt(blockPos).addTileEntity(blockPos, tileEntity);
                this.addTileEntity(tileEntity);
            }
        }
    }

    public void removeTileEntity(BlockPos blockPos) {
        TileEntity tileEntity = this.getTileEntity(blockPos);
        if (tileEntity != null && this.processingLoadedTiles) {
            tileEntity.remove();
            this.addedTileEntityList.remove(tileEntity);
        } else {
            if (tileEntity != null) {
                this.addedTileEntityList.remove(tileEntity);
                this.loadedTileEntityList.remove(tileEntity);
                this.tickableTileEntities.remove(tileEntity);
            }
            this.getChunkAt(blockPos).removeTileEntity(blockPos);
        }
    }

    public boolean isBlockPresent(BlockPos blockPos) {
        return World.isOutsideBuildHeight(blockPos) ? false : this.getChunkProvider().chunkExists(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public boolean isDirectionSolid(BlockPos blockPos, Entity entity2, Direction direction) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return true;
        }
        IChunk iChunk = this.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.FULL, true);
        return iChunk == null ? false : iChunk.getBlockState(blockPos).isTopSolid(this, blockPos, entity2, direction);
    }

    public boolean isTopSolid(BlockPos blockPos, Entity entity2) {
        return this.isDirectionSolid(blockPos, entity2, Direction.UP);
    }

    public void calculateInitialSkylight() {
        double d = 1.0 - (double)(this.getRainStrength(1.0f) * 5.0f) / 16.0;
        double d2 = 1.0 - (double)(this.getThunderStrength(1.0f) * 5.0f) / 16.0;
        double d3 = 0.5 + 2.0 * MathHelper.clamp((double)MathHelper.cos(this.func_242415_f(1.0f) * ((float)Math.PI * 2)), -0.25, 0.25);
        this.skylightSubtracted = (int)((1.0 - d3 * d * d2) * 11.0);
    }

    public void setAllowedSpawnTypes(boolean bl, boolean bl2) {
        this.getChunkProvider().setAllowedSpawnTypes(bl, bl2);
    }

    protected void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.getChunkProvider().close();
    }

    @Override
    @Nullable
    public IBlockReader getBlockReader(int n, int n2) {
        return this.getChunk(n, n2, ChunkStatus.FULL, true);
    }

    @Override
    public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, @Nullable Predicate<? super Entity> predicate) {
        this.getProfiler().func_230035_c_("getEntities");
        ArrayList<Entity> arrayList = Lists.newArrayList();
        int n = MathHelper.floor((axisAlignedBB.minX - 2.0) / 16.0);
        int n2 = MathHelper.floor((axisAlignedBB.maxX + 2.0) / 16.0);
        int n3 = MathHelper.floor((axisAlignedBB.minZ - 2.0) / 16.0);
        int n4 = MathHelper.floor((axisAlignedBB.maxZ + 2.0) / 16.0);
        AbstractChunkProvider abstractChunkProvider = this.getChunkProvider();
        for (int i = n; i <= n2; ++i) {
            for (int j = n3; j <= n4; ++j) {
                Chunk chunk = abstractChunkProvider.getChunk(i, j, true);
                if (chunk == null) continue;
                chunk.getEntitiesWithinAABBForEntity(entity2, axisAlignedBB, arrayList, predicate);
            }
        }
        return arrayList;
    }

    public <T extends Entity> List<T> getEntitiesWithinAABB(@Nullable EntityType<T> entityType, AxisAlignedBB axisAlignedBB, Predicate<? super T> predicate) {
        this.getProfiler().func_230035_c_("getEntities");
        int n = MathHelper.floor((axisAlignedBB.minX - 2.0) / 16.0);
        int n2 = MathHelper.ceil((axisAlignedBB.maxX + 2.0) / 16.0);
        int n3 = MathHelper.floor((axisAlignedBB.minZ - 2.0) / 16.0);
        int n4 = MathHelper.ceil((axisAlignedBB.maxZ + 2.0) / 16.0);
        ArrayList arrayList = Lists.newArrayList();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                Chunk chunk = this.getChunkProvider().getChunk(i, j, true);
                if (chunk == null) continue;
                chunk.getEntitiesWithinAABBForList(entityType, axisAlignedBB, arrayList, predicate);
            }
        }
        return arrayList;
    }

    @Override
    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB, @Nullable Predicate<? super T> predicate) {
        this.getProfiler().func_230035_c_("getEntities");
        int n = MathHelper.floor((axisAlignedBB.minX - 2.0) / 16.0);
        int n2 = MathHelper.ceil((axisAlignedBB.maxX + 2.0) / 16.0);
        int n3 = MathHelper.floor((axisAlignedBB.minZ - 2.0) / 16.0);
        int n4 = MathHelper.ceil((axisAlignedBB.maxZ + 2.0) / 16.0);
        ArrayList arrayList = Lists.newArrayList();
        AbstractChunkProvider abstractChunkProvider = this.getChunkProvider();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                Chunk chunk = abstractChunkProvider.getChunk(i, j, true);
                if (chunk == null) continue;
                chunk.getEntitiesOfTypeWithinAABB(clazz, axisAlignedBB, arrayList, predicate);
            }
        }
        return arrayList;
    }

    @Override
    public <T extends Entity> List<T> getLoadedEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB, @Nullable Predicate<? super T> predicate) {
        this.getProfiler().func_230035_c_("getLoadedEntities");
        int n = MathHelper.floor((axisAlignedBB.minX - 2.0) / 16.0);
        int n2 = MathHelper.ceil((axisAlignedBB.maxX + 2.0) / 16.0);
        int n3 = MathHelper.floor((axisAlignedBB.minZ - 2.0) / 16.0);
        int n4 = MathHelper.ceil((axisAlignedBB.maxZ + 2.0) / 16.0);
        ArrayList arrayList = Lists.newArrayList();
        AbstractChunkProvider abstractChunkProvider = this.getChunkProvider();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                Chunk chunk = abstractChunkProvider.getChunkNow(i, j);
                if (chunk == null) continue;
                chunk.getEntitiesOfTypeWithinAABB(clazz, axisAlignedBB, arrayList, predicate);
            }
        }
        return arrayList;
    }

    @Nullable
    public abstract Entity getEntityByID(int var1);

    public void markChunkDirty(BlockPos blockPos, TileEntity tileEntity) {
        if (this.isBlockLoaded(blockPos)) {
            this.getChunkAt(blockPos).markDirty();
        }
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    public int getStrongPower(BlockPos blockPos) {
        int n = 0;
        if ((n = Math.max(n, this.getStrongPower(blockPos.down(), Direction.DOWN))) >= 15) {
            return n;
        }
        if ((n = Math.max(n, this.getStrongPower(blockPos.up(), Direction.UP))) >= 15) {
            return n;
        }
        if ((n = Math.max(n, this.getStrongPower(blockPos.north(), Direction.NORTH))) >= 15) {
            return n;
        }
        if ((n = Math.max(n, this.getStrongPower(blockPos.south(), Direction.SOUTH))) >= 15) {
            return n;
        }
        if ((n = Math.max(n, this.getStrongPower(blockPos.west(), Direction.WEST))) >= 15) {
            return n;
        }
        return (n = Math.max(n, this.getStrongPower(blockPos.east(), Direction.EAST))) >= 15 ? n : n;
    }

    public boolean isSidePowered(BlockPos blockPos, Direction direction) {
        return this.getRedstonePower(blockPos, direction) > 0;
    }

    public int getRedstonePower(BlockPos blockPos, Direction direction) {
        BlockState blockState = this.getBlockState(blockPos);
        int n = blockState.getWeakPower(this, blockPos, direction);
        return blockState.isNormalCube(this, blockPos) ? Math.max(n, this.getStrongPower(blockPos)) : n;
    }

    public boolean isBlockPowered(BlockPos blockPos) {
        if (this.getRedstonePower(blockPos.down(), Direction.DOWN) > 0) {
            return false;
        }
        if (this.getRedstonePower(blockPos.up(), Direction.UP) > 0) {
            return false;
        }
        if (this.getRedstonePower(blockPos.north(), Direction.NORTH) > 0) {
            return false;
        }
        if (this.getRedstonePower(blockPos.south(), Direction.SOUTH) > 0) {
            return false;
        }
        if (this.getRedstonePower(blockPos.west(), Direction.WEST) > 0) {
            return false;
        }
        return this.getRedstonePower(blockPos.east(), Direction.EAST) > 0;
    }

    public int getRedstonePowerFromNeighbors(BlockPos blockPos) {
        int n = 0;
        for (Direction direction : FACING_VALUES) {
            int n2 = this.getRedstonePower(blockPos.offset(direction), direction);
            if (n2 >= 15) {
                return 0;
            }
            if (n2 <= n) continue;
            n = n2;
        }
        return n;
    }

    public void sendQuittingDisconnectingPacket() {
    }

    public long getGameTime() {
        return this.worldInfo.getGameTime();
    }

    public long getDayTime() {
        return this.worldInfo.getDayTime();
    }

    public boolean isBlockModifiable(PlayerEntity playerEntity, BlockPos blockPos) {
        return false;
    }

    public void setEntityState(Entity entity2, byte by) {
    }

    public void addBlockEvent(BlockPos blockPos, Block block, int n, int n2) {
        this.getBlockState(blockPos).receiveBlockEvent(this, blockPos, n, n2);
    }

    @Override
    public IWorldInfo getWorldInfo() {
        return this.worldInfo;
    }

    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }

    public float getThunderStrength(float f) {
        return MathHelper.lerp(f, this.prevThunderingStrength, this.thunderingStrength) * this.getRainStrength(f);
    }

    public void setThunderStrength(float f) {
        this.prevThunderingStrength = f;
        this.thunderingStrength = f;
    }

    public float getRainStrength(float f) {
        return MathHelper.lerp(f, this.prevRainingStrength, this.rainingStrength);
    }

    public void setRainStrength(float f) {
        this.prevRainingStrength = f;
        this.rainingStrength = f;
    }

    public boolean isThundering() {
        if (this.getDimensionType().hasSkyLight() && !this.getDimensionType().getHasCeiling()) {
            return (double)this.getThunderStrength(1.0f) > 0.9;
        }
        return true;
    }

    public boolean isRaining() {
        return (double)this.getRainStrength(1.0f) > 0.2;
    }

    public boolean isRainingAt(BlockPos blockPos) {
        if (!this.isRaining()) {
            return true;
        }
        if (!this.canSeeSky(blockPos)) {
            return true;
        }
        if (this.getHeight(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() > blockPos.getY()) {
            return true;
        }
        Biome biome = this.getBiome(blockPos);
        return biome.getPrecipitation() == Biome.RainType.RAIN && biome.getTemperature(blockPos) >= 0.15f;
    }

    public boolean isBlockinHighHumidity(BlockPos blockPos) {
        Biome biome = this.getBiome(blockPos);
        return biome.isHighHumidity();
    }

    @Nullable
    public abstract MapData getMapData(String var1);

    public abstract void registerMapData(MapData var1);

    public abstract int getNextMapId();

    public void playBroadcastSound(int n, BlockPos blockPos, int n2) {
    }

    public CrashReportCategory fillCrashReport(CrashReport crashReport) {
        CrashReportCategory crashReportCategory = crashReport.makeCategoryDepth("Affected level", 1);
        crashReportCategory.addDetail("All players", this::lambda$fillCrashReport$3);
        crashReportCategory.addDetail("Chunk stats", this.getChunkProvider()::makeString);
        crashReportCategory.addDetail("Level dimension", this::lambda$fillCrashReport$4);
        try {
            this.worldInfo.addToCrashReport(crashReportCategory);
        } catch (Throwable throwable) {
            crashReportCategory.addCrashSectionThrowable("Level Data Unobtainable", throwable);
        }
        return crashReportCategory;
    }

    public abstract void sendBlockBreakProgress(int var1, BlockPos var2, int var3);

    public void makeFireworks(double d, double d2, double d3, double d4, double d5, double d6, @Nullable CompoundNBT compoundNBT) {
    }

    public abstract Scoreboard getScoreboard();

    public void updateComparatorOutputLevel(BlockPos blockPos, Block block) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            if (!this.isBlockLoaded(blockPos2)) continue;
            BlockState blockState = this.getBlockState(blockPos2);
            if (blockState.isIn(Blocks.COMPARATOR)) {
                blockState.neighborChanged(this, blockPos2, block, blockPos, true);
                continue;
            }
            if (!blockState.isNormalCube(this, blockPos2) || !(blockState = this.getBlockState(blockPos2 = blockPos2.offset(direction))).isIn(Blocks.COMPARATOR)) continue;
            blockState.neighborChanged(this, blockPos2, block, blockPos, true);
        }
    }

    @Override
    public DifficultyInstance getDifficultyForLocation(BlockPos blockPos) {
        long l = 0L;
        float f = 0.0f;
        if (this.isBlockLoaded(blockPos)) {
            f = this.getMoonFactor();
            l = this.getChunkAt(blockPos).getInhabitedTime();
        }
        return new DifficultyInstance(this.getDifficulty(), this.getDayTime(), l, f);
    }

    @Override
    public int getSkylightSubtracted() {
        return this.skylightSubtracted;
    }

    public void setTimeLightningFlash(int n) {
    }

    @Override
    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }

    public void sendPacketToServer(IPacket<?> iPacket) {
        throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
    }

    @Override
    public DimensionType getDimensionType() {
        return this.dimensionType;
    }

    public RegistryKey<World> getDimensionKey() {
        return this.dimension;
    }

    @Override
    public Random getRandom() {
        return this.rand;
    }

    @Override
    public boolean hasBlockState(BlockPos blockPos, Predicate<BlockState> predicate) {
        return predicate.test(this.getBlockState(blockPos));
    }

    public abstract RecipeManager getRecipeManager();

    public abstract ITagCollectionSupplier getTags();

    public BlockPos getBlockRandomPos(int n, int n2, int n3, int n4) {
        this.updateLCG = this.updateLCG * 3 + 1013904223;
        int n5 = this.updateLCG >> 2;
        return new BlockPos(n + (n5 & 0xF), n2 + (n5 >> 16 & n4), n3 + (n5 >> 8 & 0xF));
    }

    public boolean isSaveDisabled() {
        return true;
    }

    public IProfiler getProfiler() {
        return this.profiler.get();
    }

    public Supplier<IProfiler> getWorldProfiler() {
        return this.profiler;
    }

    @Override
    public BiomeManager getBiomeManager() {
        return this.biomeManager;
    }

    public final boolean isDebug() {
        return this.isDebug;
    }

    @Override
    public IChunk getChunk(int n, int n2) {
        return this.getChunk(n, n2);
    }

    private String lambda$fillCrashReport$4() throws Exception {
        return this.getDimensionKey().getLocation().toString();
    }

    private String lambda$fillCrashReport$3() throws Exception {
        return this.getPlayers().size() + " total; " + this.getPlayers();
    }

    private static String lambda$tickBlockEntities$2(TileEntity tileEntity) {
        return String.valueOf(TileEntityType.getId(tileEntity.getType()));
    }

    private static Object lambda$addTileEntity$1(TileEntity tileEntity) {
        return Registry.BLOCK_ENTITY_TYPE.getKey(tileEntity.getType());
    }

    private static String lambda$neighborChanged$0(Block block) throws Exception {
        try {
            return String.format("ID #%s (%s // %s)", Registry.BLOCK.getKey(block), block.getTranslationKey(), block.getClass().getCanonicalName());
        } catch (Throwable throwable) {
            return "ID #" + Registry.BLOCK.getKey(block);
        }
    }
}

