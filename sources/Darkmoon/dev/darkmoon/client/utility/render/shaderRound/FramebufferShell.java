package dev.darkmoon.client.utility.render.shaderRound;

import dev.darkmoon.client.utility.render.ColorUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;

public class FramebufferShell {
    private static Framebuffer blurFramebuffer, alphaFramebuffer, scrollFramebuffer;
    public static boolean needBlur;

    public static void setupScrollFramebuffer() {
        Minecraft mc = Minecraft.getMinecraft();
        if (scrollFramebuffer == null)
            scrollFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        if (mc.displayWidth != scrollFramebuffer.framebufferWidth
                || mc.displayHeight != scrollFramebuffer.framebufferHeight)
            scrollFramebuffer.createBindFramebuffer(mc.displayWidth, mc.displayHeight);
        scrollFramebuffer.bindFramebuffer(false);
    }

    public static void setupAlphaGuiFramebuffer() {
        Minecraft mc = Minecraft.getMinecraft();
        if (alphaFramebuffer == null)
            alphaFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        if (mc.displayWidth != alphaFramebuffer.framebufferWidth
                || mc.displayHeight != alphaFramebuffer.framebufferHeight)
            alphaFramebuffer.createBindFramebuffer(mc.displayWidth, mc.displayHeight);
        alphaFramebuffer.bindFramebuffer(false);
    }


    public static void blur(Runnable runnable) {
        if (!Display.isActive()) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (blurFramebuffer == null)
            blurFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        if (mc.displayWidth != blurFramebuffer.framebufferWidth
                || mc.displayHeight != blurFramebuffer.framebufferHeight)
            blurFramebuffer.createBindFramebuffer(mc.displayWidth, mc.displayHeight);
        blurFramebuffer.bindFramebuffer(false);
        runnable.run();
        mc.getFramebuffer().bindFramebuffer(false);
        ShaderShell.BLUR_SHADER.attach();
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);
        ShaderShell.BLUR_SHADER.setupUniforms(5);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        blurFramebuffer.framebufferRenderExt(mc.displayWidth, mc.displayHeight, false);
        ShaderShell.BLUR_SHADER.detach();
        blurFramebuffer.framebufferClear();
        GL11.glEnable(GL11.GL_BLEND);
        mc.getFramebuffer().bindFramebuffer(false);
        mc.entityRenderer.setupOverlayRendering(2);
        runnable.run();
    }
}
