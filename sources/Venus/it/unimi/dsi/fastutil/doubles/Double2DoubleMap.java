/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2DoubleMap
extends Double2DoubleFunction,
Map<Double, Double> {
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

    public ObjectSet<Entry> double2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Double>> entrySet() {
        return this.double2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Double d, Double d2) {
        return Double2DoubleFunction.super.put(d, d2);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Double2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Double2DoubleFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(double d, double d2) {
        double d3 = this.get(d);
        return d3 != this.defaultReturnValue() || this.containsKey(d) ? d3 : d2;
    }

    @Override
    default public double putIfAbsent(double d, double d2) {
        double d3;
        double d4 = this.get(d);
        if (d4 != (d3 = this.defaultReturnValue()) || this.containsKey(d)) {
            return d4;
        }
        this.put(d, d2);
        return d3;
    }

    default public boolean remove(double d, double d2) {
        double d3 = this.get(d);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d2) || d3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, double d2, double d3) {
        double d4 = this.get(d);
        if (Double.doubleToLongBits(d4) != Double.doubleToLongBits(d2) || d4 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, d3);
        return false;
    }

    @Override
    default public double replace(double d, double d2) {
        return this.containsKey(d) ? this.put(d, d2) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(double d, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        double d2 = this.get(d);
        if (d2 != this.defaultReturnValue() || this.containsKey(d)) {
            return d2;
        }
        double d3 = doubleUnaryOperator.applyAsDouble(d);
        this.put(d, d3);
        return d3;
    }

    default public double computeIfAbsentNullable(double d, DoubleFunction<? extends Double> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        double d2 = this.get(d);
        double d3 = this.defaultReturnValue();
        if (d2 != d3 || this.containsKey(d)) {
            return d2;
        }
        Double d4 = doubleFunction.apply(d);
        if (d4 == null) {
            return d3;
        }
        double d5 = d4;
        this.put(d, d5);
        return d5;
    }

    default public double computeIfAbsentPartial(double d, Double2DoubleFunction double2DoubleFunction) {
        Objects.requireNonNull(double2DoubleFunction);
        double d2 = this.get(d);
        double d3 = this.defaultReturnValue();
        if (d2 != d3 || this.containsKey(d)) {
            return d2;
        }
        if (!double2DoubleFunction.containsKey(d)) {
            return d3;
        }
        double d4 = double2DoubleFunction.get(d);
        this.put(d, d4);
        return d4;
    }

    @Override
    default public double computeIfPresent(double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d2 = this.get(d);
        double d3 = this.defaultReturnValue();
        if (d2 == d3 && !this.containsKey(d)) {
            return d3;
        }
        Double d4 = biFunction.apply((Double)d, (Double)d2);
        if (d4 == null) {
            this.remove(d);
            return d3;
        }
        double d5 = d4;
        this.put(d, d5);
        return d5;
    }

    @Override
    default public double compute(double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d2 = this.get(d);
        double d3 = this.defaultReturnValue();
        boolean bl = d2 != d3 || this.containsKey(d);
        Double d4 = biFunction.apply((Double)d, bl ? Double.valueOf(d2) : null);
        if (d4 == null) {
            if (bl) {
                this.remove(d);
            }
            return d3;
        }
        double d5 = d4;
        this.put(d, d5);
        return d5;
    }

    @Override
    default public double merge(double d, double d2, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d3;
        Objects.requireNonNull(biFunction);
        double d4 = this.get(d);
        double d5 = this.defaultReturnValue();
        if (d4 != d5 || this.containsKey(d)) {
            Double d6 = biFunction.apply((Double)d4, (Double)d2);
            if (d6 == null) {
                this.remove(d);
                return d5;
            }
            d3 = d6;
        } else {
            d3 = d2;
        }
        this.put(d, d3);
        return d3;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Double d, Double d2) {
        return Map.super.putIfAbsent(d, d2);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Double d2, Double d3) {
        return Map.super.replace(d, d2, d3);
    }

    @Override
    @Deprecated
    default public Double replace(Double d, Double d2) {
        return Map.super.replace(d, d2);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Double d, Function<? super Double, ? extends Double> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Double d, Double d2, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(d, d2, biFunction);
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
        return this.put((Double)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Double)object2);
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
    extends Map.Entry<Double, Double> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
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

