/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.TimeZoneRule;
import java.util.Arrays;
import java.util.Date;

public class TimeArrayTimeZoneRule
extends TimeZoneRule {
    private static final long serialVersionUID = -1117109130077415245L;
    private final long[] startTimes;
    private final int timeType;

    public TimeArrayTimeZoneRule(String string, int n, int n2, long[] lArray, int n3) {
        super(string, n, n2);
        if (lArray == null || lArray.length == 0) {
            throw new IllegalArgumentException("No start times are specified.");
        }
        this.startTimes = (long[])lArray.clone();
        Arrays.sort(this.startTimes);
        this.timeType = n3;
    }

    public long[] getStartTimes() {
        return (long[])this.startTimes.clone();
    }

    public int getTimeType() {
        return this.timeType;
    }

    @Override
    public Date getFirstStart(int n, int n2) {
        return new Date(this.getUTC(this.startTimes[0], n, n2));
    }

    @Override
    public Date getFinalStart(int n, int n2) {
        return new Date(this.getUTC(this.startTimes[this.startTimes.length - 1], n, n2));
    }

    @Override
    public Date getNextStart(long l, int n, int n2, boolean bl) {
        long l2;
        int n3;
        for (n3 = this.startTimes.length - 1; n3 >= 0 && (l2 = this.getUTC(this.startTimes[n3], n, n2)) >= l && (bl || l2 != l); --n3) {
        }
        if (n3 == this.startTimes.length - 1) {
            return null;
        }
        return new Date(this.getUTC(this.startTimes[n3 + 1], n, n2));
    }

    @Override
    public Date getPreviousStart(long l, int n, int n2, boolean bl) {
        for (int i = this.startTimes.length - 1; i >= 0; --i) {
            long l2 = this.getUTC(this.startTimes[i], n, n2);
            if (l2 >= l && (!bl || l2 != l)) continue;
            return new Date(l2);
        }
        return null;
    }

    @Override
    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        if (!(timeZoneRule instanceof TimeArrayTimeZoneRule)) {
            return true;
        }
        if (this.timeType == ((TimeArrayTimeZoneRule)timeZoneRule).timeType && Arrays.equals(this.startTimes, ((TimeArrayTimeZoneRule)timeZoneRule).startTimes)) {
            return super.isEquivalentTo(timeZoneRule);
        }
        return true;
    }

    @Override
    public boolean isTransitionRule() {
        return false;
    }

    private long getUTC(long l, int n, int n2) {
        if (this.timeType != 2) {
            l -= (long)n;
        }
        if (this.timeType == 0) {
            l -= (long)n2;
        }
        return l;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(", timeType=");
        stringBuilder.append(this.timeType);
        stringBuilder.append(", startTimes=[");
        for (int i = 0; i < this.startTimes.length; ++i) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(Long.toString(this.startTimes[i]));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

