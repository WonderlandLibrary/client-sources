/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.ChineseCalendar;
import com.ibm.icu.util.InitialTimeZoneRule;
import com.ibm.icu.util.RuleBasedTimeZone;
import com.ibm.icu.util.TimeArrayTimeZoneRule;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;

@Deprecated
public class DangiCalendar
extends ChineseCalendar {
    private static final long serialVersionUID = 8156297445349501985L;
    private static final int DANGI_EPOCH_YEAR = -2332;
    private static final TimeZone KOREA_ZONE;

    @Deprecated
    public DangiCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    @Deprecated
    public DangiCalendar(Date date) {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
        this.setTime(date);
    }

    @Deprecated
    public DangiCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale, -2332, KOREA_ZONE);
    }

    @Override
    @Deprecated
    public String getType() {
        return "dangi";
    }

    static {
        InitialTimeZoneRule initialTimeZoneRule = new InitialTimeZoneRule("GMT+8", 28800000, 0);
        long[] lArray = new long[]{-2302128000000L};
        long[] lArray2 = new long[]{-2270592000000L};
        long[] lArray3 = new long[]{-1829088000000L};
        TimeArrayTimeZoneRule timeArrayTimeZoneRule = new TimeArrayTimeZoneRule("Korean 1897", 25200000, 0, lArray, 1);
        TimeArrayTimeZoneRule timeArrayTimeZoneRule2 = new TimeArrayTimeZoneRule("Korean 1898-1911", 28800000, 0, lArray2, 1);
        TimeArrayTimeZoneRule timeArrayTimeZoneRule3 = new TimeArrayTimeZoneRule("Korean 1912-", 32400000, 0, lArray3, 1);
        RuleBasedTimeZone ruleBasedTimeZone = new RuleBasedTimeZone("KOREA_ZONE", initialTimeZoneRule);
        ruleBasedTimeZone.addTransitionRule(timeArrayTimeZoneRule);
        ruleBasedTimeZone.addTransitionRule(timeArrayTimeZoneRule2);
        ruleBasedTimeZone.addTransitionRule(timeArrayTimeZoneRule3);
        ruleBasedTimeZone.freeze();
        KOREA_ZONE = ruleBasedTimeZone;
    }
}

