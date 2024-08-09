/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBGLSPIRV {
    public static final int GL_SHADER_BINARY_FORMAT_SPIR_V_ARB = 38225;
    public static final int GL_SPIR_V_BINARY_ARB = 38226;

    protected ARBGLSPIRV() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glSpecializeShaderARB);
    }

    public static native void nglSpecializeShaderARB(int var0, long var1, int var3, long var4, long var6);

    public static void glSpecializeShaderARB(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        ARBGLSPIRV.nglSpecializeShaderARB(n, MemoryUtil.memAddress(byteBuffer), intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glSpecializeShaderARB(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            ARBGLSPIRV.nglSpecializeShaderARB(n, l, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static void glSpecializeShaderARB(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        long l = GL.getICD().glSpecializeShaderARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkNT1(byteBuffer);
            Checks.check(nArray2, nArray.length);
        }
        JNI.callPPPV(n, MemoryUtil.memAddress(byteBuffer), nArray.length, nArray, nArray2, l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glSpecializeShaderARB(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2) {
        long l = GL.getICD().glSpecializeShaderARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray2, nArray.length);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l2 = memoryStack.getPointerAddress();
            JNI.callPPPV(n, l2, nArray.length, nArray, nArray2, l);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    static {
        GL.initialize();
    }
}

