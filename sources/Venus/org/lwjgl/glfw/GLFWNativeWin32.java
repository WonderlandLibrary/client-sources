/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLFWNativeWin32 {
    protected GLFWNativeWin32() {
        throw new UnsupportedOperationException();
    }

    public static long nglfwGetWin32Adapter(long l) {
        long l2 = Functions.GetWin32Adapter;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String glfwGetWin32Adapter(@NativeType(value="GLFWmonitor *") long l) {
        long l2 = GLFWNativeWin32.nglfwGetWin32Adapter(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    public static long nglfwGetWin32Monitor(long l) {
        long l2 = Functions.GetWin32Monitor;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String glfwGetWin32Monitor(@NativeType(value="GLFWmonitor *") long l) {
        long l2 = GLFWNativeWin32.nglfwGetWin32Monitor(l);
        return MemoryUtil.memUTF8Safe(l2);
    }

    @NativeType(value="HWND")
    public static long glfwGetWin32Window(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetWin32Window;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="GLFWwindow *")
    public static long glfwAttachWin32Window(@NativeType(value="HWND") long l, @NativeType(value="GLFWwindow *") long l2) {
        long l3 = Functions.AttachWin32Window;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePPP(l, l2, l3);
    }

    public static final class Functions {
        public static final long GetWin32Adapter = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWin32Adapter");
        public static final long GetWin32Monitor = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWin32Monitor");
        public static final long GetWin32Window = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWin32Window");
        public static final long AttachWin32Window = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwAttachWin32Window");

        private Functions() {
        }
    }
}

