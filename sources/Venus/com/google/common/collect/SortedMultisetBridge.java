/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import java.util.Set;
import java.util.SortedSet;

@GwtIncompatible
interface SortedMultisetBridge<E>
extends Multiset<E> {
    @Override
    public SortedSet<E> elementSet();

    @Override
    default public Set elementSet() {
        return this.elementSet();
    }
}

