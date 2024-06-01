package best.actinium.event.impl.network;

import best.actinium.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
@Getter
@Setter
@AllArgsConstructor
public class PacketDevEvent extends Event {
    private Packet<?> packet;
    private EnumPacketDirection direction;
    private final INetHandler netHandler;
}
