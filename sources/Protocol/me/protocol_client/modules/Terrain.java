package me.protocol_client.modules;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Terrain extends Module{
	public Terrain() {
		super("Terrain", "terrain", 0, Category.WORLD, new String[]{"dsdfsdfsdfsdghgh"});
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
	}
}
