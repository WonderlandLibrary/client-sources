package me.vaziak.sensation.client.impl.combat;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
/**
 * Made by Jonathan H. (Niada)
 *
 * Ethereal.rip
 **/
public class MoreAttacks extends Module {
    Entity thehitentity;
    boolean moreattacks;
    private DoubleProperty prop_packets = new DoubleProperty("Attacks", "The amount of attack packets sent", null, 5, 1, 200, 10, null);

    public MoreAttacks() {
        super("More Attacks", Category.COMBAT);
        registerValue(prop_packets);
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (moreattacks) {
            for (int i = 0; i < prop_packets.getValue().intValue(); ++i) {
                mc.thePlayer.swingItem();
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(thehitentity, Action.ATTACK));
            }
            moreattacks = false;
        }
    }

    @Collect
    public void onPacketSend(SendPacketEvent sendPacketEvent) {
        if (sendPacketEvent.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity) sendPacketEvent.getPacket();
            moreattacks = true;
            thehitentity = packet.entity;
        }
    }

}
