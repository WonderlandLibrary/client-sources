/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVClipSpaceWScaling {
    public static final int GL_VIEWPORT_POSITION_W_SCALE_NV = 37756;
    public static final int GL_VIEWPORT_POSITION_W_SCALE_X_COEFF = 37757;
    public static final int GL_VIEWPORT_POSITION_W_SCALE_Y_COEFF = 37758;

    protected NVClipSpaceWScaling() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glViewportPositionWScaleNV);
    }

    public static native void glViewportPositionWScaleNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    static {
        GL.initialize();
    }
}

