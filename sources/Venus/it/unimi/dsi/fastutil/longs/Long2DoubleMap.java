/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.longs.Long2DoubleFunction;
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
import java.util.function.LongToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Long2DoubleMap
extends Long2DoubleFunction,
Map<Long, Double> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(double var1);

    @Override
    public double defaultReturnValue();

    public ObjectSet<Entry> long2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Double>> entrySet() {
        return this.long2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Long l, Double d) {
        return Long2DoubleFunction.super.put(l, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Long2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Long2DoubleFunction.super.remove(object);
    }

    public LongSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(long l, double d) {
        double d2 = this.get(l);
        return d2 != this.defaultReturnValue() || this.containsKey(l) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(long l, double d) {
        double d2;
        double d3 = this.get(l);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return d3;
        }
        this.put(l, d);
        return d2;
    }

    default public boolean remove(long l, double d) {
        double d2 = this.get(l);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, double d, double d2) {
        double d3 = this.get(l);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, d2);
        return false;
    }

    @Override
    default public double replace(long l, double d) {
        return this.containsKey(l) ? this.put(l, d) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(long l, LongToDoubleFunction longToDoubleFunction) {
        Objects.requireNonNull(longToDoubleFunction);
        double d = this.get(l);
        if (d != this.defaultReturnValue() || this.containsKey(l)) {
            return d;
        }
        double d2 = longToDoubleFunction.applyAsDouble(l);
        this.put(l, d2);
        return d2;
    }

    default public double computeIfAbsentNullable(long l, LongFunction<? extends Double> longFunction) {
        Objects.requireNonNull(longFunction);
        double d = this.get(l);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(l)) {
            return d;
        }
        Double d3 = longFunction.apply(l);
        if (d3 == null) {
            return d2;
        }
        double d4 = d3;
        this.put(l, d4);
        return d4;
    }

    default public double computeIfAbsentPartial(long l, Long2DoubleFunction long2DoubleFunction) {
        Objects.requireNonNull(long2DoubleFunction);
        double d = this.get(l);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(l)) {
            return d;
        }
        if (!long2DoubleFunction.containsKey(l)) {
            return d2;
        }
        double d3 = long2DoubleFunction.get(l);
        this.put(l, d3);
        return d3;
    }

    @Override
    default public double computeIfPresent(long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(l);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(l)) {
            return d2;
        }
        Double d3 = biFunction.apply((Long)l, (Double)d);
        if (d3 == null) {
            this.remove(l);
            return d2;
        }
        double d4 = d3;
        this.put(l, d4);
        return d4;
    }

    @Override
    default public double compute(long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(l);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(l);
        Double d3 = biFunction.apply((Long)l, bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.remove(l);
            }
            return d2;
        }
        double d4 = d3;
        this.put(l, d4);
        return d4;
    }

    @Override
    default public double merge(long l, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.get(l);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(l)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.remove(l);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(l, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Long l, Double d) {
        return Map.super.putIfAbsent(l, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Double d, Double d2) {
        return Map.super.replace(l, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(Long l, Double d) {
        return Map.super.replace(l, d);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Long l, Function<? super Long, ? extends Double> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Long l, BiFunction<? super Long, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Long l, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(l, d, biFunction);
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
        return this.put((Long)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Double)object2);
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
    extends Map.Entry<Long, Double> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

        public double getDoubleValue();

        @Override
        public double setValue(double var1);

        @Override
        @Deprecated
        default public Double getValue() {
            return this.getDoubleValue();
        }

        @Override
        @Deprecated
        default public Double setValue(Double d) {
            return this.setValue((double)d);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Double)object);
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

