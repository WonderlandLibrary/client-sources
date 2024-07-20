/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration;

import java.util.Date;
import java.util.TimeZone;

public interface DateFormatter {
    public String format(Date var1);

    public String format(long var1);

    public DateFormatter withLocale(String var1);

    public DateFormatter withTimeZone(TimeZone var1);
}

