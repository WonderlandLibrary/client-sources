/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.level.ColorResolver;

public interface IWorldReader
extends IBlockDisplayReader,
ICollisionReader,
BiomeManager.IBiomeReader {
    @Nullable
    public IChunk getChunk(int var1, int var2, ChunkStatus var3, boolean var4);

    @Deprecated
    public boolean chunkExists(int var1, int var2);

    public int getHeight(Heightmap.Type var1, int var2, int var3);

    public int getSkylightSubtracted();

    public BiomeManager getBiomeManager();

    default public Biome getBiome(BlockPos blockPos) {
        return this.getBiomeManager().getBiome(blockPos);
    }

    default public Stream<BlockState> getStatesInArea(AxisAlignedBB axisAlignedBB) {
        int n;
        int n2 = MathHelper.floor(axisAlignedBB.minX);
        int n3 = MathHelper.floor(axisAlignedBB.maxX);
        int n4 = MathHelper.floor(axisAlignedBB.minY);
        int n5 = MathHelper.floor(axisAlignedBB.maxY);
        int n6 = MathHelper.floor(axisAlignedBB.minZ);
        return this.isAreaLoaded(n2, n4, n6, n3, n5, n = MathHelper.floor(axisAlignedBB.maxZ)) ? this.func_234853_a_(axisAlignedBB) : Stream.empty();
    }

    @Override
    default public int getBlockColor(BlockPos blockPos, ColorResolver colorResolver) {
        return colorResolver.getColor(this.getBiome(blockPos), blockPos.getX(), blockPos.getZ());
    }

    @Override
    default public Biome getNoiseBiome(int n, int n2, int n3) {
        IChunk iChunk = this.getChunk(n >> 2, n3 >> 2, ChunkStatus.BIOMES, false);
        return iChunk != null && iChunk.getBiomes() != null ? iChunk.getBiomes().getNoiseBiome(n, n2, n3) : this.getNoiseBiomeRaw(n, n2, n3);
    }

    public Biome getNoiseBiomeRaw(int var1, int var2, int var3);

    public boolean isRemote();

    @Deprecated
    public int getSeaLevel();

    public DimensionType getDimensionType();

    default public BlockPos getHeight(Heightmap.Type type, BlockPos blockPos) {
        return new BlockPos(blockPos.getX(), this.getHeight(type, blockPos.getX(), blockPos.getZ()), blockPos.getZ());
    }

    default public boolean isAirBlock(BlockPos blockPos) {
        return this.getBlockState(blockPos).isAir();
    }

    default public boolean canBlockSeeSky(BlockPos blockPos) {
        if (blockPos.getY() >= this.getSeaLevel()) {
            return this.canSeeSky(blockPos);
        }
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), this.getSeaLevel(), blockPos.getZ());
        if (!this.canSeeSky(blockPos2)) {
            return true;
        }
        BlockPos blockPos3 = blockPos2.down();
        while (blockPos3.getY() > blockPos.getY()) {
            BlockState blockState = this.getBlockState(blockPos3);
            if (blockState.getOpacity(this, blockPos3) > 0 && !blockState.getMaterial().isLiquid()) {
                return true;
            }
            blockPos3 = blockPos3.down();
        }
        return false;
    }

    @Deprecated
    default public float getBrightness(BlockPos blockPos) {
        return this.getDimensionType().getAmbientLight(this.getLight(blockPos));
    }

    default public int getStrongPower(BlockPos blockPos, Direction direction) {
        return this.getBlockState(blockPos).getStrongPower(this, blockPos, direction);
    }

    default public IChunk getChunk(BlockPos blockPos) {
        return this.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    default public IChunk getChunk(int n, int n2) {
        return this.getChunk(n, n2, ChunkStatus.FULL, true);
    }

    default public IChunk getChunk(int n, int n2, ChunkStatus chunkStatus) {
        return this.getChunk(n, n2, chunkStatus, true);
    }

    @Override
    @Nullable
    default public IBlockReader getBlockReader(int n, int n2) {
        return this.getChunk(n, n2, ChunkStatus.EMPTY, false);
    }

    default public boolean hasWater(BlockPos blockPos) {
        return this.getFluidState(blockPos).isTagged(FluidTags.WATER);
    }

    default public boolean containsAnyLiquid(AxisAlignedBB axisAlignedBB) {
        int n = MathHelper.floor(axisAlignedBB.minX);
        int n2 = MathHelper.ceil(axisAlignedBB.maxX);
        int n3 = MathHelper.floor(axisAlignedBB.minY);
        int n4 = MathHelper.ceil(axisAlignedBB.maxY);
        int n5 = MathHelper.floor(axisAlignedBB.minZ);
        int n6 = MathHelper.ceil(axisAlignedBB.maxZ);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n; i < n2; ++i) {
            for (int j = n3; j < n4; ++j) {
                for (int k = n5; k < n6; ++k) {
                    BlockState blockState = this.getBlockState(mutable.setPos(i, j, k));
                    if (blockState.getFluidState().isEmpty()) continue;
                    return false;
                }
            }
        }
        return true;
    }

    default public int getLight(BlockPos blockPos) {
        return this.getNeighborAwareLightSubtracted(blockPos, this.getSkylightSubtracted());
    }

    default public int getNeighborAwareLightSubtracted(BlockPos blockPos, int n) {
        return blockPos.getX() >= -30000000 && blockPos.getZ() >= -30000000 && blockPos.getX() < 30000000 && blockPos.getZ() < 30000000 ? this.getLightSubtracted(blockPos, n) : 15;
    }

    @Deprecated
    default public boolean isBlockLoaded(BlockPos blockPos) {
        return this.chunkExists(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    @Deprecated
    default public boolean isAreaLoaded(BlockPos blockPos, BlockPos blockPos2) {
        return this.isAreaLoaded(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }

    @Deprecated
    default public boolean isAreaLoaded(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n5 >= 0 && n2 < 256) {
            n3 >>= 4;
            n4 >>= 4;
            n6 >>= 4;
            for (int i = n >>= 4; i <= n4; ++i) {
                for (int j = n3; j <= n6; ++j) {
                    if (this.chunkExists(i, j)) continue;
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}

