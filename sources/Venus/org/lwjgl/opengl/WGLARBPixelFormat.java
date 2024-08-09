/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLARBPixelFormat {
    public static final int WGL_NUMBER_PIXEL_FORMATS_ARB = 8192;
    public static final int WGL_DRAW_TO_WINDOW_ARB = 8193;
    public static final int WGL_DRAW_TO_BITMAP_ARB = 8194;
    public static final int WGL_ACCELERATION_ARB = 8195;
    public static final int WGL_NEED_PALETTE_ARB = 8196;
    public static final int WGL_NEED_SYSTEM_PALETTE_ARB = 8197;
    public static final int WGL_SWAP_LAYER_BUFFERS_ARB = 8198;
    public static final int WGL_SWAP_METHOD_ARB = 8199;
    public static final int WGL_NUMBER_OVERLAYS_ARB = 8200;
    public static final int WGL_NUMBER_UNDERLAYS_ARB = 8201;
    public static final int WGL_TRANSPARENT_ARB = 8202;
    public static final int WGL_TRANSPARENT_RED_VALUE_ARB = 8247;
    public static final int WGL_TRANSPARENT_GREEN_VALUE_ARB = 8248;
    public static final int WGL_TRANSPARENT_BLUE_VALUE_ARB = 8249;
    public static final int WGL_TRANSPARENT_ALPHA_VALUE_ARB = 8250;
    public static final int WGL_TRANSPARENT_INDEX_VALUE_ARB = 8251;
    public static final int WGL_SHARE_DEPTH_ARB = 8204;
    public static final int WGL_SHARE_STENCIL_ARB = 8205;
    public static final int WGL_SHARE_ACCUM_ARB = 8206;
    public static final int WGL_SUPPORT_GDI_ARB = 8207;
    public static final int WGL_SUPPORT_OPENGL_ARB = 8208;
    public static final int WGL_DOUBLE_BUFFER_ARB = 8209;
    public static final int WGL_STEREO_ARB = 8210;
    public static final int WGL_PIXEL_TYPE_ARB = 8211;
    public static final int WGL_COLOR_BITS_ARB = 8212;
    public static final int WGL_RED_BITS_ARB = 8213;
    public static final int WGL_RED_SHIFT_ARB = 8214;
    public static final int WGL_GREEN_BITS_ARB = 8215;
    public static final int WGL_GREEN_SHIFT_ARB = 8216;
    public static final int WGL_BLUE_BITS_ARB = 8217;
    public static final int WGL_BLUE_SHIFT_ARB = 8218;
    public static final int WGL_ALPHA_BITS_ARB = 8219;
    public static final int WGL_ALPHA_SHIFT_ARB = 8220;
    public static final int WGL_ACCUM_BITS_ARB = 8221;
    public static final int WGL_ACCUM_RED_BITS_ARB = 8222;
    public static final int WGL_ACCUM_GREEN_BITS_ARB = 8223;
    public static final int WGL_ACCUM_BLUE_BITS_ARB = 8224;
    public static final int WGL_ACCUM_ALPHA_BITS_ARB = 8225;
    public static final int WGL_DEPTH_BITS_ARB = 8226;
    public static final int WGL_STENCIL_BITS_ARB = 8227;
    public static final int WGL_AUX_BUFFERS_ARB = 8228;
    public static final int WGL_NO_ACCELERATION_ARB = 8229;
    public static final int WGL_GENERIC_ACCELERATION_ARB = 8230;
    public static final int WGL_FULL_ACCELERATION_ARB = 8231;
    public static final int WGL_SWAP_EXCHANGE_ARB = 8232;
    public static final int WGL_SWAP_COPY_ARB = 8233;
    public static final int WGL_SWAP_UNDEFINED_ARB = 8234;
    public static final int WGL_TYPE_RGBA_ARB = 8235;
    public static final int WGL_TYPE_COLORINDEX_ARB = 8236;

    protected WGLARBPixelFormat() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglGetPixelFormatAttribivARB, wGLCapabilities.wglGetPixelFormatAttribfvARB, wGLCapabilities.wglChoosePixelFormatARB);
    }

    public static int nwglGetPixelFormatAttribivARB(long l, int n, int n2, int n3, long l2, long l3) {
        long l4 = GL.getCapabilitiesWGL().wglGetPixelFormatAttribivARB;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPI(l, n, n2, n3, l2, l3, l4);
    }

    @NativeType(value="BOOL")
    public static boolean wglGetPixelFormatAttribivARB(@NativeType(value="HDC") long l, int n, int n2, @NativeType(value="int const *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        return WGLARBPixelFormat.nwglGetPixelFormatAttribivARB(l, n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2)) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="BOOL")
    public static boolean wglGetPixelFormatAttribiARB(@NativeType(value="HDC") long l, int n, int n2, @NativeType(value="int const *") int n3, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer2 = memoryStack.ints(n3);
            boolean bl = WGLARBPixelFormat.nwglGetPixelFormatAttribivARB(l, n, n2, 1, MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer)) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static int nwglGetPixelFormatAttribfvARB(long l, int n, int n2, int n3, long l2, long l3) {
        long l4 = GL.getCapabilitiesWGL().wglGetPixelFormatAttribfvARB;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPI(l, n, n2, n3, l2, l3, l4);
    }

    @NativeType(value="BOOL")
    public static boolean wglGetPixelFormatAttribfvARB(@NativeType(value="HDC") long l, int n, int n2, @NativeType(value="int const *") IntBuffer intBuffer, @NativeType(value="FLOAT *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, intBuffer.remaining());
        }
        return WGLARBPixelFormat.nwglGetPixelFormatAttribfvARB(l, n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(floatBuffer)) != 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="BOOL")
    public static boolean wglGetPixelFormatAttribfARB(@NativeType(value="HDC") long l, int n, int n2, @NativeType(value="int const *") int n3, @NativeType(value="FLOAT *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n3);
            boolean bl = WGLARBPixelFormat.nwglGetPixelFormatAttribfvARB(l, n, n2, 1, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(floatBuffer)) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static int nwglChoosePixelFormatARB(long l, long l2, long l3, int n, long l4, long l5) {
        long l6 = GL.getCapabilitiesWGL().wglChoosePixelFormatARB;
        if (Checks.CHECKS) {
            Checks.check(l6);
            Checks.check(l);
        }
        return JNI.callPPPPPI(l, l2, l3, n, l4, l5, l6);
    }

    @NativeType(value="BOOL")
    public static boolean wglChoosePixelFormatARB(@NativeType(value="HDC") long l, @Nullable @NativeType(value="int const *") IntBuffer intBuffer, @Nullable @NativeType(value="FLOAT const *") FloatBuffer floatBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="UINT *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
            Checks.checkNTSafe(floatBuffer);
            Checks.check((Buffer)intBuffer3, 1);
        }
        return WGLARBPixelFormat.nwglChoosePixelFormatARB(l, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(floatBuffer), intBuffer2.remaining(), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3)) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglGetPixelFormatAttribivARB(@NativeType(value="HDC") long l, int n, int n2, @NativeType(value="int const *") int[] nArray, @NativeType(value="int *") int[] nArray2) {
        long l2 = GL.getCapabilitiesWGL().wglGetPixelFormatAttribivARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray2, nArray.length);
        }
        return JNI.callPPPI(l, n, n2, nArray.length, nArray, nArray2, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglGetPixelFormatAttribfvARB(@NativeType(value="HDC") long l, int n, int n2, @NativeType(value="int const *") int[] nArray, @NativeType(value="FLOAT *") float[] fArray) {
        long l2 = GL.getCapabilitiesWGL().wglGetPixelFormatAttribfvARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(fArray, nArray.length);
        }
        return JNI.callPPPI(l, n, n2, nArray.length, nArray, fArray, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglChoosePixelFormatARB(@NativeType(value="HDC") long l, @Nullable @NativeType(value="int const *") int[] nArray, @Nullable @NativeType(value="FLOAT const *") float[] fArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="UINT *") int[] nArray3) {
        long l2 = GL.getCapabilitiesWGL().wglChoosePixelFormatARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
            Checks.checkNTSafe(fArray);
            Checks.check(nArray3, 1);
        }
        return JNI.callPPPPPI(l, nArray, fArray, nArray2.length, nArray2, nArray3, l2) != 0;
    }
}

