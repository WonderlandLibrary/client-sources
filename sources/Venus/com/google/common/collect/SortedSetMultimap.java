/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.SetMultimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public interface SortedSetMultimap<K, V>
extends SetMultimap<K, V> {
    @Override
    public SortedSet<V> get(@Nullable K var1);

    @Override
    @CanIgnoreReturnValue
    public SortedSet<V> removeAll(@Nullable Object var1);

    @Override
    @CanIgnoreReturnValue
    public SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2);

    @Override
    public Map<K, Collection<V>> asMap();

    public Comparator<? super V> valueComparator();

    @Override
    @CanIgnoreReturnValue
    default public Set replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    default public Set removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    default public Set get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    default public Collection get(@Nullable Object object) {
        return this.get(object);
    }

    @Override
    @CanIgnoreReturnValue
    default public Collection removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    @CanIgnoreReturnValue
    default public Collection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }
}

