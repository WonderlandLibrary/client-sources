/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL44C
extends GL43C {
    public static final int GL_MAX_VERTEX_ATTRIB_STRIDE = 33509;
    public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED = 33313;
    public static final int GL_TEXTURE_BUFFER_BINDING = 35882;
    public static final int GL_MAP_PERSISTENT_BIT = 64;
    public static final int GL_MAP_COHERENT_BIT = 128;
    public static final int GL_DYNAMIC_STORAGE_BIT = 256;
    public static final int GL_CLIENT_STORAGE_BIT = 512;
    public static final int GL_BUFFER_IMMUTABLE_STORAGE = 33311;
    public static final int GL_BUFFER_STORAGE_FLAGS = 33312;
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 16384;
    public static final int GL_CLEAR_TEXTURE = 37733;
    public static final int GL_LOCATION_COMPONENT = 37706;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = 37707;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = 37708;
    public static final int GL_QUERY_RESULT_NO_WAIT = 37268;
    public static final int GL_QUERY_BUFFER = 37266;
    public static final int GL_QUERY_BUFFER_BINDING = 37267;
    public static final int GL_QUERY_BUFFER_BARRIER_BIT = 32768;
    public static final int GL_MIRROR_CLAMP_TO_EDGE = 34627;

    protected GL44C() {
        throw new UnsupportedOperationException();
    }

    public static native void nglBufferStorage(int var0, long var1, long var3, int var5);

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLbitfield") int n2) {
        GL44C.nglBufferStorage(n, l, 0L, n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.nglBufferStorage(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.nglBufferStorage(n, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.nglBufferStorage(n, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer), n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.nglBufferStorage(n, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer), n2);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLbitfield") int n2) {
        GL44C.nglBufferStorage(n, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer), n2);
    }

    public static native void nglClearTexSubImage(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglClearTexImage(int var0, int var1, int var2, int var3, long var4);

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL44C.nglClearTexImage(n, n2, n3, n4, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL44C.nglClearTexImage(n, n2, n3, n4, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL44C.nglClearTexImage(n, n2, n3, n4, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL44C.nglClearTexImage(n, n2, n3, n4, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL44C.nglClearTexImage(n, n2, n3, n4, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglBindBuffersBase(int var0, int var1, int var2, long var3);

    public static void glBindBuffersBase(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.nglBindBuffersBase(n, n2, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    public static native void nglBindBuffersRange(int var0, int var1, int var2, long var3, long var5, long var7);

    public static void glBindBuffersRange(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizeiptr const *") PointerBuffer pointerBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe(pointerBuffer, Checks.remainingSafe(intBuffer));
            Checks.checkSafe(pointerBuffer2, Checks.remainingSafe(intBuffer));
        }
        GL44C.nglBindBuffersRange(n, n2, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(pointerBuffer2));
    }

    public static native void nglBindTextures(int var0, int var1, long var2);

    public static void glBindTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.nglBindTextures(n, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    public static native void nglBindSamplers(int var0, int var1, long var2);

    public static void glBindSamplers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.nglBindSamplers(n, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    public static native void nglBindImageTextures(int var0, int var1, long var2);

    public static void glBindImageTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.nglBindImageTextures(n, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    public static native void nglBindVertexBuffers(int var0, int var1, long var2, long var4, long var6);

    public static void glBindVertexBuffers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizei const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe(pointerBuffer, Checks.remainingSafe(intBuffer));
            Checks.checkSafe((Buffer)intBuffer2, Checks.remainingSafe(intBuffer));
        }
        GL44C.nglBindVertexBuffers(n, Checks.remainingSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(intBuffer2));
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glBufferStorage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(sArray.length) << 1, sArray, n2, l);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glBufferStorage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(nArray.length) << 2, nArray, n2, l);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glBufferStorage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(fArray.length) << 2, fArray, n2, l);
    }

    public static void glBufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLbitfield") int n2) {
        long l = GL.getICD().glBufferStorage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(dArray.length) << 3, dArray, n2, l);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glClearTexSubImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray, l);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glClearTexSubImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray, l);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glClearTexSubImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray, l);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glClearTexSubImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray, l);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glClearTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, sArray, l);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glClearTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glClearTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, fArray, l);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glClearTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, dArray, l);
    }

    public static void glBindBuffersBase(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glBindBuffersBase;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, Checks.lengthSafe(nArray), nArray, l);
    }

    public static void glBindBuffersRange(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") int[] nArray, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizeiptr const *") PointerBuffer pointerBuffer2) {
        long l = GL.getICD().glBindBuffersRange;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(pointerBuffer, Checks.lengthSafe(nArray));
            Checks.checkSafe(pointerBuffer2, Checks.lengthSafe(nArray));
        }
        JNI.callPPPV(n, n2, Checks.lengthSafe(nArray), nArray, MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(pointerBuffer2), l);
    }

    public static void glBindTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glBindTextures;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, Checks.lengthSafe(nArray), nArray, l);
    }

    public static void glBindSamplers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glBindSamplers;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, Checks.lengthSafe(nArray), nArray, l);
    }

    public static void glBindImageTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glBindImageTextures;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, Checks.lengthSafe(nArray), nArray, l);
    }

    public static void glBindVertexBuffers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizei const *") int[] nArray2) {
        long l = GL.getICD().glBindVertexBuffers;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(pointerBuffer, Checks.lengthSafe(nArray));
            Checks.checkSafe(nArray2, Checks.lengthSafe(nArray));
        }
        JNI.callPPPV(n, Checks.lengthSafe(nArray), nArray, MemoryUtil.memAddressSafe(pointerBuffer), nArray2, l);
    }

    static {
        GL.initialize();
    }
}

