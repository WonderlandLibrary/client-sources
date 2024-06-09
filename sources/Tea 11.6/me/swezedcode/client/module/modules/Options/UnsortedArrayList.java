package me.swezedcode.client.module.modules.Options;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;

public class UnsortedArrayList extends Module {

	private static UnsortedArrayList instance = new UnsortedArrayList();
	
	private boolean enabled = false;
	
	public UnsortedArrayList() {
		super("UnsortedArrayList", Keyboard.KEY_NONE, -1, ModCategory.Options);
	}
	
	@Override
	public void onEnable() {
		setEnabled(true);
	}
	
	@Override
	public void onDisable() {
		setEnabled(false);
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public static UnsortedArrayList getInstance() {
		return instance;
	}
	
}
