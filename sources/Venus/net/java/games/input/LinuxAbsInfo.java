/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class LinuxAbsInfo {
    private int value;
    private int minimum;
    private int maximum;
    private int fuzz;
    private int flat;

    LinuxAbsInfo() {
    }

    public final void set(int n, int n2, int n3, int n4, int n5) {
        this.value = n;
        this.minimum = n2;
        this.maximum = n3;
        this.fuzz = n4;
        this.flat = n5;
    }

    public final String toString() {
        return "AbsInfo: value = " + this.value + " | min = " + this.minimum + " | max = " + this.maximum + " | fuzz = " + this.fuzz + " | flat = " + this.flat;
    }

    public final int getValue() {
        return this.value;
    }

    final int getMax() {
        return this.maximum;
    }

    final int getMin() {
        return this.minimum;
    }

    final int getFlat() {
        return this.flat;
    }

    final int getFuzz() {
        return this.fuzz;
    }
}

