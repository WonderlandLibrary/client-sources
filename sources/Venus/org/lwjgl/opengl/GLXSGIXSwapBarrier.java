/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXSGIXSwapBarrier {
    protected GLXSGIXSwapBarrier() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXBindSwapBarrierSGIX, gLXCapabilities.glXQueryMaxSwapBarriersSGIX);
    }

    public static void glXBindSwapBarrierSGIX(@NativeType(value="Display *") long l, @NativeType(value="GLXDrawable") long l2, int n) {
        long l3 = GL.getCapabilitiesGLXClient().glXBindSwapBarrierSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, n, l3);
    }

    public static int nglXQueryMaxSwapBarriersSGIX(long l, int n, long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXQueryMaxSwapBarriersSGIX;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPI(l, n, l2, l3);
    }

    @NativeType(value="Bool")
    public static boolean glXQueryMaxSwapBarriersSGIX(@NativeType(value="Display *") long l, int n, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLXSGIXSwapBarrier.nglXQueryMaxSwapBarriersSGIX(l, n, MemoryUtil.memAddress(intBuffer)) != 0;
    }

    @NativeType(value="Bool")
    public static boolean glXQueryMaxSwapBarriersSGIX(@NativeType(value="Display *") long l, int n, @NativeType(value="int *") int[] nArray) {
        long l2 = GL.getCapabilitiesGLXClient().glXQueryMaxSwapBarriersSGIX;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        return JNI.callPPI(l, n, nArray, l2) != 0;
    }
}

