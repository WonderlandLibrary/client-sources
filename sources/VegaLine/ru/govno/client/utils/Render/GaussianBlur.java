/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import ru.govno.client.utils.Render.ShaderUtility;
import ru.govno.client.utils.Render.StencilUtil;

public class GaussianBlur {
    static Minecraft mc = Minecraft.getMinecraft();
    public static ShaderUtility blurShader = new ShaderUtility("vegaline/system/shaders/gaussian.frag");
    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);
    public static Framebuffer framebuffer1 = ShaderUtility.createFrameBuffer(new Framebuffer(1, 1, false));

    public static void setupUniforms(float dir1, float dir2, float radius) {
        blurShader.setUniformi("textureIn", 0);
        blurShader.setUniformf("texelSize", 1.0f / (float)GaussianBlur.mc.displayWidth, 1.0f / (float)GaussianBlur.mc.displayHeight);
        blurShader.setUniformf("direction", dir1, dir2);
        blurShader.setUniformf("radius", radius);
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        int i = 0;
        while ((float)i <= radius) {
            weightBuffer.put(ShaderUtility.calculateGaussianValue(i, radius / 2.0f));
            ++i;
        }
        weightBuffer.rewind();
        GL20.glUniform1(blurShader.getUniform("weights"), weightBuffer);
    }

    public static void drawBlur(float radius, Runnable data) {
        StencilUtil.initStencilToWrite();
        data.run();
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(radius);
        StencilUtil.uninitStencilBuffer();
    }

    public static void update(Framebuffer framebuffer) {
        if (framebuffer.framebufferWidth != GaussianBlur.mc.displayWidth || framebuffer.framebufferHeight != GaussianBlur.mc.displayHeight) {
            framebuffer.createBindFramebuffer(GaussianBlur.mc.displayWidth, GaussianBlur.mc.displayHeight);
        }
    }

    public static void renderBlur(float radius, List<Runnable> run) {
        GaussianBlur.update(framebuffer1);
        framebuffer1.framebufferClear();
        framebuffer1.bindFramebuffer(true);
        run.forEach(Runnable::run);
        framebuffer1.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);
        if (framebuffer1 != null) {
            GL11.glPushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            mc.getFramebuffer().bindFramebuffer(true);
            ShaderUtility.bindTexture(GaussianBlur.framebuffer1.framebufferTexture);
            GaussianBlur.drawBlur(radius, ShaderUtility::drawQuads);
            mc.getFramebuffer().bindFramebuffer(false);
            GlStateManager.disableAlpha();
            GL11.glPopMatrix();
        }
    }

    public static void renderBlur(float radius, Runnable run) {
        GaussianBlur.update(framebuffer1);
        framebuffer1.framebufferClear();
        framebuffer1.bindFramebuffer(true);
        run.run();
        framebuffer1.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);
        if (framebuffer1 != null) {
            GL11.glPushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            mc.getFramebuffer().bindFramebuffer(true);
            ShaderUtility.bindTexture(GaussianBlur.framebuffer1.framebufferTexture);
            GaussianBlur.drawBlur(radius, ShaderUtility::drawQuads);
            mc.getFramebuffer().bindFramebuffer(false);
            GlStateManager.disableAlpha();
            GL11.glPopMatrix();
        }
    }

    public static void renderBlur(float radius) {
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.enableBlend();
        GL11.glDisable(2929);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GaussianBlur.update(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        GaussianBlur.setupUniforms(1.0f, 0.0f, radius);
        ShaderUtility.bindTexture(GaussianBlur.mc.getFramebuffer().framebufferTexture);
        ShaderUtility.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();
        mc.getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        GaussianBlur.setupUniforms(0.0f, 1.0f, radius);
        ShaderUtility.bindTexture(GaussianBlur.framebuffer.framebufferTexture);
        ShaderUtility.drawQuads();
        blurShader.unload();
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
        GL11.glEnable(2929);
    }

    public static void renderBlur(Framebuffer framebuffer, float radius) {
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.enableBlend();
        GL11.glDisable(2929);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GaussianBlur.update(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        GaussianBlur.setupUniforms(1.0f, 0.0f, radius);
        ShaderUtility.bindTexture(GaussianBlur.mc.getFramebuffer().framebufferTexture);
        ShaderUtility.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();
        mc.getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        GaussianBlur.setupUniforms(0.0f, 1.0f, radius);
        ShaderUtility.bindTexture(framebuffer.framebufferTexture);
        ShaderUtility.drawQuads();
        blurShader.unload();
        GlStateManager.resetColor();
        GlStateManager.bindTexture(0);
        GL11.glEnable(2929);
    }
}

