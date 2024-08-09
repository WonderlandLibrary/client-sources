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
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL11 {
    public static final int GL_ACCUM = 256;
    public static final int GL_LOAD = 257;
    public static final int GL_RETURN = 258;
    public static final int GL_MULT = 259;
    public static final int GL_ADD = 260;
    public static final int GL_NEVER = 512;
    public static final int GL_LESS = 513;
    public static final int GL_EQUAL = 514;
    public static final int GL_LEQUAL = 515;
    public static final int GL_GREATER = 516;
    public static final int GL_NOTEQUAL = 517;
    public static final int GL_GEQUAL = 518;
    public static final int GL_ALWAYS = 519;
    public static final int GL_CURRENT_BIT = 1;
    public static final int GL_POINT_BIT = 2;
    public static final int GL_LINE_BIT = 4;
    public static final int GL_POLYGON_BIT = 8;
    public static final int GL_POLYGON_STIPPLE_BIT = 16;
    public static final int GL_PIXEL_MODE_BIT = 32;
    public static final int GL_LIGHTING_BIT = 64;
    public static final int GL_FOG_BIT = 128;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_ACCUM_BUFFER_BIT = 512;
    public static final int GL_STENCIL_BUFFER_BIT = 1024;
    public static final int GL_VIEWPORT_BIT = 2048;
    public static final int GL_TRANSFORM_BIT = 4096;
    public static final int GL_ENABLE_BIT = 8192;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_HINT_BIT = 32768;
    public static final int GL_EVAL_BIT = 65536;
    public static final int GL_LIST_BIT = 131072;
    public static final int GL_TEXTURE_BIT = 262144;
    public static final int GL_SCISSOR_BIT = 524288;
    public static final int GL_ALL_ATTRIB_BITS = 1048575;
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_LOOP = 2;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_TRIANGLE_STRIP = 5;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_QUADS = 7;
    public static final int GL_QUAD_STRIP = 8;
    public static final int GL_POLYGON = 9;
    public static final int GL_ZERO = 0;
    public static final int GL_ONE = 1;
    public static final int GL_SRC_COLOR = 768;
    public static final int GL_ONE_MINUS_SRC_COLOR = 769;
    public static final int GL_SRC_ALPHA = 770;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static final int GL_DST_ALPHA = 772;
    public static final int GL_ONE_MINUS_DST_ALPHA = 773;
    public static final int GL_DST_COLOR = 774;
    public static final int GL_ONE_MINUS_DST_COLOR = 775;
    public static final int GL_SRC_ALPHA_SATURATE = 776;
    public static final int GL_TRUE = 1;
    public static final int GL_FALSE = 0;
    public static final int GL_CLIP_PLANE0 = 12288;
    public static final int GL_CLIP_PLANE1 = 12289;
    public static final int GL_CLIP_PLANE2 = 12290;
    public static final int GL_CLIP_PLANE3 = 12291;
    public static final int GL_CLIP_PLANE4 = 12292;
    public static final int GL_CLIP_PLANE5 = 12293;
    public static final int GL_BYTE = 5120;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_SHORT = 5122;
    public static final int GL_UNSIGNED_SHORT = 5123;
    public static final int GL_INT = 5124;
    public static final int GL_UNSIGNED_INT = 5125;
    public static final int GL_FLOAT = 5126;
    public static final int GL_2_BYTES = 5127;
    public static final int GL_3_BYTES = 5128;
    public static final int GL_4_BYTES = 5129;
    public static final int GL_DOUBLE = 5130;
    public static final int GL_NONE = 0;
    public static final int GL_FRONT_LEFT = 1024;
    public static final int GL_FRONT_RIGHT = 1025;
    public static final int GL_BACK_LEFT = 1026;
    public static final int GL_BACK_RIGHT = 1027;
    public static final int GL_FRONT = 1028;
    public static final int GL_BACK = 1029;
    public static final int GL_LEFT = 1030;
    public static final int GL_RIGHT = 1031;
    public static final int GL_FRONT_AND_BACK = 1032;
    public static final int GL_AUX0 = 1033;
    public static final int GL_AUX1 = 1034;
    public static final int GL_AUX2 = 1035;
    public static final int GL_AUX3 = 1036;
    public static final int GL_NO_ERROR = 0;
    public static final int GL_INVALID_ENUM = 1280;
    public static final int GL_INVALID_VALUE = 1281;
    public static final int GL_INVALID_OPERATION = 1282;
    public static final int GL_STACK_OVERFLOW = 1283;
    public static final int GL_STACK_UNDERFLOW = 1284;
    public static final int GL_OUT_OF_MEMORY = 1285;
    public static final int GL_2D = 1536;
    public static final int GL_3D = 1537;
    public static final int GL_3D_COLOR = 1538;
    public static final int GL_3D_COLOR_TEXTURE = 1539;
    public static final int GL_4D_COLOR_TEXTURE = 1540;
    public static final int GL_PASS_THROUGH_TOKEN = 1792;
    public static final int GL_POINT_TOKEN = 1793;
    public static final int GL_LINE_TOKEN = 1794;
    public static final int GL_POLYGON_TOKEN = 1795;
    public static final int GL_BITMAP_TOKEN = 1796;
    public static final int GL_DRAW_PIXEL_TOKEN = 1797;
    public static final int GL_COPY_PIXEL_TOKEN = 1798;
    public static final int GL_LINE_RESET_TOKEN = 1799;
    public static final int GL_EXP = 2048;
    public static final int GL_EXP2 = 2049;
    public static final int GL_CW = 2304;
    public static final int GL_CCW = 2305;
    public static final int GL_COEFF = 2560;
    public static final int GL_ORDER = 2561;
    public static final int GL_DOMAIN = 2562;
    public static final int GL_CURRENT_COLOR = 2816;
    public static final int GL_CURRENT_INDEX = 2817;
    public static final int GL_CURRENT_NORMAL = 2818;
    public static final int GL_CURRENT_TEXTURE_COORDS = 2819;
    public static final int GL_CURRENT_RASTER_COLOR = 2820;
    public static final int GL_CURRENT_RASTER_INDEX = 2821;
    public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 2822;
    public static final int GL_CURRENT_RASTER_POSITION = 2823;
    public static final int GL_CURRENT_RASTER_POSITION_VALID = 2824;
    public static final int GL_CURRENT_RASTER_DISTANCE = 2825;
    public static final int GL_POINT_SMOOTH = 2832;
    public static final int GL_POINT_SIZE = 2833;
    public static final int GL_POINT_SIZE_RANGE = 2834;
    public static final int GL_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_LINE_SMOOTH = 2848;
    public static final int GL_LINE_WIDTH = 2849;
    public static final int GL_LINE_WIDTH_RANGE = 2850;
    public static final int GL_LINE_WIDTH_GRANULARITY = 2851;
    public static final int GL_LINE_STIPPLE = 2852;
    public static final int GL_LINE_STIPPLE_PATTERN = 2853;
    public static final int GL_LINE_STIPPLE_REPEAT = 2854;
    public static final int GL_LIST_MODE = 2864;
    public static final int GL_MAX_LIST_NESTING = 2865;
    public static final int GL_LIST_BASE = 2866;
    public static final int GL_LIST_INDEX = 2867;
    public static final int GL_POLYGON_MODE = 2880;
    public static final int GL_POLYGON_SMOOTH = 2881;
    public static final int GL_POLYGON_STIPPLE = 2882;
    public static final int GL_EDGE_FLAG = 2883;
    public static final int GL_CULL_FACE = 2884;
    public static final int GL_CULL_FACE_MODE = 2885;
    public static final int GL_FRONT_FACE = 2886;
    public static final int GL_LIGHTING = 2896;
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 2897;
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 2898;
    public static final int GL_LIGHT_MODEL_AMBIENT = 2899;
    public static final int GL_SHADE_MODEL = 2900;
    public static final int GL_COLOR_MATERIAL_FACE = 2901;
    public static final int GL_COLOR_MATERIAL_PARAMETER = 2902;
    public static final int GL_COLOR_MATERIAL = 2903;
    public static final int GL_FOG = 2912;
    public static final int GL_FOG_INDEX = 2913;
    public static final int GL_FOG_DENSITY = 2914;
    public static final int GL_FOG_START = 2915;
    public static final int GL_FOG_END = 2916;
    public static final int GL_FOG_MODE = 2917;
    public static final int GL_FOG_COLOR = 2918;
    public static final int GL_DEPTH_RANGE = 2928;
    public static final int GL_DEPTH_TEST = 2929;
    public static final int GL_DEPTH_WRITEMASK = 2930;
    public static final int GL_DEPTH_CLEAR_VALUE = 2931;
    public static final int GL_DEPTH_FUNC = 2932;
    public static final int GL_ACCUM_CLEAR_VALUE = 2944;
    public static final int GL_STENCIL_TEST = 2960;
    public static final int GL_STENCIL_CLEAR_VALUE = 2961;
    public static final int GL_STENCIL_FUNC = 2962;
    public static final int GL_STENCIL_VALUE_MASK = 2963;
    public static final int GL_STENCIL_FAIL = 2964;
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 2965;
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 2966;
    public static final int GL_STENCIL_REF = 2967;
    public static final int GL_STENCIL_WRITEMASK = 2968;
    public static final int GL_MATRIX_MODE = 2976;
    public static final int GL_NORMALIZE = 2977;
    public static final int GL_VIEWPORT = 2978;
    public static final int GL_MODELVIEW_STACK_DEPTH = 2979;
    public static final int GL_PROJECTION_STACK_DEPTH = 2980;
    public static final int GL_TEXTURE_STACK_DEPTH = 2981;
    public static final int GL_MODELVIEW_MATRIX = 2982;
    public static final int GL_PROJECTION_MATRIX = 2983;
    public static final int GL_TEXTURE_MATRIX = 2984;
    public static final int GL_ATTRIB_STACK_DEPTH = 2992;
    public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = 2993;
    public static final int GL_ALPHA_TEST = 3008;
    public static final int GL_ALPHA_TEST_FUNC = 3009;
    public static final int GL_ALPHA_TEST_REF = 3010;
    public static final int GL_DITHER = 3024;
    public static final int GL_BLEND_DST = 3040;
    public static final int GL_BLEND_SRC = 3041;
    public static final int GL_BLEND = 3042;
    public static final int GL_LOGIC_OP_MODE = 3056;
    public static final int GL_INDEX_LOGIC_OP = 3057;
    public static final int GL_LOGIC_OP = 3057;
    public static final int GL_COLOR_LOGIC_OP = 3058;
    public static final int GL_AUX_BUFFERS = 3072;
    public static final int GL_DRAW_BUFFER = 3073;
    public static final int GL_READ_BUFFER = 3074;
    public static final int GL_SCISSOR_BOX = 3088;
    public static final int GL_SCISSOR_TEST = 3089;
    public static final int GL_INDEX_CLEAR_VALUE = 3104;
    public static final int GL_INDEX_WRITEMASK = 3105;
    public static final int GL_COLOR_CLEAR_VALUE = 3106;
    public static final int GL_COLOR_WRITEMASK = 3107;
    public static final int GL_INDEX_MODE = 3120;
    public static final int GL_RGBA_MODE = 3121;
    public static final int GL_DOUBLEBUFFER = 3122;
    public static final int GL_STEREO = 3123;
    public static final int GL_RENDER_MODE = 3136;
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 3152;
    public static final int GL_POINT_SMOOTH_HINT = 3153;
    public static final int GL_LINE_SMOOTH_HINT = 3154;
    public static final int GL_POLYGON_SMOOTH_HINT = 3155;
    public static final int GL_FOG_HINT = 3156;
    public static final int GL_TEXTURE_GEN_S = 3168;
    public static final int GL_TEXTURE_GEN_T = 3169;
    public static final int GL_TEXTURE_GEN_R = 3170;
    public static final int GL_TEXTURE_GEN_Q = 3171;
    public static final int GL_PIXEL_MAP_I_TO_I = 3184;
    public static final int GL_PIXEL_MAP_S_TO_S = 3185;
    public static final int GL_PIXEL_MAP_I_TO_R = 3186;
    public static final int GL_PIXEL_MAP_I_TO_G = 3187;
    public static final int GL_PIXEL_MAP_I_TO_B = 3188;
    public static final int GL_PIXEL_MAP_I_TO_A = 3189;
    public static final int GL_PIXEL_MAP_R_TO_R = 3190;
    public static final int GL_PIXEL_MAP_G_TO_G = 3191;
    public static final int GL_PIXEL_MAP_B_TO_B = 3192;
    public static final int GL_PIXEL_MAP_A_TO_A = 3193;
    public static final int GL_PIXEL_MAP_I_TO_I_SIZE = 3248;
    public static final int GL_PIXEL_MAP_S_TO_S_SIZE = 3249;
    public static final int GL_PIXEL_MAP_I_TO_R_SIZE = 3250;
    public static final int GL_PIXEL_MAP_I_TO_G_SIZE = 3251;
    public static final int GL_PIXEL_MAP_I_TO_B_SIZE = 3252;
    public static final int GL_PIXEL_MAP_I_TO_A_SIZE = 3253;
    public static final int GL_PIXEL_MAP_R_TO_R_SIZE = 3254;
    public static final int GL_PIXEL_MAP_G_TO_G_SIZE = 3255;
    public static final int GL_PIXEL_MAP_B_TO_B_SIZE = 3256;
    public static final int GL_PIXEL_MAP_A_TO_A_SIZE = 3257;
    public static final int GL_UNPACK_SWAP_BYTES = 3312;
    public static final int GL_UNPACK_LSB_FIRST = 3313;
    public static final int GL_UNPACK_ROW_LENGTH = 3314;
    public static final int GL_UNPACK_SKIP_ROWS = 3315;
    public static final int GL_UNPACK_SKIP_PIXELS = 3316;
    public static final int GL_UNPACK_ALIGNMENT = 3317;
    public static final int GL_PACK_SWAP_BYTES = 3328;
    public static final int GL_PACK_LSB_FIRST = 3329;
    public static final int GL_PACK_ROW_LENGTH = 3330;
    public static final int GL_PACK_SKIP_ROWS = 3331;
    public static final int GL_PACK_SKIP_PIXELS = 3332;
    public static final int GL_PACK_ALIGNMENT = 3333;
    public static final int GL_MAP_COLOR = 3344;
    public static final int GL_MAP_STENCIL = 3345;
    public static final int GL_INDEX_SHIFT = 3346;
    public static final int GL_INDEX_OFFSET = 3347;
    public static final int GL_RED_SCALE = 3348;
    public static final int GL_RED_BIAS = 3349;
    public static final int GL_ZOOM_X = 3350;
    public static final int GL_ZOOM_Y = 3351;
    public static final int GL_GREEN_SCALE = 3352;
    public static final int GL_GREEN_BIAS = 3353;
    public static final int GL_BLUE_SCALE = 3354;
    public static final int GL_BLUE_BIAS = 3355;
    public static final int GL_ALPHA_SCALE = 3356;
    public static final int GL_ALPHA_BIAS = 3357;
    public static final int GL_DEPTH_SCALE = 3358;
    public static final int GL_DEPTH_BIAS = 3359;
    public static final int GL_MAX_EVAL_ORDER = 3376;
    public static final int GL_MAX_LIGHTS = 3377;
    public static final int GL_MAX_CLIP_PLANES = 3378;
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_MAX_PIXEL_MAP_TABLE = 3380;
    public static final int GL_MAX_ATTRIB_STACK_DEPTH = 3381;
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 3382;
    public static final int GL_MAX_NAME_STACK_DEPTH = 3383;
    public static final int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
    public static final int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
    public static final int GL_MAX_VIEWPORT_DIMS = 3386;
    public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 3387;
    public static final int GL_SUBPIXEL_BITS = 3408;
    public static final int GL_INDEX_BITS = 3409;
    public static final int GL_RED_BITS = 3410;
    public static final int GL_GREEN_BITS = 3411;
    public static final int GL_BLUE_BITS = 3412;
    public static final int GL_ALPHA_BITS = 3413;
    public static final int GL_DEPTH_BITS = 3414;
    public static final int GL_STENCIL_BITS = 3415;
    public static final int GL_ACCUM_RED_BITS = 3416;
    public static final int GL_ACCUM_GREEN_BITS = 3417;
    public static final int GL_ACCUM_BLUE_BITS = 3418;
    public static final int GL_ACCUM_ALPHA_BITS = 3419;
    public static final int GL_NAME_STACK_DEPTH = 3440;
    public static final int GL_AUTO_NORMAL = 3456;
    public static final int GL_MAP1_COLOR_4 = 3472;
    public static final int GL_MAP1_INDEX = 3473;
    public static final int GL_MAP1_NORMAL = 3474;
    public static final int GL_MAP1_TEXTURE_COORD_1 = 3475;
    public static final int GL_MAP1_TEXTURE_COORD_2 = 3476;
    public static final int GL_MAP1_TEXTURE_COORD_3 = 3477;
    public static final int GL_MAP1_TEXTURE_COORD_4 = 3478;
    public static final int GL_MAP1_VERTEX_3 = 3479;
    public static final int GL_MAP1_VERTEX_4 = 3480;
    public static final int GL_MAP2_COLOR_4 = 3504;
    public static final int GL_MAP2_INDEX = 3505;
    public static final int GL_MAP2_NORMAL = 3506;
    public static final int GL_MAP2_TEXTURE_COORD_1 = 3507;
    public static final int GL_MAP2_TEXTURE_COORD_2 = 3508;
    public static final int GL_MAP2_TEXTURE_COORD_3 = 3509;
    public static final int GL_MAP2_TEXTURE_COORD_4 = 3510;
    public static final int GL_MAP2_VERTEX_3 = 3511;
    public static final int GL_MAP2_VERTEX_4 = 3512;
    public static final int GL_MAP1_GRID_DOMAIN = 3536;
    public static final int GL_MAP1_GRID_SEGMENTS = 3537;
    public static final int GL_MAP2_GRID_DOMAIN = 3538;
    public static final int GL_MAP2_GRID_SEGMENTS = 3539;
    public static final int GL_TEXTURE_1D = 3552;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_FEEDBACK_BUFFER_POINTER = 3568;
    public static final int GL_FEEDBACK_BUFFER_SIZE = 3569;
    public static final int GL_FEEDBACK_BUFFER_TYPE = 3570;
    public static final int GL_SELECTION_BUFFER_POINTER = 3571;
    public static final int GL_SELECTION_BUFFER_SIZE = 3572;
    public static final int GL_TEXTURE_WIDTH = 4096;
    public static final int GL_TEXTURE_HEIGHT = 4097;
    public static final int GL_TEXTURE_INTERNAL_FORMAT = 4099;
    public static final int GL_TEXTURE_COMPONENTS = 4099;
    public static final int GL_TEXTURE_BORDER_COLOR = 4100;
    public static final int GL_TEXTURE_BORDER = 4101;
    public static final int GL_DONT_CARE = 4352;
    public static final int GL_FASTEST = 4353;
    public static final int GL_NICEST = 4354;
    public static final int GL_LIGHT0 = 16384;
    public static final int GL_LIGHT1 = 16385;
    public static final int GL_LIGHT2 = 16386;
    public static final int GL_LIGHT3 = 16387;
    public static final int GL_LIGHT4 = 16388;
    public static final int GL_LIGHT5 = 16389;
    public static final int GL_LIGHT6 = 16390;
    public static final int GL_LIGHT7 = 16391;
    public static final int GL_AMBIENT = 4608;
    public static final int GL_DIFFUSE = 4609;
    public static final int GL_SPECULAR = 4610;
    public static final int GL_POSITION = 4611;
    public static final int GL_SPOT_DIRECTION = 4612;
    public static final int GL_SPOT_EXPONENT = 4613;
    public static final int GL_SPOT_CUTOFF = 4614;
    public static final int GL_CONSTANT_ATTENUATION = 4615;
    public static final int GL_LINEAR_ATTENUATION = 4616;
    public static final int GL_QUADRATIC_ATTENUATION = 4617;
    public static final int GL_COMPILE = 4864;
    public static final int GL_COMPILE_AND_EXECUTE = 4865;
    public static final int GL_CLEAR = 5376;
    public static final int GL_AND = 5377;
    public static final int GL_AND_REVERSE = 5378;
    public static final int GL_COPY = 5379;
    public static final int GL_AND_INVERTED = 5380;
    public static final int GL_NOOP = 5381;
    public static final int GL_XOR = 5382;
    public static final int GL_OR = 5383;
    public static final int GL_NOR = 5384;
    public static final int GL_EQUIV = 5385;
    public static final int GL_INVERT = 5386;
    public static final int GL_OR_REVERSE = 5387;
    public static final int GL_COPY_INVERTED = 5388;
    public static final int GL_OR_INVERTED = 5389;
    public static final int GL_NAND = 5390;
    public static final int GL_SET = 5391;
    public static final int GL_EMISSION = 5632;
    public static final int GL_SHININESS = 5633;
    public static final int GL_AMBIENT_AND_DIFFUSE = 5634;
    public static final int GL_COLOR_INDEXES = 5635;
    public static final int GL_MODELVIEW = 5888;
    public static final int GL_PROJECTION = 5889;
    public static final int GL_TEXTURE = 5890;
    public static final int GL_COLOR = 6144;
    public static final int GL_DEPTH = 6145;
    public static final int GL_STENCIL = 6146;
    public static final int GL_COLOR_INDEX = 6400;
    public static final int GL_STENCIL_INDEX = 6401;
    public static final int GL_DEPTH_COMPONENT = 6402;
    public static final int GL_RED = 6403;
    public static final int GL_GREEN = 6404;
    public static final int GL_BLUE = 6405;
    public static final int GL_ALPHA = 6406;
    public static final int GL_RGB = 6407;
    public static final int GL_RGBA = 6408;
    public static final int GL_LUMINANCE = 6409;
    public static final int GL_LUMINANCE_ALPHA = 6410;
    public static final int GL_BITMAP = 6656;
    public static final int GL_POINT = 6912;
    public static final int GL_LINE = 6913;
    public static final int GL_FILL = 6914;
    public static final int GL_RENDER = 7168;
    public static final int GL_FEEDBACK = 7169;
    public static final int GL_SELECT = 7170;
    public static final int GL_FLAT = 7424;
    public static final int GL_SMOOTH = 7425;
    public static final int GL_KEEP = 7680;
    public static final int GL_REPLACE = 7681;
    public static final int GL_INCR = 7682;
    public static final int GL_DECR = 7683;
    public static final int GL_VENDOR = 7936;
    public static final int GL_RENDERER = 7937;
    public static final int GL_VERSION = 7938;
    public static final int GL_EXTENSIONS = 7939;
    public static final int GL_S = 8192;
    public static final int GL_T = 8193;
    public static final int GL_R = 8194;
    public static final int GL_Q = 8195;
    public static final int GL_MODULATE = 8448;
    public static final int GL_DECAL = 8449;
    public static final int GL_TEXTURE_ENV_MODE = 8704;
    public static final int GL_TEXTURE_ENV_COLOR = 8705;
    public static final int GL_TEXTURE_ENV = 8960;
    public static final int GL_EYE_LINEAR = 9216;
    public static final int GL_OBJECT_LINEAR = 9217;
    public static final int GL_SPHERE_MAP = 9218;
    public static final int GL_TEXTURE_GEN_MODE = 9472;
    public static final int GL_OBJECT_PLANE = 9473;
    public static final int GL_EYE_PLANE = 9474;
    public static final int GL_NEAREST = 9728;
    public static final int GL_LINEAR = 9729;
    public static final int GL_NEAREST_MIPMAP_NEAREST = 9984;
    public static final int GL_LINEAR_MIPMAP_NEAREST = 9985;
    public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
    public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
    public static final int GL_TEXTURE_MAG_FILTER = 10240;
    public static final int GL_TEXTURE_MIN_FILTER = 10241;
    public static final int GL_TEXTURE_WRAP_S = 10242;
    public static final int GL_TEXTURE_WRAP_T = 10243;
    public static final int GL_CLAMP = 10496;
    public static final int GL_REPEAT = 10497;
    public static final int GL_CLIENT_PIXEL_STORE_BIT = 1;
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 2;
    public static final int GL_CLIENT_ALL_ATTRIB_BITS = -1;
    public static final int GL_POLYGON_OFFSET_FACTOR = 32824;
    public static final int GL_POLYGON_OFFSET_UNITS = 10752;
    public static final int GL_POLYGON_OFFSET_POINT = 10753;
    public static final int GL_POLYGON_OFFSET_LINE = 10754;
    public static final int GL_POLYGON_OFFSET_FILL = 32823;
    public static final int GL_ALPHA4 = 32827;
    public static final int GL_ALPHA8 = 32828;
    public static final int GL_ALPHA12 = 32829;
    public static final int GL_ALPHA16 = 32830;
    public static final int GL_LUMINANCE4 = 32831;
    public static final int GL_LUMINANCE8 = 32832;
    public static final int GL_LUMINANCE12 = 32833;
    public static final int GL_LUMINANCE16 = 32834;
    public static final int GL_LUMINANCE4_ALPHA4 = 32835;
    public static final int GL_LUMINANCE6_ALPHA2 = 32836;
    public static final int GL_LUMINANCE8_ALPHA8 = 32837;
    public static final int GL_LUMINANCE12_ALPHA4 = 32838;
    public static final int GL_LUMINANCE12_ALPHA12 = 32839;
    public static final int GL_LUMINANCE16_ALPHA16 = 32840;
    public static final int GL_INTENSITY = 32841;
    public static final int GL_INTENSITY4 = 32842;
    public static final int GL_INTENSITY8 = 32843;
    public static final int GL_INTENSITY12 = 32844;
    public static final int GL_INTENSITY16 = 32845;
    public static final int GL_R3_G3_B2 = 10768;
    public static final int GL_RGB4 = 32847;
    public static final int GL_RGB5 = 32848;
    public static final int GL_RGB8 = 32849;
    public static final int GL_RGB10 = 32850;
    public static final int GL_RGB12 = 32851;
    public static final int GL_RGB16 = 32852;
    public static final int GL_RGBA2 = 32853;
    public static final int GL_RGBA4 = 32854;
    public static final int GL_RGB5_A1 = 32855;
    public static final int GL_RGBA8 = 32856;
    public static final int GL_RGB10_A2 = 32857;
    public static final int GL_RGBA12 = 32858;
    public static final int GL_RGBA16 = 32859;
    public static final int GL_TEXTURE_RED_SIZE = 32860;
    public static final int GL_TEXTURE_GREEN_SIZE = 32861;
    public static final int GL_TEXTURE_BLUE_SIZE = 32862;
    public static final int GL_TEXTURE_ALPHA_SIZE = 32863;
    public static final int GL_TEXTURE_LUMINANCE_SIZE = 32864;
    public static final int GL_TEXTURE_INTENSITY_SIZE = 32865;
    public static final int GL_PROXY_TEXTURE_1D = 32867;
    public static final int GL_PROXY_TEXTURE_2D = 32868;
    public static final int GL_TEXTURE_PRIORITY = 32870;
    public static final int GL_TEXTURE_RESIDENT = 32871;
    public static final int GL_TEXTURE_BINDING_1D = 32872;
    public static final int GL_TEXTURE_BINDING_2D = 32873;
    public static final int GL_VERTEX_ARRAY = 32884;
    public static final int GL_NORMAL_ARRAY = 32885;
    public static final int GL_COLOR_ARRAY = 32886;
    public static final int GL_INDEX_ARRAY = 32887;
    public static final int GL_TEXTURE_COORD_ARRAY = 32888;
    public static final int GL_EDGE_FLAG_ARRAY = 32889;
    public static final int GL_VERTEX_ARRAY_SIZE = 32890;
    public static final int GL_VERTEX_ARRAY_TYPE = 32891;
    public static final int GL_VERTEX_ARRAY_STRIDE = 32892;
    public static final int GL_NORMAL_ARRAY_TYPE = 32894;
    public static final int GL_NORMAL_ARRAY_STRIDE = 32895;
    public static final int GL_COLOR_ARRAY_SIZE = 32897;
    public static final int GL_COLOR_ARRAY_TYPE = 32898;
    public static final int GL_COLOR_ARRAY_STRIDE = 32899;
    public static final int GL_INDEX_ARRAY_TYPE = 32901;
    public static final int GL_INDEX_ARRAY_STRIDE = 32902;
    public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 32904;
    public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 32905;
    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 32906;
    public static final int GL_EDGE_FLAG_ARRAY_STRIDE = 32908;
    public static final int GL_VERTEX_ARRAY_POINTER = 32910;
    public static final int GL_NORMAL_ARRAY_POINTER = 32911;
    public static final int GL_COLOR_ARRAY_POINTER = 32912;
    public static final int GL_INDEX_ARRAY_POINTER = 32913;
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 32914;
    public static final int GL_EDGE_FLAG_ARRAY_POINTER = 32915;
    public static final int GL_V2F = 10784;
    public static final int GL_V3F = 10785;
    public static final int GL_C4UB_V2F = 10786;
    public static final int GL_C4UB_V3F = 10787;
    public static final int GL_C3F_V3F = 10788;
    public static final int GL_N3F_V3F = 10789;
    public static final int GL_C4F_N3F_V3F = 10790;
    public static final int GL_T2F_V3F = 10791;
    public static final int GL_T4F_V4F = 10792;
    public static final int GL_T2F_C4UB_V3F = 10793;
    public static final int GL_T2F_C3F_V3F = 10794;
    public static final int GL_T2F_N3F_V3F = 10795;
    public static final int GL_T2F_C4F_N3F_V3F = 10796;
    public static final int GL_T4F_C4F_N3F_V4F = 10797;

    protected GL11() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set, boolean bl) {
        return (bl || Checks.checkFunctions(gLCapabilities.glAccum, gLCapabilities.glAlphaFunc, gLCapabilities.glAreTexturesResident, gLCapabilities.glArrayElement, gLCapabilities.glBegin, gLCapabilities.glBitmap, gLCapabilities.glCallList, gLCapabilities.glCallLists, gLCapabilities.glClearAccum, gLCapabilities.glClearIndex, gLCapabilities.glClipPlane, gLCapabilities.glColor3b, gLCapabilities.glColor3s, gLCapabilities.glColor3i, gLCapabilities.glColor3f, gLCapabilities.glColor3d, gLCapabilities.glColor3ub, gLCapabilities.glColor3us, gLCapabilities.glColor3ui, gLCapabilities.glColor3bv, gLCapabilities.glColor3sv, gLCapabilities.glColor3iv, gLCapabilities.glColor3fv, gLCapabilities.glColor3dv, gLCapabilities.glColor3ubv, gLCapabilities.glColor3usv, gLCapabilities.glColor3uiv, gLCapabilities.glColor4b, gLCapabilities.glColor4s, gLCapabilities.glColor4i, gLCapabilities.glColor4f, gLCapabilities.glColor4d, gLCapabilities.glColor4ub, gLCapabilities.glColor4us, gLCapabilities.glColor4ui, gLCapabilities.glColor4bv, gLCapabilities.glColor4sv, gLCapabilities.glColor4iv, gLCapabilities.glColor4fv, gLCapabilities.glColor4dv, gLCapabilities.glColor4ubv, gLCapabilities.glColor4usv, gLCapabilities.glColor4uiv, gLCapabilities.glColorMaterial, gLCapabilities.glColorPointer, gLCapabilities.glCopyPixels, gLCapabilities.glDeleteLists, gLCapabilities.glDrawPixels, gLCapabilities.glEdgeFlag, gLCapabilities.glEdgeFlagv, gLCapabilities.glEdgeFlagPointer, gLCapabilities.glEnd, gLCapabilities.glEvalCoord1f, gLCapabilities.glEvalCoord1fv, gLCapabilities.glEvalCoord1d, gLCapabilities.glEvalCoord1dv, gLCapabilities.glEvalCoord2f, gLCapabilities.glEvalCoord2fv, gLCapabilities.glEvalCoord2d, gLCapabilities.glEvalCoord2dv, gLCapabilities.glEvalMesh1, gLCapabilities.glEvalMesh2, gLCapabilities.glEvalPoint1, gLCapabilities.glEvalPoint2, gLCapabilities.glFeedbackBuffer, gLCapabilities.glFogi, gLCapabilities.glFogiv, gLCapabilities.glFogf, gLCapabilities.glFogfv, gLCapabilities.glGenLists, gLCapabilities.glGetClipPlane, gLCapabilities.glGetLightiv, gLCapabilities.glGetLightfv, gLCapabilities.glGetMapiv, gLCapabilities.glGetMapfv, gLCapabilities.glGetMapdv, gLCapabilities.glGetMaterialiv, gLCapabilities.glGetMaterialfv, gLCapabilities.glGetPixelMapfv, gLCapabilities.glGetPixelMapusv, gLCapabilities.glGetPixelMapuiv, gLCapabilities.glGetPolygonStipple, gLCapabilities.glGetTexEnviv, gLCapabilities.glGetTexEnvfv, gLCapabilities.glGetTexGeniv, gLCapabilities.glGetTexGenfv, gLCapabilities.glGetTexGendv, gLCapabilities.glIndexi, gLCapabilities.glIndexub, gLCapabilities.glIndexs, gLCapabilities.glIndexf, gLCapabilities.glIndexd, gLCapabilities.glIndexiv, gLCapabilities.glIndexubv, gLCapabilities.glIndexsv, gLCapabilities.glIndexfv, gLCapabilities.glIndexdv, gLCapabilities.glIndexMask, gLCapabilities.glIndexPointer, gLCapabilities.glInitNames, gLCapabilities.glInterleavedArrays, gLCapabilities.glIsList, gLCapabilities.glLightModeli, gLCapabilities.glLightModelf, gLCapabilities.glLightModeliv, gLCapabilities.glLightModelfv, gLCapabilities.glLighti, gLCapabilities.glLightf, gLCapabilities.glLightiv, gLCapabilities.glLightfv, gLCapabilities.glLineStipple, gLCapabilities.glListBase, gLCapabilities.glLoadMatrixf, gLCapabilities.glLoadMatrixd, gLCapabilities.glLoadIdentity, gLCapabilities.glLoadName, gLCapabilities.glMap1f, gLCapabilities.glMap1d, gLCapabilities.glMap2f, gLCapabilities.glMap2d, gLCapabilities.glMapGrid1f, gLCapabilities.glMapGrid1d, gLCapabilities.glMapGrid2f, gLCapabilities.glMapGrid2d, gLCapabilities.glMateriali, gLCapabilities.glMaterialf, gLCapabilities.glMaterialiv, gLCapabilities.glMaterialfv, gLCapabilities.glMatrixMode, gLCapabilities.glMultMatrixf, gLCapabilities.glMultMatrixd, gLCapabilities.glFrustum, gLCapabilities.glNewList, gLCapabilities.glEndList, gLCapabilities.glNormal3f, gLCapabilities.glNormal3b, gLCapabilities.glNormal3s, gLCapabilities.glNormal3i, gLCapabilities.glNormal3d, gLCapabilities.glNormal3fv, gLCapabilities.glNormal3bv, gLCapabilities.glNormal3sv, gLCapabilities.glNormal3iv, gLCapabilities.glNormal3dv, gLCapabilities.glNormalPointer, gLCapabilities.glOrtho, gLCapabilities.glPassThrough, gLCapabilities.glPixelMapfv, gLCapabilities.glPixelMapusv, gLCapabilities.glPixelMapuiv, gLCapabilities.glPixelTransferi, gLCapabilities.glPixelTransferf, gLCapabilities.glPixelZoom, gLCapabilities.glPolygonStipple, gLCapabilities.glPushAttrib, gLCapabilities.glPushClientAttrib, gLCapabilities.glPopAttrib, gLCapabilities.glPopClientAttrib, gLCapabilities.glPopMatrix, gLCapabilities.glPopName, gLCapabilities.glPrioritizeTextures, gLCapabilities.glPushMatrix, gLCapabilities.glPushName, gLCapabilities.glRasterPos2i, gLCapabilities.glRasterPos2s, gLCapabilities.glRasterPos2f, gLCapabilities.glRasterPos2d, gLCapabilities.glRasterPos2iv, gLCapabilities.glRasterPos2sv, gLCapabilities.glRasterPos2fv, gLCapabilities.glRasterPos2dv, gLCapabilities.glRasterPos3i, gLCapabilities.glRasterPos3s, gLCapabilities.glRasterPos3f, gLCapabilities.glRasterPos3d, gLCapabilities.glRasterPos3iv, gLCapabilities.glRasterPos3sv, gLCapabilities.glRasterPos3fv, gLCapabilities.glRasterPos3dv, gLCapabilities.glRasterPos4i, gLCapabilities.glRasterPos4s, gLCapabilities.glRasterPos4f, gLCapabilities.glRasterPos4d, gLCapabilities.glRasterPos4iv, gLCapabilities.glRasterPos4sv, gLCapabilities.glRasterPos4fv, gLCapabilities.glRasterPos4dv, gLCapabilities.glRecti, gLCapabilities.glRects, gLCapabilities.glRectf, gLCapabilities.glRectd, gLCapabilities.glRectiv, gLCapabilities.glRectsv, gLCapabilities.glRectfv, gLCapabilities.glRectdv, gLCapabilities.glRenderMode, gLCapabilities.glRotatef, gLCapabilities.glRotated, gLCapabilities.glScalef, gLCapabilities.glScaled, gLCapabilities.glSelectBuffer, gLCapabilities.glShadeModel, gLCapabilities.glTexCoord1f, gLCapabilities.glTexCoord1s, gLCapabilities.glTexCoord1i, gLCapabilities.glTexCoord1d, gLCapabilities.glTexCoord1fv, gLCapabilities.glTexCoord1sv, gLCapabilities.glTexCoord1iv, gLCapabilities.glTexCoord1dv, gLCapabilities.glTexCoord2f, gLCapabilities.glTexCoord2s, gLCapabilities.glTexCoord2i, gLCapabilities.glTexCoord2d, gLCapabilities.glTexCoord2fv, gLCapabilities.glTexCoord2sv, gLCapabilities.glTexCoord2iv, gLCapabilities.glTexCoord2dv, gLCapabilities.glTexCoord3f, gLCapabilities.glTexCoord3s, gLCapabilities.glTexCoord3i, gLCapabilities.glTexCoord3d, gLCapabilities.glTexCoord3fv, gLCapabilities.glTexCoord3sv, gLCapabilities.glTexCoord3iv, gLCapabilities.glTexCoord3dv, gLCapabilities.glTexCoord4f, gLCapabilities.glTexCoord4s, gLCapabilities.glTexCoord4i, gLCapabilities.glTexCoord4d, gLCapabilities.glTexCoord4fv, gLCapabilities.glTexCoord4sv, gLCapabilities.glTexCoord4iv, gLCapabilities.glTexCoord4dv, gLCapabilities.glTexCoordPointer, gLCapabilities.glTexEnvi, gLCapabilities.glTexEnviv, gLCapabilities.glTexEnvf, gLCapabilities.glTexEnvfv, gLCapabilities.glTexGeni, gLCapabilities.glTexGeniv, gLCapabilities.glTexGenf, gLCapabilities.glTexGenfv, gLCapabilities.glTexGend, gLCapabilities.glTexGendv, gLCapabilities.glTranslatef, gLCapabilities.glTranslated, gLCapabilities.glVertex2f, gLCapabilities.glVertex2s, gLCapabilities.glVertex2i, gLCapabilities.glVertex2d, gLCapabilities.glVertex2fv, gLCapabilities.glVertex2sv, gLCapabilities.glVertex2iv, gLCapabilities.glVertex2dv, gLCapabilities.glVertex3f, gLCapabilities.glVertex3s, gLCapabilities.glVertex3i, gLCapabilities.glVertex3d, gLCapabilities.glVertex3fv, gLCapabilities.glVertex3sv, gLCapabilities.glVertex3iv, gLCapabilities.glVertex3dv, gLCapabilities.glVertex4f, gLCapabilities.glVertex4s, gLCapabilities.glVertex4i, gLCapabilities.glVertex4d, gLCapabilities.glVertex4fv, gLCapabilities.glVertex4sv, gLCapabilities.glVertex4iv, gLCapabilities.glVertex4dv, gLCapabilities.glVertexPointer)) && Checks.checkFunctions(gLCapabilities.glEnable, gLCapabilities.glDisable, gLCapabilities.glBindTexture, gLCapabilities.glBlendFunc, gLCapabilities.glClear, gLCapabilities.glClearColor, gLCapabilities.glClearDepth, gLCapabilities.glClearStencil, gLCapabilities.glColorMask, gLCapabilities.glCullFace, gLCapabilities.glDepthFunc, gLCapabilities.glDepthMask, gLCapabilities.glDepthRange, set.contains("GL_NV_vertex_buffer_unified_memory") ? gLCapabilities.glDisableClientState : -1L, gLCapabilities.glDrawArrays, gLCapabilities.glDrawBuffer, gLCapabilities.glDrawElements, set.contains("GL_NV_vertex_buffer_unified_memory") ? gLCapabilities.glEnableClientState : -1L, gLCapabilities.glFinish, gLCapabilities.glFlush, gLCapabilities.glFrontFace, gLCapabilities.glGenTextures, gLCapabilities.glDeleteTextures, gLCapabilities.glGetBooleanv, gLCapabilities.glGetFloatv, gLCapabilities.glGetIntegerv, gLCapabilities.glGetDoublev, gLCapabilities.glGetError, gLCapabilities.glGetPointerv, gLCapabilities.glGetString, gLCapabilities.glGetTexImage, gLCapabilities.glGetTexLevelParameteriv, gLCapabilities.glGetTexLevelParameterfv, gLCapabilities.glGetTexParameteriv, gLCapabilities.glGetTexParameterfv, gLCapabilities.glHint, gLCapabilities.glIsEnabled, gLCapabilities.glIsTexture, gLCapabilities.glLineWidth, gLCapabilities.glLogicOp, gLCapabilities.glPixelStorei, gLCapabilities.glPixelStoref, gLCapabilities.glPointSize, gLCapabilities.glPolygonMode, gLCapabilities.glPolygonOffset, gLCapabilities.glReadBuffer, gLCapabilities.glReadPixels, gLCapabilities.glScissor, gLCapabilities.glStencilFunc, gLCapabilities.glStencilMask, gLCapabilities.glStencilOp, gLCapabilities.glTexImage1D, gLCapabilities.glTexImage2D, gLCapabilities.glCopyTexImage1D, gLCapabilities.glCopyTexImage2D, gLCapabilities.glCopyTexSubImage1D, gLCapabilities.glCopyTexSubImage2D, gLCapabilities.glTexParameteri, gLCapabilities.glTexParameteriv, gLCapabilities.glTexParameterf, gLCapabilities.glTexParameterfv, gLCapabilities.glTexSubImage1D, gLCapabilities.glTexSubImage2D, gLCapabilities.glViewport);
    }

    public static void glEnable(@NativeType(value="GLenum") int n) {
        GL11C.glEnable(n);
    }

    public static void glDisable(@NativeType(value="GLenum") int n) {
        GL11C.glDisable(n);
    }

    public static native void glAccum(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glAlphaFunc(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native boolean nglAreTexturesResident(int var0, long var1, long var3);

    @NativeType(value="GLboolean")
    public static boolean glAreTexturesResident(@NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, intBuffer.remaining());
        }
        return GL11.nglAreTexturesResident(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="GLboolean")
    public static boolean glAreTexturesResident(@NativeType(value="GLuint const *") int n, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            boolean bl = GL11.nglAreTexturesResident(1, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(byteBuffer));
            return bl;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void glArrayElement(@NativeType(value="GLint") int var0);

    public static native void glBegin(@NativeType(value="GLenum") int var0);

    public static void glBindTexture(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL11C.glBindTexture(n, n2);
    }

    public static native void nglBitmap(int var0, int var1, float var2, float var3, float var4, float var5, long var6);

    public static void glBitmap(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4, @Nullable @NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)byteBuffer, (n + 7 >> 3) * n2);
        }
        GL11.nglBitmap(n, n2, f, f2, f3, f4, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glBitmap(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4, @Nullable @NativeType(value="GLubyte const *") long l) {
        GL11.nglBitmap(n, n2, f, f2, f3, f4, l);
    }

    public static void glBlendFunc(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        GL11C.glBlendFunc(n, n2);
    }

    public static native void glCallList(@NativeType(value="GLuint") int var0);

    public static native void nglCallLists(int var0, int var1, long var2);

    public static void glCallLists(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglCallLists(byteBuffer.remaining() / GLChecks.typeToBytes(n), n, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glCallLists(@NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglCallLists(byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glCallLists(@NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglCallLists(shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glCallLists(@NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglCallLists(intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer));
    }

    public static void glClear(@NativeType(value="GLbitfield") int n) {
        GL11C.glClear(n);
    }

    public static native void glClearAccum(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static void glClearColor(@NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4) {
        GL11C.glClearColor(f, f2, f3, f4);
    }

    public static void glClearDepth(@NativeType(value="GLdouble") double d) {
        GL11C.glClearDepth(d);
    }

    public static native void glClearIndex(@NativeType(value="GLfloat") float var0);

    public static void glClearStencil(@NativeType(value="GLint") int n) {
        GL11C.glClearStencil(n);
    }

    public static native void nglClipPlane(int var0, long var1);

    public static void glClipPlane(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglClipPlane(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glColor3b(@NativeType(value="GLbyte") byte var0, @NativeType(value="GLbyte") byte var1, @NativeType(value="GLbyte") byte var2);

    public static native void glColor3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glColor3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glColor3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glColor3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void glColor3ub(@NativeType(value="GLubyte") byte var0, @NativeType(value="GLubyte") byte var1, @NativeType(value="GLubyte") byte var2);

    public static native void glColor3us(@NativeType(value="GLushort") short var0, @NativeType(value="GLushort") short var1, @NativeType(value="GLushort") short var2);

    public static native void glColor3ui(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void nglColor3bv(long var0);

    public static void glColor3bv(@NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        GL11.nglColor3bv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglColor3sv(long var0);

    public static void glColor3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL11.nglColor3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglColor3iv(long var0);

    public static void glColor3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL11.nglColor3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglColor3fv(long var0);

    public static void glColor3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL11.nglColor3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglColor3dv(long var0);

    public static void glColor3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL11.nglColor3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglColor3ubv(long var0);

    public static void glColor3ubv(@NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        GL11.nglColor3ubv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglColor3usv(long var0);

    public static void glColor3usv(@NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL11.nglColor3usv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglColor3uiv(long var0);

    public static void glColor3uiv(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL11.nglColor3uiv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void glColor4b(@NativeType(value="GLbyte") byte var0, @NativeType(value="GLbyte") byte var1, @NativeType(value="GLbyte") byte var2, @NativeType(value="GLbyte") byte var3);

    public static native void glColor4s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glColor4i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glColor4f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glColor4d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void glColor4ub(@NativeType(value="GLubyte") byte var0, @NativeType(value="GLubyte") byte var1, @NativeType(value="GLubyte") byte var2, @NativeType(value="GLubyte") byte var3);

    public static native void glColor4us(@NativeType(value="GLushort") short var0, @NativeType(value="GLushort") short var1, @NativeType(value="GLushort") short var2, @NativeType(value="GLushort") short var3);

    public static native void glColor4ui(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void nglColor4bv(long var0);

    public static void glColor4bv(@NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        GL11.nglColor4bv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglColor4sv(long var0);

    public static void glColor4sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL11.nglColor4sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglColor4iv(long var0);

    public static void glColor4iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglColor4iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglColor4fv(long var0);

    public static void glColor4fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglColor4fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglColor4dv(long var0);

    public static void glColor4dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglColor4dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglColor4ubv(long var0);

    public static void glColor4ubv(@NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 4);
        }
        GL11.nglColor4ubv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglColor4usv(long var0);

    public static void glColor4usv(@NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL11.nglColor4usv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglColor4uiv(long var0);

    public static void glColor4uiv(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglColor4uiv(MemoryUtil.memAddress(intBuffer));
    }

    public static void glColorMask(@NativeType(value="GLboolean") boolean bl, @NativeType(value="GLboolean") boolean bl2, @NativeType(value="GLboolean") boolean bl3, @NativeType(value="GLboolean") boolean bl4) {
        GL11C.glColorMask(bl, bl2, bl3, bl4);
    }

    public static native void glColorMaterial(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    public static native void nglColorPointer(int var0, int var1, int var2, long var3);

    public static void glColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglColorPointer(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") long l) {
        GL11.nglColorPointer(n, n2, n3, l);
    }

    public static void glColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglColorPointer(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglColorPointer(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glColorPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11.nglColorPointer(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glCopyPixels(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLenum") int var4);

    public static void glCullFace(@NativeType(value="GLenum") int n) {
        GL11C.glCullFace(n);
    }

    public static native void glDeleteLists(@NativeType(value="GLuint") int var0, @NativeType(value="GLsizei") int var1);

    public static void glDepthFunc(@NativeType(value="GLenum") int n) {
        GL11C.glDepthFunc(n);
    }

    public static void glDepthMask(@NativeType(value="GLboolean") boolean bl) {
        GL11C.glDepthMask(bl);
    }

    public static void glDepthRange(@NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2) {
        GL11C.glDepthRange(d, d2);
    }

    public static native void glDisableClientState(@NativeType(value="GLenum") int var0);

    public static void glDrawArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3) {
        GL11C.glDrawArrays(n, n2, n3);
    }

    public static void glDrawBuffer(@NativeType(value="GLenum") int n) {
        GL11C.glDrawBuffer(n);
    }

    public static void nglDrawElements(int n, int n2, int n3, long l) {
        GL11C.nglDrawElements(n, n2, n3, l);
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l) {
        GL11C.glDrawElements(n, n2, n3, l);
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.glDrawElements(n, n2, byteBuffer);
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.glDrawElements(n, byteBuffer);
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.glDrawElements(n, shortBuffer);
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.glDrawElements(n, intBuffer);
    }

    public static native void nglDrawPixels(int var0, int var1, int var2, int var3, long var4);

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglDrawPixels(n, n2, n3, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") long l) {
        GL11.nglDrawPixels(n, n2, n3, n4, l);
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglDrawPixels(n, n2, n3, n4, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglDrawPixels(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11.nglDrawPixels(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glEdgeFlag(@NativeType(value="GLboolean") boolean var0);

    public static native void nglEdgeFlagv(long var0);

    public static void glEdgeFlagv(@NativeType(value="GLboolean const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 1);
        }
        GL11.nglEdgeFlagv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglEdgeFlagPointer(int var0, long var1);

    public static void glEdgeFlagPointer(@NativeType(value="GLsizei") int n, @NativeType(value="GLboolean const *") ByteBuffer byteBuffer) {
        GL11.nglEdgeFlagPointer(n, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glEdgeFlagPointer(@NativeType(value="GLsizei") int n, @NativeType(value="GLboolean const *") long l) {
        GL11.nglEdgeFlagPointer(n, l);
    }

    public static native void glEnableClientState(@NativeType(value="GLenum") int var0);

    public static native void glEnd();

    public static native void glEvalCoord1f(@NativeType(value="GLfloat") float var0);

    public static native void nglEvalCoord1fv(long var0);

    public static void glEvalCoord1fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11.nglEvalCoord1fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glEvalCoord1d(@NativeType(value="GLdouble") double var0);

    public static native void nglEvalCoord1dv(long var0);

    public static void glEvalCoord1dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL11.nglEvalCoord1dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glEvalCoord2f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void nglEvalCoord2fv(long var0);

    public static void glEvalCoord2fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        GL11.nglEvalCoord2fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glEvalCoord2d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void nglEvalCoord2dv(long var0);

    public static void glEvalCoord2dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL11.nglEvalCoord2dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glEvalMesh1(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glEvalMesh2(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4);

    public static native void glEvalPoint1(@NativeType(value="GLint") int var0);

    public static native void glEvalPoint2(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void nglFeedbackBuffer(int var0, int var1, long var2);

    public static void glFeedbackBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL11.nglFeedbackBuffer(floatBuffer.remaining(), n, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glFinish() {
        GL11C.glFinish();
    }

    public static void glFlush() {
        GL11C.glFlush();
    }

    public static native void glFogi(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void nglFogiv(int var0, long var1);

    public static void glFogiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11.nglFogiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glFogf(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native void nglFogfv(int var0, long var1);

    public static void glFogfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11.nglFogfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glFrontFace(@NativeType(value="GLenum") int n) {
        GL11C.glFrontFace(n);
    }

    @NativeType(value="GLuint")
    public static native int glGenLists(@NativeType(value="GLsizei") int var0);

    public static void nglGenTextures(int n, long l) {
        GL11C.nglGenTextures(n, l);
    }

    public static void glGenTextures(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL11C.glGenTextures(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenTextures() {
        return GL11C.glGenTextures();
    }

    public static void nglDeleteTextures(int n, long l) {
        GL11C.nglDeleteTextures(n, l);
    }

    public static void glDeleteTextures(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL11C.glDeleteTextures(intBuffer);
    }

    public static void glDeleteTextures(@NativeType(value="GLuint const *") int n) {
        GL11C.glDeleteTextures(n);
    }

    public static native void nglGetClipPlane(int var0, long var1);

    public static void glGetClipPlane(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglGetClipPlane(n, MemoryUtil.memAddress(doubleBuffer));
    }

    public static void nglGetBooleanv(int n, long l) {
        GL11C.nglGetBooleanv(n, l);
    }

    public static void glGetBooleanv(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        GL11C.glGetBooleanv(n, byteBuffer);
    }

    @NativeType(value="void")
    public static boolean glGetBoolean(@NativeType(value="GLenum") int n) {
        return GL11C.glGetBoolean(n);
    }

    public static void nglGetFloatv(int n, long l) {
        GL11C.nglGetFloatv(n, l);
    }

    public static void glGetFloatv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL11C.glGetFloatv(n, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetFloat(@NativeType(value="GLenum") int n) {
        return GL11C.glGetFloat(n);
    }

    public static void nglGetIntegerv(int n, long l) {
        GL11C.nglGetIntegerv(n, l);
    }

    public static void glGetIntegerv(@NativeType(value="GLenum") int n, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL11C.glGetIntegerv(n, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetInteger(@NativeType(value="GLenum") int n) {
        return GL11C.glGetInteger(n);
    }

    public static void nglGetDoublev(int n, long l) {
        GL11C.nglGetDoublev(n, l);
    }

    public static void glGetDoublev(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        GL11C.glGetDoublev(n, doubleBuffer);
    }

    @NativeType(value="void")
    public static double glGetDouble(@NativeType(value="GLenum") int n) {
        return GL11C.glGetDouble(n);
    }

    @NativeType(value="GLenum")
    public static int glGetError() {
        return GL11C.glGetError();
    }

    public static native void nglGetLightiv(int var0, int var1, long var2);

    public static void glGetLightiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglGetLightiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetLighti(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11.nglGetLightiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetLightfv(int var0, int var1, long var2);

    public static void glGetLightfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglGetLightfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetLightf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11.nglGetLightfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetMapiv(int var0, int var1, long var2);

    public static void glGetMapiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglGetMapiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetMapi(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11.nglGetMapiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetMapfv(int var0, int var1, long var2);

    public static void glGetMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglGetMapfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMapf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11.nglGetMapfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetMapdv(int var0, int var1, long var2);

    public static void glGetMapdv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglGetMapdv(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetMapd(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            GL11.nglGetMapdv(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetMaterialiv(int var0, int var1, long var2);

    public static void glGetMaterialiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11.nglGetMaterialiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglGetMaterialfv(int var0, int var1, long var2);

    public static void glGetMaterialfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11.nglGetMaterialfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglGetPixelMapfv(int var0, long var1);

    public static void glGetPixelMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 32);
        }
        GL11.nglGetPixelMapfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetPixelMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") long l) {
        GL11.nglGetPixelMapfv(n, l);
    }

    public static native void nglGetPixelMapusv(int var0, long var1);

    public static void glGetPixelMapusv(@NativeType(value="GLenum") int n, @NativeType(value="GLushort *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 32);
        }
        GL11.nglGetPixelMapusv(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetPixelMapusv(@NativeType(value="GLenum") int n, @NativeType(value="GLushort *") long l) {
        GL11.nglGetPixelMapusv(n, l);
    }

    public static native void nglGetPixelMapuiv(int var0, long var1);

    public static void glGetPixelMapuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 32);
        }
        GL11.nglGetPixelMapuiv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetPixelMapuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") long l) {
        GL11.nglGetPixelMapuiv(n, l);
    }

    public static void nglGetPointerv(int n, long l) {
        GL11C.nglGetPointerv(n, l);
    }

    public static void glGetPointerv(@NativeType(value="GLenum") int n, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        GL11C.glGetPointerv(n, pointerBuffer);
    }

    @NativeType(value="void")
    public static long glGetPointer(@NativeType(value="GLenum") int n) {
        return GL11C.glGetPointer(n);
    }

    public static native void nglGetPolygonStipple(long var0);

    public static void glGetPolygonStipple(@NativeType(value="void *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 128);
        }
        GL11.nglGetPolygonStipple(MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetPolygonStipple(@NativeType(value="void *") long l) {
        GL11.nglGetPolygonStipple(l);
    }

    public static long nglGetString(int n) {
        return GL11C.nglGetString(n);
    }

    @Nullable
    @NativeType(value="GLubyte const *")
    public static String glGetString(@NativeType(value="GLenum") int n) {
        return GL11C.glGetString(n);
    }

    public static native void nglGetTexEnviv(int var0, int var1, long var2);

    public static void glGetTexEnviv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11.nglGetTexEnviv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTexEnvi(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11.nglGetTexEnviv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetTexEnvfv(int var0, int var1, long var2);

    public static void glGetTexEnvfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11.nglGetTexEnvfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetTexEnvf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11.nglGetTexEnvfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetTexGeniv(int var0, int var1, long var2);

    public static void glGetTexGeniv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11.nglGetTexGeniv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTexGeni(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11.nglGetTexGeniv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetTexGenfv(int var0, int var1, long var2);

    public static void glGetTexGenfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglGetTexGenfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetTexGenf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11.nglGetTexGenfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetTexGendv(int var0, int var1, long var2);

    public static void glGetTexGendv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglGetTexGendv(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetTexGend(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            GL11.nglGetTexGendv(n, n2, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void nglGetTexImage(int n, int n2, int n3, int n4, long l) {
        GL11C.nglGetTexImage(n, n2, n3, n4, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL11C.glGetTexImage(n, n2, n3, n4, byteBuffer);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") long l) {
        GL11C.glGetTexImage(n, n2, n3, n4, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL11C.glGetTexImage(n, n2, n3, n4, shortBuffer);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") IntBuffer intBuffer) {
        GL11C.glGetTexImage(n, n2, n3, n4, intBuffer);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL11C.glGetTexImage(n, n2, n3, n4, floatBuffer);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL11C.glGetTexImage(n, n2, n3, n4, doubleBuffer);
    }

    public static void nglGetTexLevelParameteriv(int n, int n2, int n3, long l) {
        GL11C.nglGetTexLevelParameteriv(n, n2, n3, l);
    }

    public static void glGetTexLevelParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL11C.glGetTexLevelParameteriv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTexLevelParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3) {
        return GL11C.glGetTexLevelParameteri(n, n2, n3);
    }

    public static void nglGetTexLevelParameterfv(int n, int n2, int n3, long l) {
        GL11C.nglGetTexLevelParameterfv(n, n2, n3, l);
    }

    public static void glGetTexLevelParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL11C.glGetTexLevelParameterfv(n, n2, n3, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetTexLevelParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3) {
        return GL11C.glGetTexLevelParameterf(n, n2, n3);
    }

    public static void nglGetTexParameteriv(int n, int n2, long l) {
        GL11C.nglGetTexParameteriv(n, n2, l);
    }

    public static void glGetTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL11C.glGetTexParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetTexParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL11C.glGetTexParameteri(n, n2);
    }

    public static void nglGetTexParameterfv(int n, int n2, long l) {
        GL11C.nglGetTexParameterfv(n, n2, l);
    }

    public static void glGetTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL11C.glGetTexParameterfv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetTexParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL11C.glGetTexParameterf(n, n2);
    }

    public static void glHint(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        GL11C.glHint(n, n2);
    }

    public static native void glIndexi(@NativeType(value="GLint") int var0);

    public static native void glIndexub(@NativeType(value="GLubyte") byte var0);

    public static native void glIndexs(@NativeType(value="GLshort") short var0);

    public static native void glIndexf(@NativeType(value="GLfloat") float var0);

    public static native void glIndexd(@NativeType(value="GLdouble") double var0);

    public static native void nglIndexiv(long var0);

    public static void glIndexiv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11.nglIndexiv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglIndexubv(long var0);

    public static void glIndexubv(@NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 1);
        }
        GL11.nglIndexubv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglIndexsv(long var0);

    public static void glIndexsv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        GL11.nglIndexsv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglIndexfv(long var0);

    public static void glIndexfv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11.nglIndexfv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglIndexdv(long var0);

    public static void glIndexdv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL11.nglIndexdv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glIndexMask(@NativeType(value="GLuint") int var0);

    public static native void nglIndexPointer(int var0, int var1, long var2);

    public static void glIndexPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglIndexPointer(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glIndexPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") long l) {
        GL11.nglIndexPointer(n, n2, l);
    }

    public static void glIndexPointer(@NativeType(value="GLsizei") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglIndexPointer(5121, n, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glIndexPointer(@NativeType(value="GLsizei") int n, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglIndexPointer(5122, n, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glIndexPointer(@NativeType(value="GLsizei") int n, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglIndexPointer(5124, n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glInitNames();

    public static native void nglInterleavedArrays(int var0, int var1, long var2);

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglInterleavedArrays(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") long l) {
        GL11.nglInterleavedArrays(n, n2, l);
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglInterleavedArrays(n, n2, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglInterleavedArrays(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11.nglInterleavedArrays(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11.nglInterleavedArrays(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    @NativeType(value="GLboolean")
    public static boolean glIsEnabled(@NativeType(value="GLenum") int n) {
        return GL11C.glIsEnabled(n);
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsList(@NativeType(value="GLuint") int var0);

    @NativeType(value="GLboolean")
    public static boolean glIsTexture(@NativeType(value="GLuint") int n) {
        return GL11C.glIsTexture(n);
    }

    public static native void glLightModeli(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void glLightModelf(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native void nglLightModeliv(int var0, long var1);

    public static void glLightModeliv(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglLightModeliv(n, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglLightModelfv(int var0, long var1);

    public static void glLightModelfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglLightModelfv(n, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glLighti(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void glLightf(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLfloat") float var2);

    public static native void nglLightiv(int var0, int var1, long var2);

    public static void glLightiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglLightiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglLightfv(int var0, int var1, long var2);

    public static void glLightfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglLightfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glLineStipple(@NativeType(value="GLint") int var0, @NativeType(value="GLushort") short var1);

    public static void glLineWidth(@NativeType(value="GLfloat") float f) {
        GL11C.glLineWidth(f);
    }

    public static native void glListBase(@NativeType(value="GLuint") int var0);

    public static native void nglLoadMatrixf(long var0);

    public static void glLoadMatrixf(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        GL11.nglLoadMatrixf(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglLoadMatrixd(long var0);

    public static void glLoadMatrixd(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        GL11.nglLoadMatrixd(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glLoadIdentity();

    public static native void glLoadName(@NativeType(value="GLuint") int var0);

    public static void glLogicOp(@NativeType(value="GLenum") int n) {
        GL11C.glLogicOp(n);
    }

    public static native void nglMap1f(int var0, float var1, float var2, int var3, int var4, long var5);

    public static void glMap1f(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, n3 * n2);
        }
        GL11.nglMap1f(n, f, f2, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMap1d(int var0, double var1, double var3, int var5, int var6, long var7);

    public static void glMap1d(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, n2 * n3);
        }
        GL11.nglMap1d(n, d, d2, n2, n3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglMap2f(int var0, float var1, float var2, int var3, int var4, float var5, float var6, int var7, int var8, long var9);

    public static void glMap2f(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, n2 * n3 * n4 * n5);
        }
        GL11.nglMap2f(n, f, f2, n2, n3, f3, f4, n4, n5, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMap2d(int var0, double var1, double var3, int var5, int var6, double var7, double var9, int var11, int var12, long var13);

    public static void glMap2d(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLdouble") double d3, @NativeType(value="GLdouble") double d4, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, n2 * n3 * n4 * n5);
        }
        GL11.nglMap2d(n, d, d2, n2, n3, d3, d4, n4, n5, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glMapGrid1f(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glMapGrid1d(@NativeType(value="GLint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3);

    public static native void glMapGrid2f(@NativeType(value="GLint") int var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLint") int var3, @NativeType(value="GLfloat") float var4, @NativeType(value="GLfloat") float var5);

    public static native void glMapGrid2d(@NativeType(value="GLint") int var0, @NativeType(value="GLdouble") double var1, @NativeType(value="GLdouble") double var3, @NativeType(value="GLint") int var5, @NativeType(value="GLdouble") double var6, @NativeType(value="GLdouble") double var8);

    public static native void glMateriali(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void glMaterialf(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLfloat") float var2);

    public static native void nglMaterialiv(int var0, int var1, long var2);

    public static void glMaterialiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglMaterialiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglMaterialfv(int var0, int var1, long var2);

    public static void glMaterialfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglMaterialfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glMatrixMode(@NativeType(value="GLenum") int var0);

    public static native void nglMultMatrixf(long var0);

    public static void glMultMatrixf(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 16);
        }
        GL11.nglMultMatrixf(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglMultMatrixd(long var0);

    public static void glMultMatrixd(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 16);
        }
        GL11.nglMultMatrixd(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glFrustum(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6, @NativeType(value="GLdouble") double var8, @NativeType(value="GLdouble") double var10);

    public static native void glNewList(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1);

    public static native void glEndList();

    public static native void glNormal3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glNormal3b(@NativeType(value="GLbyte") byte var0, @NativeType(value="GLbyte") byte var1, @NativeType(value="GLbyte") byte var2);

    public static native void glNormal3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glNormal3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glNormal3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void nglNormal3fv(long var0);

    public static void glNormal3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL11.nglNormal3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglNormal3bv(long var0);

    public static void glNormal3bv(@NativeType(value="GLbyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 3);
        }
        GL11.nglNormal3bv(MemoryUtil.memAddress(byteBuffer));
    }

    public static native void nglNormal3sv(long var0);

    public static void glNormal3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL11.nglNormal3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglNormal3iv(long var0);

    public static void glNormal3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL11.nglNormal3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglNormal3dv(long var0);

    public static void glNormal3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL11.nglNormal3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglNormalPointer(int var0, int var1, long var2);

    public static void glNormalPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglNormalPointer(n, n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glNormalPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") long l) {
        GL11.nglNormalPointer(n, n2, l);
    }

    public static void glNormalPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglNormalPointer(n, n2, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glNormalPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglNormalPointer(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glNormalPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11.nglNormalPointer(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glOrtho(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6, @NativeType(value="GLdouble") double var8, @NativeType(value="GLdouble") double var10);

    public static native void glPassThrough(@NativeType(value="GLfloat") float var0);

    public static native void nglPixelMapfv(int var0, int var1, long var2);

    public static void glPixelMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLfloat const *") long l) {
        GL11.nglPixelMapfv(n, n2, l);
    }

    public static void glPixelMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL11.nglPixelMapfv(n, floatBuffer.remaining(), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglPixelMapusv(int var0, int var1, long var2);

    public static void glPixelMapusv(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLushort const *") long l) {
        GL11.nglPixelMapusv(n, n2, l);
    }

    public static void glPixelMapusv(@NativeType(value="GLenum") int n, @NativeType(value="GLushort const *") ShortBuffer shortBuffer) {
        GL11.nglPixelMapusv(n, shortBuffer.remaining(), MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglPixelMapuiv(int var0, int var1, long var2);

    public static void glPixelMapuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLuint const *") long l) {
        GL11.nglPixelMapuiv(n, n2, l);
    }

    public static void glPixelMapuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL11.nglPixelMapuiv(n, intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void glPixelStorei(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2) {
        GL11C.glPixelStorei(n, n2);
    }

    public static void glPixelStoref(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat") float f) {
        GL11C.glPixelStoref(n, f);
    }

    public static native void glPixelTransferi(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void glPixelTransferf(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glPixelZoom(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static void glPointSize(@NativeType(value="GLfloat") float f) {
        GL11C.glPointSize(f);
    }

    public static void glPolygonMode(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        GL11C.glPolygonMode(n, n2);
    }

    public static void glPolygonOffset(@NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2) {
        GL11C.glPolygonOffset(f, f2);
    }

    public static native void nglPolygonStipple(long var0);

    public static void glPolygonStipple(@NativeType(value="GLubyte const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 128);
        }
        GL11.nglPolygonStipple(MemoryUtil.memAddress(byteBuffer));
    }

    public static void glPolygonStipple(@NativeType(value="GLubyte const *") long l) {
        GL11.nglPolygonStipple(l);
    }

    public static native void glPushAttrib(@NativeType(value="GLbitfield") int var0);

    public static native void glPushClientAttrib(@NativeType(value="GLbitfield") int var0);

    public static native void glPopAttrib();

    public static native void glPopClientAttrib();

    public static native void glPopMatrix();

    public static native void glPopName();

    public static native void nglPrioritizeTextures(int var0, long var1, long var3);

    public static void glPrioritizeTextures(@NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, intBuffer.remaining());
        }
        GL11.nglPrioritizeTextures(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glPushMatrix();

    public static native void glPushName(@NativeType(value="GLuint") int var0);

    public static native void glRasterPos2i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glRasterPos2s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1);

    public static native void glRasterPos2f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glRasterPos2d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void nglRasterPos2iv(long var0);

    public static void glRasterPos2iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        GL11.nglRasterPos2iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglRasterPos2sv(long var0);

    public static void glRasterPos2sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        GL11.nglRasterPos2sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglRasterPos2fv(long var0);

    public static void glRasterPos2fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        GL11.nglRasterPos2fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglRasterPos2dv(long var0);

    public static void glRasterPos2dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL11.nglRasterPos2dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glRasterPos3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glRasterPos3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glRasterPos3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glRasterPos3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void nglRasterPos3iv(long var0);

    public static void glRasterPos3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL11.nglRasterPos3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglRasterPos3sv(long var0);

    public static void glRasterPos3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL11.nglRasterPos3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglRasterPos3fv(long var0);

    public static void glRasterPos3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL11.nglRasterPos3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglRasterPos3dv(long var0);

    public static void glRasterPos3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL11.nglRasterPos3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glRasterPos4i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glRasterPos4s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glRasterPos4f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glRasterPos4d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void nglRasterPos4iv(long var0);

    public static void glRasterPos4iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglRasterPos4iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglRasterPos4sv(long var0);

    public static void glRasterPos4sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL11.nglRasterPos4sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglRasterPos4fv(long var0);

    public static void glRasterPos4fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglRasterPos4fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglRasterPos4dv(long var0);

    public static void glRasterPos4dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglRasterPos4dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static void glReadBuffer(@NativeType(value="GLenum") int n) {
        GL11C.glReadBuffer(n);
    }

    public static void nglReadPixels(int n, int n2, int n3, int n4, int n5, int n6, long l) {
        GL11C.nglReadPixels(n, n2, n3, n4, n5, n6, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") long l) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, shortBuffer);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") IntBuffer intBuffer) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, intBuffer);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, floatBuffer);
    }

    public static native void glRecti(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glRects(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glRectf(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glRectd(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void nglRectiv(long var0, long var2);

    public static void glRectiv(@NativeType(value="GLint const *") IntBuffer intBuffer, @NativeType(value="GLint const *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
            Checks.check((Buffer)intBuffer2, 2);
        }
        GL11.nglRectiv(MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    public static native void nglRectsv(long var0, long var2);

    public static void glRectsv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer, @NativeType(value="GLshort const *") ShortBuffer shortBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
            Checks.check((Buffer)shortBuffer2, 2);
        }
        GL11.nglRectsv(MemoryUtil.memAddress(shortBuffer), MemoryUtil.memAddress(shortBuffer2));
    }

    public static native void nglRectfv(long var0, long var2);

    public static void glRectfv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
            Checks.check((Buffer)floatBuffer2, 2);
        }
        GL11.nglRectfv(MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2));
    }

    public static native void nglRectdv(long var0, long var2);

    public static void glRectdv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer2) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
            Checks.check((Buffer)doubleBuffer2, 2);
        }
        GL11.nglRectdv(MemoryUtil.memAddress(doubleBuffer), MemoryUtil.memAddress(doubleBuffer2));
    }

    @NativeType(value="GLint")
    public static native int glRenderMode(@NativeType(value="GLenum") int var0);

    public static native void glRotatef(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glRotated(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void glScalef(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glScaled(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static void glScissor(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL11C.glScissor(n, n2, n3, n4);
    }

    public static native void nglSelectBuffer(int var0, long var1);

    public static void glSelectBuffer(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL11.nglSelectBuffer(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static native void glShadeModel(@NativeType(value="GLenum") int var0);

    public static void glStencilFunc(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLuint") int n3) {
        GL11C.glStencilFunc(n, n2, n3);
    }

    public static void glStencilMask(@NativeType(value="GLuint") int n) {
        GL11C.glStencilMask(n);
    }

    public static void glStencilOp(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        GL11C.glStencilOp(n, n2, n3);
    }

    public static native void glTexCoord1f(@NativeType(value="GLfloat") float var0);

    public static native void glTexCoord1s(@NativeType(value="GLshort") short var0);

    public static native void glTexCoord1i(@NativeType(value="GLint") int var0);

    public static native void glTexCoord1d(@NativeType(value="GLdouble") double var0);

    public static native void nglTexCoord1fv(long var0);

    public static void glTexCoord1fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11.nglTexCoord1fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglTexCoord1sv(long var0);

    public static void glTexCoord1sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        GL11.nglTexCoord1sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglTexCoord1iv(long var0);

    public static void glTexCoord1iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11.nglTexCoord1iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoord1dv(long var0);

    public static void glTexCoord1dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL11.nglTexCoord1dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glTexCoord2f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glTexCoord2s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1);

    public static native void glTexCoord2i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glTexCoord2d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void nglTexCoord2fv(long var0);

    public static void glTexCoord2fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        GL11.nglTexCoord2fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglTexCoord2sv(long var0);

    public static void glTexCoord2sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        GL11.nglTexCoord2sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglTexCoord2iv(long var0);

    public static void glTexCoord2iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        GL11.nglTexCoord2iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoord2dv(long var0);

    public static void glTexCoord2dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL11.nglTexCoord2dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glTexCoord3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glTexCoord3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glTexCoord3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glTexCoord3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void nglTexCoord3fv(long var0);

    public static void glTexCoord3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL11.nglTexCoord3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglTexCoord3sv(long var0);

    public static void glTexCoord3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL11.nglTexCoord3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglTexCoord3iv(long var0);

    public static void glTexCoord3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL11.nglTexCoord3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoord3dv(long var0);

    public static void glTexCoord3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL11.nglTexCoord3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glTexCoord4f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glTexCoord4s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glTexCoord4i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glTexCoord4d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void nglTexCoord4fv(long var0);

    public static void glTexCoord4fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglTexCoord4fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglTexCoord4sv(long var0);

    public static void glTexCoord4sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL11.nglTexCoord4sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglTexCoord4iv(long var0);

    public static void glTexCoord4iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglTexCoord4iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglTexCoord4dv(long var0);

    public static void glTexCoord4dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglTexCoord4dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglTexCoordPointer(int var0, int var1, int var2, long var3);

    public static void glTexCoordPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglTexCoordPointer(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTexCoordPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") long l) {
        GL11.nglTexCoordPointer(n, n2, n3, l);
    }

    public static void glTexCoordPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglTexCoordPointer(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTexCoordPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglTexCoordPointer(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTexCoordPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11.nglTexCoordPointer(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glTexEnvi(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void nglTexEnviv(int var0, int var1, long var2);

    public static void glTexEnviv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglTexEnviv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glTexEnvf(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLfloat") float var2);

    public static native void nglTexEnvfv(int var0, int var1, long var2);

    public static void glTexEnvfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglTexEnvfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glTexGeni(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void nglTexGeniv(int var0, int var1, long var2);

    public static void glTexGeniv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglTexGeniv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glTexGenf(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLfloat") float var2);

    public static native void nglTexGenfv(int var0, int var1, long var2);

    public static void glTexGenfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglTexGenfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glTexGend(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLdouble") double var2);

    public static native void nglTexGendv(int var0, int var1, long var2);

    public static void glTexGendv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglTexGendv(n, n2, MemoryUtil.memAddress(doubleBuffer));
    }

    public static void nglTexImage1D(int n, int n2, int n3, int n4, int n5, int n6, int n7, long l) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, byteBuffer);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") long l) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, shortBuffer);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, intBuffer);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, floatBuffer);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, doubleBuffer);
    }

    public static void nglTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") long l) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, shortBuffer);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, floatBuffer);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, doubleBuffer);
    }

    public static void glCopyTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7) {
        GL11C.glCopyTexImage1D(n, n2, n3, n4, n5, n6, n7);
    }

    public static void glCopyTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLint") int n8) {
        GL11C.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void glCopyTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6) {
        GL11C.glCopyTexSubImage1D(n, n2, n3, n4, n5, n6);
    }

    public static void glCopyTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8) {
        GL11C.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }

    public static void glTexParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL11C.glTexParameteri(n, n2, n3);
    }

    public static void nglTexParameteriv(int n, int n2, long l) {
        GL11C.nglTexParameteriv(n, n2, l);
    }

    public static void glTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        GL11C.glTexParameteriv(n, n2, intBuffer);
    }

    public static void glTexParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat") float f) {
        GL11C.glTexParameterf(n, n2, f);
    }

    public static void nglTexParameterfv(int n, int n2, long l) {
        GL11C.nglTexParameterfv(n, n2, l);
    }

    public static void glTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL11C.glTexParameterfv(n, n2, floatBuffer);
    }

    public static void nglTexSubImage1D(int n, int n2, int n3, int n4, int n5, int n6, long l) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") long l) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, shortBuffer);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, intBuffer);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, floatBuffer);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, doubleBuffer);
    }

    public static void nglTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, long l) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") long l) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, shortBuffer);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, floatBuffer);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, doubleBuffer);
    }

    public static native void glTranslatef(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glTranslated(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void glVertex2f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glVertex2s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1);

    public static native void glVertex2i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1);

    public static native void glVertex2d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void nglVertex2fv(long var0);

    public static void glVertex2fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 2);
        }
        GL11.nglVertex2fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertex2sv(long var0);

    public static void glVertex2sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        GL11.nglVertex2sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertex2iv(long var0);

    public static void glVertex2iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 2);
        }
        GL11.nglVertex2iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertex2dv(long var0);

    public static void glVertex2dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 2);
        }
        GL11.nglVertex2dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glVertex3f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2);

    public static native void glVertex3s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2);

    public static native void glVertex3i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2);

    public static native void glVertex3d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4);

    public static native void nglVertex3fv(long var0);

    public static void glVertex3fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 3);
        }
        GL11.nglVertex3fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertex3sv(long var0);

    public static void glVertex3sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        GL11.nglVertex3sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertex3iv(long var0);

    public static void glVertex3iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 3);
        }
        GL11.nglVertex3iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertex3dv(long var0);

    public static void glVertex3dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 3);
        }
        GL11.nglVertex3dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glVertex4f(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glVertex4s(@NativeType(value="GLshort") short var0, @NativeType(value="GLshort") short var1, @NativeType(value="GLshort") short var2, @NativeType(value="GLshort") short var3);

    public static native void glVertex4i(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3);

    public static native void glVertex4d(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2, @NativeType(value="GLdouble") double var4, @NativeType(value="GLdouble") double var6);

    public static native void nglVertex4fv(long var0);

    public static void glVertex4fv(@NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11.nglVertex4fv(MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglVertex4sv(long var0);

    public static void glVertex4sv(@NativeType(value="GLshort const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        GL11.nglVertex4sv(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertex4iv(long var0);

    public static void glVertex4iv(@NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11.nglVertex4iv(MemoryUtil.memAddress(intBuffer));
    }

    public static native void nglVertex4dv(long var0);

    public static void glVertex4dv(@NativeType(value="GLdouble const *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 4);
        }
        GL11.nglVertex4dv(MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglVertexPointer(int var0, int var1, int var2, long var3);

    public static void glVertexPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11.nglVertexPointer(n, n2, n3, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glVertexPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") long l) {
        GL11.nglVertexPointer(n, n2, n3, l);
    }

    public static void glVertexPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11.nglVertexPointer(n, n2, n3, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glVertexPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11.nglVertexPointer(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    public static void glVertexPointer(@NativeType(value="GLint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11.nglVertexPointer(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glViewport(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL11C.glViewport(n, n2, n3, n4);
    }

    @NativeType(value="GLboolean")
    public static boolean glAreTexturesResident(@NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        long l = GL.getICD().glAreTexturesResident;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check((Buffer)byteBuffer, nArray.length);
        }
        return JNI.callPPZ(nArray.length, nArray, MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void glClipPlane(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glClipPlane;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glColor3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glColor3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glColor3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glColor3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glColor3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glColor3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glColor3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glColor3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glColor3usv(@NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glColor3usv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glColor3uiv(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glColor3uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glColor4sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glColor4sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glColor4iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glColor4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(nArray, l);
    }

    public static void glColor4fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glColor4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(fArray, l);
    }

    public static void glColor4dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glColor4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(dArray, l);
    }

    public static void glColor4usv(@NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glColor4usv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glColor4uiv(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glColor4uiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(nArray, l);
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glDrawPixels;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, sArray, l);
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glDrawPixels;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glDrawPixels(@NativeType(value="GLsizei") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glDrawPixels;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, fArray, l);
    }

    public static void glEvalCoord1fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glEvalCoord1fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(fArray, l);
    }

    public static void glEvalCoord1dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glEvalCoord1dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(dArray, l);
    }

    public static void glEvalCoord2fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glEvalCoord2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(fArray, l);
    }

    public static void glEvalCoord2dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glEvalCoord2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(dArray, l);
    }

    public static void glFeedbackBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glFeedbackBuffer;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(fArray.length, n, fArray, l);
    }

    public static void glFogiv(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glFogiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glFogfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glFogfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glGenTextures(@NativeType(value="GLuint *") int[] nArray) {
        GL11C.glGenTextures(nArray);
    }

    public static void glDeleteTextures(@NativeType(value="GLuint const *") int[] nArray) {
        GL11C.glDeleteTextures(nArray);
    }

    public static void glGetClipPlane(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetClipPlane;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glGetFloatv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") float[] fArray) {
        GL11C.glGetFloatv(n, fArray);
    }

    public static void glGetIntegerv(@NativeType(value="GLenum") int n, @NativeType(value="GLint *") int[] nArray) {
        GL11C.glGetIntegerv(n, nArray);
    }

    public static void glGetDoublev(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble *") double[] dArray) {
        GL11C.glGetDoublev(n, dArray);
    }

    public static void glGetLightiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetLightiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetLightfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetLightfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetMapiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMapiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMapfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetMapdv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetMapdv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetMaterialiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetMaterialiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetMaterialfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMaterialfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetPixelMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetPixelMapfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 32);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glGetPixelMapusv(@NativeType(value="GLenum") int n, @NativeType(value="GLushort *") short[] sArray) {
        long l = GL.getICD().glGetPixelMapusv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 32);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glGetPixelMapuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetPixelMapuiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 32);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glGetTexEnviv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTexEnviv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetTexEnvfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetTexEnvfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetTexGeniv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTexGeniv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetTexGenfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetTexGenfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetTexGendv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetTexGendv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") short[] sArray) {
        GL11C.glGetTexImage(n, n2, n3, n4, sArray);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") int[] nArray) {
        GL11C.glGetTexImage(n, n2, n3, n4, nArray);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") float[] fArray) {
        GL11C.glGetTexImage(n, n2, n3, n4, fArray);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") double[] dArray) {
        GL11C.glGetTexImage(n, n2, n3, n4, dArray);
    }

    public static void glGetTexLevelParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL11C.glGetTexLevelParameteriv(n, n2, n3, nArray);
    }

    public static void glGetTexLevelParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        GL11C.glGetTexLevelParameterfv(n, n2, n3, fArray);
    }

    public static void glGetTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL11C.glGetTexParameteriv(n, n2, nArray);
    }

    public static void glGetTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL11C.glGetTexParameterfv(n, n2, fArray);
    }

    public static void glIndexiv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glIndexiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(nArray, l);
    }

    public static void glIndexsv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glIndexsv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(sArray, l);
    }

    public static void glIndexfv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glIndexfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(fArray, l);
    }

    public static void glIndexdv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glIndexdv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(dArray, l);
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glInterleavedArrays;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, sArray, l);
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glInterleavedArrays;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glInterleavedArrays;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glInterleavedArrays(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glInterleavedArrays;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glLightModeliv(@NativeType(value="GLenum") int n, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glLightModeliv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glLightModelfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glLightModelfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glLightiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glLightiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glLightfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glLightfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glLoadMatrixf(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glLoadMatrixf;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(fArray, l);
    }

    public static void glLoadMatrixd(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glLoadMatrixd;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(dArray, l);
    }

    public static void glMap1f(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMap1f;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, n3 * n2);
        }
        JNI.callPV(n, f, f2, n2, n3, fArray, l);
    }

    public static void glMap1d(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMap1d;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, n2 * n3);
        }
        JNI.callPV(n, d, d2, n2, n3, dArray, l);
    }

    public static void glMap2f(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLfloat") float f3, @NativeType(value="GLfloat") float f4, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMap2f;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, n2 * n3 * n4 * n5);
        }
        JNI.callPV(n, f, f2, n2, n3, f3, f4, n4, n5, fArray, l);
    }

    public static void glMap2d(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble") double d, @NativeType(value="GLdouble") double d2, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLdouble") double d3, @NativeType(value="GLdouble") double d4, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMap2d;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, n2 * n3 * n4 * n5);
        }
        JNI.callPV(n, d, d2, n2, n3, d3, d4, n4, n5, dArray, l);
    }

    public static void glMaterialiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glMaterialiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glMaterialfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMaterialfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glMultMatrixf(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glMultMatrixf;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 16);
        }
        JNI.callPV(fArray, l);
    }

    public static void glMultMatrixd(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glMultMatrixd;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 16);
        }
        JNI.callPV(dArray, l);
    }

    public static void glNormal3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glNormal3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glNormal3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glNormal3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glNormal3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glNormal3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glNormal3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glNormal3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glPixelMapfv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glPixelMapfv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length, fArray, l);
    }

    public static void glPixelMapusv(@NativeType(value="GLenum") int n, @NativeType(value="GLushort const *") short[] sArray) {
        long l = GL.getICD().glPixelMapusv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, sArray.length, sArray, l);
    }

    public static void glPixelMapuiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glPixelMapuiv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, nArray.length, nArray, l);
    }

    public static void glPrioritizeTextures(@NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glPrioritizeTextures;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, nArray.length);
        }
        JNI.callPPV(nArray.length, nArray, fArray, l);
    }

    public static void glRasterPos2iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glRasterPos2iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(nArray, l);
    }

    public static void glRasterPos2sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glRasterPos2sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glRasterPos2fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glRasterPos2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(fArray, l);
    }

    public static void glRasterPos2dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glRasterPos2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(dArray, l);
    }

    public static void glRasterPos3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glRasterPos3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glRasterPos3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glRasterPos3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glRasterPos3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glRasterPos3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glRasterPos3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glRasterPos3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glRasterPos4iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glRasterPos4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(nArray, l);
    }

    public static void glRasterPos4sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glRasterPos4sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glRasterPos4fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glRasterPos4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(fArray, l);
    }

    public static void glRasterPos4dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glRasterPos4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(dArray, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") short[] sArray) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, sArray);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") int[] nArray) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, nArray);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") float[] fArray) {
        GL11C.glReadPixels(n, n2, n3, n4, n5, n6, fArray);
    }

    public static void glRectiv(@NativeType(value="GLint const *") int[] nArray, @NativeType(value="GLint const *") int[] nArray2) {
        long l = GL.getICD().glRectiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
            Checks.check(nArray2, 2);
        }
        JNI.callPPV(nArray, nArray2, l);
    }

    public static void glRectsv(@NativeType(value="GLshort const *") short[] sArray, @NativeType(value="GLshort const *") short[] sArray2) {
        long l = GL.getICD().glRectsv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
            Checks.check(sArray2, 2);
        }
        JNI.callPPV(sArray, sArray2, l);
    }

    public static void glRectfv(@NativeType(value="GLfloat const *") float[] fArray, @NativeType(value="GLfloat const *") float[] fArray2) {
        long l = GL.getICD().glRectfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
            Checks.check(fArray2, 2);
        }
        JNI.callPPV(fArray, fArray2, l);
    }

    public static void glRectdv(@NativeType(value="GLdouble const *") double[] dArray, @NativeType(value="GLdouble const *") double[] dArray2) {
        long l = GL.getICD().glRectdv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
            Checks.check(dArray2, 2);
        }
        JNI.callPPV(dArray, dArray2, l);
    }

    public static void glSelectBuffer(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glSelectBuffer;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glTexCoord1fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexCoord1fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(fArray, l);
    }

    public static void glTexCoord1sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glTexCoord1sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord1iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexCoord1iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(nArray, l);
    }

    public static void glTexCoord1dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glTexCoord1dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(dArray, l);
    }

    public static void glTexCoord2fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexCoord2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(fArray, l);
    }

    public static void glTexCoord2sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glTexCoord2sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord2iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexCoord2iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(nArray, l);
    }

    public static void glTexCoord2dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glTexCoord2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(dArray, l);
    }

    public static void glTexCoord3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexCoord3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glTexCoord3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glTexCoord3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexCoord3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glTexCoord3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glTexCoord3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glTexCoord4fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexCoord4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(fArray, l);
    }

    public static void glTexCoord4sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glTexCoord4sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord4iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexCoord4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(nArray, l);
    }

    public static void glTexCoord4dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glTexCoord4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(dArray, l);
    }

    public static void glTexEnviv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexEnviv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glTexEnvfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexEnvfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glTexGeniv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexGeniv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glTexGenfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexGenfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glTexGendv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glTexGendv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(n, n2, dArray, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, sArray);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, nArray);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, fArray);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") double[] dArray) {
        GL11C.glTexImage1D(n, n2, n3, n4, n5, n6, n7, dArray);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") short[] sArray) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, sArray);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") int[] nArray) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, nArray);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") float[] fArray) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, fArray);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") double[] dArray) {
        GL11C.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, dArray);
    }

    public static void glTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        GL11C.glTexParameteriv(n, n2, nArray);
    }

    public static void glTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        GL11C.glTexParameterfv(n, n2, fArray);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") short[] sArray) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, sArray);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") int[] nArray) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, nArray);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") float[] fArray) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, fArray);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") double[] dArray) {
        GL11C.glTexSubImage1D(n, n2, n3, n4, n5, n6, dArray);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") short[] sArray) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, sArray);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") int[] nArray) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, nArray);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") float[] fArray) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, fArray);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") double[] dArray) {
        GL11C.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, dArray);
    }

    public static void glVertex2fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertex2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 2);
        }
        JNI.callPV(fArray, l);
    }

    public static void glVertex2sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertex2sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertex2iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertex2iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 2);
        }
        JNI.callPV(nArray, l);
    }

    public static void glVertex2dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertex2dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 2);
        }
        JNI.callPV(dArray, l);
    }

    public static void glVertex3fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertex3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 3);
        }
        JNI.callPV(fArray, l);
    }

    public static void glVertex3sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertex3sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertex3iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertex3iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 3);
        }
        JNI.callPV(nArray, l);
    }

    public static void glVertex3dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertex3dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 3);
        }
        JNI.callPV(dArray, l);
    }

    public static void glVertex4fv(@NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glVertex4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(fArray, l);
    }

    public static void glVertex4sv(@NativeType(value="GLshort const *") short[] sArray) {
        long l = GL.getICD().glVertex4sv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertex4iv(@NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glVertex4iv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(nArray, l);
    }

    public static void glVertex4dv(@NativeType(value="GLdouble const *") double[] dArray) {
        long l = GL.getICD().glVertex4dv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 4);
        }
        JNI.callPV(dArray, l);
    }

    static {
        GL.initialize();
    }
}

