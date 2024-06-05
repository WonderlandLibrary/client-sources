package de.tired.shaderloader.list;

import de.tired.module.impl.list.visual.Shader;
import de.tired.shaderloader.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

@ShaderAnnoation(fragName = "outlineshader.frag", renderType = ShaderRenderType.NONE)
public class OutlineShader extends ShaderProgram {

    private long startTime;

    private static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    public OutlineShader() {
        startTime = System.currentTimeMillis();
    }

    public void startESP() {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        framebuffer = KoksFramebuffer.doFrameBuffer(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        MC.entityRenderer.setupCameraTransform(ShaderPartialTicks.partialTicks, 0);
    }

    public void stopESP() {



        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MC.getFramebuffer().bindFramebuffer(true);

        MC.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        ShaderExtension.useShader(getShaderProgramID());
        setUniforms(1, 0);
        MC.entityRenderer.setupOverlayRendering();
        KoksFramebuffer.renderFRFscreen(framebuffer);
        ShaderExtension.deleteProgram();
        GlStateManager.disableBlend();
        MC.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
        GlStateManager.disableDepth();

    }

    private void setUniforms(int x, int y) {
        float time = (float) ((System.currentTimeMillis() - this.startTime) / Shader.getInstance().ShaderInBlurSpeed.getValueFloat() * 10.0 / 10.0);
        setShaderUniformI("diffuseSamper", 0);
        setShaderUniform("texel", (float) (1.0 / MC.displayWidth), (float) (1.0 / MC.displayHeight));
        setShaderUniform("time", time);
        setShaderUniform("resolution", MC.displayWidth, MC.displayHeight);

    }


}
