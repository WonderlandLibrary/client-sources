/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.impl;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.IRenderCall;
import java.util.concurrent.ConcurrentLinkedQueue;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.shader.ShaderUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL30;

public class Outline
implements IMinecraft {
    private static final ConcurrentLinkedQueue<IRenderCall> renderQueue = Queues.newConcurrentLinkedQueue();
    private static final Framebuffer inFrameBuffer = new Framebuffer(1, 1, true, false);
    private static final Framebuffer outFrameBuffer = new Framebuffer(1, 1, true, false);

    public static void registerRenderCall(IRenderCall iRenderCall) {
        renderQueue.add(iRenderCall);
    }

    public static void draw(int n, int n2) {
        if (renderQueue.isEmpty()) {
            return;
        }
        Outline.setupBuffer(inFrameBuffer);
        Outline.setupBuffer(outFrameBuffer);
        inFrameBuffer.bindFramebuffer(false);
        while (!renderQueue.isEmpty()) {
            renderQueue.poll().execute();
        }
        outFrameBuffer.bindFramebuffer(false);
        ShaderUtil.outline.attach();
        ShaderUtil.outline.setUniformf("size", new float[]{n});
        ShaderUtil.outline.setUniform("textureIn", 0);
        ShaderUtil.outline.setUniform("textureToCheck", 20);
        ShaderUtil.outline.setUniformf("texelSize", 1.0f / (float)mc.getMainWindow().getWidth(), 1.0f / (float)mc.getMainWindow().getHeight());
        ShaderUtil.outline.setUniformf("direction", 1.0f, 0.0f);
        float[] fArray = ColorUtils.rgba(n2);
        ShaderUtil.outline.setUniformf("color", fArray[0], fArray[1], fArray[2]);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 770);
        GL30.glAlphaFunc(516, 1.0E-4f);
        inFrameBuffer.bindFramebufferTexture();
        ShaderUtil.drawQuads();
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.blendFunc(770, 771);
        ShaderUtil.outline.setUniformf("direction", 0.0f, 1.0f);
        outFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(34004);
        inFrameBuffer.bindFramebufferTexture();
        GL30.glActiveTexture(33984);
        ShaderUtil.drawQuads();
        ShaderUtil.outline.detach();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    public static Framebuffer setupBuffer(Framebuffer framebuffer) {
        if (framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight()) {
            framebuffer.resize(Math.max(1, mc.getMainWindow().getWidth()), Math.max(1, mc.getMainWindow().getHeight()), true);
        } else {
            framebuffer.framebufferClear(true);
        }
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        return framebuffer;
    }
}

