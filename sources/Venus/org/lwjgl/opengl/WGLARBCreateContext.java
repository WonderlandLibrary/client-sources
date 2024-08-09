/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLARBCreateContext {
    public static final int WGL_CONTEXT_MAJOR_VERSION_ARB = 8337;
    public static final int WGL_CONTEXT_MINOR_VERSION_ARB = 8338;
    public static final int WGL_CONTEXT_LAYER_PLANE_ARB = 8339;
    public static final int WGL_CONTEXT_FLAGS_ARB = 8340;
    public static final int WGL_CONTEXT_DEBUG_BIT_ARB = 1;
    public static final int WGL_CONTEXT_FORWARD_COMPATIBLE_BIT_ARB = 2;
    public static final int ERROR_INVALID_VERSION_ARB = 8341;

    protected WGLARBCreateContext() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglCreateContextAttribsARB);
    }

    public static long nwglCreateContextAttribsARB(long l, long l2, long l3) {
        long l4 = GL.getCapabilitiesWGL().wglCreateContextAttribsARB;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
        }
        return JNI.callPPPP(l, l2, l3, l4);
    }

    @NativeType(value="HGLRC")
    public static long wglCreateContextAttribsARB(@NativeType(value="HDC") long l, @NativeType(value="HGLRC") long l2, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return WGLARBCreateContext.nwglCreateContextAttribsARB(l, l2, MemoryUtil.memAddressSafe(intBuffer));
    }

    @NativeType(value="HGLRC")
    public static long wglCreateContextAttribsARB(@NativeType(value="HDC") long l, @NativeType(value="HGLRC") long l2, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l3 = GL.getCapabilitiesWGL().wglCreateContextAttribsARB;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPPP(l, l2, nArray, l3);
    }
}

