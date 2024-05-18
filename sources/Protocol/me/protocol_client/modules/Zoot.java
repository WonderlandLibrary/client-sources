package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Zoot extends Module {

	public Zoot() {
		super("Zoot", "zoot", 0, Category.PLAYER, new String[] { "" });
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void Event(EventPreMotionUpdates event) {
		if (!Wrapper.getPlayer().isCollidedVertically) {
			return;
		}
		if ((Wrapper.getPlayer().getHeldItem() != null) && ((Wrapper.getPlayer().getHeldItem().getItem() instanceof ItemFood))) {
			return;
		}
		for (Potion potion : Potion.potionTypes) {
			if ((potion != null) && (potion.isBadEffect())) {
				PotionEffect effect = Wrapper.getPlayer().getActivePotionEffect(potion);
				if (effect != null) {
					if (!effect.getIsPotionDurationMax()) {
						for (int index = 0; index < effect.getDuration(); index++) {
							Wrapper.sendPacket(new C03PacketPlayer(Wrapper.getPlayer().onGround));
							// for (PotionEffect effect2 :
							// Wrapper.getPlayer().getActivePotionEffects()) {
							// effect2.deincrementDuration();
							// }
						}
					}
				}
			}
		}
	}
}
