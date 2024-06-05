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
public class BlurShader2 extends ShaderProgram {

    private static Framebuffer framebuffer = new Framebuffer(1, 1, false);

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
        MC.entityRenderer.setupOverlayRendering();
    }

    private void setUniforms(int xAxis, int yAxis) {
        setShaderUniformI("currentTexture", 0);
        setShaderUniform("texelSize", (float) (1.0 / MC.displayWidth), (float) (1.0 / MC.displayHeight));
        setShaderUniform("coords", xAxis, yAxis);
        setShaderUniform("blurRadius", 1);
        setShaderUniform("blursigma", 4);

        setShaderUniform("rectcolor", 0, 0, 0);
    }

    public void onFrameBufferResize() {
        if (framebuffer != null) {
            framebuffer.deleteFramebuffer();
        }
        framebuffer = KoksFramebuffer.doFrameBuffer(framebuffer);
    }

}
