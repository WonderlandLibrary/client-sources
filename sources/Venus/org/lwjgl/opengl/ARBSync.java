/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBSync {
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static final int GL_OBJECT_TYPE = 37138;
    public static final int GL_SYNC_CONDITION = 37139;
    public static final int GL_SYNC_STATUS = 37140;
    public static final int GL_SYNC_FLAGS = 37141;
    public static final int GL_SYNC_FENCE = 37142;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_UNSIGNALED = 37144;
    public static final int GL_SIGNALED = 37145;
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final long GL_TIMEOUT_IGNORED = -1L;
    public static final int GL_ALREADY_SIGNALED = 37146;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_CONDITION_SATISFIED = 37148;
    public static final int GL_WAIT_FAILED = 37149;

    protected ARBSync() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glFenceSync, gLCapabilities.glIsSync, gLCapabilities.glDeleteSync, gLCapabilities.glClientWaitSync, gLCapabilities.glWaitSync, gLCapabilities.glGetInteger64v, gLCapabilities.glGetSynciv);
    }

    @NativeType(value="GLsync")
    public static long glFenceSync(@NativeType(value="GLenum") int n, @NativeType(value="GLbitfield") int n2) {
        return GL32C.glFenceSync(n, n2);
    }

    public static boolean nglIsSync(long l) {
        return GL32C.nglIsSync(l);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsSync(@NativeType(value="GLsync") long l) {
        return GL32C.glIsSync(l);
    }

    public static void nglDeleteSync(long l) {
        GL32C.nglDeleteSync(l);
    }

    public static void glDeleteSync(@NativeType(value="GLsync") long l) {
        GL32C.glDeleteSync(l);
    }

    public static int nglClientWaitSync(long l, int n, long l2) {
        return GL32C.nglClientWaitSync(l, n, l2);
    }

    @NativeType(value="GLenum")
    public static int glClientWaitSync(@NativeType(value="GLsync") long l, @NativeType(value="GLbitfield") int n, @NativeType(value="GLuint64") long l2) {
        return GL32C.glClientWaitSync(l, n, l2);
    }

    public static void nglWaitSync(long l, int n, long l2) {
        GL32C.nglWaitSync(l, n, l2);
    }

    public static void glWaitSync(@NativeType(value="GLsync") long l, @NativeType(value="GLbitfield") int n, @NativeType(value="GLuint64") long l2) {
        GL32C.glWaitSync(l, n, l2);
    }

    public static void nglGetInteger64v(int n, long l) {
        GL32C.nglGetInteger64v(n, l);
    }

    public static void glGetInteger64v(@NativeType(value="GLenum") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL32C.glGetInteger64v(n, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetInteger64(@NativeType(value="GLenum") int n) {
        return GL32C.glGetInteger64(n);
    }

    public static void nglGetSynciv(long l, int n, int n2, long l2, long l3) {
        GL32C.nglGetSynciv(l, n, n2, l2, l3);
    }

    public static void glGetSynciv(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        GL32C.glGetSynciv(l, n, intBuffer, intBuffer2);
    }

    @NativeType(value="void")
    public static int glGetSynci(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer) {
        return GL32C.glGetSynci(l, n, intBuffer);
    }

    public static void glGetInteger64v(@NativeType(value="GLenum") int n, @NativeType(value="GLint64 *") long[] lArray) {
        GL32C.glGetInteger64v(n, lArray);
    }

    public static void glGetSynciv(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2) {
        GL32C.glGetSynciv(l, n, nArray, nArray2);
    }

    static {
        GL.initialize();
    }
}

