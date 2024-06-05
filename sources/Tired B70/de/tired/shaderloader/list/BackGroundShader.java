package de.tired.shaderloader.list;

import de.tired.shaderloader.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

@ShaderAnnoation(fragName = "test.glsl", renderType = ShaderRenderType.RENDER2D)
public class BackGroundShader extends ShaderProgram {

    private static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    private final long startTime;

    public BackGroundShader() {
        startTime = System.currentTimeMillis();
    }
    @Override
    public void doRender() {
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        framebuffer = KoksFramebuffer.doFrameBuffer(framebuffer);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MC.getFramebuffer().bindFramebuffer(true);

        MC.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        ShaderExtension.useShader(getShaderProgramID());
        setUniforms();
        MC.entityRenderer.setupOverlayRendering();
        KoksFramebuffer.renderFRFscreen(framebuffer);
        ShaderExtension.deleteProgram();
        GlStateManager.disableBlend();
        MC.entityRenderer.disableLightmap();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
        GlStateManager.disableDepth();
        super.doRender();
    }

    private void setUniforms() {
        float time = (System.currentTimeMillis() - this.startTime) / 2200f;
        setShaderUniform("resolution", MC.displayWidth, MC.displayHeight);
        setShaderUniform("time", time);
    }


}
