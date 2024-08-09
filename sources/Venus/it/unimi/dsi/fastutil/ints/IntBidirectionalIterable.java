/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntBidirectionalIterable
extends IntIterable {
    @Override
    public IntBidirectionalIterator iterator();

    @Override
    default public IntIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

