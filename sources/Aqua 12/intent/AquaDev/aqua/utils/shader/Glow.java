// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils.shader;

import events.listeners.EventGlowESP;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.Aqua;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.Minecraft;

public class Glow
{
    static Minecraft MC;
    public static Framebuffer pass;
    public static Framebuffer output;
    public static Framebuffer input;
    public static ShaderProgram blurProgram;
    
    public static void checkSetup() {
        Glow.input.checkSetup(Glow.MC.displayWidth, Glow.MC.displayHeight);
        Glow.pass.checkSetup(Glow.MC.displayWidth, Glow.MC.displayHeight);
        Glow.output.checkSetup(Glow.MC.displayWidth, Glow.MC.displayHeight);
    }
    
    public static void doBlurPass(final int pass, final int texture, final Framebuffer out, final int width, final int height) {
        out.framebufferClear();
        out.bindFramebuffer(false);
        GL20.glUniform2f(Glow.blurProgram.uniform("direction"), (float)(1 - pass), (float)pass);
        GL11.glBindTexture(3553, texture);
        Glow.blurProgram.doRenderPass((float)width, (float)height);
    }
    
    public static void setupBlurUniforms() {
        GL20.glUniform2f(Glow.blurProgram.uniform("texelSize"), 1.0f / Glow.MC.displayWidth, 1.0f / Glow.MC.displayHeight);
        GL20.glUniform1i(Glow.blurProgram.uniform("texture"), 0);
        final float sigma1 = (float)Aqua.setmgr.getSetting("ShaderMultiplierSigma").getCurrentNumber();
        final float strength = (float)Aqua.setmgr.getSetting("ShaderMultiplierMultiplier").getCurrentNumber();
        GL20.glUniform1f(Glow.blurProgram.uniform("sigmaESP"), sigma1);
        GL20.glUniform1f(Glow.blurProgram.uniform("strength"), strength);
    }
    
    public void drawTexturedQuad(final int texture, final double width, final double height) {
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
    
    public static void onGlowEvent(final EventGlowESP event) {
        event.setCancelled(true);
        Glow.input.bindFramebuffer(false);
        event.getRunnable().run();
        Glow.MC.getFramebuffer().bindFramebuffer(false);
    }
    
    static {
        Glow.MC = Minecraft.getMinecraft();
        Glow.pass = new Framebuffer(Glow.MC.displayWidth, Glow.MC.displayHeight, false);
        Glow.output = new Framebuffer(Glow.MC.displayWidth, Glow.MC.displayHeight, false);
        Glow.input = new Framebuffer(Glow.MC.displayWidth, Glow.MC.displayHeight, true);
        Glow.blurProgram = new ShaderProgram("vertex.vert", "alphaBlurESP.glsl");
    }
}
