/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBDrawElementsBaseVertex {
    protected ARBDrawElementsBaseVertex() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawElementsBaseVertex, gLCapabilities.glDrawRangeElementsBaseVertex, gLCapabilities.glDrawElementsInstancedBaseVertex, gLCapabilities.glMultiDrawElementsBaseVertex);
    }

    public static void nglDrawElementsBaseVertex(int n, int n2, int n3, long l, int n4) {
        GL32C.nglDrawElementsBaseVertex(n, n2, n3, l, n4);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLint") int n4) {
        GL32C.glDrawElementsBaseVertex(n, n2, n3, l, n4);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsBaseVertex(n, n2, byteBuffer, n3);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n2) {
        GL32C.glDrawElementsBaseVertex(n, byteBuffer, n2);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLint") int n2) {
        GL32C.glDrawElementsBaseVertex(n, shortBuffer, n2);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLint") int n2) {
        GL32C.glDrawElementsBaseVertex(n, intBuffer, n2);
    }

    public static void nglDrawRangeElementsBaseVertex(int n, int n2, int n3, int n4, int n5, long l, int n6) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, l, n6);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l, @NativeType(value="GLint") int n6) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, l, n6);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n5) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, n4, byteBuffer, n5);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n4) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, byteBuffer, n4);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLint") int n4) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, shortBuffer, n4);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLint") int n4) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, intBuffer, n4);
    }

    public static void nglDrawElementsInstancedBaseVertex(int n, int n2, int n3, long l, int n4, int n5) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5) {
        GL32C.glDrawElementsInstancedBaseVertex(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint") int n4) {
        GL32C.glDrawElementsInstancedBaseVertex(n, n2, byteBuffer, n3, n4);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsInstancedBaseVertex(n, byteBuffer, n2, n3);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsInstancedBaseVertex(n, shortBuffer, n2, n3);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsInstancedBaseVertex(n, intBuffer, n2, n3);
    }

    public static void nglMultiDrawElementsBaseVertex(int n, long l, int n2, long l2, int n3, long l3) {
        GL32C.nglMultiDrawElementsBaseVertex(n, l, n2, l2, n3, l3);
    }

    public static void glMultiDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        GL32C.glMultiDrawElementsBaseVertex(n, intBuffer, n2, pointerBuffer, intBuffer2);
    }

    public static void glMultiDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei const *") int[] nArray, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="GLint *") int[] nArray2) {
        GL32C.glMultiDrawElementsBaseVertex(n, nArray, n2, pointerBuffer, nArray2);
    }

    static {
        GL.initialize();
    }
}

