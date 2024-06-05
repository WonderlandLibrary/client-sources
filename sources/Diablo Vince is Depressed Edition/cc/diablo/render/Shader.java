/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package cc.diablo.render;

import cc.diablo.render.ShaderProgram;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

public abstract class Shader {
    protected int pass;
    protected Minecraft mc = Minecraft.getMinecraft();
    protected ShaderProgram shaderProgram;
    protected Framebuffer frameBuffer;
    protected float width;
    protected float height;

    public Shader(ShaderProgram shaderProgram) {
        this.frameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, false);
        this.shaderProgram = shaderProgram;
    }

    public abstract void setUniforms();

    public void render(float width, float height) {
        this.width = width;
        this.height = height;
        if (this.frameBuffer.framebufferWidth != this.mc.displayWidth || this.frameBuffer.framebufferHeight != this.mc.displayHeight) {
            this.frameBuffer.deleteFramebuffer();
            this.frameBuffer = new Framebuffer(this.mc.displayWidth, this.mc.displayHeight, false);
        }
        this.frameBuffer.framebufferClear();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        this.mc.getFramebuffer().bindFramebuffer(false);
        this.doShaderPass(this.pass, this.mc.getFramebuffer());
        ++this.pass;
    }

    private void doShaderPass(int pass, Framebuffer framebufferIn) {
        this.shaderProgram.init();
        this.setUniforms();
        GL11.glBindTexture((int)3553, (int)framebufferIn.framebufferTexture);
        this.shaderProgram.renderCanvas(this.width, this.height);
        this.shaderProgram.uninit();
    }

    public int pass() {
        return this.pass;
    }

    public void pass(int pass) {
        this.pass = pass;
    }
}

