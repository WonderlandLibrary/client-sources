package club.mega.event.impl;

import club.mega.event.Event;

public class EventMovePlayer extends Event {

    private double x, y, z;

    public EventMovePlayer(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
