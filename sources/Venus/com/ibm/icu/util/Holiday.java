/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.DateRule;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;

public abstract class Holiday
implements DateRule {
    private String name;
    private DateRule rule;
    private static Holiday[] noHolidays = new Holiday[0];

    public static Holiday[] getHolidays() {
        return Holiday.getHolidays(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static Holiday[] getHolidays(Locale locale) {
        return Holiday.getHolidays(ULocale.forLocale(locale));
    }

    public static Holiday[] getHolidays(ULocale uLocale) {
        Holiday[] holidayArray = noHolidays;
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com.ibm.icu.impl.data.HolidayBundle", uLocale);
            holidayArray = (Holiday[])uResourceBundle.getObject("holidays");
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return holidayArray;
    }

    @Override
    public Date firstAfter(Date date) {
        return this.rule.firstAfter(date);
    }

    @Override
    public Date firstBetween(Date date, Date date2) {
        return this.rule.firstBetween(date, date2);
    }

    @Override
    public boolean isOn(Date date) {
        return this.rule.isOn(date);
    }

    @Override
    public boolean isBetween(Date date, Date date2) {
        return this.rule.isBetween(date, date2);
    }

    protected Holiday(String string, DateRule dateRule) {
        this.name = string;
        this.rule = dateRule;
    }

    public String getDisplayName() {
        return this.getDisplayName(ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(Locale locale) {
        return this.getDisplayName(ULocale.forLocale(locale));
    }

    public String getDisplayName(ULocale uLocale) {
        String string = this.name;
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com.ibm.icu.impl.data.HolidayBundle", uLocale);
            string = uResourceBundle.getString(this.name);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return string;
    }

    public DateRule getRule() {
        return this.rule;
    }

    public void setRule(DateRule dateRule) {
        this.rule = dateRule;
    }
}

