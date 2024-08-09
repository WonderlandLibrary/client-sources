/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public interface Byte2ObjectMap<V>
extends Byte2ObjectFunction<V>,
Map<Byte, V> {
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

    public ObjectSet<Entry<V>> byte2ObjectEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, V>> entrySet() {
        return this.byte2ObjectEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Byte by, V v) {
        return Byte2ObjectFunction.super.put(by, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Byte2ObjectFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Byte2ObjectFunction.super.remove(object);
    }

    public ByteSet keySet();

    @Override
    public ObjectCollection<V> values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2ObjectFunction.super.containsKey(object);
    }

    default public V getOrDefault(byte by, V v) {
        Object v2 = this.get(by);
        return v2 != this.defaultReturnValue() || this.containsKey(by) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(byte by, V v) {
        V v2;
        Object v3 = this.get(by);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return v3;
        }
        this.put(by, v);
        return v2;
    }

    default public boolean remove(byte by, Object object) {
        Object v = this.get(by);
        if (!Objects.equals(v, object) || v == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, V v, V v2) {
        Object v3 = this.get(by);
        if (!Objects.equals(v3, v) || v3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, v2);
        return false;
    }

    @Override
    default public V replace(byte by, V v) {
        return this.containsKey(by) ? this.put(by, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(byte by, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        Object v = this.get(by);
        if (v != this.defaultReturnValue() || this.containsKey(by)) {
            return v;
        }
        V v2 = intFunction.apply(by);
        this.put(by, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(byte by, Byte2ObjectFunction<? extends V> byte2ObjectFunction) {
        Objects.requireNonNull(byte2ObjectFunction);
        Object v = this.get(by);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(by)) {
            return v;
        }
        if (!byte2ObjectFunction.containsKey(by)) {
            return v2;
        }
        V v3 = byte2ObjectFunction.get(by);
        this.put(by, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(byte by, BiFunction<? super Byte, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(by);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(by)) {
            return v2;
        }
        V v3 = biFunction.apply(by, v);
        if (v3 == null) {
            this.remove(by);
            return v2;
        }
        this.put(by, v3);
        return v3;
    }

    @Override
    default public V compute(byte by, BiFunction<? super Byte, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(by);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(by);
        V v3 = biFunction.apply(by, bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(by);
            }
            return v2;
        }
        this.put(by, v3);
        return v3;
    }

    @Override
    default public V merge(byte by, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(by);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(by)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(by);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(by, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Byte by, V v) {
        return Map.super.putIfAbsent(by, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, V v, V v2) {
        return Map.super.replace(by, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Byte by, V v) {
        return Map.super.replace(by, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Byte by, Function<? super Byte, ? extends V> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Byte by, BiFunction<? super Byte, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Byte by, BiFunction<? super Byte, ? super V, ? extends V> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Byte by, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(by, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Byte)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (V)object2);
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
    extends Map.Entry<Byte, V> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

