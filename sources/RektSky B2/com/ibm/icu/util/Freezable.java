package com.ibm.icu.util;

public interface Freezable<T> extends Cloneable
{
    boolean isFrozen();
    
    T freeze();
    
    T cloneAsThawed();
}
