/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class EntityRayTraceResult
extends RayTraceResult {
    private final Entity entity;

    public EntityRayTraceResult(Entity entity2) {
        this(entity2, entity2.getPositionVec());
    }

    public EntityRayTraceResult(Entity entity2, Vector3d vector3d) {
        super(vector3d);
        this.entity = entity2;
    }

    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public RayTraceResult.Type getType() {
        return RayTraceResult.Type.ENTITY;
    }
}

