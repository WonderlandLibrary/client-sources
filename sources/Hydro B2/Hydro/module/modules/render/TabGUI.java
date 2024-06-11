package Hydro.module.modules.render;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.event.Event;
import Hydro.event.events.EventKey;
import Hydro.event.events.EventRenderGUI;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.font.FontUtil;
import Hydro.util.render.ColourUtils.Colors;
import net.minecraft.client.gui.Gui;

public class TabGUI extends Module {
	
	public int currentTab;
	public boolean expanded;

	public TabGUI() {
		super("TabGUI", 0, false, Category.RENDER, "Manage all of your modules without a GUI");
		enabled = true;
	}
	
	@Override
	public void onEvent(Event e) {
		Category category = Category.values()[currentTab];
		List<Module> modules = Client.instance.getModulesByCategory(category);
		
		if(e instanceof EventRenderGUI) {
			Gui.drawRect(5, 28, 80, 30 + (Category.values().length - 1) * 16 + 1.5, Colors.BLACK);
			Gui.drawRect(7, 33 + currentTab * 16 - 2, 9, 33 + currentTab * 16 + 10, HUD.offsetRainbow(4, 0.8f, 1, 150L));
			
			int count = 0;
			for(Category c : Category.values()) {
				if(c.name.equalsIgnoreCase("cosmetics"))
					continue;
				FontUtil.arrayList.drawStringWithShadow(c.name, 11, 33 + count * 16, -1);
				
				count++;
			}
			
			if(expanded) {				
				if(modules.size() == 0)
					return;
				
				Gui.drawRect(82, 28, 80 + 80, 30 + modules.size() * 16 + 1.5, Colors.BLACK);
				Gui.drawRect(84, 33 + category.moduleIndex * 16 - 2, 9 + 77, 33 + category.moduleIndex * 16 + 10, HUD.offsetRainbow(4, 0.8f, 1, 1 * 150L));
				
				count = 0;
				for(Module m : modules) {
					FontUtil.arrayList.drawStringWithShadow(m.getName(), 11 + 77, 33 + count * 16, m.isEnabled() ? -1 : -1);
					
					count++;
				}
			}
			
		}
		
		if(e instanceof EventKey) {
			int code = ((EventKey)e).code;
			
			if(code == Keyboard.KEY_UP) {
				if(expanded) {
					if(category.moduleIndex <= 0) {
						category.moduleIndex = modules.size() - 1;
					}else
						category.moduleIndex--;
				}else {
					if(currentTab <= 0) {
						currentTab = Category.values().length - 2;
					}else
						currentTab--;
				}
			}
			
			if(code == Keyboard.KEY_DOWN) {
				if(expanded) {
					if(category.moduleIndex >= modules.size() - 1) {
						category.moduleIndex = 0;
					}else {
						category.moduleIndex++;
					}
				}else {
					if(currentTab >= Category.values().length - 2) {
						currentTab = 0;
					}else {
						currentTab++;
					}
				}
			}
			
			if(code == Keyboard.KEY_RIGHT) {
				if(modules.size() == 0)
					return;
				
				if(expanded) {
					
				}else
					expanded = true;
			}
			
			if(code == Keyboard.KEY_RETURN) {
				if(expanded) {
					Module module = modules.get(category.moduleIndex);
					
					if(!module.getName().equalsIgnoreCase("TabGUI"))
						module.toggle();
				}
			}
			
			if(code == Keyboard.KEY_LEFT) {
				expanded = false;
			}
		}
	}

}
