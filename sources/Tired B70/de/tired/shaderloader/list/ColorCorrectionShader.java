package de.tired.shaderloader.list;

import de.tired.module.impl.list.visual.Shader;
import de.tired.shaderloader.*;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

@ShaderAnnoation(fragName = "colorcorrection.frag", renderType = ShaderRenderType.NONE)
public class ColorCorrectionShader extends ShaderProgram {

    private static Framebuffer framebuffer = new Framebuffer(1, 1, false);
    private static Framebuffer framebufferRender = new Framebuffer(1, 1, false);


    @Override
    public void doRender() {
        framebuffer = KoksFramebuffer.doFrameBuffer(framebuffer);

        ShaderExtension.useShader(getShaderProgramID());

        //first pass
        setUniforms(1, 0);
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, MC.getFramebuffer().framebufferTexture);
        KoksFramebuffer.renderTexture();
        framebuffer.unbindFramebuffer();

        //second pass

        setUniforms(0, 1);
        MC.getFramebuffer().bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, framebuffer.framebufferTexture);
        KoksFramebuffer.renderTexture();
        ShaderExtension.deleteProgram();

        super.doRender();
    }

    private void setUniforms(int x, int y) {
        setShaderUniformI("texture", 0);
        setShaderUniform("gamma", Shader.getInstance().ExposureGamma.getValueFloat());
        setShaderUniform("yValueDown", Shader.getInstance().ExposureYValueDown.getValueFloat());
        setShaderUniform("yValueUP", Shader.getInstance().ExposureYValue.getValueFloat());


    }


}