/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.LongBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBBindlessTexture {
    public static final int GL_UNSIGNED_INT64_ARB = 5135;

    protected ARBBindlessTexture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetTextureHandleARB, gLCapabilities.glGetTextureSamplerHandleARB, gLCapabilities.glMakeTextureHandleResidentARB, gLCapabilities.glMakeTextureHandleNonResidentARB, gLCapabilities.glGetImageHandleARB, gLCapabilities.glMakeImageHandleResidentARB, gLCapabilities.glMakeImageHandleNonResidentARB, gLCapabilities.glUniformHandleui64ARB, gLCapabilities.glUniformHandleui64vARB, gLCapabilities.glProgramUniformHandleui64ARB, gLCapabilities.glProgramUniformHandleui64vARB, gLCapabilities.glIsTextureHandleResidentARB, gLCapabilities.glIsImageHandleResidentARB, gLCapabilities.glVertexAttribL1ui64ARB, gLCapabilities.glVertexAttribL1ui64vARB, gLCapabilities.glGetVertexAttribLui64vARB);
    }

    @NativeType(value="GLuint64")
    public static native long glGetTextureHandleARB(@NativeType(value="GLuint") int var0);

    @NativeType(value="GLuint64")
    public static native long glGetTextureSamplerHandleARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glMakeTextureHandleResidentARB(@NativeType(value="GLuint64") long var0);

    public static native void glMakeTextureHandleNonResidentARB(@NativeType(value="GLuint64") long var0);

    @NativeType(value="GLuint64")
    public static native long glGetImageHandleARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLboolean") boolean var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4);

    public static native void glMakeImageHandleResidentARB(@NativeType(value="GLuint64") long var0, @NativeType(value="GLenum") int var2);

    public static native void glMakeImageHandleNonResidentARB(@NativeType(value="GLuint64") long var0);

    public static native void glUniformHandleui64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64") long var1);

    public static native void nglUniformHandleui64vARB(int var0, int var1, long var2);

    public static void glUniformHandleui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBBindlessTexture.nglUniformHandleui64vARB(n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniformHandleui64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64") long var2);

    public static native void nglProgramUniformHandleui64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniformHandleui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBBindlessTexture.nglProgramUniformHandleui64vARB(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsTextureHandleResidentARB(@NativeType(value="GLuint64") long var0);

    @NativeType(value="GLboolean")
    public static native boolean glIsImageHandleResidentARB(@NativeType(value="GLuint64") long var0);

    public static native void glVertexAttribL1ui64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64") long var1);

    public static native void nglVertexAttribL1ui64vARB(int var0, long var1);

    public static void glVertexAttribL1ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        ARBBindlessTexture.nglVertexAttribL1ui64vARB(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglGetVertexAttribLui64vARB(int var0, int var1, long var2);

    public static void glGetVertexAttribLui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        ARBBindlessTexture.nglGetVertexAttribLui64vARB(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexAttribLui64ARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            ARBBindlessTexture.nglGetVertexAttribLui64vARB(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glUniformHandleui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glUniformHandleui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length, lArray, l);
    }

    public static void glProgramUniformHandleui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glProgramUniformHandleui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    public static void glVertexAttribL1ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL1ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glGetVertexAttribLui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 *") long[] lArray) {
        long l = GL.getICD().glGetVertexAttribLui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    static {
        GL.initialize();
    }
}

