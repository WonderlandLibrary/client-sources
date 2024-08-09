package net.optifine.util;

import net.minecraft.block.BlockState;
import net.optifine.Config;

import java.lang.reflect.Array;
import java.util.ArrayDeque;

public class CacheObjectArray {
    private static final ArrayDeque<int[]> arrays = new ArrayDeque<>();

    private static synchronized int[] allocateArray(int size) {
        int[] aint = arrays.pollLast();

        if (aint == null || aint.length < size) {
            aint = new int[size];
        }

        return aint;
    }

    public static synchronized void freeArray(int[] ints) {
        int maxCacheSize = 10;
        if (arrays.size() < maxCacheSize) {
            arrays.add(ints);
        }
    }

    public static void main(String[] args) throws Exception {
        int i = 4096;
        int j = 500000;
        testNew(i, j);
        testClone(i, j);
        testNewObj(i, j);
        testCloneObj(i, j);
        testNewObjDyn(BlockState.class, i, j);
        long k = testNew(i, j);
        long l = testClone(i, j);
        long i1 = testNewObj(i, j);
        long j1 = testCloneObj(i, j);
        long k1 = testNewObjDyn(BlockState.class, i, j);
        Config.dbg("New: " + k);
        Config.dbg("Clone: " + l);
        Config.dbg("NewObj: " + i1);
        Config.dbg("CloneObj: " + j1);
        Config.dbg("NewObjDyn: " + k1);
    }

    private static long testClone(int size, int count) {
        long i = System.currentTimeMillis();
        int[] aint = new int[size];

        for (int j = 0; j < count; ++j) {
            int[] aint1 = aint.clone();
        }

        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testNew(int size, int count) {
        long i = System.currentTimeMillis();

        for (int j = 0; j < count; ++j) {
            int[] aint = (int[]) Array.newInstance(Integer.TYPE, size);
        }

        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testCloneObj(int size, int count) {
        long i = System.currentTimeMillis();
        BlockState[] ablockstate = new BlockState[size];

        for (int j = 0; j < count; ++j) {
            BlockState[] ablockstate1 = ablockstate.clone();
        }

        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testNewObj(int size, int count) {
        long i = System.currentTimeMillis();

        for (int j = 0; j < count; ++j) {
            BlockState[] ablockstate = new BlockState[size];
        }

        long k = System.currentTimeMillis();
        return k - i;
    }

    private static long testNewObjDyn(Class cls, int size, int count) {
        long i = System.currentTimeMillis();

        for (int j = 0; j < count; ++j) {
            Object[] aobject = (Object[]) Array.newInstance(cls, size);
        }

        long k = System.currentTimeMillis();
        return k - i;
    }
}
