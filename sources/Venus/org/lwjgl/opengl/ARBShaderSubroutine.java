/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL40C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBShaderSubroutine {
    public static final int GL_ACTIVE_SUBROUTINES = 36325;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
    public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
    public static final int GL_MAX_SUBROUTINES = 36327;
    public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
    public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
    public static final int GL_COMPATIBLE_SUBROUTINES = 36427;

    protected ARBShaderSubroutine() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetSubroutineUniformLocation, gLCapabilities.glGetSubroutineIndex, gLCapabilities.glGetActiveSubroutineUniformiv, gLCapabilities.glGetActiveSubroutineUniformName, gLCapabilities.glGetActiveSubroutineName, gLCapabilities.glUniformSubroutinesuiv, gLCapabilities.glGetUniformSubroutineuiv, gLCapabilities.glGetProgramStageiv);
    }

    public static int nglGetSubroutineUniformLocation(int n, int n2, long l) {
        return GL40C.nglGetSubroutineUniformLocation(n, n2, l);
    }

    @NativeType(value="GLint")
    public static int glGetSubroutineUniformLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL40C.glGetSubroutineUniformLocation(n, n2, byteBuffer);
    }

    @NativeType(value="GLint")
    public static int glGetSubroutineUniformLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL40C.glGetSubroutineUniformLocation(n, n2, charSequence);
    }

    public static int nglGetSubroutineIndex(int n, int n2, long l) {
        return GL40C.nglGetSubroutineIndex(n, n2, l);
    }

    @NativeType(value="GLuint")
    public static int glGetSubroutineIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL40C.glGetSubroutineIndex(n, n2, byteBuffer);
    }

    @NativeType(value="GLuint")
    public static int glGetSubroutineIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL40C.glGetSubroutineIndex(n, n2, charSequence);
    }

    public static void nglGetActiveSubroutineUniformiv(int n, int n2, int n3, int n4, long l) {
        GL40C.nglGetActiveSubroutineUniformiv(n, n2, n3, n4, l);
    }

    public static void glGetActiveSubroutineUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL40C.glGetActiveSubroutineUniformiv(n, n2, n3, n4, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetActiveSubroutineUniformi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4) {
        return GL40C.glGetActiveSubroutineUniformi(n, n2, n3, n4);
    }

    public static void nglGetActiveSubroutineUniformName(int n, int n2, int n3, int n4, long l, long l2) {
        GL40C.nglGetActiveSubroutineUniformName(n, n2, n3, n4, l, l2);
    }

    public static void glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL40C.glGetActiveSubroutineUniformName(n, n2, n3, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4) {
        return GL40C.glGetActiveSubroutineUniformName(n, n2, n3, n4);
    }

    @NativeType(value="void")
    public static String glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return ARBShaderSubroutine.glGetActiveSubroutineUniformName(n, n2, n3, ARBShaderSubroutine.glGetActiveSubroutineUniformi(n, n2, n3, 35385));
    }

    public static void nglGetActiveSubroutineName(int n, int n2, int n3, int n4, long l, long l2) {
        GL40C.nglGetActiveSubroutineName(n, n2, n3, n4, l, l2);
    }

    public static void glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL40C.glGetActiveSubroutineName(n, n2, n3, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4) {
        return GL40C.glGetActiveSubroutineName(n, n2, n3, n4);
    }

    @NativeType(value="void")
    public static String glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return ARBShaderSubroutine.glGetActiveSubroutineName(n, n2, n3, ARBShaderSubroutine.glGetProgramStagei(n, n2, 36424));
    }

    public static void nglUniformSubroutinesuiv(int n, int n2, long l) {
        GL40C.nglUniformSubroutinesuiv(n, n2, l);
    }

    public static void glUniformSubroutinesuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL40C.glUniformSubroutinesuiv(n, intBuffer);
    }

    public static void glUniformSubroutinesui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int n2) {
        GL40C.glUniformSubroutinesui(n, n2);
    }

    public static void nglGetUniformSubroutineuiv(int n, int n2, long l) {
        GL40C.nglGetUniformSubroutineuiv(n, n2, l);
    }

    public static void glGetUniformSubroutineuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL40C.glGetUniformSubroutineuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetUniformSubroutineui(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2) {
        return GL40C.glGetUniformSubroutineui(n, n2);
    }

    public static void nglGetProgramStageiv(int n, int n2, int n3, long l) {
        GL40C.nglGetProgramStageiv(n, n2, n3, l);
    }

    public static void glGetProgramStageiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL40C.glGetProgramStageiv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetProgramStagei(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL40C.glGetProgramStagei(n, n2, n3);
    }

    public static void glGetActiveSubroutineUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") int[] nArray) {
        GL40C.glGetActiveSubroutineUniformiv(n, n2, n3, n4, nArray);
    }

    public static void glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL40C.glGetActiveSubroutineUniformName(n, n2, n3, nArray, byteBuffer);
    }

    public static void glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL40C.glGetActiveSubroutineName(n, n2, n3, nArray, byteBuffer);
    }

    public static void glUniformSubroutinesuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL40C.glUniformSubroutinesuiv(n, nArray);
    }

    public static void glGetUniformSubroutineuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL40C.glGetUniformSubroutineuiv(n, n2, nArray);
    }

    public static void glGetProgramStageiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL40C.glGetProgramStageiv(n, n2, n3, nArray);
    }

    static {
        GL.initialize();
    }
}

