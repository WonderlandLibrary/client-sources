package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class CancelVelocity implements IVelocity {

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                event.cancel();
            }
        }
    }

    @Override
    public String toString() {
        return "Cancel";
    }
}
