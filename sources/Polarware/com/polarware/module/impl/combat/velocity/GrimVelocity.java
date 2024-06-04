package com.polarware.module.impl.combat.velocity;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.vector.Vector2d;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import util.time.StopWatch;

public final class GrimVelocity extends Mode<VelocityModule> {
    public final BooleanValue viaVersion = new BooleanValue("Via Version", this, false);
    private int teleported,grimvelocity;
    public GrimVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (grimvelocity > 0) {
            grimvelocity++;
            if (grimvelocity == 2) {
                if (viaVersion.getValue()) {
                    PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.prevPosX, mc.thePlayer.prevPosY, mc.thePlayer.prevPosZ, mc.thePlayer.prevRotationYaw, mc.thePlayer.prevRotationPitch, mc.thePlayer.prevPosY % 0.015625 == 0));
                } else {
                    mc.timer.timerSpeed = 0.305F;
                    PacketUtil.sendNoEvent(new C03PacketPlayer(mc.thePlayer.prevPosY % 0.015625 == 0));
                }
                PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(mc.thePlayer), EnumFacing.UP));
            } else {
                mc.timer.timerSpeed = 1F;
                grimvelocity = 0;
            }
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) packet;
            if (s12.getEntityID() == mc.thePlayer.getEntityId() && teleported != mc.thePlayer.ticksExisted) {
                event.setCancelled(true);
                grimvelocity = 1;
            }
        } else if (packet instanceof S08PacketPlayerPosLook) {
            teleported = mc.thePlayer.ticksExisted;
        }
    };
}
