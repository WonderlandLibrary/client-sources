/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.Object2ShortFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Object2ShortMap<K>
extends Object2ShortFunction<K>,
Map<K, Short> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(short var1);

    @Override
    public short defaultReturnValue();

    public ObjectSet<Entry<K>> object2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Short>> entrySet() {
        return this.object2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(K k, Short s) {
        return Object2ShortFunction.super.put(k, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Object2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Object2ShortFunction.super.remove(object);
    }

    @Override
    public ObjectSet<K> keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    @Override
    default public short getOrDefault(Object object, short s) {
        short s2 = this.getShort(object);
        return s2 != this.defaultReturnValue() || this.containsKey(object) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(K k, short s) {
        short s2;
        short s3 = this.getShort(k);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return s3;
        }
        this.put(k, s);
        return s2;
    }

    default public boolean remove(Object object, short s) {
        short s2 = this.getShort(object);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeShort(object);
        return false;
    }

    @Override
    default public boolean replace(K k, short s, short s2) {
        short s3 = this.getShort(k);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, s2);
        return false;
    }

    @Override
    default public short replace(K k, short s) {
        return this.containsKey(k) ? this.put(k, s) : this.defaultReturnValue();
    }

    default public short computeShortIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        short s = this.getShort(k);
        if (s != this.defaultReturnValue() || this.containsKey(k)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(toIntFunction.applyAsInt(k));
        this.put(k, s2);
        return s2;
    }

    default public short computeShortIfAbsentPartial(K k, Object2ShortFunction<? super K> object2ShortFunction) {
        Objects.requireNonNull(object2ShortFunction);
        short s = this.getShort(k);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(k)) {
            return s;
        }
        if (!object2ShortFunction.containsKey(k)) {
            return s2;
        }
        short s3 = object2ShortFunction.getShort(k);
        this.put(k, s3);
        return s3;
    }

    default public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.getShort(k);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(k)) {
            return s2;
        }
        Short s3 = biFunction.apply(k, s);
        if (s3 == null) {
            this.removeShort(k);
            return s2;
        }
        short s4 = s3;
        this.put(k, s4);
        return s4;
    }

    default public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.getShort(k);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(k);
        Short s3 = biFunction.apply(k, bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.removeShort(k);
            }
            return s2;
        }
        short s4 = s3;
        this.put(k, s4);
        return s4;
    }

    default public short mergeShort(K k, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.getShort(k);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(k)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.removeShort(k);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(k, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(K k, Short s) {
        return Map.super.putIfAbsent(k, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Short s, Short s2) {
        return Map.super.replace(k, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(K k, Short s) {
        return Map.super.replace(k, s);
    }

    @Override
    @Deprecated
    default public Short merge(K k, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(k, s, biFunction);
    }

    @Override
    @Deprecated
    default public Object remove(Object object) {
        return this.remove(object);
    }

    @Override
    @Deprecated
    default public Object get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((K)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Short)object2, (BiFunction<Short, Short, Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Short)object2);
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
    public static interface Entry<K>
    extends Map.Entry<K, Short> {
        public short getShortValue();

        @Override
        public short setValue(short var1);

        @Override
        @Deprecated
        default public Short getValue() {
            return this.getShortValue();
        }

        @Override
        @Deprecated
        default public Short setValue(Short s) {
            return this.setValue((short)s);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Short)object);
        }

        @Override
        @Deprecated
        default public Object getValue() {
            return this.getValue();
        }
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
    }
}

