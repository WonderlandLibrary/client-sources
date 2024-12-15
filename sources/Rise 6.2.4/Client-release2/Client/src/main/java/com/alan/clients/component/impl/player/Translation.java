package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.component.impl.player.translation.PacketType;
import com.alan.clients.component.impl.player.translation.Position;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import net.minecraft.network.Packet;

import java.util.HashMap;
import java.util.Map;

public class Translation extends Component {

    private Map<Integer, Position> positions = new HashMap<>();
    private final Map<Class<? extends Packet<?>>, PacketType> types = new HashMap<>();

    @Override
    public void onInit() {
        for (PacketType type : PacketType.values())
            types.put(type.getPacketClass(), type);
    }

    @EventLink
    public final Listener<PacketReceiveEvent> receive = event -> {
        if (event.isCancelled()) return;

        Packet<?> packet = event.getPacket();
        PacketType packetType = types.get(packet.getClass());

        if (packetType != null) {
            Map<Integer, Position> positions = packetType.handle(packet, this.positions);
            if (positions != null) this.positions = positions;
        }
    };

    public Position getPosition(int entityId) {
        return positions.get(entityId);
    }
}
