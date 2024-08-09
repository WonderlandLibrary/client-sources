/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction
 *  com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;

@FunctionalInterface
public interface Object2ObjectFunction<K, V>
extends Function<K, V> {
    @Override
    default public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object var1);

    @Override
    default public V getOrDefault(Object object, V v) {
        V v2 = this.get(object);
        return v2 != this.defaultReturnValue() || this.containsKey(object) ? v2 : v;
    }

    @Override
    default public V remove(Object object) {
        throw new UnsupportedOperationException();
    }

    default public void defaultReturnValue(V v) {
        throw new UnsupportedOperationException();
    }

    default public V defaultReturnValue() {
        return null;
    }

    default public Object2ByteFunction<K> andThenByte(Object2ByteFunction<V> object2ByteFunction) {
        return arg_0 -> this.lambda$andThenByte$0(object2ByteFunction, arg_0);
    }

    default public Byte2ObjectFunction<V> composeByte(Byte2ObjectFunction<K> byte2ObjectFunction) {
        return arg_0 -> this.lambda$composeByte$1(byte2ObjectFunction, arg_0);
    }

    default public Object2ShortFunction<K> andThenShort(Object2ShortFunction<V> object2ShortFunction) {
        return arg_0 -> this.lambda$andThenShort$2(object2ShortFunction, arg_0);
    }

    default public Short2ObjectFunction<V> composeShort(Short2ObjectFunction<K> short2ObjectFunction) {
        return arg_0 -> this.lambda$composeShort$3(short2ObjectFunction, arg_0);
    }

    default public Object2IntFunction<K> andThenInt(Object2IntFunction<V> object2IntFunction) {
        return arg_0 -> this.lambda$andThenInt$4(object2IntFunction, arg_0);
    }

    default public Int2ObjectFunction<V> composeInt(Int2ObjectFunction<K> int2ObjectFunction) {
        return arg_0 -> this.lambda$composeInt$5(int2ObjectFunction, arg_0);
    }

    default public Object2LongFunction<K> andThenLong(Object2LongFunction<V> object2LongFunction) {
        return arg_0 -> this.lambda$andThenLong$6(object2LongFunction, arg_0);
    }

    default public Long2ObjectFunction<V> composeLong(Long2ObjectFunction<K> long2ObjectFunction) {
        return arg_0 -> this.lambda$composeLong$7(long2ObjectFunction, arg_0);
    }

    default public Object2CharFunction<K> andThenChar(Object2CharFunction<V> object2CharFunction) {
        return arg_0 -> this.lambda$andThenChar$8(object2CharFunction, arg_0);
    }

    default public Char2ObjectFunction<V> composeChar(Char2ObjectFunction<K> char2ObjectFunction) {
        return arg_0 -> this.lambda$composeChar$9(char2ObjectFunction, arg_0);
    }

    default public Object2FloatFunction<K> andThenFloat(Object2FloatFunction<V> object2FloatFunction) {
        return arg_0 -> this.lambda$andThenFloat$10(object2FloatFunction, arg_0);
    }

    default public Float2ObjectFunction<V> composeFloat(Float2ObjectFunction<K> float2ObjectFunction) {
        return arg_0 -> this.lambda$composeFloat$11(float2ObjectFunction, arg_0);
    }

    default public Object2DoubleFunction<K> andThenDouble(Object2DoubleFunction<V> object2DoubleFunction) {
        return arg_0 -> this.lambda$andThenDouble$12(object2DoubleFunction, arg_0);
    }

    default public Double2ObjectFunction<V> composeDouble(Double2ObjectFunction<K> double2ObjectFunction) {
        return arg_0 -> this.lambda$composeDouble$13(double2ObjectFunction, arg_0);
    }

    default public <T> Object2ObjectFunction<K, T> andThenObject(Object2ObjectFunction<? super V, ? extends T> object2ObjectFunction) {
        return arg_0 -> this.lambda$andThenObject$14(object2ObjectFunction, arg_0);
    }

    default public <T> Object2ObjectFunction<T, V> composeObject(Object2ObjectFunction<? super T, ? extends K> object2ObjectFunction) {
        return arg_0 -> this.lambda$composeObject$15(object2ObjectFunction, arg_0);
    }

    default public <T> Object2ReferenceFunction<K, T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> object2ReferenceFunction) {
        return arg_0 -> this.lambda$andThenReference$16(object2ReferenceFunction, arg_0);
    }

    default public <T> Reference2ObjectFunction<T, V> composeReference(Reference2ObjectFunction<? super T, ? extends K> reference2ObjectFunction) {
        return arg_0 -> this.lambda$composeReference$17(reference2ObjectFunction, arg_0);
    }

    private Object lambda$composeReference$17(Reference2ObjectFunction reference2ObjectFunction, Object object) {
        return this.get(reference2ObjectFunction.get(object));
    }

    private Object lambda$andThenReference$16(Object2ReferenceFunction object2ReferenceFunction, Object object) {
        return object2ReferenceFunction.get(this.get(object));
    }

    private Object lambda$composeObject$15(Object2ObjectFunction object2ObjectFunction, Object object) {
        return this.get(object2ObjectFunction.get(object));
    }

    private Object lambda$andThenObject$14(Object2ObjectFunction object2ObjectFunction, Object object) {
        return object2ObjectFunction.get(this.get(object));
    }

    private Object lambda$composeDouble$13(Double2ObjectFunction double2ObjectFunction, double d) {
        return this.get(double2ObjectFunction.get(d));
    }

    private double lambda$andThenDouble$12(Object2DoubleFunction object2DoubleFunction, Object object) {
        return object2DoubleFunction.getDouble(this.get(object));
    }

    private Object lambda$composeFloat$11(Float2ObjectFunction float2ObjectFunction, float f) {
        return this.get(float2ObjectFunction.get(f));
    }

    private float lambda$andThenFloat$10(Object2FloatFunction object2FloatFunction, Object object) {
        return object2FloatFunction.getFloat(this.get(object));
    }

    private Object lambda$composeChar$9(Char2ObjectFunction char2ObjectFunction, char c) {
        return this.get(char2ObjectFunction.get(c));
    }

    private char lambda$andThenChar$8(Object2CharFunction object2CharFunction, Object object) {
        return object2CharFunction.getChar(this.get(object));
    }

    private Object lambda$composeLong$7(Long2ObjectFunction long2ObjectFunction, long l) {
        return this.get(long2ObjectFunction.get(l));
    }

    private long lambda$andThenLong$6(Object2LongFunction object2LongFunction, Object object) {
        return object2LongFunction.getLong(this.get(object));
    }

    private Object lambda$composeInt$5(Int2ObjectFunction int2ObjectFunction, int n) {
        return this.get(int2ObjectFunction.get(n));
    }

    private int lambda$andThenInt$4(Object2IntFunction object2IntFunction, Object object) {
        return object2IntFunction.getInt(this.get(object));
    }

    private Object lambda$composeShort$3(Short2ObjectFunction short2ObjectFunction, short s) {
        return this.get(short2ObjectFunction.get(s));
    }

    private short lambda$andThenShort$2(Object2ShortFunction object2ShortFunction, Object object) {
        return object2ShortFunction.getShort(this.get(object));
    }

    private Object lambda$composeByte$1(Byte2ObjectFunction byte2ObjectFunction, byte by) {
        return this.get(byte2ObjectFunction.get(by));
    }

    private byte lambda$andThenByte$0(Object2ByteFunction object2ByteFunction, Object object) {
        return object2ByteFunction.getByte(this.get(object));
    }
}

