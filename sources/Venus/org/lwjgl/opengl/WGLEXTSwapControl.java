/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class WGLEXTSwapControl {
    protected WGLEXTSwapControl() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglSwapIntervalEXT, wGLCapabilities.wglGetSwapIntervalEXT);
    }

    @NativeType(value="BOOL")
    public static boolean wglSwapIntervalEXT(int n) {
        long l = GL.getCapabilitiesWGL().wglSwapIntervalEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callI(n, l) != 0;
    }

    public static int wglGetSwapIntervalEXT() {
        long l = GL.getCapabilitiesWGL().wglGetSwapIntervalEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callI(l);
    }
}

