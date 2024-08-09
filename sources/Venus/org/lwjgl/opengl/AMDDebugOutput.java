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
import org.lwjgl.opengl.GLDebugMessageAMDCallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AMDDebugOutput {
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_AMD = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_AMD = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES_AMD = 37189;
    public static final int GL_DEBUG_SEVERITY_HIGH_AMD = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM_AMD = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW_AMD = 37192;
    public static final int GL_DEBUG_CATEGORY_API_ERROR_AMD = 37193;
    public static final int GL_DEBUG_CATEGORY_WINDOW_SYSTEM_AMD = 37194;
    public static final int GL_DEBUG_CATEGORY_DEPRECATION_AMD = 37195;
    public static final int GL_DEBUG_CATEGORY_UNDEFINED_BEHAVIOR_AMD = 37196;
    public static final int GL_DEBUG_CATEGORY_PERFORMANCE_AMD = 37197;
    public static final int GL_DEBUG_CATEGORY_SHADER_COMPILER_AMD = 37198;
    public static final int GL_DEBUG_CATEGORY_APPLICATION_AMD = 37199;
    public static final int GL_DEBUG_CATEGORY_OTHER_AMD = 37200;

    protected AMDDebugOutput() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDebugMessageEnableAMD, gLCapabilities.glDebugMessageInsertAMD, gLCapabilities.glDebugMessageCallbackAMD, gLCapabilities.glGetDebugMessageLogAMD);
    }

    public static native void nglDebugMessageEnableAMD(int var0, int var1, int var2, long var3, boolean var5);

    public static void glDebugMessageEnableAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLboolean") boolean bl) {
        AMDDebugOutput.nglDebugMessageEnableAMD(n, n2, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer), bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDebugMessageEnableAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int n3, @NativeType(value="GLboolean") boolean bl) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n3);
            AMDDebugOutput.nglDebugMessageEnableAMD(n, n2, 1, MemoryUtil.memAddress(intBuffer), bl);
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglDebugMessageInsertAMD(int var0, int var1, int var2, int var3, long var4);

    public static void glDebugMessageInsertAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        AMDDebugOutput.nglDebugMessageInsertAMD(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDebugMessageInsertAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            int n5 = memoryStack.nUTF8(charSequence, true);
            long l = memoryStack.getPointerAddress();
            AMDDebugOutput.nglDebugMessageInsertAMD(n, n2, n3, n5, l);
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglDebugMessageCallbackAMD(long var0, long var2);

    public static void glDebugMessageCallbackAMD(@Nullable @NativeType(value="GLDEBUGPROCAMD") GLDebugMessageAMDCallbackI gLDebugMessageAMDCallbackI, @NativeType(value="void *") long l) {
        AMDDebugOutput.nglDebugMessageCallbackAMD(MemoryUtil.memAddressSafe(gLDebugMessageAMDCallbackI), l);
    }

    public static native int nglGetDebugMessageLogAMD(int var0, int var1, long var2, long var4, long var6, long var8, long var10);

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLogAMD(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer, @Nullable @NativeType(value="GLuint *") IntBuffer intBuffer2, @Nullable @NativeType(value="GLuint *") IntBuffer intBuffer3, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer4, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, n);
            Checks.checkSafe((Buffer)intBuffer2, n);
            Checks.checkSafe((Buffer)intBuffer3, n);
            Checks.checkSafe((Buffer)intBuffer4, n);
        }
        return AMDDebugOutput.nglGetDebugMessageLogAMD(n, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glDebugMessageEnableAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @Nullable @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLboolean") boolean bl) {
        long l = GL.getICD().glDebugMessageEnableAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, Checks.lengthSafe(nArray), nArray, bl, l);
    }

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLogAMD(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") int[] nArray, @Nullable @NativeType(value="GLuint *") int[] nArray2, @Nullable @NativeType(value="GLuint *") int[] nArray3, @Nullable @NativeType(value="GLsizei *") int[] nArray4, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetDebugMessageLogAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, n);
            Checks.checkSafe(nArray2, n);
            Checks.checkSafe(nArray3, n);
            Checks.checkSafe(nArray4, n);
        }
        return JNI.callPPPPPI(n, Checks.remainingSafe(byteBuffer), nArray, nArray2, nArray3, nArray4, MemoryUtil.memAddressSafe(byteBuffer), l);
    }

    static {
        GL.initialize();
    }
}

