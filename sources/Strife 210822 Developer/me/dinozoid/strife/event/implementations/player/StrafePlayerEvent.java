package me.dinozoid.strife.event.implementations.player;

public class StrafePlayerEvent {

    private float moveStrafe;
    private float moveForward;
    private boolean jump;
    private boolean sneak;

    public StrafePlayerEvent(float moveStrafe, float moveForward, boolean jump, boolean sneak) {
        this.moveStrafe = moveStrafe;
        this.moveForward = moveForward;
        this.jump = jump;
        this.sneak = sneak;
    }

    public float moveStrafe() {
        return moveStrafe;
    }

    public void moveStrafe(float moveStrafe) {
        this.moveStrafe = moveStrafe;
    }

    public float moveForward() {
        return moveForward;
    }

    public void moveForward(float moveForward) {
        this.moveForward = moveForward;
    }

    public boolean jump() {
        return jump;
    }

    public void jump(boolean jump) {
        this.jump = jump;
    }

    public boolean sneak() {
        return sneak;
    }

    public void sneak(boolean sneak) {
        this.sneak = sneak;
    }
}
