/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class EntitySize {
    public final float width;
    public final float height;
    public final boolean fixed;

    public EntitySize(float f, float f2, boolean bl) {
        this.width = f;
        this.height = f2;
        this.fixed = bl;
    }

    public AxisAlignedBB func_242286_a(Vector3d vector3d) {
        return this.func_242285_a(vector3d.x, vector3d.y, vector3d.z);
    }

    public AxisAlignedBB func_242285_a(double d, double d2, double d3) {
        float f = this.width / 2.0f;
        float f2 = this.height;
        return new AxisAlignedBB(d - (double)f, d2, d3 - (double)f, d + (double)f, d2 + (double)f2, d3 + (double)f);
    }

    public EntitySize scale(float f) {
        return this.scale(f, f);
    }

    public EntitySize scale(float f, float f2) {
        return !this.fixed && (f != 1.0f || f2 != 1.0f) ? EntitySize.flexible(this.width * f, this.height * f2) : this;
    }

    public static EntitySize flexible(float f, float f2) {
        return new EntitySize(f, f2, false);
    }

    public static EntitySize fixed(float f, float f2) {
        return new EntitySize(f, f2, true);
    }

    public String toString() {
        return "EntityDimensions w=" + this.width + ", h=" + this.height + ", fixed=" + this.fixed;
    }
}

