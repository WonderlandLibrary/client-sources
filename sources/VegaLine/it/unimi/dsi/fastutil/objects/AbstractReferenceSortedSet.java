/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;

public abstract class AbstractReferenceSortedSet<K>
extends AbstractReferenceSet<K>
implements ReferenceSortedSet<K> {
    protected AbstractReferenceSortedSet() {
    }

    @Override
    @Deprecated
    public ObjectBidirectionalIterator<K> objectIterator() {
        return this.iterator();
    }

    @Override
    public abstract ObjectBidirectionalIterator<K> iterator();
}

