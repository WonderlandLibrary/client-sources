/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.ints.Int2ByteFunction;
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
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Int2ByteMap
extends Int2ByteFunction,
Map<Integer, Byte> {
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

    public ObjectSet<Entry> int2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Byte>> entrySet() {
        return this.int2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Integer n, Byte by) {
        return Int2ByteFunction.super.put(n, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Int2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Int2ByteFunction.super.remove(object);
    }

    public IntSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(int n, byte by) {
        byte by2 = this.get(n);
        return by2 != this.defaultReturnValue() || this.containsKey(n) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(int n, byte by) {
        byte by2;
        byte by3 = this.get(n);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return by3;
        }
        this.put(n, by);
        return by2;
    }

    default public boolean remove(int n, byte by) {
        byte by2 = this.get(n);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, byte by, byte by2) {
        byte by3 = this.get(n);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, by2);
        return false;
    }

    @Override
    default public byte replace(int n, byte by) {
        return this.containsKey(n) ? this.put(n, by) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        byte by = this.get(n);
        if (by != this.defaultReturnValue() || this.containsKey(n)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(intUnaryOperator.applyAsInt(n));
        this.put(n, by2);
        return by2;
    }

    default public byte computeIfAbsentNullable(int n, IntFunction<? extends Byte> intFunction) {
        Objects.requireNonNull(intFunction);
        byte by = this.get(n);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(n)) {
            return by;
        }
        Byte by3 = intFunction.apply(n);
        if (by3 == null) {
            return by2;
        }
        byte by4 = by3;
        this.put(n, by4);
        return by4;
    }

    default public byte computeIfAbsentPartial(int n, Int2ByteFunction int2ByteFunction) {
        Objects.requireNonNull(int2ByteFunction);
        byte by = this.get(n);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(n)) {
            return by;
        }
        if (!int2ByteFunction.containsKey(n)) {
            return by2;
        }
        byte by3 = int2ByteFunction.get(n);
        this.put(n, by3);
        return by3;
    }

    @Override
    default public byte computeIfPresent(int n, BiFunction<? super Integer, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(n);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(n)) {
            return by2;
        }
        Byte by3 = biFunction.apply((Integer)n, (Byte)by);
        if (by3 == null) {
            this.remove(n);
            return by2;
        }
        byte by4 = by3;
        this.put(n, by4);
        return by4;
    }

    @Override
    default public byte compute(int n, BiFunction<? super Integer, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(n);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(n);
        Byte by3 = biFunction.apply((Integer)n, bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.remove(n);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(n, by4);
        return by4;
    }

    @Override
    default public byte merge(int n, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.get(n);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(n)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.remove(n);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(n, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Integer n, Byte by) {
        return Map.super.putIfAbsent(n, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Byte by, Byte by2) {
        return Map.super.replace(n, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(Integer n, Byte by) {
        return Map.super.replace(n, by);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Integer n, Function<? super Integer, ? extends Byte> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Integer n, BiFunction<? super Integer, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Integer n, BiFunction<? super Integer, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Integer n, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(n, by, biFunction);
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
        return this.put((Integer)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Byte)object2);
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
    extends Map.Entry<Integer, Byte> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

