package me.dinozoid.strife.event.implementations.player;

import me.dinozoid.strife.event.Event;

public class MovePlayerEvent extends Event {

    private double x, y, z;

    public MovePlayerEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public void x(double x) {
        this.x = x;
    }

    public void y(double y) {
        this.y = y;
    }

    public void z(double z) {
        this.z = z;
    }
}
