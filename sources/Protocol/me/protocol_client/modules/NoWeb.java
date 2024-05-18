package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class NoWeb extends Module{

	public NoWeb() {
		super("NoWeb", "noweb", Keyboard.KEY_NONE, Category.MOVEMENT, new String[]{"dsdfsdfsdfsdghgh"});
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		mc.thePlayer.isInWeb = false;
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
}
