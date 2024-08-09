package dev.darkmoon.client.utility.math;

import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.render.shader.Shader;
import dev.darkmoon.client.utility.render.shader.ShaderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_GREATER;

public class BloomUtility implements Utility {

    public static Shader gaussianBloom = new Shader("darkmoon/shaders/bloom.frag", true);

    public static Framebuffer framebuffer = ShaderUtility.createFrameBuffer(new Framebuffer(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), true));


    public static void renderBlur(int sourceTexture, int radius, int offset, Color c, float des, boolean fill) {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 1; i <= radius; i++) {
            weightBuffer.put(ShaderUtility.calculateGaussianValue(i, radius / 2f));
        }
        weightBuffer.rewind();

        setAlphaLimit(0.0F);

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        gaussianBloom.useProgram();
        setupUniforms(radius, 1, 0, weightBuffer, c, des);
        ShaderUtility.bindTexture(sourceTexture);
        ShaderUtility.drawQuads();
        gaussianBloom.unloadProgram();
        framebuffer.unbindFramebuffer();


        mc.getFramebuffer().bindFramebuffer(true);
        gaussianBloom.useProgram();
        setupUniforms(radius, 0, 1, weightBuffer, c, des);
        GlStateManager.setActiveTexture(GL13.GL_TEXTURE16);
        ShaderUtility.bindTexture(sourceTexture);
        GlStateManager.setActiveTexture(GL13.GL_TEXTURE0);
        ShaderUtility.bindTexture(framebuffer.framebufferTexture);
        ShaderUtility.drawQuads();
        gaussianBloom.unloadProgram();

        GlStateManager.alphaFunc(516, 0f);
        GlStateManager.enableAlpha();

        GlStateManager.bindTexture(0);
        GlStateManager.popMatrix();
    }

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (limit * .01));
    }

    public static void setupUniforms(int radius, int directionX, int directionY, FloatBuffer weights, Color c, float des) {
        gaussianBloom.setUniformi("inTexture", 0);
        gaussianBloom.setUniformi("textureToCheck", 16);
        gaussianBloom.setUniformf("radius", radius);
        gaussianBloom.setUniformf("exposure", des);
        gaussianBloom.setUniformf("color", c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f);
        gaussianBloom.setUniformf("texelSize", 1.0F / (float) mc.displayWidth, 1.0F / (float) mc.displayHeight);
        gaussianBloom.setUniformf("direction", directionX, directionY);
        GL20.glUniform1(gaussianBloom.getUniform("weights"), weights);
    }
}