package ru.FecuritySQ.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.GL11.*;

public class GrayShader {

    public static ShaderUtil blurShader = new ShaderUtil("gray");

    public static Framebuffer framebuffer = new Framebuffer(1, 1, false, false);


    public static void setupUniforms() {
        blurShader.setUniformi("textureIn", 0);
    }


    public static void renderGray() {
        GlStateManager.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.blendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);


        framebuffer = ShaderUtil.createFrameBuffer(framebuffer);

        framebuffer.framebufferClear(false);
        framebuffer.bindFramebuffer(true);
        blurShader.init();
        setupUniforms();

        ShaderUtil.bindTexture(Minecraft.getInstance().getFramebuffer().framebufferTexture);

        ShaderUtil.drawQuads();
        framebuffer.unbindFramebuffer();
        blurShader.unload();

        Minecraft.getInstance().getFramebuffer().bindFramebuffer(true);
        blurShader.init();
        setupUniforms();

        ShaderUtil.bindTexture(framebuffer.framebufferTexture);
        ShaderUtil.drawQuads();
        blurShader.unload();
        GlStateManager.bindTexture(0);
    }

}
