/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBWindowPos {
    protected ARBWindowPos() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glWindowPos2iARB, gLCapabilities.glWindowPos2sARB, gLCapabilities.glWindowPos2fARB, gLCapabilities.glWindowPos2dARB, gLCapabilities.glWindowPos2ivARB, gLCapabilities.glWindowPos2svARB, gLCapabilities.glWindowPos2fvARB, gLCapabilities.glWindowPos2dvARB, gLCapabilities.glWindowPos3iARB, gLCapabilities.glWindowPos3sARB, gLCapabilities.glWindowPos3fARB, gLCapabilities.glWindowPos3dARB, gLCapabilities.glWindowPos3ivARB, gLCapabilities.glWindowPos3svARB, gLCapabilities.glWindowPos3fvARB, gLCapabilities.glWindowPos3dvARB);
    }

    public static native void glWindowPos2iARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glWindowPos2sARB(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1);

    public static native void glWindowPos2fARB(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glWindowPos2dARB(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void nglWindowPos2ivARB(long var0);

    public static void glWindowPos2ivARB(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        ARBWindowPos.nglWindowPos2ivARB(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglWindowPos2svARB(long var0);

    public static void glWindowPos2svARB(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        ARBWindowPos.nglWindowPos2svARB(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglWindowPos2fvARB(long var0);

    public static void glWindowPos2fvARB(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        ARBWindowPos.nglWindowPos2fvARB(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglWindowPos2dvARB(long var0);

    public static void glWindowPos2dvARB(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        ARBWindowPos.nglWindowPos2dvARB(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glWindowPos3iARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glWindowPos3sARB(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glWindowPos3fARB(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glWindowPos3dARB(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void nglWindowPos3ivARB(long var0);

    public static void glWindowPos3ivARB(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        ARBWindowPos.nglWindowPos3ivARB(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglWindowPos3svARB(long var0);

    public static void glWindowPos3svARB(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        ARBWindowPos.nglWindowPos3svARB(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglWindowPos3fvARB(long var0);

    public static void glWindowPos3fvARB(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        ARBWindowPos.nglWindowPos3fvARB(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglWindowPos3dvARB(long var0);

    public static void glWindowPos3dvARB(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        ARBWindowPos.nglWindowPos3dvARB(MemoryUtil.memAddress(doubleBuffer));
    }

    public static void glWindowPos2ivARB(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glWindowPos2ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(nArray, l);
    }

    public static void glWindowPos2svARB(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glWindowPos2svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glWindowPos2fvARB(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glWindowPos2fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(fArray, l);
    }

    public static void glWindowPos2dvARB(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glWindowPos2dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(dArray, l);
    }

    public static void glWindowPos3ivARB(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glWindowPos3ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glWindowPos3svARB(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glWindowPos3svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glWindowPos3fvARB(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glWindowPos3fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glWindowPos3dvARB(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glWindowPos3dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    static {
        GL.initialize();
    }
}

