/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLFWNativeEGL {
    protected GLFWNativeEGL() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="EGLDisplay")
    public static long glfwGetEGLDisplay() {
        long l = Functions.GetEGLDisplay;
        return JNI.invokeP(l);
    }

    @NativeType(value="EGLContext")
    public static long glfwGetEGLContext(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetEGLContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="EGLSurface")
    public static long glfwGetEGLSurface(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetEGLSurface;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static final class Functions {
        public static final long GetEGLDisplay = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetEGLDisplay");
        public static final long GetEGLContext = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetEGLContext");
        public static final long GetEGLSurface = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetEGLSurface");

        private Functions() {
        }
    }
}

