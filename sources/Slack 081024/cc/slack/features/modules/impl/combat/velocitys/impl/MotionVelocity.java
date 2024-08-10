package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.Velocity;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class MotionVelocity implements IVelocity {

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                Velocity velocityModule = Slack.getInstance().getModuleManager().getInstance(Velocity.class);
                double horizontalValue = velocityModule.horizontal.getValue().doubleValue();
                double verticalValue = velocityModule.vertical.getValue().doubleValue();

                if (horizontalValue == 0) {
                    event.cancel();
                    mc.thePlayer.motionY = packet.getMotionY() * verticalValue / 100 / 8000.0;
                } else if (verticalValue == 0) {
                    event.cancel();
                    mc.thePlayer.motionX = packet.getMotionX() * horizontalValue / 100 / 8000.0;
                    mc.thePlayer.motionZ = packet.getMotionZ() * horizontalValue / 100 / 8000.0;
                } else {
                    packet.setMotionX((int) (packet.getMotionX() * (horizontalValue / 100)));
                    packet.setMotionY((int) (packet.getMotionY() * (verticalValue / 100)));
                    packet.setMotionZ((int) (packet.getMotionZ() * (horizontalValue / 100)));
                    event.setPacket(packet);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Motion";
    }
}
