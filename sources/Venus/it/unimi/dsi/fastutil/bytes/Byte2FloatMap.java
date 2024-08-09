/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.Byte2FloatFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
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
public interface Byte2FloatMap
extends Byte2FloatFunction,
Map<Byte, Float> {
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

    public ObjectSet<Entry> byte2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Float>> entrySet() {
        return this.byte2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Byte by, Float f) {
        return Byte2FloatFunction.super.put(by, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Byte2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Byte2FloatFunction.super.remove(object);
    }

    public ByteSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(byte by, float f) {
        float f2 = this.get(by);
        return f2 != this.defaultReturnValue() || this.containsKey(by) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(byte by, float f) {
        float f2;
        float f3 = this.get(by);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return f3;
        }
        this.put(by, f);
        return f2;
    }

    default public boolean remove(byte by, float f) {
        float f2 = this.get(by);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, float f, float f2) {
        float f3 = this.get(by);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, f2);
        return false;
    }

    @Override
    default public float replace(byte by, float f) {
        return this.containsKey(by) ? this.put(by, f) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(byte by, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        float f = this.get(by);
        if (f != this.defaultReturnValue() || this.containsKey(by)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(intToDoubleFunction.applyAsDouble(by));
        this.put(by, f2);
        return f2;
    }

    default public float computeIfAbsentNullable(byte by, IntFunction<? extends Float> intFunction) {
        Objects.requireNonNull(intFunction);
        float f = this.get(by);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(by)) {
            return f;
        }
        Float f3 = intFunction.apply(by);
        if (f3 == null) {
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(by, f4);
        return f4;
    }

    default public float computeIfAbsentPartial(byte by, Byte2FloatFunction byte2FloatFunction) {
        Objects.requireNonNull(byte2FloatFunction);
        float f = this.get(by);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(by)) {
            return f;
        }
        if (!byte2FloatFunction.containsKey(by)) {
            return f2;
        }
        float f3 = byte2FloatFunction.get(by);
        this.put(by, f3);
        return f3;
    }

    @Override
    default public float computeIfPresent(byte by, BiFunction<? super Byte, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(by);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(by)) {
            return f2;
        }
        Float f3 = biFunction.apply((Byte)by, Float.valueOf(f));
        if (f3 == null) {
            this.remove(by);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(by, f4);
        return f4;
    }

    @Override
    default public float compute(byte by, BiFunction<? super Byte, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(by);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(by);
        Float f3 = biFunction.apply((Byte)by, bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.remove(by);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(by, f4);
        return f4;
    }

    @Override
    default public float merge(byte by, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.get(by);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(by)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.remove(by);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(by, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Byte by, Float f) {
        return Map.super.putIfAbsent(by, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Float f, Float f2) {
        return Map.super.replace(by, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(Byte by, Float f) {
        return Map.super.replace(by, f);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Byte by, Function<? super Byte, ? extends Float> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Byte by, BiFunction<? super Byte, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Byte by, BiFunction<? super Byte, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Byte by, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(by, f, biFunction);
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
        return this.put((Byte)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Float)object2);
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
    extends Map.Entry<Byte, Float> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

