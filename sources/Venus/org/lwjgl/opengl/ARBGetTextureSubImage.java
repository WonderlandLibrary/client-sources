/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBGetTextureSubImage {
    protected ARBGetTextureSubImage() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetTextureSubImage, gLCapabilities.glGetCompressedTextureSubImage);
    }

    public static void nglGetTextureSubImage(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, long l) {
        GL45C.nglGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, l);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLsizei") int n11, @NativeType(value="void *") long l) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, l);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") IntBuffer intBuffer) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer);
    }

    public static void nglGetCompressedTextureSubImage(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, long l) {
        GL45C.nglGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="void *") long l) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, shortBuffer);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") IntBuffer intBuffer) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, floatBuffer);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, doubleBuffer);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") short[] sArray) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") int[] nArray) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") float[] fArray) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray);
    }

    public static void glGetTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void *") double[] dArray) {
        GL45C.glGetTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") short[] sArray) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, sArray);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") int[] nArray) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, nArray);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") float[] fArray) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, fArray);
    }

    public static void glGetCompressedTextureSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="void *") double[] dArray) {
        GL45C.glGetCompressedTextureSubImage(n, n2, n3, n4, n5, n6, n7, n8, dArray);
    }

    static {
        GL.initialize();
    }
}

