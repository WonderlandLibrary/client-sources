package com.thunderware.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.module.ModuleBase;
import com.thunderware.settings.settings.ModeSetting;

import net.minecraft.network.play.client.C03PacketPlayer;

public class Flight extends ModuleBase {

	public ModeSetting mode;
	
	public Flight() {
		super("Flight", Keyboard.KEY_V, Category.MOVEMENT);
		ArrayList<String> modes = new ArrayList<>();
		modes.add("Smooth");
		modes.add("BlocksMC");
		modes.add("Vanilla");
		mode = new ModeSetting("Mode", modes);
		addSettings(mode);
	}
	public static boolean hurt = false;
	
	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			setSuffix(mode.getCurrentValue());
			if(mode.getCurrentValue().equalsIgnoreCase("Smooth")) {
				if(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
					mc.thePlayer.setSpeed(0.5f);
				}else {
					mc.thePlayer.motionX /= 2;
					mc.thePlayer.motionZ /= 2;
				}
				if(mc.gameSettings.keyBindJump.isKeyDown())
					mc.thePlayer.motionY = 0.4;
				else if(mc.gameSettings.keyBindSneak.isKeyDown()) {
					mc.thePlayer.motionY = -0.4;
				} else
					mc.thePlayer.motionY = 0;
			} else {
				if(mc.thePlayer.hurtTime > 0.1)
					hurt = true;
				if(hurt) {
					if(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
						mc.thePlayer.setSpeed(1.2f);
					} else {
						mc.thePlayer.motionX = 0;
						mc.thePlayer.motionZ = 0;
					}
					if(mc.gameSettings.keyBindJump.isKeyDown())
						mc.thePlayer.motionY = 0.4;
					else if(mc.gameSettings.keyBindSneak.isKeyDown()) {
						mc.thePlayer.motionY = -0.4;
					} else
						mc.thePlayer.motionY = 0;
				}
			}
		}
	}
	
	public void onEnable() {
		switch(mode.getCurrentValue()) {
			case "BlocksMC":
				if(mc.thePlayer.onGround) {
					mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY + 3.0002,mc.thePlayer.posZ,false));
					mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,false));
					mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ,true));
				}else {
					hurt = true;
				}
				break;
			case "Vanilla":
				hurt = true;
				break;
		}
	}
	
	public void onDisable() {
		mc.thePlayer.motionX = 0;
		hurt = false;
		mc.thePlayer.motionZ = 0;
	}

}
