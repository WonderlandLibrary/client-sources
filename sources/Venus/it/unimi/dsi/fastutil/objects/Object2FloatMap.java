/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.Object2FloatFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Object2FloatMap<K>
extends Object2FloatFunction<K>,
Map<K, Float> {
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

    public ObjectSet<Entry<K>> object2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Float>> entrySet() {
        return this.object2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(K k, Float f) {
        return Object2FloatFunction.super.put(k, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Object2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Object2FloatFunction.super.remove(object);
    }

    @Override
    public ObjectSet<K> keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    @Override
    default public float getOrDefault(Object object, float f) {
        float f2 = this.getFloat(object);
        return f2 != this.defaultReturnValue() || this.containsKey(object) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(K k, float f) {
        float f2;
        float f3 = this.getFloat(k);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return f3;
        }
        this.put(k, f);
        return f2;
    }

    default public boolean remove(Object object, float f) {
        float f2 = this.getFloat(object);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeFloat(object);
        return false;
    }

    @Override
    default public boolean replace(K k, float f, float f2) {
        float f3 = this.getFloat(k);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, f2);
        return false;
    }

    @Override
    default public float replace(K k, float f) {
        return this.containsKey(k) ? this.put(k, f) : this.defaultReturnValue();
    }

    default public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        float f = this.getFloat(k);
        if (f != this.defaultReturnValue() || this.containsKey(k)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(toDoubleFunction.applyAsDouble(k));
        this.put(k, f2);
        return f2;
    }

    default public float computeFloatIfAbsentPartial(K k, Object2FloatFunction<? super K> object2FloatFunction) {
        Objects.requireNonNull(object2FloatFunction);
        float f = this.getFloat(k);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(k)) {
            return f;
        }
        if (!object2FloatFunction.containsKey(k)) {
            return f2;
        }
        float f3 = object2FloatFunction.getFloat(k);
        this.put(k, f3);
        return f3;
    }

    default public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.getFloat(k);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(k)) {
            return f2;
        }
        Float f3 = biFunction.apply(k, Float.valueOf(f));
        if (f3 == null) {
            this.removeFloat(k);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(k, f4);
        return f4;
    }

    default public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.getFloat(k);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(k);
        Float f3 = biFunction.apply(k, bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.removeFloat(k);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(k, f4);
        return f4;
    }

    default public float mergeFloat(K k, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.getFloat(k);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(k)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.removeFloat(k);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(k, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(K k, Float f) {
        return Map.super.putIfAbsent(k, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Float f, Float f2) {
        return Map.super.replace(k, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(K k, Float f) {
        return Map.super.replace(k, f);
    }

    @Override
    @Deprecated
    default public Float merge(K k, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(k, f, biFunction);
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
        return this.put((K)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Float)object2, (BiFunction<Float, Float, Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Float)object2);
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
    public static interface Entry<K>
    extends Map.Entry<K, Float> {
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
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
    }
}

