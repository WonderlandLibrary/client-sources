/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2ReferenceFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
public interface Float2ReferenceMap<V>
extends Float2ReferenceFunction<V>,
Map<Float, V> {
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

    public ObjectSet<Entry<V>> float2ReferenceEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, V>> entrySet() {
        return this.float2ReferenceEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Float f, V v) {
        return Float2ReferenceFunction.super.put(f, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Float2ReferenceFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Float2ReferenceFunction.super.remove(object);
    }

    public FloatSet keySet();

    @Override
    public ReferenceCollection<V> values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2ReferenceFunction.super.containsKey(object);
    }

    default public V getOrDefault(float f, V v) {
        Object v2 = this.get(f);
        return v2 != this.defaultReturnValue() || this.containsKey(f) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(float f, V v) {
        V v2;
        Object v3 = this.get(f);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return v3;
        }
        this.put(f, v);
        return v2;
    }

    default public boolean remove(float f, Object object) {
        Object v = this.get(f);
        if (v != object || v == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, V v, V v2) {
        Object v3 = this.get(f);
        if (v3 != v || v3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, v2);
        return false;
    }

    @Override
    default public V replace(float f, V v) {
        return this.containsKey(f) ? this.put(f, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(float f, DoubleFunction<? extends V> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        Object v = this.get(f);
        if (v != this.defaultReturnValue() || this.containsKey(f)) {
            return v;
        }
        V v2 = doubleFunction.apply(f);
        this.put(f, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(float f, Float2ReferenceFunction<? extends V> float2ReferenceFunction) {
        Objects.requireNonNull(float2ReferenceFunction);
        Object v = this.get(f);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(f)) {
            return v;
        }
        if (!float2ReferenceFunction.containsKey(f)) {
            return v2;
        }
        V v3 = float2ReferenceFunction.get(f);
        this.put(f, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(float f, BiFunction<? super Float, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(f);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(f)) {
            return v2;
        }
        V v3 = biFunction.apply(Float.valueOf(f), v);
        if (v3 == null) {
            this.remove(f);
            return v2;
        }
        this.put(f, v3);
        return v3;
    }

    @Override
    default public V compute(float f, BiFunction<? super Float, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(f);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(f);
        V v3 = biFunction.apply(Float.valueOf(f), bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(f);
            }
            return v2;
        }
        this.put(f, v3);
        return v3;
    }

    @Override
    default public V merge(float f, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(f);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(f)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(f);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(f, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Float f, V v) {
        return Map.super.putIfAbsent(f, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, V v, V v2) {
        return Map.super.replace(f, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Float f, V v) {
        return Map.super.replace(f, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Float f, Function<? super Float, ? extends V> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Float f, BiFunction<? super Float, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Float f, BiFunction<? super Float, ? super V, ? extends V> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Float f, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(f, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Float)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (V)object2);
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
    extends Map.Entry<Float, V> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

