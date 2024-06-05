package com.ibm.icu.util;

import java.util.*;
import com.ibm.icu.impl.*;

public final class EthiopicCalendar extends CECalendar
{
    private static final long serialVersionUID = -2438495771339315608L;
    public static final int MESKEREM = 0;
    public static final int TEKEMT = 1;
    public static final int HEDAR = 2;
    public static final int TAHSAS = 3;
    public static final int TER = 4;
    public static final int YEKATIT = 5;
    public static final int MEGABIT = 6;
    public static final int MIAZIA = 7;
    public static final int GENBOT = 8;
    public static final int SENE = 9;
    public static final int HAMLE = 10;
    public static final int NEHASSE = 11;
    public static final int PAGUMEN = 12;
    private static final int JD_EPOCH_OFFSET_AMETE_MIHRET = 1723856;
    private static final int AMETE_MIHRET_DELTA = 5500;
    private static final int AMETE_ALEM = 0;
    private static final int AMETE_MIHRET = 1;
    private static final int AMETE_MIHRET_ERA = 0;
    private static final int AMETE_ALEM_ERA = 1;
    private int eraType;
    
    public EthiopicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public EthiopicCalendar(final TimeZone zone) {
        this(zone, ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public EthiopicCalendar(final Locale aLocale) {
        this(TimeZone.getDefault(), aLocale);
    }
    
    public EthiopicCalendar(final ULocale locale) {
        this(TimeZone.getDefault(), locale);
    }
    
    public EthiopicCalendar(final TimeZone zone, final Locale aLocale) {
        this(zone, ULocale.forLocale(aLocale));
    }
    
    public EthiopicCalendar(final TimeZone zone, final ULocale locale) {
        super(zone, locale);
        this.eraType = 0;
        this.setCalcTypeForLocale(locale);
    }
    
    public EthiopicCalendar(final int year, final int month, final int date) {
        super(year, month, date);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final Date date) {
        super(date);
        this.eraType = 0;
    }
    
    public EthiopicCalendar(final int year, final int month, final int date, final int hour, final int minute, final int second) {
        super(year, month, date, hour, minute, second);
        this.eraType = 0;
    }
    
    @Override
    public String getType() {
        if (this.isAmeteAlemEra()) {
            return "ethiopic-amete-alem";
        }
        return "ethiopic";
    }
    
    public void setAmeteAlemEra(final boolean onOff) {
        this.eraType = (onOff ? 1 : 0);
    }
    
    public boolean isAmeteAlemEra() {
        return this.eraType == 1;
    }
    
    @Deprecated
    @Override
    protected int handleGetExtendedYear() {
        int eyear;
        if (this.newerField(19, 1) == 19) {
            eyear = this.internalGet(19, 1);
        }
        else if (this.isAmeteAlemEra()) {
            eyear = this.internalGet(1, 5501) - 5500;
        }
        else {
            final int era = this.internalGet(0, 1);
            if (era == 1) {
                eyear = this.internalGet(1, 1);
            }
            else {
                eyear = this.internalGet(1, 1) - 5500;
            }
        }
        return eyear;
    }
    
    @Deprecated
    @Override
    protected void handleComputeFields(final int julianDay) {
        final int[] fields = new int[3];
        CECalendar.jdToCE(julianDay, this.getJDEpochOffset(), fields);
        int era;
        int year;
        if (this.isAmeteAlemEra()) {
            era = 0;
            year = fields[0] + 5500;
        }
        else if (fields[0] > 0) {
            era = 1;
            year = fields[0];
        }
        else {
            era = 0;
            year = fields[0] + 5500;
        }
        this.internalSet(19, fields[0]);
        this.internalSet(0, era);
        this.internalSet(1, year);
        this.internalSet(2, fields[1]);
        this.internalSet(5, fields[2]);
        this.internalSet(6, 30 * fields[1] + fields[2]);
    }
    
    @Deprecated
    @Override
    protected int handleGetLimit(final int field, final int limitType) {
        if (this.isAmeteAlemEra() && field == 0) {
            return 0;
        }
        return super.handleGetLimit(field, limitType);
    }
    
    @Deprecated
    @Override
    protected int getJDEpochOffset() {
        return 1723856;
    }
    
    public static int EthiopicToJD(final long year, final int month, final int date) {
        return CECalendar.ceToJD(year, month, date, 1723856);
    }
    
    private void setCalcTypeForLocale(final ULocale locale) {
        final String localeCalType = CalendarUtil.getCalendarType(locale);
        if ("ethiopic-amete-alem".equals(localeCalType)) {
            this.setAmeteAlemEra(true);
        }
        else {
            this.setAmeteAlemEra(false);
        }
    }
}
