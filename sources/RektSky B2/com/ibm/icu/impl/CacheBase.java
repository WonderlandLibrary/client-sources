package com.ibm.icu.impl;

public abstract class CacheBase<K, V, D>
{
    public abstract V getInstance(final K p0, final D p1);
    
    protected abstract V createInstance(final K p0, final D p1);
}
