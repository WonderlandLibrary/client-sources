/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.opengl.EXTMemoryObject;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTSemaphore {
    public static final int GL_NUM_DEVICE_UUIDS_EXT = 38294;
    public static final int GL_DEVICE_UUID_EXT = 38295;
    public static final int GL_DRIVER_UUID_EXT = 38296;
    public static final int GL_UUID_SIZE_EXT = 16;
    public static final int GL_LAYOUT_GENERAL_EXT = 38285;
    public static final int GL_LAYOUT_COLOR_ATTACHMENT_EXT = 38286;
    public static final int GL_LAYOUT_DEPTH_STENCIL_ATTACHMENT_EXT = 38287;
    public static final int GL_LAYOUT_DEPTH_STENCIL_READ_ONLY_EXT = 38288;
    public static final int GL_LAYOUT_SHADER_READ_ONLY_EXT = 38289;
    public static final int GL_LAYOUT_TRANSFER_SRC_EXT = 38290;
    public static final int GL_LAYOUT_TRANSFER_DST_EXT = 38291;
    public static final int GL_LAYOUT_DEPTH_READ_ONLY_STENCIL_ATTACHMENT_EXT = 38192;
    public static final int GL_LAYOUT_DEPTH_ATTACHMENT_STENCIL_READ_ONLY_EXT = 38193;

    protected EXTSemaphore() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetUnsignedBytevEXT, gLCapabilities.glGetUnsignedBytei_vEXT, gLCapabilities.glGenSemaphoresEXT, gLCapabilities.glDeleteSemaphoresEXT, gLCapabilities.glIsSemaphoreEXT, gLCapabilities.glSemaphoreParameterui64vEXT, gLCapabilities.glGetSemaphoreParameterui64vEXT, gLCapabilities.glWaitSemaphoreEXT, gLCapabilities.glSignalSemaphoreEXT);
    }

    public static void nglGetUnsignedBytevEXT(int n, long l) {
        EXTMemoryObject.nglGetUnsignedBytevEXT(n, l);
    }

    public static void glGetUnsignedBytevEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLubyte *") ByteBuffer byteBuffer) {
        EXTMemoryObject.glGetUnsignedBytevEXT(n, byteBuffer);
    }

    public static void nglGetUnsignedBytei_vEXT(int n, int n2, long l) {
        EXTMemoryObject.nglGetUnsignedBytei_vEXT(n, n2, l);
    }

    public static void glGetUnsignedBytei_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLubyte *") ByteBuffer byteBuffer) {
        EXTMemoryObject.glGetUnsignedBytei_vEXT(n, n2, byteBuffer);
    }

    public static native void nglGenSemaphoresEXT(int var0, long var1);

    public static void glGenSemaphoresEXT(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        EXTSemaphore.nglGenSemaphoresEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenSemaphoresEXT() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTSemaphore.nglGenSemaphoresEXT(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeleteSemaphoresEXT(int var0, long var1);

    public static void glDeleteSemaphoresEXT(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTSemaphore.nglDeleteSemaphoresEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteSemaphoresEXT(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTSemaphore.nglDeleteSemaphoresEXT(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsSemaphoreEXT(@NativeType(value="GLuint") int var0);

    public static native void nglSemaphoreParameterui64vEXT(int var0, int var1, long var2);

    public static void glSemaphoreParameterui64vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        EXTSemaphore.nglSemaphoreParameterui64vEXT(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glSemaphoreParameterui64EXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 const *") long l) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.longs(l);
            EXTSemaphore.nglSemaphoreParameterui64vEXT(n, n2, MemoryUtil.memAddress(longBuffer));
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetSemaphoreParameterui64vEXT(int var0, int var1, long var2);

    public static void glGetSemaphoreParameterui64vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        EXTSemaphore.nglGetSemaphoreParameterui64vEXT(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetSemaphoreParameterui64EXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            EXTSemaphore.nglGetSemaphoreParameterui64vEXT(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglWaitSemaphoreEXT(int var0, int var1, long var2, int var4, long var5, long var7);

    public static void glWaitSemaphoreEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2, @NativeType(value="GLenum const *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer3, intBuffer2.remaining());
        }
        EXTSemaphore.nglWaitSemaphoreEXT(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), intBuffer2.remaining(), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3));
    }

    public static native void nglSignalSemaphoreEXT(int var0, int var1, long var2, int var4, long var5, long var7);

    public static void glSignalSemaphoreEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLuint const *") IntBuffer intBuffer2, @NativeType(value="GLenum const *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer3, intBuffer2.remaining());
        }
        EXTSemaphore.nglSignalSemaphoreEXT(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), intBuffer2.remaining(), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3));
    }

    public static void glGenSemaphoresEXT(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenSemaphoresEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeleteSemaphoresEXT(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteSemaphoresEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glSemaphoreParameterui64vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 const *") long[] lArray) {
        long l = GL.getICD().glSemaphoreParameterui64vEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glGetSemaphoreParameterui64vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 *") long[] lArray) {
        long l = GL.getICD().glGetSemaphoreParameterui64vEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glWaitSemaphoreEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2, @NativeType(value="GLenum const *") int[] nArray3) {
        long l = GL.getICD().glWaitSemaphoreEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray3, nArray2.length);
        }
        JNI.callPPPV(n, nArray.length, nArray, nArray2.length, nArray2, nArray3, l);
    }

    public static void glSignalSemaphoreEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLuint const *") int[] nArray2, @NativeType(value="GLenum const *") int[] nArray3) {
        long l = GL.getICD().glSignalSemaphoreEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray3, nArray2.length);
        }
        JNI.callPPPV(n, nArray.length, nArray, nArray2.length, nArray2, nArray3, l);
    }

    static {
        GL.initialize();
    }
}

