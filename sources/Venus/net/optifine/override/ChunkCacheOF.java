/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.override;

import java.util.Arrays;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.lighting.WorldLightManager;
import net.optifine.BlockPosM;
import net.optifine.render.RenderEnv;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF
implements IBlockDisplayReader {
    private final ChunkRenderCache chunkCache;
    private final int posX;
    private final int posY;
    private final int posZ;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    private final int sizeXZ;
    private int[] combinedLights;
    private BlockState[] blockStates;
    private Biome[] biomes;
    private final int arraySize;
    private RenderEnv renderEnv;
    private static final ArrayCache cacheCombinedLights = new ArrayCache(Integer.TYPE, 16);
    private static final ArrayCache cacheBlockStates = new ArrayCache(BlockState.class, 16);
    private static final ArrayCache cacheBiomes = new ArrayCache(Biome.class, 16);

    public ChunkCacheOF(ChunkRenderCache chunkRenderCache, BlockPos blockPos, BlockPos blockPos2, int n) {
        this.chunkCache = chunkRenderCache;
        int n2 = blockPos.getX() - n >> 4;
        int n3 = blockPos.getY() - n >> 4;
        int n4 = blockPos.getZ() - n >> 4;
        int n5 = blockPos2.getX() + n >> 4;
        int n6 = blockPos2.getY() + n >> 4;
        int n7 = blockPos2.getZ() + n >> 4;
        this.sizeX = n5 - n2 + 1 << 4;
        this.sizeY = n6 - n3 + 1 << 4;
        this.sizeZ = n7 - n4 + 1 << 4;
        this.sizeXZ = this.sizeX * this.sizeZ;
        this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
        this.posX = n2 << 4;
        this.posY = n3 << 4;
        this.posZ = n4 << 4;
    }

    public int getPositionIndex(BlockPos blockPos) {
        int n = blockPos.getX() - this.posX;
        if (n >= 0 && n < this.sizeX) {
            int n2 = blockPos.getY() - this.posY;
            if (n2 >= 0 && n2 < this.sizeY) {
                int n3 = blockPos.getZ() - this.posZ;
                return n3 >= 0 && n3 < this.sizeZ ? n2 * this.sizeXZ + n3 * this.sizeX + n : -1;
            }
            return 1;
        }
        return 1;
    }

    @Override
    public int getLightFor(LightType lightType, BlockPos blockPos) {
        return this.chunkCache.getLightFor(lightType, blockPos);
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        int n = this.getPositionIndex(blockPos);
        if (n >= 0 && n < this.arraySize && this.blockStates != null) {
            BlockState blockState = this.blockStates[n];
            if (blockState == null) {
                this.blockStates[n] = blockState = this.chunkCache.getBlockState(blockPos);
            }
            return blockState;
        }
        return this.chunkCache.getBlockState(blockPos);
    }

    public void renderStart() {
        if (this.combinedLights == null) {
            this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize);
        }
        if (this.blockStates == null) {
            this.blockStates = (BlockState[])cacheBlockStates.allocate(this.arraySize);
        }
        if (this.biomes == null) {
            this.biomes = (Biome[])cacheBiomes.allocate(this.arraySize);
        }
        Arrays.fill(this.combinedLights, -1);
        Arrays.fill(this.blockStates, null);
        Arrays.fill(this.biomes, null);
        this.loadBlockStates();
    }

    private void loadBlockStates() {
        if (this.sizeX == 48 && this.sizeY == 48 && this.sizeZ == 48) {
            Chunk chunk = this.chunkCache.getChunk(1, 1);
            BlockPosM blockPosM = new BlockPosM();
            for (int i = 16; i < 32; ++i) {
                int n = i * this.sizeXZ;
                for (int j = 16; j < 32; ++j) {
                    int n2 = j * this.sizeX;
                    for (int k = 16; k < 32; ++k) {
                        BlockState blockState;
                        blockPosM.setXyz(this.posX + k, this.posY + i, this.posZ + j);
                        int n3 = n + n2 + k;
                        this.blockStates[n3] = blockState = chunk.getBlockState(blockPosM);
                    }
                }
            }
        }
    }

    public void renderFinish() {
        cacheCombinedLights.free(this.combinedLights);
        this.combinedLights = null;
        cacheBlockStates.free(this.blockStates);
        this.blockStates = null;
        cacheBiomes.free(this.biomes);
        this.biomes = null;
    }

    public int[] getCombinedLights() {
        return this.combinedLights;
    }

    public Biome getBiome(BlockPos blockPos) {
        int n = this.getPositionIndex(blockPos);
        if (n >= 0 && n < this.arraySize && this.biomes != null) {
            Biome biome = this.biomes[n];
            if (biome == null) {
                this.biomes[n] = biome = this.chunkCache.getBiome(blockPos);
            }
            return biome;
        }
        return this.chunkCache.getBiome(blockPos);
    }

    @Override
    public TileEntity getTileEntity(BlockPos blockPos) {
        return this.chunkCache.getTileEntity(blockPos, Chunk.CreateEntityType.CHECK);
    }

    public TileEntity getTileEntity(BlockPos blockPos, Chunk.CreateEntityType createEntityType) {
        return this.chunkCache.getTileEntity(blockPos, createEntityType);
    }

    @Override
    public boolean canSeeSky(BlockPos blockPos) {
        return this.chunkCache.canSeeSky(blockPos);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        return this.getBlockState(blockPos).getFluidState();
    }

    @Override
    public int getBlockColor(BlockPos blockPos, ColorResolver colorResolver) {
        return this.chunkCache.getBlockColor(blockPos, colorResolver);
    }

    @Override
    public WorldLightManager getLightManager() {
        return this.chunkCache.getLightManager();
    }

    public RenderEnv getRenderEnv() {
        return this.renderEnv;
    }

    public void setRenderEnv(RenderEnv renderEnv) {
        this.renderEnv = renderEnv;
    }

    @Override
    public float func_230487_a_(Direction direction, boolean bl) {
        return this.chunkCache.func_230487_a_(direction, bl);
    }
}

