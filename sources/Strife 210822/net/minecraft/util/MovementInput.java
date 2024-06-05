package net.minecraft.util;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.event.implementations.player.StrafePlayerEvent;

public class MovementInput
{
    /**
     * The speed at which the player is strafing. Postive numbers to the left and negative to the right.
     */
    public float moveStrafe;

    /**
     * The speed at which the player is moving forward. Negative numbers will move backwards.
     */
    public float moveForward;
    public boolean jump;
    public boolean sneak;

    public void updatePlayerMoveState()
    {
        StrafePlayerEvent strafePlayerEvent = new StrafePlayerEvent(moveStrafe, moveForward, jump, sneak);
        StrifeClient.INSTANCE.eventBus().post(strafePlayerEvent);
        moveStrafe = strafePlayerEvent.moveStrafe();
        moveForward = strafePlayerEvent.moveForward();
        jump = strafePlayerEvent.jump();
        sneak = strafePlayerEvent.sneak();
    }
}
