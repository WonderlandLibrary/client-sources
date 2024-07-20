/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.Event;

public class EventElytraVector
extends Event {
    Vec3d vector;
    float yaw;
    float pitch;
    EntityLivingBase base;

    public EventElytraVector(Vec3d vector, EntityLivingBase base, float yaw, float pitch) {
        this.vector = vector;
        this.base = base;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setVectorAsYawPitch(float yaw, float pitch) {
        if (this.getEntityIn() == null) {
            return;
        }
        this.getEntityIn();
        this.setVector(EntityLivingBase.getVectorForRotation(pitch, yaw));
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setVector(Vec3d vector) {
        this.vector = vector;
    }

    public Vec3d getVector() {
        return this.vector;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public EntityLivingBase getEntityIn() {
        return this.base;
    }
}

