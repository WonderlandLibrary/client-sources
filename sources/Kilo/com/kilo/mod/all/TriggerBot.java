package com.kilo.mod.all;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class TriggerBot extends Module {
	
	private Timer timer = new Timer();

	public TriggerBot(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Speed", "Attack speed", Interactable.TYPE.SLIDER, 1, new float[] {1, 10}, true);

		addOption("Random", "Change to random triggerbot", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Animals", "Attack animals", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Monsters", "Attack monsters", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Players", "Attack other players", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Block", "Block with a sword", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Teams", "Ignore all team members for targetting", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public void onPlayerUpdate() {
		float speed = Util.makeFloat(getOptionValue("speed"));
		
		boolean random = Util.makeBoolean(getOptionValue("random"));
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		boolean teams = Util.makeBoolean(getOptionValue("teams"));

		Random rand = new Random();
		
		speed = 1+rand.nextInt(4);
		
		if (timer.isTime(speed/10f)) {
			if (mc.objectMouseOver == null || mc.objectMouseOver.entityHit == null) {
				return;
			}
			
			Entity ent = mc.objectMouseOver.entityHit;
			
			if (!(ent instanceof EntityLivingBase)) {
				return;
			}
			
			EntityLivingBase entity = (EntityLivingBase)ent;
	
			if (entity instanceof EntityAnimal){
				if (!animals) {
					return;
				}
			} else if (entity instanceof EntityMob || entity instanceof EntityFlying || entity instanceof EntitySlime) {
				if (!monsters) {
					return;
				}
			} else if (entity instanceof EntityPlayer) {
				if (!players) {
					return;
				}
			} else {
				return;
			}
			
			if (entity != null) {
				if (teams && entity instanceof EntityPlayer && mc.theWorld.getScoreboard() != null && mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getCommandSenderName()) != null && mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getDisplayName().getUnformattedText()).isSameTeam(mc.theWorld.getScoreboard().getPlayersTeam(entity.getDisplayName().getUnformattedText()))) {
					return;
				}
				mc.thePlayer.swingItem();
				mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
				mc.playerController.syncCurrentPlayItem();
				timer.reset();
			}
		}
	}
}
