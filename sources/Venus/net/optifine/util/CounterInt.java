/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

public class CounterInt {
    private int startValue;
    private int value;

    public CounterInt(int n) {
        this.startValue = n;
        this.value = n;
    }

    public synchronized int nextValue() {
        return this.value++;
    }

    public synchronized void reset() {
        this.value = this.startValue;
    }

    public int getValue() {
        return this.value;
    }
}

