/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL44C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBClearTexture {
    public static final int GL_CLEAR_TEXTURE = 37733;

    protected ARBClearTexture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glClearTexSubImage, gLCapabilities.glClearTexImage);
    }

    public static void nglClearTexSubImage(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, long l) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer);
    }

    public static void nglClearTexImage(int n, int n2, int n3, int n4, long l) {
        GL44C.nglClearTexImage(n, n2, n3, n4, l);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, byteBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, shortBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, intBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, floatBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, doubleBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") double[] dArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, sArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, nArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, fArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") double[] dArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, dArray);
    }

    static {
        GL.initialize();
    }
}

