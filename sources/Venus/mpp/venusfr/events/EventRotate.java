/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import mpp.venusfr.events.CancelEvent;

public class EventRotate
extends CancelEvent {
    private double yaw;
    private double pitch;

    public double getYaw() {
        return this.yaw;
    }

    public double getPitch() {
        return this.pitch;
    }

    public void setYaw(double d) {
        this.yaw = d;
    }

    public void setPitch(double d) {
        this.pitch = d;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventRotate)) {
            return true;
        }
        EventRotate eventRotate = (EventRotate)object;
        if (!eventRotate.canEqual(this)) {
            return true;
        }
        if (Double.compare(this.getYaw(), eventRotate.getYaw()) != 0) {
            return true;
        }
        return Double.compare(this.getPitch(), eventRotate.getPitch()) != 0;
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventRotate;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        long l = Double.doubleToLongBits(this.getYaw());
        n2 = n2 * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.getPitch());
        n2 = n2 * 59 + (int)(l2 >>> 32 ^ l2);
        return n2;
    }

    public String toString() {
        return "EventRotate(yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ")";
    }

    public EventRotate(double d, double d2) {
        this.yaw = d;
        this.pitch = d2;
    }
}

