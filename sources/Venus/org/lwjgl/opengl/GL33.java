/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL33
extends GL32 {
    public static final int GL_SRC1_COLOR = 35065;
    public static final int GL_ONE_MINUS_SRC1_COLOR = 35066;
    public static final int GL_ONE_MINUS_SRC1_ALPHA = 35067;
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 35068;
    public static final int GL_ANY_SAMPLES_PASSED = 35887;
    public static final int GL_SAMPLER_BINDING = 35097;
    public static final int GL_RGB10_A2UI = 36975;
    public static final int GL_TEXTURE_SWIZZLE_R = 36418;
    public static final int GL_TEXTURE_SWIZZLE_G = 36419;
    public static final int GL_TEXTURE_SWIZZLE_B = 36420;
    public static final int GL_TEXTURE_SWIZZLE_A = 36421;
    public static final int GL_TEXTURE_SWIZZLE_RGBA = 36422;
    public static final int GL_TIME_ELAPSED = 35007;
    public static final int GL_TIMESTAMP = 36392;
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 35070;
    public static final int GL_INT_2_10_10_10_REV = 36255;

    protected GL33() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, boolean bl) {
        return (bl || Checks.checkFunctions(gLCapabilities.glVertexP2ui, gLCapabilities.glVertexP3ui, gLCapabilities.glVertexP4ui, gLCapabilities.glVertexP2uiv, gLCapabilities.glVertexP3uiv, gLCapabilities.glVertexP4uiv, gLCapabilities.glTexCoordP1ui, gLCapabilities.glTexCoordP2ui, gLCapabilities.glTexCoordP3ui, gLCapabilities.glTexCoordP4ui, gLCapabilities.glTexCoordP1uiv, gLCapabilities.glTexCoordP2uiv, gLCapabilities.glTexCoordP3uiv, gLCapabilities.glTexCoordP4uiv, gLCapabilities.glMultiTexCoordP1ui, gLCapabilities.glMultiTexCoordP2ui, gLCapabilities.glMultiTexCoordP3ui, gLCapabilities.glMultiTexCoordP4ui, gLCapabilities.glMultiTexCoordP1uiv, gLCapabilities.glMultiTexCoordP2uiv, gLCapabilities.glMultiTexCoordP3uiv, gLCapabilities.glMultiTexCoordP4uiv, gLCapabilities.glNormalP3ui, gLCapabilities.glNormalP3uiv, gLCapabilities.glColorP3ui, gLCapabilities.glColorP4ui, gLCapabilities.glColorP3uiv, gLCapabilities.glColorP4uiv, gLCapabilities.glSecondaryColorP3ui, gLCapabilities.glSecondaryColorP3uiv)) && Checks.checkFunctions(gLCapabilities.glBindFragDataLocationIndexed, gLCapabilities.glGetFragDataIndex, gLCapabilities.glGenSamplers, gLCapabilities.glDeleteSamplers, gLCapabilities.glIsSampler, gLCapabilities.glBindSampler, gLCapabilities.glSamplerParameteri, gLCapabilities.glSamplerParameterf, gLCapabilities.glSamplerParameteriv, gLCapabilities.glSamplerParameterfv, gLCapabilities.glSamplerParameterIiv, gLCapabilities.glSamplerParameterIuiv, gLCapabilities.glGetSamplerParameteriv, gLCapabilities.glGetSamplerParameterfv, gLCapabilities.glGetSamplerParameterIiv, gLCapabilities.glGetSamplerParameterIuiv, gLCapabilities.glQueryCounter, gLCapabilities.glGetQueryObjecti64v, gLCapabilities.glGetQueryObjectui64v, gLCapabilities.glVertexAttribDivisor, gLCapabilities.glVertexAttribP1ui, gLCapabilities.glVertexAttribP2ui, gLCapabilities.glVertexAttribP3ui, gLCapabilities.glVertexAttribP4ui, gLCapabilities.glVertexAttribP1uiv, gLCapabilities.glVertexAttribP2uiv, gLCapabilities.glVertexAttribP3uiv, gLCapabilities.glVertexAttribP4uiv);
    }

    public static void nglBindFragDataLocationIndexed(int n, int n2, int n3, long l) {
        GL33C.nglBindFragDataLocationIndexed(n, n2, n3, l);
    }

    public static void glBindFragDataLocationIndexed(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL33C.glBindFragDataLocationIndexed(n, n2, n3, byteBuffer);
    }

    public static void glBindFragDataLocationIndexed(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL33C.glBindFragDataLocationIndexed(n, n2, n3, charSequence);
    }

    public static int nglGetFragDataIndex(int n, long l) {
        return GL33C.nglGetFragDataIndex(n, l);
    }

    @NativeType(value="GLint")
    public static int glGetFragDataIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL33C.glGetFragDataIndex(n, byteBuffer);
    }

    @NativeType(value="GLint")
    public static int glGetFragDataIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL33C.glGetFragDataIndex(n, charSequence);
    }

    public static void nglGenSamplers(int n, long l) {
        GL33C.nglGenSamplers(n, l);
    }

    public static void glGenSamplers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL33C.glGenSamplers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenSamplers() {
        return GL33C.glGenSamplers();
    }

    public static void nglDeleteSamplers(int n, long l) {
        GL33C.nglDeleteSamplers(n, l);
    }

    public static void glDeleteSamplers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glDeleteSamplers(intBuffer);
    }

    public static void glDeleteSamplers(@NativeType(value="GLuint const *") int n) {
        GL33C.glDeleteSamplers(n);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsSampler(@NativeType(value="GLuint") int n) {
        return GL33C.glIsSampler(n);
    }

    public static void glBindSampler(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL33C.glBindSampler(n, n2);
    }

    public static void glSamplerParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL33C.glSamplerParameteri(n, n2, n3);
    }

    public static void glSamplerParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat") float f) {
        GL33C.glSamplerParameterf(n, n2, f);
    }

    public static void nglSamplerParameteriv(int n, int n2, long l) {
        GL33C.nglSamplerParameteriv(n, n2, l);
    }

    public static void glSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL33C.glSamplerParameteriv(n, n2, intBuffer);
    }

    public static void nglSamplerParameterfv(int n, int n2, long l) {
        GL33C.nglSamplerParameterfv(n, n2, l);
    }

    public static void glSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL33C.glSamplerParameterfv(n, n2, floatBuffer);
    }

    public static void nglSamplerParameterIiv(int n, int n2, long l) {
        GL33C.nglSamplerParameterIiv(n, n2, l);
    }

    public static void glSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL33C.glSamplerParameterIiv(n, n2, intBuffer);
    }

    public static void nglSamplerParameterIuiv(int n, int n2, long l) {
        GL33C.nglSamplerParameterIuiv(n, n2, l);
    }

    public static void glSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glSamplerParameterIuiv(n, n2, intBuffer);
    }

    public static void nglGetSamplerParameteriv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameteriv(n, n2, l);
    }

    public static void glGetSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL33C.glGetSamplerParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetSamplerParameteri(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameteri(n, n2);
    }

    public static void nglGetSamplerParameterfv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameterfv(n, n2, l);
    }

    public static void glGetSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL33C.glGetSamplerParameterfv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetSamplerParameterf(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameterf(n, n2);
    }

    public static void nglGetSamplerParameterIiv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameterIiv(n, n2, l);
    }

    public static void glGetSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL33C.glGetSamplerParameterIiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetSamplerParameterIi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameterIi(n, n2);
    }

    public static void nglGetSamplerParameterIuiv(int n, int n2, long l) {
        GL33C.nglGetSamplerParameterIuiv(n, n2, l);
    }

    public static void glGetSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL33C.glGetSamplerParameterIuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetSamplerParameterIui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetSamplerParameterIui(n, n2);
    }

    public static void glQueryCounter(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        GL33C.glQueryCounter(n, n2);
    }

    public static void nglGetQueryObjecti64v(int n, int n2, long l) {
        GL33C.nglGetQueryObjecti64v(n, n2, l);
    }

    public static void glGetQueryObjecti64v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL33C.glGetQueryObjecti64v(n, n2, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetQueryObjecti64(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetQueryObjecti64(n, n2);
    }

    public static void nglGetQueryObjectui64v(int n, int n2, long l) {
        GL33C.nglGetQueryObjectui64v(n, n2, l);
    }

    public static void glGetQueryObjectui64v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 *") LongBuffer longBuffer) {
        GL33C.glGetQueryObjectui64v(n, n2, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetQueryObjectui64(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL33C.glGetQueryObjectui64(n, n2);
    }

    public static void glVertexAttribDivisor(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL33C.glVertexAttribDivisor(n, n2);
    }

    public static native void glVertexP2ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glVertexP3ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glVertexP4ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglVertexP2uiv(int var0, long var1);

    public static void glVertexP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglVertexP2uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexP3uiv(int var0, long var1);

    public static void glVertexP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglVertexP3uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertexP4uiv(int var0, long var1);

    public static void glVertexP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglVertexP4uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glTexCoordP1ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glTexCoordP2ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glTexCoordP3ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glTexCoordP4ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglTexCoordP1uiv(int var0, long var1);

    public static void glTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglTexCoordP1uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoordP2uiv(int var0, long var1);

    public static void glTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglTexCoordP2uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoordP3uiv(int var0, long var1);

    public static void glTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglTexCoordP3uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoordP4uiv(int var0, long var1);

    public static void glTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglTexCoordP4uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glMultiTexCoordP1ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void glMultiTexCoordP2ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void glMultiTexCoordP3ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void glMultiTexCoordP4ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void nglMultiTexCoordP1uiv(int var0, int var1, long var2);

    public static void glMultiTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglMultiTexCoordP1uiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoordP2uiv(int var0, int var1, long var2);

    public static void glMultiTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglMultiTexCoordP2uiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoordP3uiv(int var0, int var1, long var2);

    public static void glMultiTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglMultiTexCoordP3uiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexCoordP4uiv(int var0, int var1, long var2);

    public static void glMultiTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglMultiTexCoordP4uiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glNormalP3ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglNormalP3uiv(int var0, long var1);

    public static void glNormalP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglNormalP3uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glColorP3ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glColorP4ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglColorP3uiv(int var0, long var1);

    public static void glColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglColorP3uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglColorP4uiv(int var0, long var1);

    public static void glColorP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglColorP4uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glSecondaryColorP3ui(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglSecondaryColorP3uiv(int var0, long var1);

    public static void glSecondaryColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL33.nglSecondaryColorP3uiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static void glVertexAttribP1ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP1ui(n, n2, bl, n3);
    }

    public static void glVertexAttribP2ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP2ui(n, n2, bl, n3);
    }

    public static void glVertexAttribP3ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP3ui(n, n2, bl, n3);
    }

    public static void glVertexAttribP4ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP4ui(n, n2, bl, n3);
    }

    public static void nglVertexAttribP1uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP1uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP1uiv(n, n2, bl, intBuffer);
    }

    public static void nglVertexAttribP2uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP2uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP2uiv(n, n2, bl, intBuffer);
    }

    public static void nglVertexAttribP3uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP3uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP3uiv(n, n2, bl, intBuffer);
    }

    public static void nglVertexAttribP4uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP4uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP4uiv(n, n2, bl, intBuffer);
    }

    public static void glGenSamplers(@NativeType(value="GLuint *") int[] nArray) {
        GL33C.glGenSamplers(nArray);
    }

    public static void glDeleteSamplers(@NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glDeleteSamplers(nArray);
    }

    public static void glSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL33C.glSamplerParameteriv(n, n2, nArray);
    }

    public static void glSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL33C.glSamplerParameterfv(n, n2, fArray);
    }

    public static void glSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL33C.glSamplerParameterIiv(n, n2, nArray);
    }

    public static void glSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glSamplerParameterIuiv(n, n2, nArray);
    }

    public static void glGetSamplerParameteriv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL33C.glGetSamplerParameteriv(n, n2, nArray);
    }

    public static void glGetSamplerParameterfv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL33C.glGetSamplerParameterfv(n, n2, fArray);
    }

    public static void glGetSamplerParameterIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL33C.glGetSamplerParameterIiv(n, n2, nArray);
    }

    public static void glGetSamplerParameterIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL33C.glGetSamplerParameterIuiv(n, n2, nArray);
    }

    public static void glGetQueryObjecti64v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        GL33C.glGetQueryObjecti64v(n, n2, lArray);
    }

    public static void glGetQueryObjectui64v(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64 *") long[] lArray) {
        GL33C.glGetQueryObjectui64v(n, n2, lArray);
    }

    public static void glVertexP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexP2uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexP3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glVertexP4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glTexCoordP1uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glTexCoordP2uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glTexCoordP3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glTexCoordP4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glMultiTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoordP1uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glMultiTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoordP2uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glMultiTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoordP3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glMultiTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoordP4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glNormalP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glNormalP3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glColorP3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glColorP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glColorP4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glSecondaryColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glSecondaryColorP3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glVertexAttribP1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP1uiv(n, n2, bl, nArray);
    }

    public static void glVertexAttribP2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP2uiv(n, n2, bl, nArray);
    }

    public static void glVertexAttribP3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP3uiv(n, n2, bl, nArray);
    }

    public static void glVertexAttribP4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP4uiv(n, n2, bl, nArray);
    }

    static {
        GL.initialize();
    }
}

