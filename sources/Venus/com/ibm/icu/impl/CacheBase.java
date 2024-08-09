/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public abstract class CacheBase<K, V, D> {
    public abstract V getInstance(K var1, D var2);

    protected abstract V createInstance(K var1, D var2);
}

