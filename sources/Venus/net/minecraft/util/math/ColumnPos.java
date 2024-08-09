/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import net.minecraft.util.math.BlockPos;

public class ColumnPos {
    public final int x;
    public final int z;

    public ColumnPos(int n, int n2) {
        this.x = n;
        this.z = n2;
    }

    public ColumnPos(BlockPos blockPos) {
        this.x = blockPos.getX();
        this.z = blockPos.getZ();
    }

    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }

    public int hashCode() {
        int n = 1664525 * this.x + 1013904223;
        int n2 = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
        return n ^ n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ColumnPos)) {
            return true;
        }
        ColumnPos columnPos = (ColumnPos)object;
        return this.x == columnPos.x && this.z == columnPos.z;
    }
}

