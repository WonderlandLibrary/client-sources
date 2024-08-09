/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.renderer.VideoMode;
import net.optifine.util.VideoModeComparator;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public final class Monitor {
    private final long monitorPointer;
    private final List<VideoMode> videoModes;
    private VideoMode defaultVideoMode;
    private int virtualPosX;
    private int virtualPosY;

    public Monitor(long l) {
        this.monitorPointer = l;
        this.videoModes = Lists.newArrayList();
        this.setup();
    }

    public void setup() {
        Object object2;
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        this.videoModes.clear();
        GLFWVidMode.Buffer buffer = GLFW.glfwGetVideoModes(this.monitorPointer);
        GLFWVidMode gLFWVidMode = GLFW.glfwGetVideoMode(this.monitorPointer);
        VideoMode videoMode = new VideoMode(gLFWVidMode);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i = buffer.limit() - 1; i >= 0; --i) {
            buffer.position(i);
            object2 = new VideoMode(buffer);
            if (((VideoMode)object2).getRedBits() < 8 || ((VideoMode)object2).getGreenBits() < 8 || ((VideoMode)object2).getBlueBits() < 8) continue;
            if (((VideoMode)object2).getRefreshRate() < videoMode.getRefreshRate()) {
                arrayList.add(object2);
                continue;
            }
            this.videoModes.add((VideoMode)object2);
        }
        arrayList.sort(new VideoModeComparator().reversed());
        for (Object object2 : arrayList) {
            if (Monitor.getVideoMode(this.videoModes, ((VideoMode)object2).getWidth(), ((VideoMode)object2).getHeight()) != null) continue;
            this.videoModes.add((VideoMode)object2);
        }
        this.videoModes.sort(new VideoModeComparator());
        Object object3 = new int[1];
        object2 = new int[1];
        GLFW.glfwGetMonitorPos(this.monitorPointer, (int[])object3, (int[])object2);
        this.virtualPosX = (int)object3[0];
        this.virtualPosY = (int)object2[0];
        GLFWVidMode gLFWVidMode2 = GLFW.glfwGetVideoMode(this.monitorPointer);
        this.defaultVideoMode = new VideoMode(gLFWVidMode2);
    }

    public VideoMode getVideoModeOrDefault(Optional<VideoMode> optional) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        if (optional.isPresent()) {
            VideoMode videoMode = optional.get();
            for (VideoMode videoMode2 : this.videoModes) {
                if (!videoMode2.equals(videoMode)) continue;
                return videoMode2;
            }
        }
        return this.getDefaultVideoMode();
    }

    public int getVideoModeIndex(VideoMode videoMode) {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        return this.videoModes.indexOf(videoMode);
    }

    public VideoMode getDefaultVideoMode() {
        return this.defaultVideoMode;
    }

    public int getVirtualPosX() {
        return this.virtualPosX;
    }

    public int getVirtualPosY() {
        return this.virtualPosY;
    }

    public VideoMode getVideoModeFromIndex(int n) {
        return this.videoModes.get(n);
    }

    public int getVideoModeCount() {
        return this.videoModes.size();
    }

    public long getMonitorPointer() {
        return this.monitorPointer;
    }

    public String toString() {
        return String.format("Monitor[%s %sx%s %s]", this.monitorPointer, this.virtualPosX, this.virtualPosY, this.defaultVideoMode);
    }

    public static VideoMode getVideoMode(List<VideoMode> list, int n, int n2) {
        for (VideoMode videoMode : list) {
            if (videoMode.getWidth() != n || videoMode.getHeight() != n2) continue;
            return videoMode;
        }
        return null;
    }
}

