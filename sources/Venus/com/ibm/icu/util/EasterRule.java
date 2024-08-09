/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.DateRule;
import com.ibm.icu.util.GregorianCalendar;
import java.util.Date;

class EasterRule
implements DateRule {
    private int daysAfterEaster;
    private GregorianCalendar calendar = new GregorianCalendar();

    public EasterRule(int n, boolean bl) {
        this.daysAfterEaster = n;
        if (bl) {
            this.calendar.setGregorianChange(new Date(Long.MAX_VALUE));
        }
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
        GregorianCalendar gregorianCalendar = this.calendar;
        synchronized (gregorianCalendar) {
            this.calendar.setTime(date);
            int n = this.calendar.get(6);
            this.calendar.setTime(this.computeInYear(this.calendar.getTime(), this.calendar));
            return this.calendar.get(6) == n;
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
        GregorianCalendar gregorianCalendar = this.calendar;
        synchronized (gregorianCalendar) {
            Date date3 = this.computeInYear(date, this.calendar);
            if (date3.before(date)) {
                this.calendar.setTime(date);
                this.calendar.get(1);
                this.calendar.add(1, 1);
                date3 = this.computeInYear(this.calendar.getTime(), this.calendar);
            }
            if (date2 != null && !date3.before(date2)) {
                return null;
            }
            return date3;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Date computeInYear(Date date, GregorianCalendar gregorianCalendar) {
        if (gregorianCalendar == null) {
            gregorianCalendar = this.calendar;
        }
        GregorianCalendar gregorianCalendar2 = gregorianCalendar;
        synchronized (gregorianCalendar2) {
            int n;
            int n2;
            gregorianCalendar.setTime(date);
            int n3 = gregorianCalendar.get(1);
            int n4 = n3 % 19;
            int n5 = 0;
            int n6 = 0;
            if (gregorianCalendar.getTime().after(gregorianCalendar.getGregorianChange())) {
                n2 = n3 / 100;
                n = (n2 - n2 / 4 - (8 * n2 + 13) / 25 + 19 * n4 + 15) % 30;
                n5 = n - n / 28 * (1 - n / 28 * (29 / (n + 1)) * ((21 - n4) / 11));
                n6 = (n3 + n3 / 4 + n5 + 2 - n2 + n2 / 4) % 7;
            } else {
                n5 = (19 * n4 + 15) % 30;
                n6 = (n3 + n3 / 4 + n5) % 7;
            }
            n2 = n5 - n6;
            n = 3 + (n2 + 40) / 44;
            int n7 = n2 + 28 - 31 * (n / 4);
            gregorianCalendar.clear();
            gregorianCalendar.set(0, 1);
            gregorianCalendar.set(1, n3);
            gregorianCalendar.set(2, n - 1);
            gregorianCalendar.set(5, n7);
            gregorianCalendar.getTime();
            gregorianCalendar.add(5, this.daysAfterEaster);
            return gregorianCalendar.getTime();
        }
    }
}

