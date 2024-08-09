/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.LWJGLMemoryUntracker;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.Config;
import net.optifine.GlErrors;
import net.optifine.util.ArrayUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;

public class GlDebugTextUtils {
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final ByteBuffer BYTE_BUFFER = GLAllocation.createDirectByteBuffer(64);
    protected static final FloatBuffer FLOAT_BUFFER = BYTE_BUFFER.asFloatBuffer();
    protected static final IntBuffer INT_BUFFER = BYTE_BUFFER.asIntBuffer();
    private static final Joiner NEWLINE_JOINER = Joiner.on('\n');
    private static final Joiner STATEMENT_JOINER = Joiner.on("; ");
    private static final Map<Integer, String> GL_CONSTANT_NAMES = Maps.newHashMap();
    private static final List<Integer> DEBUG_LEVELS = ImmutableList.of(37190, 37191, 37192, 33387);
    private static final List<Integer> DEBUG_LEVELS_ARB = ImmutableList.of(37190, 37191, 37192);
    private static final Map<String, List<String>> SAVED_STATES = Maps.newHashMap();
    private static int[] ignoredErrors = GlDebugTextUtils.makeIgnoredErrors();

    private static int[] makeIgnoredErrors() {
        String string = System.getProperty("gl.ignore.errors");
        if (string == null) {
            return new int[0];
        }
        String[] stringArray = Config.tokenize(string, ",");
        int[] nArray = new int[]{};
        for (int i = 0; i < stringArray.length; ++i) {
            int n;
            String string2 = stringArray[i].trim();
            int n2 = n = string2.startsWith("0x") ? Config.parseHexInt(string2, -1) : Config.parseInt(string2, -1);
            if (n < 0) {
                Config.warn("Invalid error id: " + string2);
                continue;
            }
            Config.log("Ignore OpenGL error: " + n);
            nArray = ArrayUtils.addIntToArray(nArray, n);
        }
        return nArray;
    }

    private static String getFallbackString(int n) {
        return "Unknown (0x" + Integer.toHexString(n).toUpperCase() + ")";
    }

    private static String getSource(int n) {
        switch (n) {
            case 33350: {
                return "API";
            }
            case 33351: {
                return "WINDOW SYSTEM";
            }
            case 33352: {
                return "SHADER COMPILER";
            }
            case 33353: {
                return "THIRD PARTY";
            }
            case 33354: {
                return "APPLICATION";
            }
            case 33355: {
                return "OTHER";
            }
        }
        return GlDebugTextUtils.getFallbackString(n);
    }

    private static String getType(int n) {
        switch (n) {
            case 33356: {
                return "ERROR";
            }
            case 33357: {
                return "DEPRECATED BEHAVIOR";
            }
            case 33358: {
                return "UNDEFINED BEHAVIOR";
            }
            case 33359: {
                return "PORTABILITY";
            }
            case 33360: {
                return "PERFORMANCE";
            }
            case 33361: {
                return "OTHER";
            }
            case 33384: {
                return "MARKER";
            }
        }
        return GlDebugTextUtils.getFallbackString(n);
    }

    private static String getSeverity(int n) {
        switch (n) {
            case 33387: {
                return "NOTIFICATION";
            }
            case 37190: {
                return "HIGH";
            }
            case 37191: {
                return "MEDIUM";
            }
            case 37192: {
                return "LOW";
            }
        }
        return GlDebugTextUtils.getFallbackString(n);
    }

    private static void logDebugMessage(int n, int n2, int n3, int n4, int n5, long l, long l2) {
        Minecraft minecraft;
        if (!(n2 == 33385 || n2 == 33386 || ArrayUtils.contains(ignoredErrors, n3) || Config.isShaders() && n == 33352 || (minecraft = Minecraft.getInstance()) != null && minecraft.getMainWindow() != null && minecraft.getMainWindow().isClosed() || !GlErrors.isEnabled(n3))) {
            String string = GlDebugTextUtils.getSource(n);
            String string2 = GlDebugTextUtils.getType(n2);
            String string3 = GlDebugTextUtils.getSeverity(n4);
            String string4 = GLDebugMessageCallback.getMessage(n5, l);
            string4 = StrUtils.trim(string4, " \n\r\t");
            String string5 = String.format("OpenGL %s %s: %s (%s)", string, string2, n3, string4);
            Exception exception = new Exception("Stack trace");
            StackTraceElement[] stackTraceElementArray = exception.getStackTrace();
            StackTraceElement[] stackTraceElementArray2 = stackTraceElementArray.length > 2 ? Arrays.copyOfRange(stackTraceElementArray, 2, stackTraceElementArray.length) : stackTraceElementArray;
            exception.setStackTrace(stackTraceElementArray2);
            if (n2 == 33356) {
                LOGGER.error(string5, (Throwable)exception);
            } else {
                LOGGER.info(string5, (Throwable)exception);
            }
            if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorDebug", 10000L)) {
                String string6 = Config.getGlErrorString(n3);
                if (n3 == 0 || Config.equals(string6, "Unknown")) {
                    string6 = string4;
                }
                String string7 = I18n.format("of.message.openglError", n3, string6);
                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(string7));
            }
        }
    }

    private static void registerGlConstantName(int n, String string) {
        GL_CONSTANT_NAMES.merge(n, string, GlDebugTextUtils::lambda$registerGlConstantName$0);
    }

    public static void setDebugVerbosity(int n, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        if (n > 0) {
            GLCapabilities gLCapabilities = GL.getCapabilities();
            if (gLCapabilities.GL_KHR_debug) {
                GL11.glEnable(37600);
                if (bl) {
                    GL11.glEnable(33346);
                }
                for (int i = 0; i < DEBUG_LEVELS.size(); ++i) {
                    boolean bl2 = i < n;
                    KHRDebug.glDebugMessageControl(4352, 4352, (int)DEBUG_LEVELS.get(i), (int[])null, bl2);
                }
                KHRDebug.glDebugMessageCallback(GLX.make(GLDebugMessageCallback.create(GlDebugTextUtils::logDebugMessage), LWJGLMemoryUntracker::untrack), 0L);
            } else if (gLCapabilities.GL_ARB_debug_output) {
                if (bl) {
                    GL11.glEnable(33346);
                }
                for (int i = 0; i < DEBUG_LEVELS_ARB.size(); ++i) {
                    boolean bl3 = i < n;
                    ARBDebugOutput.glDebugMessageControlARB(4352, 4352, (int)DEBUG_LEVELS_ARB.get(i), (int[])null, bl3);
                }
                ARBDebugOutput.glDebugMessageCallbackARB(GLX.make(GLDebugMessageARBCallback.create(GlDebugTextUtils::logDebugMessage), LWJGLMemoryUntracker::untrack), 0L);
            }
        }
    }

    private static String lambda$registerGlConstantName$0(String string, String string2) {
        return string + "/" + string2;
    }

    static {
        GlDebugTextUtils.registerGlConstantName(256, "GL11.GL_ACCUM");
        GlDebugTextUtils.registerGlConstantName(257, "GL11.GL_LOAD");
        GlDebugTextUtils.registerGlConstantName(258, "GL11.GL_RETURN");
        GlDebugTextUtils.registerGlConstantName(259, "GL11.GL_MULT");
        GlDebugTextUtils.registerGlConstantName(260, "GL11.GL_ADD");
        GlDebugTextUtils.registerGlConstantName(512, "GL11.GL_NEVER");
        GlDebugTextUtils.registerGlConstantName(513, "GL11.GL_LESS");
        GlDebugTextUtils.registerGlConstantName(514, "GL11.GL_EQUAL");
        GlDebugTextUtils.registerGlConstantName(515, "GL11.GL_LEQUAL");
        GlDebugTextUtils.registerGlConstantName(516, "GL11.GL_GREATER");
        GlDebugTextUtils.registerGlConstantName(517, "GL11.GL_NOTEQUAL");
        GlDebugTextUtils.registerGlConstantName(518, "GL11.GL_GEQUAL");
        GlDebugTextUtils.registerGlConstantName(519, "GL11.GL_ALWAYS");
        GlDebugTextUtils.registerGlConstantName(0, "GL11.GL_POINTS");
        GlDebugTextUtils.registerGlConstantName(1, "GL11.GL_LINES");
        GlDebugTextUtils.registerGlConstantName(2, "GL11.GL_LINE_LOOP");
        GlDebugTextUtils.registerGlConstantName(3, "GL11.GL_LINE_STRIP");
        GlDebugTextUtils.registerGlConstantName(4, "GL11.GL_TRIANGLES");
        GlDebugTextUtils.registerGlConstantName(5, "GL11.GL_TRIANGLE_STRIP");
        GlDebugTextUtils.registerGlConstantName(6, "GL11.GL_TRIANGLE_FAN");
        GlDebugTextUtils.registerGlConstantName(7, "GL11.GL_QUADS");
        GlDebugTextUtils.registerGlConstantName(8, "GL11.GL_QUAD_STRIP");
        GlDebugTextUtils.registerGlConstantName(9, "GL11.GL_POLYGON");
        GlDebugTextUtils.registerGlConstantName(0, "GL11.GL_ZERO");
        GlDebugTextUtils.registerGlConstantName(1, "GL11.GL_ONE");
        GlDebugTextUtils.registerGlConstantName(768, "GL11.GL_SRC_COLOR");
        GlDebugTextUtils.registerGlConstantName(769, "GL11.GL_ONE_MINUS_SRC_COLOR");
        GlDebugTextUtils.registerGlConstantName(770, "GL11.GL_SRC_ALPHA");
        GlDebugTextUtils.registerGlConstantName(771, "GL11.GL_ONE_MINUS_SRC_ALPHA");
        GlDebugTextUtils.registerGlConstantName(772, "GL11.GL_DST_ALPHA");
        GlDebugTextUtils.registerGlConstantName(773, "GL11.GL_ONE_MINUS_DST_ALPHA");
        GlDebugTextUtils.registerGlConstantName(774, "GL11.GL_DST_COLOR");
        GlDebugTextUtils.registerGlConstantName(775, "GL11.GL_ONE_MINUS_DST_COLOR");
        GlDebugTextUtils.registerGlConstantName(776, "GL11.GL_SRC_ALPHA_SATURATE");
        GlDebugTextUtils.registerGlConstantName(32769, "GL14.GL_CONSTANT_COLOR");
        GlDebugTextUtils.registerGlConstantName(32770, "GL14.GL_ONE_MINUS_CONSTANT_COLOR");
        GlDebugTextUtils.registerGlConstantName(32771, "GL14.GL_CONSTANT_ALPHA");
        GlDebugTextUtils.registerGlConstantName(32772, "GL14.GL_ONE_MINUS_CONSTANT_ALPHA");
        GlDebugTextUtils.registerGlConstantName(1, "GL11.GL_TRUE");
        GlDebugTextUtils.registerGlConstantName(0, "GL11.GL_FALSE");
        GlDebugTextUtils.registerGlConstantName(12288, "GL11.GL_CLIP_PLANE0");
        GlDebugTextUtils.registerGlConstantName(12289, "GL11.GL_CLIP_PLANE1");
        GlDebugTextUtils.registerGlConstantName(12290, "GL11.GL_CLIP_PLANE2");
        GlDebugTextUtils.registerGlConstantName(12291, "GL11.GL_CLIP_PLANE3");
        GlDebugTextUtils.registerGlConstantName(12292, "GL11.GL_CLIP_PLANE4");
        GlDebugTextUtils.registerGlConstantName(12293, "GL11.GL_CLIP_PLANE5");
        GlDebugTextUtils.registerGlConstantName(5120, "GL11.GL_BYTE");
        GlDebugTextUtils.registerGlConstantName(5121, "GL11.GL_UNSIGNED_BYTE");
        GlDebugTextUtils.registerGlConstantName(5122, "GL11.GL_SHORT");
        GlDebugTextUtils.registerGlConstantName(5123, "GL11.GL_UNSIGNED_SHORT");
        GlDebugTextUtils.registerGlConstantName(5124, "GL11.GL_INT");
        GlDebugTextUtils.registerGlConstantName(5125, "GL11.GL_UNSIGNED_INT");
        GlDebugTextUtils.registerGlConstantName(5126, "GL11.GL_FLOAT");
        GlDebugTextUtils.registerGlConstantName(5127, "GL11.GL_2_BYTES");
        GlDebugTextUtils.registerGlConstantName(5128, "GL11.GL_3_BYTES");
        GlDebugTextUtils.registerGlConstantName(5129, "GL11.GL_4_BYTES");
        GlDebugTextUtils.registerGlConstantName(5130, "GL11.GL_DOUBLE");
        GlDebugTextUtils.registerGlConstantName(0, "GL11.GL_NONE");
        GlDebugTextUtils.registerGlConstantName(1024, "GL11.GL_FRONT_LEFT");
        GlDebugTextUtils.registerGlConstantName(1025, "GL11.GL_FRONT_RIGHT");
        GlDebugTextUtils.registerGlConstantName(1026, "GL11.GL_BACK_LEFT");
        GlDebugTextUtils.registerGlConstantName(1027, "GL11.GL_BACK_RIGHT");
        GlDebugTextUtils.registerGlConstantName(1028, "GL11.GL_FRONT");
        GlDebugTextUtils.registerGlConstantName(1029, "GL11.GL_BACK");
        GlDebugTextUtils.registerGlConstantName(1030, "GL11.GL_LEFT");
        GlDebugTextUtils.registerGlConstantName(1031, "GL11.GL_RIGHT");
        GlDebugTextUtils.registerGlConstantName(1032, "GL11.GL_FRONT_AND_BACK");
        GlDebugTextUtils.registerGlConstantName(1033, "GL11.GL_AUX0");
        GlDebugTextUtils.registerGlConstantName(1034, "GL11.GL_AUX1");
        GlDebugTextUtils.registerGlConstantName(1035, "GL11.GL_AUX2");
        GlDebugTextUtils.registerGlConstantName(1036, "GL11.GL_AUX3");
        GlDebugTextUtils.registerGlConstantName(0, "GL11.GL_NO_ERROR");
        GlDebugTextUtils.registerGlConstantName(1280, "GL11.GL_INVALID_ENUM");
        GlDebugTextUtils.registerGlConstantName(1281, "GL11.GL_INVALID_VALUE");
        GlDebugTextUtils.registerGlConstantName(1282, "GL11.GL_INVALID_OPERATION");
        GlDebugTextUtils.registerGlConstantName(1283, "GL11.GL_STACK_OVERFLOW");
        GlDebugTextUtils.registerGlConstantName(1284, "GL11.GL_STACK_UNDERFLOW");
        GlDebugTextUtils.registerGlConstantName(1285, "GL11.GL_OUT_OF_MEMORY");
        GlDebugTextUtils.registerGlConstantName(1536, "GL11.GL_2D");
        GlDebugTextUtils.registerGlConstantName(1537, "GL11.GL_3D");
        GlDebugTextUtils.registerGlConstantName(1538, "GL11.GL_3D_COLOR");
        GlDebugTextUtils.registerGlConstantName(1539, "GL11.GL_3D_COLOR_TEXTURE");
        GlDebugTextUtils.registerGlConstantName(1540, "GL11.GL_4D_COLOR_TEXTURE");
        GlDebugTextUtils.registerGlConstantName(1792, "GL11.GL_PASS_THROUGH_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1793, "GL11.GL_POINT_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1794, "GL11.GL_LINE_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1795, "GL11.GL_POLYGON_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1796, "GL11.GL_BITMAP_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1797, "GL11.GL_DRAW_PIXEL_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1798, "GL11.GL_COPY_PIXEL_TOKEN");
        GlDebugTextUtils.registerGlConstantName(1799, "GL11.GL_LINE_RESET_TOKEN");
        GlDebugTextUtils.registerGlConstantName(2048, "GL11.GL_EXP");
        GlDebugTextUtils.registerGlConstantName(2049, "GL11.GL_EXP2");
        GlDebugTextUtils.registerGlConstantName(2304, "GL11.GL_CW");
        GlDebugTextUtils.registerGlConstantName(2305, "GL11.GL_CCW");
        GlDebugTextUtils.registerGlConstantName(2560, "GL11.GL_COEFF");
        GlDebugTextUtils.registerGlConstantName(2561, "GL11.GL_ORDER");
        GlDebugTextUtils.registerGlConstantName(2562, "GL11.GL_DOMAIN");
        GlDebugTextUtils.registerGlConstantName(2816, "GL11.GL_CURRENT_COLOR");
        GlDebugTextUtils.registerGlConstantName(2817, "GL11.GL_CURRENT_INDEX");
        GlDebugTextUtils.registerGlConstantName(2818, "GL11.GL_CURRENT_NORMAL");
        GlDebugTextUtils.registerGlConstantName(2819, "GL11.GL_CURRENT_TEXTURE_COORDS");
        GlDebugTextUtils.registerGlConstantName(2820, "GL11.GL_CURRENT_RASTER_COLOR");
        GlDebugTextUtils.registerGlConstantName(2821, "GL11.GL_CURRENT_RASTER_INDEX");
        GlDebugTextUtils.registerGlConstantName(2822, "GL11.GL_CURRENT_RASTER_TEXTURE_COORDS");
        GlDebugTextUtils.registerGlConstantName(2823, "GL11.GL_CURRENT_RASTER_POSITION");
        GlDebugTextUtils.registerGlConstantName(2824, "GL11.GL_CURRENT_RASTER_POSITION_VALID");
        GlDebugTextUtils.registerGlConstantName(2825, "GL11.GL_CURRENT_RASTER_DISTANCE");
        GlDebugTextUtils.registerGlConstantName(2832, "GL11.GL_POINT_SMOOTH");
        GlDebugTextUtils.registerGlConstantName(2833, "GL11.GL_POINT_SIZE");
        GlDebugTextUtils.registerGlConstantName(2834, "GL11.GL_POINT_SIZE_RANGE");
        GlDebugTextUtils.registerGlConstantName(2835, "GL11.GL_POINT_SIZE_GRANULARITY");
        GlDebugTextUtils.registerGlConstantName(2848, "GL11.GL_LINE_SMOOTH");
        GlDebugTextUtils.registerGlConstantName(2849, "GL11.GL_LINE_WIDTH");
        GlDebugTextUtils.registerGlConstantName(2850, "GL11.GL_LINE_WIDTH_RANGE");
        GlDebugTextUtils.registerGlConstantName(2851, "GL11.GL_LINE_WIDTH_GRANULARITY");
        GlDebugTextUtils.registerGlConstantName(2852, "GL11.GL_LINE_STIPPLE");
        GlDebugTextUtils.registerGlConstantName(2853, "GL11.GL_LINE_STIPPLE_PATTERN");
        GlDebugTextUtils.registerGlConstantName(2854, "GL11.GL_LINE_STIPPLE_REPEAT");
        GlDebugTextUtils.registerGlConstantName(2864, "GL11.GL_LIST_MODE");
        GlDebugTextUtils.registerGlConstantName(2865, "GL11.GL_MAX_LIST_NESTING");
        GlDebugTextUtils.registerGlConstantName(2866, "GL11.GL_LIST_BASE");
        GlDebugTextUtils.registerGlConstantName(2867, "GL11.GL_LIST_INDEX");
        GlDebugTextUtils.registerGlConstantName(2880, "GL11.GL_POLYGON_MODE");
        GlDebugTextUtils.registerGlConstantName(2881, "GL11.GL_POLYGON_SMOOTH");
        GlDebugTextUtils.registerGlConstantName(2882, "GL11.GL_POLYGON_STIPPLE");
        GlDebugTextUtils.registerGlConstantName(2883, "GL11.GL_EDGE_FLAG");
        GlDebugTextUtils.registerGlConstantName(2884, "GL11.GL_CULL_FACE");
        GlDebugTextUtils.registerGlConstantName(2885, "GL11.GL_CULL_FACE_MODE");
        GlDebugTextUtils.registerGlConstantName(2886, "GL11.GL_FRONT_FACE");
        GlDebugTextUtils.registerGlConstantName(2896, "GL11.GL_LIGHTING");
        GlDebugTextUtils.registerGlConstantName(2897, "GL11.GL_LIGHT_MODEL_LOCAL_VIEWER");
        GlDebugTextUtils.registerGlConstantName(2898, "GL11.GL_LIGHT_MODEL_TWO_SIDE");
        GlDebugTextUtils.registerGlConstantName(2899, "GL11.GL_LIGHT_MODEL_AMBIENT");
        GlDebugTextUtils.registerGlConstantName(2900, "GL11.GL_SHADE_MODEL");
        GlDebugTextUtils.registerGlConstantName(2901, "GL11.GL_COLOR_MATERIAL_FACE");
        GlDebugTextUtils.registerGlConstantName(2902, "GL11.GL_COLOR_MATERIAL_PARAMETER");
        GlDebugTextUtils.registerGlConstantName(2903, "GL11.GL_COLOR_MATERIAL");
        GlDebugTextUtils.registerGlConstantName(2912, "GL11.GL_FOG");
        GlDebugTextUtils.registerGlConstantName(2913, "GL11.GL_FOG_INDEX");
        GlDebugTextUtils.registerGlConstantName(2914, "GL11.GL_FOG_DENSITY");
        GlDebugTextUtils.registerGlConstantName(2915, "GL11.GL_FOG_START");
        GlDebugTextUtils.registerGlConstantName(2916, "GL11.GL_FOG_END");
        GlDebugTextUtils.registerGlConstantName(2917, "GL11.GL_FOG_MODE");
        GlDebugTextUtils.registerGlConstantName(2918, "GL11.GL_FOG_COLOR");
        GlDebugTextUtils.registerGlConstantName(2928, "GL11.GL_DEPTH_RANGE");
        GlDebugTextUtils.registerGlConstantName(2929, "GL11.GL_DEPTH_TEST");
        GlDebugTextUtils.registerGlConstantName(2930, "GL11.GL_DEPTH_WRITEMASK");
        GlDebugTextUtils.registerGlConstantName(2931, "GL11.GL_DEPTH_CLEAR_VALUE");
        GlDebugTextUtils.registerGlConstantName(2932, "GL11.GL_DEPTH_FUNC");
        GlDebugTextUtils.registerGlConstantName(2944, "GL11.GL_ACCUM_CLEAR_VALUE");
        GlDebugTextUtils.registerGlConstantName(2960, "GL11.GL_STENCIL_TEST");
        GlDebugTextUtils.registerGlConstantName(2961, "GL11.GL_STENCIL_CLEAR_VALUE");
        GlDebugTextUtils.registerGlConstantName(2962, "GL11.GL_STENCIL_FUNC");
        GlDebugTextUtils.registerGlConstantName(2963, "GL11.GL_STENCIL_VALUE_MASK");
        GlDebugTextUtils.registerGlConstantName(2964, "GL11.GL_STENCIL_FAIL");
        GlDebugTextUtils.registerGlConstantName(2965, "GL11.GL_STENCIL_PASS_DEPTH_FAIL");
        GlDebugTextUtils.registerGlConstantName(2966, "GL11.GL_STENCIL_PASS_DEPTH_PASS");
        GlDebugTextUtils.registerGlConstantName(2967, "GL11.GL_STENCIL_REF");
        GlDebugTextUtils.registerGlConstantName(2968, "GL11.GL_STENCIL_WRITEMASK");
        GlDebugTextUtils.registerGlConstantName(2976, "GL11.GL_MATRIX_MODE");
        GlDebugTextUtils.registerGlConstantName(2977, "GL11.GL_NORMALIZE");
        GlDebugTextUtils.registerGlConstantName(2978, "GL11.GL_VIEWPORT");
        GlDebugTextUtils.registerGlConstantName(2979, "GL11.GL_MODELVIEW_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(2980, "GL11.GL_PROJECTION_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(2981, "GL11.GL_TEXTURE_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(2982, "GL11.GL_MODELVIEW_MATRIX");
        GlDebugTextUtils.registerGlConstantName(2983, "GL11.GL_PROJECTION_MATRIX");
        GlDebugTextUtils.registerGlConstantName(2984, "GL11.GL_TEXTURE_MATRIX");
        GlDebugTextUtils.registerGlConstantName(2992, "GL11.GL_ATTRIB_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(2993, "GL11.GL_CLIENT_ATTRIB_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3008, "GL11.GL_ALPHA_TEST");
        GlDebugTextUtils.registerGlConstantName(3009, "GL11.GL_ALPHA_TEST_FUNC");
        GlDebugTextUtils.registerGlConstantName(3010, "GL11.GL_ALPHA_TEST_REF");
        GlDebugTextUtils.registerGlConstantName(3024, "GL11.GL_DITHER");
        GlDebugTextUtils.registerGlConstantName(3040, "GL11.GL_BLEND_DST");
        GlDebugTextUtils.registerGlConstantName(3041, "GL11.GL_BLEND_SRC");
        GlDebugTextUtils.registerGlConstantName(3042, "GL11.GL_BLEND");
        GlDebugTextUtils.registerGlConstantName(3056, "GL11.GL_LOGIC_OP_MODE");
        GlDebugTextUtils.registerGlConstantName(3057, "GL11.GL_INDEX_LOGIC_OP");
        GlDebugTextUtils.registerGlConstantName(3058, "GL11.GL_COLOR_LOGIC_OP");
        GlDebugTextUtils.registerGlConstantName(3072, "GL11.GL_AUX_BUFFERS");
        GlDebugTextUtils.registerGlConstantName(3073, "GL11.GL_DRAW_BUFFER");
        GlDebugTextUtils.registerGlConstantName(3074, "GL11.GL_READ_BUFFER");
        GlDebugTextUtils.registerGlConstantName(3088, "GL11.GL_SCISSOR_BOX");
        GlDebugTextUtils.registerGlConstantName(3089, "GL11.GL_SCISSOR_TEST");
        GlDebugTextUtils.registerGlConstantName(3104, "GL11.GL_INDEX_CLEAR_VALUE");
        GlDebugTextUtils.registerGlConstantName(3105, "GL11.GL_INDEX_WRITEMASK");
        GlDebugTextUtils.registerGlConstantName(3106, "GL11.GL_COLOR_CLEAR_VALUE");
        GlDebugTextUtils.registerGlConstantName(3107, "GL11.GL_COLOR_WRITEMASK");
        GlDebugTextUtils.registerGlConstantName(3120, "GL11.GL_INDEX_MODE");
        GlDebugTextUtils.registerGlConstantName(3121, "GL11.GL_RGBA_MODE");
        GlDebugTextUtils.registerGlConstantName(3122, "GL11.GL_DOUBLEBUFFER");
        GlDebugTextUtils.registerGlConstantName(3123, "GL11.GL_STEREO");
        GlDebugTextUtils.registerGlConstantName(3136, "GL11.GL_RENDER_MODE");
        GlDebugTextUtils.registerGlConstantName(3152, "GL11.GL_PERSPECTIVE_CORRECTION_HINT");
        GlDebugTextUtils.registerGlConstantName(3153, "GL11.GL_POINT_SMOOTH_HINT");
        GlDebugTextUtils.registerGlConstantName(3154, "GL11.GL_LINE_SMOOTH_HINT");
        GlDebugTextUtils.registerGlConstantName(3155, "GL11.GL_POLYGON_SMOOTH_HINT");
        GlDebugTextUtils.registerGlConstantName(3156, "GL11.GL_FOG_HINT");
        GlDebugTextUtils.registerGlConstantName(3168, "GL11.GL_TEXTURE_GEN_S");
        GlDebugTextUtils.registerGlConstantName(3169, "GL11.GL_TEXTURE_GEN_T");
        GlDebugTextUtils.registerGlConstantName(3170, "GL11.GL_TEXTURE_GEN_R");
        GlDebugTextUtils.registerGlConstantName(3171, "GL11.GL_TEXTURE_GEN_Q");
        GlDebugTextUtils.registerGlConstantName(3184, "GL11.GL_PIXEL_MAP_I_TO_I");
        GlDebugTextUtils.registerGlConstantName(3185, "GL11.GL_PIXEL_MAP_S_TO_S");
        GlDebugTextUtils.registerGlConstantName(3186, "GL11.GL_PIXEL_MAP_I_TO_R");
        GlDebugTextUtils.registerGlConstantName(3187, "GL11.GL_PIXEL_MAP_I_TO_G");
        GlDebugTextUtils.registerGlConstantName(3188, "GL11.GL_PIXEL_MAP_I_TO_B");
        GlDebugTextUtils.registerGlConstantName(3189, "GL11.GL_PIXEL_MAP_I_TO_A");
        GlDebugTextUtils.registerGlConstantName(3190, "GL11.GL_PIXEL_MAP_R_TO_R");
        GlDebugTextUtils.registerGlConstantName(3191, "GL11.GL_PIXEL_MAP_G_TO_G");
        GlDebugTextUtils.registerGlConstantName(3192, "GL11.GL_PIXEL_MAP_B_TO_B");
        GlDebugTextUtils.registerGlConstantName(3193, "GL11.GL_PIXEL_MAP_A_TO_A");
        GlDebugTextUtils.registerGlConstantName(3248, "GL11.GL_PIXEL_MAP_I_TO_I_SIZE");
        GlDebugTextUtils.registerGlConstantName(3249, "GL11.GL_PIXEL_MAP_S_TO_S_SIZE");
        GlDebugTextUtils.registerGlConstantName(3250, "GL11.GL_PIXEL_MAP_I_TO_R_SIZE");
        GlDebugTextUtils.registerGlConstantName(3251, "GL11.GL_PIXEL_MAP_I_TO_G_SIZE");
        GlDebugTextUtils.registerGlConstantName(3252, "GL11.GL_PIXEL_MAP_I_TO_B_SIZE");
        GlDebugTextUtils.registerGlConstantName(3253, "GL11.GL_PIXEL_MAP_I_TO_A_SIZE");
        GlDebugTextUtils.registerGlConstantName(3254, "GL11.GL_PIXEL_MAP_R_TO_R_SIZE");
        GlDebugTextUtils.registerGlConstantName(3255, "GL11.GL_PIXEL_MAP_G_TO_G_SIZE");
        GlDebugTextUtils.registerGlConstantName(3256, "GL11.GL_PIXEL_MAP_B_TO_B_SIZE");
        GlDebugTextUtils.registerGlConstantName(3257, "GL11.GL_PIXEL_MAP_A_TO_A_SIZE");
        GlDebugTextUtils.registerGlConstantName(3312, "GL11.GL_UNPACK_SWAP_BYTES");
        GlDebugTextUtils.registerGlConstantName(3313, "GL11.GL_UNPACK_LSB_FIRST");
        GlDebugTextUtils.registerGlConstantName(3314, "GL11.GL_UNPACK_ROW_LENGTH");
        GlDebugTextUtils.registerGlConstantName(3315, "GL11.GL_UNPACK_SKIP_ROWS");
        GlDebugTextUtils.registerGlConstantName(3316, "GL11.GL_UNPACK_SKIP_PIXELS");
        GlDebugTextUtils.registerGlConstantName(3317, "GL11.GL_UNPACK_ALIGNMENT");
        GlDebugTextUtils.registerGlConstantName(3328, "GL11.GL_PACK_SWAP_BYTES");
        GlDebugTextUtils.registerGlConstantName(3329, "GL11.GL_PACK_LSB_FIRST");
        GlDebugTextUtils.registerGlConstantName(3330, "GL11.GL_PACK_ROW_LENGTH");
        GlDebugTextUtils.registerGlConstantName(3331, "GL11.GL_PACK_SKIP_ROWS");
        GlDebugTextUtils.registerGlConstantName(3332, "GL11.GL_PACK_SKIP_PIXELS");
        GlDebugTextUtils.registerGlConstantName(3333, "GL11.GL_PACK_ALIGNMENT");
        GlDebugTextUtils.registerGlConstantName(3344, "GL11.GL_MAP_COLOR");
        GlDebugTextUtils.registerGlConstantName(3345, "GL11.GL_MAP_STENCIL");
        GlDebugTextUtils.registerGlConstantName(3346, "GL11.GL_INDEX_SHIFT");
        GlDebugTextUtils.registerGlConstantName(3347, "GL11.GL_INDEX_OFFSET");
        GlDebugTextUtils.registerGlConstantName(3348, "GL11.GL_RED_SCALE");
        GlDebugTextUtils.registerGlConstantName(3349, "GL11.GL_RED_BIAS");
        GlDebugTextUtils.registerGlConstantName(3350, "GL11.GL_ZOOM_X");
        GlDebugTextUtils.registerGlConstantName(3351, "GL11.GL_ZOOM_Y");
        GlDebugTextUtils.registerGlConstantName(3352, "GL11.GL_GREEN_SCALE");
        GlDebugTextUtils.registerGlConstantName(3353, "GL11.GL_GREEN_BIAS");
        GlDebugTextUtils.registerGlConstantName(3354, "GL11.GL_BLUE_SCALE");
        GlDebugTextUtils.registerGlConstantName(3355, "GL11.GL_BLUE_BIAS");
        GlDebugTextUtils.registerGlConstantName(3356, "GL11.GL_ALPHA_SCALE");
        GlDebugTextUtils.registerGlConstantName(3357, "GL11.GL_ALPHA_BIAS");
        GlDebugTextUtils.registerGlConstantName(3358, "GL11.GL_DEPTH_SCALE");
        GlDebugTextUtils.registerGlConstantName(3359, "GL11.GL_DEPTH_BIAS");
        GlDebugTextUtils.registerGlConstantName(3376, "GL11.GL_MAX_EVAL_ORDER");
        GlDebugTextUtils.registerGlConstantName(3377, "GL11.GL_MAX_LIGHTS");
        GlDebugTextUtils.registerGlConstantName(3378, "GL11.GL_MAX_CLIP_PLANES");
        GlDebugTextUtils.registerGlConstantName(3379, "GL11.GL_MAX_TEXTURE_SIZE");
        GlDebugTextUtils.registerGlConstantName(3380, "GL11.GL_MAX_PIXEL_MAP_TABLE");
        GlDebugTextUtils.registerGlConstantName(3381, "GL11.GL_MAX_ATTRIB_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3382, "GL11.GL_MAX_MODELVIEW_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3383, "GL11.GL_MAX_NAME_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3384, "GL11.GL_MAX_PROJECTION_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3385, "GL11.GL_MAX_TEXTURE_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3386, "GL11.GL_MAX_VIEWPORT_DIMS");
        GlDebugTextUtils.registerGlConstantName(3387, "GL11.GL_MAX_CLIENT_ATTRIB_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3408, "GL11.GL_SUBPIXEL_BITS");
        GlDebugTextUtils.registerGlConstantName(3409, "GL11.GL_INDEX_BITS");
        GlDebugTextUtils.registerGlConstantName(3410, "GL11.GL_RED_BITS");
        GlDebugTextUtils.registerGlConstantName(3411, "GL11.GL_GREEN_BITS");
        GlDebugTextUtils.registerGlConstantName(3412, "GL11.GL_BLUE_BITS");
        GlDebugTextUtils.registerGlConstantName(3413, "GL11.GL_ALPHA_BITS");
        GlDebugTextUtils.registerGlConstantName(3414, "GL11.GL_DEPTH_BITS");
        GlDebugTextUtils.registerGlConstantName(3415, "GL11.GL_STENCIL_BITS");
        GlDebugTextUtils.registerGlConstantName(3416, "GL11.GL_ACCUM_RED_BITS");
        GlDebugTextUtils.registerGlConstantName(3417, "GL11.GL_ACCUM_GREEN_BITS");
        GlDebugTextUtils.registerGlConstantName(3418, "GL11.GL_ACCUM_BLUE_BITS");
        GlDebugTextUtils.registerGlConstantName(3419, "GL11.GL_ACCUM_ALPHA_BITS");
        GlDebugTextUtils.registerGlConstantName(3440, "GL11.GL_NAME_STACK_DEPTH");
        GlDebugTextUtils.registerGlConstantName(3456, "GL11.GL_AUTO_NORMAL");
        GlDebugTextUtils.registerGlConstantName(3472, "GL11.GL_MAP1_COLOR_4");
        GlDebugTextUtils.registerGlConstantName(3473, "GL11.GL_MAP1_INDEX");
        GlDebugTextUtils.registerGlConstantName(3474, "GL11.GL_MAP1_NORMAL");
        GlDebugTextUtils.registerGlConstantName(3475, "GL11.GL_MAP1_TEXTURE_COORD_1");
        GlDebugTextUtils.registerGlConstantName(3476, "GL11.GL_MAP1_TEXTURE_COORD_2");
        GlDebugTextUtils.registerGlConstantName(3477, "GL11.GL_MAP1_TEXTURE_COORD_3");
        GlDebugTextUtils.registerGlConstantName(3478, "GL11.GL_MAP1_TEXTURE_COORD_4");
        GlDebugTextUtils.registerGlConstantName(3479, "GL11.GL_MAP1_VERTEX_3");
        GlDebugTextUtils.registerGlConstantName(3480, "GL11.GL_MAP1_VERTEX_4");
        GlDebugTextUtils.registerGlConstantName(3504, "GL11.GL_MAP2_COLOR_4");
        GlDebugTextUtils.registerGlConstantName(3505, "GL11.GL_MAP2_INDEX");
        GlDebugTextUtils.registerGlConstantName(3506, "GL11.GL_MAP2_NORMAL");
        GlDebugTextUtils.registerGlConstantName(3507, "GL11.GL_MAP2_TEXTURE_COORD_1");
        GlDebugTextUtils.registerGlConstantName(3508, "GL11.GL_MAP2_TEXTURE_COORD_2");
        GlDebugTextUtils.registerGlConstantName(3509, "GL11.GL_MAP2_TEXTURE_COORD_3");
        GlDebugTextUtils.registerGlConstantName(3510, "GL11.GL_MAP2_TEXTURE_COORD_4");
        GlDebugTextUtils.registerGlConstantName(3511, "GL11.GL_MAP2_VERTEX_3");
        GlDebugTextUtils.registerGlConstantName(3512, "GL11.GL_MAP2_VERTEX_4");
        GlDebugTextUtils.registerGlConstantName(3536, "GL11.GL_MAP1_GRID_DOMAIN");
        GlDebugTextUtils.registerGlConstantName(3537, "GL11.GL_MAP1_GRID_SEGMENTS");
        GlDebugTextUtils.registerGlConstantName(3538, "GL11.GL_MAP2_GRID_DOMAIN");
        GlDebugTextUtils.registerGlConstantName(3539, "GL11.GL_MAP2_GRID_SEGMENTS");
        GlDebugTextUtils.registerGlConstantName(3552, "GL11.GL_TEXTURE_1D");
        GlDebugTextUtils.registerGlConstantName(3553, "GL11.GL_TEXTURE_2D");
        GlDebugTextUtils.registerGlConstantName(3568, "GL11.GL_FEEDBACK_BUFFER_POINTER");
        GlDebugTextUtils.registerGlConstantName(3569, "GL11.GL_FEEDBACK_BUFFER_SIZE");
        GlDebugTextUtils.registerGlConstantName(3570, "GL11.GL_FEEDBACK_BUFFER_TYPE");
        GlDebugTextUtils.registerGlConstantName(3571, "GL11.GL_SELECTION_BUFFER_POINTER");
        GlDebugTextUtils.registerGlConstantName(3572, "GL11.GL_SELECTION_BUFFER_SIZE");
        GlDebugTextUtils.registerGlConstantName(4096, "GL11.GL_TEXTURE_WIDTH");
        GlDebugTextUtils.registerGlConstantName(4097, "GL11.GL_TEXTURE_HEIGHT");
        GlDebugTextUtils.registerGlConstantName(4099, "GL11.GL_TEXTURE_INTERNAL_FORMAT");
        GlDebugTextUtils.registerGlConstantName(4100, "GL11.GL_TEXTURE_BORDER_COLOR");
        GlDebugTextUtils.registerGlConstantName(4101, "GL11.GL_TEXTURE_BORDER");
        GlDebugTextUtils.registerGlConstantName(4352, "GL11.GL_DONT_CARE");
        GlDebugTextUtils.registerGlConstantName(4353, "GL11.GL_FASTEST");
        GlDebugTextUtils.registerGlConstantName(4354, "GL11.GL_NICEST");
        GlDebugTextUtils.registerGlConstantName(16384, "GL11.GL_LIGHT0");
        GlDebugTextUtils.registerGlConstantName(16385, "GL11.GL_LIGHT1");
        GlDebugTextUtils.registerGlConstantName(16386, "GL11.GL_LIGHT2");
        GlDebugTextUtils.registerGlConstantName(16387, "GL11.GL_LIGHT3");
        GlDebugTextUtils.registerGlConstantName(16388, "GL11.GL_LIGHT4");
        GlDebugTextUtils.registerGlConstantName(16389, "GL11.GL_LIGHT5");
        GlDebugTextUtils.registerGlConstantName(16390, "GL11.GL_LIGHT6");
        GlDebugTextUtils.registerGlConstantName(16391, "GL11.GL_LIGHT7");
        GlDebugTextUtils.registerGlConstantName(4608, "GL11.GL_AMBIENT");
        GlDebugTextUtils.registerGlConstantName(4609, "GL11.GL_DIFFUSE");
        GlDebugTextUtils.registerGlConstantName(4610, "GL11.GL_SPECULAR");
        GlDebugTextUtils.registerGlConstantName(4611, "GL11.GL_POSITION");
        GlDebugTextUtils.registerGlConstantName(4612, "GL11.GL_SPOT_DIRECTION");
        GlDebugTextUtils.registerGlConstantName(4613, "GL11.GL_SPOT_EXPONENT");
        GlDebugTextUtils.registerGlConstantName(4614, "GL11.GL_SPOT_CUTOFF");
        GlDebugTextUtils.registerGlConstantName(4615, "GL11.GL_CONSTANT_ATTENUATION");
        GlDebugTextUtils.registerGlConstantName(4616, "GL11.GL_LINEAR_ATTENUATION");
        GlDebugTextUtils.registerGlConstantName(4617, "GL11.GL_QUADRATIC_ATTENUATION");
        GlDebugTextUtils.registerGlConstantName(4864, "GL11.GL_COMPILE");
        GlDebugTextUtils.registerGlConstantName(4865, "GL11.GL_COMPILE_AND_EXECUTE");
        GlDebugTextUtils.registerGlConstantName(5376, "GL11.GL_CLEAR");
        GlDebugTextUtils.registerGlConstantName(5377, "GL11.GL_AND");
        GlDebugTextUtils.registerGlConstantName(5378, "GL11.GL_AND_REVERSE");
        GlDebugTextUtils.registerGlConstantName(5379, "GL11.GL_COPY");
        GlDebugTextUtils.registerGlConstantName(5380, "GL11.GL_AND_INVERTED");
        GlDebugTextUtils.registerGlConstantName(5381, "GL11.GL_NOOP");
        GlDebugTextUtils.registerGlConstantName(5382, "GL11.GL_XOR");
        GlDebugTextUtils.registerGlConstantName(5383, "GL11.GL_OR");
        GlDebugTextUtils.registerGlConstantName(5384, "GL11.GL_NOR");
        GlDebugTextUtils.registerGlConstantName(5385, "GL11.GL_EQUIV");
        GlDebugTextUtils.registerGlConstantName(5386, "GL11.GL_INVERT");
        GlDebugTextUtils.registerGlConstantName(5387, "GL11.GL_OR_REVERSE");
        GlDebugTextUtils.registerGlConstantName(5388, "GL11.GL_COPY_INVERTED");
        GlDebugTextUtils.registerGlConstantName(5389, "GL11.GL_OR_INVERTED");
        GlDebugTextUtils.registerGlConstantName(5390, "GL11.GL_NAND");
        GlDebugTextUtils.registerGlConstantName(5391, "GL11.GL_SET");
        GlDebugTextUtils.registerGlConstantName(5632, "GL11.GL_EMISSION");
        GlDebugTextUtils.registerGlConstantName(5633, "GL11.GL_SHININESS");
        GlDebugTextUtils.registerGlConstantName(5634, "GL11.GL_AMBIENT_AND_DIFFUSE");
        GlDebugTextUtils.registerGlConstantName(5635, "GL11.GL_COLOR_INDEXES");
        GlDebugTextUtils.registerGlConstantName(5888, "GL11.GL_MODELVIEW");
        GlDebugTextUtils.registerGlConstantName(5889, "GL11.GL_PROJECTION");
        GlDebugTextUtils.registerGlConstantName(5890, "GL11.GL_TEXTURE");
        GlDebugTextUtils.registerGlConstantName(6144, "GL11.GL_COLOR");
        GlDebugTextUtils.registerGlConstantName(6145, "GL11.GL_DEPTH");
        GlDebugTextUtils.registerGlConstantName(6146, "GL11.GL_STENCIL");
        GlDebugTextUtils.registerGlConstantName(6400, "GL11.GL_COLOR_INDEX");
        GlDebugTextUtils.registerGlConstantName(6401, "GL11.GL_STENCIL_INDEX");
        GlDebugTextUtils.registerGlConstantName(6402, "GL11.GL_DEPTH_COMPONENT");
        GlDebugTextUtils.registerGlConstantName(6403, "GL11.GL_RED");
        GlDebugTextUtils.registerGlConstantName(6404, "GL11.GL_GREEN");
        GlDebugTextUtils.registerGlConstantName(6405, "GL11.GL_BLUE");
        GlDebugTextUtils.registerGlConstantName(6406, "GL11.GL_ALPHA");
        GlDebugTextUtils.registerGlConstantName(6407, "GL11.GL_RGB");
        GlDebugTextUtils.registerGlConstantName(6408, "GL11.GL_RGBA");
        GlDebugTextUtils.registerGlConstantName(6409, "GL11.GL_LUMINANCE");
        GlDebugTextUtils.registerGlConstantName(6410, "GL11.GL_LUMINANCE_ALPHA");
        GlDebugTextUtils.registerGlConstantName(6656, "GL11.GL_BITMAP");
        GlDebugTextUtils.registerGlConstantName(6912, "GL11.GL_POINT");
        GlDebugTextUtils.registerGlConstantName(6913, "GL11.GL_LINE");
        GlDebugTextUtils.registerGlConstantName(6914, "GL11.GL_FILL");
        GlDebugTextUtils.registerGlConstantName(7168, "GL11.GL_RENDER");
        GlDebugTextUtils.registerGlConstantName(7169, "GL11.GL_FEEDBACK");
        GlDebugTextUtils.registerGlConstantName(7170, "GL11.GL_SELECT");
        GlDebugTextUtils.registerGlConstantName(7424, "GL11.GL_FLAT");
        GlDebugTextUtils.registerGlConstantName(7425, "GL11.GL_SMOOTH");
        GlDebugTextUtils.registerGlConstantName(7680, "GL11.GL_KEEP");
        GlDebugTextUtils.registerGlConstantName(7681, "GL11.GL_REPLACE");
        GlDebugTextUtils.registerGlConstantName(7682, "GL11.GL_INCR");
        GlDebugTextUtils.registerGlConstantName(7683, "GL11.GL_DECR");
        GlDebugTextUtils.registerGlConstantName(7936, "GL11.GL_VENDOR");
        GlDebugTextUtils.registerGlConstantName(7937, "GL11.GL_RENDERER");
        GlDebugTextUtils.registerGlConstantName(7938, "GL11.GL_VERSION");
        GlDebugTextUtils.registerGlConstantName(7939, "GL11.GL_EXTENSIONS");
        GlDebugTextUtils.registerGlConstantName(8192, "GL11.GL_S");
        GlDebugTextUtils.registerGlConstantName(8193, "GL11.GL_T");
        GlDebugTextUtils.registerGlConstantName(8194, "GL11.GL_R");
        GlDebugTextUtils.registerGlConstantName(8195, "GL11.GL_Q");
        GlDebugTextUtils.registerGlConstantName(8448, "GL11.GL_MODULATE");
        GlDebugTextUtils.registerGlConstantName(8449, "GL11.GL_DECAL");
        GlDebugTextUtils.registerGlConstantName(8704, "GL11.GL_TEXTURE_ENV_MODE");
        GlDebugTextUtils.registerGlConstantName(8705, "GL11.GL_TEXTURE_ENV_COLOR");
        GlDebugTextUtils.registerGlConstantName(8960, "GL11.GL_TEXTURE_ENV");
        GlDebugTextUtils.registerGlConstantName(9216, "GL11.GL_EYE_LINEAR");
        GlDebugTextUtils.registerGlConstantName(9217, "GL11.GL_OBJECT_LINEAR");
        GlDebugTextUtils.registerGlConstantName(9218, "GL11.GL_SPHERE_MAP");
        GlDebugTextUtils.registerGlConstantName(9472, "GL11.GL_TEXTURE_GEN_MODE");
        GlDebugTextUtils.registerGlConstantName(9473, "GL11.GL_OBJECT_PLANE");
        GlDebugTextUtils.registerGlConstantName(9474, "GL11.GL_EYE_PLANE");
        GlDebugTextUtils.registerGlConstantName(9728, "GL11.GL_NEAREST");
        GlDebugTextUtils.registerGlConstantName(9729, "GL11.GL_LINEAR");
        GlDebugTextUtils.registerGlConstantName(9984, "GL11.GL_NEAREST_MIPMAP_NEAREST");
        GlDebugTextUtils.registerGlConstantName(9985, "GL11.GL_LINEAR_MIPMAP_NEAREST");
        GlDebugTextUtils.registerGlConstantName(9986, "GL11.GL_NEAREST_MIPMAP_LINEAR");
        GlDebugTextUtils.registerGlConstantName(9987, "GL11.GL_LINEAR_MIPMAP_LINEAR");
        GlDebugTextUtils.registerGlConstantName(10240, "GL11.GL_TEXTURE_MAG_FILTER");
        GlDebugTextUtils.registerGlConstantName(10241, "GL11.GL_TEXTURE_MIN_FILTER");
        GlDebugTextUtils.registerGlConstantName(10242, "GL11.GL_TEXTURE_WRAP_S");
        GlDebugTextUtils.registerGlConstantName(10243, "GL11.GL_TEXTURE_WRAP_T");
        GlDebugTextUtils.registerGlConstantName(10496, "GL11.GL_CLAMP");
        GlDebugTextUtils.registerGlConstantName(10497, "GL11.GL_REPEAT");
        GlDebugTextUtils.registerGlConstantName(-1, "GL11.GL_ALL_CLIENT_ATTRIB_BITS");
        GlDebugTextUtils.registerGlConstantName(32824, "GL11.GL_POLYGON_OFFSET_FACTOR");
        GlDebugTextUtils.registerGlConstantName(10752, "GL11.GL_POLYGON_OFFSET_UNITS");
        GlDebugTextUtils.registerGlConstantName(10753, "GL11.GL_POLYGON_OFFSET_POINT");
        GlDebugTextUtils.registerGlConstantName(10754, "GL11.GL_POLYGON_OFFSET_LINE");
        GlDebugTextUtils.registerGlConstantName(32823, "GL11.GL_POLYGON_OFFSET_FILL");
        GlDebugTextUtils.registerGlConstantName(32827, "GL11.GL_ALPHA4");
        GlDebugTextUtils.registerGlConstantName(32828, "GL11.GL_ALPHA8");
        GlDebugTextUtils.registerGlConstantName(32829, "GL11.GL_ALPHA12");
        GlDebugTextUtils.registerGlConstantName(32830, "GL11.GL_ALPHA16");
        GlDebugTextUtils.registerGlConstantName(32831, "GL11.GL_LUMINANCE4");
        GlDebugTextUtils.registerGlConstantName(32832, "GL11.GL_LUMINANCE8");
        GlDebugTextUtils.registerGlConstantName(32833, "GL11.GL_LUMINANCE12");
        GlDebugTextUtils.registerGlConstantName(32834, "GL11.GL_LUMINANCE16");
        GlDebugTextUtils.registerGlConstantName(32835, "GL11.GL_LUMINANCE4_ALPHA4");
        GlDebugTextUtils.registerGlConstantName(32836, "GL11.GL_LUMINANCE6_ALPHA2");
        GlDebugTextUtils.registerGlConstantName(32837, "GL11.GL_LUMINANCE8_ALPHA8");
        GlDebugTextUtils.registerGlConstantName(32838, "GL11.GL_LUMINANCE12_ALPHA4");
        GlDebugTextUtils.registerGlConstantName(32839, "GL11.GL_LUMINANCE12_ALPHA12");
        GlDebugTextUtils.registerGlConstantName(32840, "GL11.GL_LUMINANCE16_ALPHA16");
        GlDebugTextUtils.registerGlConstantName(32841, "GL11.GL_INTENSITY");
        GlDebugTextUtils.registerGlConstantName(32842, "GL11.GL_INTENSITY4");
        GlDebugTextUtils.registerGlConstantName(32843, "GL11.GL_INTENSITY8");
        GlDebugTextUtils.registerGlConstantName(32844, "GL11.GL_INTENSITY12");
        GlDebugTextUtils.registerGlConstantName(32845, "GL11.GL_INTENSITY16");
        GlDebugTextUtils.registerGlConstantName(10768, "GL11.GL_R3_G3_B2");
        GlDebugTextUtils.registerGlConstantName(32847, "GL11.GL_RGB4");
        GlDebugTextUtils.registerGlConstantName(32848, "GL11.GL_RGB5");
        GlDebugTextUtils.registerGlConstantName(32849, "GL11.GL_RGB8");
        GlDebugTextUtils.registerGlConstantName(32850, "GL11.GL_RGB10");
        GlDebugTextUtils.registerGlConstantName(32851, "GL11.GL_RGB12");
        GlDebugTextUtils.registerGlConstantName(32852, "GL11.GL_RGB16");
        GlDebugTextUtils.registerGlConstantName(32853, "GL11.GL_RGBA2");
        GlDebugTextUtils.registerGlConstantName(32854, "GL11.GL_RGBA4");
        GlDebugTextUtils.registerGlConstantName(32855, "GL11.GL_RGB5_A1");
        GlDebugTextUtils.registerGlConstantName(32856, "GL11.GL_RGBA8");
        GlDebugTextUtils.registerGlConstantName(32857, "GL11.GL_RGB10_A2");
        GlDebugTextUtils.registerGlConstantName(32858, "GL11.GL_RGBA12");
        GlDebugTextUtils.registerGlConstantName(32859, "GL11.GL_RGBA16");
        GlDebugTextUtils.registerGlConstantName(32860, "GL11.GL_TEXTURE_RED_SIZE");
        GlDebugTextUtils.registerGlConstantName(32861, "GL11.GL_TEXTURE_GREEN_SIZE");
        GlDebugTextUtils.registerGlConstantName(32862, "GL11.GL_TEXTURE_BLUE_SIZE");
        GlDebugTextUtils.registerGlConstantName(32863, "GL11.GL_TEXTURE_ALPHA_SIZE");
        GlDebugTextUtils.registerGlConstantName(32864, "GL11.GL_TEXTURE_LUMINANCE_SIZE");
        GlDebugTextUtils.registerGlConstantName(32865, "GL11.GL_TEXTURE_INTENSITY_SIZE");
        GlDebugTextUtils.registerGlConstantName(32867, "GL11.GL_PROXY_TEXTURE_1D");
        GlDebugTextUtils.registerGlConstantName(32868, "GL11.GL_PROXY_TEXTURE_2D");
        GlDebugTextUtils.registerGlConstantName(32870, "GL11.GL_TEXTURE_PRIORITY");
        GlDebugTextUtils.registerGlConstantName(32871, "GL11.GL_TEXTURE_RESIDENT");
        GlDebugTextUtils.registerGlConstantName(32872, "GL11.GL_TEXTURE_BINDING_1D");
        GlDebugTextUtils.registerGlConstantName(32873, "GL11.GL_TEXTURE_BINDING_2D");
        GlDebugTextUtils.registerGlConstantName(32884, "GL11.GL_VERTEX_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32885, "GL11.GL_NORMAL_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32886, "GL11.GL_COLOR_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32887, "GL11.GL_INDEX_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32888, "GL11.GL_TEXTURE_COORD_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32889, "GL11.GL_EDGE_FLAG_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32890, "GL11.GL_VERTEX_ARRAY_SIZE");
        GlDebugTextUtils.registerGlConstantName(32891, "GL11.GL_VERTEX_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(32892, "GL11.GL_VERTEX_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(32894, "GL11.GL_NORMAL_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(32895, "GL11.GL_NORMAL_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(32897, "GL11.GL_COLOR_ARRAY_SIZE");
        GlDebugTextUtils.registerGlConstantName(32898, "GL11.GL_COLOR_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(32899, "GL11.GL_COLOR_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(32901, "GL11.GL_INDEX_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(32902, "GL11.GL_INDEX_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(32904, "GL11.GL_TEXTURE_COORD_ARRAY_SIZE");
        GlDebugTextUtils.registerGlConstantName(32905, "GL11.GL_TEXTURE_COORD_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(32906, "GL11.GL_TEXTURE_COORD_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(32908, "GL11.GL_EDGE_FLAG_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(32910, "GL11.GL_VERTEX_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(32911, "GL11.GL_NORMAL_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(32912, "GL11.GL_COLOR_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(32913, "GL11.GL_INDEX_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(32914, "GL11.GL_TEXTURE_COORD_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(32915, "GL11.GL_EDGE_FLAG_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(10784, "GL11.GL_V2F");
        GlDebugTextUtils.registerGlConstantName(10785, "GL11.GL_V3F");
        GlDebugTextUtils.registerGlConstantName(10786, "GL11.GL_C4UB_V2F");
        GlDebugTextUtils.registerGlConstantName(10787, "GL11.GL_C4UB_V3F");
        GlDebugTextUtils.registerGlConstantName(10788, "GL11.GL_C3F_V3F");
        GlDebugTextUtils.registerGlConstantName(10789, "GL11.GL_N3F_V3F");
        GlDebugTextUtils.registerGlConstantName(10790, "GL11.GL_C4F_N3F_V3F");
        GlDebugTextUtils.registerGlConstantName(10791, "GL11.GL_T2F_V3F");
        GlDebugTextUtils.registerGlConstantName(10792, "GL11.GL_T4F_V4F");
        GlDebugTextUtils.registerGlConstantName(10793, "GL11.GL_T2F_C4UB_V3F");
        GlDebugTextUtils.registerGlConstantName(10794, "GL11.GL_T2F_C3F_V3F");
        GlDebugTextUtils.registerGlConstantName(10795, "GL11.GL_T2F_N3F_V3F");
        GlDebugTextUtils.registerGlConstantName(10796, "GL11.GL_T2F_C4F_N3F_V3F");
        GlDebugTextUtils.registerGlConstantName(10797, "GL11.GL_T4F_C4F_N3F_V4F");
        GlDebugTextUtils.registerGlConstantName(3057, "GL11.GL_LOGIC_OP");
        GlDebugTextUtils.registerGlConstantName(4099, "GL11.GL_TEXTURE_COMPONENTS");
        GlDebugTextUtils.registerGlConstantName(32874, "GL12.GL_TEXTURE_BINDING_3D");
        GlDebugTextUtils.registerGlConstantName(32875, "GL12.GL_PACK_SKIP_IMAGES");
        GlDebugTextUtils.registerGlConstantName(32876, "GL12.GL_PACK_IMAGE_HEIGHT");
        GlDebugTextUtils.registerGlConstantName(32877, "GL12.GL_UNPACK_SKIP_IMAGES");
        GlDebugTextUtils.registerGlConstantName(32878, "GL12.GL_UNPACK_IMAGE_HEIGHT");
        GlDebugTextUtils.registerGlConstantName(32879, "GL12.GL_TEXTURE_3D");
        GlDebugTextUtils.registerGlConstantName(32880, "GL12.GL_PROXY_TEXTURE_3D");
        GlDebugTextUtils.registerGlConstantName(32881, "GL12.GL_TEXTURE_DEPTH");
        GlDebugTextUtils.registerGlConstantName(32882, "GL12.GL_TEXTURE_WRAP_R");
        GlDebugTextUtils.registerGlConstantName(32883, "GL12.GL_MAX_3D_TEXTURE_SIZE");
        GlDebugTextUtils.registerGlConstantName(32992, "GL12.GL_BGR");
        GlDebugTextUtils.registerGlConstantName(32993, "GL12.GL_BGRA");
        GlDebugTextUtils.registerGlConstantName(32818, "GL12.GL_UNSIGNED_BYTE_3_3_2");
        GlDebugTextUtils.registerGlConstantName(33634, "GL12.GL_UNSIGNED_BYTE_2_3_3_REV");
        GlDebugTextUtils.registerGlConstantName(33635, "GL12.GL_UNSIGNED_SHORT_5_6_5");
        GlDebugTextUtils.registerGlConstantName(33636, "GL12.GL_UNSIGNED_SHORT_5_6_5_REV");
        GlDebugTextUtils.registerGlConstantName(32819, "GL12.GL_UNSIGNED_SHORT_4_4_4_4");
        GlDebugTextUtils.registerGlConstantName(33637, "GL12.GL_UNSIGNED_SHORT_4_4_4_4_REV");
        GlDebugTextUtils.registerGlConstantName(32820, "GL12.GL_UNSIGNED_SHORT_5_5_5_1");
        GlDebugTextUtils.registerGlConstantName(33638, "GL12.GL_UNSIGNED_SHORT_1_5_5_5_REV");
        GlDebugTextUtils.registerGlConstantName(32821, "GL12.GL_UNSIGNED_INT_8_8_8_8");
        GlDebugTextUtils.registerGlConstantName(33639, "GL12.GL_UNSIGNED_INT_8_8_8_8_REV");
        GlDebugTextUtils.registerGlConstantName(32822, "GL12.GL_UNSIGNED_INT_10_10_10_2");
        GlDebugTextUtils.registerGlConstantName(33640, "GL12.GL_UNSIGNED_INT_2_10_10_10_REV");
        GlDebugTextUtils.registerGlConstantName(32826, "GL12.GL_RESCALE_NORMAL");
        GlDebugTextUtils.registerGlConstantName(33272, "GL12.GL_LIGHT_MODEL_COLOR_CONTROL");
        GlDebugTextUtils.registerGlConstantName(33273, "GL12.GL_SINGLE_COLOR");
        GlDebugTextUtils.registerGlConstantName(33274, "GL12.GL_SEPARATE_SPECULAR_COLOR");
        GlDebugTextUtils.registerGlConstantName(33071, "GL12.GL_CLAMP_TO_EDGE");
        GlDebugTextUtils.registerGlConstantName(33082, "GL12.GL_TEXTURE_MIN_LOD");
        GlDebugTextUtils.registerGlConstantName(33083, "GL12.GL_TEXTURE_MAX_LOD");
        GlDebugTextUtils.registerGlConstantName(33084, "GL12.GL_TEXTURE_BASE_LEVEL");
        GlDebugTextUtils.registerGlConstantName(33085, "GL12.GL_TEXTURE_MAX_LEVEL");
        GlDebugTextUtils.registerGlConstantName(33000, "GL12.GL_MAX_ELEMENTS_VERTICES");
        GlDebugTextUtils.registerGlConstantName(33001, "GL12.GL_MAX_ELEMENTS_INDICES");
        GlDebugTextUtils.registerGlConstantName(33901, "GL12.GL_ALIASED_POINT_SIZE_RANGE");
        GlDebugTextUtils.registerGlConstantName(33902, "GL12.GL_ALIASED_LINE_WIDTH_RANGE");
        GlDebugTextUtils.registerGlConstantName(33984, "GL13.GL_TEXTURE0");
        GlDebugTextUtils.registerGlConstantName(33985, "GL13.GL_TEXTURE1");
        GlDebugTextUtils.registerGlConstantName(33986, "GL13.GL_TEXTURE2");
        GlDebugTextUtils.registerGlConstantName(33987, "GL13.GL_TEXTURE3");
        GlDebugTextUtils.registerGlConstantName(33988, "GL13.GL_TEXTURE4");
        GlDebugTextUtils.registerGlConstantName(33989, "GL13.GL_TEXTURE5");
        GlDebugTextUtils.registerGlConstantName(33990, "GL13.GL_TEXTURE6");
        GlDebugTextUtils.registerGlConstantName(33991, "GL13.GL_TEXTURE7");
        GlDebugTextUtils.registerGlConstantName(33992, "GL13.GL_TEXTURE8");
        GlDebugTextUtils.registerGlConstantName(33993, "GL13.GL_TEXTURE9");
        GlDebugTextUtils.registerGlConstantName(33994, "GL13.GL_TEXTURE10");
        GlDebugTextUtils.registerGlConstantName(33995, "GL13.GL_TEXTURE11");
        GlDebugTextUtils.registerGlConstantName(33996, "GL13.GL_TEXTURE12");
        GlDebugTextUtils.registerGlConstantName(33997, "GL13.GL_TEXTURE13");
        GlDebugTextUtils.registerGlConstantName(33998, "GL13.GL_TEXTURE14");
        GlDebugTextUtils.registerGlConstantName(33999, "GL13.GL_TEXTURE15");
        GlDebugTextUtils.registerGlConstantName(34000, "GL13.GL_TEXTURE16");
        GlDebugTextUtils.registerGlConstantName(34001, "GL13.GL_TEXTURE17");
        GlDebugTextUtils.registerGlConstantName(34002, "GL13.GL_TEXTURE18");
        GlDebugTextUtils.registerGlConstantName(34003, "GL13.GL_TEXTURE19");
        GlDebugTextUtils.registerGlConstantName(34004, "GL13.GL_TEXTURE20");
        GlDebugTextUtils.registerGlConstantName(34005, "GL13.GL_TEXTURE21");
        GlDebugTextUtils.registerGlConstantName(34006, "GL13.GL_TEXTURE22");
        GlDebugTextUtils.registerGlConstantName(34007, "GL13.GL_TEXTURE23");
        GlDebugTextUtils.registerGlConstantName(34008, "GL13.GL_TEXTURE24");
        GlDebugTextUtils.registerGlConstantName(34009, "GL13.GL_TEXTURE25");
        GlDebugTextUtils.registerGlConstantName(34010, "GL13.GL_TEXTURE26");
        GlDebugTextUtils.registerGlConstantName(34011, "GL13.GL_TEXTURE27");
        GlDebugTextUtils.registerGlConstantName(34012, "GL13.GL_TEXTURE28");
        GlDebugTextUtils.registerGlConstantName(34013, "GL13.GL_TEXTURE29");
        GlDebugTextUtils.registerGlConstantName(34014, "GL13.GL_TEXTURE30");
        GlDebugTextUtils.registerGlConstantName(34015, "GL13.GL_TEXTURE31");
        GlDebugTextUtils.registerGlConstantName(34016, "GL13.GL_ACTIVE_TEXTURE");
        GlDebugTextUtils.registerGlConstantName(34017, "GL13.GL_CLIENT_ACTIVE_TEXTURE");
        GlDebugTextUtils.registerGlConstantName(34018, "GL13.GL_MAX_TEXTURE_UNITS");
        GlDebugTextUtils.registerGlConstantName(34065, "GL13.GL_NORMAL_MAP");
        GlDebugTextUtils.registerGlConstantName(34066, "GL13.GL_REFLECTION_MAP");
        GlDebugTextUtils.registerGlConstantName(34067, "GL13.GL_TEXTURE_CUBE_MAP");
        GlDebugTextUtils.registerGlConstantName(34068, "GL13.GL_TEXTURE_BINDING_CUBE_MAP");
        GlDebugTextUtils.registerGlConstantName(34069, "GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X");
        GlDebugTextUtils.registerGlConstantName(34070, "GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X");
        GlDebugTextUtils.registerGlConstantName(34071, "GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y");
        GlDebugTextUtils.registerGlConstantName(34072, "GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y");
        GlDebugTextUtils.registerGlConstantName(34073, "GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z");
        GlDebugTextUtils.registerGlConstantName(34074, "GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z");
        GlDebugTextUtils.registerGlConstantName(34075, "GL13.GL_PROXY_TEXTURE_CUBE_MAP");
        GlDebugTextUtils.registerGlConstantName(34076, "GL13.GL_MAX_CUBE_MAP_TEXTURE_SIZE");
        GlDebugTextUtils.registerGlConstantName(34025, "GL13.GL_COMPRESSED_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34026, "GL13.GL_COMPRESSED_LUMINANCE");
        GlDebugTextUtils.registerGlConstantName(34027, "GL13.GL_COMPRESSED_LUMINANCE_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34028, "GL13.GL_COMPRESSED_INTENSITY");
        GlDebugTextUtils.registerGlConstantName(34029, "GL13.GL_COMPRESSED_RGB");
        GlDebugTextUtils.registerGlConstantName(34030, "GL13.GL_COMPRESSED_RGBA");
        GlDebugTextUtils.registerGlConstantName(34031, "GL13.GL_TEXTURE_COMPRESSION_HINT");
        GlDebugTextUtils.registerGlConstantName(34464, "GL13.GL_TEXTURE_COMPRESSED_IMAGE_SIZE");
        GlDebugTextUtils.registerGlConstantName(34465, "GL13.GL_TEXTURE_COMPRESSED");
        GlDebugTextUtils.registerGlConstantName(34466, "GL13.GL_NUM_COMPRESSED_TEXTURE_FORMATS");
        GlDebugTextUtils.registerGlConstantName(34467, "GL13.GL_COMPRESSED_TEXTURE_FORMATS");
        GlDebugTextUtils.registerGlConstantName(32925, "GL13.GL_MULTISAMPLE");
        GlDebugTextUtils.registerGlConstantName(32926, "GL13.GL_SAMPLE_ALPHA_TO_COVERAGE");
        GlDebugTextUtils.registerGlConstantName(32927, "GL13.GL_SAMPLE_ALPHA_TO_ONE");
        GlDebugTextUtils.registerGlConstantName(32928, "GL13.GL_SAMPLE_COVERAGE");
        GlDebugTextUtils.registerGlConstantName(32936, "GL13.GL_SAMPLE_BUFFERS");
        GlDebugTextUtils.registerGlConstantName(32937, "GL13.GL_SAMPLES");
        GlDebugTextUtils.registerGlConstantName(32938, "GL13.GL_SAMPLE_COVERAGE_VALUE");
        GlDebugTextUtils.registerGlConstantName(32939, "GL13.GL_SAMPLE_COVERAGE_INVERT");
        GlDebugTextUtils.registerGlConstantName(34019, "GL13.GL_TRANSPOSE_MODELVIEW_MATRIX");
        GlDebugTextUtils.registerGlConstantName(34020, "GL13.GL_TRANSPOSE_PROJECTION_MATRIX");
        GlDebugTextUtils.registerGlConstantName(34021, "GL13.GL_TRANSPOSE_TEXTURE_MATRIX");
        GlDebugTextUtils.registerGlConstantName(34022, "GL13.GL_TRANSPOSE_COLOR_MATRIX");
        GlDebugTextUtils.registerGlConstantName(34160, "GL13.GL_COMBINE");
        GlDebugTextUtils.registerGlConstantName(34161, "GL13.GL_COMBINE_RGB");
        GlDebugTextUtils.registerGlConstantName(34162, "GL13.GL_COMBINE_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34176, "GL13.GL_SOURCE0_RGB");
        GlDebugTextUtils.registerGlConstantName(34177, "GL13.GL_SOURCE1_RGB");
        GlDebugTextUtils.registerGlConstantName(34178, "GL13.GL_SOURCE2_RGB");
        GlDebugTextUtils.registerGlConstantName(34184, "GL13.GL_SOURCE0_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34185, "GL13.GL_SOURCE1_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34186, "GL13.GL_SOURCE2_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34192, "GL13.GL_OPERAND0_RGB");
        GlDebugTextUtils.registerGlConstantName(34193, "GL13.GL_OPERAND1_RGB");
        GlDebugTextUtils.registerGlConstantName(34194, "GL13.GL_OPERAND2_RGB");
        GlDebugTextUtils.registerGlConstantName(34200, "GL13.GL_OPERAND0_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34201, "GL13.GL_OPERAND1_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34202, "GL13.GL_OPERAND2_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34163, "GL13.GL_RGB_SCALE");
        GlDebugTextUtils.registerGlConstantName(34164, "GL13.GL_ADD_SIGNED");
        GlDebugTextUtils.registerGlConstantName(34165, "GL13.GL_INTERPOLATE");
        GlDebugTextUtils.registerGlConstantName(34023, "GL13.GL_SUBTRACT");
        GlDebugTextUtils.registerGlConstantName(34166, "GL13.GL_CONSTANT");
        GlDebugTextUtils.registerGlConstantName(34167, "GL13.GL_PRIMARY_COLOR");
        GlDebugTextUtils.registerGlConstantName(34168, "GL13.GL_PREVIOUS");
        GlDebugTextUtils.registerGlConstantName(34478, "GL13.GL_DOT3_RGB");
        GlDebugTextUtils.registerGlConstantName(34479, "GL13.GL_DOT3_RGBA");
        GlDebugTextUtils.registerGlConstantName(33069, "GL13.GL_CLAMP_TO_BORDER");
        GlDebugTextUtils.registerGlConstantName(33169, "GL14.GL_GENERATE_MIPMAP");
        GlDebugTextUtils.registerGlConstantName(33170, "GL14.GL_GENERATE_MIPMAP_HINT");
        GlDebugTextUtils.registerGlConstantName(33189, "GL14.GL_DEPTH_COMPONENT16");
        GlDebugTextUtils.registerGlConstantName(33190, "GL14.GL_DEPTH_COMPONENT24");
        GlDebugTextUtils.registerGlConstantName(33191, "GL14.GL_DEPTH_COMPONENT32");
        GlDebugTextUtils.registerGlConstantName(34890, "GL14.GL_TEXTURE_DEPTH_SIZE");
        GlDebugTextUtils.registerGlConstantName(34891, "GL14.GL_DEPTH_TEXTURE_MODE");
        GlDebugTextUtils.registerGlConstantName(34892, "GL14.GL_TEXTURE_COMPARE_MODE");
        GlDebugTextUtils.registerGlConstantName(34893, "GL14.GL_TEXTURE_COMPARE_FUNC");
        GlDebugTextUtils.registerGlConstantName(34894, "GL14.GL_COMPARE_R_TO_TEXTURE");
        GlDebugTextUtils.registerGlConstantName(33872, "GL14.GL_FOG_COORDINATE_SOURCE");
        GlDebugTextUtils.registerGlConstantName(33873, "GL14.GL_FOG_COORDINATE");
        GlDebugTextUtils.registerGlConstantName(33874, "GL14.GL_FRAGMENT_DEPTH");
        GlDebugTextUtils.registerGlConstantName(33875, "GL14.GL_CURRENT_FOG_COORDINATE");
        GlDebugTextUtils.registerGlConstantName(33876, "GL14.GL_FOG_COORDINATE_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(33877, "GL14.GL_FOG_COORDINATE_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(33878, "GL14.GL_FOG_COORDINATE_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(33879, "GL14.GL_FOG_COORDINATE_ARRAY");
        GlDebugTextUtils.registerGlConstantName(33062, "GL14.GL_POINT_SIZE_MIN");
        GlDebugTextUtils.registerGlConstantName(33063, "GL14.GL_POINT_SIZE_MAX");
        GlDebugTextUtils.registerGlConstantName(33064, "GL14.GL_POINT_FADE_THRESHOLD_SIZE");
        GlDebugTextUtils.registerGlConstantName(33065, "GL14.GL_POINT_DISTANCE_ATTENUATION");
        GlDebugTextUtils.registerGlConstantName(33880, "GL14.GL_COLOR_SUM");
        GlDebugTextUtils.registerGlConstantName(33881, "GL14.GL_CURRENT_SECONDARY_COLOR");
        GlDebugTextUtils.registerGlConstantName(33882, "GL14.GL_SECONDARY_COLOR_ARRAY_SIZE");
        GlDebugTextUtils.registerGlConstantName(33883, "GL14.GL_SECONDARY_COLOR_ARRAY_TYPE");
        GlDebugTextUtils.registerGlConstantName(33884, "GL14.GL_SECONDARY_COLOR_ARRAY_STRIDE");
        GlDebugTextUtils.registerGlConstantName(33885, "GL14.GL_SECONDARY_COLOR_ARRAY_POINTER");
        GlDebugTextUtils.registerGlConstantName(33886, "GL14.GL_SECONDARY_COLOR_ARRAY");
        GlDebugTextUtils.registerGlConstantName(32968, "GL14.GL_BLEND_DST_RGB");
        GlDebugTextUtils.registerGlConstantName(32969, "GL14.GL_BLEND_SRC_RGB");
        GlDebugTextUtils.registerGlConstantName(32970, "GL14.GL_BLEND_DST_ALPHA");
        GlDebugTextUtils.registerGlConstantName(32971, "GL14.GL_BLEND_SRC_ALPHA");
        GlDebugTextUtils.registerGlConstantName(34055, "GL14.GL_INCR_WRAP");
        GlDebugTextUtils.registerGlConstantName(34056, "GL14.GL_DECR_WRAP");
        GlDebugTextUtils.registerGlConstantName(34048, "GL14.GL_TEXTURE_FILTER_CONTROL");
        GlDebugTextUtils.registerGlConstantName(34049, "GL14.GL_TEXTURE_LOD_BIAS");
        GlDebugTextUtils.registerGlConstantName(34045, "GL14.GL_MAX_TEXTURE_LOD_BIAS");
        GlDebugTextUtils.registerGlConstantName(33648, "GL14.GL_MIRRORED_REPEAT");
        GlDebugTextUtils.registerGlConstantName(32773, "ARBImaging.GL_BLEND_COLOR");
        GlDebugTextUtils.registerGlConstantName(32777, "ARBImaging.GL_BLEND_EQUATION");
        GlDebugTextUtils.registerGlConstantName(32774, "GL14.GL_FUNC_ADD");
        GlDebugTextUtils.registerGlConstantName(32778, "GL14.GL_FUNC_SUBTRACT");
        GlDebugTextUtils.registerGlConstantName(32779, "GL14.GL_FUNC_REVERSE_SUBTRACT");
        GlDebugTextUtils.registerGlConstantName(32775, "GL14.GL_MIN");
        GlDebugTextUtils.registerGlConstantName(32776, "GL14.GL_MAX");
        GlDebugTextUtils.registerGlConstantName(34962, "GL15.GL_ARRAY_BUFFER");
        GlDebugTextUtils.registerGlConstantName(34963, "GL15.GL_ELEMENT_ARRAY_BUFFER");
        GlDebugTextUtils.registerGlConstantName(34964, "GL15.GL_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34965, "GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34966, "GL15.GL_VERTEX_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34967, "GL15.GL_NORMAL_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34968, "GL15.GL_COLOR_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34969, "GL15.GL_INDEX_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34970, "GL15.GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34971, "GL15.GL_EDGE_FLAG_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34972, "GL15.GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34973, "GL15.GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34974, "GL15.GL_WEIGHT_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(34975, "GL15.GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING");
        GlDebugTextUtils.registerGlConstantName(35040, "GL15.GL_STREAM_DRAW");
        GlDebugTextUtils.registerGlConstantName(35041, "GL15.GL_STREAM_READ");
        GlDebugTextUtils.registerGlConstantName(35042, "GL15.GL_STREAM_COPY");
        GlDebugTextUtils.registerGlConstantName(35044, "GL15.GL_STATIC_DRAW");
        GlDebugTextUtils.registerGlConstantName(35045, "GL15.GL_STATIC_READ");
        GlDebugTextUtils.registerGlConstantName(35046, "GL15.GL_STATIC_COPY");
        GlDebugTextUtils.registerGlConstantName(35048, "GL15.GL_DYNAMIC_DRAW");
        GlDebugTextUtils.registerGlConstantName(35049, "GL15.GL_DYNAMIC_READ");
        GlDebugTextUtils.registerGlConstantName(35050, "GL15.GL_DYNAMIC_COPY");
        GlDebugTextUtils.registerGlConstantName(35000, "GL15.GL_READ_ONLY");
        GlDebugTextUtils.registerGlConstantName(35001, "GL15.GL_WRITE_ONLY");
        GlDebugTextUtils.registerGlConstantName(35002, "GL15.GL_READ_WRITE");
        GlDebugTextUtils.registerGlConstantName(34660, "GL15.GL_BUFFER_SIZE");
        GlDebugTextUtils.registerGlConstantName(34661, "GL15.GL_BUFFER_USAGE");
        GlDebugTextUtils.registerGlConstantName(35003, "GL15.GL_BUFFER_ACCESS");
        GlDebugTextUtils.registerGlConstantName(35004, "GL15.GL_BUFFER_MAPPED");
        GlDebugTextUtils.registerGlConstantName(35005, "GL15.GL_BUFFER_MAP_POINTER");
        GlDebugTextUtils.registerGlConstantName(34138, "NVFogDistance.GL_FOG_DISTANCE_MODE_NV");
        GlDebugTextUtils.registerGlConstantName(34139, "NVFogDistance.GL_EYE_RADIAL_NV");
        GlDebugTextUtils.registerGlConstantName(34140, "NVFogDistance.GL_EYE_PLANE_ABSOLUTE_NV");
    }
}

