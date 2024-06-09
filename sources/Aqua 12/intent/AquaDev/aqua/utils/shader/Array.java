// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils.shader;

import events.listeners.EventGlowArray;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.Aqua;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.Minecraft;

public class Array
{
    public static Array INSTANCE;
    static Minecraft MC;
    public static Framebuffer pass;
    public static Framebuffer output;
    public static Framebuffer input;
    public static ShaderProgram blurProgram;
    
    public static void checkSetup() {
        Array.input.checkSetup(Array.MC.displayWidth, Array.MC.displayHeight);
        Array.pass.checkSetup(Array.MC.displayWidth, Array.MC.displayHeight);
        Array.output.checkSetup(Array.MC.displayWidth, Array.MC.displayHeight);
    }
    
    public static void doBlurPass(final int pass, final int texture, final Framebuffer out, final int width, final int height) {
        out.framebufferClear();
        out.bindFramebuffer(false);
        GL20.glUniform2f(Array.blurProgram.uniform("direction"), (float)(1 - pass), (float)pass);
        GL11.glBindTexture(3553, texture);
        Array.blurProgram.doRenderPass((float)width, (float)height);
    }
    
    public static void setupBlurUniforms() {
        GL20.glUniform2f(Array.blurProgram.uniform("texelSize"), 1.0f / Array.MC.displayWidth, 1.0f / Array.MC.displayHeight);
        GL20.glUniform1i(Array.blurProgram.uniform("texture"), 0);
        final float sigma1 = (float)Aqua.setmgr.getSetting("ArraylistSigma").getCurrentNumber();
        final float strength = (float)Aqua.setmgr.getSetting("ArraylistMultiplier").getCurrentNumber();
        GL20.glUniform1f(Array.blurProgram.uniform("sigmaArray"), sigma1);
        GL20.glUniform1f(Array.blurProgram.uniform("strength"), strength);
    }
    
    public void drawTexturedQuad(final int texture, final double width, final double height) {
        Array.INSTANCE = this;
        GlStateManager.enableBlend();
        GL11.glBindTexture(3553, texture);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, height);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d(width, height);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d(width, 0.0);
        GL11.glEnd();
    }
    
    public static void onArray(final EventGlowArray event) {
        event.setCancelled(true);
        Array.input.bindFramebuffer(false);
        event.getRunnable().run();
        Array.MC.getFramebuffer().bindFramebuffer(false);
    }
    
    static {
        Array.MC = Minecraft.getMinecraft();
        Array.pass = new Framebuffer(Array.MC.displayWidth, Array.MC.displayHeight, false);
        Array.output = new Framebuffer(Array.MC.displayWidth, Array.MC.displayHeight, false);
        Array.input = new Framebuffer(Array.MC.displayWidth, Array.MC.displayHeight, true);
        Array.blurProgram = new ShaderProgram("vertex.vert", "alphaBlurArray.glsl");
    }
}
