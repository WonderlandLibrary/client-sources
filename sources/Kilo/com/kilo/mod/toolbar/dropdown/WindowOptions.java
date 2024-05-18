package com.kilo.mod.toolbar.dropdown;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.ModuleOption;
import com.kilo.mod.util.FollowHandler;
import com.kilo.ui.UIChatSpam;
import com.kilo.ui.UIFriends;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;
import com.kilo.ui.UIHistory;
import com.kilo.ui.UIMacros;
import com.kilo.ui.UINuker;
import com.kilo.ui.UIWaypoints;
import com.kilo.ui.UIXray;
import com.kilo.util.Util;

public class WindowOptions extends Window {
	
	public Module module;
	
	public WindowOptions(Window parent) {
		super(parent);
	}
	
	public void update(int mouseX, int mouseY) {
		super.update(mouseX, mouseY);
		
		if (!enabled) { return; }
		
		if (module != null) {
			this.title = module.name;
		}

		translate(parent.x, parent.y);
		
		for(Interactable i : interactables) {
			i.update(mouseX, mouseY);
			i.translate(i.parent.x-(i.parent.width/2)+i.offsetX, i.parent.y+i.offsetY);
			int index = i.id;
			if (i instanceof Checkbox) {
				Checkbox c = (Checkbox)i;
				try {
					c.checked = Util.makeBoolean(module.options.get(index).value);
				} catch (Exception e) {}
			} else if (i instanceof Slider) {
				Slider s = (Slider)i;
				
				if (s.setting) {
					int relMouseX = (int)(mouseX-s.x);
					int relMouseY = (int)(mouseY-s.y);
					float value = 0;
					
					value = relMouseX/(s.width/(module.options.get(index).limit[1]-module.options.get(index).limit[0]))+module.options.get(index).limit[0];

					value = Math.min(Math.max(module.options.get(index).limit[0], value), module.options.get(index).limit[1]);
					
					if (!module.options.get(index).isFloat) {
						module.options.get(index).value = (float)Math.round(value);
					} else {
						module.options.get(index).value = value;
					}
				}
				
				float val = Util.makeFloat(module.options.get(index).value);
				s.value = (val-module.options.get(index).limit[0])*(i.width/(module.options.get(index).limit[1]-module.options.get(index).limit[0]));
				
				if (!module.options.get(index).isFloat) {
					s.alt = Util.prettyFloat(val);
				} else {
					s.alt = String.format("%.2f", val);
				}
			} else if (i instanceof Keybinder) {
				Keybinder k = (Keybinder)i;
				k.alt = Keyboard.getKeyName(Util.makeInteger(module.options.get(index).value));
				if (k.setting) {
					if (boxFade < 0.1f) {
						k.setting = false;
					}
				}
			}
		}
		
		if (contentParent == -1) {
			updateContentParent(0);
		}
		
		updateContent();
		
		interactablesFade+= ((showInteractables?0.8f:0)-interactablesFade)/UIHacks.transitionSpeed;
		
		if (active && !((UIHacks)UIHandler.currentUI).subOptions.active) {
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
		
		if (exist.getTime() < existFor) { return; }
		
		for(Interactable i : interactables) {
			if (i instanceof Keybinder) {
				if (key != Keyboard.KEY_ESCAPE && key != Keyboard.KEY_DELETE) {
					Keybinder k = (Keybinder)i;
					if (k.setting) {
						module.options.get(i.id).value = key;
						k.setting = false;
					}
				} else if (key == Keyboard.KEY_ESCAPE) {
					Keybinder k = (Keybinder)i;
					if (k.setting) {
						k.setting = false;
					}
				} else if (key == Keyboard.KEY_DELETE) {
					Keybinder k = (Keybinder)i;
					if (k.setting) {
						module.options.get(i.id).value = Keyboard.KEY_NONE;
						k.setting = false;
					}
				}
			} else if (i instanceof TextBox) {
				((TextBox)i).keyboardPress(key);
			}
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		for(Interactable i : interactables) {
			if (i instanceof TextBox) {
				((TextBox)i).keyTyped(key, keyChar);
			}
		}
	}
	
	public void mouseClick(int mouseX, int mouseY, int button) {
		super.mouseClick(mouseX, mouseY, button);
		
		if (!enabled || ((UIHacks)UIHandler.currentUI).subOptions.active || exist.getTime() < existFor) { return; }
		
		if (!mouseOver(mouseX, mouseY) && button == 0) {
			this.active = false;
		}
	}
	
	public void updateInteractables() {
		contentParent = newContentParent;
		
		module = ModuleManager.list().get(contentParent);
		
		interactables.clear();
		
		int index = 0;
		for(ModuleOption mo : module.options) {
			switch(mo.type) {
			case CHECKBOX:
				interactables.add(new Checkbox(this, Interactable.TYPE.CHECKBOX, module.options.get(index).name, index, module.options.get(index).description+(module.options.get(index).subOptions != null?"| |Right click for options":""), 0, 0, 200, 24));
				break;
			case SLIDER:
				interactables.add(new Slider(this, Interactable.TYPE.SLIDER, module.options.get(index).name, index, module.options.get(index).description+(module.options.get(index).subOptions != null?"| |Right click for options":""), 0, 0, 200, 24));
				break;
			case KEYBINDER:
				interactables.add(new Keybinder(this, Interactable.TYPE.KEYBINDER, module.options.get(index).name, index, module.options.get(index).description+(module.options.get(index).subOptions != null?"| |Right click for options":""), 0, 0, 200, 24));
				break;
			case TEXTBOX:
				interactables.add(new TextBox(this, Interactable.TYPE.TEXTBOX, TextBoxRel.valueOf(String.valueOf(module.options.get(index).value)), module.options.get(index).name, index, module.options.get(index).description+(module.options.get(index).subOptions != null?"| |Right click for options":"")+"|Press [RETURN] to submit", 0, 0, 192, 24));
				break;
			case BUTTON:
				interactables.add(new Toggle(this, Interactable.TYPE.BUTTON, module.options.get(index).name, index, module.options.get(index).description+(module.options.get(index).subOptions != null?"| |Right click for options":""), 0, 0, 200, 24));
				break;
			case SETTINGS:
				interactables.add(new Settings(this, Interactable.TYPE.SETTINGS, SettingsRel.valueOf(String.valueOf(module.options.get(index).value)), module.options.get(index).name, index, module.options.get(index).description+(module.options.get(index).subOptions != null?"| |Right click for options":""), 0, 0, 200, 24));
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
			
			i.offsetX = i instanceof TextBox?left+4:left;
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

	public void action(Interactable i, int mouseX, int mouseY, int button) {
		if (!enabled || !active || ((UIHacks)UIHandler.currentUI).subOptions.active || exist.getTime() < existFor) { return; }
		
		int index = i.id;
		switch(button) {
		case 0:
			if (i instanceof Checkbox) {
				try {
					boolean checked = Util.makeBoolean(module.options.get(index).value);
					module.options.get(index).value = !checked;
				} catch (Exception e) {}
			} else if (i instanceof Slider) {
				Slider s = (Slider)i;
				s.setting = true;
			} else if (i instanceof Keybinder) {
				Keybinder k = (Keybinder)i;
				k.setting = true;
			} else if (i instanceof TextBox) {
				switch (((TextBox)i).rel) {
				/*case HISTORY:
					final String text = ((TextBox)i).text;
					new Thread() {
						@Override
						public void run() {
							List<String> un = Util.usernameHistory(text);
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""));
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7cHistory - "+text+"\u00a77]"));
							for(String s : un) {
								Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77 - \u00a7e"+s));
							}
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(""));
						}
					}.start();
					((TextBox)i).text = "";
					break;
				case SPAMBOT:
					if (ChatSpamHandler.spam.contains(((TextBox)i).text)) {
						ChatSpamHandler.spam.remove(((TextBox)i).text);
					} else {
						ChatSpamHandler.spam.add(((TextBox)i).text);
					}
					((TextBox)i).text = "";
					break;*/
				case FOLLOW:
					FollowHandler.follow = ((TextBox)i).text;
					((TextBox)i).text = "";
					break;
				}
			} else if (i instanceof Settings) {
				switch (((Settings)i).rel) {
				case FRIENDS:
					UIHandler.changeUI(new UIFriends(UIHandler.currentUI));
					break;
				case MACROS:
					UIHandler.changeUI(new UIMacros(UIHandler.currentUI));
					break;
				case HISTORY:
					UIHandler.changeUI(new UIHistory(UIHandler.currentUI));
					break;
				case NUKER:
					UIHandler.changeUI(new UINuker(UIHandler.currentUI));
					break;
				case SPAMBOT:
					UIHandler.changeUI(new UIChatSpam(UIHandler.currentUI));
					break;
				case WAYPOINTS:
					UIHandler.changeUI(new UIWaypoints(UIHandler.currentUI));
					break;
				case XRAY:
					UIHandler.changeUI(new UIXray(UIHandler.currentUI));
					break;
				}
			}
			break;
		case 1:
			if (module.options.get(index).subOptions != null) {
				WindowSubOptions subOptions = ((UIHacks)UIHandler.currentUI).subOptions;
				subOptions.active = true;
				subOptions.updateContentParent(i.id);
			}
			break;
		}
	}
}
