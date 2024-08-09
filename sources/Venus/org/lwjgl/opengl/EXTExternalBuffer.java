/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTExternalBuffer {
    protected EXTExternalBuffer() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glBufferStorageExternalEXT, gLCapabilities.hasDSA(set) ? gLCapabilities.glNamedBufferStorageExternalEXT : -1L);
    }

    public static native void nglBufferStorageExternalEXT(int var0, long var1, long var3, long var5, int var7);

    public static void glBufferStorageExternalEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLeglClientBufferEXT") long l3, @NativeType(value="GLbitfield") int n2) {
        if (Checks.CHECKS) {
            Checks.check(l3);
        }
        EXTExternalBuffer.nglBufferStorageExternalEXT(n, l, l2, l3, n2);
    }

    public static native void nglNamedBufferStorageExternalEXT(int var0, long var1, long var3, long var5, int var7);

    public static void glNamedBufferStorageExternalEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLeglClientBufferEXT") long l3, @NativeType(value="GLbitfield") int n2) {
        if (Checks.CHECKS) {
            Checks.check(l3);
        }
        EXTExternalBuffer.nglNamedBufferStorageExternalEXT(n, l, l2, l3, n2);
    }

    static {
        GL.initialize();
    }
}

