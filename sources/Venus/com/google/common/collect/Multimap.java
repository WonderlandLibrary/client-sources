/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multimap<K, V> {
    public int size();

    public boolean isEmpty();

    public boolean containsKey(@Nullable @CompatibleWith(value="K") Object var1);

    public boolean containsValue(@Nullable @CompatibleWith(value="V") Object var1);

    public boolean containsEntry(@Nullable @CompatibleWith(value="K") Object var1, @Nullable @CompatibleWith(value="V") Object var2);

    @CanIgnoreReturnValue
    public boolean put(@Nullable K var1, @Nullable V var2);

    @CanIgnoreReturnValue
    public boolean remove(@Nullable @CompatibleWith(value="K") Object var1, @Nullable @CompatibleWith(value="V") Object var2);

    @CanIgnoreReturnValue
    public boolean putAll(@Nullable K var1, Iterable<? extends V> var2);

    @CanIgnoreReturnValue
    public boolean putAll(Multimap<? extends K, ? extends V> var1);

    @CanIgnoreReturnValue
    public Collection<V> replaceValues(@Nullable K var1, Iterable<? extends V> var2);

    @CanIgnoreReturnValue
    public Collection<V> removeAll(@Nullable @CompatibleWith(value="K") Object var1);

    public void clear();

    public Collection<V> get(@Nullable K var1);

    public Set<K> keySet();

    public Multiset<K> keys();

    public Collection<V> values();

    public Collection<Map.Entry<K, V>> entries();

    default public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        this.entries().forEach(arg_0 -> Multimap.lambda$forEach$0(biConsumer, arg_0));
    }

    public Map<K, Collection<V>> asMap();

    public boolean equals(@Nullable Object var1);

    public int hashCode();

    private static void lambda$forEach$0(BiConsumer biConsumer, Map.Entry entry) {
        biConsumer.accept(entry.getKey(), entry.getValue());
    }
}

