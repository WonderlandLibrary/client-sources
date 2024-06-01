package com.polarware.component.impl.event;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketReceiveEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class EntityTickComponent extends Component {

    @EventLink(value = Priority.VERY_LOW)
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (mc == null || mc.theWorld == null || mc.getNetHandler() == null) return;

        Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            Entity entity = mc.theWorld.getEntityByID(wrapper.getEntityID());

            if (entity == null) {
                return;
            }

            entity.ticksSinceVelocity = 0;
            if (wrapper.motionY / 8000.0D > 0.1 && Math.hypot(wrapper.motionZ / 8000.0D, wrapper.motionX / 8000.0D) > 0.2) {
                entity.ticksSincePlayerVelocity = 0;
            }
        } else if (packet instanceof S08PacketPlayerPosLook && mc.getNetHandler().doneLoadingTerrain) {
            mc.thePlayer.ticksSinceTeleport = 0;
        }
    };
}
