package xyz.gucciclient.utils;

import java.util.*;
import java.math.*;

public class MathUtil
{
    public static int getMiddle(final int i, final int i1) {
        return (i + i1) / 2;
    }
    
    public static double getMiddleint(final double d, final double e) {
        return (d + e) / 2.0;
    }
    
    public static float getAngleDifference(final float direction, final float rotationYaw) {
        final float phi = Math.abs(rotationYaw - direction) % 360.0f;
        final float distance = (phi > 180.0f) ? (360.0f - phi) : phi;
        return distance;
    }
    
    public static int getRandomInRange(final int min, final int max) {
        final Random rand = new Random();
        final int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }
    
    public static double getRandomInRange(final double min, final double max) {
        final Random random = new Random();
        final double range = max - min;
        final double scaled = random.nextDouble() * range;
        final double shifted = scaled + min;
        return shifted;
    }
    
    public static float getRandomInRange(final float min, final float max) {
        final Random random = new Random();
        final float range = max - min;
        final float scaled = random.nextFloat() * range;
        final float shifted = scaled + min;
        return shifted;
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
