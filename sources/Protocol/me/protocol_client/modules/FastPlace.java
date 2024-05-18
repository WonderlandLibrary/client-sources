package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class FastPlace extends Module {
	public FastPlace() {
		super("FastPlace", "fastplace", Keyboard.KEY_NONE, Category.PLAYER, new String[] { "dsdfsdfsdfsdghgh" });
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		Minecraft.getMinecraft().rightClickDelayTimer = 0;
	}
}
