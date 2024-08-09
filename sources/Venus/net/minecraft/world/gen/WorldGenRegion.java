/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.ITickList;
import net.minecraft.world.WorldGenTickList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegion
implements ISeedReader {
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<IChunk> chunkPrimers;
    private final int mainChunkX;
    private final int mainChunkZ;
    private final int field_217380_e;
    private final ServerWorld world;
    private final long seed;
    private final IWorldInfo worldInfo;
    private final Random random;
    private final DimensionType field_241159_j_;
    private final ITickList<Block> pendingBlockTickList = new WorldGenTickList<Block>(this::lambda$new$0);
    private final ITickList<Fluid> pendingFluidTickList = new WorldGenTickList<Fluid>(this::lambda$new$1);
    private final BiomeManager biomeManager;
    private final ChunkPos field_241160_n_;
    private final ChunkPos field_241161_o_;
    private final StructureManager field_244530_p;

    public WorldGenRegion(ServerWorld serverWorld, List<IChunk> list) {
        int n = MathHelper.floor(Math.sqrt(list.size()));
        if (n * n != list.size()) {
            throw Util.pauseDevMode(new IllegalStateException("Cache size is not a square."));
        }
        ChunkPos chunkPos = list.get(list.size() / 2).getPos();
        this.chunkPrimers = list;
        this.mainChunkX = chunkPos.x;
        this.mainChunkZ = chunkPos.z;
        this.field_217380_e = n;
        this.world = serverWorld;
        this.seed = serverWorld.getSeed();
        this.worldInfo = serverWorld.getWorldInfo();
        this.random = serverWorld.getRandom();
        this.field_241159_j_ = serverWorld.getDimensionType();
        this.biomeManager = new BiomeManager(this, BiomeManager.getHashedSeed(this.seed), serverWorld.getDimensionType().getMagnifier());
        this.field_241160_n_ = list.get(0).getPos();
        this.field_241161_o_ = list.get(list.size() - 1).getPos();
        this.field_244530_p = serverWorld.func_241112_a_().func_241464_a_(this);
    }

    public int getMainChunkX() {
        return this.mainChunkX;
    }

    public int getMainChunkZ() {
        return this.mainChunkZ;
    }

    @Override
    public IChunk getChunk(int n, int n2) {
        return this.getChunk(n, n2, ChunkStatus.EMPTY);
    }

    @Override
    @Nullable
    public IChunk getChunk(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        IChunk iChunk;
        if (this.chunkExists(n, n2)) {
            int n3 = n - this.field_241160_n_.x;
            int n4 = n2 - this.field_241160_n_.z;
            iChunk = this.chunkPrimers.get(n3 + n4 * this.field_217380_e);
            if (iChunk.getStatus().isAtLeast(chunkStatus)) {
                return iChunk;
            }
        } else {
            iChunk = null;
        }
        if (!bl) {
            return null;
        }
        LOGGER.error("Requested chunk : {} {}", (Object)n, (Object)n2);
        LOGGER.error("Region bounds : {} {} | {} {}", (Object)this.field_241160_n_.x, (Object)this.field_241160_n_.z, (Object)this.field_241161_o_.x, (Object)this.field_241161_o_.z);
        if (iChunk != null) {
            throw Util.pauseDevMode(new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", chunkStatus, iChunk.getStatus(), n, n2)));
        }
        throw Util.pauseDevMode(new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", n, n2)));
    }

    @Override
    public boolean chunkExists(int n, int n2) {
        return n >= this.field_241160_n_.x && n <= this.field_241161_o_.x && n2 >= this.field_241160_n_.z && n2 <= this.field_241161_o_.z;
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        return this.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4).getBlockState(blockPos);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        return this.getChunk(blockPos).getFluidState(blockPos);
    }

    @Override
    @Nullable
    public PlayerEntity getClosestPlayer(double d, double d2, double d3, double d4, Predicate<Entity> predicate) {
        return null;
    }

    @Override
    public int getSkylightSubtracted() {
        return 1;
    }

    @Override
    public BiomeManager getBiomeManager() {
        return this.biomeManager;
    }

    @Override
    public Biome getNoiseBiomeRaw(int n, int n2, int n3) {
        return this.world.getNoiseBiomeRaw(n, n2, n3);
    }

    @Override
    public float func_230487_a_(Direction direction, boolean bl) {
        return 1.0f;
    }

    @Override
    public WorldLightManager getLightManager() {
        return this.world.getLightManager();
    }

    @Override
    public boolean destroyBlock(BlockPos blockPos, boolean bl, @Nullable Entity entity2, int n) {
        BlockState blockState = this.getBlockState(blockPos);
        if (blockState.isAir()) {
            return true;
        }
        if (bl) {
            TileEntity tileEntity = blockState.getBlock().isTileEntityProvider() ? this.getTileEntity(blockPos) : null;
            Block.spawnDrops(blockState, this.world, blockPos, tileEntity, entity2, ItemStack.EMPTY);
        }
        return this.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3, n);
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        IChunk iChunk = this.getChunk(blockPos);
        TileEntity tileEntity = iChunk.getTileEntity(blockPos);
        if (tileEntity != null) {
            return tileEntity;
        }
        CompoundNBT compoundNBT = iChunk.getDeferredTileEntity(blockPos);
        BlockState blockState = iChunk.getBlockState(blockPos);
        if (compoundNBT != null) {
            if ("DUMMY".equals(compoundNBT.getString("id"))) {
                Block block = blockState.getBlock();
                if (!(block instanceof ITileEntityProvider)) {
                    return null;
                }
                tileEntity = ((ITileEntityProvider)((Object)block)).createNewTileEntity(this.world);
            } else {
                tileEntity = TileEntity.readTileEntity(blockState, compoundNBT);
            }
            if (tileEntity != null) {
                iChunk.addTileEntity(blockPos, tileEntity);
                return tileEntity;
            }
        }
        if (blockState.getBlock() instanceof ITileEntityProvider) {
            LOGGER.warn("Tried to access a block entity before it was created. {}", (Object)blockPos);
        }
        return null;
    }

    @Override
    public boolean setBlockState(BlockPos blockPos, BlockState blockState, int n, int n2) {
        Block block;
        IChunk iChunk = this.getChunk(blockPos);
        BlockState blockState2 = iChunk.setBlockState(blockPos, blockState, false);
        if (blockState2 != null) {
            this.world.onBlockStateChange(blockPos, blockState2, blockState);
        }
        if ((block = blockState.getBlock()).isTileEntityProvider()) {
            if (iChunk.getStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
                iChunk.addTileEntity(blockPos, ((ITileEntityProvider)((Object)block)).createNewTileEntity(this));
            } else {
                CompoundNBT compoundNBT = new CompoundNBT();
                compoundNBT.putInt("x", blockPos.getX());
                compoundNBT.putInt("y", blockPos.getY());
                compoundNBT.putInt("z", blockPos.getZ());
                compoundNBT.putString("id", "DUMMY");
                iChunk.addTileEntity(compoundNBT);
            }
        } else if (blockState2 != null && blockState2.getBlock().isTileEntityProvider()) {
            iChunk.removeTileEntity(blockPos);
        }
        if (blockState.blockNeedsPostProcessing(this, blockPos)) {
            this.markBlockForPostprocessing(blockPos);
        }
        return false;
    }

    private void markBlockForPostprocessing(BlockPos blockPos) {
        this.getChunk(blockPos).markBlockForPostprocessing(blockPos);
    }

    @Override
    public boolean addEntity(Entity entity2) {
        int n = MathHelper.floor(entity2.getPosX() / 16.0);
        int n2 = MathHelper.floor(entity2.getPosZ() / 16.0);
        this.getChunk(n, n2).addEntity(entity2);
        return false;
    }

    @Override
    public boolean removeBlock(BlockPos blockPos, boolean bl) {
        return this.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 0);
    }

    @Override
    public WorldBorder getWorldBorder() {
        return this.world.getWorldBorder();
    }

    @Override
    public boolean isRemote() {
        return true;
    }

    @Override
    @Deprecated
    public ServerWorld getWorld() {
        return this.world;
    }

    @Override
    public DynamicRegistries func_241828_r() {
        return this.world.func_241828_r();
    }

    @Override
    public IWorldInfo getWorldInfo() {
        return this.worldInfo;
    }

    @Override
    public DifficultyInstance getDifficultyForLocation(BlockPos blockPos) {
        if (!this.chunkExists(blockPos.getX() >> 4, blockPos.getZ() >> 4)) {
            throw new RuntimeException("We are asking a region for a chunk out of bound");
        }
        return new DifficultyInstance(this.world.getDifficulty(), this.world.getDayTime(), 0L, this.world.getMoonFactor());
    }

    @Override
    public AbstractChunkProvider getChunkProvider() {
        return this.world.getChunkProvider();
    }

    @Override
    public long getSeed() {
        return this.seed;
    }

    @Override
    public ITickList<Block> getPendingBlockTicks() {
        return this.pendingBlockTickList;
    }

    @Override
    public ITickList<Fluid> getPendingFluidTicks() {
        return this.pendingFluidTickList;
    }

    @Override
    public int getSeaLevel() {
        return this.world.getSeaLevel();
    }

    @Override
    public Random getRandom() {
        return this.random;
    }

    @Override
    public int getHeight(Heightmap.Type type, int n, int n2) {
        return this.getChunk(n >> 4, n2 >> 4).getTopBlockY(type, n & 0xF, n2 & 0xF) + 1;
    }

    @Override
    public void playSound(@Nullable PlayerEntity playerEntity, BlockPos blockPos, SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2) {
    }

    @Override
    public void addParticle(IParticleData iParticleData, double d, double d2, double d3, double d4, double d5, double d6) {
    }

    @Override
    public void playEvent(@Nullable PlayerEntity playerEntity, int n, BlockPos blockPos, int n2) {
    }

    @Override
    public DimensionType getDimensionType() {
        return this.field_241159_j_;
    }

    @Override
    public boolean hasBlockState(BlockPos blockPos, Predicate<BlockState> predicate) {
        return predicate.test(this.getBlockState(blockPos));
    }

    @Override
    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB, @Nullable Predicate<? super T> predicate) {
        return Collections.emptyList();
    }

    @Override
    public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, @Nullable Predicate<? super Entity> predicate) {
        return Collections.emptyList();
    }

    public List<PlayerEntity> getPlayers() {
        return Collections.emptyList();
    }

    @Override
    public Stream<? extends StructureStart<?>> func_241827_a(SectionPos sectionPos, Structure<?> structure) {
        return this.field_244530_p.func_235011_a_(sectionPos, structure);
    }

    private ITickList lambda$new$1(BlockPos blockPos) {
        return this.getChunk(blockPos).getFluidsToBeTicked();
    }

    private ITickList lambda$new$0(BlockPos blockPos) {
        return this.getChunk(blockPos).getBlocksToBeTicked();
    }
}

