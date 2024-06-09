package me.valk.agway.modules.other;

import java.awt.Color;

import me.valk.agway.AgwayClient;
import me.valk.event.EventListener;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.other.EventTick;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class PingSpoofMod extends Module {

	public PingSpoofMod() {
		super(new ModData("PingSpoof", 0, new Color(64, 10, 220)), ModType.OTHER);
	}
	
	@EventListener
	public void onEvent(EventPacket e) {
		if(e.getPacket() instanceof C00PacketKeepAlive)
		   e.setCancelled(true);
	}

}