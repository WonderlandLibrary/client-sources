/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVXConditionalRender {
    protected NVXConditionalRender() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBeginConditionalRenderNVX, gLCapabilities.glEndConditionalRenderNVX);
    }

    public static native void glBeginConditionalRenderNVX(@NativeType(value="GLuint") int var0);

    public static native void glEndConditionalRenderNVX();

    static {
        GL.initialize();
    }
}

