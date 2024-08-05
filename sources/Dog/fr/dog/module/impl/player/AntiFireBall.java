package fr.dog.module.impl.player;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.packet.PacketUtil;
import fr.dog.util.player.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class AntiFireBall extends Module {
    public AntiFireBall() {
        super("AntiFireBall", ModuleCategory.PLAYER);
    }

    @SubscribeEvent
    private void onEventPre(PlayerNetworkTickEvent event) {
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLargeFireball && PlayerUtil.getBiblicallyAccurateDistanceToEntity(e) <= 6.5) {
                mc.thePlayer.swingItem();
                PacketUtil.sendPacket(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
            }
        }
    }
}
