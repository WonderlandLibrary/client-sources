// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import intent.AquaDev.aqua.utils.shader.ShaderStencilUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import events.listeners.EventPostRender2D;
import events.Event;
import events.listeners.EventBlur;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import org.lwjgl.opengl.Display;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.shader.ShaderProgram;
import net.minecraft.client.shader.Framebuffer;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.modules.Module;

public class Blur extends Module
{
    Minecraft MC;
    ByteBuffer pixelBuf;
    private final Framebuffer fboA;
    private final Framebuffer fboB;
    private final Framebuffer maskBuffer;
    private ShaderProgram blurProgram;
    private ShaderProgram blurProgram2;
    
    public Blur() {
        super("Blur", Type.Visual, "Blur", 0, Category.Visual);
        this.MC = Minecraft.getMinecraft();
        this.pixelBuf = ByteBuffer.allocateDirect(Display.getWidth() * Display.getHeight() * 3);
        this.fboA = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, false);
        this.fboB = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, false);
        this.maskBuffer = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, true);
        this.blurProgram = new ShaderProgram("vertex.vert", "blur.glsl");
        this.blurProgram2 = new ShaderProgram("vertex.vert", "alphaBlur.glsl");
        Aqua.setmgr.register(new Setting("Sigma", this, 6.0, 4.0, 20.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Gaussian", new String[] { "Gaussian", "GaussianCleaner" }));
    }
    
    public static void drawBlurred(final Runnable runnable, final boolean renderTwice) {
        final EventBlur event = new EventBlur(runnable);
        Aqua.INSTANCE.onEvent(event);
        if ((!event.isCancelled() || renderTwice) && Display.isActive()) {
            runnable.run();
        }
    }
    
    @Override
    public void onEnable() {
        if (Aqua.setmgr.getSetting("BlurMode").getCurrentMode().equalsIgnoreCase("Gaussian")) {
            this.blurProgram = new ShaderProgram("vertex.vert", "blur.glsl");
        }
        if (Aqua.setmgr.getSetting("BlurMode").getCurrentMode().equalsIgnoreCase("GaussianCleaner")) {
            this.blurProgram2 = new ShaderProgram("vertex.vert", "alphaBlur.glsl");
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPostRender2D) {
            this.onRender2D();
        }
        else if (event instanceof EventBlur) {
            this.onBlurredEvent((EventBlur)event);
        }
    }
    
    public void onBlurredEvent(final EventBlur event) {
        event.setCancelled(true);
        this.maskBuffer.bindFramebuffer(false);
        event.getBlurredFunction().run();
        Blur.mc.getFramebuffer().bindFramebuffer(false);
    }
    
    public void checkSetup() {
        this.fboA.checkSetup(Blur.mc.displayWidth, Blur.mc.displayHeight);
        this.fboB.checkSetup(Blur.mc.displayWidth, Blur.mc.displayHeight);
        this.maskBuffer.checkSetup(Blur.mc.displayWidth, Blur.mc.displayHeight);
    }
    
    public void onRender2D() {
        this.checkSetup();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.0f, -1.0f);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.0f);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        final ScaledResolution sr = new ScaledResolution(Blur.mc);
        final float width = (float)sr.getScaledWidth_double();
        final float height = (float)sr.getScaledHeight_double();
        if (Aqua.setmgr.getSetting("BlurMode").getCurrentMode().equalsIgnoreCase("Gaussian")) {
            this.blurProgram.init();
        }
        else {
            this.blurProgram2.init();
        }
        this.blurProgram.init();
        this.setupBlurUniforms();
        this.doBlurPass(0, Blur.mc.getFramebuffer().framebufferTexture, this.fboA, (int)width, (int)height);
        this.doBlurPass(1, this.fboA.framebufferTexture, this.fboB, (int)width, (int)height);
        if (Aqua.setmgr.getSetting("BlurMode").getCurrentMode().equalsIgnoreCase("Gaussian")) {
            this.blurProgram.uninit();
        }
        else {
            this.blurProgram2.uninit();
        }
        ShaderStencilUtil.initStencil();
        ShaderStencilUtil.bindWriteStencilBuffer();
        this.drawTexturedQuad(this.maskBuffer.framebufferTexture, width, height);
        ShaderStencilUtil.bindReadStencilBuffer(1);
        this.drawTexturedQuad(this.fboB.framebufferTexture, width, height);
        ShaderStencilUtil.uninitStencilBuffer();
        Blur.mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.bindTexture(0);
        GlStateManager.alphaFunc(516, 0.2f);
        GlStateManager.disableAlpha();
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        this.maskBuffer.framebufferClear();
        Blur.mc.getFramebuffer().bindFramebuffer(false);
    }
    
    public void doBlurPass(final int pass, final int texture, final Framebuffer out, final int width, final int height) {
        out.framebufferClear();
        out.bindFramebuffer(false);
        GL20.glUniform2f(this.blurProgram.uniform("direction"), (float)(1 - pass), (float)pass);
        GL11.glBindTexture(3553, texture);
        if (Aqua.setmgr.getSetting("BlurMode").getCurrentMode().equalsIgnoreCase("Gaussian")) {
            this.blurProgram.doRenderPass((float)width, (float)height);
        }
        else {
            this.blurProgram2.doRenderPass((float)width, (float)height);
        }
    }
    
    public void setupBlurUniforms() {
        if (Aqua.setmgr.getSetting("BlurMode").getCurrentMode().equalsIgnoreCase("Gaussian")) {
            GL20.glUniform2f(this.blurProgram.uniform("texelSize"), 1.0f / this.MC.displayWidth, 1.0f / this.MC.displayHeight);
            GL20.glUniform1i(this.blurProgram.uniform("texture"), 0);
            final float sigma = (float)Aqua.setmgr.getSetting("BlurSigma").getCurrentNumber();
            GL20.glUniform1f(this.blurProgram.uniform("sigma"), sigma);
        }
        else {
            GL20.glUniform2f(this.blurProgram2.uniform("texelSize"), 1.0f / this.MC.displayWidth, 1.0f / this.MC.displayHeight);
            GL20.glUniform1i(this.blurProgram2.uniform("texture"), 0);
        }
    }
    
    private void drawTexturedQuad(final int texture, final double width, final double height) {
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
