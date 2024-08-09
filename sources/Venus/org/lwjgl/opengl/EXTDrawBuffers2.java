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

public class EXTDrawBuffers2 {
    protected EXTDrawBuffers2() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glColorMaskIndexedEXT, gLCapabilities.glGetBooleanIndexedvEXT, gLCapabilities.glGetIntegerIndexedvEXT, gLCapabilities.glEnableIndexedEXT, gLCapabilities.glDisableIndexedEXT, gLCapabilities.glIsEnabledIndexedEXT);
    }

    public static native void glColorMaskIndexedEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLboolean") boolean var1, @NativeType(value="GLboolean") boolean var2, @NativeType(value="GLboolean") boolean var3, @NativeType(value="GLboolean") boolean var4);

    public static native void nglGetBooleanIndexedvEXT(int var0, int var1, long var2);

    public static void glGetBooleanIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 1);
        }
        EXTDrawBuffers2.nglGetBooleanIndexedvEXT(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static boolean glGetBooleanIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            ByteBuffer byteBuffer = memoryStack.calloc(1);
            EXTDrawBuffers2.nglGetBooleanIndexedvEXT(n, n2, MemoryUtil.memAddress(byteBuffer));
            boolean bl = byteBuffer.get(0) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetIntegerIndexedvEXT(int var0, int var1, long var2);

    public static void glGetIntegerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDrawBuffers2.nglGetIntegerIndexedvEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetIntegerIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDrawBuffers2.nglGetIntegerIndexedvEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glEnableIndexedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDisableIndexedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    @NativeType(value="GLboolean")
    public static native boolean glIsEnabledIndexedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static void glGetIntegerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetIntegerIndexedvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    static {
        GL.initialize();
    }
}

