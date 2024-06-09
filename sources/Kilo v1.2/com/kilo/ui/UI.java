package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.util.Resources;
import com.kilo.util.UIUtil;
import com.kilo.util.Util;

public abstract class UI extends InteractableParent {

	protected final Minecraft mc = Minecraft.getMinecraft();
	protected UI parent;
	public String title;
	
	public UI(UI p) {
		super(-1, -1, -1, -1);
		parent = p;
		init();
	}
	
	public abstract void init();

	public static void drawDefaultBackground(boolean branding, float opacity) {
		float[] coords = UIUtil.getScaledImage(1920, 1080, Display.getWidth(), Display.getHeight());
		
		Draw.rectTexture(coords[0], coords[1], coords[2], coords[3], Resources.backgroundLight, Util.reAlpha(Colors.WHITE.c, opacity));

		if (!branding) { return; }
		Draw.rectTexture(Display.getWidth()-97, Display.getHeight()-62, 81, 46, Resources.brandingSmall, Util.reAlpha(Colors.WHITE.c, opacity));
	}

	public static void drawDarkerBackground(boolean branding, float opacity) {
		float[] coords = UIUtil.getScaledImage(1920, 1080, Display.getWidth(), Display.getHeight());
		
		Draw.rectTexture(coords[0], coords[1], coords[2], coords[3], Resources.backgroundDim, Util.reAlpha(Colors.WHITE.c, opacity));
		
		if (!branding) { return; }
		Draw.rectTexture(Display.getWidth()-97, Display.getHeight()-62, 81, 46, Resources.brandingSmall, Util.reAlpha(Colors.WHITE.c, opacity));
	}
}
