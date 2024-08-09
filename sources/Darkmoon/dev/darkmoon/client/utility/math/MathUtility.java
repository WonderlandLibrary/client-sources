package dev.darkmoon.client.utility.math;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtility implements Utility {
    public static float clamp(float val, float min, float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }
    public static float lerp(double start, double end, double step) {
        return (float)(start + (end - start) * step);
    }
    public static double round(double num, double increment) {
        double v = (double) Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static float fast(float end, float start, float multiple) {
        return (1 - MathHelper.clamp((float) (deltaTime() * multiple), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * multiple), 0, 1) * start;
    }
    public static double deltaTime() {
        return mc.getDebugFPS() > 0 ? (1.0000 / mc.getDebugFPS()) : 1;
    }
    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static float randomizeFloat(float min, float max) {
        return (float) (Math.random() * (double) (max - min)) + min;
    }

    public static double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    public static int getMiddle(int old, int newValue) {
        return (old + newValue) / 2;
    }

    public static int getRandomInRange(int int1, int int2) {
        return (int)(Math.random() * (int2 - int1) + int1);
    }
}
