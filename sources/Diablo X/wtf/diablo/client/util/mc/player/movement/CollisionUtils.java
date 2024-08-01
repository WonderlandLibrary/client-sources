package wtf.diablo.client.util.mc.player.movement;

import wtf.diablo.client.util.math.MathUtil;

public final class CollisionUtils {
    public static final double SERVER_GROUND_DIVISOR = 1.0D / 64.0D;

    private CollisionUtils() {}

    /**
     *
     * @param y - Y position of the player
     * @return Returns the closest multiple of 1/64 too the Y position
     */
    public static double niggerizeVerticalPosition(final double y) {
        return MathUtil.getClosestMultipleOfDivisor(y, SERVER_GROUND_DIVISOR);
    }

}