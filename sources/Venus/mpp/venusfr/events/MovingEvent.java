/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class MovingEvent {
    private Vector3d from;
    private Vector3d to;
    private Vector3d motion;
    private boolean toGround;
    private AxisAlignedBB aabbFrom;
    private boolean ignoreHorizontal;
    private boolean ignoreVertical;
    private boolean collidedHorizontal;
    private boolean collidedVertical;

    public MovingEvent(Vector3d vector3d, Vector3d vector3d2, Vector3d vector3d3, boolean bl, boolean bl2, boolean bl3, AxisAlignedBB axisAlignedBB) {
        this.from = vector3d;
        this.to = vector3d2;
        this.motion = vector3d3;
        this.toGround = bl;
        this.collidedHorizontal = bl2;
        this.collidedVertical = bl3;
        this.aabbFrom = axisAlignedBB;
    }

    public Vector3d getFrom() {
        return this.from;
    }

    public Vector3d getTo() {
        return this.to;
    }

    public Vector3d getMotion() {
        return this.motion;
    }

    public boolean isToGround() {
        return this.toGround;
    }

    public AxisAlignedBB getAabbFrom() {
        return this.aabbFrom;
    }

    public boolean isIgnoreHorizontal() {
        return this.ignoreHorizontal;
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

    public void setFrom(Vector3d vector3d) {
        this.from = vector3d;
    }

    public void setTo(Vector3d vector3d) {
        this.to = vector3d;
    }

    public void setMotion(Vector3d vector3d) {
        this.motion = vector3d;
    }

    public void setToGround(boolean bl) {
        this.toGround = bl;
    }

    public void setAabbFrom(AxisAlignedBB axisAlignedBB) {
        this.aabbFrom = axisAlignedBB;
    }

    public void setIgnoreHorizontal(boolean bl) {
        this.ignoreHorizontal = bl;
    }

    public void setIgnoreVertical(boolean bl) {
        this.ignoreVertical = bl;
    }

    public void setCollidedHorizontal(boolean bl) {
        this.collidedHorizontal = bl;
    }

    public void setCollidedVertical(boolean bl) {
        this.collidedVertical = bl;
    }
}

