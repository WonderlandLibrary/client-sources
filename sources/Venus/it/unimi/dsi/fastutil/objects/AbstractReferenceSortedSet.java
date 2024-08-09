/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractReferenceSortedSet<K>
extends AbstractReferenceSet<K>
implements ReferenceSortedSet<K> {
    protected AbstractReferenceSortedSet() {
    }

    @Override
    public abstract ObjectBidirectionalIterator<K> iterator();

    @Override
    public ObjectIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

