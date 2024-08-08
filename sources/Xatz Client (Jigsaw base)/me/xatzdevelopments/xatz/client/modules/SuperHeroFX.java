package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.events.Event;
import me.xatzdevelopments.xatz.client.events.EventUpdate;
import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SuperHeroFX extends Module {
	
	public EntityPlayerMP entity;
	//public FXManager fxManager;
	
	public SuperHeroFX() {
		super("SuperHeroFX", Keyboard.KEY_NONE, Category.HIDDEN, "Fangle Ones favorite module"); // Render
		//this.fxManager = new FXManager();
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}

	@Override
	public void onRender() {
	        Xatz.fxManager.onRender();
		}

	@Override
	public void onUpdate(UpdateEvent event) {
			Xatz.fxManager.onTick();
		}
	}
	
