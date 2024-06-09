package me.protocol_client.modules;

import org.lwjgl.input.Keyboard;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;

public class Commands extends Module{
	public Commands() {
		super("Commands", "commands", 0, Category.PLAYER, new String[]{"dsdfsdfsdfsdghgh"});
		setShowing(false);
	}
}
