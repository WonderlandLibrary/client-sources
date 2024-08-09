/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.DateRule;
import com.ibm.icu.util.Range;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RangeDateRule
implements DateRule {
    List<Range> ranges = new ArrayList<Range>(2);

    public void add(DateRule dateRule) {
        this.add(new Date(Long.MIN_VALUE), dateRule);
    }

    public void add(Date date, DateRule dateRule) {
        this.ranges.add(new Range(date, dateRule));
    }

    @Override
    public Date firstAfter(Date date) {
        int n = this.startIndex(date);
        if (n == this.ranges.size()) {
            n = 0;
        }
        Date date2 = null;
        Range range = this.rangeAt(n);
        Range range2 = this.rangeAt(n + 1);
        if (range != null && range.rule != null) {
            date2 = range2 != null ? range.rule.firstBetween(date, range2.start) : range.rule.firstAfter(date);
        }
        return date2;
    }

    @Override
    public Date firstBetween(Date date, Date date2) {
        if (date2 == null) {
            return this.firstAfter(date);
        }
        int n = this.startIndex(date);
        Date date3 = null;
        Range range = this.rangeAt(n);
        while (date3 == null && range != null && !range.start.after(date2)) {
            Range range2 = range;
            range = this.rangeAt(n + 1);
            if (range2.rule == null) continue;
            Date date4 = range != null && !range.start.after(date2) ? range.start : date2;
            date3 = range2.rule.firstBetween(date, date4);
        }
        return date3;
    }

    @Override
    public boolean isOn(Date date) {
        Range range = this.rangeAt(this.startIndex(date));
        return range != null && range.rule != null && range.rule.isOn(date);
    }

    @Override
    public boolean isBetween(Date date, Date date2) {
        return this.firstBetween(date, date2) == null;
    }

    private int startIndex(Date date) {
        int n = this.ranges.size();
        int n2 = 0;
        while (n2 < this.ranges.size()) {
            Range range = this.ranges.get(n2);
            if (date.before(range.start)) break;
            n = n2++;
        }
        return n;
    }

    private Range rangeAt(int n) {
        return n < this.ranges.size() ? this.ranges.get(n) : null;
    }
}

