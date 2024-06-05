package client.metaware.api.shader.implementations;

import client.metaware.api.shader.Shader;
import client.metaware.api.shader.ShaderProgram;
import client.metaware.api.utils.MinecraftUtil;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.util.StencilUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class BlurShader implements MinecraftUtil {

    private ShaderProgram blurShader = new ShaderProgram("fragment/blur.frag");

    private static Framebuffer blurBuffer = new Framebuffer(1, 1, false);

    private float radius;

    public BlurShader(float radius) {
        this.radius = radius;
    }

    public void blur() {
        blur(0, 0, -1, -1);
    }

    public void blur(float x, float y, float width, float height) {

        StencilUtil.checkSetupFBO(Minecraft.getMinecraft().getFramebuffer());

        blurBuffer = RenderUtil.createFramebuffer(blurBuffer);

        // horizontal blur
        blurShader.init();
        setupUniforms(1, 0);
        blurBuffer.framebufferClear();
        blurBuffer.bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, mc.getFramebuffer().framebufferTexture);
        if(width == -1 && height == -1)
            blurShader.renderCanvas();
        else blurShader.renderCanvas(x, y, width, height);
        blurBuffer.unbindFramebuffer();


        // vertical blur
        blurShader.init();
        setupUniforms(0, 1);
        mc.getFramebuffer().bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, blurBuffer.framebufferTexture);
        if(width == -1 && height == -1)
            blurShader.renderCanvas();
        else blurShader.renderCanvas(x, y, width, height);
        blurShader.uninit();
    }

    public void setupUniforms(float x, float y) {
        blurShader.setUniformi("originalTexture", 0);
        blurShader.setUniformf("texelSize", (float) (1.0 / mc.displayWidth), (float) (1.0 / mc.displayHeight));
        blurShader.setUniformf("direction", x, y);
        blurShader.setUniformf("radius", radius);
    }

    public float radius() {
        return radius;
    }

    public void radius(float radius) {
        this.radius = radius;
    }
}

