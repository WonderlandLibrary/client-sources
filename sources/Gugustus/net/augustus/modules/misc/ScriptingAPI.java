package net.augustus.modules.misc;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.ui.scripting.ScriptingUI;

public class ScriptingAPI extends Module{
	
	public ScriptingUI gui;
	
	public ScriptingAPI() {
		super("ScriptingAPI Gui", Color.blue, Categorys.MISC);
	}
	
	public void onEnable() {
		toggle();
		if(gui == null)
			gui = new ScriptingUI();
		mc.displayGuiScreen(gui);
	}

}
