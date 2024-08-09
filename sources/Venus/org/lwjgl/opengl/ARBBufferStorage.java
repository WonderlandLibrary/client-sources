/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL44C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBBufferStorage {
    public static final int GL_MAP_PERSISTENT_BIT = 64;
    public static final int GL_MAP_COHERENT_BIT = 128;
    public static final int GL_DYNAMIC_STORAGE_BIT = 256;
    public static final int GL_CLIENT_STORAGE_BIT = 512;
    public static final int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
    public static final int GL_BUFFER_STORAGE_FLAGS = 33312;
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;

    protected ARBBufferStorage() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glBufferStorage, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glNamedBufferStorageEXT : -1L);
    }

    public static void nglBufferStorage(int n, long l, long l2, int n2) {
        GL44C.nglBufferStorage(n, l, l2, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, l, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, byteBuffer, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, shortBuffer, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, intBuffer, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, floatBuffer, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, doubleBuffer, n2);
    }

    public static native void nglNamedBufferStorageEXT(int var0, long var1, long var3, int var5);

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLbitfield") int n2) {
        ARBBufferStorage.nglNamedBufferStorageEXT(n, l, 0L, n2);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLbitfield") int n2) {
        ARBBufferStorage.nglNamedBufferStorageEXT(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLbitfield") int n2) {
        ARBBufferStorage.nglNamedBufferStorageEXT(n, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLbitfield") int n2) {
        ARBBufferStorage.nglNamedBufferStorageEXT(n, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer), n2);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLbitfield") int n2) {
        ARBBufferStorage.nglNamedBufferStorageEXT(n, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer), n2);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLbitfield") int n2) {
        ARBBufferStorage.nglNamedBufferStorageEXT(n, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer), n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, sArray, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, nArray, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, fArray, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLbitfield") int n2) {
        GL44C.glBufferStorage(n, dArray, n2);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glNamedBufferStorageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(sArray.length) << 1, sArray, n2, l);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glNamedBufferStorageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(nArray.length) << 2, nArray, n2, l);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glNamedBufferStorageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(fArray.length) << 2, fArray, n2, l);
    }

    public static void glNamedBufferStorageEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glNamedBufferStorageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(dArray.length) << 3, dArray, n2, l);
    }

    static {
        GL.initialize();
    }
}

