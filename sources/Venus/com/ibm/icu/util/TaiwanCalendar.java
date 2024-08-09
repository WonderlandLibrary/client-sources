/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class TaiwanCalendar
extends GregorianCalendar {
    private static final long serialVersionUID = 2583005278132380631L;
    public static final int BEFORE_MINGUO = 0;
    public static final int MINGUO = 1;
    private static final int Taiwan_ERA_START = 1911;
    private static final int GREGORIAN_EPOCH = 1970;

    public TaiwanCalendar() {
    }

    public TaiwanCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public TaiwanCalendar(Locale locale) {
        super(locale);
    }

    public TaiwanCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public TaiwanCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public TaiwanCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public TaiwanCalendar(Date date) {
        this();
        this.setTime(date);
    }

    public TaiwanCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public TaiwanCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n;
        int n2 = 1970;
        n2 = this.newerField(19, 1) == 19 && this.newerField(19, 0) == 19 ? this.internalGet(19, 1970) : ((n = this.internalGet(0, 1)) == 1 ? this.internalGet(1, 1) + 1911 : 1 - this.internalGet(1, 1) + 1911);
        return n2;
    }

    @Override
    protected void handleComputeFields(int n) {
        super.handleComputeFields(n);
        int n2 = this.internalGet(19) - 1911;
        if (n2 > 0) {
            this.internalSet(0, 1);
            this.internalSet(1, n2);
        } else {
            this.internalSet(0, 0);
            this.internalSet(1, 1 - n2);
        }
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        if (n == 0) {
            if (n2 == 0 || n2 == 1) {
                return 1;
            }
            return 0;
        }
        return super.handleGetLimit(n, n2);
    }

    @Override
    public String getType() {
        return "roc";
    }
}

