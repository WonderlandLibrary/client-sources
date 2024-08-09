/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.Position;

public final class GlobalPosition
extends Position {
    private final String dimension;

    public GlobalPosition(String string, int n, int n2, int n3) {
        super(n, n2, n3);
        this.dimension = string;
    }

    public String dimension() {
        return this.dimension;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        GlobalPosition globalPosition = (GlobalPosition)object;
        if (this.x != globalPosition.x) {
            return true;
        }
        if (this.y != globalPosition.y) {
            return true;
        }
        if (this.z != globalPosition.z) {
            return true;
        }
        return this.dimension.equals(globalPosition.dimension);
    }

    @Override
    public int hashCode() {
        int n = this.dimension.hashCode();
        n = 31 * n + this.x;
        n = 31 * n + this.y;
        n = 31 * n + this.z;
        return n;
    }

    @Override
    public String toString() {
        return "GlobalPosition{dimension='" + this.dimension + '\'' + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}

