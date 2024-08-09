/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.IRenderCall;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.FloatBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.shader.ShaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

public class GaussianBloom
implements IMinecraft {
    public static GaussianBloom INGAME = new GaussianBloom();
    public static GaussianBloom GUI = new GaussianBloom();
    private final ShaderUtil bloom = new ShaderUtil("bloom");
    private final ConcurrentLinkedQueue<IRenderCall> renderQueue = Queues.newConcurrentLinkedQueue();
    private final Framebuffer inFrameBuffer = new Framebuffer(1, 1, true, false);
    private final Framebuffer outFrameBuffer = new Framebuffer(1, 1, true, false);

    public void registerRenderCall(IRenderCall iRenderCall) {
        this.renderQueue.add(iRenderCall);
    }

    public void draw(int n, float f, boolean bl, float f2) {
        if (this.renderQueue.isEmpty()) {
            return;
        }
        this.setupBuffer(this.inFrameBuffer);
        this.setupBuffer(this.outFrameBuffer);
        this.inFrameBuffer.bindFramebuffer(false);
        while (!this.renderQueue.isEmpty()) {
            this.renderQueue.poll().execute();
        }
        this.inFrameBuffer.unbindFramebuffer();
        this.outFrameBuffer.bindFramebuffer(false);
        this.bloom.attach();
        this.bloom.setUniformf("radius", new float[]{n});
        this.bloom.setUniformf("exposure", f);
        this.bloom.setUniform("textureIn", 0);
        this.bloom.setUniform("textureToCheck", 20);
        this.bloom.setUniform("avoidTexture", bl ? 1 : 0);
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(128);
        for (int i = 0; i <= n; ++i) {
            floatBuffer.put(this.calculateGaussianValue(i, n / 2));
        }
        floatBuffer.rewind();
        RenderSystem.glUniform1(this.bloom.getUniform("weights"), floatBuffer);
        this.bloom.setUniformf("texelSize", 1.0f / (float)Minecraft.getInstance().getMainWindow().getWidth(), 1.0f / (float)Minecraft.getInstance().getMainWindow().getHeight());
        this.bloom.setUniformf("direction", f2, 0.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 770);
        GL30.glAlphaFunc(516, 1.0E-4f);
        this.inFrameBuffer.bindFramebufferTexture();
        ShaderUtil.drawQuads();
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.blendFunc(770, 771);
        this.bloom.setUniformf("direction", 0.0f, f2);
        this.outFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(34004);
        this.inFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(33984);
        ShaderUtil.drawQuads();
        this.bloom.detach();
        this.outFrameBuffer.unbindFramebuffer();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
        mc.getFramebuffer().bindFramebuffer(true);
    }

    private Framebuffer setupBuffer(Framebuffer framebuffer) {
        if (framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight()) {
            framebuffer.resize(Math.max(1, mc.getMainWindow().getWidth()), Math.max(1, mc.getMainWindow().getHeight()), true);
        } else {
            framebuffer.framebufferClear(true);
        }
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        return framebuffer;
    }

    private float calculateGaussianValue(float f, float f2) {
        double d = 3.141592653;
        double d2 = 1.0 / Math.sqrt(2.0 * d * (double)(f2 * f2));
        return (float)(d2 * Math.exp((double)(-(f * f)) / (2.0 * (double)(f2 * f2))));
    }
}

