package com.kilo.mod.all;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.util.Vec3;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.render.Render;
import com.kilo.util.Util;

public class Breadcrumbs extends Module {
	
	private List<double[]> points = new CopyOnWriteArrayList<double[]>();
	
	public Breadcrumbs(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Red", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Green", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Blue", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Opacity", "", Interactable.TYPE.SLIDER, 96, new float[] {0, 255}, false);
		addOption("Width", "Breadcrumbs line width", Interactable.TYPE.SLIDER, 1, new float[] {1, 10}, true);
		
		addOption("Clear", "Clear the breadcrumbs trail", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void onPlayerMovePreUpdate() {
		if (Util.makeBoolean(getOptionValue("clear"))) {
			points.clear();
			options.get(getOption("clear")).value = false;
		}
		
		if (points.size() > 0) {
			double xd = Math.abs(points.get(points.size()-1)[0]-mc.thePlayer.posX);
			double yd = Math.abs(points.get(points.size()-1)[1]-mc.thePlayer.posY);
			double zd = Math.abs(points.get(points.size()-1)[2]-mc.thePlayer.posZ);
			if (xd > 0.25d || yd > 0.25d || zd > 0.25d) {
				points.add(new double[] {mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ});
			}
		} else {
			points.add(new double[] {mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ});
		}
		
		if (points.size() > 1000) {
			points.remove(0);
		}
	}
	
	public void render3D() {
		int r = Util.makeInteger(getOptionValue("red"));
		int g = Util.makeInteger(getOptionValue("green"));
		int b = Util.makeInteger(getOptionValue("blue"));
		int a = Util.makeInteger(getOptionValue("opacity"));
		float w = Util.makeFloat(getOptionValue("width"));
		
		for(int i = 1; i < points.size(); i++) {
			double[] f = points.get(i-1);
			double[] t = points.get(i);
			Vec3 from = new Vec3(f[0], f[1], f[2]);
			Vec3 to = new Vec3(t[0], t[1], t[2]);
			Render.line(from, to, new Color(r, g, b, a).getRGB(), w);
		}
	}
}
