/*
 * Decompiled with CFR 0_122.
 */
package winter.event.events;

import winter.event.Event;

public class Render3DEvent
extends Event {
    private boolean offset;
    public float renderPartialTicks;
    private int x;
    private int y;
    private int z;
    private int ix;
    private int iy;
    private int iz;

    public Render3DEvent(float renderPartialTicks, int x2, int y2, int z2) {
        this.renderPartialTicks = renderPartialTicks;
        this.x = x2;
        this.y = y2;
        this.z = z2;
        this.ix = x2;
        this.iy = y2;
        this.iz = z2;
    }

    public boolean isOffset() {
        return this.offset;
    }

    public void reset() {
        this.x = this.ix;
        this.y = this.iy;
        this.z = this.iz;
        this.offset = false;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x2) {
        this.x = x2;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y2) {
        this.y = y2;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z2) {
        this.z = z2;
    }
}

