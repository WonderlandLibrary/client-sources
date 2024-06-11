package Hydro.module.modules.render;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;

import java.util.ArrayList;

public class ClickGui extends Module {
	
	public Hydro.ClickGui.clickgui.ClickGui clickgui;

	public ClickGui() {
		super("ClickGui", Keyboard.KEY_RSHIFT, false, Category.RENDER, "Manage all of your modules");
		java.util.ArrayList<String> options = new ArrayList<>();
		options.add("Normal");
		options.add("Discord");
		Client.instance.settingsManager.rSetting(new Setting("CGuiMode", "Style", this, "Normal", options));
	}

	@Override
    public void onEnable()
    {
    	if(this.clickgui == null)
    		this.clickgui = new Hydro.ClickGui.clickgui.ClickGui();
    	
    	mc.displayGuiScreen(this.clickgui);
    	toggle();
    	super.onEnable();
    }
	
}
