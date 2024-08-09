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
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL14
extends GL13 {
    public static final int GL_GENERATE_MIPMAP = 33169;
    public static final int GL_GENERATE_MIPMAP_HINT = 33170;
    public static final int GL_CONSTANT_COLOR = 32769;
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 32770;
    public static final int GL_CONSTANT_ALPHA = 32771;
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 32772;
    public static final int GL_FUNC_ADD = 32774;
    public static final int GL_MIN = 32775;
    public static final int GL_MAX = 32776;
    public static final int GL_FUNC_SUBTRACT = 32778;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;
    public static final int GL_DEPTH_COMPONENT16 = 33189;
    public static final int GL_DEPTH_COMPONENT24 = 33190;
    public static final int GL_DEPTH_COMPONENT32 = 33191;
    public static final int GL_TEXTURE_DEPTH_SIZE = 34890;
    public static final int GL_DEPTH_TEXTURE_MODE = 34891;
    public static final int GL_TEXTURE_COMPARE_MODE = 34892;
    public static final int GL_TEXTURE_COMPARE_FUNC = 34893;
    public static final int GL_COMPARE_R_TO_TEXTURE = 34894;
    public static final int GL_FOG_COORDINATE_SOURCE = 33872;
    public static final int GL_FOG_COORDINATE = 33873;
    public static final int GL_FRAGMENT_DEPTH = 33874;
    public static final int GL_CURRENT_FOG_COORDINATE = 33875;
    public static final int GL_FOG_COORDINATE_ARRAY_TYPE = 33876;
    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = 33877;
    public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 33878;
    public static final int GL_FOG_COORDINATE_ARRAY = 33879;
    public static final int GL_POINT_SIZE_MIN = 33062;
    public static final int GL_POINT_SIZE_MAX = 33063;
    public static final int GL_POINT_FADE_THRESHOLD_SIZE = 33064;
    public static final int GL_POINT_DISTANCE_ATTENUATION = 33065;
    public static final int GL_COLOR_SUM = 33880;
    public static final int GL_CURRENT_SECONDARY_COLOR = 33881;
    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = 33882;
    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = 33883;
    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = 33884;
    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 33885;
    public static final int GL_SECONDARY_COLOR_ARRAY = 33886;
    public static final int GL_BLEND_DST_RGB = 32968;
    public static final int GL_BLEND_SRC_RGB = 32969;
    public static final int GL_BLEND_DST_ALPHA = 32970;
    public static final int GL_BLEND_SRC_ALPHA = 32971;
    public static final int GL_INCR_WRAP = 34055;
    public static final int GL_DECR_WRAP = 34056;
    public static final int GL_TEXTURE_FILTER_CONTROL = 34048;
    public static final int GL_TEXTURE_LOD_BIAS = 34049;
    public static final int GL_MAX_TEXTURE_LOD_BIAS = 34045;
    public static final int GL_MIRRORED_REPEAT = 33648;

    protected GL14() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, boolean bl) {
        return (bl || Checks.checkFunctions(gLCapabilities.glFogCoordf, gLCapabilities.glFogCoordd, gLCapabilities.glFogCoordfv, gLCapabilities.glFogCoorddv, gLCapabilities.glFogCoordPointer, gLCapabilities.glSecondaryColor3b, gLCapabilities.glSecondaryColor3s, gLCapabilities.glSecondaryColor3i, gLCapabilities.glSecondaryColor3f, gLCapabilities.glSecondaryColor3d, gLCapabilities.glSecondaryColor3ub, gLCapabilities.glSecondaryColor3us, gLCapabilities.glSecondaryColor3ui, gLCapabilities.glSecondaryColor3bv, gLCapabilities.glSecondaryColor3sv, gLCapabilities.glSecondaryColor3iv, gLCapabilities.glSecondaryColor3fv, gLCapabilities.glSecondaryColor3dv, gLCapabilities.glSecondaryColor3ubv, gLCapabilities.glSecondaryColor3usv, gLCapabilities.glSecondaryColor3uiv, gLCapabilities.glSecondaryColorPointer, gLCapabilities.glWindowPos2i, gLCapabilities.glWindowPos2s, gLCapabilities.glWindowPos2f, gLCapabilities.glWindowPos2d, gLCapabilities.glWindowPos2iv, gLCapabilities.glWindowPos2sv, gLCapabilities.glWindowPos2fv, gLCapabilities.glWindowPos2dv, gLCapabilities.glWindowPos3i, gLCapabilities.glWindowPos3s, gLCapabilities.glWindowPos3f, gLCapabilities.glWindowPos3d, gLCapabilities.glWindowPos3iv, gLCapabilities.glWindowPos3sv, gLCapabilities.glWindowPos3fv, gLCapabilities.glWindowPos3dv)) && Checks.checkFunctions(gLCapabilities.glBlendColor, gLCapabilities.glBlendEquation, gLCapabilities.glMultiDrawArrays, gLCapabilities.glMultiDrawElements, gLCapabilities.glPointParameterf, gLCapabilities.glPointParameteri, gLCapabilities.glPointParameterfv, gLCapabilities.glPointParameteriv, gLCapabilities.glBlendFuncSeparate);
    }

    public static void glBlendColor(@NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4) {
        GL14C.glBlendColor(f, f2, f3, f4);
    }

    public static void glBlendEquation(@NativeType(value="GLenum") int n) {
        GL14C.glBlendEquation(n);
    }

    public static native void glFogCoordf(@NativeType(value="GLfloat") float var0);

    public static native void glFogCoordd(@NativeType(value="GLdouble") double var0);

    public static native void nglFogCoordfv(long var0);

    public static void glFogCoordfv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL14.nglFogCoordfv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglFogCoorddv(long var0);

    public static void glFogCoorddv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL14.nglFogCoorddv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglFogCoordPointer(int var0, int var1, long var2);

    public static void glFogCoordPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL14.nglFogCoordPointer(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glFogCoordPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") long l) {
        GL14.nglFogCoordPointer(n, n2, l);
    }

    public static void glFogCoordPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL14.nglFogCoordPointer(n, n2, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glFogCoordPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL14.nglFogCoordPointer(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void nglMultiDrawArrays(int n, long l, long l2, int n2) {
        GL14C.nglMultiDrawArrays(n, l, l2, n2);
    }

    public static void glMultiDrawArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer, @NativeType(value="GLsizei const *") IntBuffer intBuffer2) {
        GL14C.glMultiDrawArrays(n, intBuffer, intBuffer2);
    }

    public static void nglMultiDrawElements(int n, long l, int n2, long l2, int n3) {
        GL14C.nglMultiDrawElements(n, l, n2, l2, n3);
    }

    public static void glMultiDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer) {
        GL14C.glMultiDrawElements(n, intBuffer, n2, pointerBuffer);
    }

    public static void glPointParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat") float f) {
        GL14C.glPointParameterf(n, f);
    }

    public static void glPointParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2) {
        GL14C.glPointParameteri(n, n2);
    }

    public static void nglPointParameterfv(int n, long l) {
        GL14C.nglPointParameterfv(n, l);
    }

    public static void glPointParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL14C.glPointParameterfv(n, floatBuffer);
    }

    public static void nglPointParameteriv(int n, long l) {
        GL14C.nglPointParameteriv(n, l);
    }

    public static void glPointParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL14C.glPointParameteriv(n, intBuffer);
    }

    public static native void glSecondaryColor3b(@NativeType(value="GLbyte") byte var0, @NativeType(value="GLbyte") byte var1, @NativeType(value="GLbyte") byte var2);

    public static native void glSecondaryColor3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glSecondaryColor3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glSecondaryColor3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glSecondaryColor3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void glSecondaryColor3ub(@NativeType(value="GLubyte") byte var0, @NativeType(value="GLubyte") byte var1, @NativeType(value="GLubyte") byte var2);

    public static native void glSecondaryColor3us(@NativeType(value="GLushort") short var0, @NativeType(value="GLushort") short var1, @NativeType(value="GLushort") short var2);

    public static native void glSecondaryColor3ui(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void nglSecondaryColor3bv(long var0);

    public static void glSecondaryColor3bv(@NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        GL14.nglSecondaryColor3bv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglSecondaryColor3sv(long var0);

    public static void glSecondaryColor3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL14.nglSecondaryColor3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglSecondaryColor3iv(long var0);

    public static void glSecondaryColor3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL14.nglSecondaryColor3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglSecondaryColor3fv(long var0);

    public static void glSecondaryColor3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL14.nglSecondaryColor3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglSecondaryColor3dv(long var0);

    public static void glSecondaryColor3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL14.nglSecondaryColor3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglSecondaryColor3ubv(long var0);

    public static void glSecondaryColor3ubv(@NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        GL14.nglSecondaryColor3ubv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglSecondaryColor3usv(long var0);

    public static void glSecondaryColor3usv(@NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL14.nglSecondaryColor3usv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglSecondaryColor3uiv(long var0);

    public static void glSecondaryColor3uiv(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL14.nglSecondaryColor3uiv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglSecondaryColorPointer(int var0, int var1, int var2, long var3);

    public static void glSecondaryColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL14.nglSecondaryColorPointer(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glSecondaryColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") long l) {
        GL14.nglSecondaryColorPointer(n, n2, n3, l);
    }

    public static void glSecondaryColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL14.nglSecondaryColorPointer(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glSecondaryColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL14.nglSecondaryColorPointer(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glSecondaryColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL14.nglSecondaryColorPointer(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glBlendFuncSeparate(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4) {
        GL14C.glBlendFuncSeparate(n, n2, n3, n4);
    }

    public static native void glWindowPos2i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glWindowPos2s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1);

    public static native void glWindowPos2f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glWindowPos2d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void nglWindowPos2iv(long var0);

    public static void glWindowPos2iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        GL14.nglWindowPos2iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglWindowPos2sv(long var0);

    public static void glWindowPos2sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        GL14.nglWindowPos2sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglWindowPos2fv(long var0);

    public static void glWindowPos2fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        GL14.nglWindowPos2fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglWindowPos2dv(long var0);

    public static void glWindowPos2dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL14.nglWindowPos2dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glWindowPos3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glWindowPos3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glWindowPos3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glWindowPos3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void nglWindowPos3iv(long var0);

    public static void glWindowPos3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL14.nglWindowPos3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglWindowPos3sv(long var0);

    public static void glWindowPos3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL14.nglWindowPos3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglWindowPos3fv(long var0);

    public static void glWindowPos3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL14.nglWindowPos3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglWindowPos3dv(long var0);

    public static void glWindowPos3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL14.nglWindowPos3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static void glFogCoordfv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glFogCoordfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(fArray, l);
    }

    public static void glFogCoorddv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glFogCoorddv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(dArray, l);
    }

    public static void glMultiDrawArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray, @NativeType(value="GLsizei const *") int[] nArray2) {
        GL14C.glMultiDrawArrays(n, nArray, nArray2);
    }

    public static void glMultiDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer) {
        GL14C.glMultiDrawElements(n, nArray, n2, pointerBuffer);
    }

    public static void glPointParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        GL14C.glPointParameterfv(n, fArray);
    }

    public static void glPointParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL14C.glPointParameteriv(n, nArray);
    }

    public static void glSecondaryColor3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glSecondaryColor3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glSecondaryColor3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glSecondaryColor3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glSecondaryColor3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glSecondaryColor3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glSecondaryColor3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glSecondaryColor3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glSecondaryColor3usv(@NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glSecondaryColor3usv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glSecondaryColor3uiv(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glSecondaryColor3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glWindowPos2iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glWindowPos2iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(nArray, l);
    }

    public static void glWindowPos2sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glWindowPos2sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glWindowPos2fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glWindowPos2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(fArray, l);
    }

    public static void glWindowPos2dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glWindowPos2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(dArray, l);
    }

    public static void glWindowPos3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glWindowPos3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glWindowPos3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glWindowPos3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glWindowPos3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glWindowPos3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glWindowPos3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glWindowPos3dv;
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

