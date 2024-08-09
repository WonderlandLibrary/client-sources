/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBVertexArrayObject {
    public static final int GL_VERTEX_ARRAY_BINDING = 34229;

    protected ARBVertexArrayObject() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindVertexArray, gLCapabilities.glDeleteVertexArrays, gLCapabilities.glGenVertexArrays, gLCapabilities.glIsVertexArray);
    }

    public static void glBindVertexArray(@NativeType(value="GLuint") int n) {
        GL30C.glBindVertexArray(n);
    }

    public static void nglDeleteVertexArrays(int n, long l) {
        GL30C.nglDeleteVertexArrays(n, l);
    }

    public static void glDeleteVertexArrays(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glDeleteVertexArrays(intBuffer);
    }

    public static void glDeleteVertexArrays(@NativeType(value="GLuint const *") int n) {
        GL30C.glDeleteVertexArrays(n);
    }

    public static void nglGenVertexArrays(int n, long l) {
        GL30C.nglGenVertexArrays(n, l);
    }

    public static void glGenVertexArrays(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGenVertexArrays(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenVertexArrays() {
        return GL30C.glGenVertexArrays();
    }

    @NativeType(value="GLboolean")
    public static boolean glIsVertexArray(@NativeType(value="GLuint") int n) {
        return GL30C.glIsVertexArray(n);
    }

    public static void glDeleteVertexArrays(@NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glDeleteVertexArrays(nArray);
    }

    public static void glGenVertexArrays(@NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGenVertexArrays(nArray);
    }

    static {
        GL.initialize();
    }
}

