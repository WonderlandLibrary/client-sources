/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVConditionalRender {
    public static final int GL_QUERY_WAIT_NV = 36371;
    public static final int GL_QUERY_NO_WAIT_NV = 36372;
    public static final int GL_QUERY_BY_REGION_WAIT_NV = 36373;
    public static final int GL_QUERY_BY_REGION_NO_WAIT_NV = 36374;

    protected NVConditionalRender() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBeginConditionalRenderNV, gLCapabilities.glEndConditionalRenderNV);
    }

    public static native void glBeginConditionalRenderNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glEndConditionalRenderNV();

    static {
        GL.initialize();
    }
}

