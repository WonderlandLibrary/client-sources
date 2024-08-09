/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTCompiledVertexArray {
    public static final int GL_ARRAY_ELEMENT_LOCK_FIRST_EXT = 33192;
    public static final int GL_ARRAY_ELEMENT_LOCK_COUNT_EXT = 33193;

    protected EXTCompiledVertexArray() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glLockArraysEXT, gLCapabilities.glUnlockArraysEXT);
    }

    public static native void glLockArraysEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLsizei") int var1);

    public static native void glUnlockArraysEXT();

    static {
        GL.initialize();
    }
}

