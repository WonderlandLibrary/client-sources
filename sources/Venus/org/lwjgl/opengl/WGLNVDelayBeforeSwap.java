/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLNVDelayBeforeSwap {
    protected WGLNVDelayBeforeSwap() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglDelayBeforeSwapNV);
    }

    @NativeType(value="BOOL")
    public static boolean wglDelayBeforeSwapNV(@NativeType(value="HDC") long l, @NativeType(value="GLfloat") float f) {
        long l2 = GL.getCapabilitiesWGL().wglDelayBeforeSwapNV;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPI(l, f, l2) != 0;
    }
}

