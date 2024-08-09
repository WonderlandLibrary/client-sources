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
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBVertexProgram {
    public static final int GL_VERTEX_PROGRAM_ARB = 34336;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 34370;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 34371;
    public static final int GL_COLOR_SUM_ARB = 33880;
    public static final int GL_PROGRAM_FORMAT_ASCII_ARB = 34933;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 34338;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 34339;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 34340;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 34341;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 34922;
    public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 34342;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 34373;
    public static final int GL_PROGRAM_LENGTH_ARB = 34343;
    public static final int GL_PROGRAM_FORMAT_ARB = 34934;
    public static final int GL_PROGRAM_BINDING_ARB = 34423;
    public static final int GL_PROGRAM_INSTRUCTIONS_ARB = 34976;
    public static final int GL_MAX_PROGRAM_INSTRUCTIONS_ARB = 34977;
    public static final int GL_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34978;
    public static final int GL_MAX_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 34979;
    public static final int GL_PROGRAM_TEMPORARIES_ARB = 34980;
    public static final int GL_MAX_PROGRAM_TEMPORARIES_ARB = 34981;
    public static final int GL_PROGRAM_NATIVE_TEMPORARIES_ARB = 34982;
    public static final int GL_MAX_PROGRAM_NATIVE_TEMPORARIES_ARB = 34983;
    public static final int GL_PROGRAM_PARAMETERS_ARB = 34984;
    public static final int GL_MAX_PROGRAM_PARAMETERS_ARB = 34985;
    public static final int GL_PROGRAM_NATIVE_PARAMETERS_ARB = 34986;
    public static final int GL_MAX_PROGRAM_NATIVE_PARAMETERS_ARB = 34987;
    public static final int GL_PROGRAM_ATTRIBS_ARB = 34988;
    public static final int GL_MAX_PROGRAM_ATTRIBS_ARB = 34989;
    public static final int GL_PROGRAM_NATIVE_ATTRIBS_ARB = 34990;
    public static final int GL_MAX_PROGRAM_NATIVE_ATTRIBS_ARB = 34991;
    public static final int GL_PROGRAM_ADDRESS_REGISTERS_ARB = 34992;
    public static final int GL_MAX_PROGRAM_ADDRESS_REGISTERS_ARB = 34993;
    public static final int GL_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 34994;
    public static final int GL_MAX_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 34995;
    public static final int GL_MAX_PROGRAM_LOCAL_PARAMETERS_ARB = 34996;
    public static final int GL_MAX_PROGRAM_ENV_PARAMETERS_ARB = 34997;
    public static final int GL_PROGRAM_UNDER_NATIVE_LIMITS_ARB = 34998;
    public static final int GL_PROGRAM_STRING_ARB = 34344;
    public static final int GL_PROGRAM_ERROR_POSITION_ARB = 34379;
    public static final int GL_CURRENT_MATRIX_ARB = 34369;
    public static final int GL_TRANSPOSE_CURRENT_MATRIX_ARB = 34999;
    public static final int GL_CURRENT_MATRIX_STACK_DEPTH_ARB = 34368;
    public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 34921;
    public static final int GL_MAX_PROGRAM_MATRICES_ARB = 34351;
    public static final int GL_MAX_PROGRAM_MATRIX_STACK_DEPTH_ARB = 34350;
    public static final int GL_PROGRAM_ERROR_STRING_ARB = 34932;
    public static final int GL_MATRIX0_ARB = 35008;
    public static final int GL_MATRIX1_ARB = 35009;
    public static final int GL_MATRIX2_ARB = 35010;
    public static final int GL_MATRIX3_ARB = 35011;
    public static final int GL_MATRIX4_ARB = 35012;
    public static final int GL_MATRIX5_ARB = 35013;
    public static final int GL_MATRIX6_ARB = 35014;
    public static final int GL_MATRIX7_ARB = 35015;
    public static final int GL_MATRIX8_ARB = 35016;
    public static final int GL_MATRIX9_ARB = 35017;
    public static final int GL_MATRIX10_ARB = 35018;
    public static final int GL_MATRIX11_ARB = 35019;
    public static final int GL_MATRIX12_ARB = 35020;
    public static final int GL_MATRIX13_ARB = 35021;
    public static final int GL_MATRIX14_ARB = 35022;
    public static final int GL_MATRIX15_ARB = 35023;
    public static final int GL_MATRIX16_ARB = 35024;
    public static final int GL_MATRIX17_ARB = 35025;
    public static final int GL_MATRIX18_ARB = 35026;
    public static final int GL_MATRIX19_ARB = 35027;
    public static final int GL_MATRIX20_ARB = 35028;
    public static final int GL_MATRIX21_ARB = 35029;
    public static final int GL_MATRIX22_ARB = 35030;
    public static final int GL_MATRIX23_ARB = 35031;
    public static final int GL_MATRIX24_ARB = 35032;
    public static final int GL_MATRIX25_ARB = 35033;
    public static final int GL_MATRIX26_ARB = 35034;
    public static final int GL_MATRIX27_ARB = 35035;
    public static final int GL_MATRIX28_ARB = 35036;
    public static final int GL_MATRIX29_ARB = 35037;
    public static final int GL_MATRIX30_ARB = 35038;
    public static final int GL_MATRIX31_ARB = 35039;

    protected ARBVertexProgram() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glVertexAttrib1sARB, gLCapabilities.glVertexAttrib1fARB, gLCapabilities.glVertexAttrib1dARB, gLCapabilities.glVertexAttrib2sARB, gLCapabilities.glVertexAttrib2fARB, gLCapabilities.glVertexAttrib2dARB, gLCapabilities.glVertexAttrib3sARB, gLCapabilities.glVertexAttrib3fARB, gLCapabilities.glVertexAttrib3dARB, gLCapabilities.glVertexAttrib4sARB, gLCapabilities.glVertexAttrib4fARB, gLCapabilities.glVertexAttrib4dARB, gLCapabilities.glVertexAttrib4NubARB, gLCapabilities.glVertexAttrib1svARB, gLCapabilities.glVertexAttrib1fvARB, gLCapabilities.glVertexAttrib1dvARB, gLCapabilities.glVertexAttrib2svARB, gLCapabilities.glVertexAttrib2fvARB, gLCapabilities.glVertexAttrib2dvARB, gLCapabilities.glVertexAttrib3svARB, gLCapabilities.glVertexAttrib3fvARB, gLCapabilities.glVertexAttrib3dvARB, gLCapabilities.glVertexAttrib4fvARB, gLCapabilities.glVertexAttrib4bvARB, gLCapabilities.glVertexAttrib4svARB, gLCapabilities.glVertexAttrib4ivARB, gLCapabilities.glVertexAttrib4ubvARB, gLCapabilities.glVertexAttrib4usvARB, gLCapabilities.glVertexAttrib4uivARB, gLCapabilities.glVertexAttrib4dvARB, gLCapabilities.glVertexAttrib4NbvARB, gLCapabilities.glVertexAttrib4NsvARB, gLCapabilities.glVertexAttrib4NivARB, gLCapabilities.glVertexAttrib4NubvARB, gLCapabilities.glVertexAttrib4NusvARB, gLCapabilities.glVertexAttrib4NuivARB, gLCapabilities.glVertexAttribPointerARB, gLCapabilities.glEnableVertexAttribArrayARB, gLCapabilities.glDisableVertexAttribArrayARB, gLCapabilities.glProgramStringARB, gLCapabilities.glBindProgramARB, gLCapabilities.glDeleteProgramsARB, gLCapabilities.glGenProgramsARB, gLCapabilities.glProgramEnvParameter4dARB, gLCapabilities.glProgramEnvParameter4dvARB, gLCapabilities.glProgramEnvParameter4fARB, gLCapabilities.glProgramEnvParameter4fvARB, gLCapabilities.glProgramLocalParameter4dARB, gLCapabilities.glProgramLocalParameter4dvARB, gLCapabilities.glProgramLocalParameter4fARB, gLCapabilities.glProgramLocalParameter4fvARB, gLCapabilities.glGetProgramEnvParameterfvARB, gLCapabilities.glGetProgramEnvParameterdvARB, gLCapabilities.glGetProgramLocalParameterfvARB, gLCapabilities.glGetProgramLocalParameterdvARB, gLCapabilities.glGetProgramivARB, gLCapabilities.glGetProgramStringARB, gLCapabilities.glGetVertexAttribfvARB, gLCapabilities.glGetVertexAttribdvARB, gLCapabilities.glGetVertexAttribivARB, gLCapabilities.glGetVertexAttribPointervARB, gLCapabilities.glIsProgramARB);
    }

    public static void glVertexAttrib1sARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort") short s) {
        ARBVertexShader.glVertexAttrib1sARB(n, s);
    }

    public static void glVertexAttrib1fARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat") float f) {
        ARBVertexShader.glVertexAttrib1fARB(n, f);
    }

    public static void glVertexAttrib1dARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d) {
        ARBVertexShader.glVertexAttrib1dARB(n, d);
    }

    public static void glVertexAttrib2sARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort") short s, @NativeType(value="GLshort") short s2) {
        ARBVertexShader.glVertexAttrib2sARB(n, s, s2);
    }

    public static void glVertexAttrib2fARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2) {
        ARBVertexShader.glVertexAttrib2fARB(n, f, f2);
    }

    public static void glVertexAttrib2dARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2) {
        ARBVertexShader.glVertexAttrib2dARB(n, d, d2);
    }

    public static void glVertexAttrib3sARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort") short s, @NativeType(value="GLshort") short s2, @NativeType(value="GLshort") short s3) {
        ARBVertexShader.glVertexAttrib3sARB(n, s, s2, s3);
    }

    public static void glVertexAttrib3fARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3) {
        ARBVertexShader.glVertexAttrib3fARB(n, f, f2, f3);
    }

    public static void glVertexAttrib3dARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLdouble") double d3) {
        ARBVertexShader.glVertexAttrib3dARB(n, d, d2, d3);
    }

    public static void glVertexAttrib4sARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort") short s, @NativeType(value="GLshort") short s2, @NativeType(value="GLshort") short s3, @NativeType(value="GLshort") short s4) {
        ARBVertexShader.glVertexAttrib4sARB(n, s, s2, s3, s4);
    }

    public static void glVertexAttrib4fARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4) {
        ARBVertexShader.glVertexAttrib4fARB(n, f, f2, f3, f4);
    }

    public static void glVertexAttrib4dARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLdouble") double d3, @NativeType(value="GLdouble") double d4) {
        ARBVertexShader.glVertexAttrib4dARB(n, d, d2, d3, d4);
    }

    public static void glVertexAttrib4NubARB(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte") byte by, @NativeType(value="GLubyte") byte by2, @NativeType(value="GLubyte") byte by3, @NativeType(value="GLubyte") byte by4) {
        ARBVertexShader.glVertexAttrib4NubARB(n, by, by2, by3, by4);
    }

    public static void nglVertexAttrib1svARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib1svARB(n, l);
    }

    public static void glVertexAttrib1svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib1svARB(n, shortBuffer);
    }

    public static void nglVertexAttrib1fvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib1fvARB(n, l);
    }

    public static void glVertexAttrib1fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBVertexShader.glVertexAttrib1fvARB(n, floatBuffer);
    }

    public static void nglVertexAttrib1dvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib1dvARB(n, l);
    }

    public static void glVertexAttrib1dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        ARBVertexShader.glVertexAttrib1dvARB(n, doubleBuffer);
    }

    public static void nglVertexAttrib2svARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib2svARB(n, l);
    }

    public static void glVertexAttrib2svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib2svARB(n, shortBuffer);
    }

    public static void nglVertexAttrib2fvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib2fvARB(n, l);
    }

    public static void glVertexAttrib2fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBVertexShader.glVertexAttrib2fvARB(n, floatBuffer);
    }

    public static void nglVertexAttrib2dvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib2dvARB(n, l);
    }

    public static void glVertexAttrib2dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        ARBVertexShader.glVertexAttrib2dvARB(n, doubleBuffer);
    }

    public static void nglVertexAttrib3svARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib3svARB(n, l);
    }

    public static void glVertexAttrib3svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib3svARB(n, shortBuffer);
    }

    public static void nglVertexAttrib3fvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib3fvARB(n, l);
    }

    public static void glVertexAttrib3fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBVertexShader.glVertexAttrib3fvARB(n, floatBuffer);
    }

    public static void nglVertexAttrib3dvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib3dvARB(n, l);
    }

    public static void glVertexAttrib3dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        ARBVertexShader.glVertexAttrib3dvARB(n, doubleBuffer);
    }

    public static void nglVertexAttrib4fvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4fvARB(n, l);
    }

    public static void glVertexAttrib4fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBVertexShader.glVertexAttrib4fvARB(n, floatBuffer);
    }

    public static void nglVertexAttrib4bvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4bvARB(n, l);
    }

    public static void glVertexAttrib4bvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        ARBVertexShader.glVertexAttrib4bvARB(n, byteBuffer);
    }

    public static void nglVertexAttrib4svARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4svARB(n, l);
    }

    public static void glVertexAttrib4svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib4svARB(n, shortBuffer);
    }

    public static void nglVertexAttrib4ivARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4ivARB(n, l);
    }

    public static void glVertexAttrib4ivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        ARBVertexShader.glVertexAttrib4ivARB(n, intBuffer);
    }

    public static void nglVertexAttrib4ubvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4ubvARB(n, l);
    }

    public static void glVertexAttrib4ubvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        ARBVertexShader.glVertexAttrib4ubvARB(n, byteBuffer);
    }

    public static void nglVertexAttrib4usvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4usvARB(n, l);
    }

    public static void glVertexAttrib4usvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib4usvARB(n, shortBuffer);
    }

    public static void nglVertexAttrib4uivARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4uivARB(n, l);
    }

    public static void glVertexAttrib4uivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        ARBVertexShader.glVertexAttrib4uivARB(n, intBuffer);
    }

    public static void nglVertexAttrib4dvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4dvARB(n, l);
    }

    public static void glVertexAttrib4dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        ARBVertexShader.glVertexAttrib4dvARB(n, doubleBuffer);
    }

    public static void nglVertexAttrib4NbvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4NbvARB(n, l);
    }

    public static void glVertexAttrib4NbvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        ARBVertexShader.glVertexAttrib4NbvARB(n, byteBuffer);
    }

    public static void nglVertexAttrib4NsvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4NsvARB(n, l);
    }

    public static void glVertexAttrib4NsvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib4NsvARB(n, shortBuffer);
    }

    public static void nglVertexAttrib4NivARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4NivARB(n, l);
    }

    public static void glVertexAttrib4NivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        ARBVertexShader.glVertexAttrib4NivARB(n, intBuffer);
    }

    public static void nglVertexAttrib4NubvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4NubvARB(n, l);
    }

    public static void glVertexAttrib4NubvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        ARBVertexShader.glVertexAttrib4NubvARB(n, byteBuffer);
    }

    public static void nglVertexAttrib4NusvARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4NusvARB(n, l);
    }

    public static void glVertexAttrib4NusvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttrib4NusvARB(n, shortBuffer);
    }

    public static void nglVertexAttrib4NuivARB(int n, long l) {
        ARBVertexShader.nglVertexAttrib4NuivARB(n, l);
    }

    public static void glVertexAttrib4NuivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        ARBVertexShader.glVertexAttrib4NuivARB(n, intBuffer);
    }

    public static void nglVertexAttribPointerARB(int n, int n2, int n3, boolean bl, int n4, long l) {
        ARBVertexShader.nglVertexAttribPointerARB(n, n2, n3, bl, n4, l);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, byteBuffer);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, l);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, shortBuffer);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, intBuffer);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, floatBuffer);
    }

    public static void glEnableVertexAttribArrayARB(@NativeType(value="GLuint") int n) {
        ARBVertexShader.glEnableVertexAttribArrayARB(n);
    }

    public static void glDisableVertexAttribArrayARB(@NativeType(value="GLuint") int n) {
        ARBVertexShader.glDisableVertexAttribArrayARB(n);
    }

    public static native void nglProgramStringARB(int var0, int var1, int var2, long var3);

    public static void glProgramStringARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBVertexProgram.nglProgramStringARB(n, n2, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void glBindProgramARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteProgramsARB(int var0, long var1);

    public static void glDeleteProgramsARB(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        ARBVertexProgram.nglDeleteProgramsARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGenProgramsARB(int var0, long var1);

    public static void glGenProgramsARB(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        ARBVertexProgram.nglGenProgramsARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenProgramsARB() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBVertexProgram.nglGenProgramsARB(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void glProgramEnvParameter4dARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6, @NativeType(value="GLdouble") double var8);

    public static native void nglProgramEnvParameter4dvARB(int var0, int var1, long var2);

    public static void glProgramEnvParameter4dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBVertexProgram.nglProgramEnvParameter4dvARB(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glProgramEnvParameter4fARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5);

    public static native void nglProgramEnvParameter4fvARB(int var0, int var1, long var2);

    public static void glProgramEnvParameter4fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBVertexProgram.nglProgramEnvParameter4fvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glProgramLocalParameter4dARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6, @NativeType(value="GLdouble") double var8);

    public static native void nglProgramLocalParameter4dvARB(int var0, int var1, long var2);

    public static void glProgramLocalParameter4dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBVertexProgram.nglProgramLocalParameter4dvARB(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glProgramLocalParameter4fARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5);

    public static native void nglProgramLocalParameter4fvARB(int var0, int var1, long var2);

    public static void glProgramLocalParameter4fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBVertexProgram.nglProgramLocalParameter4fvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetProgramEnvParameterfvARB(int var0, int var1, long var2);

    public static void glGetProgramEnvParameterfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBVertexProgram.nglGetProgramEnvParameterfvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetProgramEnvParameterdvARB(int var0, int var1, long var2);

    public static void glGetProgramEnvParameterdvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBVertexProgram.nglGetProgramEnvParameterdvARB(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetProgramLocalParameterfvARB(int var0, int var1, long var2);

    public static void glGetProgramLocalParameterfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBVertexProgram.nglGetProgramLocalParameterfvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetProgramLocalParameterdvARB(int var0, int var1, long var2);

    public static void glGetProgramLocalParameterdvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBVertexProgram.nglGetProgramLocalParameterdvARB(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetProgramivARB(int var0, int var1, long var2);

    public static void glGetProgramivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBVertexProgram.nglGetProgramivARB(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetProgramiARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBVertexProgram.nglGetProgramivARB(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetProgramStringARB(int var0, int var1, long var2);

    public static void glGetProgramStringARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.check((Buffer)byteBuffer, ARBVertexProgram.glGetProgramiARB(n, 34343));
        }
        ARBVertexProgram.nglGetProgramStringARB(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void nglGetVertexAttribfvARB(int n, int n2, long l) {
        ARBVertexShader.nglGetVertexAttribfvARB(n, n2, l);
    }

    public static void glGetVertexAttribfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        ARBVertexShader.glGetVertexAttribfvARB(n, n2, floatBuffer);
    }

    public static void nglGetVertexAttribdvARB(int n, int n2, long l) {
        ARBVertexShader.nglGetVertexAttribdvARB(n, n2, l);
    }

    public static void glGetVertexAttribdvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        ARBVertexShader.glGetVertexAttribdvARB(n, n2, doubleBuffer);
    }

    public static void nglGetVertexAttribivARB(int n, int n2, long l) {
        ARBVertexShader.nglGetVertexAttribivARB(n, n2, l);
    }

    public static void glGetVertexAttribivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        ARBVertexShader.glGetVertexAttribivARB(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetVertexAttribiARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return ARBVertexShader.glGetVertexAttribiARB(n, n2);
    }

    public static void nglGetVertexAttribPointervARB(int n, int n2, long l) {
        ARBVertexShader.nglGetVertexAttribPointervARB(n, n2, l);
    }

    public static void glGetVertexAttribPointervARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        ARBVertexShader.glGetVertexAttribPointervARB(n, n2, pointerBuffer);
    }

    @NativeType(value="void")
    public static long glGetVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return ARBVertexShader.glGetVertexAttribPointerARB(n, n2);
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsProgramARB(@NativeType(value="GLuint") int var0);

    public static void glVertexAttrib1svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib1svARB(n, sArray);
    }

    public static void glVertexAttrib1fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        ARBVertexShader.glVertexAttrib1fvARB(n, fArray);
    }

    public static void glVertexAttrib1dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        ARBVertexShader.glVertexAttrib1dvARB(n, dArray);
    }

    public static void glVertexAttrib2svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib2svARB(n, sArray);
    }

    public static void glVertexAttrib2fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        ARBVertexShader.glVertexAttrib2fvARB(n, fArray);
    }

    public static void glVertexAttrib2dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        ARBVertexShader.glVertexAttrib2dvARB(n, dArray);
    }

    public static void glVertexAttrib3svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib3svARB(n, sArray);
    }

    public static void glVertexAttrib3fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        ARBVertexShader.glVertexAttrib3fvARB(n, fArray);
    }

    public static void glVertexAttrib3dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        ARBVertexShader.glVertexAttrib3dvARB(n, dArray);
    }

    public static void glVertexAttrib4fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        ARBVertexShader.glVertexAttrib4fvARB(n, fArray);
    }

    public static void glVertexAttrib4svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib4svARB(n, sArray);
    }

    public static void glVertexAttrib4ivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        ARBVertexShader.glVertexAttrib4ivARB(n, nArray);
    }

    public static void glVertexAttrib4usvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib4usvARB(n, sArray);
    }

    public static void glVertexAttrib4uivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        ARBVertexShader.glVertexAttrib4uivARB(n, nArray);
    }

    public static void glVertexAttrib4dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        ARBVertexShader.glVertexAttrib4dvARB(n, dArray);
    }

    public static void glVertexAttrib4NsvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib4NsvARB(n, sArray);
    }

    public static void glVertexAttrib4NivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        ARBVertexShader.glVertexAttrib4NivARB(n, nArray);
    }

    public static void glVertexAttrib4NusvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") short[] sArray) {
        ARBVertexShader.glVertexAttrib4NusvARB(n, sArray);
    }

    public static void glVertexAttrib4NuivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        ARBVertexShader.glVertexAttrib4NuivARB(n, nArray);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") short[] sArray) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, sArray);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") int[] nArray) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, nArray);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") float[] fArray) {
        ARBVertexShader.glVertexAttribPointerARB(n, n2, n3, bl, n4, fArray);
    }

    public static void glDeleteProgramsARB(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteProgramsARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenProgramsARB(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenProgramsARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glProgramEnvParameter4dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramEnvParameter4dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glProgramEnvParameter4fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramEnvParameter4fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glProgramLocalParameter4dvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramLocalParameter4dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glProgramLocalParameter4fvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramLocalParameter4fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetProgramEnvParameterfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetProgramEnvParameterfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetProgramEnvParameterdvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetProgramEnvParameterdvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetProgramLocalParameterfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetProgramLocalParameterfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetProgramLocalParameterdvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetProgramLocalParameterdvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetProgramivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetProgramivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetVertexAttribfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        ARBVertexShader.glGetVertexAttribfvARB(n, n2, fArray);
    }

    public static void glGetVertexAttribdvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        ARBVertexShader.glGetVertexAttribdvARB(n, n2, dArray);
    }

    public static void glGetVertexAttribivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        ARBVertexShader.glGetVertexAttribivARB(n, n2, nArray);
    }

    static {
        GL.initialize();
    }
}

