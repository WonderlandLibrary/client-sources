// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import org.lwjgl.opengl.GL11;
import events.listeners.EventGlowESP;
import java.util.List;
import java.util.Iterator;
import intent.AquaDev.aqua.utils.shader.ShaderStencilUtil;
import java.awt.Color;
import org.lwjgl.opengl.GL20;
import net.minecraft.client.gui.ScaledResolution;
import events.listeners.EventPostRender2D;
import events.listeners.EventRender2D;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import events.listeners.EventRender3D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.shader.ShaderProgram;
import net.minecraft.client.shader.Framebuffer;
import intent.AquaDev.aqua.modules.Module;

public class ShaderESP extends Module
{
    private final Framebuffer input;
    private final Framebuffer pass;
    private final Framebuffer output;
    private ShaderProgram outlineProgram;
    
    public ShaderESP() {
        super("ShaderESP", Type.Visual, "ShaderESP", 0, Category.Visual);
        this.input = new Framebuffer(ShaderESP.mc.displayWidth, ShaderESP.mc.displayHeight, true);
        this.pass = new Framebuffer(ShaderESP.mc.displayWidth, ShaderESP.mc.displayHeight, true);
        this.output = new Framebuffer(ShaderESP.mc.displayWidth, ShaderESP.mc.displayHeight, true);
        this.outlineProgram = new ShaderProgram("vertex.vert", "outline.glsl");
        Aqua.setmgr.register(new Setting("OutlineWidth", this, 1.0, 0.0, 20.0, false));
        Aqua.setmgr.register(new Setting("ShaderAlpha", this, 255.0, 0.0, 255.0, false));
        Aqua.setmgr.register(new Setting("Players", this, true));
        Aqua.setmgr.register(new Setting("Animals", this, true));
        Aqua.setmgr.register(new Setting("Chests", this, true));
        Aqua.setmgr.register(new Setting("Items", this, true));
        Aqua.setmgr.register(new Setting("Glow", this, false));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        this.outlineProgram = new ShaderProgram("vertex.vert", "outline.glsl");
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventRender3D) {
            ShaderESP.mc.getRenderManager().setRenderOutlines(true);
            this.input.bindFramebuffer(false);
            ShaderESP.mc.getRenderManager().setRenderOutlines(false);
            GlStateManager.pushMatrix();
            for (final Entity entity : ShaderESP.mc.theWorld.loadedEntityList) {
                if (Aqua.setmgr.getSetting("ShaderESPItems").isState() && entity instanceof EntityItem) {
                    ShaderESP.mc.getRenderManager().renderEntitySimple(entity, ((EventRender3D)e).getPartialTicks());
                }
                if (Aqua.setmgr.getSetting("ShaderESPPlayers").isState() && entity instanceof EntityPlayer && (entity != ShaderESP.mc.thePlayer || ShaderESP.mc.gameSettings.thirdPersonView == 1 || ShaderESP.mc.gameSettings.thirdPersonView == 2)) {
                    ShaderESP.mc.getRenderManager().renderEntitySimple(entity, ((EventRender3D)e).getPartialTicks());
                }
                if (Aqua.setmgr.getSetting("ShaderESPAnimals").isState() && entity instanceof EntityAnimal) {
                    ShaderESP.mc.getRenderManager().renderEntitySimple(entity, ((EventRender3D)e).getPartialTicks());
                }
            }
            if (Aqua.setmgr.getSetting("ShaderESPChests").isState()) {
                final List<TileEntity> loadedTileEntityList = ShaderESP.mc.theWorld.loadedTileEntityList;
                for (int i = 0, loadedTileEntityListSize = loadedTileEntityList.size(); i < loadedTileEntityListSize; ++i) {
                    final TileEntity tileEntity = loadedTileEntityList.get(i);
                    if (tileEntity instanceof TileEntityChest) {
                        GlStateManager.disableTexture2D();
                        TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, ShaderESP.mc.timer.renderPartialTicks, 1);
                        GlStateManager.enableTexture2D();
                    }
                }
            }
            GlStateManager.popMatrix();
            ShaderESP.mc.getRenderManager().setRenderOutlines(false);
            ShaderESP.mc.getFramebuffer().bindFramebuffer(false);
        }
        if (e instanceof EventRender2D) {}
        if (e instanceof EventPostRender2D) {
            this.checkSetup();
            this.pass.framebufferClear();
            this.output.framebufferClear();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableTexture2D();
            final ScaledResolution sr = new ScaledResolution(ShaderESP.mc);
            final double screenWidth = sr.getScaledWidth_double();
            final double screenHeight = sr.getScaledHeight_double();
            GlStateManager.setActiveTexture(33984);
            this.outlineProgram.init();
            GL20.glUniform2f(this.outlineProgram.uniform("texelSize"), 1.0f / ShaderESP.mc.displayWidth, 1.0f / ShaderESP.mc.displayHeight);
            GL20.glUniform1i(this.outlineProgram.uniform("texture"), 0);
            final float alpha = (float)Aqua.setmgr.getSetting("ShaderESPShaderAlpha").getCurrentNumber();
            final float width = (float)Aqua.setmgr.getSetting("ShaderESPOutlineWidth").getCurrentNumber();
            GL20.glUniform1f(this.outlineProgram.uniform("outline_width"), width);
            GL20.glUniform1f(this.outlineProgram.uniform("alpha"), alpha / 255.0f);
            final int[] rgb = getRGB(this.getColor2());
            final int fadeColor = new Color(rgb[0], rgb[1], rgb[2]).getRGB();
            GL20.glUniform3f(this.outlineProgram.uniform("mixColor"), new Color(fadeColor).getRed() / 255.0f, new Color(fadeColor).getGreen() / 255.0f, new Color(fadeColor).getBlue() / 255.0f);
            this.doOutlinePass(0, this.input.framebufferTexture, this.output, (int)screenWidth, (int)screenHeight);
            this.outlineProgram.uninit();
            if (Aqua.setmgr.getSetting("ShaderESPGlow").isState()) {
                ShaderMultiplier.drawGlowESP(() -> this.drawTexturedQuad1(this.output.framebufferTexture, screenWidth, screenHeight), false);
            }
            ShaderStencilUtil.initStencil();
            ShaderStencilUtil.bindWriteStencilBuffer();
            this.drawTexturedQuad(this.input.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.bindReadStencilBuffer(0);
            this.drawTexturedQuad(this.output.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.uninitStencilBuffer();
            GlStateManager.bindTexture(0);
            GlStateManager.alphaFunc(516, 0.2f);
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            this.input.framebufferClear();
            ShaderESP.mc.getFramebuffer().bindFramebuffer(false);
        }
    }
    
    public static void drawGlowESP(final Runnable runnable, final boolean renderTwice) {
        final EventGlowESP event = new EventGlowESP(runnable);
        Aqua.INSTANCE.onEvent(event);
        if (!event.isCancelled() || renderTwice) {
            runnable.run();
        }
    }
    
    public void doOutlinePass(final int pass, final int texture, final Framebuffer out, final int width, final int height) {
        out.framebufferClear();
        out.bindFramebuffer(false);
        GL20.glUniform2f(this.outlineProgram.uniform("direction"), (float)(1 - pass), (float)pass);
        GL11.glBindTexture(3553, texture);
        this.outlineProgram.doRenderPass((float)width, (float)height);
    }
    
    public void checkSetup() {
        this.input.checkSetup(ShaderESP.mc.displayWidth, ShaderESP.mc.displayHeight);
        this.pass.checkSetup(ShaderESP.mc.displayWidth, ShaderESP.mc.displayHeight);
        this.output.checkSetup(ShaderESP.mc.displayWidth, ShaderESP.mc.displayHeight);
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
    
    private void drawTexturedQuad1(final int texture, final double width, final double height) {
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
    
    public static int[] getRGB(final int hex) {
        final int a = hex >> 24 & 0xFF;
        final int r = hex >> 16 & 0xFF;
        final int g = hex >> 8 & 0xFF;
        final int b = hex & 0xFF;
        return new int[] { r, g, b, a };
    }
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("ShaderESPColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
}
