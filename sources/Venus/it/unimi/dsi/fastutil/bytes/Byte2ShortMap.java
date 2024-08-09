/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.Byte2ShortFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
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
public interface Byte2ShortMap
extends Byte2ShortFunction,
Map<Byte, Short> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(short var1);

    @Override
    public short defaultReturnValue();

    public ObjectSet<Entry> byte2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Short>> entrySet() {
        return this.byte2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Byte by, Short s) {
        return Byte2ShortFunction.super.put(by, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Byte2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Byte2ShortFunction.super.remove(object);
    }

    public ByteSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(byte by, short s) {
        short s2 = this.get(by);
        return s2 != this.defaultReturnValue() || this.containsKey(by) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(byte by, short s) {
        short s2;
        short s3 = this.get(by);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return s3;
        }
        this.put(by, s);
        return s2;
    }

    default public boolean remove(byte by, short s) {
        short s2 = this.get(by);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, short s, short s2) {
        short s3 = this.get(by);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, s2);
        return false;
    }

    @Override
    default public short replace(byte by, short s) {
        return this.containsKey(by) ? this.put(by, s) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        short s = this.get(by);
        if (s != this.defaultReturnValue() || this.containsKey(by)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(by));
        this.put(by, s2);
        return s2;
    }

    default public short computeIfAbsentNullable(byte by, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        short s = this.get(by);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(by)) {
            return s;
        }
        Short s3 = intFunction.apply(by);
        if (s3 == null) {
            return s2;
        }
        short s4 = s3;
        this.put(by, s4);
        return s4;
    }

    default public short computeIfAbsentPartial(byte by, Byte2ShortFunction byte2ShortFunction) {
        Objects.requireNonNull(byte2ShortFunction);
        short s = this.get(by);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(by)) {
            return s;
        }
        if (!byte2ShortFunction.containsKey(by)) {
            return s2;
        }
        short s3 = byte2ShortFunction.get(by);
        this.put(by, s3);
        return s3;
    }

    @Override
    default public short computeIfPresent(byte by, BiFunction<? super Byte, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(by);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(by)) {
            return s2;
        }
        Short s3 = biFunction.apply((Byte)by, (Short)s);
        if (s3 == null) {
            this.remove(by);
            return s2;
        }
        short s4 = s3;
        this.put(by, s4);
        return s4;
    }

    @Override
    default public short compute(byte by, BiFunction<? super Byte, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(by);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(by);
        Short s3 = biFunction.apply((Byte)by, bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.remove(by);
            }
            return s2;
        }
        short s4 = s3;
        this.put(by, s4);
        return s4;
    }

    @Override
    default public short merge(byte by, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.get(by);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(by)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.remove(by);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(by, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Byte by, Short s) {
        return Map.super.putIfAbsent(by, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Short s, Short s2) {
        return Map.super.replace(by, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(Byte by, Short s) {
        return Map.super.replace(by, s);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Byte by, Function<? super Byte, ? extends Short> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Byte by, BiFunction<? super Byte, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Byte by, BiFunction<? super Byte, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Byte by, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(by, s, biFunction);
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
        return this.put((Byte)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Short)object2);
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
    extends Map.Entry<Byte, Short> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
        }

        public short getShortValue();

        @Override
        public short setValue(short var1);

        @Override
        @Deprecated
        default public Short getValue() {
            return this.getShortValue();
        }

        @Override
        @Deprecated
        default public Short setValue(Short s) {
            return this.setValue((short)s);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Short)object);
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

