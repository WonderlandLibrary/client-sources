package me.valk.overlay.tabGui.parts;

import org.lwjgl.input.Keyboard;

import me.valk.Vital;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.overlay.tabGui.TabPart;

public class TabTypePart extends TabPart {

	private ModType type;

	public TabTypePart(ModType type, TabPanel parent){
		super(Character.toString(type.toString().toLowerCase().charAt(0)).toUpperCase() + type.toString().toLowerCase().substring(1), parent);
		this.type = type;
		
		
	}
	
	@Override
	public void onKeyPress(int key) {
		if(key == Keyboard.KEY_RIGHT){
			TabPanel panel = new TabPanel(this.getParent().getTabGui());
			panel.setVisible(true);

			for(Module module : Vital.getManagers().getModuleManager().getContents()){
				if(module.getType() == type){
					TabModulePart mButton = new TabModulePart(module, panel);

					panel.addElement(mButton);
				}
			}

			if(panel.getElements().isEmpty()) return;

			this.getParent().getTabGui().addPanel(panel);
		}
	}
	
	public ModType getType() {
		return type;
	}

}
