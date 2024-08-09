/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2ReferenceFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Long2ReferenceMap<V>
extends Long2ReferenceFunction<V>,
Map<Long, V> {
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

    public ObjectSet<Entry<V>> long2ReferenceEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, V>> entrySet() {
        return this.long2ReferenceEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Long l, V v) {
        return Long2ReferenceFunction.super.put(l, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Long2ReferenceFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Long2ReferenceFunction.super.remove(object);
    }

    public LongSet keySet();

    @Override
    public ReferenceCollection<V> values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2ReferenceFunction.super.containsKey(object);
    }

    default public V getOrDefault(long l, V v) {
        Object v2 = this.get(l);
        return v2 != this.defaultReturnValue() || this.containsKey(l) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(long l, V v) {
        V v2;
        Object v3 = this.get(l);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return v3;
        }
        this.put(l, v);
        return v2;
    }

    default public boolean remove(long l, Object object) {
        Object v = this.get(l);
        if (v != object || v == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, V v, V v2) {
        Object v3 = this.get(l);
        if (v3 != v || v3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, v2);
        return false;
    }

    @Override
    default public V replace(long l, V v) {
        return this.containsKey(l) ? this.put(l, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(long l, LongFunction<? extends V> longFunction) {
        Objects.requireNonNull(longFunction);
        Object v = this.get(l);
        if (v != this.defaultReturnValue() || this.containsKey(l)) {
            return v;
        }
        V v2 = longFunction.apply(l);
        this.put(l, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(long l, Long2ReferenceFunction<? extends V> long2ReferenceFunction) {
        Objects.requireNonNull(long2ReferenceFunction);
        Object v = this.get(l);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(l)) {
            return v;
        }
        if (!long2ReferenceFunction.containsKey(l)) {
            return v2;
        }
        V v3 = long2ReferenceFunction.get(l);
        this.put(l, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(l);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(l)) {
            return v2;
        }
        V v3 = biFunction.apply(l, v);
        if (v3 == null) {
            this.remove(l);
            return v2;
        }
        this.put(l, v3);
        return v3;
    }

    @Override
    default public V compute(long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(l);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(l);
        V v3 = biFunction.apply(l, bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(l);
            }
            return v2;
        }
        this.put(l, v3);
        return v3;
    }

    @Override
    default public V merge(long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(l);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(l)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(l);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(l, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Long l, V v) {
        return Map.super.putIfAbsent(l, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, V v, V v2) {
        return Map.super.replace(l, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Long l, V v) {
        return Map.super.replace(l, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Long l, Function<? super Long, ? extends V> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Long l, BiFunction<? super Long, ? super V, ? extends V> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Long l, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(l, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Long)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (V)object2);
    }

    @Override
    @Deprecated
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static interface Entry<V>
    extends Map.Entry<Long, V> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

        @Override
        @Deprecated
        default public Object getKey() {
            return this.getKey();
        }
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<V>> consumer) {
            this.forEach(consumer);
        }
    }
}

