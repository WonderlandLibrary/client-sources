/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.Callback;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public final class Callbacks {
    private Callbacks() {
    }

    public static void glfwFreeCallbacks(@NativeType(value="GLFWwindow *") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        for (long l2 : new long[]{GLFW.Functions.SetWindowPosCallback, GLFW.Functions.SetWindowSizeCallback, GLFW.Functions.SetWindowCloseCallback, GLFW.Functions.SetWindowRefreshCallback, GLFW.Functions.SetWindowFocusCallback, GLFW.Functions.SetWindowIconifyCallback, GLFW.Functions.SetWindowMaximizeCallback, GLFW.Functions.SetFramebufferSizeCallback, GLFW.Functions.SetWindowContentScaleCallback, GLFW.Functions.SetKeyCallback, GLFW.Functions.SetCharCallback, GLFW.Functions.SetCharModsCallback, GLFW.Functions.SetMouseButtonCallback, GLFW.Functions.SetCursorPosCallback, GLFW.Functions.SetCursorEnterCallback, GLFW.Functions.SetScrollCallback, GLFW.Functions.SetDropCallback}) {
            long l3 = JNI.invokePPP(l, 0L, l2);
            if (l3 == 0L) continue;
            Callback.free(l3);
        }
    }
}

