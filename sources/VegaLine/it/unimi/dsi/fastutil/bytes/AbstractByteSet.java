/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.util.Set;

public abstract class AbstractByteSet
extends AbstractByteCollection
implements Cloneable,
ByteSet {
    protected AbstractByteSet() {
    }

    @Override
    public abstract ByteIterator iterator();

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
        ByteIterator i = this.iterator();
        while (n-- != 0) {
            byte k = i.nextByte();
            h += k;
        }
        return h;
    }

    @Override
    public boolean remove(byte k) {
        return this.rem(k);
    }
}

