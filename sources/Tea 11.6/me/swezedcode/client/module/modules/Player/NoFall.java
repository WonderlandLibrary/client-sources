package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.events.EventReadPacket;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;

public class NoFall extends Module {

	public NoFall() {
		super("NoFall", Keyboard.KEY_NONE, 0xFFF56C6C, ModCategory.Motion);
	}

	@EventListener
	public void onFall(EventPreMotionUpdates e) {
		if(mc.thePlayer.fallDistance > 4.0F) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		}
	}

	@EventListener
	public void onPacketSend(EventReadPacket event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
			if(mc.thePlayer.fallDistance > 3.0F) {
				packet.field_149474_g = true;
			}
		}
	}

}