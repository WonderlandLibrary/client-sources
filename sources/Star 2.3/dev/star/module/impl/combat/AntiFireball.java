package dev.star.module.impl.combat;

import dev.star.event.impl.player.MotionEvent;
import dev.star.event.impl.player.UpdateEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.utils.player.RotationUtils;
import dev.star.utils.server.PacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;

public class AntiFireball extends Module {

    public AntiFireball() {
        super("AntiFireball", Category.COMBAT, "auto hits fireballs away");
    }


    @Override
    public void onMotionEvent(MotionEvent event) {

        for (Entity entity : (mc.theWorld.loadedEntityList)) {
            if (entity instanceof net.minecraft.entity.projectile.EntityFireball && mc.thePlayer.getDistanceSqToEntity(entity) < 3.0D) {

                RotationUtils.setVisualRotations(RotationUtils.getRotations(entity));
                float test[] = RotationUtils.getRotations(entity);
                event.setRotations(test[0], test[1]);
                PacketUtils.sendPacket(new C0APacketAnimation());
                PacketUtils.sendPacket(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));

            }
        }
    }
}
