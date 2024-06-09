// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import intent.AquaDev.aqua.utils.shader.ShaderStencilUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import events.listeners.EventPostRender2D;
import events.Event;
import events.listeners.EventGlow;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.shader.ShaderProgram;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.modules.Module;

public class Shadow extends Module
{
    Minecraft MC;
    private final Framebuffer pass;
    private final Framebuffer output;
    private final Framebuffer input;
    private ShaderProgram blurProgram;
    
    public Shadow() {
        super("Shadow", Type.Visual, "Shadow", 0, Category.Visual);
        this.MC = Minecraft.getMinecraft();
        this.pass = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, false);
        this.output = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, false);
        this.input = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, true);
        this.blurProgram = new ShaderProgram("vertex.vert", "alphaBlur.glsl");
        Aqua.setmgr.register(new Setting("Sigma", this, 6.0, 6.0, 20.0, false));
    }
    
    public static void drawGlow(final Runnable runnable, final boolean renderTwice) {
        final EventGlow event = new EventGlow(runnable);
        Aqua.INSTANCE.onEvent(event);
        if (!event.isCancelled() || renderTwice) {
            runnable.run();
        }
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPostRender2D) {
            drawGlow(() -> Gui.drawRect(-2001, -2001, -2000, -2000, Color.red.getRGB()), false);
            this.checkSetup();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            final ScaledResolution sr = new ScaledResolution(this.MC);
            final double screenWidth = sr.getScaledWidth_double();
            final double screenHeight = sr.getScaledHeight_double();
            this.blurProgram.init();
            this.setupBlurUniforms();
            this.doBlurPass(0, this.input.framebufferTexture, this.pass, (int)screenWidth, (int)screenHeight);
            this.doBlurPass(1, this.pass.framebufferTexture, this.output, (int)screenWidth, (int)screenHeight);
            this.blurProgram.uninit();
            ShaderStencilUtil.initStencil();
            ShaderStencilUtil.bindWriteStencilBuffer();
            this.drawTexturedQuad(this.input.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.bindReadStencilBuffer(0);
            this.drawTexturedQuad(this.output.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.uninitStencilBuffer();
            GlStateManager.bindTexture(0);
            GlStateManager.alphaFunc(516, 0.2f);
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            this.input.framebufferClear();
            this.MC.getFramebuffer().bindFramebuffer(false);
        }
        else if (event instanceof EventGlow) {
            this.onGlowEvent((EventGlow)event);
        }
    }
    
    public void onGlowEvent(final EventGlow event) {
        event.setCancelled(true);
        this.input.bindFramebuffer(false);
        event.getRunnable().run();
        this.MC.getFramebuffer().bindFramebuffer(false);
    }
    
    @Override
    public void onEnable() {
        this.blurProgram = new ShaderProgram("vertex.vert", "alphaBlur.glsl");
        super.onEnable();
    }
    
    public void checkSetup() {
        this.input.checkSetup(this.MC.displayWidth, this.MC.displayHeight);
        this.pass.checkSetup(this.MC.displayWidth, this.MC.displayHeight);
        this.output.checkSetup(this.MC.displayWidth, this.MC.displayHeight);
    }
    
    public void doBlurPass(final int pass, final int texture, final Framebuffer out, final int width, final int height) {
        out.framebufferClear();
        out.bindFramebuffer(false);
        GL20.glUniform2f(this.blurProgram.uniform("direction"), (float)(1 - pass), (float)pass);
        GL11.glBindTexture(3553, texture);
        this.blurProgram.doRenderPass((float)width, (float)height);
    }
    
    public void setupBlurUniforms() {
        GL20.glUniform2f(this.blurProgram.uniform("texelSize"), 1.0f / this.MC.displayWidth, 1.0f / this.MC.displayHeight);
        GL20.glUniform1i(this.blurProgram.uniform("texture"), 0);
        final float strength = 1.0f;
        GL20.glUniform1f(this.blurProgram.uniform("strength"), 1.0f);
    }
    
    private void drawTexturedQuad(final int texture, final double width, final double height) {
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
}
