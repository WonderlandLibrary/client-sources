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
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL20C
extends GL15C {
    public static final int GL_SHADING_LANGUAGE_VERSION = 35724;
    public static final int GL_CURRENT_PROGRAM = 35725;
    public static final int GL_SHADER_TYPE = 35663;
    public static final int GL_DELETE_STATUS = 35712;
    public static final int GL_COMPILE_STATUS = 35713;
    public static final int GL_LINK_STATUS = 35714;
    public static final int GL_VALIDATE_STATUS = 35715;
    public static final int GL_INFO_LOG_LENGTH = 35716;
    public static final int GL_ATTACHED_SHADERS = 35717;
    public static final int GL_ACTIVE_UNIFORMS = 35718;
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 35719;
    public static final int GL_ACTIVE_ATTRIBUTES = 35721;
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 35722;
    public static final int GL_SHADER_SOURCE_LENGTH = 35720;
    public static final int GL_FLOAT_VEC2 = 35664;
    public static final int GL_FLOAT_VEC3 = 35665;
    public static final int GL_FLOAT_VEC4 = 35666;
    public static final int GL_INT_VEC2 = 35667;
    public static final int GL_INT_VEC3 = 35668;
    public static final int GL_INT_VEC4 = 35669;
    public static final int GL_BOOL = 35670;
    public static final int GL_BOOL_VEC2 = 35671;
    public static final int GL_BOOL_VEC3 = 35672;
    public static final int GL_BOOL_VEC4 = 35673;
    public static final int GL_FLOAT_MAT2 = 35674;
    public static final int GL_FLOAT_MAT3 = 35675;
    public static final int GL_FLOAT_MAT4 = 35676;
    public static final int GL_SAMPLER_1D = 35677;
    public static final int GL_SAMPLER_2D = 35678;
    public static final int GL_SAMPLER_3D = 35679;
    public static final int GL_SAMPLER_CUBE = 35680;
    public static final int GL_SAMPLER_1D_SHADOW = 35681;
    public static final int GL_SAMPLER_2D_SHADOW = 35682;
    public static final int GL_VERTEX_SHADER = 35633;
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 35658;
    public static final int GL_MAX_VARYING_FLOATS = 35659;
    public static final int GL_MAX_VERTEX_ATTRIBS = 34921;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 34930;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 35660;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 35661;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 34338;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 34339;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 34340;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 34341;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 34922;
    public static final int GL_CURRENT_VERTEX_ATTRIB = 34342;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 34373;
    public static final int GL_FRAGMENT_SHADER = 35632;
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 35657;
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 35723;
    public static final int GL_MAX_DRAW_BUFFERS = 34852;
    public static final int GL_DRAW_BUFFER0 = 34853;
    public static final int GL_DRAW_BUFFER1 = 34854;
    public static final int GL_DRAW_BUFFER2 = 34855;
    public static final int GL_DRAW_BUFFER3 = 34856;
    public static final int GL_DRAW_BUFFER4 = 34857;
    public static final int GL_DRAW_BUFFER5 = 34858;
    public static final int GL_DRAW_BUFFER6 = 34859;
    public static final int GL_DRAW_BUFFER7 = 34860;
    public static final int GL_DRAW_BUFFER8 = 34861;
    public static final int GL_DRAW_BUFFER9 = 34862;
    public static final int GL_DRAW_BUFFER10 = 34863;
    public static final int GL_DRAW_BUFFER11 = 34864;
    public static final int GL_DRAW_BUFFER12 = 34865;
    public static final int GL_DRAW_BUFFER13 = 34866;
    public static final int GL_DRAW_BUFFER14 = 34867;
    public static final int GL_DRAW_BUFFER15 = 34868;
    public static final int GL_POINT_SPRITE_COORD_ORIGIN = 36000;
    public static final int GL_LOWER_LEFT = 36001;
    public static final int GL_UPPER_LEFT = 36002;
    public static final int GL_BLEND_EQUATION_RGB = 32777;
    public static final int GL_BLEND_EQUATION_ALPHA = 34877;
    public static final int GL_STENCIL_BACK_FUNC = 34816;
    public static final int GL_STENCIL_BACK_FAIL = 34817;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 34818;
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 34819;
    public static final int GL_STENCIL_BACK_REF = 36003;
    public static final int GL_STENCIL_BACK_VALUE_MASK = 36004;
    public static final int GL_STENCIL_BACK_WRITEMASK = 36005;

    protected GL20C() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="GLuint")
    public static native int glCreateProgram();

    public static native void glDeleteProgram(@NativeType(value="GLuint") int var0);

    @NativeType(value="GLboolean")
    public static native boolean glIsProgram(@NativeType(value="GLuint") int var0);

    @NativeType(value="GLuint")
    public static native int glCreateShader(@NativeType(value="GLenum") int var0);

    public static native void glDeleteShader(@NativeType(value="GLuint") int var0);

    @NativeType(value="GLboolean")
    public static native boolean glIsShader(@NativeType(value="GLuint") int var0);

    public static native void glAttachShader(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDetachShader(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglShaderSource(int var0, int var1, long var2, long var4);

    public static void glShaderSource(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, pointerBuffer.remaining());
        }
        GL20C.nglShaderSource(n, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glShaderSource(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") CharSequence ... charSequenceArray) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArrayi(memoryStack, MemoryUtil::memUTF8, charSequenceArray);
            GL20C.nglShaderSource(n, charSequenceArray.length, l, l - (long)(charSequenceArray.length << 2));
            APIUtil.apiArrayFree(l, charSequenceArray.length);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glShaderSource(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArrayi(memoryStack, MemoryUtil::memUTF8, charSequence);
            GL20C.nglShaderSource(n, 1, l, l - 4L);
            APIUtil.apiArrayFree(l, 1);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glCompileShader(@NativeType(value="GLuint") int var0);

    public static native void glLinkProgram(@NativeType(value="GLuint") int var0);

    public static native void glUseProgram(@NativeType(value="GLuint") int var0);

    public static native void glValidateProgram(@NativeType(value="GLuint") int var0);

    public static native void glUniform1f(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glUniform2f(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glUniform3f(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glUniform4f(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glUniform1i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glUniform2i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glUniform3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glUniform4i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void nglUniform1fv(int var0, int var1, long var2);

    public static void glUniform1fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniform1fv(n, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform2fv(int var0, int var1, long var2);

    public static void glUniform2fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniform2fv(n, floatBuffer.remaining() >> 1, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform3fv(int var0, int var1, long var2);

    public static void glUniform3fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniform3fv(n, floatBuffer.remaining() / 3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform4fv(int var0, int var1, long var2);

    public static void glUniform4fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniform4fv(n, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform1iv(int var0, int var1, long var2);

    public static void glUniform1iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL20C.nglUniform1iv(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform2iv(int var0, int var1, long var2);

    public static void glUniform2iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL20C.nglUniform2iv(n, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform3iv(int var0, int var1, long var2);

    public static void glUniform3iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL20C.nglUniform3iv(n, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform4iv(int var0, int var1, long var2);

    public static void glUniform4iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL20C.nglUniform4iv(n, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniformMatrix2fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniformMatrix2fv(n, floatBuffer.remaining() >> 2, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix3fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniformMatrix3fv(n, floatBuffer.remaining() / 9, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix4fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL20C.nglUniformMatrix4fv(n, floatBuffer.remaining() >> 4, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetShaderiv(int var0, int var1, long var2);

    public static void glGetShaderiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL20C.nglGetShaderiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetShaderi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL20C.nglGetShaderiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetProgramiv(int var0, int var1, long var2);

    public static void glGetProgramiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL20C.nglGetProgramiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetProgrami(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL20C.nglGetProgramiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetShaderInfoLog(int var0, int var1, long var2, long var4);

    public static void glGetShaderInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL20C.nglGetShaderInfoLog(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetShaderInfoLog(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(n2);
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            GL20C.nglGetShaderInfoLog(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            MemoryUtil.memFree(byteBuffer);
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="void")
    public static String glGetShaderInfoLog(@NativeType(value="GLuint") int n) {
        return GL20C.glGetShaderInfoLog(n, GL20C.glGetShaderi(n, 35716));
    }

    public static native void nglGetProgramInfoLog(int var0, int var1, long var2, long var4);

    public static void glGetProgramInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL20C.nglGetProgramInfoLog(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetProgramInfoLog(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(n2);
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            GL20C.nglGetProgramInfoLog(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            MemoryUtil.memFree(byteBuffer);
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="void")
    public static String glGetProgramInfoLog(@NativeType(value="GLuint") int n) {
        return GL20C.glGetProgramInfoLog(n, GL20C.glGetProgrami(n, 35716));
    }

    public static native void nglGetAttachedShaders(int var0, int var1, long var2, long var4);

    public static void glGetAttachedShaders(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLuint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL20C.nglGetAttachedShaders(n, intBuffer2.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    public static native int nglGetUniformLocation(int var0, long var1);

    @NativeType(value="GLint")
    public static int glGetUniformLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return GL20C.nglGetUniformLocation(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetUniformLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = GL20C.nglGetUniformLocation(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetActiveUniform(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static void glGetActiveUniform(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        GL20C.nglGetActiveUniform(n, n2, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetActiveUniform(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer3 = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n3);
            GL20C.nglGetActiveUniform(n, n2, n3, MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memASCII(byteBuffer, intBuffer3.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    @NativeType(value="void")
    public static String glGetActiveUniform(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return GL20C.glGetActiveUniform(n, n2, GL20C.glGetProgrami(n, 35719), intBuffer, intBuffer2);
    }

    public static native void nglGetUniformfv(int var0, int var1, long var2);

    public static void glGetUniformfv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL20C.nglGetUniformfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetUniformf(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL20C.nglGetUniformfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetUniformiv(int var0, int var1, long var2);

    public static void glGetUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL20C.nglGetUniformiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetUniformi(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL20C.nglGetUniformiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetShaderSource(int var0, int var1, long var2, long var4);

    public static void glGetShaderSource(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL20C.nglGetShaderSource(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetShaderSource(@NativeType(value="GLuint") int n, @NativeType(value="GLsizei") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(n2);
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            GL20C.nglGetShaderSource(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            MemoryUtil.memFree(byteBuffer);
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="void")
    public static String glGetShaderSource(@NativeType(value="GLuint") int n) {
        return GL20C.glGetShaderSource(n, GL20C.glGetShaderi(n, 35720));
    }

    public static native void glVertexAttrib1f(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glVertexAttrib1s(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1);

    public static native void glVertexAttrib1d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1);

    public static native void glVertexAttrib2f(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glVertexAttrib2s(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glVertexAttrib2d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void glVertexAttrib3f(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glVertexAttrib3s(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glVertexAttrib3d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void glVertexAttrib4f(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glVertexAttrib4s(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3, @NativeType(value="GLshort") short var4);

    public static native void glVertexAttrib4d(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7);

    public static native void glVertexAttrib4Nub(@NativeType(value="GLuint") int var0, @NativeType(value="GLubyte") byte var1, @NativeType(value="GLubyte") byte var2, @NativeType(value="GLubyte") byte var3, @NativeType(value="GLubyte") byte var4);

    public static native void nglVertexAttrib1fv(int var0, long var1);

    public static void glVertexAttrib1fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL20C.nglVertexAttrib1fv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib1sv(int var0, long var1);

    public static void glVertexAttrib1sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        GL20C.nglVertexAttrib1sv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib1dv(int var0, long var1);

    public static void glVertexAttrib1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL20C.nglVertexAttrib1dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib2fv(int var0, long var1);

    public static void glVertexAttrib2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        GL20C.nglVertexAttrib2fv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib2sv(int var0, long var1);

    public static void glVertexAttrib2sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        GL20C.nglVertexAttrib2sv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib2dv(int var0, long var1);

    public static void glVertexAttrib2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL20C.nglVertexAttrib2dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib3fv(int var0, long var1);

    public static void glVertexAttrib3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL20C.nglVertexAttrib3fv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib3sv(int var0, long var1);

    public static void glVertexAttrib3sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL20C.nglVertexAttrib3sv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib3dv(int var0, long var1);

    public static void glVertexAttrib3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL20C.nglVertexAttrib3dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib4fv(int var0, long var1);

    public static void glVertexAttrib4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL20C.nglVertexAttrib4fv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib4sv(int var0, long var1);

    public static void glVertexAttrib4sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL20C.nglVertexAttrib4sv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4dv(int var0, long var1);

    public static void glVertexAttrib4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL20C.nglVertexAttrib4dv(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib4iv(int var0, long var1);

    public static void glVertexAttrib4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL20C.nglVertexAttrib4iv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttrib4bv(int var0, long var1);

    public static void glVertexAttrib4bv(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        GL20C.nglVertexAttrib4bv(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4ubv(int var0, long var1);

    public static void glVertexAttrib4ubv(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        GL20C.nglVertexAttrib4ubv(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4usv(int var0, long var1);

    public static void glVertexAttrib4usv(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL20C.nglVertexAttrib4usv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4uiv(int var0, long var1);

    public static void glVertexAttrib4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL20C.nglVertexAttrib4uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttrib4Nbv(int var0, long var1);

    public static void glVertexAttrib4Nbv(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        GL20C.nglVertexAttrib4Nbv(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4Nsv(int var0, long var1);

    public static void glVertexAttrib4Nsv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL20C.nglVertexAttrib4Nsv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4Niv(int var0, long var1);

    public static void glVertexAttrib4Niv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL20C.nglVertexAttrib4Niv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttrib4Nubv(int var0, long var1);

    public static void glVertexAttrib4Nubv(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        GL20C.nglVertexAttrib4Nubv(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4Nusv(int var0, long var1);

    public static void glVertexAttrib4Nusv(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL20C.nglVertexAttrib4Nusv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4Nuiv(int var0, long var1);

    public static void glVertexAttrib4Nuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL20C.nglVertexAttrib4Nuiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribPointer(int var0, int var1, int var2, boolean var3, int var4, long var5);

    public static void glVertexAttribPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL20C.nglVertexAttribPointer(n, n2, n3, bl, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glVertexAttribPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        GL20C.nglVertexAttribPointer(n, n2, n3, bl, n4, l);
    }

    public static void glVertexAttribPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL20C.nglVertexAttribPointer(n, n2, n3, bl, n4, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glVertexAttribPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL20C.nglVertexAttribPointer(n, n2, n3, bl, n4, MemoryUtil.memAddress(intBuffer));
    }

    public static void glVertexAttribPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL20C.nglVertexAttribPointer(n, n2, n3, bl, n4, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glEnableVertexAttribArray(@NativeType(value="GLuint") int var0);

    public static native void glDisableVertexAttribArray(@NativeType(value="GLuint") int var0);

    public static native void nglBindAttribLocation(int var0, int var1, long var2);

    public static void glBindAttribLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        GL20C.nglBindAttribLocation(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glBindAttribLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            GL20C.nglBindAttribLocation(n, n2, l);
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetActiveAttrib(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static void glGetActiveAttrib(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        GL20C.nglGetActiveAttrib(n, n2, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetActiveAttrib(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer3 = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n3);
            GL20C.nglGetActiveAttrib(n, n2, n3, MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memASCII(byteBuffer, intBuffer3.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    @NativeType(value="void")
    public static String glGetActiveAttrib(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return GL20C.glGetActiveAttrib(n, n2, GL20C.glGetProgrami(n, 35722), intBuffer, intBuffer2);
    }

    public static native int nglGetAttribLocation(int var0, long var1);

    @NativeType(value="GLint")
    public static int glGetAttribLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return GL20C.nglGetAttribLocation(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetAttribLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = GL20C.nglGetAttribLocation(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetVertexAttribiv(int var0, int var1, long var2);

    public static void glGetVertexAttribiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL20C.nglGetVertexAttribiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetVertexAttribi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL20C.nglGetVertexAttribiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetVertexAttribfv(int var0, int var1, long var2);

    public static void glGetVertexAttribfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL20C.nglGetVertexAttribfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetVertexAttribdv(int var0, int var1, long var2);

    public static void glGetVertexAttribdv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL20C.nglGetVertexAttribdv(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetVertexAttribPointerv(int var0, int var1, long var2);

    public static void glGetVertexAttribPointerv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        GL20C.nglGetVertexAttribPointerv(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexAttribPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            GL20C.nglGetVertexAttribPointerv(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglDrawBuffers(int var0, long var1);

    public static void glDrawBuffers(@NativeType(value="GLenum const *") IntBuffer intBuffer) {
        GL20C.nglDrawBuffers(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDrawBuffers(@NativeType(value="GLenum const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            GL20C.nglDrawBuffers(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glBlendEquationSeparate(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    public static native void glStencilOpSeparate(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLenum") int var3);

    public static native void glStencilFuncSeparate(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLuint") int var3);

    public static native void glStencilMaskSeparate(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static void glShaderSource(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glShaderSource;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, pointerBuffer.remaining());
        }
        JNI.callPPV(n, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), nArray, l);
    }

    public static void glUniform1fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform1fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length, fArray, l);
    }

    public static void glUniform2fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 1, fArray, l);
    }

    public static void glUniform3fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 3, fArray, l);
    }

    public static void glUniform4fv(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 2, fArray, l);
    }

    public static void glUniform1iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform1iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glUniform2iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform2iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 1, nArray, l);
    }

    public static void glUniform3iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length / 3, nArray, l);
    }

    public static void glUniform4iv(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 2, nArray, l);
    }

    public static void glUniformMatrix2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 2, bl, fArray, l);
    }

    public static void glUniformMatrix3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 9, bl, fArray, l);
    }

    public static void glUniformMatrix4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 4, bl, fArray, l);
    }

    public static void glGetShaderiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetShaderiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetProgramiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetProgramiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetShaderInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetShaderInfoLog;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetProgramInfoLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetProgramInfoLog;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetAttachedShaders(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLuint *") int[] nArray2) {
        long l = GL.getICD().glGetAttachedShaders;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, nArray2.length, nArray, nArray2, l);
    }

    public static void glGetActiveUniform(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveUniform;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        JNI.callPPPPV(n, n2, byteBuffer.remaining(), nArray, nArray2, nArray3, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetUniformfv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetUniformfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetUniformiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetShaderSource(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetShaderSource;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glVertexAttrib1fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib1fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib1sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib1sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib1dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib1dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib2fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib2sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib2sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib2dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib3fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib3sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib3dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib4fv(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib4sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4dv(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttrib4usv(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4usv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttrib4Nsv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4Nsv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4Niv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4Niv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttrib4Nusv(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4Nusv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4Nuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4Nuiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glGetActiveAttrib(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveAttrib;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        JNI.callPPPPV(n, n2, byteBuffer.remaining(), nArray, nArray2, nArray3, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetVertexAttribiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetVertexAttribiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetVertexAttribfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetVertexAttribfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetVertexAttribdv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetVertexAttribdv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glDrawBuffers(@NativeType(value="GLenum const *") int[] nArray) {
        long l = GL.getICD().glDrawBuffers;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    static {
        GL.initialize();
    }
}

