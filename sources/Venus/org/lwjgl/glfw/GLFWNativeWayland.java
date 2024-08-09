/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLFWNativeWayland {
    protected GLFWNativeWayland() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="struct wl_display *")
    public static long glfwGetWaylandDisplay() {
        long l = Functions.GetWaylandDisplay;
        return JNI.invokeP(l);
    }

    @NativeType(value="struct wl_output *")
    public static long glfwGetWaylandMonitor(@NativeType(value="GLFWmonitor *") long l) {
        long l2 = Functions.GetWaylandMonitor;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="struct wl_surface *")
    public static long glfwGetWaylandWindow(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetWaylandWindow;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static final class Functions {
        public static final long GetWaylandDisplay = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWaylandDisplay");
        public static final long GetWaylandMonitor = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWaylandMonitor");
        public static final long GetWaylandWindow = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWaylandWindow");

        private Functions() {
        }
    }
}

