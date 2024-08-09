/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
final class ImmutableMapValues<K, V>
extends ImmutableCollection<V> {
    @Weak
    private final ImmutableMap<K, V> map;

    ImmutableMapValues(ImmutableMap<K, V> immutableMap) {
        this.map = immutableMap;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public UnmodifiableIterator<V> iterator() {
        return new UnmodifiableIterator<V>(this){
            final UnmodifiableIterator<Map.Entry<K, V>> entryItr;
            final ImmutableMapValues this$0;
            {
                this.this$0 = immutableMapValues;
                this.entryItr = ((ImmutableSet)ImmutableMapValues.access$000(this.this$0).entrySet()).iterator();
            }

            @Override
            public boolean hasNext() {
                return this.entryItr.hasNext();
            }

            @Override
            public V next() {
                return ((Map.Entry)this.entryItr.next()).getValue();
            }
        };
    }

    @Override
    public Spliterator<V> spliterator() {
        return CollectSpliterators.map(((ImmutableCollection)((Object)this.map.entrySet())).spliterator(), Map.Entry::getValue);
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return object != null && Iterators.contains(this.iterator(), object);
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public ImmutableList<V> asList() {
        ImmutableList immutableList = ((ImmutableSet)this.map.entrySet()).asList();
        return new ImmutableAsList<V>(this, immutableList){
            final ImmutableList val$entryList;
            final ImmutableMapValues this$0;
            {
                this.this$0 = immutableMapValues;
                this.val$entryList = immutableList;
            }

            @Override
            public V get(int n) {
                return ((Map.Entry)this.val$entryList.get(n)).getValue();
            }

            @Override
            ImmutableCollection<V> delegateCollection() {
                return this.this$0;
            }
        };
    }

    @Override
    @GwtIncompatible
    public void forEach(Consumer<? super V> consumer) {
        Preconditions.checkNotNull(consumer);
        this.map.forEach((arg_0, arg_1) -> ImmutableMapValues.lambda$forEach$0(consumer, arg_0, arg_1));
    }

    @Override
    @GwtIncompatible
    Object writeReplace() {
        return new SerializedForm<V>(this.map);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    private static void lambda$forEach$0(Consumer consumer, Object object, Object object2) {
        consumer.accept(object2);
    }

    static ImmutableMap access$000(ImmutableMapValues immutableMapValues) {
        return immutableMapValues.map;
    }

    @GwtIncompatible
    private static class SerializedForm<V>
    implements Serializable {
        final ImmutableMap<?, V> map;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableMap<?, V> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.values();
        }
    }
}

