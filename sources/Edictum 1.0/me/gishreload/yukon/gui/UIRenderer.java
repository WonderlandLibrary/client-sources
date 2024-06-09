package me.gishreload.yukon.gui;

import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;

import me.gishreload.yukon.Edictum;
import net.minecraft.client.Minecraft;

public class UIRenderer {
	
	public static void renderAndUpdateFrames(){
		for(Frame f: Edictum.guiManager.getFrames()){
			f.update();
		}
		for(Frame f: Edictum.guiManager.getFrames()){
			if(f.isPinned() || Minecraft.getMinecraft().currentScreen instanceof GuiManagerDisplayScreen){
				f.render();
			}
		}
	}

}
