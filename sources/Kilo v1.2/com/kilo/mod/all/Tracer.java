package com.kilo.mod.all;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;

import com.kilo.manager.HackFriendManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.CombatUtil;
import com.kilo.render.Render;
import com.kilo.util.Util;

public class Tracer extends Module {
	
	public Tracer(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Animals", "Show animals", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getDistanceColorOptions());
		addOption("Monsters", "Show monsters", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getDistanceColorOptions());
		addOption("Neutrals", "Show neutral mobs", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getDistanceColorOptions());
		addOption("Players", "Show players", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getDistanceColorOptions());
	}
	
	public void render3D() {
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean neutrals = Util.makeBoolean(getOptionValue("neutrals"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity ent = (Entity)mc.theWorld.loadedEntityList.get(i);
			
			if (!(ent instanceof EntityLivingBase)) {
				continue;
			}
			
			EntityLivingBase e = (EntityLivingBase)ent; 

			if (CombatUtil.isDisabledEntity(e)) {
				continue;
			}

			float min = 1;
			float max = 20;
			float distTo = Math.min(Math.max(min, mc.thePlayer.getDistanceToEntity(e)), max);
			
			int close = 0xFFFF5555;
			int far = 0xFF55FF55;
			
			Color c = new Color(-1);
			try {
				c = new Color(Util.blendColor(far, close, ((distTo-min)/(max-min))));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			int r = c.getRed();
			int g = c.getGreen();
			int b = c.getBlue();
			int a = c.getAlpha();
			
			if (e instanceof EntityAnimal){
				if (animals) {
					boolean dist = Util.makeBoolean(getSubOptionValue("animals", "distance"));
					if (!dist) {
						r = Util.makeInteger(getSubOptionValue("animals", "red"));
						g = Util.makeInteger(getSubOptionValue("animals", "green"));
						b = Util.makeInteger(getSubOptionValue("animals", "blue"));
					}
					a = Util.makeInteger(getSubOptionValue("animals", "opacity"));
					Render.tracer(mc.thePlayer, e, new Color(r, g, b, a).getRGB());
					continue;
				}
			} else if (e instanceof EntityMob || e instanceof EntityFlying || e instanceof EntitySlime) {
				if (monsters) {
					boolean dist = Util.makeBoolean(getSubOptionValue("monsters", "distance"));
					if (!dist) {
						r = Util.makeInteger(getSubOptionValue("monsters", "red"));
						g = Util.makeInteger(getSubOptionValue("monsters", "green"));
						b = Util.makeInteger(getSubOptionValue("monsters", "blue"));
					}
					a = Util.makeInteger(getSubOptionValue("monsters", "opacity"));
					Render.tracer(mc.thePlayer, e, new Color(r, g, b, a).getRGB());
					continue;
				}
			} else if (e instanceof EntityAmbientCreature || e instanceof EntityWaterMob || e instanceof EntityVillager) {
				if (neutrals) {
					boolean dist = Util.makeBoolean(getSubOptionValue("neutrals", "distance"));
					if (!dist) {
						r = Util.makeInteger(getSubOptionValue("neutrals", "red"));
						g = Util.makeInteger(getSubOptionValue("neutrals", "green"));
						b = Util.makeInteger(getSubOptionValue("neutrals", "blue"));
					}
					a = Util.makeInteger(getSubOptionValue("neutrals", "opacity"));
					Render.tracer(mc.thePlayer, e, new Color(r, g, b, a).getRGB());
					continue;
				}
			} else if (e instanceof EntityPlayer) {
				if (players) {
					boolean dist = Util.makeBoolean(getSubOptionValue("players", "distance"));
					if (!dist) {
						r = Util.makeInteger(getSubOptionValue("players", "red"));
						g = Util.makeInteger(getSubOptionValue("players", "green"));
						b = Util.makeInteger(getSubOptionValue("players", "blue"));
					}
					a = Util.makeInteger(getSubOptionValue("players", "opacity"));

					if (HackFriendManager.getHackFriend(e.getCommandSenderName()) != null) {
						float hue = (float)((Math.sin(System.nanoTime()/1000000000f)-1)/2);
						long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 0.75f, 1f))), 16);
						Color cc = new Color((int)color);
						r = cc.getRed();
						g = cc.getGreen();
						b = cc.getBlue();
						a = 255;
					}
					Render.tracer(mc.thePlayer, e, new Color(r, g, b, a).getRGB());
					continue;
				}
			}
		}
	}
}
