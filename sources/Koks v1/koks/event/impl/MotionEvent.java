package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:28
 */
public class MotionEvent extends Event {

    private final Type type;

    private float yaw,pitch;

    public MotionEvent(Type type, float yaw, float pitch) {
        this.type = type;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Type getType() {
        return type;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public enum Type{
        PRE,POST;
    }
}
