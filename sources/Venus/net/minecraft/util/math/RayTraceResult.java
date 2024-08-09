/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;

public abstract class RayTraceResult {
    protected final Vector3d hitResult;

    protected RayTraceResult(Vector3d vector3d) {
        this.hitResult = vector3d;
    }

    public double func_237486_a_(Entity entity2) {
        double d = this.hitResult.x - entity2.getPosX();
        double d2 = this.hitResult.y - entity2.getPosY();
        double d3 = this.hitResult.z - entity2.getPosZ();
        return d * d + d2 * d2 + d3 * d3;
    }

    public abstract Type getType();

    public Vector3d getHitVec() {
        return this.hitResult;
    }

    public static enum Type {
        MISS,
        BLOCK,
        ENTITY;

    }
}

