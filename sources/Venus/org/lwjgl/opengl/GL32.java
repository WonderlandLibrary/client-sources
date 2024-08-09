/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL32
extends GL31 {
    public static final int GL_CONTEXT_PROFILE_MASK = 37158;
    public static final int GL_CONTEXT_CORE_PROFILE_BIT = 1;
    public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
    public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
    public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
    public static final int GL_FIRST_VERTEX_CONVENTION = 36429;
    public static final int GL_LAST_VERTEX_CONVENTION = 36430;
    public static final int GL_PROVOKING_VERTEX = 36431;
    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
    public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
    public static final int GL_SAMPLE_POSITION = 36432;
    public static final int GL_SAMPLE_MASK = 36433;
    public static final int GL_SAMPLE_MASK_VALUE = 36434;
    public static final int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    public static final int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    public static final int GL_MAX_INTEGER_SAMPLES = 37136;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    public static final int GL_TEXTURE_SAMPLES = 37126;
    public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    public static final int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
    public static final int GL_DEPTH_CLAMP = 34383;
    public static final int GL_GEOMETRY_SHADER = 36313;
    public static final int GL_GEOMETRY_VERTICES_OUT = 36314;
    public static final int GL_GEOMETRY_INPUT_TYPE = 36315;
    public static final int GL_GEOMETRY_OUTPUT_TYPE = 36316;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
    public static final int GL_LINES_ADJACENCY = 10;
    public static final int GL_LINE_STRIP_ADJACENCY = 11;
    public static final int GL_TRIANGLES_ADJACENCY = 12;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY = 13;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
    public static final int GL_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static final int GL_OBJECT_TYPE = 37138;
    public static final int GL_SYNC_CONDITION = 37139;
    public static final int GL_SYNC_STATUS = 37140;
    public static final int GL_SYNC_FLAGS = 37141;
    public static final int GL_SYNC_FENCE = 37142;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_UNSIGNALED = 37144;
    public static final int GL_SIGNALED = 37145;
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final long GL_TIMEOUT_IGNORED = -1L;
    public static final int GL_ALREADY_SIGNALED = 37146;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_CONDITION_SATISFIED = 37148;
    public static final int GL_WAIT_FAILED = 37149;

    protected GL32() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetBufferParameteri64v, gLCapabilities.glDrawElementsBaseVertex, gLCapabilities.glDrawRangeElementsBaseVertex, gLCapabilities.glDrawElementsInstancedBaseVertex, gLCapabilities.glMultiDrawElementsBaseVertex, gLCapabilities.glProvokingVertex, gLCapabilities.glTexImage2DMultisample, gLCapabilities.glTexImage3DMultisample, gLCapabilities.glGetMultisamplefv, gLCapabilities.glSampleMaski, gLCapabilities.glFramebufferTexture, gLCapabilities.glFenceSync, gLCapabilities.glIsSync, gLCapabilities.glDeleteSync, gLCapabilities.glClientWaitSync, gLCapabilities.glWaitSync, gLCapabilities.glGetInteger64v, gLCapabilities.glGetInteger64i_v, gLCapabilities.glGetSynciv);
    }

    public static void nglGetBufferParameteri64v(int n, int n2, long l) {
        GL32C.nglGetBufferParameteri64v(n, n2, l);
    }

    public static void glGetBufferParameteri64v(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL32C.glGetBufferParameteri64v(n, n2, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetBufferParameteri64(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL32C.glGetBufferParameteri64(n, n2);
    }

    public static void nglDrawElementsBaseVertex(int n, int n2, int n3, long l, int n4) {
        GL32C.nglDrawElementsBaseVertex(n, n2, n3, l, n4);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLint") int n4) {
        GL32C.glDrawElementsBaseVertex(n, n2, n3, l, n4);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsBaseVertex(n, n2, byteBuffer, n3);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n2) {
        GL32C.glDrawElementsBaseVertex(n, byteBuffer, n2);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLint") int n2) {
        GL32C.glDrawElementsBaseVertex(n, shortBuffer, n2);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLint") int n2) {
        GL32C.glDrawElementsBaseVertex(n, intBuffer, n2);
    }

    public static void nglDrawRangeElementsBaseVertex(int n, int n2, int n3, int n4, int n5, long l, int n6) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, l, n6);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l, @NativeType(value="GLint") int n6) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, l, n6);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n5) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, n4, byteBuffer, n5);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n4) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, byteBuffer, n4);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLint") int n4) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, shortBuffer, n4);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLint") int n4) {
        GL32C.glDrawRangeElementsBaseVertex(n, n2, n3, intBuffer, n4);
    }

    public static void nglDrawElementsInstancedBaseVertex(int n, int n2, int n3, long l, int n4, int n5) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5) {
        GL32C.glDrawElementsInstancedBaseVertex(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint") int n4) {
        GL32C.glDrawElementsInstancedBaseVertex(n, n2, byteBuffer, n3, n4);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsInstancedBaseVertex(n, byteBuffer, n2, n3);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsInstancedBaseVertex(n, shortBuffer, n2, n3);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.glDrawElementsInstancedBaseVertex(n, intBuffer, n2, n3);
    }

    public static void nglMultiDrawElementsBaseVertex(int n, long l, int n2, long l2, int n3, long l3) {
        GL32C.nglMultiDrawElementsBaseVertex(n, l, n2, l2, n3, l3);
    }

    public static void glMultiDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        GL32C.glMultiDrawElementsBaseVertex(n, intBuffer, n2, pointerBuffer, intBuffer2);
    }

    public static void glProvokingVertex(@NativeType(value="GLenum") int n) {
        GL32C.glProvokingVertex(n);
    }

    public static void glTexImage2DMultisample(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLboolean") boolean bl) {
        GL32C.glTexImage2DMultisample(n, n2, n3, n4, n5, bl);
    }

    public static void glTexImage3DMultisample(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLboolean") boolean bl) {
        GL32C.glTexImage3DMultisample(n, n2, n3, n4, n5, n6, bl);
    }

    public static void nglGetMultisamplefv(int n, int n2, long l) {
        GL32C.nglGetMultisamplefv(n, n2, l);
    }

    public static void glGetMultisamplefv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL32C.glGetMultisamplefv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetMultisamplef(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL32C.glGetMultisamplef(n, n2);
    }

    public static void glSampleMaski(@NativeType(value="GLuint") int n, @NativeType(value="GLbitfield") int n2) {
        GL32C.glSampleMaski(n, n2);
    }

    public static void glFramebufferTexture(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint") int n4) {
        GL32C.glFramebufferTexture(n, n2, n3, n4);
    }

    @NativeType(value="GLsync")
    public static long glFenceSync(@NativeType(value="GLenum") int n, @NativeType(value="GLbitfield") int n2) {
        return GL32C.glFenceSync(n, n2);
    }

    public static boolean nglIsSync(long l) {
        return GL32C.nglIsSync(l);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsSync(@NativeType(value="GLsync") long l) {
        return GL32C.glIsSync(l);
    }

    public static void nglDeleteSync(long l) {
        GL32C.nglDeleteSync(l);
    }

    public static void glDeleteSync(@NativeType(value="GLsync") long l) {
        GL32C.glDeleteSync(l);
    }

    public static int nglClientWaitSync(long l, int n, long l2) {
        return GL32C.nglClientWaitSync(l, n, l2);
    }

    @NativeType(value="GLenum")
    public static int glClientWaitSync(@NativeType(value="GLsync") long l, @NativeType(value="GLbitfield") int n, @NativeType(value="GLuint64") long l2) {
        return GL32C.glClientWaitSync(l, n, l2);
    }

    public static void nglWaitSync(long l, int n, long l2) {
        GL32C.nglWaitSync(l, n, l2);
    }

    public static void glWaitSync(@NativeType(value="GLsync") long l, @NativeType(value="GLbitfield") int n, @NativeType(value="GLuint64") long l2) {
        GL32C.glWaitSync(l, n, l2);
    }

    public static void nglGetInteger64v(int n, long l) {
        GL32C.nglGetInteger64v(n, l);
    }

    public static void glGetInteger64v(@NativeType(value="GLenum") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL32C.glGetInteger64v(n, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetInteger64(@NativeType(value="GLenum") int n) {
        return GL32C.glGetInteger64(n);
    }

    public static void nglGetInteger64i_v(int n, int n2, long l) {
        GL32C.nglGetInteger64i_v(n, n2, l);
    }

    public static void glGetInteger64i_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL32C.glGetInteger64i_v(n, n2, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetInteger64i(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL32C.glGetInteger64i(n, n2);
    }

    public static void nglGetSynciv(long l, int n, int n2, long l2, long l3) {
        GL32C.nglGetSynciv(l, n, n2, l2, l3);
    }

    public static void glGetSynciv(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        GL32C.glGetSynciv(l, n, intBuffer, intBuffer2);
    }

    @NativeType(value="void")
    public static int glGetSynci(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer) {
        return GL32C.glGetSynci(l, n, intBuffer);
    }

    public static void glGetBufferParameteri64v(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        GL32C.glGetBufferParameteri64v(n, n2, lArray);
    }

    public static void glMultiDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei const *") int[] nArray, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="GLint *") int[] nArray2) {
        GL32C.glMultiDrawElementsBaseVertex(n, nArray, n2, pointerBuffer, nArray2);
    }

    public static void glGetMultisamplefv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL32C.glGetMultisamplefv(n, n2, fArray);
    }

    public static void glGetInteger64v(@NativeType(value="GLenum") int n, @NativeType(value="GLint64 *") long[] lArray) {
        GL32C.glGetInteger64v(n, lArray);
    }

    public static void glGetInteger64i_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        GL32C.glGetInteger64i_v(n, n2, lArray);
    }

    public static void glGetSynciv(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2) {
        GL32C.glGetSynciv(l, n, nArray, nArray2);
    }

    static {
        GL.initialize();
    }
}

