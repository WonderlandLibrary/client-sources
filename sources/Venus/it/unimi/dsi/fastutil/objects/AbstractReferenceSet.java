/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Iterator;
import java.util.Set;

public abstract class AbstractReferenceSet<K>
extends AbstractReferenceCollection<K>
implements Cloneable,
ReferenceSet<K> {
    protected AbstractReferenceSet() {
    }

    @Override
    public abstract ObjectIterator<K> iterator();

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Set)) {
            return true;
        }
        Set set = (Set)object;
        if (set.size() != this.size()) {
            return true;
        }
        return this.containsAll(set);
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        Iterator iterator2 = this.iterator();
        while (n2-- != 0) {
            Object e = iterator2.next();
            n += System.identityHashCode(e);
        }
        return n;
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

