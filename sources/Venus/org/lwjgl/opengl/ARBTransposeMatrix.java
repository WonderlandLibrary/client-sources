/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBTransposeMatrix {
    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX_ARB = 34019;
    public static final int GL_TRANSPOSE_PROJECTION_MATRIX_ARB = 34020;
    public static final int GL_TRANSPOSE_TEXTURE_MATRIX_ARB = 34021;
    public static final int GL_TRANSPOSE_COLOR_MATRIX_ARB = 34022;

    protected ARBTransposeMatrix() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glLoadTransposeMatrixfARB, gLCapabilities.glLoadTransposeMatrixdARB, gLCapabilities.glMultTransposeMatrixfARB, gLCapabilities.glMultTransposeMatrixdARB);
    }

    public static native void nglLoadTransposeMatrixfARB(long var0);

    public static void glLoadTransposeMatrixfARB(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        ARBTransposeMatrix.nglLoadTransposeMatrixfARB(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglLoadTransposeMatrixdARB(long var0);

    public static void glLoadTransposeMatrixdARB(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        ARBTransposeMatrix.nglLoadTransposeMatrixdARB(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglMultTransposeMatrixfARB(long var0);

    public static void glMultTransposeMatrixfARB(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        ARBTransposeMatrix.nglMultTransposeMatrixfARB(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultTransposeMatrixdARB(long var0);

    public static void glMultTransposeMatrixdARB(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        ARBTransposeMatrix.nglMultTransposeMatrixdARB(MemoryUtil.memAddress(doubleBuffer));
    }

    public static void glLoadTransposeMatrixfARB(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glLoadTransposeMatrixfARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(fArray, l);
    }

    public static void glLoadTransposeMatrixdARB(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glLoadTransposeMatrixdARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(dArray, l);
    }

    public static void glMultTransposeMatrixfARB(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultTransposeMatrixfARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(fArray, l);
    }

    public static void glMultTransposeMatrixdARB(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultTransposeMatrixdARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(dArray, l);
    }

    static {
        GL.initialize();
    }
}

