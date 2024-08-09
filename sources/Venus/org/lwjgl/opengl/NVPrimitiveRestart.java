/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVPrimitiveRestart {
    public static final int GL_PRIMITIVE_RESTART_NV = 34136;
    public static final int GL_PRIMITIVE_RESTART_INDEX_NV = 34137;

    protected NVPrimitiveRestart() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glPrimitiveRestartNV, gLCapabilities.glPrimitiveRestartIndexNV);
    }

    public static native void glPrimitiveRestartNV();

    public static native void glPrimitiveRestartIndexNV(@NativeType(value="GLuint") int var0);

    static {
        GL.initialize();
    }
}

