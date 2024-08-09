/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class AMDSamplePositions {
    public static final int GL_SUBSAMPLE_DISTANCE_AMD = 34879;

    protected AMDSamplePositions() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glSetMultisamplefvAMD);
    }

    public static native void nglSetMultisamplefvAMD(int var0, int var1, long var2);

    public static void glSetMultisamplefvAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        AMDSamplePositions.nglSetMultisamplefvAMD(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glSetMultisamplefvAMD(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glSetMultisamplefvAMD;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    static {
        GL.initialize();
    }
}

