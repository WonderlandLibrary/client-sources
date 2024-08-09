/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLX;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLX11
extends GLX {
    public static final int GLX_VENDOR = 1;
    public static final int GLX_VERSION = 2;
    public static final int GLX_EXTENSIONS = 3;

    protected GLX11() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXQueryExtensionsString, gLXCapabilities.glXGetClientString, gLXCapabilities.glXQueryServerString);
    }

    public static long nglXQueryExtensionsString(long l, int n) {
        long l2 = GL.getCapabilitiesGLXClient().glXQueryExtensionsString;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, n, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String glXQueryExtensionsString(@NativeType(value="Display *") long l, int n) {
        long l2 = GLX11.nglXQueryExtensionsString(l, n);
        return MemoryUtil.memASCIISafe(l2);
    }

    public static long nglXGetClientString(long l, int n) {
        long l2 = GL.getCapabilitiesGLXClient().glXGetClientString;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, n, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String glXGetClientString(@NativeType(value="Display *") long l, int n) {
        long l2 = GLX11.nglXGetClientString(l, n);
        return MemoryUtil.memASCIISafe(l2);
    }

    public static long nglXQueryServerString(long l, int n, int n2) {
        long l2 = GL.getCapabilitiesGLXClient().glXQueryServerString;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, n, n2, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String glXQueryServerString(@NativeType(value="Display *") long l, int n, int n2) {
        long l2 = GLX11.nglXQueryServerString(l, n, n2);
        return MemoryUtil.memASCIISafe(l2);
    }
}

