/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class NVViewportSwizzle {
    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_X_NV = 37712;
    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_X_NV = 37713;
    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_Y_NV = 37714;
    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_Y_NV = 37715;
    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_Z_NV = 37716;
    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_Z_NV = 37717;
    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_W_NV = 37718;
    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_W_NV = 37719;
    public static final int GL_VIEWPORT_SWIZZLE_X_NV = 37720;
    public static final int GL_VIEWPORT_SWIZZLE_Y_NV = 37721;
    public static final int GL_VIEWPORT_SWIZZLE_Z_NV = 37722;
    public static final int GL_VIEWPORT_SWIZZLE_W_NV = 37723;

    protected NVViewportSwizzle() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glViewportSwizzleNV);
    }

    public static native void glViewportSwizzleNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLenum") int var4);

    static {
        GL.initialize();
    }
}

