/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTMemoryObject {
    public static final int GL_TEXTURE_TILING_EXT = 38272;
    public static final int GL_DEDICATED_MEMORY_OBJECT_EXT = 38273;
    public static final int GL_NUM_TILING_TYPES_EXT = 38274;
    public static final int GL_TILING_TYPES_EXT = 38275;
    public static final int GL_OPTIMAL_TILING_EXT = 38276;
    public static final int GL_LINEAR_TILING_EXT = 38277;
    public static final int GL_NUM_DEVICE_UUIDS_EXT = 38294;
    public static final int GL_DEVICE_UUID_EXT = 38295;
    public static final int GL_DRIVER_UUID_EXT = 38296;
    public static final int GL_UUID_SIZE_EXT = 16;

    protected EXTMemoryObject() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glGetUnsignedBytevEXT, gLCapabilities.glGetUnsignedBytei_vEXT, gLCapabilities.glDeleteMemoryObjectsEXT, gLCapabilities.glIsMemoryObjectEXT, gLCapabilities.glCreateMemoryObjectsEXT, gLCapabilities.glMemoryObjectParameterivEXT, gLCapabilities.glGetMemoryObjectParameterivEXT, gLCapabilities.glTexStorageMem2DEXT, gLCapabilities.glTexStorageMem2DMultisampleEXT, gLCapabilities.glTexStorageMem3DEXT, gLCapabilities.glTexStorageMem3DMultisampleEXT, gLCapabilities.glBufferStorageMemEXT, gLCapabilities.hasDSA(set) ? gLCapabilities.glTextureStorageMem2DEXT : -1L, gLCapabilities.hasDSA(set) ? gLCapabilities.glTextureStorageMem2DMultisampleEXT : -1L, gLCapabilities.hasDSA(set) ? gLCapabilities.glTextureStorageMem3DEXT : -1L, gLCapabilities.hasDSA(set) ? gLCapabilities.glTextureStorageMem3DMultisampleEXT : -1L, gLCapabilities.hasDSA(set) ? gLCapabilities.glNamedBufferStorageMemEXT : -1L, gLCapabilities.glTexStorageMem1DEXT, gLCapabilities.hasDSA(set) ? gLCapabilities.glTextureStorageMem1DEXT : -1L);
    }

    public static native void nglGetUnsignedBytevEXT(int var0, long var1);

    public static void glGetUnsignedBytevEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLubyte *") ByteBuffer byteBuffer) {
        EXTMemoryObject.nglGetUnsignedBytevEXT(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetUnsignedBytei_vEXT(int var0, int var1, long var2);

    public static void glGetUnsignedBytei_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLubyte *") ByteBuffer byteBuffer) {
        EXTMemoryObject.nglGetUnsignedBytei_vEXT(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglDeleteMemoryObjectsEXT(int var0, long var1);

    public static void glDeleteMemoryObjectsEXT(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTMemoryObject.nglDeleteMemoryObjectsEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteMemoryObjectsEXT(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTMemoryObject.nglDeleteMemoryObjectsEXT(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsMemoryObjectEXT(@NativeType(value="GLuint") int var0);

    public static native void nglCreateMemoryObjectsEXT(int var0, long var1);

    public static void glCreateMemoryObjectsEXT(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        EXTMemoryObject.nglCreateMemoryObjectsEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glCreateMemoryObjectsEXT() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTMemoryObject.nglCreateMemoryObjectsEXT(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglMemoryObjectParameterivEXT(int var0, int var1, long var2);

    public static void glMemoryObjectParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTMemoryObject.nglMemoryObjectParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glMemoryObjectParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n3);
            EXTMemoryObject.nglMemoryObjectParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMemoryObjectParameterivEXT(int var0, int var1, long var2);

    public static void glGetMemoryObjectParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTMemoryObject.nglGetMemoryObjectParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMemoryObjectParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTMemoryObject.nglGetMemoryObjectParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glTexStorageMem2DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLuint") int var5, @NativeType(value="GLuint64") long var6);

    public static native void glTexStorageMem2DMultisampleEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLboolean") boolean var5, @NativeType(value="GLuint") int var6, @NativeType(value="GLuint64") long var7);

    public static native void glTexStorageMem3DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLuint") int var6, @NativeType(value="GLuint64") long var7);

    public static native void glTexStorageMem3DMultisampleEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLboolean") boolean var6, @NativeType(value="GLuint") int var7, @NativeType(value="GLuint64") long var8);

    public static native void glBufferStorageMemEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizeiptr") long var1, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint64") long var4);

    public static native void glTextureStorageMem2DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLuint") int var5, @NativeType(value="GLuint64") long var6);

    public static native void glTextureStorageMem2DMultisampleEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLboolean") boolean var5, @NativeType(value="GLuint") int var6, @NativeType(value="GLuint64") long var7);

    public static native void glTextureStorageMem3DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLuint") int var6, @NativeType(value="GLuint64") long var7);

    public static native void glTextureStorageMem3DMultisampleEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLboolean") boolean var6, @NativeType(value="GLuint") int var7, @NativeType(value="GLuint64") long var8);

    public static native void glNamedBufferStorageMemEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizeiptr") long var1, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint64") long var4);

    public static native void glTexStorageMem1DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLuint") int var4, @NativeType(value="GLuint64") long var5);

    public static native void glTextureStorageMem1DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLuint") int var4, @NativeType(value="GLuint64") long var5);

    public static void glDeleteMemoryObjectsEXT(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteMemoryObjectsEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glCreateMemoryObjectsEXT(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glCreateMemoryObjectsEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glMemoryObjectParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMemoryObjectParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetMemoryObjectParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMemoryObjectParameterivEXT;
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

