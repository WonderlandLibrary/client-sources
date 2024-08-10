package cc.slack.utils.other;

import cc.slack.utils.client.IMinecraft;
import net.minecraft.util.Vec3;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil implements IMinecraft {

    public static double roundToDecimalPlace(double value, double inc) {
        final double halfOfInc = inc / 2.0D;
        final double floored = Math.floor(value / inc) * inc;

        if (value >= floored + halfOfInc)
            return new BigDecimal(Math.ceil(value / inc) * inc, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
        else
            return new BigDecimal(floored, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static float[] getRotations(double x, double y, double z) {
        Vec3 lookVec = mc.thePlayer.getPositionEyes(1.0F);
        double dx = lookVec.xCoord - x;
        double dz = lookVec.zCoord - z;
        double dy = lookVec.yCoord - y;
        double dist = Math.sqrt(dx * dx + dz * dz);
        double yaw = Math.toDegrees(Math.atan2(dz, dx));
        double pitch = Math.toDegrees(Math.atan2(dy, dist));
        return new float[]{(float)yaw + 90.0F, (float)pitch};
    }

    public static double getRandomInRange(double min, double max) {
        if (min == max)
            return min;
        else if (min > max) {
            double oldMin = min;
            min = max;
            max = oldMin;
        }

        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int getRandomInRange(int min, int max) {
        if (min == max)
            return min;
        else if (min > max) {
            int oldMin = min;
            min = max;
            max = oldMin;
        }

        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static float getDifference(float base, float yaw) {
        float bigger;
        if (base >= yaw)
            bigger = base - yaw;
        else
            bigger = yaw - base;
        return bigger;
    }


    public static float interpolate(float newValue, float oldValue, float partialTicks) {
        return oldValue + (newValue - oldValue) * partialTicks;
    }

    public static double interpolate(double newValue, double oldValue, float partialTicks) {
        return oldValue + (newValue - oldValue) * partialTicks;
    }

    public static double interpolate(double newValue, double oldValue, double partialTicks) {
        return oldValue + (newValue - oldValue) * partialTicks;
    }


    public static double lerp(double oldValue, double newValue, double partialTicks) {
        return oldValue + partialTicks * (newValue - oldValue);
    }

    public static float lerp(float oldValue, float newValue, float partialTicks) {
        return oldValue + partialTicks * (newValue - oldValue);
    }

    public static float clamp(float number, float min, float max) {
        return (number < min) ? min : Math.min(number, max);
    }

    public static double round(final double value, final int places) {
        if (places < 0) throw new IllegalArgumentException();

        return (new BigDecimal(Double.toString(value))).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
}
