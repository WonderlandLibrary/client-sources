/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public interface Short2ByteMap
extends Short2ByteFunction,
Map<Short, Byte> {
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

    public ObjectSet<Entry> short2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Byte>> entrySet() {
        return this.short2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Short s, Byte by) {
        return Short2ByteFunction.super.put(s, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Short2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Short2ByteFunction.super.remove(object);
    }

    public ShortSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(short s, byte by) {
        byte by2 = this.get(s);
        return by2 != this.defaultReturnValue() || this.containsKey(s) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(short s, byte by) {
        byte by2;
        byte by3 = this.get(s);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return by3;
        }
        this.put(s, by);
        return by2;
    }

    default public boolean remove(short s, byte by) {
        byte by2 = this.get(s);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, byte by, byte by2) {
        byte by3 = this.get(s);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, by2);
        return false;
    }

    @Override
    default public byte replace(short s, byte by) {
        return this.containsKey(s) ? this.put(s, by) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        byte by = this.get(s);
        if (by != this.defaultReturnValue() || this.containsKey(s)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(intUnaryOperator.applyAsInt(s));
        this.put(s, by2);
        return by2;
    }

    default public byte computeIfAbsentNullable(short s, IntFunction<? extends Byte> intFunction) {
        Objects.requireNonNull(intFunction);
        byte by = this.get(s);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(s)) {
            return by;
        }
        Byte by3 = intFunction.apply(s);
        if (by3 == null) {
            return by2;
        }
        byte by4 = by3;
        this.put(s, by4);
        return by4;
    }

    default public byte computeIfAbsentPartial(short s, Short2ByteFunction short2ByteFunction) {
        Objects.requireNonNull(short2ByteFunction);
        byte by = this.get(s);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(s)) {
            return by;
        }
        if (!short2ByteFunction.containsKey(s)) {
            return by2;
        }
        byte by3 = short2ByteFunction.get(s);
        this.put(s, by3);
        return by3;
    }

    @Override
    default public byte computeIfPresent(short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(s);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(s)) {
            return by2;
        }
        Byte by3 = biFunction.apply((Short)s, (Byte)by);
        if (by3 == null) {
            this.remove(s);
            return by2;
        }
        byte by4 = by3;
        this.put(s, by4);
        return by4;
    }

    @Override
    default public byte compute(short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(s);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(s);
        Byte by3 = biFunction.apply((Short)s, bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.remove(s);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(s, by4);
        return by4;
    }

    @Override
    default public byte merge(short s, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.get(s);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(s)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.remove(s);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(s, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Short s, Byte by) {
        return Map.super.putIfAbsent(s, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Byte by, Byte by2) {
        return Map.super.replace(s, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(Short s, Byte by) {
        return Map.super.replace(s, by);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Short s, Function<? super Short, ? extends Byte> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Short s, BiFunction<? super Short, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Short s, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(s, by, biFunction);
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
        return this.put((Short)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Byte)object2);
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
    extends Map.Entry<Short, Byte> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

