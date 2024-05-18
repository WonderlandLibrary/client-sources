package mods.togglesprint.me.jannik.module;

import java.io.IOException;

import mods.togglesprint.com.darkmagician6.eventapi.EventManager;
import mods.togglesprint.me.jannik.files.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class Module {
		
	public static Minecraft mc = Minecraft.getMinecraft();
	
	String name;
	Category category;
	int keyBind;
	boolean enabled;
	
	public Module(String name, Category category, int keyBind) {
		this.name = name;
		this.category = category;
		this.keyBind = keyBind;
	}
	
	public Module(String name, Category category) {
		this.name = name;
		this.category = category;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public int getKeyBind() {
		return keyBind;
	}
	
	public void setKeyBind(int keyBind) {
		this.keyBind = keyBind;
		Files.saveModules();
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		onToggled();		
		this.enabled = enabled;
		Files.saveModules();
		if (enabled) {
			EventManager.register(this);
			onEnabled();
		} else {
			EventManager.unregister(this);
			onDisabled();
		}
	}
		
	public void toggleModule() {
		setEnabled(!isEnabled());
	}
	
	public void onToggled() {}
	
	public void onEnabled() {}
	
	public void onDisabled() {}
}
