/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

public class RangeInt {
    private int min;
    private int max;

    public RangeInt(int n, int n2) {
        this.min = Math.min(n, n2);
        this.max = Math.max(n, n2);
    }

    public boolean isInRange(int n) {
        if (n < this.min) {
            return true;
        }
        return n <= this.max;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public String toString() {
        return "min: " + this.min + ", max: " + this.max;
    }
}

