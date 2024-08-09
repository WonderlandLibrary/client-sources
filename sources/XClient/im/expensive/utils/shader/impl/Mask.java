package im.expensive.utils.shader.impl;

import static com.mojang.blaze3d.platform.GlStateManager.disableBlend;
import static com.mojang.blaze3d.platform.GlStateManager.enableBlend;
import static com.mojang.blaze3d.platform.GlStateManager.popMatrix;
import static com.mojang.blaze3d.platform.GlStateManager.pushMatrix;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.platform.GlStateManager;

import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.shader.ShaderUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.client.shader.Framebuffer;

@UtilityClass
public class Mask implements IMinecraft {

    private final Framebuffer in = new Framebuffer(1, 1, true, false);
    private final Framebuffer out = new Framebuffer(1, 1, true, false);

    public void renderMask(float x, float y, float width, float height, Runnable mask) {

        setupBuffer(in);
        setupBuffer(out);

        in.bindFramebuffer(true);

        mask.run();

        out.bindFramebuffer(true);

        ShaderUtil.mask.attach();
        
        ShaderUtil.mask.setUniformf("location", (float) (x * mc.getMainWindow().getGuiScaleFactor()),
                (float) ((mc.getMainWindow().getHeight() - (height * mc.getMainWindow().getGuiScaleFactor()))
                        - (y * mc.getMainWindow().getGuiScaleFactor())));
        ShaderUtil.mask.setUniformf("rectSize", width * mc.getMainWindow().getGuiScaleFactor(),
                height * mc.getMainWindow().getGuiScaleFactor());
       

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL30.GL_ONE, GL30.GL_SRC_ALPHA);
        GL30.glAlphaFunc(GL30.GL_GREATER, 0.0001f);

        in.bindFramebufferTexture();
        ShaderUtil.drawQuads();

        mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.blendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        out.bindFramebufferTexture();
        GL30.glActiveTexture(GL30.GL_TEXTURE20);
        in.bindFramebufferTexture();
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        ShaderUtil.drawQuads();
        ShaderUtil.mask.detach();
        GlStateManager.bindTexture(0);
        GlStateManager.disableBlend();
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

}
