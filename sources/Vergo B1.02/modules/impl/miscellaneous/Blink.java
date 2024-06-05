package xyz.vergoclient.modules.impl.miscellaneous;

import java.util.concurrent.CopyOnWriteArrayList;

import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;

public class Blink extends Module {

	public Blink() {
		super("Blink", Category.MISCELLANEOUS);
	}
	
	public static CopyOnWriteArrayList<Packet> packets = new CopyOnWriteArrayList<>();
	
	@Override
	public void onEnable() {
		packets.clear();
	}
	
	@Override
	public void onDisable() {
		
		for (Packet p : packets) {
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
		}
		packets.clear();
		
	}
	
	public void sendPacket(EventSendPacket e) {
		if (!e.isCanceled() && !(e.packet instanceof C00Handshake) && !(e.packet instanceof C00PacketLoginStart)) {
			packets.add(e.packet);
			e.setCanceled(true);
		}
	}
}
