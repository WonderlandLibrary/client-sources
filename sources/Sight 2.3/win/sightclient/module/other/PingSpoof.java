package win.sightclient.module.other;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;

public class PingSpoof extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Delay", "Cancel"});
	private NumberSetting delay = new NumberSetting("Delay", this, 200, 10, 1000, true);
	
	public PingSpoof() {
		super("PingSpoof", Category.OTHER);
	}

	@Override
	public void updateSettings() {
		this.delay.setVisible(this.mode.getValue().equalsIgnoreCase("Delay"));
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventPacket) {
			final EventPacket ep = (EventPacket) e;
			
			if (ep.getPacket() instanceof C00PacketKeepAlive) {
				e.setCancelled();
				
				if (this.mode.getValue().equalsIgnoreCase("Delay")) {
					Thread spoof = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(delay.getValueLong());
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (mc.thePlayer != null) {
								mc.thePlayer.sendQueue.addToSendQueueNoEvent(ep.getPacket());
							}
						}
					});
					spoof.start();
				}
			}
		}
	}
}
