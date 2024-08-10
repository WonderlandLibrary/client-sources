package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class ConditionalVelocity implements IVelocity {


    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = event.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                event.cancel();
                double yx = (double)((S12PacketEntityVelocity)event.getPacket()).getMotionY() / 8000.0;
                if (yx >= 0.0) {
                    mc.thePlayer.motionY = yx;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Conditional";
    }


}
