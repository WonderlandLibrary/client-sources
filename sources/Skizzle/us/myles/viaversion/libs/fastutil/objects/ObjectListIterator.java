/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import java.util.ListIterator;
import us.myles.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;

public interface ObjectListIterator<K>
extends ObjectBidirectionalIterator<K>,
ListIterator<K> {
    @Override
    default public void set(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(K k) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }
}

