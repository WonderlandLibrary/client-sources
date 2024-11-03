package dev.stephen.nexus.utils.math;

import dev.stephen.nexus.utils.Utils;

public final class MathUtils implements Utils {


    public static double roundToDecimal(double n, double point) {
        return point * Math.round(n / point);
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static float wrapAngleTo180_float(float p_76142_0_) {
        p_76142_0_ %= 360.0F;

        if (p_76142_0_ >= 180.0F) {
            p_76142_0_ -= 360.0F;
        }

        if (p_76142_0_ < -180.0F) {
            p_76142_0_ += 360.0F;
        }

        return p_76142_0_;
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static double wrapAngleTo180_double(double p_76138_0_) {
        p_76138_0_ %= 360.0D;

        if (p_76138_0_ >= 180.0D) {
            p_76138_0_ -= 360.0D;
        }

        if (p_76138_0_ < -180.0D) {
            p_76138_0_ += 360.0D;
        }

        return p_76138_0_;
    }

    public static int clamp_int(int num, int min, int max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static float clamp_float(float num, float min, float max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp_double(double num, double min, double max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static float sqrt_float(float value)
    {
        return (float)Math.sqrt((double)value);
    }

    public static float sqrt_double(double value)
    {
        return (float)Math.sqrt(value);
    }
}
