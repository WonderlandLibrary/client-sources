/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.floats.Float2ByteFunction;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Float2ByteMap
extends Float2ByteFunction,
Map<Float, Byte> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(byte var1);

    @Override
    public byte defaultReturnValue();

    public ObjectSet<Entry> float2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Byte>> entrySet() {
        return this.float2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Float f, Byte by) {
        return Float2ByteFunction.super.put(f, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Float2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Float2ByteFunction.super.remove(object);
    }

    public FloatSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(float f, byte by) {
        byte by2 = this.get(f);
        return by2 != this.defaultReturnValue() || this.containsKey(f) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(float f, byte by) {
        byte by2;
        byte by3 = this.get(f);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return by3;
        }
        this.put(f, by);
        return by2;
    }

    default public boolean remove(float f, byte by) {
        byte by2 = this.get(f);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, byte by, byte by2) {
        byte by3 = this.get(f);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, by2);
        return false;
    }

    @Override
    default public byte replace(float f, byte by) {
        return this.containsKey(f) ? this.put(f, by) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        byte by = this.get(f);
        if (by != this.defaultReturnValue() || this.containsKey(f)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(doubleToIntFunction.applyAsInt(f));
        this.put(f, by2);
        return by2;
    }

    default public byte computeIfAbsentNullable(float f, DoubleFunction<? extends Byte> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        byte by = this.get(f);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(f)) {
            return by;
        }
        Byte by3 = doubleFunction.apply(f);
        if (by3 == null) {
            return by2;
        }
        byte by4 = by3;
        this.put(f, by4);
        return by4;
    }

    default public byte computeIfAbsentPartial(float f, Float2ByteFunction float2ByteFunction) {
        Objects.requireNonNull(float2ByteFunction);
        byte by = this.get(f);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(f)) {
            return by;
        }
        if (!float2ByteFunction.containsKey(f)) {
            return by2;
        }
        byte by3 = float2ByteFunction.get(f);
        this.put(f, by3);
        return by3;
    }

    @Override
    default public byte computeIfPresent(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(f);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(f)) {
            return by2;
        }
        Byte by3 = biFunction.apply(Float.valueOf(f), (Byte)by);
        if (by3 == null) {
            this.remove(f);
            return by2;
        }
        byte by4 = by3;
        this.put(f, by4);
        return by4;
    }

    @Override
    default public byte compute(float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(f);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(f);
        Byte by3 = biFunction.apply(Float.valueOf(f), bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.remove(f);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(f, by4);
        return by4;
    }

    @Override
    default public byte merge(float f, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.get(f);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(f)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.remove(f);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(f, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Float f, Byte by) {
        return Map.super.putIfAbsent(f, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Byte by, Byte by2) {
        return Map.super.replace(f, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(Float f, Byte by) {
        return Map.super.replace(f, by);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Float f, Function<? super Float, ? extends Byte> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Float f, BiFunction<? super Float, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Float f, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(f, by, biFunction);
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
        return this.put((Float)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Byte)object2);
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
    extends Map.Entry<Float, Byte> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
        }

        public byte getByteValue();

        @Override
        public byte setValue(byte var1);

        @Override
        @Deprecated
        default public Byte getValue() {
            return this.getByteValue();
        }

        @Override
        @Deprecated
        default public Byte setValue(Byte by) {
            return this.setValue((byte)by);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Byte)object);
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

