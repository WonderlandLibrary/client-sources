/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBTextureBufferRange {
    public static final int GL_TEXTURE_BUFFER_OFFSET = 37277;
    public static final int GL_TEXTURE_BUFFER_SIZE = 37278;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;

    protected ARBTextureBufferRange() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glTexBufferRange, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glTextureBufferRangeEXT : -1L);
    }

    public static void glTexBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL43C.glTexBufferRange(n, n2, n3, l, l2);
    }

    public static native void glTextureBufferRangeEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLintptr") long var4, @NativeType(value="GLsizeiptr") long var6);

    static {
        GL.initialize();
    }
}

