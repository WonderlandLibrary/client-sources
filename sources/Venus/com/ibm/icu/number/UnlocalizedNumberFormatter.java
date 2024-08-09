/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatterSettings;
import com.ibm.icu.util.ULocale;
import java.util.Locale;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UnlocalizedNumberFormatter
extends NumberFormatterSettings<UnlocalizedNumberFormatter> {
    UnlocalizedNumberFormatter() {
        super(null, 14, new Long(3L));
    }

    UnlocalizedNumberFormatter(NumberFormatterSettings<?> numberFormatterSettings, int n, Object object) {
        super(numberFormatterSettings, n, object);
    }

    public LocalizedNumberFormatter locale(Locale locale) {
        return new LocalizedNumberFormatter(this, 1, ULocale.forLocale(locale));
    }

    public LocalizedNumberFormatter locale(ULocale uLocale) {
        return new LocalizedNumberFormatter(this, 1, uLocale);
    }

    @Override
    UnlocalizedNumberFormatter create(int n, Object object) {
        return new UnlocalizedNumberFormatter(this, n, object);
    }

    @Override
    NumberFormatterSettings create(int n, Object object) {
        return this.create(n, object);
    }
}

