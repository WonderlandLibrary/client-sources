package me.gishreload.yukon.hacks;

import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.gui.UIRenderer;
import me.gishreload.yukon.module.Module;


public class ClickGui extends Module{

	public ClickGui() {
		super("", Keyboard.KEY_RSHIFT, Category.OTHER);
	}
	
	@Override
	public void onEnable(){
		mc.displayGuiScreen(new GuiManagerDisplayScreen(Edictum.guiManager));
		}
		
	@Override
	public void onDisable(){
		mc.displayGuiScreen(new GuiManagerDisplayScreen(Edictum.guiManager));
	}
}
