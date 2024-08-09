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

public class ARBGPUShaderInt64 {
    public static final int GL_INT64_ARB = 5134;
    public static final int GL_UNSIGNED_INT64_ARB = 5135;
    public static final int GL_INT64_VEC2_ARB = 36841;
    public static final int GL_INT64_VEC3_ARB = 36842;
    public static final int GL_INT64_VEC4_ARB = 36843;
    public static final int GL_UNSIGNED_INT64_VEC2_ARB = 36853;
    public static final int GL_UNSIGNED_INT64_VEC3_ARB = 36854;
    public static final int GL_UNSIGNED_INT64_VEC4_ARB = 36855;

    protected ARBGPUShaderInt64() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glUniform1i64ARB, gLCapabilities.glUniform1i64vARB, gLCapabilities.glProgramUniform1i64ARB, gLCapabilities.glProgramUniform1i64vARB, gLCapabilities.glUniform2i64ARB, gLCapabilities.glUniform2i64vARB, gLCapabilities.glProgramUniform2i64ARB, gLCapabilities.glProgramUniform2i64vARB, gLCapabilities.glUniform3i64ARB, gLCapabilities.glUniform3i64vARB, gLCapabilities.glProgramUniform3i64ARB, gLCapabilities.glProgramUniform3i64vARB, gLCapabilities.glUniform4i64ARB, gLCapabilities.glUniform4i64vARB, gLCapabilities.glProgramUniform4i64ARB, gLCapabilities.glProgramUniform4i64vARB, gLCapabilities.glUniform1ui64ARB, gLCapabilities.glUniform1ui64vARB, gLCapabilities.glProgramUniform1ui64ARB, gLCapabilities.glProgramUniform1ui64vARB, gLCapabilities.glUniform2ui64ARB, gLCapabilities.glUniform2ui64vARB, gLCapabilities.glProgramUniform2ui64ARB, gLCapabilities.glProgramUniform2ui64vARB, gLCapabilities.glUniform3ui64ARB, gLCapabilities.glUniform3ui64vARB, gLCapabilities.glProgramUniform3ui64ARB, gLCapabilities.glProgramUniform3ui64vARB, gLCapabilities.glUniform4ui64ARB, gLCapabilities.glUniform4ui64vARB, gLCapabilities.glProgramUniform4ui64ARB, gLCapabilities.glProgramUniform4ui64vARB, gLCapabilities.glGetUniformi64vARB, gLCapabilities.glGetUniformui64vARB, gLCapabilities.glGetnUniformi64vARB, gLCapabilities.glGetnUniformui64vARB);
    }

    public static native void glUniform1i64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint64") long var1);

    public static native void nglUniform1i64vARB(int var0, int var1, long var2);

    public static void glUniform1i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform1i64vARB(n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform1i64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64") long var2);

    public static native void nglProgramUniform1i64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform1i64vARB(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform2i64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint64") long var1, @NativeType(value="GLint64") long var3);

    public static native void nglUniform2i64vARB(int var0, int var1, long var2);

    public static void glUniform2i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform2i64vARB(n, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform2i64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64") long var2, @NativeType(value="GLint64") long var4);

    public static native void nglProgramUniform2i64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform2i64vARB(n, n2, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform3i64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint64") long var1, @NativeType(value="GLint64") long var3, @NativeType(value="GLint64") long var5);

    public static native void nglUniform3i64vARB(int var0, int var1, long var2);

    public static void glUniform3i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform3i64vARB(n, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform3i64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64") long var2, @NativeType(value="GLint64") long var4, @NativeType(value="GLint64") long var6);

    public static native void nglProgramUniform3i64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform3i64vARB(n, n2, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform4i64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint64") long var1, @NativeType(value="GLint64") long var3, @NativeType(value="GLint64") long var5, @NativeType(value="GLint64") long var7);

    public static native void nglUniform4i64vARB(int var0, int var1, long var2);

    public static void glUniform4i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform4i64vARB(n, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform4i64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64") long var2, @NativeType(value="GLint64") long var4, @NativeType(value="GLint64") long var6, @NativeType(value="GLint64") long var8);

    public static native void nglProgramUniform4i64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform4i64vARB(n, n2, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform1ui64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64") long var1);

    public static native void nglUniform1ui64vARB(int var0, int var1, long var2);

    public static void glUniform1ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform1ui64vARB(n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform1ui64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64") long var2);

    public static native void nglProgramUniform1ui64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform1ui64vARB(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform2ui64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64") long var1, @NativeType(value="GLuint64") long var3);

    public static native void nglUniform2ui64vARB(int var0, int var1, long var2);

    public static void glUniform2ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform2ui64vARB(n, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform2ui64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64") long var2, @NativeType(value="GLuint64") long var4);

    public static native void nglProgramUniform2ui64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform2ui64vARB(n, n2, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform3ui64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64") long var1, @NativeType(value="GLuint64") long var3, @NativeType(value="GLuint64") long var5);

    public static native void nglUniform3ui64vARB(int var0, int var1, long var2);

    public static void glUniform3ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform3ui64vARB(n, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform3ui64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64") long var2, @NativeType(value="GLuint64") long var4, @NativeType(value="GLuint64") long var6);

    public static native void nglProgramUniform3ui64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform3ui64vARB(n, n2, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform4ui64ARB(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64") long var1, @NativeType(value="GLuint64") long var3, @NativeType(value="GLuint64") long var5, @NativeType(value="GLuint64") long var7);

    public static native void nglUniform4ui64vARB(int var0, int var1, long var2);

    public static void glUniform4ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglUniform4ui64vARB(n, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform4ui64ARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64") long var2, @NativeType(value="GLuint64") long var4, @NativeType(value="GLuint64") long var6, @NativeType(value="GLuint64") long var8);

    public static native void nglProgramUniform4ui64vARB(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglProgramUniform4ui64vARB(n, n2, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglGetUniformi64vARB(int var0, int var1, long var2);

    public static void glGetUniformi64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        ARBGPUShaderInt64.nglGetUniformi64vARB(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetUniformi64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            ARBGPUShaderInt64.nglGetUniformi64vARB(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetUniformui64vARB(int var0, int var1, long var2);

    public static void glGetUniformui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        ARBGPUShaderInt64.nglGetUniformui64vARB(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetUniformui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            ARBGPUShaderInt64.nglGetUniformui64vARB(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnUniformi64vARB(int var0, int var1, int var2, long var3);

    public static void glGetnUniformi64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglGetnUniformi64vARB(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetnUniformi64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            ARBGPUShaderInt64.nglGetnUniformi64vARB(n, n2, 1, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnUniformui64vARB(int var0, int var1, int var2, long var3);

    public static void glGetnUniformui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 *") LongBuffer longBuffer) {
        ARBGPUShaderInt64.nglGetnUniformui64vARB(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetnUniformui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            ARBGPUShaderInt64.nglGetnUniformui64vARB(n, n2, 1, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glUniform1i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glUniform1i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length, lArray, l);
    }

    public static void glProgramUniform1i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glProgramUniform1i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    public static void glUniform2i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glUniform2i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 1, lArray, l);
    }

    public static void glProgramUniform2i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glProgramUniform2i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 1, lArray, l);
    }

    public static void glUniform3i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glUniform3i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length / 3, lArray, l);
    }

    public static void glProgramUniform3i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glProgramUniform3i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length / 3, lArray, l);
    }

    public static void glUniform4i64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glUniform4i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 2, lArray, l);
    }

    public static void glProgramUniform4i64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glProgramUniform4i64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 2, lArray, l);
    }

    public static void glUniform1ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glUniform1ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length, lArray, l);
    }

    public static void glProgramUniform1ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform1ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    public static void glUniform2ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glUniform2ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 1, lArray, l);
    }

    public static void glProgramUniform2ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform2ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 1, lArray, l);
    }

    public static void glUniform3ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glUniform3ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length / 3, lArray, l);
    }

    public static void glProgramUniform3ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform3ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length / 3, lArray, l);
    }

    public static void glUniform4ui64vARB(@NativeType(value="GLint") int n, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glUniform4ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 2, lArray, l);
    }

    public static void glProgramUniform4ui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform4ui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 2, lArray, l);
    }

    public static void glGetUniformi64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glGetUniformi64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glGetUniformui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 *") long[] lArray) {
        long l = GL.getICD().glGetUniformui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glGetnUniformi64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glGetnUniformi64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    public static void glGetnUniformui64vARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64 *") long[] lArray) {
        long l = GL.getICD().glGetnUniformui64vARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    static {
        GL.initialize();
    }
}

