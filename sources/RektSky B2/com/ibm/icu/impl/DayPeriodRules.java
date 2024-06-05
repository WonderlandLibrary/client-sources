package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.util.*;

public final class DayPeriodRules
{
    private static final DayPeriodRulesData DATA;
    private boolean hasMidnight;
    private boolean hasNoon;
    private DayPeriod[] dayPeriodForHour;
    
    private DayPeriodRules() {
        this.hasMidnight = false;
        this.hasNoon = false;
        this.dayPeriodForHour = new DayPeriod[24];
    }
    
    public static DayPeriodRules getInstance(final ULocale locale) {
        String localeCode = locale.getBaseName();
        if (localeCode.isEmpty()) {
            localeCode = "root";
        }
        Integer ruleSetNum = null;
        while (ruleSetNum == null) {
            ruleSetNum = DayPeriodRules.DATA.localesToRuleSetNumMap.get(localeCode);
            if (ruleSetNum != null) {
                break;
            }
            localeCode = ULocale.getFallback(localeCode);
            if (localeCode.isEmpty()) {
                break;
            }
        }
        if (ruleSetNum == null || DayPeriodRules.DATA.rules[ruleSetNum] == null) {
            return null;
        }
        return DayPeriodRules.DATA.rules[ruleSetNum];
    }
    
    public double getMidPointForDayPeriod(final DayPeriod dayPeriod) {
        final int startHour = this.getStartHourForDayPeriod(dayPeriod);
        final int endHour = this.getEndHourForDayPeriod(dayPeriod);
        double midPoint = (startHour + endHour) / 2.0;
        if (startHour > endHour) {
            midPoint += 12.0;
            if (midPoint >= 24.0) {
                midPoint -= 24.0;
            }
        }
        return midPoint;
    }
    
    private static DayPeriodRulesData loadData() {
        final DayPeriodRulesData data = new DayPeriodRulesData();
        final ICUResourceBundle rb = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "dayPeriods", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
        final DayPeriodRulesCountSink countSink = new DayPeriodRulesCountSink(data);
        rb.getAllItemsWithFallback("rules", countSink);
        data.rules = new DayPeriodRules[data.maxRuleSetNum + 1];
        final DayPeriodRulesDataSink sink = new DayPeriodRulesDataSink(data);
        rb.getAllItemsWithFallback("", sink);
        return data;
    }
    
    private int getStartHourForDayPeriod(final DayPeriod dayPeriod) throws IllegalArgumentException {
        if (dayPeriod == DayPeriod.MIDNIGHT) {
            return 0;
        }
        if (dayPeriod == DayPeriod.NOON) {
            return 12;
        }
        if (this.dayPeriodForHour[0] == dayPeriod && this.dayPeriodForHour[23] == dayPeriod) {
            for (int i = 22; i >= 1; --i) {
                if (this.dayPeriodForHour[i] != dayPeriod) {
                    return i + 1;
                }
            }
        }
        else {
            for (int i = 0; i <= 23; ++i) {
                if (this.dayPeriodForHour[i] == dayPeriod) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException();
    }
    
    private int getEndHourForDayPeriod(final DayPeriod dayPeriod) {
        if (dayPeriod == DayPeriod.MIDNIGHT) {
            return 0;
        }
        if (dayPeriod == DayPeriod.NOON) {
            return 12;
        }
        if (this.dayPeriodForHour[0] == dayPeriod && this.dayPeriodForHour[23] == dayPeriod) {
            for (int i = 1; i <= 22; ++i) {
                if (this.dayPeriodForHour[i] != dayPeriod) {
                    return i;
                }
            }
        }
        else {
            for (int i = 23; i >= 0; --i) {
                if (this.dayPeriodForHour[i] == dayPeriod) {
                    return i + 1;
                }
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
    
    public DayPeriod getDayPeriodForHour(final int hour) {
        return this.dayPeriodForHour[hour];
    }
    
    private void add(final int startHour, final int limitHour, final DayPeriod period) {
        for (int i = startHour; i != limitHour; ++i) {
            if (i == 24) {
                i = 0;
            }
            this.dayPeriodForHour[i] = period;
        }
    }
    
    private static int parseSetNum(final String setNumStr) {
        if (!setNumStr.startsWith("set")) {
            throw new ICUException("Set number should start with \"set\".");
        }
        final String numStr = setNumStr.substring(3);
        return Integer.parseInt(numStr);
    }
    
    static {
        DATA = loadData();
    }
    
    public enum DayPeriod
    {
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
        
        private static DayPeriod fromStringOrNull(final CharSequence str) {
            if ("midnight".contentEquals(str)) {
                return DayPeriod.MIDNIGHT;
            }
            if ("noon".contentEquals(str)) {
                return DayPeriod.NOON;
            }
            if ("morning1".contentEquals(str)) {
                return DayPeriod.MORNING1;
            }
            if ("afternoon1".contentEquals(str)) {
                return DayPeriod.AFTERNOON1;
            }
            if ("evening1".contentEquals(str)) {
                return DayPeriod.EVENING1;
            }
            if ("night1".contentEquals(str)) {
                return DayPeriod.NIGHT1;
            }
            if ("morning2".contentEquals(str)) {
                return DayPeriod.MORNING2;
            }
            if ("afternoon2".contentEquals(str)) {
                return DayPeriod.AFTERNOON2;
            }
            if ("evening2".contentEquals(str)) {
                return DayPeriod.EVENING2;
            }
            if ("night2".contentEquals(str)) {
                return DayPeriod.NIGHT2;
            }
            if ("am".contentEquals(str)) {
                return DayPeriod.AM;
            }
            if ("pm".contentEquals(str)) {
                return DayPeriod.PM;
            }
            return null;
        }
        
        static {
            DayPeriod.VALUES = values();
        }
    }
    
    private enum CutoffType
    {
        BEFORE, 
        AFTER, 
        FROM, 
        AT;
        
        private static CutoffType fromStringOrNull(final CharSequence str) {
            if ("from".contentEquals(str)) {
                return CutoffType.FROM;
            }
            if ("before".contentEquals(str)) {
                return CutoffType.BEFORE;
            }
            if ("after".contentEquals(str)) {
                return CutoffType.AFTER;
            }
            if ("at".contentEquals(str)) {
                return CutoffType.AT;
            }
            return null;
        }
    }
    
    private static final class DayPeriodRulesData
    {
        Map<String, Integer> localesToRuleSetNumMap;
        DayPeriodRules[] rules;
        int maxRuleSetNum;
        
        private DayPeriodRulesData() {
            this.localesToRuleSetNumMap = new HashMap<String, Integer>();
            this.maxRuleSetNum = -1;
        }
    }
    
    private static final class DayPeriodRulesDataSink extends UResource.Sink
    {
        private DayPeriodRulesData data;
        private int[] cutoffs;
        private int ruleSetNum;
        private DayPeriod period;
        private CutoffType cutoffType;
        
        private DayPeriodRulesDataSink(final DayPeriodRulesData data) {
            this.cutoffs = new int[25];
            this.data = data;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table dayPeriodData = value.getTable();
            for (int i = 0; dayPeriodData.getKeyAndValue(i, key, value); ++i) {
                if (key.contentEquals("locales")) {
                    final UResource.Table locales = value.getTable();
                    for (int j = 0; locales.getKeyAndValue(j, key, value); ++j) {
                        final int setNum = parseSetNum(value.getString());
                        this.data.localesToRuleSetNumMap.put(key.toString(), setNum);
                    }
                }
                else if (key.contentEquals("rules")) {
                    final UResource.Table rules = value.getTable();
                    this.processRules(rules, key, value);
                }
            }
        }
        
        private void processRules(final UResource.Table rules, final UResource.Key key, final UResource.Value value) {
            for (int i = 0; rules.getKeyAndValue(i, key, value); ++i) {
                this.ruleSetNum = parseSetNum(key.toString());
                this.data.rules[this.ruleSetNum] = new DayPeriodRules(null);
                final UResource.Table ruleSet = value.getTable();
                for (int j = 0; ruleSet.getKeyAndValue(j, key, value); ++j) {
                    this.period = fromStringOrNull(key);
                    if (this.period == null) {
                        throw new ICUException("Unknown day period in data.");
                    }
                    final UResource.Table periodDefinition = value.getTable();
                    for (int k = 0; periodDefinition.getKeyAndValue(k, key, value); ++k) {
                        if (value.getType() == 0) {
                            final CutoffType type = fromStringOrNull(key);
                            this.addCutoff(type, value.getString());
                        }
                        else {
                            this.cutoffType = fromStringOrNull(key);
                            final UResource.Array cutoffArray = value.getArray();
                            for (int length = cutoffArray.getSize(), l = 0; l < length; ++l) {
                                cutoffArray.getValue(l, value);
                                this.addCutoff(this.cutoffType, value.getString());
                            }
                        }
                    }
                    this.setDayPeriodForHoursFromCutoffs();
                    for (int k = 0; k < this.cutoffs.length; ++k) {
                        this.cutoffs[k] = 0;
                    }
                }
                for (final DayPeriod period : this.data.rules[this.ruleSetNum].dayPeriodForHour) {
                    if (period == null) {
                        throw new ICUException("Rules in data don't cover all 24 hours (they should).");
                    }
                }
            }
        }
        
        private void addCutoff(final CutoffType type, final String hourStr) {
            if (type == null) {
                throw new ICUException("Cutoff type not recognized.");
            }
            final int hour = parseHour(hourStr);
            final int[] cutoffs = this.cutoffs;
            final int n = hour;
            cutoffs[n] |= 1 << type.ordinal();
        }
        
        private void setDayPeriodForHoursFromCutoffs() {
            final DayPeriodRules rule = this.data.rules[this.ruleSetNum];
        Label_0197:
            for (int startHour = 0; startHour <= 24; ++startHour) {
                if ((this.cutoffs[startHour] & 1 << CutoffType.AT.ordinal()) > 0) {
                    if (startHour == 0 && this.period == DayPeriod.MIDNIGHT) {
                        rule.hasMidnight = true;
                    }
                    else {
                        if (startHour != 12 || this.period != DayPeriod.NOON) {
                            throw new ICUException("AT cutoff must only be set for 0:00 or 12:00.");
                        }
                        rule.hasNoon = true;
                    }
                }
                if ((this.cutoffs[startHour] & 1 << CutoffType.FROM.ordinal()) > 0 || (this.cutoffs[startHour] & 1 << CutoffType.AFTER.ordinal()) > 0) {
                    for (int hour = startHour + 1; hour != startHour; ++hour) {
                        if (hour == 25) {
                            hour = 0;
                        }
                        if ((this.cutoffs[hour] & 1 << CutoffType.BEFORE.ordinal()) > 0) {
                            rule.add(startHour, hour, this.period);
                            continue Label_0197;
                        }
                    }
                    throw new ICUException("FROM/AFTER cutoffs must have a matching BEFORE cutoff.");
                }
            }
        }
        
        private static int parseHour(final String str) {
            final int firstColonPos = str.indexOf(58);
            if (firstColonPos < 0 || !str.substring(firstColonPos).equals(":00")) {
                throw new ICUException("Cutoff time must end in \":00\".");
            }
            final String hourStr = str.substring(0, firstColonPos);
            if (firstColonPos != 1 && firstColonPos != 2) {
                throw new ICUException("Cutoff time must begin with h: or hh:");
            }
            final int hour = Integer.parseInt(hourStr);
            if (hour < 0 || hour > 24) {
                throw new ICUException("Cutoff hour must be between 0 and 24, inclusive.");
            }
            return hour;
        }
    }
    
    private static class DayPeriodRulesCountSink extends UResource.Sink
    {
        private DayPeriodRulesData data;
        
        private DayPeriodRulesCountSink(final DayPeriodRulesData data) {
            this.data = data;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table rules = value.getTable();
            for (int i = 0; rules.getKeyAndValue(i, key, value); ++i) {
                final int setNum = parseSetNum(key.toString());
                if (setNum > this.data.maxRuleSetNum) {
                    this.data.maxRuleSetNum = setNum;
                }
            }
        }
    }
}
