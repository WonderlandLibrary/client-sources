package com.kilo.mod.toolbar.dropdown;

import org.lwjgl.opengl.Display;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;

public class WindowModule extends Window {
	
	private Category category;

	public WindowModule(Window parent) {
		super(parent);
	}

	public void update(int mouseX, int mouseY) {
		super.update(mouseX, mouseY);
		if (!enabled) { return; }
		
		if (category != null) {
			this.title = category.name();
		}
		
		for(Interactable i : interactables) {
			i.update(mouseX, mouseY);
			int index = i.id;
			if (i instanceof Toggle) {
				Toggle t = (Toggle)i;
				t.active = ModuleManager.list().get(index).active;
			}
		}
		
		if (contentParent == -1) {
			updateContentParent(0);
		}
		
		updateContent();
		
		interactablesFade+= ((showInteractables?0.8f:0)-interactablesFade)/UIHacks.transitionSpeed;
		
		if (active && !((UIHacks)UIHandler.currentUI).options.active && !((UIHacks)UIHandler.currentUI).subOptions.active) {
			this.boxFade+= (0.7f-boxFade)/UIHacks.transitionSpeed;
			if (boxFade > 0.1f) {
				showInteractables = true;
				newContentParent = contentParent;
			}
			maxWidth = Math.max(160, maxWidth);
			maxHeight = Math.max(28, maxHeight);
			resize(maxWidth, maxHeight);
		} else {
			showInteractables = false;
			resize(24, 0);
			if (interactablesFade < 0.1f) {
				this.boxFade+= (0-boxFade)/UIHacks.transitionSpeed;
			}
		}
		
		for(Interactable i : interactables) {
			i.translate(i.parent.x-(i.parent.width/2)+i.offsetX, i.parent.y+i.offsetY);
		}
	}
	
	public void updateInteractables() {
		contentParent = newContentParent;
		
		category = Category.values()[contentParent];
		
		interactables.clear();
		
		for(Module module : ModuleManager.list()) {
			if (category != Category.ALL) {
				if (module.category != category) {
					continue;
				}
			}
			int index = ModuleManager.list().indexOf(module);
			interactables.add(new Toggle(this, Interactable.TYPE.BUTTON, module.name, index, module.description+(module.warning.length() > 0?"|\u00a7c"+module.warning:"")+(module.options != null?"| |Right click for options":""), 0, 0, 160, 24));
		}
		
		float left = 0;
		float top = 0;
		float right = -1;
		float bottom = -1;
		
		for(Interactable i : interactables) {
			int index = interactables.indexOf(i);
			
			i.offsetX = left;
			i.offsetY = 32+top;
			
			if (i.offsetX+i.width > right) {
				right = i.offsetX+i.width;
			}
			if (i.offsetY+i.height > bottom) {
				bottom = i.offsetY+i.height;
			}
			
			top+= 24;
			if (bottom > Display.getHeight()-128-y) {
				top = 0;
				left+= 164;
				bottom = 24;
			}
		}
		
		right = 0;
		bottom = 0;
		for(Interactable i : interactables) {
			if (i.offsetX+i.width > right) {
				right = i.offsetX+i.width;
			}
			if (i.offsetY+i.height > bottom) {
				bottom = i.offsetY+i.height;
			}
		}
		
		maxWidth = right;
		maxHeight = bottom;
	}
	
	public void mouseClick(int mouseX, int mouseY, int button) {
		super.mouseClick(mouseX, mouseY, button);
		
		if (!enabled || ((UIHacks)UIHandler.currentUI).options.active || ((UIHacks)UIHandler.currentUI).subOptions.active || exist.getTime() < existFor) { return; }
		
		if (!mouseOver(mouseX, mouseY) && button == 0) {
			this.active = false;
		}
	}

	public void action(Interactable i, int mouseX, int mouseY, int button) {
		if (!enabled || !active || ((UIHacks)UIHandler.currentUI).options.active || ((UIHacks)UIHandler.currentUI).subOptions.active || exist.getTime() < existFor) { return; }
		
		switch (button) {
		case 0:
			if (i instanceof Toggle) {
				i.active = ModuleManager.list().get(i.id).active;
				ModuleManager.list().get(i.id).toggle();
			}
			break;
		case 1:
			if (i instanceof Toggle) {
				WindowOptions options = ((UIHacks)UIHandler.currentUI).options;
				options.active = true;
				options.updateContentParent(i.id);
			}
			break;
		}
	}
}
