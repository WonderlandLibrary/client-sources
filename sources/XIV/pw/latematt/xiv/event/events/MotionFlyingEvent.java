package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class MotionFlyingEvent extends Event implements Cancellable {
    private float strafe;
    private float forward;
    private float friction;
    private final State state;
    private boolean cancelled;

    public MotionFlyingEvent(final float strafe, final float forward, final float friction, State state) {
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
        this.state = state;
    }

    public float getStrafe() {
        return strafe;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public float getForward() {
        return forward;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public State getState() {
        return state;
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
