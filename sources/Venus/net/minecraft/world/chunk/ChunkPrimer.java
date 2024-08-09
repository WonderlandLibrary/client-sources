/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.AbstractCollection;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.world.ITickList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimerTickList;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.lighting.WorldLightManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPrimer
implements IChunk {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ChunkPos pos;
    private volatile boolean modified;
    @Nullable
    private BiomeContainer biomes;
    @Nullable
    private volatile WorldLightManager lightManager;
    private final Map<Heightmap.Type, Heightmap> heightmaps = Maps.newEnumMap(Heightmap.Type.class);
    private volatile ChunkStatus status = ChunkStatus.EMPTY;
    private final Map<BlockPos, TileEntity> tileEntities = Maps.newHashMap();
    private final Map<BlockPos, CompoundNBT> deferredTileEntities = Maps.newHashMap();
    private final ChunkSection[] sections = new ChunkSection[16];
    private final List<CompoundNBT> entities = Lists.newArrayList();
    private final List<BlockPos> lightPositions = Lists.newArrayList();
    private final ShortList[] packedPositions = new ShortList[16];
    private final Map<Structure<?>, StructureStart<?>> structureStartMap = Maps.newHashMap();
    private final Map<Structure<?>, LongSet> structureReferenceMap = Maps.newHashMap();
    private final UpgradeData upgradeData;
    private final ChunkPrimerTickList<Block> pendingBlockTicks;
    private final ChunkPrimerTickList<Fluid> pendingFluidTicks;
    private long inhabitedTime;
    private final Map<GenerationStage.Carving, BitSet> carvingMasks = new Object2ObjectArrayMap<GenerationStage.Carving, BitSet>();
    private volatile boolean hasLight;

    public ChunkPrimer(ChunkPos chunkPos, UpgradeData upgradeData) {
        this(chunkPos, upgradeData, null, new ChunkPrimerTickList<Block>(ChunkPrimer::lambda$new$0, chunkPos), new ChunkPrimerTickList<Fluid>(ChunkPrimer::lambda$new$1, chunkPos));
    }

    public ChunkPrimer(ChunkPos chunkPos, UpgradeData upgradeData, @Nullable ChunkSection[] chunkSectionArray, ChunkPrimerTickList<Block> chunkPrimerTickList, ChunkPrimerTickList<Fluid> chunkPrimerTickList2) {
        this.pos = chunkPos;
        this.upgradeData = upgradeData;
        this.pendingBlockTicks = chunkPrimerTickList;
        this.pendingFluidTicks = chunkPrimerTickList2;
        if (chunkSectionArray != null) {
            if (this.sections.length == chunkSectionArray.length) {
                System.arraycopy(chunkSectionArray, 0, this.sections, 0, this.sections.length);
            } else {
                LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", (Object)chunkSectionArray.length, (Object)this.sections.length);
            }
        }
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        int n = blockPos.getY();
        if (World.isYOutOfBounds(n)) {
            return Blocks.VOID_AIR.getDefaultState();
        }
        ChunkSection chunkSection = this.getSections()[n >> 4];
        return ChunkSection.isEmpty(chunkSection) ? Blocks.AIR.getDefaultState() : chunkSection.getBlockState(blockPos.getX() & 0xF, n & 0xF, blockPos.getZ() & 0xF);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        int n = blockPos.getY();
        if (World.isYOutOfBounds(n)) {
            return Fluids.EMPTY.getDefaultState();
        }
        ChunkSection chunkSection = this.getSections()[n >> 4];
        return ChunkSection.isEmpty(chunkSection) ? Fluids.EMPTY.getDefaultState() : chunkSection.getFluidState(blockPos.getX() & 0xF, n & 0xF, blockPos.getZ() & 0xF);
    }

    @Override
    public Stream<BlockPos> getLightSources() {
        return this.lightPositions.stream();
    }

    public ShortList[] getPackedLightPositions() {
        ShortList[] shortListArray = new ShortList[16];
        for (BlockPos blockPos : this.lightPositions) {
            IChunk.getList(shortListArray, blockPos.getY() >> 4).add(ChunkPrimer.packToLocal(blockPos));
        }
        return shortListArray;
    }

    public void addLightValue(short s, int n) {
        this.addLightPosition(ChunkPrimer.unpackToWorld(s, n, this.pos));
    }

    public void addLightPosition(BlockPos blockPos) {
        this.lightPositions.add(blockPos.toImmutable());
    }

    @Override
    @Nullable
    public BlockState setBlockState(BlockPos blockPos, BlockState blockState, boolean bl) {
        int n = blockPos.getX();
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ();
        if (n2 >= 0 && n2 < 256) {
            Heightmap.Type type;
            Object object;
            if (this.sections[n2 >> 4] == Chunk.EMPTY_SECTION && blockState.isIn(Blocks.AIR)) {
                return blockState;
            }
            if (blockState.getLightValue() > 0) {
                this.lightPositions.add(new BlockPos((n & 0xF) + this.getPos().getXStart(), n2, (n3 & 0xF) + this.getPos().getZStart()));
            }
            ChunkSection chunkSection = this.getSection(n2 >> 4);
            BlockState blockState2 = chunkSection.setBlockState(n & 0xF, n2 & 0xF, n3 & 0xF, blockState);
            if (this.status.isAtLeast(ChunkStatus.FEATURES) && blockState != blockState2 && (blockState.getOpacity(this, blockPos) != blockState2.getOpacity(this, blockPos) || blockState.getLightValue() != blockState2.getLightValue() || blockState.isTransparent() || blockState2.isTransparent())) {
                object = this.getWorldLightManager();
                ((WorldLightManager)object).checkBlock(blockPos);
            }
            object = this.getStatus().getHeightMaps();
            EnumSet<Heightmap.Type> enumSet = null;
            Iterator iterator2 = ((AbstractCollection)object).iterator();
            while (iterator2.hasNext()) {
                type = (Heightmap.Type)iterator2.next();
                Heightmap heightmap = this.heightmaps.get(type);
                if (heightmap != null) continue;
                if (enumSet == null) {
                    enumSet = EnumSet.noneOf(Heightmap.Type.class);
                }
                enumSet.add(type);
            }
            if (enumSet != null) {
                Heightmap.updateChunkHeightmaps(this, enumSet);
            }
            iterator2 = ((AbstractCollection)object).iterator();
            while (iterator2.hasNext()) {
                type = (Heightmap.Type)iterator2.next();
                this.heightmaps.get(type).update(n & 0xF, n2, n3 & 0xF, blockState);
            }
            return blockState2;
        }
        return Blocks.VOID_AIR.getDefaultState();
    }

    public ChunkSection getSection(int n) {
        if (this.sections[n] == Chunk.EMPTY_SECTION) {
            this.sections[n] = new ChunkSection(n << 4);
        }
        return this.sections[n];
    }

    @Override
    public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
        tileEntity.setPos(blockPos);
        this.tileEntities.put(blockPos, tileEntity);
    }

    @Override
    public Set<BlockPos> getTileEntitiesPos() {
        HashSet<BlockPos> hashSet = Sets.newHashSet(this.deferredTileEntities.keySet());
        hashSet.addAll(this.tileEntities.keySet());
        return hashSet;
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        return this.tileEntities.get(blockPos);
    }

    public Map<BlockPos, TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    public void addEntity(CompoundNBT compoundNBT) {
        this.entities.add(compoundNBT);
    }

    @Override
    public void addEntity(Entity entity2) {
        if (!entity2.isPassenger()) {
            CompoundNBT compoundNBT = new CompoundNBT();
            entity2.writeUnlessPassenger(compoundNBT);
            this.addEntity(compoundNBT);
        }
    }

    public List<CompoundNBT> getEntities() {
        return this.entities;
    }

    public void setBiomes(BiomeContainer biomeContainer) {
        this.biomes = biomeContainer;
    }

    @Override
    @Nullable
    public BiomeContainer getBiomes() {
        return this.biomes;
    }

    @Override
    public void setModified(boolean bl) {
        this.modified = bl;
    }

    @Override
    public boolean isModified() {
        return this.modified;
    }

    @Override
    public ChunkStatus getStatus() {
        return this.status;
    }

    public void setStatus(ChunkStatus chunkStatus) {
        this.status = chunkStatus;
        this.setModified(false);
    }

    @Override
    public ChunkSection[] getSections() {
        return this.sections;
    }

    @Nullable
    public WorldLightManager getWorldLightManager() {
        return this.lightManager;
    }

    @Override
    public Collection<Map.Entry<Heightmap.Type, Heightmap>> getHeightmaps() {
        return Collections.unmodifiableSet(this.heightmaps.entrySet());
    }

    @Override
    public void setHeightmap(Heightmap.Type type, long[] lArray) {
        this.getHeightmap(type).setDataArray(lArray);
    }

    @Override
    public Heightmap getHeightmap(Heightmap.Type type) {
        return this.heightmaps.computeIfAbsent(type, this::lambda$getHeightmap$2);
    }

    @Override
    public int getTopBlockY(Heightmap.Type type, int n, int n2) {
        Heightmap heightmap = this.heightmaps.get(type);
        if (heightmap == null) {
            Heightmap.updateChunkHeightmaps(this, EnumSet.of(type));
            heightmap = this.heightmaps.get(type);
        }
        return heightmap.getHeight(n & 0xF, n2 & 0xF) - 1;
    }

    @Override
    public ChunkPos getPos() {
        return this.pos;
    }

    @Override
    public void setLastSaveTime(long l) {
    }

    @Override
    @Nullable
    public StructureStart<?> func_230342_a_(Structure<?> structure) {
        return this.structureStartMap.get(structure);
    }

    @Override
    public void func_230344_a_(Structure<?> structure, StructureStart<?> structureStart) {
        this.structureStartMap.put(structure, structureStart);
        this.modified = true;
    }

    @Override
    public Map<Structure<?>, StructureStart<?>> getStructureStarts() {
        return Collections.unmodifiableMap(this.structureStartMap);
    }

    @Override
    public void setStructureStarts(Map<Structure<?>, StructureStart<?>> map) {
        this.structureStartMap.clear();
        this.structureStartMap.putAll(map);
        this.modified = true;
    }

    @Override
    public LongSet func_230346_b_(Structure<?> structure) {
        return this.structureReferenceMap.computeIfAbsent(structure, ChunkPrimer::lambda$func_230346_b_$3);
    }

    @Override
    public void func_230343_a_(Structure<?> structure, long l) {
        this.structureReferenceMap.computeIfAbsent(structure, ChunkPrimer::lambda$func_230343_a_$4).add(l);
        this.modified = true;
    }

    @Override
    public Map<Structure<?>, LongSet> getStructureReferences() {
        return Collections.unmodifiableMap(this.structureReferenceMap);
    }

    @Override
    public void setStructureReferences(Map<Structure<?>, LongSet> map) {
        this.structureReferenceMap.clear();
        this.structureReferenceMap.putAll(map);
        this.modified = true;
    }

    public static short packToLocal(BlockPos blockPos) {
        int n = blockPos.getX();
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ();
        int n4 = n & 0xF;
        int n5 = n2 & 0xF;
        int n6 = n3 & 0xF;
        return (short)(n4 | n5 << 4 | n6 << 8);
    }

    public static BlockPos unpackToWorld(short s, int n, ChunkPos chunkPos) {
        int n2 = (s & 0xF) + (chunkPos.x << 4);
        int n3 = (s >>> 4 & 0xF) + (n << 4);
        int n4 = (s >>> 8 & 0xF) + (chunkPos.z << 4);
        return new BlockPos(n2, n3, n4);
    }

    @Override
    public void markBlockForPostprocessing(BlockPos blockPos) {
        if (!World.isOutsideBuildHeight(blockPos)) {
            IChunk.getList(this.packedPositions, blockPos.getY() >> 4).add(ChunkPrimer.packToLocal(blockPos));
        }
    }

    @Override
    public ShortList[] getPackedPositions() {
        return this.packedPositions;
    }

    @Override
    public void addPackedPosition(short s, int n) {
        IChunk.getList(this.packedPositions, n).add(s);
    }

    public ChunkPrimerTickList<Block> getBlocksToBeTicked() {
        return this.pendingBlockTicks;
    }

    public ChunkPrimerTickList<Fluid> getFluidsToBeTicked() {
        return this.pendingFluidTicks;
    }

    @Override
    public UpgradeData getUpgradeData() {
        return this.upgradeData;
    }

    @Override
    public void setInhabitedTime(long l) {
        this.inhabitedTime = l;
    }

    @Override
    public long getInhabitedTime() {
        return this.inhabitedTime;
    }

    @Override
    public void addTileEntity(CompoundNBT compoundNBT) {
        this.deferredTileEntities.put(new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z")), compoundNBT);
    }

    public Map<BlockPos, CompoundNBT> getDeferredTileEntities() {
        return Collections.unmodifiableMap(this.deferredTileEntities);
    }

    @Override
    public CompoundNBT getDeferredTileEntity(BlockPos blockPos) {
        return this.deferredTileEntities.get(blockPos);
    }

    @Override
    @Nullable
    public CompoundNBT getTileEntityNBT(BlockPos blockPos) {
        TileEntity tileEntity = this.getTileEntity(blockPos);
        return tileEntity != null ? tileEntity.write(new CompoundNBT()) : this.deferredTileEntities.get(blockPos);
    }

    @Override
    public void removeTileEntity(BlockPos blockPos) {
        this.tileEntities.remove(blockPos);
        this.deferredTileEntities.remove(blockPos);
    }

    @Nullable
    public BitSet getCarvingMask(GenerationStage.Carving carving) {
        return this.carvingMasks.get(carving);
    }

    public BitSet getOrAddCarvingMask(GenerationStage.Carving carving) {
        return this.carvingMasks.computeIfAbsent(carving, ChunkPrimer::lambda$getOrAddCarvingMask$5);
    }

    public void setCarvingMask(GenerationStage.Carving carving, BitSet bitSet) {
        this.carvingMasks.put(carving, bitSet);
    }

    public void setLightManager(WorldLightManager worldLightManager) {
        this.lightManager = worldLightManager;
    }

    @Override
    public boolean hasLight() {
        return this.hasLight;
    }

    @Override
    public void setLight(boolean bl) {
        this.hasLight = bl;
        this.setModified(false);
    }

    public ITickList getFluidsToBeTicked() {
        return this.getFluidsToBeTicked();
    }

    public ITickList getBlocksToBeTicked() {
        return this.getBlocksToBeTicked();
    }

    private static BitSet lambda$getOrAddCarvingMask$5(GenerationStage.Carving carving) {
        return new BitSet(65536);
    }

    private static LongSet lambda$func_230343_a_$4(Structure structure) {
        return new LongOpenHashSet();
    }

    private static LongSet lambda$func_230346_b_$3(Structure structure) {
        return new LongOpenHashSet();
    }

    private Heightmap lambda$getHeightmap$2(Heightmap.Type type) {
        return new Heightmap(this, type);
    }

    private static boolean lambda$new$1(Fluid fluid) {
        return fluid == null || fluid == Fluids.EMPTY;
    }

    private static boolean lambda$new$0(Block block) {
        return block == null || block.getDefaultState().isAir();
    }
}

