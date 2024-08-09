/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.optifine.override.ChunkCacheOF;

public class LightCacheOF {
    public static final float getBrightness(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        float f = blockState.getAmbientOcclusionLightValue(iBlockDisplayReader, blockPos);
        return BlockModelRenderer.fixAoLightValue(f);
    }

    public static final int getPackedLight(BlockState blockState, IBlockDisplayReader iBlockDisplayReader, BlockPos blockPos) {
        if (iBlockDisplayReader instanceof ChunkCacheOF) {
            ChunkCacheOF chunkCacheOF = (ChunkCacheOF)iBlockDisplayReader;
            int[] nArray = chunkCacheOF.getCombinedLights();
            int n = chunkCacheOF.getPositionIndex(blockPos);
            if (n >= 0 && n < nArray.length && nArray != null) {
                int n2 = nArray[n];
                if (n2 == -1) {
                    nArray[n] = n2 = WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState, blockPos);
                }
                return n2;
            }
            return WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState, blockPos);
        }
        return WorldRenderer.getPackedLightmapCoords(iBlockDisplayReader, blockState, blockPos);
    }
}

