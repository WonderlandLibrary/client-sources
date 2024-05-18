package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;

public class entityStep extends Module
{
	
	public static Value entityStep = new Value("Entity Step");
	
	public entityStep()
	{
		super("entityStep", "NoCheat+", "Increase step height for on entities (buggy).", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}

	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if(player.ridingEntity != null && player.ridingEntity.stepHeight != (int) entityStep.getValue()) {
				player.ridingEntity.stepHeight = (int) entityStep.getValue();
			}
		}
	}
	
	public void onDisable() {
		try {
			if(Wrapper.getPlayer().ridingEntity != null) {
				Wrapper.getPlayer().ridingEntity.stepHeight = 1;
			}
		}catch(Exception ex){}
	}
}