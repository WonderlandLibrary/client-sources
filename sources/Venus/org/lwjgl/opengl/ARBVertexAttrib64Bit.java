/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL41C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBVertexAttrib64Bit {
    public static final int GL_DOUBLE_VEC2 = 36860;
    public static final int GL_DOUBLE_VEC3 = 36861;
    public static final int GL_DOUBLE_VEC4 = 36862;
    public static final int GL_DOUBLE_MAT2 = 36678;
    public static final int GL_DOUBLE_MAT3 = 36679;
    public static final int GL_DOUBLE_MAT4 = 36680;
    public static final int GL_DOUBLE_MAT2x3 = 36681;
    public static final int GL_DOUBLE_MAT2x4 = 36682;
    public static final int GL_DOUBLE_MAT3x2 = 36683;
    public static final int GL_DOUBLE_MAT3x4 = 36684;
    public static final int GL_DOUBLE_MAT4x2 = 36685;
    public static final int GL_DOUBLE_MAT4x3 = 36686;

    protected ARBVertexAttrib64Bit() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glVertexAttribL1d, gLCapabilities.glVertexAttribL2d, gLCapabilities.glVertexAttribL3d, gLCapabilities.glVertexAttribL4d, gLCapabilities.glVertexAttribL1dv, gLCapabilities.glVertexAttribL2dv, gLCapabilities.glVertexAttribL3dv, gLCapabilities.glVertexAttribL4dv, gLCapabilities.glVertexAttribLPointer, gLCapabilities.glGetVertexAttribLdv, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glVertexArrayVertexAttribLOffsetEXT : -1L);
    }

    public static void glVertexAttribL1d(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d) {
        GL41C.glVertexAttribL1d(n, d);
    }

    public static void glVertexAttribL2d(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2) {
        GL41C.glVertexAttribL2d(n, d, d2);
    }

    public static void glVertexAttribL3d(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLdouble") double d3) {
        GL41C.glVertexAttribL3d(n, d, d2, d3);
    }

    public static void glVertexAttribL4d(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLdouble") double d3, @NativeType(value="GLdouble") double d4) {
        GL41C.glVertexAttribL4d(n, d, d2, d3, d4);
    }

    public static void nglVertexAttribL1dv(int n, long l) {
        GL41C.nglVertexAttribL1dv(n, l);
    }

    public static void glVertexAttribL1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glVertexAttribL1dv(n, doubleBuffer);
    }

    public static void nglVertexAttribL2dv(int n, long l) {
        GL41C.nglVertexAttribL2dv(n, l);
    }

    public static void glVertexAttribL2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glVertexAttribL2dv(n, doubleBuffer);
    }

    public static void nglVertexAttribL3dv(int n, long l) {
        GL41C.nglVertexAttribL3dv(n, l);
    }

    public static void glVertexAttribL3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glVertexAttribL3dv(n, doubleBuffer);
    }

    public static void nglVertexAttribL4dv(int n, long l) {
        GL41C.nglVertexAttribL4dv(n, l);
    }

    public static void glVertexAttribL4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glVertexAttribL4dv(n, doubleBuffer);
    }

    public static void nglVertexAttribLPointer(int n, int n2, int n3, int n4, long l) {
        GL41C.nglVertexAttribLPointer(n, n2, n3, n4, l);
    }

    public static void glVertexAttribLPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL41C.glVertexAttribLPointer(n, n2, n3, n4, byteBuffer);
    }

    public static void glVertexAttribLPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        GL41C.glVertexAttribLPointer(n, n2, n3, n4, l);
    }

    public static void glVertexAttribLPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL41C.glVertexAttribLPointer(n, n2, n3, doubleBuffer);
    }

    public static void nglGetVertexAttribLdv(int n, int n2, long l) {
        GL41C.nglGetVertexAttribLdv(n, n2, l);
    }

    public static void glGetVertexAttribLdv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        GL41C.glGetVertexAttribLdv(n, n2, doubleBuffer);
    }

    public static native void glVertexArrayVertexAttribLOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLintptr") long var6);

    public static void glVertexAttribL1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glVertexAttribL1dv(n, dArray);
    }

    public static void glVertexAttribL2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glVertexAttribL2dv(n, dArray);
    }

    public static void glVertexAttribL3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glVertexAttribL3dv(n, dArray);
    }

    public static void glVertexAttribL4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glVertexAttribL4dv(n, dArray);
    }

    public static void glGetVertexAttribLdv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        GL41C.glGetVertexAttribLdv(n, n2, dArray);
    }

    static {
        GL.initialize();
    }
}

