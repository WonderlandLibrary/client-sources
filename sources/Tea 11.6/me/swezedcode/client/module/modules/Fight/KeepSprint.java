package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.events.EventReadPacket;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class KeepSprint extends Module {

	public KeepSprint() {
		super("KeepSprint", Keyboard.KEY_NONE, 0xFF, ModCategory.Fight);
	}

	@EventListener
	public void onPre(EventReadPacket e) {
		if (((e.getPacket() instanceof C0BPacketEntityAction)) && ((e.getPacket() instanceof C02PacketUseEntity))) {
			C0BPacketEntityAction packet = (C0BPacketEntityAction) e.getPacket();
			C02PacketUseEntity useEntity = (C02PacketUseEntity) e.getPacket();
			if (useEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
				packet.func_180764_b();
				if (packet.func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
					e.setCancelled(true);
				}
			}
		}
	}

}
