package com.ibm.icu.impl.number;

public interface Modifier
{
    int apply(final NumberStringBuilder p0, final int p1, final int p2);
    
    int getPrefixLength();
    
    int getCodePointCount();
    
    boolean isStrong();
}
