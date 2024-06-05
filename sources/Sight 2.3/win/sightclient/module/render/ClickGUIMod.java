package win.sightclient.module.render;

import org.lwjgl.input.Keyboard;

import win.sightclient.Sight;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.ui.clickgui.ClickGui;

public class ClickGUIMod extends Module {

	public ClickGui click;
	
	public static NumberSetting red;
	public static NumberSetting green;
	public static NumberSetting blue;
	
	public ClickGUIMod() {
		super("ClickGUI", Category.RENDER);
		this.setKey(Keyboard.KEY_RSHIFT);
		red = new NumberSetting("Red", this, 1, 0, 1, false);
		green = new NumberSetting("Green", this, 0.39, 0, 1, false);
		blue = new NumberSetting("Blue", this, 0, 0, 1, false);
	}
	
	@Override
	public void onEnable() {
		if (click == null) {
			click = new ClickGui();
		}
		
		mc.displayGuiScreen(click);
		this.setToggled(false);
	}
}
