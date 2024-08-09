/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlDebugTextUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.optifine.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class GLX {
    private static final Logger LOGGER = LogManager.getLogger();
    private static String capsString = "";
    private static String cpuInfo;
    private static final Map<Integer, String> LOOKUP_MAP;

    public static String getOpenGLVersionString() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        return GLFW.glfwGetCurrentContext() == 0L ? "NO CONTEXT" : GlStateManager.getString(7937) + " GL version " + GlStateManager.getString(7938) + ", " + GlStateManager.getString(7936);
    }

    public static int _getRefreshRate(MainWindow mainWindow) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        long l = GLFW.glfwGetWindowMonitor(mainWindow.getHandle());
        if (l == 0L) {
            l = GLFW.glfwGetPrimaryMonitor();
        }
        GLFWVidMode gLFWVidMode = l == 0L ? null : GLFW.glfwGetVideoMode(l);
        return gLFWVidMode == null ? 0 : gLFWVidMode.refreshRate();
    }

    public static String _getLWJGLVersion() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return Version.getVersion();
    }

    public static LongSupplier _initGlfw() {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        MainWindow.checkGlfwError(GLX::lambda$_initGlfw$1);
        ArrayList<String> arrayList = Lists.newArrayList();
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback((arg_0, arg_1) -> GLX.lambda$_initGlfw$2(arrayList, arg_0, arg_1));
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(arrayList));
        }
        LongSupplier longSupplier = GLX::lambda$_initGlfw$3;
        for (String string : arrayList) {
            LOGGER.error("GLFW error collected during initialization: {}", (Object)string);
        }
        RenderSystem.setErrorCallback(gLFWErrorCallback);
        return longSupplier;
    }

    public static void _setGlfwErrorCallback(GLFWErrorCallbackI gLFWErrorCallbackI) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLFWErrorCallback gLFWErrorCallback = GLFW.glfwSetErrorCallback(gLFWErrorCallbackI);
        if (gLFWErrorCallback != null) {
            gLFWErrorCallback.free();
        }
    }

    public static boolean _shouldClose(MainWindow mainWindow) {
        return GLFW.glfwWindowShouldClose(mainWindow.getHandle());
    }

    public static void _setupNvFogDistance() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (GL.getCapabilities().GL_NV_fog_distance) {
            if (Config.isFogFancy()) {
                GlStateManager.fogi(34138, 34139);
            }
            if (Config.isFogFast()) {
                GlStateManager.fogi(34138, 34140);
            }
        }
    }

    public static void _init(int n, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        GLCapabilities gLCapabilities = GL.getCapabilities();
        capsString = "Using framebuffer using " + GlStateManager.init(gLCapabilities);
        try {
            Processor[] processorArray = new SystemInfo().getHardware().getProcessors();
            cpuInfo = String.format("%dx %s", processorArray.length, processorArray[0]).replaceAll("\\s+", " ");
        } catch (Throwable throwable) {
            // empty catch block
        }
        GlDebugTextUtils.setDebugVerbosity(n, bl);
    }

    public static String _getCapsString() {
        return capsString;
    }

    public static String _getCpuInfo() {
        return cpuInfo == null ? "<unknown>" : cpuInfo;
    }

    public static void _renderCrosshair(int n, boolean bl, boolean bl2, boolean bl3) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.disableTexture();
        GlStateManager.depthMask(false);
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GL11.glLineWidth(4.0f);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        if (bl) {
            bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(n, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        }
        if (bl2) {
            bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(0.0, n, 0.0).color(0, 0, 0, 255).endVertex();
        }
        if (bl3) {
            bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(0.0, 0.0, n).color(0, 0, 0, 255).endVertex();
        }
        tessellator.draw();
        GL11.glLineWidth(2.0f);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        if (bl) {
            bufferBuilder.pos(0.0, 0.0, 0.0).color(255, 0, 0, 255).endVertex();
            bufferBuilder.pos(n, 0.0, 0.0).color(255, 0, 0, 255).endVertex();
        }
        if (bl2) {
            bufferBuilder.pos(0.0, 0.0, 0.0).color(0, 255, 0, 255).endVertex();
            bufferBuilder.pos(0.0, n, 0.0).color(0, 255, 0, 255).endVertex();
        }
        if (bl3) {
            bufferBuilder.pos(0.0, 0.0, 0.0).color(127, 127, 255, 255).endVertex();
            bufferBuilder.pos(0.0, 0.0, n).color(127, 127, 255, 255).endVertex();
        }
        tessellator.draw();
        GL11.glLineWidth(1.0f);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture();
    }

    public static String getErrorString(int n) {
        return LOOKUP_MAP.get(n);
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> T make(T t, Consumer<T> consumer) {
        consumer.accept(t);
        return t;
    }

    public static boolean isUsingFBOs() {
        return !Config.isAntialiasing();
    }

    public static boolean useVbo() {
        return false;
    }

    private static long lambda$_initGlfw$3() {
        return (long)(GLFW.glfwGetTime() * 1.0E9);
    }

    private static void lambda$_initGlfw$2(List list, int n, long l) {
        list.add(String.format("GLFW error during init: [0x%X]%s", n, l));
    }

    private static void lambda$_initGlfw$1(Integer n, String string) {
        throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", n, string));
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put(0, "No error");
        hashMap.put(1280, "Enum parameter is invalid for this function");
        hashMap.put(1281, "Parameter is invalid for this function");
        hashMap.put(1282, "Current state is invalid for this function");
        hashMap.put(1283, "Stack overflow");
        hashMap.put(1284, "Stack underflow");
        hashMap.put(1285, "Out of memory");
        hashMap.put(1286, "Operation on incomplete framebuffer");
        hashMap.put(1286, "Operation on incomplete framebuffer");
    }

    static {
        LOOKUP_MAP = GLX.make(Maps.newHashMap(), GLX::lambda$static$0);
    }
}

