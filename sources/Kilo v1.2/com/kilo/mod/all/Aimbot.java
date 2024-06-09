package com.kilo.mod.all;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.CombatUtil;
import com.kilo.util.Util;

public class Aimbot extends Module {

	public Aimbot(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Range", "Maximum distance to target entities at", Interactable.TYPE.SLIDER, 5, new float[] {1, 20}, true);
		addOption("FOV", "Maximum field of view to target entities within", Interactable.TYPE.SLIDER, 180, new float[] {20, 180}, false);

		addOption("Animals", "Target animals", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Monsters", "Target monsters", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Players", "Target players", Interactable.TYPE.CHECKBOX, true, null, false);

		addOption("Bow", "Only target entities when a bow is in use", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Click", "Only target entities when the mouse is clicked", Interactable.TYPE.CHECKBOX, false, null, false);
		addOption("Teams", "Ignore all team members for targetting", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public void onPlayerPreUpdate() {
		boolean bow = Util.makeBoolean(getOptionValue("bow"));
		boolean click = Util.makeBoolean(getOptionValue("click"));
		
		if (click || bow) {
			return;
		}
		
		faceEntity();
	}
	
	public void onPlayerUpdate() {
		boolean bow = Util.makeBoolean(getOptionValue("bow"));
		float range = Util.makeFloat(getOptionValue("range"));
		
		if (!bow) {
			return;
		}
		
		if (mc.thePlayer.getItemInUse() == null) {
			return;
		}
		
		if (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
			return;
		}
		
		if (mc.thePlayer.getItemInUseDuration() == 0) {
			return;
		}
		float oldRange = range;
		
		options.get(getOption("range")).value = oldRange*10;
		faceEntity();
		options.get(getOption("range")).value = oldRange;
	}
	
	public void onPlayerAttack() {
		boolean click = Util.makeBoolean(getOptionValue("click"));
		
		if (!click) {
			return;
		}
		
		faceEntity();
	}
	
	public void faceEntity() {
		float fov = Util.makeFloat(getOptionValue("fov"));
		float range = Util.makeFloat(getOptionValue("range"));
		boolean animals = Util.makeBoolean(getOptionValue("animals"));
		boolean monsters = Util.makeBoolean(getOptionValue("monsters"));
		boolean players = Util.makeBoolean(getOptionValue("players"));
		boolean teams = Util.makeBoolean(getOptionValue("teams"));
		
		EntityLivingBase entity = CombatUtil.getEntityNearest(range, fov, 0, true, animals, monsters, players, false, teams);

		if (entity == null) {
			return;
		}
		
		float[] rotations = CombatUtil.getRotationToEntity(entity);
		
		mc.thePlayer.rotationYaw = rotations[0];
		mc.thePlayer.rotationPitch = rotations[1];
	}
}
