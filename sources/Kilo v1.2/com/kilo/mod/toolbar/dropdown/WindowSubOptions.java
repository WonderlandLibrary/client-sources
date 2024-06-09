package com.kilo.mod.toolbar.dropdown;

import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.mod.Module;
import com.kilo.mod.ModuleSubOption;
import com.kilo.ui.UIHacks;
import com.kilo.util.Util;

public class WindowSubOptions extends Window {
	
	private Module module;
	
	public WindowSubOptions(Window parent) {
		super(parent);
	}
	
	public void update(int mouseX, int mouseY) {
		super.update(mouseX, mouseY);
		if (!enabled) { return; }

		try {
			this.title = module.options.get(contentParent).name;
		} catch (Exception e) {}

		translate(parent.x, parent.y);
		
		for(Interactable i : interactables) {
			i.update(mouseX, mouseY);
			i.translate(i.parent.x-(i.parent.width/2)+i.offsetX, i.parent.y+i.offsetY);

			int index = i.id;
			if (i instanceof Checkbox) {
				Checkbox c = (Checkbox)i;
				try {
					c.checked = Util.makeBoolean(module.options.get(contentParent).subOptions.get(index).value);
				} catch (Exception e) {}
			} else if (i instanceof Slider) {
				Slider s = (Slider)i;
				
				if (s.setting) {

					int relMouseX = (int)(mouseX-s.x);
					int relMouseY = (int)(mouseY-s.y);
					float value = 0;
					
					value = relMouseX/(s.width/(module.options.get(contentParent).subOptions.get(index).limit[1]-module.options.get(contentParent).subOptions.get(index).limit[0]))+module.options.get(contentParent).subOptions.get(index).limit[0];

					value = Math.min(Math.max(module.options.get(contentParent).subOptions.get(index).limit[0], value), module.options.get(contentParent).subOptions.get(index).limit[1]);
					
					if (!module.options.get(contentParent).subOptions.get(index).isFloat) {
						module.options.get(contentParent).subOptions.get(index).value = (float)Math.round(value);
					} else {
						module.options.get(contentParent).subOptions.get(index).value = value;
					}
				}
				float val = Util.makeFloat(module.options.get(contentParent).subOptions.get(index).value);
				s.value = (val-module.options.get(contentParent).subOptions.get(index).limit[0])*(i.width/(module.options.get(contentParent).subOptions.get(index).limit[1]-module.options.get(contentParent).subOptions.get(index).limit[0]));
				
				if (!module.options.get(contentParent).subOptions.get(index).isFloat) {
					s.alt = Util.prettyFloat(val);
				} else {
					s.alt = String.format("%.2f", val);
				}
			} else if (i instanceof Keybinder) {
				Keybinder k = (Keybinder)i;
				k.alt = Keyboard.getKeyName(Util.makeInteger(module.options.get(contentParent).subOptions.get(index).value));
				if (k.setting) {
					if (boxFade < 0.1f) {
						k.setting = false;
					}
				}
			}
		}
		
		updateContent();
		
		interactablesFade+= ((showInteractables?0.8f:0)-interactablesFade)/UIHacks.transitionSpeed;
		
		if (active) {
			this.boxFade+= (0.7f-boxFade)/UIHacks.transitionSpeed;
			if (boxFade > 0.4f) {
				showInteractables = true;
			}
			maxWidth = Math.max(200, maxWidth);
			maxHeight = Math.max(28, maxHeight);
			resize(maxWidth, maxHeight);
		} else {
			showInteractables = false;
			resize(24, 0);
			if (interactablesFade < 0.1f) {
				this.boxFade+= (0-boxFade)/UIHacks.transitionSpeed;
			}
		}
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		if (key != Keyboard.KEY_ESCAPE && key != Keyboard.KEY_DELETE) {
			for(Interactable i : interactables) {
				if (i instanceof Keybinder) {
					Keybinder k = (Keybinder)i;
					if (k.setting) {
						module.options.get(contentParent).subOptions.get(i.id).value = key;
						k.setting = false;
					}
				}
			}
		} else {
			if (key == Keyboard.KEY_ESCAPE) {
				for(Interactable i : interactables) {
					if (i instanceof Keybinder) {
						Keybinder k = (Keybinder)i;
						if (k.setting) {
							k.setting = false;
						}
					}
				}
			}
			if (key == Keyboard.KEY_DELETE) {
				for(Interactable i : interactables) {
					if (i instanceof Keybinder) {
						Keybinder k = (Keybinder)i;
						if (k.setting) {
							module.options.get(contentParent).subOptions.get(i.id).value = Keyboard.KEY_NONE;
							k.setting = false;
						}
					}
				}
			}
		}
	}
	
	public void updateInteractables() {
		contentParent = newContentParent;
		
		this.module = ((WindowOptions)parent).module;
		
		interactables.clear();
		
		int index = 0;
		for(ModuleSubOption mo : module.options.get(contentParent).subOptions) {
			switch(mo.type) {
			case CHECKBOX:
				interactables.add(new Checkbox(this, Interactable.TYPE.CHECKBOX, module.options.get(contentParent).subOptions.get(index).name, index, module.options.get(contentParent).subOptions.get(index).description, 0, 0, 200, 24));
				break;
			case SLIDER:
				interactables.add(new Slider(this, Interactable.TYPE.SLIDER, module.options.get(contentParent).subOptions.get(index).name, index, module.options.get(contentParent).subOptions.get(index).description, 0, 0, 200, 24));
				break;
			case KEYBINDER:
				interactables.add(new Keybinder(this, Interactable.TYPE.KEYBINDER, module.options.get(contentParent).subOptions.get(index).name, index, module.options.get(contentParent).subOptions.get(index).description, 0, 0, 200, 24));
				break;
			default:
				break;
			}
			index++;
		}
		
		float left = 0;
		float top = 0;
		float right = -1;
		float bottom = -1;
		
		for(Interactable i : interactables) {
			index = interactables.indexOf(i);
			
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
				left+= 204;
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
		
		if (!enabled || exist.getTime() < existFor) { return; }
		
		if (!mouseOver(mouseX, mouseY) && button == 0) {
			this.active = false;
		}
	}

	public void action(Interactable i, int mouseX, int mouseY, int button) {
		if (!enabled || !active || exist.getTime() < existFor) { return; }
		
		if (i instanceof Checkbox) {
			switch (button) {
			case 0:
				int index = i.id;
				try {
					boolean checked = Util.makeBoolean(module.options.get(contentParent).subOptions.get(index).value);
					module.options.get(contentParent).subOptions.get(index).value = !checked;
				} catch (Exception e) {}
				break;
			}
		} else if (i instanceof Slider) {
			Slider s = (Slider)i;
			switch (button) {
			case 0:
				s.setting = true;
				break;
			}
		} else if (i instanceof Keybinder) {
			Keybinder k = (Keybinder)i;
			switch (button) {
			case 0:
				k.setting = true;
				break;
			}
		}
	}
}
