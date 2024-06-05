package com.ibm.icu.number;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.number.*;
import com.ibm.icu.text.*;

public final class NumberFormatter
{
    private static final UnlocalizedNumberFormatter BASE;
    static final long DEFAULT_THRESHOLD = 3L;
    
    private NumberFormatter() {
    }
    
    public static UnlocalizedNumberFormatter with() {
        return NumberFormatter.BASE;
    }
    
    public static LocalizedNumberFormatter withLocale(final Locale locale) {
        return NumberFormatter.BASE.locale(locale);
    }
    
    public static LocalizedNumberFormatter withLocale(final ULocale locale) {
        return NumberFormatter.BASE.locale(locale);
    }
    
    public static UnlocalizedNumberFormatter forSkeleton(final String skeleton) {
        return NumberSkeletonImpl.getOrCreate(skeleton);
    }
    
    @Deprecated
    public static UnlocalizedNumberFormatter fromDecimalFormat(final DecimalFormatProperties properties, final DecimalFormatSymbols symbols, final DecimalFormatProperties exportedProperties) {
        return NumberPropertyMapper.create(properties, symbols, exportedProperties);
    }
    
    static {
        BASE = new UnlocalizedNumberFormatter();
    }
    
    public enum UnitWidth
    {
        NARROW, 
        SHORT, 
        FULL_NAME, 
        ISO_CODE, 
        HIDDEN;
    }
    
    public enum GroupingStrategy
    {
        OFF, 
        MIN2, 
        AUTO, 
        ON_ALIGNED, 
        THOUSANDS;
    }
    
    public enum SignDisplay
    {
        AUTO, 
        ALWAYS, 
        NEVER, 
        ACCOUNTING, 
        ACCOUNTING_ALWAYS, 
        EXCEPT_ZERO, 
        ACCOUNTING_EXCEPT_ZERO;
    }
    
    public enum DecimalSeparatorDisplay
    {
        AUTO, 
        ALWAYS;
    }
}
