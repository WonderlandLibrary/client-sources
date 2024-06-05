package win.sightclient.module.player;

import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.minecraft.ChatUtils;

public class FastEat extends Module {

	private int stage;
	
	public FastEat() {
		super("FastEat", Category.PLAYER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre()) {
				if (this.isEating()) {
					ChatUtils.sendMessage(this.stage + "");
					if (this.stage == 0) {
						for (int i = 0; i < 8; i++) {
							mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
						}
					} else if (this.stage == 1 || this.stage == 2) {
						for (int i = 0; i < 8; i++) {
							mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.onGround));
						}
					} else if (this.stage == 8) {
						for (int i = 0; i < 8; i++) {
							mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
						}
					}
					
					this.stage++;
				} else {
					this.stage = 0;
				}
			}
		}
	}
	
	private boolean isEating() {
		return mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.isUsingItem() && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood;
	}
}
