package Hydro.util.render;

public class AnimationUtil
{
    public static float calculateCompensation(final float target, float current, long delta, final double speed) {
        final float diff = current - target;
        if (delta < 1L) {
            delta = 1L;
        }
        if (delta > 1000L) {
            delta = 16L;
        }
        if (diff > speed) {
            final double xD = (speed * delta / 16.0 < 0.5) ? 0.5 : (speed * delta / 16.0);
            current -= (float)xD;
            if (current < target) {
                current = target;
            }
        }
        else if (diff < -speed) {
            final double xD = (speed * delta / 16.0 < 0.5) ? 0.5 : (speed * delta / 16.0);
            current += (float)xD;
            if (current > target) {
                current = target;
            }
        }
        else {
            current = target;
        }
        return current;
    }
}