/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.Library;
import org.lwjgl.system.MathUtil;
import org.lwjgl.system.MemoryManage;
import org.lwjgl.system.MultiReleaseMemCopy;
import org.lwjgl.system.MultiReleaseTextDecoding;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.Struct;
import org.lwjgl.system.jni.JNINativeInterface;
import org.lwjgl.system.libc.LibCString;
import sun.misc.Unsafe;

public final class MemoryUtil {
    public static final long NULL = 0L;
    public static final int PAGE_SIZE;
    public static final int CACHE_LINE_SIZE;
    static final int ARRAY_TLC_SIZE;
    static final ThreadLocal<byte[]> ARRAY_TLC_BYTE;
    static final ThreadLocal<char[]> ARRAY_TLC_CHAR;
    static final Unsafe UNSAFE;
    static final ByteOrder NATIVE_ORDER;
    private static final Charset UTF16;
    static final Class<? extends ByteBuffer> BUFFER_BYTE;
    static final Class<? extends ShortBuffer> BUFFER_SHORT;
    static final Class<? extends CharBuffer> BUFFER_CHAR;
    static final Class<? extends IntBuffer> BUFFER_INT;
    static final Class<? extends LongBuffer> BUFFER_LONG;
    static final Class<? extends FloatBuffer> BUFFER_FLOAT;
    static final Class<? extends DoubleBuffer> BUFFER_DOUBLE;
    private static final long MARK;
    private static final long POSITION;
    private static final long LIMIT;
    private static final long CAPACITY;
    private static final long ADDRESS;
    private static final long PARENT_BYTE;
    private static final long PARENT_SHORT;
    private static final long PARENT_CHAR;
    private static final long PARENT_INT;
    private static final long PARENT_LONG;
    private static final long PARENT_FLOAT;
    private static final long PARENT_DOUBLE;
    private static final NativeShift SHIFT;
    private static final long FILL_PATTERN;
    private static final int MAGIC_CAPACITY = 219540062;
    private static final int MAGIC_POSITION = 16435934;

    private MemoryUtil() {
    }

    public static MemoryAllocator getAllocator() {
        return MemoryUtil.getAllocator(false);
    }

    public static MemoryAllocator getAllocator(boolean bl) {
        return bl ? LazyInit.ALLOCATOR : LazyInit.ALLOCATOR_IMPL;
    }

    public static long nmemAlloc(long l) {
        return LazyInit.ALLOCATOR.malloc(l);
    }

    public static long nmemAllocChecked(long l) {
        long l2 = MemoryUtil.nmemAlloc(l != 0L ? l : 1L);
        if (Checks.CHECKS && l2 == 0L) {
            throw new OutOfMemoryError();
        }
        return l2;
    }

    private static long getAllocationSize(int n, int n2) {
        return APIUtil.apiCheckAllocation(n, Integer.toUnsignedLong(n) << n2, Pointer.BITS64 ? Long.MAX_VALUE : 0xFFFFFFFFL);
    }

    public static ByteBuffer memAlloc(int n) {
        return MemoryUtil.wrap(BUFFER_BYTE, MemoryUtil.nmemAllocChecked(n), n).order(NATIVE_ORDER);
    }

    public static ShortBuffer memAllocShort(int n) {
        return MemoryUtil.wrap(BUFFER_SHORT, MemoryUtil.nmemAllocChecked(MemoryUtil.getAllocationSize(n, 1)), n);
    }

    public static IntBuffer memAllocInt(int n) {
        return MemoryUtil.wrap(BUFFER_INT, MemoryUtil.nmemAllocChecked(MemoryUtil.getAllocationSize(n, 2)), n);
    }

    public static FloatBuffer memAllocFloat(int n) {
        return MemoryUtil.wrap(BUFFER_FLOAT, MemoryUtil.nmemAllocChecked(MemoryUtil.getAllocationSize(n, 2)), n);
    }

    public static LongBuffer memAllocLong(int n) {
        return MemoryUtil.wrap(BUFFER_LONG, MemoryUtil.nmemAllocChecked(MemoryUtil.getAllocationSize(n, 3)), n);
    }

    public static DoubleBuffer memAllocDouble(int n) {
        return MemoryUtil.wrap(BUFFER_DOUBLE, MemoryUtil.nmemAllocChecked(MemoryUtil.getAllocationSize(n, 3)), n);
    }

    public static PointerBuffer memAllocPointer(int n) {
        return Pointer.Default.wrap(PointerBuffer.class, MemoryUtil.nmemAllocChecked(MemoryUtil.getAllocationSize(n, Pointer.POINTER_SHIFT)), n);
    }

    public static void nmemFree(long l) {
        LazyInit.ALLOCATOR.free(l);
    }

    public static void memFree(@Nullable Buffer buffer) {
        if (buffer != null) {
            MemoryUtil.nmemFree(UNSAFE.getLong((Object)buffer, ADDRESS));
        }
    }

    public static void memFree(@Nullable PointerBuffer pointerBuffer) {
        if (pointerBuffer != null) {
            MemoryUtil.nmemFree(pointerBuffer.address);
        }
    }

    public static long nmemCalloc(long l, long l2) {
        return LazyInit.ALLOCATOR.calloc(l, l2);
    }

    public static long nmemCallocChecked(long l, long l2) {
        if (l == 0L || l2 == 0L) {
            l = 1L;
            l2 = 1L;
        }
        long l3 = MemoryUtil.nmemCalloc(l, l2);
        if (Checks.CHECKS && l3 == 0L) {
            throw new OutOfMemoryError();
        }
        return l3;
    }

    public static ByteBuffer memCalloc(int n, int n2) {
        return MemoryUtil.wrap(BUFFER_BYTE, MemoryUtil.nmemCallocChecked(n, n2), n * n2).order(NATIVE_ORDER);
    }

    public static ByteBuffer memCalloc(int n) {
        return MemoryUtil.wrap(BUFFER_BYTE, MemoryUtil.nmemCallocChecked(n, 1L), n).order(NATIVE_ORDER);
    }

    public static ShortBuffer memCallocShort(int n) {
        return MemoryUtil.wrap(BUFFER_SHORT, MemoryUtil.nmemCallocChecked(n, 2L), n);
    }

    public static IntBuffer memCallocInt(int n) {
        return MemoryUtil.wrap(BUFFER_INT, MemoryUtil.nmemCallocChecked(n, 4L), n);
    }

    public static FloatBuffer memCallocFloat(int n) {
        return MemoryUtil.wrap(BUFFER_FLOAT, MemoryUtil.nmemCallocChecked(n, 4L), n);
    }

    public static LongBuffer memCallocLong(int n) {
        return MemoryUtil.wrap(BUFFER_LONG, MemoryUtil.nmemCallocChecked(n, 8L), n);
    }

    public static DoubleBuffer memCallocDouble(int n) {
        return MemoryUtil.wrap(BUFFER_DOUBLE, MemoryUtil.nmemCallocChecked(n, 8L), n);
    }

    public static PointerBuffer memCallocPointer(int n) {
        return Pointer.Default.wrap(PointerBuffer.class, MemoryUtil.nmemCallocChecked(n, Pointer.POINTER_SIZE), n);
    }

    public static long nmemRealloc(long l, long l2) {
        return LazyInit.ALLOCATOR.realloc(l, l2);
    }

    public static long nmemReallocChecked(long l, long l2) {
        long l3 = MemoryUtil.nmemRealloc(l, l2 != 0L ? l2 : 1L);
        if (Checks.CHECKS && l3 == 0L) {
            throw new OutOfMemoryError();
        }
        return l3;
    }

    private static <T extends Buffer> T realloc(@Nullable T t, T t2, int n) {
        if (t != null) {
            t2.position(Math.min(t.position(), n));
        }
        return t2;
    }

    public static ByteBuffer memRealloc(@Nullable ByteBuffer byteBuffer, int n) {
        return MemoryUtil.realloc(byteBuffer, MemoryUtil.memByteBuffer(MemoryUtil.nmemReallocChecked(byteBuffer == null ? 0L : UNSAFE.getLong((Object)byteBuffer, ADDRESS), n), n), n);
    }

    public static ShortBuffer memRealloc(@Nullable ShortBuffer shortBuffer, int n) {
        return MemoryUtil.realloc(shortBuffer, MemoryUtil.memShortBuffer(MemoryUtil.nmemReallocChecked(shortBuffer == null ? 0L : UNSAFE.getLong((Object)shortBuffer, ADDRESS), MemoryUtil.getAllocationSize(n, 1)), n), n);
    }

    public static IntBuffer memRealloc(@Nullable IntBuffer intBuffer, int n) {
        return MemoryUtil.realloc(intBuffer, MemoryUtil.memIntBuffer(MemoryUtil.nmemReallocChecked(intBuffer == null ? 0L : UNSAFE.getLong((Object)intBuffer, ADDRESS), MemoryUtil.getAllocationSize(n, 2)), n), n);
    }

    public static LongBuffer memRealloc(@Nullable LongBuffer longBuffer, int n) {
        return MemoryUtil.realloc(longBuffer, MemoryUtil.memLongBuffer(MemoryUtil.nmemReallocChecked(longBuffer == null ? 0L : UNSAFE.getLong((Object)longBuffer, ADDRESS), MemoryUtil.getAllocationSize(n, 3)), n), n);
    }

    public static FloatBuffer memRealloc(@Nullable FloatBuffer floatBuffer, int n) {
        return MemoryUtil.realloc(floatBuffer, MemoryUtil.memFloatBuffer(MemoryUtil.nmemReallocChecked(floatBuffer == null ? 0L : UNSAFE.getLong((Object)floatBuffer, ADDRESS), MemoryUtil.getAllocationSize(n, 2)), n), n);
    }

    public static DoubleBuffer memRealloc(@Nullable DoubleBuffer doubleBuffer, int n) {
        return MemoryUtil.realloc(doubleBuffer, MemoryUtil.memDoubleBuffer(MemoryUtil.nmemReallocChecked(doubleBuffer == null ? 0L : UNSAFE.getLong((Object)doubleBuffer, ADDRESS), MemoryUtil.getAllocationSize(n, 3)), n), n);
    }

    public static PointerBuffer memRealloc(@Nullable PointerBuffer pointerBuffer, int n) {
        PointerBuffer pointerBuffer2 = MemoryUtil.memPointerBuffer(MemoryUtil.nmemReallocChecked(pointerBuffer == null ? 0L : pointerBuffer.address, MemoryUtil.getAllocationSize(n, Pointer.POINTER_SHIFT)), n);
        if (pointerBuffer != null) {
            pointerBuffer2.position(Math.min(pointerBuffer.position(), n));
        }
        return pointerBuffer2;
    }

    public static long nmemAlignedAlloc(long l, long l2) {
        return LazyInit.ALLOCATOR.aligned_alloc(l, l2);
    }

    public static long nmemAlignedAllocChecked(long l, long l2) {
        long l3 = MemoryUtil.nmemAlignedAlloc(l, l2 != 0L ? l2 : 1L);
        if (Checks.CHECKS && l3 == 0L) {
            throw new OutOfMemoryError();
        }
        return l3;
    }

    public static ByteBuffer memAlignedAlloc(int n, int n2) {
        return MemoryUtil.wrap(BUFFER_BYTE, MemoryUtil.nmemAlignedAllocChecked(n, n2), n2).order(NATIVE_ORDER);
    }

    public static void nmemAlignedFree(long l) {
        LazyInit.ALLOCATOR.aligned_free(l);
    }

    public static void memAlignedFree(@Nullable ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            MemoryUtil.nmemAlignedFree(UNSAFE.getLong((Object)byteBuffer, ADDRESS));
        }
    }

    public static void memReport(MemoryAllocationReport memoryAllocationReport) {
        MemoryManage.DebugAllocator.report(memoryAllocationReport);
    }

    public static void memReport(MemoryAllocationReport memoryAllocationReport, MemoryAllocationReport.Aggregate aggregate, boolean bl) {
        MemoryManage.DebugAllocator.report(memoryAllocationReport, aggregate, bl);
    }

    public static long memAddress0(Buffer buffer) {
        return UNSAFE.getLong((Object)buffer, ADDRESS);
    }

    public static long memAddress(ByteBuffer byteBuffer) {
        return (long)byteBuffer.position() + MemoryUtil.memAddress0(byteBuffer);
    }

    public static long memAddress(ByteBuffer byteBuffer, int n) {
        Objects.requireNonNull(byteBuffer);
        return MemoryUtil.memAddress0(byteBuffer) + Integer.toUnsignedLong(n);
    }

    private static long address(int n, int n2, long l) {
        return l + (((long)n & 0xFFFFFFFFL) << n2);
    }

    public static long memAddress(ShortBuffer shortBuffer) {
        return MemoryUtil.address(shortBuffer.position(), 1, MemoryUtil.memAddress0(shortBuffer));
    }

    public static long memAddress(ShortBuffer shortBuffer, int n) {
        Objects.requireNonNull(shortBuffer);
        return MemoryUtil.address(n, 1, MemoryUtil.memAddress0(shortBuffer));
    }

    public static long memAddress(CharBuffer charBuffer) {
        return MemoryUtil.address(charBuffer.position(), 1, MemoryUtil.memAddress0(charBuffer));
    }

    public static long memAddress(CharBuffer charBuffer, int n) {
        Objects.requireNonNull(charBuffer);
        return MemoryUtil.address(n, 1, MemoryUtil.memAddress0(charBuffer));
    }

    public static long memAddress(IntBuffer intBuffer) {
        return MemoryUtil.address(intBuffer.position(), 2, MemoryUtil.memAddress0(intBuffer));
    }

    public static long memAddress(IntBuffer intBuffer, int n) {
        Objects.requireNonNull(intBuffer);
        return MemoryUtil.address(n, 2, MemoryUtil.memAddress0(intBuffer));
    }

    public static long memAddress(FloatBuffer floatBuffer) {
        return MemoryUtil.address(floatBuffer.position(), 2, MemoryUtil.memAddress0(floatBuffer));
    }

    public static long memAddress(FloatBuffer floatBuffer, int n) {
        Objects.requireNonNull(floatBuffer);
        return MemoryUtil.address(n, 2, MemoryUtil.memAddress0(floatBuffer));
    }

    public static long memAddress(LongBuffer longBuffer) {
        return MemoryUtil.address(longBuffer.position(), 3, MemoryUtil.memAddress0(longBuffer));
    }

    public static long memAddress(LongBuffer longBuffer, int n) {
        Objects.requireNonNull(longBuffer);
        return MemoryUtil.address(n, 3, MemoryUtil.memAddress0(longBuffer));
    }

    public static long memAddress(DoubleBuffer doubleBuffer) {
        return MemoryUtil.address(doubleBuffer.position(), 3, MemoryUtil.memAddress0(doubleBuffer));
    }

    public static long memAddress(DoubleBuffer doubleBuffer, int n) {
        Objects.requireNonNull(doubleBuffer);
        return MemoryUtil.address(n, 3, MemoryUtil.memAddress0(doubleBuffer));
    }

    public static long memAddress(Buffer buffer) {
        int n = buffer instanceof ByteBuffer ? 0 : (buffer instanceof ShortBuffer || buffer instanceof CharBuffer ? 1 : (buffer instanceof IntBuffer || buffer instanceof FloatBuffer ? 2 : 3));
        return MemoryUtil.address(buffer.position(), n, MemoryUtil.memAddress0(buffer));
    }

    public static long memAddress(CustomBuffer<?> customBuffer) {
        return customBuffer.address();
    }

    public static long memAddress(CustomBuffer<?> customBuffer, int n) {
        return customBuffer.address(n);
    }

    public static long memAddressSafe(@Nullable ByteBuffer byteBuffer) {
        return byteBuffer == null ? 0L : MemoryUtil.memAddress0(byteBuffer) + (long)byteBuffer.position();
    }

    public static long memAddressSafe(@Nullable ShortBuffer shortBuffer) {
        return shortBuffer == null ? 0L : MemoryUtil.address(shortBuffer.position(), 1, MemoryUtil.memAddress0(shortBuffer));
    }

    public static long memAddressSafe(@Nullable CharBuffer charBuffer) {
        return charBuffer == null ? 0L : MemoryUtil.address(charBuffer.position(), 1, MemoryUtil.memAddress0(charBuffer));
    }

    public static long memAddressSafe(@Nullable IntBuffer intBuffer) {
        return intBuffer == null ? 0L : MemoryUtil.address(intBuffer.position(), 2, MemoryUtil.memAddress0(intBuffer));
    }

    public static long memAddressSafe(@Nullable FloatBuffer floatBuffer) {
        return floatBuffer == null ? 0L : MemoryUtil.address(floatBuffer.position(), 2, MemoryUtil.memAddress0(floatBuffer));
    }

    public static long memAddressSafe(@Nullable LongBuffer longBuffer) {
        return longBuffer == null ? 0L : MemoryUtil.address(longBuffer.position(), 3, MemoryUtil.memAddress0(longBuffer));
    }

    public static long memAddressSafe(@Nullable DoubleBuffer doubleBuffer) {
        return doubleBuffer == null ? 0L : MemoryUtil.address(doubleBuffer.position(), 3, MemoryUtil.memAddress0(doubleBuffer));
    }

    public static long memAddressSafe(@Nullable Pointer pointer) {
        return pointer == null ? 0L : pointer.address();
    }

    public static ByteBuffer memByteBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_BYTE, l, n).order(NATIVE_ORDER);
    }

    @Nullable
    public static ByteBuffer memByteBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_BYTE, l, n).order(NATIVE_ORDER);
    }

    public static ShortBuffer memShortBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_SHORT, l, n);
    }

    @Nullable
    public static ShortBuffer memShortBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_SHORT, l, n);
    }

    public static CharBuffer memCharBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_CHAR, l, n);
    }

    @Nullable
    public static CharBuffer memCharBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_CHAR, l, n);
    }

    public static IntBuffer memIntBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_INT, l, n);
    }

    @Nullable
    public static IntBuffer memIntBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_INT, l, n);
    }

    public static LongBuffer memLongBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_LONG, l, n);
    }

    @Nullable
    public static LongBuffer memLongBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_LONG, l, n);
    }

    public static FloatBuffer memFloatBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_FLOAT, l, n);
    }

    @Nullable
    public static FloatBuffer memFloatBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_FLOAT, l, n);
    }

    public static DoubleBuffer memDoubleBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return MemoryUtil.wrap(BUFFER_DOUBLE, l, n);
    }

    @Nullable
    public static DoubleBuffer memDoubleBufferSafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.wrap(BUFFER_DOUBLE, l, n);
    }

    public static PointerBuffer memPointerBuffer(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return Pointer.Default.wrap(PointerBuffer.class, l, n);
    }

    @Nullable
    public static PointerBuffer memPointerBufferSafe(long l, int n) {
        return l == 0L ? null : Pointer.Default.wrap(PointerBuffer.class, l, n);
    }

    public static ByteBuffer memDuplicate(ByteBuffer byteBuffer) {
        return MemoryUtil.duplicate(BUFFER_BYTE, byteBuffer, PARENT_BYTE).order(byteBuffer.order());
    }

    public static ShortBuffer memDuplicate(ShortBuffer shortBuffer) {
        return MemoryUtil.duplicate(BUFFER_SHORT, shortBuffer, PARENT_SHORT);
    }

    public static CharBuffer memDuplicate(CharBuffer charBuffer) {
        return MemoryUtil.duplicate(BUFFER_CHAR, charBuffer, PARENT_CHAR);
    }

    public static IntBuffer memDuplicate(IntBuffer intBuffer) {
        return MemoryUtil.duplicate(BUFFER_INT, intBuffer, PARENT_INT);
    }

    public static LongBuffer memDuplicate(LongBuffer longBuffer) {
        return MemoryUtil.duplicate(BUFFER_LONG, longBuffer, PARENT_LONG);
    }

    public static FloatBuffer memDuplicate(FloatBuffer floatBuffer) {
        return MemoryUtil.duplicate(BUFFER_FLOAT, floatBuffer, PARENT_FLOAT);
    }

    public static DoubleBuffer memDuplicate(DoubleBuffer doubleBuffer) {
        return MemoryUtil.duplicate(BUFFER_DOUBLE, doubleBuffer, PARENT_DOUBLE);
    }

    public static ByteBuffer memSlice(ByteBuffer byteBuffer) {
        return MemoryUtil.slice(BUFFER_BYTE, byteBuffer, MemoryUtil.memAddress0(byteBuffer) + (long)byteBuffer.position(), byteBuffer.remaining(), PARENT_BYTE).order(byteBuffer.order());
    }

    public static ShortBuffer memSlice(ShortBuffer shortBuffer) {
        return MemoryUtil.slice(BUFFER_SHORT, shortBuffer, MemoryUtil.address(shortBuffer.position(), 1, MemoryUtil.memAddress0(shortBuffer)), shortBuffer.remaining(), PARENT_SHORT);
    }

    public static CharBuffer memSlice(CharBuffer charBuffer) {
        return MemoryUtil.slice(BUFFER_CHAR, charBuffer, MemoryUtil.address(charBuffer.position(), 1, MemoryUtil.memAddress0(charBuffer)), charBuffer.remaining(), PARENT_CHAR);
    }

    public static IntBuffer memSlice(IntBuffer intBuffer) {
        return MemoryUtil.slice(BUFFER_INT, intBuffer, MemoryUtil.address(intBuffer.position(), 2, MemoryUtil.memAddress0(intBuffer)), intBuffer.remaining(), PARENT_INT);
    }

    public static LongBuffer memSlice(LongBuffer longBuffer) {
        return MemoryUtil.slice(BUFFER_LONG, longBuffer, MemoryUtil.address(longBuffer.position(), 3, MemoryUtil.memAddress0(longBuffer)), longBuffer.remaining(), PARENT_LONG);
    }

    public static FloatBuffer memSlice(FloatBuffer floatBuffer) {
        return MemoryUtil.slice(BUFFER_FLOAT, floatBuffer, MemoryUtil.address(floatBuffer.position(), 2, MemoryUtil.memAddress0(floatBuffer)), floatBuffer.remaining(), PARENT_FLOAT);
    }

    public static DoubleBuffer memSlice(DoubleBuffer doubleBuffer) {
        return MemoryUtil.slice(BUFFER_DOUBLE, doubleBuffer, MemoryUtil.address(doubleBuffer.position(), 3, MemoryUtil.memAddress0(doubleBuffer)), doubleBuffer.remaining(), PARENT_DOUBLE);
    }

    public static ByteBuffer memSlice(ByteBuffer byteBuffer, int n, int n2) {
        int n3 = byteBuffer.position() + n;
        if (n < 0 || byteBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || byteBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_BYTE, byteBuffer, MemoryUtil.memAddress0(byteBuffer) + (long)n3, n2, PARENT_BYTE).order(byteBuffer.order());
    }

    public static ShortBuffer memSlice(ShortBuffer shortBuffer, int n, int n2) {
        int n3 = shortBuffer.position() + n;
        if (n < 0 || shortBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || shortBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_SHORT, shortBuffer, MemoryUtil.address(n3, 1, MemoryUtil.memAddress0(shortBuffer)), n2, PARENT_SHORT);
    }

    public static CharBuffer memSlice(CharBuffer charBuffer, int n, int n2) {
        int n3 = charBuffer.position() + n;
        if (n < 0 || charBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || charBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_CHAR, charBuffer, MemoryUtil.address(n3, 1, MemoryUtil.memAddress0(charBuffer)), n2, PARENT_CHAR);
    }

    public static IntBuffer memSlice(IntBuffer intBuffer, int n, int n2) {
        int n3 = intBuffer.position() + n;
        if (n < 0 || intBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || intBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_INT, intBuffer, MemoryUtil.address(n3, 2, MemoryUtil.memAddress0(intBuffer)), n2, PARENT_INT);
    }

    public static LongBuffer memSlice(LongBuffer longBuffer, int n, int n2) {
        int n3 = longBuffer.position() + n;
        if (n < 0 || longBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || longBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_LONG, longBuffer, MemoryUtil.address(n3, 3, MemoryUtil.memAddress0(longBuffer)), n2, PARENT_LONG);
    }

    public static FloatBuffer memSlice(FloatBuffer floatBuffer, int n, int n2) {
        int n3 = floatBuffer.position() + n;
        if (n < 0 || floatBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || floatBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_FLOAT, floatBuffer, MemoryUtil.address(n3, 2, MemoryUtil.memAddress0(floatBuffer)), n2, PARENT_FLOAT);
    }

    public static DoubleBuffer memSlice(DoubleBuffer doubleBuffer, int n, int n2) {
        int n3 = doubleBuffer.position() + n;
        if (n < 0 || doubleBuffer.limit() < n3) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || doubleBuffer.capacity() - n3 < n2) {
            throw new IllegalArgumentException();
        }
        return MemoryUtil.slice(BUFFER_DOUBLE, doubleBuffer, MemoryUtil.address(n3, 3, MemoryUtil.memAddress0(doubleBuffer)), n2, PARENT_DOUBLE);
    }

    public static <T extends CustomBuffer<T>> T memSlice(T t, int n, int n2) {
        return t.slice(n, n2);
    }

    public static void memSet(ByteBuffer byteBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(byteBuffer), n, byteBuffer.remaining());
    }

    public static void memSet(ShortBuffer shortBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(shortBuffer), n, APIUtil.apiGetBytes(shortBuffer.remaining(), 1));
    }

    public static void memSet(CharBuffer charBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(charBuffer), n, APIUtil.apiGetBytes(charBuffer.remaining(), 1));
    }

    public static void memSet(IntBuffer intBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(intBuffer), n, APIUtil.apiGetBytes(intBuffer.remaining(), 2));
    }

    public static void memSet(LongBuffer longBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(longBuffer), n, APIUtil.apiGetBytes(longBuffer.remaining(), 3));
    }

    public static void memSet(FloatBuffer floatBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(floatBuffer), n, APIUtil.apiGetBytes(floatBuffer.remaining(), 2));
    }

    public static void memSet(DoubleBuffer doubleBuffer, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(doubleBuffer), n, APIUtil.apiGetBytes(doubleBuffer.remaining(), 3));
    }

    public static <T extends CustomBuffer<T>> void memSet(T t, int n) {
        MemoryUtil.memSet(MemoryUtil.memAddress(t), n, Integer.toUnsignedLong(t.remaining()) * (long)t.sizeof());
    }

    public static <T extends Struct> void memSet(T t, int n) {
        MemoryUtil.memSet(t.address, n, t.sizeof());
    }

    public static void memCopy(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer2, byteBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2), byteBuffer.remaining());
    }

    public static void memCopy(ShortBuffer shortBuffer, ShortBuffer shortBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer2, shortBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(shortBuffer), MemoryUtil.memAddress(shortBuffer2), APIUtil.apiGetBytes(shortBuffer.remaining(), 1));
    }

    public static void memCopy(CharBuffer charBuffer, CharBuffer charBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)charBuffer2, charBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(charBuffer), MemoryUtil.memAddress(charBuffer2), APIUtil.apiGetBytes(charBuffer.remaining(), 1));
    }

    public static void memCopy(IntBuffer intBuffer, IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), APIUtil.apiGetBytes(intBuffer.remaining(), 2));
    }

    public static void memCopy(LongBuffer longBuffer, LongBuffer longBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer2, longBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(longBuffer), MemoryUtil.memAddress(longBuffer2), APIUtil.apiGetBytes(longBuffer.remaining(), 3));
    }

    public static void memCopy(FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer2, floatBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), APIUtil.apiGetBytes(floatBuffer.remaining(), 2));
    }

    public static void memCopy(DoubleBuffer doubleBuffer, DoubleBuffer doubleBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer2, doubleBuffer.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(doubleBuffer), MemoryUtil.memAddress(doubleBuffer2), APIUtil.apiGetBytes(doubleBuffer.remaining(), 3));
    }

    public static <T extends CustomBuffer<T>> void memCopy(T t, T t2) {
        if (Checks.CHECKS) {
            Checks.check(t2, t.remaining());
        }
        MultiReleaseMemCopy.copy(MemoryUtil.memAddress(t), MemoryUtil.memAddress(t2), Integer.toUnsignedLong(t.remaining()) * (long)t.sizeof());
    }

    public static <T extends Struct> void memCopy(T t, T t2) {
        MultiReleaseMemCopy.copy(t.address, t2.address, t.sizeof());
    }

    public static void memSet(long l, int n, long l2) {
        int n2;
        if (Checks.DEBUG && (l == 0L || l2 < 0L)) {
            throw new IllegalArgumentException();
        }
        if (256L <= l2) {
            LibCString.nmemset(l, n, l2);
            return;
        }
        long l3 = (long)(n & 0xFF) * FILL_PATTERN;
        int n3 = 0;
        int n4 = (int)l2 & 0xFF;
        if (n4 != 0 && (n2 = (int)l & 7) != 0) {
            long l4 = l - (long)n2;
            UNSAFE.putLong(null, l4, MemoryUtil.merge(UNSAFE.getLong(null, l4), l3, SHIFT.right(SHIFT.left(-1L, Math.max(0, 8 - n4)), n2)));
            n3 += 8 - n2;
        }
        while (n3 <= n4 - 8) {
            UNSAFE.putLong(null, l + (long)n3, l3);
            n3 += 8;
        }
        n2 = n4 - n3;
        if (0 < n2) {
            UNSAFE.putLong(null, l + (long)n3, MemoryUtil.merge(l3, UNSAFE.getLong(null, l + (long)n3), SHIFT.right(-1L, n2)));
        }
    }

    static long merge(long l, long l2, long l3) {
        return l ^ (l ^ l2) & l3;
    }

    public static void memCopy(long l, long l2, long l3) {
        if (Checks.DEBUG && (l == 0L || l2 == 0L || l3 < 0L)) {
            throw new IllegalArgumentException();
        }
        MultiReleaseMemCopy.copy(l, l2, l3);
    }

    static void memCopyAligned(long l, long l2, int n) {
        int n2;
        for (n2 = 0; n2 <= n - 8; n2 += 8) {
            UNSAFE.putLong(null, l2 + (long)n2, UNSAFE.getLong(null, l + (long)n2));
        }
        if (n2 < n) {
            UNSAFE.putLong(null, l2 + (long)n2, MemoryUtil.merge(UNSAFE.getLong(null, l + (long)n2), UNSAFE.getLong(null, l2 + (long)n2), SHIFT.right(-1L, n - n2)));
        }
    }

    public static boolean memGetBoolean(long l) {
        return UNSAFE.getByte(null, l) != 0;
    }

    public static byte memGetByte(long l) {
        return UNSAFE.getByte(null, l);
    }

    public static short memGetShort(long l) {
        return UNSAFE.getShort(null, l);
    }

    public static int memGetInt(long l) {
        return UNSAFE.getInt(null, l);
    }

    public static long memGetLong(long l) {
        return UNSAFE.getLong(null, l);
    }

    public static float memGetFloat(long l) {
        return UNSAFE.getFloat(null, l);
    }

    public static double memGetDouble(long l) {
        return UNSAFE.getDouble(null, l);
    }

    public static long memGetCLong(long l) {
        return Pointer.CLONG_SIZE == 8 ? UNSAFE.getLong(null, l) : (long)UNSAFE.getInt(null, l);
    }

    public static long memGetAddress(long l) {
        return Pointer.BITS64 ? UNSAFE.getLong(null, l) : (long)UNSAFE.getInt(null, l) & 0xFFFFFFFFL;
    }

    public static void memPutByte(long l, byte by) {
        UNSAFE.putByte(null, l, by);
    }

    public static void memPutShort(long l, short s) {
        UNSAFE.putShort(null, l, s);
    }

    public static void memPutInt(long l, int n) {
        UNSAFE.putInt(null, l, n);
    }

    public static void memPutLong(long l, long l2) {
        UNSAFE.putLong(null, l, l2);
    }

    public static void memPutFloat(long l, float f) {
        UNSAFE.putFloat(null, l, f);
    }

    public static void memPutDouble(long l, double d) {
        UNSAFE.putDouble(null, l, d);
    }

    public static void memPutCLong(long l, long l2) {
        if (Pointer.CLONG_SIZE == 8) {
            UNSAFE.putLong(null, l, l2);
        } else {
            UNSAFE.putInt(null, l, (int)l2);
        }
    }

    public static void memPutAddress(long l, long l2) {
        if (Pointer.BITS64) {
            UNSAFE.putLong(null, l, l2);
        } else {
            UNSAFE.putInt(null, l, (int)l2);
        }
    }

    public static native <T> T memGlobalRefToObject(long var0);

    @Deprecated
    public static long memNewGlobalRef(Object object) {
        return JNINativeInterface.NewGlobalRef(object);
    }

    @Deprecated
    public static void memDeleteGlobalRef(long l) {
        JNINativeInterface.DeleteGlobalRef(l);
    }

    @Deprecated
    public static long memNewWeakGlobalRef(Object object) {
        return JNINativeInterface.NewWeakGlobalRef(object);
    }

    @Deprecated
    public static void memDeleteWeakGlobalRef(long l) {
        JNINativeInterface.DeleteWeakGlobalRef(l);
    }

    public static ByteBuffer memASCII(CharSequence charSequence) {
        return MemoryUtil.memASCII(charSequence, true);
    }

    @Nullable
    public static ByteBuffer memASCIISafe(@Nullable CharSequence charSequence) {
        return charSequence == null ? null : MemoryUtil.memASCII(charSequence, true);
    }

    public static ByteBuffer memASCII(CharSequence charSequence, boolean bl) {
        int n = MemoryUtil.memLengthASCII(charSequence, bl);
        long l = MemoryUtil.nmemAlloc(n);
        MemoryUtil.encodeASCII(charSequence, bl, l);
        return MemoryUtil.wrap(BUFFER_BYTE, l, n).order(NATIVE_ORDER);
    }

    @Nullable
    public static ByteBuffer memASCIISafe(@Nullable CharSequence charSequence, boolean bl) {
        return charSequence == null ? null : MemoryUtil.memASCII(charSequence, bl);
    }

    public static int memASCII(CharSequence charSequence, boolean bl, ByteBuffer byteBuffer) {
        return MemoryUtil.encodeASCII(charSequence, bl, MemoryUtil.memAddress(byteBuffer));
    }

    public static int memASCII(CharSequence charSequence, boolean bl, ByteBuffer byteBuffer, int n) {
        return MemoryUtil.encodeASCII(charSequence, bl, MemoryUtil.memAddress(byteBuffer, n));
    }

    static int encodeASCII(CharSequence charSequence, boolean bl, long l) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            UNSAFE.putByte(l + (long)i, (byte)charSequence.charAt(i));
        }
        if (bl) {
            UNSAFE.putByte(l + (long)n++, (byte)0);
        }
        return n;
    }

    public static int memLengthASCII(CharSequence charSequence, boolean bl) {
        return charSequence.length() + (bl ? 1 : 0);
    }

    public static ByteBuffer memUTF8(CharSequence charSequence) {
        return MemoryUtil.memUTF8(charSequence, true);
    }

    @Nullable
    public static ByteBuffer memUTF8Safe(@Nullable CharSequence charSequence) {
        return charSequence == null ? null : MemoryUtil.memUTF8(charSequence, true);
    }

    public static ByteBuffer memUTF8(CharSequence charSequence, boolean bl) {
        int n = MemoryUtil.memLengthUTF8(charSequence, bl);
        long l = MemoryUtil.nmemAlloc(n);
        MemoryUtil.encodeUTF8(charSequence, bl, l);
        return MemoryUtil.wrap(BUFFER_BYTE, l, n).order(NATIVE_ORDER);
    }

    @Nullable
    public static ByteBuffer memUTF8Safe(@Nullable CharSequence charSequence, boolean bl) {
        return charSequence == null ? null : MemoryUtil.memUTF8(charSequence, bl);
    }

    public static int memUTF8(CharSequence charSequence, boolean bl, ByteBuffer byteBuffer) {
        return MemoryUtil.encodeUTF8(charSequence, bl, MemoryUtil.memAddress(byteBuffer));
    }

    public static int memUTF8(CharSequence charSequence, boolean bl, ByteBuffer byteBuffer, int n) {
        return MemoryUtil.encodeUTF8(charSequence, bl, MemoryUtil.memAddress(byteBuffer, n));
    }

    static int encodeUTF8(CharSequence charSequence, boolean bl, long l) {
        int n;
        int n2;
        int n3 = charSequence.length();
        int n4 = 0;
        for (n2 = 0; n2 < n3 && (n = charSequence.charAt(n2)) < 128; ++n2) {
            UNSAFE.putByte(l + (long)n4++, (byte)n);
        }
        while (n2 < n3) {
            if ((n = charSequence.charAt(n2++)) < 128) {
                UNSAFE.putByte(l + (long)n4++, (byte)n);
                continue;
            }
            int n5 = n;
            if (n < 2048) {
                UNSAFE.putByte(l + (long)n4++, (byte)(0xC0 | n5 >> 6));
            } else {
                if (!Character.isHighSurrogate((char)n)) {
                    UNSAFE.putByte(l + (long)n4++, (byte)(0xE0 | n5 >> 12));
                } else {
                    n5 = Character.toCodePoint((char)n, charSequence.charAt(n2++));
                    UNSAFE.putByte(l + (long)n4++, (byte)(0xF0 | n5 >> 18));
                    UNSAFE.putByte(l + (long)n4++, (byte)(0x80 | n5 >> 12 & 0x3F));
                }
                UNSAFE.putByte(l + (long)n4++, (byte)(0x80 | n5 >> 6 & 0x3F));
            }
            UNSAFE.putByte(l + (long)n4++, (byte)(0x80 | n5 & 0x3F));
        }
        if (bl) {
            UNSAFE.putByte(l + (long)n4++, (byte)0);
        }
        return n4;
    }

    public static int memLengthUTF8(CharSequence charSequence, boolean bl) {
        int n;
        int n2;
        int n3 = n2 = charSequence.length();
        for (n = 0; n < n2 && '\u0080' > charSequence.charAt(n); ++n) {
        }
        while (n < n2) {
            char c = charSequence.charAt(n);
            if ('\u0800' <= c) {
                n3 += MemoryUtil.encodeUTF8LengthSlow(charSequence, n, n2);
                break;
            }
            n3 += 127 - c >>> 31;
            ++n;
        }
        return n3 + (bl ? 1 : 0);
    }

    private static int encodeUTF8LengthSlow(CharSequence charSequence, int n, int n2) {
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            char c = charSequence.charAt(i);
            if (c < '\u0800') {
                n3 += 127 - c >>> 31;
                continue;
            }
            if (c < '\ud800' || '\udfff' < c) {
                n3 += 2;
                continue;
            }
            n3 += 2;
            ++i;
        }
        return n3;
    }

    public static ByteBuffer memUTF16(CharSequence charSequence) {
        return MemoryUtil.memUTF16(charSequence, true);
    }

    @Nullable
    public static ByteBuffer memUTF16Safe(@Nullable CharSequence charSequence) {
        return charSequence == null ? null : MemoryUtil.memUTF16(charSequence, true);
    }

    public static ByteBuffer memUTF16(CharSequence charSequence, boolean bl) {
        int n = MemoryUtil.memLengthUTF16(charSequence, bl);
        long l = MemoryUtil.nmemAlloc(n);
        MemoryUtil.encodeUTF16(charSequence, bl, l);
        return MemoryUtil.wrap(BUFFER_BYTE, l, n).order(NATIVE_ORDER);
    }

    @Nullable
    public static ByteBuffer memUTF16Safe(@Nullable CharSequence charSequence, boolean bl) {
        return charSequence == null ? null : MemoryUtil.memUTF16(charSequence, bl);
    }

    public static int memUTF16(CharSequence charSequence, boolean bl, ByteBuffer byteBuffer) {
        return MemoryUtil.encodeUTF16(charSequence, bl, MemoryUtil.memAddress(byteBuffer));
    }

    public static int memUTF16(CharSequence charSequence, boolean bl, ByteBuffer byteBuffer, int n) {
        return MemoryUtil.encodeUTF16(charSequence, bl, MemoryUtil.memAddress(byteBuffer, n));
    }

    static int encodeUTF16(CharSequence charSequence, boolean bl, long l) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            UNSAFE.putShort(l + Integer.toUnsignedLong(i) * 2L, (short)charSequence.charAt(i));
        }
        if (bl) {
            UNSAFE.putShort(l + Integer.toUnsignedLong(n++) * 2L, (short)0);
        }
        return 2 * n;
    }

    public static int memLengthUTF16(CharSequence charSequence, boolean bl) {
        return charSequence.length() + (bl ? 1 : 0) << 1;
    }

    private static int memLengthNT1(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return Pointer.BITS64 ? MemoryUtil.strlen64NT1(l, n) : MemoryUtil.strlen32NT1(l, n);
    }

    private static int strlen64NT1(long l, int n) {
        int n2;
        if (8 <= n) {
            int n3 = (int)l & 7;
            if (n3 != 0) {
                int n4 = 8 - n3;
                for (n2 = 0; n2 < n4; ++n2) {
                    if (UNSAFE.getByte(null, l + (long)n2) != 0) continue;
                    return n2;
                }
            }
            while (n2 <= n - 8 && !MathUtil.mathHasZeroByte(UNSAFE.getLong(null, l + (long)n2))) {
                n2 += 8;
            }
        }
        while (n2 < n && UNSAFE.getByte(null, l + (long)n2) != 0) {
            ++n2;
        }
        return n2;
    }

    private static int strlen32NT1(long l, int n) {
        int n2;
        if (4 <= n) {
            int n3 = (int)l & 3;
            if (n3 != 0) {
                int n4 = 4 - n3;
                for (n2 = 0; n2 < n4; ++n2) {
                    if (UNSAFE.getByte(null, l + (long)n2) != 0) continue;
                    return n2;
                }
            }
            while (n2 <= n - 4 && !MathUtil.mathHasZeroByte(UNSAFE.getInt(null, l + (long)n2))) {
                n2 += 4;
            }
        }
        while (n2 < n && UNSAFE.getByte(null, l + (long)n2) != 0) {
            ++n2;
        }
        return n2;
    }

    public static int memLengthNT1(ByteBuffer byteBuffer) {
        return MemoryUtil.memLengthNT1(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    private static int memLengthNT2(long l, int n) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return Pointer.BITS64 ? MemoryUtil.strlen64NT2(l, n) : MemoryUtil.strlen32NT2(l, n);
    }

    private static int strlen64NT2(long l, int n) {
        int n2;
        if (8 <= n) {
            int n3 = (int)l & 7;
            if (n3 != 0) {
                int n4 = 8 - n3;
                for (n2 = 0; n2 < n4; n2 += 2) {
                    if (UNSAFE.getShort(null, l + (long)n2) != 0) continue;
                    return n2;
                }
            }
            while (n2 <= n - 8 && !MathUtil.mathHasZeroShort(UNSAFE.getLong(null, l + (long)n2))) {
                n2 += 8;
            }
        }
        while (n2 < n && UNSAFE.getShort(null, l + (long)n2) != 0) {
            n2 += 2;
        }
        return n2;
    }

    private static int strlen32NT2(long l, int n) {
        int n2;
        if (4 <= n) {
            int n3 = (int)l & 3;
            if (n3 != 0) {
                int n4 = 4 - n3;
                for (n2 = 0; n2 < n4; n2 += 2) {
                    if (UNSAFE.getShort(null, l + (long)n2) != 0) continue;
                    return n2;
                }
            }
            while (n2 <= n - 4 && !MathUtil.mathHasZeroShort(UNSAFE.getInt(null, l + (long)n2))) {
                n2 += 4;
            }
        }
        while (n2 < n && UNSAFE.getShort(null, l + (long)n2) != 0) {
            n2 += 2;
        }
        return n2;
    }

    public static int memLengthNT2(ByteBuffer byteBuffer) {
        return MemoryUtil.memLengthNT2(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    public static ByteBuffer memByteBufferNT1(long l) {
        return MemoryUtil.memByteBuffer(l, MemoryUtil.memLengthNT1(l, Integer.MAX_VALUE));
    }

    public static ByteBuffer memByteBufferNT1(long l, int n) {
        return MemoryUtil.memByteBuffer(l, MemoryUtil.memLengthNT1(l, n));
    }

    @Nullable
    public static ByteBuffer memByteBufferNT1Safe(long l) {
        return l == 0L ? null : MemoryUtil.memByteBuffer(l, MemoryUtil.memLengthNT1(l, Integer.MAX_VALUE));
    }

    @Nullable
    public static ByteBuffer memByteBufferNT1Safe(long l, int n) {
        return l == 0L ? null : MemoryUtil.memByteBuffer(l, MemoryUtil.memLengthNT1(l, n));
    }

    public static ByteBuffer memByteBufferNT2(long l) {
        return MemoryUtil.memByteBufferNT2(l, 0x7FFFFFFE);
    }

    public static ByteBuffer memByteBufferNT2(long l, int n) {
        if (Checks.DEBUG && (n & 1) != 0) {
            throw new IllegalArgumentException("The maximum length must be an even number.");
        }
        return MemoryUtil.memByteBuffer(l, MemoryUtil.memLengthNT2(l, n));
    }

    @Nullable
    public static ByteBuffer memByteBufferNT2Safe(long l) {
        return l == 0L ? null : MemoryUtil.memByteBufferNT2(l, 0x7FFFFFFE);
    }

    @Nullable
    public static ByteBuffer memByteBufferNT2Safe(long l, int n) {
        return l == 0L ? null : MemoryUtil.memByteBufferNT2(l, n);
    }

    public static String memASCII(long l) {
        return MemoryUtil.memASCII(l, MemoryUtil.memLengthNT1(l, Integer.MAX_VALUE));
    }

    public static String memASCII(long l, int n) {
        if (n <= 0) {
            return "";
        }
        byte[] byArray = n <= ARRAY_TLC_SIZE ? ARRAY_TLC_BYTE.get() : new byte[n];
        MemoryUtil.memByteBuffer(l, n).get(byArray, 0, n);
        return new String(byArray, 0, 0, n);
    }

    public static String memASCII(ByteBuffer byteBuffer) {
        return MemoryUtil.memASCII(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    @Nullable
    public static String memASCIISafe(long l) {
        return l == 0L ? null : MemoryUtil.memASCII(l, MemoryUtil.memLengthNT1(l, Integer.MAX_VALUE));
    }

    @Nullable
    public static String memASCIISafe(long l, int n) {
        return l == 0L ? null : MemoryUtil.memASCII(l, n);
    }

    @Nullable
    public static String memASCIISafe(@Nullable ByteBuffer byteBuffer) {
        return byteBuffer == null ? null : MemoryUtil.memASCII(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    public static String memASCII(ByteBuffer byteBuffer, int n) {
        return MemoryUtil.memASCII(MemoryUtil.memAddress(byteBuffer), n);
    }

    public static String memASCII(ByteBuffer byteBuffer, int n, int n2) {
        return MemoryUtil.memASCII(MemoryUtil.memAddress(byteBuffer, n2), n);
    }

    public static String memUTF8(long l) {
        return MultiReleaseTextDecoding.decodeUTF8(l, MemoryUtil.memLengthNT1(l, Integer.MAX_VALUE));
    }

    public static String memUTF8(long l, int n) {
        return MultiReleaseTextDecoding.decodeUTF8(l, n);
    }

    public static String memUTF8(ByteBuffer byteBuffer) {
        return MultiReleaseTextDecoding.decodeUTF8(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    @Nullable
    public static String memUTF8Safe(long l) {
        return l == 0L ? null : MultiReleaseTextDecoding.decodeUTF8(l, MemoryUtil.memLengthNT1(l, Integer.MAX_VALUE));
    }

    @Nullable
    public static String memUTF8Safe(long l, int n) {
        return l == 0L ? null : MultiReleaseTextDecoding.decodeUTF8(l, n);
    }

    @Nullable
    public static String memUTF8Safe(@Nullable ByteBuffer byteBuffer) {
        return byteBuffer == null ? null : MultiReleaseTextDecoding.decodeUTF8(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    public static String memUTF8(ByteBuffer byteBuffer, int n) {
        return MultiReleaseTextDecoding.decodeUTF8(MemoryUtil.memAddress(byteBuffer), n);
    }

    public static String memUTF8(ByteBuffer byteBuffer, int n, int n2) {
        return MultiReleaseTextDecoding.decodeUTF8(MemoryUtil.memAddress(byteBuffer, n2), n);
    }

    public static String memUTF16(long l) {
        return MemoryUtil.memUTF16(l, MemoryUtil.memLengthNT2(l, 0x7FFFFFFE) >> 1);
    }

    public static String memUTF16(long l, int n) {
        if (n <= 0) {
            return "";
        }
        if (Checks.DEBUG) {
            int n2 = n << 1;
            byte[] byArray = n2 <= ARRAY_TLC_SIZE ? ARRAY_TLC_BYTE.get() : new byte[n2];
            MemoryUtil.memByteBuffer(l, n2).get(byArray, 0, n2);
            return new String(byArray, 0, n2, UTF16);
        }
        char[] cArray = n <= ARRAY_TLC_SIZE ? ARRAY_TLC_CHAR.get() : new char[n];
        MemoryUtil.memCharBuffer(l, n).get(cArray, 0, n);
        return new String(cArray, 0, n);
    }

    public static String memUTF16(ByteBuffer byteBuffer) {
        return MemoryUtil.memUTF16(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining() >> 1);
    }

    @Nullable
    public static String memUTF16Safe(long l) {
        return l == 0L ? null : MemoryUtil.memUTF16(l, MemoryUtil.memLengthNT2(l, 0x7FFFFFFE) >> 1);
    }

    @Nullable
    public static String memUTF16Safe(long l, int n) {
        return l == 0L ? null : MemoryUtil.memUTF16(l, n);
    }

    @Nullable
    public static String memUTF16Safe(@Nullable ByteBuffer byteBuffer) {
        return byteBuffer == null ? null : MemoryUtil.memUTF16(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining() >> 1);
    }

    public static String memUTF16(ByteBuffer byteBuffer, int n) {
        return MemoryUtil.memUTF16(MemoryUtil.memAddress(byteBuffer), n);
    }

    public static String memUTF16(ByteBuffer byteBuffer, int n, int n2) {
        return MemoryUtil.memUTF16(MemoryUtil.memAddress(byteBuffer, n2), n);
    }

    private static Unsafe getUnsafeInstance() {
        Field[] fieldArray;
        for (Field field : fieldArray = Unsafe.class.getDeclaredFields()) {
            int n;
            if (!field.getType().equals(Unsafe.class) || !Modifier.isStatic(n = field.getModifiers()) || !Modifier.isFinal(n)) continue;
            try {
                field.setAccessible(false);
                return (Unsafe)field.get(null);
            } catch (Exception exception) {
                break;
            }
        }
        throw new UnsupportedOperationException("LWJGL requires sun.misc.Unsafe to be available.");
    }

    private static long getAddressOffset() {
        long l = -2401053090268712947L;
        if (Pointer.BITS32) {
            l &= 0xFFFFFFFFL;
        }
        ByteBuffer byteBuffer = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(l, 0L));
        long l2 = 8L;
        while (UNSAFE.getLong((Object)byteBuffer, l2) != l) {
            l2 += 8L;
        }
        return l2;
    }

    private static long getIntFieldOffset(ByteBuffer byteBuffer, int n) {
        long l = 4L;
        while (UNSAFE.getInt((Object)byteBuffer, l) != n) {
            l += 4L;
        }
        return l;
    }

    private static long getMarkOffset() {
        ByteBuffer byteBuffer = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(1L, 0L));
        return MemoryUtil.getIntFieldOffset(byteBuffer, -1);
    }

    private static long getPositionOffset() {
        ByteBuffer byteBuffer = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
        byteBuffer.position(16435934);
        return MemoryUtil.getIntFieldOffset(byteBuffer, 16435934);
    }

    private static long getLimitOffset() {
        ByteBuffer byteBuffer = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
        byteBuffer.limit(16435934);
        return MemoryUtil.getIntFieldOffset(byteBuffer, 16435934);
    }

    private static long getCapacityOffset() {
        ByteBuffer byteBuffer = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
        byteBuffer.limit(0);
        return MemoryUtil.getIntFieldOffset(byteBuffer, 219540062);
    }

    private static <T extends Buffer> long getParentOffset(int n, T t, Function<T, T> function) {
        Buffer buffer = (Buffer)function.apply(t);
        long l = n;
        switch (n) {
            case 4: {
                while (true) {
                    if (UNSAFE.getInt(t, l) != UNSAFE.getInt((Object)buffer, l)) {
                        return l;
                    }
                    l += (long)n;
                }
            }
            case 8: {
                while (UNSAFE.getLong(t, l) == UNSAFE.getLong((Object)buffer, l)) {
                    l += (long)n;
                }
                return l;
            }
        }
        throw new IllegalStateException();
    }

    static <T extends Buffer> T wrap(Class<? extends T> clazz, long l, int n) {
        Buffer buffer;
        try {
            buffer = (Buffer)UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)buffer, ADDRESS, l);
        UNSAFE.putInt((Object)buffer, MARK, -1);
        UNSAFE.putInt((Object)buffer, LIMIT, n);
        UNSAFE.putInt((Object)buffer, CAPACITY, n);
        return (T)buffer;
    }

    static <T extends Buffer> T slice(Class<? extends T> clazz, T t, long l, int n, long l2) {
        Buffer buffer;
        try {
            buffer = (Buffer)UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)buffer, ADDRESS, l);
        UNSAFE.putInt((Object)buffer, MARK, -1);
        UNSAFE.putInt((Object)buffer, LIMIT, n);
        UNSAFE.putInt((Object)buffer, CAPACITY, n);
        UNSAFE.putObject((Object)buffer, l2, UNSAFE.getObject(t, l2));
        return (T)buffer;
    }

    static <T extends Buffer> T duplicate(Class<? extends T> clazz, T t, long l) {
        Buffer buffer;
        try {
            buffer = (Buffer)UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)buffer, ADDRESS, UNSAFE.getLong(t, ADDRESS));
        UNSAFE.putInt((Object)buffer, MARK, UNSAFE.getInt(t, MARK));
        UNSAFE.putInt((Object)buffer, POSITION, UNSAFE.getInt(t, POSITION));
        UNSAFE.putInt((Object)buffer, LIMIT, UNSAFE.getInt(t, LIMIT));
        UNSAFE.putInt((Object)buffer, CAPACITY, UNSAFE.getInt(t, CAPACITY));
        UNSAFE.putObject((Object)buffer, l, UNSAFE.getObject(t, l));
        return (T)buffer;
    }

    private static ByteBuffer lambda$static$2(ByteBuffer byteBuffer) {
        return byteBuffer.duplicate().order(byteBuffer.order());
    }

    private static char[] lambda$static$1() {
        return new char[ARRAY_TLC_SIZE];
    }

    private static byte[] lambda$static$0() {
        return new byte[ARRAY_TLC_SIZE];
    }

    static {
        int n;
        ARRAY_TLC_SIZE = Configuration.ARRAY_TLC_SIZE.get(8192);
        ARRAY_TLC_BYTE = ThreadLocal.withInitial(MemoryUtil::lambda$static$0);
        ARRAY_TLC_CHAR = ThreadLocal.withInitial(MemoryUtil::lambda$static$1);
        NATIVE_ORDER = ByteOrder.nativeOrder();
        UTF16 = NATIVE_ORDER == ByteOrder.LITTLE_ENDIAN ? StandardCharsets.UTF_16LE : StandardCharsets.UTF_16BE;
        Library.initialize();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(0).order(NATIVE_ORDER);
        BUFFER_BYTE = byteBuffer.getClass();
        BUFFER_SHORT = byteBuffer.asShortBuffer().getClass();
        BUFFER_CHAR = byteBuffer.asCharBuffer().getClass();
        BUFFER_INT = byteBuffer.asIntBuffer().getClass();
        BUFFER_LONG = byteBuffer.asLongBuffer().getClass();
        BUFFER_FLOAT = byteBuffer.asFloatBuffer().getClass();
        BUFFER_DOUBLE = byteBuffer.asDoubleBuffer().getClass();
        UNSAFE = MemoryUtil.getUnsafeInstance();
        try {
            ADDRESS = MemoryUtil.getAddressOffset();
            MARK = MemoryUtil.getMarkOffset();
            POSITION = MemoryUtil.getPositionOffset();
            LIMIT = MemoryUtil.getLimitOffset();
            CAPACITY = MemoryUtil.getCapacityOffset();
            n = UNSAFE.arrayIndexScale(Object[].class);
            PARENT_BYTE = MemoryUtil.getParentOffset(n, byteBuffer, MemoryUtil::lambda$static$2);
            PARENT_SHORT = MemoryUtil.getParentOffset(n, byteBuffer.asShortBuffer(), ShortBuffer::duplicate);
            PARENT_CHAR = MemoryUtil.getParentOffset(n, byteBuffer.asCharBuffer(), CharBuffer::duplicate);
            PARENT_INT = MemoryUtil.getParentOffset(n, byteBuffer.asIntBuffer(), IntBuffer::duplicate);
            PARENT_LONG = MemoryUtil.getParentOffset(n, byteBuffer.asLongBuffer(), LongBuffer::duplicate);
            PARENT_FLOAT = MemoryUtil.getParentOffset(n, byteBuffer.asFloatBuffer(), FloatBuffer::duplicate);
            PARENT_DOUBLE = MemoryUtil.getParentOffset(n, byteBuffer.asDoubleBuffer(), DoubleBuffer::duplicate);
        } catch (Throwable throwable) {
            throw new UnsupportedOperationException(throwable);
        }
        for (n = 0; n < 10000; ++n) {
            UNSAFE.putObject((Object)byteBuffer, PARENT_BYTE, UNSAFE.getObject((Object)byteBuffer, PARENT_BYTE));
        }
        PAGE_SIZE = UNSAFE.pageSize();
        CACHE_LINE_SIZE = 64;
        SHIFT = NATIVE_ORDER == ByteOrder.BIG_ENDIAN ? new NativeShift(){

            @Override
            public long left(long l, int n) {
                return l << (n << 3);
            }

            @Override
            public long right(long l, int n) {
                return l >>> (n << 3);
            }
        } : new NativeShift(){

            @Override
            public long left(long l, int n) {
                return l >>> (n << 3);
            }

            @Override
            public long right(long l, int n) {
                return l << (n << 3);
            }
        };
        FILL_PATTERN = Long.divideUnsigned(-1L, 255L);
    }

    private static interface NativeShift {
        public long left(long var1, int var3);

        public long right(long var1, int var3);
    }

    public static interface MemoryAllocationReport {
        public void invoke(long var1, long var3, long var5, @Nullable String var7, @Nullable StackTraceElement ... var8);

        public static enum Aggregate {
            ALL,
            GROUP_BY_METHOD,
            GROUP_BY_STACKTRACE;

        }
    }

    public static interface MemoryAllocator {
        public long getMalloc();

        public long getCalloc();

        public long getRealloc();

        public long getFree();

        public long getAlignedAlloc();

        public long getAlignedFree();

        public long malloc(long var1);

        public long calloc(long var1, long var3);

        public long realloc(long var1, long var3);

        public void free(long var1);

        public long aligned_alloc(long var1, long var3);

        public void aligned_free(long var1);
    }

    static final class LazyInit {
        static final MemoryAllocator ALLOCATOR_IMPL = MemoryManage.getInstance();
        static final MemoryAllocator ALLOCATOR = Configuration.DEBUG_MEMORY_ALLOCATOR.get(false) != false ? new MemoryManage.DebugAllocator(ALLOCATOR_IMPL) : ALLOCATOR_IMPL;

        private LazyInit() {
        }

        static {
            APIUtil.apiLog("MemoryUtil allocator: " + ALLOCATOR.getClass().getSimpleName());
        }
    }
}

