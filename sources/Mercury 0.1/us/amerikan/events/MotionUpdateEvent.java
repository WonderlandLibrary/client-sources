/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.types.EventType;

public class MotionUpdateEvent
implements Event {
    private State state;
    private final EventType eventType;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public MotionUpdateEvent(EventType eventType, double x2, double y2, double z2, float yaw, float pitch, boolean onGround, State state) {
        this.eventType = eventType;
        this.x = x2;
        this.y = y2;
        this.z = z2;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.state = state;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public State getState() {
        return this.state;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x2) {
        this.x = x2;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y2) {
        this.y = y2;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z2) {
        this.z = z2;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public static enum State {
        PRE,
        POST;
        
    }

}

