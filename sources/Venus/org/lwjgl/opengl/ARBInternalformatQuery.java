/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL42C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBInternalformatQuery {
    public static final int GL_NUM_SAMPLE_COUNTS = 37760;

    protected ARBInternalformatQuery() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetInternalformativ);
    }

    public static void nglGetInternalformativ(int n, int n2, int n3, int n4, long l) {
        GL42C.nglGetInternalformativ(n, n2, n3, n4, l);
    }

    public static void glGetInternalformativ(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL42C.glGetInternalformativ(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetInternalformati(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL42C.glGetInternalformati(n, n2, n3);
    }

    public static void glGetInternalformativ(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL42C.glGetInternalformativ(n, n2, n3, nArray);
    }

    static {
        GL.initialize();
    }
}

