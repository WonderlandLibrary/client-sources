/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.IMonitorFactory;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public class MonitorHandler {
    private final Long2ObjectMap<Monitor> monitorsById = new Long2ObjectOpenHashMap<Monitor>();
    private final IMonitorFactory monitorFactory;

    public MonitorHandler(IMonitorFactory iMonitorFactory) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        this.monitorFactory = iMonitorFactory;
        GLFW.glfwSetMonitorCallback(this::onMonitorUpdate);
        PointerBuffer pointerBuffer = GLFW.glfwGetMonitors();
        if (pointerBuffer != null) {
            for (int i = 0; i < pointerBuffer.limit(); ++i) {
                long l = pointerBuffer.get(i);
                this.monitorsById.put(l, iMonitorFactory.createMonitor(l));
            }
        }
    }

    private void onMonitorUpdate(long l, int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (n == 262145) {
            this.monitorsById.put(l, this.monitorFactory.createMonitor(l));
        } else if (n == 262146) {
            this.monitorsById.remove(l);
        }
    }

    @Nullable
    public Monitor getMonitor(long l) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return (Monitor)this.monitorsById.get(l);
    }

    @Nullable
    public Monitor getMonitor(MainWindow mainWindow) {
        long l = GLFW.glfwGetWindowMonitor(mainWindow.getHandle());
        if (l != 0L) {
            return this.getMonitor(l);
        }
        int n = mainWindow.getWindowX();
        int n2 = n + mainWindow.getWidth();
        int n3 = mainWindow.getWindowY();
        int n4 = n3 + mainWindow.getHeight();
        int n5 = -1;
        Monitor monitor = null;
        for (Monitor monitor2 : this.monitorsById.values()) {
            int n6;
            int n7 = monitor2.getVirtualPosX();
            int n8 = n7 + monitor2.getDefaultVideoMode().getWidth();
            int n9 = monitor2.getVirtualPosY();
            int n10 = n9 + monitor2.getDefaultVideoMode().getHeight();
            int n11 = MonitorHandler.clamp(n, n7, n8);
            int n12 = MonitorHandler.clamp(n2, n7, n8);
            int n13 = MonitorHandler.clamp(n3, n9, n10);
            int n14 = MonitorHandler.clamp(n4, n9, n10);
            int n15 = Math.max(0, n12 - n11);
            int n16 = n15 * (n6 = Math.max(0, n14 - n13));
            if (n16 <= n5) continue;
            monitor = monitor2;
            n5 = n16;
        }
        return monitor;
    }

    public static int clamp(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        return n > n3 ? n3 : n;
    }

    public void close() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GLFWMonitorCallback gLFWMonitorCallback = GLFW.glfwSetMonitorCallback(null);
        if (gLFWMonitorCallback != null) {
            gLFWMonitorCallback.free();
        }
    }
}

