/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL40C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBDrawIndirect {
    public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;

    protected ARBDrawIndirect() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawArraysIndirect, gLCapabilities.glDrawElementsIndirect);
    }

    public static void nglDrawArraysIndirect(int n, long l) {
        GL40C.nglDrawArraysIndirect(n, l);
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL40C.glDrawArraysIndirect(n, byteBuffer);
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l) {
        GL40C.glDrawArraysIndirect(n, l);
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL40C.glDrawArraysIndirect(n, intBuffer);
    }

    public static void nglDrawElementsIndirect(int n, int n2, long l) {
        GL40C.nglDrawElementsIndirect(n, n2, l);
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL40C.glDrawElementsIndirect(n, n2, byteBuffer);
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l) {
        GL40C.glDrawElementsIndirect(n, n2, l);
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL40C.glDrawElementsIndirect(n, n2, intBuffer);
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray) {
        GL40C.glDrawArraysIndirect(n, nArray);
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray) {
        GL40C.glDrawElementsIndirect(n, n2, nArray);
    }

    static {
        GL.initialize();
    }
}

