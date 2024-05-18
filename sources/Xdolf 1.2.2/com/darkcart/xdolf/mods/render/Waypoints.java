package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;
import com.darkcart.xdolf.util.Waypoint;

public class Waypoints extends Module {
	public Waypoints() {
		super("Waypoints", "Toggles waypoint rendering.", Keyboard.KEY_EQUALS, 0xFFFFFF, Category.RENDER);
	}
	
	@Override
	public void onRender() {
		if(isEnabled()) {
			for(Waypoint w : Waypoint.wayPoints) {
				w.update();			
				GL11.glDisable(2896 /*GL_LIGHTING*/);
				RenderUtils.drawESP(w.dX, w.dY, w.dZ, w.red, w.green, w.blue);
				GL11.glEnable(2896 /*GL_LIGHTING*/);
				RenderUtils.drawWayPointTracer(w);
			}
		}
	}
}
