/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;

public class CustomFramebuffer
extends Framebuffer {
    private boolean linear;

    public CustomFramebuffer(boolean bl) {
        super(1, 1, bl, Minecraft.IS_RUNNING_ON_MAC);
    }

    private static void resizeFramebuffer(Framebuffer framebuffer) {
        Minecraft minecraft = Minecraft.getInstance();
        if (framebuffer.framebufferWidth != minecraft.getMainWindow().getWidth() || framebuffer.framebufferHeight != minecraft.getMainWindow().getHeight()) {
            framebuffer.createBuffers(Math.max(minecraft.getMainWindow().getWidth(), 1), Math.max(minecraft.getMainWindow().getHeight(), 1), Minecraft.IS_RUNNING_ON_MAC);
        }
    }

    public static void drawTexture() {
        Minecraft minecraft = Minecraft.getInstance();
        MainWindow mainWindow = minecraft.getMainWindow();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        float f = mainWindow.getScaledWidth();
        float f2 = mainWindow.getScaledHeight();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0.0, 0.0, 0.0).tex(0.0f, 1.0f).endVertex();
        bufferBuilder.pos(0.0, f2, 0.0).tex(0.0f, 0.0f).endVertex();
        bufferBuilder.pos(f, f2, 0.0).tex(1.0f, 0.0f).endVertex();
        bufferBuilder.pos(f, 0.0, 0.0).tex(1.0f, 1.0f).endVertex();
        tessellator.draw();
    }

    public static void drawTexture(int n) {
        Minecraft minecraft = Minecraft.getInstance();
        MainWindow mainWindow = minecraft.getMainWindow();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        float f = mainWindow.getScaledWidth();
        float f2 = mainWindow.getScaledHeight();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        bufferBuilder.pos(0.0, 0.0, 0.0).color(n).tex(0.0f, 1.0f).endVertex();
        bufferBuilder.pos(0.0, f2, 0.0).color(n).tex(0.0f, 0.0f).endVertex();
        bufferBuilder.pos(f, f2, 0.0).color(n).tex(1.0f, 0.0f).endVertex();
        bufferBuilder.pos(f, 0.0, 0.0).color(n).tex(1.0f, 1.0f).endVertex();
        tessellator.draw();
    }

    public CustomFramebuffer setLinear() {
        this.linear = true;
        return this;
    }

    @Override
    public void setFramebufferFilter(int n) {
        super.setFramebufferFilter(this.linear ? 9729 : n);
    }

    public void setup() {
        CustomFramebuffer.resizeFramebuffer(this);
        this.framebufferClear(Minecraft.IS_RUNNING_ON_MAC);
        this.bindFramebuffer(true);
    }

    public void draw() {
        this.bindFramebufferTexture();
        CustomFramebuffer.drawTexture();
    }

    public void draw(int n) {
        this.bindFramebufferTexture();
        CustomFramebuffer.drawTexture(n);
    }

    public void draw(Framebuffer framebuffer) {
        framebuffer.bindFramebufferTexture();
        CustomFramebuffer.drawTexture();
    }
}

