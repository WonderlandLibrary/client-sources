/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.util.DateTimeRule;
import com.ibm.icu.util.TimeZoneRule;
import java.util.Date;

public class AnnualTimeZoneRule
extends TimeZoneRule {
    private static final long serialVersionUID = -8870666707791230688L;
    public static final int MAX_YEAR = Integer.MAX_VALUE;
    private final DateTimeRule dateTimeRule;
    private final int startYear;
    private final int endYear;

    public AnnualTimeZoneRule(String string, int n, int n2, DateTimeRule dateTimeRule, int n3, int n4) {
        super(string, n, n2);
        this.dateTimeRule = dateTimeRule;
        this.startYear = n3;
        this.endYear = n4;
    }

    public DateTimeRule getRule() {
        return this.dateTimeRule;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public Date getStartInYear(int n, int n2, int n3) {
        long l;
        if (n < this.startYear || n > this.endYear) {
            return null;
        }
        int n4 = this.dateTimeRule.getDateRuleType();
        if (n4 == 0) {
            l = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), this.dateTimeRule.getRuleDayOfMonth());
        } else {
            int n5;
            int n6;
            boolean bl = true;
            if (n4 == 1) {
                n6 = this.dateTimeRule.getRuleWeekInMonth();
                if (n6 > 0) {
                    l = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), 1);
                    l += (long)(7 * (n6 - 1));
                } else {
                    bl = false;
                    l = Grego.fieldsToDay(n, this.dateTimeRule.getRuleMonth(), Grego.monthLength(n, this.dateTimeRule.getRuleMonth()));
                    l += (long)(7 * (n6 + 1));
                }
            } else {
                n6 = this.dateTimeRule.getRuleMonth();
                n5 = this.dateTimeRule.getRuleDayOfMonth();
                if (n4 == 3) {
                    bl = false;
                    if (n6 == 1 && n5 == 29 && !Grego.isLeapYear(n)) {
                        --n5;
                    }
                }
                l = Grego.fieldsToDay(n, n6, n5);
            }
            n6 = Grego.dayOfWeek(l);
            n5 = this.dateTimeRule.getRuleDayOfWeek() - n6;
            n5 = bl ? (n5 < 0 ? n5 + 7 : n5) : (n5 > 0 ? n5 - 7 : n5);
            l += (long)n5;
        }
        long l2 = l * 86400000L + (long)this.dateTimeRule.getRuleMillisInDay();
        if (this.dateTimeRule.getTimeRuleType() != 2) {
            l2 -= (long)n2;
        }
        if (this.dateTimeRule.getTimeRuleType() == 0) {
            l2 -= (long)n3;
        }
        return new Date(l2);
    }

    @Override
    public Date getFirstStart(int n, int n2) {
        return this.getStartInYear(this.startYear, n, n2);
    }

    @Override
    public Date getFinalStart(int n, int n2) {
        if (this.endYear == Integer.MAX_VALUE) {
            return null;
        }
        return this.getStartInYear(this.endYear, n, n2);
    }

    @Override
    public Date getNextStart(long l, int n, int n2, boolean bl) {
        int[] nArray = Grego.timeToFields(l, null);
        int n3 = nArray[0];
        if (n3 < this.startYear) {
            return this.getFirstStart(n, n2);
        }
        Date date = this.getStartInYear(n3, n, n2);
        if (date != null && (date.getTime() < l || !bl && date.getTime() == l)) {
            date = this.getStartInYear(n3 + 1, n, n2);
        }
        return date;
    }

    @Override
    public Date getPreviousStart(long l, int n, int n2, boolean bl) {
        int[] nArray = Grego.timeToFields(l, null);
        int n3 = nArray[0];
        if (n3 > this.endYear) {
            return this.getFinalStart(n, n2);
        }
        Date date = this.getStartInYear(n3, n, n2);
        if (date != null && (date.getTime() > l || !bl && date.getTime() == l)) {
            date = this.getStartInYear(n3 - 1, n, n2);
        }
        return date;
    }

    @Override
    public boolean isEquivalentTo(TimeZoneRule timeZoneRule) {
        if (!(timeZoneRule instanceof AnnualTimeZoneRule)) {
            return true;
        }
        AnnualTimeZoneRule annualTimeZoneRule = (AnnualTimeZoneRule)timeZoneRule;
        if (this.startYear == annualTimeZoneRule.startYear && this.endYear == annualTimeZoneRule.endYear && this.dateTimeRule.equals(annualTimeZoneRule.dateTimeRule)) {
            return super.isEquivalentTo(timeZoneRule);
        }
        return true;
    }

    @Override
    public boolean isTransitionRule() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(", rule={" + this.dateTimeRule + "}");
        stringBuilder.append(", startYear=" + this.startYear);
        stringBuilder.append(", endYear=");
        if (this.endYear == Integer.MAX_VALUE) {
            stringBuilder.append("max");
        } else {
            stringBuilder.append(this.endYear);
        }
        return stringBuilder.toString();
    }
}

