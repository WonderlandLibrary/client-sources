package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.Velocity;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class HypixelAirVelocity implements IVelocity {

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                Velocity velocityModule = Slack.getInstance().getModuleManager().getInstance(Velocity.class);
                double verticalValue = velocityModule.vertical.getValue().doubleValue();

                event.cancel();
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = packet.getMotionY() * verticalValue / 100 / 8000.0;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Hypixel";
    }
}
