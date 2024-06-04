package com.polarware.component.impl.viamcp;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketSendEvent;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.viamcp.viamcp.ViaMCP;

public final class BlockPlacementFixComponent extends Component {

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {

        if (ViaMCP.NATIVE_VERSION >= ProtocolVersion.v1_11.getVersion()) {
            final Packet<?> packet = event.getPacket();

            if (packet instanceof C08PacketPlayerBlockPlacement) {
                final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) packet);

                wrapper.facingX /= 16.0F;
                wrapper.facingY /= 16.0F;
                wrapper.facingZ /= 16.0F;

                event.setPacket(wrapper);
            }
        }
    };

}
