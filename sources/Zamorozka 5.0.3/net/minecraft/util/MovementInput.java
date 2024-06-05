package net.minecraft.util;

import net.minecraft.util.math.Vec2f;

public class MovementInput {
    public static float moveStrafe;
    public static float moveForward;
    public static boolean forwardKeyDown;
    public static boolean backKeyDown;
    public static boolean leftKeyDown;
    public static boolean rightKeyDown;
    public static boolean jump;
    public static boolean sneak;

    public void updatePlayerMoveState() {
    }

    public Vec2f getMoveVector() {
        return new Vec2f(moveStrafe, moveForward);
    }
}
