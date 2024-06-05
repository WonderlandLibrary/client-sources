package client.metaware.api.shader;

import client.metaware.api.utils.MinecraftUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.shader.Framebuffer;

import static org.lwjgl.opengl.GL11.*;

public abstract class Shader implements MinecraftUtil {

    protected int pass;

    protected ShaderProgram shaderProgram;
    protected Framebuffer frameBuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);

    protected float width, height;

    public Shader(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public abstract void setUniforms();

    public void render(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        if (frameBuffer.framebufferWidth != mc.displayWidth || frameBuffer.framebufferHeight != mc.displayHeight) {
            frameBuffer.deleteFramebuffer();
            frameBuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        }
        frameBuffer.framebufferClear();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        mc.getFramebuffer().bindFramebuffer(false);
        doShaderPass(x, y, mc.getFramebuffer());
        pass++;
    }

    private void doShaderPass(float x, float y, Framebuffer framebufferIn) {
        shaderProgram.init();
        setUniforms();
        glBindTexture(GL_TEXTURE_2D, framebufferIn.framebufferTexture);
        shaderProgram.renderCanvas();
        shaderProgram.uninit();
    }

    public int pass() {
        return pass;
    }

    public void pass(int pass) {
        this.pass = pass;
    }
}
