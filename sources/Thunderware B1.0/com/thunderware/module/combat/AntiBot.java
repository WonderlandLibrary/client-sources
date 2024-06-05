package com.thunderware.module.combat;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventUpdate;
import com.thunderware.module.ModuleBase;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot extends ModuleBase {

	public AntiBot() {
		super("AntiBot", Keyboard.KEY_J, Category.COMBAT);
	}
	private static CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<Entity>();
	
	public void onEvent(Event event) {
		if(event instanceof EventUpdate) {
			
			CopyOnWriteArrayList<Entity> entList = new CopyOnWriteArrayList<Entity>();
			for(Entity ent : mc.theWorld.loadedEntityList) {
					/*
					 * AntiBot (Hypixel) Removes Invisibles
					*/
					if(!ent.isInvisible() && ent != mc.thePlayer && ent instanceof EntityPlayer)
						entList.add(ent);
			}
			entities = entList;
		}
	}
	
	public void onSkipEvent(Event event) {
		if(event instanceof EventUpdate) {

			CopyOnWriteArrayList<Entity> entList = new CopyOnWriteArrayList<Entity>();
			for(Entity ent : mc.theWorld.loadedEntityList) {
				entList.add(ent);
			}
			entities = entList;
			//System.out.println(entList.get(0));
		}
	}
	
	public static CopyOnWriteArrayList<Entity> getEntities() {
		return entities;
	}
	
}
