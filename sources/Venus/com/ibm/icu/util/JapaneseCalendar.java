/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CalType;
import com.ibm.icu.impl.EraRules;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class JapaneseCalendar
extends GregorianCalendar {
    private static final long serialVersionUID = -2977189902603704691L;
    private static final int GREGORIAN_EPOCH = 1970;
    private static final EraRules ERA_RULES = EraRules.getInstance(CalType.JAPANESE, JapaneseCalendar.enableTentativeEra());
    public static final int CURRENT_ERA;
    public static final int MEIJI;
    public static final int TAISHO;
    public static final int SHOWA;
    public static final int HEISEI;
    public static final int REIWA;

    public JapaneseCalendar() {
    }

    public JapaneseCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public JapaneseCalendar(Locale locale) {
        super(locale);
    }

    public JapaneseCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public JapaneseCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public JapaneseCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public JapaneseCalendar(Date date) {
        this();
        this.setTime(date);
    }

    public JapaneseCalendar(int n, int n2, int n3, int n4) {
        super(n2, n3, n4);
        this.set(0, n);
    }

    public JapaneseCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
        this.set(0, CURRENT_ERA);
    }

    public JapaneseCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
        this.set(0, CURRENT_ERA);
    }

    @Deprecated
    public static boolean enableTentativeEra() {
        String string;
        boolean bl = false;
        String string2 = "ICU_ENABLE_TENTATIVE_ERA";
        String string3 = System.getProperty("ICU_ENABLE_TENTATIVE_ERA");
        if (string3 == null) {
            string3 = System.getenv("ICU_ENABLE_TENTATIVE_ERA");
        }
        bl = string3 != null ? string3.equalsIgnoreCase("true") : (string = System.getProperty("jdk.calendar.japanese.supplemental.era")) != null;
        return bl;
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 && this.newerField(19, 0) == 19 ? this.internalGet(19, 1970) : this.internalGet(1, 1) + ERA_RULES.getStartYear(this.internalGet(0, CURRENT_ERA)) - 1;
        return n;
    }

    @Override
    protected int getDefaultMonthInYear(int n) {
        int n2 = this.internalGet(0, CURRENT_ERA);
        int[] nArray = ERA_RULES.getStartDate(n2, null);
        if (n == nArray[0]) {
            return nArray[1] - 1;
        }
        return super.getDefaultMonthInYear(n);
    }

    @Override
    protected int getDefaultDayInMonth(int n, int n2) {
        int n3 = this.internalGet(0, CURRENT_ERA);
        int[] nArray = ERA_RULES.getStartDate(n3, null);
        if (n == nArray[0] && n2 == nArray[1] - 1) {
            return nArray[2];
        }
        return super.getDefaultDayInMonth(n, n2);
    }

    @Override
    protected void handleComputeFields(int n) {
        super.handleComputeFields(n);
        int n2 = this.internalGet(19);
        int n3 = ERA_RULES.getEraIndex(n2, this.internalGet(2) + 1, this.internalGet(5));
        this.internalSet(0, n3);
        this.internalSet(1, n2 - ERA_RULES.getStartYear(n3) + 1);
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        switch (n) {
            case 0: {
                if (n2 == 0 || n2 == 1) {
                    return 1;
                }
                return ERA_RULES.getNumberOfEras() - 1;
            }
            case 1: {
                switch (n2) {
                    case 0: 
                    case 1: {
                        return 0;
                    }
                    case 2: {
                        return 0;
                    }
                    case 3: {
                        return super.handleGetLimit(n, 3) - ERA_RULES.getStartYear(CURRENT_ERA);
                    }
                }
            }
        }
        return super.handleGetLimit(n, n2);
    }

    @Override
    public String getType() {
        return "japanese";
    }

    @Override
    @Deprecated
    public boolean haveDefaultCentury() {
        return true;
    }

    @Override
    public int getActualMaximum(int n) {
        if (n == 1) {
            int n2 = this.get(0);
            if (n2 == ERA_RULES.getNumberOfEras() - 1) {
                return this.handleGetLimit(1, 3);
            }
            int[] nArray = ERA_RULES.getStartDate(n2 + 1, null);
            int n3 = nArray[0];
            int n4 = nArray[1];
            int n5 = nArray[2];
            int n6 = n3 - ERA_RULES.getStartYear(n2) + 1;
            if (n4 == 1 && n5 == 1) {
                --n6;
            }
            return n6;
        }
        return super.getActualMaximum(n);
    }

    static {
        MEIJI = 232;
        TAISHO = 233;
        SHOWA = 234;
        HEISEI = 235;
        REIWA = 236;
        CURRENT_ERA = ERA_RULES.getCurrentEraIndex();
    }
}

