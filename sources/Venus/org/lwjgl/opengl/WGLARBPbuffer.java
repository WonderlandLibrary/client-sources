/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLARBPbuffer {
    public static final int WGL_DRAW_TO_PBUFFER_ARB = 8237;
    public static final int WGL_MAX_PBUFFER_PIXELS_ARB = 8238;
    public static final int WGL_MAX_PBUFFER_WIDTH_ARB = 8239;
    public static final int WGL_MAX_PBUFFER_HEIGHT_ARB = 8240;
    public static final int WGL_PBUFFER_LARGEST_ARB = 8243;
    public static final int WGL_PBUFFER_WIDTH_ARB = 8244;
    public static final int WGL_PBUFFER_HEIGHT_ARB = 8245;
    public static final int WGL_PBUFFER_LOST_ARB = 8246;

    protected WGLARBPbuffer() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglCreatePbufferARB, wGLCapabilities.wglGetPbufferDCARB, wGLCapabilities.wglReleasePbufferDCARB, wGLCapabilities.wglDestroyPbufferARB, wGLCapabilities.wglQueryPbufferARB);
    }

    public static long nwglCreatePbufferARB(long l, int n, int n2, int n3, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglCreatePbufferARB;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPP(l, n, n2, n3, l2, l3);
    }

    @NativeType(value="HPBUFFERARB")
    public static long wglCreatePbufferARB(@NativeType(value="HDC") long l, int n, int n2, int n3, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return WGLARBPbuffer.nwglCreatePbufferARB(l, n, n2, n3, MemoryUtil.memAddressSafe(intBuffer));
    }

    @NativeType(value="HDC")
    public static long wglGetPbufferDCARB(@NativeType(value="HPBUFFERARB") long l) {
        long l2 = GL.getCapabilitiesWGL().wglGetPbufferDCARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, l2);
    }

    public static int wglReleasePbufferDCARB(@NativeType(value="HPBUFFERARB") long l, @NativeType(value="HDC") long l2) {
        long l3 = GL.getCapabilitiesWGL().wglReleasePbufferDCARB;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPI(l, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglDestroyPbufferARB(@NativeType(value="HPBUFFERARB") long l) {
        long l2 = GL.getCapabilitiesWGL().wglDestroyPbufferARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, l2) != 0;
    }

    public static int nwglQueryPbufferARB(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesWGL().wglQueryPbufferARB;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="BOOL")
    public static boolean wglQueryPbufferARB(@NativeType(value="HPBUFFERARB") long l, int n, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return WGLARBPbuffer.nwglQueryPbufferARB(l, n, MemoryUtil.memAddress(intBuffer)) != 0;
    }

    @NativeType(value="HPBUFFERARB")
    public static long wglCreatePbufferARB(@NativeType(value="HDC") long l, int n, int n2, int n3, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getCapabilitiesWGL().wglCreatePbufferARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPP(l, n, n2, n3, nArray, l2);
    }

    @NativeType(value="BOOL")
    public static boolean wglQueryPbufferARB(@NativeType(value="HPBUFFERARB") long l, int n, @NativeType(value="int *") int[] nArray) {
        long l2 = GL.getCapabilitiesWGL().wglQueryPbufferARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        return JNI.callPPI(l, n, nArray, l2) != 0;
    }
}

