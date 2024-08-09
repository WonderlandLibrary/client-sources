/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.optifine.shaders.IteratorRenderChunks;
import net.optifine.shaders.Shaders;

public class ShadowUtils {
    public static Iterator<ChunkRenderDispatcher.ChunkRender> makeShadowChunkIterator(ClientWorld clientWorld, double d, Entity entity2, int n, ViewFrustum viewFrustum) {
        float f = Shaders.getShadowRenderDistance();
        if (!(f <= 0.0f) && !(f >= (float)((n - 1) * 16))) {
            int n2 = MathHelper.ceil(f / 16.0f) + 1;
            float f2 = clientWorld.getCelestialAngleRadians((float)d);
            float f3 = Shaders.sunPathRotation * MathHelper.deg2Rad;
            float f4 = f2 > MathHelper.PId2 && f2 < 3.0f * MathHelper.PId2 ? f2 + MathHelper.PI : f2;
            float f5 = -MathHelper.sin(f4);
            float f6 = MathHelper.cos(f4) * MathHelper.cos(f3);
            float f7 = -MathHelper.cos(f4) * MathHelper.sin(f3);
            BlockPos blockPos = new BlockPos(MathHelper.floor(entity2.getPosX()) >> 4, MathHelper.floor(entity2.getPosY()) >> 4, MathHelper.floor(entity2.getPosZ()) >> 4);
            BlockPos blockPos2 = blockPos.add(-f5 * (float)n2, -f6 * (float)n2, -f7 * (float)n2);
            BlockPos blockPos3 = blockPos.add(f5 * (float)n, f6 * (float)n, f7 * (float)n);
            return new IteratorRenderChunks(viewFrustum, blockPos2, blockPos3, n2, n2);
        }
        List<ChunkRenderDispatcher.ChunkRender> list = Arrays.asList(viewFrustum.renderChunks);
        return list.iterator();
    }
}

