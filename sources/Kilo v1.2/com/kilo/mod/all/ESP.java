package com.kilo.mod.all;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

import com.kilo.manager.HackFriendManager;
import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.CombatUtil;
import com.kilo.render.Render;
import com.kilo.util.RenderUtil;
import com.kilo.util.Util;

public class ESP extends Module {
	
	public ESP(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Animals", "Show animals", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Monsters", "Show monsters", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Neutrals", "Show neutral mobs", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Players", "Show other players", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Prop Hunt", "Show players in the server minigame \"Prop Hunt\"", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		
		List<ModuleSubOption> items = new ArrayList<ModuleSubOption>();
		items.addAll(ModuleManager.getColorOptions());
		items.add(new ModuleSubOption("Labels", "Show item name and stack size", Interactable.TYPE.CHECKBOX, false, null, false));
		
		addOption("Items", "Show item drops", Interactable.TYPE.CHECKBOX, false, null, false, items);
		addOption("View Predict", "Show the players view direction", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void render3D() {
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean neutrals = Util.makeBoolean(getOptionValue("neutrals"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		boolean prophunt = Util.makeBoolean(getOptionValue("prop hunt"));
		boolean items = Util.makeBoolean(getOptionValue("items"));
		boolean viewPredict = Util.makeBoolean(getOptionValue("view predict"));
		
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity ent = (Entity)mc.theWorld.loadedEntityList.get(i);

			int r = -1;
			int g = r;
			int b = r;
			int a = r;
			
			if (ent instanceof EntityLivingBase) {
				EntityLivingBase e = (EntityLivingBase)ent;
	
				if (CombatUtil.isDisabledEntity(e)) {
					continue;
				}
				
				if (e instanceof EntityAnimal){
					if (animals) {
						r = Util.makeInteger(getSubOptionValue("animals", "red"));
						g = Util.makeInteger(getSubOptionValue("animals", "green"));
						b = Util.makeInteger(getSubOptionValue("animals", "blue"));
						a = Util.makeInteger(getSubOptionValue("animals", "opacity"));
					}
				} else if (e instanceof EntityMob || e instanceof EntityFlying || e instanceof EntitySlime) {
					if (monsters) {
						r = Util.makeInteger(getSubOptionValue("monsters", "red"));
						g = Util.makeInteger(getSubOptionValue("monsters", "green"));
						b = Util.makeInteger(getSubOptionValue("monsters", "blue"));
						a = Util.makeInteger(getSubOptionValue("monsters", "opacity"));
					}
				} else if (e instanceof EntityAmbientCreature || e instanceof EntityWaterMob || e instanceof EntityVillager) {
					if (neutrals) {
						r = Util.makeInteger(getSubOptionValue("neutrals", "red"));
						g = Util.makeInteger(getSubOptionValue("neutrals", "green"));
						b = Util.makeInteger(getSubOptionValue("neutrals", "blue"));
						a = Util.makeInteger(getSubOptionValue("neutrals", "opacity"));
					}
				} else if (e instanceof EntityPlayer) {
					if (players) {
						r = Util.makeInteger(getSubOptionValue("players", "red"));
						g = Util.makeInteger(getSubOptionValue("players", "green"));
						b = Util.makeInteger(getSubOptionValue("players", "blue"));
						a = Util.makeInteger(getSubOptionValue("players", "opacity"));
	
						if (HackFriendManager.getHackFriend(e.getCommandSenderName()) != null) {
							float hue = (float)((Math.sin(System.nanoTime()/1000000000f)-1)/2);
							long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 0.75f, 1f))), 16);
							Color c = new Color((int)color);
							r = c.getRed();
							g = c.getGreen();
							b = c.getBlue();
							a = 128;
						}
					}
				}
				
				if (r != -1) {
					if (viewPredict) {
						Vec3 from = new Vec3(e.posX, e.posY+e.getEyeHeight(), e.posZ);
						Vec3 to = from.add(e.getLookVec());
						Render.line(from, to, new Color(r, g, b, (a/2)+128).getRGB(), 2f);
					}
				}
			} else if (ent instanceof EntityFallingBlock) {
				if (prophunt) {
					r = Util.makeInteger(getSubOptionValue("prop hunt", "red"));
					g = Util.makeInteger(getSubOptionValue("prop hunt", "green"));
					b = Util.makeInteger(getSubOptionValue("prop hunt", "blue"));
					a = Util.makeInteger(getSubOptionValue("prop hunt", "opacity"));
				}
				EntityFallingBlock e = (EntityFallingBlock)ent;
				if (r != -1) {
					Render.boxFilled(e, new Color(r, g, b, a/2).getRGB(), new Color(r, g, b, (a/2)+128).getRGB());
				}
			} else if (ent instanceof EntityItem) {
				if (items) {
					r = Util.makeInteger(getSubOptionValue("items", "red"));
					g = Util.makeInteger(getSubOptionValue("items", "green"));
					b = Util.makeInteger(getSubOptionValue("items", "blue"));
					a = Util.makeInteger(getSubOptionValue("items", "opacity"));
				}
				EntityItem e = (EntityItem)ent;

				if (r != -1) {
					if (Util.makeBoolean(getSubOptionValue("items", "labels"))) {
						double[] p = RenderUtil.entityRenderPos(e);
						Render.render3DNameTag(e.getEntityItem().getDisplayName()+(e.getEntityItem().stackSize != 1?" x"+e.getEntityItem().stackSize:""), p[0], p[1]+e.height, p[2], e.posX, e.posY, e.posZ);
					}
				}
			}
			if (r != -1) {
				Render.boxFilled(ent, new Color(r, g, b, a/2).getRGB(), new Color(r, g, b, (a/2)+128).getRGB());
			}
		}
	}
}
