package com.thunderware.module.visuals;

import org.lwjgl.input.Keyboard;

import com.thunderware.module.ModuleBase;
import com.thunderware.ui.ClickGUI.dropDown.ClickGui;

public class ClickGUI extends ModuleBase {

	public ClickGui clickGUI = new ClickGui();
	
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.VISUALS);
	}
	
	public void onEnable() {
		mc.displayGuiScreen(clickGUI);
	}

}
