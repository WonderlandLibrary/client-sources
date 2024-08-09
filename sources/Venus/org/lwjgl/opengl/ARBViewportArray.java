/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL41C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBViewportArray {
    public static final int GL_MAX_VIEWPORTS = 33371;
    public static final int GL_VIEWPORT_SUBPIXEL_BITS = 33372;
    public static final int GL_VIEWPORT_BOUNDS_RANGE = 33373;
    public static final int GL_LAYER_PROVOKING_VERTEX = 33374;
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 33375;
    public static final int GL_UNDEFINED_VERTEX = 33376;

    protected ARBViewportArray() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glViewportArrayv, gLCapabilities.glViewportIndexedf, gLCapabilities.glViewportIndexedfv, gLCapabilities.glScissorArrayv, gLCapabilities.glScissorIndexed, gLCapabilities.glScissorIndexedv, gLCapabilities.glDepthRangeArrayv, gLCapabilities.glDepthRangeIndexed, gLCapabilities.glGetFloati_v, gLCapabilities.glGetDoublei_v);
    }

    public static void nglViewportArrayv(int n, int n2, long l) {
        GL41C.nglViewportArrayv(n, n2, l);
    }

    public static void glViewportArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glViewportArrayv(n, floatBuffer);
    }

    public static void glViewportIndexedf(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4) {
        GL41C.glViewportIndexedf(n, f, f2, f3, f4);
    }

    public static void nglViewportIndexedfv(int n, long l) {
        GL41C.nglViewportIndexedfv(n, l);
    }

    public static void glViewportIndexedfv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glViewportIndexedfv(n, floatBuffer);
    }

    public static void nglScissorArrayv(int n, int n2, long l) {
        GL41C.nglScissorArrayv(n, n2, l);
    }

    public static void glScissorArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.glScissorArrayv(n, intBuffer);
    }

    public static void glScissorIndexed(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL41C.glScissorIndexed(n, n2, n3, n4, n5);
    }

    public static void nglScissorIndexedv(int n, long l) {
        GL41C.nglScissorIndexedv(n, l);
    }

    public static void glScissorIndexedv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.glScissorIndexedv(n, intBuffer);
    }

    public static void nglDepthRangeArrayv(int n, int n2, long l) {
        GL41C.nglDepthRangeArrayv(n, n2, l);
    }

    public static void glDepthRangeArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glDepthRangeArrayv(n, doubleBuffer);
    }

    public static void glDepthRangeIndexed(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2) {
        GL41C.glDepthRangeIndexed(n, d, d2);
    }

    public static void nglGetFloati_v(int n, int n2, long l) {
        GL41C.nglGetFloati_v(n, n2, l);
    }

    public static void glGetFloati_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL41C.glGetFloati_v(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetFloati(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL41C.glGetFloati(n, n2);
    }

    public static void nglGetDoublei_v(int n, int n2, long l) {
        GL41C.nglGetDoublei_v(n, n2, l);
    }

    public static void glGetDoublei_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        GL41C.glGetDoublei_v(n, n2, doubleBuffer);
    }

    @NativeType(value="void")
    public static double glGetDoublei(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL41C.glGetDoublei(n, n2);
    }

    public static void glViewportArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glViewportArrayv(n, fArray);
    }

    public static void glViewportIndexedfv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glViewportIndexedfv(n, fArray);
    }

    public static void glScissorArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL41C.glScissorArrayv(n, nArray);
    }

    public static void glScissorIndexedv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL41C.glScissorIndexedv(n, nArray);
    }

    public static void glDepthRangeArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glDepthRangeArrayv(n, dArray);
    }

    public static void glGetFloati_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL41C.glGetFloati_v(n, n2, fArray);
    }

    public static void glGetDoublei_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        GL41C.glGetDoublei_v(n, n2, dArray);
    }

    static {
        GL.initialize();
    }
}

