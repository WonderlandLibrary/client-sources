package me.dinozoid.strife.shader;

import me.dinozoid.strife.util.MinecraftUtil;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader extends MinecraftUtil {

    protected int pass;

    protected ShaderProgram shaderProgram;
    protected Framebuffer frameBuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);

    protected float width, height;

    public Shader(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public abstract void setUniforms();

    public void render(float width, float height) {
        this.width = width;
        this.height = height;
        if (frameBuffer.framebufferWidth != mc.displayWidth || frameBuffer.framebufferHeight != mc.displayHeight) {
            frameBuffer.deleteFramebuffer();
            frameBuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        }
        frameBuffer.framebufferClear();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getFramebuffer().bindFramebuffer(false);
        doShaderPass(pass, mc.getFramebuffer());
        pass++;
    }

    private void doShaderPass(int pass, Framebuffer framebufferIn) {
        shaderProgram.init();
        setUniforms();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferIn.framebufferTexture);
        shaderProgram.renderCanvas(width, height);
        shaderProgram.uninit();
    }

    public int pass() {
        return pass;
    }
    public void pass(int pass) {
        this.pass = pass;
    }
}
