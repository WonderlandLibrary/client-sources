/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.ints.Int2FloatFunction;
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
public interface Int2FloatMap
extends Int2FloatFunction,
Map<Integer, Float> {
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

    public ObjectSet<Entry> int2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Float>> entrySet() {
        return this.int2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Integer n, Float f) {
        return Int2FloatFunction.super.put(n, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Int2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Int2FloatFunction.super.remove(object);
    }

    public IntSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(int n, float f) {
        float f2 = this.get(n);
        return f2 != this.defaultReturnValue() || this.containsKey(n) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(int n, float f) {
        float f2;
        float f3 = this.get(n);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return f3;
        }
        this.put(n, f);
        return f2;
    }

    default public boolean remove(int n, float f) {
        float f2 = this.get(n);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, float f, float f2) {
        float f3 = this.get(n);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, f2);
        return false;
    }

    @Override
    default public float replace(int n, float f) {
        return this.containsKey(n) ? this.put(n, f) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(int n, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        float f = this.get(n);
        if (f != this.defaultReturnValue() || this.containsKey(n)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(intToDoubleFunction.applyAsDouble(n));
        this.put(n, f2);
        return f2;
    }

    default public float computeIfAbsentNullable(int n, IntFunction<? extends Float> intFunction) {
        Objects.requireNonNull(intFunction);
        float f = this.get(n);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(n)) {
            return f;
        }
        Float f3 = intFunction.apply(n);
        if (f3 == null) {
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(n, f4);
        return f4;
    }

    default public float computeIfAbsentPartial(int n, Int2FloatFunction int2FloatFunction) {
        Objects.requireNonNull(int2FloatFunction);
        float f = this.get(n);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(n)) {
            return f;
        }
        if (!int2FloatFunction.containsKey(n)) {
            return f2;
        }
        float f3 = int2FloatFunction.get(n);
        this.put(n, f3);
        return f3;
    }

    @Override
    default public float computeIfPresent(int n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(n);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(n)) {
            return f2;
        }
        Float f3 = biFunction.apply((Integer)n, Float.valueOf(f));
        if (f3 == null) {
            this.remove(n);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(n, f4);
        return f4;
    }

    @Override
    default public float compute(int n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(n);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(n);
        Float f3 = biFunction.apply((Integer)n, bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.remove(n);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(n, f4);
        return f4;
    }

    @Override
    default public float merge(int n, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.get(n);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(n)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.remove(n);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(n, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Integer n, Float f) {
        return Map.super.putIfAbsent(n, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Float f, Float f2) {
        return Map.super.replace(n, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(Integer n, Float f) {
        return Map.super.replace(n, f);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Integer n, Function<? super Integer, ? extends Float> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Integer n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Integer n, BiFunction<? super Integer, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Integer n, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(n, f, biFunction);
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
        return this.put((Integer)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Float)object2);
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
    extends Map.Entry<Integer, Float> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

