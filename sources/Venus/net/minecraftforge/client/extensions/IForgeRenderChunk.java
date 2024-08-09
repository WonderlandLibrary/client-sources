/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.client.extensions;

import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IForgeRenderChunk {
    default public ChunkRenderCache createRegionRenderCache(World world, BlockPos blockPos, BlockPos blockPos2, int n) {
        return ChunkRenderCache.generateCache(world, blockPos, blockPos2, n);
    }
}

