/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.DateRule;
import com.ibm.icu.util.GregorianCalendar;
import java.util.Date;

public class SimpleDateRule
implements DateRule {
    private Calendar calendar = new GregorianCalendar();
    private int month;
    private int dayOfMonth;
    private int dayOfWeek;

    public SimpleDateRule(int n, int n2) {
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = 0;
    }

    SimpleDateRule(int n, int n2, Calendar calendar) {
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = 0;
        this.calendar = calendar;
    }

    public SimpleDateRule(int n, int n2, int n3, boolean bl) {
        this.month = n;
        this.dayOfMonth = n2;
        this.dayOfWeek = bl ? n3 : -n3;
    }

    @Override
    public Date firstAfter(Date date) {
        return this.doFirstBetween(date, null);
    }

    @Override
    public Date firstBetween(Date date, Date date2) {
        return this.doFirstBetween(date, date2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isOn(Date date) {
        Calendar calendar;
        Calendar calendar2 = calendar = this.calendar;
        synchronized (calendar2) {
            calendar.setTime(date);
            int n = calendar.get(6);
            calendar.setTime(this.computeInYear(calendar.get(1), calendar));
            return calendar.get(6) == n;
        }
    }

    @Override
    public boolean isBetween(Date date, Date date2) {
        return this.firstBetween(date, date2) != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date doFirstBetween(Date date, Date date2) {
        Calendar calendar;
        Calendar calendar2 = calendar = this.calendar;
        synchronized (calendar2) {
            calendar.setTime(date);
            int n = calendar.get(1);
            int n2 = calendar.get(2);
            if (n2 > this.month) {
                ++n;
            }
            Date date3 = this.computeInYear(n, calendar);
            if (n2 == this.month && date3.before(date)) {
                date3 = this.computeInYear(n + 1, calendar);
            }
            if (date2 != null && date3.after(date2)) {
                return null;
            }
            return date3;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date computeInYear(int n, Calendar calendar) {
        Calendar calendar2 = calendar;
        synchronized (calendar2) {
            calendar.clear();
            calendar.set(0, calendar.getMaximum(0));
            calendar.set(1, n);
            calendar.set(2, this.month);
            calendar.set(5, this.dayOfMonth);
            if (this.dayOfWeek != 0) {
                calendar.setTime(calendar.getTime());
                int n2 = calendar.get(7);
                int n3 = 0;
                n3 = this.dayOfWeek > 0 ? (this.dayOfWeek - n2 + 7) % 7 : -((this.dayOfWeek + n2 + 7) % 7);
                calendar.add(5, n3);
            }
            return calendar.getTime();
        }
    }
}

