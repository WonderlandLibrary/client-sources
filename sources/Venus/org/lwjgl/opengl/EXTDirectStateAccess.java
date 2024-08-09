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
import java.util.Set;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.EXTDrawBuffers2;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTDirectStateAccess {
    public static final int GL_PROGRAM_MATRIX_EXT = 36397;
    public static final int GL_TRANSPOSE_PROGRAM_MATRIX_EXT = 36398;
    public static final int GL_PROGRAM_MATRIX_STACK_DEPTH_EXT = 36399;

    protected EXTDirectStateAccess() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glClientAttribDefaultEXT, gLCapabilities.glPushClientAttribDefaultEXT, gLCapabilities.glMatrixLoadfEXT, gLCapabilities.glMatrixLoaddEXT, gLCapabilities.glMatrixMultfEXT, gLCapabilities.glMatrixMultdEXT, gLCapabilities.glMatrixLoadIdentityEXT, gLCapabilities.glMatrixRotatefEXT, gLCapabilities.glMatrixRotatedEXT, gLCapabilities.glMatrixScalefEXT, gLCapabilities.glMatrixScaledEXT, gLCapabilities.glMatrixTranslatefEXT, gLCapabilities.glMatrixTranslatedEXT, gLCapabilities.glMatrixOrthoEXT, gLCapabilities.glMatrixFrustumEXT, gLCapabilities.glMatrixPushEXT, gLCapabilities.glMatrixPopEXT, gLCapabilities.glTextureParameteriEXT, gLCapabilities.glTextureParameterivEXT, gLCapabilities.glTextureParameterfEXT, gLCapabilities.glTextureParameterfvEXT, gLCapabilities.glTextureImage1DEXT, gLCapabilities.glTextureImage2DEXT, gLCapabilities.glTextureSubImage1DEXT, gLCapabilities.glTextureSubImage2DEXT, gLCapabilities.glCopyTextureImage1DEXT, gLCapabilities.glCopyTextureImage2DEXT, gLCapabilities.glCopyTextureSubImage1DEXT, gLCapabilities.glCopyTextureSubImage2DEXT, gLCapabilities.glGetTextureImageEXT, gLCapabilities.glGetTextureParameterfvEXT, gLCapabilities.glGetTextureParameterivEXT, gLCapabilities.glGetTextureLevelParameterfvEXT, gLCapabilities.glGetTextureLevelParameterivEXT, set.contains("OpenGL12") ? gLCapabilities.glTextureImage3DEXT : -1L, set.contains("OpenGL12") ? gLCapabilities.glTextureSubImage3DEXT : -1L, set.contains("OpenGL12") ? gLCapabilities.glCopyTextureSubImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glBindMultiTextureEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexCoordPointerEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexEnvfEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexEnvfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexEnviEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexEnvivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexGendEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexGendvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexGenfEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexGenfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexGeniEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexGenivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexEnvfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexEnvivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexGendvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexGenfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexGenivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexParameteriEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexParameterivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexParameterfEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexParameterfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexSubImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexSubImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCopyMultiTexImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCopyMultiTexImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCopyMultiTexSubImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCopyMultiTexSubImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexImageEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexParameterfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexParameterivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexLevelParameterfvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetMultiTexLevelParameterivEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMultiTexSubImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCopyMultiTexSubImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glEnableClientStateIndexedEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glDisableClientStateIndexedEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetFloatIndexedvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetDoubleIndexedvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetPointerIndexedvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glEnableIndexedEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glDisableIndexedEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glIsEnabledIndexedEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetIntegerIndexedvEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetBooleanIndexedvEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glNamedProgramStringEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glNamedProgramLocalParameter4dEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glNamedProgramLocalParameter4dvEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glNamedProgramLocalParameter4fEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glNamedProgramLocalParameter4fvEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glGetNamedProgramLocalParameterdvEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glGetNamedProgramLocalParameterfvEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glGetNamedProgramivEXT : -1L, set.contains("GL_ARB_vertex_program") ? gLCapabilities.glGetNamedProgramStringEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedTextureImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedTextureImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedTextureImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedTextureSubImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedTextureSubImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedTextureSubImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetCompressedTextureImageEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedMultiTexImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedMultiTexImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedMultiTexImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedMultiTexSubImage3DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedMultiTexSubImage2DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glCompressedMultiTexSubImage1DEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glGetCompressedMultiTexImageEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMatrixLoadTransposefEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMatrixLoadTransposedEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMatrixMultTransposefEXT : -1L, set.contains("OpenGL13") ? gLCapabilities.glMatrixMultTransposedEXT : -1L, set.contains("OpenGL15") ? gLCapabilities.glNamedBufferDataEXT : -1L, set.contains("OpenGL15") ? gLCapabilities.glNamedBufferSubDataEXT : -1L, set.contains("OpenGL15") ? gLCapabilities.glMapNamedBufferEXT : -1L, set.contains("OpenGL15") ? gLCapabilities.glUnmapNamedBufferEXT : -1L, set.contains("OpenGL15") ? gLCapabilities.glGetNamedBufferParameterivEXT : -1L, set.contains("OpenGL15") ? gLCapabilities.glGetNamedBufferSubDataEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform1fEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform2fEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform3fEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform4fEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform1iEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform2iEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform3iEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform4iEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform1fvEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform2fvEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform3fvEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform4fvEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform1ivEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform2ivEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform3ivEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniform4ivEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniformMatrix2fvEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniformMatrix3fvEXT : -1L, set.contains("OpenGL20") ? gLCapabilities.glProgramUniformMatrix4fvEXT : -1L, set.contains("OpenGL21") ? gLCapabilities.glProgramUniformMatrix2x3fvEXT : -1L, set.contains("OpenGL21") ? gLCapabilities.glProgramUniformMatrix3x2fvEXT : -1L, set.contains("OpenGL21") ? gLCapabilities.glProgramUniformMatrix2x4fvEXT : -1L, set.contains("OpenGL21") ? gLCapabilities.glProgramUniformMatrix4x2fvEXT : -1L, set.contains("OpenGL21") ? gLCapabilities.glProgramUniformMatrix3x4fvEXT : -1L, set.contains("OpenGL21") ? gLCapabilities.glProgramUniformMatrix4x3fvEXT : -1L, set.contains("GL_EXT_texture_buffer_object") ? gLCapabilities.glTextureBufferEXT : -1L, set.contains("GL_EXT_texture_buffer_object") ? gLCapabilities.glMultiTexBufferEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glTextureParameterIivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glTextureParameterIuivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glGetTextureParameterIivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glGetTextureParameterIuivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glMultiTexParameterIivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glMultiTexParameterIuivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glGetMultiTexParameterIivEXT : -1L, set.contains("GL_EXT_texture_integer") ? gLCapabilities.glGetMultiTexParameterIuivEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform1uiEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform2uiEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform3uiEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform4uiEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform1uivEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform2uivEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform3uivEXT : -1L, set.contains("GL_EXT_gpu_shader4") ? gLCapabilities.glProgramUniform4uivEXT : -1L, set.contains("GL_EXT_gpu_program_parameters") ? gLCapabilities.glNamedProgramLocalParameters4fvEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedProgramLocalParameterI4iEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedProgramLocalParameterI4ivEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedProgramLocalParametersI4ivEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedProgramLocalParameterI4uiEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedProgramLocalParameterI4uivEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedProgramLocalParametersI4uivEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glGetNamedProgramLocalParameterIivEXT : -1L, set.contains("GL_NV_gpu_program4") ? gLCapabilities.glGetNamedProgramLocalParameterIuivEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedRenderbufferStorageEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetNamedRenderbufferParameterivEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedRenderbufferStorageMultisampleEXT : -1L, set.contains("GL_NV_framebuffer_multisample_coverage") ? gLCapabilities.glNamedRenderbufferStorageMultisampleCoverageEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glCheckNamedFramebufferStatusEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedFramebufferTexture1DEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedFramebufferTexture2DEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedFramebufferTexture3DEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedFramebufferRenderbufferEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetNamedFramebufferAttachmentParameterivEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGenerateTextureMipmapEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGenerateMultiTexMipmapEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glFramebufferDrawBufferEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glFramebufferDrawBuffersEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glFramebufferReadBufferEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetFramebufferParameterivEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glNamedCopyBufferSubDataEXT : -1L, set.contains("GL_EXT_geometry_shader4") || set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedFramebufferTextureEXT : -1L, set.contains("GL_EXT_geometry_shader4") || set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedFramebufferTextureLayerEXT : -1L, set.contains("GL_EXT_geometry_shader4") || set.contains("GL_NV_gpu_program4") ? gLCapabilities.glNamedFramebufferTextureFaceEXT : -1L, set.contains("GL_NV_explicit_multisample") ? gLCapabilities.glTextureRenderbufferEXT : -1L, set.contains("GL_NV_explicit_multisample") ? gLCapabilities.glMultiTexRenderbufferEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayVertexOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayColorOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayEdgeFlagOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayIndexOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayNormalOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayTexCoordOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayMultiTexCoordOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayFogCoordOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArraySecondaryColorOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayVertexAttribOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glVertexArrayVertexAttribIOffsetEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glEnableVertexArrayEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glDisableVertexArrayEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glEnableVertexArrayAttribEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glDisableVertexArrayAttribEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetVertexArrayIntegervEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetVertexArrayPointervEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetVertexArrayIntegeri_vEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glGetVertexArrayPointeri_vEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glMapNamedBufferRangeEXT : -1L, set.contains("OpenGL30") ? gLCapabilities.glFlushMappedNamedBufferRangeEXT : -1L);
    }

    public static native void glClientAttribDefaultEXT(@NativeType(value="GLbitfield") int var0);

    public static native void glPushClientAttribDefaultEXT(@NativeType(value="GLbitfield") int var0);

    public static native void nglMatrixLoadfEXT(int var0, long var1);

    public static void glMatrixLoadfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixLoadfEXT(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMatrixLoaddEXT(int var0, long var1);

    public static void glMatrixLoaddEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixLoaddEXT(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglMatrixMultfEXT(int var0, long var1);

    public static void glMatrixMultfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixMultfEXT(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMatrixMultdEXT(int var0, long var1);

    public static void glMatrixMultdEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixMultdEXT(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glMatrixLoadIdentityEXT(@NativeType(value="GLenum") int var0);

    public static native void glMatrixRotatefEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glMatrixRotatedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7);

    public static native void glMatrixScalefEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glMatrixScaledEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void glMatrixTranslatefEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glMatrixTranslatedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5);

    public static native void glMatrixOrthoEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7, @NativeType(value="GLdouble") double var9, @NativeType(value="GLdouble") double var11);

    public static native void glMatrixFrustumEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7, @NativeType(value="GLdouble") double var9, @NativeType(value="GLdouble") double var11);

    public static native void glMatrixPushEXT(@NativeType(value="GLenum") int var0);

    public static native void glMatrixPopEXT(@NativeType(value="GLenum") int var0);

    public static native void glTextureParameteriEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3);

    public static native void nglTextureParameterivEXT(int var0, int var1, int var2, long var3);

    public static void glTextureParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglTextureParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glTextureParameterfEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLfloat") float var3);

    public static native void nglTextureParameterfvEXT(int var0, int var1, int var2, long var3);

    public static void glTextureParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        EXTDirectStateAccess.nglTextureParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglTextureImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglTextureImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglTextureSubImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglTextureSubImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glCopyTextureImage1DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLint") int var7);

    public static native void glCopyTextureImage2DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLsizei") int var7, @NativeType(value="GLint") int var8);

    public static native void glCopyTextureSubImage1DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6);

    public static native void glCopyTextureSubImage2DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6, @NativeType(value="GLsizei") int var7, @NativeType(value="GLsizei") int var8);

    public static native void nglGetTextureImageEXT(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") long l) {
        EXTDirectStateAccess.nglGetTextureImageEXT(n, n2, n3, n4, n5, l);
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglGetTextureImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetTextureParameterfvEXT(int var0, int var1, int var2, long var3);

    public static void glGetTextureParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetTextureParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetTextureParameterfEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetTextureParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetTextureParameterivEXT(int var0, int var1, int var2, long var3);

    public static void glGetTextureParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetTextureParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTextureParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetTextureParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetTextureLevelParameterfvEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glGetTextureLevelParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetTextureLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetTextureLevelParameterfEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetTextureLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglGetTextureLevelParameterivEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glGetTextureLevelParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetTextureLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTextureLevelParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetTextureLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
            int n6 = intBuffer.get(0);
            return n6;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglTextureImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglTextureSubImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11);

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, l);
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glCopyTextureSubImage3DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6, @NativeType(value="GLint") int var7, @NativeType(value="GLsizei") int var8, @NativeType(value="GLsizei") int var9);

    public static native void glBindMultiTextureEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void nglMultiTexCoordPointerEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexCoordPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexCoordPointerEXT(n, n2, n3, n4, l);
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexCoordPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexCoordPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexCoordPointerEXT(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glMultiTexEnvfEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLfloat") float var3);

    public static native void nglMultiTexEnvfvEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexEnvfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexEnvfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glMultiTexEnviEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3);

    public static native void nglMultiTexEnvivEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexEnvivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexEnvivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glMultiTexGendEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLdouble") double var3);

    public static native void nglMultiTexGendvEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexGendvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexGendvEXT(n, n2, n3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glMultiTexGenfEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLfloat") float var3);

    public static native void nglMultiTexGenfvEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexGenfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexGenfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glMultiTexGeniEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3);

    public static native void nglMultiTexGenivEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexGenivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexGenivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetMultiTexEnvfvEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexEnvfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexEnvfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMultiTexEnvfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetMultiTexEnvfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexEnvivEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexEnvivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexEnvivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMultiTexEnviEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetMultiTexEnvivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexGendvEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexGendvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexGendvEXT(n, n2, n3, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetMultiTexGendEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            EXTDirectStateAccess.nglGetMultiTexGendvEXT(n, n2, n3, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexGenfvEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexGenfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexGenfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMultiTexGenfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetMultiTexGenfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexGenivEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexGenivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexGenivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMultiTexGeniEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetMultiTexGenivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void glMultiTexParameteriEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3);

    public static native void nglMultiTexParameterivEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glMultiTexParameterfEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLfloat") float var3);

    public static native void nglMultiTexParameterfvEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexParameterfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultiTexImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglMultiTexImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglMultiTexSubImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(intBuffer));
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglMultiTexSubImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(intBuffer));
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glCopyMultiTexImage1DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLint") int var7);

    public static native void glCopyMultiTexImage2DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLsizei") int var7, @NativeType(value="GLint") int var8);

    public static native void glCopyMultiTexSubImage1DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6);

    public static native void glCopyMultiTexSubImage2DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6, @NativeType(value="GLsizei") int var7, @NativeType(value="GLsizei") int var8);

    public static native void nglGetMultiTexImageEXT(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") long l) {
        EXTDirectStateAccess.nglGetMultiTexImageEXT(n, n2, n3, n4, n5, l);
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglGetMultiTexImageEXT(n, n2, n3, n4, n5, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetMultiTexParameterfvEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexParameterfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMultiTexParameterfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetMultiTexParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexParameterivEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMultiTexParameteriEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetMultiTexParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexLevelParameterfvEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glGetMultiTexLevelParameterfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMultiTexLevelParameterfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetMultiTexLevelParameterfvEXT(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglGetMultiTexLevelParameterivEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glGetMultiTexLevelParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMultiTexLevelParameteriEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetMultiTexLevelParameterivEXT(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
            int n6 = intBuffer.get(0);
            return n6;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nglMultiTexImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglMultiTexSubImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11);

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, l);
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(intBuffer));
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glCopyMultiTexSubImage3DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6, @NativeType(value="GLint") int var7, @NativeType(value="GLsizei") int var8, @NativeType(value="GLsizei") int var9);

    public static native void glEnableClientStateIndexedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDisableClientStateIndexedEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glEnableClientStateiEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDisableClientStateiEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglGetFloatIndexedvEXT(int var0, int var1, long var2);

    public static void glGetFloatIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetFloatIndexedvEXT(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetFloatIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetFloatIndexedvEXT(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetDoubleIndexedvEXT(int var0, int var1, long var2);

    public static void glGetDoubleIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        EXTDirectStateAccess.nglGetDoubleIndexedvEXT(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetDoubleIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            EXTDirectStateAccess.nglGetDoubleIndexedvEXT(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetPointerIndexedvEXT(int var0, int var1, long var2);

    public static void glGetPointerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        EXTDirectStateAccess.nglGetPointerIndexedvEXT(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetPointerIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            EXTDirectStateAccess.nglGetPointerIndexedvEXT(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetFloati_vEXT(int var0, int var1, long var2);

    public static void glGetFloati_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        EXTDirectStateAccess.nglGetFloati_vEXT(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetFloatiEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            EXTDirectStateAccess.nglGetFloati_vEXT(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetDoublei_vEXT(int var0, int var1, long var2);

    public static void glGetDoublei_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        EXTDirectStateAccess.nglGetDoublei_vEXT(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetDoubleiEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            EXTDirectStateAccess.nglGetDoublei_vEXT(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetPointeri_vEXT(int var0, int var1, long var2);

    public static void glGetPointeri_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        EXTDirectStateAccess.nglGetPointeri_vEXT(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetPointeriEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            EXTDirectStateAccess.nglGetPointeri_vEXT(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glEnableIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        EXTDrawBuffers2.glEnableIndexedEXT(n, n2);
    }

    public static void glDisableIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        EXTDrawBuffers2.glDisableIndexedEXT(n, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsEnabledIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return EXTDrawBuffers2.glIsEnabledIndexedEXT(n, n2);
    }

    public static void nglGetIntegerIndexedvEXT(int n, int n2, long l) {
        EXTDrawBuffers2.nglGetIntegerIndexedvEXT(n, n2, l);
    }

    public static void glGetIntegerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        EXTDrawBuffers2.glGetIntegerIndexedvEXT(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetIntegerIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return EXTDrawBuffers2.glGetIntegerIndexedEXT(n, n2);
    }

    public static void nglGetBooleanIndexedvEXT(int n, int n2, long l) {
        EXTDrawBuffers2.nglGetBooleanIndexedvEXT(n, n2, l);
    }

    public static void glGetBooleanIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        EXTDrawBuffers2.glGetBooleanIndexedvEXT(n, n2, byteBuffer);
    }

    @NativeType(value="void")
    public static boolean glGetBooleanIndexedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return EXTDrawBuffers2.glGetBooleanIndexedEXT(n, n2);
    }

    public static native void nglNamedProgramStringEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glNamedProgramStringEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglNamedProgramStringEXT(n, n2, n3, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void glNamedProgramLocalParameter4dEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLdouble") double var3, @NativeType(value="GLdouble") double var5, @NativeType(value="GLdouble") double var7, @NativeType(value="GLdouble") double var9);

    public static native void nglNamedProgramLocalParameter4dvEXT(int var0, int var1, int var2, long var3);

    public static void glNamedProgramLocalParameter4dvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        EXTDirectStateAccess.nglNamedProgramLocalParameter4dvEXT(n, n2, n3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glNamedProgramLocalParameter4fEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5, @NativeType(value="GLfloat") float var6);

    public static native void nglNamedProgramLocalParameter4fvEXT(int var0, int var1, int var2, long var3);

    public static void glNamedProgramLocalParameter4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        EXTDirectStateAccess.nglNamedProgramLocalParameter4fvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetNamedProgramLocalParameterdvEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedProgramLocalParameterdvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        EXTDirectStateAccess.nglGetNamedProgramLocalParameterdvEXT(n, n2, n3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetNamedProgramLocalParameterfvEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedProgramLocalParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        EXTDirectStateAccess.nglGetNamedProgramLocalParameterfvEXT(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetNamedProgramivEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedProgramivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetNamedProgramivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetNamedProgramiEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetNamedProgramivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetNamedProgramStringEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedProgramStringEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.check((Buffer)byteBuffer, EXTDirectStateAccess.glGetNamedProgramiEXT(n, n2, 34343));
        }
        EXTDirectStateAccess.nglGetNamedProgramStringEXT(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglCompressedTextureImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glCompressedTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLsizei") int n9, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glCompressedTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedTextureImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglCompressedTextureImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static void glCompressedTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLsizei") int n8, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glCompressedTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedTextureImage2DEXT(n, n2, n3, n4, n5, n6, n7, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglCompressedTextureImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glCompressedTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedTextureImage1DEXT(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glCompressedTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedTextureImage1DEXT(n, n2, n3, n4, n5, n6, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglCompressedTextureSubImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11);

    public static void glCompressedTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLsizei") int n11, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, l);
    }

    public static void glCompressedTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedTextureSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglCompressedTextureSubImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glCompressedTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glCompressedTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedTextureSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglCompressedTextureSubImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glCompressedTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glCompressedTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedTextureSubImage1DEXT(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetCompressedTextureImageEXT(int var0, int var1, int var2, long var3);

    public static void glGetCompressedTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.check((Buffer)byteBuffer, EXTDirectStateAccess.glGetTextureLevelParameteriEXT(n, n2, n3, 34464));
        }
        EXTDirectStateAccess.nglGetCompressedTextureImageEXT(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetCompressedTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="void *") long l) {
        EXTDirectStateAccess.nglGetCompressedTextureImageEXT(n, n2, n3, l);
    }

    public static native void nglCompressedMultiTexImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glCompressedMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLsizei") int n9, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glCompressedMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedMultiTexImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglCompressedMultiTexImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static void glCompressedMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLsizei") int n8, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glCompressedMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedMultiTexImage2DEXT(n, n2, n3, n4, n5, n6, n7, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglCompressedMultiTexImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glCompressedMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @Nullable @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glCompressedMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedMultiTexImage1DEXT(n, n2, n3, n4, n5, n6, Checks.remainingSafe(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static native void nglCompressedMultiTexSubImage3DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, long var11);

    public static void glCompressedMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLsizei") int n11, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, l);
    }

    public static void glCompressedMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedMultiTexSubImage3DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglCompressedMultiTexSubImage2DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glCompressedMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glCompressedMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedMultiTexSubImage2DEXT(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglCompressedMultiTexSubImage1DEXT(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glCompressedMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="void const *") long l) {
        EXTDirectStateAccess.nglCompressedMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glCompressedMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglCompressedMultiTexSubImage1DEXT(n, n2, n3, n4, n5, n6, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglGetCompressedMultiTexImageEXT(int var0, int var1, int var2, long var3);

    public static void glGetCompressedMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS && Checks.DEBUG) {
            Checks.check((Buffer)byteBuffer, EXTDirectStateAccess.glGetMultiTexLevelParameteriEXT(n, n2, n3, 34464));
        }
        EXTDirectStateAccess.nglGetCompressedMultiTexImageEXT(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetCompressedMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="void *") long l) {
        EXTDirectStateAccess.nglGetCompressedMultiTexImageEXT(n, n2, n3, l);
    }

    public static native void nglMatrixLoadTransposefEXT(int var0, long var1);

    public static void glMatrixLoadTransposefEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixLoadTransposefEXT(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMatrixLoadTransposedEXT(int var0, long var1);

    public static void glMatrixLoadTransposedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixLoadTransposedEXT(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglMatrixMultTransposefEXT(int var0, long var1);

    public static void glMatrixMultTransposefEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixMultTransposefEXT(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMatrixMultTransposedEXT(int var0, long var1);

    public static void glMatrixMultTransposedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        EXTDirectStateAccess.nglMatrixMultTransposedEXT(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglNamedBufferDataEXT(int var0, long var1, long var3, int var5);

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLenum") int n2) {
        EXTDirectStateAccess.nglNamedBufferDataEXT(n, l, 0L, n2);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLenum") int n2) {
        EXTDirectStateAccess.nglNamedBufferDataEXT(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLenum") int n2) {
        EXTDirectStateAccess.nglNamedBufferDataEXT(n, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2) {
        EXTDirectStateAccess.nglNamedBufferDataEXT(n, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer), n2);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLenum") int n2) {
        EXTDirectStateAccess.nglNamedBufferDataEXT(n, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer), n2);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLenum") int n2) {
        EXTDirectStateAccess.nglNamedBufferDataEXT(n, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer), n2);
    }

    public static native void nglNamedBufferSubDataEXT(int var0, long var1, long var3, long var5);

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglNamedBufferSubDataEXT(n, l, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native long nglMapNamedBufferEXT(int var0, int var1);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        long l = EXTDirectStateAccess.nglMapNamedBufferEXT(n, n2);
        return MemoryUtil.memByteBufferSafe(l, EXTDirectStateAccess.glGetNamedBufferParameteriEXT(n, 34660));
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @Nullable ByteBuffer byteBuffer) {
        long l = EXTDirectStateAccess.nglMapNamedBufferEXT(n, n2);
        int n3 = EXTDirectStateAccess.glGetNamedBufferParameteriEXT(n, 34660);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l, n3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, long l, @Nullable ByteBuffer byteBuffer) {
        long l2 = EXTDirectStateAccess.nglMapNamedBufferEXT(n, n2);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l2, (int)l);
    }

    @NativeType(value="GLboolean")
    public static native boolean glUnmapNamedBufferEXT(@NativeType(value="GLuint") int var0);

    public static native void nglGetNamedBufferParameterivEXT(int var0, int var1, long var2);

    public static void glGetNamedBufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetNamedBufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetNamedBufferParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetNamedBufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetNamedBufferSubDataEXT(int var0, long var1, long var3, long var5);

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ByteBuffer byteBuffer) {
        EXTDirectStateAccess.nglGetNamedBufferSubDataEXT(n, l, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ShortBuffer shortBuffer) {
        EXTDirectStateAccess.nglGetNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglGetNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglGetNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        EXTDirectStateAccess.nglGetNamedBufferSubDataEXT(n, l, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glProgramUniform1fEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2);

    public static native void glProgramUniform2fEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glProgramUniform3fEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4);

    public static native void glProgramUniform4fEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5);

    public static native void glProgramUniform1iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glProgramUniform2iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glProgramUniform3iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void glProgramUniform4iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5);

    public static native void nglProgramUniform1fvEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniform1fvEXT(n, n2, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform2fvEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniform2fvEXT(n, n2, floatBuffer.remaining() >> 1, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform3fvEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniform3fvEXT(n, n2, floatBuffer.remaining() / 3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform4fvEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniform4fvEXT(n, n2, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniform1ivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform1ivEXT(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform2ivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform2ivEXT(n, n2, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform3ivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform3ivEXT(n, n2, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform4ivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform4ivEXT(n, n2, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniformMatrix2fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix2fvEXT(n, n2, floatBuffer.remaining() >> 2, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix3fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix3fvEXT(n, n2, floatBuffer.remaining() / 9, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix4fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix4fvEXT(n, n2, floatBuffer.remaining() >> 4, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix2x3fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2x3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix2x3fvEXT(n, n2, floatBuffer.remaining() / 6, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix3x2fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3x2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix3x2fvEXT(n, n2, floatBuffer.remaining() / 6, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix2x4fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix2x4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix2x4fvEXT(n, n2, floatBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix4x2fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4x2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix4x2fvEXT(n, n2, floatBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix3x4fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix3x4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix3x4fvEXT(n, n2, floatBuffer.remaining() / 12, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglProgramUniformMatrix4x3fvEXT(int var0, int var1, int var2, boolean var3, long var4);

    public static void glProgramUniformMatrix4x3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglProgramUniformMatrix4x3fvEXT(n, n2, floatBuffer.remaining() / 12, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glTextureBufferEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3);

    public static native void glMultiTexBufferEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3);

    public static native void nglTextureParameterIivEXT(int var0, int var1, int var2, long var3);

    public static void glTextureParameterIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglTextureParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTextureParameterIuivEXT(int var0, int var1, int var2, long var3);

    public static void glTextureParameterIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglTextureParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetTextureParameterIivEXT(int var0, int var1, int var2, long var3);

    public static void glGetTextureParameterIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetTextureParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTextureParameterIiEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetTextureParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetTextureParameterIuivEXT(int var0, int var1, int var2, long var3);

    public static void glGetTextureParameterIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetTextureParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTextureParameterIuiEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetTextureParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglMultiTexParameterIivEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexParameterIivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMultiTexParameterIuivEXT(int var0, int var1, int var2, long var3);

    public static void glMultiTexParameterIuivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglMultiTexParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetMultiTexParameterIivEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexParameterIivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMultiTexParameterIiEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetMultiTexParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetMultiTexParameterIuivEXT(int var0, int var1, int var2, long var3);

    public static void glGetMultiTexParameterIuivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetMultiTexParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMultiTexParameterIuiEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetMultiTexParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void glProgramUniform1uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glProgramUniform2uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3);

    public static native void glProgramUniform3uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint") int var4);

    public static native void glProgramUniform4uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint") int var4, @NativeType(value="GLuint") int var5);

    public static native void nglProgramUniform1uivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform1uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform1uivEXT(n, n2, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform2uivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform2uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform2uivEXT(n, n2, intBuffer.remaining() >> 1, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform3uivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform3uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform3uivEXT(n, n2, intBuffer.remaining() / 3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglProgramUniform4uivEXT(int var0, int var1, int var2, long var3);

    public static void glProgramUniform4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglProgramUniform4uivEXT(n, n2, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglNamedProgramLocalParameters4fvEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glNamedProgramLocalParameters4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        EXTDirectStateAccess.nglNamedProgramLocalParameters4fvEXT(n, n2, n3, floatBuffer.remaining() >> 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glNamedProgramLocalParameterI4iEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6);

    public static native void nglNamedProgramLocalParameterI4ivEXT(int var0, int var1, int var2, long var3);

    public static void glNamedProgramLocalParameterI4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglNamedProgramLocalParameterI4ivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglNamedProgramLocalParametersI4ivEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glNamedProgramLocalParametersI4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglNamedProgramLocalParametersI4ivEXT(n, n2, n3, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glNamedProgramLocalParameterI4uiEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLuint") int var4, @NativeType(value="GLuint") int var5, @NativeType(value="GLuint") int var6);

    public static native void nglNamedProgramLocalParameterI4uivEXT(int var0, int var1, int var2, long var3);

    public static void glNamedProgramLocalParameterI4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglNamedProgramLocalParameterI4uivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglNamedProgramLocalParametersI4uivEXT(int var0, int var1, int var2, int var3, long var4);

    public static void glNamedProgramLocalParametersI4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglNamedProgramLocalParametersI4uivEXT(n, n2, n3, intBuffer.remaining() >> 2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetNamedProgramLocalParameterIivEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedProgramLocalParameterIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglGetNamedProgramLocalParameterIivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetNamedProgramLocalParameterIuivEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedProgramLocalParameterIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        EXTDirectStateAccess.nglGetNamedProgramLocalParameterIuivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glNamedRenderbufferStorageEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static native void nglGetNamedRenderbufferParameterivEXT(int var0, int var1, long var2);

    public static void glGetNamedRenderbufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetNamedRenderbufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetNamedRenderbufferParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetNamedRenderbufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glNamedRenderbufferStorageMultisampleEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4);

    public static native void glNamedRenderbufferStorageMultisampleCoverageEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5);

    @NativeType(value="GLenum")
    public static native int glCheckNamedFramebufferStatusEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glNamedFramebufferTexture1DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLint") int var4);

    public static native void glNamedFramebufferTexture2DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLint") int var4);

    public static native void glNamedFramebufferTexture3DEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5);

    public static native void glNamedFramebufferRenderbufferEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3);

    public static native void nglGetNamedFramebufferAttachmentParameterivEXT(int var0, int var1, int var2, long var3);

    public static void glGetNamedFramebufferAttachmentParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetNamedFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetNamedFramebufferAttachmentParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetNamedFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void glGenerateTextureMipmapEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glGenerateMultiTexMipmapEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    public static native void glFramebufferDrawBufferEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void nglFramebufferDrawBuffersEXT(int var0, int var1, long var2);

    public static void glFramebufferDrawBuffersEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer) {
        EXTDirectStateAccess.nglFramebufferDrawBuffersEXT(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void glFramebufferReadBufferEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void nglGetFramebufferParameterivEXT(int var0, int var1, long var2);

    public static void glGetFramebufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetFramebufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetFramebufferParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetFramebufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glNamedCopyBufferSubDataEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLintptr") long var2, @NativeType(value="GLintptr") long var4, @NativeType(value="GLsizeiptr") long var6);

    public static native void glNamedFramebufferTextureEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3);

    public static native void glNamedFramebufferTextureLayerEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void glNamedFramebufferTextureFaceEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4);

    public static native void glTextureRenderbufferEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void glMultiTexRenderbufferEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2);

    public static native void glVertexArrayVertexOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLintptr") long var5);

    public static native void glVertexArrayColorOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLintptr") long var5);

    public static native void glVertexArrayEdgeFlagOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLintptr") long var3);

    public static native void glVertexArrayIndexOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLintptr") long var4);

    public static native void glVertexArrayNormalOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLintptr") long var4);

    public static native void glVertexArrayTexCoordOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLintptr") long var5);

    public static native void glVertexArrayMultiTexCoordOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLintptr") long var6);

    public static native void glVertexArrayFogCoordOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLintptr") long var4);

    public static native void glVertexArraySecondaryColorOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLenum") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLintptr") long var5);

    public static native void glVertexArrayVertexAttribOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4, @NativeType(value="GLboolean") boolean var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLintptr") long var7);

    public static native void glVertexArrayVertexAttribIOffsetEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLenum") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLintptr") long var6);

    public static native void glEnableVertexArrayEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glDisableVertexArrayEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glEnableVertexArrayAttribEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void glDisableVertexArrayAttribEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglGetVertexArrayIntegervEXT(int var0, int var1, long var2);

    public static void glGetVertexArrayIntegervEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetVertexArrayIntegervEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetVertexArrayIntegerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetVertexArrayIntegervEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetVertexArrayPointervEXT(int var0, int var1, long var2);

    public static void glGetVertexArrayPointervEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        EXTDirectStateAccess.nglGetVertexArrayPointervEXT(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexArrayPointerEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            EXTDirectStateAccess.nglGetVertexArrayPointervEXT(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetVertexArrayIntegeri_vEXT(int var0, int var1, int var2, long var3);

    public static void glGetVertexArrayIntegeri_vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTDirectStateAccess.nglGetVertexArrayIntegeri_vEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetVertexArrayIntegeriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTDirectStateAccess.nglGetVertexArrayIntegeri_vEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetVertexArrayPointeri_vEXT(int var0, int var1, int var2, long var3);

    public static void glGetVertexArrayPointeri_vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        EXTDirectStateAccess.nglGetVertexArrayPointeri_vEXT(n, n2, n3, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexArrayPointeriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            EXTDirectStateAccess.nglGetVertexArrayPointeri_vEXT(n, n2, n3, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native long nglMapNamedBufferRangeEXT(int var0, long var1, long var3, int var5);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferRangeEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2) {
        long l3 = EXTDirectStateAccess.nglMapNamedBufferRangeEXT(n, l, l2, n2);
        return MemoryUtil.memByteBufferSafe(l3, (int)l2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapNamedBufferRangeEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2, @Nullable ByteBuffer byteBuffer) {
        long l3 = EXTDirectStateAccess.nglMapNamedBufferRangeEXT(n, l, l2, n2);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l3, (int)l2);
    }

    public static native void glFlushMappedNamedBufferRangeEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLintptr") long var1, @NativeType(value="GLsizeiptr") long var3);

    public static void glMatrixLoadfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMatrixLoadfEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMatrixLoaddEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMatrixLoaddEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glMatrixMultfEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMatrixMultfEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMatrixMultdEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMatrixMultdEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glTextureParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTextureParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glTextureParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTextureParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTextureImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, sArray, l);
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTextureImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, nArray, l);
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTextureImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, fArray, l);
    }

    public static void glTextureImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTextureImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, dArray, l);
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTextureImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, sArray, l);
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTextureImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, nArray, l);
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTextureImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, fArray, l);
    }

    public static void glTextureImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTextureImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, dArray, l);
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTextureSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, sArray, l);
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTextureSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, nArray, l);
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTextureSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, fArray, l);
    }

    public static void glTextureSubImage1DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTextureSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, dArray, l);
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTextureSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, sArray, l);
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTextureSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, nArray, l);
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTextureSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, fArray, l);
    }

    public static void glTextureSubImage2DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTextureSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, dArray, l);
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glGetTextureImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, sArray, l);
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetTextureImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, nArray, l);
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetTextureImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, fArray, l);
    }

    public static void glGetTextureImageEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") double[] dArray) {
        long l = GL.getICD().glGetTextureImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, dArray, l);
    }

    public static void glGetTextureParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetTextureParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetTextureParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTextureParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetTextureLevelParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetTextureLevelParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, n4, fArray, l);
    }

    public static void glGetTextureLevelParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTextureLevelParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTextureImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray, l);
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTextureImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray, l);
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTextureImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray, l);
    }

    public static void glTextureImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTextureImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray, l);
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTextureSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, sArray, l);
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTextureSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, nArray, l);
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTextureSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, fArray, l);
    }

    public static void glTextureSubImage3DEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTextureSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, dArray, l);
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoordPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, sArray, l);
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexCoordPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glMultiTexCoordPointerEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexCoordPointerEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, fArray, l);
    }

    public static void glMultiTexEnvfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexEnvfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glMultiTexEnvivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexEnvivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glMultiTexGendvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultiTexGendvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, n3, dArray, l);
    }

    public static void glMultiTexGenfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexGenfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glMultiTexGenivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexGenivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetMultiTexEnvfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMultiTexEnvfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetMultiTexEnvivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexEnvivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetMultiTexGendvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetMultiTexGendvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, n2, n3, dArray, l);
    }

    public static void glGetMultiTexGenfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMultiTexGenfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetMultiTexGenivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexGenivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glMultiTexParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glMultiTexParameterfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultiTexParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, sArray, l);
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, nArray, l);
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, fArray, l);
    }

    public static void glMultiTexImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glMultiTexImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, dArray, l);
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, sArray, l);
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, nArray, l);
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, fArray, l);
    }

    public static void glMultiTexImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glMultiTexImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, dArray, l);
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, sArray, l);
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, nArray, l);
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, fArray, l);
    }

    public static void glMultiTexSubImage1DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glMultiTexSubImage1DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, dArray, l);
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, sArray, l);
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, nArray, l);
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, fArray, l);
    }

    public static void glMultiTexSubImage2DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glMultiTexSubImage2DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, dArray, l);
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glGetMultiTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, sArray, l);
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, nArray, l);
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetMultiTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, fArray, l);
    }

    public static void glGetMultiTexImageEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void *") double[] dArray) {
        long l = GL.getICD().glGetMultiTexImageEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, dArray, l);
    }

    public static void glGetMultiTexParameterfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMultiTexParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetMultiTexParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetMultiTexLevelParameterfvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMultiTexLevelParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, n4, fArray, l);
    }

    public static void glGetMultiTexLevelParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexLevelParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray, l);
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray, l);
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray, l);
    }

    public static void glMultiTexImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glMultiTexImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray, l);
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glMultiTexSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, sArray, l);
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glMultiTexSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, nArray, l);
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glMultiTexSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, fArray, l);
    }

    public static void glMultiTexSubImage3DEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLsizei") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="GLenum") int n11, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glMultiTexSubImage3DEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, dArray, l);
    }

    public static void glGetFloatIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetFloatIndexedvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetDoubleIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetDoubleIndexedvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetFloati_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetFloati_vEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetDoublei_vEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetDoublei_vEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetIntegerIndexedvEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") int[] nArray) {
        EXTDrawBuffers2.glGetIntegerIndexedvEXT(n, n2, nArray);
    }

    public static void glNamedProgramLocalParameter4dvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glNamedProgramLocalParameter4dvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, n3, dArray, l);
    }

    public static void glNamedProgramLocalParameter4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glNamedProgramLocalParameter4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetNamedProgramLocalParameterdvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetNamedProgramLocalParameterdvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, n3, dArray, l);
    }

    public static void glGetNamedProgramLocalParameterfvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetNamedProgramLocalParameterfvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetNamedProgramivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetNamedProgramivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glMatrixLoadTransposefEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMatrixLoadTransposefEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMatrixLoadTransposedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMatrixLoadTransposedEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glMatrixMultTransposefEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMatrixMultTransposefEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glMatrixMultTransposedEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMatrixMultTransposedEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glNamedBufferDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(sArray.length) << 1, sArray, n2, l);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glNamedBufferDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(nArray.length) << 2, nArray, n2, l);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glNamedBufferDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(fArray.length) << 2, fArray, n2, l);
    }

    public static void glNamedBufferDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glNamedBufferDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(dArray.length) << 3, dArray, n2, l);
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") short[] sArray) {
        long l2 = GL.getICD().glNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(sArray.length) << 1, sArray, l2);
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") int[] nArray) {
        long l2 = GL.getICD().glNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(nArray.length) << 2, nArray, l2);
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") float[] fArray) {
        long l2 = GL.getICD().glNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(fArray.length) << 2, fArray, l2);
    }

    public static void glNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") double[] dArray) {
        long l2 = GL.getICD().glNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(dArray.length) << 3, dArray, l2);
    }

    public static void glGetNamedBufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetNamedBufferParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") short[] sArray) {
        long l2 = GL.getICD().glGetNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(sArray.length) << 1, sArray, l2);
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") int[] nArray) {
        long l2 = GL.getICD().glGetNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(nArray.length) << 2, nArray, l2);
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") float[] fArray) {
        long l2 = GL.getICD().glGetNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(fArray.length) << 2, fArray, l2);
    }

    public static void glGetNamedBufferSubDataEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") double[] dArray) {
        long l2 = GL.getICD().glGetNamedBufferSubDataEXT;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(dArray.length) << 3, dArray, l2);
    }

    public static void glProgramUniform1fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform1fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length, fArray, l);
    }

    public static void glProgramUniform2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform2fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 1, fArray, l);
    }

    public static void glProgramUniform3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform3fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 3, fArray, l);
    }

    public static void glProgramUniform4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniform4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 2, fArray, l);
    }

    public static void glProgramUniform1ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform1ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glProgramUniform2ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform2ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 1, nArray, l);
    }

    public static void glProgramUniform3ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform3ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length / 3, nArray, l);
    }

    public static void glProgramUniform4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform4ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 2, nArray, l);
    }

    public static void glProgramUniformMatrix2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix2fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 2, bl, fArray, l);
    }

    public static void glProgramUniformMatrix3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix3fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 9, bl, fArray, l);
    }

    public static void glProgramUniformMatrix4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 4, bl, fArray, l);
    }

    public static void glProgramUniformMatrix2x3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix2x3fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 6, bl, fArray, l);
    }

    public static void glProgramUniformMatrix3x2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix3x2fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 6, bl, fArray, l);
    }

    public static void glProgramUniformMatrix2x4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix2x4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 3, bl, fArray, l);
    }

    public static void glProgramUniformMatrix4x2fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix4x2fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 3, bl, fArray, l);
    }

    public static void glProgramUniformMatrix3x4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix3x4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 12, bl, fArray, l);
    }

    public static void glProgramUniformMatrix4x3fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glProgramUniformMatrix4x3fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length / 12, bl, fArray, l);
    }

    public static void glTextureParameterIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTextureParameterIivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glTextureParameterIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glTextureParameterIuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetTextureParameterIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTextureParameterIivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetTextureParameterIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetTextureParameterIuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glMultiTexParameterIivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexParameterIivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glMultiTexParameterIuivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glMultiTexParameterIuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetMultiTexParameterIivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexParameterIivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetMultiTexParameterIuivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetMultiTexParameterIuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glProgramUniform1uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform1uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length, nArray, l);
    }

    public static void glProgramUniform2uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform2uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 1, nArray, l);
    }

    public static void glProgramUniform3uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform3uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length / 3, nArray, l);
    }

    public static void glProgramUniform4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glProgramUniform4uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray.length >> 2, nArray, l);
    }

    public static void glNamedProgramLocalParameters4fvEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glNamedProgramLocalParameters4fvEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, fArray.length >> 2, fArray, l);
    }

    public static void glNamedProgramLocalParameterI4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glNamedProgramLocalParameterI4ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glNamedProgramLocalParametersI4ivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glNamedProgramLocalParametersI4ivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, nArray.length >> 2, nArray, l);
    }

    public static void glNamedProgramLocalParameterI4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glNamedProgramLocalParameterI4uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glNamedProgramLocalParametersI4uivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glNamedProgramLocalParametersI4uivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, nArray.length >> 2, nArray, l);
    }

    public static void glGetNamedProgramLocalParameterIivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetNamedProgramLocalParameterIivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetNamedProgramLocalParameterIuivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetNamedProgramLocalParameterIuivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetNamedRenderbufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetNamedRenderbufferParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetNamedFramebufferAttachmentParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetNamedFramebufferAttachmentParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glFramebufferDrawBuffersEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum const *") int[] nArray) {
        long l = GL.getICD().glFramebufferDrawBuffersEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glGetFramebufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetFramebufferParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetVertexArrayIntegervEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetVertexArrayIntegervEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetVertexArrayIntegeri_vEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetVertexArrayIntegeri_vEXT;
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

