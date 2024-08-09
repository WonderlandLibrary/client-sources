/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterators;
import java.util.AbstractCollection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShortCollection
extends AbstractCollection<Short>
implements ShortCollection {
    protected AbstractShortCollection() {
    }

    @Override
    public abstract ShortIterator iterator();

    @Override
    public boolean add(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(short s) {
        ShortIterator shortIterator = this.iterator();
        while (shortIterator.hasNext()) {
            if (s != shortIterator.nextShort()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean rem(short s) {
        ShortIterator shortIterator = this.iterator();
        while (shortIterator.hasNext()) {
            if (s != shortIterator.nextShort()) continue;
            shortIterator.remove();
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Short s) {
        return ShortCollection.super.add(s);
    }

    @Override
    @Deprecated
    public boolean contains(Object object) {
        return ShortCollection.super.contains(object);
    }

    @Override
    @Deprecated
    public boolean remove(Object object) {
        return ShortCollection.super.remove(object);
    }

    @Override
    public short[] toArray(short[] sArray) {
        if (sArray == null || sArray.length < this.size()) {
            sArray = new short[this.size()];
        }
        ShortIterators.unwrap(this.iterator(), sArray);
        return sArray;
    }

    @Override
    public short[] toShortArray() {
        return this.toArray((short[])null);
    }

    @Override
    @Deprecated
    public short[] toShortArray(short[] sArray) {
        return this.toArray(sArray);
    }

    @Override
    public boolean addAll(ShortCollection shortCollection) {
        boolean bl = false;
        ShortIterator shortIterator = shortCollection.iterator();
        while (shortIterator.hasNext()) {
            if (!this.add(shortIterator.nextShort())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(ShortCollection shortCollection) {
        ShortIterator shortIterator = shortCollection.iterator();
        while (shortIterator.hasNext()) {
            if (this.contains(shortIterator.nextShort())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(ShortCollection shortCollection) {
        boolean bl = false;
        ShortIterator shortIterator = shortCollection.iterator();
        while (shortIterator.hasNext()) {
            if (!this.rem(shortIterator.nextShort())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(ShortCollection shortCollection) {
        boolean bl = false;
        ShortIterator shortIterator = this.iterator();
        while (shortIterator.hasNext()) {
            if (shortCollection.contains(shortIterator.nextShort())) continue;
            shortIterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ShortIterator shortIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            short s = shortIterator.nextShort();
            stringBuilder.append(String.valueOf(s));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean add(Object object) {
        return this.add((Short)object);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

