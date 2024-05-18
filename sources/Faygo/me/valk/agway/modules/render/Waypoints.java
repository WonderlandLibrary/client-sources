package me.valk.agway.modules.render;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.valk.Vital;
import me.valk.event.EventListener;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.event.events.screen.EventRenderWorld;
import me.valk.manager.managers.FriendManager;
import me.valk.manager.managers.WaypointManager;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.GuiUtils;
import me.valk.utils.MathUtil;
import me.valk.utils.Wrapper;
import me.valk.utils.render.PrizonRenderUtils;
import me.valk.utils.render.RenderUtil;
import me.valk.utils.waypoint.WayPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class Waypoints extends Module {

	public GuiUtils guiUtils;

	public Waypoints() {
		super(new ModData("Waypoints", Keyboard.KEY_NONE, new Color(66, 174, 212)), ModType.RENDER);
	}

	@Override
	public void onEnable() {
		addChat(".Waypoint add name x y z");
	}

	@Override
	public void onDisable() {
	}

	@EventListener
	public void renderWorld(EventRenderWorld e) {
		GL11.glPushMatrix();
		ScaledResolution sc = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		for (WayPoint point : WaypointManager.points) {
			try {

				double n = point.pos.getX() + 0.0f * mc.timer.renderPartialTicks;
				mc.getRenderManager();
				float x = (float) (n - RenderManager.renderPosX);
				double n2 = point.pos.getY() + 0.0f * mc.timer.renderPartialTicks;
				mc.getRenderManager();
				float y = (float) (n2 - RenderManager.renderPosY);
				double n3 = point.pos.getZ() + 0.0f * mc.timer.renderPartialTicks;
				mc.getRenderManager();
				float z = (float) (n3 - RenderManager.renderPosZ);
				y += 1.5;
				int scale = mc.gameSettings.guiScale;
				mc.gameSettings.guiScale = 2;
				mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
				mc.gameSettings.guiScale = scale;
				if (project2D(x, y, z) == null) {
					continue;
				}
				  final Vector3f pos = project2D(x, y, z);
				
				
				mc.gameSettings.guiScale = 2;
				mc.entityRenderer.setupOverlayRendering();
				mc.gameSettings.guiScale = scale;
				pos.setY(Display.getHeight() / 2 - pos.getY());
				GlStateManager.pushMatrix();
				GL11.glTranslated(pos.getX(), pos.getY(), 0.0f);
				PrizonRenderUtils.waypoint(point, 0.0, 0.0);
				GL11.glTranslatef(-pos.getX(), -pos.getY(), 0.0f);
				GlStateManager.popMatrix();
			} catch (Exception ex) {
			}
		}
		GL11.glPopMatrix();
		GlStateManager.enableBlend();
		mc.entityRenderer.setupOverlayRendering();
	}

	public static Vector3f project2D(final float x, final float y, final float z) {
		final FloatBuffer screen_coords = GLAllocation.createDirectFloatBuffer(3);
		final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
		final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
		final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
		screen_coords.clear();
		modelview.clear();
		projection.clear();
		viewport.clear();
		GL11.glGetFloat(2982, modelview);
		GL11.glGetFloat(2983, projection);
		GL11.glGetInteger(2978, viewport);
		final boolean ret = GLU.gluProject(x, y, z, modelview, projection, viewport, screen_coords);
		if (ret) {
			return new Vector3f(screen_coords.get(0) / 2.0f, screen_coords.get(1) / 2.0f, screen_coords.get(2));
		}
		return null;
	}
}