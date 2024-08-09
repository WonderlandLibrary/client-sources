/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.number.LocalizedNumberRangeFormatter;
import com.ibm.icu.number.UnlocalizedNumberRangeFormatter;
import com.ibm.icu.util.ULocale;
import java.util.Locale;

public abstract class NumberRangeFormatter {
    private static final UnlocalizedNumberRangeFormatter BASE = new UnlocalizedNumberRangeFormatter();

    public static UnlocalizedNumberRangeFormatter with() {
        return BASE;
    }

    public static LocalizedNumberRangeFormatter withLocale(Locale locale) {
        return BASE.locale(locale);
    }

    public static LocalizedNumberRangeFormatter withLocale(ULocale uLocale) {
        return BASE.locale(uLocale);
    }

    private NumberRangeFormatter() {
    }

    public static enum RangeIdentityResult {
        EQUAL_BEFORE_ROUNDING,
        EQUAL_AFTER_ROUNDING,
        NOT_EQUAL;

    }

    public static enum RangeIdentityFallback {
        SINGLE_VALUE,
        APPROXIMATELY_OR_SINGLE_VALUE,
        APPROXIMATELY,
        RANGE;

    }

    public static enum RangeCollapse {
        AUTO,
        NONE,
        UNIT,
        ALL;

    }
}

