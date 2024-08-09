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

public class NVScissorExclusive {
    public static final int GL_SCISSOR_TEST_EXCLUSIVE_NV = 38229;
    public static final int GL_SCISSOR_BOX_EXCLUSIVE_NV = 38230;

    protected NVScissorExclusive() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glScissorExclusiveArrayvNV, gLCapabilities.glScissorExclusiveNV);
    }

    public static native void nglScissorExclusiveArrayvNV(int var0, int var1, long var2);

    public static void glScissorExclusiveArrayvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        NVScissorExclusive.nglScissorExclusiveArrayvNV(n, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glScissorExclusiveNV(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static void glScissorExclusiveArrayvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glScissorExclusiveArrayvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 2, nArray, l);
    }

    static {
        GL.initialize();
    }
}

