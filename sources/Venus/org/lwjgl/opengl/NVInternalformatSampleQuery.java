/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVInternalformatSampleQuery {
    public static final int GL_MULTISAMPLES_NV = 37745;
    public static final int GL_SUPERSAMPLE_SCALE_X_NV = 37746;
    public static final int GL_SUPERSAMPLE_SCALE_Y_NV = 37747;
    public static final int GL_CONFORMANT_NV = 37748;

    protected NVInternalformatSampleQuery() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetInternalformatSampleivNV);
    }

    public static native void nglGetInternalformatSampleivNV(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glGetInternalformatSampleivNV(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") IntBuffer intBuffer) {
        NVInternalformatSampleQuery.nglGetInternalformatSampleivNV(n, n2, n3, n4, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetInternalformatSampleivNV(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetInternalformatSampleivNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray.length, nArray, l);
    }

    static {
        GL.initialize();
    }
}

