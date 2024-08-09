/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.world.chunk.Chunk;
import net.optifine.ChunkOF;

public class ChunkUtils {
    public static boolean hasEntities(Chunk chunk) {
        if (chunk instanceof ChunkOF) {
            ChunkOF chunkOF = (ChunkOF)chunk;
            return chunkOF.hasEntities();
        }
        return false;
    }

    public static boolean isLoaded(Chunk chunk) {
        if (chunk instanceof ChunkOF) {
            ChunkOF chunkOF = (ChunkOF)chunk;
            return chunkOF.isLoaded();
        }
        return true;
    }
}

