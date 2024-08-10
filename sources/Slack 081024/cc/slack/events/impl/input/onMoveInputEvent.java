package cc.slack.events.impl.input;

import cc.slack.events.Event;

public class onMoveInputEvent extends Event {
    public float forward, strafe;
    public boolean jump, sneak;
    public double sneakSlowDownMultiplier;

    public onMoveInputEvent(float moveForward, float moveStrafe, boolean jump, boolean sneak, double v) {
        forward = moveForward;
        strafe = moveStrafe;
        this.jump = jump;
        this.sneak = sneak;
        sneakSlowDownMultiplier = v;
    }
}
