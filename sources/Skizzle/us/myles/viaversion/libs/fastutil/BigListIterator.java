/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil;

import us.myles.viaversion.libs.fastutil.BidirectionalIterator;

public interface BigListIterator<K>
extends BidirectionalIterator<K> {
    public long nextIndex();

    public long previousIndex();

    default public void set(K e) {
        throw new UnsupportedOperationException();
    }

    default public void add(K e) {
        throw new UnsupportedOperationException();
    }
}

