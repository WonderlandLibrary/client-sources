/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLARBBufferRegion {
    public static final int WGL_FRONT_COLOR_BUFFER_BIT_ARB = 1;
    public static final int WGL_BACK_COLOR_BUFFER_BIT_ARB = 2;
    public static final int WGL_DEPTH_BUFFER_BIT_ARB = 4;
    public static final int WGL_STENCIL_BUFFER_BIT_ARB = 8;

    protected WGLARBBufferRegion() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglCreateBufferRegionARB, wGLCapabilities.wglDeleteBufferRegionARB, wGLCapabilities.wglSaveBufferRegionARB, wGLCapabilities.wglRestoreBufferRegionARB);
    }

    @NativeType(value="HANDLE")
    public static long wglCreateBufferRegionARB(@NativeType(value="HDC") long l, int n, @NativeType(value="UINT") int n2) {
        long l2 = GL.getCapabilitiesWGL().wglCreateBufferRegionARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, n, n2, l2);
    }

    @NativeType(value="VOID")
    public static void wglDeleteBufferRegionARB(@NativeType(value="HANDLE") long l) {
        long l2 = GL.getCapabilitiesWGL().wglDeleteBufferRegionARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        JNI.callPV(l, l2);
    }

    @NativeType(value="BOOL")
    public static boolean wglSaveBufferRegionARB(@NativeType(value="HANDLE") long l, int n, int n2, int n3, int n4) {
        long l2 = GL.getCapabilitiesWGL().wglSaveBufferRegionARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, n2, n3, n4, l2) != 0;
    }

    @NativeType(value="BOOL")
    public static boolean wglRestoreBufferRegionARB(@NativeType(value="HANDLE") long l, int n, int n2, int n3, int n4, int n5, int n6) {
        long l2 = GL.getCapabilitiesWGL().wglRestoreBufferRegionARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, n, n2, n3, n4, n5, n6, l2) != 0;
    }
}

