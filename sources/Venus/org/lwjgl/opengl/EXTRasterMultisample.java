/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTRasterMultisample {
    public static final int GL_RASTER_MULTISAMPLE_EXT = 37671;
    public static final int GL_RASTER_SAMPLES_EXT = 37672;
    public static final int GL_MAX_RASTER_SAMPLES_EXT = 37673;
    public static final int GL_RASTER_FIXED_SAMPLE_LOCATIONS_EXT = 37674;
    public static final int GL_MULTISAMPLE_RASTERIZATION_ALLOWED_EXT = 37675;
    public static final int GL_EFFECTIVE_RASTER_SAMPLES_EXT = 37676;

    protected EXTRasterMultisample() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glRasterSamplesEXT);
    }

    public static native void glRasterSamplesEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLboolean") boolean var1);

    static {
        GL.initialize();
    }
}

