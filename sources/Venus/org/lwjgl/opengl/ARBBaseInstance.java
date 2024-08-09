/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL42C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBBaseInstance {
    protected ARBBaseInstance() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawArraysInstancedBaseInstance, gLCapabilities.glDrawElementsInstancedBaseInstance, gLCapabilities.glDrawElementsInstancedBaseVertexBaseInstance);
    }

    public static void glDrawArraysInstancedBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLuint") int n5) {
        GL42C.glDrawArraysInstancedBaseInstance(n, n2, n3, n4, n5);
    }

    public static void nglDrawElementsInstancedBaseInstance(int n, int n2, int n3, long l, int n4, int n5) {
        GL42C.nglDrawElementsInstancedBaseInstance(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n4, @NativeType(value="GLuint") int n5) {
        GL42C.glDrawElementsInstancedBaseInstance(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLuint") int n4) {
        GL42C.glDrawElementsInstancedBaseInstance(n, n2, byteBuffer, n3, n4);
    }

    public static void glDrawElementsInstancedBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLuint") int n3) {
        GL42C.glDrawElementsInstancedBaseInstance(n, byteBuffer, n2, n3);
    }

    public static void glDrawElementsInstancedBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLuint") int n3) {
        GL42C.glDrawElementsInstancedBaseInstance(n, shortBuffer, n2, n3);
    }

    public static void glDrawElementsInstancedBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLuint") int n3) {
        GL42C.glDrawElementsInstancedBaseInstance(n, intBuffer, n2, n3);
    }

    public static void nglDrawElementsInstancedBaseVertexBaseInstance(int n, int n2, int n3, long l, int n4, int n5, int n6) {
        GL42C.nglDrawElementsInstancedBaseVertexBaseInstance(n, n2, n3, l, n4, n5, n6);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLuint") int n6) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(n, n2, n3, l, n4, n5, n6);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLuint") int n5) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(n, n2, byteBuffer, n3, n4, n5);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLuint") int n4) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(n, byteBuffer, n2, n3, n4);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLuint") int n4) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(n, shortBuffer, n2, n3, n4);
    }

    public static void glDrawElementsInstancedBaseVertexBaseInstance(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLuint") int n4) {
        GL42C.glDrawElementsInstancedBaseVertexBaseInstance(n, intBuffer, n2, n3, n4);
    }

    static {
        GL.initialize();
    }
}

