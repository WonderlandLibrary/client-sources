package dev.vertic.util.render;

import dev.vertic.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ZERO;
import static org.lwjgl.opengl.GL20.glUniform1;

public class BlurUtil implements Utils {

    public static ShaderUtil blurShader = new ShaderUtil("vertic/shaders/gaussian.frag");

    public static Framebuffer framebuffer = new Framebuffer(1, 1, false);


    public static void setupUniforms(float dir1, float dir2, float radius) {
        blurShader.setUniformi("textureIn", 0);
        blurShader.setUniformf("texelSize", 1.0F / (float) mc.displayWidth, 1.0F / (float) mc.displayHeight);
        blurShader.setUniformf("direction", dir1, dir2);
        blurShader.setUniformf("radius", radius);

        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(256);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(calculateGaussianValue(i, radius / 2));
        }

        weightBuffer.rewind();
        glUniform1(blurShader.getUniform("weights"), weightBuffer);
    }

    public static void startBlur() {
        StencilUtil.initStencilToWrite();
    }

    public static void endBlur(final int blurRadius) {
        StencilUtil.readStencilBuffer(1);
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 1);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);

        framebuffer = RenderUtil.createFrameBuffer(framebuffer);

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        setupUniforms(1, 0, blurRadius);

        RenderUtil.bindTexture(mc.getFramebuffer().framebufferTexture);

        ShaderUtil.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();

        mc.getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        setupUniforms(0, 1, blurRadius);

        RenderUtil.bindTexture(framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        blurShader.unload();

        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.bindTexture(0);
        StencilUtil.uninitStencilBuffer();
    }

    public static void doBlur(final int blurRadius, final Runnable... runnable) {
        if (!Display.isActive() || mc.gameSettings.ofFastRender) {
            return;
        }

        StencilUtil.initStencilToWrite();

        Arrays.stream(runnable).forEach(Runnable::run);

        StencilUtil.readStencilBuffer(1);
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 1);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        framebuffer = RenderUtil.createFrameBuffer(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        setupUniforms(1, 0, blurRadius);
        RenderUtil.bindTexture(mc.getFramebuffer().framebufferTexture);
        ShaderUtil.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();
        mc.getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        setupUniforms(0, 1, blurRadius);
        RenderUtil.bindTexture(framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        blurShader.unload();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.bindTexture(0);
        StencilUtil.uninitStencilBuffer();
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

}
