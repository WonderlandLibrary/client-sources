/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GL46C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL46
extends GL45 {
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

    protected GL46() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMultiDrawArraysIndirectCount, gLCapabilities.glMultiDrawElementsIndirectCount, gLCapabilities.glPolygonOffsetClamp, gLCapabilities.glSpecializeShader);
    }

    public static void nglMultiDrawArraysIndirectCount(int n, long l, long l2, int n2, int n3) {
        GL46C.nglMultiDrawArraysIndirectCount(n, l, l2, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL46C.glMultiDrawArraysIndirectCount(n, byteBuffer, l, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL46C.glMultiDrawArraysIndirectCount(n, l, l2, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL46C.glMultiDrawArraysIndirectCount(n, intBuffer, l, n2, n3);
    }

    public static void nglMultiDrawElementsIndirectCount(int n, int n2, long l, long l2, int n3, int n4) {
        GL46C.nglMultiDrawElementsIndirectCount(n, n2, l, l2, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL46C.glMultiDrawElementsIndirectCount(n, n2, byteBuffer, l, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL46C.glMultiDrawElementsIndirectCount(n, n2, l, l2, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL46C.glMultiDrawElementsIndirectCount(n, n2, intBuffer, l, n3, n4);
    }

    public static void glPolygonOffsetClamp(@NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3) {
        GL46C.glPolygonOffsetClamp(f, f2, f3);
    }

    public static void nglSpecializeShader(int n, long l, int n2, long l2, long l3) {
        GL46C.nglSpecializeShader(n, l, n2, l2, l3);
    }

    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        GL46C.glSpecializeShader(n, byteBuffer, intBuffer, intBuffer2);
    }

    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        GL46C.glSpecializeShader(n, charSequence, intBuffer, intBuffer2);
    }

    public static void glMultiDrawArraysIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL46C.glMultiDrawArraysIndirectCount(n, nArray, l, n2, n3);
    }

    public static void glMultiDrawElementsIndirectCount(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL46C.glMultiDrawElementsIndirectCount(n, n2, nArray, l, n3, n4);
    }

    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        GL46C.glSpecializeShader(n, byteBuffer, nArray, nArray2);
    }

    public static void glSpecializeShader(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        GL46C.glSpecializeShader(n, charSequence, nArray, nArray2);
    }

    static {
        GL.initialize();
    }
}

