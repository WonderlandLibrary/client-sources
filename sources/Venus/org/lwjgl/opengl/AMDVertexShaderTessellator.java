/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDVertexShaderTessellator {
    public static final int GL_SAMPLER_BUFFER_AMD = 36865;
    public static final int GL_INT_SAMPLER_BUFFER_AMD = 36866;
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_AMD = 36867;
    public static final int GL_DISCRETE_AMD = 36870;
    public static final int GL_CONTINUOUS_AMD = 36871;
    public static final int GL_TESSELLATION_MODE_AMD = 36868;
    public static final int GL_TESSELLATION_FACTOR_AMD = 36869;

    protected AMDVertexShaderTessellator() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glTessellationFactorAMD, gLCapabilities.glTessellationModeAMD);
    }

    public static native void glTessellationFactorAMD(@NativeType(value="GLfloat") float var0);

    public static native void glTessellationModeAMD(@NativeType(value="GLenum") int var0);

    static {
        GL.initialize();
    }
}

