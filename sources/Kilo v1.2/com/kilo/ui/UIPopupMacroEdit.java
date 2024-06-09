package com.kilo.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.MacroManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.ui.inter.slotlist.part.Macro;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIPopupMacroEdit extends UI {

	private float formOffset;
	
	private float fX, fY, fW, fH;
	
	private Macro macro;
	
	public UIPopupMacroEdit(UI parent, Macro macro) {
		super(parent);
		this.macro = macro;
	}
	
	@Override
	public void init() {
		title = "Edit Macro";

		formOffset = 0;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 350;
		fH = 250;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Save", fX+8, fY+(fH/2)-48, (fW/2)-24, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, Resources.iconSubmit[1], 16, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter name...", fX-(fW/2)+24, fY-(fH/2)+80, fW-48, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter command...", fX-(fW/2)+24, fY-(fH/2)+80+32+16, fW-48, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new Button(this, "Keybind: NONE", fX-(fW/2)+16, fY+(fH/2)-48, (fW/2)-24, 32, Fonts.ttfRoundedBold14, Colors.DARKBLUE.c, null, 0, Align.C, Align.C));
		
		if (macro != null) {
			setValues();
		}
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		String name = ((TextBox)inters.get(2)).text;
		String command = ((TextBox)inters.get(3)).text;
		String keybind = ((Button)inters.get(4)).text.replace("Keybind: ", "");
		inters.get(1).enabled = name.length() > 0 && command.length() > 0 && !keybind.equalsIgnoreCase("none") && !keybind.equalsIgnoreCase("press a key");
		
		if (inters.get(4).active) {
			((Button)inters.get(4)).text = "Press a key";
		} else {
			if (((Button)inters.get(4)).text.equalsIgnoreCase("Press a key")) {
				((Button)inters.get(4)).text = "Keybind: NONE";
			}
		}
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIMacros)parent).changePopup(null);
			break;
		case 1:
			int index = MacroManager.getIndex(macro);
			
			MacroManager.removeMacro(macro);
			
			String name = ((TextBox)inters.get(2)).text;
			String command = ((TextBox)inters.get(3)).text;
			int keybind = Keyboard.getKeyIndex(((Button)inters.get(4)).text.replace("Keybind: ", ""));
			
			MacroManager.addMacro(index, new Macro(name, command, keybind));
			((UIMacros)parent).changePopup(null);
			
			Config.saveMacros();
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
			((UIMacros)parent).changePopup(null);
			break;
		default:
			if (inters.get(4).active) {
				((Button)inters.get(4)).text = "Keybind: "+Keyboard.getKeyName(key);
				inters.get(4).active = false;
			}
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void setValues() {
		((TextBox)inters.get(2)).text = macro.name;
		((TextBox)inters.get(3)).text = macro.command;
		((Button)inters.get(4)).text = "Keybind: "+Keyboard.getKeyName(macro.keybind);
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		super.render(opacity);
	}
}
