/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2ObjectMap<V>
extends Double2ObjectFunction<V>,
Map<Double, V> {
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

    public ObjectSet<Entry<V>> double2ObjectEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, V>> entrySet() {
        return this.double2ObjectEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Double d, V v) {
        return Double2ObjectFunction.super.put(d, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Double2ObjectFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Double2ObjectFunction.super.remove(object);
    }

    public DoubleSet keySet();

    @Override
    public ObjectCollection<V> values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2ObjectFunction.super.containsKey(object);
    }

    default public V getOrDefault(double d, V v) {
        Object v2 = this.get(d);
        return v2 != this.defaultReturnValue() || this.containsKey(d) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(double d, V v) {
        V v2;
        Object v3 = this.get(d);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return v3;
        }
        this.put(d, v);
        return v2;
    }

    default public boolean remove(double d, Object object) {
        Object v = this.get(d);
        if (!Objects.equals(v, object) || v == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, V v, V v2) {
        Object v3 = this.get(d);
        if (!Objects.equals(v3, v) || v3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, v2);
        return false;
    }

    @Override
    default public V replace(double d, V v) {
        return this.containsKey(d) ? this.put(d, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(double d, DoubleFunction<? extends V> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        Object v = this.get(d);
        if (v != this.defaultReturnValue() || this.containsKey(d)) {
            return v;
        }
        V v2 = doubleFunction.apply(d);
        this.put(d, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(double d, Double2ObjectFunction<? extends V> double2ObjectFunction) {
        Objects.requireNonNull(double2ObjectFunction);
        Object v = this.get(d);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(d)) {
            return v;
        }
        if (!double2ObjectFunction.containsKey(d)) {
            return v2;
        }
        V v3 = double2ObjectFunction.get(d);
        this.put(d, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(d);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(d)) {
            return v2;
        }
        V v3 = biFunction.apply(d, v);
        if (v3 == null) {
            this.remove(d);
            return v2;
        }
        this.put(d, v3);
        return v3;
    }

    @Override
    default public V compute(double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(d);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(d);
        V v3 = biFunction.apply(d, bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(d);
            }
            return v2;
        }
        this.put(d, v3);
        return v3;
    }

    @Override
    default public V merge(double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(d);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(d)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(d);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(d, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Double d, V v) {
        return Map.super.putIfAbsent(d, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, V v, V v2) {
        return Map.super.replace(d, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Double d, V v) {
        return Map.super.replace(d, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Double d, Function<? super Double, ? extends V> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Double d, BiFunction<? super Double, ? super V, ? extends V> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Double d, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(d, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Double)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (V)object2);
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
    extends Map.Entry<Double, V> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
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

