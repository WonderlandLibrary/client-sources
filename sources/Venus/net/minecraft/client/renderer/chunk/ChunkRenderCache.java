/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.lighting.WorldLightManager;

public class ChunkRenderCache
implements IBlockDisplayReader {
    protected final int chunkStartX;
    protected final int chunkStartZ;
    protected final BlockPos cacheStartPos;
    protected final int cacheSizeX;
    protected final int cacheSizeY;
    protected final int cacheSizeZ;
    protected final Chunk[][] chunks;
    protected final BlockState[] blockStates;
    protected final FluidState[] fluidStates;
    protected final World world;

    @Nullable
    public static ChunkRenderCache generateCache(World world, BlockPos blockPos, BlockPos blockPos2, int n) {
        return ChunkRenderCache.generateCache(world, blockPos, blockPos2, n, true);
    }

    public static ChunkRenderCache generateCache(World world, BlockPos blockPos, BlockPos blockPos2, int n, boolean bl) {
        int n2;
        int n3 = blockPos.getX() - n >> 4;
        int n4 = blockPos.getZ() - n >> 4;
        int n5 = blockPos2.getX() + n >> 4;
        int n6 = blockPos2.getZ() + n >> 4;
        Chunk[][] chunkArray = new Chunk[n5 - n3 + 1][n6 - n4 + 1];
        for (n2 = n3; n2 <= n5; ++n2) {
            for (int i = n4; i <= n6; ++i) {
                chunkArray[n2 - n3][i - n4] = world.getChunk(n2, i);
            }
        }
        if (bl && ChunkRenderCache.func_241718_a_(blockPos, blockPos2, n3, n4, chunkArray)) {
            return null;
        }
        n2 = 1;
        BlockPos blockPos3 = blockPos.add(-1, -1, -1);
        BlockPos blockPos4 = blockPos2.add(1, 1, 1);
        return new ChunkRenderCache(world, n3, n4, chunkArray, blockPos3, blockPos4);
    }

    public static boolean func_241718_a_(BlockPos blockPos, BlockPos blockPos2, int n, int n2, Chunk[][] chunkArray) {
        for (int i = blockPos.getX() >> 4; i <= blockPos2.getX() >> 4; ++i) {
            for (int j = blockPos.getZ() >> 4; j <= blockPos2.getZ() >> 4; ++j) {
                Chunk chunk = chunkArray[i - n][j - n2];
                if (chunk.isEmptyBetween(blockPos.getY(), blockPos2.getY())) continue;
                return true;
            }
        }
        return false;
    }

    public ChunkRenderCache(World world, int n, int n2, Chunk[][] chunkArray, BlockPos blockPos, BlockPos blockPos2) {
        this.world = world;
        this.chunkStartX = n;
        this.chunkStartZ = n2;
        this.chunks = chunkArray;
        this.cacheStartPos = blockPos;
        this.cacheSizeX = blockPos2.getX() - blockPos.getX() + 1;
        this.cacheSizeY = blockPos2.getY() - blockPos.getY() + 1;
        this.cacheSizeZ = blockPos2.getZ() - blockPos.getZ() + 1;
        this.blockStates = null;
        this.fluidStates = null;
    }

    protected final int getIndex(BlockPos blockPos) {
        return this.getIndex(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    protected int getIndex(int n, int n2, int n3) {
        int n4 = n - this.cacheStartPos.getX();
        int n5 = n2 - this.cacheStartPos.getY();
        int n6 = n3 - this.cacheStartPos.getZ();
        return n6 * this.cacheSizeX * this.cacheSizeY + n5 * this.cacheSizeX + n4;
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        int n = (blockPos.getX() >> 4) - this.chunkStartX;
        int n2 = (blockPos.getZ() >> 4) - this.chunkStartZ;
        return this.chunks[n][n2].getBlockState(blockPos);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        int n = (blockPos.getX() >> 4) - this.chunkStartX;
        int n2 = (blockPos.getZ() >> 4) - this.chunkStartZ;
        return this.chunks[n][n2].getFluidState(blockPos);
    }

    @Override
    public float func_230487_a_(Direction direction, boolean bl) {
        return this.world.func_230487_a_(direction, bl);
    }

    @Override
    public WorldLightManager getLightManager() {
        return this.world.getLightManager();
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        return this.getTileEntity(blockPos, Chunk.CreateEntityType.IMMEDIATE);
    }

    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos, Chunk.CreateEntityType createEntityType) {
        int n = (blockPos.getX() >> 4) - this.chunkStartX;
        int n2 = (blockPos.getZ() >> 4) - this.chunkStartZ;
        return this.chunks[n][n2].getTileEntity(blockPos, createEntityType);
    }

    @Override
    public int getBlockColor(BlockPos blockPos, ColorResolver colorResolver) {
        return this.world.getBlockColor(blockPos, colorResolver);
    }

    public Biome getBiome(BlockPos blockPos) {
        return this.world.getBiome(blockPos);
    }

    public Chunk getChunk(int n, int n2) {
        return this.chunks[n][n2];
    }
}

