/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.longs.Long2FloatFunction;
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
public interface Long2FloatMap
extends Long2FloatFunction,
Map<Long, Float> {
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

    public ObjectSet<Entry> long2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Float>> entrySet() {
        return this.long2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Long l, Float f) {
        return Long2FloatFunction.super.put(l, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Long2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Long2FloatFunction.super.remove(object);
    }

    public LongSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(long l, float f) {
        float f2 = this.get(l);
        return f2 != this.defaultReturnValue() || this.containsKey(l) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(long l, float f) {
        float f2;
        float f3 = this.get(l);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return f3;
        }
        this.put(l, f);
        return f2;
    }

    default public boolean remove(long l, float f) {
        float f2 = this.get(l);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, float f, float f2) {
        float f3 = this.get(l);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, f2);
        return false;
    }

    @Override
    default public float replace(long l, float f) {
        return this.containsKey(l) ? this.put(l, f) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(long l, LongToDoubleFunction longToDoubleFunction) {
        Objects.requireNonNull(longToDoubleFunction);
        float f = this.get(l);
        if (f != this.defaultReturnValue() || this.containsKey(l)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(longToDoubleFunction.applyAsDouble(l));
        this.put(l, f2);
        return f2;
    }

    default public float computeIfAbsentNullable(long l, LongFunction<? extends Float> longFunction) {
        Objects.requireNonNull(longFunction);
        float f = this.get(l);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(l)) {
            return f;
        }
        Float f3 = longFunction.apply(l);
        if (f3 == null) {
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(l, f4);
        return f4;
    }

    default public float computeIfAbsentPartial(long l, Long2FloatFunction long2FloatFunction) {
        Objects.requireNonNull(long2FloatFunction);
        float f = this.get(l);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(l)) {
            return f;
        }
        if (!long2FloatFunction.containsKey(l)) {
            return f2;
        }
        float f3 = long2FloatFunction.get(l);
        this.put(l, f3);
        return f3;
    }

    @Override
    default public float computeIfPresent(long l, BiFunction<? super Long, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(l);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(l)) {
            return f2;
        }
        Float f3 = biFunction.apply((Long)l, Float.valueOf(f));
        if (f3 == null) {
            this.remove(l);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(l, f4);
        return f4;
    }

    @Override
    default public float compute(long l, BiFunction<? super Long, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(l);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(l);
        Float f3 = biFunction.apply((Long)l, bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.remove(l);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(l, f4);
        return f4;
    }

    @Override
    default public float merge(long l, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.get(l);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(l)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.remove(l);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(l, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Long l, Float f) {
        return Map.super.putIfAbsent(l, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Float f, Float f2) {
        return Map.super.replace(l, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(Long l, Float f) {
        return Map.super.replace(l, f);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Long l, Function<? super Long, ? extends Float> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Long l, BiFunction<? super Long, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Long l, BiFunction<? super Long, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Long l, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(l, f, biFunction);
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
        return this.put((Long)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Float)object2);
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
    extends Map.Entry<Long, Float> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
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

