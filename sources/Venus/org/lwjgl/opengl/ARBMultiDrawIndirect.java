/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBMultiDrawIndirect {
    protected ARBMultiDrawIndirect() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMultiDrawArraysIndirect, gLCapabilities.glMultiDrawElementsIndirect);
    }

    public static void nglMultiDrawArraysIndirect(int n, long l, int n2, int n3) {
        GL43C.nglMultiDrawArraysIndirect(n, l, n2, n3);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, byteBuffer, n2, n3);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, l, n2, n3);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, intBuffer, n2, n3);
    }

    public static void nglMultiDrawElementsIndirect(int n, int n2, long l, int n3, int n4) {
        GL43C.nglMultiDrawElementsIndirect(n, n2, l, n3, n4);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, byteBuffer, n3, n4);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, l, n3, n4);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, intBuffer, n3, n4);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, nArray, n2, n3);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, nArray, n3, n4);
    }

    static {
        GL.initialize();
    }
}

