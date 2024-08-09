/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.util.Date;
import java.util.Locale;

public class BuddhistCalendar
extends GregorianCalendar {
    private static final long serialVersionUID = 2583005278132380631L;
    public static final int BE = 0;
    private static final int BUDDHIST_ERA_START = -543;
    private static final int GREGORIAN_EPOCH = 1970;

    public BuddhistCalendar() {
    }

    public BuddhistCalendar(TimeZone timeZone) {
        super(timeZone);
    }

    public BuddhistCalendar(Locale locale) {
        super(locale);
    }

    public BuddhistCalendar(ULocale uLocale) {
        super(uLocale);
    }

    public BuddhistCalendar(TimeZone timeZone, Locale locale) {
        super(timeZone, locale);
    }

    public BuddhistCalendar(TimeZone timeZone, ULocale uLocale) {
        super(timeZone, uLocale);
    }

    public BuddhistCalendar(Date date) {
        this();
        this.setTime(date);
    }

    public BuddhistCalendar(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public BuddhistCalendar(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n4, n5, n6);
    }

    @Override
    protected int handleGetExtendedYear() {
        int n = this.newerField(19, 1) == 19 ? this.internalGet(19, 1970) : this.internalGet(1, 2513) + -543;
        return n;
    }

    @Override
    protected int handleComputeMonthStart(int n, int n2, boolean bl) {
        return super.handleComputeMonthStart(n, n2, bl);
    }

    @Override
    protected void handleComputeFields(int n) {
        super.handleComputeFields(n);
        int n2 = this.internalGet(19) - -543;
        this.internalSet(0, 0);
        this.internalSet(1, n2);
    }

    @Override
    protected int handleGetLimit(int n, int n2) {
        if (n == 0) {
            return 1;
        }
        return super.handleGetLimit(n, n2);
    }

    @Override
    public String getType() {
        return "buddhist";
    }
}

