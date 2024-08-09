/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLFWNativeX11 {
    protected GLFWNativeX11() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="Display *")
    public static long glfwGetX11Display() {
        long l = Functions.GetX11Display;
        return JNI.invokeP(l);
    }

    @NativeType(value="RRCrtc")
    public static long glfwGetX11Adapter(@NativeType(value="GLFWmonitor *") long l) {
        long l2 = Functions.GetX11Adapter;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="RROutput")
    public static long glfwGetX11Monitor(@NativeType(value="GLFWmonitor *") long l) {
        long l2 = Functions.GetX11Monitor;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="Window")
    public static long glfwGetX11Window(@NativeType(value="GLFWwindow *") long l) {
        long l2 = Functions.GetX11Window;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return JNI.invokePP(l, l2);
    }

    public static void nglfwSetX11SelectionString(long l) {
        long l2 = Functions.SetX11SelectionString;
        JNI.invokePV(l, l2);
    }

    public static void glfwSetX11SelectionString(@NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        GLFWNativeX11.nglfwSetX11SelectionString(MemoryUtil.memAddress(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glfwSetX11SelectionString(@NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            GLFWNativeX11.nglfwSetX11SelectionString(l);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nglfwGetX11SelectionString() {
        long l = Functions.GetX11SelectionString;
        return JNI.invokeP(l);
    }

    @Nullable
    @NativeType(value="char const *")
    public static String glfwGetX11SelectionString() {
        long l = GLFWNativeX11.nglfwGetX11SelectionString();
        return MemoryUtil.memUTF8Safe(l);
    }

    public static final class Functions {
        public static final long GetX11Display = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Display");
        public static final long GetX11Adapter = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Adapter");
        public static final long GetX11Monitor = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Monitor");
        public static final long GetX11Window = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Window");
        public static final long SetX11SelectionString = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwSetX11SelectionString");
        public static final long GetX11SelectionString = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11SelectionString");

        private Functions() {
        }
    }
}

