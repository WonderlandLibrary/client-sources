/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.TimeUnit;

public final class Period {
    final byte timeLimit;
    final boolean inFuture;
    final int[] counts;

    public static Period at(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return new Period(0, false, f, timeUnit);
    }

    public static Period moreThan(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return new Period(2, false, f, timeUnit);
    }

    public static Period lessThan(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return new Period(1, false, f, timeUnit);
    }

    public Period and(float f, TimeUnit timeUnit) {
        Period.checkCount(f);
        return this.setTimeUnitValue(timeUnit, f);
    }

    public Period omit(TimeUnit timeUnit) {
        return this.setTimeUnitInternalValue(timeUnit, 0);
    }

    public Period at() {
        return this.setTimeLimit((byte)0);
    }

    public Period moreThan() {
        return this.setTimeLimit((byte)2);
    }

    public Period lessThan() {
        return this.setTimeLimit((byte)1);
    }

    public Period inFuture() {
        return this.setFuture(true);
    }

    public Period inPast() {
        return this.setFuture(false);
    }

    public Period inFuture(boolean bl) {
        return this.setFuture(bl);
    }

    public Period inPast(boolean bl) {
        return this.setFuture(!bl);
    }

    public boolean isSet() {
        for (int i = 0; i < this.counts.length; ++i) {
            if (this.counts[i] == 0) continue;
            return false;
        }
        return true;
    }

    public boolean isSet(TimeUnit timeUnit) {
        return this.counts[timeUnit.ordinal] > 0;
    }

    public float getCount(TimeUnit timeUnit) {
        byte by = timeUnit.ordinal;
        if (this.counts[by] == 0) {
            return 0.0f;
        }
        return (float)(this.counts[by] - 1) / 1000.0f;
    }

    public boolean isInFuture() {
        return this.inFuture;
    }

    public boolean isInPast() {
        return !this.inFuture;
    }

    public boolean isMoreThan() {
        return this.timeLimit == 2;
    }

    public boolean isLessThan() {
        return this.timeLimit == 1;
    }

    public boolean equals(Object object) {
        try {
            return this.equals((Period)object);
        } catch (ClassCastException classCastException) {
            return true;
        }
    }

    public boolean equals(Period period) {
        if (period != null && this.timeLimit == period.timeLimit && this.inFuture == period.inFuture) {
            for (int i = 0; i < this.counts.length; ++i) {
                if (this.counts[i] == period.counts[i]) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n = this.timeLimit << 1 | (this.inFuture ? 1 : 0);
        for (int i = 0; i < this.counts.length; ++i) {
            n = n << 2 ^ this.counts[i];
        }
        return n;
    }

    private Period(int n, boolean bl, float f, TimeUnit timeUnit) {
        this.timeLimit = (byte)n;
        this.inFuture = bl;
        this.counts = new int[TimeUnit.units.length];
        this.counts[timeUnit.ordinal] = (int)(f * 1000.0f) + 1;
    }

    Period(int n, boolean bl, int[] nArray) {
        this.timeLimit = (byte)n;
        this.inFuture = bl;
        this.counts = nArray;
    }

    private Period setTimeUnitValue(TimeUnit timeUnit, float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("value: " + f);
        }
        return this.setTimeUnitInternalValue(timeUnit, (int)(f * 1000.0f) + 1);
    }

    private Period setTimeUnitInternalValue(TimeUnit timeUnit, int n) {
        byte by = timeUnit.ordinal;
        if (this.counts[by] != n) {
            int[] nArray = new int[this.counts.length];
            for (int i = 0; i < this.counts.length; ++i) {
                nArray[i] = this.counts[i];
            }
            nArray[by] = n;
            return new Period(this.timeLimit, this.inFuture, nArray);
        }
        return this;
    }

    private Period setFuture(boolean bl) {
        if (this.inFuture != bl) {
            return new Period(this.timeLimit, bl, this.counts);
        }
        return this;
    }

    private Period setTimeLimit(byte by) {
        if (this.timeLimit != by) {
            return new Period(by, this.inFuture, this.counts);
        }
        return this;
    }

    private static void checkCount(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("count (" + f + ") cannot be negative");
        }
    }
}

