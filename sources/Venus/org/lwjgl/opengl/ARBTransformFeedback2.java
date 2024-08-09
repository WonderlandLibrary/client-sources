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

public class ARBTransformFeedback2 {
    public static final int GL_TRANSFORM_FEEDBACK = 36386;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING = 36389;

    protected ARBTransformFeedback2() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindTransformFeedback, gLCapabilities.glDeleteTransformFeedbacks, gLCapabilities.glGenTransformFeedbacks, gLCapabilities.glIsTransformFeedback, gLCapabilities.glPauseTransformFeedback, gLCapabilities.glResumeTransformFeedback, gLCapabilities.glDrawTransformFeedback);
    }

    public static void glBindTransformFeedback(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL40C.glBindTransformFeedback(n, n2);
    }

    public static void nglDeleteTransformFeedbacks(int n, long l) {
        GL40C.nglDeleteTransformFeedbacks(n, l);
    }

    public static void glDeleteTransformFeedbacks(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL40C.glDeleteTransformFeedbacks(intBuffer);
    }

    public static void glDeleteTransformFeedbacks(@NativeType(value="GLuint const *") int n) {
        GL40C.glDeleteTransformFeedbacks(n);
    }

    public static void nglGenTransformFeedbacks(int n, long l) {
        GL40C.nglGenTransformFeedbacks(n, l);
    }

    public static void glGenTransformFeedbacks(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL40C.glGenTransformFeedbacks(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenTransformFeedbacks() {
        return GL40C.glGenTransformFeedbacks();
    }

    @NativeType(value="GLboolean")
    public static boolean glIsTransformFeedback(@NativeType(value="GLuint") int n) {
        return GL40C.glIsTransformFeedback(n);
    }

    public static void glPauseTransformFeedback() {
        GL40C.glPauseTransformFeedback();
    }

    public static void glResumeTransformFeedback() {
        GL40C.glResumeTransformFeedback();
    }

    public static void glDrawTransformFeedback(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL40C.glDrawTransformFeedback(n, n2);
    }

    public static void glDeleteTransformFeedbacks(@NativeType(value="GLuint const *") int[] nArray) {
        GL40C.glDeleteTransformFeedbacks(nArray);
    }

    public static void glGenTransformFeedbacks(@NativeType(value="GLuint *") int[] nArray) {
        GL40C.glGenTransformFeedbacks(nArray);
    }

    static {
        GL.initialize();
    }
}

