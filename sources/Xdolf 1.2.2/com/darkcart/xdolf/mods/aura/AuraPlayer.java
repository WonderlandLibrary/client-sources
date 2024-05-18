package com.darkcart.xdolf.mods.aura;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

public class AuraPlayer extends Module
{
	public AuraPlayer()
	{
		super("Players", "Players hit mode for Kill Aura.", Keyboard.KEYBOARD_SIZE, Category.COMBAT);
		setState(true);
	}
}
