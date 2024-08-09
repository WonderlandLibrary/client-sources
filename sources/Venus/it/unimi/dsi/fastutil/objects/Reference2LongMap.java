/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2LongFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Reference2LongMap<K>
extends Reference2LongFunction<K>,
Map<K, Long> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(long var1);

    @Override
    public long defaultReturnValue();

    public ObjectSet<Entry<K>> reference2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Long>> entrySet() {
        return this.reference2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(K k, Long l) {
        return Reference2LongFunction.super.put(k, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Reference2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Reference2LongFunction.super.remove(object);
    }

    @Override
    public ReferenceSet<K> keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    @Override
    default public long getOrDefault(Object object, long l) {
        long l2 = this.getLong(object);
        return l2 != this.defaultReturnValue() || this.containsKey(object) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(K k, long l) {
        long l2;
        long l3 = this.getLong(k);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return l3;
        }
        this.put(k, l);
        return l2;
    }

    default public boolean remove(Object object, long l) {
        long l2 = this.getLong(object);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeLong(object);
        return false;
    }

    @Override
    default public boolean replace(K k, long l, long l2) {
        long l3 = this.getLong(k);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, l2);
        return false;
    }

    @Override
    default public long replace(K k, long l) {
        return this.containsKey(k) ? this.put(k, l) : this.defaultReturnValue();
    }

    default public long computeLongIfAbsent(K k, ToLongFunction<? super K> toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        long l = this.getLong(k);
        if (l != this.defaultReturnValue() || this.containsKey(k)) {
            return l;
        }
        long l2 = toLongFunction.applyAsLong(k);
        this.put(k, l2);
        return l2;
    }

    default public long computeLongIfAbsentPartial(K k, Reference2LongFunction<? super K> reference2LongFunction) {
        Objects.requireNonNull(reference2LongFunction);
        long l = this.getLong(k);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(k)) {
            return l;
        }
        if (!reference2LongFunction.containsKey(k)) {
            return l2;
        }
        long l3 = reference2LongFunction.getLong(k);
        this.put(k, l3);
        return l3;
    }

    default public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.getLong(k);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(k)) {
            return l2;
        }
        Long l3 = biFunction.apply(k, l);
        if (l3 == null) {
            this.removeLong(k);
            return l2;
        }
        long l4 = l3;
        this.put(k, l4);
        return l4;
    }

    default public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.getLong(k);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(k);
        Long l3 = biFunction.apply(k, bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.removeLong(k);
            }
            return l2;
        }
        long l4 = l3;
        this.put(k, l4);
        return l4;
    }

    default public long mergeLong(K k, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.getLong(k);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(k)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.removeLong(k);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(k, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(K k, Long l) {
        return Map.super.putIfAbsent(k, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Long l, Long l2) {
        return Map.super.replace(k, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(K k, Long l) {
        return Map.super.replace(k, l);
    }

    @Override
    @Deprecated
    default public Long merge(K k, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(k, l, biFunction);
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
        return this.put((K)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Long)object2, (BiFunction<Long, Long, Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Long)object2);
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
    extends Map.Entry<K, Long> {
        public long getLongValue();

        @Override
        public long setValue(long var1);

        @Override
        @Deprecated
        default public Long getValue() {
            return this.getLongValue();
        }

        @Override
        @Deprecated
        default public Long setValue(Long l) {
            return this.setValue((long)l);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Long)object);
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

