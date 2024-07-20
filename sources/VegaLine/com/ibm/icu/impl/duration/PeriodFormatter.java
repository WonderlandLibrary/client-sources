/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import com.ibm.icu.impl.duration.Period;

public interface PeriodFormatter {
    public String format(Period var1);

    public PeriodFormatter withLocale(String var1);
}

