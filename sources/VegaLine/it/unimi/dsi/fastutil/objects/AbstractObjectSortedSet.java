/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;

public abstract class AbstractObjectSortedSet<K>
extends AbstractObjectSet<K>
implements ObjectSortedSet<K> {
    protected AbstractObjectSortedSet() {
    }

    @Override
    @Deprecated
    public ObjectBidirectionalIterator<K> objectIterator() {
        return this.iterator();
    }

    @Override
    public abstract ObjectBidirectionalIterator<K> iterator();
}

