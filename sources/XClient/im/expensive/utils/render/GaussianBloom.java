package im.expensive.utils.render;

import java.nio.FloatBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.IRenderCall;

import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.shader.ShaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

import com.mojang.blaze3d.systems.RenderSystem;

public class GaussianBloom implements IMinecraft {

    public static GaussianBloom INGAME = new GaussianBloom();
    public static GaussianBloom GUI = new GaussianBloom();
    private final ShaderUtil bloom = new ShaderUtil("bloom");
    private final ConcurrentLinkedQueue<IRenderCall> renderQueue = Queues.newConcurrentLinkedQueue();

    private final Framebuffer inFrameBuffer = new Framebuffer(1, 1, true, false);
    private final Framebuffer outFrameBuffer = new Framebuffer(1, 1, true, false);

    public void registerRenderCall(IRenderCall rc) {
        renderQueue.add(rc);
    }

    public void draw(int radius, float exp, boolean fill, float direction) {
        if (renderQueue.isEmpty())
            return;
        
        setupBuffer(inFrameBuffer);
        setupBuffer(outFrameBuffer);

        inFrameBuffer.bindFramebuffer(true);
        while (!renderQueue.isEmpty()) {
            renderQueue.poll().execute();
        }
        inFrameBuffer.unbindFramebuffer();

        outFrameBuffer.bindFramebuffer(true);

        bloom.attach();
        bloom.setUniformf("radius", radius);
        bloom.setUniformf("exposure", exp);
        bloom.setUniform("textureIn", 0);
        bloom.setUniform("textureToCheck", 20);
        bloom.setUniform("avoidTexture", fill ? 1 : 0);
        final FloatBuffer weightBuffer = BufferUtils.createFloatBuffer(128);
        for (int i = 0; i <= radius; i++) {
            weightBuffer.put(calculateGaussianValue(i, radius / 2));
        }
        weightBuffer.rewind();
        RenderSystem.glUniform1(bloom.getUniform("weights"), weightBuffer);
        bloom.setUniformf("texelSize", 1.0F / (float) Minecraft.getInstance().getMainWindow().getWidth(),
                1.0F / (float) Minecraft.getInstance().getMainWindow().getHeight());
        bloom.setUniformf("direction", direction, 0.0F);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL30.GL_ONE, GL30.GL_SRC_ALPHA);
        GL30.glAlphaFunc(GL30.GL_GREATER, 0.0001f);

        inFrameBuffer.bindFramebufferTexture();
        ShaderUtil.drawQuads();

        mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        bloom.setUniformf("direction", 0.0F, direction);

        outFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(GL30.GL_TEXTURE20);
        inFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        ShaderUtil.drawQuads();

        bloom.detach();
        outFrameBuffer.unbindFramebuffer();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
        mc.getFramebuffer().bindFramebuffer(false);
    }

    private Framebuffer setupBuffer(Framebuffer frameBuffer) {
        if (frameBuffer.framebufferWidth != mc.getMainWindow().getWidth()
                || frameBuffer.framebufferHeight != mc.getMainWindow().getHeight())
            frameBuffer.resize(Math.max(1, mc.getMainWindow().getWidth()), Math.max(1, mc.getMainWindow().getHeight()),
                    false);
        else
            frameBuffer.framebufferClear(false);
        frameBuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);

        return frameBuffer;
    }

    private float calculateGaussianValue(float x, float sigma) {
        double PI = 3.141592653;
        double output = 1.0 / Math.sqrt(2.0 * PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

}
