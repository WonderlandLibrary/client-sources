/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.IIntArray;

public class IntArray
implements IIntArray {
    private final int[] array;

    public IntArray(int n) {
        this.array = new int[n];
    }

    @Override
    public int get(int n) {
        return this.array[n];
    }

    @Override
    public void set(int n, int n2) {
        this.array[n] = n2;
    }

    @Override
    public int size() {
        return this.array.length;
    }
}

