/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class EXTBindableUniform {
    public static final int GL_MAX_VERTEX_BINDABLE_UNIFORMS_EXT = 36322;
    public static final int GL_MAX_FRAGMENT_BINDABLE_UNIFORMS_EXT = 36323;
    public static final int GL_MAX_GEOMETRY_BINDABLE_UNIFORMS_EXT = 36324;
    public static final int GL_MAX_BINDABLE_UNIFORM_SIZE_EXT = 36333;
    public static final int GL_UNIFORM_BUFFER_BINDING_EXT = 36335;
    public static final int GL_UNIFORM_BUFFER_EXT = 36334;

    protected EXTBindableUniform() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glUniformBufferEXT, gLCapabilities.glGetUniformBufferSizeEXT, gLCapabilities.glGetUniformOffsetEXT);
    }

    public static native void glUniformBufferEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2);

    @NativeType(value="GLint")
    public static native int glGetUniformBufferSizeEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1);

    @NativeType(value="GLintptr")
    public static native long glGetUniformOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1);

    static {
        GL.initialize();
    }
}

