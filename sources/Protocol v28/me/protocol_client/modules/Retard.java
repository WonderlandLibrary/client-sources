package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import events.EventPrePlayerUpdate;

public class Retard extends Module {
	public Retard() {
		super("Retard", "retard", Keyboard.KEY_NONE, Category.MISC, new String[]{"derp", "retard"});
	}

	public final Value<Boolean> normal = new Value<>("retard_normal", true);
	public final Value<Boolean> spin = new Value<>("retard_spin", false);
	public final Value<Boolean> headless = new Value<>("retard_headless", false);
	public final Value<Boolean> swing = new Value<>("retard_swing", false);
	

	public static int spincount = 0;

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onEvent(EventPrePlayerUpdate event) {
		if (spin.getValue()) {
			for (int i = 0; i < 90; i++) {
				spincount--;
			}
		}
		if(swing.getValue()){
			Wrapper.sendPacket(new C0APacketAnimation());
		}
		Wrapper.getPlayer().rotationPitch += 0.00001;
	}

	@EventTarget
	public void onPacket(EventPacketSent event) {
		Packet packet = event.getPacket();
		if ((packet instanceof C03PacketPlayer)) {
			C03PacketPlayer p = (C03PacketPlayer) packet;
			if (spin.getValue()) {
				p.yaw = spincount;
			}
			if (normal.getValue()) {
				p.yaw = (float) (360 * Math.random());
				p.pitch = (float) (360 * Math.random());
			}
			if (headless.getValue()) {
				if ((p.pitch > 80.0F) || (p.pitch < -80.0F)) {
					p.pitch = 180.0F;
				} else {
					p.pitch = (180.0F - p.pitch);
				}
			}
		}
	}
}
