/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2FloatFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public interface Short2FloatMap
extends Short2FloatFunction,
Map<Short, Float> {
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

    public ObjectSet<Entry> short2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Float>> entrySet() {
        return this.short2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Short s, Float f) {
        return Short2FloatFunction.super.put(s, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Short2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Short2FloatFunction.super.remove(object);
    }

    public ShortSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(short s, float f) {
        float f2 = this.get(s);
        return f2 != this.defaultReturnValue() || this.containsKey(s) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(short s, float f) {
        float f2;
        float f3 = this.get(s);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return f3;
        }
        this.put(s, f);
        return f2;
    }

    default public boolean remove(short s, float f) {
        float f2 = this.get(s);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, float f, float f2) {
        float f3 = this.get(s);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, f2);
        return false;
    }

    @Override
    default public float replace(short s, float f) {
        return this.containsKey(s) ? this.put(s, f) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(short s, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        float f = this.get(s);
        if (f != this.defaultReturnValue() || this.containsKey(s)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(intToDoubleFunction.applyAsDouble(s));
        this.put(s, f2);
        return f2;
    }

    default public float computeIfAbsentNullable(short s, IntFunction<? extends Float> intFunction) {
        Objects.requireNonNull(intFunction);
        float f = this.get(s);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(s)) {
            return f;
        }
        Float f3 = intFunction.apply(s);
        if (f3 == null) {
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(s, f4);
        return f4;
    }

    default public float computeIfAbsentPartial(short s, Short2FloatFunction short2FloatFunction) {
        Objects.requireNonNull(short2FloatFunction);
        float f = this.get(s);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(s)) {
            return f;
        }
        if (!short2FloatFunction.containsKey(s)) {
            return f2;
        }
        float f3 = short2FloatFunction.get(s);
        this.put(s, f3);
        return f3;
    }

    @Override
    default public float computeIfPresent(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(s);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(s)) {
            return f2;
        }
        Float f3 = biFunction.apply((Short)s, Float.valueOf(f));
        if (f3 == null) {
            this.remove(s);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(s, f4);
        return f4;
    }

    @Override
    default public float compute(short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(s);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(s);
        Float f3 = biFunction.apply((Short)s, bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.remove(s);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(s, f4);
        return f4;
    }

    @Override
    default public float merge(short s, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.get(s);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(s)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.remove(s);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(s, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Short s, Float f) {
        return Map.super.putIfAbsent(s, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Float f, Float f2) {
        return Map.super.replace(s, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(Short s, Float f) {
        return Map.super.replace(s, f);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Short s, Function<? super Short, ? extends Float> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Short s, BiFunction<? super Short, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Short s, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(s, f, biFunction);
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
        return this.put((Short)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Float)object2);
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
    extends Map.Entry<Short, Float> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

