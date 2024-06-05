package com.ibm.icu.number;

import java.util.*;
import com.ibm.icu.util.*;

public class UnlocalizedNumberFormatter extends NumberFormatterSettings<UnlocalizedNumberFormatter>
{
    UnlocalizedNumberFormatter() {
        super(null, 14, new Long(3L));
    }
    
    UnlocalizedNumberFormatter(final NumberFormatterSettings<?> parent, final int key, final Object value) {
        super(parent, key, value);
    }
    
    public LocalizedNumberFormatter locale(final Locale locale) {
        return new LocalizedNumberFormatter(this, 1, ULocale.forLocale(locale));
    }
    
    public LocalizedNumberFormatter locale(final ULocale locale) {
        return new LocalizedNumberFormatter(this, 1, locale);
    }
    
    @Override
    UnlocalizedNumberFormatter create(final int key, final Object value) {
        return new UnlocalizedNumberFormatter(this, key, value);
    }
}
