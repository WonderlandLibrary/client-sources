/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import java.nio.FloatBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.ShaderUtility;
import ru.govno.client.utils.Utility;

public class BloomUtil
implements Utility {
    static Minecraft mc = Minecraft.getMinecraft();
    public static ShaderUtility gaussianBloom = new ShaderUtility("vegaline/system/shaders/bloom.frag");
    public static Framebuffer framebuffer = ShaderUtility.createFrameBuffer(new Framebuffer(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), true));
    static Framebuffer framebuffer1 = ShaderUtility.createFrameBuffer(new Framebuffer(1, 1, false));

    public static void update(Framebuffer framebuffer) {
        if (framebuffer.framebufferWidth != BloomUtil.mc.displayWidth || framebuffer.framebufferHeight != BloomUtil.mc.displayHeight) {
            framebuffer.createBindFramebuffer(BloomUtil.mc.displayWidth, BloomUtil.mc.displayHeight);
        }
    }

    public static void renderShadow(Runnable run, int color, int radius, int offset, float des, boolean fill) {
        BloomUtil.update(framebuffer1);
        framebuffer1.framebufferClear();
        framebuffer1.bindFramebuffer(true);
        run.run();
        framebuffer1.unbindFramebuffer();
        BloomUtil.renderBlur(BloomUtil.framebuffer1.framebufferTexture, radius, offset, color, des, fill);
    }

    public static void renderBlur(int sourceTexture, float radius, int offset, int c, float des, boolean fill) {
        framebuffer = ShaderUtility.createFrameBuffer(framebuffer);
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableBlend();
        FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        int i = 1;
        while ((float)i <= radius + 1.0f) {
            weightBuffer.put(ShaderUtility.calculateGaussianValue(i, radius / 2.0f));
            ++i;
        }
        weightBuffer.rewind();
        BloomUtil.setAlphaLimit(0.0f);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        gaussianBloom.init();
        BloomUtil.setupUniforms(radius, 1, 0, weightBuffer, c, des, fill);
        ShaderUtility.bindTexture(sourceTexture);
        ShaderUtility.drawQuads();
        gaussianBloom.unload();
        framebuffer.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);
        gaussianBloom.init();
        BloomUtil.setupUniforms(radius, 0, 1, weightBuffer, c, des, fill);
        GlStateManager.setActiveTexture(34000);
        ShaderUtility.bindTexture(sourceTexture);
        GlStateManager.setActiveTexture(33984);
        ShaderUtility.bindTexture(BloomUtil.framebuffer.framebufferTexture);
        ShaderUtility.drawQuads();
        gaussianBloom.unload();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableAlpha();
        GlStateManager.bindTexture(0);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float)((double)limit * 0.01));
    }

    public static void setupUniforms(float radius, int directionX, int directionY, FloatBuffer weights, int c, float des, boolean fill) {
        gaussianBloom.setUniformi("avoidTexture", fill ? 1 : 0);
        gaussianBloom.setUniformi("inTexture", 0);
        gaussianBloom.setUniformi("textureToCheck", 16);
        gaussianBloom.setUniformf("radius", radius);
        gaussianBloom.setUniformf("exposure", des);
        gaussianBloom.setUniformf("color", ColorUtils.getGLRedFromColor(c), ColorUtils.getGLGreenFromColor(c), ColorUtils.getGLBlueFromColor(c));
        gaussianBloom.setUniformf("texelSize", 1.0f / (float)BloomUtil.mc.displayWidth, 1.0f / (float)BloomUtil.mc.displayHeight);
        gaussianBloom.setUniformf("direction", directionX, directionY);
        GL20.glUniform1(gaussianBloom.getUniform("weights"), weights);
    }
}

