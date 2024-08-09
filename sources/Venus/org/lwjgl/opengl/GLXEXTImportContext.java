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

public class GLXEXTImportContext {
    public static final int GLX_SHARE_CONTEXT_EXT = 32778;
    public static final int GLX_VISUAL_ID_EXT = 32779;
    public static final int GLX_SCREEN_EXT = 32780;

    protected GLXEXTImportContext() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXGetCurrentDisplayEXT, gLXCapabilities.glXQueryContextInfoEXT, gLXCapabilities.glXGetContextIDEXT, gLXCapabilities.glXImportContextEXT, gLXCapabilities.glXFreeContextEXT);
    }

    @NativeType(value="Display *")
    public static long glXGetCurrentDisplayEXT() {
        long l = GL.getCapabilitiesGLXClient().glXGetCurrentDisplayEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.callP(l);
    }

    public static int nglXQueryContextInfoEXT(long l, long l2, int n, long l3) {
        long l4 = GL.getCapabilitiesGLXClient().glXQueryContextInfoEXT;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPI(l, l2, n, l3, l4);
    }

    public static int glXQueryContextInfoEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, int n, @NativeType(value="int *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        return GLXEXTImportContext.nglXQueryContextInfoEXT(l, l2, n, MemoryUtil.memAddress(intBuffer));
    }

    @NativeType(value="GLXContextID")
    public static long glXGetContextIDEXT(@NativeType(value="GLXContext const") long l) {
        long l2 = GL.getCapabilitiesGLXClient().glXGetContextIDEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, l2);
    }

    @NativeType(value="GLXContext")
    public static long glXImportContextEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXContextID") long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXImportContextEXT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
        }
        return JNI.callPPP(l, l2, l3);
    }

    public static void glXFreeContextEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2) {
        long l3 = GL.getCapabilitiesGLXClient().glXFreeContextEXT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
        }
        JNI.callPPV(l, l2, l3);
    }

    public static int glXQueryContextInfoEXT(@NativeType(value="Display *") long l, @NativeType(value="GLXContext") long l2, int n, @NativeType(value="int *") int[] nArray) {
        long l3 = GL.getCapabilitiesGLXClient().glXQueryContextInfoEXT;
        if (Checks.CHECKS) {
            Checks.check(l3);
            Checks.check(l);
            Checks.check(l2);
            Checks.check(nArray, 1);
        }
        return JNI.callPPPI(l, l2, n, nArray, l3);
    }
}

