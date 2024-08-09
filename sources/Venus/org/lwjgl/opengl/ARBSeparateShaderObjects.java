/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL41C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBSeparateShaderObjects {
    public static final int GL_VERTEX_SHADER_BIT = 1;
    public static final int GL_FRAGMENT_SHADER_BIT = 2;
    public static final int GL_GEOMETRY_SHADER_BIT = 4;
    public static final int GL_TESS_CONTROL_SHADER_BIT = 8;
    public static final int GL_TESS_EVALUATION_SHADER_BIT = 16;
    public static final int GL_ALL_SHADER_BITS = -1;
    public static final int GL_PROGRAM_SEPARABLE = 33368;
    public static final int GL_ACTIVE_PROGRAM = 33369;
    public static final int GL_PROGRAM_PIPELINE_BINDING = 33370;

    protected ARBSeparateShaderObjects() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glUseProgramStages, gLCapabilities.glActiveShaderProgram, gLCapabilities.glCreateShaderProgramv, gLCapabilities.glBindProgramPipeline, gLCapabilities.glDeleteProgramPipelines, gLCapabilities.glGenProgramPipelines, gLCapabilities.glIsProgramPipeline, gLCapabilities.glProgramParameteri, gLCapabilities.glGetProgramPipelineiv, gLCapabilities.glProgramUniform1i, gLCapabilities.glProgramUniform2i, gLCapabilities.glProgramUniform3i, gLCapabilities.glProgramUniform4i, gLCapabilities.glProgramUniform1ui, gLCapabilities.glProgramUniform2ui, gLCapabilities.glProgramUniform3ui, gLCapabilities.glProgramUniform4ui, gLCapabilities.glProgramUniform1f, gLCapabilities.glProgramUniform2f, gLCapabilities.glProgramUniform3f, gLCapabilities.glProgramUniform4f, gLCapabilities.glProgramUniform1d, gLCapabilities.glProgramUniform2d, gLCapabilities.glProgramUniform3d, gLCapabilities.glProgramUniform4d, gLCapabilities.glProgramUniform1iv, gLCapabilities.glProgramUniform2iv, gLCapabilities.glProgramUniform3iv, gLCapabilities.glProgramUniform4iv, gLCapabilities.glProgramUniform1uiv, gLCapabilities.glProgramUniform2uiv, gLCapabilities.glProgramUniform3uiv, gLCapabilities.glProgramUniform4uiv, gLCapabilities.glProgramUniform1fv, gLCapabilities.glProgramUniform2fv, gLCapabilities.glProgramUniform3fv, gLCapabilities.glProgramUniform4fv, gLCapabilities.glProgramUniform1dv, gLCapabilities.glProgramUniform2dv, gLCapabilities.glProgramUniform3dv, gLCapabilities.glProgramUniform4dv, gLCapabilities.glProgramUniformMatrix2fv, gLCapabilities.glProgramUniformMatrix3fv, gLCapabilities.glProgramUniformMatrix4fv, gLCapabilities.glProgramUniformMatrix2dv, gLCapabilities.glProgramUniformMatrix3dv, gLCapabilities.glProgramUniformMatrix4dv, gLCapabilities.glProgramUniformMatrix2x3fv, gLCapabilities.glProgramUniformMatrix3x2fv, gLCapabilities.glProgramUniformMatrix2x4fv, gLCapabilities.glProgramUniformMatrix4x2fv, gLCapabilities.glProgramUniformMatrix3x4fv, gLCapabilities.glProgramUniformMatrix4x3fv, gLCapabilities.glProgramUniformMatrix2x3dv, gLCapabilities.glProgramUniformMatrix3x2dv, gLCapabilities.glProgramUniformMatrix2x4dv, gLCapabilities.glProgramUniformMatrix4x2dv, gLCapabilities.glProgramUniformMatrix3x4dv, gLCapabilities.glProgramUniformMatrix4x3dv, gLCapabilities.glValidateProgramPipeline, gLCapabilities.glGetProgramPipelineInfoLog);
    }

    public static void glUseProgramStages(@NativeType(value="GLuint") int n, @NativeType(value="GLbitfield") int n2, @NativeType(value="GLuint") int n3) {
        GL41C.glUseProgramStages(n, n2, n3);
    }

    public static void glActiveShaderProgram(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL41C.glActiveShaderProgram(n, n2);
    }

    public static int nglCreateShaderProgramv(int n, int n2, long l) {
        return GL41C.nglCreateShaderProgramv(n, n2, l);
    }

    @NativeType(value="GLuint")
    public static int glCreateShaderProgramv(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const **") PointerBuffer pointerBuffer) {
        return GL41C.glCreateShaderProgramv(n, pointerBuffer);
    }

    @NativeType(value="GLuint")
    public static int glCreateShaderProgramv(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const **") CharSequence ... charSequenceArray) {
        return GL41C.glCreateShaderProgramv(n, charSequenceArray);
    }

    @NativeType(value="GLuint")
    public static int glCreateShaderProgramv(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const **") CharSequence charSequence) {
        return GL41C.glCreateShaderProgramv(n, charSequence);
    }

    public static void glBindProgramPipeline(@NativeType(value="GLuint") int n) {
        GL41C.glBindProgramPipeline(n);
    }

    public static void nglDeleteProgramPipelines(int n, long l) {
        GL41C.nglDeleteProgramPipelines(n, l);
    }

    public static void glDeleteProgramPipelines(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.glDeleteProgramPipelines(intBuffer);
    }

    public static void glDeleteProgramPipelines(@NativeType(value="GLuint const *") int n) {
        GL41C.glDeleteProgramPipelines(n);
    }

    public static void nglGenProgramPipelines(int n, long l) {
        GL41C.nglGenProgramPipelines(n, l);
    }

    public static void glGenProgramPipelines(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL41C.glGenProgramPipelines(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenProgramPipelines() {
        return GL41C.glGenProgramPipelines();
    }

    @NativeType(value="GLboolean")
    public static boolean glIsProgramPipeline(@NativeType(value="GLuint") int n) {
        return GL41C.glIsProgramPipeline(n);
    }

    public static void glProgramParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL41C.glProgramParameteri(n, n2, n3);
    }

    public static void nglGetProgramPipelineiv(int n, int n2, long l) {
        GL41C.nglGetProgramPipelineiv(n, n2, l);
    }

    public static void glGetProgramPipelineiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL41C.glGetProgramPipelineiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetProgramPipelinei(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL41C.glGetProgramPipelinei(n, n2);
    }

    public static void glProgramUniform1i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3) {
        GL41C.glProgramUniform1i(n, n2, n3);
    }

    public static void glProgramUniform2i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4) {
        GL41C.glProgramUniform2i(n, n2, n3, n4);
    }

    public static void glProgramUniform3i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5) {
        GL41C.glProgramUniform3i(n, n2, n3, n4, n5);
    }

    public static void glProgramUniform4i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6) {
        GL41C.glProgramUniform4i(n, n2, n3, n4, n5, n6);
    }

    public static void glProgramUniform1ui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint") int n3) {
        GL41C.glProgramUniform1ui(n, n2, n3);
    }

    public static void glProgramUniform2ui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint") int n4) {
        GL41C.glProgramUniform2ui(n, n2, n3, n4);
    }

    public static void glProgramUniform3ui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLuint") int n5) {
        GL41C.glProgramUniform3ui(n, n2, n3, n4, n5);
    }

    public static void glProgramUniform4ui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLuint") int n5, @NativeType(value="GLuint") int n6) {
        GL41C.glProgramUniform4ui(n, n2, n3, n4, n5, n6);
    }

    public static void glProgramUniform1f(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat") float f) {
        GL41C.glProgramUniform1f(n, n2, f);
    }

    public static void glProgramUniform2f(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2) {
        GL41C.glProgramUniform2f(n, n2, f, f2);
    }

    public static void glProgramUniform3f(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3) {
        GL41C.glProgramUniform3f(n, n2, f, f2, f3);
    }

    public static void glProgramUniform4f(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4) {
        GL41C.glProgramUniform4f(n, n2, f, f2, f3, f4);
    }

    public static void glProgramUniform1d(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble") double d) {
        GL41C.glProgramUniform1d(n, n2, d);
    }

    public static void glProgramUniform2d(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2) {
        GL41C.glProgramUniform2d(n, n2, d, d2);
    }

    public static void glProgramUniform3d(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLdouble") double d3) {
        GL41C.glProgramUniform3d(n, n2, d, d2, d3);
    }

    public static void glProgramUniform4d(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLdouble") double d3, @NativeType(value="GLdouble") double d4) {
        GL41C.glProgramUniform4d(n, n2, d, d2, d3, d4);
    }

    public static void nglProgramUniform1iv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform1iv(n, n2, n3, l);
    }

    public static void glProgramUniform1iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform1iv(n, n2, intBuffer);
    }

    public static void nglProgramUniform2iv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform2iv(n, n2, n3, l);
    }

    public static void glProgramUniform2iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform2iv(n, n2, intBuffer);
    }

    public static void nglProgramUniform3iv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform3iv(n, n2, n3, l);
    }

    public static void glProgramUniform3iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform3iv(n, n2, intBuffer);
    }

    public static void nglProgramUniform4iv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform4iv(n, n2, n3, l);
    }

    public static void glProgramUniform4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform4iv(n, n2, intBuffer);
    }

    public static void nglProgramUniform1uiv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform1uiv(n, n2, n3, l);
    }

    public static void glProgramUniform1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform1uiv(n, n2, intBuffer);
    }

    public static void nglProgramUniform2uiv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform2uiv(n, n2, n3, l);
    }

    public static void glProgramUniform2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform2uiv(n, n2, intBuffer);
    }

    public static void nglProgramUniform3uiv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform3uiv(n, n2, n3, l);
    }

    public static void glProgramUniform3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform3uiv(n, n2, intBuffer);
    }

    public static void nglProgramUniform4uiv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform4uiv(n, n2, n3, l);
    }

    public static void glProgramUniform4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.glProgramUniform4uiv(n, n2, intBuffer);
    }

    public static void nglProgramUniform1fv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform1fv(n, n2, n3, l);
    }

    public static void glProgramUniform1fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniform1fv(n, n2, floatBuffer);
    }

    public static void nglProgramUniform2fv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform2fv(n, n2, n3, l);
    }

    public static void glProgramUniform2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniform2fv(n, n2, floatBuffer);
    }

    public static void nglProgramUniform3fv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform3fv(n, n2, n3, l);
    }

    public static void glProgramUniform3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniform3fv(n, n2, floatBuffer);
    }

    public static void nglProgramUniform4fv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform4fv(n, n2, n3, l);
    }

    public static void glProgramUniform4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniform4fv(n, n2, floatBuffer);
    }

    public static void nglProgramUniform1dv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform1dv(n, n2, n3, l);
    }

    public static void glProgramUniform1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniform1dv(n, n2, doubleBuffer);
    }

    public static void nglProgramUniform2dv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform2dv(n, n2, n3, l);
    }

    public static void glProgramUniform2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniform2dv(n, n2, doubleBuffer);
    }

    public static void nglProgramUniform3dv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform3dv(n, n2, n3, l);
    }

    public static void glProgramUniform3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniform3dv(n, n2, doubleBuffer);
    }

    public static void nglProgramUniform4dv(int n, int n2, int n3, long l) {
        GL41C.nglProgramUniform4dv(n, n2, n3, l);
    }

    public static void glProgramUniform4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniform4dv(n, n2, doubleBuffer);
    }

    public static void nglProgramUniformMatrix2fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix2fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix2fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix3fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix3fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix3fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix4fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix4fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix4fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix2dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix2dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix2dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix3dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix3dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix3dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix4dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix4dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix4dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix2x3fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix2x3fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix2x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix2x3fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix3x2fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix3x2fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix3x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix3x2fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix2x4fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix2x4fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix2x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix2x4fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix4x2fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix4x2fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix4x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix4x2fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix3x4fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix3x4fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix3x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix3x4fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix4x3fv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix4x3fv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix4x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.glProgramUniformMatrix4x3fv(n, n2, bl, floatBuffer);
    }

    public static void nglProgramUniformMatrix2x3dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix2x3dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix2x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix2x3dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix3x2dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix3x2dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix3x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix3x2dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix2x4dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix2x4dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix2x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix2x4dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix4x2dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix4x2dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix4x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix4x2dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix3x4dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix3x4dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix3x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix3x4dv(n, n2, bl, doubleBuffer);
    }

    public static void nglProgramUniformMatrix4x3dv(int n, int n2, int n3, boolean bl, long l) {
        GL41C.nglProgramUniformMatrix4x3dv(n, n2, n3, bl, l);
    }

    public static void glProgramUniformMatrix4x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.glProgramUniformMatrix4x3dv(n, n2, bl, doubleBuffer);
    }

    public static void glValidateProgramPipeline(@NativeType(value="GLuint") int n) {
        GL41C.glValidateProgramPipeline(n);
    }

    public static void nglGetProgramPipelineInfoLog(int n, int n2, long l, long l2) {
        GL41C.nglGetProgramPipelineInfoLog(n, n2, l, l2);
    }

    public static void glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL41C.glGetProgramPipelineInfoLog(n, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2) {
        return GL41C.glGetProgramPipelineInfoLog(n, n2);
    }

    @NativeType(value="void")
    public static String glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n) {
        return ARBSeparateShaderObjects.glGetProgramPipelineInfoLog(n, ARBSeparateShaderObjects.glGetProgramPipelinei(n, 35716));
    }

    public static void glDeleteProgramPipelines(@NativeType(value="GLuint const *") int[] nArray) {
        GL41C.glDeleteProgramPipelines(nArray);
    }

    public static void glGenProgramPipelines(@NativeType(value="GLuint *") int[] nArray) {
        GL41C.glGenProgramPipelines(nArray);
    }

    public static void glGetProgramPipelineiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL41C.glGetProgramPipelineiv(n, n2, nArray);
    }

    public static void glProgramUniform1iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL41C.glProgramUniform1iv(n, n2, nArray);
    }

    public static void glProgramUniform2iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL41C.glProgramUniform2iv(n, n2, nArray);
    }

    public static void glProgramUniform3iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL41C.glProgramUniform3iv(n, n2, nArray);
    }

    public static void glProgramUniform4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL41C.glProgramUniform4iv(n, n2, nArray);
    }

    public static void glProgramUniform1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL41C.glProgramUniform1uiv(n, n2, nArray);
    }

    public static void glProgramUniform2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL41C.glProgramUniform2uiv(n, n2, nArray);
    }

    public static void glProgramUniform3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL41C.glProgramUniform3uiv(n, n2, nArray);
    }

    public static void glProgramUniform4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL41C.glProgramUniform4uiv(n, n2, nArray);
    }

    public static void glProgramUniform1fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniform1fv(n, n2, fArray);
    }

    public static void glProgramUniform2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniform2fv(n, n2, fArray);
    }

    public static void glProgramUniform3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniform3fv(n, n2, fArray);
    }

    public static void glProgramUniform4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniform4fv(n, n2, fArray);
    }

    public static void glProgramUniform1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniform1dv(n, n2, dArray);
    }

    public static void glProgramUniform2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniform2dv(n, n2, dArray);
    }

    public static void glProgramUniform3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniform3dv(n, n2, dArray);
    }

    public static void glProgramUniform4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniform4dv(n, n2, dArray);
    }

    public static void glProgramUniformMatrix2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix2fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix3fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix4fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix2dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix3dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix4dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix2x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix2x3fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix3x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix3x2fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix2x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix2x4fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix4x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix4x2fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix3x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix3x4fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix4x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL41C.glProgramUniformMatrix4x3fv(n, n2, bl, fArray);
    }

    public static void glProgramUniformMatrix2x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix2x3dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix3x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix3x2dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix2x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix2x4dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix4x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix4x2dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix3x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix3x4dv(n, n2, bl, dArray);
    }

    public static void glProgramUniformMatrix4x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        GL41C.glProgramUniformMatrix4x3dv(n, n2, bl, dArray);
    }

    public static void glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL41C.glGetProgramPipelineInfoLog(n, nArray, byteBuffer);
    }

    static {
        GL.initialize();
    }
}

