/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import net.minecraft.block.BlockState;
import net.optifine.Config;

public class CacheObjectArray {
    private static ArrayDeque<int[]> arrays = new ArrayDeque();
    private static int maxCacheSize = 10;

    private static synchronized int[] allocateArray(int n) {
        int[] nArray = arrays.pollLast();
        if (nArray == null || nArray.length < n) {
            nArray = new int[n];
        }
        return nArray;
    }

    public static synchronized void freeArray(int[] nArray) {
        if (arrays.size() < maxCacheSize) {
            arrays.add(nArray);
        }
    }

    public static void main(String[] stringArray) throws Exception {
        int n = 4096;
        int n2 = 500000;
        CacheObjectArray.testNew(n, n2);
        CacheObjectArray.testClone(n, n2);
        CacheObjectArray.testNewObj(n, n2);
        CacheObjectArray.testCloneObj(n, n2);
        CacheObjectArray.testNewObjDyn(BlockState.class, n, n2);
        long l = CacheObjectArray.testNew(n, n2);
        long l2 = CacheObjectArray.testClone(n, n2);
        long l3 = CacheObjectArray.testNewObj(n, n2);
        long l4 = CacheObjectArray.testCloneObj(n, n2);
        long l5 = CacheObjectArray.testNewObjDyn(BlockState.class, n, n2);
        Config.dbg("New: " + l);
        Config.dbg("Clone: " + l2);
        Config.dbg("NewObj: " + l3);
        Config.dbg("CloneObj: " + l4);
        Config.dbg("NewObjDyn: " + l5);
    }

    private static long testClone(int n, int n2) {
        long l = System.currentTimeMillis();
        int[] nArray = new int[n];
        for (int i = 0; i < n2; ++i) {
            int[] nArray2 = (int[])nArray.clone();
        }
        long l2 = System.currentTimeMillis();
        return l2 - l;
    }

    private static long testNew(int n, int n2) {
        long l = System.currentTimeMillis();
        for (int i = 0; i < n2; ++i) {
            int[] nArray = (int[])Array.newInstance(Integer.TYPE, n);
        }
        long l2 = System.currentTimeMillis();
        return l2 - l;
    }

    private static long testCloneObj(int n, int n2) {
        long l = System.currentTimeMillis();
        BlockState[] blockStateArray = new BlockState[n];
        for (int i = 0; i < n2; ++i) {
            BlockState[] blockStateArray2 = (BlockState[])blockStateArray.clone();
        }
        long l2 = System.currentTimeMillis();
        return l2 - l;
    }

    private static long testNewObj(int n, int n2) {
        long l = System.currentTimeMillis();
        for (int i = 0; i < n2; ++i) {
            BlockState[] blockStateArray = new BlockState[n];
        }
        long l2 = System.currentTimeMillis();
        return l2 - l;
    }

    private static long testNewObjDyn(Class clazz, int n, int n2) {
        long l = System.currentTimeMillis();
        for (int i = 0; i < n2; ++i) {
            Object[] objectArray = (Object[])Array.newInstance(clazz, n);
        }
        long l2 = System.currentTimeMillis();
        return l2 - l;
    }
}

