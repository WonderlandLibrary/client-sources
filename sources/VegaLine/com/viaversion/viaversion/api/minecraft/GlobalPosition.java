/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.Position;

public final class GlobalPosition
extends Position {
    private final String dimension;

    public GlobalPosition(String dimension, int x, int y, int z) {
        super(x, y, z);
        this.dimension = dimension;
    }

    public String dimension() {
        return this.dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GlobalPosition position = (GlobalPosition)o;
        if (this.x != position.x) {
            return false;
        }
        if (this.y != position.y) {
            return false;
        }
        if (this.z != position.z) {
            return false;
        }
        return this.dimension.equals(position.dimension);
    }

    @Override
    public int hashCode() {
        int result = this.dimension.hashCode();
        result = 31 * result + this.x;
        result = 31 * result + this.y;
        result = 31 * result + this.z;
        return result;
    }

    @Override
    public String toString() {
        return "GlobalPosition{dimension='" + this.dimension + '\'' + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}

