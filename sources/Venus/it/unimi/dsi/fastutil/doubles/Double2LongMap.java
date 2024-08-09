/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2LongFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2LongMap
extends Double2LongFunction,
Map<Double, Long> {
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

    public ObjectSet<Entry> double2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Long>> entrySet() {
        return this.double2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Double d, Long l) {
        return Double2LongFunction.super.put(d, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Double2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Double2LongFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(double d, long l) {
        long l2 = this.get(d);
        return l2 != this.defaultReturnValue() || this.containsKey(d) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(double d, long l) {
        long l2;
        long l3 = this.get(d);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return l3;
        }
        this.put(d, l);
        return l2;
    }

    default public boolean remove(double d, long l) {
        long l2 = this.get(d);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, long l, long l2) {
        long l3 = this.get(d);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, l2);
        return false;
    }

    @Override
    default public long replace(double d, long l) {
        return this.containsKey(d) ? this.put(d, l) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(double d, DoubleToLongFunction doubleToLongFunction) {
        Objects.requireNonNull(doubleToLongFunction);
        long l = this.get(d);
        if (l != this.defaultReturnValue() || this.containsKey(d)) {
            return l;
        }
        long l2 = doubleToLongFunction.applyAsLong(d);
        this.put(d, l2);
        return l2;
    }

    default public long computeIfAbsentNullable(double d, DoubleFunction<? extends Long> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        long l = this.get(d);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(d)) {
            return l;
        }
        Long l3 = doubleFunction.apply(d);
        if (l3 == null) {
            return l2;
        }
        long l4 = l3;
        this.put(d, l4);
        return l4;
    }

    default public long computeIfAbsentPartial(double d, Double2LongFunction double2LongFunction) {
        Objects.requireNonNull(double2LongFunction);
        long l = this.get(d);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(d)) {
            return l;
        }
        if (!double2LongFunction.containsKey(d)) {
            return l2;
        }
        long l3 = double2LongFunction.get(d);
        this.put(d, l3);
        return l3;
    }

    @Override
    default public long computeIfPresent(double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(d);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(d)) {
            return l2;
        }
        Long l3 = biFunction.apply((Double)d, (Long)l);
        if (l3 == null) {
            this.remove(d);
            return l2;
        }
        long l4 = l3;
        this.put(d, l4);
        return l4;
    }

    @Override
    default public long compute(double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(d);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(d);
        Long l3 = biFunction.apply((Double)d, bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.remove(d);
            }
            return l2;
        }
        long l4 = l3;
        this.put(d, l4);
        return l4;
    }

    @Override
    default public long merge(double d, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.get(d);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(d)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.remove(d);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(d, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Double d, Long l) {
        return Map.super.putIfAbsent(d, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Long l, Long l2) {
        return Map.super.replace(d, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(Double d, Long l) {
        return Map.super.replace(d, l);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Double d, Function<? super Double, ? extends Long> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Double d, BiFunction<? super Double, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Double d, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(d, l, biFunction);
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
        return this.put((Double)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Long)object2);
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
    extends Map.Entry<Double, Long> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
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

