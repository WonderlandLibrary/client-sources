/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import skizzle.events.Event;

public class EventServerMotion
extends Event<EventServerMotion> {
    public float yaw;
    public double z;
    public double x;
    public float pitchHead;
    public float pitch;
    public double y;

    public void setYaw(float Nigga) {
        Nigga.yaw = Nigga;
    }

    public static {
        throw throwable;
    }

    public void setY(double Nigga) {
        Nigga.y = Nigga;
    }

    public double getZ() {
        EventServerMotion Nigga;
        return Nigga.z;
    }

    public float getYaw() {
        EventServerMotion Nigga;
        return Nigga.yaw;
    }

    public double getY() {
        EventServerMotion Nigga;
        return Nigga.y;
    }

    public void setZ(double Nigga) {
        Nigga.z = Nigga;
    }

    public void setPitch(float Nigga) {
        Nigga.pitch = Nigga;
    }

    public double getX() {
        EventServerMotion Nigga;
        return Nigga.x;
    }

    public void setX(double Nigga) {
        Nigga.x = Nigga;
    }

    public EventServerMotion(double Nigga, double Nigga2, double Nigga3, float Nigga4, float Nigga5) {
        EventServerMotion Nigga6;
        Nigga6.x = Nigga;
        Nigga6.y = Nigga2;
        Nigga6.z = Nigga3;
        Nigga6.yaw = Nigga4;
        Nigga6.pitch = Nigga5;
    }

    public float getPitch() {
        EventServerMotion Nigga;
        return Nigga.pitch;
    }
}

