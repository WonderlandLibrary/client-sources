/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXEXTSwapControl {
    public static final int GLX_SWAP_INTERVAL_EXT = 8433;
    public static final int GLX_MAX_SWAP_INTERVAL_EXT = 8434;

    protected GLXEXTSwapControl() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXSwapIntervalEXT);
    }

    public static void glXSwapIntervalEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n) {
        long l3 = GL.getCapabilitiesGLXClient().glXSwapIntervalEXT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, n, l3);
    }
}

