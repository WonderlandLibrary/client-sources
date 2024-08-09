/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.WGLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class WGLARBExtensionsString {
    protected WGLARBExtensionsString() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(WGLCapabilities wGLCapabilities) {
        return Checks.checkFunctions(wGLCapabilities.wglGetExtensionsStringARB);
    }

    public static long nwglGetExtensionsStringARB(long l) {
        long l2 = GL.getCapabilitiesWGL().wglGetExtensionsStringARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
        }
        return JNI.callPP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String wglGetExtensionsStringARB(@NativeType(value="HDC") long l) {
        long l2 = WGLARBExtensionsString.nwglGetExtensionsStringARB(l);
        return MemoryUtil.memASCIISafe(l2);
    }
}

