/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingSetMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingSortedSetMultimap<K, V>
extends ForwardingSetMultimap<K, V>
implements SortedSetMultimap<K, V> {
    protected ForwardingSortedSetMultimap() {
    }

    @Override
    protected abstract SortedSetMultimap<K, V> delegate();

    @Override
    public SortedSet<V> get(@Nullable K k) {
        return this.delegate().get((Object)k);
    }

    @Override
    public SortedSet<V> removeAll(@Nullable Object object) {
        return this.delegate().removeAll(object);
    }

    @Override
    public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return this.delegate().replaceValues((Object)k, (Iterable)iterable);
    }

    @Override
    public Comparator<? super V> valueComparator() {
        return this.delegate().valueComparator();
    }

    @Override
    public Set replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    public Set removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    public Set get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    protected SetMultimap delegate() {
        return this.delegate();
    }

    @Override
    public Collection get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    public Collection removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    public Collection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    protected Multimap delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

