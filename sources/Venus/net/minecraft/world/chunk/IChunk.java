/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IStructureReader;
import net.minecraft.world.ITickList;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import org.apache.logging.log4j.LogManager;

public interface IChunk
extends IBlockReader,
IStructureReader {
    @Nullable
    public BlockState setBlockState(BlockPos var1, BlockState var2, boolean var3);

    public void addTileEntity(BlockPos var1, TileEntity var2);

    public void addEntity(Entity var1);

    @Nullable
    default public ChunkSection getLastExtendedBlockStorage() {
        ChunkSection[] chunkSectionArray = this.getSections();
        for (int i = chunkSectionArray.length - 1; i >= 0; --i) {
            ChunkSection chunkSection = chunkSectionArray[i];
            if (ChunkSection.isEmpty(chunkSection)) continue;
            return chunkSection;
        }
        return null;
    }

    default public int getTopFilledSegment() {
        ChunkSection chunkSection = this.getLastExtendedBlockStorage();
        return chunkSection == null ? 0 : chunkSection.getYLocation();
    }

    public Set<BlockPos> getTileEntitiesPos();

    public ChunkSection[] getSections();

    public Collection<Map.Entry<Heightmap.Type, Heightmap>> getHeightmaps();

    public void setHeightmap(Heightmap.Type var1, long[] var2);

    public Heightmap getHeightmap(Heightmap.Type var1);

    public int getTopBlockY(Heightmap.Type var1, int var2, int var3);

    public ChunkPos getPos();

    public void setLastSaveTime(long var1);

    public Map<Structure<?>, StructureStart<?>> getStructureStarts();

    public void setStructureStarts(Map<Structure<?>, StructureStart<?>> var1);

    default public boolean isEmptyBetween(int n, int n2) {
        if (n < 0) {
            n = 0;
        }
        if (n2 >= 256) {
            n2 = 255;
        }
        for (int i = n; i <= n2; i += 16) {
            if (ChunkSection.isEmpty(this.getSections()[i >> 4])) continue;
            return true;
        }
        return false;
    }

    @Nullable
    public BiomeContainer getBiomes();

    public void setModified(boolean var1);

    public boolean isModified();

    public ChunkStatus getStatus();

    public void removeTileEntity(BlockPos var1);

    default public void markBlockForPostprocessing(BlockPos blockPos) {
        LogManager.getLogger().warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", (Object)blockPos);
    }

    public ShortList[] getPackedPositions();

    default public void addPackedPosition(short s, int n) {
        IChunk.getList(this.getPackedPositions(), n).add(s);
    }

    default public void addTileEntity(CompoundNBT compoundNBT) {
        LogManager.getLogger().warn("Trying to set a BlockEntity, but this operation is not supported.");
    }

    @Nullable
    public CompoundNBT getDeferredTileEntity(BlockPos var1);

    @Nullable
    public CompoundNBT getTileEntityNBT(BlockPos var1);

    public Stream<BlockPos> getLightSources();

    public ITickList<Block> getBlocksToBeTicked();

    public ITickList<Fluid> getFluidsToBeTicked();

    public UpgradeData getUpgradeData();

    public void setInhabitedTime(long var1);

    public long getInhabitedTime();

    public static ShortList getList(ShortList[] shortListArray, int n) {
        if (shortListArray[n] == null) {
            shortListArray[n] = new ShortArrayList();
        }
        return shortListArray[n];
    }

    public boolean hasLight();

    public void setLight(boolean var1);
}

