/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBMapBufferRange {
    public static final int GL_MAP_READ_BIT = 1;
    public static final int GL_MAP_WRITE_BIT = 2;
    public static final int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    public static final int GL_MAP_UNSYNCHRONIZED_BIT = 32;

    protected ARBMapBufferRange() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMapBufferRange, gLCapabilities.glFlushMappedBufferRange);
    }

    public static long nglMapBufferRange(int n, long l, long l2, int n2) {
        return GL30C.nglMapBufferRange(n, l, l2, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2) {
        return GL30C.glMapBufferRange(n, l, l2, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2, @Nullable ByteBuffer byteBuffer) {
        return GL30C.glMapBufferRange(n, l, l2, n2);
    }

    public static void glFlushMappedBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL30C.glFlushMappedBufferRange(n, l, l2);
    }

    static {
        GL.initialize();
    }
}

