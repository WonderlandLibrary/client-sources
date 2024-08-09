/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLARBMakeCurrentRead {
    public static final int ERROR_INVALID_PIXEL_TYPE_ARB = 8259;
    public static final int ERROR_INCOMPATIBLE_DEVICE_CONTEXTS_ARB = 8276;

    protected WGLARBMakeCurrentRead() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglMakeContextCurrentARB, wGLCapabilities.wglGetCurrentReadDCARB);
    }

    @NativeType(value="BOOL")
    public static boolean wglMakeContextCurrentARB(@NativeType(value="HDC") long l, @NativeType(value="HDC") long l2, @NativeType(value="HGLRC") long l3) {
        long l4 = GL.getCapabilitiesWGL().wglMakeContextCurrentARB;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(l3);
        }
        return JNI.callPPPI(l, l2, l3, l4) != 0;
    }

    @NativeType(value="HDC")
    public static long wglGetCurrentReadDCARB() {
        long l = GL.getCapabilitiesWGL().wglGetCurrentReadDCARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(l);
    }
}

