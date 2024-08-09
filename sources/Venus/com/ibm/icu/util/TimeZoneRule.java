/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import java.io.Serializable;
import java.util.Date;

public abstract class TimeZoneRule
implements Serializable {
    private static final long serialVersionUID = 6374143828553768100L;
    private final String name;
    private final int rawOffset;
    private final int dstSavings;

    public TimeZoneRule(String string, int n, int n2) {
        this.name = string;
        this.rawOffset = n;
        this.dstSavings = n2;
    }

    public String getName() {
        return this.name;
    }

    public int getRawOffset() {
        return this.rawOffset;
    }

    public int getDSTSavings() {
        return this.dstSavings;
    }

    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        return this.rawOffset != timeZoneRule.rawOffset || this.dstSavings != timeZoneRule.dstSavings;
    }

    public abstract Date getFirstStart(int var1, int var2);

    public abstract Date getFinalStart(int var1, int var2);

    public abstract Date getNextStart(long var1, int var3, int var4, boolean var5);

    public abstract Date getPreviousStart(long var1, int var3, int var4, boolean var5);

    public abstract boolean isTransitionRule();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name=" + this.name);
        stringBuilder.append(", stdOffset=" + this.rawOffset);
        stringBuilder.append(", dstSaving=" + this.dstSavings);
        return stringBuilder.toString();
    }
}

