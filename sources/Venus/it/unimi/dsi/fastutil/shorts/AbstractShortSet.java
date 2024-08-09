/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShortSet
extends AbstractShortCollection
implements Cloneable,
ShortSet {
    protected AbstractShortSet() {
    }

    @Override
    public abstract ShortIterator iterator();

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
        ShortIterator shortIterator = this.iterator();
        while (n2-- != 0) {
            short s = shortIterator.nextShort();
            n += s;
        }
        return n;
    }

    @Override
    public boolean remove(short s) {
        return super.rem(s);
    }

    @Override
    @Deprecated
    public boolean rem(short s) {
        return this.remove(s);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

