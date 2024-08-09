/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.Byte2ByteFunction;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
public interface Byte2ByteMap
extends Byte2ByteFunction,
Map<Byte, Byte> {
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

    public ObjectSet<Entry> byte2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Byte>> entrySet() {
        return this.byte2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Byte by, Byte by2) {
        return Byte2ByteFunction.super.put(by, by2);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Byte2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Byte2ByteFunction.super.remove(object);
    }

    public ByteSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(byte by, byte by2) {
        byte by3 = this.get(by);
        return by3 != this.defaultReturnValue() || this.containsKey(by) ? by3 : by2;
    }

    @Override
    default public byte putIfAbsent(byte by, byte by2) {
        byte by3;
        byte by4 = this.get(by);
        if (by4 != (by3 = this.defaultReturnValue()) || this.containsKey(by)) {
            return by4;
        }
        this.put(by, by2);
        return by3;
    }

    default public boolean remove(byte by, byte by2) {
        byte by3 = this.get(by);
        if (by3 != by2 || by3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, byte by2, byte by3) {
        byte by4 = this.get(by);
        if (by4 != by2 || by4 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, by3);
        return false;
    }

    @Override
    default public byte replace(byte by, byte by2) {
        return this.containsKey(by) ? this.put(by, by2) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        byte by2 = this.get(by);
        if (by2 != this.defaultReturnValue() || this.containsKey(by)) {
            return by2;
        }
        byte by3 = SafeMath.safeIntToByte(intUnaryOperator.applyAsInt(by));
        this.put(by, by3);
        return by3;
    }

    default public byte computeIfAbsentNullable(byte by, IntFunction<? extends Byte> intFunction) {
        Objects.requireNonNull(intFunction);
        byte by2 = this.get(by);
        byte by3 = this.defaultReturnValue();
        if (by2 != by3 || this.containsKey(by)) {
            return by2;
        }
        Byte by4 = intFunction.apply(by);
        if (by4 == null) {
            return by3;
        }
        byte by5 = by4;
        this.put(by, by5);
        return by5;
    }

    default public byte computeIfAbsentPartial(byte by, Byte2ByteFunction byte2ByteFunction) {
        Objects.requireNonNull(byte2ByteFunction);
        byte by2 = this.get(by);
        byte by3 = this.defaultReturnValue();
        if (by2 != by3 || this.containsKey(by)) {
            return by2;
        }
        if (!byte2ByteFunction.containsKey(by)) {
            return by3;
        }
        byte by4 = byte2ByteFunction.get(by);
        this.put(by, by4);
        return by4;
    }

    @Override
    default public byte computeIfPresent(byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by2 = this.get(by);
        byte by3 = this.defaultReturnValue();
        if (by2 == by3 && !this.containsKey(by)) {
            return by3;
        }
        Byte by4 = biFunction.apply((Byte)by, (Byte)by2);
        if (by4 == null) {
            this.remove(by);
            return by3;
        }
        byte by5 = by4;
        this.put(by, by5);
        return by5;
    }

    @Override
    default public byte compute(byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by2 = this.get(by);
        byte by3 = this.defaultReturnValue();
        boolean bl = by2 != by3 || this.containsKey(by);
        Byte by4 = biFunction.apply((Byte)by, bl ? Byte.valueOf(by2) : null);
        if (by4 == null) {
            if (bl) {
                this.remove(by);
            }
            return by3;
        }
        byte by5 = by4;
        this.put(by, by5);
        return by5;
    }

    @Override
    default public byte merge(byte by, byte by2, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by3;
        Objects.requireNonNull(biFunction);
        byte by4 = this.get(by);
        byte by5 = this.defaultReturnValue();
        if (by4 != by5 || this.containsKey(by)) {
            Byte by6 = biFunction.apply((Byte)by4, (Byte)by2);
            if (by6 == null) {
                this.remove(by);
                return by5;
            }
            by3 = by6;
        } else {
            by3 = by2;
        }
        this.put(by, by3);
        return by3;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Byte by, Byte by2) {
        return Map.super.putIfAbsent(by, by2);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Byte by2, Byte by3) {
        return Map.super.replace(by, by2, by3);
    }

    @Override
    @Deprecated
    default public Byte replace(Byte by, Byte by2) {
        return Map.super.replace(by, by2);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Byte by, Function<? super Byte, ? extends Byte> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Byte by, Byte by2, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(by, by2, biFunction);
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
        return this.put((Byte)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Byte)object2);
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
    extends Map.Entry<Byte, Byte> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

