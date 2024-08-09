/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.io.PrintStream;
import javax.annotation.Nullable;
import org.lwjgl.opengl.AMDDebugOutput;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageAMDCallback;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;

public final class GLUtil {
    private GLUtil() {
    }

    @Nullable
    public static Callback setupDebugMessageCallback() {
        return GLUtil.setupDebugMessageCallback(APIUtil.DEBUG_STREAM);
    }

    @Nullable
    public static Callback setupDebugMessageCallback(PrintStream printStream) {
        GLCapabilities gLCapabilities = GL.getCapabilities();
        if (gLCapabilities.OpenGL43) {
            APIUtil.apiLog("[GL] Using OpenGL 4.3 for error logging.");
            GLDebugMessageCallback gLDebugMessageCallback = GLDebugMessageCallback.create((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6) -> GLUtil.lambda$setupDebugMessageCallback$0(printStream, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6));
            GL43C.glDebugMessageCallback(gLDebugMessageCallback, 0L);
            if ((GL43C.glGetInteger(33310) & 2) == 0) {
                APIUtil.apiLog("[GL] Warning: A non-debug context may not produce any debug output.");
                GL43C.glEnable(37600);
            }
            return gLDebugMessageCallback;
        }
        if (gLCapabilities.GL_KHR_debug) {
            APIUtil.apiLog("[GL] Using KHR_debug for error logging.");
            GLDebugMessageCallback gLDebugMessageCallback = GLDebugMessageCallback.create((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6) -> GLUtil.lambda$setupDebugMessageCallback$1(printStream, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6));
            KHRDebug.glDebugMessageCallback(gLDebugMessageCallback, 0L);
            if (gLCapabilities.OpenGL30 && (GL43C.glGetInteger(33310) & 2) == 0) {
                APIUtil.apiLog("[GL] Warning: A non-debug context may not produce any debug output.");
                GL43C.glEnable(37600);
            }
            return gLDebugMessageCallback;
        }
        if (gLCapabilities.GL_ARB_debug_output) {
            APIUtil.apiLog("[GL] Using ARB_debug_output for error logging.");
            GLDebugMessageARBCallback gLDebugMessageARBCallback = GLDebugMessageARBCallback.create((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6) -> GLUtil.lambda$setupDebugMessageCallback$2(printStream, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5, arg_6));
            ARBDebugOutput.glDebugMessageCallbackARB(gLDebugMessageARBCallback, 0L);
            return gLDebugMessageARBCallback;
        }
        if (gLCapabilities.GL_AMD_debug_output) {
            APIUtil.apiLog("[GL] Using AMD_debug_output for error logging.");
            GLDebugMessageAMDCallback gLDebugMessageAMDCallback = GLDebugMessageAMDCallback.create((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> GLUtil.lambda$setupDebugMessageCallback$3(printStream, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5));
            AMDDebugOutput.glDebugMessageCallbackAMD(gLDebugMessageAMDCallback, 0L);
            return gLDebugMessageAMDCallback;
        }
        APIUtil.apiLog("[GL] No debug output implementation is available.");
        return null;
    }

    private static void printDetail(PrintStream printStream, String string, String string2) {
        printStream.printf("\t%s: %s\n", string, string2);
    }

    private static String getDebugSource(int n) {
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
        return APIUtil.apiUnknownToken(n);
    }

    private static String getDebugType(int n) {
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
        return APIUtil.apiUnknownToken(n);
    }

    private static String getDebugSeverity(int n) {
        switch (n) {
            case 37190: {
                return "HIGH";
            }
            case 37191: {
                return "MEDIUM";
            }
            case 37192: {
                return "LOW";
            }
            case 33387: {
                return "NOTIFICATION";
            }
        }
        return APIUtil.apiUnknownToken(n);
    }

    private static String getSourceARB(int n) {
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
        return APIUtil.apiUnknownToken(n);
    }

    private static String getTypeARB(int n) {
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
        }
        return APIUtil.apiUnknownToken(n);
    }

    private static String getSeverityARB(int n) {
        switch (n) {
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
        return APIUtil.apiUnknownToken(n);
    }

    private static String getCategoryAMD(int n) {
        switch (n) {
            case 37193: {
                return "API ERROR";
            }
            case 37194: {
                return "WINDOW SYSTEM";
            }
            case 37195: {
                return "DEPRECATION";
            }
            case 37196: {
                return "UNDEFINED BEHAVIOR";
            }
            case 37197: {
                return "PERFORMANCE";
            }
            case 37198: {
                return "SHADER COMPILER";
            }
            case 37199: {
                return "APPLICATION";
            }
            case 37200: {
                return "OTHER";
            }
        }
        return APIUtil.apiUnknownToken(n);
    }

    private static String getSeverityAMD(int n) {
        switch (n) {
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
        return APIUtil.apiUnknownToken(n);
    }

    private static void lambda$setupDebugMessageCallback$3(PrintStream printStream, int n, int n2, int n3, int n4, long l, long l2) {
        printStream.println("[LWJGL] AMD_debug_output message");
        GLUtil.printDetail(printStream, "ID", String.format("0x%X", n));
        GLUtil.printDetail(printStream, "Category", GLUtil.getCategoryAMD(n2));
        GLUtil.printDetail(printStream, "Severity", GLUtil.getSeverityAMD(n3));
        GLUtil.printDetail(printStream, "Message", GLDebugMessageAMDCallback.getMessage(n4, l));
    }

    private static void lambda$setupDebugMessageCallback$2(PrintStream printStream, int n, int n2, int n3, int n4, int n5, long l, long l2) {
        printStream.println("[LWJGL] ARB_debug_output message");
        GLUtil.printDetail(printStream, "ID", String.format("0x%X", n3));
        GLUtil.printDetail(printStream, "Source", GLUtil.getSourceARB(n));
        GLUtil.printDetail(printStream, "Type", GLUtil.getTypeARB(n2));
        GLUtil.printDetail(printStream, "Severity", GLUtil.getSeverityARB(n4));
        GLUtil.printDetail(printStream, "Message", GLDebugMessageARBCallback.getMessage(n5, l));
    }

    private static void lambda$setupDebugMessageCallback$1(PrintStream printStream, int n, int n2, int n3, int n4, int n5, long l, long l2) {
        printStream.println("[LWJGL] OpenGL debug message");
        GLUtil.printDetail(printStream, "ID", String.format("0x%X", n3));
        GLUtil.printDetail(printStream, "Source", GLUtil.getDebugSource(n));
        GLUtil.printDetail(printStream, "Type", GLUtil.getDebugType(n2));
        GLUtil.printDetail(printStream, "Severity", GLUtil.getDebugSeverity(n4));
        GLUtil.printDetail(printStream, "Message", GLDebugMessageCallback.getMessage(n5, l));
    }

    private static void lambda$setupDebugMessageCallback$0(PrintStream printStream, int n, int n2, int n3, int n4, int n5, long l, long l2) {
        printStream.println("[LWJGL] OpenGL debug message");
        GLUtil.printDetail(printStream, "ID", String.format("0x%X", n3));
        GLUtil.printDetail(printStream, "Source", GLUtil.getDebugSource(n));
        GLUtil.printDetail(printStream, "Type", GLUtil.getDebugType(n2));
        GLUtil.printDetail(printStream, "Severity", GLUtil.getDebugSeverity(n4));
        GLUtil.printDetail(printStream, "Message", GLDebugMessageCallback.getMessage(n5, l));
    }
}

