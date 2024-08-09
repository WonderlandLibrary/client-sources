package dev.excellent.impl.util.render;

import com.google.common.collect.Queues;
import dev.excellent.impl.util.shader.ShaderLink;
import net.minecraft.client.shader.Framebuffer;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.IRenderCall;
import org.lwjgl.opengl.GL30;

import java.util.concurrent.ConcurrentLinkedQueue;

import static dev.excellent.api.interfaces.game.IMinecraft.mc;


public class OutlineShader {

    private static final ShaderLink outline = ShaderLink.create("outline");
    private static final ConcurrentLinkedQueue<IRenderCall> renderQueue = Queues.newConcurrentLinkedQueue();
    private static final Framebuffer inFrameBuffer = new Framebuffer(1, 1, true, false);
    private static final Framebuffer outFrameBuffer = new Framebuffer(1, 1, true, false);

    public static void addTask2D(IRenderCall rc) {
        renderQueue.add(rc);
    }

    public static void draw(int radius) {
        if (renderQueue.isEmpty())
            return;

        setupBuffer(inFrameBuffer);
        setupBuffer(outFrameBuffer);

        inFrameBuffer.bindFramebuffer(true);

        while (!renderQueue.isEmpty()) {
            renderQueue.poll().execute();
        }

        outFrameBuffer.bindFramebuffer(true);

        outline.init();
        outline.setUniformf("size", (float) radius);
        outline.setUniformi("textureIn", 0);
        outline.setUniformi("textureToCheck", 20);
        outline.setUniformf("texelSize", 1.0F / (float) mc.getMainWindow().getWidth(), 1.0F / (float) mc.getMainWindow().getHeight());
        outline.setUniformf("direction", 1.0F, 0.0F);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL30.GL_ONE, GL30.GL_SRC_ALPHA);
        GL30.glAlphaFunc(GL30.GL_GREATER, 0.0001f);

        inFrameBuffer.bindFramebufferTexture();
        CustomFramebuffer.drawTexture();

        mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        outline.setUniformf("direction", 0.0F, 1.0F);

        outFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(GL30.GL_TEXTURE20);
        inFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        CustomFramebuffer.drawTexture();

        outline.unload();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static Framebuffer setupBuffer(Framebuffer frameBuffer) {
        if (frameBuffer.framebufferWidth != mc.getMainWindow().getWidth() || frameBuffer.framebufferHeight != mc.getMainWindow().getFramebufferHeight())
            frameBuffer.resize(Math.max(1, mc.getMainWindow().getWidth()), Math.max(1, mc.getMainWindow().getFramebufferHeight()), false);
        else
            frameBuffer.framebufferClear(false);
        frameBuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);

        return frameBuffer;
    }
}
