/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTStencilClearTag {
    public static final int GL_STENCIL_TAG_BITS_EXT = 35058;
    public static final int GL_STENCIL_CLEAR_TAG_VALUE_EXT = 35059;

    protected EXTStencilClearTag() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glStencilClearTagEXT);
    }

    public static native void glStencilClearTagEXT(@NativeType(value="GLsizei") int var0, @NativeType(value="GLuint") int var1);

    static {
        GL.initialize();
    }
}

