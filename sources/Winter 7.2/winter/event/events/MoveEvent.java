/*
 * Decompiled with CFR 0_122.
 */
package winter.event.events;

import winter.event.Event;

public class MoveEvent
extends Event {
    public double y;
    public double x;
    public double z;

    public MoveEvent(double x2, double y2, double z2) {
        this.x = x2;
        this.y = y2;
        this.z = z2;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double newx) {
        this.x = newx;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double newy) {
        this.y = newy;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double newz) {
        this.z = newz;
    }
}

