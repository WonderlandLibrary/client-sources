/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTBlendEquationSeparate {
    public static final int GL_BLEND_EQUATION_RGB_EXT = 32777;
    public static final int GL_BLEND_EQUATION_ALPHA_EXT = 34877;

    protected EXTBlendEquationSeparate() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBlendEquationSeparateEXT);
    }

    public static native void glBlendEquationSeparateEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    static {
        GL.initialize();
    }
}

