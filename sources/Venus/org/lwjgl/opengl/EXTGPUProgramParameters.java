/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTGPUProgramParameters {
    protected EXTGPUProgramParameters() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glProgramEnvParameters4fvEXT, gLCapabilities.glProgramLocalParameters4fvEXT);
    }

    public static native void nglProgramEnvParameters4fvEXT(int var0, int var1, int var2, long var3);

    public static void glProgramEnvParameters4fvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTGPUProgramParameters.nglProgramEnvParameters4fvEXT(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramLocalParameters4fvEXT(int var0, int var1, int var2, long var3);

    public static void glProgramLocalParameters4fvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTGPUProgramParameters.nglProgramLocalParameters4fvEXT(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glProgramEnvParameters4fvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramEnvParameters4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 2, fArray, l);
    }

    public static void glProgramLocalParameters4fvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramLocalParameters4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 2, fArray, l);
    }

    static {
        GL.initialize();
    }
}

