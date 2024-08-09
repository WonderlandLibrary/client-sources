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

public class ARBMultitexture {
    public static final int GL_TEXTURE0_ARB = 33984;
    public static final int GL_TEXTURE1_ARB = 33985;
    public static final int GL_TEXTURE2_ARB = 33986;
    public static final int GL_TEXTURE3_ARB = 33987;
    public static final int GL_TEXTURE4_ARB = 33988;
    public static final int GL_TEXTURE5_ARB = 33989;
    public static final int GL_TEXTURE6_ARB = 33990;
    public static final int GL_TEXTURE7_ARB = 33991;
    public static final int GL_TEXTURE8_ARB = 33992;
    public static final int GL_TEXTURE9_ARB = 33993;
    public static final int GL_TEXTURE10_ARB = 33994;
    public static final int GL_TEXTURE11_ARB = 33995;
    public static final int GL_TEXTURE12_ARB = 33996;
    public static final int GL_TEXTURE13_ARB = 33997;
    public static final int GL_TEXTURE14_ARB = 33998;
    public static final int GL_TEXTURE15_ARB = 33999;
    public static final int GL_TEXTURE16_ARB = 34000;
    public static final int GL_TEXTURE17_ARB = 34001;
    public static final int GL_TEXTURE18_ARB = 34002;
    public static final int GL_TEXTURE19_ARB = 34003;
    public static final int GL_TEXTURE20_ARB = 34004;
    public static final int GL_TEXTURE21_ARB = 34005;
    public static final int GL_TEXTURE22_ARB = 34006;
    public static final int GL_TEXTURE23_ARB = 34007;
    public static final int GL_TEXTURE24_ARB = 34008;
    public static final int GL_TEXTURE25_ARB = 34009;
    public static final int GL_TEXTURE26_ARB = 34010;
    public static final int GL_TEXTURE27_ARB = 34011;
    public static final int GL_TEXTURE28_ARB = 34012;
    public static final int GL_TEXTURE29_ARB = 34013;
    public static final int GL_TEXTURE30_ARB = 34014;
    public static final int GL_TEXTURE31_ARB = 34015;
    public static final int GL_ACTIVE_TEXTURE_ARB = 34016;
    public static final int GL_CLIENT_ACTIVE_TEXTURE_ARB = 34017;
    public static final int GL_MAX_TEXTURE_UNITS_ARB = 34018;

    protected ARBMultitexture() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glActiveTextureARB, gLCapabilities.glClientActiveTextureARB, gLCapabilities.glMultiTexCoord1fARB, gLCapabilities.glMultiTexCoord1sARB, gLCapabilities.glMultiTexCoord1iARB, gLCapabilities.glMultiTexCoord1dARB, gLCapabilities.glMultiTexCoord1fvARB, gLCapabilities.glMultiTexCoord1svARB, gLCapabilities.glMultiTexCoord1ivARB, gLCapabilities.glMultiTexCoord1dvARB, gLCapabilities.glMultiTexCoord2fARB, gLCapabilities.glMultiTexCoord2sARB, gLCapabilities.glMultiTexCoord2iARB, gLCapabilities.glMultiTexCoord2dARB, gLCapabilities.glMultiTexCoord2fvARB, gLCapabilities.glMultiTexCoord2svARB, gLCapabilities.glMultiTexCoord2ivARB, gLCapabilities.glMultiTexCoord2dvARB, gLCapabilities.glMultiTexCoord3fARB, gLCapabilities.glMultiTexCoord3sARB, gLCapabilities.glMultiTexCoord3iARB, gLCapabilities.glMultiTexCoord3dARB, gLCapabilities.glMultiTexCoord3fvARB, gLCapabilities.glMultiTexCoord3svARB, gLCapabilities.glMultiTexCoord3ivARB, gLCapabilities.glMultiTexCoord3dvARB, gLCapabilities.glMultiTexCoord4fARB, gLCapabilities.glMultiTexCoord4sARB, gLCapabilities.glMultiTexCoord4iARB, gLCapabilities.glMultiTexCoord4dARB, gLCapabilities.glMultiTexCoord4fvARB, gLCapabilities.glMultiTexCoord4svARB, gLCapabilities.glMultiTexCoord4ivARB, gLCapabilities.glMultiTexCoord4dvARB);
    }

    public static native void glActiveTextureARB(@NativeType(value="GLenum") int var0);

    public static native void glClientActiveTextureARB(@NativeType(value="GLenum") int var0);

    public static native void glMultiTexCoord1fARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glMultiTexCoord1sARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLshort") short var1);

    public static native void glMultiTexCoord1iARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void glMultiTexCoord1dARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1);

    public static native void nglMultiTexCoord1fvARB(int var0, long var1);

    public static void glMultiTexCoord1fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        ARBMultitexture.nglMultiTexCoord1fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultiTexCoord1svARB(int var0, long var1);

    public static void glMultiTexCoord1svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        ARBMultitexture.nglMultiTexCoord1svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglMultiTexCoord1ivARB(int var0, long var1);

    public static void glMultiTexCoord1ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBMultitexture.nglMultiTexCoord1ivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoord1dvARB(int var0, long var1);

    public static void glMultiTexCoord1dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        ARBMultitexture.nglMultiTexCoord1dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glMultiTexCoord2fARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glMultiTexCoord2sARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glMultiTexCoord2iARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glMultiTexCoord2dARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void nglMultiTexCoord2fvARB(int var0, long var1);

    public static void glMultiTexCoord2fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        ARBMultitexture.nglMultiTexCoord2fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultiTexCoord2svARB(int var0, long var1);

    public static void glMultiTexCoord2svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        ARBMultitexture.nglMultiTexCoord2svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglMultiTexCoord2ivARB(int var0, long var1);

    public static void glMultiTexCoord2ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        ARBMultitexture.nglMultiTexCoord2ivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoord2dvARB(int var0, long var1);

    public static void glMultiTexCoord2dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        ARBMultitexture.nglMultiTexCoord2dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glMultiTexCoord3fARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glMultiTexCoord3sARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glMultiTexCoord3iARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glMultiTexCoord3dARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void nglMultiTexCoord3fvARB(int var0, long var1);

    public static void glMultiTexCoord3fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        ARBMultitexture.nglMultiTexCoord3fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultiTexCoord3svARB(int var0, long var1);

    public static void glMultiTexCoord3svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        ARBMultitexture.nglMultiTexCoord3svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglMultiTexCoord3ivARB(int var0, long var1);

    public static void glMultiTexCoord3ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        ARBMultitexture.nglMultiTexCoord3ivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoord3dvARB(int var0, long var1);

    public static void glMultiTexCoord3dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        ARBMultitexture.nglMultiTexCoord3dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glMultiTexCoord4fARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glMultiTexCoord4sARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3, @NativeType(value="GLshort") short var4);

    public static native void glMultiTexCoord4iARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void glMultiTexCoord4dARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7);

    public static native void nglMultiTexCoord4fvARB(int var0, long var1);

    public static void glMultiTexCoord4fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBMultitexture.nglMultiTexCoord4fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultiTexCoord4svARB(int var0, long var1);

    public static void glMultiTexCoord4svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        ARBMultitexture.nglMultiTexCoord4svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglMultiTexCoord4ivARB(int var0, long var1);

    public static void glMultiTexCoord4ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBMultitexture.nglMultiTexCoord4ivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoord4dvARB(int var0, long var1);

    public static void glMultiTexCoord4dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBMultitexture.nglMultiTexCoord4dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static void glMultiTexCoord1fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexCoord1fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMultiTexCoord1svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord1svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord1ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoord1ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glMultiTexCoord1dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultiTexCoord1dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glMultiTexCoord2fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexCoord2fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMultiTexCoord2svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord2svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord2ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoord2ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glMultiTexCoord2dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultiTexCoord2dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glMultiTexCoord3fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexCoord3fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMultiTexCoord3svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord3svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord3ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoord3ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glMultiTexCoord3dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultiTexCoord3dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glMultiTexCoord4fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexCoord4fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMultiTexCoord4svARB(@NativeType(value="GLenum") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord4svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord4ivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoord4ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glMultiTexCoord4dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultiTexCoord4dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, dArray, l);
    }

    static {
        GL.initialize();
    }
}

