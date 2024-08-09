/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLXARBCreateContext {
    public static final int GLX_CONTEXT_MAJOR_VERSION_ARB = 8337;
    public static final int GLX_CONTEXT_MINOR_VERSION_ARB = 8338;
    public static final int GLX_CONTEXT_FLAGS_ARB = 8340;
    public static final int GLX_CONTEXT_DEBUG_BIT_ARB = 1;
    public static final int GLX_CONTEXT_FORWARD_COMPATIBLE_BIT_ARB = 2;

    protected GLXARBCreateContext() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLXCapabilities gLXCapabilities) {
        return Checks.checkFunctions(gLXCapabilities.glXCreateContextAttribsARB);
    }

    public static long nglXCreateContextAttribsARB(long l, long l2, long l3, int n, long l4) {
        long l5 = GL.getCapabilitiesGLXClient().glXCreateContextAttribsARB;
        if (Checks.CHECKS) {
            Checks.check(l5);
            Checks.check(l);
            Checks.check(l2);
        }
        return JNI.callPPPPP(l, l2, l3, n, l4, l5);
    }

    @NativeType(value="GLXContext")
    public static long glXCreateContextAttribsARB(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="GLXContext") long l3, @NativeType(value="Bool") boolean bl, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNTSafe(intBuffer);
        }
        return GLXARBCreateContext.nglXCreateContextAttribsARB(l, l2, l3, bl ? 1 : 0, MemoryUtil.memAddressSafe(intBuffer));
    }

    @NativeType(value="GLXContext")
    public static long glXCreateContextAttribsARB(@NativeType(value="Display *") long l, @NativeType(value="GLXFBConfig") long l2, @NativeType(value="GLXContext") long l3, @NativeType(value="Bool") boolean bl, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l4 = GL.getCapabilitiesGLXClient().glXCreateContextAttribsARB;
        if (Checks.CHECKS) {
            Checks.check(l4);
            Checks.check(l);
            Checks.check(l2);
            Checks.checkNTSafe(nArray);
        }
        return JNI.callPPPPP(l, l2, l3, bl ? 1 : 0, nArray, l4);
    }
}

