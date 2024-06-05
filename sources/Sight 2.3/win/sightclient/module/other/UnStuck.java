package win.sightclient.module.other;

import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.TimerUtils;

public class UnStuck extends Module {

	private int setBack = 0;
	private TimerUtils timer = new TimerUtils();
	
	public UnStuck() {
		super("UnStuck", Category.OTHER);
	}

	@Override
	public void onEnable() {
		this.setBack = 0;
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventFlag) {
			if (setBack > 5 && !timer.hasReached(500)) {
				e.setCancelled();
			}
			this.setBack++;
			timer.reset();
		} else if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (ep.isSending() && ep.getPacket() instanceof C03PacketPlayer) {
				if (setBack > 5 && !timer.hasReached(500)) {
					ep.setCancelled();
				} else if (timer.hasReached(500)) {
					this.setBack = 0;
				}
			}
		}
	}
}
