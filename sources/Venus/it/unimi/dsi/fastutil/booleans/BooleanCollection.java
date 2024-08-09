/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanIterable;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import java.util.Collection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanCollection
extends Collection<Boolean>,
BooleanIterable {
    @Override
    public BooleanIterator iterator();

    @Override
    public boolean add(boolean var1);

    public boolean contains(boolean var1);

    public boolean rem(boolean var1);

    @Override
    @Deprecated
    default public boolean add(Boolean bl) {
        return this.add((boolean)bl);
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains((Boolean)object);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem((Boolean)object);
    }

    public boolean[] toBooleanArray();

    @Deprecated
    public boolean[] toBooleanArray(boolean[] var1);

    public boolean[] toArray(boolean[] var1);

    public boolean addAll(BooleanCollection var1);

    public boolean containsAll(BooleanCollection var1);

    public boolean removeAll(BooleanCollection var1);

    public boolean retainAll(BooleanCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Boolean)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

