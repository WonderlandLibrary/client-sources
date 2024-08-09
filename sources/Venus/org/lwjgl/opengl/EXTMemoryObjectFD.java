/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTMemoryObjectFD {
    public static final int GL_HANDLE_TYPE_OPAQUE_FD_EXT = 38278;

    protected EXTMemoryObjectFD() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glImportMemoryFdEXT);
    }

    public static native void glImportMemoryFdEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64") long var1, @NativeType(value="GLenum") int var3, @NativeType(value="GLint") int var4);

    static {
        GL.initialize();
    }
}

