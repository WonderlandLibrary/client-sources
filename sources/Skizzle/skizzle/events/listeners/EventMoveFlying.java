/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import skizzle.events.Event;

public class EventMoveFlying
extends Event<EventMoveFlying> {
    public float strafe;
    public float friciton;
    public float forward;

    public void setFriciton(float Nigga) {
        Nigga.friciton = Nigga;
    }

    public float getForward() {
        EventMoveFlying Nigga;
        return Nigga.forward;
    }

    public float getFriciton() {
        EventMoveFlying Nigga;
        return Nigga.friciton;
    }

    public float getStrafe() {
        EventMoveFlying Nigga;
        return Nigga.strafe;
    }

    public void setStrafe(float Nigga) {
        Nigga.strafe = Nigga;
    }

    public void setForward(float Nigga) {
        Nigga.forward = Nigga;
    }

    public static {
        throw throwable;
    }

    public EventMoveFlying(float Nigga, float Nigga2, float Nigga3) {
        EventMoveFlying Nigga4;
        Nigga4.forward = Nigga;
        Nigga4.strafe = Nigga2;
        Nigga4.friciton = Nigga3;
    }
}

