/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.stb.LibSTB;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class STBEasyFont {
    protected STBEasyFont() {
        throw new UnsupportedOperationException();
    }

    public static native int nstb_easy_font_width(long var0);

    public static int stb_easy_font_width(@NativeType(value="char *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return STBEasyFont.nstb_easy_font_width(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int stb_easy_font_width(@NativeType(value="char *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = STBEasyFont.nstb_easy_font_width(l);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstb_easy_font_height(long var0);

    public static int stb_easy_font_height(@NativeType(value="char *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return STBEasyFont.nstb_easy_font_height(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int stb_easy_font_height(@NativeType(value="char *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = STBEasyFont.nstb_easy_font_height(l);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native int nstb_easy_font_print(float var0, float var1, long var2, long var4, long var6, int var8);

    public static int stb_easy_font_print(float f, float f2, @NativeType(value="char *") ByteBuffer byteBuffer, @Nullable @NativeType(value="unsigned char *") ByteBuffer byteBuffer2, @NativeType(value="void *") ByteBuffer byteBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.checkSafe((Buffer)byteBuffer2, 4);
        }
        return STBEasyFont.nstb_easy_font_print(f, f2, MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), MemoryUtil.memAddress(byteBuffer3), byteBuffer3.remaining());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int stb_easy_font_print(float f, float f2, @NativeType(value="char *") CharSequence charSequence, @Nullable @NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="void *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)byteBuffer, 4);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = STBEasyFont.nstb_easy_font_print(f, f2, l, MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddress(byteBuffer2), byteBuffer2.remaining());
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void stb_easy_font_spacing(float var0);

    static {
        LibSTB.initialize();
    }
}

