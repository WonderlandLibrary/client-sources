package arsenic.event.impl;

import arsenic.event.types.CancellableEvent;

public class EventMovementInput extends CancellableEvent {

    private float speed, strafe;
    private boolean jump;

    public EventMovementInput(float speed, float strafe, boolean jump) {
        this.speed = speed;
        this.strafe = strafe;
        this.jump = jump;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public float getSpeed() {
        return speed;
    }

    public float getStrafe() {
        return strafe;
    }

    public boolean isJumping() {
        return jump;
    }

}
