/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVTransformFeedback {
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_NV = 35982;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_NV = 35972;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_NV = 35973;
    public static final int GL_TRANSFORM_FEEDBACK_RECORD_NV = 35974;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_NV = 35983;
    public static final int GL_INTERLEAVED_ATTRIBS_NV = 35980;
    public static final int GL_SEPARATE_ATTRIBS_NV = 35981;
    public static final int GL_PRIMITIVES_GENERATED_NV = 35975;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_NV = 35976;
    public static final int GL_RASTERIZER_DISCARD_NV = 35977;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_NV = 35978;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_NV = 35979;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_NV = 35968;
    public static final int GL_TRANSFORM_FEEDBACK_ATTRIBS_NV = 35966;
    public static final int GL_ACTIVE_VARYINGS_NV = 35969;
    public static final int GL_ACTIVE_VARYING_MAX_LENGTH_NV = 35970;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_NV = 35971;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_NV = 35967;
    public static final int GL_BACK_PRIMARY_COLOR_NV = 35959;
    public static final int GL_BACK_SECONDARY_COLOR_NV = 35960;
    public static final int GL_TEXTURE_COORD_NV = 35961;
    public static final int GL_CLIP_DISTANCE_NV = 35962;
    public static final int GL_VERTEX_ID_NV = 35963;
    public static final int GL_PRIMITIVE_ID_NV = 35964;
    public static final int GL_GENERIC_ATTRIB_NV = 35965;
    public static final int GL_SECONDARY_COLOR_NV = 34093;
    public static final int GL_LAYER_NV = 36266;

    protected NVTransformFeedback() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBeginTransformFeedbackNV, gLCapabilities.glEndTransformFeedbackNV, gLCapabilities.glTransformFeedbackAttribsNV, gLCapabilities.glBindBufferRangeNV, gLCapabilities.glBindBufferOffsetNV, gLCapabilities.glBindBufferBaseNV, gLCapabilities.glTransformFeedbackVaryingsNV, gLCapabilities.glActiveVaryingNV, gLCapabilities.glGetVaryingLocationNV, gLCapabilities.glGetActiveVaryingNV, gLCapabilities.glGetTransformFeedbackVaryingNV, gLCapabilities.glTransformFeedbackStreamAttribsNV);
    }

    public static native void glBeginTransformFeedbackNV(@NativeType(value="GLenum") int var0);

    public static native void glEndTransformFeedbackNV();

    public static native void nglTransformFeedbackAttribsNV(int var0, long var1, int var3);

    public static void glTransformFeedbackAttribsNV(@NativeType(value="GLint const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n) {
        NVTransformFeedback.nglTransformFeedbackAttribsNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), n);
    }

    public static native void glBindBufferRangeNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLintptr") long var3, @NativeType(value="GLsizeiptr") long var5);

    public static native void glBindBufferOffsetNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLintptr") long var3);

    public static native void glBindBufferBaseNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2);

    public static native void nglTransformFeedbackVaryingsNV(int var0, int var1, long var2, int var4);

    public static void glTransformFeedbackVaryingsNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2) {
        NVTransformFeedback.nglTransformFeedbackVaryingsNV(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), n2);
    }

    public static native void nglActiveVaryingNV(int var0, long var1);

    public static void glActiveVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        NVTransformFeedback.nglActiveVaryingNV(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glActiveVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            NVTransformFeedback.nglActiveVaryingNV(n, l);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native int nglGetVaryingLocationNV(int var0, long var1);

    @NativeType(value="GLint")
    public static int glGetVaryingLocationNV(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return NVTransformFeedback.nglGetVaryingLocationNV(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetVaryingLocationNV(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = NVTransformFeedback.nglGetVaryingLocationNV(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetActiveVaryingNV(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static void glGetActiveVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLsizei *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        NVTransformFeedback.nglGetActiveVaryingNV(n, n2, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetTransformFeedbackVaryingNV(int var0, int var1, long var2);

    public static void glGetTransformFeedbackVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        NVTransformFeedback.nglGetTransformFeedbackVaryingNV(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTransformFeedbackVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            NVTransformFeedback.nglGetTransformFeedbackVaryingNV(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglTransformFeedbackStreamAttribsNV(int var0, long var1, int var3, long var4, int var6);

    public static void glTransformFeedbackStreamAttribsNV(@NativeType(value="GLint const *") IntBuffer intBuffer, @NativeType(value="GLint const *") IntBuffer intBuffer2, @NativeType(value="GLenum") int n) {
        NVTransformFeedback.nglTransformFeedbackStreamAttribsNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), intBuffer2.remaining(), MemoryUtil.memAddress(intBuffer2), n);
    }

    public static void glTransformFeedbackAttribsNV(@NativeType(value="GLint const *") int[] nArray, @NativeType(value="GLenum") int n) {
        long l = GL.getICD().glTransformFeedbackAttribsNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, n, l);
    }

    public static void glTransformFeedbackVaryingsNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glTransformFeedbackVaryingsNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, n2, l);
    }

    public static void glGetActiveVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLsizei *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveVaryingNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        JNI.callPPPPV(n, n2, byteBuffer.remaining(), nArray, nArray2, nArray3, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetTransformFeedbackVaryingNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTransformFeedbackVaryingNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glTransformFeedbackStreamAttribsNV(@NativeType(value="GLint const *") int[] nArray, @NativeType(value="GLint const *") int[] nArray2, @NativeType(value="GLenum") int n) {
        long l = GL.getICD().glTransformFeedbackStreamAttribsNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(nArray.length, nArray, nArray2.length, nArray2, n, l);
    }

    static {
        GL.initialize();
    }
}

