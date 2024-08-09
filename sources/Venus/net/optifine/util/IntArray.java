/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.util.math.MathHelper;

public class IntArray {
    private int[] array = null;
    private int position = 0;
    private int limit = 0;

    public IntArray(int n) {
        this.array = new int[n];
    }

    public void put(int n) {
        this.checkPutIndex(this.position);
        this.array[this.position] = n;
        ++this.position;
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    public void put(int n, int n2) {
        this.checkPutIndex(n2);
        this.array[n] = n2;
        if (this.limit < n) {
            this.limit = n;
        }
    }

    public void position(int n) {
        this.position = n;
    }

    public void put(int[] nArray) {
        this.checkPutIndex(this.position + nArray.length - 1);
        for (int this.array[this.position] : nArray) {
            ++this.position;
        }
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    private void checkPutIndex(int n) {
        if (n >= this.array.length) {
            int n2 = MathHelper.smallestEncompassingPowerOfTwo(n + 1);
            int[] nArray = new int[n2];
            System.arraycopy(this.array, 0, nArray, 0, this.array.length);
            this.array = nArray;
        }
    }

    public int get(int n) {
        return this.array[n];
    }

    public int[] getArray() {
        return this.array;
    }

    public void clear() {
        this.position = 0;
        this.limit = 0;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getPosition() {
        return this.position;
    }

    public int[] toIntArray() {
        int[] nArray = new int[this.limit];
        System.arraycopy(this.array, 0, nArray, 0, nArray.length);
        return nArray;
    }

    public String toString() {
        return "position: " + this.position + ", limit: " + this.limit + ", capacity: " + this.array.length;
    }
}

