package club.mega.util;

public final class AnimationUtil {

    public static double animate(final double current, final double target, final double speed) {
        double animated = current;
        if (current < target) {
            if (current + speed > target)
                animated = target;
            else
                animated += speed;
        }
        if (current > target) {
            if (current - speed < target)
                animated = target;
            else
                animated -= speed;
        }
        return animated;
    }

}
