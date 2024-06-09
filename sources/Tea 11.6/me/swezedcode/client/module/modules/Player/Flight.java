package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Flight extends Module {

	public Flight() {
		super("Flight", Keyboard.KEY_F, 0xFFFF1CE5, ModCategory.Motion);
	}

	private BooleanValue anti_fly_kick = new BooleanValue(this, "Fly Kick Bypass", "", Boolean.valueOf(true));
	
	@EventListener
	public void onFly(EventPreMotionUpdates e) {
		mc.thePlayer.capabilities.isFlying = true;
		mc.thePlayer.capabilities.setFlySpeed(0.2F);
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.capabilities.setFlySpeed(0.15F);
	}

}
