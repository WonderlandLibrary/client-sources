package com.ibm.icu.impl.number;

public interface AffixPatternProvider
{
    public static final int FLAG_POS_PREFIX = 256;
    public static final int FLAG_POS_SUFFIX = 0;
    public static final int FLAG_NEG_PREFIX = 768;
    public static final int FLAG_NEG_SUFFIX = 512;
    
    char charAt(final int p0, final int p1);
    
    int length(final int p0);
    
    String getString(final int p0);
    
    boolean hasCurrencySign();
    
    boolean positiveHasPlusSign();
    
    boolean hasNegativeSubpattern();
    
    boolean negativeHasMinusSign();
    
    boolean containsSymbolType(final int p0);
    
    boolean hasBody();
    
    public static final class Flags
    {
        public static final int PLURAL_MASK = 255;
        public static final int PREFIX = 256;
        public static final int NEGATIVE_SUBPATTERN = 512;
        public static final int PADDING = 1024;
    }
}
