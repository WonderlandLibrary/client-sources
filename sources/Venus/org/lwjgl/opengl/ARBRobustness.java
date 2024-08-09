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
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBRobustness {
    public static final int GL_GUILTY_CONTEXT_RESET_ARB = 33363;
    public static final int GL_INNOCENT_CONTEXT_RESET_ARB = 33364;
    public static final int GL_UNKNOWN_CONTEXT_RESET_ARB = 33365;
    public static final int GL_RESET_NOTIFICATION_STRATEGY_ARB = 33366;
    public static final int GL_LOSE_CONTEXT_ON_RESET_ARB = 33362;
    public static final int GL_NO_RESET_NOTIFICATION_ARB = 33377;
    public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT_ARB = 4;

    protected ARBRobustness() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glGetGraphicsResetStatusARB, gLCapabilities.glGetMapdv != 0L ? gLCapabilities.glGetnMapdvARB : -1L, gLCapabilities.glGetMapfv != 0L ? gLCapabilities.glGetnMapfvARB : -1L, gLCapabilities.glGetMapiv != 0L ? gLCapabilities.glGetnMapivARB : -1L, gLCapabilities.glGetPixelMapfv != 0L ? gLCapabilities.glGetnPixelMapfvARB : -1L, gLCapabilities.glGetPixelMapuiv != 0L ? gLCapabilities.glGetnPixelMapuivARB : -1L, gLCapabilities.glGetPixelMapusv != 0L ? gLCapabilities.glGetnPixelMapusvARB : -1L, gLCapabilities.glGetPolygonStipple != 0L ? gLCapabilities.glGetnPolygonStippleARB : -1L, gLCapabilities.glGetnTexImageARB, gLCapabilities.glReadnPixelsARB, set.contains("GL_ARB_imaging") && gLCapabilities.glGetColorTable != 0L ? gLCapabilities.glGetnColorTableARB : -1L, set.contains("GL_ARB_imaging") && gLCapabilities.glGetConvolutionFilter != 0L ? gLCapabilities.glGetnConvolutionFilterARB : -1L, set.contains("GL_ARB_imaging") && gLCapabilities.glGetSeparableFilter != 0L ? gLCapabilities.glGetnSeparableFilterARB : -1L, set.contains("GL_ARB_imaging") && gLCapabilities.glGetHistogram != 0L ? gLCapabilities.glGetnHistogramARB : -1L, set.contains("GL_ARB_imaging") && gLCapabilities.glGetMinmax != 0L ? gLCapabilities.glGetnMinmaxARB : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetnCompressedTexImageARB : -1L, set.contains("OpenGL20") ? gLCapabilities.glGetnUniformfvARB : -1L, set.contains("OpenGL20") ? gLCapabilities.glGetnUniformivARB : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetnUniformuivARB : -1L, set.contains("OpenGL40") ? gLCapabilities.glGetnUniformdvARB : -1L);
    }

    @NativeType(value="GLenum")
    public static native int glGetGraphicsResetStatusARB();

    public static native void nglGetnMapdvARB(int var0, int var1, int var2, long var3);

    public static void glGetnMapdvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        ARBRobustness.nglGetnMapdvARB(n, n2, doubleBuffer.remaining(), MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetnMapdARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            ARBRobustness.nglGetnMapdvARB(n, n2, 1, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnMapfvARB(int var0, int var1, int var2, long var3);

    public static void glGetnMapfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        ARBRobustness.nglGetnMapfvARB(n, n2, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetnMapfARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBRobustness.nglGetnMapfvARB(n, n2, 1, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnMapivARB(int var0, int var1, int var2, long var3);

    public static void glGetnMapivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        ARBRobustness.nglGetnMapivARB(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetnMapiARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBRobustness.nglGetnMapivARB(n, n2, 1, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnPixelMapfvARB(int var0, int var1, long var2);

    public static void glGetnPixelMapfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        ARBRobustness.nglGetnPixelMapfvARB(n, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetnPixelMapuivARB(int var0, int var1, long var2);

    public static void glGetnPixelMapuivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        ARBRobustness.nglGetnPixelMapuivARB(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetnPixelMapusvARB(int var0, int var1, long var2);

    public static void glGetnPixelMapusvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLushort *") ShortBuffer shortBuffer) {
        ARBRobustness.nglGetnPixelMapusvARB(n, shortBuffer.remaining(), MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglGetnPolygonStippleARB(int var0, long var1);

    public static void glGetnPolygonStippleARB(@NativeType(value="GLsizei") int n, @NativeType(value="GLubyte *") long l) {
        ARBRobustness.nglGetnPolygonStippleARB(n, l);
    }

    public static void glGetnPolygonStippleARB(@NativeType(value="GLubyte *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnPolygonStippleARB(byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetnTexImageARB(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="void *") long l) {
        ARBRobustness.nglGetnTexImageARB(n, n2, n3, n4, n5, l);
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnTexImageARB(n, n2, n3, n4, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ShortBuffer shortBuffer) {
        ARBRobustness.nglGetnTexImageARB(n, n2, n3, n4, shortBuffer.remaining() << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") IntBuffer intBuffer) {
        ARBRobustness.nglGetnTexImageARB(n, n2, n3, n4, intBuffer.remaining() << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") FloatBuffer floatBuffer) {
        ARBRobustness.nglGetnTexImageARB(n, n2, n3, n4, floatBuffer.remaining() << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        ARBRobustness.nglGetnTexImageARB(n, n2, n3, n4, doubleBuffer.remaining() << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglReadnPixelsARB(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="void *") long l) {
        ARBRobustness.nglReadnPixelsARB(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglReadnPixelsARB(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ShortBuffer shortBuffer) {
        ARBRobustness.nglReadnPixelsARB(n, n2, n3, n4, n5, n6, shortBuffer.remaining() << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") IntBuffer intBuffer) {
        ARBRobustness.nglReadnPixelsARB(n, n2, n3, n4, n5, n6, intBuffer.remaining() << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") FloatBuffer floatBuffer) {
        ARBRobustness.nglReadnPixelsARB(n, n2, n3, n4, n5, n6, floatBuffer.remaining() << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetnColorTableARB(int var0, int var1, int var2, int var3, long var4);

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void *") long l) {
        ARBRobustness.nglGetnColorTableARB(n, n2, n3, n4, l);
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnColorTableARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ShortBuffer shortBuffer) {
        ARBRobustness.nglGetnColorTableARB(n, n2, n3, shortBuffer.remaining() << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") IntBuffer intBuffer) {
        ARBRobustness.nglGetnColorTableARB(n, n2, n3, intBuffer.remaining() << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") FloatBuffer floatBuffer) {
        ARBRobustness.nglGetnColorTableARB(n, n2, n3, floatBuffer.remaining() << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetnConvolutionFilterARB(int var0, int var1, int var2, int var3, long var4);

    public static void glGetnConvolutionFilterARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void *") long l) {
        ARBRobustness.nglGetnConvolutionFilterARB(n, n2, n3, n4, l);
    }

    public static void glGetnConvolutionFilterARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnConvolutionFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetnSeparableFilterARB(int var0, int var1, int var2, int var3, long var4, int var6, long var7, long var9);

    public static void glGetnSeparableFilterARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void *") long l, @NativeType(value="GLsizei") int n5, @NativeType(value="void *") long l2, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnSeparableFilterARB(n, n2, n3, n4, l, n5, l2, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glGetnSeparableFilterARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="void *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer3) {
        ARBRobustness.nglGetnSeparableFilterARB(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), byteBuffer2.remaining(), MemoryUtil.memAddress(byteBuffer2), MemoryUtil.memAddressSafe(byteBuffer3));
    }

    public static native void nglGetnHistogramARB(int var0, boolean var1, int var2, int var3, int var4, long var5);

    public static void glGetnHistogramARB(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void *") long l) {
        ARBRobustness.nglGetnHistogramARB(n, bl, n2, n3, n4, l);
    }

    public static void glGetnHistogramARB(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnHistogramARB(n, bl, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetnMinmaxARB(int var0, boolean var1, int var2, int var3, int var4, long var5);

    public static void glGetnMinmaxARB(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void *") long l) {
        ARBRobustness.nglGetnMinmaxARB(n, bl, n2, n3, n4, l);
    }

    public static void glGetnMinmaxARB(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBRobustness.nglGetnMinmaxARB(n, bl, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetnCompressedTexImageARB(int var0, int var1, int var2, long var3);

    public static void glGetnCompressedTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void *") long l) {
        ARBRobustness.nglGetnCompressedTexImageARB(n, n2, n3, l);
    }

    public static void glGetnCompressedTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.check((Buffer)byteBuffer, GL11.glGetTexLevelParameteri(n, n2, 34464));
        }
        ARBRobustness.nglGetnCompressedTexImageARB(n, n2, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetnUniformfvARB(int var0, int var1, int var2, long var3);

    public static void glGetnUniformfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        ARBRobustness.nglGetnUniformfvARB(n, n2, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetnUniformfARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBRobustness.nglGetnUniformfvARB(n, n2, 1, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnUniformivARB(int var0, int var1, int var2, long var3);

    public static void glGetnUniformivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        ARBRobustness.nglGetnUniformivARB(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetnUniformiARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBRobustness.nglGetnUniformivARB(n, n2, 1, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnUniformuivARB(int var0, int var1, int var2, long var3);

    public static void glGetnUniformuivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        ARBRobustness.nglGetnUniformuivARB(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetnUniformuiARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBRobustness.nglGetnUniformuivARB(n, n2, 1, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetnUniformdvARB(int var0, int var1, int var2, long var3);

    public static void glGetnUniformdvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        ARBRobustness.nglGetnUniformdvARB(n, n2, doubleBuffer.remaining(), MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetnUniformdARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            ARBRobustness.nglGetnUniformdvARB(n, n2, 1, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glGetnMapdvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetnMapdvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length, dArray, l);
    }

    public static void glGetnMapfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetnMapfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length, fArray, l);
    }

    public static void glGetnMapivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetnMapivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glGetnPixelMapfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetnPixelMapfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length, fArray, l);
    }

    public static void glGetnPixelMapuivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetnPixelMapuivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glGetnPixelMapusvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLushort *") short[] sArray) {
        long l = GL.getICD().glGetnPixelMapusvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, sArray.length, sArray, l);
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glGetnTexImageARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, sArray.length << 1, sArray, l);
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetnTexImageARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray.length << 2, nArray, l);
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetnTexImageARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, fArray.length << 2, fArray, l);
    }

    public static void glGetnTexImageARB(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") double[] dArray) {
        long l = GL.getICD().glGetnTexImageARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, dArray.length << 3, dArray, l);
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glReadnPixelsARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, sArray.length << 1, sArray, l);
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glReadnPixelsARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, nArray.length << 2, nArray, l);
    }

    public static void glReadnPixelsARB(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glReadnPixelsARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, fArray.length << 2, fArray, l);
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glGetnColorTableARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, sArray.length << 1, sArray, l);
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetnColorTableARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, nArray.length << 2, nArray, l);
    }

    public static void glGetnColorTableARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetnColorTableARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, fArray.length << 2, fArray, l);
    }

    public static void glGetnUniformfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetnUniformfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length, fArray, l);
    }

    public static void glGetnUniformivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetnUniformivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glGetnUniformuivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetnUniformuivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glGetnUniformdvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetnUniformdvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length, dArray, l);
    }

    static {
        GL.initialize();
    }
}

