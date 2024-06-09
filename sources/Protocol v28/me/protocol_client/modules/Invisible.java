package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Invisible extends Module{

	public Invisible() {
		super("AntiInvis", "antiinvis", 0, Category.RENDER, new String[]{"dsdfsdfsdfsdghgh"});
		setShowing(false);
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
