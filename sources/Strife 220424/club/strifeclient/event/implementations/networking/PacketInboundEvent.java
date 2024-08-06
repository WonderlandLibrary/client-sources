package club.strifeclient.event.implementations.networking;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;
import net.minecraft.network.Packet;

@AllArgsConstructor
public class PacketInboundEvent extends Event {
    public Packet packet;

    @SuppressWarnings("unchecked")
    public <T extends Packet<?>> T getPacket() {
        return (T) packet;
    }
}
