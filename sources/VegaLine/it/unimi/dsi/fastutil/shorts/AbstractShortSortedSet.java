/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.AbstractShortSet;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;

public abstract class AbstractShortSortedSet
extends AbstractShortSet
implements ShortSortedSet {
    protected AbstractShortSortedSet() {
    }

    @Override
    @Deprecated
    public ShortSortedSet headSet(Short to) {
        return this.headSet((short)to);
    }

    @Override
    @Deprecated
    public ShortSortedSet tailSet(Short from) {
        return this.tailSet((short)from);
    }

    @Override
    @Deprecated
    public ShortSortedSet subSet(Short from, Short to) {
        return this.subSet((short)from, (short)to);
    }

    @Override
    @Deprecated
    public Short first() {
        return this.firstShort();
    }

    @Override
    @Deprecated
    public Short last() {
        return this.lastShort();
    }

    @Override
    @Deprecated
    public ShortBidirectionalIterator shortIterator() {
        return this.iterator();
    }

    @Override
    public abstract ShortBidirectionalIterator iterator();
}

