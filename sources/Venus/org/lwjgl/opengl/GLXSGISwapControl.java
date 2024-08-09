/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXSGISwapControl {
    protected GLXSGISwapControl() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXSwapIntervalSGI);
    }

    @NativeType(value="GLint")
    public static int glXSwapIntervalSGI(int n) {
        long l = GL.getCapabilitiesGLXClient().glXSwapIntervalSGI;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callI(n, l);
    }
}

