/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Int2DoubleMap
extends Int2DoubleFunction,
Map<Integer, Double> {
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

    public ObjectSet<Entry> int2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Double>> entrySet() {
        return this.int2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Integer n, Double d) {
        return Int2DoubleFunction.super.put(n, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Int2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Int2DoubleFunction.super.remove(object);
    }

    public IntSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(int n, double d) {
        double d2 = this.get(n);
        return d2 != this.defaultReturnValue() || this.containsKey(n) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(int n, double d) {
        double d2;
        double d3 = this.get(n);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return d3;
        }
        this.put(n, d);
        return d2;
    }

    default public boolean remove(int n, double d) {
        double d2 = this.get(n);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, double d, double d2) {
        double d3 = this.get(n);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, d2);
        return false;
    }

    @Override
    default public double replace(int n, double d) {
        return this.containsKey(n) ? this.put(n, d) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(int n, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        double d = this.get(n);
        if (d != this.defaultReturnValue() || this.containsKey(n)) {
            return d;
        }
        double d2 = intToDoubleFunction.applyAsDouble(n);
        this.put(n, d2);
        return d2;
    }

    default public double computeIfAbsentNullable(int n, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        double d = this.get(n);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(n)) {
            return d;
        }
        Double d3 = intFunction.apply(n);
        if (d3 == null) {
            return d2;
        }
        double d4 = d3;
        this.put(n, d4);
        return d4;
    }

    default public double computeIfAbsentPartial(int n, Int2DoubleFunction int2DoubleFunction) {
        Objects.requireNonNull(int2DoubleFunction);
        double d = this.get(n);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(n)) {
            return d;
        }
        if (!int2DoubleFunction.containsKey(n)) {
            return d2;
        }
        double d3 = int2DoubleFunction.get(n);
        this.put(n, d3);
        return d3;
    }

    @Override
    default public double computeIfPresent(int n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(n);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(n)) {
            return d2;
        }
        Double d3 = biFunction.apply((Integer)n, (Double)d);
        if (d3 == null) {
            this.remove(n);
            return d2;
        }
        double d4 = d3;
        this.put(n, d4);
        return d4;
    }

    @Override
    default public double compute(int n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(n);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(n);
        Double d3 = biFunction.apply((Integer)n, bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.remove(n);
            }
            return d2;
        }
        double d4 = d3;
        this.put(n, d4);
        return d4;
    }

    @Override
    default public double merge(int n, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.get(n);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(n)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.remove(n);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(n, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Integer n, Double d) {
        return Map.super.putIfAbsent(n, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Double d, Double d2) {
        return Map.super.replace(n, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(Integer n, Double d) {
        return Map.super.replace(n, d);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Integer n, Function<? super Integer, ? extends Double> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Integer n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Integer n, BiFunction<? super Integer, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Integer n, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(n, d, biFunction);
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
        return this.put((Integer)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Double)object2);
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
    extends Map.Entry<Integer, Double> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

