/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.world.ITickList;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerTickList;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.lighting.WorldLightManager;

public class ChunkPrimerWrapper
extends ChunkPrimer {
    private final Chunk chunk;

    public ChunkPrimerWrapper(Chunk chunk) {
        super(chunk.getPos(), UpgradeData.EMPTY);
        this.chunk = chunk;
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        return this.chunk.getTileEntity(blockPos);
    }

    @Override
    @Nullable
    public BlockState getBlockState(BlockPos blockPos) {
        return this.chunk.getBlockState(blockPos);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        return this.chunk.getFluidState(blockPos);
    }

    @Override
    public int getMaxLightLevel() {
        return this.chunk.getMaxLightLevel();
    }

    @Override
    @Nullable
    public BlockState setBlockState(BlockPos blockPos, BlockState blockState, boolean bl) {
        return null;
    }

    @Override
    public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
    }

    @Override
    public void addEntity(Entity entity2) {
    }

    @Override
    public void setStatus(ChunkStatus chunkStatus) {
    }

    @Override
    public ChunkSection[] getSections() {
        return this.chunk.getSections();
    }

    @Override
    @Nullable
    public WorldLightManager getWorldLightManager() {
        return this.chunk.getWorldLightManager();
    }

    @Override
    public void setHeightmap(Heightmap.Type type, long[] lArray) {
    }

    private Heightmap.Type func_209532_c(Heightmap.Type type) {
        if (type == Heightmap.Type.WORLD_SURFACE_WG) {
            return Heightmap.Type.WORLD_SURFACE;
        }
        return type == Heightmap.Type.OCEAN_FLOOR_WG ? Heightmap.Type.OCEAN_FLOOR : type;
    }

    @Override
    public int getTopBlockY(Heightmap.Type type, int n, int n2) {
        return this.chunk.getTopBlockY(this.func_209532_c(type), n, n2);
    }

    @Override
    public ChunkPos getPos() {
        return this.chunk.getPos();
    }

    @Override
    public void setLastSaveTime(long l) {
    }

    @Override
    @Nullable
    public StructureStart<?> func_230342_a_(Structure<?> structure) {
        return this.chunk.func_230342_a_(structure);
    }

    @Override
    public void func_230344_a_(Structure<?> structure, StructureStart<?> structureStart) {
    }

    @Override
    public Map<Structure<?>, StructureStart<?>> getStructureStarts() {
        return this.chunk.getStructureStarts();
    }

    @Override
    public void setStructureStarts(Map<Structure<?>, StructureStart<?>> map) {
    }

    @Override
    public LongSet func_230346_b_(Structure<?> structure) {
        return this.chunk.func_230346_b_(structure);
    }

    @Override
    public void func_230343_a_(Structure<?> structure, long l) {
    }

    @Override
    public Map<Structure<?>, LongSet> getStructureReferences() {
        return this.chunk.getStructureReferences();
    }

    @Override
    public void setStructureReferences(Map<Structure<?>, LongSet> map) {
    }

    @Override
    public BiomeContainer getBiomes() {
        return this.chunk.getBiomes();
    }

    @Override
    public void setModified(boolean bl) {
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public ChunkStatus getStatus() {
        return this.chunk.getStatus();
    }

    @Override
    public void removeTileEntity(BlockPos blockPos) {
    }

    @Override
    public void markBlockForPostprocessing(BlockPos blockPos) {
    }

    @Override
    public void addTileEntity(CompoundNBT compoundNBT) {
    }

    @Override
    @Nullable
    public CompoundNBT getDeferredTileEntity(BlockPos blockPos) {
        return this.chunk.getDeferredTileEntity(blockPos);
    }

    @Override
    @Nullable
    public CompoundNBT getTileEntityNBT(BlockPos blockPos) {
        return this.chunk.getTileEntityNBT(blockPos);
    }

    @Override
    public void setBiomes(BiomeContainer biomeContainer) {
    }

    @Override
    public Stream<BlockPos> getLightSources() {
        return this.chunk.getLightSources();
    }

    @Override
    public ChunkPrimerTickList<Block> getBlocksToBeTicked() {
        return new ChunkPrimerTickList<Block>(ChunkPrimerWrapper::lambda$getBlocksToBeTicked$0, this.getPos());
    }

    @Override
    public ChunkPrimerTickList<Fluid> getFluidsToBeTicked() {
        return new ChunkPrimerTickList<Fluid>(ChunkPrimerWrapper::lambda$getFluidsToBeTicked$1, this.getPos());
    }

    @Override
    public BitSet getCarvingMask(GenerationStage.Carving carving) {
        throw Util.pauseDevMode(new UnsupportedOperationException("Meaningless in this context"));
    }

    @Override
    public BitSet getOrAddCarvingMask(GenerationStage.Carving carving) {
        throw Util.pauseDevMode(new UnsupportedOperationException("Meaningless in this context"));
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    @Override
    public boolean hasLight() {
        return this.chunk.hasLight();
    }

    @Override
    public void setLight(boolean bl) {
        this.chunk.setLight(bl);
    }

    @Override
    public ITickList getFluidsToBeTicked() {
        return this.getFluidsToBeTicked();
    }

    @Override
    public ITickList getBlocksToBeTicked() {
        return this.getBlocksToBeTicked();
    }

    private static boolean lambda$getFluidsToBeTicked$1(Fluid fluid) {
        return fluid == Fluids.EMPTY;
    }

    private static boolean lambda$getBlocksToBeTicked$0(Block block) {
        return block.getDefaultState().isAir();
    }
}

