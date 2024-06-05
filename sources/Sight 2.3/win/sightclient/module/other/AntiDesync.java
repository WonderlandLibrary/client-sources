package win.sightclient.module.other;

import net.minecraft.network.play.client.C09PacketHeldItemChange;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class AntiDesync extends Module {

	private int lastSlot = -1;
	
	
	public AntiDesync() {
		super("AntiDesync", Category.OTHER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre() && this.lastSlot != -1 && this.lastSlot != mc.thePlayer.inventory.currentItem) {
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
			}
		} else if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			
			if (ep.getPacket() instanceof C09PacketHeldItemChange) {
				C09PacketHeldItemChange packet = (C09PacketHeldItemChange)ep.getPacket();
				this.lastSlot = packet.getSlotId();
			}
		}
	}
}
