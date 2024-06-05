package digital.rbq.addon.api.packet.client;

import net.minecraft.network.play.client.C0APacketAnimation;
import digital.rbq.addon.api.packet.AddonPacket;

public class PacketAnimation extends AddonPacket {

	public PacketAnimation(C0APacketAnimation packet) {
		super(packet);
	}

	public PacketAnimation() {
		super(new C0APacketAnimation());
	}
}
