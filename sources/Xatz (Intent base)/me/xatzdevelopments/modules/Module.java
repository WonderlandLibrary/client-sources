package me.xatzdevelopments.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.modules.movement.Fly;
import me.xatzdevelopments.notifications.Notification;
import me.xatzdevelopments.notifications.NotificationManager;
import me.xatzdevelopments.notifications.NotificationType;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.KeybindSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;
import net.minecraft.client.Minecraft;

public class Module {

	public Minecraft mc = Minecraft.getMinecraft();
	public String name;
	public String description;
	public String addonText;
	public boolean toggled;
    public boolean isInteracting;
	
	public Category category;
	public boolean expanded;
	public int index;
	public List<Setting> settings = new ArrayList<Setting>();
	public KeybindSetting keycode;
	
	public Module(String name, int key, Category c, String description) {
		this.name = name;
		this.description = description;
		this.keycode = new KeybindSetting(0);
		keycode.code = key;
		this.category = c;
		this.addSettings(keycode);
	}
	
	public void addSettings(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
		this.settings.sort(Comparator.comparingInt(s -> s == keycode ? 1 : 0));
	}
	
	public String getAddonText() {
		return this.addonText;
	}
	
	public void setAddonText() {
		
	}
	
	public ModeSetting getModeSetting(String name) {
		for (Setting settings : this.settings) {
			if(settings instanceof ModeSetting && name == settings.name) {
				return (ModeSetting) settings;
			}
		}
		System.err.println("Setting: " + name + " was not found");
		return null;	
	}
	
	public NumberSetting getNumberSetting(String name) {
		for (Setting settings : this.settings) {
			if(settings instanceof NumberSetting && name == settings.name) {
				return (NumberSetting) settings;
			}
		}
		System.err.println("Setting: " + name + " was not found");
		return null;	
	}
	
	public BooleanSetting getBooleanSetting(String name) {
		for (Setting settings : this.settings) {
			if(settings instanceof BooleanSetting && name == settings.name) {
				return (BooleanSetting) settings;
			}
		}
		System.err.println("Setting: " + name + " was not found");
		return null;	
	}
	
	
	public boolean isEnabled() {
		return toggled;
	}
	
	public int getKey() {
		return keycode.code;
	}
	
	public void onEvent(Event e) {
		
	}
	
	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			onEnable();
			if(Xatz.getModuleByName("Notifications").getBooleanSetting("OnEnable").isEnabled() && !Fly.overridenotification) {
				NotificationManager.show(new Notification(NotificationType.INFO, name, name + " was enabled", 1));
			} else {
				NotificationManager.show(new Notification(NotificationType.INFO, name, "Real fly men", 1));
			}
		}else {
			onDisable();
			if(Xatz.getModuleByName("Notifications").getBooleanSetting("OnDisable").isEnabled()) {
				NotificationManager.show(new Notification(NotificationType.INFO, name, name + " was disabled", 1));
			}
		}
	}
	
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public enum Category {
		COMBAT("COMBAT", 0, "Combat"), 
        MOVEMENT("MOVEMENT", 1, "Movement"), 
        PLAYER("PLAYER", 2, "Player"), 
        WORLD("WORLD", 3, "World"), 
        RENDER("RENDER", 4, "Render"),
        EXPLOITS("EXPLOITS", 5, "Exploits"),
        MINIGAMES("MINIGAMES", 6, "Minigames");
		//SETTINGS("SETTINGS", 7, "Settings");
		
		public String name;
		public int moduleIndex;
		
		 private Category(final String s, final int n, final String name) {
	            this.name = name;
	        }
	}

	public void onRender() {

	}
	
}
