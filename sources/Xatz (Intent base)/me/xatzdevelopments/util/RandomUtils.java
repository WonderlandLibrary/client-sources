package me.xatzdevelopments.util;

import java.util.*;

public class RandomUtils
{
    public static Random RANDOM;
    
    static {
        RandomUtils.RANDOM = new Random();
    }
    
    public static double RandomBetween(final double MinValue, final double MaxValue) {
        return Math.random() * (MaxValue - MinValue + 1.0) + MinValue;
    }
    
    public static byte[] nextBytes(final int count) {
        final byte[] result = new byte[count];
        RandomUtils.RANDOM.nextBytes(result);
        return result;
    }
    
    public static int nextInt(final int startInclusive, final int endExclusive) {
        return (endExclusive - startInclusive <= 0) ? startInclusive : (startInclusive + RandomUtils.RANDOM.nextInt(endExclusive - startInclusive));
    }
    
    public static long nextLong(final long startInclusive, final long endExclusive) {
        return (startInclusive == endExclusive) ? startInclusive : ((long)nextDouble((double)startInclusive, (double)endExclusive));
    }
    
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        return (startInclusive == endInclusive) ? startInclusive : (startInclusive + (endInclusive - startInclusive) * RandomUtils.RANDOM.nextDouble());
    }
    
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        return (startInclusive == endInclusive) ? startInclusive : (startInclusive + (endInclusive - startInclusive) * RandomUtils.RANDOM.nextFloat());
    }
}
