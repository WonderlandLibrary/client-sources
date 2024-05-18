package com.kilo.ui;

import java.awt.Color;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.apache.logging.log4j.core.helpers.Integers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.WaypointManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.ui.inter.slotlist.part.Waypoint;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIPopupWaypointEdit extends UI {

	private float formOffset;
	
	private float fX, fY, fW, fH;
	
	private Waypoint waypoint;
	
	public UIPopupWaypointEdit(UI parent, Waypoint waypoint) {
		super(parent);
		this.waypoint = waypoint;
	}
	
	@Override
	public void init() {
		title = "Edit Waypoint";

		formOffset = 0;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 350;
		fH = 300;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Save", fX+(fW/2)-136, fY+(fH/2)-48, 120, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, Resources.iconSubmit[1], 16, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter waypoint name...", fX-(fW/2)+24, fY-(fH/2)+80, fW-48, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "X", fX-(fW/2)+24, fY-(fH/2)+80+32+16, 64, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Y", fX-32, fY-(fH/2)+80+32+16, 64, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Z", fX+(fW/2)-24-64, fY-(fH/2)+80+32+16, 64, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Red", fX-(fW/2)+24, fY-(fH/2)+80+32+16+32+16, 64, 32, Fonts.ttfRoundedBold20, Colors.RED.c, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Green", fX-32, fY-(fH/2)+80+32+16+32+16, 64, 32, Fonts.ttfRoundedBold20, Colors.GREEN.c, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Blue", fX+(fW/2)-24-64, fY-(fH/2)+80+32+16+32+16, 64, 32, Fonts.ttfRoundedBold20, Colors.BLUE.c, Align.L, Align.C));
		
		if (waypoint != null) {
			setValues();
		}
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		boolean can = true;
		
		String name = ((TextBox)inters.get(2)).text;
		try { Double.parseDouble(((TextBox)inters.get(3)).text); } catch (Exception e) { can = false; }
		try { Double.parseDouble(((TextBox)inters.get(4)).text); } catch (Exception e) { can = false; }
		try { Double.parseDouble(((TextBox)inters.get(5)).text); } catch (Exception e) { can = false; }
		TextBox[] tbs = new TextBox[] {(TextBox)inters.get(6), (TextBox)inters.get(7), (TextBox)inters.get(8)};
		
		for(TextBox tb : tbs) {
			try {
				String text = tb.text;
				int i = Integer.parseInt(text);
				if (i < 0 || i > 255) {
					tb.text = ""+Math.max(Math.min(0, i), 255);
					tb.cursorPos = tb.text.length();
				}
			} catch (Exception e) { can = can?tb.text.length() == 0:false; }
		}
		
		if (name.length() == 0) {
			can = false;
		}
		
		inters.get(1).enabled = can;
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIWaypoints)parent).changePopup(null);
			break;
		case 1:
			try {
				int index = WaypointManager.getIndex(waypoint);
				
				WaypointManager.removeWaypoint(waypoint);
				
				String name = ((TextBox)inters.get(2)).text;
				double x = Double.parseDouble(((TextBox)inters.get(3)).text);
				double y = Double.parseDouble(((TextBox)inters.get(4)).text);
				double z = Double.parseDouble(((TextBox)inters.get(5)).text);
				int r = ((TextBox)inters.get(6)).text.length() > 0?Integers.parseInt(((TextBox)inters.get(6)).text):0;
				int g = ((TextBox)inters.get(7)).text.length() > 0?Integers.parseInt(((TextBox)inters.get(7)).text):0;
				int b = ((TextBox)inters.get(8)).text.length() > 0?Integers.parseInt(((TextBox)inters.get(8)).text):0;
				
				WaypointManager.addWaypoint(index, new Waypoint(name, x, y, z, new Color(r, g, b, 255).getRGB()));
				((UIWaypoints)parent).changePopup(null);
				
				Config.saveWaypoints();
			} catch (Exception e) {}
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_ESCAPE:
			((UIWaypoints)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void setValues() {
		((TextBox)inters.get(2)).text = waypoint.name;
		((TextBox)inters.get(3)).text = (int)waypoint.x+"";
		((TextBox)inters.get(4)).text = (int)waypoint.y+"";
		((TextBox)inters.get(5)).text = (int)waypoint.z+"";
		
		Color c = new Color(waypoint.color);
		
		((TextBox)inters.get(6)).text = c.getRed()+"";
		((TextBox)inters.get(7)).text = c.getGreen()+"";
		((TextBox)inters.get(8)).text = c.getBlue()+"";
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		super.render(opacity);
	}
}
