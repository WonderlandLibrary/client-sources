/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingListMultimap<K, V>
extends ForwardingMultimap<K, V>
implements ListMultimap<K, V> {
    protected ForwardingListMultimap() {
    }

    @Override
    protected abstract ListMultimap<K, V> delegate();

    @Override
    public List<V> get(@Nullable K k) {
        return this.delegate().get((Object)k);
    }

    @Override
    @CanIgnoreReturnValue
    public List<V> removeAll(@Nullable Object object) {
        return this.delegate().removeAll(object);
    }

    @Override
    @CanIgnoreReturnValue
    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return this.delegate().replaceValues((Object)k, (Iterable)iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public Collection replaceValues(Object object, Iterable iterable) {
        return this.replaceValues(object, iterable);
    }

    @Override
    @CanIgnoreReturnValue
    public Collection removeAll(@Nullable Object object) {
        return this.removeAll(object);
    }

    @Override
    public Collection get(@Nullable Object object) {
        return this.get(object);
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

