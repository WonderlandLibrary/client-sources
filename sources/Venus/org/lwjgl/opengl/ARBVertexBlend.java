/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
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

public class ARBVertexBlend {
    public static final int GL_MAX_VERTEX_UNITS_ARB = 34468;
    public static final int GL_ACTIVE_VERTEX_UNITS_ARB = 34469;
    public static final int GL_WEIGHT_SUM_UNITY_ARB = 34470;
    public static final int GL_VERTEX_BLEND_ARB = 34471;
    public static final int GL_MODELVIEW0_ARB = 5888;
    public static final int GL_MODELVIEW1_ARB = 34058;
    public static final int GL_MODELVIEW2_ARB = 34594;
    public static final int GL_MODELVIEW3_ARB = 34595;
    public static final int GL_MODELVIEW4_ARB = 34596;
    public static final int GL_MODELVIEW5_ARB = 34597;
    public static final int GL_MODELVIEW6_ARB = 34598;
    public static final int GL_MODELVIEW7_ARB = 34599;
    public static final int GL_MODELVIEW8_ARB = 34600;
    public static final int GL_MODELVIEW9_ARB = 34601;
    public static final int GL_MODELVIEW10_ARB = 34602;
    public static final int GL_MODELVIEW11_ARB = 34603;
    public static final int GL_MODELVIEW12_ARB = 34604;
    public static final int GL_MODELVIEW13_ARB = 34605;
    public static final int GL_MODELVIEW14_ARB = 34606;
    public static final int GL_MODELVIEW15_ARB = 34607;
    public static final int GL_MODELVIEW16_ARB = 34608;
    public static final int GL_MODELVIEW17_ARB = 34609;
    public static final int GL_MODELVIEW18_ARB = 34610;
    public static final int GL_MODELVIEW19_ARB = 34611;
    public static final int GL_MODELVIEW20_ARB = 34612;
    public static final int GL_MODELVIEW21_ARB = 34613;
    public static final int GL_MODELVIEW22_ARB = 34614;
    public static final int GL_MODELVIEW23_ARB = 34615;
    public static final int GL_MODELVIEW24_ARB = 34616;
    public static final int GL_MODELVIEW25_ARB = 34617;
    public static final int GL_MODELVIEW26_ARB = 34618;
    public static final int GL_MODELVIEW27_ARB = 34619;
    public static final int GL_MODELVIEW28_ARB = 34620;
    public static final int GL_MODELVIEW29_ARB = 34621;
    public static final int GL_MODELVIEW30_ARB = 34622;
    public static final int GL_MODELVIEW31_ARB = 34623;
    public static final int GL_CURRENT_WEIGHT_ARB = 34472;
    public static final int GL_WEIGHT_ARRAY_TYPE_ARB = 34473;
    public static final int GL_WEIGHT_ARRAY_STRIDE_ARB = 34474;
    public static final int GL_WEIGHT_ARRAY_SIZE_ARB = 34475;
    public static final int GL_WEIGHT_ARRAY_POINTER_ARB = 34476;
    public static final int GL_WEIGHT_ARRAY_ARB = 34477;

    protected ARBVertexBlend() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glWeightfvARB, gLCapabilities.glWeightbvARB, gLCapabilities.glWeightubvARB, gLCapabilities.glWeightsvARB, gLCapabilities.glWeightusvARB, gLCapabilities.glWeightivARB, gLCapabilities.glWeightuivARB, gLCapabilities.glWeightdvARB, gLCapabilities.glWeightPointerARB, gLCapabilities.glVertexBlendARB);
    }

    public static native void nglWeightfvARB(int var0, long var1);

    public static void glWeightfvARB(@NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        ARBVertexBlend.nglWeightfvARB(floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglWeightbvARB(int var0, long var1);

    public static void glWeightbvARB(@NativeType(value="GLbyte *") ByteBuffer byteBuffer) {
        ARBVertexBlend.nglWeightbvARB(byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglWeightubvARB(int var0, long var1);

    public static void glWeightubvARB(@NativeType(value="GLubyte *") ByteBuffer byteBuffer) {
        ARBVertexBlend.nglWeightubvARB(byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglWeightsvARB(int var0, long var1);

    public static void glWeightsvARB(@NativeType(value="GLshort *") ShortBuffer shortBuffer) {
        ARBVertexBlend.nglWeightsvARB(shortBuffer.remaining(), MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglWeightusvARB(int var0, long var1);

    public static void glWeightusvARB(@NativeType(value="GLushort *") ShortBuffer shortBuffer) {
        ARBVertexBlend.nglWeightusvARB(shortBuffer.remaining(), MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglWeightivARB(int var0, long var1);

    public static void glWeightivARB(@NativeType(value="GLint *") IntBuffer intBuffer) {
        ARBVertexBlend.nglWeightivARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglWeightuivARB(int var0, long var1);

    public static void glWeightuivARB(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        ARBVertexBlend.nglWeightuivARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglWeightdvARB(int var0, long var1);

    public static void glWeightdvARB(@NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        ARBVertexBlend.nglWeightdvARB(doubleBuffer.remaining(), MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglWeightPointerARB(int var0, int var1, int var2, long var3);

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBVertexBlend.nglWeightPointerARB(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") long l) {
        ARBVertexBlend.nglWeightPointerARB(n, n2, n3, l);
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        ARBVertexBlend.nglWeightPointerARB(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        ARBVertexBlend.nglWeightPointerARB(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        ARBVertexBlend.nglWeightPointerARB(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glVertexBlendARB(@NativeType(value="GLint") int var0);

    public static void glWeightfvARB(@NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glWeightfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(fArray.length, fArray, l);
    }

    public static void glWeightsvARB(@NativeType(value="GLshort *") short[] sArray) {
        long l = GL.getICD().glWeightsvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(sArray.length, sArray, l);
    }

    public static void glWeightusvARB(@NativeType(value="GLushort *") short[] sArray) {
        long l = GL.getICD().glWeightusvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(sArray.length, sArray, l);
    }

    public static void glWeightivARB(@NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glWeightivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glWeightuivARB(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glWeightuivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glWeightdvARB(@NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glWeightdvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(dArray.length, dArray, l);
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glWeightPointerARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, sArray, l);
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glWeightPointerARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glWeightPointerARB(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glWeightPointerARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    static {
        GL.initialize();
    }
}

