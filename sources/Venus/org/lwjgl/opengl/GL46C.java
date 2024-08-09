/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL46C
extends GL45C {
    public static final int GL_PARAMETER_BUFFER = 33006;
    public static final int GL_PARAMETER_BUFFER_BINDING = 33007;
    public static final int GL_VERTICES_SUBMITTED = 33518;
    public static final int GL_PRIMITIVES_SUBMITTED = 33519;
    public static final int GL_VERTEX_SHADER_INVOCATIONS = 33520;
    public static final int GL_TESS_CONTROL_SHADER_PATCHES = 33521;
    public static final int GL_TESS_EVALUATION_SHADER_INVOCATIONS = 33522;
    public static final int GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED = 33523;
    public static final int GL_FRAGMENT_SHADER_INVOCATIONS = 33524;
    public static final int GL_COMPUTE_SHADER_INVOCATIONS = 33525;
    public static final int GL_CLIPPING_INPUT_PRIMITIVES = 33526;
    public static final int GL_CLIPPING_OUTPUT_PRIMITIVES = 33527;
    public static final int GL_POLYGON_OFFSET_CLAMP = 36379;
    public static final int GL_CONTEXT_FLAG_NO_ERROR_BIT = 8;
    public static final int GL_SHADER_BINARY_FORMAT_SPIR_V = 38225;
    public static final int GL_SPIR_V_BINARY = 38226;
    public static final int GL_SPIR_V_EXTENSIONS = 38227;
    public static final int GL_NUM_SPIR_V_EXTENSIONS = 38228;
    public static final int GL_TEXTURE_MAX_ANISOTROPY = 34046;
    public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY = 34047;
    public static final int GL_TRANSFORM_FEEDBACK_OVERFLOW = 33516;
    public static final int GL_TRANSFORM_FEEDBACK_STREAM_OVERFLOW = 33517;

    protected GL46C() {
        throw new UnsupportedOperationException();
    }

    public static native void nglMultiDrawArraysIndirectCount(int var0, long var1, long var3, int var5, int var6);

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n2 * (n3 == 0 ? 16 : n3));
        }
        GL46C.nglMultiDrawArraysIndirectCount(n, MemoryUtil.memAddress(byteBuffer), l, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL46C.nglMultiDrawArraysIndirectCount(n, l, l2, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, n2 * (n3 == 0 ? 16 : n3) >> 2);
        }
        GL46C.nglMultiDrawArraysIndirectCount(n, MemoryUtil.memAddress(intBuffer), l, n2, n3);
    }

    public static native void nglMultiDrawElementsIndirectCount(int var0, int var1, long var2, long var4, int var6, int var7);

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n3 * (n4 == 0 ? 20 : n4));
        }
        GL46C.nglMultiDrawElementsIndirectCount(n, n2, MemoryUtil.memAddress(byteBuffer), l, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL46C.nglMultiDrawElementsIndirectCount(n, n2, l, l2, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, n3 * (n4 == 0 ? 20 : n4) >> 2);
        }
        GL46C.nglMultiDrawElementsIndirectCount(n, n2, MemoryUtil.memAddress(intBuffer), l, n3, n4);
    }

    public static native void glPolygonOffsetClamp(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void nglSpecializeShader(int var0, long var1, int var3, long var4, long var6);

    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        GL46C.nglSpecializeShader(n, MemoryUtil.memAddress(byteBuffer), intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            GL46C.nglSpecializeShader(n, l, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        long l2 = GL.getICD().glMultiDrawArraysIndirectCount;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(nArray, n2 * (n3 == 0 ? 16 : n3) >> 2);
        }
        JNI.callPPV(n, nArray, l, n2, n3, l2);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        long l2 = GL.getICD().glMultiDrawElementsIndirectCount;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(nArray, n3 * (n4 == 0 ? 20 : n4) >> 2);
        }
        JNI.callPPV(n, n2, nArray, l, n3, n4, l2);
    }

    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        long l = GL.getICD().glSpecializeShader;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray2, nArray.length);
        }
        JNI.callPPPV(n, MemoryUtil.memAddress(byteBuffer), nArray.length, nArray, nArray2, l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        long l = GL.getICD().glSpecializeShader;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray2, nArray.length);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            JNI.callPPPV(n, l2, nArray.length, nArray, nArray2, l);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    static {
        GL.initialize();
    }
}

