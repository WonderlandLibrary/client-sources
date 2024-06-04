package com.polarware.component.impl.performance;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2APacketParticles;

public class ParticleDistanceComponent extends Component {

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S2APacketParticles) {
            final S2APacketParticles wrapper = ((S2APacketParticles) packet);

            final double distance = mc.thePlayer.getDistanceSq(wrapper.getXCoordinate(), wrapper.getYCoordinate(), wrapper.getZCoordinate());

            if (distance > 6 * 6) {
                event.setCancelled(true);
            }
        }
    };
}