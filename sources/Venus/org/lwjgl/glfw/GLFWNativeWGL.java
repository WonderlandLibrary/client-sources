/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLFWNativeWGL {
    protected GLFWNativeWGL() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="HGLRC")
    public static long glfwGetWGLContext(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetWGLContext;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static final class Functions {
        public static final long GetWGLContext = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWGLContext");

        private Functions() {
        }
    }
}

