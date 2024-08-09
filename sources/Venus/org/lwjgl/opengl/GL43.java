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
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL43
extends GL42 {
    public static final int GL_NUM_SHADING_LANGUAGE_VERSIONS = 33513;
    public static final int GL_VERTEX_ATTRIB_ARRAY_LONG = 34638;
    public static final int GL_COMPRESSED_RGB8_ETC2 = 37492;
    public static final int GL_COMPRESSED_SRGB8_ETC2 = 37493;
    public static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37494;
    public static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 37495;
    public static final int GL_COMPRESSED_RGBA8_ETC2_EAC = 37496;
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 37497;
    public static final int GL_COMPRESSED_R11_EAC = 37488;
    public static final int GL_COMPRESSED_SIGNED_R11_EAC = 37489;
    public static final int GL_COMPRESSED_RG11_EAC = 37490;
    public static final int GL_COMPRESSED_SIGNED_RG11_EAC = 37491;
    public static final int GL_PRIMITIVE_RESTART_FIXED_INDEX = 36201;
    public static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 36202;
    public static final int GL_MAX_ELEMENT_INDEX = 36203;
    public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 33503;
    public static final int GL_COMPUTE_SHADER = 37305;
    public static final int GL_MAX_COMPUTE_UNIFORM_BLOCKS = 37307;
    public static final int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 37308;
    public static final int GL_MAX_COMPUTE_IMAGE_UNIFORMS = 37309;
    public static final int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 33378;
    public static final int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 33379;
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 33380;
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTERS = 33381;
    public static final int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 33382;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 37099;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_COUNT = 37310;
    public static final int GL_MAX_COMPUTE_WORK_GROUP_SIZE = 37311;
    public static final int GL_COMPUTE_WORK_GROUP_SIZE = 33383;
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = 37100;
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = 37101;
    public static final int GL_DISPATCH_INDIRECT_BUFFER = 37102;
    public static final int GL_DISPATCH_INDIRECT_BUFFER_BINDING = 37103;
    public static final int GL_COMPUTE_SHADER_BIT = 32;
    public static final int GL_DEBUG_OUTPUT = 37600;
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 33346;
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 2;
    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 37187;
    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 37188;
    public static final int GL_DEBUG_LOGGED_MESSAGES = 37189;
    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 33347;
    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 33388;
    public static final int GL_DEBUG_GROUP_STACK_DEPTH = 33389;
    public static final int GL_MAX_LABEL_LENGTH = 33512;
    public static final int GL_DEBUG_CALLBACK_FUNCTION = 33348;
    public static final int GL_DEBUG_CALLBACK_USER_PARAM = 33349;
    public static final int GL_DEBUG_SOURCE_API = 33350;
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 33351;
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 33352;
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 33353;
    public static final int GL_DEBUG_SOURCE_APPLICATION = 33354;
    public static final int GL_DEBUG_SOURCE_OTHER = 33355;
    public static final int GL_DEBUG_TYPE_ERROR = 33356;
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 33357;
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 33358;
    public static final int GL_DEBUG_TYPE_PORTABILITY = 33359;
    public static final int GL_DEBUG_TYPE_PERFORMANCE = 33360;
    public static final int GL_DEBUG_TYPE_OTHER = 33361;
    public static final int GL_DEBUG_TYPE_MARKER = 33384;
    public static final int GL_DEBUG_TYPE_PUSH_GROUP = 33385;
    public static final int GL_DEBUG_TYPE_POP_GROUP = 33386;
    public static final int GL_DEBUG_SEVERITY_HIGH = 37190;
    public static final int GL_DEBUG_SEVERITY_MEDIUM = 37191;
    public static final int GL_DEBUG_SEVERITY_LOW = 37192;
    public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 33387;
    public static final int GL_BUFFER = 33504;
    public static final int GL_SHADER = 33505;
    public static final int GL_PROGRAM = 33506;
    public static final int GL_QUERY = 33507;
    public static final int GL_PROGRAM_PIPELINE = 33508;
    public static final int GL_SAMPLER = 33510;
    public static final int GL_DISPLAY_LIST = 33511;
    public static final int GL_MAX_UNIFORM_LOCATIONS = 33390;
    public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = 37648;
    public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 37649;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = 37650;
    public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 37651;
    public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 37652;
    public static final int GL_MAX_FRAMEBUFFER_WIDTH = 37653;
    public static final int GL_MAX_FRAMEBUFFER_HEIGHT = 37654;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS = 37655;
    public static final int GL_MAX_FRAMEBUFFER_SAMPLES = 37656;
    public static final int GL_INTERNALFORMAT_SUPPORTED = 33391;
    public static final int GL_INTERNALFORMAT_PREFERRED = 33392;
    public static final int GL_INTERNALFORMAT_RED_SIZE = 33393;
    public static final int GL_INTERNALFORMAT_GREEN_SIZE = 33394;
    public static final int GL_INTERNALFORMAT_BLUE_SIZE = 33395;
    public static final int GL_INTERNALFORMAT_ALPHA_SIZE = 33396;
    public static final int GL_INTERNALFORMAT_DEPTH_SIZE = 33397;
    public static final int GL_INTERNALFORMAT_STENCIL_SIZE = 33398;
    public static final int GL_INTERNALFORMAT_SHARED_SIZE = 33399;
    public static final int GL_INTERNALFORMAT_RED_TYPE = 33400;
    public static final int GL_INTERNALFORMAT_GREEN_TYPE = 33401;
    public static final int GL_INTERNALFORMAT_BLUE_TYPE = 33402;
    public static final int GL_INTERNALFORMAT_ALPHA_TYPE = 33403;
    public static final int GL_INTERNALFORMAT_DEPTH_TYPE = 33404;
    public static final int GL_INTERNALFORMAT_STENCIL_TYPE = 33405;
    public static final int GL_MAX_WIDTH = 33406;
    public static final int GL_MAX_HEIGHT = 33407;
    public static final int GL_MAX_DEPTH = 33408;
    public static final int GL_MAX_LAYERS = 33409;
    public static final int GL_MAX_COMBINED_DIMENSIONS = 33410;
    public static final int GL_COLOR_COMPONENTS = 33411;
    public static final int GL_DEPTH_COMPONENTS = 33412;
    public static final int GL_STENCIL_COMPONENTS = 33413;
    public static final int GL_COLOR_RENDERABLE = 33414;
    public static final int GL_DEPTH_RENDERABLE = 33415;
    public static final int GL_STENCIL_RENDERABLE = 33416;
    public static final int GL_FRAMEBUFFER_RENDERABLE = 33417;
    public static final int GL_FRAMEBUFFER_RENDERABLE_LAYERED = 33418;
    public static final int GL_FRAMEBUFFER_BLEND = 33419;
    public static final int GL_READ_PIXELS = 33420;
    public static final int GL_READ_PIXELS_FORMAT = 33421;
    public static final int GL_READ_PIXELS_TYPE = 33422;
    public static final int GL_TEXTURE_IMAGE_FORMAT = 33423;
    public static final int GL_TEXTURE_IMAGE_TYPE = 33424;
    public static final int GL_GET_TEXTURE_IMAGE_FORMAT = 33425;
    public static final int GL_GET_TEXTURE_IMAGE_TYPE = 33426;
    public static final int GL_MIPMAP = 33427;
    public static final int GL_MANUAL_GENERATE_MIPMAP = 33428;
    public static final int GL_AUTO_GENERATE_MIPMAP = 33429;
    public static final int GL_COLOR_ENCODING = 33430;
    public static final int GL_SRGB_READ = 33431;
    public static final int GL_SRGB_WRITE = 33432;
    public static final int GL_FILTER = 33434;
    public static final int GL_VERTEX_TEXTURE = 33435;
    public static final int GL_TESS_CONTROL_TEXTURE = 33436;
    public static final int GL_TESS_EVALUATION_TEXTURE = 33437;
    public static final int GL_GEOMETRY_TEXTURE = 33438;
    public static final int GL_FRAGMENT_TEXTURE = 33439;
    public static final int GL_COMPUTE_TEXTURE = 33440;
    public static final int GL_TEXTURE_SHADOW = 33441;
    public static final int GL_TEXTURE_GATHER = 33442;
    public static final int GL_TEXTURE_GATHER_SHADOW = 33443;
    public static final int GL_SHADER_IMAGE_LOAD = 33444;
    public static final int GL_SHADER_IMAGE_STORE = 33445;
    public static final int GL_SHADER_IMAGE_ATOMIC = 33446;
    public static final int GL_IMAGE_TEXEL_SIZE = 33447;
    public static final int GL_IMAGE_COMPATIBILITY_CLASS = 33448;
    public static final int GL_IMAGE_PIXEL_FORMAT = 33449;
    public static final int GL_IMAGE_PIXEL_TYPE = 33450;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = 33452;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = 33453;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = 33454;
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = 33455;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = 33457;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = 33458;
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = 33459;
    public static final int GL_CLEAR_BUFFER = 33460;
    public static final int GL_TEXTURE_VIEW = 33461;
    public static final int GL_VIEW_COMPATIBILITY_CLASS = 33462;
    public static final int GL_FULL_SUPPORT = 33463;
    public static final int GL_CAVEAT_SUPPORT = 33464;
    public static final int GL_IMAGE_CLASS_4_X_32 = 33465;
    public static final int GL_IMAGE_CLASS_2_X_32 = 33466;
    public static final int GL_IMAGE_CLASS_1_X_32 = 33467;
    public static final int GL_IMAGE_CLASS_4_X_16 = 33468;
    public static final int GL_IMAGE_CLASS_2_X_16 = 33469;
    public static final int GL_IMAGE_CLASS_1_X_16 = 33470;
    public static final int GL_IMAGE_CLASS_4_X_8 = 33471;
    public static final int GL_IMAGE_CLASS_2_X_8 = 33472;
    public static final int GL_IMAGE_CLASS_1_X_8 = 33473;
    public static final int GL_IMAGE_CLASS_11_11_10 = 33474;
    public static final int GL_IMAGE_CLASS_10_10_10_2 = 33475;
    public static final int GL_VIEW_CLASS_128_BITS = 33476;
    public static final int GL_VIEW_CLASS_96_BITS = 33477;
    public static final int GL_VIEW_CLASS_64_BITS = 33478;
    public static final int GL_VIEW_CLASS_48_BITS = 33479;
    public static final int GL_VIEW_CLASS_32_BITS = 33480;
    public static final int GL_VIEW_CLASS_24_BITS = 33481;
    public static final int GL_VIEW_CLASS_16_BITS = 33482;
    public static final int GL_VIEW_CLASS_8_BITS = 33483;
    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGB = 33484;
    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGBA = 33485;
    public static final int GL_VIEW_CLASS_S3TC_DXT3_RGBA = 33486;
    public static final int GL_VIEW_CLASS_S3TC_DXT5_RGBA = 33487;
    public static final int GL_VIEW_CLASS_RGTC1_RED = 33488;
    public static final int GL_VIEW_CLASS_RGTC2_RG = 33489;
    public static final int GL_VIEW_CLASS_BPTC_UNORM = 33490;
    public static final int GL_VIEW_CLASS_BPTC_FLOAT = 33491;
    public static final int GL_UNIFORM = 37601;
    public static final int GL_UNIFORM_BLOCK = 37602;
    public static final int GL_PROGRAM_INPUT = 37603;
    public static final int GL_PROGRAM_OUTPUT = 37604;
    public static final int GL_BUFFER_VARIABLE = 37605;
    public static final int GL_SHADER_STORAGE_BLOCK = 37606;
    public static final int GL_VERTEX_SUBROUTINE = 37608;
    public static final int GL_TESS_CONTROL_SUBROUTINE = 37609;
    public static final int GL_TESS_EVALUATION_SUBROUTINE = 37610;
    public static final int GL_GEOMETRY_SUBROUTINE = 37611;
    public static final int GL_FRAGMENT_SUBROUTINE = 37612;
    public static final int GL_COMPUTE_SUBROUTINE = 37613;
    public static final int GL_VERTEX_SUBROUTINE_UNIFORM = 37614;
    public static final int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 37615;
    public static final int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 37616;
    public static final int GL_GEOMETRY_SUBROUTINE_UNIFORM = 37617;
    public static final int GL_FRAGMENT_SUBROUTINE_UNIFORM = 37618;
    public static final int GL_COMPUTE_SUBROUTINE_UNIFORM = 37619;
    public static final int GL_TRANSFORM_FEEDBACK_VARYING = 37620;
    public static final int GL_ACTIVE_RESOURCES = 37621;
    public static final int GL_MAX_NAME_LENGTH = 37622;
    public static final int GL_MAX_NUM_ACTIVE_VARIABLES = 37623;
    public static final int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 37624;
    public static final int GL_NAME_LENGTH = 37625;
    public static final int GL_TYPE = 37626;
    public static final int GL_ARRAY_SIZE = 37627;
    public static final int GL_OFFSET = 37628;
    public static final int GL_BLOCK_INDEX = 37629;
    public static final int GL_ARRAY_STRIDE = 37630;
    public static final int GL_MATRIX_STRIDE = 37631;
    public static final int GL_IS_ROW_MAJOR = 37632;
    public static final int GL_ATOMIC_COUNTER_BUFFER_INDEX = 37633;
    public static final int GL_BUFFER_BINDING = 37634;
    public static final int GL_BUFFER_DATA_SIZE = 37635;
    public static final int GL_NUM_ACTIVE_VARIABLES = 37636;
    public static final int GL_ACTIVE_VARIABLES = 37637;
    public static final int GL_REFERENCED_BY_VERTEX_SHADER = 37638;
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 37639;
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 37640;
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER = 37641;
    public static final int GL_REFERENCED_BY_FRAGMENT_SHADER = 37642;
    public static final int GL_REFERENCED_BY_COMPUTE_SHADER = 37643;
    public static final int GL_TOP_LEVEL_ARRAY_SIZE = 37644;
    public static final int GL_TOP_LEVEL_ARRAY_STRIDE = 37645;
    public static final int GL_LOCATION = 37646;
    public static final int GL_LOCATION_INDEX = 37647;
    public static final int GL_IS_PER_PATCH = 37607;
    public static final int GL_SHADER_STORAGE_BUFFER = 37074;
    public static final int GL_SHADER_STORAGE_BUFFER_BINDING = 37075;
    public static final int GL_SHADER_STORAGE_BUFFER_START = 37076;
    public static final int GL_SHADER_STORAGE_BUFFER_SIZE = 37077;
    public static final int GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = 37078;
    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = 37079;
    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = 37080;
    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = 37081;
    public static final int GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = 37082;
    public static final int GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = 37083;
    public static final int GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = 37084;
    public static final int GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = 37085;
    public static final int GL_MAX_SHADER_STORAGE_BLOCK_SIZE = 37086;
    public static final int GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = 37087;
    public static final int GL_SHADER_STORAGE_BARRIER_BIT = 8192;
    public static final int GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = 36665;
    public static final int GL_DEPTH_STENCIL_TEXTURE_MODE = 37098;
    public static final int GL_TEXTURE_BUFFER_OFFSET = 37277;
    public static final int GL_TEXTURE_BUFFER_SIZE = 37278;
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 37279;
    public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 33499;
    public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 33500;
    public static final int GL_TEXTURE_VIEW_MIN_LAYER = 33501;
    public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 33502;
    public static final int GL_VERTEX_ATTRIB_BINDING = 33492;
    public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 33493;
    public static final int GL_VERTEX_BINDING_DIVISOR = 33494;
    public static final int GL_VERTEX_BINDING_OFFSET = 33495;
    public static final int GL_VERTEX_BINDING_STRIDE = 33496;
    public static final int GL_VERTEX_BINDING_BUFFER = 36687;
    public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 33497;
    public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 33498;

    protected GL43() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glClearBufferData, gLCapabilities.glClearBufferSubData, gLCapabilities.glDispatchCompute, gLCapabilities.glDispatchComputeIndirect, gLCapabilities.glCopyImageSubData, gLCapabilities.glDebugMessageControl, gLCapabilities.glDebugMessageInsert, gLCapabilities.glDebugMessageCallback, gLCapabilities.glGetDebugMessageLog, gLCapabilities.glPushDebugGroup, gLCapabilities.glPopDebugGroup, gLCapabilities.glObjectLabel, gLCapabilities.glGetObjectLabel, gLCapabilities.glObjectPtrLabel, gLCapabilities.glGetObjectPtrLabel, gLCapabilities.glFramebufferParameteri, gLCapabilities.glGetFramebufferParameteriv, gLCapabilities.glGetInternalformati64v, gLCapabilities.glInvalidateTexSubImage, gLCapabilities.glInvalidateTexImage, gLCapabilities.glInvalidateBufferSubData, gLCapabilities.glInvalidateBufferData, gLCapabilities.glInvalidateFramebuffer, gLCapabilities.glInvalidateSubFramebuffer, gLCapabilities.glMultiDrawArraysIndirect, gLCapabilities.glMultiDrawElementsIndirect, gLCapabilities.glGetProgramInterfaceiv, gLCapabilities.glGetProgramResourceIndex, gLCapabilities.glGetProgramResourceName, gLCapabilities.glGetProgramResourceiv, gLCapabilities.glGetProgramResourceLocation, gLCapabilities.glGetProgramResourceLocationIndex, gLCapabilities.glShaderStorageBlockBinding, gLCapabilities.glTexBufferRange, gLCapabilities.glTexStorage2DMultisample, gLCapabilities.glTexStorage3DMultisample, gLCapabilities.glTextureView, gLCapabilities.glBindVertexBuffer, gLCapabilities.glVertexAttribFormat, gLCapabilities.glVertexAttribIFormat, gLCapabilities.glVertexAttribLFormat, gLCapabilities.glVertexAttribBinding, gLCapabilities.glVertexBindingDivisor);
    }

    public static void nglClearBufferData(int n, int n2, int n3, int n4, long l) {
        GL43C.nglClearBufferData(n, n2, n3, n4, l);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL43C.glClearBufferData(n, n2, n3, n4, byteBuffer);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL43C.glClearBufferData(n, n2, n3, n4, shortBuffer);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL43C.glClearBufferData(n, n2, n3, n4, intBuffer);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL43C.glClearBufferData(n, n2, n3, n4, floatBuffer);
    }

    public static void nglClearBufferSubData(int n, int n2, long l, long l2, int n3, int n4, long l3) {
        GL43C.nglClearBufferSubData(n, n2, l, l2, n3, n4, l3);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, byteBuffer);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, shortBuffer);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, intBuffer);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, floatBuffer);
    }

    public static void glDispatchCompute(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL43C.glDispatchCompute(n, n2, n3);
    }

    public static void glDispatchComputeIndirect(@NativeType(value="GLintptr") long l) {
        GL43C.glDispatchComputeIndirect(l);
    }

    public static void glCopyImageSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLuint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLint") int n9, @NativeType(value="GLint") int n10, @NativeType(value="GLint") int n11, @NativeType(value="GLint") int n12, @NativeType(value="GLsizei") int n13, @NativeType(value="GLsizei") int n14, @NativeType(value="GLsizei") int n15) {
        GL43C.glCopyImageSubData(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15);
    }

    public static void nglDebugMessageControl(int n, int n2, int n3, int n4, long l, boolean bl) {
        GL43C.nglDebugMessageControl(n, n2, n3, n4, l, bl);
    }

    public static void glDebugMessageControl(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @Nullable @NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glDebugMessageControl(n, n2, n3, intBuffer, bl);
    }

    public static void glDebugMessageControl(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint const *") int n4, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glDebugMessageControl(n, n2, n3, n4, bl);
    }

    public static void nglDebugMessageInsert(int n, int n2, int n3, int n4, int n5, long l) {
        GL43C.nglDebugMessageInsert(n, n2, n3, n4, n5, l);
    }

    public static void glDebugMessageInsert(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glDebugMessageInsert(n, n2, n3, n4, byteBuffer);
    }

    public static void glDebugMessageInsert(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glDebugMessageInsert(n, n2, n3, n4, charSequence);
    }

    public static void nglDebugMessageCallback(long l, long l2) {
        GL43C.nglDebugMessageCallback(l, l2);
    }

    public static void glDebugMessageCallback(@Nullable @NativeType(value="GLDEBUGPROC") GLDebugMessageCallbackI gLDebugMessageCallbackI, @NativeType(value="void const *") long l) {
        GL43C.glDebugMessageCallback(gLDebugMessageCallbackI, l);
    }

    public static int nglGetDebugMessageLog(int n, int n2, long l, long l2, long l3, long l4, long l5, long l6) {
        return GL43C.nglGetDebugMessageLog(n, n2, l, l2, l3, l4, l5, l6);
    }

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer2, @Nullable @NativeType(value="GLuint *") IntBuffer intBuffer3, @Nullable @NativeType(value="GLenum *") IntBuffer intBuffer4, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer5, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        return GL43C.glGetDebugMessageLog(n, intBuffer, intBuffer2, intBuffer3, intBuffer4, intBuffer5, byteBuffer);
    }

    public static void nglPushDebugGroup(int n, int n2, int n3, long l) {
        GL43C.nglPushDebugGroup(n, n2, n3, l);
    }

    public static void glPushDebugGroup(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glPushDebugGroup(n, n2, byteBuffer);
    }

    public static void glPushDebugGroup(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glPushDebugGroup(n, n2, charSequence);
    }

    public static void glPopDebugGroup() {
        GL43C.glPopDebugGroup();
    }

    public static void nglObjectLabel(int n, int n2, int n3, long l) {
        GL43C.nglObjectLabel(n, n2, n3, l);
    }

    public static void glObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glObjectLabel(n, n2, byteBuffer);
    }

    public static void glObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glObjectLabel(n, n2, charSequence);
    }

    public static void nglGetObjectLabel(int n, int n2, int n3, long l, long l2) {
        GL43C.nglGetObjectLabel(n, n2, n3, l, l2);
    }

    public static void glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectLabel(n, n2, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLsizei") int n3) {
        return GL43C.glGetObjectLabel(n, n2, n3);
    }

    @NativeType(value="void")
    public static String glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        return GL43.glGetObjectLabel(n, n2, GL11.glGetInteger(33512));
    }

    public static void nglObjectPtrLabel(long l, int n, long l2) {
        GL43C.nglObjectPtrLabel(l, n, l2);
    }

    public static void glObjectPtrLabel(@NativeType(value="void *") long l, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        GL43C.glObjectPtrLabel(l, byteBuffer);
    }

    public static void glObjectPtrLabel(@NativeType(value="void *") long l, @NativeType(value="GLchar const *") CharSequence charSequence) {
        GL43C.glObjectPtrLabel(l, charSequence);
    }

    public static void nglGetObjectPtrLabel(long l, int n, long l2, long l3) {
        GL43C.nglGetObjectPtrLabel(l, n, l2, l3);
    }

    public static void glGetObjectPtrLabel(@NativeType(value="void *") long l, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectPtrLabel(l, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetObjectPtrLabel(@NativeType(value="void *") long l, @NativeType(value="GLsizei") int n) {
        return GL43C.glGetObjectPtrLabel(l, n);
    }

    @NativeType(value="void")
    public static String glGetObjectPtrLabel(@NativeType(value="void *") long l) {
        return GL43.glGetObjectPtrLabel(l, GL11.glGetInteger(33512));
    }

    public static void glFramebufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL43C.glFramebufferParameteri(n, n2, n3);
    }

    public static void nglGetFramebufferParameteriv(int n, int n2, long l) {
        GL43C.nglGetFramebufferParameteriv(n, n2, l);
    }

    public static void glGetFramebufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL43C.glGetFramebufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetFramebufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL43C.glGetFramebufferParameteri(n, n2);
    }

    public static void nglGetInternalformati64v(int n, int n2, int n3, int n4, long l) {
        GL43C.nglGetInternalformati64v(n, n2, n3, n4, l);
    }

    public static void glGetInternalformati64v(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        GL43C.glGetInternalformati64v(n, n2, n3, longBuffer);
    }

    @NativeType(value="void")
    public static long glGetInternalformati64(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL43C.glGetInternalformati64(n, n2, n3);
    }

    public static void glInvalidateTexSubImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8) {
        GL43C.glInvalidateTexSubImage(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void glInvalidateTexImage(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        GL43C.glInvalidateTexImage(n, n2);
    }

    public static void glInvalidateBufferSubData(@NativeType(value="GLuint") int n, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL43C.glInvalidateBufferSubData(n, l, l2);
    }

    public static void glInvalidateBufferData(@NativeType(value="GLuint") int n) {
        GL43C.glInvalidateBufferData(n);
    }

    public static void nglInvalidateFramebuffer(int n, int n2, long l) {
        GL43C.nglInvalidateFramebuffer(n, n2, l);
    }

    public static void glInvalidateFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer) {
        GL43C.glInvalidateFramebuffer(n, intBuffer);
    }

    public static void glInvalidateFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int n2) {
        GL43C.glInvalidateFramebuffer(n, n2);
    }

    public static void nglInvalidateSubFramebuffer(int n, int n2, long l, int n3, int n4, int n5, int n6) {
        GL43C.nglInvalidateSubFramebuffer(n, n2, l, n3, n4, n5, n6);
    }

    public static void glInvalidateSubFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") IntBuffer intBuffer, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL43C.glInvalidateSubFramebuffer(n, intBuffer, n2, n3, n4, n5);
    }

    public static void glInvalidateSubFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6) {
        GL43C.glInvalidateSubFramebuffer(n, n2, n3, n4, n5, n6);
    }

    public static void nglMultiDrawArraysIndirect(int n, long l, int n2, int n3) {
        GL43C.nglMultiDrawArraysIndirect(n, l, n2, n3);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, byteBuffer, n2, n3);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, l, n2, n3);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, intBuffer, n2, n3);
    }

    public static void nglMultiDrawElementsIndirect(int n, int n2, long l, int n3, int n4) {
        GL43C.nglMultiDrawElementsIndirect(n, n2, l, n3, n4);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, byteBuffer, n3, n4);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, l, n3, n4);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, intBuffer, n3, n4);
    }

    public static void nglGetProgramInterfaceiv(int n, int n2, int n3, long l) {
        GL43C.nglGetProgramInterfaceiv(n, n2, n3, l);
    }

    public static void glGetProgramInterfaceiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL43C.glGetProgramInterfaceiv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetProgramInterfacei(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL43C.glGetProgramInterfacei(n, n2, n3);
    }

    public static int nglGetProgramResourceIndex(int n, int n2, long l) {
        return GL43C.nglGetProgramResourceIndex(n, n2, l);
    }

    @NativeType(value="GLuint")
    public static int glGetProgramResourceIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL43C.glGetProgramResourceIndex(n, n2, byteBuffer);
    }

    @NativeType(value="GLuint")
    public static int glGetProgramResourceIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL43C.glGetProgramResourceIndex(n, n2, charSequence);
    }

    public static void nglGetProgramResourceName(int n, int n2, int n3, int n4, long l, long l2) {
        GL43C.nglGetProgramResourceName(n, n2, n3, n4, l, l2);
    }

    public static void glGetProgramResourceName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetProgramResourceName(n, n2, n3, intBuffer, byteBuffer);
    }

    @NativeType(value="void")
    public static String glGetProgramResourceName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4) {
        return GL43C.glGetProgramResourceName(n, n2, n3, n4);
    }

    @NativeType(value="void")
    public static String glGetProgramResourceName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        return GL43.glGetProgramResourceName(n, n2, n3, GL43.glGetProgramInterfacei(n, n2, 37622));
    }

    public static void nglGetProgramResourceiv(int n, int n2, int n3, int n4, long l, int n5, long l2, long l3) {
        GL43C.nglGetProgramResourceiv(n, n2, n3, n4, l, n5, l2, l3);
    }

    public static void glGetProgramResourceiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum const *") IntBuffer intBuffer, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer2, @NativeType(value="GLint *") IntBuffer intBuffer3) {
        GL43C.glGetProgramResourceiv(n, n2, n3, intBuffer, intBuffer2, intBuffer3);
    }

    public static int nglGetProgramResourceLocation(int n, int n2, long l) {
        return GL43C.nglGetProgramResourceLocation(n, n2, l);
    }

    @NativeType(value="GLint")
    public static int glGetProgramResourceLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL43C.glGetProgramResourceLocation(n, n2, byteBuffer);
    }

    @NativeType(value="GLint")
    public static int glGetProgramResourceLocation(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL43C.glGetProgramResourceLocation(n, n2, charSequence);
    }

    public static int nglGetProgramResourceLocationIndex(int n, int n2, long l) {
        return GL43C.nglGetProgramResourceLocationIndex(n, n2, l);
    }

    @NativeType(value="GLint")
    public static int glGetProgramResourceLocationIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") ByteBuffer byteBuffer) {
        return GL43C.glGetProgramResourceLocationIndex(n, n2, byteBuffer);
    }

    @NativeType(value="GLint")
    public static int glGetProgramResourceLocationIndex(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLchar const *") CharSequence charSequence) {
        return GL43C.glGetProgramResourceLocationIndex(n, n2, charSequence);
    }

    public static void glShaderStorageBlockBinding(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3) {
        GL43C.glShaderStorageBlockBinding(n, n2, n3);
    }

    public static void glTexBufferRange(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2) {
        GL43C.glTexBufferRange(n, n2, n3, l, l2);
    }

    public static void glTexStorage2DMultisample(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glTexStorage2DMultisample(n, n2, n3, n4, n5, bl);
    }

    public static void glTexStorage3DMultisample(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glTexStorage3DMultisample(n, n2, n3, n4, n5, n6, bl);
    }

    public static void glTextureView(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="GLuint") int n5, @NativeType(value="GLuint") int n6, @NativeType(value="GLuint") int n7, @NativeType(value="GLuint") int n8) {
        GL43C.glTextureView(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void glBindVertexBuffer(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3) {
        GL43C.glBindVertexBuffer(n, n2, l, n3);
    }

    public static void glVertexAttribFormat(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n4) {
        GL43C.glVertexAttribFormat(n, n2, n3, bl, n4);
    }

    public static void glVertexAttribIFormat(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4) {
        GL43C.glVertexAttribIFormat(n, n2, n3, n4);
    }

    public static void glVertexAttribLFormat(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4) {
        GL43C.glVertexAttribLFormat(n, n2, n3, n4);
    }

    public static void glVertexAttribBinding(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL43C.glVertexAttribBinding(n, n2);
    }

    public static void glVertexBindingDivisor(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2) {
        GL43C.glVertexBindingDivisor(n, n2);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL43C.glClearBufferData(n, n2, n3, n4, sArray);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL43C.glClearBufferData(n, n2, n3, n4, nArray);
    }

    public static void glClearBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL43C.glClearBufferData(n, n2, n3, n4, fArray);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, sArray);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, nArray);
    }

    public static void glClearBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizeiptr") long l2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL43C.glClearBufferSubData(n, n2, l, l2, n3, n4, fArray);
    }

    public static void glDebugMessageControl(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @Nullable @NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLboolean") boolean bl) {
        GL43C.glDebugMessageControl(n, n2, n3, nArray, bl);
    }

    @NativeType(value="GLuint")
    public static int glGetDebugMessageLog(@NativeType(value="GLuint") int n, @Nullable @NativeType(value="GLenum *") int[] nArray, @Nullable @NativeType(value="GLenum *") int[] nArray2, @Nullable @NativeType(value="GLuint *") int[] nArray3, @Nullable @NativeType(value="GLenum *") int[] nArray4, @Nullable @NativeType(value="GLsizei *") int[] nArray5, @Nullable @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        return GL43C.glGetDebugMessageLog(n, nArray, nArray2, nArray3, nArray4, nArray5, byteBuffer);
    }

    public static void glGetObjectLabel(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectLabel(n, n2, nArray, byteBuffer);
    }

    public static void glGetObjectPtrLabel(@NativeType(value="void *") long l, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetObjectPtrLabel(l, nArray, byteBuffer);
    }

    public static void glGetFramebufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL43C.glGetFramebufferParameteriv(n, n2, nArray);
    }

    public static void glGetInternalformati64v(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint64 *") long[] lArray) {
        GL43C.glGetInternalformati64v(n, n2, n3, lArray);
    }

    public static void glInvalidateFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int[] nArray) {
        GL43C.glInvalidateFramebuffer(n, nArray);
    }

    public static void glInvalidateSubFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum const *") int[] nArray, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL43C.glInvalidateSubFramebuffer(n, nArray, n2, n3, n4, n5);
    }

    public static void glMultiDrawArraysIndirect(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        GL43C.glMultiDrawArraysIndirect(n, nArray, n2, n3);
    }

    public static void glMultiDrawElementsIndirect(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL43C.glMultiDrawElementsIndirect(n, n2, nArray, n3, n4);
    }

    public static void glGetProgramInterfaceiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL43C.glGetProgramInterfaceiv(n, n2, n3, nArray);
    }

    public static void glGetProgramResourceName(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLchar *") ByteBuffer byteBuffer) {
        GL43C.glGetProgramResourceName(n, n2, n3, nArray, byteBuffer);
    }

    public static void glGetProgramResourceiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum const *") int[] nArray, @Nullable @NativeType(value="GLsizei *") int[] nArray2, @NativeType(value="GLint *") int[] nArray3) {
        GL43C.glGetProgramResourceiv(n, n2, n3, nArray, nArray2, nArray3);
    }

    static {
        GL.initialize();
    }
}

