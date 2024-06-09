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

public class Macros extends Module {
	
	public Macros(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Edit", "Add, change or remove macros", Interactable.TYPE.SETTINGS, SettingsRel.MACROS, null, false);
	}
	
	public void onEnable() {
		super.onEnable();
		onDisable();
	}
}
