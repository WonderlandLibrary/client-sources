/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL14C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBImaging {
    public static final int GL_COLOR_TABLE = 32976;
    public static final int GL_POST_CONVOLUTION_COLOR_TABLE = 32977;
    public static final int GL_POST_COLOR_MATRIX_COLOR_TABLE = 32978;
    public static final int GL_PROXY_COLOR_TABLE = 32979;
    public static final int GL_PROXY_POST_CONVOLUTION_COLOR_TABLE = 32980;
    public static final int GL_PROXY_POST_COLOR_MATRIX_COLOR_TABLE = 32981;
    public static final int GL_COLOR_TABLE_SCALE = 32982;
    public static final int GL_COLOR_TABLE_BIAS = 32983;
    public static final int GL_COLOR_TABLE_FORMAT = 32984;
    public static final int GL_COLOR_TABLE_WIDTH = 32985;
    public static final int GL_COLOR_TABLE_RED_SIZE = 32986;
    public static final int GL_COLOR_TABLE_GREEN_SIZE = 32987;
    public static final int GL_COLOR_TABLE_BLUE_SIZE = 32988;
    public static final int GL_COLOR_TABLE_ALPHA_SIZE = 32989;
    public static final int GL_COLOR_TABLE_LUMINANCE_SIZE = 32990;
    public static final int GL_COLOR_TABLE_INTENSITY_SIZE = 32991;
    public static final int GL_TABLE_TOO_LARGE = 32817;
    public static final int GL_CONVOLUTION_1D = 32784;
    public static final int GL_CONVOLUTION_2D = 32785;
    public static final int GL_SEPARABLE_2D = 32786;
    public static final int GL_CONVOLUTION_BORDER_MODE = 32787;
    public static final int GL_CONVOLUTION_FILTER_SCALE = 32788;
    public static final int GL_CONVOLUTION_FILTER_BIAS = 32789;
    public static final int GL_REDUCE = 32790;
    public static final int GL_CONVOLUTION_FORMAT = 32791;
    public static final int GL_CONVOLUTION_WIDTH = 32792;
    public static final int GL_CONVOLUTION_HEIGHT = 32793;
    public static final int GL_MAX_CONVOLUTION_WIDTH = 32794;
    public static final int GL_MAX_CONVOLUTION_HEIGHT = 32795;
    public static final int GL_POST_CONVOLUTION_RED_SCALE = 32796;
    public static final int GL_POST_CONVOLUTION_GREEN_SCALE = 32797;
    public static final int GL_POST_CONVOLUTION_BLUE_SCALE = 32798;
    public static final int GL_POST_CONVOLUTION_ALPHA_SCALE = 32799;
    public static final int GL_POST_CONVOLUTION_RED_BIAS = 32800;
    public static final int GL_POST_CONVOLUTION_GREEN_BIAS = 32801;
    public static final int GL_POST_CONVOLUTION_BLUE_BIAS = 32802;
    public static final int GL_POST_CONVOLUTION_ALPHA_BIAS = 32803;
    public static final int GL_CONSTANT_BORDER = 33105;
    public static final int GL_REPLICATE_BORDER = 33107;
    public static final int GL_CONVOLUTION_BORDER_COLOR = 33108;
    public static final int GL_COLOR_MATRIX = 32945;
    public static final int GL_COLOR_MATRIX_STACK_DEPTH = 32946;
    public static final int GL_MAX_COLOR_MATRIX_STACK_DEPTH = 32947;
    public static final int GL_POST_COLOR_MATRIX_RED_SCALE = 32948;
    public static final int GL_POST_COLOR_MATRIX_GREEN_SCALE = 32949;
    public static final int GL_POST_COLOR_MATRIX_BLUE_SCALE = 32950;
    public static final int GL_POST_COLOR_MATRIX_ALPHA_SCALE = 32951;
    public static final int GL_POST_COLOR_MATRIX_RED_BIAS = 32952;
    public static final int GL_POST_COLOR_MATRIX_GREEN_BIAS = 32953;
    public static final int GL_POST_COLOR_MATRIX_BLUE_BIAS = 32954;
    public static final int GL_POST_COLOR_MATRIX_ALPHA_BIAS = 32955;
    public static final int GL_HISTOGRAM = 32804;
    public static final int GL_PROXY_HISTOGRAM = 32805;
    public static final int GL_HISTOGRAM_WIDTH = 32806;
    public static final int GL_HISTOGRAM_FORMAT = 32807;
    public static final int GL_HISTOGRAM_RED_SIZE = 32808;
    public static final int GL_HISTOGRAM_GREEN_SIZE = 32809;
    public static final int GL_HISTOGRAM_BLUE_SIZE = 32810;
    public static final int GL_HISTOGRAM_ALPHA_SIZE = 32811;
    public static final int GL_HISTOGRAM_LUMINANCE_SIZE = 32812;
    public static final int GL_HISTOGRAM_SINK = 32813;
    public static final int GL_MINMAX = 32814;
    public static final int GL_MINMAX_FORMAT = 32815;
    public static final int GL_MINMAX_SINK = 32816;
    public static final int GL_CONSTANT_COLOR = 32769;
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 32770;
    public static final int GL_CONSTANT_ALPHA = 32771;
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 32772;
    public static final int GL_BLEND_COLOR = 32773;
    public static final int GL_FUNC_ADD = 32774;
    public static final int GL_MIN = 32775;
    public static final int GL_MAX = 32776;
    public static final int GL_BLEND_EQUATION = 32777;
    public static final int GL_FUNC_SUBTRACT = 32778;
    public static final int GL_FUNC_REVERSE_SUBTRACT = 32779;

    protected ARBImaging() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, boolean bl) {
        return (bl || Checks.checkFunctions(gLCapabilities.glColorTable, gLCapabilities.glCopyColorTable, gLCapabilities.glColorTableParameteriv, gLCapabilities.glColorTableParameterfv, gLCapabilities.glGetColorTable, gLCapabilities.glGetColorTableParameteriv, gLCapabilities.glGetColorTableParameterfv, gLCapabilities.glColorSubTable, gLCapabilities.glCopyColorSubTable, gLCapabilities.glConvolutionFilter1D, gLCapabilities.glConvolutionFilter2D, gLCapabilities.glCopyConvolutionFilter1D, gLCapabilities.glCopyConvolutionFilter2D, gLCapabilities.glGetConvolutionFilter, gLCapabilities.glSeparableFilter2D, gLCapabilities.glGetSeparableFilter, gLCapabilities.glConvolutionParameteri, gLCapabilities.glConvolutionParameteriv, gLCapabilities.glConvolutionParameterf, gLCapabilities.glConvolutionParameterfv, gLCapabilities.glGetConvolutionParameteriv, gLCapabilities.glGetConvolutionParameterfv, gLCapabilities.glHistogram, gLCapabilities.glResetHistogram, gLCapabilities.glGetHistogram, gLCapabilities.glGetHistogramParameteriv, gLCapabilities.glGetHistogramParameterfv, gLCapabilities.glMinmax, gLCapabilities.glResetMinmax, gLCapabilities.glGetMinmax, gLCapabilities.glGetMinmaxParameteriv, gLCapabilities.glGetMinmaxParameterfv)) && Checks.checkFunctions(gLCapabilities.glBlendColor, gLCapabilities.glBlendEquation);
    }

    public static native void nglColorTable(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBImaging.nglColorTable(n, n2, n3, n4, n5, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l) {
        ARBImaging.nglColorTable(n, n2, n3, n4, n5, l);
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        ARBImaging.nglColorTable(n, n2, n3, n4, n5, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") IntBuffer intBuffer) {
        ARBImaging.nglColorTable(n, n2, n3, n4, n5, MemoryUtil.memAddress(intBuffer));
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        ARBImaging.nglColorTable(n, n2, n3, n4, n5, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glCopyColorTable(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLsizei") int var4);

    public static native void nglColorTableParameteriv(int var0, int var1, long var2);

    public static void glColorTableParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBImaging.nglColorTableParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglColorTableParameterfv(int var0, int var1, long var2);

    public static void glColorTableParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBImaging.nglColorTableParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetColorTable(int var0, int var1, int var2, long var3);

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBImaging.nglGetColorTable(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") long l) {
        ARBImaging.nglGetColorTable(n, n2, n3, l);
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ShortBuffer shortBuffer) {
        ARBImaging.nglGetColorTable(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") IntBuffer intBuffer) {
        ARBImaging.nglGetColorTable(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") FloatBuffer floatBuffer) {
        ARBImaging.nglGetColorTable(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetColorTableParameteriv(int var0, int var1, long var2);

    public static void glGetColorTableParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBImaging.nglGetColorTableParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetColorTableParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBImaging.nglGetColorTableParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetColorTableParameterfv(int var0, int var1, long var2);

    public static void glGetColorTableParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBImaging.nglGetColorTableParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetColorTableParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBImaging.nglGetColorTableParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglColorSubTable(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glColorSubTable(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBImaging.nglColorSubTable(n, n2, n3, n4, n5, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glColorSubTable(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l) {
        ARBImaging.nglColorSubTable(n, n2, n3, n4, n5, l);
    }

    public static native void glCopyColorSubTable(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLsizei") int var4);

    public static native void nglConvolutionFilter1D(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glConvolutionFilter1D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBImaging.nglConvolutionFilter1D(n, n2, n3, n4, n5, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glConvolutionFilter1D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l) {
        ARBImaging.nglConvolutionFilter1D(n, n2, n3, n4, n5, l);
    }

    public static native void nglConvolutionFilter2D(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

    public static void glConvolutionFilter2D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBImaging.nglConvolutionFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glConvolutionFilter2D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") long l) {
        ARBImaging.nglConvolutionFilter2D(n, n2, n3, n4, n5, n6, l);
    }

    public static native void glCopyConvolutionFilter1D(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLsizei") int var4);

    public static native void glCopyConvolutionFilter2D(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5);

    public static native void nglGetConvolutionFilter(int var0, int var1, int var2, long var3);

    public static void glGetConvolutionFilter(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBImaging.nglGetConvolutionFilter(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetConvolutionFilter(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") long l) {
        ARBImaging.nglGetConvolutionFilter(n, n2, n3, l);
    }

    public static native void nglSeparableFilter2D(int var0, int var1, int var2, int var3, int var4, int var5, long var6, long var8);

    public static void glSeparableFilter2D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="void const *") ByteBuffer byteBuffer2) {
        ARBImaging.nglSeparableFilter2D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2));
    }

    public static void glSeparableFilter2D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") long l, @NativeType(value="void const *") long l2) {
        ARBImaging.nglSeparableFilter2D(n, n2, n3, n4, n5, n6, l, l2);
    }

    public static native void nglGetSeparableFilter(int var0, int var1, int var2, long var3, long var5, long var7);

    public static void glGetSeparableFilter(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="void *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer3) {
        ARBImaging.nglGetSeparableFilter(n, n2, n3, MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2), MemoryUtil.memAddressSafe(byteBuffer3));
    }

    public static void glGetSeparableFilter(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") long l, @NativeType(value="void *") long l2, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBImaging.nglGetSeparableFilter(n, n2, n3, l, l2, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void glConvolutionParameteri(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void nglConvolutionParameteriv(int var0, int var1, long var2);

    public static void glConvolutionParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBImaging.nglConvolutionParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glConvolutionParameterf(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLfloat") float var2);

    public static native void nglConvolutionParameterfv(int var0, int var1, long var2);

    public static void glConvolutionParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBImaging.nglConvolutionParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetConvolutionParameteriv(int var0, int var1, long var2);

    public static void glGetConvolutionParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBImaging.nglGetConvolutionParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetConvolutionParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBImaging.nglGetConvolutionParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetConvolutionParameterfv(int var0, int var1, long var2);

    public static void glGetConvolutionParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBImaging.nglGetConvolutionParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetConvolutionParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBImaging.nglGetConvolutionParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glHistogram(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLboolean") boolean var3);

    public static native void glResetHistogram(@NativeType(value="GLenum") int var0);

    public static native void nglGetHistogram(int var0, boolean var1, int var2, int var3, long var4);

    public static void glGetHistogram(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBImaging.nglGetHistogram(n, bl, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetHistogram(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") long l) {
        ARBImaging.nglGetHistogram(n, bl, n2, n3, l);
    }

    public static native void nglGetHistogramParameteriv(int var0, int var1, long var2);

    public static void glGetHistogramParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBImaging.nglGetHistogramParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetHistogramParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBImaging.nglGetHistogramParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetHistogramParameterfv(int var0, int var1, long var2);

    public static void glGetHistogramParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        ARBImaging.nglGetHistogramParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetHistogramParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBImaging.nglGetHistogramParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glMinmax(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLboolean") boolean var2);

    public static native void glResetMinmax(@NativeType(value="GLenum") int var0);

    public static native void nglGetMinmax(int var0, boolean var1, int var2, int var3, long var4);

    public static void glGetMinmax(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBImaging.nglGetMinmax(n, bl, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetMinmax(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") long l) {
        ARBImaging.nglGetMinmax(n, bl, n2, n3, l);
    }

    public static native void nglGetMinmaxParameteriv(int var0, int var1, long var2);

    public static void glGetMinmaxParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBImaging.nglGetMinmaxParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMinmaxParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBImaging.nglGetMinmaxParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetMinmaxParameterfv(int var0, int var1, long var2);

    public static void glGetMinmaxParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        ARBImaging.nglGetMinmaxParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMinmaxParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBImaging.nglGetMinmaxParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glBlendColor(@NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4) {
        GL14C.glBlendColor(f, f2, f3, f4);
    }

    public static void glBlendEquation(@NativeType(value="GLenum") int n) {
        GL14C.glBlendEquation(n);
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glColorTable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, sArray, l);
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glColorTable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, nArray, l);
    }

    public static void glColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glColorTable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, fArray, l);
    }

    public static void glColorTableParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glColorTableParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glColorTableParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glColorTableParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glGetColorTable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, sArray, l);
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetColorTable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetColorTable(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetColorTable;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetColorTableParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetColorTableParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetColorTableParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetColorTableParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glConvolutionParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glConvolutionParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glConvolutionParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glConvolutionParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetConvolutionParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetConvolutionParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetConvolutionParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetConvolutionParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetHistogramParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetHistogramParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetHistogramParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetHistogramParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetMinmaxParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMinmaxParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetMinmaxParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMinmaxParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    static {
        GL.initialize();
    }
}

