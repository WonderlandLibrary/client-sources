/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.doubles.Double2ByteFunction;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2ByteMap
extends Double2ByteFunction,
Map<Double, Byte> {
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

    public ObjectSet<Entry> double2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Byte>> entrySet() {
        return this.double2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Double d, Byte by) {
        return Double2ByteFunction.super.put(d, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Double2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Double2ByteFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(double d, byte by) {
        byte by2 = this.get(d);
        return by2 != this.defaultReturnValue() || this.containsKey(d) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(double d, byte by) {
        byte by2;
        byte by3 = this.get(d);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return by3;
        }
        this.put(d, by);
        return by2;
    }

    default public boolean remove(double d, byte by) {
        byte by2 = this.get(d);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, byte by, byte by2) {
        byte by3 = this.get(d);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, by2);
        return false;
    }

    @Override
    default public byte replace(double d, byte by) {
        return this.containsKey(d) ? this.put(d, by) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        byte by = this.get(d);
        if (by != this.defaultReturnValue() || this.containsKey(d)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(doubleToIntFunction.applyAsInt(d));
        this.put(d, by2);
        return by2;
    }

    default public byte computeIfAbsentNullable(double d, DoubleFunction<? extends Byte> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        byte by = this.get(d);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(d)) {
            return by;
        }
        Byte by3 = doubleFunction.apply(d);
        if (by3 == null) {
            return by2;
        }
        byte by4 = by3;
        this.put(d, by4);
        return by4;
    }

    default public byte computeIfAbsentPartial(double d, Double2ByteFunction double2ByteFunction) {
        Objects.requireNonNull(double2ByteFunction);
        byte by = this.get(d);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(d)) {
            return by;
        }
        if (!double2ByteFunction.containsKey(d)) {
            return by2;
        }
        byte by3 = double2ByteFunction.get(d);
        this.put(d, by3);
        return by3;
    }

    @Override
    default public byte computeIfPresent(double d, BiFunction<? super Double, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(d);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(d)) {
            return by2;
        }
        Byte by3 = biFunction.apply((Double)d, (Byte)by);
        if (by3 == null) {
            this.remove(d);
            return by2;
        }
        byte by4 = by3;
        this.put(d, by4);
        return by4;
    }

    @Override
    default public byte compute(double d, BiFunction<? super Double, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(d);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(d);
        Byte by3 = biFunction.apply((Double)d, bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.remove(d);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(d, by4);
        return by4;
    }

    @Override
    default public byte merge(double d, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.get(d);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(d)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.remove(d);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(d, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Double d, Byte by) {
        return Map.super.putIfAbsent(d, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Byte by, Byte by2) {
        return Map.super.replace(d, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(Double d, Byte by) {
        return Map.super.replace(d, by);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Double d, Function<? super Double, ? extends Byte> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Double d, BiFunction<? super Double, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Double d, BiFunction<? super Double, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Double d, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(d, by, biFunction);
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
        return this.put((Double)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Byte)object2);
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
    extends Map.Entry<Double, Byte> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
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

