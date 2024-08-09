/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVFragmentCoverageToColor {
    public static final int GL_FRAGMENT_COVERAGE_TO_COLOR_NV = 37597;
    public static final int GL_FRAGMENT_COVERAGE_COLOR_NV = 37598;

    protected NVFragmentCoverageToColor() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glFragmentCoverageColorNV);
    }

    public static native void glFragmentCoverageColorNV(@NativeType(value="GLuint") int var0);

    static {
        GL.initialize();
    }
}

