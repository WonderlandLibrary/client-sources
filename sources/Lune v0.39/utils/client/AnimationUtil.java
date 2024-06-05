package me.superskidder.lune.utils.client;

public class AnimationUtil {
    private static float defaultSpeed = 0.125f;

    public static float calculateCompensation(float target, float current, long delta, double speed) {
        float diff = current - target;
        if (delta < 1L) {
            delta = 1L;
        }
        if (delta > 1000L) {
            delta = 16L;
        }
        if ((double) diff > speed) {
            double xD = speed * (double) delta / 16.0 < 0.5 ? 0.5 : speed * (double) delta / 16.0;
            if ((current = (float) ((double) current - xD)) < target) {
                current = target;
            }
        } else if ((double) diff < -speed) {
            double xD = speed * (double) delta / 16.0 < 0.5 ? 0.5 : speed * (double) delta / 16.0;
            if ((current = (float) ((double) current + xD)) > target) {
                current = target;
            }
        } else {
            current = target;
        }
        return current;
    }

    public static float mvoeUD(float current, float end, float minSpeed) {
        return AnimationUtil.moveUD(current, end, defaultSpeed, minSpeed);
    }

    public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = (end - current) * smoothSpeed;
        if (movement > 0.0f) {
            movement = Math.max((float) minSpeed, (float) movement);
            movement = Math.min((float) (end - current), (float) movement);
        } else if (movement < 0.0f) {
            movement = Math.min((float) (-minSpeed), (float) movement);
            movement = Math.max((float) (end - current), (float) movement);
        }
        return current + movement;
    }

}
