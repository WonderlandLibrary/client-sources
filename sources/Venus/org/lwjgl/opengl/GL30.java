/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL30
extends GL21 {
    public static final int GL_MAJOR_VERSION = 33307;
    public static final int GL_MINOR_VERSION = 33308;
    public static final int GL_NUM_EXTENSIONS = 33309;
    public static final int GL_CONTEXT_FLAGS = 33310;
    public static final int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 1;
    public static final int GL_COMPARE_REF_TO_TEXTURE = 34894;
    public static final int GL_CLIP_DISTANCE0 = 12288;
    public static final int GL_CLIP_DISTANCE1 = 12289;
    public static final int GL_CLIP_DISTANCE2 = 12290;
    public static final int GL_CLIP_DISTANCE3 = 12291;
    public static final int GL_CLIP_DISTANCE4 = 12292;
    public static final int GL_CLIP_DISTANCE5 = 12293;
    public static final int GL_CLIP_DISTANCE6 = 12294;
    public static final int GL_CLIP_DISTANCE7 = 12295;
    public static final int GL_MAX_CLIP_DISTANCES = 3378;
    public static final int GL_MAX_VARYING_COMPONENTS = 35659;
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 35069;
    public static final int GL_SAMPLER_1D_ARRAY = 36288;
    public static final int GL_SAMPLER_2D_ARRAY = 36289;
    public static final int GL_SAMPLER_1D_ARRAY_SHADOW = 36291;
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW = 36292;
    public static final int GL_SAMPLER_CUBE_SHADOW = 36293;
    public static final int GL_UNSIGNED_INT_VEC2 = 36294;
    public static final int GL_UNSIGNED_INT_VEC3 = 36295;
    public static final int GL_UNSIGNED_INT_VEC4 = 36296;
    public static final int GL_INT_SAMPLER_1D = 36297;
    public static final int GL_INT_SAMPLER_2D = 36298;
    public static final int GL_INT_SAMPLER_3D = 36299;
    public static final int GL_INT_SAMPLER_CUBE = 36300;
    public static final int GL_INT_SAMPLER_1D_ARRAY = 36302;
    public static final int GL_INT_SAMPLER_2D_ARRAY = 36303;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D = 36305;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D = 36306;
    public static final int GL_UNSIGNED_INT_SAMPLER_3D = 36307;
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE = 36308;
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 36310;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 36311;
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET = 35076;
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET = 35077;
    public static final int GL_QUERY_WAIT = 36371;
    public static final int GL_QUERY_NO_WAIT = 36372;
    public static final int GL_QUERY_BY_REGION_WAIT = 36373;
    public static final int GL_QUERY_BY_REGION_NO_WAIT = 36374;
    public static final int GL_MAP_READ_BIT = 1;
    public static final int GL_MAP_WRITE_BIT = 2;
    public static final int GL_MAP_INVALIDATE_RANGE_BIT = 4;
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 8;
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 16;
    public static final int GL_MAP_UNSYNCHRONIZED_BIT = 32;
    public static final int GL_BUFFER_ACCESS_FLAGS = 37151;
    public static final int GL_BUFFER_MAP_LENGTH = 37152;
    public static final int GL_BUFFER_MAP_OFFSET = 37153;
    public static final int GL_CLAMP_VERTEX_COLOR = 35098;
    public static final int GL_CLAMP_FRAGMENT_COLOR = 35099;
    public static final int GL_CLAMP_READ_COLOR = 35100;
    public static final int GL_FIXED_ONLY = 35101;
    public static final int GL_DEPTH_COMPONENT32F = 36012;
    public static final int GL_DEPTH32F_STENCIL8 = 36013;
    public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 36269;
    public static final int GL_TEXTURE_RED_TYPE = 35856;
    public static final int GL_TEXTURE_GREEN_TYPE = 35857;
    public static final int GL_TEXTURE_BLUE_TYPE = 35858;
    public static final int GL_TEXTURE_ALPHA_TYPE = 35859;
    public static final int GL_TEXTURE_LUMINANCE_TYPE = 35860;
    public static final int GL_TEXTURE_INTENSITY_TYPE = 35861;
    public static final int GL_TEXTURE_DEPTH_TYPE = 35862;
    public static final int GL_UNSIGNED_NORMALIZED = 35863;
    public static final int GL_RGBA32F = 34836;
    public static final int GL_RGB32F = 34837;
    public static final int GL_RGBA16F = 34842;
    public static final int GL_RGB16F = 34843;
    public static final int GL_R11F_G11F_B10F = 35898;
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 35899;
    public static final int GL_RGB9_E5 = 35901;
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV = 35902;
    public static final int GL_TEXTURE_SHARED_SIZE = 35903;
    public static final int GL_FRAMEBUFFER = 36160;
    public static final int GL_READ_FRAMEBUFFER = 36008;
    public static final int GL_DRAW_FRAMEBUFFER = 36009;
    public static final int GL_RENDERBUFFER = 36161;
    public static final int GL_STENCIL_INDEX1 = 36166;
    public static final int GL_STENCIL_INDEX4 = 36167;
    public static final int GL_STENCIL_INDEX8 = 36168;
    public static final int GL_STENCIL_INDEX16 = 36169;
    public static final int GL_RENDERBUFFER_WIDTH = 36162;
    public static final int GL_RENDERBUFFER_HEIGHT = 36163;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
    public static final int GL_RENDERBUFFER_RED_SIZE = 36176;
    public static final int GL_RENDERBUFFER_GREEN_SIZE = 36177;
    public static final int GL_RENDERBUFFER_BLUE_SIZE = 36178;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
    public static final int GL_RENDERBUFFER_SAMPLES = 36011;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
    public static final int GL_FRAMEBUFFER_DEFAULT = 33304;
    public static final int GL_INDEX = 33314;
    public static final int GL_COLOR_ATTACHMENT0 = 36064;
    public static final int GL_COLOR_ATTACHMENT1 = 36065;
    public static final int GL_COLOR_ATTACHMENT2 = 36066;
    public static final int GL_COLOR_ATTACHMENT3 = 36067;
    public static final int GL_COLOR_ATTACHMENT4 = 36068;
    public static final int GL_COLOR_ATTACHMENT5 = 36069;
    public static final int GL_COLOR_ATTACHMENT6 = 36070;
    public static final int GL_COLOR_ATTACHMENT7 = 36071;
    public static final int GL_COLOR_ATTACHMENT8 = 36072;
    public static final int GL_COLOR_ATTACHMENT9 = 36073;
    public static final int GL_COLOR_ATTACHMENT10 = 36074;
    public static final int GL_COLOR_ATTACHMENT11 = 36075;
    public static final int GL_COLOR_ATTACHMENT12 = 36076;
    public static final int GL_COLOR_ATTACHMENT13 = 36077;
    public static final int GL_COLOR_ATTACHMENT14 = 36078;
    public static final int GL_COLOR_ATTACHMENT15 = 36079;
    public static final int GL_COLOR_ATTACHMENT16 = 36080;
    public static final int GL_COLOR_ATTACHMENT17 = 36081;
    public static final int GL_COLOR_ATTACHMENT18 = 36082;
    public static final int GL_COLOR_ATTACHMENT19 = 36083;
    public static final int GL_COLOR_ATTACHMENT20 = 36084;
    public static final int GL_COLOR_ATTACHMENT21 = 36085;
    public static final int GL_COLOR_ATTACHMENT22 = 36086;
    public static final int GL_COLOR_ATTACHMENT23 = 36087;
    public static final int GL_COLOR_ATTACHMENT24 = 36088;
    public static final int GL_COLOR_ATTACHMENT25 = 36089;
    public static final int GL_COLOR_ATTACHMENT26 = 36090;
    public static final int GL_COLOR_ATTACHMENT27 = 36091;
    public static final int GL_COLOR_ATTACHMENT28 = 36092;
    public static final int GL_COLOR_ATTACHMENT29 = 36093;
    public static final int GL_COLOR_ATTACHMENT30 = 36094;
    public static final int GL_COLOR_ATTACHMENT31 = 36095;
    public static final int GL_DEPTH_ATTACHMENT = 36096;
    public static final int GL_STENCIL_ATTACHMENT = 36128;
    public static final int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
    public static final int GL_MAX_SAMPLES = 36183;
    public static final int GL_FRAMEBUFFER_COMPLETE = 36053;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
    public static final int GL_FRAMEBUFFER_UNDEFINED = 33305;
    public static final int GL_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_READ_FRAMEBUFFER_BINDING = 36010;
    public static final int GL_RENDERBUFFER_BINDING = 36007;
    public static final int GL_MAX_COLOR_ATTACHMENTS = 36063;
    public static final int GL_MAX_RENDERBUFFER_SIZE = 34024;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
    public static final int GL_DEPTH_STENCIL = 34041;
    public static final int GL_UNSIGNED_INT_24_8 = 34042;
    public static final int GL_DEPTH24_STENCIL8 = 35056;
    public static final int GL_TEXTURE_STENCIL_SIZE = 35057;
    public static final int GL_HALF_FLOAT = 5131;
    public static final int GL_RGBA32UI = 36208;
    public static final int GL_RGB32UI = 36209;
    public static final int GL_RGBA16UI = 36214;
    public static final int GL_RGB16UI = 36215;
    public static final int GL_RGBA8UI = 36220;
    public static final int GL_RGB8UI = 36221;
    public static final int GL_RGBA32I = 36226;
    public static final int GL_RGB32I = 36227;
    public static final int GL_RGBA16I = 36232;
    public static final int GL_RGB16I = 36233;
    public static final int GL_RGBA8I = 36238;
    public static final int GL_RGB8I = 36239;
    public static final int GL_RED_INTEGER = 36244;
    public static final int GL_GREEN_INTEGER = 36245;
    public static final int GL_BLUE_INTEGER = 36246;
    public static final int GL_ALPHA_INTEGER = 36247;
    public static final int GL_RGB_INTEGER = 36248;
    public static final int GL_RGBA_INTEGER = 36249;
    public static final int GL_BGR_INTEGER = 36250;
    public static final int GL_BGRA_INTEGER = 36251;
    public static final int GL_TEXTURE_1D_ARRAY = 35864;
    public static final int GL_TEXTURE_2D_ARRAY = 35866;
    public static final int GL_PROXY_TEXTURE_2D_ARRAY = 35867;
    public static final int GL_PROXY_TEXTURE_1D_ARRAY = 35865;
    public static final int GL_TEXTURE_BINDING_1D_ARRAY = 35868;
    public static final int GL_TEXTURE_BINDING_2D_ARRAY = 35869;
    public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 35071;
    public static final int GL_COMPRESSED_RED_RGTC1 = 36283;
    public static final int GL_COMPRESSED_SIGNED_RED_RGTC1 = 36284;
    public static final int GL_COMPRESSED_RG_RGTC2 = 36285;
    public static final int GL_COMPRESSED_SIGNED_RG_RGTC2 = 36286;
    public static final int GL_R8 = 33321;
    public static final int GL_R16 = 33322;
    public static final int GL_RG8 = 33323;
    public static final int GL_RG16 = 33324;
    public static final int GL_R16F = 33325;
    public static final int GL_R32F = 33326;
    public static final int GL_RG16F = 33327;
    public static final int GL_RG32F = 33328;
    public static final int GL_R8I = 33329;
    public static final int GL_R8UI = 33330;
    public static final int GL_R16I = 33331;
    public static final int GL_R16UI = 33332;
    public static final int GL_R32I = 33333;
    public static final int GL_R32UI = 33334;
    public static final int GL_RG8I = 33335;
    public static final int GL_RG8UI = 33336;
    public static final int GL_RG16I = 33337;
    public static final int GL_RG16UI = 33338;
    public static final int GL_RG32I = 33339;
    public static final int GL_RG32UI = 33340;
    public static final int GL_RG = 33319;
    public static final int GL_COMPRESSED_RED = 33317;
    public static final int GL_COMPRESSED_RG = 33318;
    public static final int GL_RG_INTEGER = 33320;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER = 35982;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = 35972;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 35973;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 35983;
    public static final int GL_INTERLEAVED_ATTRIBS = 35980;
    public static final int GL_SEPARATE_ATTRIBS = 35981;
    public static final int GL_PRIMITIVES_GENERATED = 35975;
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 35976;
    public static final int GL_RASTERIZER_DISCARD = 35977;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 35978;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 35979;
    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 35968;
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS = 35971;
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 35967;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 35958;
    public static final int GL_VERTEX_ARRAY_BINDING = 34229;
    public static final int GL_FRAMEBUFFER_SRGB = 36281;

    protected GL30() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetStringi, gLCapabilities.glClearBufferiv, gLCapabilities.glClearBufferuiv, gLCapabilities.glClearBufferfv, gLCapabilities.glClearBufferfi, gLCapabilities.glVertexAttribI1i, gLCapabilities.glVertexAttribI2i, gLCapabilities.glVertexAttribI3i, gLCapabilities.glVertexAttribI4i, gLCapabilities.glVertexAttribI1ui, gLCapabilities.glVertexAttribI2ui, gLCapabilities.glVertexAttribI3ui, gLCapabilities.glVertexAttribI4ui, gLCapabilities.glVertexAttribI1iv, gLCapabilities.glVertexAttribI2iv, gLCapabilities.glVertexAttribI3iv, gLCapabilities.glVertexAttribI4iv, gLCapabilities.glVertexAttribI1uiv, gLCapabilities.glVertexAttribI2uiv, gLCapabilities.glVertexAttribI3uiv, gLCapabilities.glVertexAttribI4uiv, gLCapabilities.glVertexAttribI4bv, gLCapabilities.glVertexAttribI4sv, gLCapabilities.glVertexAttribI4ubv, gLCapabilities.glVertexAttribI4usv, gLCapabilities.glVertexAttribIPointer, gLCapabilities.glGetVertexAttribIiv, gLCapabilities.glGetVertexAttribIuiv, gLCapabilities.glUniform1ui, gLCapabilities.glUniform2ui, gLCapabilities.glUniform3ui, gLCapabilities.glUniform4ui, gLCapabilities.glUniform1uiv, gLCapabilities.glUniform2uiv, gLCapabilities.glUniform3uiv, gLCapabilities.glUniform4uiv, gLCapabilities.glGetUniformuiv, gLCapabilities.glBindFragDataLocation, gLCapabilities.glGetFragDataLocation, gLCapabilities.glBeginConditionalRender, gLCapabilities.glEndConditionalRender, gLCapabilities.glMapBufferRange, gLCapabilities.glFlushMappedBufferRange, gLCapabilities.glClampColor, gLCapabilities.glIsRenderbuffer, gLCapabilities.glBindRenderbuffer, gLCapabilities.glDeleteRenderbuffers, gLCapabilities.glGenRenderbuffers, gLCapabilities.glRenderbufferStorage, gLCapabilities.glRenderbufferStorageMultisample, gLCapabilities.glGetRenderbufferParameteriv, gLCapabilities.glIsFramebuffer, gLCapabilities.glBindFramebuffer, gLCapabilities.glDeleteFramebuffers, gLCapabilities.glGenFramebuffers, gLCapabilities.glCheckFramebufferStatus, gLCapabilities.glFramebufferTexture1D, gLCapabilities.glFramebufferTexture2D, gLCapabilities.glFramebufferTexture3D, gLCapabilities.glFramebufferTextureLayer, gLCapabilities.glFramebufferRenderbuffer, gLCapabilities.glGetFramebufferAttachmentParameteriv, gLCapabilities.glBlitFramebuffer, gLCapabilities.glGenerateMipmap, gLCapabilities.glTexParameterIiv, gLCapabilities.glTexParameterIuiv, gLCapabilities.glGetTexParameterIiv, gLCapabilities.glGetTexParameterIuiv, gLCapabilities.glColorMaski, gLCapabilities.glGetBooleani_v, gLCapabilities.glGetIntegeri_v, gLCapabilities.glEnablei, gLCapabilities.glDisablei, gLCapabilities.glIsEnabledi, gLCapabilities.glBindBufferRange, gLCapabilities.glBindBufferBase, gLCapabilities.glBeginTransformFeedback, gLCapabilities.glEndTransformFeedback, gLCapabilities.glTransformFeedbackVaryings, gLCapabilities.glGetTransformFeedbackVarying, gLCapabilities.glBindVertexArray, gLCapabilities.glDeleteVertexArrays, gLCapabilities.glGenVertexArrays, gLCapabilities.glIsVertexArray);
    }

    public static long nglGetStringi(int n, int n2) {
        return GL30C.nglGetStringi(n, n2);
    }

    @Nullable
    @NativeType(value="GLubyte const *")
    public static String glGetStringi(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL30C.glGetStringi(n, n2);
    }

    public static void nglClearBufferiv(int n, int n2, long l) {
        GL30C.nglClearBufferiv(n, n2, l);
    }

    public static void glClearBufferiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glClearBufferiv(n, n2, intBuffer);
    }

    public static void nglClearBufferuiv(int n, int n2, long l) {
        GL30C.nglClearBufferuiv(n, n2, l);
    }

    public static void glClearBufferuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glClearBufferuiv(n, n2, intBuffer);
    }

    public static void nglClearBufferfv(int n, int n2, long l) {
        GL30C.nglClearBufferfv(n, n2, l);
    }

    public static void glClearBufferfv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL30C.glClearBufferfv(n, n2, floatBuffer);
    }

    public static void glClearBufferfi(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat") float f, @NativeType(value="GLint") int n3) {
        GL30C.glClearBufferfi(n, n2, f, n3);
    }

    public static void glVertexAttribI1i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        GL30C.glVertexAttribI1i(n, n2);
    }

    public static void glVertexAttribI2i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3) {
        GL30C.glVertexAttribI2i(n, n2, n3);
    }

    public static void glVertexAttribI3i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4) {
        GL30C.glVertexAttribI3i(n, n2, n3, n4);
    }

    public static void glVertexAttribI4i(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glVertexAttribI4i(n, n2, n3, n4, n5);
    }

    public static void glVertexAttribI1ui(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glVertexAttribI1ui(n, n2);
    }

    public static void glVertexAttribI2ui(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL30C.glVertexAttribI2ui(n, n2, n3);
    }

    public static void glVertexAttribI3ui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4) {
        GL30C.glVertexAttribI3ui(n, n2, n3, n4);
    }

    public static void glVertexAttribI4ui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glVertexAttribI4ui(n, n2, n3, n4, n5);
    }

    public static void nglVertexAttribI1iv(int n, long l) {
        GL30C.nglVertexAttribI1iv(n, l);
    }

    public static void glVertexAttribI1iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI1iv(n, intBuffer);
    }

    public static void nglVertexAttribI2iv(int n, long l) {
        GL30C.nglVertexAttribI2iv(n, l);
    }

    public static void glVertexAttribI2iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI2iv(n, intBuffer);
    }

    public static void nglVertexAttribI3iv(int n, long l) {
        GL30C.nglVertexAttribI3iv(n, l);
    }

    public static void glVertexAttribI3iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI3iv(n, intBuffer);
    }

    public static void nglVertexAttribI4iv(int n, long l) {
        GL30C.nglVertexAttribI4iv(n, l);
    }

    public static void glVertexAttribI4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI4iv(n, intBuffer);
    }

    public static void nglVertexAttribI1uiv(int n, long l) {
        GL30C.nglVertexAttribI1uiv(n, l);
    }

    public static void glVertexAttribI1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI1uiv(n, intBuffer);
    }

    public static void nglVertexAttribI2uiv(int n, long l) {
        GL30C.nglVertexAttribI2uiv(n, l);
    }

    public static void glVertexAttribI2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI2uiv(n, intBuffer);
    }

    public static void nglVertexAttribI3uiv(int n, long l) {
        GL30C.nglVertexAttribI3uiv(n, l);
    }

    public static void glVertexAttribI3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI3uiv(n, intBuffer);
    }

    public static void nglVertexAttribI4uiv(int n, long l) {
        GL30C.nglVertexAttribI4uiv(n, l);
    }

    public static void glVertexAttribI4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribI4uiv(n, intBuffer);
    }

    public static void nglVertexAttribI4bv(int n, long l) {
        GL30C.nglVertexAttribI4bv(n, l);
    }

    public static void glVertexAttribI4bv(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        GL30C.glVertexAttribI4bv(n, byteBuffer);
    }

    public static void nglVertexAttribI4sv(int n, long l) {
        GL30C.nglVertexAttribI4sv(n, l);
    }

    public static void glVertexAttribI4sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        GL30C.glVertexAttribI4sv(n, shortBuffer);
    }

    public static void nglVertexAttribI4ubv(int n, long l) {
        GL30C.nglVertexAttribI4ubv(n, l);
    }

    public static void glVertexAttribI4ubv(@NativeType(value="GLuint") int n, @NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        GL30C.glVertexAttribI4ubv(n, byteBuffer);
    }

    public static void nglVertexAttribI4usv(int n, long l) {
        GL30C.nglVertexAttribI4usv(n, l);
    }

    public static void glVertexAttribI4usv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        GL30C.glVertexAttribI4usv(n, shortBuffer);
    }

    public static void nglVertexAttribIPointer(int n, int n2, int n3, int n4, long l) {
        GL30C.nglVertexAttribIPointer(n, n2, n3, n4, l);
    }

    public static void glVertexAttribIPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL30C.glVertexAttribIPointer(n, n2, n3, n4, byteBuffer);
    }

    public static void glVertexAttribIPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") long l) {
        GL30C.glVertexAttribIPointer(n, n2, n3, n4, l);
    }

    public static void glVertexAttribIPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL30C.glVertexAttribIPointer(n, n2, n3, n4, shortBuffer);
    }

    public static void glVertexAttribIPointer(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL30C.glVertexAttribIPointer(n, n2, n3, n4, intBuffer);
    }

    public static void nglGetVertexAttribIiv(int n, int n2, long l) {
        GL30C.nglGetVertexAttribIiv(n, n2, l);
    }

    public static void glGetVertexAttribIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetVertexAttribIiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetVertexAttribIi(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL30C.glGetVertexAttribIi(n, n2);
    }

    public static void nglGetVertexAttribIuiv(int n, int n2, long l) {
        GL30C.nglGetVertexAttribIuiv(n, n2, l);
    }

    public static void glGetVertexAttribIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGetVertexAttribIuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetVertexAttribIui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL30C.glGetVertexAttribIui(n, n2);
    }

    public static void glUniform1ui(@NativeType(value="GLint") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glUniform1ui(n, n2);
    }

    public static void glUniform2ui(@NativeType(value="GLint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL30C.glUniform2ui(n, n2, n3);
    }

    public static void glUniform3ui(@NativeType(value="GLint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint") int n4) {
        GL30C.glUniform3ui(n, n2, n3, n4);
    }

    public static void glUniform4ui(@NativeType(value="GLint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLuint") int n5) {
        GL30C.glUniform4ui(n, n2, n3, n4, n5);
    }

    public static void nglUniform1uiv(int n, int n2, long l) {
        GL30C.nglUniform1uiv(n, n2, l);
    }

    public static void glUniform1uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glUniform1uiv(n, intBuffer);
    }

    public static void nglUniform2uiv(int n, int n2, long l) {
        GL30C.nglUniform2uiv(n, n2, l);
    }

    public static void glUniform2uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glUniform2uiv(n, intBuffer);
    }

    public static void nglUniform3uiv(int n, int n2, long l) {
        GL30C.nglUniform3uiv(n, n2, l);
    }

    public static void glUniform3uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glUniform3uiv(n, intBuffer);
    }

    public static void nglUniform4uiv(int n, int n2, long l) {
        GL30C.nglUniform4uiv(n, n2, l);
    }

    public static void glUniform4uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glUniform4uiv(n, intBuffer);
    }

    public static void nglGetUniformuiv(int n, int n2, long l) {
        GL30C.nglGetUniformuiv(n, n2, l);
    }

    public static void glGetUniformuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGetUniformuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetUniformui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        return GL30C.glGetUniformui(n, n2);
    }

    public static void nglBindFragDataLocation(int n, int n2, long l) {
        GL30C.nglBindFragDataLocation(n, n2, l);
    }

    public static void glBindFragDataLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL30C.glBindFragDataLocation(n, n2, byteBuffer);
    }

    public static void glBindFragDataLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL30C.glBindFragDataLocation(n, n2, charSequence);
    }

    public static int nglGetFragDataLocation(int n, long l) {
        return GL30C.nglGetFragDataLocation(n, l);
    }

    @NativeType(value="GLint")
    public static int glGetFragDataLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL30C.glGetFragDataLocation(n, byteBuffer);
    }

    @NativeType(value="GLint")
    public static int glGetFragDataLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL30C.glGetFragDataLocation(n, charSequence);
    }

    public static void glBeginConditionalRender(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        GL30C.glBeginConditionalRender(n, n2);
    }

    public static void glEndConditionalRender() {
        GL30C.glEndConditionalRender();
    }

    public static long nglMapBufferRange(int n, long l, long l2, int n2) {
        return GL30C.nglMapBufferRange(n, l, l2, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2) {
        return GL30C.glMapBufferRange(n, l, l2, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLbitfield") int n2, @Nullable ByteBuffer byteBuffer) {
        return GL30C.glMapBufferRange(n, l, l2, n2);
    }

    public static void glFlushMappedBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL30C.glFlushMappedBufferRange(n, l, l2);
    }

    public static void glClampColor(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        GL30C.glClampColor(n, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsRenderbuffer(@NativeType(value="GLuint") int n) {
        return GL30C.glIsRenderbuffer(n);
    }

    public static void glBindRenderbuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glBindRenderbuffer(n, n2);
    }

    public static void nglDeleteRenderbuffers(int n, long l) {
        GL30C.nglDeleteRenderbuffers(n, l);
    }

    public static void glDeleteRenderbuffers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glDeleteRenderbuffers(intBuffer);
    }

    public static void glDeleteRenderbuffers(@NativeType(value="GLuint const *") int n) {
        GL30C.glDeleteRenderbuffers(n);
    }

    public static void nglGenRenderbuffers(int n, long l) {
        GL30C.nglGenRenderbuffers(n, l);
    }

    public static void glGenRenderbuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGenRenderbuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenRenderbuffers() {
        return GL30C.glGenRenderbuffers();
    }

    public static void glRenderbufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL30C.glRenderbufferStorage(n, n2, n3, n4);
    }

    public static void glRenderbufferStorageMultisample(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL30C.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
    }

    public static void nglGetRenderbufferParameteriv(int n, int n2, long l) {
        GL30C.nglGetRenderbufferParameteriv(n, n2, l);
    }

    public static void glGetRenderbufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetRenderbufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetRenderbufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL30C.glGetRenderbufferParameteri(n, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsFramebuffer(@NativeType(value="GLuint") int n) {
        return GL30C.glIsFramebuffer(n);
    }

    public static void glBindFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glBindFramebuffer(n, n2);
    }

    public static void nglDeleteFramebuffers(int n, long l) {
        GL30C.nglDeleteFramebuffers(n, l);
    }

    public static void glDeleteFramebuffers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glDeleteFramebuffers(intBuffer);
    }

    public static void glDeleteFramebuffers(@NativeType(value="GLuint const *") int n) {
        GL30C.glDeleteFramebuffers(n);
    }

    public static void nglGenFramebuffers(int n, long l) {
        GL30C.nglGenFramebuffers(n, l);
    }

    public static void glGenFramebuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGenFramebuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenFramebuffers() {
        return GL30C.glGenFramebuffers();
    }

    @NativeType(value="GLenum")
    public static int glCheckFramebufferStatus(@NativeType(value="GLenum") int n) {
        return GL30C.glCheckFramebufferStatus(n);
    }

    public static void glFramebufferTexture1D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glFramebufferTexture1D(n, n2, n3, n4, n5);
    }

    public static void glFramebufferTexture2D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glFramebufferTexture2D(n, n2, n3, n4, n5);
    }

    public static void glFramebufferTexture3D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6) {
        GL30C.glFramebufferTexture3D(n, n2, n3, n4, n5, n6);
    }

    public static void glFramebufferTextureLayer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glFramebufferTextureLayer(n, n2, n3, n4, n5);
    }

    public static void glFramebufferRenderbuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4) {
        GL30C.glFramebufferRenderbuffer(n, n2, n3, n4);
    }

    public static void nglGetFramebufferAttachmentParameteriv(int n, int n2, int n3, long l) {
        GL30C.nglGetFramebufferAttachmentParameteriv(n, n2, n3, l);
    }

    public static void glGetFramebufferAttachmentParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetFramebufferAttachmentParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL30C.glGetFramebufferAttachmentParameteri(n, n2, n3);
    }

    public static void glBlitFramebuffer(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLbitfield") int n9, @NativeType(value="GLenum") int n10) {
        GL30C.glBlitFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }

    public static void glGenerateMipmap(@NativeType(value="GLenum") int n) {
        GL30C.glGenerateMipmap(n);
    }

    public static void nglTexParameterIiv(int n, int n2, long l) {
        GL30C.nglTexParameterIiv(n, n2, l);
    }

    public static void glTexParameterIiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL30C.glTexParameterIiv(n, n2, intBuffer);
    }

    public static void glTexParameterIi(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int n3) {
        GL30C.glTexParameterIi(n, n2, n3);
    }

    public static void nglTexParameterIuiv(int n, int n2, long l) {
        GL30C.nglTexParameterIuiv(n, n2, l);
    }

    public static void glTexParameterIuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glTexParameterIuiv(n, n2, intBuffer);
    }

    public static void glTexParameterIui(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int n3) {
        GL30C.glTexParameterIui(n, n2, n3);
    }

    public static void nglGetTexParameterIiv(int n, int n2, long l) {
        GL30C.nglGetTexParameterIiv(n, n2, l);
    }

    public static void glGetTexParameterIiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetTexParameterIiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTexParameterIi(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL30C.glGetTexParameterIi(n, n2);
    }

    public static void nglGetTexParameterIuiv(int n, int n2, long l) {
        GL30C.nglGetTexParameterIuiv(n, n2, l);
    }

    public static void glGetTexParameterIuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGetTexParameterIuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTexParameterIui(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL30C.glGetTexParameterIui(n, n2);
    }

    public static void glColorMaski(@NativeType(value="GLuint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLboolean") boolean bl2, @NativeType(value="GLboolean") boolean bl3, @NativeType(value="GLboolean") boolean bl4) {
        GL30C.glColorMaski(n, bl, bl2, bl3, bl4);
    }

    public static void nglGetBooleani_v(int n, int n2, long l) {
        GL30C.nglGetBooleani_v(n, n2, l);
    }

    public static void glGetBooleani_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        GL30C.glGetBooleani_v(n, n2, byteBuffer);
    }

    @NativeType(value="void")
    public static boolean glGetBooleani(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL30C.glGetBooleani(n, n2);
    }

    public static void nglGetIntegeri_v(int n, int n2, long l) {
        GL30C.nglGetIntegeri_v(n, n2, l);
    }

    public static void glGetIntegeri_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetIntegeri_v(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetIntegeri(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL30C.glGetIntegeri(n, n2);
    }

    public static void glEnablei(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glEnablei(n, n2);
    }

    public static void glDisablei(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glDisablei(n, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsEnabledi(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL30C.glIsEnabledi(n, n2);
    }

    public static void glBindBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL30C.glBindBufferRange(n, n2, n3, l, l2);
    }

    public static void glBindBufferBase(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL30C.glBindBufferBase(n, n2, n3);
    }

    public static void glBeginTransformFeedback(@NativeType(value="GLenum") int n) {
        GL30C.glBeginTransformFeedback(n);
    }

    public static void glEndTransformFeedback() {
        GL30C.glEndTransformFeedback();
    }

    public static void nglTransformFeedbackVaryings(int n, int n2, long l, int n3) {
        GL30C.nglTransformFeedbackVaryings(n, n2, l, n3);
    }

    public static void glTransformFeedbackVaryings(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") PointerBuffer pointerBuffer, @NativeType(value="GLenum") int n2) {
        GL30C.glTransformFeedbackVaryings(n, pointerBuffer, n2);
    }

    public static void glTransformFeedbackVaryings(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") CharSequence[] charSequenceArray, @NativeType(value="GLenum") int n2) {
        GL30C.glTransformFeedbackVaryings(n, charSequenceArray, n2);
    }

    public static void glTransformFeedbackVaryings(@NativeType(value="GLuint") int n, @NativeType(value="GLchar const **") CharSequence charSequence, @NativeType(value="GLenum") int n2) {
        GL30C.glTransformFeedbackVaryings(n, charSequence, n2);
    }

    public static void nglGetTransformFeedbackVarying(int n, int n2, int n3, long l, long l2, long l3, long l4) {
        GL30C.nglGetTransformFeedbackVarying(n, n2, n3, l, l2, l3, l4);
    }

    public static void glGetTransformFeedbackVarying(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLsizei *") IntBuffer intBuffer2, @NativeType(value="GLenum *") IntBuffer intBuffer3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL30C.glGetTransformFeedbackVarying(n, n2, intBuffer, intBuffer2, intBuffer3, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetTransformFeedbackVarying(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return GL30C.glGetTransformFeedbackVarying(n, n2, n3, intBuffer, intBuffer2);
    }

    @NativeType(value="void")
    public static String glGetTransformFeedbackVarying(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLenum *") IntBuffer intBuffer2) {
        return GL30.glGetTransformFeedbackVarying(n, n2, GL20.glGetProgrami(n, 35958), intBuffer, intBuffer2);
    }

    public static void glBindVertexArray(@NativeType(value="GLuint") int n) {
        GL30C.glBindVertexArray(n);
    }

    public static void nglDeleteVertexArrays(int n, long l) {
        GL30C.nglDeleteVertexArrays(n, l);
    }

    public static void glDeleteVertexArrays(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glDeleteVertexArrays(intBuffer);
    }

    public static void glDeleteVertexArrays(@NativeType(value="GLuint const *") int n) {
        GL30C.glDeleteVertexArrays(n);
    }

    public static void nglGenVertexArrays(int n, long l) {
        GL30C.nglGenVertexArrays(n, l);
    }

    public static void glGenVertexArrays(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGenVertexArrays(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenVertexArrays() {
        return GL30C.glGenVertexArrays();
    }

    @NativeType(value="GLboolean")
    public static boolean glIsVertexArray(@NativeType(value="GLuint") int n) {
        return GL30C.glIsVertexArray(n);
    }

    public static void glClearBufferiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glClearBufferiv(n, n2, nArray);
    }

    public static void glClearBufferuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glClearBufferuiv(n, n2, nArray);
    }

    public static void glClearBufferfv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL30C.glClearBufferfv(n, n2, fArray);
    }

    public static void glVertexAttribI1iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL30C.glVertexAttribI1iv(n, nArray);
    }

    public static void glVertexAttribI2iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL30C.glVertexAttribI2iv(n, nArray);
    }

    public static void glVertexAttribI3iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL30C.glVertexAttribI3iv(n, nArray);
    }

    public static void glVertexAttribI4iv(@NativeType(value="GLuint") int n, @NativeType(value="GLint const *") int[] nArray) {
        GL30C.glVertexAttribI4iv(n, nArray);
    }

    public static void glVertexAttribI1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glVertexAttribI1uiv(n, nArray);
    }

    public static void glVertexAttribI2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glVertexAttribI2uiv(n, nArray);
    }

    public static void glVertexAttribI3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glVertexAttribI3uiv(n, nArray);
    }

    public static void glVertexAttribI4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glVertexAttribI4uiv(n, nArray);
    }

    public static void glVertexAttribI4sv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        GL30C.glVertexAttribI4sv(n, sArray);
    }

    public static void glVertexAttribI4usv(@NativeType(value="GLuint") int n, @NativeType(value="GLshort const *") short[] sArray) {
        GL30C.glVertexAttribI4usv(n, sArray);
    }

    public static void glGetVertexAttribIiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetVertexAttribIiv(n, n2, nArray);
    }

    public static void glGetVertexAttribIuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGetVertexAttribIuiv(n, n2, nArray);
    }

    public static void glUniform1uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glUniform1uiv(n, nArray);
    }

    public static void glUniform2uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glUniform2uiv(n, nArray);
    }

    public static void glUniform3uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glUniform3uiv(n, nArray);
    }

    public static void glUniform4uiv(@NativeType(value="GLint") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glUniform4uiv(n, nArray);
    }

    public static void glGetUniformuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGetUniformuiv(n, n2, nArray);
    }

    public static void glDeleteRenderbuffers(@NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glDeleteRenderbuffers(nArray);
    }

    public static void glGenRenderbuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGenRenderbuffers(nArray);
    }

    public static void glGetRenderbufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetRenderbufferParameteriv(n, n2, nArray);
    }

    public static void glDeleteFramebuffers(@NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glDeleteFramebuffers(nArray);
    }

    public static void glGenFramebuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGenFramebuffers(nArray);
    }

    public static void glGetFramebufferAttachmentParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetFramebufferAttachmentParameteriv(n, n2, n3, nArray);
    }

    public static void glTexParameterIiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL30C.glTexParameterIiv(n, n2, nArray);
    }

    public static void glTexParameterIuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glTexParameterIuiv(n, n2, nArray);
    }

    public static void glGetTexParameterIiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetTexParameterIiv(n, n2, nArray);
    }

    public static void glGetTexParameterIuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGetTexParameterIuiv(n, n2, nArray);
    }

    public static void glGetIntegeri_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetIntegeri_v(n, n2, nArray);
    }

    public static void glGetTransformFeedbackVarying(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLsizei *") int[] nArray2, @NativeType(value="GLenum *") int[] nArray3, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL30C.glGetTransformFeedbackVarying(n, n2, nArray, nArray2, nArray3, byteBuffer);
    }

    public static void glDeleteVertexArrays(@NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glDeleteVertexArrays(nArray);
    }

    public static void glGenVertexArrays(@NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGenVertexArrays(nArray);
    }

    static {
        GL.initialize();
    }
}

