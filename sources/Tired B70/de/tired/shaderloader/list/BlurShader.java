package de.tired.shaderloader.list;

import de.tired.api.util.render.StencilUtil;
import de.tired.module.impl.list.visual.ClickGUI;
import de.tired.module.impl.list.visual.Shader;
import de.tired.shaderloader.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

@ShaderAnnoation(fragName = "blur.frag", renderType = ShaderRenderType.RENDER2D)
public class BlurShader extends ShaderProgram {

    private static Framebuffer framebuffer = new Framebuffer(1, 1, false);

    private final long startTime;

    public BlurShader() {
        startTime = System.currentTimeMillis();
    }


    @Override
    public void doRender() {

        if (!Shader.getInstance().blur.getValue()) return;

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
        ShaderExtension.useShader(getShaderProgramID());
        setUniforms(0, 1);
        MC.getFramebuffer().bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, framebuffer.framebufferTexture);
        KoksFramebuffer.renderTexture();

        ShaderExtension.deleteProgram();

        super.doRender();
    }


    public void startBlur() {
        StencilUtil.initStencilToWrite();
    }

    public void stopBlur() {
        StencilUtil.readStencilBuffer(1);
        doRender();
        StencilUtil.uninitStencilBuffer();
        GlStateManager.enableBlend();

    }

    private void setUniforms(int xAxis, int yAxis) {
        float time = (float) ((System.currentTimeMillis() - this.startTime) / Shader.getInstance().ShaderInBlurSpeed.getValueFloat() * 10.0 / 10.0);
        setShaderUniformI("currentTexture", 0);
        setShaderUniform("texelSize", (float) (1.0 / MC.displayWidth), (float) (1.0 / MC.displayHeight));
        setShaderUniform("coords", xAxis, yAxis);
        setShaderUniform("time", time);
        setShaderUniform("resolution", MC.displayWidth, MC.displayHeight);
        setShaderUniform("blurRadius", Shader.getInstance().blurRadius.getValueInt());
        setShaderUniform("blursigma", Shader.getInstance().blurSigma.getValueInt());
        int state = Shader.getInstance().ShaderInBlur.getValue() ? 1 : 0;
        int state2 = Shader.getInstance().adjustBlurSaturation.getValue() ? 1 : 0;
        setShaderUniformI("shaderInBlur", state);
        setShaderUniformI("adjustSaturation", state2);
    }

    public void onFrameBufferResize() {
        if (framebuffer != null) {
            framebuffer.deleteFramebuffer();
        }
        framebuffer = KoksFramebuffer.doFrameBuffer(framebuffer);
    }

}
