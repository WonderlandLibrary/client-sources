/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction
 *  com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction
 *  com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction
 *  com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Int2IntFunction
extends Function<Integer, Integer>,
IntUnaryOperator {
    @Override
    default public int applyAsInt(int n) {
        return this.get(n);
    }

    @Override
    default public int put(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    public int get(int var1);

    default public int getOrDefault(int n, int n2) {
        int n3 = this.get(n);
        return n3 != this.defaultReturnValue() || this.containsKey(n) ? n3 : n2;
    }

    default public int remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Integer put(Integer n, Integer n2) {
        int n3 = n;
        boolean bl = this.containsKey(n3);
        int n4 = this.put(n3, (int)n2);
        return bl ? Integer.valueOf(n4) : null;
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        int n2 = this.get(n);
        return n2 != this.defaultReturnValue() || this.containsKey(n) ? Integer.valueOf(n2) : null;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        if (object == null) {
            return n;
        }
        int n2 = (Integer)object;
        int n3 = this.get(n2);
        return n3 != this.defaultReturnValue() || this.containsKey(n2) ? Integer.valueOf(n3) : n;
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        if (object == null) {
            return null;
        }
        int n = (Integer)object;
        return this.containsKey(n) ? Integer.valueOf(this.remove(n)) : null;
    }

    default public boolean containsKey(int n) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Integer)object);
    }

    default public void defaultReturnValue(int n) {
        throw new UnsupportedOperationException();
    }

    default public int defaultReturnValue() {
        return 1;
    }

    public static Int2IntFunction identity() {
        return Int2IntFunction::lambda$identity$0;
    }

    @Override
    @Deprecated
    default public <T> java.util.function.Function<T, Integer> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return Function.super.compose(function);
    }

    @Override
    @Deprecated
    default public <T> java.util.function.Function<Integer, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return Function.super.andThen(function);
    }

    default public Int2ByteFunction andThenByte(Int2ByteFunction int2ByteFunction) {
        return arg_0 -> this.lambda$andThenByte$1(int2ByteFunction, arg_0);
    }

    default public Byte2IntFunction composeByte(Byte2IntFunction byte2IntFunction) {
        return arg_0 -> this.lambda$composeByte$2(byte2IntFunction, arg_0);
    }

    default public Int2ShortFunction andThenShort(Int2ShortFunction int2ShortFunction) {
        return arg_0 -> this.lambda$andThenShort$3(int2ShortFunction, arg_0);
    }

    default public Short2IntFunction composeShort(Short2IntFunction short2IntFunction) {
        return arg_0 -> this.lambda$composeShort$4(short2IntFunction, arg_0);
    }

    default public Int2IntFunction andThenInt(Int2IntFunction int2IntFunction) {
        return arg_0 -> this.lambda$andThenInt$5(int2IntFunction, arg_0);
    }

    default public Int2IntFunction composeInt(Int2IntFunction int2IntFunction) {
        return arg_0 -> this.lambda$composeInt$6(int2IntFunction, arg_0);
    }

    default public Int2LongFunction andThenLong(Int2LongFunction int2LongFunction) {
        return arg_0 -> this.lambda$andThenLong$7(int2LongFunction, arg_0);
    }

    default public Long2IntFunction composeLong(Long2IntFunction long2IntFunction) {
        return arg_0 -> this.lambda$composeLong$8(long2IntFunction, arg_0);
    }

    default public Int2CharFunction andThenChar(Int2CharFunction int2CharFunction) {
        return arg_0 -> this.lambda$andThenChar$9(int2CharFunction, arg_0);
    }

    default public Char2IntFunction composeChar(Char2IntFunction char2IntFunction) {
        return arg_0 -> this.lambda$composeChar$10(char2IntFunction, arg_0);
    }

    default public Int2FloatFunction andThenFloat(Int2FloatFunction int2FloatFunction) {
        return arg_0 -> this.lambda$andThenFloat$11(int2FloatFunction, arg_0);
    }

    default public Float2IntFunction composeFloat(Float2IntFunction float2IntFunction) {
        return arg_0 -> this.lambda$composeFloat$12(float2IntFunction, arg_0);
    }

    default public Int2DoubleFunction andThenDouble(Int2DoubleFunction int2DoubleFunction) {
        return arg_0 -> this.lambda$andThenDouble$13(int2DoubleFunction, arg_0);
    }

    default public Double2IntFunction composeDouble(Double2IntFunction double2IntFunction) {
        return arg_0 -> this.lambda$composeDouble$14(double2IntFunction, arg_0);
    }

    default public <T> Int2ObjectFunction<T> andThenObject(Int2ObjectFunction<? extends T> int2ObjectFunction) {
        return arg_0 -> this.lambda$andThenObject$15(int2ObjectFunction, arg_0);
    }

    default public <T> Object2IntFunction<T> composeObject(Object2IntFunction<? super T> object2IntFunction) {
        return arg_0 -> this.lambda$composeObject$16(object2IntFunction, arg_0);
    }

    default public <T> Int2ReferenceFunction<T> andThenReference(Int2ReferenceFunction<? extends T> int2ReferenceFunction) {
        return arg_0 -> this.lambda$andThenReference$17(int2ReferenceFunction, arg_0);
    }

    default public <T> Reference2IntFunction<T> composeReference(Reference2IntFunction<? super T> reference2IntFunction) {
        return arg_0 -> this.lambda$composeReference$18(reference2IntFunction, arg_0);
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
        return this.put((Integer)object, (Integer)object2);
    }

    private int lambda$composeReference$18(Reference2IntFunction reference2IntFunction, Object object) {
        return this.get(reference2IntFunction.getInt(object));
    }

    private Object lambda$andThenReference$17(Int2ReferenceFunction int2ReferenceFunction, int n) {
        return int2ReferenceFunction.get(this.get(n));
    }

    private int lambda$composeObject$16(Object2IntFunction object2IntFunction, Object object) {
        return this.get(object2IntFunction.getInt(object));
    }

    private Object lambda$andThenObject$15(Int2ObjectFunction int2ObjectFunction, int n) {
        return int2ObjectFunction.get(this.get(n));
    }

    private int lambda$composeDouble$14(Double2IntFunction double2IntFunction, double d) {
        return this.get(double2IntFunction.get(d));
    }

    private double lambda$andThenDouble$13(Int2DoubleFunction int2DoubleFunction, int n) {
        return int2DoubleFunction.get(this.get(n));
    }

    private int lambda$composeFloat$12(Float2IntFunction float2IntFunction, float f) {
        return this.get(float2IntFunction.get(f));
    }

    private float lambda$andThenFloat$11(Int2FloatFunction int2FloatFunction, int n) {
        return int2FloatFunction.get(this.get(n));
    }

    private int lambda$composeChar$10(Char2IntFunction char2IntFunction, char c) {
        return this.get(char2IntFunction.get(c));
    }

    private char lambda$andThenChar$9(Int2CharFunction int2CharFunction, int n) {
        return int2CharFunction.get(this.get(n));
    }

    private int lambda$composeLong$8(Long2IntFunction long2IntFunction, long l) {
        return this.get(long2IntFunction.get(l));
    }

    private long lambda$andThenLong$7(Int2LongFunction int2LongFunction, int n) {
        return int2LongFunction.get(this.get(n));
    }

    private int lambda$composeInt$6(Int2IntFunction int2IntFunction, int n) {
        return this.get(int2IntFunction.get(n));
    }

    private int lambda$andThenInt$5(Int2IntFunction int2IntFunction, int n) {
        return int2IntFunction.get(this.get(n));
    }

    private int lambda$composeShort$4(Short2IntFunction short2IntFunction, short s) {
        return this.get(short2IntFunction.get(s));
    }

    private short lambda$andThenShort$3(Int2ShortFunction int2ShortFunction, int n) {
        return int2ShortFunction.get(this.get(n));
    }

    private int lambda$composeByte$2(Byte2IntFunction byte2IntFunction, byte by) {
        return this.get(byte2IntFunction.get(by));
    }

    private byte lambda$andThenByte$1(Int2ByteFunction int2ByteFunction, int n) {
        return int2ByteFunction.get(this.get(n));
    }

    private static int lambda$identity$0(int n) {
        return n;
    }
}

