/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBShaderObjects {
    public static final int GL_PROGRAM_OBJECT_ARB = 35648;
    public static final int GL_OBJECT_TYPE_ARB = 35662;
    public static final int GL_OBJECT_SUBTYPE_ARB = 35663;
    public static final int GL_OBJECT_DELETE_STATUS_ARB = 35712;
    public static final int GL_OBJECT_COMPILE_STATUS_ARB = 35713;
    public static final int GL_OBJECT_LINK_STATUS_ARB = 35714;
    public static final int GL_OBJECT_VALIDATE_STATUS_ARB = 35715;
    public static final int GL_OBJECT_INFO_LOG_LENGTH_ARB = 35716;
    public static final int GL_OBJECT_ATTACHED_OBJECTS_ARB = 35717;
    public static final int GL_OBJECT_ACTIVE_UNIFORMS_ARB = 35718;
    public static final int GL_OBJECT_ACTIVE_UNIFORM_MAX_LENGTH_ARB = 35719;
    public static final int GL_OBJECT_SHADER_SOURCE_LENGTH_ARB = 35720;
    public static final int GL_SHADER_OBJECT_ARB = 35656;
    public static final int GL_FLOAT_VEC2_ARB = 35664;
    public static final int GL_FLOAT_VEC3_ARB = 35665;
    public static final int GL_FLOAT_VEC4_ARB = 35666;
    public static final int GL_INT_VEC2_ARB = 35667;
    public static final int GL_INT_VEC3_ARB = 35668;
    public static final int GL_INT_VEC4_ARB = 35669;
    public static final int GL_BOOL_ARB = 35670;
    public static final int GL_BOOL_VEC2_ARB = 35671;
    public static final int GL_BOOL_VEC3_ARB = 35672;
    public static final int GL_BOOL_VEC4_ARB = 35673;
    public static final int GL_FLOAT_MAT2_ARB = 35674;
    public static final int GL_FLOAT_MAT3_ARB = 35675;
    public static final int GL_FLOAT_MAT4_ARB = 35676;
    public static final int GL_SAMPLER_1D_ARB = 35677;
    public static final int GL_SAMPLER_2D_ARB = 35678;
    public static final int GL_SAMPLER_3D_ARB = 35679;
    public static final int GL_SAMPLER_CUBE_ARB = 35680;
    public static final int GL_SAMPLER_1D_SHADOW_ARB = 35681;
    public static final int GL_SAMPLER_2D_SHADOW_ARB = 35682;
    public static final int GL_SAMPLER_2D_RECT_ARB = 35683;
    public static final int GL_SAMPLER_2D_RECT_SHADOW_ARB = 35684;

    protected ARBShaderObjects() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDeleteObjectARB, gLCapabilities.glGetHandleARB, gLCapabilities.glDetachObjectARB, gLCapabilities.glCreateShaderObjectARB, gLCapabilities.glShaderSourceARB, gLCapabilities.glCompileShaderARB, gLCapabilities.glCreateProgramObjectARB, gLCapabilities.glAttachObjectARB, gLCapabilities.glLinkProgramARB, gLCapabilities.glUseProgramObjectARB, gLCapabilities.glValidateProgramARB, gLCapabilities.glUniform1fARB, gLCapabilities.glUniform2fARB, gLCapabilities.glUniform3fARB, gLCapabilities.glUniform4fARB, gLCapabilities.glUniform1iARB, gLCapabilities.glUniform2iARB, gLCapabilities.glUniform3iARB, gLCapabilities.glUniform4iARB, gLCapabilities.glUniform1fvARB, gLCapabilities.glUniform2fvARB, gLCapabilities.glUniform3fvARB, gLCapabilities.glUniform4fvARB, gLCapabilities.glUniform1ivARB, gLCapabilities.glUniform2ivARB, gLCapabilities.glUniform3ivARB, gLCapabilities.glUniform4ivARB, gLCapabilities.glUniformMatrix2fvARB, gLCapabilities.glUniformMatrix3fvARB, gLCapabilities.glUniformMatrix4fvARB, gLCapabilities.glGetObjectParameterfvARB, gLCapabilities.glGetObjectParameterivARB, gLCapabilities.glGetInfoLogARB, gLCapabilities.glGetAttachedObjectsARB, gLCapabilities.glGetUniformLocationARB, gLCapabilities.glGetActiveUniformARB, gLCapabilities.glGetUniformfvARB, gLCapabilities.glGetUniformivARB, gLCapabilities.glGetShaderSourceARB);
    }

    public static native void glDeleteObjectARB(@NativeType(value="GLhandleARB") int var0);

    @NativeType(value="GLhandleARB")
    public static native int glGetHandleARB(@NativeType(value="GLenum") int var0);

    public static native void glDetachObjectARB(@NativeType(value="GLhandleARB") int var0, @NativeType(value="GLhandleARB") int var1);

    @NativeType(value="GLhandleARB")
    public static native int glCreateShaderObjectARB(@NativeType(value="GLenum") int var0);

    public static native void nglShaderSourceARB(int var0, int var1, long var2, long var4);

    public static void glShaderSourceARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLcharARB const **") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, pointerBuffer.remaining());
        }
        ARBShaderObjects.nglShaderSourceARB(n, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddressSafe(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glShaderSourceARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLcharARB const **") CharSequence ... charSequenceArray) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArrayi(memoryStack, MemoryUtil::memUTF8, charSequenceArray);
            ARBShaderObjects.nglShaderSourceARB(n, charSequenceArray.length, l, l - (long)(charSequenceArray.length << 2));
            APIUtil.apiArrayFree(l, charSequenceArray.length);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glShaderSourceARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLcharARB const **") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            long l = APIUtil.apiArrayi(memoryStack, MemoryUtil::memUTF8, charSequence);
            ARBShaderObjects.nglShaderSourceARB(n, 1, l, l - 4L);
            APIUtil.apiArrayFree(l, 1);
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glCompileShaderARB(@NativeType(value="GLhandleARB") int var0);

    @NativeType(value="GLhandleARB")
    public static native int glCreateProgramObjectARB();

    public static native void glAttachObjectARB(@NativeType(value="GLhandleARB") int var0, @NativeType(value="GLhandleARB") int var1);

    public static native void glLinkProgramARB(@NativeType(value="GLhandleARB") int var0);

    public static native void glUseProgramObjectARB(@NativeType(value="GLhandleARB") int var0);

    public static native void glValidateProgramARB(@NativeType(value="GLhandleARB") int var0);

    public static native void glUniform1fARB(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glUniform2fARB(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glUniform3fARB(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glUniform4fARB(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glUniform1iARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glUniform2iARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glUniform3iARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glUniform4iARB(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void nglUniform1fvARB(int var0, int var1, long var2);

    public static void glUniform1fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniform1fvARB(n, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform2fvARB(int var0, int var1, long var2);

    public static void glUniform2fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniform2fvARB(n, floatBuffer.remaining() >> 1, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform3fvARB(int var0, int var1, long var2);

    public static void glUniform3fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniform3fvARB(n, floatBuffer.remaining() / 3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform4fvARB(int var0, int var1, long var2);

    public static void glUniform4fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniform4fvARB(n, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniform1ivARB(int var0, int var1, long var2);

    public static void glUniform1ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        ARBShaderObjects.nglUniform1ivARB(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform2ivARB(int var0, int var1, long var2);

    public static void glUniform2ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        ARBShaderObjects.nglUniform2ivARB(n, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform3ivARB(int var0, int var1, long var2);

    public static void glUniform3ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        ARBShaderObjects.nglUniform3ivARB(n, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniform4ivARB(int var0, int var1, long var2);

    public static void glUniform4ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        ARBShaderObjects.nglUniform4ivARB(n, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglUniformMatrix2fvARB(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniformMatrix2fvARB(n, floatBuffer.remaining() >> 2, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix3fvARB(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniformMatrix3fvARB(n, floatBuffer.remaining() / 9, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix4fvARB(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBShaderObjects.nglUniformMatrix4fvARB(n, floatBuffer.remaining() >> 4, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetObjectParameterfvARB(int var0, int var1, long var2);

    public static void glGetObjectParameterfvARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        ARBShaderObjects.nglGetObjectParameterfvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetObjectParameterivARB(int var0, int var1, long var2);

    public static void glGetObjectParameterivARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBShaderObjects.nglGetObjectParameterivARB(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetObjectParameteriARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBShaderObjects.nglGetObjectParameterivARB(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetInfoLogARB(int var0, int var1, long var2, long var4);

    public static void glGetInfoLogARB(@NativeType(value="GLhandleARB") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLcharARB *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        ARBShaderObjects.nglGetInfoLogARB(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetInfoLogARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLsizei") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(n2);
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            ARBShaderObjects.nglGetInfoLogARB(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            MemoryUtil.memFree(byteBuffer);
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="void")
    public static String glGetInfoLogARB(@NativeType(value="GLhandleARB") int n) {
        return ARBShaderObjects.glGetInfoLogARB(n, ARBShaderObjects.glGetObjectParameteriARB(n, 35716));
    }

    public static native void nglGetAttachedObjectsARB(int var0, int var1, long var2, long var4);

    public static void glGetAttachedObjectsARB(@NativeType(value="GLhandleARB") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLhandleARB *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        ARBShaderObjects.nglGetAttachedObjectsARB(n, intBuffer2.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    public static native int nglGetUniformLocationARB(int var0, long var1);

    @NativeType(value="GLint")
    public static int glGetUniformLocationARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLcharARB const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ARBShaderObjects.nglGetUniformLocationARB(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetUniformLocationARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLcharARB const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = ARBShaderObjects.nglGetUniformLocationARB(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetActiveUniformARB(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static void glGetActiveUniformARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLcharARB *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        ARBShaderObjects.nglGetActiveUniformARB(n, n2, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetActiveUniformARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer3 = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n3);
            ARBShaderObjects.nglGetActiveUniformARB(n, n2, n3, MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer3.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    @NativeType(value="void")
    public static String glGetActiveUniformARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return ARBShaderObjects.glGetActiveUniformARB(n, n2, ARBShaderObjects.glGetObjectParameteriARB(n, 35719), intBuffer, intBuffer2);
    }

    public static native void nglGetUniformfvARB(int var0, int var1, long var2);

    public static void glGetUniformfvARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        ARBShaderObjects.nglGetUniformfvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetUniformfARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            ARBShaderObjects.nglGetUniformfvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetUniformivARB(int var0, int var1, long var2);

    public static void glGetUniformivARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBShaderObjects.nglGetUniformivARB(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetUniformiARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBShaderObjects.nglGetUniformivARB(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetShaderSourceARB(int var0, int var1, long var2, long var4);

    public static void glGetShaderSourceARB(@NativeType(value="GLhandleARB") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLcharARB *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        ARBShaderObjects.nglGetShaderSourceARB(n, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetShaderSourceARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLsizei") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        ByteBuffer byteBuffer = MemoryUtil.memAlloc(n2);
        try {
            IntBuffer intBuffer = memoryStack.ints(0);
            ARBShaderObjects.nglGetShaderSourceARB(n, n2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memUTF8(byteBuffer, intBuffer.get(0));
            return string;
        } finally {
            MemoryUtil.memFree(byteBuffer);
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="void")
    public static String glGetShaderSourceARB(@NativeType(value="GLhandleARB") int n) {
        return ARBShaderObjects.glGetShaderSourceARB(n, ARBShaderObjects.glGetObjectParameteriARB(n, 35720));
    }

    public static void glShaderSourceARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLcharARB const **") PointerBuffer pointerBuffer, @Nullable @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glShaderSourceARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, pointerBuffer.remaining());
        }
        JNI.callPPV(n, pointerBuffer.remaining(), MemoryUtil.memAddress(pointerBuffer), nArray, l);
    }

    public static void glUniform1fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform1fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length, fArray, l);
    }

    public static void glUniform2fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform2fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 1, fArray, l);
    }

    public static void glUniform3fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform3fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 3, fArray, l);
    }

    public static void glUniform4fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniform4fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 2, fArray, l);
    }

    public static void glUniform1ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform1ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glUniform2ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform2ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 1, nArray, l);
    }

    public static void glUniform3ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform3ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length / 3, nArray, l);
    }

    public static void glUniform4ivARB(@NativeType(value="GLint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glUniform4ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length >> 2, nArray, l);
    }

    public static void glUniformMatrix2fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix2fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 2, bl, fArray, l);
    }

    public static void glUniformMatrix3fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix3fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 9, bl, fArray, l);
    }

    public static void glUniformMatrix4fvARB(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix4fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 4, bl, fArray, l);
    }

    public static void glGetObjectParameterfvARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetObjectParameterfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetObjectParameterivARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetObjectParameterivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetInfoLogARB(@NativeType(value="GLhandleARB") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLcharARB *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetInfoLogARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetAttachedObjectsARB(@NativeType(value="GLhandleARB") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLhandleARB *") int[] nArray2) {
        long l = GL.getICD().glGetAttachedObjectsARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, nArray2.length, nArray, nArray2, l);
    }

    public static void glGetActiveUniformARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLcharARB *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveUniformARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        JNI.callPPPPV(n, n2, byteBuffer.remaining(), nArray, nArray2, nArray3, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetUniformfvARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetUniformfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetUniformivARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetUniformivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetShaderSourceARB(@NativeType(value="GLhandleARB") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLcharARB *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetShaderSourceARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPV(n, byteBuffer.remaining(), nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    static {
        GL.initialize();
    }
}

