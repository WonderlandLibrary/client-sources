/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.awt.Dimension;

public class DynamicDimension {
    private boolean relative = false;
    private float width;
    private float height;

    public DynamicDimension(boolean bl, float f, float f2) {
        this.relative = bl;
        this.width = f;
        this.height = f2;
    }

    public boolean isRelative() {
        return this.relative;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public Dimension getDimension(int n, int n2) {
        return this.relative ? new Dimension((int)(this.width * (float)n), (int)(this.height * (float)n2)) : new Dimension((int)this.width, (int)this.height);
    }

    public int hashCode() {
        int n = this.relative ? 1 : 0;
        n = n * 37 + (int)this.width;
        return n * 37 + (int)this.height;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DynamicDimension)) {
            return true;
        }
        DynamicDimension dynamicDimension = (DynamicDimension)object;
        if (this.relative != dynamicDimension.relative) {
            return true;
        }
        if (this.width != dynamicDimension.width) {
            return true;
        }
        return this.height == dynamicDimension.height;
    }
}

