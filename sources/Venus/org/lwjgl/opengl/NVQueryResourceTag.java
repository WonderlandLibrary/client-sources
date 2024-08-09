/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVQueryResourceTag {
    protected NVQueryResourceTag() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGenQueryResourceTagNV, gLCapabilities.glDeleteQueryResourceTagNV, gLCapabilities.glQueryResourceTagNV);
    }

    public static native void nglGenQueryResourceTagNV(int var0, long var1);

    public static void glGenQueryResourceTagNV(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        NVQueryResourceTag.nglGenQueryResourceTagNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenQueryResourceTagNV() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            NVQueryResourceTag.nglGenQueryResourceTagNV(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeleteQueryResourceTagNV(int var0, long var1);

    public static void glDeleteQueryResourceTagNV(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        NVQueryResourceTag.nglDeleteQueryResourceTagNV(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteQueryResourceTagNV(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            NVQueryResourceTag.nglDeleteQueryResourceTagNV(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglQueryResourceTagNV(int var0, long var1);

    public static void glQueryResourceTagNV(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        NVQueryResourceTag.nglQueryResourceTagNV(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glQueryResourceTagNV(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            NVQueryResourceTag.nglQueryResourceTagNV(n, l);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static void glGenQueryResourceTagNV(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenQueryResourceTagNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeleteQueryResourceTagNV(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteQueryResourceTagNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    static {
        GL.initialize();
    }
}

