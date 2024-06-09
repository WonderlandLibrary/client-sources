package io.github.liticane.clients.feature.event.impl.other;

import io.github.liticane.clients.feature.event.Event;


public class StrafeEvent extends Event {
    private float strafe, forward, friction, yaw, pitch;

    public StrafeEvent(float strafe, float forward, float friction, float yaw, float pitch) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getStrafe() {
        return strafe;
    }

    public float getForward() {
        return forward;
    }

    public float getFriction() {
        return friction;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}

