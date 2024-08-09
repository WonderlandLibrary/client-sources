/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class ImmutableMapKeySet<K, V>
extends ImmutableSet.Indexed<K> {
    @Weak
    private final ImmutableMap<K, V> map;

    ImmutableMapKeySet(ImmutableMap<K, V> immutableMap) {
        this.map = immutableMap;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public UnmodifiableIterator<K> iterator() {
        return this.map.keyIterator();
    }

    @Override
    public Spliterator<K> spliterator() {
        return this.map.keySpliterator();
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.map.containsKey(object);
    }

    @Override
    K get(int n) {
        return ((Map.Entry)((ImmutableSet)this.map.entrySet()).asList().get(n)).getKey();
    }

    @Override
    public void forEach(Consumer<? super K> consumer) {
        Preconditions.checkNotNull(consumer);
        this.map.forEach((arg_0, arg_1) -> ImmutableMapKeySet.lambda$forEach$0(consumer, arg_0, arg_1));
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    @GwtIncompatible
    Object writeReplace() {
        return new KeySetSerializedForm<K>(this.map);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    private static void lambda$forEach$0(Consumer consumer, Object object, Object object2) {
        consumer.accept(object);
    }

    @GwtIncompatible
    private static class KeySetSerializedForm<K>
    implements Serializable {
        final ImmutableMap<K, ?> map;
        private static final long serialVersionUID = 0L;

        KeySetSerializedForm(ImmutableMap<K, ?> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.keySet();
        }
    }
}

