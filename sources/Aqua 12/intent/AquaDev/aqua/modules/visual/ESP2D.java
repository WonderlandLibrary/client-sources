// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import java.util.List;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import javax.vecmath.Vector4d;
import java.util.Arrays;
import javax.vecmath.Vector3d;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Iterator;
import intent.AquaDev.aqua.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import events.listeners.EventRender2D;
import events.listeners.EventPostRender2D;
import events.listeners.EventRender3D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.renderer.GLAllocation;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.entity.Entity;
import java.util.ArrayList;
import net.minecraft.client.renderer.culling.Frustum;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import intent.AquaDev.aqua.modules.Module;

public class ESP2D extends Module
{
    private static final IntBuffer viewport;
    private static final FloatBuffer modelview;
    private static final FloatBuffer projection;
    private final Frustum frustum;
    private final FloatBuffer vector;
    private final ArrayList<Entity> collectedEntities;
    
    public ESP2D() {
        super("ESP2D", Type.Visual, "ESP2D", 0, Category.Visual);
        this.frustum = new Frustum();
        this.vector = GLAllocation.createDirectFloatBuffer(4);
        this.collectedEntities = new ArrayList<Entity>();
        Aqua.setmgr.register(new Setting("Glow", this, true));
        Aqua.setmgr.register(new Setting("Fade", this, false));
        Aqua.setmgr.register(new Setting("GifBackground", this, false));
        Aqua.setmgr.register(new Setting("GifMode", this, "Aqua", new String[] { "Aqua", "Rias", "Anime", "Rias2" }));
        Aqua.setmgr.register(new Setting("Mode", this, "Real2D", new String[] { "Real2D", "RiasGif" }));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventRender3D) {
            final String currentMode = Aqua.setmgr.getSetting("ESP2DMode").getCurrentMode();
            switch (currentMode) {
                case "RiasGif": {
                    this.drawGifESP();
                    break;
                }
            }
        }
        if (e instanceof EventPostRender2D && Aqua.setmgr.getSetting("ESP2DGlow").isState()) {
            final String currentMode2 = Aqua.setmgr.getSetting("ESP2DMode").getCurrentMode();
            switch (currentMode2) {
                case "Real2D": {
                    Shadow.drawGlow(() -> this.draw2D(), false);
                    break;
                }
            }
        }
        if (e instanceof EventRender2D) {
            final String currentMode3 = Aqua.setmgr.getSetting("ESP2DMode").getCurrentMode();
            switch (currentMode3) {
                case "Real2D": {
                    this.draw2D();
                    break;
                }
            }
        }
    }
    
    public void drawGifESP() {
        for (final EntityPlayer player : ESP2D.mc.theWorld.playerEntities) {
            if (player != ESP2D.mc.thePlayer || ESP2D.mc.gameSettings.thirdPersonView != 0) {
                if (player.isInvisible()) {
                    continue;
                }
                final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * ESP2D.mc.timer.renderPartialTicks - RenderManager.renderPosX;
                final double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * ESP2D.mc.timer.renderPartialTicks - RenderManager.renderPosY;
                final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * ESP2D.mc.timer.renderPartialTicks - RenderManager.renderPosZ;
                GL11.glPushMatrix();
                GL11.glTranslated(x, y - 0.2, z);
                GL11.glScalef(0.03f, 0.03f, 0.03f);
                GL11.glRotated(-ESP2D.mc.getRenderManager().playerViewY, 0.0, 1.0, 0.0);
                GlStateManager.disableDepth();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                RenderUtil.drawGif(50, 90, -100, -100, "rias");
                GlStateManager.enableDepth();
                GL11.glPopMatrix();
            }
        }
    }
    
    public void draw2D() {
        GL11.glPushMatrix();
        final boolean outline = true;
        this.collectEntities();
        final float partialTicks = ESP2D.mc.timer.renderPartialTicks;
        final ScaledResolution scaledResolution = new ScaledResolution(ESP2D.mc);
        final int scaleFactor = scaledResolution.getScaleFactor();
        final double scaling = scaleFactor / Math.pow(scaleFactor, 2.0);
        GL11.glScaled(scaling, scaling, scaling);
        for (final EntityPlayer player : ESP2D.mc.theWorld.playerEntities) {
            if (player != ESP2D.mc.thePlayer || ESP2D.mc.gameSettings.thirdPersonView != 0) {
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
                ESP2D.mc.entityRenderer.setupCameraTransform(partialTicks, 0);
                Vector4d position = null;
                for (int i1 = 0, vectorsSize = vectors.size(); i1 < vectorsSize; ++i1) {
                    Vector3d vector = vectors.get(i1);
                    vector = this.project2D(scaleFactor, vector.x - ESP2D.mc.getRenderManager().viewerPosX, vector.y - ESP2D.mc.getRenderManager().viewerPosY, vector.z - ESP2D.mc.getRenderManager().viewerPosZ);
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
                ESP2D.mc.entityRenderer.setupOverlayRendering();
                final double posX = position.x;
                final double posY = position.y;
                final double endPosX = position.z;
                final double endPosY = position.w;
                final int color = this.getColor2();
                final int color2 = Color.red.getRGB();
                final int black = Color.black.getRGB();
                final float health = player.getHealth();
                final float maxHealth = player.getMaxHealth();
                if (Aqua.setmgr.getSetting("ESP2DGifBackground").isState()) {
                    final String currentMode = Aqua.setmgr.getSetting("ESP2DGifMode").getCurrentMode();
                    switch (currentMode) {
                        case "Aqua": {
                            RenderUtil.drawGif((int)((float)posX - 2.0f), (int)posY, (int)((float)endPosX - posX + 2.0), (int)((float)endPosY - posY), "aqua");
                            break;
                        }
                        case "Rias": {
                            RenderUtil.drawGif((int)((float)posX - 2.0f), (int)posY, (int)((float)endPosX - posX + 2.0), (int)((float)endPosY - posY), "rias");
                            break;
                        }
                        case "Anime": {
                            RenderUtil.drawGif((int)((float)posX - 2.0f), (int)posY, (int)((float)endPosX - posX + 2.0), (int)((float)endPosY - posY), "anime");
                            break;
                        }
                        case "Rias2": {
                            RenderUtil.drawGif((int)((float)posX - 2.0f), (int)posY, (int)((float)endPosX - posX + 2.0), (int)((float)endPosY - posY), "rias2");
                            break;
                        }
                    }
                }
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
        final Entity current = ESP2D.mc.getRenderViewEntity();
        this.frustum.setPosition(current.posX, current.posY, current.posZ);
        return this.frustum.isBoundingBoxInFrustum(bb);
    }
    
    private double interpolate(final double current, final double old, final double scale) {
        return old + (current - old) * scale;
    }
    
    private Vector3d project2D(final int scaleFactor, final double x, final double y, final double z) {
        GL11.glGetFloat(2982, ESP2D.modelview);
        GL11.glGetFloat(2983, ESP2D.projection);
        GL11.glGetInteger(2978, ESP2D.viewport);
        if (GLU.gluProject((float)x, (float)y, (float)z, ESP2D.modelview, ESP2D.projection, ESP2D.viewport, this.vector)) {
            return new Vector3d(this.vector.get(0) / scaleFactor, (Display.getHeight() - this.vector.get(1)) / scaleFactor, this.vector.get(2));
        }
        return null;
    }
    
    private void collectEntities() {
        this.collectedEntities.clear();
        final List<Entity> playerEntities = ESP2D.mc.theWorld.loadedEntityList;
        for (final Entity entity : playerEntities) {
            if (entity instanceof EntityPlayer && !(entity instanceof EntityPlayerSP) && !entity.isDead) {
                this.collectedEntities.add(entity);
            }
        }
    }
    
    public int getColor2() {
        try {
            return Aqua.setmgr.getSetting("ESP2DColor").getColor();
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
    
    static {
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
    }
}
