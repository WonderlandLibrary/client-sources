/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class BitArray {
    private final long[] longArray;
    private final int bitsPerEntry;
    private final long maxEntryValue;
    private final int arraySize;

    public BitArray(int bitsPerEntryIn, int arraySizeIn) {
        Validate.inclusiveBetween(1L, 32L, bitsPerEntryIn);
        this.arraySize = arraySizeIn;
        this.bitsPerEntry = bitsPerEntryIn;
        this.maxEntryValue = (1L << bitsPerEntryIn) - 1L;
        this.longArray = new long[MathHelper.roundUp(arraySizeIn * bitsPerEntryIn, 64) / 64];
    }

    public void setAt(int index, int value) {
        Validate.inclusiveBetween(0L, this.arraySize - 1, index);
        Validate.inclusiveBetween(0L, this.maxEntryValue, value);
        int i = index * this.bitsPerEntry;
        int j = i / 64;
        int k = ((index + 1) * this.bitsPerEntry - 1) / 64;
        int l = i % 64;
        this.longArray[j] = this.longArray[j] & (this.maxEntryValue << l ^ 0xFFFFFFFFFFFFFFFFL) | ((long)value & this.maxEntryValue) << l;
        if (j != k) {
            int i1 = 64 - l;
            int j1 = this.bitsPerEntry - i1;
            this.longArray[k] = this.longArray[k] >>> j1 << j1 | ((long)value & this.maxEntryValue) >> i1;
        }
    }

    public int getAt(int index) {
        Validate.inclusiveBetween(0L, this.arraySize - 1, index);
        int i = index * this.bitsPerEntry;
        int j = i / 64;
        int k = ((index + 1) * this.bitsPerEntry - 1) / 64;
        int l = i % 64;
        if (j == k) {
            return (int)(this.longArray[j] >>> l & this.maxEntryValue);
        }
        int i1 = 64 - l;
        return (int)((this.longArray[j] >>> l | this.longArray[k] << i1) & this.maxEntryValue);
    }

    public long[] getBackingLongArray() {
        return this.longArray;
    }

    public int size() {
        return this.arraySize;
    }
}

