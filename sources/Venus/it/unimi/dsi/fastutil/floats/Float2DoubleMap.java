/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.floats.Float2DoubleFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
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
public interface Float2DoubleMap
extends Float2DoubleFunction,
Map<Float, Double> {
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

    public ObjectSet<Entry> float2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Double>> entrySet() {
        return this.float2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Float f, Double d) {
        return Float2DoubleFunction.super.put(f, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Float2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Float2DoubleFunction.super.remove(object);
    }

    public FloatSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(float f, double d) {
        double d2 = this.get(f);
        return d2 != this.defaultReturnValue() || this.containsKey(f) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(float f, double d) {
        double d2;
        double d3 = this.get(f);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return d3;
        }
        this.put(f, d);
        return d2;
    }

    default public boolean remove(float f, double d) {
        double d2 = this.get(f);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, double d, double d2) {
        double d3 = this.get(f);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, d2);
        return false;
    }

    @Override
    default public double replace(float f, double d) {
        return this.containsKey(f) ? this.put(f, d) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        double d = this.get(f);
        if (d != this.defaultReturnValue() || this.containsKey(f)) {
            return d;
        }
        double d2 = doubleUnaryOperator.applyAsDouble(f);
        this.put(f, d2);
        return d2;
    }

    default public double computeIfAbsentNullable(float f, DoubleFunction<? extends Double> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        double d = this.get(f);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(f)) {
            return d;
        }
        Double d3 = doubleFunction.apply(f);
        if (d3 == null) {
            return d2;
        }
        double d4 = d3;
        this.put(f, d4);
        return d4;
    }

    default public double computeIfAbsentPartial(float f, Float2DoubleFunction float2DoubleFunction) {
        Objects.requireNonNull(float2DoubleFunction);
        double d = this.get(f);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(f)) {
            return d;
        }
        if (!float2DoubleFunction.containsKey(f)) {
            return d2;
        }
        double d3 = float2DoubleFunction.get(f);
        this.put(f, d3);
        return d3;
    }

    @Override
    default public double computeIfPresent(float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(f);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(f)) {
            return d2;
        }
        Double d3 = biFunction.apply(Float.valueOf(f), (Double)d);
        if (d3 == null) {
            this.remove(f);
            return d2;
        }
        double d4 = d3;
        this.put(f, d4);
        return d4;
    }

    @Override
    default public double compute(float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(f);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(f);
        Double d3 = biFunction.apply(Float.valueOf(f), bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.remove(f);
            }
            return d2;
        }
        double d4 = d3;
        this.put(f, d4);
        return d4;
    }

    @Override
    default public double merge(float f, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.get(f);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(f)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.remove(f);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(f, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Float f, Double d) {
        return Map.super.putIfAbsent(f, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Double d, Double d2) {
        return Map.super.replace(f, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(Float f, Double d) {
        return Map.super.replace(f, d);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Float f, Function<? super Float, ? extends Double> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Float f, BiFunction<? super Float, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Float f, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(f, d, biFunction);
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
        return this.put((Float)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Double)object2);
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
    extends Map.Entry<Float, Double> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

