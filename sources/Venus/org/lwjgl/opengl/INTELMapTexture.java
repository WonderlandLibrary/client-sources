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
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class INTELMapTexture {
    public static final int GL_TEXTURE_MEMORY_LAYOUT_INTEL = 33791;
    public static final int GL_LAYOUT_DEFAULT_INTEL = 0;
    public static final int GL_LAYOUT_LINEAR_INTEL = 1;
    public static final int GL_LAYOUT_LINEAR_CPU_CACHED_INTEL = 2;

    protected INTELMapTexture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glSyncTextureINTEL, gLCapabilities.glUnmapTexture2DINTEL, gLCapabilities.glMapTexture2DINTEL);
    }

    public static native void glSyncTextureINTEL(@NativeType(value="GLuint") int var0);

    public static native void glUnmapTexture2DINTEL(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1);

    public static native long nglMapTexture2DINTEL(int var0, int var1, int var2, long var3, long var5);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapTexture2DINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLbitfield") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        long l = INTELMapTexture.nglMapTexture2DINTEL(n, n2, n3, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
        return MemoryUtil.memByteBufferSafe(l, INTELMapTexture.getStride(intBuffer) * GLChecks.getTexLevelParameteri(n, 3553, n2, 4097));
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapTexture2DINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLbitfield") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2, @Nullable ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        long l = INTELMapTexture.nglMapTexture2DINTEL(n, n2, n3, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
        int n4 = INTELMapTexture.getStride(intBuffer) * GLChecks.getTexLevelParameteri(n, 3553, n2, 4097);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l, n4);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapTexture2DINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLbitfield") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2, long l, @Nullable ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        long l2 = INTELMapTexture.nglMapTexture2DINTEL(n, n2, n3, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
        return APIUtil.apiGetMappedBuffer(byteBuffer, l2, (int)l);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapTexture2DINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLbitfield") int n3, @NativeType(value="GLint *") int[] nArray, @NativeType(value="GLenum *") int[] nArray2) {
        long l = GL.getICD().glMapTexture2DINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        long l2 = JNI.callPPP(n, n2, n3, nArray, nArray2, l);
        return MemoryUtil.memByteBufferSafe(l2, INTELMapTexture.getStride(nArray) * GLChecks.getTexLevelParameteri(n, 3553, n2, 4097));
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapTexture2DINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLbitfield") int n3, @NativeType(value="GLint *") int[] nArray, @NativeType(value="GLenum *") int[] nArray2, @Nullable ByteBuffer byteBuffer) {
        long l = GL.getICD().glMapTexture2DINTEL;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        long l2 = JNI.callPPP(n, n2, n3, nArray, nArray2, l);
        int n4 = INTELMapTexture.getStride(nArray) * GLChecks.getTexLevelParameteri(n, 3553, n2, 4097);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l2, n4);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapTexture2DINTEL(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLbitfield") int n3, @NativeType(value="GLint *") int[] nArray, @NativeType(value="GLenum *") int[] nArray2, long l, @Nullable ByteBuffer byteBuffer) {
        long l2 = GL.getICD().glMapTexture2DINTEL;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
        }
        long l3 = JNI.callPPP(n, n2, n3, nArray, nArray2, l2);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l3, (int)l);
    }

    private static int getStride(IntBuffer intBuffer) {
        return intBuffer.get(intBuffer.position());
    }

    private static int getStride(int[] nArray) {
        return nArray[0];
    }

    static {
        GL.initialize();
    }
}

