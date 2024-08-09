/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractLongSet
extends AbstractLongCollection
implements Cloneable,
LongSet {
    protected AbstractLongSet() {
    }

    @Override
    public abstract LongIterator iterator();

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
        LongIterator longIterator = this.iterator();
        while (n2-- != 0) {
            long l = longIterator.nextLong();
            n += HashCommon.long2int(l);
        }
        return n;
    }

    @Override
    public boolean remove(long l) {
        return super.rem(l);
    }

    @Override
    @Deprecated
    public boolean rem(long l) {
        return this.remove(l);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

