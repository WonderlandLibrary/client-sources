/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

public interface Freezable<T>
extends Cloneable {
    public boolean isFrozen();

    public T freeze();

    public T cloneAsThawed();
}

