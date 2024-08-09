/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLFWNativeCocoa {
    protected GLFWNativeCocoa() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="CGDirectDisplayID")
    public static int glfwGetCocoaMonitor(@NativeType(value="GLFWmonitor *") long l) {
        long l2 = Functions.GetCocoaMonitor;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePI(l, l2);
    }

    @NativeType(value="id")
    public static long glfwGetCocoaWindow(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetCocoaWindow;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static final class Functions {
        public static final long GetCocoaMonitor = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetCocoaMonitor");
        public static final long GetCocoaWindow = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetCocoaWindow");

        private Functions() {
        }
    }
}

