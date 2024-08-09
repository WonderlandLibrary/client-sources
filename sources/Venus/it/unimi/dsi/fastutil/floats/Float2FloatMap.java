/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.floats.FloatCollection;
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
public interface Float2FloatMap
extends Float2FloatFunction,
Map<Float, Float> {
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

    public ObjectSet<Entry> float2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Float>> entrySet() {
        return this.float2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Float f, Float f2) {
        return Float2FloatFunction.super.put(f, f2);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Float2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Float2FloatFunction.super.remove(object);
    }

    public FloatSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(float f, float f2) {
        float f3 = this.get(f);
        return f3 != this.defaultReturnValue() || this.containsKey(f) ? f3 : f2;
    }

    @Override
    default public float putIfAbsent(float f, float f2) {
        float f3;
        float f4 = this.get(f);
        if (f4 != (f3 = this.defaultReturnValue()) || this.containsKey(f)) {
            return f4;
        }
        this.put(f, f2);
        return f3;
    }

    default public boolean remove(float f, float f2) {
        float f3 = this.get(f);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f2) || f3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, float f2, float f3) {
        float f4 = this.get(f);
        if (Float.floatToIntBits(f4) != Float.floatToIntBits(f2) || f4 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, f3);
        return false;
    }

    @Override
    default public float replace(float f, float f2) {
        return this.containsKey(f) ? this.put(f, f2) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(float f, DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        float f2 = this.get(f);
        if (f2 != this.defaultReturnValue() || this.containsKey(f)) {
            return f2;
        }
        float f3 = SafeMath.safeDoubleToFloat(doubleUnaryOperator.applyAsDouble(f));
        this.put(f, f3);
        return f3;
    }

    default public float computeIfAbsentNullable(float f, DoubleFunction<? extends Float> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        float f2 = this.get(f);
        float f3 = this.defaultReturnValue();
        if (f2 != f3 || this.containsKey(f)) {
            return f2;
        }
        Float f4 = doubleFunction.apply(f);
        if (f4 == null) {
            return f3;
        }
        float f5 = f4.floatValue();
        this.put(f, f5);
        return f5;
    }

    default public float computeIfAbsentPartial(float f, Float2FloatFunction float2FloatFunction) {
        Objects.requireNonNull(float2FloatFunction);
        float f2 = this.get(f);
        float f3 = this.defaultReturnValue();
        if (f2 != f3 || this.containsKey(f)) {
            return f2;
        }
        if (!float2FloatFunction.containsKey(f)) {
            return f3;
        }
        float f4 = float2FloatFunction.get(f);
        this.put(f, f4);
        return f4;
    }

    @Override
    default public float computeIfPresent(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f2 = this.get(f);
        float f3 = this.defaultReturnValue();
        if (f2 == f3 && !this.containsKey(f)) {
            return f3;
        }
        Float f4 = biFunction.apply(Float.valueOf(f), Float.valueOf(f2));
        if (f4 == null) {
            this.remove(f);
            return f3;
        }
        float f5 = f4.floatValue();
        this.put(f, f5);
        return f5;
    }

    @Override
    default public float compute(float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f2 = this.get(f);
        float f3 = this.defaultReturnValue();
        boolean bl = f2 != f3 || this.containsKey(f);
        Float f4 = biFunction.apply(Float.valueOf(f), bl ? Float.valueOf(f2) : null);
        if (f4 == null) {
            if (bl) {
                this.remove(f);
            }
            return f3;
        }
        float f5 = f4.floatValue();
        this.put(f, f5);
        return f5;
    }

    @Override
    default public float merge(float f, float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f3;
        Objects.requireNonNull(biFunction);
        float f4 = this.get(f);
        float f5 = this.defaultReturnValue();
        if (f4 != f5 || this.containsKey(f)) {
            Float f6 = biFunction.apply(Float.valueOf(f4), Float.valueOf(f2));
            if (f6 == null) {
                this.remove(f);
                return f5;
            }
            f3 = f6.floatValue();
        } else {
            f3 = f2;
        }
        this.put(f, f3);
        return f3;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Float f, Float f2) {
        return Map.super.putIfAbsent(f, f2);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Float f2, Float f3) {
        return Map.super.replace(f, f2, f3);
    }

    @Override
    @Deprecated
    default public Float replace(Float f, Float f2) {
        return Map.super.replace(f, f2);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Float f, Function<? super Float, ? extends Float> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Float f, Float f2, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(f, f2, biFunction);
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
        return this.put((Float)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Float)object2);
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
    extends Map.Entry<Float, Float> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

