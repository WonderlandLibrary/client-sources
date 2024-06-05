package club.mega.event.impl;

import club.mega.event.Event;

public class EventPreTick extends Event {

    private boolean onGround;
    private float[] rotations;
    private double x;
    private double y;
    private double z;

    public EventPreTick(final boolean onGround, final float[] rotations, final double x, final double y, final double z) {
        this.onGround = onGround;
        this.rotations = rotations;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final boolean isOnGround() {
        return onGround;
    }

    public final void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }

    public final float[] getRotations() {
        return rotations;
    }

    public final void setRotations(final float[] rotations) {
        this.rotations = rotations;
    }

    public final void setYaw(final float yaw) {
        this.rotations[0] = yaw;
    }

    public final void setPitch(final float pitch) {
        this.rotations[1] = pitch;
    }

    public final double getX() {
        return x;
    }

    public final void setX(final double x) {
        this.x = x;
    }

    public final double getY() {
        return y;
    }

    public final void setY(final double y) {
        this.y = y;
    }

    public final double getZ() {
        return z;
    }

    public final void setZ(final double z) {
        this.z = z;
    }

}
