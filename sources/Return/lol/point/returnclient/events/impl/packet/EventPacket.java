package lol.point.returnclient.events.impl.packet;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;
import net.minecraft.network.Packet;

@AllArgsConstructor
public class EventPacket extends Event {
    public Packet<?> packet;
}
