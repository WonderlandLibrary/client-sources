/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL40C
extends GL33C {
    public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;
    public static final int GL_GEOMETRY_SHADER_INVOCATIONS = 34943;
    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 36442;
    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 36443;
    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 36444;
    public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 36445;
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
    public static final int GL_SAMPLE_SHADING = 35894;
    public static final int GL_MIN_SAMPLE_SHADING_VALUE = 35895;
    public static final int GL_ACTIVE_SUBROUTINES = 36325;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 36326;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 36423;
    public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 36424;
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 36425;
    public static final int GL_MAX_SUBROUTINES = 36327;
    public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 36328;
    public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 36426;
    public static final int GL_COMPATIBLE_SUBROUTINES = 36427;
    public static final int GL_PATCHES = 14;
    public static final int GL_PATCH_VERTICES = 36466;
    public static final int GL_PATCH_DEFAULT_INNER_LEVEL = 36467;
    public static final int GL_PATCH_DEFAULT_OUTER_LEVEL = 36468;
    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES = 36469;
    public static final int GL_TESS_GEN_MODE = 36470;
    public static final int GL_TESS_GEN_SPACING = 36471;
    public static final int GL_TESS_GEN_VERTEX_ORDER = 36472;
    public static final int GL_TESS_GEN_POINT_MODE = 36473;
    public static final int GL_ISOLINES = 36474;
    public static final int GL_FRACTIONAL_ODD = 36475;
    public static final int GL_FRACTIONAL_EVEN = 36476;
    public static final int GL_MAX_PATCH_VERTICES = 36477;
    public static final int GL_MAX_TESS_GEN_LEVEL = 36478;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 36479;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 36480;
    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 36481;
    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 36482;
    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 36483;
    public static final int GL_MAX_TESS_PATCH_COMPONENTS = 36484;
    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 36485;
    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 36486;
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 36489;
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 36490;
    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 34924;
    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 34925;
    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 36382;
    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 36383;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 34032;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 34033;
    public static final int GL_TESS_EVALUATION_SHADER = 36487;
    public static final int GL_TESS_CONTROL_SHADER = 36488;
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY = 36873;
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 36874;
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = 36875;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY = 36876;
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 36877;
    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY = 36878;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 36879;
    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET = 36446;
    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET = 36447;
    public static final int GL_TRANSFORM_FEEDBACK = 36386;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 36387;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 36388;
    public static final int GL_TRANSFORM_FEEDBACK_BINDING = 36389;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 36464;
    public static final int GL_MAX_VERTEX_STREAMS = 36465;

    protected GL40C() {
        throw new UnsupportedOperationException();
    }

    public static native void glBlendEquationi(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glBlendEquationSeparatei(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2);

    public static native void glBlendFunci(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2);

    public static native void glBlendFuncSeparatei(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLenum") int var4);

    public static native void nglDrawArraysIndirect(int var0, long var1);

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 16);
        }
        GL40C.nglDrawArraysIndirect(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l) {
        GL40C.nglDrawArraysIndirect(n, l);
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL40C.nglDrawArraysIndirect(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglDrawElementsIndirect(int var0, int var1, long var2);

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 20);
        }
        GL40C.nglDrawElementsIndirect(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l) {
        GL40C.nglDrawElementsIndirect(n, n2, l);
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 5);
        }
        GL40C.nglDrawElementsIndirect(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glUniform1d(@NativeType(value="GLint") int var0, @NativeType(value="GLdouble") double var1);

    public static native void glUniform2d(@NativeType(value="GLint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void glUniform3d(@NativeType(value="GLint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void glUniform4d(@NativeType(value="GLint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7);

    public static native void nglUniform1dv(int var0, int var1, long var2);

    public static void glUniform1dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniform1dv(n, doubleBuffer.remaining(), MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniform2dv(int var0, int var1, long var2);

    public static void glUniform2dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniform2dv(n, doubleBuffer.remaining() >> 1, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniform3dv(int var0, int var1, long var2);

    public static void glUniform3dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniform3dv(n, doubleBuffer.remaining() / 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniform4dv(int var0, int var1, long var2);

    public static void glUniform4dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniform4dv(n, doubleBuffer.remaining() >> 2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix2dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix2dv(n, doubleBuffer.remaining() >> 2, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix3dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix3dv(n, doubleBuffer.remaining() / 9, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix4dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix4dv(n, doubleBuffer.remaining() >> 4, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix2x3dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2x3dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix2x3dv(n, doubleBuffer.remaining() / 6, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix2x4dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2x4dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix2x4dv(n, doubleBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix3x2dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3x2dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix3x2dv(n, doubleBuffer.remaining() / 6, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix3x4dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3x4dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix3x4dv(n, doubleBuffer.remaining() / 12, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix4x2dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4x2dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix4x2dv(n, doubleBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglUniformMatrix4x3dv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4x3dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL40C.nglUniformMatrix4x3dv(n, doubleBuffer.remaining() / 12, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetUniformdv(int var0, int var1, long var2);

    public static void glGetUniformdv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL40C.nglGetUniformdv(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetUniformd(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            GL40C.nglGetUniformdv(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glMinSampleShading(@NativeType(value="GLfloat") float var0);

    public static native int nglGetSubroutineUniformLocation(int var0, int var1, long var2);

    @NativeType(value="GLint")
    public static int glGetSubroutineUniformLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return GL40C.nglGetSubroutineUniformLocation(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetSubroutineUniformLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n4 = GL40C.nglGetSubroutineUniformLocation(n, n2, l);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native int nglGetSubroutineIndex(int var0, int var1, long var2);

    @NativeType(value="GLuint")
    public static int glGetSubroutineIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return GL40C.nglGetSubroutineIndex(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLuint")
    public static int glGetSubroutineIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n4 = GL40C.nglGetSubroutineIndex(n, n2, l);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetActiveSubroutineUniformiv(int var0, int var1, int var2, int var3, long var4);

    public static void glGetActiveSubroutineUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL40C.nglGetActiveSubroutineUniformiv(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetActiveSubroutineUniformi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL40C.nglGetActiveSubroutineUniformiv(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
            int n6 = intBuffer.get(0);
            return n6;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglGetActiveSubroutineUniformName(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL40C.nglGetActiveSubroutineUniformName(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n4);
            GL40C.nglGetActiveSubroutineUniformName(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memASCII(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    @NativeType(value="void")
    public static String glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return GL40C.glGetActiveSubroutineUniformName(n, n2, n3, GL40C.glGetActiveSubroutineUniformi(n, n2, n3, 35385));
    }

    public static native void nglGetActiveSubroutineName(int var0, int var1, int var2, int var3, long var4, long var6);

    public static void glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL40C.nglGetActiveSubroutineName(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n4);
            GL40C.nglGetActiveSubroutineName(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memASCII(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    @NativeType(value="void")
    public static String glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return GL40C.glGetActiveSubroutineName(n, n2, n3, GL40C.glGetProgramStagei(n, n2, 36424));
    }

    public static native void nglUniformSubroutinesuiv(int var0, int var1, long var2);

    public static void glUniformSubroutinesuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL40C.nglUniformSubroutinesuiv(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glUniformSubroutinesui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n2);
            GL40C.nglUniformSubroutinesuiv(n, 1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetUniformSubroutineuiv(int var0, int var1, long var2);

    public static void glGetUniformSubroutineuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL40C.nglGetUniformSubroutineuiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetUniformSubroutineui(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL40C.nglGetUniformSubroutineuiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetProgramStageiv(int var0, int var1, int var2, long var3);

    public static void glGetProgramStageiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL40C.nglGetProgramStageiv(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetProgramStagei(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL40C.nglGetProgramStageiv(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void glPatchParameteri(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void nglPatchParameterfv(int var0, long var1);

    public static void glPatchParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.check((Buffer)floatBuffer, GL11.glGetInteger(36466));
        }
        GL40C.nglPatchParameterfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glBindTransformFeedback(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteTransformFeedbacks(int var0, long var1);

    public static void glDeleteTransformFeedbacks(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL40C.nglDeleteTransformFeedbacks(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteTransformFeedbacks(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            GL40C.nglDeleteTransformFeedbacks(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenTransformFeedbacks(int var0, long var1);

    public static void glGenTransformFeedbacks(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL40C.nglGenTransformFeedbacks(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenTransformFeedbacks() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL40C.nglGenTransformFeedbacks(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsTransformFeedback(@NativeType(value="GLuint") int var0);

    public static native void glPauseTransformFeedback();

    public static native void glResumeTransformFeedback();

    public static native void glDrawTransformFeedback(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDrawTransformFeedbackStream(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glBeginQueryIndexed(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glEndQueryIndexed(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglGetQueryIndexediv(int var0, int var1, int var2, long var3);

    public static void glGetQueryIndexediv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL40C.nglGetQueryIndexediv(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetQueryIndexedi(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL40C.nglGetQueryIndexediv(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static void glDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glDrawArraysIndirect;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glDrawElementsIndirect;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 5);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glUniform1dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniform1dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length, dArray, l);
    }

    public static void glUniform2dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniform2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 1, dArray, l);
    }

    public static void glUniform3dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniform3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length / 3, dArray, l);
    }

    public static void glUniform4dv(@NativeType(value="GLint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniform4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 2, dArray, l);
    }

    public static void glUniformMatrix2dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 2, bl, dArray, l);
    }

    public static void glUniformMatrix3dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length / 9, bl, dArray, l);
    }

    public static void glUniformMatrix4dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 4, bl, dArray, l);
    }

    public static void glUniformMatrix2x3dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix2x3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length / 6, bl, dArray, l);
    }

    public static void glUniformMatrix2x4dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix2x4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 3, bl, dArray, l);
    }

    public static void glUniformMatrix3x2dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix3x2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length / 6, bl, dArray, l);
    }

    public static void glUniformMatrix3x4dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix3x4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length / 12, bl, dArray, l);
    }

    public static void glUniformMatrix4x2dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix4x2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 3, bl, dArray, l);
    }

    public static void glUniformMatrix4x3dv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glUniformMatrix4x3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length / 12, bl, dArray, l);
    }

    public static void glGetUniformdv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetUniformdv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetActiveSubroutineUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetActiveSubroutineUniformiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glGetActiveSubroutineUniformName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveSubroutineUniformName;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, n2, n3, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetActiveSubroutineName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveSubroutineName;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, n2, n3, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glUniformSubroutinesuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glUniformSubroutinesuiv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glGetUniformSubroutineuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetUniformSubroutineuiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetProgramStageiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetProgramStageiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glPatchParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glPatchParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            if (Checks.DEBUG) {
                Checks.check(fArray, GL11.glGetInteger(36466));
            }
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glDeleteTransformFeedbacks(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteTransformFeedbacks;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenTransformFeedbacks(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenTransformFeedbacks;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGetQueryIndexediv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetQueryIndexediv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    static {
        GL.initialize();
    }
}

