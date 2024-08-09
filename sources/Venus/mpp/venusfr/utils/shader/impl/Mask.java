/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.shader.ShaderUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL30;

public final class Mask
implements IMinecraft {
    private static final Framebuffer in = new Framebuffer(1, 1, true, false);
    private static final Framebuffer out = new Framebuffer(1, 1, true, false);

    public static void renderMask(float f, float f2, float f3, float f4, Runnable runnable) {
        Mask.setupBuffer(in);
        Mask.setupBuffer(out);
        in.bindFramebuffer(false);
        runnable.run();
        out.bindFramebuffer(false);
        ShaderUtil.mask.attach();
        ShaderUtil.mask.setUniformf("location", (float)((double)f * mc.getMainWindow().getGuiScaleFactor()), (float)((double)mc.getMainWindow().getHeight() - (double)f4 * mc.getMainWindow().getGuiScaleFactor() - (double)f2 * mc.getMainWindow().getGuiScaleFactor()));
        ShaderUtil.mask.setUniformf("rectSize", (double)f3 * mc.getMainWindow().getGuiScaleFactor(), (double)f4 * mc.getMainWindow().getGuiScaleFactor());
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 770);
        GL30.glAlphaFunc(516, 1.0E-4f);
        in.bindFramebufferTexture();
        ShaderUtil.drawQuads();
        mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.blendFunc(770, 771);
        out.bindFramebufferTexture();
        GL30.glActiveTexture(34004);
        in.bindFramebufferTexture();
        GL30.glActiveTexture(33984);
        ShaderUtil.drawQuads();
        ShaderUtil.mask.detach();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
    }

    private static Framebuffer setupBuffer(Framebuffer framebuffer) {
        if (framebuffer.framebufferWidth != mc.getMainWindow().getWidth() || framebuffer.framebufferHeight != mc.getMainWindow().getHeight()) {
            framebuffer.resize(Math.max(1, mc.getMainWindow().getWidth()), Math.max(1, mc.getMainWindow().getHeight()), true);
        } else {
            framebuffer.framebufferClear(true);
        }
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        return framebuffer;
    }

    private Mask() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

