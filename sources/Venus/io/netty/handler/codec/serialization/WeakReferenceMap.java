/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.serialization;

import io.netty.handler.codec.serialization.ReferenceMap;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

final class WeakReferenceMap<K, V>
extends ReferenceMap<K, V> {
    WeakReferenceMap(Map<K, Reference<V>> map) {
        super(map);
    }

    @Override
    Reference<V> fold(V v) {
        return new WeakReference<V>(v);
    }
}

