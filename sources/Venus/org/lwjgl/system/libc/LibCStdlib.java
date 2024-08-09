/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.libc;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class LibCStdlib {
    protected LibCStdlib() {
        throw new UnsupportedOperationException();
    }

    public static native long nmalloc(long var0);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer malloc(@NativeType(value="size_t") long l) {
        long l2 = LibCStdlib.nmalloc(l);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static native long ncalloc(long var0, long var2);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer calloc(@NativeType(value="size_t") long l, @NativeType(value="size_t") long l2) {
        long l3 = LibCStdlib.ncalloc(l, l2);
        return MemoryUtil.memByteBufferSafe(l3, (int)l * (int)l2);
    }

    public static native long nrealloc(long var0, long var2);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer realloc(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="size_t") long l) {
        long l2 = LibCStdlib.nrealloc(MemoryUtil.memAddressSafe(byteBuffer), l);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static native void nfree(long var0);

    public static void free(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void free(@Nullable @NativeType(value="void *") ShortBuffer shortBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void free(@Nullable @NativeType(value="void *") IntBuffer intBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void free(@Nullable @NativeType(value="void *") LongBuffer longBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(longBuffer));
    }

    public static void free(@Nullable @NativeType(value="void *") FloatBuffer floatBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void free(@Nullable @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static void free(@Nullable @NativeType(value="void *") PointerBuffer pointerBuffer) {
        LibCStdlib.nfree(MemoryUtil.memAddressSafe(pointerBuffer));
    }

    public static native long naligned_alloc(long var0, long var2);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer aligned_alloc(@NativeType(value="size_t") long l, @NativeType(value="size_t") long l2) {
        long l3 = LibCStdlib.naligned_alloc(l, l2);
        return MemoryUtil.memByteBufferSafe(l3, (int)l2);
    }

    public static native void naligned_free(long var0);

    public static void aligned_free(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void aligned_free(@Nullable @NativeType(value="void *") ShortBuffer shortBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void aligned_free(@Nullable @NativeType(value="void *") IntBuffer intBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void aligned_free(@Nullable @NativeType(value="void *") LongBuffer longBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(longBuffer));
    }

    public static void aligned_free(@Nullable @NativeType(value="void *") FloatBuffer floatBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void aligned_free(@Nullable @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static void aligned_free(@Nullable @NativeType(value="void *") PointerBuffer pointerBuffer) {
        LibCStdlib.naligned_free(MemoryUtil.memAddressSafe(pointerBuffer));
    }

    static {
        Library.initialize();
    }
}

