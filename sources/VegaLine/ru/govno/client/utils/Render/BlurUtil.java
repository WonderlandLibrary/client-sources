/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BlurUtil {
    protected static Minecraft mc = Minecraft.getMinecraft();
    public ResourceLocation resourceLocation = new ResourceLocation("vegaline/system/shaders/blur.json");
    public ShaderGroup shaderGroup;
    public Framebuffer framebuffer;
    public int lastFactor;
    public int lastWidth;
    public int lastHeight;

    public void init() {
        try {
            this.shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), this.resourceLocation);
            this.shaderGroup.createBindFramebuffers(BlurUtil.mc.displayWidth, BlurUtil.mc.displayHeight);
            this.framebuffer = this.shaderGroup.mainFramebuffer;
        } catch (Exception exception) {
            // empty catch block
        }
    }

    public void blur(float xBlur, float yBlur, float widthBlur, float heightBlur, float strength) {
        int height;
        int width;
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaleFactor = ScaledResolution.getScaleFactor();
        if (this.sizeHasChanged(scaleFactor, width = scaledResolution.getScaledWidth(), height = scaledResolution.getScaledHeight()) || this.framebuffer == null || this.shaderGroup == null) {
            this.init();
        }
        this.lastFactor = scaleFactor;
        this.lastWidth = width;
        this.lastHeight = height;
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        BlurUtil.scissorRect(xBlur, yBlur, widthBlur, heightBlur);
        if (this.framebuffer != null) {
            this.framebuffer.bindFramebuffer(true);
        }
        this.shaderGroup.loadShaderGroup(mc.getRenderPartialTicks());
        this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("BlurDir").set(1.0f, 0.0f);
        this.shaderGroup.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("BlurDir").set(0.0f, 1.0f);
        this.shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(2).getShaderManager().getShaderUniform("BlurDir").set(0.0f, 1.0f);
        this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("Radius").set(strength);
        this.shaderGroup.getShaders().get(3).getShaderManager().getShaderUniform("BlurDir").set(1.0f, 0.0f);
        if (mc.getFramebuffer() != null) {
            mc.getFramebuffer().bindFramebuffer(true);
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public boolean sizeHasChanged(int scaleFactor, int width, int height) {
        return this.lastFactor != scaleFactor || this.lastWidth != width || this.lastHeight != height;
    }

    public static void scissorRect(float x, float y, float width, double height) {
        ScaledResolution sr = new ScaledResolution(mc);
        int factor = ScaledResolution.getScaleFactor();
        GL11.glScissor((int)(x * (float)factor), (int)(((double)sr.getScaledHeight() - height) * (double)factor), (int)((width - x) * (float)factor), (int)((height - (double)y) * (double)factor));
    }
}

