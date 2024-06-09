/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import winter.event.EventListener;
import winter.event.events.Render2DEvent;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.friend.FriendUtil;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;

public class ESP
extends Module {
    public static BooleanValue name;
    public static BooleanValue box;
    public static BooleanValue item;
    public static BooleanValue health;

    public ESP() {
        super("Wallhack", Module.Category.Render, -49370);
        this.setBind(45);
        name = new BooleanValue("Name", true);
        box = new BooleanValue("Box", true);
        item = new BooleanValue("Item", true);
        health = new BooleanValue("Health", true);
        this.addValue(name);
        this.addValue(box);
        this.addValue(item);
        this.addValue(health);
    }

    @EventListener
    public void onRender(Render2DEvent event) {
        GL11.glPushMatrix();
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        for (Object obj : this.mc.theWorld.playerEntities) {
            if (!(obj instanceof EntityPlayer) || (EntityPlayer)obj == this.mc.thePlayer) continue;
            try {
                Vector3f pos;
                EntityPlayer entity = (EntityPlayer)obj;
                if (entity.isInvisible() || !entity.isEntityAlive()) continue;
                this.mc.getRenderManager();
                float x2 = (float)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosX);
                this.mc.getRenderManager();
                float y2 = (float)(entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosY);
                this.mc.getRenderManager();
                float z2 = (float)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
                y2 = (float)((double)y2 + (0.4 + (double)entity.getEyeHeight()));
                int scale = this.mc.gameSettings.guiScale;
                this.mc.gameSettings.guiScale = 2;
                this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 0);
                this.mc.gameSettings.guiScale = scale;
                if (ESP.project2D(x2, y2, z2) == null || (pos = ESP.project2D(x2, y2, z2)).getZ() < 0.0f || pos.getZ() > 1.0f) continue;
                this.mc.gameSettings.guiScale = 2;
                this.mc.entityRenderer.setupOverlayRendering();
                this.mc.gameSettings.guiScale = scale;
                pos.setY((float)(Display.getHeight() / 2) - pos.getY());
                GlStateManager.pushMatrix();
                GL11.glTranslatef(pos.getX(), pos.getY(), 0.0f);
                Render.wallhack(entity, 0.0, 0.0, FriendUtil.isAFriend(entity.getName()) ? -12734764 : -7924716);
                GL11.glTranslatef(- pos.getX(), - pos.getY(), 0.0f);
                GlStateManager.popMatrix();
            }
            catch (Exception entity) {
                // empty catch block
            }
        }
        GL11.glPopMatrix();
        GlStateManager.enableBlend();
        this.mc.entityRenderer.setupOverlayRendering();
    }

    public static Vector3f project2D(float x2, float y2, float z2) {
        FloatBuffer screen_coords = GLAllocation.createDirectFloatBuffer(3);
        IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        screen_coords.clear();
        modelview.clear();
        projection.clear();
        viewport.clear();
        GL11.glGetFloat(2982, modelview);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        boolean ret = GLU.gluProject(x2, y2, z2, modelview, projection, viewport, screen_coords);
        if (ret) {
            return new Vector3f(screen_coords.get(0) / 2.0f, screen_coords.get(1) / 2.0f, screen_coords.get(2));
        }
        return null;
    }
}

