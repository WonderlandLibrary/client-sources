package dev.excellent.client.component.impl;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.component.Component;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;

public class ParticleRangeComponent extends Component {

    public final Listener<PacketEvent> onPacket = event -> {
        if (event.isSent()) return;
        final IPacket<?> packet = event.getPacket();

        if (packet instanceof SSpawnParticlePacket wrapper && mc.player != null) {
            final double distance = mc.player.getDistanceSq(wrapper.getXCoordinate(), wrapper.getYCoordinate(), wrapper.getZCoordinate());
            if (distance > (8 * 8)) {
                event.setCancelled(true);
            }
        }
    };
}
