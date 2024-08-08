package me.xatzdevelopments.modules.render;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.NumberSetting;

public class Camera extends Module{

	public static double itemSize = 1.0;
	//public ModeSetting animation = new ModeSetting("Animation", "Vanilla", "Xatz", "Spinny", "Exhi", "HaxxerClient");
	public BooleanSetting spinitems = new BooleanSetting("Spinny Items", false);
	public BooleanSetting ufr = new BooleanSetting("UnicodeFontRenderer", false);
	public NumberSetting ItemSize = new NumberSetting("ItemSize", 1.0, 0.1, 2.0, 0.1);
	public NumberSetting SwingSpeed = new NumberSetting("Swing Speed", 6, 1, 25, 1);	
	
	public Camera() {
		super("Camera", 0, Category.RENDER, "Change render options");
		this.addSettings(ItemSize, spinitems, SwingSpeed, ufr);
		toggled = true;
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		this.toggle();
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			this.itemSize = this.ItemSize.getValue();
		}
		
	}
}
