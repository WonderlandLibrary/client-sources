/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import java.util.HashMap;
import java.util.Map;

public final class DayPeriodRules {
    private static final DayPeriodRulesData DATA = DayPeriodRules.loadData();
    private boolean hasMidnight = false;
    private boolean hasNoon = false;
    private DayPeriod[] dayPeriodForHour = new DayPeriod[24];

    private DayPeriodRules() {
    }

    public static DayPeriodRules getInstance(ULocale uLocale) {
        String string = uLocale.getBaseName();
        if (string.isEmpty()) {
            string = "root";
        }
        Integer n = null;
        while (n == null && (n = DayPeriodRules.DATA.localesToRuleSetNumMap.get(string)) == null && !(string = ULocale.getFallback(string)).isEmpty()) {
        }
        if (n == null || DayPeriodRules.DATA.rules[n] == null) {
            return null;
        }
        return DayPeriodRules.DATA.rules[n];
    }

    public double getMidPointForDayPeriod(DayPeriod dayPeriod) {
        int n = this.getStartHourForDayPeriod(dayPeriod);
        int n2 = this.getEndHourForDayPeriod(dayPeriod);
        double d = (double)(n + n2) / 2.0;
        if (n > n2 && (d += 12.0) >= 24.0) {
            d -= 24.0;
        }
        return d;
    }

    private static DayPeriodRulesData loadData() {
        DayPeriodRulesData dayPeriodRulesData = new DayPeriodRulesData(null);
        ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "dayPeriods", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
        DayPeriodRulesCountSink dayPeriodRulesCountSink = new DayPeriodRulesCountSink(dayPeriodRulesData, null);
        iCUResourceBundle.getAllItemsWithFallback("rules", dayPeriodRulesCountSink);
        dayPeriodRulesData.rules = new DayPeriodRules[dayPeriodRulesData.maxRuleSetNum + 1];
        DayPeriodRulesDataSink dayPeriodRulesDataSink = new DayPeriodRulesDataSink(dayPeriodRulesData, null);
        iCUResourceBundle.getAllItemsWithFallback("", dayPeriodRulesDataSink);
        return dayPeriodRulesData;
    }

    private int getStartHourForDayPeriod(DayPeriod dayPeriod) throws IllegalArgumentException {
        if (dayPeriod == DayPeriod.MIDNIGHT) {
            return 1;
        }
        if (dayPeriod == DayPeriod.NOON) {
            return 1;
        }
        if (this.dayPeriodForHour[0] == dayPeriod && this.dayPeriodForHour[23] == dayPeriod) {
            for (int i = 22; i >= 1; --i) {
                if (this.dayPeriodForHour[i] == dayPeriod) continue;
                return i + 1;
            }
        } else {
            for (int i = 0; i <= 23; ++i) {
                if (this.dayPeriodForHour[i] != dayPeriod) continue;
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private int getEndHourForDayPeriod(DayPeriod dayPeriod) {
        if (dayPeriod == DayPeriod.MIDNIGHT) {
            return 1;
        }
        if (dayPeriod == DayPeriod.NOON) {
            return 1;
        }
        if (this.dayPeriodForHour[0] == dayPeriod && this.dayPeriodForHour[23] == dayPeriod) {
            for (int i = 1; i <= 22; ++i) {
                if (this.dayPeriodForHour[i] == dayPeriod) continue;
                return i;
            }
        } else {
            for (int i = 23; i >= 0; --i) {
                if (this.dayPeriodForHour[i] != dayPeriod) continue;
                return i + 1;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean hasMidnight() {
        return this.hasMidnight;
    }

    public boolean hasNoon() {
        return this.hasNoon;
    }

    public DayPeriod getDayPeriodForHour(int n) {
        return this.dayPeriodForHour[n];
    }

    private void add(int n, int n2, DayPeriod dayPeriod) {
        for (int i = n; i != n2; ++i) {
            if (i == 24) {
                i = 0;
            }
            this.dayPeriodForHour[i] = dayPeriod;
        }
    }

    private static int parseSetNum(String string) {
        if (!string.startsWith("set")) {
            throw new ICUException("Set number should start with \"set\".");
        }
        String string2 = string.substring(3);
        return Integer.parseInt(string2);
    }

    static int access$000(String string) {
        return DayPeriodRules.parseSetNum(string);
    }

    DayPeriodRules(1 var1_1) {
        this();
    }

    static DayPeriod[] access$400(DayPeriodRules dayPeriodRules) {
        return dayPeriodRules.dayPeriodForHour;
    }

    static boolean access$502(DayPeriodRules dayPeriodRules, boolean bl) {
        dayPeriodRules.hasMidnight = bl;
        return dayPeriodRules.hasMidnight;
    }

    static boolean access$602(DayPeriodRules dayPeriodRules, boolean bl) {
        dayPeriodRules.hasNoon = bl;
        return dayPeriodRules.hasNoon;
    }

    static void access$700(DayPeriodRules dayPeriodRules, int n, int n2, DayPeriod dayPeriod) {
        dayPeriodRules.add(n, n2, dayPeriod);
    }

    private static class DayPeriodRulesCountSink
    extends UResource.Sink {
        private DayPeriodRulesData data;

        private DayPeriodRulesCountSink(DayPeriodRulesData dayPeriodRulesData) {
            this.data = dayPeriodRulesData;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2 = DayPeriodRules.access$000(key.toString());
                if (n2 > this.data.maxRuleSetNum) {
                    this.data.maxRuleSetNum = n2;
                }
                ++n;
            }
        }

        DayPeriodRulesCountSink(DayPeriodRulesData dayPeriodRulesData, 1 var2_2) {
            this(dayPeriodRulesData);
        }
    }

    private static final class DayPeriodRulesDataSink
    extends UResource.Sink {
        private DayPeriodRulesData data;
        private int[] cutoffs = new int[25];
        private int ruleSetNum;
        private DayPeriod period;
        private CutoffType cutoffType;

        private DayPeriodRulesDataSink(DayPeriodRulesData dayPeriodRulesData) {
            this.data = dayPeriodRulesData;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                UResource.Table table2;
                if (key.contentEquals("locales")) {
                    table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        int n3 = DayPeriodRules.access$000(value.getString());
                        this.data.localesToRuleSetNumMap.put(key.toString(), n3);
                        ++n2;
                    }
                } else if (key.contentEquals("rules")) {
                    table2 = value.getTable();
                    this.processRules(table2, key, value);
                }
                ++n;
            }
        }

        private void processRules(UResource.Table table, UResource.Key key, UResource.Value value) {
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                this.ruleSetNum = DayPeriodRules.access$000(key.toString());
                this.data.rules[this.ruleSetNum] = new DayPeriodRules(null);
                UResource.Table table2 = value.getTable();
                int n2 = 0;
                while (table2.getKeyAndValue(n2, key, value)) {
                    this.period = DayPeriod.access$200(key);
                    if (this.period == null) {
                        throw new ICUException("Unknown day period in data.");
                    }
                    UResource.Table table3 = value.getTable();
                    int n3 = 0;
                    while (table3.getKeyAndValue(n3, key, value)) {
                        if (value.getType() == 0) {
                            CutoffType cutoffType = CutoffType.access$300(key);
                            this.addCutoff(cutoffType, value.getString());
                        } else {
                            this.cutoffType = CutoffType.access$300(key);
                            UResource.Array array = value.getArray();
                            int n4 = array.getSize();
                            for (int i = 0; i < n4; ++i) {
                                array.getValue(i, value);
                                this.addCutoff(this.cutoffType, value.getString());
                            }
                        }
                        ++n3;
                    }
                    this.setDayPeriodForHoursFromCutoffs();
                    for (n3 = 0; n3 < this.cutoffs.length; ++n3) {
                        this.cutoffs[n3] = 0;
                    }
                    ++n2;
                }
                for (DayPeriod dayPeriod : DayPeriodRules.access$400(this.data.rules[this.ruleSetNum])) {
                    if (dayPeriod != null) continue;
                    throw new ICUException("Rules in data don't cover all 24 hours (they should).");
                }
                ++n;
            }
        }

        private void addCutoff(CutoffType cutoffType, String string) {
            int n;
            if (cutoffType == null) {
                throw new ICUException("Cutoff type not recognized.");
            }
            int n2 = n = DayPeriodRulesDataSink.parseHour(string);
            this.cutoffs[n2] = this.cutoffs[n2] | 1 << cutoffType.ordinal();
        }

        private void setDayPeriodForHoursFromCutoffs() {
            DayPeriodRules dayPeriodRules = this.data.rules[this.ruleSetNum];
            block0: for (int i = 0; i <= 24; ++i) {
                if ((this.cutoffs[i] & 1 << CutoffType.AT.ordinal()) > 0) {
                    if (i == 0 && this.period == DayPeriod.MIDNIGHT) {
                        DayPeriodRules.access$502(dayPeriodRules, true);
                    } else if (i == 12 && this.period == DayPeriod.NOON) {
                        DayPeriodRules.access$602(dayPeriodRules, true);
                    } else {
                        throw new ICUException("AT cutoff must only be set for 0:00 or 12:00.");
                    }
                }
                if ((this.cutoffs[i] & 1 << CutoffType.FROM.ordinal()) <= 0 && (this.cutoffs[i] & 1 << CutoffType.AFTER.ordinal()) <= 0) continue;
                int n = i + 1;
                while (true) {
                    if (n == i) {
                        throw new ICUException("FROM/AFTER cutoffs must have a matching BEFORE cutoff.");
                    }
                    if (n == 25) {
                        n = 0;
                    }
                    if ((this.cutoffs[n] & 1 << CutoffType.BEFORE.ordinal()) > 0) {
                        DayPeriodRules.access$700(dayPeriodRules, i, n, this.period);
                        continue block0;
                    }
                    ++n;
                }
            }
        }

        private static int parseHour(String string) {
            int n = string.indexOf(58);
            if (n < 0 || !string.substring(n).equals(":00")) {
                throw new ICUException("Cutoff time must end in \":00\".");
            }
            String string2 = string.substring(0, n);
            if (n != 1 && n != 2) {
                throw new ICUException("Cutoff time must begin with h: or hh:");
            }
            int n2 = Integer.parseInt(string2);
            if (n2 < 0 || n2 > 24) {
                throw new ICUException("Cutoff hour must be between 0 and 24, inclusive.");
            }
            return n2;
        }

        DayPeriodRulesDataSink(DayPeriodRulesData dayPeriodRulesData, 1 var2_2) {
            this(dayPeriodRulesData);
        }
    }

    private static final class DayPeriodRulesData {
        Map<String, Integer> localesToRuleSetNumMap = new HashMap<String, Integer>();
        DayPeriodRules[] rules;
        int maxRuleSetNum = -1;

        private DayPeriodRulesData() {
        }

        DayPeriodRulesData(1 var1_1) {
            this();
        }
    }

    private static enum CutoffType {
        BEFORE,
        AFTER,
        FROM,
        AT;


        private static CutoffType fromStringOrNull(CharSequence charSequence) {
            if ("from".contentEquals(charSequence)) {
                return FROM;
            }
            if ("before".contentEquals(charSequence)) {
                return BEFORE;
            }
            if ("after".contentEquals(charSequence)) {
                return AFTER;
            }
            if ("at".contentEquals(charSequence)) {
                return AT;
            }
            return null;
        }

        static CutoffType access$300(CharSequence charSequence) {
            return CutoffType.fromStringOrNull(charSequence);
        }
    }

    public static enum DayPeriod {
        MIDNIGHT,
        NOON,
        MORNING1,
        AFTERNOON1,
        EVENING1,
        NIGHT1,
        MORNING2,
        AFTERNOON2,
        EVENING2,
        NIGHT2,
        AM,
        PM;

        public static DayPeriod[] VALUES;

        private static DayPeriod fromStringOrNull(CharSequence charSequence) {
            if ("midnight".contentEquals(charSequence)) {
                return MIDNIGHT;
            }
            if ("noon".contentEquals(charSequence)) {
                return NOON;
            }
            if ("morning1".contentEquals(charSequence)) {
                return MORNING1;
            }
            if ("afternoon1".contentEquals(charSequence)) {
                return AFTERNOON1;
            }
            if ("evening1".contentEquals(charSequence)) {
                return EVENING1;
            }
            if ("night1".contentEquals(charSequence)) {
                return NIGHT1;
            }
            if ("morning2".contentEquals(charSequence)) {
                return MORNING2;
            }
            if ("afternoon2".contentEquals(charSequence)) {
                return AFTERNOON2;
            }
            if ("evening2".contentEquals(charSequence)) {
                return EVENING2;
            }
            if ("night2".contentEquals(charSequence)) {
                return NIGHT2;
            }
            if ("am".contentEquals(charSequence)) {
                return AM;
            }
            if ("pm".contentEquals(charSequence)) {
                return PM;
            }
            return null;
        }

        static DayPeriod access$200(CharSequence charSequence) {
            return DayPeriod.fromStringOrNull(charSequence);
        }

        static {
            VALUES = DayPeriod.values();
        }
    }
}

