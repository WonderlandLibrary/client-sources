/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVTransformFeedback2 {
    public static final int GL_TRANSFORM_FEEDBACK_NV = 36386;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED_NV = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE_NV = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING_NV = 36389;

    protected NVTransformFeedback2() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindTransformFeedbackNV, gLCapabilities.glDeleteTransformFeedbacksNV, gLCapabilities.glGenTransformFeedbacksNV, gLCapabilities.glIsTransformFeedbackNV, gLCapabilities.glPauseTransformFeedbackNV, gLCapabilities.glResumeTransformFeedbackNV, gLCapabilities.glDrawTransformFeedbackNV);
    }

    public static native void glBindTransformFeedbackNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteTransformFeedbacksNV(int var0, long var1);

    public static void glDeleteTransformFeedbacksNV(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        NVTransformFeedback2.nglDeleteTransformFeedbacksNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteTransformFeedbacksNV(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            NVTransformFeedback2.nglDeleteTransformFeedbacksNV(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenTransformFeedbacksNV(int var0, long var1);

    public static void glGenTransformFeedbacksNV(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        NVTransformFeedback2.nglGenTransformFeedbacksNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenTransformFeedbacksNV() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            NVTransformFeedback2.nglGenTransformFeedbacksNV(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsTransformFeedbackNV(@NativeType(value="GLuint") int var0);

    public static native void glPauseTransformFeedbackNV();

    public static native void glResumeTransformFeedbackNV();

    public static native void glDrawTransformFeedbackNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static void glDeleteTransformFeedbacksNV(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteTransformFeedbacksNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenTransformFeedbacksNV(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenTransformFeedbacksNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    static {
        GL.initialize();
    }
}

