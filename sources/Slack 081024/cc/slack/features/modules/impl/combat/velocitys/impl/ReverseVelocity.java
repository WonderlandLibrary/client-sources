package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class ReverseVelocity implements IVelocity {

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                event.cancel();
                mc.thePlayer.motionY = packet.getMotionY() / 8000.0;
                mc.thePlayer.motionX = packet.getMotionX() / 8000.0;
                mc.thePlayer.motionZ = packet.getMotionZ() / 8000.0;
                MovementUtil.strafe();
            }
        }
    }

    @Override
    public String toString() {
        return "Reverse";
    }

}
