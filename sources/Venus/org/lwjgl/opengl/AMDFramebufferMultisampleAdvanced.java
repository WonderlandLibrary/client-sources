/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class AMDFramebufferMultisampleAdvanced {
    public static final int GL_RENDERBUFFER_STORAGE_SAMPLES_AMD = 37298;
    public static final int GL_MAX_COLOR_FRAMEBUFFER_SAMPLES_AMD = 37299;
    public static final int GL_MAX_COLOR_FRAMEBUFFER_STORAGE_SAMPLES_AMD = 37300;
    public static final int GL_MAX_DEPTH_STENCIL_FRAMEBUFFER_SAMPLES_AMD = 37301;
    public static final int GL_NUM_SUPPORTED_MULTISAMPLE_MODES_AMD = 37302;
    public static final int GL_SUPPORTED_MULTISAMPLE_MODES_AMD = 37303;

    protected AMDFramebufferMultisampleAdvanced() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glRenderbufferStorageMultisampleAdvancedAMD, gLCapabilities.glNamedRenderbufferStorageMultisampleAdvancedAMD);
    }

    public static native void glRenderbufferStorageMultisampleAdvancedAMD(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5);

    public static native void glNamedRenderbufferStorageMultisampleAdvancedAMD(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5);

    static {
        GL.initialize();
    }
}

