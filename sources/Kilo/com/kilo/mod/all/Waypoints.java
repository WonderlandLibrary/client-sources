package com.kilo.mod.all;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.kilo.manager.WaypointManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.SettingsRel;
import com.kilo.render.Render;
import com.kilo.ui.inter.slotlist.part.Waypoint;
import com.kilo.util.RenderUtil;
import com.kilo.util.Util;

public class Waypoints extends Module {
	
	public Waypoints(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Edit", "Add, change or remove waypoints", Interactable.TYPE.SETTINGS, SettingsRel.WAYPOINTS, null, false);
	}
	
	public void render3DPost() {
		if (!active) { return; }
		
		RenderUtil.setupFarRender();
		
		float s = 0.5f;
		GlStateManager.disableDepth();
		for (Waypoint w : WaypointManager.getList()) {
			
			double dist = mc.thePlayer.getDistance(w.x+0.5, mc.thePlayer.posY, w.z+0.5);
			double[] f = new double[] {w.x+s, 0-dist, w.z+s};
			double[] t = new double[] {w.x+s, 255+dist, w.z+s};
			double[] p = RenderUtil.renderPos(new Vec3(w.x+0.5, w.y+0.5f, w.z+0.5));
			Render.line(new Vec3(f[0], f[1], f[2]), new Vec3(t[0], t[1], t[2]), w.color, Util.reAlpha(w.color, 0.2f));
			Render.render3DNameTag(w.name, p[0], p[1], p[2], w.x+0.5, w.y+0.5f, w.z+0.5);
		}
		GlStateManager.enableDepth();
	}
}
