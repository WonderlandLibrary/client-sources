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
package com.viaversion.viaversion.libs.fastutil.objects;

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
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Object2IntFunction<K>
extends Function<K, Integer>,
ToIntFunction<K> {
    @Override
    default public int applyAsInt(K k) {
        return this.getInt(k);
    }

    @Override
    default public int put(K k, int n) {
        throw new UnsupportedOperationException();
    }

    public int getInt(Object var1);

    @Override
    default public int getOrDefault(Object object, int n) {
        int n2 = this.getInt(object);
        return n2 != this.defaultReturnValue() || this.containsKey(object) ? n2 : n;
    }

    default public int removeInt(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(K k, Integer n) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        int n2 = this.put(k2, (int)n);
        return bl ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        Object object2 = object;
        int n = this.getInt(object2);
        return n != this.defaultReturnValue() || this.containsKey(object2) ? Integer.valueOf(n) : null;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        Object object2 = object;
        int n2 = this.getInt(object2);
        return n2 != this.defaultReturnValue() || this.containsKey(object2) ? Integer.valueOf(n2) : n;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Integer.valueOf(this.removeInt(object2)) : null;
    }

    default public void defaultReturnValue(int n) {
        throw new UnsupportedOperationException();
    }

    default public int defaultReturnValue() {
        return 1;
    }

    @Override
    @Deprecated
    default public <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return Function.super.andThen(function);
    }

    default public Object2ByteFunction<K> andThenByte(Int2ByteFunction int2ByteFunction) {
        return arg_0 -> this.lambda$andThenByte$0(int2ByteFunction, arg_0);
    }

    default public Byte2IntFunction composeByte(Byte2ObjectFunction<K> byte2ObjectFunction) {
        return arg_0 -> this.lambda$composeByte$1(byte2ObjectFunction, arg_0);
    }

    default public Object2ShortFunction<K> andThenShort(Int2ShortFunction int2ShortFunction) {
        return arg_0 -> this.lambda$andThenShort$2(int2ShortFunction, arg_0);
    }

    default public Short2IntFunction composeShort(Short2ObjectFunction<K> short2ObjectFunction) {
        return arg_0 -> this.lambda$composeShort$3(short2ObjectFunction, arg_0);
    }

    default public Object2IntFunction<K> andThenInt(Int2IntFunction int2IntFunction) {
        return arg_0 -> this.lambda$andThenInt$4(int2IntFunction, arg_0);
    }

    default public Int2IntFunction composeInt(Int2ObjectFunction<K> int2ObjectFunction) {
        return arg_0 -> this.lambda$composeInt$5(int2ObjectFunction, arg_0);
    }

    default public Object2LongFunction<K> andThenLong(Int2LongFunction int2LongFunction) {
        return arg_0 -> this.lambda$andThenLong$6(int2LongFunction, arg_0);
    }

    default public Long2IntFunction composeLong(Long2ObjectFunction<K> long2ObjectFunction) {
        return arg_0 -> this.lambda$composeLong$7(long2ObjectFunction, arg_0);
    }

    default public Object2CharFunction<K> andThenChar(Int2CharFunction int2CharFunction) {
        return arg_0 -> this.lambda$andThenChar$8(int2CharFunction, arg_0);
    }

    default public Char2IntFunction composeChar(Char2ObjectFunction<K> char2ObjectFunction) {
        return arg_0 -> this.lambda$composeChar$9(char2ObjectFunction, arg_0);
    }

    default public Object2FloatFunction<K> andThenFloat(Int2FloatFunction int2FloatFunction) {
        return arg_0 -> this.lambda$andThenFloat$10(int2FloatFunction, arg_0);
    }

    default public Float2IntFunction composeFloat(Float2ObjectFunction<K> float2ObjectFunction) {
        return arg_0 -> this.lambda$composeFloat$11(float2ObjectFunction, arg_0);
    }

    default public Object2DoubleFunction<K> andThenDouble(Int2DoubleFunction int2DoubleFunction) {
        return arg_0 -> this.lambda$andThenDouble$12(int2DoubleFunction, arg_0);
    }

    default public Double2IntFunction composeDouble(Double2ObjectFunction<K> double2ObjectFunction) {
        return arg_0 -> this.lambda$composeDouble$13(double2ObjectFunction, arg_0);
    }

    default public <T> Object2ObjectFunction<K, T> andThenObject(Int2ObjectFunction<? extends T> int2ObjectFunction) {
        return arg_0 -> this.lambda$andThenObject$14(int2ObjectFunction, arg_0);
    }

    default public <T> Object2IntFunction<T> composeObject(Object2ObjectFunction<? super T, ? extends K> object2ObjectFunction) {
        return arg_0 -> this.lambda$composeObject$15(object2ObjectFunction, arg_0);
    }

    default public <T> Object2ReferenceFunction<K, T> andThenReference(Int2ReferenceFunction<? extends T> int2ReferenceFunction) {
        return arg_0 -> this.lambda$andThenReference$16(int2ReferenceFunction, arg_0);
    }

    default public <T> Reference2IntFunction<T> composeReference(Reference2ObjectFunction<? super T, ? extends K> reference2ObjectFunction) {
        return arg_0 -> this.lambda$composeReference$17(reference2ObjectFunction, arg_0);
    }

    @Override
    @Deprecated
    default public Object remove(Object object) {
        return this.remove(object);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((K)object, (Integer)object2);
    }

    private int lambda$composeReference$17(Reference2ObjectFunction reference2ObjectFunction, Object object) {
        return this.getInt(reference2ObjectFunction.get(object));
    }

    private Object lambda$andThenReference$16(Int2ReferenceFunction int2ReferenceFunction, Object object) {
        return int2ReferenceFunction.get(this.getInt(object));
    }

    private int lambda$composeObject$15(Object2ObjectFunction object2ObjectFunction, Object object) {
        return this.getInt(object2ObjectFunction.get(object));
    }

    private Object lambda$andThenObject$14(Int2ObjectFunction int2ObjectFunction, Object object) {
        return int2ObjectFunction.get(this.getInt(object));
    }

    private int lambda$composeDouble$13(Double2ObjectFunction double2ObjectFunction, double d) {
        return this.getInt(double2ObjectFunction.get(d));
    }

    private double lambda$andThenDouble$12(Int2DoubleFunction int2DoubleFunction, Object object) {
        return int2DoubleFunction.get(this.getInt(object));
    }

    private int lambda$composeFloat$11(Float2ObjectFunction float2ObjectFunction, float f) {
        return this.getInt(float2ObjectFunction.get(f));
    }

    private float lambda$andThenFloat$10(Int2FloatFunction int2FloatFunction, Object object) {
        return int2FloatFunction.get(this.getInt(object));
    }

    private int lambda$composeChar$9(Char2ObjectFunction char2ObjectFunction, char c) {
        return this.getInt(char2ObjectFunction.get(c));
    }

    private char lambda$andThenChar$8(Int2CharFunction int2CharFunction, Object object) {
        return int2CharFunction.get(this.getInt(object));
    }

    private int lambda$composeLong$7(Long2ObjectFunction long2ObjectFunction, long l) {
        return this.getInt(long2ObjectFunction.get(l));
    }

    private long lambda$andThenLong$6(Int2LongFunction int2LongFunction, Object object) {
        return int2LongFunction.get(this.getInt(object));
    }

    private int lambda$composeInt$5(Int2ObjectFunction int2ObjectFunction, int n) {
        return this.getInt(int2ObjectFunction.get(n));
    }

    private int lambda$andThenInt$4(Int2IntFunction int2IntFunction, Object object) {
        return int2IntFunction.get(this.getInt(object));
    }

    private int lambda$composeShort$3(Short2ObjectFunction short2ObjectFunction, short s) {
        return this.getInt(short2ObjectFunction.get(s));
    }

    private short lambda$andThenShort$2(Int2ShortFunction int2ShortFunction, Object object) {
        return int2ShortFunction.get(this.getInt(object));
    }

    private int lambda$composeByte$1(Byte2ObjectFunction byte2ObjectFunction, byte by) {
        return this.getInt(byte2ObjectFunction.get(by));
    }

    private byte lambda$andThenByte$0(Int2ByteFunction int2ByteFunction, Object object) {
        return int2ByteFunction.get(this.getInt(object));
    }
}

