/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBBlendFuncExtended {
    public static final int GL_SRC1_COLOR = 35065;
    public static final int GL_ONE_MINUS_SRC1_COLOR = 35066;
    public static final int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;

    protected ARBBlendFuncExtended() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindFragDataLocationIndexed, gLCapabilities.glGetFragDataIndex);
    }

    public static void nglBindFragDataLocationIndexed(int n, int n2, int n3, long l) {
        GL33C.nglBindFragDataLocationIndexed(n, n2, n3, l);
    }

    public static void glBindFragDataLocationIndexed(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL33C.glBindFragDataLocationIndexed(n, n2, n3, byteBuffer);
    }

    public static void glBindFragDataLocationIndexed(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL33C.glBindFragDataLocationIndexed(n, n2, n3, charSequence);
    }

    public static int nglGetFragDataIndex(int n, long l) {
        return GL33C.nglGetFragDataIndex(n, l);
    }

    @NativeType(value="GLint")
    public static int glGetFragDataIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL33C.glGetFragDataIndex(n, byteBuffer);
    }

    @NativeType(value="GLint")
    public static int glGetFragDataIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL33C.glGetFragDataIndex(n, charSequence);
    }

    static {
        GL.initialize();
    }
}

