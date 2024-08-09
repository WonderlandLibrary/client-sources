/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.math.SectionPos;
import net.minecraft.world.lighting.LevelBasedGraph;

public abstract class SectionDistanceGraph
extends LevelBasedGraph {
    protected SectionDistanceGraph(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected boolean isRoot(long l) {
        return l == Long.MAX_VALUE;
    }

    @Override
    protected void notifyNeighbors(long l, int n, boolean bl) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    long l2 = SectionPos.withOffset(l, i, j, k);
                    if (l2 == l) continue;
                    this.propagateLevel(l, l2, n, bl);
                }
            }
        }
    }

    @Override
    protected int computeLevel(long l, long l2, int n) {
        int n2 = n;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    long l3 = SectionPos.withOffset(l, i, j, k);
                    if (l3 == l) {
                        l3 = Long.MAX_VALUE;
                    }
                    if (l3 == l2) continue;
                    int n3 = this.getEdgeLevel(l3, l, this.getLevel(l3));
                    if (n2 > n3) {
                        n2 = n3;
                    }
                    if (n2 != 0) continue;
                    return n2;
                }
            }
        }
        return n2;
    }

    @Override
    protected int getEdgeLevel(long l, long l2, int n) {
        return l == Long.MAX_VALUE ? this.getSourceLevel(l2) : n + 1;
    }

    protected abstract int getSourceLevel(long var1);

    public void updateSourceLevel(long l, int n, boolean bl) {
        this.scheduleUpdate(Long.MAX_VALUE, l, n, bl);
    }
}

