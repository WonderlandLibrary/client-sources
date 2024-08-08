package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.ui.ClickGui;


public class ClickGuiModule extends Module {
	
	public ModeSetting design = new ModeSetting("Design", "New", "New", "Jelly");
	public NumberSetting red = new NumberSetting("Red", 255, 0, 255, 1);
	public NumberSetting green = new NumberSetting("Green", 26, 0, 255, 1);
	public NumberSetting blue = new NumberSetting("Blue", 42, 0, 255, 1);
	public BooleanSetting blur = new BooleanSetting("Blur", false);
	public BooleanSetting Darken = new BooleanSetting("Darken Background", false);
	
	public ClickGuiModule() {
		super("ClickGui", Keyboard.KEY_RSHIFT, Category.RENDER, "Self Explanatory");
		this.addSettings(design, red, green, blue);
	}
	
	public void onEnable() {
		
	       final ClickGui ClickGui = new ClickGui();
	       
	       this.mc.displayGuiScreen(Xatz.clickgui);
	    
	}
	
	public void onDisable() {
		
	}
	
	@Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            this.toggled = false;
        }
    }
	
}
