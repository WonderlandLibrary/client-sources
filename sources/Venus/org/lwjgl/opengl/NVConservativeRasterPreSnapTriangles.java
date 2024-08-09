/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVConservativeRasterPreSnapTriangles {
    public static final int GL_CONSERVATIVE_RASTER_MODE_NV = 38221;
    public static final int GL_CONSERVATIVE_RASTER_MODE_POST_SNAP_NV = 38222;
    public static final int GL_CONSERVATIVE_RASTER_MODE_PRE_SNAP_TRIANGLES_NV = 38223;

    protected NVConservativeRasterPreSnapTriangles() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glConservativeRasterParameteriNV);
    }

    public static native void glConservativeRasterParameteriNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    static {
        GL.initialize();
    }
}

