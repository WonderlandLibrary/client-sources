/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class DataFixUtils {
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};

    private DataFixUtils() {
    }

    public static int smallestEncompassingPowerOfTwo(int n) {
        int n2 = n - 1;
        n2 |= n2 >> 1;
        n2 |= n2 >> 2;
        n2 |= n2 >> 4;
        n2 |= n2 >> 8;
        n2 |= n2 >> 16;
        return n2 + 1;
    }

    private static boolean isPowerOfTwo(int n) {
        return n != 0 && (n & n - 1) == 0;
    }

    public static int ceillog2(int n) {
        n = DataFixUtils.isPowerOfTwo(n) ? n : DataFixUtils.smallestEncompassingPowerOfTwo(n);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)((long)n * 125613361L >> 27) & 0x1F];
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> T make(T t, Consumer<T> consumer) {
        consumer.accept(t);
        return t;
    }

    public static <U> U orElse(Optional<? extends U> optional, U u) {
        if (optional.isPresent()) {
            return optional.get();
        }
        return u;
    }

    public static <U> U orElseGet(Optional<? extends U> optional, Supplier<? extends U> supplier) {
        if (optional.isPresent()) {
            return optional.get();
        }
        return supplier.get();
    }

    public static <U> Optional<U> or(Optional<? extends U> optional, Supplier<? extends Optional<? extends U>> supplier) {
        if (optional.isPresent()) {
            return optional.map(DataFixUtils::lambda$or$0);
        }
        return supplier.get().map(DataFixUtils::lambda$or$1);
    }

    public static byte[] toArray(ByteBuffer byteBuffer) {
        byte[] byArray;
        if (byteBuffer.hasArray()) {
            byArray = byteBuffer.array();
        } else {
            byArray = new byte[byteBuffer.capacity()];
            byteBuffer.get(byArray, 0, byArray.length);
        }
        return byArray;
    }

    public static int makeKey(int n) {
        return DataFixUtils.makeKey(n, 0);
    }

    public static int makeKey(int n, int n2) {
        return n * 10 + n2;
    }

    public static int getVersion(int n) {
        return n / 10;
    }

    public static int getSubVersion(int n) {
        return n % 10;
    }

    public static <T> UnaryOperator<T> consumerToFunction(Consumer<T> consumer) {
        return arg_0 -> DataFixUtils.lambda$consumerToFunction$2(consumer, arg_0);
    }

    private static Object lambda$consumerToFunction$2(Consumer consumer, Object object) {
        consumer.accept(object);
        return object;
    }

    private static Object lambda$or$1(Object object) {
        return object;
    }

    private static Object lambda$or$0(Object object) {
        return object;
    }
}

