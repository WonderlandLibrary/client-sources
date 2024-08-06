package com.shroomclient.shroomclientnextgen.listeners;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

@RegisterListeners
public class PacketSendListener {

    public static @Nullable Entity lastHitEntity = null;
    public static long HitTime = 0;

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                lastHitEntity = C.w().getEntityById(eId);
                HitTime = System.currentTimeMillis();
            }
        }
    }

    @SubscribeEvent
    public void onRender(RenderTickEvent e) {
        if (lastHitEntity != null) {
            if (System.currentTimeMillis() - HitTime > 1500) {
                lastHitEntity = null;
            }
        }
    }
}
