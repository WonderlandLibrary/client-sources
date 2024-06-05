package com.ibm.icu.text;

import java.text.*;

public interface SymbolTable
{
    public static final char SYMBOL_REF = '$';
    
    char[] lookup(final String p0);
    
    UnicodeMatcher lookupMatcher(final int p0);
    
    String parseReference(final String p0, final ParsePosition p1, final int p2);
}
