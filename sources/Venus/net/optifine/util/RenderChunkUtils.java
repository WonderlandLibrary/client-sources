/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.ChunkSection;

public class RenderChunkUtils {
    public static int getCountBlocks(ChunkRenderDispatcher.ChunkRender chunkRender) {
        ChunkSection[] chunkSectionArray = chunkRender.getChunk().getSections();
        if (chunkSectionArray == null) {
            return 1;
        }
        int n = chunkRender.getPosition().getY() >> 4;
        ChunkSection chunkSection = chunkSectionArray[n];
        return chunkSection == null ? (short)0 : chunkSection.getBlockRefCount();
    }

    public static double getRelativeBufferSize(ChunkRenderDispatcher.ChunkRender chunkRender) {
        int n = RenderChunkUtils.getCountBlocks(chunkRender);
        return RenderChunkUtils.getRelativeBufferSize(n);
    }

    public static double getRelativeBufferSize(int n) {
        double d = (double)n / 4096.0;
        double d2 = (d *= 0.995) * 2.0 - 1.0;
        d2 = MathHelper.clamp(d2, -1.0, 1.0);
        return MathHelper.sqrt(1.0 - d2 * d2);
    }
}

