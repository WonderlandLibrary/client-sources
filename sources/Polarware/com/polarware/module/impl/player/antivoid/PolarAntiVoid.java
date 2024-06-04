package com.polarware.module.impl.player.antivoid;

import com.polarware.module.impl.player.AntiVoidModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PolarAntiVoid extends Mode<AntiVoidModule> {
    private double lastGroundY, lastGroundX, lastGroundZ;

    public PolarAntiVoid(String name, AntiVoidModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSendEvent = event -> {
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (!PlayerUtil.isBlockUnder()) {
                if (mc.thePlayer.fallDistance < 5) {
                    event.setCancelled(true);
                } else {
                    MoveUtil.stop();
                    mc.thePlayer.setPosition(lastGroundX, lastGroundY, lastGroundZ);
                }
            } else {
                if (mc.thePlayer.ticksExisted % 10 == 0) {
                    lastGroundX = mc.thePlayer.posX;
                    lastGroundY = mc.thePlayer.posY;
                    lastGroundZ = mc.thePlayer.posZ;
                }
            }
        }
    };

}