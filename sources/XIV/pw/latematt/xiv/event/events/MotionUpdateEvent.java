package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class MotionUpdateEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final State currentState;
    private float yaw, pitch;
    private double x, y, z;
    private boolean onGround, wasSprinting;

    public MotionUpdateEvent(State currentState, float yaw, float pitch, double x, double y, double z, boolean onGround, boolean wasSprinting) {
        this.currentState = currentState;
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
        this.wasSprinting = wasSprinting;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean wasSprinting() {
        return wasSprinting;
    }

    public State getCurrentState() {
        return currentState;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum State {
        PRE, POST
    }
}
