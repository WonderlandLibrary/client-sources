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
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL40C;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL41C
extends GL40C {
    public static final int GL_SHADER_COMPILER = 36346;
    public static final int GL_SHADER_BINARY_FORMATS = 36344;
    public static final int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    public static final int GL_MAX_VARYING_VECTORS = 36348;
    public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    public static final int GL_FIXED = 5132;
    public static final int GL_LOW_FLOAT = 36336;
    public static final int GL_MEDIUM_FLOAT = 36337;
    public static final int GL_HIGH_FLOAT = 36338;
    public static final int GL_LOW_INT = 36339;
    public static final int GL_MEDIUM_INT = 36340;
    public static final int GL_HIGH_INT = 36341;
    public static final int GL_RGB565 = 36194;
    public static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 33367;
    public static final int GL_PROGRAM_BINARY_LENGTH = 34625;
    public static final int GL_NUM_PROGRAM_BINARY_FORMATS = 34814;
    public static final int GL_PROGRAM_BINARY_FORMATS = 34815;
    public static final int GL_VERTEX_SHADER_BIT = 1;
    public static final int GL_FRAGMENT_SHADER_BIT = 2;
    public static final int GL_GEOMETRY_SHADER_BIT = 4;
    public static final int GL_TESS_CONTROL_SHADER_BIT = 8;
    public static final int GL_TESS_EVALUATION_SHADER_BIT = 16;
    public static final int GL_ALL_SHADER_BITS = -1;
    public static final int GL_PROGRAM_SEPARABLE = 33368;
    public static final int GL_ACTIVE_PROGRAM = 33369;
    public static final int GL_PROGRAM_PIPELINE_BINDING = 33370;
    public static final int GL_MAX_VIEWPORTS = 33371;
    public static final int GL_VIEWPORT_SUBPIXEL_BITS = 33372;
    public static final int GL_VIEWPORT_BOUNDS_RANGE = 33373;
    public static final int GL_LAYER_PROVOKING_VERTEX = 33374;
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 33375;
    public static final int GL_UNDEFINED_VERTEX = 33376;

    protected GL41C() {
        throw new UnsupportedOperationException();
    }

    public static native void glReleaseShaderCompiler();

    public static native void nglShaderBinary(int var0, long var1, int var3, long var4, int var6);

    public static void glShaderBinary(@NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL41C.nglShaderBinary(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), n, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    public static native void nglGetShaderPrecisionFormat(int var0, int var1, long var2, long var4);

    public static void glGetShaderPrecisionFormat(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
            Checks.check((Buffer)intBuffer2, 1);
        }
        GL41C.nglGetShaderPrecisionFormat(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetShaderPrecisionFormat(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer2 = memoryStack.callocInt(1);
            GL41C.nglGetShaderPrecisionFormat(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
            int n4 = intBuffer2.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glDepthRangef(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glClearDepthf(@NativeType(value="GLfloat") float var0);

    public static native void nglGetProgramBinary(int var0, int var1, long var2, long var4, long var6);

    public static void glGetProgramBinary(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        GL41C.nglGetProgramBinary(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglProgramBinary(int var0, int var1, long var2, int var4);

    public static void glProgramBinary(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL41C.nglProgramBinary(n, n2, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining());
    }

    public static native void glProgramParameteri(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void glUseProgramStages(@NativeType(value="GLuint") int var0, @NativeType(value="GLbitfield") int var1, @NativeType(value="GLuint") int var2);

    public static native void glActiveShaderProgram(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native int nglCreateShaderProgramv(int var0, int var1, long var2);

    @NativeType(value="GLuint")
    public static int glCreateShaderProgramv(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const **") PointerBuffer pointerBuffer) {
        return GL41C.nglCreateShaderProgramv(n, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLuint")
    public static int glCreateShaderProgramv(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const **") CharSequence ... charSequenceArray) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArray(memoryStack, MemoryUtil::memUTF8, charSequenceArray);
            int n3 = GL41C.nglCreateShaderProgramv(n, charSequenceArray.length, l);
            APIUtil.apiArrayFree(l, charSequenceArray.length);
            int n4 = n3;
            return n4;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLuint")
    public static int glCreateShaderProgramv(@NativeType(value="GLenum") int n, @NativeType(value="GLchar const **") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArray(memoryStack, MemoryUtil::memUTF8, charSequence);
            int n3 = GL41C.nglCreateShaderProgramv(n, 1, l);
            APIUtil.apiArrayFree(l, 1);
            int n4 = n3;
            return n4;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glBindProgramPipeline(@NativeType(value="GLuint") int var0);

    public static native void nglDeleteProgramPipelines(int var0, long var1);

    public static void glDeleteProgramPipelines(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.nglDeleteProgramPipelines(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteProgramPipelines(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            GL41C.nglDeleteProgramPipelines(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenProgramPipelines(int var0, long var1);

    public static void glGenProgramPipelines(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL41C.nglGenProgramPipelines(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenProgramPipelines() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL41C.nglGenProgramPipelines(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsProgramPipeline(@NativeType(value="GLuint") int var0);

    public static native void nglGetProgramPipelineiv(int var0, int var1, long var2);

    public static void glGetProgramPipelineiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL41C.nglGetProgramPipelineiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetProgramPipelinei(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL41C.nglGetProgramPipelineiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glProgramUniform1i(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glProgramUniform2i(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glProgramUniform3i(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void glProgramUniform4i(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5);

    public static native void glProgramUniform1ui(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glProgramUniform2ui(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3);

    public static native void glProgramUniform3ui(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint") int var4);

    public static native void glProgramUniform4ui(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint") int var4, @NativeType(value="GLuint") int var5);

    public static native void glProgramUniform1f(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2);

    public static native void glProgramUniform2f(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glProgramUniform3f(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glProgramUniform4f(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5);

    public static native void glProgramUniform1d(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLdouble") double var2);

    public static native void glProgramUniform2d(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void glProgramUniform3d(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void glProgramUniform4d(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6, @NativeType(value="GLdouble") double var8);

    public static native void nglProgramUniform1iv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform1iv(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform2iv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform2iv(n, n2, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform3iv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform3iv(n, n2, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform4iv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform4iv(n, n2, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform1uiv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform1uiv(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform2uiv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform2uiv(n, n2, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform3uiv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform3uiv(n, n2, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform4uiv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL41C.nglProgramUniform4uiv(n, n2, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform1fv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniform1fv(n, n2, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform2fv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniform2fv(n, n2, floatBuffer.remaining() >> 1, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform3fv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniform3fv(n, n2, floatBuffer.remaining() / 3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform4fv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniform4fv(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform1dv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniform1dv(n, n2, doubleBuffer.remaining(), MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniform2dv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniform2dv(n, n2, doubleBuffer.remaining() >> 1, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniform3dv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniform3dv(n, n2, doubleBuffer.remaining() / 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniform4dv(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniform4dv(n, n2, doubleBuffer.remaining() >> 2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix2fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix2fv(n, n2, floatBuffer.remaining() >> 2, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix3fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix3fv(n, n2, floatBuffer.remaining() / 9, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix4fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix4fv(n, n2, floatBuffer.remaining() >> 4, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix2dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix2dv(n, n2, doubleBuffer.remaining() >> 2, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix3dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix3dv(n, n2, doubleBuffer.remaining() / 9, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix4dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix4dv(n, n2, doubleBuffer.remaining() >> 4, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix2x3fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix2x3fv(n, n2, floatBuffer.remaining() / 6, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix3x2fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix3x2fv(n, n2, floatBuffer.remaining() / 6, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix2x4fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix2x4fv(n, n2, floatBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix4x2fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix4x2fv(n, n2, floatBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix3x4fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix3x4fv(n, n2, floatBuffer.remaining() / 12, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix4x3fv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglProgramUniformMatrix4x3fv(n, n2, floatBuffer.remaining() / 12, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix2x3dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix2x3dv(n, n2, doubleBuffer.remaining() / 6, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix3x2dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix3x2dv(n, n2, doubleBuffer.remaining() / 6, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix2x4dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix2x4dv(n, n2, doubleBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix4x2dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix4x2dv(n, n2, doubleBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix3x4dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix3x4dv(n, n2, doubleBuffer.remaining() / 12, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglProgramUniformMatrix4x3dv(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglProgramUniformMatrix4x3dv(n, n2, doubleBuffer.remaining() / 12, bl, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glValidateProgramPipeline(@NativeType(value="GLuint") int var0);

    public static native void nglGetProgramPipelineInfoLog(int var0, int var1, long var2, long var4);

    public static void glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL41C.nglGetProgramPipelineInfoLog(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(n2);
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            GL41C.nglGetProgramPipelineInfoLog(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            MemoryUtil.memFree(byteBuffer);
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="void")
    public static String glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n) {
        return GL41C.glGetProgramPipelineInfoLog(n, GL41C.glGetProgramPipelinei(n, 35716));
    }

    public static native void glVertexAttribL1d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1);

    public static native void glVertexAttribL2d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void glVertexAttribL3d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void glVertexAttribL4d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7);

    public static native void nglVertexAttribL1dv(int var0, long var1);

    public static void glVertexAttribL1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL41C.nglVertexAttribL1dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttribL2dv(int var0, long var1);

    public static void glVertexAttribL2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL41C.nglVertexAttribL2dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttribL3dv(int var0, long var1);

    public static void glVertexAttribL3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL41C.nglVertexAttribL3dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttribL4dv(int var0, long var1);

    public static void glVertexAttribL4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL41C.nglVertexAttribL4dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttribLPointer(int var0, int var1, int var2, int var3, long var4);

    public static void glVertexAttribLPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL41C.nglVertexAttribLPointer(n, n2, n3, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glVertexAttribLPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        GL41C.nglVertexAttribLPointer(n, n2, n3, n4, l);
    }

    public static void glVertexAttribLPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL41C.nglVertexAttribLPointer(n, n2, 5130, n3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetVertexAttribLdv(int var0, int var1, long var2);

    public static void glGetVertexAttribLdv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL41C.nglGetVertexAttribLdv(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglViewportArrayv(int var0, int var1, long var2);

    public static void glViewportArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL41C.nglViewportArrayv(n, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glViewportIndexedf(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void nglViewportIndexedfv(int var0, long var1);

    public static void glViewportIndexedfv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL41C.nglViewportIndexedfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglScissorArrayv(int var0, int var1, long var2);

    public static void glScissorArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL41C.nglScissorArrayv(n, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glScissorIndexed(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4);

    public static native void nglScissorIndexedv(int var0, long var1);

    public static void glScissorIndexedv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL41C.nglScissorIndexedv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglDepthRangeArrayv(int var0, int var1, long var2);

    public static void glDepthRangeArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        GL41C.nglDepthRangeArrayv(n, doubleBuffer.remaining() >> 1, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glDepthRangeIndexed(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void nglGetFloati_v(int var0, int var1, long var2);

    public static void glGetFloati_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL41C.nglGetFloati_v(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetFloati(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL41C.nglGetFloati_v(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetDoublei_v(int var0, int var1, long var2);

    public static void glGetDoublei_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL41C.nglGetDoublei_v(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetDoublei(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            GL41C.nglGetDoublei_v(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glShaderBinary(@NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glShaderBinary;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(nArray.length, nArray, n, MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), l);
    }

    public static void glGetShaderPrecisionFormat(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray, @NativeType(value="GLint *") int[] nArray2) {
        long l = GL.getICD().glGetShaderPrecisionFormat;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
            Checks.check(nArray2, 1);
        }
        JNI.callPPV(n, n2, nArray, nArray2, l);
    }

    public static void glGetProgramBinary(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLenum *") int[] nArray2, @NativeType(value="void *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetProgramBinary;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
        }
        JNI.callPPPV(n, byteBuffer.remaining(), nArray, nArray2, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glDeleteProgramPipelines(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteProgramPipelines;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenProgramPipelines(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenProgramPipelines;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGetProgramPipelineiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetProgramPipelineiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glProgramUniform1iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform1iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glProgramUniform2iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform2iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 1, nArray, l);
    }

    public static void glProgramUniform3iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length / 3, nArray, l);
    }

    public static void glProgramUniform4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 2, nArray, l);
    }

    public static void glProgramUniform1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform1uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glProgramUniform2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform2uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 1, nArray, l);
    }

    public static void glProgramUniform3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length / 3, nArray, l);
    }

    public static void glProgramUniform4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 2, nArray, l);
    }

    public static void glProgramUniform1fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform1fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length, fArray, l);
    }

    public static void glProgramUniform2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 1, fArray, l);
    }

    public static void glProgramUniform3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 3, fArray, l);
    }

    public static void glProgramUniform4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 2, fArray, l);
    }

    public static void glProgramUniform1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniform1dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length, dArray, l);
    }

    public static void glProgramUniform2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniform2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length >> 1, dArray, l);
    }

    public static void glProgramUniform3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniform3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length / 3, dArray, l);
    }

    public static void glProgramUniform4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniform4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length >> 2, dArray, l);
    }

    public static void glProgramUniformMatrix2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 2, bl, fArray, l);
    }

    public static void glProgramUniformMatrix3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 9, bl, fArray, l);
    }

    public static void glProgramUniformMatrix4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 4, bl, fArray, l);
    }

    public static void glProgramUniformMatrix2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length >> 2, bl, dArray, l);
    }

    public static void glProgramUniformMatrix3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length / 9, bl, dArray, l);
    }

    public static void glProgramUniformMatrix4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length >> 4, bl, dArray, l);
    }

    public static void glProgramUniformMatrix2x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix2x3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 6, bl, fArray, l);
    }

    public static void glProgramUniformMatrix3x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix3x2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 6, bl, fArray, l);
    }

    public static void glProgramUniformMatrix2x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix2x4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 3, bl, fArray, l);
    }

    public static void glProgramUniformMatrix4x2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix4x2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 3, bl, fArray, l);
    }

    public static void glProgramUniformMatrix3x4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix3x4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 12, bl, fArray, l);
    }

    public static void glProgramUniformMatrix4x3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix4x3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 12, bl, fArray, l);
    }

    public static void glProgramUniformMatrix2x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix2x3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length / 6, bl, dArray, l);
    }

    public static void glProgramUniformMatrix3x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix3x2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length / 6, bl, dArray, l);
    }

    public static void glProgramUniformMatrix2x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix2x4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length >> 3, bl, dArray, l);
    }

    public static void glProgramUniformMatrix4x2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix4x2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length >> 3, bl, dArray, l);
    }

    public static void glProgramUniformMatrix3x4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix3x4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length / 12, bl, dArray, l);
    }

    public static void glProgramUniformMatrix4x3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glProgramUniformMatrix4x3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray.length / 12, bl, dArray, l);
    }

    public static void glGetProgramPipelineInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetProgramPipelineInfoLog;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glVertexAttribL1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttribL1dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttribL2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttribL2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttribL3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttribL3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttribL4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttribL4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glGetVertexAttribLdv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetVertexAttribLdv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glViewportArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glViewportArrayv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 2, fArray, l);
    }

    public static void glViewportIndexedfv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glViewportIndexedfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glScissorArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glScissorArrayv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 2, nArray, l);
    }

    public static void glScissorIndexedv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glScissorIndexedv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glDepthRangeArrayv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glDepthRangeArrayv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, dArray.length >> 1, dArray, l);
    }

    public static void glGetFloati_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetFloati_v;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetDoublei_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetDoublei_v;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    static {
        GL.initialize();
    }
}

