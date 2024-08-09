/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.IIntArray;

public abstract class IntReferenceHolder {
    private int lastKnownValue;

    public static IntReferenceHolder create(IIntArray iIntArray, int n) {
        return new IntReferenceHolder(iIntArray, n){
            final IIntArray val$data;
            final int val$idx;
            {
                this.val$data = iIntArray;
                this.val$idx = n;
            }

            @Override
            public int get() {
                return this.val$data.get(this.val$idx);
            }

            @Override
            public void set(int n) {
                this.val$data.set(this.val$idx, n);
            }
        };
    }

    public static IntReferenceHolder create(int[] nArray, int n) {
        return new IntReferenceHolder(nArray, n){
            final int[] val$data;
            final int val$idx;
            {
                this.val$data = nArray;
                this.val$idx = n;
            }

            @Override
            public int get() {
                return this.val$data[this.val$idx];
            }

            @Override
            public void set(int n) {
                this.val$data[this.val$idx] = n;
            }
        };
    }

    public static IntReferenceHolder single() {
        return new IntReferenceHolder(){
            private int value;

            @Override
            public int get() {
                return this.value;
            }

            @Override
            public void set(int n) {
                this.value = n;
            }
        };
    }

    public abstract int get();

    public abstract void set(int var1);

    public boolean isDirty() {
        int n = this.get();
        boolean bl = n != this.lastKnownValue;
        this.lastKnownValue = n;
        return bl;
    }
}

