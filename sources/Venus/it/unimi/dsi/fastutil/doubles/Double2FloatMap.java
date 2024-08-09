/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.Double2FloatFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
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
public interface Double2FloatMap
extends Double2FloatFunction,
Map<Double, Float> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(float var1);

    @Override
    public float defaultReturnValue();

    public ObjectSet<Entry> double2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Float>> entrySet() {
        return this.double2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Double d, Float f) {
        return Double2FloatFunction.super.put(d, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Double2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Double2FloatFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(double d, float f) {
        float f2 = this.get(d);
        return f2 != this.defaultReturnValue() || this.containsKey(d) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(double d, float f) {
        float f2;
        float f3 = this.get(d);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return f3;
        }
        this.put(d, f);
        return f2;
    }

    default public boolean remove(double d, float f) {
        float f2 = this.get(d);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, float f, float f2) {
        float f3 = this.get(d);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, f2);
        return false;
    }

    @Override
    default public float replace(double d, float f) {
        return this.containsKey(d) ? this.put(d, f) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(double d, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        float f = this.get(d);
        if (f != this.defaultReturnValue() || this.containsKey(d)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(doubleUnaryOperator.applyAsDouble(d));
        this.put(d, f2);
        return f2;
    }

    default public float computeIfAbsentNullable(double d, DoubleFunction<? extends Float> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        float f = this.get(d);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(d)) {
            return f;
        }
        Float f3 = doubleFunction.apply(d);
        if (f3 == null) {
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(d, f4);
        return f4;
    }

    default public float computeIfAbsentPartial(double d, Double2FloatFunction double2FloatFunction) {
        Objects.requireNonNull(double2FloatFunction);
        float f = this.get(d);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(d)) {
            return f;
        }
        if (!double2FloatFunction.containsKey(d)) {
            return f2;
        }
        float f3 = double2FloatFunction.get(d);
        this.put(d, f3);
        return f3;
    }

    @Override
    default public float computeIfPresent(double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(d);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(d)) {
            return f2;
        }
        Float f3 = biFunction.apply((Double)d, Float.valueOf(f));
        if (f3 == null) {
            this.remove(d);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(d, f4);
        return f4;
    }

    @Override
    default public float compute(double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(d);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(d);
        Float f3 = biFunction.apply((Double)d, bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.remove(d);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(d, f4);
        return f4;
    }

    @Override
    default public float merge(double d, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.get(d);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(d)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.remove(d);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(d, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Double d, Float f) {
        return Map.super.putIfAbsent(d, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Float f, Float f2) {
        return Map.super.replace(d, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(Double d, Float f) {
        return Map.super.replace(d, f);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Double d, Function<? super Double, ? extends Float> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Double d, BiFunction<? super Double, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Double d, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(d, f, biFunction);
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
        return this.put((Double)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Float)object2);
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
    extends Map.Entry<Double, Float> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
        }

        public float getFloatValue();

        @Override
        public float setValue(float var1);

        @Override
        @Deprecated
        default public Float getValue() {
            return Float.valueOf(this.getFloatValue());
        }

        @Override
        @Deprecated
        default public Float setValue(Float f) {
            return Float.valueOf(this.setValue(f.floatValue()));
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Float)object);
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

