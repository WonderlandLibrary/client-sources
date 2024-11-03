package net.silentclient.client.mods;

import net.silentclient.client.gui.animation.SimpleAnimation;

public enum ModCategory {
	MODS("Mods"), SETTINGS("Settings"), CONFIGS("Configs"), PLUS("Premium");
	private final String name;
	SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
	float scrollY = 0;

	ModCategory(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
