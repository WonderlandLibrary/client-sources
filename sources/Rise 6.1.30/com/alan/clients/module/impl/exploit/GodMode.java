package com.alan.clients.module.impl.exploit;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.packet.PacketUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

@ModuleInfo(aliases = {"module.combat.godmode.name"}, description = "module.exploit.godmode.description", category = Category.EXPLOIT)
public class GodMode extends Module {

    private float yaw;
    private float pitch;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        event.setPosY(event.getPosY() - 0.05);
        event.setYaw(yaw);
        event.setPitch(pitch);
    };

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet<?> p = event.getPacket();

        if (p instanceof C02PacketUseEntity) {
            final C02PacketUseEntity wrapper = (C02PacketUseEntity) p;

            if (wrapper.getAction().equals(C02PacketUseEntity.Action.ATTACK)) {
                event.setCancelled();

                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                PacketUtil.sendNoEvent(wrapper);
                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.05, mc.thePlayer.posZ, false));
            }
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (event.getPacket() instanceof S2DPacketOpenWindow) {
            event.setCancelled();
        }
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        event.setSpeed(0);
    };

    @EventLink
    public final Listener<MoveInputEvent> onMove = event -> {
        event.setJump(false);
    };

    @Override
    public void onEnable() {
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
    }
}