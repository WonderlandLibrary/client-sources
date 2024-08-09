/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
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
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Int2LongMap
extends Int2LongFunction,
Map<Integer, Long> {
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

    public ObjectSet<Entry> int2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Long>> entrySet() {
        return this.int2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Integer n, Long l) {
        return Int2LongFunction.super.put(n, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Int2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Int2LongFunction.super.remove(object);
    }

    public IntSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(int n, long l) {
        long l2 = this.get(n);
        return l2 != this.defaultReturnValue() || this.containsKey(n) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(int n, long l) {
        long l2;
        long l3 = this.get(n);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return l3;
        }
        this.put(n, l);
        return l2;
    }

    default public boolean remove(int n, long l) {
        long l2 = this.get(n);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, long l, long l2) {
        long l3 = this.get(n);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, l2);
        return false;
    }

    @Override
    default public long replace(int n, long l) {
        return this.containsKey(n) ? this.put(n, l) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(int n, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        long l = this.get(n);
        if (l != this.defaultReturnValue() || this.containsKey(n)) {
            return l;
        }
        long l2 = intToLongFunction.applyAsLong(n);
        this.put(n, l2);
        return l2;
    }

    default public long computeIfAbsentNullable(int n, IntFunction<? extends Long> intFunction) {
        Objects.requireNonNull(intFunction);
        long l = this.get(n);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(n)) {
            return l;
        }
        Long l3 = intFunction.apply(n);
        if (l3 == null) {
            return l2;
        }
        long l4 = l3;
        this.put(n, l4);
        return l4;
    }

    default public long computeIfAbsentPartial(int n, Int2LongFunction int2LongFunction) {
        Objects.requireNonNull(int2LongFunction);
        long l = this.get(n);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(n)) {
            return l;
        }
        if (!int2LongFunction.containsKey(n)) {
            return l2;
        }
        long l3 = int2LongFunction.get(n);
        this.put(n, l3);
        return l3;
    }

    @Override
    default public long computeIfPresent(int n, BiFunction<? super Integer, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(n);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(n)) {
            return l2;
        }
        Long l3 = biFunction.apply((Integer)n, (Long)l);
        if (l3 == null) {
            this.remove(n);
            return l2;
        }
        long l4 = l3;
        this.put(n, l4);
        return l4;
    }

    @Override
    default public long compute(int n, BiFunction<? super Integer, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(n);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(n);
        Long l3 = biFunction.apply((Integer)n, bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.remove(n);
            }
            return l2;
        }
        long l4 = l3;
        this.put(n, l4);
        return l4;
    }

    @Override
    default public long merge(int n, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.get(n);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(n)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.remove(n);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(n, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Integer n, Long l) {
        return Map.super.putIfAbsent(n, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Long l, Long l2) {
        return Map.super.replace(n, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(Integer n, Long l) {
        return Map.super.replace(n, l);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Integer n, Function<? super Integer, ? extends Long> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Integer n, BiFunction<? super Integer, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Integer n, BiFunction<? super Integer, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Integer n, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(n, l, biFunction);
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
        return this.put((Integer)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Long)object2);
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
    extends Map.Entry<Integer, Long> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

