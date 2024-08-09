/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction
 *  com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Int2ObjectFunction<V>
extends Function<Integer, V>,
IntFunction<V> {
    @Override
    default public V apply(int n) {
        return this.get(n);
    }

    @Override
    default public V put(int n, V v) {
        throw new UnsupportedOperationException();
    }

    public V get(int var1);

    default public V getOrDefault(int n, V v) {
        V v2 = this.get(n);
        return v2 != this.defaultReturnValue() || this.containsKey(n) ? v2 : v;
    }

    default public V remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public V put(Integer n, V v) {
        int n2 = n;
        boolean bl = this.containsKey(n2);
        V v2 = this.put(n2, v);
        return (V)(bl ? v2 : null);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        V v = this.get(n);
        return (V)(v != this.defaultReturnValue() || this.containsKey(n) ? v : null);
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        if (object == null) {
            return v;
        }
        int n = (Integer)object;
        V v2 = this.get(n);
        return v2 != this.defaultReturnValue() || this.containsKey(n) ? v2 : v;
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? (V)this.remove(n) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
    }

    default public void defaultReturnValue(V v) {
        throw new UnsupportedOperationException();
    }

    default public V defaultReturnValue() {
        return null;
    }

    @Override
    @Deprecated
    default public <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return Function.super.compose(function);
    }

    default public Int2ByteFunction andThenByte(Object2ByteFunction<V> object2ByteFunction) {
        return arg_0 -> this.lambda$andThenByte$0(object2ByteFunction, arg_0);
    }

    default public Byte2ObjectFunction<V> composeByte(Byte2IntFunction byte2IntFunction) {
        return arg_0 -> this.lambda$composeByte$1(byte2IntFunction, arg_0);
    }

    default public Int2ShortFunction andThenShort(Object2ShortFunction<V> object2ShortFunction) {
        return arg_0 -> this.lambda$andThenShort$2(object2ShortFunction, arg_0);
    }

    default public Short2ObjectFunction<V> composeShort(Short2IntFunction short2IntFunction) {
        return arg_0 -> this.lambda$composeShort$3(short2IntFunction, arg_0);
    }

    default public Int2IntFunction andThenInt(Object2IntFunction<V> object2IntFunction) {
        return arg_0 -> this.lambda$andThenInt$4(object2IntFunction, arg_0);
    }

    default public Int2ObjectFunction<V> composeInt(Int2IntFunction int2IntFunction) {
        return arg_0 -> this.lambda$composeInt$5(int2IntFunction, arg_0);
    }

    default public Int2LongFunction andThenLong(Object2LongFunction<V> object2LongFunction) {
        return arg_0 -> this.lambda$andThenLong$6(object2LongFunction, arg_0);
    }

    default public Long2ObjectFunction<V> composeLong(Long2IntFunction long2IntFunction) {
        return arg_0 -> this.lambda$composeLong$7(long2IntFunction, arg_0);
    }

    default public Int2CharFunction andThenChar(Object2CharFunction<V> object2CharFunction) {
        return arg_0 -> this.lambda$andThenChar$8(object2CharFunction, arg_0);
    }

    default public Char2ObjectFunction<V> composeChar(Char2IntFunction char2IntFunction) {
        return arg_0 -> this.lambda$composeChar$9(char2IntFunction, arg_0);
    }

    default public Int2FloatFunction andThenFloat(Object2FloatFunction<V> object2FloatFunction) {
        return arg_0 -> this.lambda$andThenFloat$10(object2FloatFunction, arg_0);
    }

    default public Float2ObjectFunction<V> composeFloat(Float2IntFunction float2IntFunction) {
        return arg_0 -> this.lambda$composeFloat$11(float2IntFunction, arg_0);
    }

    default public Int2DoubleFunction andThenDouble(Object2DoubleFunction<V> object2DoubleFunction) {
        return arg_0 -> this.lambda$andThenDouble$12(object2DoubleFunction, arg_0);
    }

    default public Double2ObjectFunction<V> composeDouble(Double2IntFunction double2IntFunction) {
        return arg_0 -> this.lambda$composeDouble$13(double2IntFunction, arg_0);
    }

    default public <T> Int2ObjectFunction<T> andThenObject(Object2ObjectFunction<? super V, ? extends T> object2ObjectFunction) {
        return arg_0 -> this.lambda$andThenObject$14(object2ObjectFunction, arg_0);
    }

    default public <T> Object2ObjectFunction<T, V> composeObject(Object2IntFunction<? super T> object2IntFunction) {
        return arg_0 -> this.lambda$composeObject$15(object2IntFunction, arg_0);
    }

    default public <T> Int2ReferenceFunction<T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> object2ReferenceFunction) {
        return arg_0 -> this.lambda$andThenReference$16(object2ReferenceFunction, arg_0);
    }

    default public <T> Reference2ObjectFunction<T, V> composeReference(Reference2IntFunction<? super T> reference2IntFunction) {
        return arg_0 -> this.lambda$composeReference$17(reference2IntFunction, arg_0);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Integer)object, (V)object2);
    }

    private Object lambda$composeReference$17(Reference2IntFunction reference2IntFunction, Object object) {
        return this.get(reference2IntFunction.getInt(object));
    }

    private Object lambda$andThenReference$16(Object2ReferenceFunction object2ReferenceFunction, int n) {
        return object2ReferenceFunction.get(this.get(n));
    }

    private Object lambda$composeObject$15(Object2IntFunction object2IntFunction, Object object) {
        return this.get(object2IntFunction.getInt(object));
    }

    private Object lambda$andThenObject$14(Object2ObjectFunction object2ObjectFunction, int n) {
        return object2ObjectFunction.get(this.get(n));
    }

    private Object lambda$composeDouble$13(Double2IntFunction double2IntFunction, double d) {
        return this.get(double2IntFunction.get(d));
    }

    private double lambda$andThenDouble$12(Object2DoubleFunction object2DoubleFunction, int n) {
        return object2DoubleFunction.getDouble(this.get(n));
    }

    private Object lambda$composeFloat$11(Float2IntFunction float2IntFunction, float f) {
        return this.get(float2IntFunction.get(f));
    }

    private float lambda$andThenFloat$10(Object2FloatFunction object2FloatFunction, int n) {
        return object2FloatFunction.getFloat(this.get(n));
    }

    private Object lambda$composeChar$9(Char2IntFunction char2IntFunction, char c) {
        return this.get(char2IntFunction.get(c));
    }

    private char lambda$andThenChar$8(Object2CharFunction object2CharFunction, int n) {
        return object2CharFunction.getChar(this.get(n));
    }

    private Object lambda$composeLong$7(Long2IntFunction long2IntFunction, long l) {
        return this.get(long2IntFunction.get(l));
    }

    private long lambda$andThenLong$6(Object2LongFunction object2LongFunction, int n) {
        return object2LongFunction.getLong(this.get(n));
    }

    private Object lambda$composeInt$5(Int2IntFunction int2IntFunction, int n) {
        return this.get(int2IntFunction.get(n));
    }

    private int lambda$andThenInt$4(Object2IntFunction object2IntFunction, int n) {
        return object2IntFunction.getInt(this.get(n));
    }

    private Object lambda$composeShort$3(Short2IntFunction short2IntFunction, short s) {
        return this.get(short2IntFunction.get(s));
    }

    private short lambda$andThenShort$2(Object2ShortFunction object2ShortFunction, int n) {
        return object2ShortFunction.getShort(this.get(n));
    }

    private Object lambda$composeByte$1(Byte2IntFunction byte2IntFunction, byte by) {
        return this.get(byte2IntFunction.get(by));
    }

    private byte lambda$andThenByte$0(Object2ByteFunction object2ByteFunction, int n) {
        return object2ByteFunction.getByte(this.get(n));
    }
}

