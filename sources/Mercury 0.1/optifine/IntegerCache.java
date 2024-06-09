/*
 * Decompiled with CFR 0.145.
 */
package optifine;

public class IntegerCache {
    private static final int CACHE_SIZE = 4096;
    private static final Integer[] cache = IntegerCache.makeCache(4096);

    private static Integer[] makeCache(int size) {
        Integer[] arr2 = new Integer[size];
        for (int i2 = 0; i2 < size; ++i2) {
            arr2[i2] = new Integer(i2);
        }
        return arr2;
    }

    public static Integer valueOf(int value) {
        return value >= 0 && value < 4096 ? cache[value] : new Integer(value);
    }
}

