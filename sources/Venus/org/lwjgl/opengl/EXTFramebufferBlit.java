/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTFramebufferBlit {
    public static final int GL_READ_FRAMEBUFFER_EXT = 36008;
    public static final int GL_DRAW_FRAMEBUFFER_EXT = 36009;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING_EXT = 36006;
    public static final int GL_READ_FRAMEBUFFER_BINDING_EXT = 36010;

    protected EXTFramebufferBlit() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBlitFramebufferEXT);
    }

    public static native void glBlitFramebufferEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6, @NativeType(value="GLint") int var7, @NativeType(value="GLbitfield") int var8, @NativeType(value="GLenum") int var9);

    static {
        GL.initialize();
    }
}

