package Snipeware.module.impl.player;

import java.awt.Color;

import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Zoot extends Module {

	public Zoot() {
		super("Zoot", 0, ModuleCategory.PLAYER);
	}

	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
}
