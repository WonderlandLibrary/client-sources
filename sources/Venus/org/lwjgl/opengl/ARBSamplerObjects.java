/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBSamplerObjects {
    public static final int GL_SAMPLER_BINDING = 35097;

    protected ARBSamplerObjects() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGenSamplers, gLCapabilities.glDeleteSamplers, gLCapabilities.glIsSampler, gLCapabilities.glBindSampler, gLCapabilities.glSamplerParameteri, gLCapabilities.glSamplerParameterf, gLCapabilities.glSamplerParameteriv, gLCapabilities.glSamplerParameterfv, gLCapabilities.glSamplerParameterIiv, gLCapabilities.glSamplerParameterIuiv, gLCapabilities.glGetSamplerParameteriv, gLCapabilities.glGetSamplerParameterfv, gLCapabilities.glGetSamplerParameterIiv, gLCapabilities.glGetSamplerParameterIuiv);
    }

    public static void nglGenSamplers(int n, long l) {
        GL33C.nglGenSamplers(n, l);
    }

    public static void glGenSamplers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL33C.glGenSamplers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenSamplers() {
        return GL33C.glGenSamplers();
    }

    public static void nglDeleteSamplers(int n, long l) {
        GL33C.nglDeleteSamplers(n, l);
    }

    public static void glDeleteSamplers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glDeleteSamplers(intBuffer);
    }

    public static void glDeleteSamplers(@NativeType(value="GLuint const *") int n) {
        GL33C.glDeleteSamplers(n);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsSampler(@NativeType(value="GLuint") int n) {
        return GL33C.glIsSampler(n);
    }

    public static void glBindSampler(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL33C.glBindSampler(n, n2);
    }

    public static void glSamplerParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL33C.glSamplerParameteri(n, n2, n3);
    }

    public static void glSamplerParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat") float f) {
        GL33C.glSamplerParameterf(n, n2, f);
    }

    public static void nglSamplerParameteriv(int n, int n2, long l) {
        GL33C.nglSamplerParameteriv(n, n2, l);
    }

    public static void glSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL33C.glSamplerParameteriv(n, n2, intBuffer);
    }

    public static void nglSamplerParameterfv(int n, int n2, long l) {
        GL33C.nglSamplerParameterfv(n, n2, l);
    }

    public static void glSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL33C.glSamplerParameterfv(n, n2, floatBuffer);
    }

    public static void nglSamplerParameterIiv(int n, int n2, long l) {
        GL33C.nglSamplerParameterIiv(n, n2, l);
    }

    public static void glSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL33C.glSamplerParameterIiv(n, n2, intBuffer);
    }

    public static void nglSamplerParameterIuiv(int n, int n2, long l) {
        GL33C.nglSamplerParameterIuiv(n, n2, l);
    }

    public static void glSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glSamplerParameterIuiv(n, n2, intBuffer);
    }

    public static void nglGetSamplerParameteriv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameteriv(n, n2, l);
    }

    public static void glGetSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL33C.glGetSamplerParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetSamplerParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameteri(n, n2);
    }

    public static void nglGetSamplerParameterfv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameterfv(n, n2, l);
    }

    public static void glGetSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL33C.glGetSamplerParameterfv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetSamplerParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameterf(n, n2);
    }

    public static void nglGetSamplerParameterIiv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameterIiv(n, n2, l);
    }

    public static void glGetSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL33C.glGetSamplerParameterIiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetSamplerParameterIi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameterIi(n, n2);
    }

    public static void nglGetSamplerParameterIuiv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameterIuiv(n, n2, l);
    }

    public static void glGetSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL33C.glGetSamplerParameterIuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetSamplerParameterIui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameterIui(n, n2);
    }

    public static void glGenSamplers(@NativeType(value="GLuint *") int[] nArray) {
        GL33C.glGenSamplers(nArray);
    }

    public static void glDeleteSamplers(@NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glDeleteSamplers(nArray);
    }

    public static void glSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL33C.glSamplerParameteriv(n, n2, nArray);
    }

    public static void glSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL33C.glSamplerParameterfv(n, n2, fArray);
    }

    public static void glSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL33C.glSamplerParameterIiv(n, n2, nArray);
    }

    public static void glSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glSamplerParameterIuiv(n, n2, nArray);
    }

    public static void glGetSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL33C.glGetSamplerParameteriv(n, n2, nArray);
    }

    public static void glGetSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL33C.glGetSamplerParameterfv(n, n2, fArray);
    }

    public static void glGetSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL33C.glGetSamplerParameterIiv(n, n2, nArray);
    }

    public static void glGetSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL33C.glGetSamplerParameterIuiv(n, n2, nArray);
    }

    static {
        GL.initialize();
    }
}

