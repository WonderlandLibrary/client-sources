package cc.slack.events.impl.network;

import cc.slack.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.Packet;

@Getter
@AllArgsConstructor
public class PacketEvent extends Event {
    private Packet packet;
    private final PacketDirection direction;


    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
    public <T extends Packet> T getPacket() {
        return (T) packet;
    }
}
