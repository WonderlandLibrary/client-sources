package de.tired.shaderloader.list;

import de.tired.api.extension.Extension;
import de.tired.module.impl.list.visual.ClickGUI;
import de.tired.shaderloader.*;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

@ShaderAnnoation(fragName = "arraylistshader.glsl", renderType = ShaderRenderType.RENDER2D)
public class GradientShader extends ShaderProgram {

    private Framebuffer frameBuffer = new Framebuffer(1, 1, true);

    private long startTime;

    public GradientShader() {
        startTime = System.currentTimeMillis();
    }

    public void renderShader() {
        frameBuffer = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.createFramebuffer(frameBuffer, false);
        frameBuffer.framebufferClear();
        frameBuffer.bindFramebuffer(true);

    }

    public void stopRender() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MC.getFramebuffer().bindFramebuffer(true);


        MC.entityRenderer.setupOverlayRendering();
        MC.entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        ShaderExtension.useShader(getShaderProgramID());
        setUniforms();
        glBindTexture(GL_TEXTURE_2D, frameBuffer.framebufferTexture);
        KoksFramebuffer.renderTexture();

        ShaderExtension.deleteProgram();
    }


    private void setUniforms() {
        float time = (System.currentTimeMillis() - this.startTime) / 2200f;
        setShaderUniform("resolution", MC.displayWidth, MC.displayHeight);
        setShaderUniform("time", time);
    }

}
