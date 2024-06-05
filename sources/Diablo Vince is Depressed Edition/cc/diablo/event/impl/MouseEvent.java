/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event.impl;

import cc.diablo.event.Event;

public class MouseEvent
extends Event {
    public double x;
    public double y;
    public double z;
    public int button;

    public MouseEvent(double x, double y, int button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public int getButton() {
        return this.button;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setButton(int button) {
        this.button = button;
    }
}

