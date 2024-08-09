/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractCharSet
extends AbstractCharCollection
implements Cloneable,
CharSet {
    protected AbstractCharSet() {
    }

    @Override
    public abstract CharIterator iterator();

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
        CharIterator charIterator = this.iterator();
        while (n2-- != 0) {
            char c = charIterator.nextChar();
            n += c;
        }
        return n;
    }

    @Override
    public boolean remove(char c) {
        return super.rem(c);
    }

    @Override
    @Deprecated
    public boolean rem(char c) {
        return this.remove(c);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

