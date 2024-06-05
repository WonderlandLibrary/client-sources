package ClassSub;

import java.util.*;
import java.security.*;
import java.math.*;

public class Class262
{
    private static final Random rng;
    
    
    public static boolean isInteger(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            return false;
        }
        catch (NullPointerException ex2) {
            return false;
        }
        return true;
    }
    
    public static Double clamp(final double n, final double n2, final double n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }
    
    public static Double getDifference(double n, double n2) {
        if (n > n2) {
            final double n3 = n;
            n = n2;
            n2 = n3;
        }
        return n2 - n;
    }
    
    public static float randomSeed(long n) {
        n += System.currentTimeMillis();
        return 0.4f + new Random(n).nextInt(80000000) / 1.0E9f + 1.45E-9f;
    }
    
    public static float secRanFloat(final float n, final float n2) {
        return new SecureRandom().nextFloat() * (n2 - n) + n;
    }
    
    public static int randInt(final int n, final int n2) {
        return new SecureRandom().nextInt() * (n2 - n) + n;
    }
    
    public static double secRanDouble(final double n, final double n2) {
        return new SecureRandom().nextDouble() * (n2 - n) + n;
    }
    
    public static float getAngleDifference(final float n, final float n2) {
        final float n3 = Math.abs(n2 - n) % 360.0f;
        return (n3 > 180.0f) ? (360.0f - n3) : n3;
    }
    
    public static double getMiddle(final double n, final double n2) {
        return (n + n2) / 2.0;
    }
    
    public static float getMiddle(final float n, final float n2) {
        return (n + n2) / 2.0f;
    }
    
    public static double getMiddleint(final double n, final double n2) {
        return (n + n2) / 2.0;
    }
    
    public static int getRandom(final int n, final int n2) {
        return n + Class262.rng.nextInt(n2 - n + 1);
    }
    
    public static double getRandom(final double n, final double n2) {
        return n + Class262.rng.nextInt((int)(n2 - n + 1.0));
    }
    
    public static double getRandomInRange(final double n, final double n2) {
        double n3 = new Random().nextDouble() * (n2 - n);
        if (n3 > n2) {
            n3 = n2;
        }
        double n4 = n3 + n;
        if (n4 > n2) {
            n4 = n2;
        }
        return n4;
    }
    
    public static float getRandomInRange(final float n, final float n2) {
        return new Random().nextFloat() * (n2 - n) + n;
    }
    
    public static int getRandomInRange(final int n, final int n2) {
        return new Random().nextInt(n2 - n + 1) + n;
    }
    
    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    static {
        rng = new Random();
    }
}
