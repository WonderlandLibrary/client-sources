/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.CECalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public final class CopticCalendar
extends CECalendar {
    private static final long serialVersionUID = 5903818751846742911L;
    public static final int TOUT = 0;
    public static final int BABA = 1;
    public static final int HATOR = 2;
    public static final int KIAHK = 3;
    public static final int TOBA = 4;
    public static final int AMSHIR = 5;
    public static final int BARAMHAT = 6;
    public static final int BARAMOUDA = 7;
    public static final int BASHANS = 8;
    public static final int PAONA = 9;
    public static final int EPEP = 10;
    public static final int MESRA = 11;
    public static final int NASIE = 12;
    private static final int JD_EPOCH_OFFSET = 1824665;
    private static final int BCE = 0;
    private static final int CE = 1;

    public CopticCalendar() {
    }

    public CopticCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public CopticCalendar(Locale locale) {
        super(locale);
    }

    public CopticCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public CopticCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public CopticCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public CopticCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public CopticCalendar(Date date) {
        super(date);
    }

    public CopticCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
    }

    @Override
    public String getType() {
        return "coptic";
    }

    @Override
    @Deprecated
    protected int handleGetExtendedYear() {
        int n;
        int n2 = this.newerField(19, 1) == 19 ? this.internalGet(19, 1) : ((n = this.internalGet(0, 1)) == 0 ? 1 - this.internalGet(1, 1) : this.internalGet(1, 1));
        return n2;
    }

    @Override
    @Deprecated
    protected void handleComputeFields(int n) {
        int n2;
        int n3;
        int[] nArray = new int[3];
        CopticCalendar.jdToCE(n, this.getJDEpochOffset(), nArray);
        if (nArray[0] <= 0) {
            n3 = 0;
            n2 = 1 - nArray[0];
        } else {
            n3 = 1;
            n2 = nArray[0];
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
    protected int getJDEpochOffset() {
        return 0;
    }

    public static int copticToJD(long l, int n, int n2) {
        return CopticCalendar.ceToJD(l, n, n2, 1824665);
    }
}

