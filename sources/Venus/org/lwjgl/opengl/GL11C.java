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
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL11C {
    public static final int GL_NEVER = 512;
    public static final int GL_LESS = 513;
    public static final int GL_EQUAL = 514;
    public static final int GL_LEQUAL = 515;
    public static final int GL_GREATER = 516;
    public static final int GL_NOTEQUAL = 517;
    public static final int GL_GEQUAL = 518;
    public static final int GL_ALWAYS = 519;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_STENCIL_BUFFER_BIT = 1024;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_LOOP = 2;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_TRIANGLE_STRIP = 5;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_QUADS = 7;
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
    public static final int GL_BYTE = 5120;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_SHORT = 5122;
    public static final int GL_UNSIGNED_SHORT = 5123;
    public static final int GL_INT = 5124;
    public static final int GL_UNSIGNED_INT = 5125;
    public static final int GL_FLOAT = 5126;
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
    public static final int GL_NO_ERROR = 0;
    public static final int GL_INVALID_ENUM = 1280;
    public static final int GL_INVALID_VALUE = 1281;
    public static final int GL_INVALID_OPERATION = 1282;
    public static final int GL_STACK_OVERFLOW = 1283;
    public static final int GL_STACK_UNDERFLOW = 1284;
    public static final int GL_OUT_OF_MEMORY = 1285;
    public static final int GL_CW = 2304;
    public static final int GL_CCW = 2305;
    public static final int GL_POINT_SIZE = 2833;
    public static final int GL_POINT_SIZE_RANGE = 2834;
    public static final int GL_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_LINE_SMOOTH = 2848;
    public static final int GL_LINE_WIDTH = 2849;
    public static final int GL_LINE_WIDTH_RANGE = 2850;
    public static final int GL_LINE_WIDTH_GRANULARITY = 2851;
    public static final int GL_POLYGON_MODE = 2880;
    public static final int GL_POLYGON_SMOOTH = 2881;
    public static final int GL_CULL_FACE = 2884;
    public static final int GL_CULL_FACE_MODE = 2885;
    public static final int GL_FRONT_FACE = 2886;
    public static final int GL_DEPTH_RANGE = 2928;
    public static final int GL_DEPTH_TEST = 2929;
    public static final int GL_DEPTH_WRITEMASK = 2930;
    public static final int GL_DEPTH_CLEAR_VALUE = 2931;
    public static final int GL_DEPTH_FUNC = 2932;
    public static final int GL_STENCIL_TEST = 2960;
    public static final int GL_STENCIL_CLEAR_VALUE = 2961;
    public static final int GL_STENCIL_FUNC = 2962;
    public static final int GL_STENCIL_VALUE_MASK = 2963;
    public static final int GL_STENCIL_FAIL = 2964;
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 2965;
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 2966;
    public static final int GL_STENCIL_REF = 2967;
    public static final int GL_STENCIL_WRITEMASK = 2968;
    public static final int GL_VIEWPORT = 2978;
    public static final int GL_DITHER = 3024;
    public static final int GL_BLEND_DST = 3040;
    public static final int GL_BLEND_SRC = 3041;
    public static final int GL_BLEND = 3042;
    public static final int GL_LOGIC_OP_MODE = 3056;
    public static final int GL_COLOR_LOGIC_OP = 3058;
    public static final int GL_DRAW_BUFFER = 3073;
    public static final int GL_READ_BUFFER = 3074;
    public static final int GL_SCISSOR_BOX = 3088;
    public static final int GL_SCISSOR_TEST = 3089;
    public static final int GL_COLOR_CLEAR_VALUE = 3106;
    public static final int GL_COLOR_WRITEMASK = 3107;
    public static final int GL_DOUBLEBUFFER = 3122;
    public static final int GL_STEREO = 3123;
    public static final int GL_LINE_SMOOTH_HINT = 3154;
    public static final int GL_POLYGON_SMOOTH_HINT = 3155;
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
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_MAX_VIEWPORT_DIMS = 3386;
    public static final int GL_SUBPIXEL_BITS = 3408;
    public static final int GL_TEXTURE_1D = 3552;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_TEXTURE_WIDTH = 4096;
    public static final int GL_TEXTURE_HEIGHT = 4097;
    public static final int GL_TEXTURE_INTERNAL_FORMAT = 4099;
    public static final int GL_TEXTURE_BORDER_COLOR = 4100;
    public static final int GL_DONT_CARE = 4352;
    public static final int GL_FASTEST = 4353;
    public static final int GL_NICEST = 4354;
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
    public static final int GL_COLOR = 6144;
    public static final int GL_DEPTH = 6145;
    public static final int GL_STENCIL = 6146;
    public static final int GL_STENCIL_INDEX = 6401;
    public static final int GL_DEPTH_COMPONENT = 6402;
    public static final int GL_RED = 6403;
    public static final int GL_GREEN = 6404;
    public static final int GL_BLUE = 6405;
    public static final int GL_ALPHA = 6406;
    public static final int GL_RGB = 6407;
    public static final int GL_RGBA = 6408;
    public static final int GL_POINT = 6912;
    public static final int GL_LINE = 6913;
    public static final int GL_FILL = 6914;
    public static final int GL_KEEP = 7680;
    public static final int GL_REPLACE = 7681;
    public static final int GL_INCR = 7682;
    public static final int GL_DECR = 7683;
    public static final int GL_VENDOR = 7936;
    public static final int GL_RENDERER = 7937;
    public static final int GL_VERSION = 7938;
    public static final int GL_EXTENSIONS = 7939;
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
    public static final int GL_REPEAT = 10497;
    public static final int GL_POLYGON_OFFSET_FACTOR = 32824;
    public static final int GL_POLYGON_OFFSET_UNITS = 10752;
    public static final int GL_POLYGON_OFFSET_POINT = 10753;
    public static final int GL_POLYGON_OFFSET_LINE = 10754;
    public static final int GL_POLYGON_OFFSET_FILL = 32823;
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
    public static final int GL_PROXY_TEXTURE_1D = 32867;
    public static final int GL_PROXY_TEXTURE_2D = 32868;
    public static final int GL_TEXTURE_BINDING_1D = 32872;
    public static final int GL_TEXTURE_BINDING_2D = 32873;
    public static final int GL_VERTEX_ARRAY = 32884;

    protected GL11C() {
        throw new UnsupportedOperationException();
    }

    public static native void glEnable(@NativeType(value="GLenum") int var0);

    public static native void glDisable(@NativeType(value="GLenum") int var0);

    public static native void glBindTexture(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glBlendFunc(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    public static native void glClear(@NativeType(value="GLbitfield") int var0);

    public static native void glClearColor(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1, @NativeType(value="GLfloat") float var2, @NativeType(value="GLfloat") float var3);

    public static native void glClearDepth(@NativeType(value="GLdouble") double var0);

    public static native void glClearStencil(@NativeType(value="GLint") int var0);

    public static native void glColorMask(@NativeType(value="GLboolean") boolean var0, @NativeType(value="GLboolean") boolean var1, @NativeType(value="GLboolean") boolean var2, @NativeType(value="GLboolean") boolean var3);

    public static native void glCullFace(@NativeType(value="GLenum") int var0);

    public static native void glDepthFunc(@NativeType(value="GLenum") int var0);

    public static native void glDepthMask(@NativeType(value="GLboolean") boolean var0);

    public static native void glDepthRange(@NativeType(value="GLdouble") double var0, @NativeType(value="GLdouble") double var2);

    public static native void glDrawArrays(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLsizei") int var2);

    public static native void glDrawBuffer(@NativeType(value="GLenum") int var0);

    public static native void nglDrawElements(int var0, int var1, int var2, long var3);

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l) {
        GL11C.nglDrawElements(n, n2, n3, l);
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.nglDrawElements(n, byteBuffer.remaining() >> GLChecks.typeToByteShift(n2), n2, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.nglDrawElements(n, byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.nglDrawElements(n, shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glDrawElements(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.nglDrawElements(n, intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glFinish();

    public static native void glFlush();

    public static native void glFrontFace(@NativeType(value="GLenum") int var0);

    public static native void nglGenTextures(int var0, long var1);

    public static void glGenTextures(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL11C.nglGenTextures(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenTextures() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11C.nglGenTextures(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeleteTextures(int var0, long var1);

    public static void glDeleteTextures(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL11C.nglDeleteTextures(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteTextures(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            GL11C.nglDeleteTextures(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetBooleanv(int var0, long var1);

    public static void glGetBooleanv(@NativeType(value="GLenum") int n, @NativeType(value="GLboolean *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, 1);
        }
        GL11C.nglGetBooleanv(n, MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static boolean glGetBoolean(@NativeType(value="GLenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            ByteBuffer byteBuffer = memoryStack.calloc(1);
            GL11C.nglGetBooleanv(n, MemoryUtil.memAddress(byteBuffer));
            boolean bl = byteBuffer.get(0) != 0;
            return bl;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetFloatv(int var0, long var1);

    public static void glGetFloatv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11C.nglGetFloatv(n, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetFloat(@NativeType(value="GLenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11C.nglGetFloatv(n, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetIntegerv(int var0, long var1);

    public static void glGetIntegerv(@NativeType(value="GLenum") int n, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11C.nglGetIntegerv(n, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetInteger(@NativeType(value="GLenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11C.nglGetIntegerv(n, MemoryUtil.memAddress(intBuffer));
            int n3 = intBuffer.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetDoublev(int var0, long var1);

    public static void glGetDoublev(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble *") DoubleBuffer doubleBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)doubleBuffer, 1);
        }
        GL11C.nglGetDoublev(n, MemoryUtil.memAddress(doubleBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static double glGetDouble(@NativeType(value="GLenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            DoubleBuffer doubleBuffer = memoryStack.callocDouble(1);
            GL11C.nglGetDoublev(n, MemoryUtil.memAddress(doubleBuffer));
            double d = doubleBuffer.get(0);
            return d;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLenum")
    public static native int glGetError();

    public static native void nglGetPointerv(int var0, long var1);

    public static void glGetPointerv(@NativeType(value="GLenum") int n, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        GL11C.nglGetPointerv(n, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetPointer(@NativeType(value="GLenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            GL11C.nglGetPointerv(n, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native long nglGetString(int var0);

    @Nullable
    @NativeType(value="GLubyte const *")
    public static String glGetString(@NativeType(value="GLenum") int n) {
        long l = GL11C.nglGetString(n);
        return MemoryUtil.memUTF8Safe(l);
    }

    public static native void nglGetTexImage(int var0, int var1, int var2, int var3, long var4);

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL11C.nglGetTexImage(n, n2, n3, n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") long l) {
        GL11C.nglGetTexImage(n, n2, n3, n4, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL11C.nglGetTexImage(n, n2, n3, n4, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") IntBuffer intBuffer) {
        GL11C.nglGetTexImage(n, n2, n3, n4, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL11C.nglGetTexImage(n, n2, n3, n4, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL11C.nglGetTexImage(n, n2, n3, n4, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetTexLevelParameteriv(int var0, int var1, int var2, long var3);

    public static void glGetTexLevelParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11C.nglGetTexLevelParameteriv(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTexLevelParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11C.nglGetTexLevelParameteriv(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetTexLevelParameterfv(int var0, int var1, int var2, long var3);

    public static void glGetTexLevelParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11C.nglGetTexLevelParameterfv(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetTexLevelParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11C.nglGetTexLevelParameterfv(n, n2, n3, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void nglGetTexParameteriv(int var0, int var1, long var2);

    public static void glGetTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL11C.nglGetTexParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetTexParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL11C.nglGetTexParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetTexParameterfv(int var0, int var1, long var2);

    public static void glGetTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL11C.nglGetTexParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetTexParameterf(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL11C.nglGetTexParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glHint(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    @NativeType(value="GLboolean")
    public static native boolean glIsEnabled(@NativeType(value="GLenum") int var0);

    @NativeType(value="GLboolean")
    public static native boolean glIsTexture(@NativeType(value="GLuint") int var0);

    public static native void glLineWidth(@NativeType(value="GLfloat") float var0);

    public static native void glLogicOp(@NativeType(value="GLenum") int var0);

    public static native void glPixelStorei(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1);

    public static native void glPixelStoref(@NativeType(value="GLenum") int var0, @NativeType(value="GLfloat") float var1);

    public static native void glPointSize(@NativeType(value="GLfloat") float var0);

    public static native void glPolygonMode(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1);

    public static native void glPolygonOffset(@NativeType(value="GLfloat") float var0, @NativeType(value="GLfloat") float var1);

    public static native void glReadBuffer(@NativeType(value="GLenum") int var0);

    public static native void nglReadPixels(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL11C.nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") long l) {
        GL11C.nglReadPixels(n, n2, n3, n4, n5, n6, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL11C.nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") IntBuffer intBuffer) {
        GL11C.nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(intBuffer));
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL11C.nglReadPixels(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glScissor(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static native void glStencilFunc(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLuint") int var2);

    public static native void glStencilMask(@NativeType(value="GLuint") int var0);

    public static native void glStencilOp(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2);

    public static native void nglTexImage1D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, long var7);

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") long l) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.nglTexImage1D(n, n2, n3, n4, n5, n6, n7, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglTexImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") long l) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.nglTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void glCopyTexImage1D(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLint") int var6);

    public static native void glCopyTexImage2D(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLint") int var7);

    public static native void glCopyTexSubImage1D(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLsizei") int var5);

    public static native void glCopyTexSubImage2D(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLsizei") int var6, @NativeType(value="GLsizei") int var7);

    public static native void glTexParameteri(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void nglTexParameteriv(int var0, int var1, long var2);

    public static void glTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 4);
        }
        GL11C.nglTexParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    public static native void glTexParameterf(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLfloat") float var2);

    public static native void nglTexParameterfv(int var0, int var1, long var2);

    public static void glTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 4);
        }
        GL11C.nglTexParameterfv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglTexSubImage1D(int var0, int var1, int var2, int var3, int var4, int var5, long var6);

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") long l) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.nglTexSubImage1D(n, n2, n3, n4, n5, n6, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglTexSubImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8);

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") long l) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL11C.nglTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glViewport(@NativeType(value="GLint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static void glGenTextures(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenTextures;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeleteTextures(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteTextures;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGetFloatv(@NativeType(value="GLenum") int n, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetFloatv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, fArray, l);
    }

    public static void glGetIntegerv(@NativeType(value="GLenum") int n, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetIntegerv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, nArray, l);
    }

    public static void glGetDoublev(@NativeType(value="GLenum") int n, @NativeType(value="GLdouble *") double[] dArray) {
        long l = GL.getICD().glGetDoublev;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(dArray, 1);
        }
        JNI.callPV(n, dArray, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glGetTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, sArray, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glGetTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, nArray, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glGetTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, fArray, l);
    }

    public static void glGetTexImage(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void *") double[] dArray) {
        long l = GL.getICD().glGetTexImage;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, dArray, l);
    }

    public static void glGetTexLevelParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTexLevelParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    public static void glGetTexLevelParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetTexLevelParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, n3, fArray, l);
    }

    public static void glGetTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetTexParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetTexParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") short[] sArray) {
        long l = GL.getICD().glReadPixels;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, sArray, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") int[] nArray) {
        long l = GL.getICD().glReadPixels;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, nArray, l);
    }

    public static void glReadPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") float[] fArray) {
        long l = GL.getICD().glReadPixels;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, fArray, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTexImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, sArray, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTexImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, nArray, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTexImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, fArray, l);
    }

    public static void glTexImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLenum") int n7, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTexImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, dArray, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTexImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, sArray, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTexImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, nArray, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTexImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, fArray, l);
    }

    public static void glTexImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTexImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, dArray, l);
    }

    public static void glTexParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint const *") int[] nArray) {
        long l = GL.getICD().glTexParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 4);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glTexParameterfv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glTexParameterfv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 4);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTexSubImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, sArray, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTexSubImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, nArray, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTexSubImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, fArray, l);
    }

    public static void glTexSubImage1D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTexSubImage1D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, dArray, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTexSubImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, sArray, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTexSubImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, nArray, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTexSubImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, fArray, l);
    }

    public static void glTexSubImage2D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLenum") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTexSubImage2D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, dArray, l);
    }

    static {
        GL.initialize();
    }
}

