package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventReadPacket;
import net.minecraft.network.play.client.C03PacketPlayer;

public class StopPackets extends Module {

	public StopPackets() {
		super("StopPackets", Keyboard.KEY_NONE, 0xFFFA9614, ModCategory.Player);
	}

	@EventListener
	public void stopPackets(EventMotion e) {
		mc.timer.timerSpeed = 0.1F;
		mc.thePlayer.setSpeed(0.001F);
	}
	
	@EventListener
	public void stopPackets(EventReadPacket e) {
		if(e.getPacket() instanceof C03PacketPlayer) {
			C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
			if(mc.thePlayer.onGround) {
				packet.field_149474_g = true;
			}
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1.0F;
	}
	
}
