/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.LongBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.NVShaderBufferLoad;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVGPUShader5 {
    public static final int GL_INT64_NV = 5134;
    public static final int GL_UNSIGNED_INT64_NV = 5135;
    public static final int GL_INT8_NV = 36832;
    public static final int GL_INT8_VEC2_NV = 36833;
    public static final int GL_INT8_VEC3_NV = 36834;
    public static final int GL_INT8_VEC4_NV = 36835;
    public static final int GL_INT16_NV = 36836;
    public static final int GL_INT16_VEC2_NV = 36837;
    public static final int GL_INT16_VEC3_NV = 36838;
    public static final int GL_INT16_VEC4_NV = 36839;
    public static final int GL_INT64_VEC2_NV = 36841;
    public static final int GL_INT64_VEC3_NV = 36842;
    public static final int GL_INT64_VEC4_NV = 36843;
    public static final int GL_UNSIGNED_INT8_NV = 36844;
    public static final int GL_UNSIGNED_INT8_VEC2_NV = 36845;
    public static final int GL_UNSIGNED_INT8_VEC3_NV = 36846;
    public static final int GL_UNSIGNED_INT8_VEC4_NV = 36847;
    public static final int GL_UNSIGNED_INT16_NV = 36848;
    public static final int GL_UNSIGNED_INT16_VEC2_NV = 36849;
    public static final int GL_UNSIGNED_INT16_VEC3_NV = 36850;
    public static final int GL_UNSIGNED_INT16_VEC4_NV = 36851;
    public static final int GL_UNSIGNED_INT64_VEC2_NV = 36853;
    public static final int GL_UNSIGNED_INT64_VEC3_NV = 36854;
    public static final int GL_UNSIGNED_INT64_VEC4_NV = 36855;
    public static final int GL_FLOAT16_NV = 36856;
    public static final int GL_FLOAT16_VEC2_NV = 36857;
    public static final int GL_FLOAT16_VEC3_NV = 36858;
    public static final int GL_FLOAT16_VEC4_NV = 36859;

    protected NVGPUShader5() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glUniform1i64NV, gLCapabilities.glUniform2i64NV, gLCapabilities.glUniform3i64NV, gLCapabilities.glUniform4i64NV, gLCapabilities.glUniform1i64vNV, gLCapabilities.glUniform2i64vNV, gLCapabilities.glUniform3i64vNV, gLCapabilities.glUniform4i64vNV, gLCapabilities.glUniform1ui64NV, gLCapabilities.glUniform2ui64NV, gLCapabilities.glUniform3ui64NV, gLCapabilities.glUniform4ui64NV, gLCapabilities.glUniform1ui64vNV, gLCapabilities.glUniform2ui64vNV, gLCapabilities.glUniform3ui64vNV, gLCapabilities.glUniform4ui64vNV, gLCapabilities.glGetUniformi64vNV, gLCapabilities.glGetUniformui64vNV, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform1i64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform2i64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform3i64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform4i64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform1i64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform2i64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform3i64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform4i64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform1ui64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform2ui64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform3ui64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform4ui64NV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform1ui64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform2ui64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform3ui64vNV : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glProgramUniform4ui64vNV : -1L);
    }

    public static native void glUniform1i64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLint64EXT") long var1);

    public static native void glUniform2i64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLint64EXT") long var1, @NativeType(value="GLint64EXT") long var3);

    public static native void glUniform3i64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLint64EXT") long var1, @NativeType(value="GLint64EXT") long var3, @NativeType(value="GLint64EXT") long var5);

    public static native void glUniform4i64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLint64EXT") long var1, @NativeType(value="GLint64EXT") long var3, @NativeType(value="GLint64EXT") long var5, @NativeType(value="GLint64EXT") long var7);

    public static native void nglUniform1i64vNV(int var0, int var1, long var2);

    public static void glUniform1i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform1i64vNV(n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglUniform2i64vNV(int var0, int var1, long var2);

    public static void glUniform2i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform2i64vNV(n, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglUniform3i64vNV(int var0, int var1, long var2);

    public static void glUniform3i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform3i64vNV(n, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglUniform4i64vNV(int var0, int var1, long var2);

    public static void glUniform4i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform4i64vNV(n, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glUniform1ui64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64EXT") long var1);

    public static native void glUniform2ui64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64EXT") long var1, @NativeType(value="GLuint64EXT") long var3);

    public static native void glUniform3ui64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64EXT") long var1, @NativeType(value="GLuint64EXT") long var3, @NativeType(value="GLuint64EXT") long var5);

    public static native void glUniform4ui64NV(@NativeType(value="GLint") int var0, @NativeType(value="GLuint64EXT") long var1, @NativeType(value="GLuint64EXT") long var3, @NativeType(value="GLuint64EXT") long var5, @NativeType(value="GLuint64EXT") long var7);

    public static native void nglUniform1ui64vNV(int var0, int var1, long var2);

    public static void glUniform1ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform1ui64vNV(n, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglUniform2ui64vNV(int var0, int var1, long var2);

    public static void glUniform2ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform2ui64vNV(n, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglUniform3ui64vNV(int var0, int var1, long var2);

    public static void glUniform3ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform3ui64vNV(n, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglUniform4ui64vNV(int var0, int var1, long var2);

    public static void glUniform4ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglUniform4ui64vNV(n, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglGetUniformi64vNV(int var0, int var1, long var2);

    public static void glGetUniformi64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        NVGPUShader5.nglGetUniformi64vNV(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetUniformi64NV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            NVGPUShader5.nglGetUniformi64vNV(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nglGetUniformui64vNV(int n, int n2, long l) {
        NVShaderBufferLoad.nglGetUniformui64vNV(n, n2, l);
    }

    public static void glGetUniformui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT *") LongBuffer longBuffer) {
        NVShaderBufferLoad.glGetUniformui64vNV(n, n2, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetUniformui64NV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        return NVShaderBufferLoad.glGetUniformui64NV(n, n2);
    }

    public static native void glProgramUniform1i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64EXT") long var2);

    public static native void glProgramUniform2i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64EXT") long var2, @NativeType(value="GLint64EXT") long var4);

    public static native void glProgramUniform3i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64EXT") long var2, @NativeType(value="GLint64EXT") long var4, @NativeType(value="GLint64EXT") long var6);

    public static native void glProgramUniform4i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint64EXT") long var2, @NativeType(value="GLint64EXT") long var4, @NativeType(value="GLint64EXT") long var6, @NativeType(value="GLint64EXT") long var8);

    public static native void nglProgramUniform1i64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform1i64vNV(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglProgramUniform2i64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform2i64vNV(n, n2, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglProgramUniform3i64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform3i64vNV(n, n2, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglProgramUniform4i64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform4i64vNV(n, n2, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glProgramUniform1ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64EXT") long var2);

    public static native void glProgramUniform2ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64EXT") long var2, @NativeType(value="GLuint64EXT") long var4);

    public static native void glProgramUniform3ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64EXT") long var2, @NativeType(value="GLuint64EXT") long var4, @NativeType(value="GLuint64EXT") long var6);

    public static native void glProgramUniform4ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint64EXT") long var2, @NativeType(value="GLuint64EXT") long var4, @NativeType(value="GLuint64EXT") long var6, @NativeType(value="GLuint64EXT") long var8);

    public static native void nglProgramUniform1ui64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform1ui64vNV(n, n2, longBuffer.remaining(), MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglProgramUniform2ui64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform2ui64vNV(n, n2, longBuffer.remaining() >> 1, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglProgramUniform3ui64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform3ui64vNV(n, n2, longBuffer.remaining() / 3, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglProgramUniform4ui64vNV(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        NVGPUShader5.nglProgramUniform4ui64vNV(n, n2, longBuffer.remaining() >> 2, MemoryUtil.memAddress(longBuffer));
    }

    public static void glUniform1i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform1i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length, lArray, l);
    }

    public static void glUniform2i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform2i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 1, lArray, l);
    }

    public static void glUniform3i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform3i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length / 3, lArray, l);
    }

    public static void glUniform4i64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform4i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 2, lArray, l);
    }

    public static void glUniform1ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform1ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length, lArray, l);
    }

    public static void glUniform2ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT *") long[] lArray) {
        long l = GL.getICD().glUniform2ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 1, lArray, l);
    }

    public static void glUniform3ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform3ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length / 3, lArray, l);
    }

    public static void glUniform4ui64vNV(@NativeType(value="GLint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glUniform4ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, lArray.length >> 2, lArray, l);
    }

    public static void glGetUniformi64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT *") long[] lArray) {
        long l = GL.getICD().glGetUniformi64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glGetUniformui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT *") long[] lArray) {
        NVShaderBufferLoad.glGetUniformui64vNV(n, n2, lArray);
    }

    public static void glProgramUniform1i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform1i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    public static void glProgramUniform2i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform2i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 1, lArray, l);
    }

    public static void glProgramUniform3i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform3i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length / 3, lArray, l);
    }

    public static void glProgramUniform4i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform4i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 2, lArray, l);
    }

    public static void glProgramUniform1ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform1ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length, lArray, l);
    }

    public static void glProgramUniform2ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform2ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 1, lArray, l);
    }

    public static void glProgramUniform3ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform3ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length / 3, lArray, l);
    }

    public static void glProgramUniform4ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glProgramUniform4ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, lArray.length >> 2, lArray, l);
    }

    static {
        GL.initialize();
    }
}

