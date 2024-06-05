package win.sightclient.event.events.client;

import net.minecraft.client.Minecraft;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.module.Module;
import win.sightclient.utils.management.Manager;
import win.sightclient.utils.minecraft.ServerUtils;

public class EventClientTick extends Event {
	
	@Override
	public void call() {
		if (Sight.instance.mm == null) {
			return;
		}
		
		for (Module m : Sight.instance.mm.getModules()) {
			m.updateSettings();
		}
		Manager.clientTick();
		if (Minecraft.getMinecraft().theWorld == null) {
			ServerUtils.lastConnected = "";
		}
	}
}
