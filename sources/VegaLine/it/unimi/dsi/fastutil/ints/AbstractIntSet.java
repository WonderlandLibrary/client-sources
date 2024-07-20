/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Set;

public abstract class AbstractIntSet
extends AbstractIntCollection
implements Cloneable,
IntSet {
    protected AbstractIntSet() {
    }

    @Override
    public abstract IntIterator iterator();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        Set s = (Set)o;
        if (s.size() != this.size()) {
            return false;
        }
        return this.containsAll(s);
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        IntIterator i = this.iterator();
        while (n-- != 0) {
            int k = i.nextInt();
            h += k;
        }
        return h;
    }

    @Override
    public boolean remove(int k) {
        return this.rem(k);
    }
}

