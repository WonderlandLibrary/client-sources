/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil;

import java.util.Iterator;

public interface BidirectionalIterator<K>
extends Iterator<K> {
    public K previous();

    public boolean hasPrevious();
}

