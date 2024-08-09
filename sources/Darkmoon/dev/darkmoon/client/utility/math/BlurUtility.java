package dev.darkmoon.client.utility.math;

import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.render.StencilUtility;
import dev.darkmoon.client.utility.render.blur.GaussianBlur;
import dev.darkmoon.client.utility.render.shader.ShaderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlurUtility implements Utility {
    public static Framebuffer bloomFramebuffer =  ShaderUtility.createFrameBuffer(new Framebuffer(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), true));
    private static int lastScale, lastScaleWidth,lastScaleHeight;
    private static Framebuffer buffer;
    private static final ResourceLocation shader = new ResourceLocation("shaders/post/blur.json");

    private static ShaderGroup blurShader;
    public static void drawBlur(float radius, Runnable data) {
        StencilUtility.initStencilToWrite();
        data.run();
        StencilUtility.readStencilBuffer(1);
        GaussianBlur.renderBlur(radius);
        StencilUtility.uninitStencilBuffer();
    }
    public static void drawBlur(Runnable data, float radius) {
        StencilUtility.initStencilToWrite();
        data.run();
        StencilUtility.readStencilBuffer(1);
        GaussianBlur.renderBlur(radius);
        StencilUtility.uninitStencilBuffer();
    }
    public static void blurAreaBoarder(int x, int y, int width, int height, float intensity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null || blurShader == null) {
            inShaderFBO();
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        GL11.glScissor(x * factor, mc.displayHeight - y * factor - height * factor, width * factor, height * factor);
        GL11.glEnable(3089);
        shaderConfigFix(intensity, 1.0f, 0.0f);
        buffer.bindFramebuffer(true);
        blurShader.render(mc.timer.renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
        GL11.glDisable(3089);
    }
    public static void shaderConfigFix(float intensity, float blurWidth, float blurHeight) {
        blurShader.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(intensity);
        blurShader.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(intensity);
        blurShader.getShaders().get(0).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
        blurShader.getShaders().get(1).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
    }
    public static void inShaderFBO() {
        try {
            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = blurShader.mainFramebuffer;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void renderBlur(int x, int y, int width, int height, int blurRadius) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        blurAreaBoarder(x, y, width, height, blurRadius);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
    }
    public static void drawShadow(float radius, float des, Runnable data, Color c) {
        bloomFramebuffer.framebufferClear();
        bloomFramebuffer.bindFramebuffer(true);
        data.run();
        bloomFramebuffer.unbindFramebuffer();
        BloomUtility.renderBlur(bloomFramebuffer.framebufferTexture, (int) radius, (int) 1, c, des, true);

    }
}