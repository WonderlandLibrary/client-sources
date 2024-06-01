package best.actinium.event.impl.network;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;
@Getter
@Setter
@AllArgsConstructor
public class PacketEvent extends Event {
    private Packet<?> packet;
}
