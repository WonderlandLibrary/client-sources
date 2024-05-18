package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class Greeter extends Module
{
	public Greeter()
	{
		super("greeter", "Broken", "Thanks Sleepinqq, not done tho.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(this.isEnabled()){
		}}
		}

