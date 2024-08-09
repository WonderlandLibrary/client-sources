/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.lighting.LevelBasedGraph;

public abstract class ChunkDistanceGraph
extends LevelBasedGraph {
    protected ChunkDistanceGraph(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected boolean isRoot(long l) {
        return l == ChunkPos.SENTINEL;
    }

    @Override
    protected void notifyNeighbors(long l, int n, boolean bl) {
        ChunkPos chunkPos = new ChunkPos(l);
        int n2 = chunkPos.x;
        int n3 = chunkPos.z;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                long l2 = ChunkPos.asLong(n2 + i, n3 + j);
                if (l2 == l) continue;
                this.propagateLevel(l, l2, n, bl);
            }
        }
    }

    @Override
    protected int computeLevel(long l, long l2, int n) {
        int n2 = n;
        ChunkPos chunkPos = new ChunkPos(l);
        int n3 = chunkPos.x;
        int n4 = chunkPos.z;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                long l3 = ChunkPos.asLong(n3 + i, n4 + j);
                if (l3 == l) {
                    l3 = ChunkPos.SENTINEL;
                }
                if (l3 == l2) continue;
                int n5 = this.getEdgeLevel(l3, l, this.getLevel(l3));
                if (n2 > n5) {
                    n2 = n5;
                }
                if (n2 != 0) continue;
                return n2;
            }
        }
        return n2;
    }

    @Override
    protected int getEdgeLevel(long l, long l2, int n) {
        return l == ChunkPos.SENTINEL ? this.getSourceLevel(l2) : n + 1;
    }

    protected abstract int getSourceLevel(long var1);

    public void updateSourceLevel(long l, int n, boolean bl) {
        this.scheduleUpdate(ChunkPos.SENTINEL, l, n, bl);
    }
}

