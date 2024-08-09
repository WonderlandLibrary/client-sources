/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBES32Compatibility {
    public static final int GL_PRIMITIVE_BOUNDING_BOX_ARB = 37566;
    public static final int GL_MULTISAMPLE_LINE_WIDTH_RANGE_ARB = 37761;
    public static final int GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY_ARB = 37762;

    protected ARBES32Compatibility() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glPrimitiveBoundingBoxARB);
    }

    public static native void glPrimitiveBoundingBoxARB(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5, @NativeType(value="GLfloat") float var6, @NativeType(value="GLfloat") float var7);

    static {
        GL.initialize();
    }
}

