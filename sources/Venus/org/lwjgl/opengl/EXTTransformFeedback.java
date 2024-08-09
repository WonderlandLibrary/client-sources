/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTDrawBuffers2;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTTransformFeedback {
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_EXT = 35982;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_EXT = 35972;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_EXT = 35973;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_EXT = 35983;
    public static final int GL_INTERLEAVED_ATTRIBS_EXT = 35980;
    public static final int GL_SEPARATE_ATTRIBS_EXT = 35981;
    public static final int GL_PRIMITIVES_GENERATED_EXT = 35975;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_EXT = 35976;
    public static final int GL_RASTERIZER_DISCARD_EXT = 35977;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_EXT = 35978;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_EXT = 35979;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_EXT = 35968;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_EXT = 35971;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_EXT = 35967;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH_EXT = 35958;

    protected EXTTransformFeedback() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindBufferRangeEXT, gLCapabilities.glBindBufferOffsetEXT, gLCapabilities.glBindBufferBaseEXT, gLCapabilities.glBeginTransformFeedbackEXT, gLCapabilities.glEndTransformFeedbackEXT, gLCapabilities.glTransformFeedbackVaryingsEXT, gLCapabilities.glGetTransformFeedbackVaryingEXT, gLCapabilities.glGetIntegerIndexedvEXT, gLCapabilities.glGetBooleanIndexedvEXT);
    }

    public static native void glBindBufferRangeEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLintptr") long var3, @NativeType(value="GLsizeiptr") long var5);

    public static native void glBindBufferOffsetEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLintptr") long var3);

    public static native void glBindBufferBaseEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glBeginTransformFeedbackEXT(@NativeType(value="GLenum") int var0);

    public static native void glEndTransformFeedbackEXT();

    public static native void nglTransformFeedbackVaryingsEXT(int var0, int var1, long var2, int var4);

    public static void glTransformFeedbackVaryingsEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const * const *") PointerBuffer pointerBuffer, @NativeType(value="GLenum") int n2) {
        EXTTransformFeedback.nglTransformFeedbackVaryingsEXT(n, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), n2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glTransformFeedbackVaryingsEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const * const *") CharSequence[] charSequenceArray, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArray(memoryStack, MemoryUtil::memASCII, charSequenceArray);
            EXTTransformFeedback.nglTransformFeedbackVaryingsEXT(n, charSequenceArray.length, l, n2);
            APIUtil.apiArrayFree(l, charSequenceArray.length);
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glTransformFeedbackVaryingsEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const * const *") CharSequence charSequence, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArray(memoryStack, MemoryUtil::memASCII, charSequence);
            EXTTransformFeedback.nglTransformFeedbackVaryingsEXT(n, 1, l, n2);
            APIUtil.apiArrayFree(l, 1);
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetTransformFeedbackVaryingEXT(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static void glGetTransformFeedbackVaryingEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLsizei *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        EXTTransformFeedback.nglGetTransformFeedbackVaryingEXT(n, n2, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetTransformFeedbackVaryingEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer3 = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n3);
            EXTTransformFeedback.nglGetTransformFeedbackVaryingEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memASCII(byteBuffer, intBuffer3.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    @NativeType(value="void")
    public static String glGetTransformFeedbackVaryingEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return EXTTransformFeedback.glGetTransformFeedbackVaryingEXT(n, n2, GL.getCapabilities().OpenGL20 ? GL20.glGetProgrami(n, 35958) : ARBShaderObjects.glGetObjectParameteriARB(n, 35958), intBuffer, intBuffer2);
    }

    public static void nglGetIntegerIndexedvEXT(int n, int n2, long l) {
        EXTDrawBuffers2.nglGetIntegerIndexedvEXT(n, n2, l);
    }

    public static void glGetIntegerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        EXTDrawBuffers2.glGetIntegerIndexedvEXT(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetIntegerIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2);
    }

    public static void nglGetBooleanIndexedvEXT(int n, int n2, long l) {
        EXTDrawBuffers2.nglGetBooleanIndexedvEXT(n, n2, l);
    }

    public static void glGetBooleanIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        EXTDrawBuffers2.glGetBooleanIndexedvEXT(n, n2, byteBuffer);
    }

    @NativeType(value="void")
    public static boolean glGetBooleanIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return EXTDrawBuffers2.glGetBooleanIndexedEXT(n, n2);
    }

    public static void glGetTransformFeedbackVaryingEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLsizei *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetTransformFeedbackVaryingEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        JNI.callPPPPV(n, n2, byteBuffer.remaining(), nArray, nArray2, nArray3, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetIntegerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") int[] nArray) {
        EXTDrawBuffers2.glGetIntegerIndexedvEXT(n, n2, nArray);
    }

    static {
        GL.initialize();
    }
}

