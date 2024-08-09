package dev.excellent.impl.util.shader.impl;

import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.shader.ShaderLink;
import lombok.Getter;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.IRenderCall;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

@Getter
public class BloomShader implements IRenderAccess {
    private final ShaderLink upSample = ShaderLink.create("kawaseUpBloom");
    private final ShaderLink downSample = ShaderLink.create("kawaseDownBloom");
    public List<IRenderCall> displayBlurQueue = new LinkedList<>();
    public List<IRenderCall> cameraBlurQueue = new LinkedList<>();
    private Framebuffer inFramebuffer = new Framebuffer(mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight(), true);
    private Framebuffer outFramebuffer = new Framebuffer(mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight(), true);
    private int iterations;
    private final List<Framebuffer> framebufferList = new ArrayList<>();

    private void initFramebuffers(float iterations) {

        for (Framebuffer framebuffer : framebufferList) {
            framebuffer.deleteFramebuffer();
        }
        framebufferList.clear();

        framebufferList.add(outFramebuffer = RenderUtil.FrameBuffer.createFrameBuffer(null, false));

        for (int i = 0; i <= iterations; i++) {
            int width = (int) (mc.getMainWindow().getFramebufferWidth() / Math.pow(2, i));
            int height = (int) (mc.getMainWindow().getFramebufferHeight() / Math.pow(2, i));
            Framebuffer currentBuffer = new Framebuffer(width, height, false);

            currentBuffer.setFramebufferFilter(GL_LINEAR);
            GlStateManager.bindTexture(currentBuffer.framebufferTexture);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL14.GL_MIRRORED_REPEAT);
            GlStateManager.bindTexture(0);

            framebufferList.add(currentBuffer);
        }
    }


    public void draw(int iterations, float offset, RenderType type, float saturation) {
        if (cameraBlurQueue.isEmpty() && displayBlurQueue.isEmpty()) return;
        if (inFramebuffer.framebufferWidth != mc.getMainWindow().getFramebufferWidth() || inFramebuffer.framebufferHeight != mc.getMainWindow().getFramebufferHeight()) {
            inFramebuffer.framebufferClear();
            inFramebuffer.deleteFramebuffer();
            inFramebuffer = RenderUtil.FrameBuffer.createFrameBuffer(inFramebuffer);
        }
        if (this.iterations != iterations || outFramebuffer.framebufferWidth != mc.getMainWindow().getFramebufferWidth() || outFramebuffer.framebufferHeight != mc.getMainWindow().getFramebufferHeight()) {
            initFramebuffers(iterations);
            this.iterations = iterations;
        }

        switch (type) {
            case CAMERA -> {
                inFramebuffer.framebufferClear();

                inFramebuffer.bindFramebuffer(false);
                if (!cameraBlurQueue.isEmpty()) {
                    processQueue(cameraBlurQueue);
                }
                inFramebuffer.unbindFramebuffer();
                mc.getFramebuffer().bindFramebuffer(true);
                RenderHelper.disableStandardItemLighting();
            }
            case DISPLAY -> {

                inFramebuffer.bindFramebuffer(false);
                if (!displayBlurQueue.isEmpty()) {
                    processQueue(displayBlurQueue);
                }
                inFramebuffer.unbindFramebuffer();

                GlStateManager.enableAlphaTest();
                GlStateManager.alphaFunc(GL_GREATER, 0);

                GL11.glClearColor(0, 0, 0, 0);
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);


                renderFBO(framebufferList.get(1), inFramebuffer.framebufferTexture, downSample, offset);
                renderFBO(framebufferList.get(1), inFramebuffer.framebufferTexture, upSample, offset);

                for (int i = 1; i < iterations; i++) {
                    renderFBO(framebufferList.get(i + 1), framebufferList.get(i).framebufferTexture, downSample, offset);
                }

                for (int i = iterations; i > 1; i--) {
                    renderFBO(framebufferList.get(i - 1), framebufferList.get(i).framebufferTexture, upSample, offset);
                }

                Framebuffer lastBuffer = framebufferList.get(0);
                lastBuffer.framebufferClear();
                lastBuffer.bindFramebuffer(false);

                upSample.init();
                upSample.setUniformf("offset", offset, offset);
                upSample.setUniformi("inTexture", 0);
                upSample.setUniformf("saturation", saturation);
                upSample.setUniformi("check", 1);
                upSample.setUniformi("textureToCheck", 16);
                upSample.setUniformf("halfpixel", 1.0f / lastBuffer.framebufferWidth, 1.0f / lastBuffer.framebufferHeight);
                upSample.setUniformf("iResolution", lastBuffer.framebufferWidth, lastBuffer.framebufferHeight);

                GL13.glActiveTexture(GL13.GL_TEXTURE16);
                GlStateManager.bindTexture(inFramebuffer.framebufferTexture);

                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GlStateManager.bindTexture(framebufferList.get(1).framebufferTexture);

                drawQuads();
                upSample.unload();

                mc.getFramebuffer().bindFramebuffer(false);
                GlStateManager.bindTexture(framebufferList.get(0).framebufferTexture);
                GLUtils.startBlend();
                drawQuads();
                GLUtils.endBlend();

                GlStateManager.bindTexture(0);
                reset();
            }
        }
    }

    private void renderFBO(Framebuffer framebuffer, int framebufferTexture, ShaderLink shader, float offset) {
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(false);

        GlStateManager.bindTexture(framebufferTexture);
        shader.init();
        shader.setUniformf("offset", offset, offset);
        shader.setUniformi("inTexture", 0);
        shader.setUniformi("check", 0);
        shader.setUniformf("halfpixel", 1.0f / framebuffer.framebufferWidth, 1.0f / framebuffer.framebufferHeight);
        shader.setUniformf("iResolution", framebuffer.framebufferWidth, framebuffer.framebufferHeight);
        GlStateManager.disableBlend();
        drawQuads();
        shader.unload();
    }

    private void drawQuads() {
        BUFFER.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        BUFFER.pos(0, 0, 0).tex(0, 1).endVertex();
        BUFFER.pos(0, Math.max(mc.getMainWindow().getScaledHeight(), 1), 0).tex(0, 0).endVertex();
        BUFFER.pos(Math.max(mc.getMainWindow().getScaledWidth(), 1), Math.max(mc.getMainWindow().getScaledHeight(), 1), 0).tex(1, 0).endVertex();
        BUFFER.pos(Math.max(mc.getMainWindow().getScaledWidth(), 1), 0, 0).tex(1, 1).endVertex();
        TESSELLATOR.draw();
    }

    public void addTask2D(IRenderCall callback) {
        displayBlurQueue.add(callback);
    }

    public void addTask3D(IRenderCall callback) {
        cameraBlurQueue.add(callback);
    }

    public enum RenderType {
        CAMERA, DISPLAY
    }

    public void processQueue(List<IRenderCall> queue) {
        for (IRenderCall renderCall : queue) {
            renderCall.execute();
        }
    }

    public void reset() {
        cameraBlurQueue.clear();
        displayBlurQueue.clear();
    }
}
