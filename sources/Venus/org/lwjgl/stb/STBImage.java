/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.LibSTB;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class STBImage {
    public static final int STBI_default = 0;
    public static final int STBI_grey = 1;
    public static final int STBI_grey_alpha = 2;
    public static final int STBI_rgb = 3;
    public static final int STBI_rgb_alpha = 4;

    protected STBImage() {
        throw new UnsupportedOperationException();
    }

    public static native long nstbi_load(long var0, long var2, long var4, long var6, int var8);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        long l = STBImage.nstbi_load(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBImage.nstbi_load(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
            ByteBuffer byteBuffer = MemoryUtil.memByteBufferSafe(l2, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
            return byteBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_load_from_memory(long var0, int var2, long var3, long var5, long var7, int var9);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        long l = STBImage.nstbi_load_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    public static native long nstbi_load_from_callbacks(long var0, long var2, long var4, long var6, long var8, int var10);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        long l2 = STBImage.nstbi_load_from_callbacks(sTBIIOCallbacks.address(), l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memByteBufferSafe(l2, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    public static native long nstbi_load_gif_from_memory(long var0, int var2, long var3, long var5, long var7, long var9, long var11, int var13);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load_gif_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int **") PointerBuffer pointerBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, @NativeType(value="int *") IntBuffer intBuffer4, int n) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            Checks.check((Buffer)intBuffer4, 1);
        }
        long l = STBImage.nstbi_load_gif_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer4), n);
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * intBuffer3.get(intBuffer3.position()) * (n != 0 ? n : intBuffer4.get(intBuffer4.position())));
    }

    public static native long nstbi_load_16(long var0, long var2, long var4, long var6, int var8);

    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        long l = STBImage.nstbi_load_16(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memShortBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBImage.nstbi_load_16(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
            ShortBuffer shortBuffer = MemoryUtil.memShortBufferSafe(l2, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
            return shortBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_load_16_from_memory(long var0, int var2, long var3, long var5, long var7, int var9);

    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        long l = STBImage.nstbi_load_16_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memShortBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    public static native long nstbi_load_16_from_callbacks(long var0, long var2, long var4, long var6, long var8, int var10);

    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        long l2 = STBImage.nstbi_load_16_from_callbacks(sTBIIOCallbacks.address(), l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memShortBufferSafe(l2, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    public static native long nstbi_loadf(long var0, long var2, long var4, long var6, int var8);

    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        long l = STBImage.nstbi_loadf(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memFloatBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBImage.nstbi_loadf(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
            FloatBuffer floatBuffer = MemoryUtil.memFloatBufferSafe(l2, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
            return floatBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_loadf_from_memory(long var0, int var2, long var3, long var5, long var7, int var9);

    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        long l = STBImage.nstbi_loadf_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memFloatBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    public static native long nstbi_loadf_from_callbacks(long var0, long var2, long var4, long var6, long var8, int var10);

    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        long l2 = STBImage.nstbi_loadf_from_callbacks(sTBIIOCallbacks.address(), l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), n);
        return MemoryUtil.memFloatBufferSafe(l2, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()) * (n != 0 ? n : intBuffer3.get(intBuffer3.position())));
    }

    public static native void stbi_hdr_to_ldr_gamma(float var0);

    public static native void stbi_hdr_to_ldr_scale(float var0);

    public static native void stbi_ldr_to_hdr_gamma(float var0);

    public static native void stbi_ldr_to_hdr_scale(float var0);

    public static native int nstbi_is_hdr(long var0);

    @NativeType(value="int")
    public static boolean stbi_is_hdr(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return STBImage.nstbi_is_hdr(MemoryUtil.memAddress(byteBuffer)) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="int")
    public static boolean stbi_is_hdr(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            boolean bl = STBImage.nstbi_is_hdr(l) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstbi_is_hdr_from_memory(long var0, int var2);

    @NativeType(value="int")
    public static boolean stbi_is_hdr_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer) {
        return STBImage.nstbi_is_hdr_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining()) != 0;
    }

    public static native int nstbi_is_hdr_from_callbacks(long var0, long var2);

    @NativeType(value="int")
    public static boolean stbi_is_hdr_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l) {
        if (Checks.CHECKS) {
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        return STBImage.nstbi_is_hdr_from_callbacks(sTBIIOCallbacks.address(), l) != 0;
    }

    public static native long nstbi_failure_reason();

    @Nullable
    @NativeType(value="char const *")
    public static String stbi_failure_reason() {
        long l = STBImage.nstbi_failure_reason();
        return MemoryUtil.memASCIISafe(l);
    }

    public static native void nstbi_image_free(long var0);

    public static void stbi_image_free(@NativeType(value="void *") ByteBuffer byteBuffer) {
        STBImage.nstbi_image_free(MemoryUtil.memAddress(byteBuffer));
    }

    public static void stbi_image_free(@NativeType(value="void *") ShortBuffer shortBuffer) {
        STBImage.nstbi_image_free(MemoryUtil.memAddress(shortBuffer));
    }

    public static void stbi_image_free(@NativeType(value="void *") FloatBuffer floatBuffer) {
        STBImage.nstbi_image_free(MemoryUtil.memAddress(floatBuffer));
    }

    public static native int nstbi_info(long var0, long var2, long var4, long var6);

    @NativeType(value="int")
    public static boolean stbi_info(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        return STBImage.nstbi_info(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3)) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="int")
    public static boolean stbi_info(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            boolean bl = STBImage.nstbi_info(l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3)) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstbi_info_from_memory(long var0, int var2, long var3, long var5, long var7);

    @NativeType(value="int")
    public static boolean stbi_info_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        return STBImage.nstbi_info_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3)) != 0;
    }

    public static native int nstbi_info_from_callbacks(long var0, long var2, long var4, long var6, long var8);

    @NativeType(value="int")
    public static boolean stbi_info_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        return STBImage.nstbi_info_from_callbacks(sTBIIOCallbacks.address(), l, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3)) != 0;
    }

    public static native int nstbi_is_16_bit(long var0);

    @NativeType(value="int")
    public static boolean stbi_is_16_bit(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return STBImage.nstbi_is_16_bit(MemoryUtil.memAddress(byteBuffer)) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="int")
    public static boolean stbi_is_16_bit(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            boolean bl = STBImage.nstbi_is_16_bit(l) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstbi_is_16_bit_from_memory(long var0, int var2);

    @NativeType(value="int")
    public static boolean stbi_is_16_bit_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer) {
        return STBImage.nstbi_is_16_bit_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining()) != 0;
    }

    public static native int nstbi_is_16_bit_from_callbacks(long var0, long var2);

    @NativeType(value="int")
    public static boolean stbi_is_16_bit_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l) {
        if (Checks.CHECKS) {
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        return STBImage.nstbi_is_16_bit_from_callbacks(sTBIIOCallbacks.address(), l) != 0;
    }

    public static native void nstbi_set_unpremultiply_on_load(int var0);

    public static void stbi_set_unpremultiply_on_load(@NativeType(value="int") boolean bl) {
        STBImage.nstbi_set_unpremultiply_on_load(bl ? 1 : 0);
    }

    public static native void nstbi_convert_iphone_png_to_rgb(int var0);

    public static void stbi_convert_iphone_png_to_rgb(@NativeType(value="int") boolean bl) {
        STBImage.nstbi_convert_iphone_png_to_rgb(bl ? 1 : 0);
    }

    public static native void nstbi_set_flip_vertically_on_load(int var0);

    public static void stbi_set_flip_vertically_on_load(@NativeType(value="int") boolean bl) {
        STBImage.nstbi_set_flip_vertically_on_load(bl ? 1 : 0);
    }

    public static native long nstbi_zlib_decode_malloc_guesssize(long var0, int var2, int var3, long var4);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char *")
    public static ByteBuffer stbi_zlib_decode_malloc_guesssize(@NativeType(value="char const *") ByteBuffer byteBuffer, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            long l = STBImage.nstbi_zlib_decode_malloc_guesssize(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), n, MemoryUtil.memAddress(intBuffer));
            ByteBuffer byteBuffer2 = MemoryUtil.memByteBufferSafe(l, intBuffer.get(0));
            return byteBuffer2;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_zlib_decode_malloc_guesssize_headerflag(long var0, int var2, int var3, long var4, int var6);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char *")
    public static ByteBuffer stbi_zlib_decode_malloc_guesssize_headerflag(@NativeType(value="char const *") ByteBuffer byteBuffer, int n, @NativeType(value="int") boolean bl) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            long l = STBImage.nstbi_zlib_decode_malloc_guesssize_headerflag(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), n, MemoryUtil.memAddress(intBuffer), bl ? 1 : 0);
            ByteBuffer byteBuffer2 = MemoryUtil.memByteBufferSafe(l, intBuffer.get(0));
            return byteBuffer2;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_zlib_decode_malloc(long var0, int var2, long var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char *")
    public static ByteBuffer stbi_zlib_decode_malloc(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            long l = STBImage.nstbi_zlib_decode_malloc(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
            ByteBuffer byteBuffer2 = MemoryUtil.memByteBufferSafe(l, intBuffer.get(0));
            return byteBuffer2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstbi_zlib_decode_buffer(long var0, int var2, long var3, int var5);

    public static int stbi_zlib_decode_buffer(@NativeType(value="char *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2) {
        return STBImage.nstbi_zlib_decode_buffer(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer2), byteBuffer2.remaining());
    }

    public static native long nstbi_zlib_decode_noheader_malloc(long var0, int var2, long var3);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char *")
    public static ByteBuffer stbi_zlib_decode_noheader_malloc(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            long l = STBImage.nstbi_zlib_decode_noheader_malloc(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
            ByteBuffer byteBuffer2 = MemoryUtil.memByteBufferSafe(l, intBuffer.get(0));
            return byteBuffer2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstbi_zlib_decode_noheader_buffer(long var0, int var2, long var3, int var5);

    public static int stbi_zlib_decode_noheader_buffer(@NativeType(value="char *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2) {
        return STBImage.nstbi_zlib_decode_noheader_buffer(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer2), byteBuffer2.remaining());
    }

    public static native long nstbi_load(long var0, int[] var2, int[] var3, int[] var4, int var5);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        long l = STBImage.nstbi_load(MemoryUtil.memAddress(byteBuffer), nArray, nArray2, nArray3, n);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBImage.nstbi_load(l, nArray, nArray2, nArray3, n);
            ByteBuffer byteBuffer = MemoryUtil.memByteBufferSafe(l2, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
            return byteBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_load_from_memory(long var0, int var2, int[] var3, int[] var4, int[] var5, int var6);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        long l = STBImage.nstbi_load_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, nArray2, nArray3, n);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    public static native long nstbi_load_from_callbacks(long var0, long var2, int[] var4, int[] var5, int[] var6, int var7);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        long l2 = STBImage.nstbi_load_from_callbacks(sTBIIOCallbacks.address(), l, nArray, nArray2, nArray3, n);
        return MemoryUtil.memByteBufferSafe(l2, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    public static native long nstbi_load_gif_from_memory(long var0, int var2, long var3, int[] var5, int[] var6, int[] var7, int[] var8, int var9);

    @Nullable
    @NativeType(value="stbi_uc *")
    public static ByteBuffer stbi_load_gif_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int **") PointerBuffer pointerBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, @NativeType(value="int *") int[] nArray4, int n) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            Checks.check(nArray4, 1);
        }
        long l = STBImage.nstbi_load_gif_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), nArray, nArray2, nArray3, nArray4, n);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0] * nArray3[0] * (n != 0 ? n : nArray4[0]));
    }

    public static native long nstbi_load_16(long var0, int[] var2, int[] var3, int[] var4, int var5);

    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        long l = STBImage.nstbi_load_16(MemoryUtil.memAddress(byteBuffer), nArray, nArray2, nArray3, n);
        return MemoryUtil.memShortBufferSafe(l, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBImage.nstbi_load_16(l, nArray, nArray2, nArray3, n);
            ShortBuffer shortBuffer = MemoryUtil.memShortBufferSafe(l2, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
            return shortBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_load_16_from_memory(long var0, int var2, int[] var3, int[] var4, int[] var5, int var6);

    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        long l = STBImage.nstbi_load_16_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, nArray2, nArray3, n);
        return MemoryUtil.memShortBufferSafe(l, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    public static native long nstbi_load_16_from_callbacks(long var0, long var2, int[] var4, int[] var5, int[] var6, int var7);

    @Nullable
    @NativeType(value="stbi_us *")
    public static ShortBuffer stbi_load_16_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        long l2 = STBImage.nstbi_load_16_from_callbacks(sTBIIOCallbacks.address(), l, nArray, nArray2, nArray3, n);
        return MemoryUtil.memShortBufferSafe(l2, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    public static native long nstbi_loadf(long var0, int[] var2, int[] var3, int[] var4, int var5);

    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        long l = STBImage.nstbi_loadf(MemoryUtil.memAddress(byteBuffer), nArray, nArray2, nArray3, n);
        return MemoryUtil.memFloatBufferSafe(l, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            long l2 = STBImage.nstbi_loadf(l, nArray, nArray2, nArray3, n);
            FloatBuffer floatBuffer = MemoryUtil.memFloatBufferSafe(l2, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
            return floatBuffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nstbi_loadf_from_memory(long var0, int var2, int[] var3, int[] var4, int[] var5, int var6);

    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        long l = STBImage.nstbi_loadf_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, nArray2, nArray3, n);
        return MemoryUtil.memFloatBufferSafe(l, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    public static native long nstbi_loadf_from_callbacks(long var0, long var2, int[] var4, int[] var5, int[] var6, int var7);

    @Nullable
    @NativeType(value="float *")
    public static FloatBuffer stbi_loadf_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, int n) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        long l2 = STBImage.nstbi_loadf_from_callbacks(sTBIIOCallbacks.address(), l, nArray, nArray2, nArray3, n);
        return MemoryUtil.memFloatBufferSafe(l2, nArray[0] * nArray2[0] * (n != 0 ? n : nArray3[0]));
    }

    public static native int nstbi_info(long var0, int[] var2, int[] var3, int[] var4);

    @NativeType(value="int")
    public static boolean stbi_info(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        return STBImage.nstbi_info(MemoryUtil.memAddress(byteBuffer), nArray, nArray2, nArray3) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="int")
    public static boolean stbi_info(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            boolean bl = STBImage.nstbi_info(l, nArray, nArray2, nArray3) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstbi_info_from_memory(long var0, int var2, int[] var3, int[] var4, int[] var5);

    @NativeType(value="int")
    public static boolean stbi_info_from_memory(@NativeType(value="stbi_uc const *") ByteBuffer byteBuffer, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        return STBImage.nstbi_info_from_memory(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), nArray, nArray2, nArray3) != 0;
    }

    public static native int nstbi_info_from_callbacks(long var0, long var2, int[] var4, int[] var5, int[] var6);

    @NativeType(value="int")
    public static boolean stbi_info_from_callbacks(@NativeType(value="stbi_io_callbacks const *") STBIIOCallbacks sTBIIOCallbacks, @NativeType(value="void *") long l, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            STBIIOCallbacks.validate(sTBIIOCallbacks.address());
        }
        return STBImage.nstbi_info_from_callbacks(sTBIIOCallbacks.address(), l, nArray, nArray2, nArray3) != 0;
    }

    static {
        LibSTB.initialize();
    }
}

