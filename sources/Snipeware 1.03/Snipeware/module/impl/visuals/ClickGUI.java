package Snipeware.module.impl.visuals;

import org.lwjgl.input.Keyboard;

import Snipeware.api.annotations.Handler;
import Snipeware.events.render.EventRender2D;
import Snipeware.gui.click.ClickGui;
import Snipeware.gui.click.Panel;
import Snipeware.module.Module;
import net.minecraft.client.gui.Gui;

public class ClickGUI extends Module {
	
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, ModuleCategory.VISUALS);
	}
	
	public void onEnable() {
		toggle();
		
		mc.displayGuiScreen(new Panel());
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
}
