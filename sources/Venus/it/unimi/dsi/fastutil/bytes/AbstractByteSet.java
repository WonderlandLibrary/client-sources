/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByteSet
extends AbstractByteCollection
implements Cloneable,
ByteSet {
    protected AbstractByteSet() {
    }

    @Override
    public abstract ByteIterator iterator();

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
        ByteIterator byteIterator = this.iterator();
        while (n2-- != 0) {
            byte by = byteIterator.nextByte();
            n += by;
        }
        return n;
    }

    @Override
    public boolean remove(byte by) {
        return super.rem(by);
    }

    @Override
    @Deprecated
    public boolean rem(byte by) {
        return this.remove(by);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

