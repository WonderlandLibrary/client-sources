package me.protocol_client.modules;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;

public class Weather extends Module{
	
	public Weather() {
		super("Weather", "weather", Keyboard.KEY_NONE, Category.WORLD, new String[]{"dsdfsdfsdfsdghgh"});
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
}
