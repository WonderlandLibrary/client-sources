/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLXSGIMakeCurrentRead {
    protected GLXSGIMakeCurrentRead() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXMakeCurrentReadSGI, gLXCapabilities.glXGetCurrentReadDrawableSGI);
    }

    @NativeType(value="Bool")
    public static boolean glXMakeCurrentReadSGI(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, @NativeType(value="GLXDrawable") long l3, @NativeType(value="GLXContext") long l4) {
        long l5 = GL.getCapabilitiesGLXClient().glXMakeCurrentReadSGI;
        if (Checks.CHECKS) {
            Checks.check(l5);
            Checks.check(l);
        }
        return JNI.callPPPPI(l, l2, l3, l4, l5) != 0;
    }

    @NativeType(value="GLXDrawable")
    public static long glXGetCurrentReadDrawableSGI() {
        long l = GL.getCapabilitiesGLXClient().glXGetCurrentReadDrawableSGI;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(l);
    }
}

