package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.combat.Velocity;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import cc.slack.start.Slack;
import cc.slack.utils.network.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class VulcanVelocity implements IVelocity {



    @Override
    public void onPacket(PacketEvent event) {
        final Packet<?> p = event.getPacket();

        final double horizontal = Slack.getInstance().getModuleManager().getInstance(Velocity.class).horizontal.getValue().doubleValue() ;
        final double vertical = Slack.getInstance().getModuleManager().getInstance(Velocity.class).vertical.getValue().doubleValue();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                if (horizontal == 0 && vertical == 0) {
                    event.cancel();
                    return;
                }

                wrapper.motionX *= horizontal / 100;
                wrapper.motionY *= vertical / 100;
                wrapper.motionZ *= horizontal / 100;

                event.setPacket(wrapper);
            }
        }

        if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;

            if (horizontal == 0 && vertical == 0) {
                event.cancel();
                return;
            }

            wrapper.posX *= horizontal / 100;
            wrapper.posY *= vertical / 100;
            wrapper.posZ *= horizontal / 100;

            event.setPacket(wrapper);
        }

        if (event.getPacket() instanceof C0FPacketConfirmTransaction && mc.thePlayer.hurtTime == 10) {
            PacketUtil.send(new C0FPacketConfirmTransaction((short) Integer.MAX_VALUE, (short) Integer.MIN_VALUE, false));
        }
    }

    @Override
    public String toString() {
        return "Vulcan";
    }
}
