package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.Velocity;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class HypixelVelocity implements IVelocity {

    @Override
    public void onPacket(PacketEvent event) {
            if (event.getPacket() instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity packet = event.getPacket();
                if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                    event.cancel();
                    mc.thePlayer.motionY = packet.getMotionY() * Slack.getInstance().getModuleManager().getInstance(Velocity.class).vertical.getValue().doubleValue() / 100 / 8000.0;
                }
            }
    }

    @Override
    public String toString() {
        return "Hypixel Basic";
    }
}
