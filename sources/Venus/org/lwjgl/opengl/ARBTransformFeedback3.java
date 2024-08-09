/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL40C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTransformFeedback3 {
    public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
    public static final int GL_MAX_VERTEX_STREAMS = 36465;

    protected ARBTransformFeedback3() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawTransformFeedbackStream, gLCapabilities.glBeginQueryIndexed, gLCapabilities.glEndQueryIndexed, gLCapabilities.glGetQueryIndexediv);
    }

    public static void glDrawTransformFeedbackStream(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL40C.glDrawTransformFeedbackStream(n, n2, n3);
    }

    public static void glBeginQueryIndexed(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL40C.glBeginQueryIndexed(n, n2, n3);
    }

    public static void glEndQueryIndexed(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL40C.glEndQueryIndexed(n, n2);
    }

    public static void nglGetQueryIndexediv(int n, int n2, int n3, long l) {
        GL40C.nglGetQueryIndexediv(n, n2, n3, l);
    }

    public static void glGetQueryIndexediv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL40C.glGetQueryIndexediv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetQueryIndexedi(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3) {
        return GL40C.glGetQueryIndexedi(n, n2, n3);
    }

    public static void glGetQueryIndexediv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL40C.glGetQueryIndexediv(n, n2, n3, nArray);
    }

    static {
        GL.initialize();
    }
}

