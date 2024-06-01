package com.polarware.component.impl.viamcp;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketSendEvent;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.viamcp.viamcp.ViaMCP;

public final class FlyingPacketFixComponent extends Component {

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (ViaMCP.NATIVE_VERSION > ProtocolVersion.v1_8.getVersion()) {
            final Packet<?> packet = event.getPacket();

            if (packet instanceof C03PacketPlayer) {
                final C03PacketPlayer wrapper = ((C03PacketPlayer) packet);

                if (!wrapper.isMoving() && !wrapper.isRotating()) {
                    event.setCancelled(true);
                }
            }
        }
    };
}
