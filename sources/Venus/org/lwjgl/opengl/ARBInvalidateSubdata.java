/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBInvalidateSubdata {
    protected ARBInvalidateSubdata() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glInvalidateTexSubImage, gLCapabilities.glInvalidateTexImage, gLCapabilities.glInvalidateBufferSubData, gLCapabilities.glInvalidateBufferData, gLCapabilities.glInvalidateFramebuffer, gLCapabilities.glInvalidateSubFramebuffer);
    }

    public static void glInvalidateTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8) {
        GL43C.glInvalidateTexSubImage(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void glInvalidateTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        GL43C.glInvalidateTexImage(n, n2);
    }

    public static void glInvalidateBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL43C.glInvalidateBufferSubData(n, l, l2);
    }

    public static void glInvalidateBufferData(@NativeType(value="GLuint") int n) {
        GL43C.glInvalidateBufferData(n);
    }

    public static void nglInvalidateFramebuffer(int n, int n2, long l) {
        GL43C.nglInvalidateFramebuffer(n, n2, l);
    }

    public static void glInvalidateFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer) {
        GL43C.glInvalidateFramebuffer(n, intBuffer);
    }

    public static void glInvalidateFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int n2) {
        GL43C.glInvalidateFramebuffer(n, n2);
    }

    public static void nglInvalidateSubFramebuffer(int n, int n2, long l, int n3, int n4, int n5, int n6) {
        GL43C.nglInvalidateSubFramebuffer(n, n2, l, n3, n4, n5, n6);
    }

    public static void glInvalidateSubFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL43C.glInvalidateSubFramebuffer(n, intBuffer, n2, n3, n4, n5);
    }

    public static void glInvalidateSubFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6) {
        GL43C.glInvalidateSubFramebuffer(n, n2, n3, n4, n5, n6);
    }

    public static void glInvalidateFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int[] nArray) {
        GL43C.glInvalidateFramebuffer(n, nArray);
    }

    public static void glInvalidateSubFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int[] nArray, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL43C.glInvalidateSubFramebuffer(n, nArray, n2, n3, n4, n5);
    }

    static {
        GL.initialize();
    }
}

