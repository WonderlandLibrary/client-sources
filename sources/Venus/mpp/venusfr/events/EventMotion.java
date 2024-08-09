/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import mpp.venusfr.events.CancelEvent;

public class EventMotion
extends CancelEvent {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    Runnable postMotion;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public Runnable getPostMotion() {
        return this.postMotion;
    }

    public void setX(double d) {
        this.x = d;
    }

    public void setY(double d) {
        this.y = d;
    }

    public void setZ(double d) {
        this.z = d;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public void setOnGround(boolean bl) {
        this.onGround = bl;
    }

    public void setPostMotion(Runnable runnable) {
        this.postMotion = runnable;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventMotion)) {
            return true;
        }
        EventMotion eventMotion = (EventMotion)object;
        if (!eventMotion.canEqual(this)) {
            return true;
        }
        if (Double.compare(this.getX(), eventMotion.getX()) != 0) {
            return true;
        }
        if (Double.compare(this.getY(), eventMotion.getY()) != 0) {
            return true;
        }
        if (Double.compare(this.getZ(), eventMotion.getZ()) != 0) {
            return true;
        }
        if (Float.compare(this.getYaw(), eventMotion.getYaw()) != 0) {
            return true;
        }
        if (Float.compare(this.getPitch(), eventMotion.getPitch()) != 0) {
            return true;
        }
        if (this.isOnGround() != eventMotion.isOnGround()) {
            return true;
        }
        Runnable runnable = this.getPostMotion();
        Runnable runnable2 = eventMotion.getPostMotion();
        return runnable == null ? runnable2 != null : !runnable.equals(runnable2);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventMotion;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        long l = Double.doubleToLongBits(this.getX());
        n2 = n2 * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.getY());
        n2 = n2 * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.getZ());
        n2 = n2 * 59 + (int)(l3 >>> 32 ^ l3);
        n2 = n2 * 59 + Float.floatToIntBits(this.getYaw());
        n2 = n2 * 59 + Float.floatToIntBits(this.getPitch());
        n2 = n2 * 59 + (this.isOnGround() ? 79 : 97);
        Runnable runnable = this.getPostMotion();
        n2 = n2 * 59 + (runnable == null ? 43 : runnable.hashCode());
        return n2;
    }

    public String toString() {
        return "EventMotion(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ", onGround=" + this.isOnGround() + ", postMotion=" + this.getPostMotion() + ")";
    }

    public EventMotion(double d, double d2, double d3, float f, float f2, boolean bl, Runnable runnable) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.yaw = f;
        this.pitch = f2;
        this.onGround = bl;
        this.postMotion = runnable;
    }
}

