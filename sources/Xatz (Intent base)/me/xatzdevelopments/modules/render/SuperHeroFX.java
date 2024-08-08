package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.ui.FXManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SuperHeroFX extends Module {
	
	public EntityPlayerMP entity;
	//public FXManager fxManager;
	
	public SuperHeroFX() {
		super("SuperHeroFX", Keyboard.KEY_NONE, Category.RENDER, "test");
		//this.fxManager = new FXManager();
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}

	public void onEvent(Event e) {
		if(e instanceof EventRenderGUI) {
	        Xatz.fxManager.onRender();
		}
		if(e instanceof EventUpdate) {
			Xatz.fxManager.onTick();
		}
	}
	
	
}
