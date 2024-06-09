package com.kilo.mod.all;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.CombatUtil;
import com.kilo.mod.util.ItemValue;
import com.kilo.render.Render;
import com.kilo.util.Util;

public class KillAura extends Module {
	
	private float[] oldRotation = new float[] {0, 0};
	private com.kilo.util.Timer timer = new com.kilo.util.Timer();
	
	private List<Integer> swords = new CopyOnWriteArrayList<Integer>();
	
	public KillAura(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Range", "Attack range", Interactable.TYPE.SLIDER, 4, new float[] {1, 6}, true);
		addOption("FOV", "Attack FOV", Interactable.TYPE.SLIDER, 180, new float[] {20, 180}, false);
		addOption("Speed", "Attack speed", Interactable.TYPE.SLIDER, 1, new float[] {1, 10}, true);
		addOption("Exist Time", "Time entity has to exist before targetting", Interactable.TYPE.SLIDER, 0.5f, new float[] {0, 10}, true);

		addOption("Animals", "Attack animals", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Monsters", "Attack monsters", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Players", "Attack other players", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Others", "Attack all other entities", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Invisibles", "Ignore invisible entities", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Walls", "Hit through walls", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Block", "Block your sword near entities", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Silent", "Remove kill aura aimbot", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Click", "Change to click aura", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("ESP", "Show targetted entity", Interactable.TYPE.CHECKBOX, true, null, false, ModuleManager.getColorOptions());
		addOption("Teams", "Ignore all team members for targetting", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public void onPlayerPreUpdate() {
		if (Util.makeBoolean(getOptionValue("click"))) {
			return;
		}
		look();
	}
	
	public void onPlayerUpdate() {
		if (Util.makeBoolean(getOptionValue("click"))) {
			return;
		}
		attackPlayer();
	}
	
	public void onPlayerPostUpdate() {
		if (Util.makeBoolean(getOptionValue("click"))) {
			return;
		}
		if (Util.makeBoolean(getOptionValue("block"))) {
			if (getEntityToAttack() != null) { 
				if (mc.thePlayer.getCurrentEquippedItem() != null) {
					if (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
					}
				}
			}
		}
		returnLook();
	}
	
	public void onPlayerAttack() {
		if (!Util.makeBoolean(getOptionValue("click"))) {
			return;
		}
		look();
		attackPlayer();
		returnLook();
	}
	
	public void look() {
		oldRotation[0] = mc.thePlayer.rotationYaw;
		oldRotation[1] = mc.thePlayer.rotationPitch;
		
		EntityLivingBase entity = getEntityToAttack();

		if (entity == null) {
			return;
		}
		
		float[] rotations = CombatUtil.getRotationToEntity(entity);
		
		mc.thePlayer.rotationYaw = rotations[0];
		mc.thePlayer.rotationPitch = rotations[1];
	}
	
	public void attackPlayer() {
		EntityLivingBase entity = getEntityToAttack();
		
		if (entity != null && timer.isTime((10-Util.makeFloat(getOptionValue("speed")))/10f)) {
			mc.thePlayer.swingItem();
			System.out.println("attack");
			mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
			mc.playerController.syncCurrentPlayItem();
		}
	}
	
	public void returnLook() {
		if (!Util.makeBoolean(getOptionValue("silent"))) {
			return;
		}

		mc.thePlayer.rotationYaw = oldRotation[0];
		mc.thePlayer.rotationPitch = oldRotation[1];
	}

	public void render3D() {
		if (!Util.makeBoolean(getOptionValue("esp"))) { return; }
		
		EntityLivingBase entity = getEntityToAttack();
		
		if (entity == null) {
			return;
		}
		
		try {
			int r = Util.makeInteger(getSubOptionValue("esp", "red"));
			int g = Util.makeInteger(getSubOptionValue("esp", "green"));
			int b = Util.makeInteger(getSubOptionValue("esp", "blue"));
			int a = Util.makeInteger(getSubOptionValue("esp", "opacity"));
			
			Render.boxFilled(entity, new Color(r, g, b, a/2).getRGB(), new Color(r, g, b, (a/2)+128).getRGB());
		} catch (Exception e) {}
	}
	
	private EntityLivingBase getEntityToAttack() {
		float fov = Util.makeFloat(getOptionValue("fov"));
		float range = Util.makeFloat(getOptionValue("range"));
		float speed = Util.makeFloat(getOptionValue("speed"));
		float exist = Util.makeFloat(getOptionValue("exist time"));
		
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		boolean others = Util.makeBoolean(getOptionValue("others"));
		boolean invis = Util.makeBoolean(getOptionValue("invisibles"));
		boolean wall = Util.makeBoolean(getOptionValue("walls"));
		boolean teams = Util.makeBoolean(getOptionValue("teams"));
		
		return CombatUtil.getEntityNearestForAttack(range, fov, exist, animals, monsters, players, others, invis, wall, teams);
	}

	public int getWeaponValue(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemSword)) {
			return 0;
		}
		ItemSword item = (ItemSword)stack.getItem();
		int itemValue = 0;
		int id = Item.getIdFromItem(item);
		
		Item.ToolMaterial material = Item.ToolMaterial.valueOf(item.getToolMaterialName());
		
		itemValue = ItemValue.tool.valueOf(material.name()).val();
		
		if (stack.getEnchantmentTagList() == null) {
			return itemValue;
		}
		
		int enchantValue = 0;
		for(int i = 0; i < stack.getEnchantmentTagList().tagCount(); i++) {
			int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("lvl");
			int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(i).getInteger("id");
			enchantValue+= ItemValue.enchant.val(enchantID)*enchantLevel;
		}
		
		return itemValue+enchantValue;
	}
}
