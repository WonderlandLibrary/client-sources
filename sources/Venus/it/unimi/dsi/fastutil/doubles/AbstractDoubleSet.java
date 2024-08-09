/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDoubleSet
extends AbstractDoubleCollection
implements Cloneable,
DoubleSet {
    protected AbstractDoubleSet() {
    }

    @Override
    public abstract DoubleIterator iterator();

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
        DoubleIterator doubleIterator = this.iterator();
        while (n2-- != 0) {
            double d = doubleIterator.nextDouble();
            n += HashCommon.double2int(d);
        }
        return n;
    }

    @Override
    public boolean remove(double d) {
        return super.rem(d);
    }

    @Override
    @Deprecated
    public boolean rem(double d) {
        return this.remove(d);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

