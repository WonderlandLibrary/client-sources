/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.stb.LibSTB;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class STBDXT {
    public static final int STB_DXT_NORMAL = 0;
    public static final int STB_DXT_DITHER = 1;
    public static final int STB_DXT_HIGHQUAL = 2;

    protected STBDXT() {
        throw new UnsupportedOperationException();
    }

    public static native void nstb_compress_dxt_block(long var0, long var2, int var4, int var5);

    public static void stb_compress_dxt_block(@NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer2, @NativeType(value="int") boolean bl, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, bl ? 16 : 8);
            Checks.check((Buffer)byteBuffer2, 64);
        }
        STBDXT.nstb_compress_dxt_block(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2), bl ? 1 : 0, n);
    }

    public static native void nstb_compress_bc4_block(long var0, long var2);

    public static void stb_compress_bc4_block(@NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 8);
            Checks.check((Buffer)byteBuffer2, 16);
        }
        STBDXT.nstb_compress_bc4_block(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2));
    }

    public static native void nstb_compress_bc5_block(long var0, long var2);

    public static void stb_compress_bc5_block(@NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 16);
            Checks.check((Buffer)byteBuffer2, 32);
        }
        STBDXT.nstb_compress_bc5_block(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2));
    }

    static {
        LibSTB.initialize();
    }
}

