package dev.darkmoon.client.event.player;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class EventMove extends EventCancellable implements Event{
    private Vec3d from, to, motion, collisionOffset;
    public boolean toGround;
    private AxisAlignedBB aabbFrom;
    public boolean ignoreHorizontal, ignoreVertical, collidedHorizontal, collidedVertical;

    public EventMove(Vec3d from, Vec3d to, Vec3d motion, Vec3d collisionOffset, boolean toGround,
                     boolean isCollidedHorizontal, boolean isCollidedVertical, AxisAlignedBB aabbFrom) {
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
    public void setX(double x) {
        this.to = new Vec3d(x, this.to.y, this.to.z);
    }
    public void setY(double y) {
        this.to = new Vec3d(this.to.x, y, this.to.z);
    }

    public void setZ(double z) {
        this.to = new Vec3d(this.to.x, this.to.y, z);
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
