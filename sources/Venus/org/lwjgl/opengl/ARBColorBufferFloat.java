/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBColorBufferFloat {
    public static final int GL_RGBA_FLOAT_MODE_ARB = 34848;
    public static final int GL_CLAMP_VERTEX_COLOR_ARB = 35098;
    public static final int GL_CLAMP_FRAGMENT_COLOR_ARB = 35099;
    public static final int GL_CLAMP_READ_COLOR_ARB = 35100;
    public static final int GL_FIXED_ONLY_ARB = 35101;

    protected ARBColorBufferFloat() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glClampColorARB);
    }

    public static native void glClampColorARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    static {
        GL.initialize();
    }
}

