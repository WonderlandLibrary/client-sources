package win.sightclient.event.events.client;

import net.minecraft.network.Packet;
import win.sightclient.cmd.commands.exploit.PluginsCommand;
import win.sightclient.event.Event;
import win.sightclient.utils.management.Manager;

public class EventPacket extends Event {

	private Packet packet;
	private boolean sending;
	
	public EventPacket(Packet packet, boolean sending) {
		this.packet = packet;
		this.sending = sending;
	}
	
	@Override
	public void call() {
		super.call();
		Manager.onPacket(this);
		if (this.isRecieving() && PluginsCommand.scanning) {
			PluginsCommand.onPacket(this.getPacket());
		}
	}
	
	public boolean isSending() {
		return this.sending;
	}
	
	public boolean isRecieving() {
		return !this.sending;
	}
	
	public Packet getPacket() {
		return this.packet;
	}
}
