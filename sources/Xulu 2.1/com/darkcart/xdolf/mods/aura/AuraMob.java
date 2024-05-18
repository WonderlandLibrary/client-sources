package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class AuraMob extends Module
{
	public AuraMob()
	{
		super("mobs", "Aura", "Mobs hit mode for Kill Aura.", Keyboard.KEYBOARD_SIZE, Category.COMBAT);
	}
}