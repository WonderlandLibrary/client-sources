/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.Random;

public class RangedInteger {
    private final int minInclusive;
    private final int max;

    public RangedInteger(int n, int n2) {
        if (n2 < n) {
            throw new IllegalArgumentException("max must be >= minInclusive! Given minInclusive: " + n + ", Given max: " + n2);
        }
        this.minInclusive = n;
        this.max = n2;
    }

    public static RangedInteger createRangedInteger(int n, int n2) {
        return new RangedInteger(n, n2);
    }

    public int getRandomWithinRange(Random random2) {
        return this.minInclusive == this.max ? this.minInclusive : random2.nextInt(this.max - this.minInclusive + 1) + this.minInclusive;
    }

    public int getMinInclusive() {
        return this.minInclusive;
    }

    public int getMax() {
        return this.max;
    }

    public String toString() {
        return "IntRange[" + this.minInclusive + "-" + this.max + "]";
    }
}

