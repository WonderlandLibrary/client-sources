package Squad.Events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class EventVelocity implements Event {

	private static S12PacketEntityVelocity packet;
	private static boolean cancelled = false;

	public EventVelocity(S12PacketEntityVelocity packetIn) {
		this.packet = packetIn;
	}

	public static S12PacketEntityVelocity getPacket() {
		return packet;
	}

	public static void setPacket(S12PacketEntityVelocity packet1) {
		packet = packet1;
	}

	public static boolean isCancelled() {
		return cancelled;
	}

	public static void cancel() {
		cancelled = true;
	}
}
