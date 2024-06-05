package com.ibm.icu.text;

@Deprecated
public interface RbnfLenientScanner
{
    @Deprecated
    boolean allIgnorable(final String p0);
    
    @Deprecated
    int prefixLength(final String p0, final String p1);
    
    @Deprecated
    int[] findText(final String p0, final String p1, final int p2);
}
