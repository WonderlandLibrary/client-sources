package me.valk.module;

import me.valk.help.entity.Player;
import net.minecraft.client.Minecraft;

public class ModMode<T> {

	protected T parent;
	
	private String name;
	
	public static Player p;
	public static Minecraft mc;

	public ModMode(T parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	public T getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public void onEnable(){}
	
	public void onDisable(){}
	
}
