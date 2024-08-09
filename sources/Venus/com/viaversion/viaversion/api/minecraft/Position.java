/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.GlobalPosition;

public class Position {
    protected final int x;
    protected final int y;
    protected final int z;

    public Position(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
    }

    public Position(int n, short s, int n2) {
        this(n, (int)s, n2);
    }

    @Deprecated
    public Position(Position position) {
        this(position.x(), position.y(), position.z());
    }

    public Position getRelative(BlockFace blockFace) {
        return new Position(this.x + blockFace.modX(), (short)(this.y + blockFace.modY()), this.z + blockFace.modZ());
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int z() {
        return this.z;
    }

    public GlobalPosition withDimension(String string) {
        return new GlobalPosition(string, this.x, this.y, this.z);
    }

    @Deprecated
    public int getX() {
        return this.x;
    }

    @Deprecated
    public int getY() {
        return this.y;
    }

    @Deprecated
    public int getZ() {
        return this.z;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Position position = (Position)object;
        if (this.x != position.x) {
            return true;
        }
        if (this.y != position.y) {
            return true;
        }
        return this.z == position.z;
    }

    public int hashCode() {
        int n = this.x;
        n = 31 * n + this.y;
        n = 31 * n + this.z;
        return n;
    }

    public String toString() {
        return "Position{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}

