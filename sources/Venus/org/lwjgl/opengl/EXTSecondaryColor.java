/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
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

public class EXTSecondaryColor {
    public static final int GL_COLOR_SUM_EXT = 33880;
    public static final int GL_CURRENT_SECONDARY_COLOR_EXT = 33881;
    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE_EXT = 33882;
    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE_EXT = 33883;
    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE_EXT = 33884;
    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER_EXT = 33885;
    public static final int GL_SECONDARY_COLOR_ARRAY_EXT = 33886;

    protected EXTSecondaryColor() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glSecondaryColor3bEXT, gLCapabilities.glSecondaryColor3sEXT, gLCapabilities.glSecondaryColor3iEXT, gLCapabilities.glSecondaryColor3fEXT, gLCapabilities.glSecondaryColor3dEXT, gLCapabilities.glSecondaryColor3ubEXT, gLCapabilities.glSecondaryColor3usEXT, gLCapabilities.glSecondaryColor3uiEXT, gLCapabilities.glSecondaryColor3bvEXT, gLCapabilities.glSecondaryColor3svEXT, gLCapabilities.glSecondaryColor3ivEXT, gLCapabilities.glSecondaryColor3fvEXT, gLCapabilities.glSecondaryColor3dvEXT, gLCapabilities.glSecondaryColor3ubvEXT, gLCapabilities.glSecondaryColor3usvEXT, gLCapabilities.glSecondaryColor3uivEXT, gLCapabilities.glSecondaryColorPointerEXT);
    }

    public static native void glSecondaryColor3bEXT(@NativeType(value="GLbyte") byte var0, @NativeType(value="GLbyte") byte var1, @NativeType(value="GLbyte") byte var2);

    public static native void glSecondaryColor3sEXT(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glSecondaryColor3iEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glSecondaryColor3fEXT(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glSecondaryColor3dEXT(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void glSecondaryColor3ubEXT(@NativeType(value="GLubyte") byte var0, @NativeType(value="GLubyte") byte var1, @NativeType(value="GLubyte") byte var2);

    public static native void glSecondaryColor3usEXT(@NativeType(value="GLushort") short var0, @NativeType(value="GLushort") short var1, @NativeType(value="GLushort") short var2);

    public static native void glSecondaryColor3uiEXT(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void nglSecondaryColor3bvEXT(long var0);

    public static void glSecondaryColor3bvEXT(@NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3bvEXT(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglSecondaryColor3svEXT(long var0);

    public static void glSecondaryColor3svEXT(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3svEXT(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglSecondaryColor3ivEXT(long var0);

    public static void glSecondaryColor3ivEXT(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3ivEXT(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglSecondaryColor3fvEXT(long var0);

    public static void glSecondaryColor3fvEXT(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3fvEXT(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglSecondaryColor3dvEXT(long var0);

    public static void glSecondaryColor3dvEXT(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3dvEXT(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglSecondaryColor3ubvEXT(long var0);

    public static void glSecondaryColor3ubvEXT(@NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3ubvEXT(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglSecondaryColor3usvEXT(long var0);

    public static void glSecondaryColor3usvEXT(@NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3usvEXT(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglSecondaryColor3uivEXT(long var0);

    public static void glSecondaryColor3uivEXT(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        EXTSecondaryColor.nglSecondaryColor3uivEXT(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglSecondaryColorPointerEXT(int var0, int var1, int var2, long var3);

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTSecondaryColor.nglSecondaryColorPointerEXT(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") long l) {
        EXTSecondaryColor.nglSecondaryColorPointerEXT(n, n2, n3, l);
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTSecondaryColor.nglSecondaryColorPointerEXT(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTSecondaryColor.nglSecondaryColorPointerEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTSecondaryColor.nglSecondaryColorPointerEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glSecondaryColor3svEXT(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glSecondaryColor3svEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glSecondaryColor3ivEXT(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glSecondaryColor3ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glSecondaryColor3fvEXT(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glSecondaryColor3fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glSecondaryColor3dvEXT(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glSecondaryColor3dvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glSecondaryColor3usvEXT(@NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glSecondaryColor3usvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glSecondaryColor3uivEXT(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glSecondaryColor3uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glSecondaryColorPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, sArray, l);
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glSecondaryColorPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glSecondaryColorPointerEXT(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glSecondaryColorPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    static {
        GL.initialize();
    }
}

