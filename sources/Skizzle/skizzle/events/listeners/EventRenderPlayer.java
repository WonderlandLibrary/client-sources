/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import skizzle.events.Event;

public class EventRenderPlayer
extends Event<EventRenderPlayer> {
    public float bodyYaw;
    public float yaw;
    public float partialTicks;
    public float pitch;
    public float yawChange;

    public float getPartialTicks() {
        EventRenderPlayer Nigga;
        return Nigga.partialTicks;
    }

    public float getBodyYaw() {
        EventRenderPlayer Nigga;
        return Nigga.bodyYaw;
    }

    public float getPitch() {
        EventRenderPlayer Nigga;
        return Nigga.pitch;
    }

    public EventRenderPlayer(float Nigga, float Nigga2, float Nigga3, float Nigga4, float Nigga5) {
        EventRenderPlayer Nigga6;
        Nigga6.yaw = Nigga;
        Nigga6.pitch = Nigga2;
        Nigga6.yawChange = Nigga3;
        Nigga6.bodyYaw = Nigga4;
        Nigga6.partialTicks = Nigga5;
    }

    public void setPitch(float Nigga) {
        Nigga.pitch = Nigga;
    }

    public float getYaw() {
        EventRenderPlayer Nigga;
        return Nigga.yaw;
    }

    public void setBodyYaw(float Nigga) {
        Nigga.bodyYaw = Nigga;
    }

    public static {
        throw throwable;
    }

    public void setYaw(float Nigga) {
        Nigga.yaw = Nigga;
    }
}

