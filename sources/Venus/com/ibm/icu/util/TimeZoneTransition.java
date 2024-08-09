/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.TimeZoneRule;

public class TimeZoneTransition {
    private final TimeZoneRule from;
    private final TimeZoneRule to;
    private final long time;

    public TimeZoneTransition(long l, TimeZoneRule timeZoneRule, TimeZoneRule timeZoneRule2) {
        this.time = l;
        this.from = timeZoneRule;
        this.to = timeZoneRule2;
    }

    public long getTime() {
        return this.time;
    }

    public TimeZoneRule getTo() {
        return this.to;
    }

    public TimeZoneRule getFrom() {
        return this.from;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("time=" + this.time);
        stringBuilder.append(", from={" + this.from + "}");
        stringBuilder.append(", to={" + this.to + "}");
        return stringBuilder.toString();
    }
}

