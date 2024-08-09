/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVConservativeRaster {
    public static final int GL_CONSERVATIVE_RASTERIZATION_NV = 37702;
    public static final int GL_SUBPIXEL_PRECISION_BIAS_X_BITS_NV = 37703;
    public static final int GL_SUBPIXEL_PRECISION_BIAS_Y_BITS_NV = 37704;
    public static final int GL_MAX_SUBPIXEL_PRECISION_BIAS_BITS_NV = 37705;

    protected NVConservativeRaster() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glSubpixelPrecisionBiasNV);
    }

    public static native void glSubpixelPrecisionBiasNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    static {
        GL.initialize();
    }
}

