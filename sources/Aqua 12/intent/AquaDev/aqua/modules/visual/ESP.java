// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import javax.vecmath.Vector4d;
import java.util.Arrays;
import javax.vecmath.Vector3d;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.List;
import intent.AquaDev.aqua.utils.ColorUtils;
import org.lwjgl.opengl.GL20;
import intent.AquaDev.aqua.utils.shader.ShaderStencilUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import events.listeners.EventPostRender2D;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.GlStateManager;
import events.listeners.EventRender3D;
import events.Event;
import events.listeners.EventGlowESP;
import intent.AquaDev.aqua.utils.shader.Glow;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.renderer.GLAllocation;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.shader.ShaderProgram;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import net.minecraft.client.renderer.culling.Frustum;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.modules.Module;

public class ESP extends Module
{
    Minecraft MC;
    private static final IntBuffer viewport;
    private static final FloatBuffer modelview;
    private static final FloatBuffer projection;
    private final Frustum frustum;
    private final FloatBuffer vector;
    private final ArrayList<Entity> collectedEntities;
    private final Framebuffer input;
    private final Framebuffer pass;
    private final Framebuffer output;
    private ShaderProgram outlineProgram;
    public static double stringWidth;
    public static double glowStrength;
    
    public ESP() {
        super("ESP", Type.Visual, "ESP", 0, Category.Visual);
        this.MC = Minecraft.getMinecraft();
        this.frustum = new Frustum();
        this.vector = GLAllocation.createDirectFloatBuffer(4);
        this.collectedEntities = new ArrayList<Entity>();
        this.input = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, true);
        this.pass = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, true);
        this.output = new Framebuffer(this.MC.displayWidth, this.MC.displayHeight, true);
        this.outlineProgram = new ShaderProgram("vertex.vert", "outline.glsl");
        Aqua.setmgr.register(new Setting("Sigma", this, 5.0, 0.0, 50.0, true));
        Aqua.setmgr.register(new Setting("Multiplier", this, 1.0, 0.0, 3.0, false));
        Aqua.setmgr.register(new Setting("OutlineWidth", this, 1.0, 0.0, 20.0, false));
        Aqua.setmgr.register(new Setting("ShaderAlpha", this, 255.0, 0.0, 255.0, false));
        Aqua.setmgr.register(new Setting("VanillaTeamColor", this, false));
        Aqua.setmgr.register(new Setting("VanillaBlackLines", this, false));
        Aqua.setmgr.register(new Setting("Glow", this, true));
        Aqua.setmgr.register(new Setting("Fade", this, false));
        Aqua.setmgr.register(new Setting("Animals", this, true));
        Aqua.setmgr.register(new Setting("Mobs", this, false));
        Aqua.setmgr.register(new Setting("Players", this, true));
        Aqua.setmgr.register(new Setting("Items", this, false));
        Aqua.setmgr.register(new Setting("Rainbow", this, false));
        Aqua.setmgr.register(new Setting("AstolfoColors", this, false));
        Aqua.setmgr.register(new Setting("Chests", this, true));
        Aqua.setmgr.register(new Setting("EnderChests", this, false));
        Aqua.setmgr.register(new Setting("ClientColor", this, true));
        Aqua.setmgr.register(new Setting("Mode", this, "Shader", new String[] { "Shader", "Vanilla", "Hizzy", "Xave" }));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        this.outlineProgram = new ShaderProgram("vertex.vert", "outline.glsl");
        Glow.blurProgram = new ShaderProgram("vertex.vert", "alphaBlurESP.glsl");
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    public static void drawGlowESP(final Runnable runnable, final boolean renderTwice) {
        final EventGlowESP event = new EventGlowESP(runnable);
        Aqua.INSTANCE.onEvent(event);
        if (!event.isCancelled() || renderTwice) {
            runnable.run();
        }
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRender3D) {
            ESP.mc.getRenderManager().setRenderOutlines(true);
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            this.input.bindFramebuffer(false);
            this.MC.getRenderManager().setRenderOutlines(false);
            if (Aqua.setmgr.getSetting("ESPChests").isState()) {
                final List<TileEntity> loadedTileEntityList = ESP.mc.theWorld.loadedTileEntityList;
                for (int i = 0, loadedTileEntityListSize = loadedTileEntityList.size(); i < loadedTileEntityListSize; ++i) {
                    final TileEntity tileEntity = loadedTileEntityList.get(i);
                    if (tileEntity instanceof TileEntityChest) {
                        GlStateManager.disableTexture2D();
                        TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, ESP.mc.timer.renderPartialTicks, 1);
                        GlStateManager.enableTexture2D();
                    }
                }
            }
            if (Aqua.setmgr.getSetting("ESPEnderChests").isState()) {
                final List<TileEntity> loadedTileEntityList = ESP.mc.theWorld.loadedTileEntityList;
                for (int i = 0, loadedTileEntityListSize = loadedTileEntityList.size(); i < loadedTileEntityListSize; ++i) {
                    final TileEntity tileEntity = loadedTileEntityList.get(i);
                    if (tileEntity instanceof TileEntityEnderChest) {
                        GlStateManager.disableTexture2D();
                        TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, ESP.mc.timer.renderPartialTicks, 1);
                        GlStateManager.enableTexture2D();
                    }
                }
            }
            if (Aqua.setmgr.getSetting("ESPMode").getCurrentMode().equalsIgnoreCase("Shader") && !Aqua.setmgr.getSetting("ESPMode").getCurrentMode().equalsIgnoreCase("Vanilla") && Aqua.setmgr.getSetting("ESPPlayers").isState()) {
                for (final Entity entity : this.MC.theWorld.loadedEntityList) {
                    if (entity instanceof EntityPlayer && (entity != ESP.mc.thePlayer || ESP.mc.gameSettings.thirdPersonView == 1 || ESP.mc.gameSettings.thirdPersonView == 2)) {
                        ESP.mc.getRenderManager().renderEntityStatic(entity, ((EventRender3D)event).getPartialTicks(), false);
                    }
                }
            }
            if (Aqua.setmgr.getSetting("ESPItems").isState()) {
                for (final Entity entity : this.MC.theWorld.loadedEntityList) {
                    if (entity instanceof EntityItem) {
                        ESP.mc.getRenderManager().renderEntityStatic(entity, ((EventRender3D)event).getPartialTicks(), false);
                    }
                }
            }
            if (Aqua.setmgr.getSetting("ESPAnimals").isState() && !Aqua.setmgr.getSetting("ESPMode").getCurrentMode().equalsIgnoreCase("Vanilla")) {
                for (final Entity entity : this.MC.theWorld.loadedEntityList) {
                    if (entity instanceof EntityAnimal) {
                        ESP.mc.getRenderManager().renderEntityStatic(entity, ((EventRender3D)event).getPartialTicks(), false);
                    }
                }
            }
            if (Aqua.setmgr.getSetting("ESPMobs").isState() && !Aqua.setmgr.getSetting("ESPMode").getCurrentMode().equalsIgnoreCase("Vanilla")) {
                for (final Entity entity : this.MC.theWorld.loadedEntityList) {
                    if (entity instanceof EntityMob) {
                        ESP.mc.getRenderManager().renderEntityStatic(entity, ((EventRender3D)event).getPartialTicks(), false);
                    }
                }
            }
            ESP.mc.getRenderManager().setRenderOutlines(false);
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
            ESP.mc.getFramebuffer().bindFramebuffer(false);
        }
        if (event instanceof EventPostRender2D) {
            drawGlowESP(() -> Gui.drawRect(-2001, -2001, -2000, -2000, Color.red.getRGB()), false);
            Glow.checkSetup();
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
            Glow.blurProgram.init();
            Glow.setupBlurUniforms();
            Glow.doBlurPass(0, Glow.input.framebufferTexture, Glow.pass, (int)screenWidth, (int)screenHeight);
            Glow.doBlurPass(1, Glow.pass.framebufferTexture, Glow.output, (int)screenWidth, (int)screenHeight);
            Glow.blurProgram.uninit();
            ShaderStencilUtil.initStencil();
            ShaderStencilUtil.bindWriteStencilBuffer();
            this.drawTexturedQuad1(Glow.input.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.bindReadStencilBuffer(0);
            this.drawTexturedQuad1(Glow.output.framebufferTexture, screenWidth, screenHeight);
            ShaderStencilUtil.uninitStencilBuffer();
            GlStateManager.bindTexture(0);
            GlStateManager.alphaFunc(516, 0.2f);
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            Glow.input.framebufferClear();
            this.MC.getFramebuffer().bindFramebuffer(false);
        }
        else if (event instanceof EventGlowESP) {
            Glow.onGlowEvent((EventGlowESP)event);
        }
        if (event instanceof EventPostRender2D) {
            this.checkSetup();
            this.pass.framebufferClear();
            this.output.framebufferClear();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.0f);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableTexture2D();
            final ScaledResolution sr = new ScaledResolution(this.MC);
            final double screenWidth = sr.getScaledWidth_double();
            final double screenHeight = sr.getScaledHeight_double();
            GlStateManager.setActiveTexture(33984);
            this.outlineProgram.init();
            GL20.glUniform2f(this.outlineProgram.uniform("texelSize"), 1.0f / this.MC.displayWidth, 1.0f / this.MC.displayHeight);
            GL20.glUniform1i(this.outlineProgram.uniform("texture"), 0);
            final float alpha = (float)Aqua.setmgr.getSetting("ESPShaderAlpha").getCurrentNumber();
            final float width = (float)Aqua.setmgr.getSetting("ESPOutlineWidth").getCurrentNumber();
            GL20.glUniform1f(this.outlineProgram.uniform("outline_width"), width);
            GL20.glUniform1f(this.outlineProgram.uniform("alpha"), alpha / 255.0f);
            final int[] rgb = Aqua.setmgr.getSetting("ESPAstolfoColors").isState() ? getRGB(SkyRainbow(20, 1.0f, 0.5f).getRGB()) : (Aqua.setmgr.getSetting("ESPRainbow").isState() ? getRGB(rainbowESP(0)) : (Aqua.setmgr.getSetting("ESPClientColor").isState() ? getRGB(this.getColor()) : getRGB(this.getColor2())));
            final int fadeColor = Aqua.setmgr.getSetting("ESPFade").isState() ? ColorUtils.getColorAlpha(Arraylist.getGradientOffset(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()), new Color(Aqua.setmgr.getSetting("ArraylistColor").getColor()), 15.0), 255).getRGB() : new Color(rgb[0], rgb[1], rgb[2]).getRGB();
            GL20.glUniform3f(this.outlineProgram.uniform("mixColor"), new Color(fadeColor).getRed() / 255.0f, new Color(fadeColor).getGreen() / 255.0f, new Color(fadeColor).getBlue() / 255.0f);
            this.doOutlinePass(0, this.input.framebufferTexture, this.output, (int)screenWidth, (int)screenHeight);
            this.outlineProgram.uninit();
            if (Aqua.setmgr.getSetting("ESPGlow").isState()) {
                final double screenWidth2;
                final double screenHeight2;
                drawGlowESP(() -> this.drawTexturedQuad1(this.output.framebufferTexture, screenWidth2, screenHeight2), false);
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
            ESP.mc.getFramebuffer().bindFramebuffer(false);
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
        this.input.checkSetup(this.MC.displayWidth, this.MC.displayHeight);
        this.pass.checkSetup(this.MC.displayWidth, this.MC.displayHeight);
        this.output.checkSetup(this.MC.displayWidth, this.MC.displayHeight);
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
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("ESPColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public int getColor() {
        try {
            return Aqua.setmgr.getSetting("HUDColor").getColor();
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }
    
    public static int[] getRGB(final int hex) {
        final int a = hex >> 24 & 0xFF;
        final int r = hex >> 16 & 0xFF;
        final int g = hex >> 8 & 0xFF;
        final int b = hex & 0xFF;
        return new int[] { r, g, b, a };
    }
    
    public static int rainbowESP(final int delay) {
        final float rainbowSpeed = 25.0f;
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + delay)) / 25.0;
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.9f, 1.0f).getRGB();
    }
    
    public static Color SkyRainbow(final int counter, final float bright, final float st) {
        double v1 = Math.ceil((double)(System.currentTimeMillis() + counter * 109L)) / 6.0;
        return Color.getHSBColor(((float)((v1 %= 360.0) / 360.0) < 0.5) ? (-(float)(v1 / 360.0)) : ((float)(v1 / 360.0)), st, bright);
    }
    
    public void drawHizzyESP() {
        for (final Object o : ESP.mc.theWorld.loadedEntityList) {
            final Entity e = (Entity)o;
            if (e instanceof EntityPlayer && e != ESP.mc.thePlayer) {
                final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * ESP.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * ESP.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ESP.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                GL11.glPushMatrix();
                GL11.glTranslated(x, y - 0.2, z);
                GL11.glScalef(0.03f, 0.03f, 0.03f);
                GL11.glRotated(-ESP.mc.getRenderManager().playerViewY, 0.0, 1.0, 0.0);
                GlStateManager.disableDepth();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                ESP.mc.getTextureManager().bindTexture(new ResourceLocation("Aqua/gui/max.png"));
                Gui.drawModalRectWithCustomSizedTexture(50, 90, 0.0f, 0.0f, -100, -100, -100.0f, -100.0f);
                GlStateManager.enableDepth();
                GL11.glPopMatrix();
            }
        }
    }
    
    public void draw2D() {
        GL11.glPushMatrix();
        final boolean outline = true;
        this.collectEntities();
        final float partialTicks = ESP.mc.timer.renderPartialTicks;
        final ScaledResolution scaledResolution = new ScaledResolution(ESP.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final double scaling = scaleFactor / Math.pow(scaleFactor, 2.0);
        GL11.glScaled(scaling, scaling, scaling);
        for (final EntityPlayer player : ESP.mc.theWorld.playerEntities) {
            if (player != ESP.mc.thePlayer || ESP.mc.gameSettings.thirdPersonView != 0) {
                if (player.isInvisible()) {
                    continue;
                }
                if (!this.isInViewFrustrum(player)) {
                    continue;
                }
                final double x1 = this.interpolate(player.posX, player.lastTickPosX, partialTicks);
                final double y1 = this.interpolate(player.posY, player.lastTickPosY, partialTicks);
                final double z1 = this.interpolate(player.posZ, player.lastTickPosZ, partialTicks);
                final double width = player.width / 1.5;
                final double height = player.height + 0.2;
                final AxisAlignedBB aabb = new AxisAlignedBB(x1 - width, y1, z1 - width, x1 + width, y1 + height, z1 + width);
                final List vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));
                ESP.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                for (int i1 = 0, vectorsSize = vectors.size(); i1 < vectorsSize; ++i1) {
                    Vector3d vector = vectors.get(i1);
                    vector = this.project2D(scaleFactor, vector.x - ESP.mc.getRenderManager().viewerPosX, vector.y - ESP.mc.getRenderManager().viewerPosY, vector.z - ESP.mc.getRenderManager().viewerPosZ);
                    if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                        if (position == null) {
                            position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                        }
                        position.x = Math.min(vector.x, position.x);
                        position.y = Math.min(vector.y, position.y);
                        position.z = Math.max(vector.x, position.z);
                        position.w = Math.max(vector.y, position.w);
                    }
                }
                if (position == null) {
                    continue;
                }
                ESP.mc.entityRenderer.setupOverlayRendering();
                final double posX = position.x;
                final double posY = position.y;
                final double endPosX = position.z;
                final double endPosY = position.w;
                final int color = Aqua.setmgr.getSetting("ESPClientColor").isState() ? this.getColor() : this.getColor2();
                final int color2 = Color.red.getRGB();
                final int black = Color.black.getRGB();
                if (!outline) {
                    continue;
                }
                final float health = player.getHealth();
                final float maxHealth = player.getMaxHealth();
                Gui.drawRect2(posX - 1.2, endPosY - health / maxHealth * (endPosY - posY), posX - 2.0, posY, Color.black.getRGB());
                Gui.drawRect2(posX - 1.2, endPosY - health / maxHealth * (endPosY - posY), posX - 2.0, endPosY, Color.green.getRGB());
                Gui.drawRect2(posX - 1.0, posY, posX + 0.5, endPosY + 0.5, black);
                Gui.drawRect2(posX - 1.0, posY - 0.5, endPosX + 0.5, posY + 0.5 + 0.5, black);
                Gui.drawRect2(endPosX - 0.5 - 0.5, posY, endPosX + 0.5, endPosY + 0.5, black);
                Gui.drawRect2(posX - 1.0, endPosY - 0.5 - 0.5, endPosX + 0.5, endPosY + 0.5, black);
                Gui.drawRect2(posX - 0.5, posY, posX + 0.5 - 0.5, endPosY, color);
                Gui.drawRect2(posX, endPosY - 0.5, endPosX, endPosY, color);
                Gui.drawRect2(posX - 0.5, posY, endPosX, posY + 0.5, color);
                Gui.drawRect2(endPosX - 0.5, posY, endPosX, endPosY, color);
            }
        }
        GL11.glPopMatrix();
    }
    
    public boolean isInViewFrustrum(final Entity entity) {
        return this.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }
    
    private boolean isInViewFrustrum(final AxisAlignedBB bb) {
        final Entity current = ESP.mc.getRenderViewEntity();
        this.frustum.setPosition(current.posX, current.posY, current.posZ);
        return this.frustum.isBoundingBoxInFrustum(bb);
    }
    
    private double interpolate(final double current, final double old, final double scale) {
        return old + (current - old) * scale;
    }
    
    private Vector3d project2D(final int scaleFactor, final double x, final double y, final double z) {
        GL11.glGetFloat(2982, ESP.modelview);
        GL11.glGetFloat(2983, ESP.projection);
        GL11.glGetInteger(2978, ESP.viewport);
        if (GLU.gluProject((float)x, (float)y, (float)z, ESP.modelview, ESP.projection, ESP.viewport, this.vector)) {
            return new Vector3d(this.vector.get(0) / scaleFactor, (Display.getHeight() - this.vector.get(1)) / scaleFactor, this.vector.get(2));
        }
        return null;
    }
    
    private void collectEntities() {
        this.collectedEntities.clear();
        final List<Entity> playerEntities = ESP.mc.theWorld.loadedEntityList;
        for (final Entity entity : playerEntities) {
            if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP) && !entity.isDead) {
                this.collectedEntities.add(entity);
            }
        }
    }
    
    public void drawXaveESP() {
        for (final Object o : ESP.mc.theWorld.loadedEntityList) {
            final Entity e = (Entity)o;
            if (e instanceof EntityPlayer && e != ESP.mc.thePlayer) {
                final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * ESP.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * ESP.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ESP.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                GlStateManager.pushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GlStateManager.disableCull();
                GL11.glDepthMask(false);
                GlStateManager.translate(x, y + e.height / 2.0f, z);
                GlStateManager.rotate(-ESP.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                GlStateManager.scale(-0.098, -0.098, 0.098);
                final Color color = new Color(this.getColor2());
                if (((EntityPlayer)e).hurtTime != 0) {
                    GlStateManager.color(1.0f, 0.0f, 0.0f);
                }
                else {
                    GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
                }
                final float width = (float)(23.3 * e.width / 2.0);
                final float height = 12.0f;
                draw3DRect(width, height - 1.0f, width - 4.0f, height);
                draw3DRect(-width, height - 1.0f, -width + 4.0f, height);
                draw3DRect(-width, height, -width + 1.0f, height - 4.0f);
                draw3DRect(width, height, width - 1.0f, height - 4.0f);
                draw3DRect(width, -height, width - 4.0f, -height + 1.0f);
                draw3DRect(-width, -height, -width + 4.0f, -height + 1.0f);
                draw3DRect(-width, -height + 1.0f, -width + 1.0f, -height + 4.0f);
                draw3DRect(width, -height + 1.0f, width - 1.0f, -height + 4.0f);
                GlStateManager.color(0.0f, 0.0f, 0.0f);
                draw3DRect(width, height, width - 4.0f, height + 0.2f);
                draw3DRect(-width, height, -width + 4.0f, height + 0.2f);
                draw3DRect(-width - 0.2f, height + 0.2f, -width, height - 4.0f);
                draw3DRect(width + 0.2f, height + 0.2f, width, height - 4.0f);
                draw3DRect(width + 0.2f, -height, width - 4.0f, -height - 0.2f);
                draw3DRect(-width - 0.2f, -height, -width + 4.0f, -height - 0.2f);
                draw3DRect(-width - 0.2f, -height, -width, -height + 4.0f);
                draw3DRect(width + 0.2f, -height, width, -height + 4.0f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDepthMask(true);
                GlStateManager.enableCull();
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2848);
                GlStateManager.popMatrix();
            }
        }
    }
    
    public static void draw3DRect(final float x1, final float y1, final float x2, final float y2) {
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
    }
    
    static {
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
    }
}
