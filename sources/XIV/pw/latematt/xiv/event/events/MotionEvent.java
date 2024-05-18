package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Event;

/**
 * @author Jack
 */

public class MotionEvent extends Event {
    private double motionX;
    private double motionY;
    private double motionZ;
    private final State state;

    public MotionEvent(final double motionX, final double motionY, final double motionZ, State state) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        this.state = state;
    }

    public double getMotionX() {
        return motionX;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }

    public State getState() {
        return state;
    }

    public enum State {
        PRE, POST
    }
}
