package net.minecraft.util;

import net.minecraft.util.math.Vec2f;

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
    public boolean forwardKeyDown;
    public boolean backKeyDown;
    public boolean leftKeyDown;
    public boolean rightKeyDown;
    public boolean jump;
    public boolean sneak;

    public void updatePlayerMoveState()
    {
    }

    public Vec2f func_190020_b()
    {
        return new Vec2f(this.moveStrafe, this.moveForward);
    }
}
