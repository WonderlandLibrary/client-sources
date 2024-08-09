/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface Object2ObjectMap<K, V>
extends Object2ObjectFunction<K, V>,
Map<K, V> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(V var1);

    @Override
    public V defaultReturnValue();

    public ObjectSet<Entry<K, V>> object2ObjectEntrySet();

    @Override
    default public ObjectSet<Map.Entry<K, V>> entrySet() {
        return this.object2ObjectEntrySet();
    }

    @Override
    default public V put(K k, V v) {
        return Object2ObjectFunction.super.put(k, v);
    }

    @Override
    default public V remove(Object object) {
        return Object2ObjectFunction.super.remove(object);
    }

    @Override
    public ObjectSet<K> keySet();

    @Override
    public ObjectCollection<V> values();

    @Override
    public boolean containsKey(Object var1);

    @Override
    default public void forEach(BiConsumer<? super K, ? super V> biConsumer) {
        ObjectSet<Entry<K, V>> objectSet = this.object2ObjectEntrySet();
        Consumer<Entry> consumer = arg_0 -> Object2ObjectMap.lambda$forEach$0(biConsumer, arg_0);
        if (objectSet instanceof FastEntrySet) {
            ((FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    @Override
    default public V getOrDefault(Object object, V v) {
        Object v2 = this.get(object);
        return v2 != this.defaultReturnValue() || this.containsKey(object) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(K k, V v) {
        V v2;
        Object v3 = this.get(k);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return v3;
        }
        this.put(k, v);
        return v2;
    }

    @Override
    default public boolean remove(Object object, Object object2) {
        Object v = this.get(object);
        if (!Objects.equals(v, object2) || v == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.remove(object);
        return false;
    }

    @Override
    default public boolean replace(K k, V v, V v2) {
        Object v3 = this.get(k);
        if (!Objects.equals(v3, v) || v3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, v2);
        return false;
    }

    @Override
    default public V replace(K k, V v) {
        return this.containsKey(k) ? this.put(k, v) : this.defaultReturnValue();
    }

    @Override
    default public V computeIfAbsent(K k, Object2ObjectFunction<? super K, ? extends V> object2ObjectFunction) {
        Objects.requireNonNull(object2ObjectFunction);
        Object v = this.get(k);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(k)) {
            return v;
        }
        if (!object2ObjectFunction.containsKey(k)) {
            return v2;
        }
        V v3 = object2ObjectFunction.get(k);
        this.put(k, v3);
        return v3;
    }

    @Deprecated
    default public V computeObjectIfAbsentPartial(K k, Object2ObjectFunction<? super K, ? extends V> object2ObjectFunction) {
        return this.computeIfAbsent(k, object2ObjectFunction);
    }

    @Override
    default public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(k);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(k)) {
            return v2;
        }
        V v3 = biFunction.apply(k, v);
        if (v3 == null) {
            this.remove(k);
            return v2;
        }
        this.put(k, v3);
        return v3;
    }

    @Override
    default public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(k);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(k);
        V v3 = biFunction.apply(k, bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(k);
            }
            return v2;
        }
        this.put(k, v3);
        return v3;
    }

    @Override
    default public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(k);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(k)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(k);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(k, v2);
        return v2;
    }

    @Override
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    private static void lambda$forEach$0(BiConsumer biConsumer, Entry entry) {
        biConsumer.accept(entry.getKey(), entry.getValue());
    }

    public static interface FastEntrySet<K, V>
    extends ObjectSet<Entry<K, V>> {
        public ObjectIterator<Entry<K, V>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K, V>> consumer) {
            this.forEach(consumer);
        }
    }

    public static interface Entry<K, V>
    extends Map.Entry<K, V> {
    }
}

