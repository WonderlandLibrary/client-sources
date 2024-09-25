/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import skizzle.events.Event;

public class EventMotion
extends Event<EventMotion> {
    public boolean sneak;
    public float yaw;
    public boolean onGround;
    public double x;
    public double y;
    public static float postPitch;
    public double z;
    public static float prePitch;
    public float pitch;

    public void setPitch(float Nigga) {
        Nigga.pitch = Nigga;
    }

    public void setX(double Nigga) {
        Nigga.x = Nigga;
    }

    public void setZ(double Nigga) {
        Nigga.z = Nigga;
    }

    public static {
        throw throwable;
    }

    public void setYaw(float Nigga) {
        Nigga.yaw = Nigga;
    }

    public void setOnGround(boolean Nigga) {
        Nigga.onGround = Nigga;
    }

    public void setY(double Nigga) {
        Nigga.y = Nigga;
    }

    public double getY() {
        EventMotion Nigga;
        return Nigga.y;
    }

    public float getPitch() {
        EventMotion Nigga;
        return Nigga.pitch;
    }

    public double getZ() {
        EventMotion Nigga;
        return Nigga.z;
    }

    public double getX() {
        EventMotion Nigga;
        return Nigga.x;
    }

    public boolean isOnGround() {
        EventMotion Nigga;
        return Nigga.onGround;
    }

    public float getYaw() {
        EventMotion Nigga;
        return Nigga.yaw;
    }

    public EventMotion(double Nigga, double Nigga2, double Nigga3, float Nigga4, float Nigga5, boolean Nigga6, boolean Nigga7) {
        EventMotion Nigga8;
        Nigga8.x = Nigga;
        Nigga8.y = Nigga2;
        Nigga8.z = Nigga3;
        Nigga8.yaw = Nigga4;
        Nigga8.pitch = Nigga5;
        Nigga8.onGround = Nigga6;
        Nigga8.sneak = Nigga7;
        if (Nigga8.isPost()) {
            postPitch = Nigga5;
        }
        if (Nigga8.isPre()) {
            prePitch = Nigga5;
        }
    }
}

