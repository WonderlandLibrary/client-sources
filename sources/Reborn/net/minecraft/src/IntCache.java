package net.minecraft.src;

import java.util.*;

public class IntCache
{
    private static int intCacheSize;
    private static List freeSmallArrays;
    private static List inUseSmallArrays;
    private static List freeLargeArrays;
    private static List inUseLargeArrays;
    
    static {
        IntCache.intCacheSize = 256;
        IntCache.freeSmallArrays = new ArrayList();
        IntCache.inUseSmallArrays = new ArrayList();
        IntCache.freeLargeArrays = new ArrayList();
        IntCache.inUseLargeArrays = new ArrayList();
    }
    
    public static synchronized int[] getIntCache(final int par0) {
        if (par0 <= 256) {
            if (IntCache.freeSmallArrays.isEmpty()) {
                final int[] var1 = new int[256];
                IntCache.inUseSmallArrays.add(var1);
                return var1;
            }
            final int[] var1 = IntCache.freeSmallArrays.remove(IntCache.freeSmallArrays.size() - 1);
            IntCache.inUseSmallArrays.add(var1);
            return var1;
        }
        else {
            if (par0 > IntCache.intCacheSize) {
                IntCache.intCacheSize = par0;
                IntCache.freeLargeArrays.clear();
                IntCache.inUseLargeArrays.clear();
                final int[] var1 = new int[IntCache.intCacheSize];
                IntCache.inUseLargeArrays.add(var1);
                return var1;
            }
            if (IntCache.freeLargeArrays.isEmpty()) {
                final int[] var1 = new int[IntCache.intCacheSize];
                IntCache.inUseLargeArrays.add(var1);
                return var1;
            }
            final int[] var1 = IntCache.freeLargeArrays.remove(IntCache.freeLargeArrays.size() - 1);
            IntCache.inUseLargeArrays.add(var1);
            return var1;
        }
    }
    
    public static synchronized void resetIntCache() {
        if (!IntCache.freeLargeArrays.isEmpty()) {
            IntCache.freeLargeArrays.remove(IntCache.freeLargeArrays.size() - 1);
        }
        if (!IntCache.freeSmallArrays.isEmpty()) {
            IntCache.freeSmallArrays.remove(IntCache.freeSmallArrays.size() - 1);
        }
        IntCache.freeLargeArrays.addAll(IntCache.inUseLargeArrays);
        IntCache.freeSmallArrays.addAll(IntCache.inUseSmallArrays);
        IntCache.inUseLargeArrays.clear();
        IntCache.inUseSmallArrays.clear();
    }
    
    public static synchronized String func_85144_b() {
        return "cache: " + IntCache.freeLargeArrays.size() + ", tcache: " + IntCache.freeSmallArrays.size() + ", allocated: " + IntCache.inUseLargeArrays.size() + ", tallocated: " + IntCache.inUseSmallArrays.size();
    }
}
