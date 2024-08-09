/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.number;

import com.ibm.icu.number.LocalizedNumberRangeFormatter;
import com.ibm.icu.number.NumberRangeFormatterSettings;
import com.ibm.icu.util.ULocale;
import java.util.Locale;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UnlocalizedNumberRangeFormatter
extends NumberRangeFormatterSettings<UnlocalizedNumberRangeFormatter> {
    UnlocalizedNumberRangeFormatter() {
        super(null, 0, null);
    }

    UnlocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int n, Object object) {
        super(numberRangeFormatterSettings, n, object);
    }

    public LocalizedNumberRangeFormatter locale(Locale locale) {
        return new LocalizedNumberRangeFormatter(this, 1, ULocale.forLocale(locale));
    }

    public LocalizedNumberRangeFormatter locale(ULocale uLocale) {
        return new LocalizedNumberRangeFormatter(this, 1, uLocale);
    }

    @Override
    UnlocalizedNumberRangeFormatter create(int n, Object object) {
        return new UnlocalizedNumberRangeFormatter(this, n, object);
    }

    @Override
    NumberRangeFormatterSettings create(int n, Object object) {
        return this.create(n, object);
    }
}

