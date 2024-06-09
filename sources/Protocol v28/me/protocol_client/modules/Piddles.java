package me.protocol_client.modules;

import darkmagician6.EventManager;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;

public class Piddles extends Module{
	public Piddles() {
		super("Piddles", "piddles", 0, Category.RENDER, new String[]{"dsdfsdfsdfsdghgh"});
	}
	public void onEnable(){
		Wrapper.mc().renderGlobal.loadRenderers();
	}
	public void onDisable(){
		Wrapper.mc().renderGlobal.loadRenderers();
	}
}
