/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.world;

import net.minecraft.util.BlockPos;

public class ChunkCoordIntPair {
    public final int chunkXPos;
    public final int chunkZPos;
    private static final String __OBFID = "CL_00000133";
    private int cachedHashCode = 0;

    public ChunkCoordIntPair(int x2, int z2) {
        this.chunkXPos = x2;
        this.chunkZPos = z2;
    }

    public static long chunkXZ2Int(int x2, int z2) {
        return (long)x2 & 0xFFFFFFFFL | ((long)z2 & 0xFFFFFFFFL) << 32;
    }

    public int hashCode() {
        if (this.cachedHashCode == 0) {
            int var1 = 1664525 * this.chunkXPos + 1013904223;
            int var2 = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
            this.cachedHashCode = var1 ^ var2;
        }
        return this.cachedHashCode;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChunkCoordIntPair)) {
            return false;
        }
        ChunkCoordIntPair var2 = (ChunkCoordIntPair)p_equals_1_;
        return this.chunkXPos == var2.chunkXPos && this.chunkZPos == var2.chunkZPos;
    }

    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZPosition() {
        return (this.chunkZPos << 4) + 8;
    }

    public int getXStart() {
        return this.chunkXPos << 4;
    }

    public int getZStart() {
        return this.chunkZPos << 4;
    }

    public int getXEnd() {
        return (this.chunkXPos << 4) + 15;
    }

    public int getZEnd() {
        return (this.chunkZPos << 4) + 15;
    }

    public BlockPos getBlock(int x2, int y2, int z2) {
        return new BlockPos((this.chunkXPos << 4) + x2, y2, (this.chunkZPos << 4) + z2);
    }

    public BlockPos getCenterBlock(int y2) {
        return new BlockPos(this.getCenterXPos(), y2, this.getCenterZPosition());
    }

    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}

