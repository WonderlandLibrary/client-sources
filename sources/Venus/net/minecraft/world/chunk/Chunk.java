/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.EmptyTickList;
import net.minecraft.world.ITickList;
import net.minecraft.world.SerializableTickList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerTickList;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerTickList;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chunk
implements IChunk {
    private static final Logger LOGGER = LogManager.getLogger();
    @Nullable
    public static final ChunkSection EMPTY_SECTION = null;
    private final ChunkSection[] sections = new ChunkSection[16];
    private BiomeContainer blockBiomeArray;
    private final Map<BlockPos, CompoundNBT> deferredTileEntities = Maps.newHashMap();
    private boolean loaded;
    private final World world;
    private final Map<Heightmap.Type, Heightmap> heightMap = Maps.newEnumMap(Heightmap.Type.class);
    private final UpgradeData upgradeData;
    private final Map<BlockPos, TileEntity> tileEntities = Maps.newHashMap();
    private final ClassInheritanceMultiMap<Entity>[] entityLists;
    private final Map<Structure<?>, StructureStart<?>> structureStarts = Maps.newHashMap();
    private final Map<Structure<?>, LongSet> structureReferences = Maps.newHashMap();
    private final ShortList[] packedBlockPositions = new ShortList[16];
    private ITickList<Block> blocksToBeTicked;
    private ITickList<Fluid> fluidsToBeTicked;
    private boolean hasEntities;
    private long lastSaveTime;
    private volatile boolean dirty;
    private long inhabitedTime;
    @Nullable
    private Supplier<ChunkHolder.LocationType> locationType;
    @Nullable
    private Consumer<Chunk> postLoadConsumer;
    private final ChunkPos pos;
    private volatile boolean lightCorrect;

    public Chunk(World world, ChunkPos chunkPos, BiomeContainer biomeContainer) {
        this(world, chunkPos, biomeContainer, UpgradeData.EMPTY, EmptyTickList.get(), EmptyTickList.get(), 0L, null, null);
    }

    public Chunk(World world, ChunkPos chunkPos, BiomeContainer biomeContainer, UpgradeData upgradeData, ITickList<Block> iTickList, ITickList<Fluid> iTickList2, long l, @Nullable ChunkSection[] chunkSectionArray, @Nullable Consumer<Chunk> consumer) {
        this.entityLists = new ClassInheritanceMultiMap[16];
        this.world = world;
        this.pos = chunkPos;
        this.upgradeData = upgradeData;
        for (Heightmap.Type type : Heightmap.Type.values()) {
            if (!ChunkStatus.FULL.getHeightMaps().contains(type)) continue;
            this.heightMap.put(type, new Heightmap(this, type));
        }
        for (int i = 0; i < this.entityLists.length; ++i) {
            this.entityLists[i] = new ClassInheritanceMultiMap<Entity>(Entity.class);
        }
        this.blockBiomeArray = biomeContainer;
        this.blocksToBeTicked = iTickList;
        this.fluidsToBeTicked = iTickList2;
        this.inhabitedTime = l;
        this.postLoadConsumer = consumer;
        if (chunkSectionArray != null) {
            if (this.sections.length == chunkSectionArray.length) {
                System.arraycopy(chunkSectionArray, 0, this.sections, 0, this.sections.length);
            } else {
                LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", (Object)chunkSectionArray.length, (Object)this.sections.length);
            }
        }
    }

    public Chunk(World world, ChunkPrimer chunkPrimer) {
        this(world, chunkPrimer.getPos(), chunkPrimer.getBiomes(), chunkPrimer.getUpgradeData(), chunkPrimer.getBlocksToBeTicked(), chunkPrimer.getFluidsToBeTicked(), chunkPrimer.getInhabitedTime(), chunkPrimer.getSections(), null);
        for (CompoundNBT entry : chunkPrimer.getEntities()) {
            EntityType.loadEntityAndExecute(entry, world, this::lambda$new$0);
        }
        for (TileEntity tileEntity : chunkPrimer.getTileEntities().values()) {
            this.addTileEntity(tileEntity);
        }
        this.deferredTileEntities.putAll(chunkPrimer.getDeferredTileEntities());
        for (int i = 0; i < chunkPrimer.getPackedPositions().length; ++i) {
            this.packedBlockPositions[i] = chunkPrimer.getPackedPositions()[i];
        }
        this.setStructureStarts(chunkPrimer.getStructureStarts());
        this.setStructureReferences(chunkPrimer.getStructureReferences());
        for (Map.Entry<Heightmap.Type, Heightmap> entry : chunkPrimer.getHeightmaps()) {
            if (!ChunkStatus.FULL.getHeightMaps().contains(entry.getKey())) continue;
            this.getHeightmap(entry.getKey()).setDataArray(entry.getValue().getDataArray());
        }
        this.setLight(chunkPrimer.hasLight());
        this.dirty = true;
    }

    @Override
    public Heightmap getHeightmap(Heightmap.Type type) {
        return this.heightMap.computeIfAbsent(type, this::lambda$getHeightmap$1);
    }

    @Override
    public Set<BlockPos> getTileEntitiesPos() {
        HashSet<BlockPos> hashSet = Sets.newHashSet(this.deferredTileEntities.keySet());
        hashSet.addAll(this.tileEntities.keySet());
        return hashSet;
    }

    @Override
    public ChunkSection[] getSections() {
        return this.sections;
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        int n = blockPos.getX();
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ();
        if (this.world.isDebug()) {
            BlockState blockState = null;
            if (n2 == 60) {
                blockState = Blocks.BARRIER.getDefaultState();
            }
            if (n2 == 70) {
                blockState = DebugChunkGenerator.getBlockStateFor(n, n3);
            }
            return blockState == null ? Blocks.AIR.getDefaultState() : blockState;
        }
        try {
            ChunkSection chunkSection;
            if (n2 >= 0 && n2 >> 4 < this.sections.length && !ChunkSection.isEmpty(chunkSection = this.sections[n2 >> 4])) {
                return chunkSection.getBlockState(n & 0xF, n2 & 0xF, n3 & 0xF);
            }
            return Blocks.AIR.getDefaultState();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Getting block state");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being got");
            crashReportCategory.addDetail("Location", () -> Chunk.lambda$getBlockState$2(n, n2, n3));
            throw new ReportedException(crashReport);
        }
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        return this.getFluidState(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public FluidState getFluidState(int n, int n2, int n3) {
        try {
            ChunkSection chunkSection;
            if (n2 >= 0 && n2 >> 4 < this.sections.length && !ChunkSection.isEmpty(chunkSection = this.sections[n2 >> 4])) {
                return chunkSection.getFluidState(n & 0xF, n2 & 0xF, n3 & 0xF);
            }
            return Fluids.EMPTY.getDefaultState();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Getting fluid state");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being got");
            crashReportCategory.addDetail("Location", () -> Chunk.lambda$getFluidState$3(n, n2, n3));
            throw new ReportedException(crashReport);
        }
    }

    @Override
    @Nullable
    public BlockState setBlockState(BlockPos blockPos, BlockState blockState, boolean bl) {
        TileEntity tileEntity;
        int n = blockPos.getX() & 0xF;
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ() & 0xF;
        ChunkSection chunkSection = this.sections[n2 >> 4];
        if (chunkSection == EMPTY_SECTION) {
            if (blockState.isAir()) {
                return null;
            }
            this.sections[n2 >> 4] = chunkSection = new ChunkSection(n2 >> 4 << 4);
        }
        boolean bl2 = chunkSection.isEmpty();
        BlockState blockState2 = chunkSection.setBlockState(n, n2 & 0xF, n3, blockState);
        if (blockState2 == blockState) {
            return null;
        }
        Block block = blockState.getBlock();
        Block block2 = blockState2.getBlock();
        this.heightMap.get(Heightmap.Type.MOTION_BLOCKING).update(n, n2, n3, blockState);
        this.heightMap.get(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).update(n, n2, n3, blockState);
        this.heightMap.get(Heightmap.Type.OCEAN_FLOOR).update(n, n2, n3, blockState);
        this.heightMap.get(Heightmap.Type.WORLD_SURFACE).update(n, n2, n3, blockState);
        boolean bl3 = chunkSection.isEmpty();
        if (bl2 != bl3) {
            this.world.getChunkProvider().getLightManager().func_215567_a(blockPos, bl3);
        }
        if (!this.world.isRemote) {
            blockState2.onReplaced(this.world, blockPos, blockState, bl);
        } else if (block2 != block && block2 instanceof ITileEntityProvider) {
            this.world.removeTileEntity(blockPos);
        }
        if (!chunkSection.getBlockState(n, n2 & 0xF, n3).isIn(block)) {
            return null;
        }
        if (block2 instanceof ITileEntityProvider && (tileEntity = this.getTileEntity(blockPos, CreateEntityType.CHECK)) != null) {
            tileEntity.updateContainingBlockInfo();
        }
        if (!this.world.isRemote) {
            blockState.onBlockAdded(this.world, blockPos, blockState2, bl);
        }
        if (block instanceof ITileEntityProvider) {
            tileEntity = this.getTileEntity(blockPos, CreateEntityType.CHECK);
            if (tileEntity == null) {
                tileEntity = ((ITileEntityProvider)((Object)block)).createNewTileEntity(this.world);
                this.world.setTileEntity(blockPos, tileEntity);
            } else {
                tileEntity.updateContainingBlockInfo();
            }
        }
        this.dirty = true;
        return blockState2;
    }

    @Nullable
    public WorldLightManager getWorldLightManager() {
        return this.world.getChunkProvider().getLightManager();
    }

    @Override
    public void addEntity(Entity entity2) {
        int n;
        this.hasEntities = true;
        int n2 = MathHelper.floor(entity2.getPosX() / 16.0);
        int n3 = MathHelper.floor(entity2.getPosZ() / 16.0);
        if (n2 != this.pos.x || n3 != this.pos.z) {
            LOGGER.warn("Wrong location! ({}, {}) should be ({}, {}), {}", (Object)n2, (Object)n3, (Object)this.pos.x, (Object)this.pos.z, (Object)entity2);
            entity2.removed = true;
        }
        if ((n = MathHelper.floor(entity2.getPosY() / 16.0)) < 0) {
            n = 0;
        }
        if (n >= this.entityLists.length) {
            n = this.entityLists.length - 1;
        }
        entity2.addedToChunk = true;
        entity2.chunkCoordX = this.pos.x;
        entity2.chunkCoordY = n;
        entity2.chunkCoordZ = this.pos.z;
        this.entityLists[n].add(entity2);
    }

    @Override
    public void setHeightmap(Heightmap.Type type, long[] lArray) {
        this.heightMap.get(type).setDataArray(lArray);
    }

    public void removeEntity(Entity entity2) {
        this.removeEntityAtIndex(entity2, entity2.chunkCoordY);
    }

    public void removeEntityAtIndex(Entity entity2, int n) {
        if (n < 0) {
            n = 0;
        }
        if (n >= this.entityLists.length) {
            n = this.entityLists.length - 1;
        }
        this.entityLists[n].remove(entity2);
    }

    @Override
    public int getTopBlockY(Heightmap.Type type, int n, int n2) {
        return this.heightMap.get(type).getHeight(n & 0xF, n2 & 0xF) - 1;
    }

    @Nullable
    private TileEntity createNewTileEntity(BlockPos blockPos) {
        BlockState blockState = this.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return !block.isTileEntityProvider() ? null : ((ITileEntityProvider)((Object)block)).createNewTileEntity(this.world);
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        return this.getTileEntity(blockPos, CreateEntityType.CHECK);
    }

    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos, CreateEntityType createEntityType) {
        TileEntity tileEntity;
        CompoundNBT compoundNBT;
        TileEntity tileEntity2 = this.tileEntities.get(blockPos);
        if (tileEntity2 == null && (compoundNBT = this.deferredTileEntities.remove(blockPos)) != null && (tileEntity = this.setDeferredTileEntity(blockPos, compoundNBT)) != null) {
            return tileEntity;
        }
        if (tileEntity2 == null) {
            if (createEntityType == CreateEntityType.IMMEDIATE) {
                tileEntity2 = this.createNewTileEntity(blockPos);
                this.world.setTileEntity(blockPos, tileEntity2);
            }
        } else if (tileEntity2.isRemoved()) {
            this.tileEntities.remove(blockPos);
            return null;
        }
        return tileEntity2;
    }

    public void addTileEntity(TileEntity tileEntity) {
        this.addTileEntity(tileEntity.getPos(), tileEntity);
        if (this.loaded || this.world.isRemote()) {
            this.world.setTileEntity(tileEntity.getPos(), tileEntity);
        }
    }

    @Override
    public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
        if (this.getBlockState(blockPos).getBlock() instanceof ITileEntityProvider) {
            tileEntity.setWorldAndPos(this.world, blockPos);
            tileEntity.validate();
            TileEntity tileEntity2 = this.tileEntities.put(blockPos.toImmutable(), tileEntity);
            if (tileEntity2 != null && tileEntity2 != tileEntity) {
                tileEntity2.remove();
            }
        }
    }

    @Override
    public void addTileEntity(CompoundNBT compoundNBT) {
        this.deferredTileEntities.put(new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z")), compoundNBT);
    }

    @Override
    @Nullable
    public CompoundNBT getTileEntityNBT(BlockPos blockPos) {
        TileEntity tileEntity = this.getTileEntity(blockPos);
        if (tileEntity != null && !tileEntity.isRemoved()) {
            CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
            compoundNBT.putBoolean("keepPacked", true);
            return compoundNBT;
        }
        CompoundNBT compoundNBT = this.deferredTileEntities.get(blockPos);
        if (compoundNBT != null) {
            compoundNBT = compoundNBT.copy();
            compoundNBT.putBoolean("keepPacked", false);
        }
        return compoundNBT;
    }

    @Override
    public void removeTileEntity(BlockPos blockPos) {
        TileEntity tileEntity;
        if ((this.loaded || this.world.isRemote()) && (tileEntity = this.tileEntities.remove(blockPos)) != null) {
            tileEntity.remove();
        }
    }

    public void postLoad() {
        if (this.postLoadConsumer != null) {
            this.postLoadConsumer.accept(this);
            this.postLoadConsumer = null;
        }
    }

    public void markDirty() {
        this.dirty = true;
    }

    public void getEntitiesWithinAABBForEntity(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, List<Entity> list, @Nullable Predicate<? super Entity> predicate) {
        int n = MathHelper.floor((axisAlignedBB.minY - 2.0) / 16.0);
        int n2 = MathHelper.floor((axisAlignedBB.maxY + 2.0) / 16.0);
        n = MathHelper.clamp(n, 0, this.entityLists.length - 1);
        n2 = MathHelper.clamp(n2, 0, this.entityLists.length - 1);
        for (int i = n; i <= n2; ++i) {
            ClassInheritanceMultiMap<Entity> classInheritanceMultiMap = this.entityLists[i];
            List<Entity> list2 = classInheritanceMultiMap.func_241289_a_();
            int n3 = list2.size();
            for (int j = 0; j < n3; ++j) {
                Entity entity3 = list2.get(j);
                if (!entity3.getBoundingBox().intersects(axisAlignedBB) || entity3 == entity2) continue;
                if (predicate == null || predicate.test(entity3)) {
                    list.add(entity3);
                }
                if (!(entity3 instanceof EnderDragonEntity)) continue;
                for (EnderDragonPartEntity enderDragonPartEntity : ((EnderDragonEntity)entity3).getDragonParts()) {
                    if (enderDragonPartEntity == entity2 || !enderDragonPartEntity.getBoundingBox().intersects(axisAlignedBB) || predicate != null && !predicate.test(enderDragonPartEntity)) continue;
                    list.add(enderDragonPartEntity);
                }
            }
        }
    }

    public <T extends Entity> void getEntitiesWithinAABBForList(@Nullable EntityType<?> entityType, AxisAlignedBB axisAlignedBB, List<? super T> list, Predicate<? super T> predicate) {
        int n = MathHelper.floor((axisAlignedBB.minY - 2.0) / 16.0);
        int n2 = MathHelper.floor((axisAlignedBB.maxY + 2.0) / 16.0);
        n = MathHelper.clamp(n, 0, this.entityLists.length - 1);
        n2 = MathHelper.clamp(n2, 0, this.entityLists.length - 1);
        for (int i = n; i <= n2; ++i) {
            for (Entity entity2 : this.entityLists[i].getByClass(Entity.class)) {
                if (entityType != null && entity2.getType() != entityType || !entity2.getBoundingBox().intersects(axisAlignedBB) || !predicate.test(entity2)) continue;
                list.add(entity2);
            }
        }
    }

    public <T extends Entity> void getEntitiesOfTypeWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB, List<T> list, @Nullable Predicate<? super T> predicate) {
        int n = MathHelper.floor((axisAlignedBB.minY - 2.0) / 16.0);
        int n2 = MathHelper.floor((axisAlignedBB.maxY + 2.0) / 16.0);
        n = MathHelper.clamp(n, 0, this.entityLists.length - 1);
        n2 = MathHelper.clamp(n2, 0, this.entityLists.length - 1);
        for (int i = n; i <= n2; ++i) {
            for (Entity entity2 : this.entityLists[i].getByClass(clazz)) {
                if (!entity2.getBoundingBox().intersects(axisAlignedBB) || predicate != null && !predicate.test(entity2)) continue;
                list.add(entity2);
            }
        }
    }

    public boolean isEmpty() {
        return true;
    }

    @Override
    public ChunkPos getPos() {
        return this.pos;
    }

    public void read(@Nullable BiomeContainer biomeContainer, PacketBuffer packetBuffer, CompoundNBT compoundNBT, int n) {
        boolean bl = biomeContainer != null;
        Predicate<BlockPos> predicate = bl ? Chunk::lambda$read$4 : arg_0 -> Chunk.lambda$read$5(n, arg_0);
        Sets.newHashSet(this.tileEntities.keySet()).stream().filter(predicate).forEach(this.world::removeTileEntity);
        for (int i = 0; i < this.sections.length; ++i) {
            ChunkSection chunkSection = this.sections[i];
            if ((n & 1 << i) == 0) {
                if (!bl || chunkSection == EMPTY_SECTION) continue;
                this.sections[i] = EMPTY_SECTION;
                continue;
            }
            if (chunkSection == EMPTY_SECTION) {
                this.sections[i] = chunkSection = new ChunkSection(i << 4);
            }
            chunkSection.read(packetBuffer);
        }
        if (biomeContainer != null) {
            this.blockBiomeArray = biomeContainer;
        }
        for (Heightmap.Type type : Heightmap.Type.values()) {
            String string = type.getId();
            if (!compoundNBT.contains(string, 1)) continue;
            this.setHeightmap(type, compoundNBT.getLongArray(string));
        }
        for (TileEntity tileEntity : this.tileEntities.values()) {
            tileEntity.updateContainingBlockInfo();
        }
    }

    @Override
    public BiomeContainer getBiomes() {
        return this.blockBiomeArray;
    }

    public void setLoaded(boolean bl) {
        this.loaded = bl;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public Collection<Map.Entry<Heightmap.Type, Heightmap>> getHeightmaps() {
        return Collections.unmodifiableSet(this.heightMap.entrySet());
    }

    public Map<BlockPos, TileEntity> getTileEntityMap() {
        return this.tileEntities;
    }

    public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
        return this.entityLists;
    }

    @Override
    public CompoundNBT getDeferredTileEntity(BlockPos blockPos) {
        return this.deferredTileEntities.get(blockPos);
    }

    @Override
    public Stream<BlockPos> getLightSources() {
        return StreamSupport.stream(BlockPos.getAllInBoxMutable(this.pos.getXStart(), 0, this.pos.getZStart(), this.pos.getXEnd(), 255, this.pos.getZEnd()).spliterator(), false).filter(this::lambda$getLightSources$6);
    }

    @Override
    public ITickList<Block> getBlocksToBeTicked() {
        return this.blocksToBeTicked;
    }

    @Override
    public ITickList<Fluid> getFluidsToBeTicked() {
        return this.fluidsToBeTicked;
    }

    @Override
    public void setModified(boolean bl) {
        this.dirty = bl;
    }

    @Override
    public boolean isModified() {
        return this.dirty || this.hasEntities && this.world.getGameTime() != this.lastSaveTime;
    }

    public void setHasEntities(boolean bl) {
        this.hasEntities = bl;
    }

    @Override
    public void setLastSaveTime(long l) {
        this.lastSaveTime = l;
    }

    @Override
    @Nullable
    public StructureStart<?> func_230342_a_(Structure<?> structure) {
        return this.structureStarts.get(structure);
    }

    @Override
    public void func_230344_a_(Structure<?> structure, StructureStart<?> structureStart) {
        this.structureStarts.put(structure, structureStart);
    }

    @Override
    public Map<Structure<?>, StructureStart<?>> getStructureStarts() {
        return this.structureStarts;
    }

    @Override
    public void setStructureStarts(Map<Structure<?>, StructureStart<?>> map) {
        this.structureStarts.clear();
        this.structureStarts.putAll(map);
    }

    @Override
    public LongSet func_230346_b_(Structure<?> structure) {
        return this.structureReferences.computeIfAbsent(structure, Chunk::lambda$func_230346_b_$7);
    }

    @Override
    public void func_230343_a_(Structure<?> structure, long l) {
        this.structureReferences.computeIfAbsent(structure, Chunk::lambda$func_230343_a_$8).add(l);
    }

    @Override
    public Map<Structure<?>, LongSet> getStructureReferences() {
        return this.structureReferences;
    }

    @Override
    public void setStructureReferences(Map<Structure<?>, LongSet> map) {
        this.structureReferences.clear();
        this.structureReferences.putAll(map);
    }

    @Override
    public long getInhabitedTime() {
        return this.inhabitedTime;
    }

    @Override
    public void setInhabitedTime(long l) {
        this.inhabitedTime = l;
    }

    public void postProcess() {
        ChunkPos chunkPos = this.getPos();
        for (int i = 0; i < this.packedBlockPositions.length; ++i) {
            if (this.packedBlockPositions[i] == null) continue;
            for (Short s : this.packedBlockPositions[i]) {
                BlockPos blockPos = ChunkPrimer.unpackToWorld(s, i, chunkPos);
                BlockState blockState = this.getBlockState(blockPos);
                BlockState blockState2 = Block.getValidBlockForPosition(blockState, this.world, blockPos);
                this.world.setBlockState(blockPos, blockState2, 1);
            }
            this.packedBlockPositions[i].clear();
        }
        this.rescheduleTicks();
        for (BlockPos blockPos : Sets.newHashSet(this.deferredTileEntities.keySet())) {
            this.getTileEntity(blockPos);
        }
        this.deferredTileEntities.clear();
        this.upgradeData.postProcessChunk(this);
    }

    @Nullable
    private TileEntity setDeferredTileEntity(BlockPos blockPos, CompoundNBT compoundNBT) {
        TileEntity tileEntity;
        BlockState blockState = this.getBlockState(blockPos);
        if ("DUMMY".equals(compoundNBT.getString("id"))) {
            Block block = blockState.getBlock();
            if (block instanceof ITileEntityProvider) {
                tileEntity = ((ITileEntityProvider)((Object)block)).createNewTileEntity(this.world);
            } else {
                tileEntity = null;
                LOGGER.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", (Object)blockPos, (Object)blockState);
            }
        } else {
            tileEntity = TileEntity.readTileEntity(blockState, compoundNBT);
        }
        if (tileEntity != null) {
            tileEntity.setWorldAndPos(this.world, blockPos);
            this.addTileEntity(tileEntity);
        } else {
            LOGGER.warn("Tried to load a block entity for block {} but failed at location {}", (Object)blockState, (Object)blockPos);
        }
        return tileEntity;
    }

    @Override
    public UpgradeData getUpgradeData() {
        return this.upgradeData;
    }

    @Override
    public ShortList[] getPackedPositions() {
        return this.packedBlockPositions;
    }

    public void rescheduleTicks() {
        if (this.blocksToBeTicked instanceof ChunkPrimerTickList) {
            ((ChunkPrimerTickList)this.blocksToBeTicked).postProcess(this.world.getPendingBlockTicks(), this::lambda$rescheduleTicks$9);
            this.blocksToBeTicked = EmptyTickList.get();
        } else if (this.blocksToBeTicked instanceof SerializableTickList) {
            ((SerializableTickList)this.blocksToBeTicked).func_234855_a_(this.world.getPendingBlockTicks());
            this.blocksToBeTicked = EmptyTickList.get();
        }
        if (this.fluidsToBeTicked instanceof ChunkPrimerTickList) {
            ((ChunkPrimerTickList)this.fluidsToBeTicked).postProcess(this.world.getPendingFluidTicks(), this::lambda$rescheduleTicks$10);
            this.fluidsToBeTicked = EmptyTickList.get();
        } else if (this.fluidsToBeTicked instanceof SerializableTickList) {
            ((SerializableTickList)this.fluidsToBeTicked).func_234855_a_(this.world.getPendingFluidTicks());
            this.fluidsToBeTicked = EmptyTickList.get();
        }
    }

    public void saveScheduledTicks(ServerWorld serverWorld) {
        if (this.blocksToBeTicked == EmptyTickList.get()) {
            this.blocksToBeTicked = new SerializableTickList<Block>(Registry.BLOCK::getKey, ((ServerTickList)serverWorld.getPendingBlockTicks()).getPending(this.pos, true, true), serverWorld.getGameTime());
            this.setModified(false);
        }
        if (this.fluidsToBeTicked == EmptyTickList.get()) {
            this.fluidsToBeTicked = new SerializableTickList<Fluid>(Registry.FLUID::getKey, ((ServerTickList)serverWorld.getPendingFluidTicks()).getPending(this.pos, true, true), serverWorld.getGameTime());
            this.setModified(false);
        }
    }

    @Override
    public ChunkStatus getStatus() {
        return ChunkStatus.FULL;
    }

    public ChunkHolder.LocationType getLocationType() {
        return this.locationType == null ? ChunkHolder.LocationType.BORDER : this.locationType.get();
    }

    public void setLocationType(Supplier<ChunkHolder.LocationType> supplier) {
        this.locationType = supplier;
    }

    @Override
    public boolean hasLight() {
        return this.lightCorrect;
    }

    @Override
    public void setLight(boolean bl) {
        this.lightCorrect = bl;
        this.setModified(false);
    }

    private Fluid lambda$rescheduleTicks$10(BlockPos blockPos) {
        return this.getFluidState(blockPos).getFluid();
    }

    private Block lambda$rescheduleTicks$9(BlockPos blockPos) {
        return this.getBlockState(blockPos).getBlock();
    }

    private static LongSet lambda$func_230343_a_$8(Structure structure) {
        return new LongOpenHashSet();
    }

    private static LongSet lambda$func_230346_b_$7(Structure structure) {
        return new LongOpenHashSet();
    }

    private boolean lambda$getLightSources$6(BlockPos blockPos) {
        return this.getBlockState(blockPos).getLightValue() != 0;
    }

    private static boolean lambda$read$5(int n, BlockPos blockPos) {
        return (n & 1 << (blockPos.getY() >> 4)) != 0;
    }

    private static boolean lambda$read$4(BlockPos blockPos) {
        return false;
    }

    private static String lambda$getFluidState$3(int n, int n2, int n3) throws Exception {
        return CrashReportCategory.getCoordinateInfo(n, n2, n3);
    }

    private static String lambda$getBlockState$2(int n, int n2, int n3) throws Exception {
        return CrashReportCategory.getCoordinateInfo(n, n2, n3);
    }

    private Heightmap lambda$getHeightmap$1(Heightmap.Type type) {
        return new Heightmap(this, type);
    }

    private Entity lambda$new$0(Entity entity2) {
        this.addEntity(entity2);
        return entity2;
    }

    public static enum CreateEntityType {
        IMMEDIATE,
        QUEUED,
        CHECK;

    }
}

