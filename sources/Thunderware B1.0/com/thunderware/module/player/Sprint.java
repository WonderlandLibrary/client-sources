package com.thunderware.module.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.module.ModuleBase;
import com.thunderware.settings.settings.ModeSetting;

import net.minecraft.network.play.client.C03PacketPlayer;

public class Sprint extends ModuleBase {
	
	public Sprint() {
		super("Sprint", Keyboard.KEY_M, Category.PLAYER);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			if(mc.thePlayer.moveForward > 0 && mc.thePlayer.getFoodStats().getFoodLevel() > 6)
				mc.thePlayer.setSprinting(true);
		}
	}
}
