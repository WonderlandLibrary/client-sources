/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.shorts.Short2ReferenceFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public interface Short2ReferenceMap<V>
extends Short2ReferenceFunction<V>,
Map<Short, V> {
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

    public ObjectSet<Entry<V>> short2ReferenceEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, V>> entrySet() {
        return this.short2ReferenceEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Short s, V v) {
        return Short2ReferenceFunction.super.put(s, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Short2ReferenceFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Short2ReferenceFunction.super.remove(object);
    }

    public ShortSet keySet();

    @Override
    public ReferenceCollection<V> values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2ReferenceFunction.super.containsKey(object);
    }

    default public V getOrDefault(short s, V v) {
        Object v2 = this.get(s);
        return v2 != this.defaultReturnValue() || this.containsKey(s) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(short s, V v) {
        V v2;
        Object v3 = this.get(s);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return v3;
        }
        this.put(s, v);
        return v2;
    }

    default public boolean remove(short s, Object object) {
        Object v = this.get(s);
        if (v != object || v == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, V v, V v2) {
        Object v3 = this.get(s);
        if (v3 != v || v3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, v2);
        return false;
    }

    @Override
    default public V replace(short s, V v) {
        return this.containsKey(s) ? this.put(s, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(short s, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        Object v = this.get(s);
        if (v != this.defaultReturnValue() || this.containsKey(s)) {
            return v;
        }
        V v2 = intFunction.apply(s);
        this.put(s, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(short s, Short2ReferenceFunction<? extends V> short2ReferenceFunction) {
        Objects.requireNonNull(short2ReferenceFunction);
        Object v = this.get(s);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(s)) {
            return v;
        }
        if (!short2ReferenceFunction.containsKey(s)) {
            return v2;
        }
        V v3 = short2ReferenceFunction.get(s);
        this.put(s, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(short s, BiFunction<? super Short, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(s);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(s)) {
            return v2;
        }
        V v3 = biFunction.apply(s, v);
        if (v3 == null) {
            this.remove(s);
            return v2;
        }
        this.put(s, v3);
        return v3;
    }

    @Override
    default public V compute(short s, BiFunction<? super Short, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(s);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(s);
        V v3 = biFunction.apply(s, bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(s);
            }
            return v2;
        }
        this.put(s, v3);
        return v3;
    }

    @Override
    default public V merge(short s, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(s);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(s)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(s);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(s, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Short s, V v) {
        return Map.super.putIfAbsent(s, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, V v, V v2) {
        return Map.super.replace(s, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Short s, V v) {
        return Map.super.replace(s, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Short s, Function<? super Short, ? extends V> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Short s, BiFunction<? super Short, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Short s, BiFunction<? super Short, ? super V, ? extends V> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Short s, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(s, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Short)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (V)object2);
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
    extends Map.Entry<Short, V> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

