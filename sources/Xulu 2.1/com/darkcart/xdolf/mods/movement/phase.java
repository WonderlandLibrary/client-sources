package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class phase extends Module
{
	private boolean noClip;
	public phase()
	{
		super("phase", "Old", "", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Wrapper.getPlayer().noClip = true;
			Wrapper.getPlayer().fallDistance = 0;
			Wrapper.getPlayer().onGround = true;
		}
	}
}