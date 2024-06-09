// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

public final class AnimationUtil
{
    public static float calculateCompensation(final float target, float current, long delta, final double speed) {
        final float diff = current - target;
        if (delta < 1L) {
            delta = 1L;
        }
        if (delta > 1000L) {
            delta = 16L;
        }
        final double d0 = (speed * delta / 16.0 < 0.5) ? 0.5 : (speed * delta / 16.0);
        if (diff > speed) {
            final double xD = d0;
            current -= (float)xD;
            if (current < target) {
                current = target;
            }
        }
        else if (diff < -speed) {
            final double xD = d0;
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
    
    public static double animate(final double current, final double target, final double speed) {
        double animated = current;
        if (current < target) {
            if (current + speed > target) {
                animated = target;
            }
            else {
                animated += speed;
            }
        }
        if (current > target) {
            if (current - speed < target) {
                animated = target;
            }
            else {
                animated -= speed;
            }
        }
        return animated;
    }
}
