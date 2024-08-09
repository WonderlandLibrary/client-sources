/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.LongBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessTexture {
    protected NVBindlessTexture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetTextureHandleNV, gLCapabilities.glGetTextureSamplerHandleNV, gLCapabilities.glMakeTextureHandleResidentNV, gLCapabilities.glMakeTextureHandleNonResidentNV, gLCapabilities.glGetImageHandleNV, gLCapabilities.glMakeImageHandleResidentNV, gLCapabilities.glMakeImageHandleNonResidentNV, gLCapabilities.glUniformHandleui64NV, gLCapabilities.glUniformHandleui64vNV, gLCapabilities.glProgramUniformHandleui64NV, gLCapabilities.glProgramUniformHandleui64vNV, gLCapabilities.glIsTextureHandleResidentNV, gLCapabilities.glIsImageHandleResidentNV);
    }

    @NativeType(value="GLuint64")
    public static native long glGetTextureHandleNV(@NativeType(value="GLuint") int var0);

    @NativeType(value="GLuint64")
    public static native long glGetTextureSamplerHandleNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glMakeTextureHandleResidentNV(@NativeType(value="GLuint64") long var0);

    public static native void glMakeTextureHandleNonResidentNV(@NativeType(value="GLuint64") long var0);

    @NativeType(value="GLuint64")
    public static native long glGetImageHandleNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLboolean") boolean var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4);

    public static native void glMakeImageHandleResidentNV(@NativeType(value="GLuint64") long var0, @NativeType(value="GLenum") int var2);

    public static native void glMakeImageHandleNonResidentNV(@NativeType(value="GLuint64") long var0);

    public static native void glUniformHandleui64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64") long var1);

    public static native void nglUniformHandleui64vNV(int var0, int var1, long var2);

    public static void glUniformHandleui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        NVBindlessTexture.nglUniformHandleui64vNV(n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniformHandleui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64") long var2);

    public static native void nglProgramUniformHandleui64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniformHandleui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        NVBindlessTexture.nglProgramUniformHandleui64vNV(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsTextureHandleResidentNV(@NativeType(value="GLuint64") long var0);

    @NativeType(value="GLboolean")
    public static native boolean glIsImageHandleResidentNV(@NativeType(value="GLuint64") long var0);

    public static void glUniformHandleui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glUniformHandleui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length, lArray, l);
    }

    public static void glProgramUniformHandleui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glProgramUniformHandleui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    static {
        GL.initialize();
    }
}

