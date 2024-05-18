package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

import net.minecraft.client.entity.EntityPlayerSP;

public class EntityStep extends Module
{
	
	public static Value entityStep = new Value("Entity Step");
	
	public EntityStep()
	{
		super("EntityStep", "Increase step height for on entities (buggy).", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
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
