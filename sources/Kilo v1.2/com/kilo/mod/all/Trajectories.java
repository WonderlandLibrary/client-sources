package com.kilo.mod.all;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.render.Render;
import com.kilo.util.RenderUtil;
import com.kilo.util.Util;

public class Trajectories extends Module {
	
	private final int[] id = new int[] {				261,	344,	368,			384,			346,					373,		332};
	private final float[] drag = new float[] {			0.99f,	0.99f,	0.99f,			0.99f,			0.92f,					0.99f,		0.99f};
	private final float[] dragWater = new float[] {		0.6f,	0.8f,	0.8f,			0.8f,			0f,						0.5f,		0.8f};
	private final float[] grav = new float[] {			0.05f,	0.03f,	0.03f,			0.07f,			0.03999999910593033f,	0.05f,		0.03f};
	private final float[] pitchOffset = new float[] {	0f,		0f,		0f,				-20f,			0f,						-20f,		0f};
	private final float[] motionMult = new float[] {	1f,		0.4f,	0.4f,			0.4f,			0.4f,					0.4f,		0.4f};
	private final float[] multiplier = new float[] {	1.5f,	1.5f,	1.5f,			0.7f,			1.5f, 					0.5f, 		1.5f};
	private final String[] name = new String[] {		"bow",	"egg",	"ender pearl", 	"exp bottle", 	"fishing rod", 			"potion", 	"snowball"};
	private final Class[] classes = new Class[] {EntityArrow.class, EntityEgg.class, EntityEnderPearl.class, EntityExpBottle.class, EntityFishHook.class, EntityPotion.class, EntitySnowball.class};
	
	public Trajectories(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Prediction", "Show a held item trajectory prediction line", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Projectiles", "Show in flight projectile trajectory prediction lines", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Arrow Criticals", "Show all critical arrow trajectory prediction lines", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Other Players", "Show other players' held item trajectory prediction lines", Interactable.TYPE.CHECKBOX, true, null, false);
		
		addOption("Bow", "Show bow and arrow trajectory lines", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Egg", "Show egg trajectory lines", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Ender Pearl", "Show ender pearl trajectory lines", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Exp Bottle", "Show exp bottle trajectory lines", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Fishing Rod", "Show fishing rod trajectory lines", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Potion", "Show splash potion trajectory lines", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
		addOption("Snowball", "Show snowball trajectory lines", Interactable.TYPE.CHECKBOX, false, null, false, ModuleManager.getColorOptions());
	}

	public void render3D() {
		boolean prediction = Util.makeBoolean(getOptionValue("prediction"));
		boolean projectiles = Util.makeBoolean(getOptionValue("projectiles"));
		
		if (prediction) {
			if (Util.makeBoolean(getOptionValue("other players"))) {
				for(int i = 0; i < mc.theWorld.playerEntities.size(); i++) {
					predictionLine((EntityPlayer)mc.theWorld.playerEntities.get(i));
				}
			} else {
				predictionLine(mc.thePlayer);
			}
		}
		
		if (projectiles) {
			projectileLine();
		}
	}
	
	public void predictionLine(EntityPlayer e) {
		ItemStack held = e.getCurrentEquippedItem();
		
		if (held == null) {
			return;
		}
		
		int i = Item.getIdFromItem(held.getItem());
		int index = -1;
		for(int a = 0; a < id.length; a++) {
			if (id[a] == i) {
				index = a;
				break;
			}
		}
		
		if (index == -1) {
			return;
		}
		
		boolean on = Util.makeBoolean(getOptionValue(name[index]));
		
		if (!on) {
			return;
		}
		
		String colorOption = name[index];
		
		if (!Util.makeBoolean(getOptionValue(colorOption))) {
			return;
		}
		
		float arrowPow = 0;
		
		if (held.getItem() instanceof ItemBow) {
			float pull = e.getItemInUseCount();
	        float var7 = (float)(int)(held.getMaxItemUseDuration() - pull) / 20.0F;
	        var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
	        pull = var7;
	        
			if (pull >= 1 && e.getItemInUseCount() > 0) {
				if (Util.makeBoolean(getOptionValue("arrow criticals"))) {
					colorOption = "arrow criticals";
				}
				pull = 1;
			}
			
			arrowPow = pull;
		}
		
		if (arrowPow == 0) {
			arrowPow = 0.5f;
		}
		arrowPow = MathHelper.clamp_float(arrowPow, 0f, 1f);
		
		int r = Util.makeInteger(getSubOptionValue(colorOption, "red"));
		int g = Util.makeInteger(getSubOptionValue(colorOption, "green"));
		int b = Util.makeInteger(getSubOptionValue(colorOption, "blue"));
		int a = Util.makeInteger(getSubOptionValue(colorOption, "opacity"));
		
		int color = new Color(r, g, b, a).getRGB();
		
		double[] pos = RenderUtil.entityWorldPos(e);
		
		double x = pos[0]-(double)(MathHelper.cos(e.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		double y = pos[1]+e.getEyeHeight()-0.10000000149011612D;
		double z = pos[2]-(double)(MathHelper.sin(e.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);

		double mx = ((double)(-MathHelper.sin(e.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(e.rotationPitch / 180.0F * (float)Math.PI)*motionMult[index]));
		double my = ((double)(-MathHelper.sin((e.rotationPitch+pitchOffset[index]) / 180.0F * (float)Math.PI)*motionMult[index]));
		double mz = ((double)(MathHelper.cos(e.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(e.rotationPitch / 180.0F * (float)Math.PI)*motionMult[index]));
		
		float var9 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
		mx /= (double)var9;
		my /= (double)var9;
		mz /= (double)var9;
		
		mx *= multiplier[index]*(2*arrowPow);
		my *= multiplier[index]*(2*arrowPow);
		mz *= multiplier[index]*(2*arrowPow);
		
		Render.renderTrail(x, y, z, mx, my, mz, drag[index], dragWater[index], grav[index], color, true);
	}
	
	public void projectileLine() {
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity e = (Entity)mc.theWorld.loadedEntityList.get(i);
			
			double x = e.posX;
			double y = e.posY;
			double z = e.posZ;
			double mx = e.motionX;
			double my = e.motionY;
			double mz = e.motionZ;

			int index = -1;
			for(int a = 0; a < classes.length; a++) {
				if (classes[a].isInstance(e)) {
					index = a;
					break;
				}
			}
			
			if (index == -1) {
				continue;
			}
			
			String colorOption = name[index];

			if (e instanceof EntityArrow) {
				if (((EntityArrow)e).getIsCritical()) {
					colorOption = "Arrow Criticals";
				}
			}
			
			int r = Util.makeInteger(getSubOptionValue(colorOption, "red"));
			int g = Util.makeInteger(getSubOptionValue(colorOption, "green"));
			int b = Util.makeInteger(getSubOptionValue(colorOption, "blue"));
			int a = Util.makeInteger(getSubOptionValue(colorOption, "opacity"));
			
			int color = new Color(r, g, b, a).getRGB();
			
			Render.renderTrail(x, y, z, mx, my, mz, drag[index], dragWater[index], grav[index], color, false);
		}
	}
}
