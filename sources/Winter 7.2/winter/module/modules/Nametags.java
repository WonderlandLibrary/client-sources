/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import winter.utils.value.types.NumberValue;

public class Nametags
extends Module {
    public static NumberValue scale;
    public static BooleanValue invis;

    public Nametags() {
        super("Nametags", Module.Category.Render, -9);
        this.setBind(45);
        scale = new NumberValue("Scale", 0.75, 0.5, 3.0, 0.01);
        this.addValue(scale);
        invis = new BooleanValue("Show Invis", true);
        this.addValue(invis);
    }

    @EventListener
    public void onRender(Render2DEvent event) {
        GL11.glPushMatrix();
        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        for (Object obj : this.mc.theWorld.playerEntities) {
            if (!(obj instanceof EntityPlayer)) continue;
            try {
                Vector3f pos;
                EntityPlayer entity = (EntityPlayer)obj;
                if (!entity.isEntityAlive() || entity == this.mc.thePlayer && this.mc.gameSettings.thirdPersonView == 0 || (invis.isEnabled() || entity.isInvisible()) && !invis.isEnabled()) continue;
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
                if (Nametags.project2D(x2, y2, z2) == null || (pos = Nametags.project2D(x2, y2, z2)).getZ() < 0.0f || pos.getZ() > 1.0f) continue;
                this.mc.gameSettings.guiScale = 2;
                this.mc.entityRenderer.setupOverlayRendering();
                this.mc.gameSettings.guiScale = scale;
                pos.setY((float)(Display.getHeight() / 2) - pos.getY());
                GlStateManager.pushMatrix();
                GL11.glTranslatef(pos.getX(), pos.getY(), 0.0f);
                Render.nametag(entity, 0.0, 0.0, FriendUtil.isAFriend(entity.getName()) ? -11493190 : (entity.isSneaking() ? -30584 : (entity.isInvisible() ? -2708736 : -1)));
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

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }
}

