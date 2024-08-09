/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.CalendarUtil;
import com.ibm.icu.util.CECalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public final class EthiopicCalendar
extends CECalendar {
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
    private int eraType = 0;

    public EthiopicCalendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public EthiopicCalendar(TimeZone timeZone) {
        this(timeZone, ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public EthiopicCalendar(Locale locale) {
        this(TimeZone.getDefault(), locale);
    }

    public EthiopicCalendar(ULocale uLocale) {
        this(TimeZone.getDefault(), uLocale);
    }

    public EthiopicCalendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    public EthiopicCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
        this.setCalcTypeForLocale(uLocale);
    }

    public EthiopicCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public EthiopicCalendar(Date date) {
        super(date);
    }

    public EthiopicCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
    }

    @Override
    public String getType() {
        if (this.isAmeteAlemEra()) {
            return "ethiopic-amete-alem";
        }
        return "ethiopic";
    }

    public void setAmeteAlemEra(boolean bl) {
        this.eraType = bl ? 1 : 0;
    }

    public boolean isAmeteAlemEra() {
        return this.eraType == 1;
    }

    @Override
    @Deprecated
    protected int handleGetExtendedYear() {
        int n;
        int n2 = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : (this.isAmeteAlemEra() ? this.internalGet(1, 5501) - 5500 : ((n = this.internalGet(0, 1)) == 1 ? this.internalGet(1, 1) : this.internalGet(1, 1) - 5500));
        return n2;
    }

    @Override
    @Deprecated
    protected void handleComputeFields(int n) {
        int n2;
        int n3;
        int[] nArray = new int[3];
        EthiopicCalendar.jdToCE(n, this.getJDEpochOffset(), nArray);
        if (this.isAmeteAlemEra()) {
            n3 = 0;
            n2 = nArray[0] + 5500;
        } else if (nArray[0] > 0) {
            n3 = 1;
            n2 = nArray[0];
        } else {
            n3 = 0;
            n2 = nArray[0] + 5500;
        }
        this.internalSet(19, nArray[0]);
        this.internalSet(0, n3);
        this.internalSet(1, n2);
        this.internalSet(2, nArray[1]);
        this.internalSet(5, nArray[2]);
        this.internalSet(6, 30 * nArray[1] + nArray[2]);
    }

    @Override
    @Deprecated
    protected int handleGetLimit(int n, int n2) {
        if (this.isAmeteAlemEra() && n == 0) {
            return 1;
        }
        return super.handleGetLimit(n, n2);
    }

    @Override
    @Deprecated
    protected int getJDEpochOffset() {
        return 1;
    }

    public static int EthiopicToJD(long l, int n, int n2) {
        return EthiopicCalendar.ceToJD(l, n, n2, 1723856);
    }

    private void setCalcTypeForLocale(ULocale uLocale) {
        String string = CalendarUtil.getCalendarType(uLocale);
        if ("ethiopic-amete-alem".equals(string)) {
            this.setAmeteAlemEra(false);
        } else {
            this.setAmeteAlemEra(true);
        }
    }
}

