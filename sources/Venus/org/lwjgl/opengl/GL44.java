/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL44
extends GL43 {
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

    protected GL44() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBufferStorage, gLCapabilities.glClearTexSubImage, gLCapabilities.glClearTexImage, gLCapabilities.glBindBuffersBase, gLCapabilities.glBindBuffersRange, gLCapabilities.glBindTextures, gLCapabilities.glBindSamplers, gLCapabilities.glBindImageTextures, gLCapabilities.glBindVertexBuffers);
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

    public static void nglClearTexSubImage(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, long l) {
        GL44C.nglClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, shortBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, intBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, floatBuffer);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, doubleBuffer);
    }

    public static void nglClearTexImage(int n, int n2, int n3, int n4, long l) {
        GL44C.nglClearTexImage(n, n2, n3, n4, l);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, byteBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, shortBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, intBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, floatBuffer);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL44C.glClearTexImage(n, n2, n3, n4, doubleBuffer);
    }

    public static void nglBindBuffersBase(int n, int n2, int n3, long l) {
        GL44C.nglBindBuffersBase(n, n2, n3, l);
    }

    public static void glBindBuffersBase(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.glBindBuffersBase(n, n2, intBuffer);
    }

    public static void nglBindBuffersRange(int n, int n2, int n3, long l, long l2, long l3) {
        GL44C.nglBindBuffersRange(n, n2, n3, l, l2, l3);
    }

    public static void glBindBuffersRange(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizeiptr const *") PointerBuffer pointerBuffer2) {
        GL44C.glBindBuffersRange(n, n2, intBuffer, pointerBuffer, pointerBuffer2);
    }

    public static void nglBindTextures(int n, int n2, long l) {
        GL44C.nglBindTextures(n, n2, l);
    }

    public static void glBindTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.glBindTextures(n, intBuffer);
    }

    public static void nglBindSamplers(int n, int n2, long l) {
        GL44C.nglBindSamplers(n, n2, l);
    }

    public static void glBindSamplers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.glBindSamplers(n, intBuffer);
    }

    public static void nglBindImageTextures(int n, int n2, long l) {
        GL44C.nglBindImageTextures(n, n2, l);
    }

    public static void glBindImageTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL44C.glBindImageTextures(n, intBuffer);
    }

    public static void nglBindVertexBuffers(int n, int n2, long l, long l2, long l3) {
        GL44C.nglBindVertexBuffers(n, n2, l, l2, l3);
    }

    public static void glBindVertexBuffers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizei const *") IntBuffer intBuffer2) {
        GL44C.glBindVertexBuffers(n, intBuffer, pointerBuffer, intBuffer2);
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

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray);
    }

    public static void glClearTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") double[] dArray) {
        GL44C.glClearTexSubImage(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, sArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, nArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, fArray);
    }

    public static void glClearTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") double[] dArray) {
        GL44C.glClearTexImage(n, n2, n3, n4, dArray);
    }

    public static void glBindBuffersBase(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        GL44C.glBindBuffersBase(n, n2, nArray);
    }

    public static void glBindBuffersRange(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLuint const *") int[] nArray, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizeiptr const *") PointerBuffer pointerBuffer2) {
        GL44C.glBindBuffersRange(n, n2, nArray, pointerBuffer, pointerBuffer2);
    }

    public static void glBindTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        GL44C.glBindTextures(n, nArray);
    }

    public static void glBindSamplers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        GL44C.glBindSamplers(n, nArray);
    }

    public static void glBindImageTextures(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray) {
        GL44C.glBindImageTextures(n, nArray);
    }

    public static void glBindVertexBuffers(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLuint const *") int[] nArray, @Nullable @NativeType(value="GLintptr const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLsizei const *") int[] nArray2) {
        GL44C.glBindVertexBuffers(n, nArray, pointerBuffer, nArray2);
    }

    static {
        GL.initialize();
    }
}

