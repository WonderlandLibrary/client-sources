package net.shoreline.client.util.player;

import net.shoreline.client.util.Globals;
import net.minecraft.util.math.MathHelper;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class MovementUtil implements Globals
{
    /**
     *
     *
     * @return
     */
    public static boolean isInputtingMovement()
    {
        return mc.player.input.pressingForward
                || mc.player.input.pressingBack
                || mc.player.input.pressingLeft
                || mc.player.input.pressingRight;
    }

    /**
     *
     * @return
     */
    public static boolean isMovingInput()
    {
        return mc.player.input.movementForward != 0.0f
                || mc.player.input.movementSideways != 0.0f;
    }

    /**
     *
     *
     * @return
     */
    public static boolean isMoving()
    {
        double d = mc.player.getX() - mc.player.lastX;
        double e = mc.player.getY() - mc.player.lastBaseY;
        double f = mc.player.getZ() - mc.player.lastZ;
        return MathHelper.squaredMagnitude(d, e, f) > MathHelper.square(2.0e-4);
    }
}
