/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBSparseBuffer {
    public static final int GL_SPARSE_STORAGE_BIT_ARB = 1024;
    public static final int GL_SPARSE_BUFFER_PAGE_SIZE_ARB = 33528;

    protected ARBSparseBuffer() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glBufferPageCommitmentARB);
    }

    public static native void glBufferPageCommitmentARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLintptr") long var1, @NativeType(value="GLsizeiptr") long var3, @NativeType(value="GLboolean") boolean var5);

    public static native void glNamedBufferPageCommitmentEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLintptr") long var1, @NativeType(value="GLsizeiptr") long var3, @NativeType(value="GLboolean") boolean var5);

    public static native void glNamedBufferPageCommitmentARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLintptr") long var1, @NativeType(value="GLsizeiptr") long var3, @NativeType(value="GLboolean") boolean var5);

    static {
        GL.initialize();
    }
}

