package me.rich.helpers.render;

import net.minecraft.client.Minecraft;

public class AnimationHelper {
	public static int deltaTime;
    public static float speedTarget = 0.125f;

    public static float animation(float current, float targetAnimation, float speed) {
        return AnimationHelper.animation(current, targetAnimation, speedTarget, speed);
    }

    public static float animation(float animation, float target, float poxyi, float speedTarget) {
        float da = (target - animation) / Math.max((float)Minecraft.getDebugFPS(), 5.0f) * 15.0f;
        if (da > 0.0f) {
            da = Math.max(speedTarget, da);
            da = Math.min(target - animation, da);
        } else if (da < 0.0f) {
            da = Math.min(-speedTarget, da);
            da = Math.max(target - animation, da);
        }
        return animation + da;
    }

    public static double animate(double target, double current, double speed) {
        boolean larger;
        boolean bl = larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        current = larger ? (current += factor) : (current -= factor);
        return current;
    }
    public static float calculateCompensation(float target, float current, long delta, double speed) {
        float diff = current - target;
        if (delta < 1) {
            delta = 1;
        }
        if (delta > 1000) {
            delta = 16;
        }
        if (diff > speed) {
            double xD = (speed * delta / (1000 / 60) < 0.5 ? 0.5 : speed * delta / (1000 / 60));
            current -= xD;
            if (current < target) {
                current = target;
            }
        } else if (diff < -speed) {
            double xD = (speed * delta / (1000 / 60) < 0.5 ? 0.5 : speed * delta / (1000 / 60));
            current += xD;
            if (current > target) {
                current = target;
            }
        } else {
            current = target;
        }
        return current;
    }
}

