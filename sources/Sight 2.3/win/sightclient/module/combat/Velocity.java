package win.sightclient.module.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.utils.TimerUtils;

public class Velocity extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Vanilla", "AGC"});
	private TimerUtils timer = new TimerUtils();
	
	public Velocity() {
		super("Velocity", Category.COMBAT);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (ep.isRecieving()) {
				switch (this.mode.getValue()) {
					case "Hypixel":
						if (ep.getPacket() instanceof S12PacketEntityVelocity || ep.getPacket() instanceof S27PacketExplosion) {
							ep.setCancelled();
						}
						break;
					case "Vanilla":
						if (ep.getPacket() instanceof S12PacketEntityVelocity) {
							ep.setCancelled();
						}
						break;
					case "AGC":
			            if (ep.getPacket() instanceof S12PacketEntityVelocity){
			                if (!timer.hasReached(3000)){
			                	ep.setCancelled();
			                }else{
			                    timer.reset();
			                }
			            }
						break;
				}
			}
		}
	}
}
