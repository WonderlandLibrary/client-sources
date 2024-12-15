package com.alan.clients.util.shader.impl;

import com.alan.clients.util.shader.base.RiseShader;
import com.alan.clients.util.shader.base.RiseShaderProgram;
import com.alan.clients.util.shader.base.ShaderRenderType;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class BAWShader extends RiseShader {

    private final RiseShaderProgram program = new RiseShaderProgram("baw.frag", "vertex.vsh");

    private Framebuffer tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

    @Override
    public void run(ShaderRenderType type, float partialTicks, List<Runnable> runnable) {
        // Prevent rendering
        if (!Display.isVisible()) {
            return;
        }

        if (type == ShaderRenderType.OVERLAY) {
            this.update();

            // program ids
            final int programID = this.program.getProgramId();
            ScaledResolution scaledResolution = new ScaledResolution(mc);

            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();

            mc.getFramebuffer().bindFramebuffer(true);
            this.program.start();
            RiseShaderProgram.drawQuad();
            RiseShaderProgram.stop();
        }
    }

    @Override
    public void update() {
        // can be true since this is only called in gui screen
        this.setActive(true);

        if (mc.displayWidth != tempFBO.framebufferWidth || mc.displayHeight != tempFBO.framebufferHeight) {
            tempFBO.deleteFramebuffer();
            tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        } else {
            tempFBO.framebufferClear();
        }
    }
}

