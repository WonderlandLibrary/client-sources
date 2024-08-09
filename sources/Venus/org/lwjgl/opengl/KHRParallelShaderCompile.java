/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class KHRParallelShaderCompile {
    public static final int GL_MAX_SHADER_COMPILER_THREADS_KHR = 37296;
    public static final int GL_COMPLETION_STATUS_KHR = 37297;

    protected KHRParallelShaderCompile() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMaxShaderCompilerThreadsKHR);
    }

    public static native void glMaxShaderCompilerThreadsKHR(@NativeType(value="GLuint") int var0);

    static {
        GL.initialize();
    }
}

