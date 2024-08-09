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
import org.lwjgl.opengl.GLDebugMessageARBCallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBDebugOutput {
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS_ARB = 33346;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_ARB = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_ARB = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES_ARB = 37189;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH_ARB = 33347;
    public static final int GL_DEBUG_CALLBACK_FUNCTION_ARB = 33348;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM_ARB = 33349;
    public static final int GL_DEBUG_SOURCE_API_ARB = 33350;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM_ARB = 33351;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER_ARB = 33352;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY_ARB = 33353;
    public static final int GL_DEBUG_SOURCE_APPLICATION_ARB = 33354;
    public static final int GL_DEBUG_SOURCE_OTHER_ARB = 33355;
    public static final int GL_DEBUG_TYPE_ERROR_ARB = 33356;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_ARB = 33357;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_ARB = 33358;
    public static final int GL_DEBUG_TYPE_PORTABILITY_ARB = 33359;
    public static final int GL_DEBUG_TYPE_PERFORMANCE_ARB = 33360;
    public static final int GL_DEBUG_TYPE_OTHER_ARB = 33361;
    public static final int GL_DEBUG_SEVERITY_HIGH_ARB = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM_ARB = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW_ARB = 37192;

    protected ARBDebugOutput() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDebugMessageControlARB, gLCapabilities.glDebugMessageInsertARB, gLCapabilities.glDebugMessageCallbackARB, gLCapabilities.glGetDebugMessageLogARB);
    }

    public static native void nglDebugMessageControlARB(int var0, int var1, int var2, int var3, long var4, boolean var6);

    public static void glDebugMessageControlARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLboolean") boolean bl) {
        ARBDebugOutput.nglDebugMessageControlARB(n, n2, n3, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer), bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDebugMessageControlARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") int n4, @NativeType(value="GLboolean") boolean bl) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n4);
            ARBDebugOutput.nglDebugMessageControlARB(n, n2, n3, 1, MemoryUtil.memAddress(intBuffer), bl);
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglDebugMessageInsertARB(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glDebugMessageInsertARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        ARBDebugOutput.nglDebugMessageInsertARB(n, n2, n3, n4, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDebugMessageInsertARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            int n6 = memoryStack.nUTF8(charSequence, true);
            long l = memoryStack.getPointerAddress();
            ARBDebugOutput.nglDebugMessageInsertARB(n, n2, n3, n4, n6, l);
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglDebugMessageCallbackARB(long var0, long var2);

    public static void glDebugMessageCallbackARB(@Nullable @NativeType(value="GLDEBUGPROCARB") GLDebugMessageARBCallbackI gLDebugMessageARBCallbackI, @NativeType(value="void const *") long l) {
        ARBDebugOutput.nglDebugMessageCallbackARB(MemoryUtil.memAddressSafe(gLDebugMessageARBCallbackI), l);
    }

    public static native int nglGetDebugMessageLogARB(int var0, int var1, long var2, long var4, long var6, long var8, long var10, long var12);

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLogARB(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer2, @Nullable @NativeType(value="GLuint *") IntBuffer intBuffer3, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer4, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer5, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, n);
            Checks.checkSafe((Buffer)intBuffer2, n);
            Checks.checkSafe((Buffer)intBuffer3, n);
            Checks.checkSafe((Buffer)intBuffer4, n);
            Checks.checkSafe((Buffer)intBuffer5, n);
        }
        return ARBDebugOutput.nglGetDebugMessageLogARB(n, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4), MemoryUtil.memAddressSafe(intBuffer5), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glDebugMessageControlARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @Nullable @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLboolean") boolean bl) {
        long l = GL.getICD().glDebugMessageControlARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, Checks.lengthSafe(nArray), nArray, bl, l);
    }

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLogARB(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") int[] nArray, @Nullable @NativeType(value="GLenum *") int[] nArray2, @Nullable @NativeType(value="GLuint *") int[] nArray3, @Nullable @NativeType(value="GLenum *") int[] nArray4, @Nullable @NativeType(value="GLsizei *") int[] nArray5, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetDebugMessageLogARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, n);
            Checks.checkSafe(nArray2, n);
            Checks.checkSafe(nArray3, n);
            Checks.checkSafe(nArray4, n);
            Checks.checkSafe(nArray5, n);
        }
        return JNI.callPPPPPPI(n, Checks.remainingSafe(byteBuffer), nArray, nArray2, nArray3, nArray4, nArray5, MemoryUtil.memAddressSafe(byteBuffer), l);
    }

    static {
        GL.initialize();
    }
}

