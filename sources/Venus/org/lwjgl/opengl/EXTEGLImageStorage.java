/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTEGLImageStorage {
    protected EXTEGLImageStorage() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glEGLImageTargetTexStorageEXT, gLCapabilities.hasDSA(set) ? gLCapabilities.glEGLImageTargetTextureStorageEXT : -1L);
    }

    public static native void nglEGLImageTargetTexStorageEXT(int var0, long var1, long var3);

    public static void glEGLImageTargetTexStorageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLeglImageOES") long l, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNTSafe(intBuffer);
        }
        EXTEGLImageStorage.nglEGLImageTargetTexStorageEXT(n, l, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static native void nglEGLImageTargetTextureStorageEXT(int var0, long var1, long var3);

    public static void glEGLImageTargetTextureStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLeglImageOES") long l, @Nullable @NativeType(value="int const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNTSafe(intBuffer);
        }
        EXTEGLImageStorage.nglEGLImageTargetTextureStorageEXT(n, l, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glEGLImageTargetTexStorageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLeglImageOES") long l, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getICD().glEGLImageTargetTexStorageEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        JNI.callPPV(n, l, nArray, l2);
    }

    public static void glEGLImageTargetTextureStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLeglImageOES") long l, @Nullable @NativeType(value="int const *") int[] nArray) {
        long l2 = GL.getICD().glEGLImageTargetTextureStorageEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkNTSafe(nArray);
        }
        JNI.callPPV(n, l, nArray, l2);
    }

    static {
        GL.initialize();
    }
}

