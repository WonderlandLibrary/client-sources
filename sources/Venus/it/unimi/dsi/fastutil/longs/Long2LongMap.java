/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.Long2LongFunction;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Long2LongMap
extends Long2LongFunction,
Map<Long, Long> {
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

    public ObjectSet<Entry> long2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Long>> entrySet() {
        return this.long2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Long l, Long l2) {
        return Long2LongFunction.super.put(l, l2);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Long2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Long2LongFunction.super.remove(object);
    }

    public LongSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(long l, long l2) {
        long l3 = this.get(l);
        return l3 != this.defaultReturnValue() || this.containsKey(l) ? l3 : l2;
    }

    @Override
    default public long putIfAbsent(long l, long l2) {
        long l3;
        long l4 = this.get(l);
        if (l4 != (l3 = this.defaultReturnValue()) || this.containsKey(l)) {
            return l4;
        }
        this.put(l, l2);
        return l3;
    }

    default public boolean remove(long l, long l2) {
        long l3 = this.get(l);
        if (l3 != l2 || l3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, long l2, long l3) {
        long l4 = this.get(l);
        if (l4 != l2 || l4 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, l3);
        return false;
    }

    @Override
    default public long replace(long l, long l2) {
        return this.containsKey(l) ? this.put(l, l2) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(long l, LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        long l2 = this.get(l);
        if (l2 != this.defaultReturnValue() || this.containsKey(l)) {
            return l2;
        }
        long l3 = longUnaryOperator.applyAsLong(l);
        this.put(l, l3);
        return l3;
    }

    default public long computeIfAbsentNullable(long l, LongFunction<? extends Long> longFunction) {
        Objects.requireNonNull(longFunction);
        long l2 = this.get(l);
        long l3 = this.defaultReturnValue();
        if (l2 != l3 || this.containsKey(l)) {
            return l2;
        }
        Long l4 = longFunction.apply(l);
        if (l4 == null) {
            return l3;
        }
        long l5 = l4;
        this.put(l, l5);
        return l5;
    }

    default public long computeIfAbsentPartial(long l, Long2LongFunction long2LongFunction) {
        Objects.requireNonNull(long2LongFunction);
        long l2 = this.get(l);
        long l3 = this.defaultReturnValue();
        if (l2 != l3 || this.containsKey(l)) {
            return l2;
        }
        if (!long2LongFunction.containsKey(l)) {
            return l3;
        }
        long l4 = long2LongFunction.get(l);
        this.put(l, l4);
        return l4;
    }

    @Override
    default public long computeIfPresent(long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l2 = this.get(l);
        long l3 = this.defaultReturnValue();
        if (l2 == l3 && !this.containsKey(l)) {
            return l3;
        }
        Long l4 = biFunction.apply((Long)l, (Long)l2);
        if (l4 == null) {
            this.remove(l);
            return l3;
        }
        long l5 = l4;
        this.put(l, l5);
        return l5;
    }

    @Override
    default public long compute(long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l2 = this.get(l);
        long l3 = this.defaultReturnValue();
        boolean bl = l2 != l3 || this.containsKey(l);
        Long l4 = biFunction.apply((Long)l, bl ? Long.valueOf(l2) : null);
        if (l4 == null) {
            if (bl) {
                this.remove(l);
            }
            return l3;
        }
        long l5 = l4;
        this.put(l, l5);
        return l5;
    }

    @Override
    default public long merge(long l, long l2, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l3;
        Objects.requireNonNull(biFunction);
        long l4 = this.get(l);
        long l5 = this.defaultReturnValue();
        if (l4 != l5 || this.containsKey(l)) {
            Long l6 = biFunction.apply((Long)l4, (Long)l2);
            if (l6 == null) {
                this.remove(l);
                return l5;
            }
            l3 = l6;
        } else {
            l3 = l2;
        }
        this.put(l, l3);
        return l3;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Long l, Long l2) {
        return Map.super.putIfAbsent(l, l2);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Long l2, Long l3) {
        return Map.super.replace(l, l2, l3);
    }

    @Override
    @Deprecated
    default public Long replace(Long l, Long l2) {
        return Map.super.replace(l, l2);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Long l, Function<? super Long, ? extends Long> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Long l, Long l2, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(l, l2, biFunction);
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
        return this.put((Long)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Long)object2);
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
    public static interface Entry
    extends Map.Entry<Long, Long> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

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

        @Override
        @Deprecated
        default public Object getKey() {
            return this.getKey();
        }
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();

        default public void fastForEach(Consumer<? super Entry> consumer) {
            this.forEach(consumer);
        }
    }
}

