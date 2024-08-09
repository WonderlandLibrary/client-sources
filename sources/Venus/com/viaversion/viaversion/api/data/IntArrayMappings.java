/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.Mappings;
import java.util.Arrays;

public class IntArrayMappings
implements Mappings {
    private final int[] mappings;
    private final int mappedIds;

    protected IntArrayMappings(int[] nArray, int n) {
        this.mappings = nArray;
        this.mappedIds = n;
    }

    public static IntArrayMappings of(int[] nArray, int n) {
        return new IntArrayMappings(nArray, n);
    }

    @Deprecated
    public static Mappings.Builder<IntArrayMappings> builder() {
        return Mappings.builder(IntArrayMappings::new);
    }

    @Override
    public int getNewId(int n) {
        return n >= 0 && n < this.mappings.length ? this.mappings[n] : -1;
    }

    @Override
    public void setNewId(int n, int n2) {
        this.mappings[n] = n2;
    }

    @Override
    public int size() {
        return this.mappings.length;
    }

    @Override
    public int mappedSize() {
        return this.mappedIds;
    }

    @Override
    public Mappings inverse() {
        int[] nArray = new int[this.mappedIds];
        Arrays.fill(nArray, -1);
        for (int i = 0; i < this.mappings.length; ++i) {
            int n = this.mappings[i];
            if (n == -1 || nArray[n] != -1) continue;
            nArray[n] = i;
        }
        return IntArrayMappings.of(nArray, this.mappings.length);
    }

    public int[] raw() {
        return this.mappings;
    }
}

