/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTGPUShader4 {
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER_EXT = 35069;
    public static final int GL_SAMPLER_1D_ARRAY_EXT = 36288;
    public static final int GL_SAMPLER_2D_ARRAY_EXT = 36289;
    public static final int GL_SAMPLER_BUFFER_EXT = 36290;
    public static final int GL_SAMPLER_1D_ARRAY_SHADOW_EXT = 36291;
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW_EXT = 36292;
    public static final int GL_SAMPLER_CUBE_SHADOW_EXT = 36293;
    public static final int GL_UNSIGNED_INT_VEC2_EXT = 36294;
    public static final int GL_UNSIGNED_INT_VEC3_EXT = 36295;
    public static final int GL_UNSIGNED_INT_VEC4_EXT = 36296;
    public static final int GL_INT_SAMPLER_1D_EXT = 36297;
    public static final int GL_INT_SAMPLER_2D_EXT = 36298;
    public static final int GL_INT_SAMPLER_3D_EXT = 36299;
    public static final int GL_INT_SAMPLER_CUBE_EXT = 36300;
    public static final int GL_INT_SAMPLER_2D_RECT_EXT = 36301;
    public static final int GL_INT_SAMPLER_1D_ARRAY_EXT = 36302;
    public static final int GL_INT_SAMPLER_2D_ARRAY_EXT = 36303;
    public static final int GL_INT_SAMPLER_BUFFER_EXT = 36304;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_EXT = 36305;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_EXT = 36306;
    public static final int GL_UNSIGNED_INT_SAMPLER_3D_EXT = 36307;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_EXT = 36308;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT_EXT = 36309;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY_EXT = 36310;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY_EXT = 36311;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_EXT = 36312;
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET_EXT = 35076;
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET_EXT = 35077;

    protected EXTGPUShader4() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glVertexAttribI1iEXT, gLCapabilities.glVertexAttribI2iEXT, gLCapabilities.glVertexAttribI3iEXT, gLCapabilities.glVertexAttribI4iEXT, gLCapabilities.glVertexAttribI1uiEXT, gLCapabilities.glVertexAttribI2uiEXT, gLCapabilities.glVertexAttribI3uiEXT, gLCapabilities.glVertexAttribI4uiEXT, gLCapabilities.glVertexAttribI1ivEXT, gLCapabilities.glVertexAttribI2ivEXT, gLCapabilities.glVertexAttribI3ivEXT, gLCapabilities.glVertexAttribI4ivEXT, gLCapabilities.glVertexAttribI1uivEXT, gLCapabilities.glVertexAttribI2uivEXT, gLCapabilities.glVertexAttribI3uivEXT, gLCapabilities.glVertexAttribI4uivEXT, gLCapabilities.glVertexAttribI4bvEXT, gLCapabilities.glVertexAttribI4svEXT, gLCapabilities.glVertexAttribI4ubvEXT, gLCapabilities.glVertexAttribI4usvEXT, gLCapabilities.glVertexAttribIPointerEXT, gLCapabilities.glGetVertexAttribIivEXT, gLCapabilities.glGetVertexAttribIuivEXT, gLCapabilities.glGetUniformuivEXT, gLCapabilities.glBindFragDataLocationEXT, gLCapabilities.glGetFragDataLocationEXT, gLCapabilities.glUniform1uiEXT, gLCapabilities.glUniform2uiEXT, gLCapabilities.glUniform3uiEXT, gLCapabilities.glUniform4uiEXT, gLCapabilities.glUniform1uivEXT, gLCapabilities.glUniform2uivEXT, gLCapabilities.glUniform3uivEXT, gLCapabilities.glUniform4uivEXT);
    }

    public static native void glVertexAttribI1iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1);

    public static native void glVertexAttribI2iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glVertexAttribI3iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glVertexAttribI4iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void glVertexAttribI1uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glVertexAttribI2uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glVertexAttribI3uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glVertexAttribI4uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void nglVertexAttribI1ivEXT(int var0, long var1);

    public static void glVertexAttribI1ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTGPUShader4.nglVertexAttribI1ivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI2ivEXT(int var0, long var1);

    public static void glVertexAttribI2ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        EXTGPUShader4.nglVertexAttribI2ivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI3ivEXT(int var0, long var1);

    public static void glVertexAttribI3ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        EXTGPUShader4.nglVertexAttribI3ivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI4ivEXT(int var0, long var1);

    public static void glVertexAttribI4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTGPUShader4.nglVertexAttribI4ivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI1uivEXT(int var0, long var1);

    public static void glVertexAttribI1uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTGPUShader4.nglVertexAttribI1uivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI2uivEXT(int var0, long var1);

    public static void glVertexAttribI2uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        EXTGPUShader4.nglVertexAttribI2uivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI3uivEXT(int var0, long var1);

    public static void glVertexAttribI3uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        EXTGPUShader4.nglVertexAttribI3uivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI4uivEXT(int var0, long var1);

    public static void glVertexAttribI4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTGPUShader4.nglVertexAttribI4uivEXT(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribI4bvEXT(int var0, long var1);

    public static void glVertexAttribI4bvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        EXTGPUShader4.nglVertexAttribI4bvEXT(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttribI4svEXT(int var0, long var1);

    public static void glVertexAttribI4svEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        EXTGPUShader4.nglVertexAttribI4svEXT(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttribI4ubvEXT(int var0, long var1);

    public static void glVertexAttribI4ubvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        EXTGPUShader4.nglVertexAttribI4ubvEXT(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttribI4usvEXT(int var0, long var1);

    public static void glVertexAttribI4usvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        EXTGPUShader4.nglVertexAttribI4usvEXT(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttribIPointerEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glVertexAttribIPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTGPUShader4.nglVertexAttribIPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glVertexAttribIPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        EXTGPUShader4.nglVertexAttribIPointerEXT(n, n2, n3, n4, l);
    }

    public static void glVertexAttribIPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTGPUShader4.nglVertexAttribIPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glVertexAttribIPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTGPUShader4.nglVertexAttribIPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetVertexAttribIivEXT(int var0, int var1, long var2);

    public static void glGetVertexAttribIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTGPUShader4.nglGetVertexAttribIivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetVertexAttribIiEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTGPUShader4.nglGetVertexAttribIivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetVertexAttribIuivEXT(int var0, int var1, long var2);

    public static void glGetVertexAttribIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTGPUShader4.nglGetVertexAttribIuivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetVertexAttribIuiEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTGPUShader4.nglGetVertexAttribIuivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetUniformuivEXT(int var0, int var1, long var2);

    public static void glGetUniformuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTGPUShader4.nglGetUniformuivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetUniformuiEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTGPUShader4.nglGetUniformuivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglBindFragDataLocationEXT(int var0, int var1, long var2);

    public static void glBindFragDataLocationEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        EXTGPUShader4.nglBindFragDataLocationEXT(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glBindFragDataLocationEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            EXTGPUShader4.nglBindFragDataLocationEXT(n, n2, l);
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native int nglGetFragDataLocationEXT(int var0, long var1);

    @NativeType(value="GLint")
    public static int glGetFragDataLocationEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return EXTGPUShader4.nglGetFragDataLocationEXT(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetFragDataLocationEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = EXTGPUShader4.nglGetFragDataLocationEXT(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glUniform1uiEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glUniform2uiEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glUniform3uiEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3);

    public static native void glUniform4uiEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint") int var4);

    public static native void nglUniform1uivEXT(int var0, int var1, long var2);

    public static void glUniform1uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTGPUShader4.nglUniform1uivEXT(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform2uivEXT(int var0, int var1, long var2);

    public static void glUniform2uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTGPUShader4.nglUniform2uivEXT(n, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform3uivEXT(int var0, int var1, long var2);

    public static void glUniform3uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTGPUShader4.nglUniform3uivEXT(n, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform4uivEXT(int var0, int var1, long var2);

    public static void glUniform4uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTGPUShader4.nglUniform4uivEXT(n, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glVertexAttribI1ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI1ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI2ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI2ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI3ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI3ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI4ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI1uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI1uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI2uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI2uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI3uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI3uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribI4uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribI4svEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribI4svEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttribI4usvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribI4usvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttribIPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribIPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, sArray, l);
    }

    public static void glVertexAttribIPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribIPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glGetVertexAttribIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetVertexAttribIivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetVertexAttribIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetVertexAttribIuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetUniformuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetUniformuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glUniform1uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glUniform1uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glUniform2uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glUniform2uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 1, nArray, l);
    }

    public static void glUniform3uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glUniform3uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length / 3, nArray, l);
    }

    public static void glUniform4uivEXT(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glUniform4uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 2, nArray, l);
    }

    static {
        GL.initialize();
    }
}

