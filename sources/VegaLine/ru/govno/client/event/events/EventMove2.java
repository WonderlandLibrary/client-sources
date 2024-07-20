/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.Event;

public class EventMove2
extends Event {
    private Vec3d from;
    private Vec3d to;
    private Vec3d motion;
    private Vec3d collisionOffset;
    private boolean toGround;
    private AxisAlignedBB aabbFrom;
    public boolean ignoreHorizontal;
    public boolean ignoreVertical;
    public boolean collidedHorizontal;
    public boolean collidedVertical;

    public EventMove2(Vec3d from, Vec3d to, Vec3d motion, Vec3d collisionOffset, boolean toGround, boolean isCollidedHorizontal, boolean isCollidedVertical, AxisAlignedBB aabbFrom) {
        this.from = from;
        this.to = to;
        this.motion = motion;
        this.collisionOffset = collisionOffset;
        this.toGround = toGround;
        this.collidedHorizontal = isCollidedHorizontal;
        this.collidedVertical = isCollidedVertical;
        this.aabbFrom = aabbFrom;
    }

    public void setIgnoreHorizontalCollision() {
        this.ignoreHorizontal = true;
    }

    public void setIgnoreVerticalCollision() {
        this.ignoreVertical = true;
    }

    public boolean isIgnoreHorizontal() {
        return this.ignoreHorizontal;
    }

    public AxisAlignedBB getAABBFrom() {
        return this.aabbFrom;
    }

    public boolean isIgnoreVertical() {
        return this.ignoreVertical;
    }

    public boolean isCollidedHorizontal() {
        return this.collidedHorizontal;
    }

    public boolean isCollidedVertical() {
        return this.collidedVertical;
    }

    public boolean toGround() {
        return this.toGround;
    }

    public Vec3d from() {
        return this.from;
    }

    public Vec3d to() {
        return this.to;
    }

    public Vec3d motion() {
        return this.motion;
    }

    public Vec3d collisionOffset() {
        return this.collisionOffset;
    }
}

