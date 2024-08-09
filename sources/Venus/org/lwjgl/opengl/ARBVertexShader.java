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
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBVertexShader {
    public static final int GL_VERTEX_SHADER_ARB = 35633;
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS_ARB = 35658;
    public static final int GL_MAX_VARYING_FLOATS_ARB = 35659;
    public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 34921;
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS_ARB = 34930;
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS_ARB = 35660;
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS_ARB = 35661;
    public static final int GL_MAX_TEXTURE_COORDS_ARB = 34929;
    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 34370;
    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 34371;
    public static final int GL_OBJECT_ACTIVE_ATTRIBUTES_ARB = 35721;
    public static final int GL_OBJECT_ACTIVE_ATTRIBUTE_MAX_LENGTH_ARB = 35722;
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 34338;
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 34339;
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 34340;
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 34341;
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 34922;
    public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 34342;
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 34373;
    public static final int GL_FLOAT_VEC2_ARB = 35664;
    public static final int GL_FLOAT_VEC3_ARB = 35665;
    public static final int GL_FLOAT_VEC4_ARB = 35666;
    public static final int GL_FLOAT_MAT2_ARB = 35674;
    public static final int GL_FLOAT_MAT3_ARB = 35675;
    public static final int GL_FLOAT_MAT4_ARB = 35676;

    protected ARBVertexShader() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glVertexAttrib1fARB, gLCapabilities.glVertexAttrib1sARB, gLCapabilities.glVertexAttrib1dARB, gLCapabilities.glVertexAttrib2fARB, gLCapabilities.glVertexAttrib2sARB, gLCapabilities.glVertexAttrib2dARB, gLCapabilities.glVertexAttrib3fARB, gLCapabilities.glVertexAttrib3sARB, gLCapabilities.glVertexAttrib3dARB, gLCapabilities.glVertexAttrib4fARB, gLCapabilities.glVertexAttrib4sARB, gLCapabilities.glVertexAttrib4dARB, gLCapabilities.glVertexAttrib4NubARB, gLCapabilities.glVertexAttrib1fvARB, gLCapabilities.glVertexAttrib1svARB, gLCapabilities.glVertexAttrib1dvARB, gLCapabilities.glVertexAttrib2fvARB, gLCapabilities.glVertexAttrib2svARB, gLCapabilities.glVertexAttrib2dvARB, gLCapabilities.glVertexAttrib3fvARB, gLCapabilities.glVertexAttrib3svARB, gLCapabilities.glVertexAttrib3dvARB, gLCapabilities.glVertexAttrib4fvARB, gLCapabilities.glVertexAttrib4svARB, gLCapabilities.glVertexAttrib4dvARB, gLCapabilities.glVertexAttrib4ivARB, gLCapabilities.glVertexAttrib4bvARB, gLCapabilities.glVertexAttrib4ubvARB, gLCapabilities.glVertexAttrib4usvARB, gLCapabilities.glVertexAttrib4uivARB, gLCapabilities.glVertexAttrib4NbvARB, gLCapabilities.glVertexAttrib4NsvARB, gLCapabilities.glVertexAttrib4NivARB, gLCapabilities.glVertexAttrib4NubvARB, gLCapabilities.glVertexAttrib4NusvARB, gLCapabilities.glVertexAttrib4NuivARB, gLCapabilities.glVertexAttribPointerARB, gLCapabilities.glEnableVertexAttribArrayARB, gLCapabilities.glDisableVertexAttribArrayARB, gLCapabilities.glBindAttribLocationARB, gLCapabilities.glGetActiveAttribARB, gLCapabilities.glGetAttribLocationARB, gLCapabilities.glGetVertexAttribivARB, gLCapabilities.glGetVertexAttribfvARB, gLCapabilities.glGetVertexAttribdvARB, gLCapabilities.glGetVertexAttribPointervARB);
    }

    public static native void glVertexAttrib1fARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glVertexAttrib1sARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1);

    public static native void glVertexAttrib1dARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1);

    public static native void glVertexAttrib2fARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glVertexAttrib2sARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glVertexAttrib2dARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void glVertexAttrib3fARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glVertexAttrib3sARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glVertexAttrib3dARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void glVertexAttrib4fARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glVertexAttrib4sARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3, @NativeType(value="GLshort") short var4);

    public static native void glVertexAttrib4dARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7);

    public static native void glVertexAttrib4NubARB(@NativeType(value="GLuint") int var0, @NativeType(value="GLubyte") byte var1, @NativeType(value="GLubyte") byte var2, @NativeType(value="GLubyte") byte var3, @NativeType(value="GLubyte") byte var4);

    public static native void nglVertexAttrib1fvARB(int var0, long var1);

    public static void glVertexAttrib1fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        ARBVertexShader.nglVertexAttrib1fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib1svARB(int var0, long var1);

    public static void glVertexAttrib1svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        ARBVertexShader.nglVertexAttrib1svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib1dvARB(int var0, long var1);

    public static void glVertexAttrib1dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        ARBVertexShader.nglVertexAttrib1dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib2fvARB(int var0, long var1);

    public static void glVertexAttrib2fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        ARBVertexShader.nglVertexAttrib2fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib2svARB(int var0, long var1);

    public static void glVertexAttrib2svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        ARBVertexShader.nglVertexAttrib2svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib2dvARB(int var0, long var1);

    public static void glVertexAttrib2dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        ARBVertexShader.nglVertexAttrib2dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib3fvARB(int var0, long var1);

    public static void glVertexAttrib3fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        ARBVertexShader.nglVertexAttrib3fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib3svARB(int var0, long var1);

    public static void glVertexAttrib3svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        ARBVertexShader.nglVertexAttrib3svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib3dvARB(int var0, long var1);

    public static void glVertexAttrib3dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        ARBVertexShader.nglVertexAttrib3dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib4fvARB(int var0, long var1);

    public static void glVertexAttrib4fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4fvARB(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertexAttrib4svARB(int var0, long var1);

    public static void glVertexAttrib4svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4svARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4dvARB(int var0, long var1);

    public static void glVertexAttrib4dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4dvARB(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexAttrib4ivARB(int var0, long var1);

    public static void glVertexAttrib4ivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4ivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttrib4bvARB(int var0, long var1);

    public static void glVertexAttrib4bvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4bvARB(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4ubvARB(int var0, long var1);

    public static void glVertexAttrib4ubvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4ubvARB(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4usvARB(int var0, long var1);

    public static void glVertexAttrib4usvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4usvARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4uivARB(int var0, long var1);

    public static void glVertexAttrib4uivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4uivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttrib4NbvARB(int var0, long var1);

    public static void glVertexAttrib4NbvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4NbvARB(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4NsvARB(int var0, long var1);

    public static void glVertexAttrib4NsvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4NsvARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4NivARB(int var0, long var1);

    public static void glVertexAttrib4NivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4NivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttrib4NubvARB(int var0, long var1);

    public static void glVertexAttrib4NubvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4NubvARB(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglVertexAttrib4NusvARB(int var0, long var1);

    public static void glVertexAttrib4NusvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4NusvARB(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttrib4NuivARB(int var0, long var1);

    public static void glVertexAttrib4NuivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        ARBVertexShader.nglVertexAttrib4NuivARB(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexAttribPointerARB(int var0, int var1, int var2, boolean var3, int var4, long var5);

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBVertexShader.nglVertexAttribPointerARB(n, n2, n3, bl, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        ARBVertexShader.nglVertexAttribPointerARB(n, n2, n3, bl, n4, l);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        ARBVertexShader.nglVertexAttribPointerARB(n, n2, n3, bl, n4, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        ARBVertexShader.nglVertexAttribPointerARB(n, n2, n3, bl, n4, MemoryUtil.memAddress(intBuffer));
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        ARBVertexShader.nglVertexAttribPointerARB(n, n2, n3, bl, n4, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glEnableVertexAttribArrayARB(@NativeType(value="GLuint") int var0);

    public static native void glDisableVertexAttribArrayARB(@NativeType(value="GLuint") int var0);

    public static native void nglBindAttribLocationARB(int var0, int var1, long var2);

    public static void glBindAttribLocationARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        ARBVertexShader.nglBindAttribLocationARB(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glBindAttribLocationARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            ARBVertexShader.nglBindAttribLocationARB(n, n2, l);
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetActiveAttribARB(int var0, int var1, int var2, long var3, long var5, long var7, long var9);

    public static void glGetActiveAttribARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
        }
        ARBVertexShader.nglGetActiveAttribARB(n, n2, byteBuffer.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static String glGetActiveAttribARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer3 = memoryStack.ints(0);
            ByteBuffer byteBuffer = memoryStack.malloc(n3);
            ARBVertexShader.nglGetActiveAttribARB(n, n2, n3, MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(byteBuffer));
            String string = MemoryUtil.memASCII(byteBuffer, intBuffer3.get(0));
            return string;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    @NativeType(value="void")
    public static String glGetActiveAttribARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return ARBVertexShader.glGetActiveAttribARB(n, n2, ARBShaderObjects.glGetObjectParameteriARB(n, 35722), intBuffer, intBuffer2);
    }

    public static native int nglGetAttribLocationARB(int var0, long var1);

    @NativeType(value="GLint")
    public static int glGetAttribLocationARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        return ARBVertexShader.nglGetAttribLocationARB(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLint")
    public static int glGetAttribLocationARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = ARBVertexShader.nglGetAttribLocationARB(n, l);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetVertexAttribivARB(int var0, int var1, long var2);

    public static void glGetVertexAttribivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBVertexShader.nglGetVertexAttribivARB(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetVertexAttribiARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBVertexShader.nglGetVertexAttribivARB(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetVertexAttribfvARB(int var0, int var1, long var2);

    public static void glGetVertexAttribfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        ARBVertexShader.nglGetVertexAttribfvARB(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetVertexAttribdvARB(int var0, int var1, long var2);

    public static void glGetVertexAttribdvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        ARBVertexShader.nglGetVertexAttribdvARB(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetVertexAttribPointervARB(int var0, int var1, long var2);

    public static void glGetVertexAttribPointervARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        ARBVertexShader.nglGetVertexAttribPointervARB(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            ARBVertexShader.nglGetVertexAttribPointervARB(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glVertexAttrib1fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib1fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib1svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib1svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib1dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib1dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib2fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib2fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib2svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib2svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib2dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib2dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib3fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib3fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib3svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib3svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib3dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib3dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib4fvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertexAttrib4fvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glVertexAttrib4svARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4svARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4dvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertexAttrib4dvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glVertexAttrib4ivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4ivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttrib4usvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4usvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4uivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4uivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttrib4NsvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4NsvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4NivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4NivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttrib4NusvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4NusvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4NuivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexAttrib4NuivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribPointerARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, bl, n4, sArray, l);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glVertexAttribPointerARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, bl, n4, nArray, l);
    }

    public static void glVertexAttribPointerARB(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glVertexAttribPointerARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, bl, n4, fArray, l);
    }

    public static void glGetActiveAttribARB(@NativeType(value="GLhandleARB") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glGetActiveAttribARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
        }
        JNI.callPPPPV(n, n2, byteBuffer.remaining(), nArray, nArray2, nArray3, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glGetVertexAttribivARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetVertexAttribivARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetVertexAttribfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetVertexAttribfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetVertexAttribdvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetVertexAttribdvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    static {
        GL.initialize();
    }
}

