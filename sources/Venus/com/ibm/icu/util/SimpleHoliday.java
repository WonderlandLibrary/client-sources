/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.DateRule;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.Holiday;
import com.ibm.icu.util.RangeDateRule;
import com.ibm.icu.util.SimpleDateRule;
import java.util.Date;

public class SimpleHoliday
extends Holiday {
    public static final SimpleHoliday NEW_YEARS_DAY = new SimpleHoliday(0, 1, "New Year's Day");
    public static final SimpleHoliday EPIPHANY = new SimpleHoliday(0, 6, "Epiphany");
    public static final SimpleHoliday MAY_DAY = new SimpleHoliday(4, 1, "May Day");
    public static final SimpleHoliday ASSUMPTION = new SimpleHoliday(7, 15, "Assumption");
    public static final SimpleHoliday ALL_SAINTS_DAY = new SimpleHoliday(10, 1, "All Saints' Day");
    public static final SimpleHoliday ALL_SOULS_DAY = new SimpleHoliday(10, 2, "All Souls' Day");
    public static final SimpleHoliday IMMACULATE_CONCEPTION = new SimpleHoliday(11, 8, "Immaculate Conception");
    public static final SimpleHoliday CHRISTMAS_EVE = new SimpleHoliday(11, 24, "Christmas Eve");
    public static final SimpleHoliday CHRISTMAS = new SimpleHoliday(11, 25, "Christmas");
    public static final SimpleHoliday BOXING_DAY = new SimpleHoliday(11, 26, "Boxing Day");
    public static final SimpleHoliday ST_STEPHENS_DAY = new SimpleHoliday(11, 26, "St. Stephen's Day");
    public static final SimpleHoliday NEW_YEARS_EVE = new SimpleHoliday(11, 31, "New Year's Eve");

    public SimpleHoliday(int n, int n2, String string) {
        super(string, new SimpleDateRule(n, n2));
    }

    public SimpleHoliday(int n, int n2, String string, int n3) {
        super(string, SimpleHoliday.rangeRule(n3, 0, new SimpleDateRule(n, n2)));
    }

    public SimpleHoliday(int n, int n2, String string, int n3, int n4) {
        super(string, SimpleHoliday.rangeRule(n3, n4, new SimpleDateRule(n, n2)));
    }

    public SimpleHoliday(int n, int n2, int n3, String string) {
        super(string, new SimpleDateRule(n, n2, n3 > 0 ? n3 : -n3, n3 > 0));
    }

    public SimpleHoliday(int n, int n2, int n3, String string, int n4) {
        super(string, SimpleHoliday.rangeRule(n4, 0, new SimpleDateRule(n, n2, n3 > 0 ? n3 : -n3, n3 > 0)));
    }

    public SimpleHoliday(int n, int n2, int n3, String string, int n4, int n5) {
        super(string, SimpleHoliday.rangeRule(n4, n5, new SimpleDateRule(n, n2, n3 > 0 ? n3 : -n3, n3 > 0)));
    }

    private static DateRule rangeRule(int n, int n2, DateRule dateRule) {
        Cloneable cloneable;
        if (n == 0 && n2 == 0) {
            return dateRule;
        }
        RangeDateRule rangeDateRule = new RangeDateRule();
        if (n != 0) {
            cloneable = new GregorianCalendar(n, 0, 1);
            rangeDateRule.add(cloneable.getTime(), dateRule);
        } else {
            rangeDateRule.add(dateRule);
        }
        if (n2 != 0) {
            cloneable = new GregorianCalendar(n2, 11, 31).getTime();
            rangeDateRule.add((Date)cloneable, null);
        }
        return rangeDateRule;
    }
}

