/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.waypoint;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import winter.event.EventListener;
import winter.event.events.Render2DEvent;
import winter.module.Module;
import winter.module.modules.waypoint.WaypointUtil;
import winter.utils.Render;

public class Waypoints
extends Module {
    public Waypoints() {
        super("Waypoints", Module.Category.Render, -8331);
        this.setBind(45);
    }

    @EventListener
    public void onRender(Render2DEvent event) {
        GL11.glPushMatrix();
        if (!WaypointUtil.getWaypoints().isEmpty()) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            for (WaypointUtil.Point point : WaypointUtil.getWaypoints()) {
                try {
                    Vector3f pos;
                    this.mc.getRenderManager();
                    float x2 = (float)(point.pos()[0] + (double)(0.0f * this.mc.timer.renderPartialTicks) - RenderManager.renderPosX);
                    this.mc.getRenderManager();
                    float y2 = (float)(point.pos()[1] + (double)(0.0f * this.mc.timer.renderPartialTicks) - RenderManager.renderPosY);
                    this.mc.getRenderManager();
                    float z2 = (float)(point.pos()[2] + (double)(0.0f * this.mc.timer.renderPartialTicks) - RenderManager.renderPosZ);
                    int scale = this.mc.gameSettings.guiScale;
                    this.mc.gameSettings.guiScale = 2;
                    this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 0);
                    this.mc.gameSettings.guiScale = scale;
                    if (Waypoints.project2D(x2, y2 += 2.0f, z2) == null || (pos = Waypoints.project2D(x2, y2, z2)).getZ() < 0.0f || pos.getZ() > 1.0f) continue;
                    this.mc.gameSettings.guiScale = 2;
                    this.mc.entityRenderer.setupOverlayRendering();
                    this.mc.gameSettings.guiScale = scale;
                    pos.setY((float)(Display.getHeight() / 2) - pos.getY());
                    GlStateManager.pushMatrix();
                    GL11.glTranslatef(pos.getX(), pos.getY(), 0.0f);
                    Render.waypoint(point, 0.0, 0.0);
                    GL11.glTranslatef(- pos.getX(), - pos.getY(), 0.0f);
                    GlStateManager.popMatrix();
                }
                catch (Exception x2) {
                    // empty catch block
                }
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

