/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBParallelShaderCompile {
    public static final int GL_MAX_SHADER_COMPILER_THREADS_ARB = 37296;
    public static final int GL_COMPLETION_STATUS_ARB = 37297;

    protected ARBParallelShaderCompile() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMaxShaderCompilerThreadsARB);
    }

    public static native void glMaxShaderCompilerThreadsARB(@NativeType(value="GLuint") int var0);

    static {
        GL.initialize();
    }
}

