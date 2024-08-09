/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterators;
import java.util.AbstractCollection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractBooleanCollection
extends AbstractCollection<Boolean>
implements BooleanCollection {
    protected AbstractBooleanCollection() {
    }

    @Override
    public abstract BooleanIterator iterator();

    @Override
    public boolean add(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(boolean bl) {
        BooleanIterator booleanIterator = this.iterator();
        while (booleanIterator.hasNext()) {
            if (bl != booleanIterator.nextBoolean()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean rem(boolean bl) {
        BooleanIterator booleanIterator = this.iterator();
        while (booleanIterator.hasNext()) {
            if (bl != booleanIterator.nextBoolean()) continue;
            booleanIterator.remove();
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Boolean bl) {
        return BooleanCollection.super.add(bl);
    }

    @Override
    @Deprecated
    public boolean contains(Object object) {
        return BooleanCollection.super.contains(object);
    }

    @Override
    @Deprecated
    public boolean remove(Object object) {
        return BooleanCollection.super.remove(object);
    }

    @Override
    public boolean[] toArray(boolean[] blArray) {
        if (blArray == null || blArray.length < this.size()) {
            blArray = new boolean[this.size()];
        }
        BooleanIterators.unwrap(this.iterator(), blArray);
        return blArray;
    }

    @Override
    public boolean[] toBooleanArray() {
        return this.toArray((boolean[])null);
    }

    @Override
    @Deprecated
    public boolean[] toBooleanArray(boolean[] blArray) {
        return this.toArray(blArray);
    }

    @Override
    public boolean addAll(BooleanCollection booleanCollection) {
        boolean bl = false;
        BooleanIterator booleanIterator = booleanCollection.iterator();
        while (booleanIterator.hasNext()) {
            if (!this.add(booleanIterator.nextBoolean())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(BooleanCollection booleanCollection) {
        BooleanIterator booleanIterator = booleanCollection.iterator();
        while (booleanIterator.hasNext()) {
            if (this.contains(booleanIterator.nextBoolean())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(BooleanCollection booleanCollection) {
        boolean bl = false;
        BooleanIterator booleanIterator = booleanCollection.iterator();
        while (booleanIterator.hasNext()) {
            if (!this.rem(booleanIterator.nextBoolean())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(BooleanCollection booleanCollection) {
        boolean bl = false;
        BooleanIterator booleanIterator = this.iterator();
        while (booleanIterator.hasNext()) {
            if (booleanCollection.contains(booleanIterator.nextBoolean())) continue;
            booleanIterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        BooleanIterator booleanIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            boolean bl2 = booleanIterator.nextBoolean();
            stringBuilder.append(String.valueOf(bl2));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean add(Object object) {
        return this.add((Boolean)object);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

