/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

public interface ObjectBigListIterator<K>
extends ObjectBidirectionalIterator<K>,
BigListIterator<K> {
    public void set(K var1);

    public void add(K var1);
}

