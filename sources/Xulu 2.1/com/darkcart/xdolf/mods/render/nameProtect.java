package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;

public class nameProtect extends Module {

	
	public static String newName = "§7§kALALoWIWOWDKFMSDOSMCASW";
	
	public nameProtect() {
		super("nameProtect", "Broken", "Replaces your Minecraft Name to a obfuscated name.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.RENDER);
	}

	@Override
	public void onUpdate(EntityPlayerSP player) {
		if (isEnabled()) {
		}
	}

}
