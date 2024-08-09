/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ByteBidirectionalIterable
extends ByteIterable {
    @Override
    public ByteBidirectionalIterator iterator();

    @Override
    default public ByteIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }
}

