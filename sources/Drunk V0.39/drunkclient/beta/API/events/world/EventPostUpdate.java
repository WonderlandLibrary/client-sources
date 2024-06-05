/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.world;

import drunkclient.beta.API.Event;

public class EventPostUpdate
extends Event {
    private float yaw;
    public static float pitch;
    public static boolean rotatingPitch;

    public EventPostUpdate(float yaw, float pitch) {
        this.yaw = yaw;
        EventPostUpdate.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        EventPostUpdate.pitch = pitch;
        rotatingPitch = true;
    }
}

