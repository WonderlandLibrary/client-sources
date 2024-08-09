/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Int2ObjectMap<V>
extends Int2ObjectFunction<V>,
Map<Integer, V> {
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

    public ObjectSet<Entry<V>> int2ObjectEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, V>> entrySet() {
        return this.int2ObjectEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Integer n, V v) {
        return Int2ObjectFunction.super.put(n, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Int2ObjectFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Int2ObjectFunction.super.remove(object);
    }

    public IntSet keySet();

    @Override
    public ObjectCollection<V> values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2ObjectFunction.super.containsKey(object);
    }

    default public V getOrDefault(int n, V v) {
        Object v2 = this.get(n);
        return v2 != this.defaultReturnValue() || this.containsKey(n) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(int n, V v) {
        V v2;
        Object v3 = this.get(n);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return v3;
        }
        this.put(n, v);
        return v2;
    }

    default public boolean remove(int n, Object object) {
        Object v = this.get(n);
        if (!Objects.equals(v, object) || v == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, V v, V v2) {
        Object v3 = this.get(n);
        if (!Objects.equals(v3, v) || v3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, v2);
        return false;
    }

    @Override
    default public V replace(int n, V v) {
        return this.containsKey(n) ? this.put(n, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(int n, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        Object v = this.get(n);
        if (v != this.defaultReturnValue() || this.containsKey(n)) {
            return v;
        }
        V v2 = intFunction.apply(n);
        this.put(n, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(int n, Int2ObjectFunction<? extends V> int2ObjectFunction) {
        Objects.requireNonNull(int2ObjectFunction);
        Object v = this.get(n);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(n)) {
            return v;
        }
        if (!int2ObjectFunction.containsKey(n)) {
            return v2;
        }
        V v3 = int2ObjectFunction.get(n);
        this.put(n, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(int n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(n);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(n)) {
            return v2;
        }
        V v3 = biFunction.apply(n, v);
        if (v3 == null) {
            this.remove(n);
            return v2;
        }
        this.put(n, v3);
        return v3;
    }

    @Override
    default public V compute(int n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(n);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(n);
        V v3 = biFunction.apply(n, bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(n);
            }
            return v2;
        }
        this.put(n, v3);
        return v3;
    }

    @Override
    default public V merge(int n, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(n);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(n)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(n);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(n, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Integer n, V v) {
        return Map.super.putIfAbsent(n, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, V v, V v2) {
        return Map.super.replace(n, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Integer n, V v) {
        return Map.super.replace(n, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Integer n, Function<? super Integer, ? extends V> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Integer n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Integer n, BiFunction<? super Integer, ? super V, ? extends V> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Integer n, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(n, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Integer)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (V)object2);
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
    extends Map.Entry<Integer, V> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

