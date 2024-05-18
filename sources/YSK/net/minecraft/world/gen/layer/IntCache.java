package net.minecraft.world.gen.layer;

import com.google.common.collect.*;
import java.util.*;

public class IntCache
{
    private static int intCacheSize;
    private static List<int[]> freeSmallArrays;
    private static List<int[]> inUseLargeArrays;
    private static List<int[]> freeLargeArrays;
    private static final String[] I;
    private static List<int[]> inUseSmallArrays;
    
    private static void I() {
        (I = new String[0x2A ^ 0x2E])["".length()] = I(")\u0014\u001b9'pU", "JuxQB");
        IntCache.I[" ".length()] = I("yE&\u0017\u00146\r7NU", "UeRtu");
        IntCache.I["  ".length()] = I("Cy\u0016\u0006:\u0000:\u0016\u001e3\u000bcW", "oYwjV");
        IntCache.I["   ".length()] = I("ta\u001b\u000b!4.\f\u000b9=%UJ", "XAojM");
    }
    
    static {
        I();
        IntCache.intCacheSize = 237 + 146 - 318 + 191;
        IntCache.freeSmallArrays = (List<int[]>)Lists.newArrayList();
        IntCache.inUseSmallArrays = (List<int[]>)Lists.newArrayList();
        IntCache.freeLargeArrays = (List<int[]>)Lists.newArrayList();
        IntCache.inUseLargeArrays = (List<int[]>)Lists.newArrayList();
    }
    
    public static synchronized String getCacheSizes() {
        return IntCache.I["".length()] + IntCache.freeLargeArrays.size() + IntCache.I[" ".length()] + IntCache.freeSmallArrays.size() + IntCache.I["  ".length()] + IntCache.inUseLargeArrays.size() + IntCache.I["   ".length()] + IntCache.inUseSmallArrays.size();
    }
    
    public static synchronized int[] getIntCache(final int intCacheSize) {
        if (intCacheSize <= 71 + 186 - 138 + 137) {
            if (IntCache.freeSmallArrays.isEmpty()) {
                final int[] array = new int[50 + 172 - 84 + 118];
                IntCache.inUseSmallArrays.add(array);
                return array;
            }
            final int[] array2 = IntCache.freeSmallArrays.remove(IntCache.freeSmallArrays.size() - " ".length());
            IntCache.inUseSmallArrays.add(array2);
            return array2;
        }
        else {
            if (intCacheSize > IntCache.intCacheSize) {
                IntCache.intCacheSize = intCacheSize;
                IntCache.freeLargeArrays.clear();
                IntCache.inUseLargeArrays.clear();
                final int[] array3 = new int[IntCache.intCacheSize];
                IntCache.inUseLargeArrays.add(array3);
                return array3;
            }
            if (IntCache.freeLargeArrays.isEmpty()) {
                final int[] array4 = new int[IntCache.intCacheSize];
                IntCache.inUseLargeArrays.add(array4);
                return array4;
            }
            final int[] array5 = IntCache.freeLargeArrays.remove(IntCache.freeLargeArrays.size() - " ".length());
            IntCache.inUseLargeArrays.add(array5);
            return array5;
        }
    }
    
    public static synchronized void resetIntCache() {
        if (!IntCache.freeLargeArrays.isEmpty()) {
            IntCache.freeLargeArrays.remove(IntCache.freeLargeArrays.size() - " ".length());
        }
        if (!IntCache.freeSmallArrays.isEmpty()) {
            IntCache.freeSmallArrays.remove(IntCache.freeSmallArrays.size() - " ".length());
        }
        IntCache.freeLargeArrays.addAll(IntCache.inUseLargeArrays);
        IntCache.freeSmallArrays.addAll(IntCache.inUseSmallArrays);
        IntCache.inUseLargeArrays.clear();
        IntCache.inUseSmallArrays.clear();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
